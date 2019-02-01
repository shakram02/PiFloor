<template>
    <b-col offset-md="4" cols="4" align-self="center">
      <div  class="containerMenu">
        <h1>Pi Floor</h1>
        <br/>
        <br/>
        <FileUploader ref="uploader"/>
        <br/>
        <ListOfQuestions/>
        <br/>
        <FileDownloader class="btn-block" />
        <br/>
        <b-btn block variant="outline-secondary" @click="startGame">Play Game</b-btn>
      </div>
    </b-col>
</template>

<script>
import FileUploader from './AdminComponents/FileUploader.vue'
import FileDownloader from './AdminComponents/FileDownloader.vue'
import ListOfQuestions from './AdminComponents/ListOfQuestions.vue'

export default {
  name: 'AdminPage',
  components: {
    FileUploader,
    FileDownloader,
    ListOfQuestions,
  },
  methods: {
    startGame(){
      this.$parent.playing = true;
    }
  }
}
</script>

<style scoped>
.containerMenu {
  padding: 20px;
  border: solid 2px grey;
  border-radius:5px;
  width: fit-content;
  margin: auto;
}
</style>
