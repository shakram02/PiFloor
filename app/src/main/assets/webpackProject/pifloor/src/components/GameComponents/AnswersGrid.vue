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
      oldPosition: null,
      oldPositionScore: 0
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
      //Remove from from other
      for(let i=0; i<this.$refs.cell.length; i++){
        this.$refs.cell[i].classList.remove("current-cell");
      }
      //Display footprints on this tile
      this.$refs.cell[position].classList.add("current-cell");

      //check if the same as old, if so increment
      if(position===this.oldPosition){
        if(this.oldPositionScore>=10){
          this.$parent.checkAnswer(position);
          this.resetData()
        }
        else{
          this.oldPositionScore++;
        }
      }
      else{
        this.resetData();
        this.oldPosition = position;
      }
    },
    resetData: function(){
      this.oldPositionScore = 0;
      this.oldPosition = null;
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
  background-image: url('../../assets/images/footstep.png');
  background-position: center;
  background-size: contain;
  background-repeat: no-repeat;
  opacity: 0.9;
}
</style>
