import { ROUTES } from '@/api';
import { Direction, Point, Snake } from '@/models';
import { Client } from '@stomp/stompjs';
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
  },
  mutations: {
    setId(state, id) {
      state.id = id;
    },
    setSnake(state, positions) {
      state.clientSnake = new Snake(
        new Point(positions[0].x, positions[0].y),
        positions.slice(1).map((point: any) => new Point(point.x, point.y)));
    },
    connect(state) {
      state.isConnected = true;
    },
    disconnect(state) {
      state.isConnected = false;
    },
  },
  actions: {
    async setupClient({ commit, state }) {
      state.stompClient = new Client();

      state.stompClient.configure({
        brokerURL: `ws://localhost:8088${ROUTES.websockets.game}`,
        debug: (str) => console.log(str),
        onConnect: () => {
          state.stompClient.subscribe(ROUTES.output.enterGame,
            (message) => {
              const messageContent = JSON.parse(message.body);
              commit('setId', messageContent.id);
              commit('setSnake', messageContent.positions);
              commit('connect');
            });
        },
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
          body: JSON.stringify({
            id: state.id,
            positions: [
              state.clientSnake.head,
              ...state.clientSnake.middles,
            ],
          }),
        });
      }
    },
  },
});
