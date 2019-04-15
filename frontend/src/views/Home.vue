<template>
  <div class="home">
    <button @click="sendCoords">Send random position</button>
    <button @click="getLastPosition">Get last position</button>
    <div>
      {{ `${last.x} ${last.y}` }}
    </div>
    <div v-if="stompClient.active">
      Aktywny
    </div>
  </div>
</template>

<script lang="ts">
import HelloWorld from '@/components/HelloWorld.vue';
import { ROUTES } from '@/api';
import Vue from 'vue';
import SocketJS from 'sockjs-client';
import { Stomp, CompatClient, FrameImpl } from '@stomp/stompjs';

export default Vue.extend({
  name: 'Home',
  components: {
    HelloWorld,
  },
  created() {
    this.stompClient = Stomp.over(new SocketJS(ROUTES.websockets.game));
    this.stompClient.connect({}, (frame: FrameImpl) => {
      this.stompClient.subscribe(ROUTES.output.game, (message) => {
        console.log(JSON.parse(message.body));
        this.last = JSON.parse(message.body);
      });
    });
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
    };
  },
  methods: {
    sendCoords(): void {
      let object = {
        x: Math.random() * 10 % 10,
        y: Math.random() * 10 % 10,
      };
      console.log(object);
      this.stompClient.send(ROUTES.input.sendPosition, {}, JSON.stringify(object));
    },
    getLastPosition(): void {
      this.stompClient.send(ROUTES.input.getLast, {}, '');
    },
  },
});
</script>
