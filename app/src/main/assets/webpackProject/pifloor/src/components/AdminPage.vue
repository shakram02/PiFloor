<template>
    <b-col offset-md="4" cols="4" align-self="center">
      <div class="shape-container">
        <div  class="container-menu" v-if="!editing">
          <h1>{{ $t('PiFloor') }}</h1>
          <br/>
          <div v-bind:class="['shape-' + $root.$children[0].themeColor]">
            <b-btn block variant="outline-secondary" @click="startGame">{{ $t('PlayGame') }}</b-btn>
          </div>
          <br/>
          <FileUploader ref="uploader"/>
          <br/>
          <div v-bind:class="['shape-' + $root.$children[0].themeColor]">
            <b-btn block variant="outline-secondary" @click="shuffleView">{{ $t('ComposeQuestions') }}</b-btn>
          </div>
          <br />
          <div v-bind:class="['shape-' + $root.$children[0].themeColor]">
            <b-form-select v-model="$i18n.locale">
              <option class="option-style" v-for="option in this.$root.$children[0].lang" v-bind:key="option" v-bind:value="option">
                {{ option }}
              </option>
            </b-form-select>
          </div>
        </div>

        <div  class="container-menu" v-else>
          <h1>{{ $t('PiFloor') }}</h1>
          <br/>
          <ListOfQuestions/>
          <br/>
          <FileDownloader />
          <br/>
          <div v-bind:class="['shape-' + $root.$children[0].themeColor]">
            <b-btn block variant="outline-secondary" @click="shuffleView">{{ $t('Back') }}</b-btn>
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
  data(){
    return {
      editing: false
    }
  },
  components: {
    FileUploader,
    FileDownloader,
    ListOfQuestions,
  },
  methods: {
    startGame(){
      this.$root.$children[0].playing = true;
    },
    shuffleView(){
      this.editing = !this.editing;
    }
  },

}
</script>

<style lang="scss" scoped>
h1 {
  font-family: cursive;
  color: #325575;
}
.container-menu {
  padding: 20px;
  width: fit-content;
  margin: auto;
}
.shape-container { // problem with responsiveness with border-bottom
  width: 70%;
  background-color: #FFC000;
  border-bottom: 10px solid #ED7D31;
}
.form-control,
.custom-select,
.custom-select:focus,
.custom-select:hover {
  border: 0px;
  background-color: transparent !important;
  -webkit-box-shadow: 0 0 0 0;
  box-shadow: 0 0 0 0;
}

.custom-select:hover {
  color: white;
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
