var tt = null;
+(function () {
  $('#wechatMenu').addClass('active');
  $('#customMenu').addClass('active');
  tt = new Vue({
    el: '#container',
    data: {
      $dataTable: null
    },
    ready: function () {
      this.fetchTreeData();
    },
    methods: {
      fetchTreeData: function () {
        var self = this;
        var _$jstree = $('#jstree');
        self.$http.get('/api/wx/menu/tree').then(function (response) {
          var res = response.data;
          if (res.code == 1) {
            $.jstree.defaults.sort = function (obj, deep) {
              return 1;
            };
            _$jstree.jstree({
              core: {
                multiple: false,
                // 不加此项无法动态删除节点
                check_callback: true,
                data: res.data
              },
              types: {
                default: {
                  icon: 'glyphicon glyphicon-stop'
                }
              },
              plugins: ['types', 'wholerow', 'changed']
            });
          }
        });
      },
      createBtnClickHandler: function (e) {
        var self = this;
        var _$jstree = $('#jstree');
        var menu = {
          id: '',
          name: '',
          type: '',
          level: '',
          clickKey: '',
          url: '',
          mediaId: '',
          pid: ''
        };
        var ref = _$jstree.jstree(true),
          sel = ref.get_selected(true);
        // 如果未选择节点，则默认创建一级菜单
        if (!sel.length) {
          menu.level = '1';
          menu.pid = 0;
          self.showModel(menu, false);
        } else {
          if (sel[0].parent != '#') {
            self.$toastr.error("微信最多只能创建两级菜单");
            return;
          }
          menu.level = '2';
          menu.pid = sel[0].id;
          this.showModel(menu, false);
        }
      },
      editBtn: function () {
        var self = this;
        var _$jstree = $('#jstree');
        var ref = _$jstree.jstree(true),
          sel = ref.get_selected(true);
        if (!sel.length) {
          toastr.error("请选择节点");
          return;
        } else {
          self.$http.get('/api/wx/menu/' + sel[0].id).then(function (response) {
            var res = response.data;
            if (res.code == 1) {
              var menu = res.data;
              this.showModel(menu, true);
            }
          });
        }
      },
      deleteBtn: function () {
        var self = this;
        var _$jstree = $('#jstree');
        var ref = _$jstree.jstree(true),
          sel = ref.get_selected(true);
        if (!sel.length) {
          toastr.error("请选择节点");
          return;
        } else {
          swal({
            title: "你确定删除该菜单吗?",
            text: "警告:删除后不可恢复！",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            closeOnConfirm: false
          }, function (isConfirm) {
            if (isConfirm) {
              self.$http.delete('/api/wx/menu/' + sel[0].id)
                .then(function (response) {
                  var res = response.data;
                  if (res.code == 1) {
                    swal("操作成功!", "", "success");
                    ref.delete_node(sel);
                  } else {
                    self.$toastr.error(res.message);
                  }
                }).catch(function () {
                swal("操作失败！", "", "error");
              }).finally(function () {
                swal.close();
              });
            }
          });
        }
      },

      showModel: function (menu, isEdit) {
        var _$modal = $('#modal').clone();
        var $modal = _$modal.modal({
          height: 300,
          maxHeight: 450,
          backdrop: 'static',
          keyboard: false
        });
        modal($modal, menu, isEdit, function (data) {
          var _$jstree = $('#jstree');
          var ref = _$jstree.jstree(true),
            sel = ref.get_selected(true);
          if (isEdit) {
            if (menu.pid == 0) { // 如果一级菜单 直接刷新页面
              window.location.reload();
            } else {
              ref.rename_node(sel, data.text);
            }
          } else {
            if (menu.pid == 0) { // 如果一级菜单 直接刷新页面
              window.location.reload();
            } else {
              ref.create_node(menu.pid, data);
            }
          }
        });
      }
    }
  });

  // 实现弹窗方法
  function modal($el, model, isEdit, callback) {
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
        menu: model,
        type: {
          'click': false
        },
        isEdit: isEdit,
        submitBtnClick: false
      },
      validators: {
        digit: function (val) {
          return /^[0-9]*$/.test(val);
        }
      },
      watch: {
        'menu.type': function (val) {
          var self = this;
          self.hideAllExtraElement();
          self.showExtraElement(val);
        }
      },
      created: function () {
      },
      ready: function () {
        var self = this;
        if (self.isEdit) {
          this.showExtraElement(self.menu.type);
        }
      },
      methods: {
        submit: function () {
          var self = this;
          self.submitBtnClick = true;
          self.$validate(true, function () {
            if (self.$validation.valid) {
              self.disabled = true;
              if (self.isEdit) {
                self.$http.put('/api/wx/menu', self.menu)
                  .then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                      $el.on('hidden.bs.modal', function () {
                        self.$toastr.success('编辑成功');
                        callback(res.data);
                      });
                      $el.modal('hide');
                    } else {
                      self.$toastr.error(res.message);
                    }
                  }).finally(function () {
                  self.disabled = false;
                });
              } else {
                self.$http.post('/api/wx/menu', self.menu)
                  .then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                      $el.on('hidden.bs.modal', function () {
                        self.$toastr.success('创建成功');
                        callback(res.data);
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
        },
        hideAllExtraElement: function () {
          var self = this;
          _.forEach(self.type, function (n, key) {
            self.type[key] = false;
          });
        },
        showExtraElement: function (val) {
          var self = this;
          switch (val) {
            case 'VIEW':
              self.type.click = true;
              break;
          }
        }
      }
    });

    // 创建的Vue对象应该被返回
    return vueModal;
  }
})();