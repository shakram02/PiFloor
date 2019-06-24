<template>
  <div >
    <h1 v-bind:class="['shape-' + theme]" ref="question"><slot></slot></h1>
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
}
.wrong{
  color: red;
}
</style>
