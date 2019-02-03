<template>
  <div>
    <b-container class="wrapper" v-if="questions.length">
      <GameHeader ref="gameHeader"></GameHeader>

      <!-- Testing components -->
      <div id="test" v-if="testing">
        <input type="text" v-model="testAnswer">
        <button type="button" @click="checkAnswer(testAnswer)">Submit</button>
      </div>

      <b-container class="gameContainer">
        <QuestionBody>{{questionText}}</QuestionBody>
        <AnswersGrid v-bind:PossibleAnswers="possibleAnswers"/>
        <b-btn class="nextQues" variant="outline-secondary" @click="nextQuestion">Next Question</b-btn>
      </b-container>
    </b-container>
    <b-modal ref="helperModal">
      <div slot="modal-header"></div>
      <p v-if="failed">No questions added yet!</p>
      <p v-else>Congrats, You finished your questions!</p>
      <div slot="modal-footer">
        <b-btn variant="outline-secondary" @click="returnToHome">Back</b-btn>
      </div>
    </b-modal>
  </div>
</template>

<script>
import GameHeader from './GameComponents/GameHeader.vue'
import QuestionBody from './GameComponents/QuestionBody.vue'
import AnswersGrid from './GameComponents/AnswersGrid.vue'

export default {
  name: 'GamePage',
  components :{
    GameHeader,
    QuestionBody,
    AnswersGrid
  },
  data(){
    return{
      questions : [],
      questionIndex: 0,
      failed: true,
      testing: true,
      testAnswer: ""
    }
  },
  computed: {
    questionText: function(){
      return this.questions[this.questionIndex].question;
    },
    correctAnswer: function(){
      return this.questions[this.questionIndex].correct;
    },
    possibleAnswers: function(){
      return this.questions[this.questionIndex].choices;
    }
  },
  methods: {
    nextQuestion: function(){
      if(this.questionIndex == this.questions.length -1){
        // Display celebration ...
        // eslint-disable-next-line
        let sound = new Audio(require('../assets/soundEffects/success.mp3'))
        sound.play()
        this.failed = false;
        this.$refs.helperModal.show();
      }
      else  this.questionIndex++;
    },
    returnToHome: function(){
      this.failed = true;
      this.$parent.playing = false;
    },
    checkAnswer: function(answer){
      let ansIndex = this.possibleAnswers.indexOf(this.correctAnswer);
      if(parseInt(answer) === ansIndex) this.celebrate();
      else this.getUpset();
      setTimeout(this.nextQuestion, 3000);
    },
    celebrate: function(){
      // TODO: Animate celebrations
      this.$refs.gameHeader.correctAnswer();
      // eslint-disable-next-line
      let sound = new Audio(require('../assets/soundEffects/correct.mp3'));
      sound.play();
    },
    getUpset: function(){
      // TODO: Animate disappointment
      // eslint-disable-next-line
      console.log("Wrong answer");
      // eslint-disable-next-line
      let sound = new Audio(require('../assets/soundEffects/fail.mp3'))
      sound.play()

    }
  },
  mounted() {
    let rawQuestions = this.$parent.questions;
    if(rawQuestions.length)  this.questions = rawQuestions;
    else  this.$refs.helperModal.show();
  },
  sockets: {
      connect: function () {
          // eslint-disable-next-line
          console.log("We're connected!");
      },
      game: function (data) {
          if(isNaN(data)) alert('Wrong data type!');
          else this.checkAnswer(data);
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
