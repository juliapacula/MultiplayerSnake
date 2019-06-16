import { ROUTES } from '@/api';
import { Player, Point, Position, Snake } from '@/models';
import { Client, IFrame } from '@stomp/stompjs';
import * as _ from 'lodash';
import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    id: '',
    clientSnake: new Snake('', []),
    snakes: [] as Snake[],
    stompClient: {} as Client,
    isConnected: false,
    hasLost: false,
  },
  mutations: {
    setId(state, id) {
      state.id = id;
    },
    setColor(state, color) {
      state.clientSnake.color = color;
    },
    setSnake(state, positions) {
      if (positions.length === 0) {
        state.clientSnake.points = [];
        return;
      }

      const newSnake = new Snake(state.id, positions.map((point: Position) => new Point(point.x, point.y)));
      state.clientSnake.createFromSnake(newSnake);
    },
    setSnakes(state, players) {
      state.snakes = players.map((player: Player) => {
        return new Snake(player.id,
          player.positions.map((point: Position) => new Point(point.x, point.y)),
          player.color);
      });
    },
    removeSnake(state, id) {
      state.snakes = state.snakes.filter((snake) => snake.id !== id);
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
        // debug: (str) => console.log(str),
        reconnectDelay: 1000,
        onConnect: (message: IFrame) => {
          commit('setId', message.headers.id);

          state.stompClient.subscribe(ROUTES.output.broadcastPositions,
              (subscribeMessage) => {
                const messageContent = JSON.parse(subscribeMessage.body) as Player[];
                const currentCientSnake = messageContent.find((snake) => snake.id === state.id);
                if (currentCientSnake) {
                  commit('setSnake', currentCientSnake.positions);
                  if (currentCientSnake.color !== state.clientSnake.color) {
                    commit('setColor', currentCientSnake.color);
                  }
                }
                const otherSnakes = messageContent.filter((snake) => snake.id !== state.id);
                if (otherSnakes) {
                  commit('setSnakes', otherSnakes);
                }
            });

          state.stompClient.subscribe(ROUTES.output.broadcastDeaths,
              (subscribeMessage) => {
                const deadPlayer = JSON.parse(subscribeMessage.body) as Player;
                if (deadPlayer.id === state.id) {
                  commit('setSnake', []);
                  commit('lose');
                } else {
                  commit('removeSnake', deadPlayer.id);
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
            ...state.clientSnake.points,
          ]),
        });
      }
    },
  },
});
