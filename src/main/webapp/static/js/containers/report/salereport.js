var vueIndex = null; +
(function (RocoUtils) {
//  var httpUrl = 'http://103.249.255.142:8013'
  //  var httpUrl = 'http://report.rocozxb.cn';

  vueIndex = new Vue({
    el: '#container',
    data: {
      clue: {
        day: 'getLvTopXSOneDay',
        month: 'getLvTopXSPerioud'
      },
      shop: {
        day: 'getLvTopInShopOneDay',
        month: 'getLvTopInShopPerioud'
      },
      dateTime: null,
      dataMonth: null,
      day: {
        startTime: null,
        endTime: null,
      },
      dayDisabled:false,
      monthDisabled:false,
      lastDisabled:false,
      dayAmount: null,
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
            aimUrl: httpUrl + '/api/GatherCustomerReport/' + this[type].day,
            startTime: this.day.startTime,
            endTime: this.day.endTime,
          }
        }).then(function (res) {
          if (res.data.code == 1) {
            var arr
            self.dayData = [];
            arr = JSON.parse(res.data.data).allBigTypeSource;
            self.dayAmount = JSON.parse(res.data.data).totalAmount;
            arr.forEach(function (val) {
              var obj = {};
              obj.name = val.sourceName;
              obj.value = val.amount;
              self.dayData.push(obj);
            })
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
            aimUrl: httpUrl + '/api/GatherCustomerReport/' + this[type].month,
          }
        }).then(function (res) {
          if (res.data.code == 1) {
            var arr;
            self.monthData = [];
            self.lineMonthData = [];
            self.legend = [];
            self.allMonthData = JSON.parse(res.data.data)
            arr = JSON.parse(res.data.data).allBigTypeSource;
            arr.forEach(function (val) {
              var obj = {},
                lineObj = {
                  type: 'line'
                }
              obj.name = lineObj.name = val.sourceName;
              obj.value = val.amount;
              lineObj.data = val.amountInPerioud
              self.monthData.push(obj);
              self.legend.push(val.sourceName)
              self.lineMonthData.push(lineObj);
            })
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
        rbLineOption.tooltip.formatter = function (params) {
            var label = params[0].name + '<br />';  //添加时间
            params.forEach(function (val) {
              label = label + val.seriesName + ':' + val.value + '万元<br />'
            })
            return label;
          },
          rbLineOption.legend.data = self.legend;

        rbLineOption.series = [];
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
        if (flag === 'month') {
          window.location.href = '/api/reports/saledetail?type=' + this.params.type + '&startTime=' + this.startTime + '&endTime=' + this.endTime;
        } else {
          window.location.href = '/api/reports/saledetail?type=' + this.params.type + '&startTime=' + this.day.startTime + '&endTime=' + this.day.endTime;
        }
      },
      goBack: function () {
        window.history.go(-1)
      },
      backList: function () {
        window.location.href = '/api/reports/list'
      },
      getX: function () {
        if(this.allMonthData.allBigTypeSource.length === 0){
          this.xAxis = [];
          return false;
        }
        var xlength = this.allMonthData.allBigTypeSource[0].amountInPerioud.length,
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