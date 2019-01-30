var adminPage = Vue.component('question', {
    props: ['index', 'title', 'options', 'picked'],
    data: function () {
      return {
        index: this.index,
        title: this.title,
        options: this.options,
        picked: this.picked,
      }
    },
    template: `
    <div>
      <h3>{{index+1}}) <input type="text" v-model="title" /></h3>
      <div v-for="(option, index) in options">
        {{index+1}}. <input v-model="option" />
      </div>
      <select v-model="picked">
        <option v-for="option in options" v-bind:value="option">
          {{ option }}
        </option>
      </select>
    </div>`
  })
  