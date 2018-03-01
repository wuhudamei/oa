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
        code: ''
      },
      submitting: false
    },
    methods: {
      submit: function () {
        var self = this;
        var data = {
          username: self.form.code
        };
        self.submitting = true;
        self.$http.post(ctx + '/wx/scan/bind', $.param(data)).then(function (response) {
          var res = response.data;
          if (res.code == '1') {
            window.location.href = ctx + "/wx/scan/modifyPassword";
          } else {
            alert(res.message);
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