var ConstructionCostComponent = Vue.extend({
    template: '#constructionCostTmpl',
    data: function () {
        return {
            orderNo: null,
            pmSettleInfo: null,
            loaded:false
        }
    },
    props:['msg'],
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
        goType: function (val) {
            if (val === 1) {
                return '包工包料';
            } else {
                return '包工';
            }
        },
        billType: function (val) {
            if (val === 1) {
                return '中期';
            } else {
                return '竣工';
            }
        }
    },
    methods: {
        //提成
        fetchInstallBasePm: function () {
            var self = this;
            self.$http.post("/api/standBook/installedBasePm?orderId=" + this.orderNo).then(function (res) {
                if(this.msg == 14){
                    self.pmSettleInfo = res.data.pmSettleInfo;
                    self.loaded = true;
                }
            }, function (res) {
            })
        }
    },
    watch:{
        msg:function(curVal,oldVal){
            if(curVal==14 && !this.loaded){
                this.fetchInstallBasePm();
            }
        }

    }
});