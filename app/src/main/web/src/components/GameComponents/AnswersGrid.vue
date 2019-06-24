<template>
  <b-container>
    <center>
      <b-row id="grid" no-gutters class="justify-content-center">
        <b-col v-bind:cols="numPerRow" v-for="(answer, index) in PossibleAnswers" v-bind:key="answer + index" class="answer">
          <div v-bind:class="['grid-cell-' + theme]" ref="cell">{{answer}}</div>
        </b-col>
      </b-row>
    </center>
  </b-container>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  props: ["PossibleAnswers"],
  data(){
    return{
      answerFrequencyKey: [],
      answerFrequencyValue: []
    }
  },
  computed: {
    numPerRow: function(){
      let num = this.PossibleAnswers.length;
      num = Math.ceil(Math.sqrt(num))
      return 12/num;
    },
    ...mapGetters([
      'theme',
    ]),
  },
  methods: {
    changeLocation: function(position){
      // Add to hashtable, or increment
      if (this.answerFrequencyKey.indexOf(position) !== -1){
        let index = this.answerFrequencyKey.indexOf(position);
        this.answerFrequencyValue[index]++;

        // Check if value exceeded 3
        if(this.answerFrequencyValue[index] >= 2){
          this.$parent.checkAnswer(position);
          this.markChosen();
          this.resetData();
          return;
        }
      }
      else{
        this.answerFrequencyKey.push(position);
        this.answerFrequencyValue.push(1);
      }
    },
    resetData: function(){
      this.answerFrequencyKey = [];
      this.answerFrequencyValue = [];
    },
    markChosen: function(){
      // Remove from others
      for(let i=0; i<this.$refs.cell.length; i++){
        this.$refs.cell[i].classList.remove("current-cell");
      }

      // Get most frequent answer
      let arr = this.answerFrequencyValue;
      let indexOfMaxValue = arr.indexOf(Math.max(...arr));
      let currentChoice = this.answerFrequencyKey[indexOfMaxValue];

      // Color chosen tile
      let chosenTileIndex = this.PossibleAnswers.indexOf(currentChoice);
      this.$refs.cell[chosenTileIndex].classList.add("current-cell");
    }
  },
  mounted() {
    setTimeout(()=>{
      let num = this.PossibleAnswers.length;
      num = Math.pow(Math.ceil(Math.sqrt(num)),2);
      num = num - this.PossibleAnswers.length;
      for(let i=0; i<num; i++) this.PossibleAnswers.push("")
    },200)
  },
  beforeUpdate() {
    setTimeout(()=>{
      let num = this.PossibleAnswers.length;
      num = Math.pow(Math.ceil(Math.sqrt(num)),2);
      num = num - this.PossibleAnswers.length;
      for(let i=0; i<num; i++) this.PossibleAnswers.push("")
    },200)
  }
}
</script>

<style lang="scss" scoped>
@import '../../styles/colorConstants';

.answer{
  padding: 2%;
}
%grid-cell {
  width: 70%;
  padding: 10%;
  transform: perspective(300px) rotateY(15deg);
  border-bottom: 5px solid;
}
.grid-cell-blue {
  @extend %grid-cell;
  background-color: $lightBlue;
  border-bottom-color: $darkBlue;
}
.grid-cell-pink {
  @extend %grid-cell;
  background-color: $lightPink;
  border-bottom-color: $darkPink;
}
.grid-cell-green {
  @extend %grid-cell;
  background-color: $lightGreen;
  border-bottom-color: $darkGreen;
}
#grid{
  width: 80%;
}
.current-cell{
  background-color: yellow;
}
</style>
