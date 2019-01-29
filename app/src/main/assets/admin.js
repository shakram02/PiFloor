var adminPage = Vue.component('admin', {
  data: function () {
    return {
      count: 0
    }
  },
  template: `<div><center>
              <h1>Pi Floor</h1>
              <br />
              <uploader />
              <br /><br />
              <button>Edit Questions</button>
              <br /><br />
              <button>Play Game</button>
            </center></div>`
})
