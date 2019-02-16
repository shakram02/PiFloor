<template>
  <div >
    <h1 v-bind:class="['shape-' + $root.$children[0].theme]"><slot></slot></h1>
  </div>
</template>

<script>
export default {

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
