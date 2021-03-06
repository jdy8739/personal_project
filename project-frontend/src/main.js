import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'

// import "materialize-css/dist/css/materialize.css"; 
// import "material-design-icons/iconsfont/material-icons.css";

import VueAgile from 'vue-agile';
Vue.use(VueAgile);

import cookies from 'vue-cookies';
Vue.use(cookies);

import axios from 'axios';
Vue.use(axios);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
