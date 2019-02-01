<i18n src="../locales/AdminPage.json"></i18n>

<template>
    <b-col offset-md="4" cols="4" align-self="center">
      <div  class="containerMenu">
        <h1>{{ $t('PiFloor') }}</h1>
        <br/>
        <br/>
        <FileUploader ref="uploader"/>
        <br/>
        <ListOfQuestions v-bind:questions="questions"/>
        <br/>
        <FileDownloader class="btn-block" />
        <br/>
        <b-btn block variant="outline-secondary" @click="startGame">{{ $t('PlayGame') }}</b-btn>
        <br/>
        <b-button :pressed.sync="myToggle" variant="primary">Toggle Me</b-button>
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
  },
  watch: {
    locale (val) {
      this.$i18n.locale = val
    }
  },
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
