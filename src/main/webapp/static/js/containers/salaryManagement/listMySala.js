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
          salaMonth:''
      },
      webUploaderSub: {
          type: 'sub',
          formData: {month: ''},
      },
      salaryDetail: '',
      name:'',
      account:'',
      selectedRows: {}, //选中列
      modalModel: null, //模式窗体模型
      _$el: null, //自己的 el $对象
      _$dataTable: null, //datatable $对象
      orgData: null, // 机构树数据
      showOrgTree: false // 是否显示机构树
    },
    created: function () {
      this.form.salaMonth = getLastMonth();//默认上个月时间
      this.webUploaderSub.formData.month = this.form.salaMonth;
      var id = this.$parseQueryString()['id'];
      //通过id获取我的工资单
      if(id == null) {
          this.fetchSalaryDetail();
      }
    },
    ready: function () {
        var self = this;
        var id = this.$parseQueryString()['id'];
        //通过id获取我的工资单
        if(id != null){
            self.$http.get('/api/rest/salarydetail/getSalaryDetailById?id=' + id).then(function (response) {
                var res = response.data;
                if (res.code == '1') {
                    self.salaryDetail = res.data[0];
                    if(self.salaryDetail.salaryPerformanceDn==null){
                        self.salaryDetail.salaryPerformanceDn='0';
                    }
                } else {
                    self.$toastr.error(res.message);
                }
            });
        }
        this.activeDatepicker();
    },
    watch: {
        'form.salaMonth': function (value) {
            this.webUploaderSub.formData.month = value;
        }
    },
    methods: {
      // 获取我的工资数据
      fetchSalaryDetail: function () {
          var fUser = window.RocoUser;
          var self = this;
          this.$http.get('/api/rest/salarydetail/getSalaryDetailByEmpId?empId='+fUser.userId+'&salaMonth='+self.form.salaMonth).then(function (response) {
              var res = response.data;
              if (res.code == 1) {
                  self.salaryDetail = res.data[0];
                  if(self.salaryDetail==null){
                      self.name = fUser.name;
                      self.account = fUser.account;
                  }else{
                      if(self.salaryDetail.salaryPerformanceDn==null){
                          self.salaryDetail.salaryPerformanceDn='0';
                      }
                  }
              }
          })
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
})(RocoUser);

