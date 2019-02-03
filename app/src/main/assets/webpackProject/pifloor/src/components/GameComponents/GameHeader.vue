<template>
  <div class="wrapper" v-if="$i18n.locale==='ar'">
    <h3>{{score}} :{{t('Score')}}</h3>
    <h3>{{timer}} :{{t('Timer')}}</h3>
  </div>
  <div class="wrapper" v-else>
    <h3>{{t('Score')}}: {{score}}</h3>
    <h3>{{t('Timer')}}: {{timer}}s</h3>
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
