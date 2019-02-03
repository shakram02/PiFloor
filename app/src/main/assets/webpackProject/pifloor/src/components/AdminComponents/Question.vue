<template>
    <div>
    <b-row align-v="center">

      <b-col cols="9">
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="sheet input-group-text" style="margin-left: 15px;">{{index+1}}. </span>
          </div>
          <b-form-input type="text" class="sheet" v-model="question" />
        </div>
      </b-col>

      <b-col cols="3">
        <b-btn v-b-toggle="'collapse' + index" size="sm" variant="outline-secondary">{{ $t('CloseAndOpen') }}</b-btn>
        <b-btn v-on:click="deleteQuestion(index)" variant="outline-secondary">{{ $t('Delete') }}</b-btn>
      </b-col>
    </b-row>
    <br/>

    <b-collapse v-bind:id="'collapse' + index" accordion="ques-accordion" class="mt-2">
      <div v-for="(option, i) in questions[index].choices">
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="sheet input-group-text" style="margin-left: 15px;">{{i+1}}. </span>
          </div>
          <b-form-input type="text" class="sheet" v-model="questions[index].choices[i]" />
        </div>
        <button v-on:click="removeChoice(i)">
          {{ $t('Delete') }}
        </button>
      </div>
      <button v-on:click="addChoice">
        {{ $t('Add') }}
      </button>
      <br/>
      <div class="select">
        <p>{{ $('SelectCorrectAnswer') }}</p>
        <b-form-select v-model="correct">
          <option v-for="(option, i) in questions[index].choices" v-bind:key="questions[index].choices[i]" v-bind:value="questions[index].choices[i]">
            {{ option }}
          </option>
        </b-form-select>
      </div>
    </b-collapse>
    </div>
</template>

<script>
export default {
    props: ['index', 'questions'],
    data(){
      return {
        question: this.questions[this.index].question,
        correct: this.questions[this.index].correct,
      }
    },
    methods: {
      addChoice : function() {
        this.questions[this.index].choices.push("");
      },
      removeChoice: function(i) {
        this.questions[this.index].choices.splice(i, 1);
      },
      deleteQuestion: function(i) {
        this.questions.splice(i, 1);
      },
    },
    watch: {
    question(val) {
      this.$emit('inputQuestion', val);
    },
    correct(val) {
      this.$emit('inputCorrect', val);
    },
  }
};
</script>

<style scoped>
  .select {
    display: flex;
  }
  .sheet {
      border: 0px;
      border-bottom: solid 2px grey;
      border-radius: 0px;
      padding-right:0px;
      padding-left:0px;
      background: white;
  }
</style>
