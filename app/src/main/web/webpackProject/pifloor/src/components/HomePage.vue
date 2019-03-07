<template>
  <div>
    <b-col offset-md="4" cols="4" align-self="center">
      <div v-bind:class="['shape-container-' + theme]">
        <div  class="container-menu">
          <h1>{{ $t('PiFloor') }}</h1>
          <br/>
          <div v-bind:class="['shape-' + theme]">
            <b-btn variant="outline-secondary" @click="useAsTeacher">Teacher</b-btn>
          </div>
          </br>
          <div v-bind:class="['shape-' + theme]">
          <b-btn variant="outline-secondary" @click="useAsStudent">Student</b-btn>
          </div>
        </div>
      </div>
    </b-col>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  name: 'Home',
  computed: mapGetters([
    'theme',
  ]),
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
