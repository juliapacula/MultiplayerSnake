<template>
  <div class="game-grid">
    <VueP5 v-on="{ setup, draw }"></VueP5>
  </div>
</template>

<script lang="ts">
import { Direction } from '@/models/directions.enum';
import { Point } from '@/models/point';
import { Snake } from '@/models/snake';
import { p5InstanceExtensions } from 'p5';
import Vue from 'vue';
import VueP5 from 'vue-p5';

export default Vue.extend({
  name: 'GameBoard',
  components: {
    VueP5,
  },
  props: {
    snakes: Array as () => Snake[],
    size: Number,
  },
  data() {
    return {
      blockSize: 20,
    };
  },
  methods: {
    setup(sketch: p5InstanceExtensions) {
      sketch.resizeCanvas(this.size, this.size);
    },
    draw(sketch: p5InstanceExtensions) {
      sketch.background('lightpink');
      this.$__drawSnakes(sketch);
    },
    $__drawSnakes(sketch: p5InstanceExtensions) {
      for (const snake of this.snakes) {
        sketch.strokeWeight(1);
        sketch.stroke(sketch.brightness(snake.color));
        sketch.fill(snake.color);
        sketch.rect(
          snake.head.scale(this.blockSize).getX(),
          snake.head.scale(this.blockSize).getY(),
          this.blockSize,
          this.blockSize);
        for (const block of snake.getBlocks()) {
          sketch.rect(
            block.scale(this.blockSize).getX(),
            block.scale(this.blockSize).getY(),
            this.blockSize,
            this.blockSize);
        }
      }
    },
  },
});
</script>

<style scoped lang="scss">
.game-grid {
  height: 100%;
  width: 100%;
}
</style>
