<template>
  <b-row class="wrapper-space-between" v-if="$i18n.locale === 'ar'">
    <b-col cols="3" v-bind:class="['shape-' + $root.$children[0].theme]">
      <h3>{{score}} : {{$t('Score')}}</h3>
    </b-col>
    <b-col cols="3" v-bind:class="['shape-' + $root.$children[0].theme]">
      <h3>{{timer}} : {{$t('Timer')}}</h3>
    </b-col>
  </b-row>
  <b-row class="wrapper-space-between" v-else>
    <b-col cols="3" v-bind:class="['shape-' + $root.$children[0].theme]">
      <h3>{{$t('Score')}}: {{score}}</h3>
    </b-col>
    <b-col cols="3" v-bind:class="['shape-' + $root.$children[0].theme]">
      <h3>{{$t('Timer')}}: {{timer}}s</h3>
    </b-col>
  </b-row>
</template>

<script>
export default {
  data(){
    return{
      score: 0,
      timer: 30,
      timeout: false,
      timeVariable : null
    }
  },
  methods: {
    correctAnswer: function(){
      this.score += 10;
    },
    wrongAnswer: function(){
    },
    startTimeDown: function(){
      this.timeVariable = setInterval(()=>{
        if(this.timer === 0){
          this.stopTimer();
          this.$parent.getUpset();
        }
        else this.timer -= 1;
      },1000)
    },
    stopTimer: function(){
      clearInterval(this.timeVariable);
      this.timer = 30;
    }
  },
  mounted(){
    this.startTimeDown();
  }
}
</script>
