var tt = null;
+(function (window, RocoUtils) {
    $('#materialBill').addClass('active');
    $('#budget').addClass('active');
    tt = new Vue({
        el: '#container',
        validators: {
            laterThanStart: function (val, startDate) {
                try {
                    var end = moment(val);
                    return end.isBefore(startDate) ? false : true;
                } catch (e) {
                    return false;
                }
            }
        },
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '项目毛利率预测',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            budgets:null,
            form: {
                keyword: '',
                startDate: '',
                endDate: '',
                startTime: '',
                endTime: ''
            },
        },
        filters:{
            goDate:function(el){
                if(el){
                    return moment(el).format('YYYY-MM-DD')
                }
                else{
                    return '-'
                }
            }
        },
        methods: {
            fetchRecord: function () {
                var self = this;
                var data = _.clone(self.form);
                self.$http.post('/api/stOrdCapitalBudget/findStOrdCapitalBudgetByNoAndMobileAndName', data, {emulateJSON: true}).then(function (res) {
                    if (res.data.code == 1) {
                        self.budgets = res.data.data;
                    }
                    self.submitting = false;
                }).catch(function () {

                }).finally(function () {
                    self.submitting = false;
                });
            },
            activeDate:function () {

            },
            activeTime: function () {
                $(this.$els.startDate).datetimepicker({
                    minView: 2,
                    format: 'yyyy-mm-dd',
                    weekStart: 1,
                    todayBtn: true
                });
                $(this.$els.endDate).datetimepicker({
                    minView: 2,
                    format: 'yyyy-mm-dd',
                    weekStart: 1,
                    todayBtn: true
                });
                $(this.$els.startTime).datetimepicker({
                    minView: 2,
                    format: 'yyyy-mm-dd',
                    weekStart: 1,
                    todayBtn: true
                });
                $(this.$els.endTime).datetimepicker({
                    minView: 2,
                    format: 'yyyy-mm-dd',
                    weekStart: 1,
                    todayBtn: true
                });
            },
            //点击搜索功能
            query: function () {
                this.fetchRecord();
            },
        },

        created: function () {
        },
        ready: function () {
            this.fetchRecord();
            this.activeDate();
            this.activeTime();
        },
    });
})(this.window, RocoUtils);