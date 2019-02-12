<template>
  <b-container>
    <center>
      <b-row id="grid" no-gutters class="justify-content-center">
        <b-col v-bind:cols="numPerRow" v-for="(answer, index) in PossibleAnswers" v-bind:key="answer + index" class="answer">
          <div class="grid-cell" ref="cell">{{answer}}</div>
        </b-col>
      </b-row>
    </center>
  </b-container>
</template>

<script>
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
    }
  },
  methods: {
    changeLocation: function(position){
      // Add to hashtable, or increment
      if (this.answerFrequencyKey.indexOf(position) !== -1){
        let index = this.answerFrequencyKey.indexOf(position);
        this.answerFrequencyValue[index]++;

        // Check if value exceeded 4
        if(this.answerFrequencyValue[index] >= 0){
          this.$parent.checkAnswer(position);
          this.resetData()
          return;
        }
      }
      else{
        this.answerFrequencyKey.push(position);
        this.answerFrequencyValue.push(1);
      }

      // Remove from others
      for(let i=0; i<this.$refs.cell.length; i++){
        this.$refs.cell[i].classList.remove("current-cell");
      }

      // Get most frequent answer
      let arr = this.answerFrequencyValue;
      let indexOfMaxValue = arr.indexOf(Math.max(...arr));
      let currentChoice = this.answerFrequencyKey[indexOfMaxValue];

      // Display footprints on this tile
      this.$refs.cell[currentChoice].classList.add("current-cell");
    },
    resetData: function(){
      this.answerFrequencyKey = [];
      this.answerFrequencyValue = [];
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

<style scoped>
.answer{
  padding: 2%;
}
.grid-cell {
  width: 70%;
  padding: 10%;
  background-color: #5B9BD5;
  border-bottom: 5px solid #325575;
  transform: perspective(300px) rotateY(15deg);
}
#grid{
  width: 80%;
}
.current-cell {
  background-color: #5B9BD5;
  background-image: url('http://' + window.location.hostname + ':' + window.location.port + '/footstep.png');
  background-position: center;
  background-size: contain;
  background-repeat: no-repeat;
  opacity: 0.9;
}
</style>
