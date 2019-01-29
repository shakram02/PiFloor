var modalQues = Vue.component('modal', {
    data: function () {
      return {
        showModal: false,
      }
    },
    methods: {
        onClose: function() {
            this.$emit('close');
        }
    },
    template: `
    <div>
        <button @click="showModal = true">Questions</button>
        <transition name="modal" v-if="showModal">
            <div class="modal-mask">
            <div class="modal-wrapper">
                <div class="modal-container">
                <slot></slot>
                <div class="modal-footer">
                    <slot name="footer">
                    <button class="modal-default-button" @click="showModal = false; onClose()">
                        Done
                    </button>
                    </slot>
                </div>
                </div>
            </div>
            </div>
        </transition>
    </div>
  `
})
  