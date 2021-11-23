import Vue from 'vue'
import App from './App.vue'
import router from "./router/router"
import store from './vuex/store'
import './assets/bootstrap.min.css'

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')
