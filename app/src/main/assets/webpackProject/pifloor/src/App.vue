<template>
  <div id="app">
    <div v-if="loggedin">
      <AdminPage v-if="!playing"/>
      <GamePage v-else/>
    </div>
    <HomePage v-else />
  </div>
</template>

<script>
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import AdminPage from './components/AdminPage.vue'
import GamePage from './components/GamePage.vue'
import HomePage from './components/HomePage.vue'
import './styles/irregularButton.scss';

export default {
  name: 'app',
  components: {
    AdminPage,
    GamePage,
    HomePage
  },
  data() {
    return{
      lang: ['en', 'ar'],
      questions: [],
      themeColor: 'blue',
      playing: false,
      loggedin: false
    }
  }
}
</script>

<style scoped>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
