<template>
    <div>
        <div v-bind:class="['shape-' + $root.$children[0].themeColor]">
            <b-btn block variant="outline-secondary" v-b-modal.questionsModal>{{ $t('Questions') }}</b-btn>
        </div>
        <b-modal size="lg" id="questionsModal">
            <div v-for="(ques, index) in $root.$children[0].questions" v-bind:key="ques">
                <Question
                    v-bind:index="index"
                    v-bind:questions="$root.$children[0].questions"
                />
                <div class="breakLine"/>
            </div>
            <button @click="addQuestion">
                {{ $t('NewQuestion') }}
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
            this.$root.$children[0].questions.push({
                    choices: [" "],
                    correct: " ",
                    question: " ",
                });
        },
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
