Dropzone.options.dropzoneForm = {
  accept: function (file, done) {
    var reader = new FileReader();
    reader.addEventListener("loadend", function(event) {console.log(event.target.result);})
    reader.readAsText(file);
    done('Dummy');
    $('.dz-preview').last().toggleClass('dz-error dz-success');
  }
};
