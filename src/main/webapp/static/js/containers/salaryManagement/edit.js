var vueIndex = null;
    +(function () {
  $('#salaryManagement').addClass('active');
  $('#listPayroll').addClass('active');
  vueIndex = new Vue({
    el: '#container',
    data: {
      // 面包屑
      breadcrumbs: [{
        path: '/',
        name: '主页'
      }, {
        path: '/',
        name: '编辑员工',
        active: true //激活面包屑的
      }],
      salaryDetail:{
          name:'',
          orgCode:'',
          idNum:'',
          orgCompanyName:'',
          orgDepartmentName:'',
          position:'',
          bankOfDeposit:'',
          creditCardNumbers:'',
          shouldWorkDays:'',
          practicalWorkDays:'',
          salaryBasic:'',
          salaryBasicDn:'',
          mealSubsidy:'',
          mealSubsidyDn:'',
          otherSubsidy:'',
          otherSubsidyDn:'',
          compensation:'',
          compensationDn:'',
          socialSecurityPersonal:'',
          socialSecurityPersonalDn:'',
          housingFund:'',
          housingFundDn:'',
          deductMoney:'',
          deductMoneyDn:'',
          shouldBasicSalary:'',
          shouldBasicSalaryDn:'',
          salaryTaxable:'',
          salaryTaxableDn:'',
          individualIncomeTax:'',
          individualIncomeTaxDn:'',
          practicalSalaryTotal:'',
          practicalSalaryTotalDn:''
      },
      mealSubsidyBaseDn:'',
      salaryBaseDetail:null,
      orgData: null, // 机构树数据
      orgName: null, // 机构名称回显
      showOrgTree: false, // 是否显示机构树
      submitBtnClick: false,
      _$eduDataTable: null,
      _$workDataTable: null,
      disabled: false
    },
     validators: {
          num: {
              message: '请输入正确的金额格式，例（10或10.00）',
              check: function (val) {
                  // /^([0-9]+|[0-9]{1,3}(,[0-9]{3})*)(.[0-9]{1,2})?$/
                  return /^([1-9]\d{0,9}|0)([.]?|(\.\d{1,2})?)$/.test(val);
              }
          },
          days: {
              message: '请输入正确的天数，例（1-31）',
              check: function (val) {
                  return (/^([1-9]|[12]\d|3[01])$/i).test(val);
              }
          },
         days2: {
             message: '请输入正确的天数，例（1-31）',
             check: function (val) {
                 return (/^([0-9]|[12]\d|3[01])$/i).test(val);
             }
         },
          card: {
              message: '请输入正确的银行卡号，银行卡号长度为16位或19位',
              check: function (val) {
                  return (/^([1-9]{1})(\d{15}|\d{18})$/).test(val);
              }
          }
    },
    created: function () {
        this.getSalaryDetail();
    },
    ready: function () {

       // this.getSalaryBaseData();
        //this.getSalaryBaseData();
    },
    methods: {
      getSalaryDetail:function(){
          //根据id回显数据
          var self = this;
          var id = this.$parseQueryString()['id'];
          var empId = this.$parseQueryString()['empId'];
          self.$http.get('/api/rest/salarydetail/getSalaryDetailById?id=' + id+'&empId='+empId)
              .then(function (response) {
                  var res = response.data;
                  if (res.code == '1') {
                      self.salaryDetail = res.data[0];
                      /*if(self.salaryDetail!=null){
                          self.salaryDetail.mealSubsidyDn = self.mealSubsidyBaseDn;
                      }*/
                  } else {
                      self.$toastr.error(res.message);
                  }
              })
      },
      /*getSalaryBaseData: function(){
          //根据empid回显数据
          var self = this;
          var empId = this.$parseQueryString()['empId'];
          self.$http.get('/api/rest/salarydetail/findAllByUpMonth?empId=' + empId)
              .then(function (response) {
                  var res = response.data;
                  if (res.code == '1') {
                      self.salaryBaseDetail = res.data[0];
                      if(self.salaryBaseDetail!=null){
                          self.mealSubsidyBaseDn = self.salaryBaseDetail.mealSubsidyDn;
                      }else{
                          self.mealSubsidyBaseDn = 0;
                      }
                  } else {
                      self.$toastr.error(res.message);
                  }
              });


      },
          // this.getSalaryDetail();
      },*/

      submit: function () {
        var self = this;
        self.submitBtnClick = true;
        self.$validate(true, function () {
          if (self.$validation.valid) {
            self.disabled = true;
              self.salaryDetail.salaryBasic = self.salaryDetail.salaryBasicDn;
              self.salaryDetail.mealSubsidy = self.salaryDetail.mealSubsidyDn;
              self.salaryDetail.otherSubsidy = self.salaryDetail.otherSubsidyDn;
              self.salaryDetail.compensation = self.salaryDetail.compensationDn;
              self.salaryDetail.socialSecurityPersonal = self.salaryDetail.socialSecurityPersonalDn;
              self.salaryDetail.housingFund = self.salaryDetail.housingFundDn;
              self.salaryDetail.deductMoney = self.salaryDetail.deductMoneyDn;
              self.salaryDetail.shouldBasicSalary = self.salaryDetail.shouldBasicSalaryDn;
              self.salaryDetail.salaryTaxable = self.salaryDetail.salaryTaxableDn;
              self.salaryDetail.individualIncomeTax = self.salaryDetail.individualIncomeTaxDn;
              self.salaryDetail.practicalSalaryTotal = self.salaryDetail.practicalSalaryTotalDn;
              self.salaryDetail.practicalWorkDays = self.salaryDetail.practicalWorkDays;
              var datas = self.salaryDetail;
            self.$http.put(ctx + '/api/rest/salarydetail/update', datas).then(function (response) {
              var res = response.data;
              if (res.code == 1) {
                  self.$toastr.success("更新成功!");
                  setTimeout(function () {
                      window.location.href = ctx + '/admin/salaryManagement/listPayroll';
                  }, 800)
              } else {
                self.$toastr.error(res.message);
                self.disabled = false;
              }
            })
          }
        });
      },
      //如果传空串，spring反序列化json串时，遇到枚举会报错，所以只传有值得属性
      filterNull: function (data) {
        var t = {};
        if (data) {
            for (var k in data) {
                var v = data[k];
                if (v) {
                    t[k] = v;
                }
            }
        }
        return t;
     },
      cancel: function () {
        window.history.go(-1);
      }
    }
  });
        vueIndex.$watch('mealSubsidyBaseDn',function () {
            vueIndex.getSalaryDetail();
        })
})();