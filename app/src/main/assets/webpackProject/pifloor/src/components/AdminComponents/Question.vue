<template>
    <div>
    <b-row align-v="center">
      <b-col cols="9">
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="sheet input-group-text" style="margin-left: 15px;">{{index+1}}. </span>
          </div>
          <b-form-input type="text" class="sheet" v-model="title" />
        </div>
      </b-col>
      <b-col cols="3">
        <b-btn v-b-toggle="'collapse' + index" size="sm" variant="outline-secondary">close/open</b-btn>
      </b-col>
    </b-row>
    <br/>
    <b-collapse v-bind:id="'collapse' + index" accordion="ques-accordion" class="mt-2">
      <div v-for="(option, index) in options" v-bind:key="index">
        <div class="input-group">
          <div class="input-group-prepend">
            <span class="sheet input-group-text" style="margin-left: 15px;">{{index+1}}. </span>
          </div>
          <b-form-input type="text" class="sheet" v-model="options[index]" />
        </div>
      </div>

      <br/>
      <div class="select">
        <p>Select Correct Answer</p>
        <b-form-select v-model="picked">
          <option v-for="(option, index) in options" v-bind:key="options[index]" v-bind:value="options[index]">
            {{ option }}
          </option>
        </b-form-select>
      </div>
    </b-collapse>
    </div>
</template>

<script>
export default {
    props: ['index', 'title', 'options', 'picked'],
};
</script>

<style scoped>
.select {
  display: flex;
}
.sheet {
    border: 0px;
    border-bottom: solid 2px grey;
    border-radius: 0px;
    padding-right:0px;
    padding-left:0px;
    background: white;
}
</style>

  