/**
 * Created by aaron on 2017/3/9.
 */
+(function () {
  $('#salaryManagement').addClass('active');
  $('#listPayroll').addClass('active');
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
        name: '工资管理',
        active: true //激活面包屑的
      }],
      // 查询表单
      form: {
        keyword: '',
        orgCode: '',
        orgName: '',
        salaMonth: ''
      },
      webUploaderSub: {
          type: 'sub',
          formData: {month: ''},
      },
      selectedRows: {}, //选中列
      _$el: null, //自己的 el $对象
      _$dataTable: null, //datatable $对象
      orgData: null, // 机构树数据
      showOrgTree: false // 是否显示机构树
    },
    created: function () {
      this.fetchOrgTree();
      this.form.salaMonth = getLastMonth();//默认上个月时间
      this.webUploaderSub.formData.month = this.form.salaMonth;

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
      this.activeDatepicker();
    },
    watch: {
        'form.salaMonth': function (value) {
            this.webUploaderSub.formData.month = value;
        }
    },
    methods: {
        //生成工资单
        salary:function () {
            var self = this;
            self.$http.get('/api/rest/salarydetail/salary').then(function (res) {
                var res = res.data;
                if (res.code == 1) {
                    vueIndex.$dataTable.bootstrapTable('refresh');
                }
            })
        },
      //导出报表
      exportBill: function () {
          var self = this;
          window.location.href = '/api/rest/salarydetail/export?salaMonth=' + self.form.salaMonth;
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
          url: '/api/rest/salarydetail/list',
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
              title: '序号',//标题  可不加 
              align: 'center',  
              formatter: function (value, row, index) {
                  return index+1;
              }
            },
            {
              field: 'orgCode',
              title: '工号',
              align: 'center',
            }, {
              field: 'empName',
              title: '姓名',
              align: 'center',
            }, {
              field: 'workCoefficient',
              title: '出勤系数',
              align: 'center'
            }, {
              field: 'salaryBasicDn',
              title: '基本工资',
              align: 'center'
            }, {
              field: 'mealSubsidyDn',
              title: '餐补',
              align: 'center',
            }, {
              field: 'otherSubsidyDn',
              title: '其他补助',
              align: 'center',
            }, {
              field: 'socialSecurityPersonalDn',
              title: "社保个人扣款",
              align: 'center'
            }, {
              field: 'housingFundDn',
              title: "住房公积金",
              align: 'center'
            }, {
              field: 'individualIncomeTaxDn',
              title: "个人所得税",
              align: 'center'
            }, {
              field: 'practicalSalaryTotalDn',
              title: "实发工资",
              align: 'center'
            },{
              field: 'id', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
              title: "操作",
              align: 'center',
              formatter: function (value, row, index) {
                var fragment = '';
                if (RocoUtils.hasPermission('emp:edit'))
                  fragment += '<button data-handle="edit" data-id="' + row.id + '" data-empid="' + row.empId + '" type="button" class="btn btn-xs btn-default">编辑</button>&nbsp;';
                if (RocoUtils.hasPermission('emp:view'))
                  fragment += '<button data-handle="view" data-id="' + row.id + '" data-empid="' + row.empId + '" data-monthsalary="' + row.monthSalary + '" type="button" class="btn btn-xs btn-default">详情</button>&nbsp;';

                return fragment;
              }
            }]
        });

        //查看事件
        self.$dataTable.on('click', '[data-handle="view"]', function (e) {
          var id = $(this).data('id');
          var empId = $(this).data('empid');
          var monthSalary = $(this).data('monthsalary');
          window.location.href = ctx + '/admin/salaryManagement/listDetails?id=' + id +'&empId='+ empId +'&monthSalary='+monthSalary;
          e.stopPropagation();
        });

        // 编辑事件
        self.$dataTable.on('click', '[data-handle="edit"]', function (e) {
          var id = $(this).data('id');
          var empId = $(this).data('empid');
          window.location.href = ctx + '/admin/salaryManagement/edit?id=' + id +'&empId='+ empId;
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
      },
      activeDatepicker: function () {
          var self = this;
          $(self.$els.salaMonth).datetimepicker({
              clearBtn:true,
              startView: 3,//启始视图显示年视图
              minView: "year", //选择日期后，不会再跳转去选择时分秒
              format: 'yyyy-mm',
              todayBtn: true
          });
      }
    }
  });
  /**
   * 获取上月日期
   * @returns
   */
  function getLastMonth() {
      return moment().add('month', -1).format('YYYY-MM');
  }
})();

