<template>
  <div >
    <h1 v-bind:class="['shape-' + theme]"><slot></slot></h1>
  </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
  computed: mapGetters([
    'theme'
  ]),
}
</script>

<style scoped>
h1{
  border-radius: 5px;
  padding: 1%;
  position: relative;
  transform: rotateY(15deg) translateY(-50%);
  width: 70%;
  margin: auto;
}
</style>
