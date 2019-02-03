<template>
  <div>
    <b-btn variant="outline-secondary" @click="useAsTeacher">Teacher</b-btn>
    <b-btn variant="outline-secondary" @click="useAsStudent">Student</b-btn>
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

<style scoped>

</style>
