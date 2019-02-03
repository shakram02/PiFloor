<template>
    <div>
        <div v-bind:class="['shape-' + $root.$children[0].themeColor]">
            <b-btn block variant="outline-secondary" v-b-modal.questionsModal>{{ $t('Questions') }}</b-btn>
        </div>
        <b-modal size="lg" id="questionsModal">
            <div v-for="(ques, index) in $parent.$parent.questions" v-bind:key="ques">
                <Question
                    v-bind:index="index"
                    v-bind:title="ques.question"
                    v-bind:options="ques.choices"
                    v-bind:picked="ques.correct"
                    v-bind:questions="$parent.$parent.questions"
                />
                <button @click="() => deleteQuestion(index)">
                    {{ $t('Delete') }}
                </button>
                <div id="breakLine"/>
            </div>
            <button @click="addQuestion">
                {{ $t('NewQuestion') }}
            </button>
            <div slot="modal-footer">
              <b-button variant="outline-success" @click="uploadFiles">{{ $t('Done') }}</b-button>
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
            this.$parent.$parent.questions.push({
                    choices: [" "],
                    correct: " ",
                    question: " ",
                });
        },
        deleteQuestion: function (index) {
            this.$parent.$parent.questions.splice(index, 1);
        }
    }
}
</script>

<style lang="scss" scoped>

#breakLine {
    margin: 15px;
    height: 1px;
    width:1;
    background: grey;
}

</style>
