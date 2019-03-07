import Vue from 'vue';
import Vuex from 'vuex';
import { blue, pink, green } from './styles/ThemeConstants.js';

Vue.use(Vuex);

const state = {
    theme: blue,
};

const getters = {
   theme: state => state.theme,
}

const mutations = {
    changeColorBlue (state) {
        state.theme = blue;
    },
    changeColorPink (state) {
        state.theme = pink;
    },
    changeColorGreen (state) {
        state.theme = green;
    },
}

export default new Vuex.Store({
    state,
    getters,
    mutations,
});