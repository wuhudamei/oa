var vueIndex = null;
+(function () {
    $('#regulationMenu').addClass('active');
  vueIndex = new Vue({
    el: '#container',
    data: {
      // 面包屑
      breadcrumbs: [{
        path: '/',
        name: '主页'
      }, {
        path: '/',
        name: '查看规章制度',
        active: true //激活面包屑的
      }],
      regulation: null,
      submitBtnClick: false,
      _$eduDataTable: null,
      _$workDataTable: null
    },

    created: function () {
    },
    ready: function () {
      var self = this;
      var id = this.$parseQueryString()['id'];
      self.$http.get('/api/regulations/detail/' + id).then(function (response) {
        var res = response.data;
        if (res.code == '1') {
          self.regulation = res.data;

        } else {
          self.$toastr.error(res.message);
        }
      });
      self.initDatePicker();

    },
    methods: {
      cancel: function () {
        window.history.go(-1);
      }
    }
  });
})();