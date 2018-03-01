var vueIndex = null; +
(function (RocoUtils) {

//  var httpUrl = 'http://103.249.255.142:8013'
	var httpUrl = 'http://report.rocozxb.cn';
  vueIndex = new Vue({
    el: '#container',
    data: {
      date:null,
      //  designStatus:{
      //   '1':'新增单',
      //   '2':'已派单',
      //   '3':'转单量',
      //   '4':'派单量',
      //   '5':'待转单',
      //   '6':'销售家具量',
      //   '7':'转单产值'
      // },
      pieData:[],
      params:{}
    },
    methods: {
      // 日饼状图数组
      fetchDay: function (type) {
        var self = this;
        this.$http.get(ctx + '/api/reports/signReq', {
          params: {
            aimUrl:httpUrl + '/api/Design/getDesignDetailData',
            startTime: this.params.startTime,
            endTime: this.params.endTime,
          }
        }).then(function (res) {
          if (res.data.code == 1) {
            var arr;
            self.pieData = [];
            self.pieData = JSON.parse(res.data.data).details;
          }
        })
      },
       goBack:function(){
        window.history.go(-1)
       },
       backList:function(){
        window.location.href = '/api/reports/list'
      }
    },
    created: function () {
      this.params = this.$parseQueryString();
      if(moment(this.params.startTime).format('YYYY-MM-DD') === moment(this.params.endTime).format('YYYY-MM-DD')){
        this.date = moment(this.params.startTime).format('YYYY-MM-DD')
      }else {
        this.date = moment(this.params.startTime).format('YYYY-MM')
      }
      // // 判断线索还是进店
    },
    ready: function () {
      this.fetchDay()
    }
  })
})(this.RocoUtils)