<template>
  <div >
    <h1 v-bind:class="['shape-' + $root.$children[0].themeColor]"><slot></slot></h1>
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
  transform: perspective(250px) rotateY(10deg) translateY(-50%);
  width: 70%;
  margin: auto;
  left: 10%;
}
</style>
