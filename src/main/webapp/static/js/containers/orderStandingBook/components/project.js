var ProjectComponent = Vue.extend({
    template: '#projectTmpl',
    data: function () {
        return {
            detail: {
                serviceName: '',
                stylistName: ''
            },
            orderNo: null,
            tabPlanList: [],
            person: [],
            delaySheet: [],
            num: [],
            loaded: false
        }
    },
    created: function () {
        this.orderNo = RocoUtils.parseQueryString()['orderno'];
    },
    ready: function () {

    },
    props: ['msg'],
    watch: {
        msg: function (curVal, oldVal) {
            if (curVal == 6 && !this.loaded) {
                this.findPerson();
                this.fetchPlan();
                this.fetchDelaySheet();
            }
        }

    },
    filters: {
        noName: function (value) {
            return value ? value : ' - ';
        },
        isNo: function (value, planDoneDate) {
            if (!value) {
                return '-';
            } else {
                return Math.floor((value - planDoneDate) / 86400000);
            }
        },
        goDate: function (el) {
            if (el) {
                return moment(el).format('YYYY-MM-DD');
            }
            else {
                return '-';
            }
        },
        goType: function (val) {
            if (val == 10) {
                return '待审核';
            } else if (val == 15) {
                return '已拒绝';
            } else {
                return '审批通过';
            }
        }
    },
    methods: {
        findPerson: function () {
            var self = this;
            self.$http.get('/api/standBook/findServiceNameAndStylistName?orderno=' + this.orderNo).then(function (res) {
                if (this.msg == 6) {
                    self.detail = res.data.data;
                    self.loaded = true;
                }
            })
        },
        days: function () {
            var item = this.tabPlanList;
            this.num = item.realDoneDate - item.planDoneDate;
        },
        fetchPlan: function () {
            var self = this;
            self.$http.get('/api/standBook/findPlanAndDoneTimeByOrderNo?orderno=' + this.orderNo).then(function (res) {
                if (this.msg == 6) {
                    self.person = res.data[0];
                    self.tabPlanList = res.data;
                    self.loaded = true;
                }
            })
        },
        //延期单
        fetchDelaySheet: function () {
            var self = this;
            self.$http.get('/api/delaySheetStandBook/findDelaySheetByOrderNo?orderno=' + this.orderNo).then(function (res) {
                if (this.msg == 6) {
                    self.delaySheet = res.data;
                    self.loaded = true;
                }
            })
        }
    }
});