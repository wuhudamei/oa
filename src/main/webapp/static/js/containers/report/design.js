var vueIndex = null; +
(function (RocoUtils) {
  // var httpUrl = 'http://103.249.255.142:8013'
  // var httpUrl = 'http://report.rocozxb.cn';

  vueIndex = new Vue({
    el: '#container',
    data: {
      // clue: {
      //   day: 'getLvTopXSOneDay',
      //   month: 'getLvTopXSPerioud'
      // },
      // shop: {
      //   day: 'getLvTopInShopOneDay',
      //   month: 'getLvTopInShopPerioud'
      // },
      design:{
        day: '/Design/getDesignDayData',
        month: '/Design/getDesignMonthData'
      },
      designStatus:{
        'newData':'新增单',
        'sentData':'已派单',
        'waitTransData':'转单量',
        'transData':'派单量',
        'backData':'待转单',
        'salesFurnitureData':'销售家具量',
        'transferMoneyData':'转单产值'
      },
      dateTime: null,
      dataMonth: null,
      day: {
        startTime: null,
        endTime: null,
      },
      date: {
        day:null,
        month:null
      },
      dayDisabled:false,
      monthDisabled:false,
      lastDisabled:false,
      transferMoneyData: null,   //转单产值单独拿出
      monthTransferMoneyData: null,   //转单产值单独拿出
      allMonthData: null,
      legend: [],
      params: null,
      startTime: '',
      endTime: '',
      // 饼状图数据
      dayData: [],
      monthData: [],
      // 折线图数据
      lineMonthData: null,
      style: {
        height: '150px'
      },
      xAxis: []
    },
    methods: {
      // 日饼状图数组
      fetchDay: function (type) {
        var self = this;
        this.$http.get(ctx + '/api/reports/signReq', {
          params: {
            aimUrl: httpUrl + '/api' + this.design.day,
            startTime: this.day.startTime,
            endTime: this.day.endTime,
          }
        }).then(function (res) {
          if (res.data.code == 1) {
            var designObj
            self.dayData = [];
            self.transferMoneyData = null;
            designObj = JSON.parse(res.data.data).DesignNumberInfo[0];
            for(var i in designObj){
              var obj = {};
              obj.name = self.designStatus[i];
              obj.value = designObj[i];
              if(i === 'transferMoneyData'){
                self.transferMoneyData = designObj[i];
              }else{
                self.dayData.push(obj);
              }
            }
            // arr.forEach(function (val) {
            //   var obj = {};
            //   obj.name = self.designStatus[val.status];
              
            //   self.dayData.push(obj);
            // })
          }
        })
      },
      // 月饼状图数组
      fetchMonth: function (type) {
        var self = this;
        this.$http.get(ctx + '/api/reports/signReq', {
          params: {
            startTime: this.startTime,
            endTime: this.endTime,
            aimUrl: httpUrl + '/api' + this.design.month,
          }
        }).then(function (res) {
          if (res.data.code == 1) {
            var arr ,designObj;
            self.monthData = [];
            self.monthTransferMoneyData = null;
            self.lineMonthData = [];
            self.legend = [];
            self.allMonthData = JSON.parse(res.data.data)
            arr = JSON.parse(res.data.data).DesignNumberInfo;

            designObj = JSON.parse(res.data.data).DesignNumberInfo[0];
            for(var i in designObj){
              var obj = {};
              obj.name = self.designStatus[i];
              obj.value = designObj[i];
              if(i === 'transferMoneyData'){
                self.monthTransferMoneyData = designObj[i];
              }else{
                self.monthData.push(obj);
              }
            }
            // 计算X轴日期
            self.getX();
            self.$nextTick(function () {
              self.lineChart()
            })
          }
        })
      },
      lineChart: function () {
          // 折线图处理
        var self = this;
        var rbLineOption = $.extend(true, {}, lineOption);
        rbLineOption.xAxis.data = self.xAxis;
        // rbLineOption.tooltip.formatter = '{b} <br />{a0}: {c0} 万元<br />{a1}: {c1} 万元<br />{a2}: {c2} 万元<br />',
         rbLineOption.tooltip.formatter = function (params) {
            var label = params[0].name + '<br />';  //添加时间
            params.forEach(function (val) {
              label = label + val.seriesName + ':' + val.value + '<br />'
            })
            return label;
          },
        rbLineOption.legend.data = self.allMonthData.monthChat.legend;
        var arr = Object.keys(self.allMonthData.monthChat)
        arr.forEach(function(val){
          var obj = {
            type: 'line'
          }
          switch (val) {
            case 'newCount':
              obj.name = '新增量';
              obj.data = self.allMonthData.monthChat.newCount;
              self.lineMonthData.push(obj)
              break;
            case 'sentCount':
              obj.name = '已派单';
              obj.data = self.allMonthData.monthChat.sentCount;
              self.lineMonthData.push(obj)
              break;
            case 'waitTransCount':
              obj.name = '待转单';
              obj.data = self.allMonthData.monthChat.waitTransCount;
              self.lineMonthData.push(obj)
              break;
            case 'transCount':
              obj.name = '转单量';
              obj.data = self.allMonthData.monthChat.transCount;
              self.lineMonthData.push(obj)
              break;
            case 'backCount':
              obj.name = '退单量';
              obj.data = self.allMonthData.monthChat.backCount;
              self.lineMonthData.push(obj)
              break;
            case 'LastChangeCharge':
              obj.name = '尾款后变更款';
              obj.data = self.allMonthData.monthChat.LastChangeCharge;
              self.lineMonthData.push(obj)
              break;
            case 'salesFurnitureCount':
              obj.name = '销售家具';
              obj.data = self.allMonthData.monthChat.salesFurnitureCount;
              self.lineMonthData.push(obj)
              break;
          }
        })
        rbLineOption.series = self.lineMonthData;
        var secondLineDay = echarts.init(document.getElementById('secondFour'));
        secondLineDay.setOption(rbLineOption)
      },
      nextClick: function (flag) {
        // 月
        if (flag) {
          // startTime大于等于当前月return
          if (this.startTime >= moment().startOf('month').format("YYYY-MM-DD HH:mm:ss")) {
            return false;
          } else {
            this.startTime = moment(this.startTime).add(1, 'M').format("YYYY-MM-DD HH:mm:ss")
            //判断是否为当前月
            if(moment().format('YYYY-MM') === moment(this.startTime).format('YYYY-MM')){
              this.endTime = moment().subtract(1,'d').format('YYYY-MM-DD') + ' 23:59:59'
            }else{
              this.endTime = moment(moment(this.endTime).add(1, 'M')).endOf('month').format("YYYY-MM-DD HH:mm:ss")
            }
          }
        } else {
          if (this.day.endTime >= moment().subtract(1, 'd').format('YYYY-MM-DD HH:mm:ss')) {
            return false;
          } else {
            this.day = {
              startTime: moment(this.day.startTime).add(1, 'd').format("YYYY-MM-DD HH:mm:ss"),
              endTime: moment(this.day.endTime).add(1, 'd').format("YYYY-MM-DD HH:mm:ss")
            }
          }
        }
      },
      lastClick: function (flag) {
        // 月
        if (flag) {
          // startTime大于等于当前月return
          this.startTime = moment(moment(this.startTime).subtract(1, 'M')).startOf('month').format("YYYY-MM-DD HH:mm:ss")
          this.endTime = moment(moment(this.endTime).subtract(1,'M')).endOf('month').format("YYYY-MM-DD HH:mm:ss")
        } else {
          this.day = {
            startTime: moment(this.day.startTime).subtract(1, 'd').format("YYYY-MM-DD HH:mm:ss"),
            endTime: moment(this.day.endTime).subtract(1, 'd').format("YYYY-MM-DD HH:mm:ss")
          }
        }
      },
      handleHref: function (flag) {
        if(flag === 'month'){
          window.location.href = '/api/reports/designdetail?startTime=' + this.startTime + '&endTime=' + this.endTime;
        }else{
          window.location.href = '/api/reports/designdetail?startTime=' + this.day.startTime + '&endTime=' + this.day.endTime;
        }
          
      },
      goBack: function () {
        window.history.go(-1)
      },
      backList: function () {
        window.location.href = '/api/reports/list'
      },
      getX: function () {
        if(this.allMonthData.monthChat.backCount.length === 0){
          this.xAxis = [];
          return false;
        }
        var xlength = this.allMonthData.monthChat.backCount.length,
          initDate = moment(this.startTime).format('YYYY-MM'),
          xArr = []
        for (var i = 1; i <= xlength; i++) {
          var initDay = i > 9 ? i : '0' + i;
          xArr.push(initDate + '-' + initDay)
        }
        this.xAxis = xArr
      }
    },
    created: function () {
      this.params = this.$parseQueryString();

      // 判断线索还是进店
      // this.dayUrl =  === 'clue' ? '线索url' : '进店'
    },
    ready: function () {
      var dateTime = moment().subtract(1, 'd').format('YYYY-MM-DD'); //当前日期前一天
      // this.day = {
      //   startTime: '2017-04-23' + ' 00:00:00',
      //   endTime: '2017-04-23' + ' 23:59:59',
      // }
      this.day = {
        startTime: dateTime + ' 00:00:00',
        endTime: dateTime + ' 23:59:59',
      }
      this.startTime = moment().startOf('month').format("YYYY-MM-DD") + ' 00:00:00'
      this.endTime = dateTime + ' 23:59:59'
    },
    computed: {
      dataMonth: function () {
        return moment(this.startTime).month() + 1
      },
      dateTime: function () {
        return moment(this.day.startTime).format("YYYY-MM-DD")
      },
      nextDisabled:function(){
         return this.startTime >= moment().startOf('month').format("YYYY-MM-DD HH:mm:ss")
      }
    },
    watch: {
      'day.endTime': function (val) {
        this.dayDisabled = moment(val).format("YYYY-MM-DD") >= moment().subtract(1, 'd').format('YYYY-MM-DD')
        this.fetchDay(this.params.type)
      },
      'endTime': function (val) {
        this.monthDisabled = moment(val).format("YYYY-MM") >= moment().format("YYYY-MM")
        this.fetchMonth(this.params.type)
      }
    }
  })
})(this.RocoUtils)