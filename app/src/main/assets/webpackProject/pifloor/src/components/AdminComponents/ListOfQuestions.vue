<template>
    <div>
        <div v-bind:class="['shape-' + $root.$children[0].themeColor]">
            <b-btn block variant="outline-secondary" v-b-modal.questionsModal>{{ $t('Questions') }}</b-btn>
        </div>
        <b-modal size="lg" id="questionsModal" ref="questionsModal">
            <div v-for="(ques, index) in $root.$children[0].questions" v-bind:key="ques">
                <Question
                    v-bind:index="index"
                    v-bind:questions="$root.$children[0].questions"
                />
                <div class="breakLine"/>
            </div>
            <b-btn block variant="outline-secondary" @click="addQuestion">
                {{ $t('NewQuestion') }}
            </b-btn>
            <div slot="modal-footer">
              <b-button variant="outline-success" @click="hideModal">{{ $t('Done') }}</b-button>
            </div>
        </b-modal>
    </div>
</template>

<script>
import Question from "./Question.vue";

export default {
    components: {
        Question,
    },
    methods: {
        addQuestion: function () {
            this.$root.$children[0].questions.push({
                    choices: [""],
                    correct: "",
                    question: "",
                });
        },
        hideModal: function() {
          this.$refs.questionsModal.hide();
        }
    }
}
</script>

<style lang="scss" scoped>

.breakLine {
    margin: 15px;
    height: 1px;
    width:1;
    background: grey;
}

</style>
