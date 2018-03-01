var SupplyComponent = Vue.extend({
    template: '#supplyTmpl',
    data: function () {
        return {
            orderNo: null,
            supply: '',
            loaded: false
        }
    },
    props: ['msg'],
    ready: function () {
    },
    created: function () {
        this.orderNo = RocoUtils.parseQueryString()['orderno'];
    },
    filters: {
        goDate: function (el) {
            if (el) {
                return moment(el).format('YYYY-MM-DD');
            }
            else {
                return '-';
            }
        },
        goType:function (val) {
            if(val = 'NOT_PAIED'){
                return '未结算';
            }else {
                return '已结算';
            }
        },
        placeStatus:function (val) {
            if(val = 'NORMAL'){
                return '正常下单';
            }else {
                return '变更单';
            }
        }
    },
    methods: {
        //面板超链接
        chooseSpan: function (index) {
            $("#" + index + "aId").attr("href", "#" + index);
        },
        fetchData: function () {
            var self = this;
            this.$http.get('/api/supply/findSupplyInfoByContractNo?contractNo=' + this.orderNo).then(function (res) {
                if(this.msg== 12) {
                    self.supply = res.data;
                    self.loaded = true;
                }
            })
        }
    },
    watch: {
        msg: function (curVal, oldVal) {
            if (curVal == 12 && !this.loaded) {
                this.fetchData();
            }
        }

    }
});