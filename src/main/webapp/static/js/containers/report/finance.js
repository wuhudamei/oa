var vm = null; +
(function (RocoUtils) {
//  var httpUrl = 'http://103.249.255.142:8013';
  // var httpUrl = 'http://report.rocozxb.cn';
  vm = new Vue({
    el: '#container',
    data: {
      setday: {
        categoryImensions: null,
        numberImensions: null,
        moneyImensions: null
      },
      setmonth: {
        categoryImensions: null,
        numberImensions: null,
        moneyImensions: null,
        monthChat: null
      },
      finance: {
        day: 'getFinanceStatementInfoByDay',
        month: 'getFinanceStatementInfoByMonth'
      },
      receive: {
        day: 'GetFinanceReceivableInfoByDay',
        month: 'GetFinanceReceivableInfoByMonth'
      },
      dateTime: null,
      dateMonth: null,
      day: {
        startTime: null,
        endTime: null
      },
      startTime: null,
      endTime: null,
      // 饼状图数据
      dayData: [],
      params: null,
      monthData: null,
      // 折线图数据
      lineMonthData: null,
      xAxis: [],
      dayDisabled:false,
      monthDisabled:false,
    },
    methods: {
      // 日列表
      fetchDay: function (type) {
        var self = this;
        this.$http.get(ctx + '/api/reports/signReq', {
          params: {
            aimUrl: httpUrl + '/api/Finance/' + this[type].day,
            startTime: this.day.startTime,
            endTime: this.day.endTime,
          }
        }).then(function (res) {
          if (res.data.code == 1) {
            self.setday = JSON.parse(res.data.data);
          }
        })
      },
      // 月列表
      fetchMonth: function (type) {
        var self = this;
        this.$http.get(ctx + '/api/reports/signReq', {
          params: {
            startTime: this.startTime,
            endTime: this.endTime,
            aimUrl: httpUrl + '/api/Finance/' + this[type].month,
          }
        }).then(function (res) {
          if (res.data.code == 1) {
            self.setmonth = JSON.parse(res.data.data);
            // self.getX();
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
        rbLineOption.xAxis.data = self.setmonth.OneMonthStatementLineChatInfo ? self.setmonth.OneMonthStatementLineChatInfo[0].xAxis : self.setmonth.OneMonthReceivableLineChatInfo[0].xAxis;
        rbLineOption.tooltip.formatter = function (params) {
            var label = params[0].name + '<br />';  //添加时间
            params.forEach(function (val) {
              label = label + val.seriesName + ':' + val.value + '万元<br />'
            })
            return label;
          },
          rbLineOption.legend.data =  self.setmonth.OneMonthStatementLineChatInfo ? self.setmonth.OneMonthStatementLineChatInfo[0].legend : self.setmonth.OneMonthReceivableLineChatInfo[0].legend;
        var arr = self.setmonth.OneMonthStatementLineChatInfo ? Object.keys(self.setmonth.OneMonthStatementLineChatInfo[0]) : Object.keys(self.setmonth.OneMonthReceivableLineChatInfo[0])
        self.lineMonthData = [];
        arr.forEach(function (val) {
          var obj = {
            type: 'line'
          }
          switch (val) {
            case 'AdvanceCharge':
              obj.name = '预付款';
              obj.data = self.setmonth.OneMonthStatementLineChatInfo ? self.setmonth.OneMonthStatementLineChatInfo[0].AdvanceCharge :self.setmonth.OneMonthReceivableLineChatInfo[0].AdvanceCharge;
              self.lineMonthData.push(obj)
              break;
            case 'InitialCharge':
              obj.name = self.setmonth.OneMonthStatementLineChatInfo ? '首期款' : "应收首期款";
              obj.data = self.setmonth.OneMonthStatementLineChatInfo ? self.setmonth.OneMonthStatementLineChatInfo[0].InitialCharge : self.setmonth.OneMonthReceivableLineChatInfo[0].InitialCharge;
              self.lineMonthData.push(obj)
              break;
            case 'MiddleCharge':
              obj.name = self.setmonth.OneMonthStatementLineChatInfo ? '中期款' : "应收中期款";
              obj.data = self.setmonth.OneMonthStatementLineChatInfo ? self.setmonth.OneMonthStatementLineChatInfo[0].MiddleCharge : self.setmonth.OneMonthReceivableLineChatInfo[0].MiddleCharge;
              self.lineMonthData.push(obj)
              break;
            case 'LastCharge':
              obj.name = self.setmonth.OneMonthStatementLineChatInfo ? '尾款' : '应收尾款';
              obj.data = self.setmonth.OneMonthStatementLineChatInfo ? self.setmonth.OneMonthStatementLineChatInfo[0].LastCharge : self.setmonth.OneMonthReceivableLineChatInfo[0].LastCharge;
              self.lineMonthData.push(obj)
              break;
            case 'RemoveCharge':
              obj.name = self.setmonth.OneMonthStatementLineChatInfo ? '拆改费' : "应收拆改费";
              obj.data = self.setmonth.OneMonthStatementLineChatInfo ? self.setmonth.OneMonthStatementLineChatInfo[0].RemoveCharge : self.setmonth.OneMonthReceivableLineChatInfo[0].RemoveCharge;
              self.lineMonthData.push(obj)
              break;
            case 'LastChangeCharge':
              obj.name = '尾款后变更款';
              obj.data = self.setmonth.OneMonthStatementLineChatInfo[0].LastChangeCharge;
              self.lineMonthData.push(obj)
              break;
            case 'TotalCharge':
              obj.name = self.setmonth.OneMonthStatementLineChatInfo ? '总收入':"应收合计";
              obj.data = self.setmonth.OneMonthStatementLineChatInfo ? self.setmonth.OneMonthStatementLineChatInfo[0].TotalCharge : self.setmonth.OneMonthReceivableLineChatInfo[0].TotalCharge;
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
              this.endTime = moment().subtract(1, 'd').format('YYYY-MM-DD') + ' 23:59:59'
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
        if (flag === 'month') {
          window.location.href = '/api/reports/setdetail?type=' + this.params.type + '&startTime=' + this.startTime + '&endTime=' + this.endTime;
        } else {
          window.location.href = '/api/reports/setdetail?type=' + this.params.type + '&startTime=' + this.day.startTime + '&endTime=' + this.day.endTime;
        }
      },
      goBack: function () {
        window.history.go(-1)
      },
      backList:function(){
        window.location.href = '/api/reports/list'
      }
    },
    created: function () {
      this.params = this.$parseQueryString();

    },
    ready: function () {
      var dateTime = moment().subtract(1, 'd').format('YYYY-MM-DD'); //当前日期前一天
      this.day = {
        startTime: dateTime + ' 00:00:00',
        endTime: dateTime + ' 23:59:59',
      }
      this.startTime = moment().startOf('month').format("YYYY-MM-DD") + ' 00:00:00'
      this.endTime = dateTime + ' 23:59:59'
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
    },
    computed: {
      dateMonth: function () {
        return moment(this.startTime).month() + 1
      },
      dateTime: function () {
        return moment(this.day.startTime).format("YYYY-MM-DD")
      }
    }
  })
})(this.RocoUtils)