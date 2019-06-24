<template>
    <div>
    <b-row align-v="center">

      <b-col cols="10">
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="sheet input-group-text">{{index+1}}. </span>
          </div>
          <b-form-input type="text" class="sheet" v-model="questions[index].question" />
        </div>
      </b-col>

      <b-col cols="2">
        <b-button-group>
          <b-btn v-b-toggle="'collapse' + index" size="sm" variant="outline-secondary">
            <i class="fas fa-angle-down"></i>
          </b-btn>
          <b-btn v-on:click="deleteQuestion(index)" variant="outline-secondary">
            <i class="fas fa-times"></i>
          </b-btn>
        </b-button-group>
      </b-col>

    </b-row>
    <br/>

    <b-collapse v-bind:id="'collapse' + index" accordion="ques-accordion" class="mt-2 choices">
      <div v-for="(option, i) in questions[index].choices">
        <b-row align-v="center">
          <b-col cols="11">
            <div class="input-group">
              <div class="input-group-prepend">
                <span class="sheet input-group-text">{{i+1}}. </span>
              </div>
              <b-form-input type="text" class="sheet" v-model="questions[index].choices[i]" />
            </div>
          </b-col>
          <b-col cols="1">
            <b-btn block variant="outline-secondary" v-on:click="removeChoice(i)">
              <i class="fas fa-times"></i>
            </b-btn>
          </b-col>
        </b-row>
        <br/>
      </div>
      
      <b-row class="wrapper-space-between" align-v="center">
        <b-col cols="5">
          <p>{{ $t('SelectCorrectAnswer') }}</p>
          <b-form-select v-model="questions[index].correct">
            <option v-for="(option, i) in questions[index].choices" v-bind:key="questions[index].choices[i]" v-bind:value="questions[index].choices[i]">
              {{ option }}
            </option>
          </b-form-select>
        </b-col>
        <b-col cols="3">
          <b-btn block variant="outline-secondary" v-on:click="addChoice">
            {{ $t('Add') }}
          </b-btn>
        </b-col>
      </b-row>
    </b-collapse>
    </div>
</template>

<script>
export default {
    props: ['index', 'questions'],
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
  p {
    margin: 0px;
  }
  option  {
    color: #6c757d;
  }
  button .fa-angle-down .collapsed{
   display: none;
  }
  button [aria-expanded=true] .fa-angle-down {
     transform: rotate(90deg);
  }
  .sheet {
    border: 0px;
    border-bottom: solid 2px grey;
    border-radius: 0px;
    padding-right:0px;
    padding-left: 0px;
    background: white;
  }
  .choices {
    margin: 35px;
  }
  .btn-group, i {
    width: 100%;
  }
  .form-control .select, .custom-select {
    padding: 0px;
    height: 30px;
    border: 1px solid #6c757d;
  }
  .input-group > .form-control:not(:first-child), .btn-outline-secondary:focus, .btn-outline-secondary.focus {
    box-shadow: 0 0 0 0;
  }
</style>
