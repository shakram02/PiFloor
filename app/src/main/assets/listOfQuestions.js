var modalQues = Vue.component('listOfQues', {
    props: ['questions'],
    data: function () {
      return {
        questions: this.questions,
      }
    },
    methods: {
        appendQuestions: function() {
            this.questions.push("hi");
            this.$emit('close', this.questions);
        }
    },
    template: `
    <div>
        <modal @close="appendQuestions">
            <template slot="title">
                questions
            </template>
            <template slot="content">
                <div class="modal-header">
                    <slot name="header">
                    default header
                    </slot>
                </div>
                <div class="modal-body">
                    <slot name="body">
                    <div v-for="(question, index) in questions">
                        <question 
                            v-bind:index="index" 
                            v-bind:title="question.title"
                            v-bind:options="question.options"
                            v-bind:picked="question.picked"
                        />
                    </div>
                    </slot>
                </div>
            </template>
        </modal>
    </div>
  `
})
  