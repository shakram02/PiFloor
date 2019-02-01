<template>
  <div class="wrapper">
    <h3>Score: {{score}}</h3>
    <h3>Timer: {{timer}}s</h3>
  </div>
</template>

<script>
export default {
  data(){
    return{
      score: 0,
      timer: 10,
    }
  },
  methods: {
    correctAnswer: function(){
      this.score += 10;
    }
  }
}
</script>

<style scoped>
.wrapper {
  display: flex;
  justify-content: space-between;
}
</style>
