import Vue from 'vue'



import { router } from './router';
import { BootstrapVue } from 'bootstrap-vue'
import store from './store';
import 'bootstrap/dist/css/bootstrap.css'
import VeeValidate from 'vee-validate';
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'bootstrap/dist/css/bootstrap.min.css';
import App from './App.vue'
import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import {
  faHome,
  faUser,
  faUserPlus,
  faSignInAlt,
  faSignOutAlt,
  faChevronDown,
  faChevronUp
} from '@fortawesome/free-solid-svg-icons';

library.add(faHome, faUser, faUserPlus, faSignInAlt, faSignOutAlt, faChevronDown, faChevronUp);
Vue.use(BootstrapVue)

Vue.config.productionTip = false;
Vue.use(VeeValidate);
Vue.component('font-awesome-icon', FontAwesomeIcon);

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
