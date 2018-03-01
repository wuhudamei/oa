var vueIndex = null;
+(function () {
  $('#empManagerMenu').addClass('active');
  $('#employeeManage').addClass('active');
  vueIndex = new Vue({
    el: '#container',
    data: {
      // 面包屑
      breadcrumbs: [{
        path: '/',
        name: '主页'
      }, {
        path: '/',
        name: '编辑员工',
        active: true //激活面包屑的
      }],
      webUploaderSub: {
        type: 'sub',
        formData: {},
        accept: {
          title: '文件',
          extensions: 'jpg,jpeg,png'
        },
        server: ctx + '/api/upload',
        //上传路径
        fileNumLimit: 1,
        fileSizeLimit: 50000 * 1024,
        fileSingleSizeLimit: 5000 * 1024
      },
      employee: null,
      orgData: null, // 机构树数据
      orgName: null, // 机构名称回显
      showOrgTree: false, // 是否显示机构树
      submitBtnClick: false,
      _$eduDataTable: null,
      _$workDataTable: null,
      disabled: false
    },
    components: {
      'web-uploader': RocoVueComponents.WebUploaderComponent
    },
    events: {
      'webupload-upload-success-sub': function (file, res) {
        console.log(res);
        if (res.code == '1') {
          this.$toastr.success('上传成功');
          this.employee.photo = res.data.path;
        } else {
          this.$toastr.error(res.message);
        }
      }
    },
    created: function () {
      var self = this;
      self.fetchOrgTree();
    },
    ready: function () {
      var self = this;
      var id = this.$parseQueryString()['id'];
      self.$http.get('/api/employees/' + id)
        .then(function (response) {
          var res = response.data;
          if (res.code == '1') {
            return res.data;
          } else {
            self.$toastr.error(res.message);
          }
        })
        .then(function (data) { // Promise写法 data是上一个then的return结果
          self.employee = data;
          // 机构名称回显
          self.orgName = self.employee.org.org.orgName;
          self.employee.orgId = self.employee.org.department.id;
          // 渲染教育经历和工作经历的table
          self.drawEduDataTable();
          self.drawWorkDataTable();
        });
      self.initDatePicker();

    },
    methods: {
      // 获取机构选择树的数据
      fetchOrgTree: function () {
        var self = this;
        this.$http.get('/api/org/fetchTree').then(function (response) {
          var res = response.data;
          if (res.code == 1) {
            self.orgData = res.data;
          }
        })
      },
      // 勾选机构数外部时，隐藏窗口
      clickOut: function () {
        this.showOrgTree = false
      },
      // 选择机构时回调事件
      selectOrg: function (node) {
        var self = this;
        self.orgName = node.name;
        self.employee.orgId = node.id;
      },
      // 初始化时间选择器
      initDatePicker: function () {
        $(this.$els.entryDate).datetimepicker({
          minView: 2,
          format: 'yyyy-mm-dd',
          todayBtn: true
        });
        $(this.$els.birthday).datetimepicker({
          minView: 2,
          format: 'yyyy-mm-dd',
          todayBtn: true
        });
      },
      drawEduDataTable: function () {
        var self = this;
        self._$eduDataTable = $('#eduDataTable').bootstrapTable({
          data: self.employee.edus,
          columns: [{
            field: 'startDate',
            title: '开始日期',
            align: 'center'
          }, {
            field: 'endDate',
            title: '结束日期',
            align: 'center'
          }, {
            field: 'schoolName',
            title: '学校名称',
            align: 'center'
          }, {
            field: 'subject',
            title: '专业',
            align: 'center'
          }, {
            field: 'degree',
            title: '学历',
            align: 'center'
          }, {
            field: 'schoolName',
            title: '操作',
            align: 'center',
            formatter: function (value, row, index) {
              return '<button data-handle="delete-edu" data-index="' + index + '" type="button" class="btn btn-xs btn-danger">删除</button>';
            }
          }]
        });

        // 删除事件
        self._$eduDataTable.on('click', '[data-handle="delete-edu"]', function (e) {
          var index = $(this).data('index');
          self.employee.edus.splice(index, 1);
          self.reloadEduDataTable();
          e.stopPropagation();
        });
      },
      reloadEduDataTable: function () {
        var self = this;
        self._$eduDataTable.bootstrapTable('load', self.employee.edus);
      },
      drawWorkDataTable: function () {
        var self = this;
        self._$workDataTable = $('#workDataTable').bootstrapTable({
          data: self.employee.works,
          columns: [{
            field: 'startDate',
            title: '开始日期',
            align: 'center'
          }, {
            field: 'endDate',
            title: '结束日期',
            align: 'center'
          }, {
            field: 'companyName',
            title: '公司名称',
            align: 'center'
          }, {
            field: 'position',
            title: '职位',
            align: 'center'
          }, {
            field: 'duty',
            title: '主要工作',
            align: 'center',
            formatter: function (value, row, index) {
              if (value && value.length > 15) {
                return value.substring(0, 15);
              } else {
                return value;
              }
            }
          }, {
            field: 'certifierName',
            title: '证明人',
            align: 'center'
          }, {
            field: 'certifierPhone',
            title: '联系方式',
            align: 'center'
          }, {
            field: 'schoolName',
            title: '操作',
            algin: 'center',
            formatter: function (value, row, index) {
              return '<button data-handle="delete-work" data-index="' + index + '" type="button" class="btn btn-xs btn-danger">删除</button>';
            }
          }]
        });

        // 删除事件
        self._$workDataTable.on('click', '[data-handle="delete-work"]', function (e) {
          var index = $(this).data('index');
          self.employee.works.splice(index, 1);
          self.reloadWorkDataTable();
          e.stopPropagation();
        });
      },
      reloadWorkDataTable: function () {
        var self = this;
        self._$workDataTable.bootstrapTable('load', self.employee.works);
      },
      addEdu: function () { //添加教育经历
        var self = this;
        var _$modal = $('#edu').clone();
        var $modal = _$modal.modal({
          height: 450,
          maxHeight: 450,
          backdrop: 'static',
          keyboard: false
        });
        eduModal($modal, {
          startDate: '',
          endDate: '',
          schoolName: '',
          subject: '',
          degree: ''
        }, false, self);
      },
      addWork: function () { //添加教育经历
        var self = this;
        var _$modal = $('#work').clone();
        var $modal = _$modal.modal({
          height: 450,
          maxHeight: 450,
          backdrop: 'static',
          keyboard: false
        });
        workModal($modal, {
          startDate: '',
          endDate: '',
          companyName: '',
          position: '',
          duty: '',
          certifierName: '',
          certifierPhone: ''
        }, false, self);
      },
      submit: function () {
        var self = this;
        self.submitBtnClick = true;
        self.$validate(true, function () {
          if (self.$validation.valid) {
            self.disabled = true;
            // spring反序列化json串时，如果传的是空值，遇到枚举时会报错，所以只传有值得属性
            if(self.employee.employeeStatus == 'DIMISSION'){
                self.employee.employeeStatus ='ON_JOB';
            }
            var data = self.filterNull(self.employee);
            self.$http.put(ctx + '/api/employees', data).then(function (response) {
              var res = response.data;
              if (res.code == 1) {
                swal({
                  title: '更新成功',
                  text: '',
                  type: 'success',
                  confirmButtonText: '确定',
                  showCancelButton: false,
                  showConfirmButton: true,
                  showLoaderOnConfirm: true,
                  confirmButtonColor: '#ed5565',
                  closeOnConfirm: false
                }, function () {
                  swal.close();
                  window.location.href = ctx + '/admin/employee/management';
                });
              } else {
                self.$toastr.error(res.message);
                self.disabled = false;
              }
            })
          }
        });
      },
      //如果传空串，spring反序列化json串时，遇到枚举会报错，所以只传有值得属性
      // 但由于是更新操作，所以有些字段允许是空值，因此只考虑后来是枚举类型的字段
      filterNull: function (data) {
        var emun = ['gender', 'censusNature', 'maritalStatus', 'englishLevel'];
        var t = {};
        if (data) {
          for (var k in data) {
            var v = data[k];
            if ($.inArray(k, emun)) { // 如果属性后台类型为枚举类型，则属性有值才post
              if (k) {
                t[k] = v;
              }
            } else {
              t[k] = v;
            }
          }
        }
        return t;
      },
      cancel: function () {
        window.history.go(-1);
      }
    }
  });

  // 添加教育经历
  function eduModal($el, model, isEdit, parent) {
    // 获取 node
    var el = $el.get(0);
    // 创建 Vue 对象编译节点
    var vueModal = new Vue({
      el: el,
      // 模式窗体必须引用 ModalMixin
      mixins: [RocoVueMixins.ModalMixin],
      $modal: $el, //模式窗体 jQuery 对象
      data: {
        //控制 按钮是否可点击
        disabled: false,
        //模型复制给对应的key
        edu: model,
        submitBtnClick: false
      },
      created: function () {
      },
      ready: function () {
        var self = this;
        self.initDatePicker();
      },
      methods: {
        initDatePicker: function () {
          $(this.$els.startDate).datetimepicker({
            minView: 2,
            format: 'yyyy-mm',
            todayBtn: true
          });
          $(this.$els.endDate).datetimepicker({
            minView: 2,
            format: 'yyyy-mm',
            todayBtn: true
          });
        },
        submit: function () {
          var self = this;
          self.submitBtnClick = true;
          self.$validate(true, function () {
            if (self.$validation.valid) {
              self.disabled = true;
              $el.on('hidden.bs.modal', function () {
                parent.employee.edus.push(self.edu);
                parent.reloadEduDataTable();
                self.$toastr.success('添加成功');
              });
              $el.modal('hide');
            }
          });
        }
      }
    });

    // 创建的Vue对象应该被返回
    return vueModal;
  }

  // 添加工作经历
  function workModal($el, model, isEdit, parent) {
    // 获取 node
    var el = $el.get(0);
    // 创建 Vue 对象编译节点
    var vueModal = new Vue({
      el: el,
      // 模式窗体必须引用 ModalMixin
      mixins: [RocoVueMixins.ModalMixin],
      $modal: $el, //模式窗体 jQuery 对象
      data: {
        //控制 按钮是否可点击
        disabled: false,
        //模型复制给对应的key
        work: model,
        submitBtnClick: false
      },
      created: function () {
      },
      ready: function () {
        var self = this;
        self.initDatePicker();
      },
      methods: {
        initDatePicker: function () {
          $(this.$els.startDate).datetimepicker({
            minView: 2,
            format: 'yyyy-mm',
            todayBtn: true
          });
          $(this.$els.endDate).datetimepicker({
            minView: 2,
            format: 'yyyy-mm',
            todayBtn: true
          });
        },
        submit: function () {
          var self = this;
          self.submitBtnClick = true;
          self.$validate(true, function () {
            if (self.$validation.valid) {
              self.disabled = true;
              $el.on('hidden.bs.modal', function () {
                parent.employee.works.push(self.work);
                parent.reloadWorkDataTable();
                self.$toastr.success('添加成功');
              });
              $el.modal('hide');
            }
          });
        }
      }
    });

    // 创建的Vue对象应该被返回
    return vueModal;
  }
})();