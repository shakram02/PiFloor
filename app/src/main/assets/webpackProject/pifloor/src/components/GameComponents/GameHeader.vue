<template>
  <b-row class="wrapper" v-if="$i18n.locale === 'ar'">
    <b-col cols="3" class="" v-bind:class="['shape-' + $root.$children[0].themeColor]">
      <h3>{{score}} : {{$t('Score')}}</h3>
    </b-col>
    <b-col cols="3" v-bind:class="['shape-' + $root.$children[0].themeColor]">
      <h3>{{timer}} : {{$t('Timer')}}</h3>
    </b-col>
  </b-row>
  <b-row class="wrapper" v-else>
    <b-col cols="3" class="" v-bind:class="['shape-' + $root.$children[0].themeColor]">
      <h3>{{$t('Score')}}: {{score}}</h3>
    </b-col>
    <b-col cols="3" v-bind:class="['shape-' + $root.$children[0].themeColor]">
      <h3>{{$t('Timer')}}: {{timer}}s</h3>
    </b-col>
  </b-row>
</template>

<script>
export default {
  data(){
    return{
      score: 0,
      timer: 10,
      timeout: false,
      timeVariable : null
    }
  },
  methods: {
    correctAnswer: function(){
      this.stopTimer()
      this.timer = 10;
      this.score += 10;
    },
    wrongAnswer: function(){
      this.stopTimer();
      this.timer = 10;
    },
    startTimeDown: function(){
      this.timeVariable = setInterval(()=>{
        if(this.timer === 0){
          this.$parent.getUpset();
        }
        else this.timer -= 1;
      },1000)
    },
    stopTimer: function(){
      clearInterval(this.timeVariable);
    }
  },
  mounted(){
    this.startTimeDown();
  }
}
</script>

<style scoped>
.wrapper {
  display: flex;
  justify-content: space-between;
}
</style>
