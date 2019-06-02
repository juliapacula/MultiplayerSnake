<template>
  <div class="home">
    <GameBoard
      :snakes="[...snakes, clientSnake]"
      :size="1000"
      @change-direction="changeClientSnakeDirection" />
  </div>
</template>

<script lang="ts">
import GameBoard from '@/components/GameBoard.vue';
import { ROUTES } from '@/api';
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
      this.stompClient.subscribe(ROUTES.output.game, (message) => {
        // console.log(JSON.parse(message.body));
        this.last = JSON.parse(message.body);
      });
    });
  },
  mounted() {
    this.clientSnake.color = '#e2e2e2';
    this.updateInterval = setInterval(this.update, 1000);
  },
  destroyed() {
    this.stompClient.disconnect();
  },
  data() {
    return {
      stompClient: {} as CompatClient,
      last: {
        x: 0,
        y: 0,
      },
      snakes: [],
      clientSnake: new Snake(new Point(10, 8), [new Point(10, 5)]),
      updateInterval: 0,
    };
  },
  methods: {
    changeClientSnakeDirection(direction: Direction) {
      this.clientSnake.direction = direction;
    },
    update() {
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
      this.stompClient.send(ROUTES.input.getLast, {}, '');
    },
  },
});
</script>
