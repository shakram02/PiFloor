<template>
  <div>
    <b-container v-if="questions.length">
      <GameHeader ref="gameHeader"></GameHeader>
      </br>
      </br>
      </br>
      <b-container class="game-container">
        <QuestionBody>{{questionText}}</QuestionBody>
        <AnswersGrid v-bind:PossibleAnswers="possibleAnswers"/>
        </br>
        <div class="next-ques" v-bind:class="['shape-' + $root.$children[0].themeColor]">
          <b-btn variant="outline-secondary" @click="nextQuestion">{{ $t('NextQuestion') }}</b-btn>
        </div>
      </b-container>
    </b-container>
    <b-modal ref="helperModal">
      <div slot="modal-header"></div>
      <p v-if="failed">{{ $t('NoQuestionsYet') }}</p>
      <p v-else>{{ $t('CongratsYouFinished') }}</p>
      <div slot="modal-footer">
        <b-btn variant="outline-secondary" @click="returnToHome">{{ $t('Back') }}</b-btn>
      </div>
    </b-modal>
    <!-- Testing components -->
      <div id="test" v-if="testing">
        </br>
        <input type="text" v-model="testAnswer">
        
        <button type="button" @click="checkAnswer(testAnswer)">Submit</button>
      </div>
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
      else{
        this.questionIndex++;
        this.$refs.gameHeader.startTimeDown()
      }
    },
    returnToHome: function(){
      this.failed = true;
      this.$parent.playing = false;
    },
    checkAnswer: function(answer){
      let ansIndex = this.possibleAnswers.indexOf(this.correctAnswer);
      if(parseInt(answer) === ansIndex) this.celebrate();
      else this.getUpset();
    },
    celebrate: function(){
      // TODO: Animate celebrations
      this.$refs.gameHeader.correctAnswer();
      // eslint-disable-next-line
      let sound = new Audio(require('../assets/soundEffects/correct.mp3'));
      sound.play();
      setTimeout(this.nextQuestion, 3000);
    },
    getUpset: function(){
      // TODO: Animate disappointment
      this.$refs.gameHeader.wrongAnswer();
      // eslint-disable-next-line
      let sound = new Audio(require('../assets/soundEffects/fail.mp3'))
      sound.play();
      setTimeout(this.nextQuestion, 1000);
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
  .game-container {
    padding: 20px;
    background-color: #FFC000;
    border-top: 5px solid lightgrey;
    border-bottom: 5px solid grey;
    border-right: 5px solid grey;
    border-left: 5px solid lightgrey;
  }
  .next-ques {
    margin-left: auto;
    display: block;
    width: fit-content;
  }
</style>
