<template>
    <b-col offset-md="4" cols="4" align-self="center">
      <div class="shape-container">
        <div  class="container-menu">
          <h1>{{ $t('PiFloor') }}</h1>
          <br/>
          <FileUploader ref="uploader"/>
          <br/>
          <ListOfQuestions/>
          <br/>
          <FileDownloader class="btn-block" />
          <br/>
          <div v-bind:class="['shape-' + $root.$children[0].themeColor]">
            <b-btn block variant="outline-secondary" @click="startGame">{{ $t('PlayGame') }}</b-btn>
          </div>
          <br/>
          <div v-bind:class="['shape-' + $root.$children[0].themeColor]">
            <b-form-select v-model="$i18n.locale">
              <option class="option-style" v-for="option in this.$parent.lang" v-bind:key="option" v-bind:value="option">
                {{ option }}
              </option>
            </b-form-select>     
          </div> 
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
.container-menu {
  padding: 20px;
  width: fit-content;
  margin: auto;
}
.shape-container { // problem with responsiveness with border-bottom
  width: 70%;
  background-color: #FFFACD;
  border-top: 3px solid transparent;
  border-bottom: 3px solid grey;
  border-right: 3px solid grey;
  border-left: 3px solid transparent;
}
.option-style {
  color: grey;
  background: lightblue;

}
.option-style :focus { //not working :(
  background: grey;
  color: white;
}
</style>
