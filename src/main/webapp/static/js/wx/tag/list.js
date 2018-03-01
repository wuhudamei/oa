/**
 * Created by aaron on 2017/3/9.
 */
+(function () {
  $('#wechatMenu').addClass('active');
  $('#tagMenu').addClass('active');
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
        name: '标签管理',
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
          url: '/api/wx/tag',
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
              field: 'id',
              title: "操作",
              align: 'center',
              formatter: function (value, row, index) {
                return '<button data-handle="view" data-id="' + row.id + '" data-name="' + row.name + '" type="button" class="btn btn-xs btn-default">详情</button>&nbsp;'
                  + '<button data-handle="edit" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">编辑</button>&nbsp;'
                  + '<button data-handle="delete" data-id="' + row.id + '" type="button" class="btn btn-xs btn-danger">删除</button>&nbsp;'
                  + '<button data-handle="tag" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">选择用户</button>&nbsp;';

              }
            }]
        });

        // 打标签
        self.$dataTable.on('click', '[data-handle="view"]', function (e) {
          var id = $(this).data('id');
          var name = $(this).data('name');
          var _$modal = $('#detailModal').clone();
          var $modal = _$modal.modal({
            height: 580,
            backdrop: 'static',
            keyboard: false
          });
          detailModal($modal, {'id': id, 'name': name});
          e.stopPropagation();
        });

        // 编辑按钮事件
        self.$dataTable.on('click', '[data-handle="edit"]', function (e) {
          var id = $(this).data('id');
          self.$http.get('/api/wx/tag/' + id).then(function (response) {
            var res = response.data;
            if (res.code == '1') {
              var tag = res.data;
              var _$modal = $('#createModal').clone();
              var $modal = _$modal.modal({
                height: 120,
                maxHeight: 450,
                backdrop: 'static',
                keyboard: false
              });
              createModal($modal, tag, true);
            }
          });
          e.stopPropagation();
        });

        // 删除按钮事件
        self.$dataTable.on('click', '[data-handle="delete"]', function (e) {
          var id = $(this).data('id');
          swal({
            title: '标签删除',
            text: '确定要删除该标签？',
            type: 'info',
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            showCancelButton: true,
            showConfirmButton: true,
            showLoaderOnConfirm: true,
            confirmButtonColor: '#ed5565',
            closeOnConfirm: false
          }, function () {
            self.$http.delete('/api/wx/tag/' + id).then(function (response) {
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

        // 选择用户打标签
        self.$dataTable.on('click', '[data-handle="tag"]', function (e) {
          var id = $(this).data('id');
          var _$modal = $('#tagModal').clone();
          var $modal = _$modal.modal({
            height: 550,
            backdrop: 'static',
            keyboard: false
          });
          tagModal($modal, id);
          e.stopPropagation();
        });
      },
      create: function () {
        var _$modal = $('#createModal').clone();
        var $modal = _$modal.modal({
          height: 120,
          maxHeight: 450,
          backdrop: 'static',
          keyboard: false
        });
        createModal($modal, {
          name: '',
          oid: ''
        }, false);
      }
    }
  });

  function createModal($el, model, isEdit) {
    // 获取 node
    var el = $el.get(0);
    // 创建 Vue 对象编译节点
    vueTag = new Vue({
      el: el,
      // 模式窗体必须引用 ModalMixin
      mixins: [RocoVueMixins.ModalMixin],
      $modal: $el, //模式窗体 jQuery 对象
      data: {
        //控制 按钮是否可点击
        disabled: false,
        //模型复制给对应的key
        tag: model,
        // 进项列表
        submitBtnClick: false
      },
      methods: {
        submit: function () {
          var self = this;
          self.submitBtnClick = true;
          self.$validate(true, function () {
            if (self.$validation.valid) {
              self.disabled = true;
              if (isEdit) {
                self.$http.put(ctx + '/api/wx/tag', self.tag)
                  .then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                      $el.on('hidden.bs.modal', function () {
                        vueIndex.$dataTable.bootstrapTable('refresh');
                        self.$toastr.success('更新成功');
                      });
                      $el.modal('hide');
                    }else{
                    	self.$toastr.error(res.message + "【标签名称已存在】");
                    }
                  }).finally(function () {
                  self.disabled = false;
                });
              } else {
                self.$http.post(ctx + '/api/wx/tag', self.tag)
                  .then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                      $el.on('hidden.bs.modal', function () {
                        vueIndex.$dataTable.bootstrapTable('refresh');
                        self.$toastr.success('创建成功');
                      });
                      $el.modal('hide');
                    }else{
                    	self.$toastr.error(res.message + "【标签名称已存在】");
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
    return vueTag;
  }

  // 选择用户打标签modal
  function tagModal($el, tagId) {
    // 获取 node
    var el = $el.get(0);
    var vueModal = new Vue({
      el: el,
      mixins: [RocoVueMixins.ModalMixin],
      $modal: $el,
      created: function () {
      },
      data: {
        form: {
          keyword: '',
          orgCode: '',
          employeeStatus: 'ON_JOB'
        },
        selectedIds: [], // 用户选择的员工id
        tagId: tagId, // 标签id
        orgData: null, // 机构树数据
        showOrgTree: false, // 是否显示机构树
        //控制 按钮是否可点击
        disabled: false,
        submitBtnClick: false
      },
      created: function () {
        var self = this;
        self.fetchOrgTree();
        self.fetchHasTagged();
      },
      ready: function () {
        this.drawTable();
      },
      methods: {
        // 获取已经打过标签的员工数据,回显用
        fetchHasTagged: function () {
          var self = this;
          this.$http.get('/api/wx/tag/' + self.tagId + '/employee')
            .then(function (response) {
              var res = response.data;
              if (res.code == 1) {
                return res.data;
              }
            }).then(function (hasTagged) {
            if (hasTagged && hasTagged.length > 0) {
              _.forEach(hasTagged, function (val) {
                self.selectedIds.push(val.employee.id);
              });
            }
          })
        },
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
          var self = this;
          self.showOrgTree = false;
        },
        // 选择机构时回调事件
        selectOrg: function (node) {
          var self = this;
          self.form.orgName = node.name;
          self.form.orgCode = node.familyCode;
        },
        query: function () {
          this.$dataTable.bootstrapTable('refresh', 1);
        },
        drawTable: function () {
          var self = this;
          self.$dataTable = $('#employeeDataTable', self._$el).bootstrapTable({
            url: '/api/employees/openid',
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
            columns: [{
              checkbox: true,
              align: 'center',
              width: '5%',
              checked: true,
              formatter: function (value, row) {
                // 回显
                var select = false;
                self.selectedIds.forEach(function (val) {
                  if (row.id == val) {
                    select = true;
                  }
                });
                return select;
              }
            }, {
              field: 'jobNum',
              title: '工号',
              align: 'center'
            }, {
              field: 'name',
              title: '姓名',
              align: 'center'
            }, {
              field: 'position',
              title: '职务',
              align: 'center'
            }, {
              field: 'mobile',
              title: '手机号',
              align: 'center'
            }, {
              field: 'org',
              title: '部门',
              align: 'center',
              formatter: function (org, row, index) {
                if (org && org.department && org.department.orgName) {
                  var s = '';
                  switch (org.type) {
                    case 'DIRECTLY':
                      s = ' (直属)';
                      break;
                    case 'PART_TIME':
                      s = ' (兼职)';
                      break;
                    default:
                      break;
                  }
                  s = org.department.orgName + s;
                  if (org.departmentPrincipal) {
                    s = '<font color="red">' + s + '</font>';
                  }
                  return s;
                }
                return '';
              }
            }, {
              field: 'org',
              title: '公司',
              align: 'center',
              formatter: function (org, row, index) {
                if (org && org.company && org.company.orgName) {
                  var s = '';
                  switch (org.type) {
                    case 'DIRECTLY':
                      s = ' (直属)';
                      break;
                    case 'PART_TIME':
                      s = ' (兼职)';
                      break;
                    default:
                      break;
                  }
                  s = org.company.orgName + s;
                  if (org.companyPrincipal) {
                    s = '<font color="red">' + s + '</font>';
                  }
                  return s;
                }
                return '';
              }
            }]
          });

          // checkbox的全选事件
          self.$dataTable.on('check-all.bs.table', function (row, data) {
            _.forEach(data, function (val) {
              // 先查找是否在selectedIds中已经有了，如果有了，那么不填添加到数组中
              var index = _.findIndex(self.selectedIds, function (id) {
                return id == val.id;
              });
              if (index == -1) {
                self.selectedIds.push(val.id);
              }
            });
          });

          // checkbox的全选取消事件
          self.$dataTable.on('uncheck-all.bs.table', function (row, data) {
            _.forEach(data, function (val) {
              // 先查找是否在selectedIds中已经有了，如果有了，那么移除
              var index = _.findIndex(self.selectedIds, function (id) {
                return id == val.id;
              });
              if (index != -1) {
                self.selectedIds.splice(index, 1);
              }
            });
          });

          // checkbox的选择事件
          self.$dataTable.on('check.bs.table', function (row, data) {
            self.selectedIds.push(data.id);
          });

          // checkbox 选择取消事件
          self.$dataTable.on('uncheck.bs.table', function (row, data) {
            var id = data.id;
            var index = _.findIndex(self.selectedIds, function (val) {
              return id == val;
            });
            if (index != -1) {
              self.selectedIds.splice(index, 1);
            }
          });
        },
        submit: function () {
          var self = this;
          self.submitBtnClick = true;
          self.disabled = true;
          self.$http.post(ctx + '/api/wx/tag/' + self.tagId + '/employee', self.selectedIds)
            .then(function (response) {
              var res = response.data;
              if (res.code == 1) {
                $el.on('hidden.bs.modal', function () {
                  // vueIndex.$dataTable.bootstrapTable('refresh');
                  self.$toastr.success('操作成功');
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
    return vueModal;
  }

  // 查看详情modal
  function detailModal($el, tag) {
    // 获取 node
    var el = $el.get(0);
    var vueModal = new Vue({
      el: el,
      mixins: [RocoVueMixins.ModalMixin],
      $modal: $el,
      created: function () {
      },
      data: {
        form: {
          keyword: '',
          orgCode: '',
          employeeStatus: 'ON_JOB'
        },
        tag: tag, // 标签
        orgData: null, // 机构树数据
        showOrgTree: false, // 是否显示机构树
        //控制 按钮是否可点击
        disabled: false,
        submitBtnClick: false
      },
      created: function () {
        var self = this;
        self.fetchOrgTree();
      },
      ready: function () {
        this.drawTable();
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
          var self = this;
          self.showOrgTree = false;
        },
        // 选择机构时回调事件
        selectOrg: function (node) {
          var self = this;
          self.form.orgName = node.name;
          self.form.orgCode = node.familyCode;
        },
        query: function () {
          this.$dataTable.bootstrapTable('refresh', 1);
        },
        drawTable: function () {
          var self = this;
          self.$dataTable = $('#detailDataTable', self._$el).bootstrapTable({
            url: '/api/wx/tag/' + self.tag.id + '/employee/list',
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
            maintainSelected: false, //维护checkbox选项
            columns: [{
              field: 'employee',
              title: '工号',
              align: 'center',
              formatter: function (val) {
                if (val) {
                  return val.jobNum || '';
                }
                return '';
              }
            }, {
              field: 'employee',
              title: '姓名',
              align: 'center',
              formatter: function (val) {
                if (val) {
                  return val.name || '';
                }
                return '';
              }
            }, {
              field: 'employee',
              title: '职务',
              align: 'center',
              formatter: function (val) {
                if (val) {
                  return val.position || '';
                }
                return '';
              }
            }, {
              field: 'employee',
              title: '手机号',
              align: 'center',
              formatter: function (val) {
                if (val) {
                  return val.mobile || '';
                }
                return '';
              }
            }, {
              field: 'employee',
              title: '部门',
              align: 'center',
              formatter: function (val) {
                var org = null;
                if (val) {
                  org = val.org || null;
                }
                if (org && org.department && org.department.orgName) {
                  var s = '';
                  switch (org.type) {
                    case 'DIRECTLY':
                      s = ' (直属)';
                      break;
                    case 'PART_TIME':
                      s = ' (兼职)';
                      break;
                    default:
                      break;
                  }
                  s = org.department.orgName + s;
                  if (org.departmentPrincipal) {
                    s = '<font color="red">' + s + '</font>';
                  }
                  return s;
                }
                return '';
              }
            }, {
              field: 'employee',
              title: '公司',
              align: 'center',
              formatter: function (val) {
                var org = null;
                if (val) {
                  org = val.org || null;
                }
                if (org && org.company && org.company.orgName) {
                  var s = '';
                  switch (org.type) {
                    case 'DIRECTLY':
                      s = ' (直属)';
                      break;
                    case 'PART_TIME':
                      s = ' (兼职)';
                      break;
                    default:
                      break;
                  }
                  s = org.company.orgName + s;
                  if (org.companyPrincipal) {
                    s = '<font color="red">' + s + '</font>';
                  }
                  return s;
                }
                return '';
              }
            }, {
              field: 'id',
              title: '操作',
              align: 'center',
              formatter: function (org, row) {
                return '<button data-handle="cancel" data-id="' + row.id + '" type="button" class="btn btn-xs btn-danger">取消</button>&nbsp;'
              }
            }]
          });

          // 取消标签事件
          self.$dataTable.on('click', '[data-handle="cancel"]', function (e) {
            var id = $(this).data('id');
            swal({
              title: '取消标签',
              text: '确定要将[' + tag.name + ']标签从该用户身上取消？',
              type: 'info',
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              showCancelButton: true,
              showConfirmButton: true,
              showLoaderOnConfirm: true,
              confirmButtonColor: '#ed5565',
              closeOnConfirm: false
            }, function () {
              self.$http.get('/api/wx/tag/' + id + '/untag').then(function (response) {
                var res = response.data;
                if (res.code == '1') {
                  self.$toastr.success('取消标签成功！');
                  self.$dataTable.bootstrapTable('refresh');
                }
              }).catch(function () {
                self.$toastr.error('系统异常');
              }).finally(function () {
                swal.close();
              });
            });
            e.stopPropagation();
          })
        }
      }
    });
    return vueModal;
  }
})();

