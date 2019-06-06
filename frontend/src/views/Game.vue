<template>
  <div class="game-container">
    <GameBoard
      :snakes="[...snakes, clientSnake]"
      :size="size"
      @change-direction="changeClientSnakeDirection" />
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
  },
  mounted() {
    this.clientSnake.color = '#e2e2e2';
    this.updateInterval = setInterval(this.$__update, 1000);
  },
  destroyed() {
    this.stompClient.disconnect();
    clearInterval(this.updateInterval);
  },
  data() {
    return {
      stompClient: {} as CompatClient,
      last: {
        x: 0,
        y: 0,
      },
      size: window.innerWidth - window.innerWidth % config.BLOCK_SIZE,
      snakes: [],
      clientSnake: new Snake(new Point(10, 8), [new Point(10, 5)]),
      updateInterval: 0,
    };
  },
  methods: {
    changeClientSnakeDirection(direction: Direction) {
      this.clientSnake.direction = direction;
    },
    $__update() {
      this.clientSnake.move();
    },
    sendCoords(): void {
      const object = {
        x: Math.random() * 10 % 10,
        y: Math.random() * 10 % 10,
      };
      this.stompClient.send(ROUTES.input.sendPosition, {}, JSON.stringify(object));
    },
    getLastPosition(): void {
      // this.stompClient.send(ROUTES.input.getLast, {}, '');
    },
  },
});
</script>

<style lang="scss" scoped>
.game-container {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  min-height: 100vh;
  background-image: linear-gradient(to right, #ffecd2 0%, #fcb69f 100%);
}
</style>
