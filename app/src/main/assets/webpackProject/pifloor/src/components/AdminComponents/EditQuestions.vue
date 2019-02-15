<template>
    <div class="container">
        <h1>{{ $t('PiFloor') }}</h1>
        <br/>
        <ListOfQuestions />
        <br/>
        <b-row class="wrapper-space-between">
            <b-col cols="5">
                <b-row class="wrapper-space-between">
                    <b-col v-bind:class="['shape-' + $root.$children[0].themeColor]">
                        <b-btn block variant="outline-secondary" @click="addQuestion">
                            {{ $t('NewQuestion') }}
                        </b-btn>
                    </b-col>
                    <b-col>
                        <FileDownloader />
                    </b-col>
                </b-row>
            </b-col>
            <b-col cols="2" v-bind:class="['shape-' + $root.$children[0].themeColor]">
                <b-btn block variant="outline-secondary" @click="shuffleView">{{ $t('Back') }}</b-btn>
            </b-col>
        </b-row>
    </div>
</template>

<script>
import FileDownloader from './FileDownloader.vue';
import ListOfQuestions from './ListOfQuestions.vue';

export default {
  name: 'QuestionsPage',
  components: {
    FileDownloader,
    ListOfQuestions,
  },
  methods: {
    addQuestion: function () {
        this.$root.$children[0].questions.push({
            choices: [""],
            correct: "",
            question: "",
        });
    },
    shuffleView: function () {
      this.$emit('shuffleView');
    },
  }
}
</script>