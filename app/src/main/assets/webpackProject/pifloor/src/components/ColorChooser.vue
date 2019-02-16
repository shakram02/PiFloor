<template>
    <b-col v-bind:class="['div-' + $root.$children[0].theme]" cols="1">
        <b-btn block class="blue-button" @click="changeColorBlue" />
        <b-btn block class="pink-button" @click="changeColorPink" />
        <b-btn block class="green-button" @click="changeColorGreen" />
    </b-col>
</template>

<script>
import { blue, pink, green } from '../styles/ThemeConstants.js'
export default {
  name: 'ColorChooser',
  methods: {
    changeColorBlue: function (){
        this.$root.theme = blue;
        console.log(this.$root.theme);
    },
    changeColorPink: function (){
        this.$root.theme = pink;
        console.log(this.$root.theme);
    },
    changeColorGreen: function (){
        this.$root.theme = green;
        console.log(this.$root.theme);
    },
  }
}
</script>

<style lang="scss" scoped>

@import '../styles/colorConstants';

%div {
    position: fixed;
    top: 20%;
    left : 0px;
    width: fit-content;
    padding: 10px;

}
%button {
    width: 25px;
    height: 25px;
    border-radius: 50%;
}
.div-blue {
    @extend %div;
    background-color: $backgroundLightBlue;
    border-bottom: $backgroundDarkBlue solid 10px;
}
.div-pink {
    @extend %div;
    background-color: $backgroundLightPink;
    border-bottom: $backgroundDarkPink solid 10px;
}
.div-green {
    @extend %div;
    background-color: $backgroundLightGreen;
    border-bottom: $backgroundDarkGreen solid 10px;
}
.blue-button {
    @extend %button;
    background-color: $lightBlue;
}
.pink-button {
    @extend %button;
    background-color: $lightPink;
}
.green-button {
    @extend %button;
    background-color: $lightGreen;
}

</style>
