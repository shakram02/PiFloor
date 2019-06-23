<template>
  <div>
    <b-col offset-md="4" cols="4" align-self="center">
      <div v-bind:class="['shape-container-' + theme]">
        <div  class="container-menu">
          <h1>{{ $t('PiFloor') }}</h1>
          <br/>
          <div v-bind:class="['shape-' + theme]">
            <b-btn variant="outline-secondary" @click="useAsTeacher">{{ $t('Teacher') }}</b-btn>
          </div>
          <br/>
          <div v-bind:class="['shape-' + theme]">
            <b-btn variant="outline-secondary" @click="useAsStudent">{{ $t('Student') }}</b-btn>
          </div>
          <br/>
          <div v-bind:class="['shape-' + theme]">
            <b-form-select v-bind:class="['select-' + theme]" v-model="$i18n.locale">
              <option v-bind:class="['option-style-' + theme]" v-for="option in this.$root.$children[0].lang" v-bind:key="option" v-bind:value="option">
                {{ $t(option) }}
              </option>
            </b-form-select>
          </div>
        </div>
      </div>
    </b-col>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  name: 'Home',
  computed: mapGetters([
    'theme',
  ]),
  methods: {
    useAsTeacher : function() {
      this.$parent.loggedin = true;
    },
    useAsStudent: function() {
      this.$parent.playing = true;
      this.$parent.loggedin = true;
    }
  }
}
</script>
