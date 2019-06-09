<template>
  <div class="main-screen">
    <button
      @click="$router.push({ name: 'gameBoard' })"
      class="btn">Start game</button>
  </div>
</template>

<script lang="ts">
import Vue from 'vue';

export default Vue.extend({
  name: 'Home',
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

