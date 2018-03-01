+(function () {
  var login = new Vue({
    el: '#loginCont',
    http: {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    },
    data: {
      form: {
    	mobile: '',
    	password: ''
      },
      submitting: false
    },
    methods: {
      submit: function () {
	    var self = this;
		  //校验手机号格式
    	var mobile = self.form.mobile;
//		if(! mobile.match("^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,2,5-9])|(177))\\d{8}$")){
//			self.$toastr.error("请输入有效的手机号码");
//			return;
//		}
		
		var data = {
			mobile: self.form.mobile,
			password: self.form.password
		};
		self.submitting = true;
		self.$http.post(ctx + '/api/crm/crmBusiness/checkLogin', $.param(data)).then(function (response) {
			  var res = response.data;
			  if (res.code == 1) {
			    window.location.href = ctx + "/oldCrm/list";
		      } else {
		    	 //alert(res.message);
		    	 self.$toastr.error(res.message);
		      }
		    }).catch(function () {
		
		    }).finally(function () {
		      self.submitting = false;
		    });
		  }
    },
    created: function () {
    	
    },
    ready: function () {
    }
  });
})();