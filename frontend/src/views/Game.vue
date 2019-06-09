<template>
  <div class="game-container">
    <GameBoard
      v-if="connected"
      :snakes="[...snakes, clientSnake]"
      :size="size"
      @change-direction="changeClientSnakeDirection" />
  </div>
</template>

<script lang="ts">
import { ROUTES } from '@/api';
import GameBoard from '@/components/GameBoard.vue';
import * as config from '@/config';
import { Direction, Point, Snake } from '@/models';
import Vue from 'vue';

export default Vue.extend({
  name: 'Game',
  components: {
    GameBoard,
  },
  mounted() {
    this.$store.dispatch('setupClient');
    this.updateInterval = setInterval(this.$__update, 1000);
  },
  destroyed() {
    clearInterval(this.updateInterval);
    this.$store.dispatch('disconnectFromClient');
  },
  data() {
    return {
      size: 800 - 800 % config.BLOCK_SIZE,
      updateInterval: 0,
    };
  },
  computed: {
    clientSnake(): Snake {
      return this.$store.state.clientSnake;
    },
    snakes(): Snake {
      return this.$store.state.snakes;
    },
    connected(): boolean {
      return this.$store.state.isConnected;
    },
  },
  methods: {
    changeClientSnakeDirection(direction: Direction) {
      this.clientSnake.direction = direction;
    },
    $__update() {
      if (this.connected) {
        this.clientSnake.move();
        this.$store.dispatch('sendPosition');
      }
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
