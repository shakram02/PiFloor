<template>
    <b-col offset-md="4" cols="4" align-self="center" v-if="!editing">
      <div v-bind:class="['shape-container-' + $root.$children[0].theme]">
        <div  class="container-menu" >
          <h1>{{ $t('PiFloor') }}</h1>
          <br/>
          <div v-bind:class="['shape-' + $root.$children[0].theme]">
            <b-btn block variant="outline-secondary" @click="startGame">{{ $t('PlayGame') }}</b-btn>
          </div>
          <br/>
          <FileUploader ref="uploader"/>
          <br/>
          <div v-bind:class="['shape-' + $root.$children[0].theme]">
            <b-btn block variant="outline-secondary" @click="shuffleView">{{ $t('ComposeQuestions') }}</b-btn>
          </div>
          <br />
          <div v-bind:class="['shape-' + $root.$children[0].theme]">
            <b-form-select @change="onChangeLocale($event)" v-model="$i18n.locale">
              <option v-bind:class="['option-style-' + $root.$children[0].theme]" v-for="option in this.$root.$children[0].lang" v-bind:key="option" v-bind:value="option">
                {{ $t(option) }}
              </option>
            </b-form-select>
          </div>
        </div>
      </div>
    </b-col>
    <div v-else>
      <EditQuestions @shuffleView="shuffleView" />
    </div>
</template>

<script>
import FileUploader from './AdminComponents/FileUploader.vue'
import EditQuestions from './AdminComponents/EditQuestions.vue'

export default {
  name: 'AdminPage',
  data(){
    return {
      editing: false,
    }
  },
  components: {
    FileUploader,
    EditQuestions,
  },
  methods: {
    startGame(){
      this.$root.$children[0].playing = true;
    },
    shuffleView(){
      this.editing = !this.editing;
    },
    onChangeLocale(event) {
      console.log(event.target.value);
    }
  },

}
</script>
