<template>
  <div id="app" :key="theme">
    <ColorChooser />
    <div v-if="loggedin">
      <AdminPage v-if="!playing"/>
      <GamePage v-else/>
    </div>
    <HomePage v-else />
  </div>
</template>

<script>
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import { availableLocales } from './locales.js';
import AdminPage from './components/AdminPage.vue';
import GamePage from './components/GamePage.vue';
import HomePage from './components/HomePage.vue';
import ColorChooser from './components/ColorChooser.vue';
import './styles/irregularButton.scss';
import './styles/irregularContainer.scss';

export default {
  name: 'app',
  components: {
    AdminPage,
    GamePage,
    HomePage,
    ColorChooser,
  },
  data: function () {
    return{
      lang: availableLocales,
      questions: [],
      playing: false,
      loggedin: false
    }
  }
}
</script>

<style scoped>
#app {
  margin-top: 60px;
}
</style>
