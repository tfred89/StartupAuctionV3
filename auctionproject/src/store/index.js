import Vue from 'vue';
import Vuex from 'vuex';

import { auth } from './auth.module';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    auth
  },
  state: {
    jwtUser: {
      user: {}
    }
  },
  mutations: {
    SAVE_USER(state, payload) {
      state.jwtUser.user = payload;
    }
  }

});

