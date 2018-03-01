var vueIndex = null;
+(function (RocoUtils) {
    // 接口Url
    //var httpUrl = 'http://103.249.255.142:8013'
    // var httpUrl = 'http://report.rocozxb.cn';
    var key = '7bqwe2235df6aq2we4r3t6y1vxnmhjklpewd23';

   vueIndex = new Vue({
        el: '#container',
        data: {
            currDay: '', // 本日
            currWeek: '', // 本周
            currMonth: '', // 本月
            nearThirty: '', // 近三十天
            change: null, // 转单
            receivable: null, // 财务应收
            statement: null // 财务现金收入流水
        },
        ready: function() {
            this.currDateHandle();
            this.getOrderStatusChangeCount();
            this.getFinanceReceivableInfo();
            this.getFinanceStatement();
        },
        methods: {
            // 计算当前日期
            currDateHandle: function() {
                this.currDay = moment().format('YYYY-MM-DD');
                this.currWeek = moment().startOf('week').format('MM-DD') + '至' + moment().format('MM-DD');
                this.currMonth = moment().startOf('month').format('MM-DD') + '至' + moment().format('MM-DD');
                this.nearThirty = moment().subtract(30, 'days').format('MM-DD')  + '至' + moment().format('MM-DD');
            },
            // A02转单状况统计接口
            getOrderStatusChangeCount: function() {
                // /api/finance/GetOrderStatusChangeCount
                var self = this;
                var _t = +new Date();
                self.$http.get(ctx + '/api/reports/signReq',
                {
                    params: {
                        timeStamp: _t,
                        key: md5(_t + key).toUpperCase(),
                        aimUrl: httpUrl + '/api/finance/GetOrderStatusChangeCount' 
                    }
                }).then(function(res) {
                    // console.log(res.data);
                    if(res.data.code == 1) {
                        self.change = JSON.parse(res.data.data)
                        self.changeChart();
                    }
                })

            },
            // A03财务应收状况统计
            getFinanceReceivableInfo: function() {
                // /api/finance/GetFinanceReceivableInfo
                var self = this;
                var _t = +new Date();
                self.$http.get(ctx + '/api/reports/signReq',
                {
                    params: {
                        timeStamp: _t,
                        key: md5(_t + key).toUpperCase(),
                        aimUrl: httpUrl + '/api/finance/GetFinanceReceivableInfo'
                    }
                }).then(function(res) {
                    console.log(res.data);
                    if(res.data.code == 1) {
                        self.receivable = JSON.parse(res.data.data)
                        self.receivableChart();
                    }
                })
            },
            // A01财务现金收入流水状况统计
            getFinanceStatement: function() {
                // /api/finance/GetFinanceStatement
                var self = this;
                var _t = +new Date();
                self.$http.get(ctx + '/api/reports/signReq',
                {
                    params: {
                        timeStamp: _t,
                        key: md5(_t + key).toUpperCase(),
                        aimUrl: httpUrl + '/api/finance/GetFinanceStatement'
                    }
                }).then(function(res) {
                    // console.log(res.data);
                    if(res.data.code == 1) {
                        self.statement = JSON.parse(res.data.data)
                        self.statementChart();
                    }
                })
            },
            // 转单图表
            changeChart: function() {
                var self = this;
                // 饼图处理处
                var rbpieOption = {};
                rbpieOption = $.extend(true, rbpieOption, pieOption);
                rbpieOption.legend.data = ['新大定数', '新转设计数', '新转单数', '新竣工数', '新退单数'];

                // rbpieOption.legend.formatter = function(name) {
                //     var _val = 0;
                //     switch(name) {
                //         case '新大定数':
                //             _val = self.change.dayData.BigCount
                //             break;
                //         case '新转设计数':
                //             _val = self.change.dayData.ToDesign
                //             break;
                //         case '新转单数':
                //             _val = self.change.dayData.ToConstruct
                //             break;
                //         case '新竣工数':
                //             _val = self.change.dayData.ToComplete
                //             break;
                //         case '新退单数':
                //             _val = self.change.dayData.ToQuit
                //             break;
                //     }
                //     return name + ' ' + _val;
                // }

                rbpieOption.series[0].data = [
                    {
                        value:self.change.dayData.BigCount,
                        name: '新大定数'
                    },
                    {
                        value:self.change.dayData.ToDesign,
                        name: '新转设计数'
                    },
                    {
                        value:self.change.dayData.ToConstruct,
                        name: '新转单数'
                    },
                    {
                        value:self.change.dayData.ToComplete,
                        name: '新竣工数'
                    },
                    {
                        value:self.change.dayData.ToQuit,
                        name: '新退单数'
                    }
                ]

                var firstDay = echarts.init(document.getElementById('firstOne'));
                firstDay.setOption(rbpieOption)
                // 折线图处理
                var rbLineOption = $.extend(true, {}, lineOption);
                rbLineOption.xAxis.data = self.change.nearThirtyData.xAxis;
                rbLineOption.legend.data = ['新大定数', '新转设计数', '新转单数', '新竣工数', '新退单数'];
                rbLineOption.series[0].name = '新大定数';
                rbLineOption.series[0].type = 'line';
                rbLineOption.series[0].data = self.change.nearThirtyData.BigCount;

                rbLineOption.series[1].name = '新转设计数';
                rbLineOption.series[1].type = 'line';
                rbLineOption.series[1].data = self.change.nearThirtyData.ToDesign;

                rbLineOption.series[2].name = '新转单数';
                rbLineOption.series[2].type = 'line';
                rbLineOption.series[2].data = self.change.nearThirtyData.ToConstruct;

                rbLineOption.series[3].name = '新竣工数';
                rbLineOption.series[3].type = 'line';
                rbLineOption.series[3].data = self.change.nearThirtyData.ToComplete;

                rbLineOption.series[4].name = '新退单数';
                rbLineOption.series[4].type = 'line';
                rbLineOption.series[4].data = self.change.nearThirtyData.ToQuit;

                var firstLineDay = echarts.init(document.getElementById('firstFour'));
                firstLineDay.setOption(rbLineOption)
            },
            // 财务应收图表
            receivableChart: function() {
                var self = this;
                // 饼图处理处
                var rbpieOption = {};
                rbpieOption = $.extend(true, rbpieOption, pieOption);
                rbpieOption.tooltip.formatter = '{a} <br/>{b} : {c} 万元';
                rbpieOption.legend.data = [ '应收首期款', '应收拆改费', '应收中期款', '应收尾款'];

                // rbpieOption.legend.formatter = function(name) {
                //     var _val = 0;
                //     switch(name) {
                //         case '应收合计':
                //             _val = self.receivable.dayData.TotalCharge
                //             break;
                //         case '应收首期款':
                //             _val = self.receivable.dayData.InitialCharge
                //             break;
                //         case '应收拆改费':
                //             _val = self.receivable.dayData.RemoveCharge
                //             break;
                //         case '应收中期款':
                //             _val = self.receivable.dayData.MiddleCharge
                //             break;
                //         case '应收尾款':
                //             _val = self.receivable.dayData.LastCharge
                //             break;
                //     }
                //     return name + ' ' + _val;
                // }

                rbpieOption.series[0].data = [
                    // {
                    //     value:self.receivable.dayData.TotalCharge,
                    //     name: '应收合计'
                    // },
                    {
                        value:self.receivable.dayData.InitialCharge,
                        name: '应收首期款'
                    },
                    {
                        value:self.receivable.dayData.RemoveCharge,
                        name: '应收拆改费'
                    },
                    {
                        value:self.receivable.dayData.MiddleCharge,
                        name: '应收中期款'
                    },
                    {
                        value:self.receivable.dayData.LastCharge,
                        name: '应收尾款'
                    }
                ]

                var secondDay = echarts.init(document.getElementById('secondOne'));
                secondDay.setOption(rbpieOption)
                // 折线图处理
                var rbLineOption = $.extend(true, {}, lineOption);
                rbLineOption.xAxis.data = self.receivable.nearThirtyData.xAxis;
                rbLineOption.tooltip.formatter = '{b} <br />{a0}: {c0} 万元<br />{a1}: {c1} 万元<br />{a2}: {c2} 万元<br />{a3}: {c3} 万元<br />{a4}: {c4} 万元',
                rbLineOption.legend.data = ['应收首期款', '应收拆改费', '应收中期款', '应收尾款', '应收合计'];
                

                rbLineOption.series[0].name = '应收首期款';
                rbLineOption.series[0].type = 'line';
                rbLineOption.series[0].data = self.receivable.nearThirtyData.InitialCharge;

                rbLineOption.series[1].name = '应收拆改费';
                rbLineOption.series[1].type = 'line';
                rbLineOption.series[1].data = self.receivable.nearThirtyData.RemoveCharge;

                rbLineOption.series[2].name = '应收中期款';
                rbLineOption.series[2].type = 'line';
                rbLineOption.series[2].data = self.receivable.nearThirtyData.MiddleCharge;

                rbLineOption.series[3].name = '应收尾款';
                rbLineOption.series[3].type = 'line';
                rbLineOption.series[3].data = self.receivable.nearThirtyData.LastCharge;

                rbLineOption.series[4].name = '应收合计';
                rbLineOption.series[4].type = 'line';
                rbLineOption.series[4].data = self.receivable.nearThirtyData.TotalCharge;

                var secondLineDay = echarts.init(document.getElementById('secondFour'));
                secondLineDay.setOption(rbLineOption)

            },
            // 财务现金收入流水
            statementChart: function() {
                var self = this;
                
                // 饼图处理处
                var rbpieOption = {};
                rbpieOption = $.extend(true, rbpieOption, pieOption);
                rbpieOption.tooltip.formatter = '{a} <br/>{b} : {c} 万元';
                rbpieOption.legend.data = ['预付款', '首期款', '拆改费', '中期款', '尾款', '尾款后变更款'];
                rbpieOption.legend.padding = 0;

                // rbpieOption.legend.formatter = function(name) {
                //     var _val = 0;
                //     switch(name) {
                //         case '总收入':
                //             _val = self.statement.dayInfo.TotalCharge
                //             break;
                //         case '预付款':
                //             _val = self.statement.dayInfo.AdvanceCharge
                //             break;
                //         case '首期款':
                //             _val = self.statement.dayInfo.InitialCharge
                //             break;
                //         case '拆改费':
                //             _val = self.statement.dayInfo.RemoveCharge
                //             break;
                //         case '中期款':
                //             _val = self.statement.dayInfo.MiddleCharge
                //             break;
                //         case '尾款':
                //             _val = self.statement.dayInfo.LastCharge
                //             break;
                //         case '尾款后变更款':
                //             _val = self.statement.dayInfo.LastChangeCharge
                //             break;
                //     }
                //     return name + ' ' + _val;
                // }

                rbpieOption.series[0].data = [
                    // {
                    //     value:self.statement.dayInfo.TotalCharge,
                    //     name: '总收入'
                    // },
                    {
                        value:self.statement.dayInfo.AdvanceCharge,
                        name: '预付款'
                    },
                    {
                        value:self.statement.dayInfo.InitialCharge,
                        name: '首期款'
                    },
                    {
                        value:self.statement.dayInfo.RemoveCharge,
                        name: '拆改费'
                    },
                    {
                        value:self.statement.dayInfo.MiddleCharge,
                        name: '中期款'
                    },
                    {
                        value:self.statement.dayInfo.LastCharge,
                        name: '尾款'
                    },
                    {
                        value:self.statement.dayInfo.LastChangeCharge,
                        name: '尾款后变更款'
                    }
                ]

                var threeDay = echarts.init(document.getElementById('threeOne'));
                threeDay.setOption(rbpieOption)
                // 折线图处理
                var rbLineOption = $.extend(true, {}, lineOption);
                rbLineOption.xAxis.data = self.statement.nearThirtyInfo.xAxis;
                rbLineOption.tooltip.formatter = '{b} <br />{a0}: {c0} 万元<br />{a1}: {c1} 万元<br />{a2}: {c2} 万元<br />{a3}: {c3} 万元<br />{a4}: {c4} 万元<br />{a5}: {c5} 万元<br />{a6}: {c6} 万元',
                rbLineOption.legend.data = ['预付款', '首期款', '拆改费', '中期款', '尾款', '尾款后变更款', '总收入'];

                rbLineOption.series = [];

                rbLineOption.series.push({
                    name: '预付款',
                    type: 'line',
                    data: self.statement.nearThirtyInfo.AdvanceCharge
                })

                rbLineOption.series.push({
                    name: '首期款',
                    type: 'line',
                    data: self.statement.nearThirtyInfo.InitialCharge
                })

                rbLineOption.series.push({
                    name: '拆改费',
                    type: 'line',
                    data: self.statement.nearThirtyInfo.RemoveCharge
                })

                rbLineOption.series.push({
                    name: '中期款',
                    type: 'line',
                    data: self.statement.nearThirtyInfo.MiddleCharge
                })

                rbLineOption.series.push({
                    name: '尾款',
                    type: 'line',
                    data: self.statement.nearThirtyInfo.LastCharge
                })

                rbLineOption.series.push({
                    name: '尾款后变更款',
                    type: 'line',
                    data: self.statement.nearThirtyInfo.LastChangeCharge
                })

                rbLineOption.series.push({
                    name: '总收入',
                    type: 'line',
                    data: self.statement.nearThirtyInfo.TotalCharge
                })

                var fourLineDay = echarts.init(document.getElementById('threeFour'));
                fourLineDay.setOption(rbLineOption)
            }
        }
    })
})(this.RocoUtils)