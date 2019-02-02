<template>
    <div>
        <div class="shape">
            <b-btn block variant="outline-secondary" v-b-modal.questionsModal>{{ $t('Questions') }}</b-btn>
        </div>
        <b-modal size="lg" id="questionsModal" title="Questions">
            <div v-for="(ques, index) in $parent.$parent.questions" v-bind:key="index">
                <Question
                    v-bind:index="index"
                    v-bind:title="ques.question"
                    v-bind:options="ques.choices"
                    v-bind:picked="ques.correct"
                    v-bind:questions="$parent.$parent.questions"
                    @inputQuestion="(val) => setQuestion(val, index)"
                />
                <button @click="() => deleteQuestion(index)">
                    delete
                </button>
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
        deleteQuestion: function (index) {
            this.$parent.$parent.questions.splice(index, 1);
        },
        setQuestion: function (val, index) {
            this.$parent.$parent.questions[0].question = val;
            console.log(this.$parent.$parent.questions);
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
