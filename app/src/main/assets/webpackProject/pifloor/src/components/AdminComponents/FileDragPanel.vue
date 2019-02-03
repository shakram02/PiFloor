<template>
  <div class="example-drag">
    <div class="upload">
      <ul v-if="files.length">
        <li v-for="(file) in files" :key="file.id">
          <span>{{file.name}}</span> -
          <span>{{file.size}}</span> -
          <span v-if="file.error">{{file.error}}</span>
          <span v-else-if="file.success">success</span>
          <span v-else-if="file.active">active</span>
          <span v-else></span>
        </li>
      </ul>
      <ul v-else>
        <td colspan="7">
          <div class="text-center p-5">
            <h4>{{ $t('DropAnyWhere') }}<br/>{{ $t('Or') }}</h4>
            <label for="file" class="btn btn-lg btn-outline-secondary">{{ $t('SelectFiles') }}</label>
          </div>
        </td>
      </ul>
      <div v-show="$refs.upload && $refs.upload.dropActive" class="drop-active">
        <h3>{{ $t('DropFiles') }}</h3>
      </div>
      <div class="example-btn">
        <file-upload
          :multiple="true"
          :drop="true"
          v-model="files"
          ref="upload">
          <i class="fa fa-plus"></i>
        </file-upload>
      </div>
    </div>
  </div>
</template>

<script>
import FileUpload from 'vue-upload-component'
export default {
  components: {
    FileUpload,
  },
  data() {
    return {
      files: [],
      drop: true,
      questions: []
    }
  },
  methods: {
    saveFiles(){
      this.stringfyFiles();
      return this.questions;
    },
    stringfyFiles(){
      for(var i=0; i<this.files.length; i++){
        // Validate file type
        if(this.files[i].name.slice(-4) !== '.txt'){
          alert("Wrong file format, try again!");
          this.questions = [];
          return;
        }
        let reader = new FileReader();
        reader.addEventListener("loadend", event=>{
          let questions = event.target.result;
          questions = questions.split('\n');
          for(let k=0; k<questions.length; k++){
            if(questions[k]=="") continue;
            this.questions.push(questions[k])
          }
        })
        reader.readAsText(this.files[i].file);
      }
    },
  }
}
</script>

<style>
.example-drag label.btn {
  margin-bottom: 0;
  margin-right: 1rem;
}
.example-drag .drop-active {
  top: 0;
  bottom: 0;
  right: 0;
  left: 0;
  position: fixed;
  z-index: 9999;
  opacity: .6;
  text-align: center;
  background: #000;
}
.example-drag .drop-active h3 {
  margin: -.5em 0 0;
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  -webkit-transform: translateY(-50%);
  -ms-transform: translateY(-50%);
  transform: translateY(-50%);
  font-size: 40px;
  color: #fff;
  padding: 0;
}

li{
  list-style-type: none;
  font-size: 25px;
  font-weight: bold;
}
</style>
