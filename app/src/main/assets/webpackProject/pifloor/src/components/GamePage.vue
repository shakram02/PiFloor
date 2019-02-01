<template>
  <b-container class="wrapper">
    <GameHeader></GameHeader>
    <b-container class="gameContainer" v-if="questions.length">
      <QuestionBody>{{questionText}}</QuestionBody>
      <AnswersGrid v-bind:PossibleAnswers="possibleAnswers"/>
      <b-btn class="nextQues" variant="outline-secondary" @click="nextQuestion">Next Question</b-btn>
    </b-container>
    <b-modal ref="helperModal">
      <div slot="modal-header"></div>
      <p v-if="failed">No questions added yet!</p>
      <p v-else>Congrats, You finished your questions!</p>
      <div slot="modal-footer">
        <b-btn variant="outline-secondary" @click="returnToHome">Back</b-btn>
      </div>
    </b-modal>
  </b-container>
</template>

<script>
import GameHeader from './GameComponents/GameHeader.vue'
import QuestionBody from './GameComponents/QuestionBody.vue'
import AnswersGrid from './GameComponents/AnswersGrid.vue'

export default {
  name: 'AdminPage',
  components :{
    GameHeader,
    QuestionBody,
    AnswersGrid
  },
  data(){
    return{
      questions : [],
      questionIndex: 0,
      failed: true
    }
  },
  computed: {
    questionText: function(){
      return this.questions[this.questionIndex].body;
    },
    correctAnswer: function(){
      return this.questions[this.questionIndex].correct;
    },
    possibleAnswers: function(){
      return this.questions[this.questionIndex].answers;
    }
  },
  methods: {
    preprocessQuestions: function(rawQuestions){
      for(let i=0; i<rawQuestions.length; i++){
        let sepQuestions = rawQuestions[i].split(',');
        let obj = {
          'body': sepQuestions[0],
          'correct': sepQuestions[1],
          'answers': sepQuestions.slice(2)
        }
        this.questions.push(obj);
      }
    },
    nextQuestion: function(){
      if(this.questionIndex == this.questions.length -1){
        // Display celebration ...
        this.failed = false;
        this.$refs.helperModal.show();
      }
      else  this.questionIndex++;
    },
    returnToHome: function(){
      this.failed = true;
      this.$parent.playing = false;
    }
  },
  mounted() {
    let rawQuestions = this.$parent.questions;
    if(rawQuestions.length)  this.preprocessQuestions(rawQuestions);
    else  this.$refs.helperModal.show();
  },
  sockets: {
      connect: function () {
          this.$socket.emit('connected', "We're not connected")
      },
      message: function (data) {
          // To be configured
      }
  }
}

</script>

<style scoped>
  .wrapper {
    padding: 10px;
  }
  .gameContainer {
    border: solid 2px grey;
    border-radius: 5px;
    padding: 20px;
  }
  .nextQues {
    margin-left: auto;
    display: block;
  }
</style>
