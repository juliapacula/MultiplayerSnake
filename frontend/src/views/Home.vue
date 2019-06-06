<template>
  <div class="main-screen">
    <button class="btn">Start game</button>
  </div>
</template>

<script lang="ts">
import { ROUTES } from '@/api';
import GameBoard from '@/components/GameBoard.vue';
import * as config from '@/config';
import { Snake } from '@/models/snake';
import { Point } from '@/models/point';
import Vue from 'vue';
import SocketJS from 'sockjs-client';
import { Stomp, CompatClient, FrameImpl } from '@stomp/stompjs';
import { Direction } from '../models/directions.enum';

export default Vue.extend({
  name: 'Game',
  components: {
    GameBoard,
  },
  created() {
    this.stompClient = Stomp.over(new SocketJS(ROUTES.websockets.game));
    this.stompClient.connect({}, (frame: FrameImpl) => {
      this.stompClient.subscribe(ROUTES.output.enterGame, (message) => {
        const id = JSON.parse(message.body);
        this.$router.push({ name: 'gameBoard', params: { id } })
      });
    });
  },
  destroyed() {
    this.stompClient.disconnect();
  },
  data() {
    return {
      stompClient: {} as CompatClient,
    };
  },
  methods: {
    startGame() {
      this.stompClient.send(ROUTES.input.enterGame, {}, "");
    },
  },
});
</script>

<style lang="scss" scoped>
.main-screen {
  display: flex;
  align-items: center;
  justify-content: center;
  min-width: 100vw;
  min-height: 100vh;
  background-image: linear-gradient(to top, #a8edea 0%, #fed6e3 100%);
}

.btn {
  font-size: 2rem;
  background-color: #fed6e3;
  padding: 1rem 2rem;
  border: 0;
  border-radius: 50px;
  color: #fff;
  cursor: pointer;
  box-shadow: 0 10px 20px rgba(0,0,0,0.19), 0 6px 6px rgba(0,0,0,0.23);
  transition: box-shadow 500ms;

  &:focus,
  &:active {
    outline: none;
  }

  &:hover {
    box-shadow: 0 19px 38px rgba(0,0,0,0.30), 0 15px 12px rgba(0,0,0,0.22);
  }
}
</style>

