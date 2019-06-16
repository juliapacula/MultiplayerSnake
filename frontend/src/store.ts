import { ROUTES } from '@/api';
import { Direction, Player, Point, Position, Snake } from '@/models';
import { Client, FrameImpl, IFrame } from '@stomp/stompjs';
import * as _ from 'lodash';
import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    id: '',
    clientSnake: {} as Snake,
    snakes: [] as Snake[],
    stompClient: {} as Client,
    isConnected: false,
    hasLost: false,
  },
  mutations: {
    setId(state, id) {
      state.id = id;
    },
    setSnake(state, positions) {
      if (positions.length === 0) {
        state.clientSnake = {} as Snake;
        return;
      }

      if (_.isEmpty(state.clientSnake)) {
        state.clientSnake = new Snake(
          new Point(positions[0].x, positions[0].y),
          positions.slice(1).map((point: Position) => new Point(point.x, point.y)));
      } else {
        state.clientSnake.createFromSnake(new Snake(
          new Point(positions[0].x, positions[0].y),
          positions.slice(1).map((point: Position) => new Point(point.x, point.y))));
      }
    },
    setSnakes(state, players) {
      state.snakes = players.map((player: Player) => new Snake(
        new Point(player.positions[0].x, player.positions[0].y),
        player.positions.slice(1).map((point: Position) => new Point(point.x, point.y))));
    },
    connect(state) {
      state.isConnected = true;
    },
    disconnect(state) {
      state.isConnected = false;
    },
    lose(state) {
      state.hasLost = true;
    },
  },
  actions: {
    async setupClient({ commit, state }) {
      state.stompClient = new Client();

      state.stompClient.configure({
        brokerURL: `ws://localhost:8088${ROUTES.websockets.game}`,
        debug: (str) => console.log(str),
        reconnectDelay: 1000,
        onConnect: (message: IFrame) => {
          commit('setId', message.headers.id);

          state.stompClient.subscribe(ROUTES.output.broadcastPositions,
              (subscribeMessage) => {
                const messageContent = JSON.parse(subscribeMessage.body) as Player[];
                const currentCientSnake = messageContent.find((snake) => snake.id === state.id);
                if (currentCientSnake) {
                  commit('setSnake', currentCientSnake.positions);
                }
                const otherSnakes = messageContent.filter((snake) => snake.id !== state.id);
                if (otherSnakes) {
                  commit('setSnakes', otherSnakes);
                }
            });

          state.stompClient.subscribe(ROUTES.output.broadcastDeaths,
              (subscribeMessage) => {
                const messageContent = JSON.parse(subscribeMessage.body) as Player;
                if (messageContent.id === state.id) {
                  commit('setSnake', []);
                  commit('lose');
                }
            });

          state.stompClient.publish({ destination: ROUTES.input.enterGame });
          commit('connect');
        },
        onWebSocketClose: () => commit('disconnect'),
        onStompError: () => commit('disconnect'),
        onDisconnect: () => {
          commit('disconnect');
        },
      });

      state.stompClient.activate();
    },
    async disconnectFromClient({ state }) {
      state.stompClient.deactivate();
    },
    async sendPosition({ state }) {
      if (state.isConnected) {
        state.stompClient.publish({
          destination: ROUTES.input.sendPosition,
          body: JSON.stringify([
            state.clientSnake.head,
            ...state.clientSnake.middles,
          ]),
        });
      }
    },
  },
});
