<template>
  <div>
    <h3>Score: {{score}}</h3>
  </div>
</template>

<script>
export default {
  data(){
    return{
      score: 0
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

</style>
