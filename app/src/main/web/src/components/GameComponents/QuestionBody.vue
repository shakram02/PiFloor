<template>
  <div >
    <h1 v-bind:class="['shape-' + theme]"><span ref="question"><slot></slot></span></h1>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  computed: mapGetters([
    'theme'
  ]),
  methods: {
    correctAnswer: function(){
      this.$refs.question.classList.add("correct");
      setTimeout(()=>{
        this.$refs.question.classList.remove("correct");
      }, 800)
    },
    wrongAnswer: function(){
      this.$refs.question.classList.add("wrong");
      setTimeout(()=>{
        this.$refs.question.classList.remove("wrong");
      }, 800)
    }
  }
}
</script>

<style scoped>
h1{
  border-radius: 5px;
  padding: 1%;
  position: relative;
  transform: rotateY(15deg) translateY(-50%);
  width: 70%;
  margin: auto;
}
.correct{
  color: lawngreen;
  animation: shakeV 0.8s cubic-bezier(.36,.07,.19,.97) both;
}
.wrong{
  color: red;
  animation: shakeH 0.8s cubic-bezier(.36,.07,.19,.97) both;
  transform: translate3d(0, 0, 0);
  backface-visibility: hidden;
  perspective: 1000px;
}

.correct, .wrong{
  transform: translate3d(0, 0, 0);
  backface-visibility: hidden;
  perspective: 1000px;
}

@keyframes shakeV {
  10%, 90% {
    transform: translate3d(0, -1px, 0);
  }

  20%, 80% {
    transform: translate3d(0, 2px, 0);
  }

  30%, 50%, 70% {
    transform: translate3d(0, -4px, 0);
  }

  40%, 60% {
    transform: translate3d(0, 4px, 0);
  }
}

@keyframes shakeH {
  10%, 90% {
    transform: translate3d(-1px, 0, 0);
  }

  20%, 80% {
    transform: translate3d(2px, 0, 0);
  }

  30%, 50%, 70% {
    transform: translate3d(-4px, 0, 0);
  }

  40%, 60% {
    transform: translate3d(4px, 0, 0);
  }
}
</style>
