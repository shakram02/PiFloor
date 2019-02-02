<template>
    <div>
        <div class="shape">
            <b-btn block variant="outline-secondary" v-b-modal.questionsModal>{{ $t('Questions') }}</b-btn>
        </div>
        <b-modal size="lg" id="questionsModal" title="Questions">
            <div v-for="(ques, index) in $parent.$parent.questions" v-bind:key="ques">
                <Question
                    v-bind:index="index"
                    v-bind:questions="$parent.$parent.questions"
                    @inputQuestion="(val) => setQuestion(val, index)"
                    @inputCorrect="(val) => setCorrect(val, index)"
                />
                <div id="breakLine"/>
            </div>
            <button @click="addQuestion">
                new Question
            </button>
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
        setQuestion: function (val, index) {
            this.$parent.$parent.questions[index].question = val;
            console.log()
        },
        setCorrect: function (val, index) {
            this.$parent.$parent.questions[index].correct = val;
        },
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
