<template>
  <div>
    <b-col offset-md="4" cols="4" align-self="center">
      <div v-bind:class="['shape-container-' + $root.$children[0].themeColor]">
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
    useAsTeacher : function() {
      this.$parent.loggedin = true;
    },
    useAsStudent: function() {
      this.$parent.playing = true;
      this.$parent.loggedin = true;
    }
  }
}
</script>
