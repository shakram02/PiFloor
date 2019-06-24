<template>
  <div>
    <b-btn block variant="outline-secondary" @click="download">{{ $t('DownloadQuestions') }}</b-btn>
    <a v-bind:show="false" ref="link"></a>
    <b-modal ref="emptyFileModal">
      <p>{{ $t('NoQuestionsYet') }}</p>
      <div slot="modal-footer"></div>
    </b-modal>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  computed: mapGetters([
    'theme',
  ]),
  methods: {
      download(){
        let questions = this.$root.$children[0].questions;
        setTimeout(()=>{
          if(!questions.length){this.$refs.emptyFileModal.show();}
          else{
            questions = this.stringifyQuestions(questions)
            this.$refs.link.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(questions));
            this.$refs.link.setAttribute('download', 'questions.txt');
            this.$refs.link.click();
          }
        }, 100)
      },
      stringifyQuestions(questions){
        var result = "";
        for(let i=0; i<questions.length; i++){
          let questionObject = questions[i];
          let questionString = questionObject["question"] + ',';
          questionString += questionObject["correct"] + ',';
          questionString += questionObject["choices"].join(',') + '\n';
          result += questionString;
        }
        return result
      }
  }
}
</script>
