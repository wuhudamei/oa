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
        newPassword: '',
        confirmPassword: ''
      },
      submitting: false
    },
    methods: {
      submit: function () {
        var self = this;
        var data = {
          newPassword: self.form.newPassword,
          confirmPassword: self.form.confirmPassword
        };
        self.submitting = true;
        self.$http.post(ctx + '/wx/click/password', $.param(data)).then(function (response) {
          var res = response.data;
          if (res.code == 1) {
        	var tmpData = res.data;
        	var tmpUrl = tmpData.substring(tmpData.indexOf(":")+1,tmpData.length);
            window.location.href = ctx + tmpUrl;
          } else {
            Vue.toastr.error(res.data.message);
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