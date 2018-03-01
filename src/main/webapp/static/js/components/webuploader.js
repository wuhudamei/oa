(function() {
  var index = 0;
  var WebUploaderComponent = Vue.extend({
    // data-id 供 webuploader 寻找容器使用
    template: '<div class="wuc-con" data-id="webuploader" :id="targetId"><slot></slot></div>',
    props: {
      type: {
        type: String,
        required: true
      },
      // targetId: {
      //   type: String
      // },
      text: {
        type: String,
        default: '直接导入模板'
      },
      wResize: {
        type: Boolean,
        default: false
      },
      wMultiple:{
        type: Boolean,
        default: false
      },
      wAccept: {
        type: Object,
        default: function() {
          return {
            title: 'Excel',
            extensions: 'xls,xlsx,txt',
            mimeTypes: 'application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
          }
        }
      },
      wFormData: {
        type: Object
          // 不能包含 type name id size lastModifiedDate file 等关键字
      },
      wAuto: {
        type: Boolean,
        default: true
          // 自动上传
      },
      wChunked: {
        type: Boolean,
        default: false
          // 分片处理大文件上传
      },
      wFileNumLimit: {
        type: Number,
        default: null
          // 文件总数,默认不限制上传数量
      },
      wFileSizeLimit: {
        type: Number,
        default: 1024 * 1024 * 5
      },
      wFileSingleSizeLimit: {
        type: Number,
        default: 1024 * 1024 * 5 * 1
      },
      wServer: {
        type: String,
        default: ctx + '/apply/activity/importExcel'
      }
    },
    data: function() {
      return {
        config: {
          swf: ctx + '/static/admin/vendor/webupload/Uploader.swf',

        },
        uploader: null,
        targetId:('webuploader') + index++
      };
    },
    methods: {

    },
    computed: function() {

    },
    created: function() {},
    ready: function() {
      var self = this;
      try {
        this.uploader = new WebUploader.Uploader({
          duplicate: true,
          pick: {
            id:this.$el,
            multiple:this.wMultiple
          },
          dnd: this.$el,
          resize: this.wResize,
          swf: this.config.swf,
          auto: this.wAuto,
          server: this.wServer,
          accept: this.wAccept,
          fileNumLimit: this.wFileNumLimit,
          fileSingleSizeLimit: this.wFileSingleSizeLimit,
          fileSizeLimit: this.wFileSizeLimit
        });
        this.uploader.on('uploadBeforeSend', function(obj, data, header) {
          for (key in self.wFormData) {
            data[key] = self.wFormData[key];
          }
        });
        this.uploader.on('uploadSuccess', function(file, response) {
          self.$dispatch('webupload-upload-success-' + self.type, file, response);
        });
        this.uploader.on('uploadError', function(file, response) {
          self.$dispatch('webupload-upload-error-' + self.type, file, response);
        });
        this.uploader.on('error', function(type) {
          switch (type) {
            case 'Q_EXCEED_NUM_LIMIT':
              alert('上传文件超出了限制');
              break;
            case 'Q_EXCEED_SIZE_LIMIT':
              alert('上传文件大小超过限制');
              break;
            case 'Q_TYPE_DENIED':
              alert('上传文件类型错误');
              break;
            default:
              break;
          }
        });
        this.uploader.on('uploadProgress', function(file, percentage) {
          self.$dispatch('webupload-upload-progress-' + self.type, file, percentage);
        });
        this.uploader.on('uploadStart', function(file) {
          self.$dispatch('webupload-upload-start-' + self.type, file);
        });
        this.uploader.on('uploadComplete', function(file) {
          self.$dispatch('webupload-upload-complete-' + self.type, file);
        });
      } catch (e) {
        this.$toastr.warning('升级浏览器版本后再使用本页面');
      }
    },
    beforeDestroy: function() {
      this.uploader.destroy();
    }
  });

  RocoVueComponents.WebUploaderComponent = WebUploaderComponent;
})(Vue, RocoVueComponents);