+(function (RocoUtils) {
  $('#empManagerMenu').addClass('active');
  $('#employeeManage').addClass('active');
  var vueIndex = new Vue({
    el: '#container',
    mixins: [RocoVueMixins.DataTableMixin],
    data: {
      // 面包屑
      breadcrumbs: [{
        path: '/',
        name: '主页'
      }, {
        path: '/',
        name: '员工合同',
        active: true //激活面包屑的
      }],
      // 查询表单
      form: {
        empId: ''
      },
      empId: '',
      empName: '',
      selectedRows: {}, //选中列
      modalModel: null, //模式窗体模型
      _$el: null, //自己的 el $对象
      _$dataTable: null //datatable $对象
    },
    created: function () {
      var self = this;
      var id = this.$parseQueryString()['id'];
      var name = this.$parseQueryString()['name'];
      self.empId = id;
      self.empName = name;
      self.form.empId = id;
    },
    ready: function () {
      this.drawTable();
    },
    methods: {
      query: function () {
        this.$dataTable.bootstrapTable('selectPage', 1);
      },
      drawTable: function () {
        var self = this;
        self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
          url: '/api/employeeContract/list',
          method: 'get',
          dataType: 'json',
          cache: false, //去缓存
          pagination: true, //是否分页
          sidePagination: 'server', //服务端分页
          queryParams: function (params) {
            // 将table 参数与搜索表单参数合并
            return _.extend({}, params, self.form);
          },
          mobileResponsive: true,
          undefinedText: '-', //空数据的默认显示字符
          striped: true, //斑马线
          maintainSelected: true, //维护checkbox选项
          columns: [
            {
              field: 'contractNo',
              title: '合同编号',
              align: 'center'
            }, {
              field: 'firstParty',
              title: '甲方',
              align: 'center'
            }, {
              field: 'signDate',
              title: '签订日期',
              align: 'center'
            }, {
              field: 'effectiveDate',
              title: '生效日期',
              align: 'center'
            }, {
              field: 'tryDate',
              title: '转正日期',
              align: 'center'
            }, {
              field: 'endDate',
              title: '终止日期',
              align: 'center'
            }, {
              field: 'fileName',
              title: '附件',
              align: 'center',
              formatter: function (value, row, index) {
                if (value) {
                  return '<a href="' + row.fileUrl + '">' + value + '</a>';
                }
                return '';
              }
            }, {
              field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
              title: "操作",
              align: 'center',
              formatter: function (value, row, index) {
                var btns = '';
                btns += '<button data-handle="edit" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">编辑</button>&nbsp;';
                btns += '<button data-handle="del" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-danger">删除</button>&nbsp;';
                return btns;
              }
            }]
        });

        // 编辑按钮
        self.$dataTable.on('click', '[data-handle="edit"]', function (e) {
          var id = $(this).data('id');
          self.$http.get('/api/employeeContract/' + id).then(function (response) {
            var res = response.data;
            if (res.code == '1') {
              var _$modal = $('#modal').clone();
              var $modal = _$modal.modal({
                height: 450,
                maxHeight: 450,
                backdrop: 'static',
                keyboard: false
              });
              $modal.on('shown.bs.modal', function () {
                modal($modal, response.data.data, true);
              });
            }
          });
          e.stopPropagation();
        });

        // 删除按钮
        self.$dataTable.on('click', '[data-handle="del"]', function (e) {
          var id = $(this).data('id');
          swal({
            title: '合同删除',
            text: '确定要删除该合同？',
            type: 'info',
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            showCancelButton: true,
            showConfirmButton: true,
            showLoaderOnConfirm: true,
            confirmButtonColor: '#ed5565',
            closeOnConfirm: false
          }, function () {
            self.$http.delete('/api/employeeContract/' + id).then(function (response) {
              var res = response.data;
              if (res.code == '1') {
                self.$toastr.success('删除成功！');
                self.$dataTable.bootstrapTable('refresh');
              }
            }).catch(function () {
              self.$toastr.error('系统异常');
            }).finally(function () {
              swal.close();
            });
          });
          e.stopPropagation();
        });

        self.checkEventHandle('ordNo');
      },
      createBtnClickHandler: function (e) {
        var self = this;
        var _$modal = $('#modal').clone();
        var $modal = _$modal.modal({
          height: 450,
          maxHeight: 450,
          backdrop: 'static',
          keyboard: false
        });
        $modal.on('shown.bs.modal', function () {
          modal($modal, {
            id: '',
            empId: self.empId,
            contractNo: '',
            firstParty: '',
            secondParty: '',
            signDate: '',
            effectiveDate: '',
            tryDate: '',
            endDate: '',
            baseSalary: '',
            meritPay: '',
            otherSalary: '',
            remarks: '',
            fileName: '',
            fileUrl: ''
          }, false);
        });
      },
      activeDatepicker: function () {
        $(this.$els.beginTime).datetimepicker({
          minView: 2,
          format: 'yyyy-mm-dd',
          todayBtn: true
        });
        $(this.$els.endTime).datetimepicker({
          minView: 2,
          format: 'yyyy-mm-dd',
          todayBtn: true
        });
      },
    }
  });

  // 实现弹窗方法
  function modal($el, model, isEdit) {
    // 获取 node
    var el = $el.get(0);
    // 创建 Vue 对象编译节点
    vueContract = new Vue({
      el: el,
      components: {
        'web-uploader': RocoVueComponents.WebUploaderComponent
      },
      // 模式窗体必须引用 ModalMixin
      mixins: [RocoVueMixins.ModalMixin],
      validators: {
        unitTwodecimal: function (val) {
          if (val.trim() === '') {
            return true;
          } else {
            return /^\d+$|^\d+\.\d{1,2}$/.test(val);
          }
        }
      },
      $modal: $el, //模式窗体 jQuery 对象
      data: {
        webUploaderSub: {
          type: 'sub',
          formData: {},
          accept: {
            title: '文件',
            extensions: 'doc,docx,xls,xlsx,pdf'
          },
          server: ctx + '/api/upload',
          //上传路径
          fileNumLimit: 1,
          fileSizeLimit: 50000 * 1024,
          fileSingleSizeLimit: 5000 * 1024
        },
        //控制 按钮是否可点击
        disabled: false,
        //模型复制给对应的key
        contract: model,
        // 进项列表
        submitBtnClick: false
      },
      created: function () {
      },
      ready: function () {
        this.activeDatepicker();
      },
      methods: {
        activeDatepicker: function () {
          $(this.$els.signDate).datetimepicker({
            minView: 2,
            format: 'yyyy-mm-dd',
            todayBtn: true
          });
          $(this.$els.effectiveDate).datetimepicker({
            minView: 2,
            format: 'yyyy-mm-dd',
            todayBtn: true
          });
          $(this.$els.tryDate).datetimepicker({
            minView: 2,
            format: 'yyyy-mm-dd',
            todayBtn: true
          });
          $(this.$els.endDate).datetimepicker({
            minView: 2,
            format: 'yyyy-mm-dd',
            todayBtn: true
          });
        },
        deleteFlie: function () {
          var self = this;
          self.$http.delete(ctx + '/api/upload', {
            params: {
              path: self.contract.fileUrl
            }
          }).then(function (response) {
            var res = response.data;
            if (res.code == '1') {
              this.contract.fileName = '';
              this.contract.fileUrl = '';
            } else {
              self.$toastr.error("删除失败");
            }
          });
        },
        submit: function () {
          var self = this;
          self.submitBtnClick = true;
          self.$validate(true, function () {
            if (self.$validation.valid) {
              self.disabled = true;
              if (isEdit) {
                self.$http.put(ctx + '/api/employeeContract', self.contract, {emulateJSON: true}).then(function (res) {
                  if (res.data.code == 1) {
                    $el.on('hidden.bs.modal', function () {
                      vueIndex.$dataTable.bootstrapTable('refresh');
                      self.$toastr.success('更新成功');
                    });
                    $el.modal('hide');
                  }
                }).finally(function () {
                  self.disabled = false;
                });
              } else {
                self.$http.post(ctx + '/api/employeeContract', self.contract, {emulateJSON: true}).then(function (res) {
                  if (res.data.code == 1) {
                    $el.on('hidden.bs.modal', function () {
                      vueIndex.$dataTable.bootstrapTable('refresh');
                      self.$toastr.success('创建成功');
                    });
                    $el.modal('hide');
                  }
                }).finally(function () {
                  self.disabled = false;
                });
              }
            }
          });
        }
      },
      events: {
        'webupload-upload-success-sub': function (file, res) {
          if (res.code == 1) {
            this.$toastr.success('上传成功');
            this.contract.fileName = res.data.fileName;
            this.contract.fileUrl = res.data.path;
          } else {
            this.$toastr.error(res.message);
          }
        }
      }

    });
    // 创建的Vue对象应该被返回
    return vueContract;
  }
})(this.RocoUtils);

