<template>
    <b-col v-bind:class="['div-' + theme]" cols="1">
        <b-btn block class="blue-button" @click="changeColorBlue" />
        <b-btn block class="pink-button" @click="changeColorPink" />
        <b-btn block class="green-button" @click="changeColorGreen" />
    </b-col>
</template>

<script>
import { blue, pink, green } from '../styles/ThemeConstants.js';
import { mapMutations } from 'vuex';
import { mapGetters } from 'vuex';

export default {
  name: 'ColorChooser',
  computed: mapGetters([
    'theme'
  ]),
  methods: {
    changeColorBlue: function (){
        this.$store.commit('changeColorBlue');
    },
    changeColorPink: function (){
        this.$store.commit('changeColorPink');
    },
    changeColorGreen: function (){
        this.$store.commit('changeColorGreen');
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
    border-bottom: 10px solid $backgroundDarkBlue;
}
.div-pink {
    @extend %div;
    background-color: $backgroundLightPink;
    border-bottom: 10px solid $backgroundDarkPink;
}
.div-green {
    @extend %div;
    background-color: $backgroundLightGreen;
    border-bottom: 10px solid $backgroundDarkGreen;
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
