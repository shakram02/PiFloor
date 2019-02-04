<template>
  <div>
    <b-col offset-md="4" cols="4" align-self="center">
      <div class="shape-container">
        <div  class="container-menu">
          <h1>{{ $t('PiFloor') }}</h1>
          <br/>
          <div v-bind:class="['shape-' + $root.$children[0].themeColor]">
            <b-btn variant="outline-secondary" @click="useAsTeacher">Teacher</b-btn>
          </div>
          </br>
          <div v-bind:class="['shape-' + $root.$children[0].themeColor]">
          <b-btn variant="outline-secondary" @click="useAsStudent">Student</b-btn>
          </div>
        </div>
      </div>
    </b-col>
  </div>
</template>

<script>

export default {
  name: 'home',
  methods: {
    useAsTeacher : function(){
      this.$parent.loggedin = true;
    },
    useAsStudent: function(){
      this.$parent.playing = true;
      this.$parent.loggedin = true;
    }
  }
}
</script>

<style lang="scss" scoped>
h1 {
  font-family: cursive;
  color: #325575;
}
.container-menu {
  padding: 20px;
  width: fit-content;
  margin: auto;
}
.shape-container { // problem with responsiveness with border-bottom
  width: 70%;
  background-color: #FFC000;
  border-bottom: 10px solid #ED7D31;
}
</style>
