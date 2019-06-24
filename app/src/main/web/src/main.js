import Vue from 'vue';
import App from './App.vue';
import BootstrapVue from 'bootstrap-vue';
//import VueSocketIO from 'vue-socket.io';
import VueNativeSock from 'vue-native-websocket';
import VueI18n from 'vue-i18n';
import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { dom } from '@fortawesome/fontawesome-svg-core'
import store from './store.js';
import { locales } from './locales.js';

dom.watch();

//Vue.use(new VueSocketIO({connection: window.location.host}))
Vue.use(VueNativeSock, extractWebSocketAdressFromHttp());
Vue.config.productionTip = false
Vue.use(BootstrapVue);
Vue.use(VueI18n)

library.add(fas)

const i18n = new VueI18n({
  locale: 'English',
  messages: locales,
  })

new Vue({
  i18n,
  store,
  components: {FontAwesomeIcon},
  render: h => h(App),
}).$mount('#app')


//Helper functions
function extractWebSocketAdressFromHttp(){
	var webSocketUrl = window.location.origin.replace("http","ws");
	webSocketUrl+= "/game"
	return webSocketUrl
}
