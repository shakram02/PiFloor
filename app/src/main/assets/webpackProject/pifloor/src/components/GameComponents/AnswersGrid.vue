<template>
  <b-container fluid><center>
    <b-row id="grid" no-gutters class="justify-content-center">
      <b-col v-bind:cols="numPerRow" v-for="answer in PossibleAnswers" v-bind:key="answer" class="answer">
        <span>{{answer}}</span>
      </b-col>
    </b-row>
  </center></b-container>
</template>

<script>
export default {
  props: ["PossibleAnswers"],
  data(){
    return{
    }
  },
  methods: {
    correctAnswer: function(){
      this.score += 10;
    }
  },
  computed: {
    numPerRow: function(){
      let num = this.PossibleAnswers.length;
      num = Math.ceil(Math.sqrt(num))
      return 12/num;
    }
  }
}
</script>

<style scoped>
.answer{
  padding: 10px;
  margin-bottom: 50px;
}
span{
  background-color: lightgreen;
  color: white;
  font-size: 35px;
  padding: 20px;
  width: 80%;
  display: block;
}
#grid{
  width: 80%;
  padding: 50px;
}

</style>
