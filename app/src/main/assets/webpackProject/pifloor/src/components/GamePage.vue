<template>
  <div>
    <div v-if="questions.length">
      <ScoreBoard></ScoreBoard>
      <QuestionBody>{{questionText}}</QuestionBody>
      <AnswersGrid v-bind:PossibleAnswers="possibleAnswers"/>
      <b-btn variant="primary" @click="nextQuestion">Next Question</b-btn>
    </div>
    <b-modal ref="helperModal">
      <div slot="modal-header"></div>
      <p v-if="failed">No questions added yet!</p>
      <p v-else>Congrats, You finished your questions!</p>
      <div slot="modal-footer">
        <b-btn variant="primary" @click="returnToHome">Back</b-btn>
      </div>
    </b-modal>
  </div>
</template>

<script>
import ScoreBoard from './GameComponents/ScoreBoard.vue'
import QuestionBody from './GameComponents/QuestionBody.vue'
import AnswersGrid from './GameComponents/AnswersGrid.vue'

export default {
  name: 'AdminPage',
  components :{
    ScoreBoard,
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
h1{
  color: blue;
}
</style>
