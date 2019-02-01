<template>
    <b-col offset-md="4" cols="4" align-self="center">
      <div  class="containerMenu">
        <h1>{{ $t('PiFloor') }}</h1>
        <br/>
        <br/>
        <FileUploader ref="uploader"/>
        <br/>
        <ListOfQuestions/>
        <br/>
        <FileDownloader class="btn-block" />
        <br/>
        <div class="shape">
          <b-btn block variant="outline-secondary" @click="startGame">{{ $t('PlayGame') }}</b-btn>
        </div>
        <br/>
        <div class="shape">
          <b-form-select v-model="$i18n.locale">
            <option v-for="option in this.$parent.lang" v-bind:key="option" v-bind:value="option">
              {{ option }}
            </option>
          </b-form-select>     
        </div> 
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

}
</script>

<style lang="scss" scoped>
.containerMenu {
  padding: 20px;
  border: solid 2px grey;
  border-radius:5px;
  width: fit-content;
  margin: auto;
}
</style>
