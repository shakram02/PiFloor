<template>
  <div><center>
    <b-btn blockn variant="outline-secondary" v-b-modal.modallg>Add Questions</b-btn>

    <b-modal id="modallg" size="lg" title="Drag or Browse Question File" v-model="show">
      <FileDragPanel ref="dragger"/>
      <div slot="modal-footer">
        <b-button variant="outline-success" @click="uploadFiles">Done</b-button>
      </div>
    </b-modal>

  </center></div>
</template>

<script>
import FileDragPanel from './FileDragPanel.vue'

export default {
  name: 'FileUploader',
  components: {
    FileDragPanel,
  },
  data(){
    return{
      show: false,
      file: null,
      questions: []
    }
  },
  methods: {
    uploadFiles(){
      this.questions = this.$refs.dragger.saveFiles();
      for(let i=0; i<this.questions.length; i++){
        let tempQues = this.questions[i].split(',');
        this.questions[i] = {
          "question": tempQues[0],
          "correct": tempQues[1],
          "choices": tempQues.slice(2)
        }
      }
      this.$parent.$parent.questions = this.questions;
      this.show = false;
    }
  }
}
</script>

<style scoped>

</style>
