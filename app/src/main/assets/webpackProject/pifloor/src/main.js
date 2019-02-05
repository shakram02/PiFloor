import Vue from 'vue'
import App from './App.vue'
import BootstrapVue from 'bootstrap-vue'
//import VueSocketIO from 'vue-socket.io';
import VueNativeSock from 'vue-native-websocket'
import VueI18n from 'vue-i18n'
import { locales } from './locales.js';

//Vue.use(new VueSocketIO({connection: window.location.host}))
Vue.use(VueNativeSock, extractWebSocketAdressFromHttp());
Vue.config.productionTip = false
Vue.use(BootstrapVue);
Vue.use(VueI18n)

const i18n = new VueI18n({
  locale: 'en',
  messages: locales,
  })

new Vue({
  i18n,
  render: h => h(App),
}).$mount('#app')


//Helper functions
function extractWebSocketAdressFromHttp(){
	var webSocketUrl = window.location.origin.replace("http","ws");
	webSocketUrl+= "/game"
	return webSocketUrl
}
