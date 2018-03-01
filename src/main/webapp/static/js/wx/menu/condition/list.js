/**
 * Created by aaron on 2017/3/9.
 */
+(function () {
  $('#wechatMenu').addClass('active');
  $('#conditionalMenu').addClass('active');
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
        name: '个性化菜单',
        active: true //激活面包屑的
      }],
      // 查询表单
      form: {
        keyword: ''
      },
      selectedRows: {}, //选中列
      modalModel: null, //模式窗体模型
      _$el: null, //自己的 el $对象
      _$dataTable: null //datatable $对象
    },
    created: function () {
    },
    ready: function () {
      this.drawTable();
    },
    methods: {
      query: function () {
        this.$dataTable.bootstrapTable('refresh', {pageNumber: 1});
      },
      drawTable: function () {
        var self = this;
        self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
          url: '/api/wx/condition/menu',
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
              field: 'name',
              title: '名称',
              align: 'center'
            }, {
              field: 'description',
              title: '描述',
              align: 'center',
              formatter: function (value) {
                if (value && value.length > 15) {
                  return value.substr(0, 15) + '...';
                }
                return value;
              }
            }, {
              field: 'tag',
              title: '标签',
              align: 'center',
              formatter: function (value) {
                if (value) {
                  return value.name || '';
                }
                return '';
              }
            }, {
              field: 'id',
              title: "操作",
              align: 'center',
              formatter: function (value, row, index) {
                return '<button data-handle="edit" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">编辑</button>&nbsp;'
                  + '<button data-handle="delete" data-id="' + row.id + '" type="button" class="btn btn-xs btn-danger">删除</button>&nbsp;'
                  + '<button data-handle="set" data-id="' + row.id + '" data-name="' + row.name + '" type="button" class="btn btn-xs btn-default">设置菜单</button>&nbsp;'
                  + '<button data-handle="sync" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">同步</button>&nbsp;';

              }
            }]
        });

        // 编辑按钮事件
        self.$dataTable.on('click', '[data-handle="edit"]', function (e) {
          var id = $(this).data('id');
          self.$http.get('/api/wx/condition/menu/' + id).then(function (response) {
            var res = response.data;
            if (res.code == '1') {
              var menu = res.data;
              var _$modal = $('#createModal').clone();
              var $modal = _$modal.modal({
                height: 250,
                maxHeight: 450,
                backdrop: 'static',
                keyboard: false
              });
              createModal($modal, menu, true);
            }
          });
          e.stopPropagation();
        });

        // 删除按钮事件
        self.$dataTable.on('click', '[data-handle="delete"]', function (e) {
          var id = $(this).data('id');
          swal({
            title: '标签删除',
            text: '删除操作将会导致微信创建的个性化菜单同时被删除，请谨慎考虑！',
            type: 'info',
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            showCancelButton: true,
            showConfirmButton: true,
            showLoaderOnConfirm: true,
            confirmButtonColor: '#ed5565',
            closeOnConfirm: false
          }, function () {
            self.$http.delete('/api/wx/condition/menu/' + id).then(function (response) {
              var res = response.data;
              if (res.code == '1') {
                self.$toastr.success('删除成功！');
                self.$dataTable.bootstrapTable('refresh');
              } else {
                self.$toastr.error(res.message);
              }
            }).catch(function () {
              self.$toastr.error('系统异常');
            }).finally(function () {
              swal.close();
            });
          });
          e.stopPropagation();
        });

        // 设置菜单
        self.$dataTable.on('click', '[data-handle="set"]', function (e) {
          var id = $(this).data('id');
          var name = $(this).data('name');
          window.location.href = ctx + '/admin/wechat/menu/condition/detail?id=' + id + '&name=' + name;
          e.stopPropagation();
        });

        // 同步时间
        self.$dataTable.on('click', '[data-handle="sync"]', function (e) {
          var id = $(this).data('id');
          swal({
            title: '同步菜单',
            text: '你确定要将个性化菜单的更改同步到微信端？',
            type: 'info',
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            showCancelButton: true,
            showConfirmButton: true,
            showLoaderOnConfirm: true,
            confirmButtonColor: '#ed5565',
            closeOnConfirm: false
          }, function () {
            self.$http.get('/api/wx/condition/menu/sync/' + id).then(function (response) {
              var res = response.data;
              if (res.code == '1') {
                self.$toastr.success('同步成功！');
                self.$dataTable.bootstrapTable('refresh');
              } else {
                self.$toastr.error(res.message);
              }
            }).catch(function () {
              self.$toastr.error('系统异常');
            }).finally(function () {
              swal.close();
            });
          });
          e.stopPropagation();
        });
      },
      create: function () {
        var _$modal = $('#createModal').clone();
        var $modal = _$modal.modal({
          height: 250,
          maxHeight: 450,
          backdrop: 'static',
          keyboard: false
        });
        createModal($modal, {
          name: '',
          description: '',
          tag: {
            id: ''
          }
        }, false);
      }
    }
  });

  function createModal($el, model, isEdit) {
    // 获取 node
    var el = $el.get(0);
    // 创建 Vue 对象编译节点
    vueContract = new Vue({
      el: el,
      // 模式窗体必须引用 ModalMixin
      mixins: [RocoVueMixins.ModalMixin],
      $modal: $el, //模式窗体 jQuery 对象
      data: {
        //控制 按钮是否可点击
        disabled: false,
        //模型复制给对应的key
        menu: model,
        tags: null,
        // 进项列表
        submitBtnClick: false
      },
      created: function () {
        this.fetchTag();
      },
      methods: {
        fetchTag: function () {
          var self = this;
          self.$http.get("/api/wx/tag/all").then(function (response) {
            var res = response.data;
            if (res.code == '1') {
              self.tags = res.data;
            }
          })
        },
        submit: function () {
          var self = this;
          self.submitBtnClick = true;
          self.$validate(true, function () {
            if (self.$validation.valid) {
              self.disabled = true;
              if (isEdit) {
                self.$http.put(ctx + '/api/wx/condition/menu', self.menu)
                  .then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                      $el.on('hidden.bs.modal', function () {
                        vueIndex.$dataTable.bootstrapTable('refresh');
                        self.$toastr.success('更新成功');
                      });
                      $el.modal('hide');
                    } else {
                      self.$toastr.error(res.message);
                    }
                  }).finally(function () {
                  self.disabled = false;
                });
              } else {
                self.$http.post(ctx + '/api/wx/condition/menu', self.menu)
                  .then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                      $el.on('hidden.bs.modal', function () {
                        vueIndex.$dataTable.bootstrapTable('refresh');
                        self.$toastr.success('创建成功');
                      });
                      $el.modal('hide');
                    } else {
                      self.$toastr.error(res.message);
                    }
                  }).finally(function () {
                  self.disabled = false;
                });
              }
            }
          });
        }
      }
    });
    // 创建的Vue对象应该被返回
    return vueContract;
  }
})();

