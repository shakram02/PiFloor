<template>
  <div>
    <div v-bind:class="['shape-' + $root.$children[0].themeColor]">
      <b-btn block variant="outline-secondary" @click="download">{{ $t('DownloadQuestions') }}</b-btn>
    </div>
    <a v-bind:show="false" ref="link"></a>
    <b-modal ref="emptyFileModal">
      <p>{{ $('NoQuestionsYet') }}</p>
      <div slot="modal-footer"></div>
    </b-modal>
  </div>
</template>

<script>
export default {
  methods: {
      download(){
        let questions = this.$parent.$refs.uploader.$refs.dragger.questions;
        if(!questions.length){this.$refs.emptyFileModal.show();}
        else{
          questions = questions.join('\n');
          this.$refs.link.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(questions));
          this.$refs.link.setAttribute('download', 'questions.txt');
          this.$refs.link.click();
        }
      }
  }
}
</script>

<style scoped>

</style>
