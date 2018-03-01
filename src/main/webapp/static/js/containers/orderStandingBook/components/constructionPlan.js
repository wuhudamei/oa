var ConstructionPlanComponent = Vue.extend({
    template: '#constructionPlanTmpl',
    data: function () {
        return {
            detail: {
                serviceName: '',
                stylistName: ''
            },
            orderNo: null,
            tabPlanList: [],
            person: [],
            num: [],
            loaded: false
        }
    },
    created: function () {
        this.orderNo = RocoUtils.parseQueryString()['orderno'];
    },
    props: ['msg'],
    watch: {
        msg: function (curVal, oldVal) {
            if (curVal == 14 && !this.loaded) {
                this.fetchData();
                this.findPerson();
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
        }
    },
    methods: {
        findPerson: function () {
            var self = this;
            self.$http.get('/api/standBook/findServiceNameAndStylistName?orderno=' + this.orderNo).then(function (res) {
                if(this.msg == 14){
                    self.detail = res.data.data;
                    self.loaded = true;
                }
            })
        },
        days: function () {
            var item = this.tabPlanList;
            this.num = item.realDoneDate - item.planDoneDate;
        },
        fetchData: function () {
            var self = this;
            self.$http.get('/api/standBook/findPlanAndDoneTimeByOrderNo?orderno=' + this.orderNo).then(function (res) {
                if(this.msg == 14){
                    self.person = res.data[0];
                    self.tabPlanList = res.data;
                    self.loaded = true;
                }
            })
        }
    }
});