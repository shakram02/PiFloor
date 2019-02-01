<template>
    <div>
        <div class="shape">
            <b-btn block variant="outline-secondary" v-b-modal.questionsModal>{{ $t('Questions') }}</b-btn>
        </div>
        <b-modal size="lg" id="questionsModal" title="Questions">
            <div v-for="(question, index) in questions"  v-bind:key="index">
                <Question 
                    v-bind:index="index" 
                    v-bind:title="question.title"
                    v-bind:options="question.options"
                    v-bind:picked="question.picked"
                />
                <div id="breakLine"/>
            </div>
        </b-modal>
    </div>
</template>

<script>
import Question from "./Question.vue";

export default {
    data: function () {
      return {
        questions: this.$parent.$parent.questions,
      }
    },
    components: {
        Question,
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