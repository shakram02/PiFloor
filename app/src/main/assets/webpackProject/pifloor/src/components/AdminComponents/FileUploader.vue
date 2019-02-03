<template>
  <div><center>
    <div v-bind:class="['shape-' + $root.$children[0].themeColor]">
      <b-btn block variant="outline-secondary" v-b-modal.modallg>Add Questions</b-btn>
    </div>
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
      let tempArray = this.$refs.dragger.saveFiles();
      setTimeout(()=>{
        let resultArray = [];
        for(let i=0; i<tempArray.length; i++){
          let tempQues = tempArray[i].split(',');
          resultArray.push({
            "question": tempQues[0],
            "correct": tempQues[1],
            "choices": tempQues.slice(2)
          })
        }
        this.questions = resultArray;
        this.$parent.$parent.questions = this.questions;
        this.show = false;
      }, 300)
    }
  }
}
</script>

<style scoped>

</style>
