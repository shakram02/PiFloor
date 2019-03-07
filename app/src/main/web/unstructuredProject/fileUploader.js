var fileUploader = Vue.component('uploader', {
  data: function() {
    return {
      show: false
    }
  },
  template: `
      <div>
        <b-btn v-b-modal.modallg variant="primary">Add Questions</b-btn>

        <b-modal id="modallg" size="lg" title="Drag or Browse Question File" v-model="show">
          <iframe src="fileUploader.html" height="220px" width="100%" style="border:none;"></iframe>
          <div slot="modal-footer">
            <b-button variant="success" @click="show=false">Done</b-button>
          </div>
        </b-modal>
      </div>
  `
})
/*
  File is being uploaded either by dragging, or by browsing,
  Then the text inside it is stored in localStorage to be used from else where.
*/
