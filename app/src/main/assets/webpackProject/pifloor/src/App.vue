<template>
  <div id="app">
    <AdminPage v-if="!playing"/>
    <GamePage v-else/>
  </div>
</template>

<script>
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import AdminPage from './components/AdminPage.vue'
import GamePage from './components/GamePage.vue'

export default {
  name: 'app',
  components: {
    AdminPage,
    GamePage
  },
  data() {
    return{
      playing: false
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
