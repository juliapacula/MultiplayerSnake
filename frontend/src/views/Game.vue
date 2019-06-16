<template>
  <div class="game-container">
    <GameBoard
      v-if="connected && snakesToDraw.length"
      :snakes="snakesToDraw"
      :size="size"
      @change-direction="changeClientSnakeDirection" />
    <div
      v-if="hasLost"
      class="lost overlay">
      <h1>You lost 😭</h1>
      <h2>Reload to join again!</h2>
    </div>
    <div
      v-else-if="connected && !isClientLoaded"
      class="spectator overlay">
      <h1>Spectator mode 😎</h1>
      <h2>There are more than 5 players in game.</h2>
    </div>
    <div
      v-else-if="!connected"
      class="no-connection overlay">
      <h1>No connection! ❌</h1>
      <h2>Trying to reconnect...</h2>
    </div>
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
    this.growInterval = setInterval(this.$__growSnake, 5000);
  },
  destroyed() {
    clearInterval(this.updateInterval);
    clearInterval(this.growInterval);
    this.$store.dispatch('disconnectFromClient');
  },
  data() {
    return {
      size: 800 - 800 % config.BLOCK_SIZE,
      updateInterval: 0,
      growInterval: 0,
    };
  },
  computed: {
    clientSnake(): Snake {
      return this.$store.state.clientSnake;
    },
    snakes(): Snake[] {
      return this.$store.state.snakes;
    },
    connected(): boolean {
      return this.$store.state.isConnected;
    },
    hasLost(): boolean {
      return this.$store.state.hasLost;
    },
    snakesToDraw(): Snake[] {
      return !this.isClientLoaded ?
        this.snakes
        : [...this.snakes, this.clientSnake];
    },
    isClientLoaded(): boolean {
      return this.clientSnake.points.length !== 0;
    },
  },
  methods: {
    changeClientSnakeDirection(direction: Direction) {
      this.clientSnake.direction = direction;
    },
    $__update() {
      if (this.connected && this.isClientLoaded) {
        this.clientSnake.move();
        this.$store.dispatch('sendPosition');
      }
    },
    $__growSnake() {
      if (this.connected && this.isClientLoaded) {
        this.clientSnake.grow();
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

.overlay {
  position: fixed;
  top: 0;
  left: 0;
  height: 100vh;
  width: 100vw;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  font-size: 2rem;
  font-weight: bold;

  &.lost {
    background-color: rgba(#000, 0.5);
  }

  &.spectator {
    background-color: rgba(#fff, 0.5);
  }

  &.no-connection {
    color: #fff;
    background-color: rgba(#c42727, 0.5);
  }

  h1,
  h2 {
    margin: 0;
  }

  h2 {
    color: darken(#fff, 80);
  }
}
</style>
