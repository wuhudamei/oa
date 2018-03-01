var vueIndex = null; +
(function (RocoUtils) {

//  var httpUrl = 'http://103.249.255.142:8013';
  // var httpUrl = 'http://report.rocozxb.cn';
  vueIndex = new Vue({
    el: '#container',
    data: {
      url: '',
      date:null,
      small: 'getSmallBookingDetail',
      large:'getMarketingBigDataDetailsByDate',
      pieData:[],
      params:{}
    },
    methods: {
      goBack:function(){
        window.history.go(-1)
      },
      backList:function(){
        window.location.href = '/api/reports/list'
      },
      // 日饼状图数组
      fetchDay: function (type) {
        var self = this;
        this.$http.get(ctx + '/api/reports/signReq', {
          params: {
            aimUrl:httpUrl + '/api/GatherCustomerReport/' + this[type],
            startTime: this.params.startTime,
            endTime: this.params.endTime,
          }
        }).then(function (res) {
          if (res.data.code == 1) {
            self.pieData =JSON.parse(res.data.data).details;
          }
        })
      }
    },
    created: function () {
      this.params = this.$parseQueryString();
      this.date = moment(this.params.startTime).format("YYYY-MM-DD") === moment(this.params.endTime).format("YYYY-MM-DD") ? moment(this.params.endTime).format("YYYY-MM-DD") : moment(this.params.endTime).format("YYYY-MM")
      // this.url = this.params.type === 'small' ? '线索url' : '进店'
    },
    ready: function () {
      this.fetchDay(this.params.type)
    }
  })
})(this.RocoUtils)