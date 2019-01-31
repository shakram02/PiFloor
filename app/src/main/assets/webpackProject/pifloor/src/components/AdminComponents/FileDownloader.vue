<template>
  <div>
    <b-btn variant="primary" @click="download">Download Questions</b-btn>
    <a v-bind:show="false" ref="link"></a>
    <b-modal id="modal1"  ref="emptyFileModal">
    <p>No questions added yet!</p>
    <div slot="modal-footer"></div>
  </b-modal>
  </div>
</template>

<script>
export default {
  methods: {
      download(){
        let questions = this.$parent.$refs.uploader.questions;
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
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
