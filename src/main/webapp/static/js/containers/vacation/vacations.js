/**
 * Created by aaron on 2017/3/30.
 */
var vueIndex;
+(function () {
  $('#leveAndBusinessMenu').addClass('active');
  $('#myLeveApply').addClass('active');
  vueIndex = new Vue({
    el: '#container',
    mixins: [RocoVueMixins.DataTableMixin],
    data: {
      // 面包屑
      breadcrumbs: [{
        path: '/',
        name: '主页'
      }, {
        path: '/',
        name: '请假管理',
        active: true //激活面包屑的
      }],
      // 查询表单
      form: {
        startTime: '',
        endTime: '',
        applyType:[]
      },
      modalModel: null, //模式窗体模型
      _$el: null, //自己的 el $对象
      $dataTable: null //datatable $对象
    },
    created: function () {
    },
    ready: function () {
      this.getApplyType();
      this.initDatePicker();
      this.drawTable();
    },
    methods: {
      getApplyType:function () {
        var self = this;
        var data = {
          'type':5
        };
        self.$http.get(ctx + '/api/dict/getByType',data).then(function (res) {
              if (res.data.code == 1) {
                self.form.applyType = res.data.date;
              }
            }
        ).finally(function () {});
      },
      initDatePicker: function () {
        $(this.$els.startTime).datetimepicker({
          minView: 0,
          format: 'yyyy-mm-dd hh:ii',
          todayBtn: true
        });
        $(this.$els.endTime).datetimepicker({
          minView: 0,
          format: 'yyyy-mm-dd hh:ii',
          todayBtn: true
        });
      },
      query: function () {
        this.$dataTable.bootstrapTable('selectPage', 1);
      },
      drawTable: function () {
        var self = this;
        self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
          url: '/api/vacations',
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
              field: 'applyTitle',
              title: '申请标题',
              align: 'center'
            },{
              field: 'applyCode',
              title: '申请编号',
              align: 'center'
            }, {
              field: 'startTime',
              title: '开始时间',
              align: 'center'
            }, {
              field: 'endTime',
              title: '结束时间',
              align: 'center'
            }, {
              field: 'days',
              title: '请假天数',
              align: 'center'
            }, {
              field: 'reason',
              title: '申请事由',
              align: 'center'
            }, {
              field: 'status',
              title: '申请状态',
              align: 'center',
              formatter: function (value, row, index) {
                var s = '';
                switch (value) {
                  case 'APPROVALING':
                    s = '审批中';
                    break;
                  case 'ADOPT':
                    s = '审批通过';
                    break;
                  case 'REFUSE':
                    s = '拒绝';
                    break;
                  default:
                    break;
                }
                return s;
              }
            }, {
              field: 'approver',
              title: '当前进度',
              align: 'center',
              formatter: function (value, row, index) {
                if (value) {
                  return "等待[" + value + "]审批"
                }
              }
            }, {
              field: 'createTime',
              title: '提交日期',
              align: 'center'
            }, {
              field: 'id', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
              title: "操作",
              align: 'center',
              formatter: function (value, row, index) {
                var btn='';
                // 被拒绝的可以编辑
                if (row.status == 'REFUSE') {
                  btn += '<button data-handle="edit" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">编辑</button>&nbsp;'
                }
                btn += '<button data-handle="view" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">详情</button>&nbsp;';
                // 审批中的允许删除
                // if (row.status == 'APPROVALING') {
                //   btn += '<button data-handle="delete" data-id="' + row.id + '" type="button" class="btn btn-xs btn-danger">删除</button>&nbsp;';
                // }
                return btn;
              }
            }]
        });

        // 查看
//        self.$dataTable.on('click', '[data-handle="view"]', function (e) {
//          var id = $(this).data('id');
//          self.$http.get('/api/vacations/' + id).then(function (response) {
//            var res = response.data;
//            if (res.code == '1') {
//              var vacation = res.data;
//              var _$modal = $('#view').clone();
//              var $modal = _$modal.modal({
//                height: 450,
//                maxHeight: 450,
//                backdrop: 'static',
//                keyboard: false
//              });
//              view($modal, vacation, id);
//              //model($modal, vacation, true);
//            } else {
//              self.$toastr.error('获取记录失败');
//            }
//          });
//          e.stopPropagation();
//        });

        // 编辑
        self.$dataTable.on('click', '[data-handle="edit"]', function (e) {
          var id = $(this).data('id');
          self.$http.get('/api/vacations/' + id).then(function (response) {
            var res = response.data;
            if (res.code == '1') {
              var vacation = res.data;
              var _$modal = $('#modal').clone();
              var $modal = _$modal.modal({
                height: 450,
                maxHeight: 450,
                backdrop: 'static',
                keyboard: false
              });
              modal($modal, {
                id: vacation.id,
                startTime: vacation.startTime,
                endTime: vacation.endTime,
                days: vacation.days,
                reason: vacation.reason
              }, true);
            } else {
              self.$toastr.error('获取记录失败');
            }
          });
          e.stopPropagation();
        });

        // 删除
        self.$dataTable.on('click', '[data-handle="delete"]', function (e) {
          var id = $(this).data('id');
          swal({
            title: '删除请假申请',
            text: '确定删除这条请假申请吗？（只有申请中的才允许被删除）',
            type: 'info',
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            showCancelButton: true,
            showConfirmButton: true,
            showLoaderOnConfirm: true,
            confirmButtonColor: '#ed5565',
            closeOnConfirm: false
          }, function () {
            self.$http.delete('/api/vacations/' + id).then(function (res) {
              if (res.data.code == 1) {
                self.$toastr.success('操作成功');
                self.$dataTable.bootstrapTable('refresh');
              }
            }).catch(function () {
              self.$toastr.error('系统异常');
            }).finally(function () {
              swal.close();
            });
          });
          e.stopPropagation()
        });
      },
      createBtnClickHandler: function (e) {
        var _$modal = $('#modal').clone();
        var $modal = _$modal.modal({
          height: 450,
          maxHeight: 450,
          backdrop: 'static',
          keyboard: false
        });
        modal($modal, {
          startTime: '',
          endTime: '',
          days: '',
          reason: ''
        },false);
      }
    }
  });

  // 实现弹窗方法
  function modal($el, model, isEdit) {
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
        vacation: model,
        submitBtnClick: false,
        applyType:[]
      },
      validators: {
        validDays: function (val) {
          return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(val);
        }
      },
      created: function () {
      },
      ready: function () {
        this.getApplyType();
        this.initDatePicker();
      },
      methods: {
        getApplyType:function () {
          var self = this;
          var data = {
            'type':5
          };
          self.$http.get(ctx + '/api/dict/getByType',data).then(function (res) {
                if (res.data.code == 1) {
                  self.applyType = res.data.date;
                }
              }
          ).finally(function () {});
        },
        initDatePicker: function () {
          $(this.$els.startTime).datetimepicker({
            minView: 0,
            format: 'yyyy-mm-dd hh:ii',
            todayBtn: true
          });
          $(this.$els.endTime).datetimepicker({
            minView: 0,
            format: 'yyyy-mm-dd hh:ii',
            todayBtn: true
          });
        },
        submit: function () {
          var self = this;
          self.submitBtnClick = true;
          self.$validate(true, function () {
            if (self.$validation.valid) {
              self.disabled = true;
              if (!isEdit) { // 新增
                self.$http.post(ctx + '/api/vacations', self.vacation).then(function (res) {
                    if (res.data.code == 1) {
                      $el.on('hidden.bs.modal', function () {
                        self.$toastr.success('创建成功！');
                      });
                      vueIndex.$dataTable.bootstrapTable('refresh');
                      $el.modal('hide');
                    }
                  }
                ).finally(function () {
                  self.disabled = false;
                });
              } else { // 编辑
                self.$http.put(ctx + '/api/vacations', self.vacation).then(function (res) {
                    if (res.data.code == 1) {
                      $el.on('hidden.bs.modal', function () {
                        self.$toastr.success('编辑成功！');
                      });
                      vueIndex.$dataTable.bootstrapTable('refresh');
                      $el.modal('hide');
                    }
                  }
                ).finally(function () {
                  self.disabled = false;
                });
              }
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