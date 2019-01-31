var adminPage = Vue.component('admin', {
  data: function () {
    return {
      Questions:[
        {
          title:"title",
          options: ["1st option", "2nd option"],
          picked: "2nd option",
        }
      ],
    }
  },
  methods: {
    updateArray: function(newQuestions) {
      console.log(newQuestions);
    }
  },
  template: `<center>
              <h1>Pi Floor</h1>
              <br />
              <uploader />
              <br /><br />
              <listOfQues @close="updateArray" v-bind:questions="Questions"/>
              <br /><br />
              <button>Play Game</button>
            </center></div>`
})
