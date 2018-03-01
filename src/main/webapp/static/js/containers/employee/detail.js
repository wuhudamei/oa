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
        name: '查看员工',
        active: true //激活面包屑的
      }],
      employee: null,
      submitBtnClick: false,
      _$eduDataTable: null,
      _$workDataTable: null
    },
    filters: {
      // 是否负责人过滤器
      'principal-filter': function (val) {
        if (val)
          return '是';
        return '否';
      },
      // 性别过滤器
      'gender-filter': function (val) {
        if (val == 'MALE')
          return '男';
        if (val == 'FEMALE')
          return '女';
        return '';
      },
      // 户口性质过滤器
      'censusnature-filter': function (val) {
        var s = '';
        switch (val) {
          case 'BJ_CITY':
            s = '北京城镇';
            break;
          case 'BJ_COUNTRY':
            s = '北京农村';
            break;
          case 'OTHER_CITY':
            s = '外地城镇';
            break;
          case 'OTHER_COUNTRY':
            s = '外地农村';
            break;
          default:
            break;
        }
        return s;
      },
      // 婚姻状态过滤器
      'maritalstatus-filter': function (val) {
        if (val == 'MARRIED')
          return '已婚';
        if (val == 'UNMARRIED')
          return '未婚';
        return '';
      },
      // 英语水平过滤器
      'englishlevel-filter': function (val) {
        var s = '';
        switch (val) {
          case 'MASTER':
            s = '精通';
            break;
          case 'PROFICIENCY':
            s = '熟练';
            break;
          case 'GENERAL':
            s = '一般';
            break;
          case 'BASE':
            s = '基本';
            break;
          default:
            break;
        }
        return s;
      },
      // 员工状态过滤器
      'employeeStatus-filter': function (val) {
        if (val == 'ON_JOB')
          return '在职';
        if (val == 'DIMISSION')
          return '离职';
        return '';
      },
      // 用工类型过滤器
      'types-filter': function (val) {
        var s = '';
        if (val && val.length > 0) {
          _.forEach(val, function (item) {
            switch (item) {
              case 'PRACTICE':
                s += '实习学生，';
                break;
              case 'SOLDIER':
                s += '在役军人，';
                break;
              case 'DISABLED':
                s += '残疾人，';
                break;
              case 'RETIRE':
                s += '退休，';
                break;
              case 'FOREIGN':
                s += '外籍及港澳台人员，';
                break;
              case 'OVERAGE':
                s += '超龄人员，';
                break;
              case 'INSURANCE_IN_ORIGIN':
                s += '保险关系在原单位，';
                break;
              case 'INSURANCE_IN_TALENT':
                s += '保险关系在人才或职介，';
                break;
              case 'OTHER':
                s += '其他，';
                break;
              default:
                break;
            }
          });
        }
        if (s) {
          s = s.substring(0, s.length - 1);
        }
        return s;
      }
    },
    created: function () {
    },
    ready: function () {
      var self = this;
      var id = this.$parseQueryString()['id'];
      var department = this.$parseQueryString()['department'];
      var company = this.$parseQueryString()['company'];
      self.$http.get('/api/employees/' + id).then(function (response) {
        var res = response.data;
        if (res.code == '1') {
          self.employee = res.data;
          self.employee.department = department;
          self.employee.company = company;
          self.employee.photo = res.data.photo || '/static/img/default_photo.jpeg';
          // 渲染教育经历和工作经历的table
          self.drawEduDataTable();
          self.drawWorkDataTable();
        } else {
          self.$toastr.error(res.message);
        }
      });
      self.initDatePicker();

    },
    methods: {
      // 初始化时间选择器
      initDatePicker: function () {
        $(this.$els.entryDate).datetimepicker({
          minView: 2,
          format: 'yyyy-mm-dd',
          todayBtn: true
        });
        $(this.$els.fillDate).datetimepicker({
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
          }]
        });
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
          }]
        });
      },
      cancel: function () {
        window.history.go(-1);
      }
    }
  });
})();