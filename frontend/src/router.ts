import { Game, Home } from '@/views';
import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
    },
    {
      path: '/gameplay',
      name: 'gameBoard',
      component: Game,
    },
  ],
});
