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
        self.$http.post(ctx + '/wx/scan/password', $.param(data)).then(function (res) {
          if (res.data.code == 1) {
            window.location.href = ctx + "/index";
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