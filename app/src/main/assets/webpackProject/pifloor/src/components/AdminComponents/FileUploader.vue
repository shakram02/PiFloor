<template>
  <div><center>
    <b-btn v-b-modal.modallg variant="primary">Add Questions</b-btn>

    <b-modal id="modallg" size="lg" title="Drag or Browse Question File" v-model="show">
      <FileDragPanel ref="dragger"/>
      <div slot="modal-footer">
        <b-button variant="success" @click="uploadFiles">Done</b-button>
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
      this.$parent.$parent.questions = this.questions;
      this.show = false;
    }
  }
}
</script>

<style scoped>

</style>
