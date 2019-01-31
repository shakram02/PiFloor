<template>
  <div><center>
    <h1>Pi Floor</h1>
    <br />
    <FileUploader ref="uploader"/>
    <br /><br />
    <b-btn variant="primary">Edit Questions</b-btn>
    <br /><br />
    <FileDownloader />
    <br /><br />
    <b-btn variant="primary" @click="startGame">Play Game</b-btn>
  </center></div>
</template>

<script>
import FileUploader from './AdminComponents/FileUploader.vue'
import FileDownloader from './AdminComponents/FileDownloader.vue'

export default {
  name: 'AdminPage',
  components: {
    FileUploader,
    FileDownloader
  },
  methods: {
    startGame(){
      this.$parent.playing = true;
    }
  }
}
</script>

<style scoped>

</style>
