/**
 * Created by aaron on 2017/3/9.
 */
+(function () {
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
        name: '员工管理',
        active: true //激活面包屑的
      }],
      // 查询表单
      form: {
        keyword: '',
        orgCode: '',
        orgName: '',
        employeeStatus: 'ON_JOB'
      },
      selectedRows: {}, //选中列
      modalModel: null, //模式窗体模型
      _$el: null, //自己的 el $对象
      _$dataTable: null, //datatable $对象
      orgData: null, // 机构树数据
      showOrgTree: false // 是否显示机构树
    },
    created: function () {
      this.fetchOrgTree();
    },
    ready: function () {
      var self = this;
      if (orgName && orgFamilyCode) {
        self.form.orgName = orgName;
        self.form.orgCode = orgFamilyCode;
      }
      if (!self.form.orgName || self.form.orgName == 'null') {
        self.form.orgName = '';
      }
      self.drawTable();
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
        self.form.orgName = node.name;
        self.form.orgCode = node.familyCode;
      },
      query: function () {
        this.$dataTable.bootstrapTable('refresh', {pageNumber: 1});
      },
      drawTable: function () {
        var self = this;
        self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
          url: '/api/employees',
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
              field: 'orgCode',
              title: '工号',
              align: 'center',
              formatter: function (value, row) {
                var department = self.getDepartmentName(row.org);
                var company = self.getCompanyName(row.org);
                return '<a href="javascript:void(0)" data-handle="view" data-id="' + row.id + '" data-department="' + department + '" data-company="' + company + '">' + value + '</a>';
              }
            }, {
              field: 'name',
              title: '姓名',
              align: 'center',
              formatter: function (value, row) {
                var department = self.getDepartmentName(row.org);
                var company = self.getCompanyName(row.org);
                return '<a href="javascript:void(0)" data-handle="view" data-id="' + row.id + '" data-department="' + department + '" data-company="' + company + '">' + value + '</a>';
              }
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
                console.log(row)
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
              field: 'email',
              title: "邮箱",
              align: 'center'
            }, {
              field: 'id', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
              title: "操作",
              align: 'center',
              formatter: function (value, row, index) {

                var s = '';
                if (row.employeeStatus == 'ON_JOB') {
                  if (RocoUtils.hasPermission('emp:dismission'))
                    s = '<button data-handle="dimission" data-id="' + row.id + '" type="button" class="btn btn-xs btn-danger">离职</button>&nbsp;';
                }

                var deptId = '';
                var deptName = '';
                var cmpId = '';
                var cmpName = '';
                if (row.org) {
                  if (row.org.department) {
                    var deptId = row.org.department.id || '';
                    var deptName = row.org.department.orgName || '';
                  }
                  if (row.org.company) {
                    var cmpId = row.org.company.id || '';
                    var cmpName = row.org.company.orgName || '';
                  }
                }

                var fragment = '';
                if (RocoUtils.hasPermission('emp:edit'))
                  fragment += '<button data-handle="edit" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">编辑</button>&nbsp;';
                if (RocoUtils.hasPermission('emp:part'))
                  fragment += '<button data-handle="hold" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">兼职</button>&nbsp;';
                if (RocoUtils.hasPermission('emp:role'))
                  fragment += '<button data-handle="roles" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">设置科目角色</button>&nbsp;';
                if (RocoUtils.hasPermission('emp:contract'))
                  fragment += '<button data-handle="contract" data-id="' + row.id + '" data-name="' + row.name + '" type="button" class="btn btn-xs btn-default">合同</button>&nbsp;';
                if (RocoUtils.hasPermission('emp:salarydetail'))
                  fragment += '<button data-handle="salarydetail" data-orgcode="' + row.orgCode + '"  data-id="' + row.id + '" data-name="' + row.name + '" type="button" class="btn btn-xs btn-default">薪资</button>&nbsp;';
                if (RocoUtils.hasPermission('emp:department'))
                  fragment += '<button data-handle="dept-principal" data-org="' + deptId + '" data-id="' + row.id + '" data-name="' + deptName + '" type="button" class="btn btn-xs btn-default">部门负责人</button>&nbsp;';
                if (RocoUtils.hasPermission('emp:company'))
                  fragment += '<button data-handle="cmp-principal" data-org="' + cmpId + '" data-id="' + row.id + '" data-name="' + cmpName + '" type="button" class="btn btn-xs btn-default">公司负责人</button>&nbsp;';
                if (RocoUtils.hasPermission('emp:resetPw'))
                  fragment += '<button data-handle="reset" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">重置密码</button>&nbsp;';
                if (RocoUtils.hasPermission('emp:delete'))
                  fragment += '<button data-handle="delete" data-id="' + row.id + '" type="button" class="btn btn-xs btn-danger">删除</button>&nbsp;';
                if(row.employeeStatus == 'DIMISSION'){
                    fragment += '<button data-handle="edit" data-id="' + row.id + '" type="button" class="btn btn-xs btn-danger">再入职</button>&nbsp;';
                }
                return fragment + s;
              }
            }]
        });

        //查看事件
        self.$dataTable.on('click', '[data-handle="view"]', function (e) {
              var id = $(this).data('id');
              var department = $(this).data("department");
              var company = $(this).data("company");
              var param = '?id=' + id + '&department=' + encodeURIComponent(department) + '&company=' + encodeURIComponent(company);
              window.location.href = ctx + '/admin/employee/view/detail' + param;
              e.stopPropagation();
          });

        // 编辑事件
        self.$dataTable.on('click', '[data-handle="edit"]', function (e) {
          var id = $(this).data('id');
          window.location.href = ctx + '/admin/employee/edit?id=' + id;
          e.stopPropagation();
        });

        // 兼职事件
        self.$dataTable.on('click', '[data-handle="hold"]', function (e) {
          var id = $(this).data('id');
          var _$modal = $('#modal').clone();
          var $modal = _$modal.modal({
            height: 450,
            maxHeight: 450,
            backdrop: 'static',
            keyboard: false
          });
          modal($modal, {
            id: id
          }, false, self);
          e.stopPropagation();
        });

        // 设置角色
        self.$dataTable.on('click', '[data-handle="roles"]', function (e) {
          var id = $(this).data('id');
          var _$modal = $('#rolesModal').clone();
          var $modal = _$modal.modal({
            height: 450,
            maxHeight: 450,
            backdrop: 'static',
            keyboard: false
          });
          rolesModal($modal, id);
        });

        // 查看合同
        self.$dataTable.on('click', '[data-handle="contract"]', function (e) {
          var id = $(this).data('id');
          var name = $(this).data('name');
          window.location.href = ctx + '/admin/employee/contract?id=' + id + '&name=' + name;
          e.stopPropagation();
        });
          // 查看基本薪资维护
          self.$dataTable.on('click', '[data-handle="salarydetail"]', function (e) {
              var orgCode = $(this).data('orgcode');
              var name = $(this).data('name');
              var id = $(this).data('id');
              window.location.href = ctx + '/admin/employee/salarydetail?orgCode=' + orgCode + '&name=' + name+ '&id=' + id;
              e.stopPropagation();
          });
        // 重置密码事件
        self.$dataTable.on('click', '[data-handle="reset"]', function (e) {
          var id = $(this).data('id');
          swal({
            title: '重置密码',
            text: '确定要将该员工密码重置为默认密码？',
            type: 'info',
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            showCancelButton: true,
            showConfirmButton: true,
            showLoaderOnConfirm: true,
            confirmButtonColor: '#ed5565',
            closeOnConfirm: false
          }, function () {
            self.$http.get('/api/employees/' + id + '/password').then(function (res) {
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
          e.stopPropagation();
        });

        // 指定部门负责人
        self.$dataTable.on('click', '[data-handle="dept-principal"]', function (e) {
          var id = $(this).data('id');
          var org = $(this).data('org');
          var name = $(this).data('name');
          swal({
            title: '指定部门负责人',
            text: '确定要将该员工指定为' + name + '负责人？(原部门负责人将被撤销)',
            type: 'info',
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            showCancelButton: true,
            showConfirmButton: true,
            showLoaderOnConfirm: true,
            confirmButtonColor: '#ed5565',
            closeOnConfirm: false
          }, function () {
            self.$http.post('/api/employees/principal/department', {
              'empId': id,
              'departmentId': org
            }, {emulateJSON: true}).then(function (res) {
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
          e.stopPropagation();
        });

        // 指定公司负责人
        self.$dataTable.on('click', '[data-handle="cmp-principal"]', function (e) {
          var id = $(this).data('id');
          var org = $(this).data('org');
          var name = $(this).data('name');
          swal({
            title: '指定公司负责人',
            text: '确定要将该员工指定为' + name + '负责人？(原公司负责人将被撤销)',
            type: 'info',
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            showCancelButton: true,
            showConfirmButton: true,
            showLoaderOnConfirm: true,
            confirmButtonColor: '#ed5565',
            closeOnConfirm: false
          }, function () {
            self.$http.post('/api/employees/principal/company', {
              'empId': id,
              'companyId': org
            }, {emulateJSON: true}).then(function (res) {
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
          e.stopPropagation();
        });

        // 删除事件
        self.$dataTable.on('click', '[data-handle="delete"]', function (e) {
          var id = $(this).data('id');
          swal({
            title: '删除员工',
            text: '确定删除该员工么？',
            type: 'info',
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            showCancelButton: true,
            showConfirmButton: true,
            showLoaderOnConfirm: true,
            confirmButtonColor: '#ed5565',
            closeOnConfirm: false
          }, function () {
            self.$http.delete('/api/employees/' + id).then(function (res) {
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
          e.stopPropagation();
        });

        // 离职事件
        self.$dataTable.on('click', '[data-handle="dimission"]', function (e) {
          var id = $(this).data('id');
          swal({
            title: '员工离职',
            text: '确定要将该员工状态置为离职吗？',
            type: 'info',
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            showCancelButton: true,
            showConfirmButton: true,
            showLoaderOnConfirm: true,
            confirmButtonColor: '#ed5565',
            closeOnConfirm: false
          }, function () {
            self.$http.get('/api/employees/' + id + '/dimission').then(function (res) {
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
          e.stopPropagation();
        });
      },
      createEmployee: function () {
        return window.location.href = '/admin/employee/create';
      },
      getDepartmentName: function (org) {
        var s = '';
        if (org && org.department && org.department.orgName) {
          s = org.department.orgName;
        }
        return s;
      },
      getCompanyName: function (org) {
        var s = '';
        if (org && org.company && org.company.orgName) {
          s = org.company.orgName;
        }
        return s;
      }
    }
  });

  // 选择兼职机构
  function modal($el, model, isEdit, parent) {
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
        employee: model,
        orgData: null, // 机构树数据
        showOrgTree: true, // 是否显示机构树
        checkedData: [],
        submitBtnClick: false
      },
      created: function () {
        this.fetchOrgTree();
      },
      ready: function () {
      },
      methods: {
        // 获取机构选择树的数据
        fetchOrgTree: function () {
          var self = this;
          this.$http.get('/api/employees/' + self.employee.id + '/org/tree').then(function (response) {
            var res = response.data;
            if (res.code == 1) {
              return res.data;
            }
          }).then(function (orgData) {
            self.orgData = orgData;
            // 获取以前已经兼职的机构
            _.forEach(orgData, function (item) {
              if (item.checked) {
                self.checkedData.push({'id': item.id, 'pid': item.pId, 'type': item.type});
              }
            });
          });
        },
        // 选择机构时回调事件
        changeOrg: function (node) {
          var self = this;
          var id = node.id;
          var pid = node.pId;
          var type = node.type;
          // 如果是选中，添加到checkData中
          if (node.checked) {
            self.checkedData.push({'id': id, 'pid': pid, 'type': type});
          } else { // 如果是取消选中，则从checkData中移除
            var index = _.findIndex(self.checkedData, function (val) {
              return val.id == id;
            });
            if (index != -1) {
              self.checkedData.splice(index, 1);
            }
          }
        },
        submit: function () {
          var self = this;
          self.submitBtnClick = true;
          self.disabled = true;
          self.$http.post(ctx + '/api/employees/' + self.employee.id + '/org', self.checkedData).then(function (response) {
            var res = response.data;
            if (res.code == '1') {
              $el.on('hidden.bs.modal', function () {
                self.$toastr.success('保存成功');
              });
              $el.modal('hide');
            } else {
              self.disabled = true;
            }
          }).catch(function () {

          }).finally(function () {
            self.disabled = false;
            self.submitBtnClick = false;
          });
        }
      }
    });

    // 创建的Vue对象应该被返回
    return vueModal;
  }

  //设置员工角色
  function rolesModal($el, id) {
    //获取node
    var el = $el.get(0);

    //创建Vue对象编译节点
    var vueModal = new Vue({
        el: el,
        minxins: [RocoVueMixins.ModalMixin],
        $modal: $el, //模式窗体 jQuery 对象
        data: {
          roles: [],
          selectedRoles: {}
        },
        created: function () {
          this.getRoles(id);
        },
        ready: function () {
        },
        methods: {
          //查询用户角色信息
          getRoles: function (id) {
            var self = this;
            self.$http.get('/api/users/role/' + id).then(function (res) {
              if (res.data.code == 1) {
                self.roles = res.data.data;
                self.setCheckedRole();//将该用户已有的角色添加到选中角色中
              }
            }).catch(function () {

            }).finally(function () {

            });
          },
          setCheckedRole: function () {
            var self = this;
            if (self.roles) {
              $(self.roles).each(function (index, _this) {
                if (_this.checked == true) {
                  self.selectedRoles[_this.id] = _this.id;
                }
              });
            }
          },
          // 查询组织架构
          checkSub: function (role, e) {
            var self = this;
            var checked = e.target.checked;
            if (checked) {
              self.selectedRoles[role.id] = role.id;
            } else {
              self.selectedRoles[role.id] = null;
            }
          },
          submit: function () {
            var self = this;
            var roles = [];
            for (var key in self.selectedRoles) {
              if (self.selectedRoles[key]) {
                roles.push(self.selectedRoles[key]);
              }
            }
            if (roles.length == 0) {
              Vue.toastr.warning('请至少选择一个角色');
              return false;
            }
            var data = {
              userId: id,
              roles: roles
            }
            self.$http.post('/api/users/userrole', data, {emulateJSON: true}).then(function (res) {
              if (res.data.code == 1) {
                self.$toastr.success('操作成功');
                $el.modal('hide');
                self.$destroy();
              }
            }).finally(function () {
              self.disabled = false;
            });
          }
        }
      }
    );
    //创建的vue对象应该被返回
    return vueModal;
  }
})();

