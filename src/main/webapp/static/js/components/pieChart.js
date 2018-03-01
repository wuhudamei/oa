+(function() {
    var pieChart = Vue.extend({
        template:'#piechart',
        props:{
            show:{
                type:Boolean,
                default:false
            },
            href:{
                default:null
            },
            legend:{
              type:Array,
              default:[ '应收首期款', '应收拆改费', '应收中期款', '应收尾款']
            },
            name:{
                type:String,
                default:'本日'
            },
            series:{
              default: {"weekData":{"RemoveCharge":3.41,"dates":"0001-01-01T00:00:00","LastCharge":82.92,"TotalCharge":1055.57,"MiddleCharge":428.74,"InitialCharge":540.5},"monthData":{"RemoveCharge":0,"dates":"0001-01-01T00:00:00","LastCharge":520.09,"TotalCharge":3917.49,"MiddleCharge":2328.05,"InitialCharge":1069.35},"dayData":{"RemoveCharge":11.96,"dates":"0001-01-01T00:00:00","LastCharge":6.08,"TotalCharge":153.02,"MiddleCharge":25.71,"InitialCharge":109.27},"nearThirtyData":{"xAxis":["2017-04-11","2017-04-12","2017-04-13","2017-04-14","2017-04-15","2017-04-16","2017-04-17","2017-04-18","2017-04-19","2017-04-20","2017-04-21","2017-04-22","2017-04-23","2017-04-24","2017-04-25","2017-04-26","2017-04-27","2017-04-28","2017-04-29","2017-04-30","2017-05-01","2017-05-02","2017-05-03","2017-05-04","2017-05-05","2017-05-06","2017-05-07","2017-05-08","2017-05-09","2017-05-10","2017-05-11"],"legend":["应收合计","应收首期款","应收拆改费","应收中期款","应收尾款"],"RemoveCharge":[13.11,13.77,9.38,9.67,3.98,10.12,4.88,10.93,15.14,10.64,1.83,3.46,9.07,10.64,9.61,9.22,5.04,10.05,7.93,11.02,11.77,19.07,16.95,6.14,2.65,9.3,4.6,9.46,13.83,12.68,11.96],"LastCharge":[0.88,0.52,0,0.65,1.03,1.19,4.63,0.92,17.32,24.18,9.51,10.55,2.6,19.71,3.97,6.64,4.1,9.16,7.74,5.61,11.72,8.3,48.95,7.85,13.48,5.91,19.71,17.44,7.62,7.56,6.08],"TotalCharge":[182.1,156.09,401.87,130.04,114.6,135.61,189.98,299.74,225.88,163.76,85.93,174.58,168.62,198.14,169.07,155.44,310.05,240.17,152.84,223.02,150.84,315.14,263.86,101.25,64.2,88.37,129.94,135.1,196.99,222.5,153.02],"MiddleCharge":[70.31,49.77,293.91,47.12,80.89,35.44,118.25,104.64,45.75,45.38,36.46,127.92,50.18,87.2,45.22,46.32,242.64,143.53,57.38,84.38,38.82,159.47,34.1,32.63,25.8,23.53,87.57,37.3,72.8,83.64,25.71],"InitialCharge":[97.8,92.03,98.58,72.61,28.7,88.86,62.23,183.25,147.67,83.56,38.13,32.65,106.77,80.59,110.27,93.26,58.26,77.42,79.79,122.02,88.53,128.3,163.86,54.63,22.27,49.64,18.06,70.89,102.73,118.62,109.27]}}
            },
            unit:{
                type:Boolean,
                default:true
            },
            hei:{
                default:100
            }
        },
        data: function(){
            return {  
            };
        },
        created: function() {

        },
        ready: function() {
            this.receivableChart()
        },
        methods: {
            handleClick: function() {
                this.$emit('detail-click')
            },
            receivableChart: function() {
                var self = this;
                // 饼图处理处
                var rbpieOption = {};
                rbpieOption = $.extend(true, rbpieOption, pieOption);
                rbpieOption.tooltip.formatter = self.unit ? '{a} <br/>{b} : {c} 万元' : '{a} <br/>{b} : {c}' ;
                rbpieOption.legend.data = this.legend;

              
                // rbpieOption.series[0].data = [

                //     {
                //         value:self.series.dayData.InitialCharge,
                //         name: '应收首期款'
                //     },
                //     {
                //         value:self.series.dayData.RemoveCharge,
                //         name: '应收拆改费'
                //     },
                //     {
                //         value:self.series.dayData.MiddleCharge,
                //         name: '应收中期款'
                //     },
                //     {
                //         value:self.series.dayData.LastCharge,
                //         name: '应收尾款'
                //     }
                // ]
                rbpieOption.series[0].name = self.name
                rbpieOption.series[0].data = self.series
                self.$nextTick(function(){
                  var secondDay = echarts.init(self.$els.echart);
                  secondDay.setOption(rbpieOption)
                })
            }
        },
        watch:{
            series:function(){
                this.receivableChart()
            }    
        }
    });

    Vue.component('pie-chart', pieChart);
})();