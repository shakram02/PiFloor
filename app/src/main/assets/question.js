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
        <h3>{{index+1}}. {{title}}</h3>
        <input v-for="option in options" type="radio" id="option" value="option" v-model="picked">
    </div>`
  })
  