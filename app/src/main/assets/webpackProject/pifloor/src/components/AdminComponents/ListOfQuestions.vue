<template>
    <div>
        <div v-for="(ques, index) in $root.$children[0].questions" v-bind:key="ques">
            <Question
                v-bind:index="index"
                v-bind:questions="$root.$children[0].questions"
            />
            <div class="breakLine"/>
        </div>
    </div>
</template>

<script>
import Question from "./Question.vue";

export default {
    components: {
        Question,
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
