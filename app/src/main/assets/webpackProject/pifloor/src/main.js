import Vue from 'vue'
import App from './App.vue'
import BootstrapVue from 'bootstrap-vue'
import VueSocketIO from 'vue-socket.io';
import VueI18n from 'vue-i18n'

Vue.use(new VueSocketIO({connection: window.location.host}))
Vue.config.productionTip = false
Vue.use(BootstrapVue);
Vue.use(VueI18n)

const i18n = new VueI18n({
  locale: 'ar',
})

new Vue({
  i18n,
  render: h => h(App),
}).$mount('#app')
