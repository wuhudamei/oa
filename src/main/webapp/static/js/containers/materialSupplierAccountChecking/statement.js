var tt = null;

+(function (window, RocoUtils) {
    $('#materialBill').addClass('active');
    $('#statement').addClass('active');
    tt = new Vue({
        el: '#container',

        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '报表',
                active: true //激活面包屑的
            }]
        },

        methods: {
            app: function () {
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('reportDiv'));

                var data1 = [100,180,200,340,130,250,260,201,300,312,274,280],
                    data2 = [100,180,200,340,130,250,260,201,300,310,270,280],
                    data3 = [90,156,214,365,128,336,254,201,300,412,0,0];

                var option = {
                    title: {
                        text:'2017年各供应商对账金额汇总',
                        left:'left' ,
                        top:'10px',
                        bottom:'10px',
                        textStyle: {
                            color: '#666',
                            fontWeight: 'normal'
                        }
                    },
                    toolbox: {
                        feature: {
                            dataView: {show: true, readOnly: false},
                            magicType: {show: true, type: ['line', 'bar']},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    legend: {
                        data:['未对账','已对账','实际结算']
                    },
                    tooltip : {
                        trigger: 'axis',
                        formatter:function(params)
                        {
                            var relVal = params[0].name;
                            for (var i = 0, l = params.length; i < l; i++) {
                                relVal += '<br/>' + params[i].seriesName + ' : ' + params[i].value+"元";
                            }
                            return relVal;
                        }
                    },
                    grid: {
                        y: '10%',
                        y2: '10%'
                    },
                    xAxis: [
                        {
                            type: 'category',
                            axisLabel:{
                                interval: 0,    // {number}
                                rotate: 40
                            },
                            data: ['2017-01','2017-02','2017-03','2017-04','2017-05','2017-06','2017-07','2017-08','2017-09','2017-10','2017-11','2017-12']
                        }
                    ],
                    yAxis: [
                        {
                            type: 'value'

                        }
                    ],
                    series: [
                        {
                            name:'未对账',
                            type:'bar',
                            stack:'xxx',
                            itemStyle: {
                                normal: {
                                    color: '#548dd5'
                                }
                            },
                            data: data1
                        },
                        {
                            name:'已对账',
                            type:'bar',
                            stack:'xxx',
                            itemStyle: {
                                normal: {
                                    color: '#e56c0a'
                                }
                            },
                            label: {
                                normal: {
                                    show: true,
                                    position: 'top',
                                    textStyle: {
                                        color: '#666'
                                    },
                                    formatter: function(params) {
                                        return params.value + data1[params.dataIndex]
                                    }
                                }
                            },
                            data: data2
                        },
                        {
                            name:'实际结算',
                            type:'bar',
                            stack:'xxx',
                            itemStyle: {
                                normal: {
                                    color: '#EAC100'
                                }
                            },
                            data: data3
                        }
                    ]
                };
                myChart.setOption(option);
            }
        },

        created: function () {

        },
        ready: function () {
            // this.drawTable();
            this.app();
        },
    });


})(this.window, RocoUtils);