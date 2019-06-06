<template>
  <VueP5 v-on='{ setup, draw }'></VueP5>
</template>

<script lang='ts'>
import * as config from '@/config';
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
      blockSize: config.BLOCK_SIZE,
    };
  },
  mounted() {
    window.addEventListener('keydown', this.keydown);
  },
  destroyed() {
    window.removeEventListener('keydown', this.keydown);
  },
  methods: {
    setup(sketch: p5InstanceExtensions) {
      sketch.resizeCanvas(this.size, this.size);
    },
    draw(sketch: p5InstanceExtensions) {
      sketch.background('white');
      this.$__drawSnakes(sketch);
    },
    keydown(event: KeyboardEvent) {
      if (event.defaultPrevented) {
        return;
      }

      switch (event.key) {
        case 'Down':
        case 'ArrowDown':
          this.$emit('change-direction', Direction.DOWN);
          break;
        case 'Up':
        case 'ArrowUp':
          this.$emit('change-direction', Direction.UP);
          break;
        case 'Left':
        case 'ArrowLeft':
          this.$emit('change-direction', Direction.LEFT);
          break;
        case 'Right':
        case 'ArrowRight':
          this.$emit('change-direction', Direction.RIGHT);
          break;
        default:
          return;
      }
    },
    $__drawSnakes(sketch: p5InstanceExtensions) {
      for (const snake of this.snakes) {
        sketch.strokeWeight(1);
        sketch.stroke('black');
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
