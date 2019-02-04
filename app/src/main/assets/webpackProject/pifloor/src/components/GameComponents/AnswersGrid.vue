<template>
  <b-container>
    <center>
      <b-row id="grid" no-gutters class="justify-content-center">
        <b-col v-bind:cols="numPerRow" v-for="(answer, index) in PossibleAnswers" v-bind:key="answer + index" class="answer">
          <div class="grid-cell">{{answer}}</div>
        </b-col>
      </b-row>
    </center>
  </b-container>
</template>

<script>
export default {
  props: ["PossibleAnswers"],
  computed: {
    numPerRow: function(){
      let num = this.PossibleAnswers.length;
      num = Math.ceil(Math.sqrt(num))
      return 12/num;
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
  border-top: 3px solid transparent;
  border-bottom: 3px solid #325575;
  border-right: 3px solid #325575;
  border-left: 3px solid transparent;

}
#grid{
  width: 80%;
}

</style>
