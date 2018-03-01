/**
 * Created by aaron on 2017/3/9.
 */
+(function () {
  $('#empManagerMenu').addClass('active');
  $('#employeeList').addClass('active');
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
              align: 'center',
              formatter: function (value, row) {
            	  return '<a href="tel:' + value + '">' + value + '</a>';
              }
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
            }, {
              field: 'email',
              title: "邮箱",
              align: 'center'
            }]
        });

        self.$dataTable.on('click', '[data-handle="view"]', function (e) {
          var id = $(this).data("id");
          var department = $(this).data("department");
          var company = $(this).data("company");
          self.$http.get('/api/employees/' + id).then(function (response) {
            var res = response.data;
            if (res.code == '1') {
              var _$modal = $('#modal').clone();
              var $modal = _$modal.modal({
                height: 450,
                maxHeight: 500,
                backdrop: 'static',
                keyboard: false
              });
              var employee = res.data;
              employee.department = department;
              employee.company = company;
              employee.photo = employee.photo || '/static/img/default_photo.jpeg';
              modal($modal, employee);
            } else {
              self.$toastr.error(res.message);
            }
          });
          e.stopPropagation();
        });
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

  function modal($el, model) {
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
        submitBtnClick: false
      },
      filters: {
        // 性别过滤器
        'gender-filter': function (val) {
          console.log(val)
          if (val == 'MALE')
            return '男';
          if (val == 'FEMALE')
            return '女';
          return '';
        }
      }
    });
    // 创建的Vue对象应该被返回
    return vueModal;
  }

})();

