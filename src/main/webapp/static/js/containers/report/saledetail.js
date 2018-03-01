var vueIndex = null; +
(function (RocoUtils) {

//  var httpUrl = 'http://103.249.255.142:8013'
  // var httpUrl = 'http://report.rocozxb.cn';
  vueIndex = new Vue({
    el: '#container',
    data: {
      clue: 'getDtlSrcXSPerioud',
      shop:'getDtlSrcInShopPerioud',
      date:null,
      pieData:[],
      params:{}
    },
    methods: {
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
            var arr;
            self.pieData = [];
            self.pieData = JSON.parse(res.data.data).allBigTypeSource;
            self.pieData.forEach(function (val) {
              var pieArr = [];
              val.childSource.forEach(function(value){
                var obj = {}
                obj.name  = value.sourceName;
                obj.value = value.amount;
                pieArr.push(obj)
              })
              val.childPie = pieArr;
            })
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
      this.fetchDay(this.params.type)
    }
  })
})(this.RocoUtils)