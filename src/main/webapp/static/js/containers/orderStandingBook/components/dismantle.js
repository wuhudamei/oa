var DismantleComponent = Vue.extend({
    template: '#dismantleTmpl',
    data: function () {
        return {
            orderNo: null,
            addItemtype: null,
            loaded: false,
            dismantle: null,

            //老房拆除基装定额
            installBase: [],
            //老房拆除基装综合服务
            installBaseService: [],
            //老房拆除其他综合服务
            installBaseChange: []
        }
    },
    ready: function () {
    },
    props: ['msg'],
    created: function () {
        this.orderNo = RocoUtils.parseQueryString()['orderno'];
    },
    filters: {
        goType: function (val) {
            if (val == 1) {
                return '固定单价';
            } else if (val == 2) {
                return '计价面积';
            } else if (val == 3) {
                return '基装增项总价占比';
            } else if (val == 4) {
                return '工程总价占比 ';
            } else if (val == 6) {
                return '价格范围';
            } else if (val == 7) {
                return '拆除基装定额总价占比';
            } else {
                return '拆除工程总价占比';
            }
        },
        quotaScale:function (val) {
            return val + "%";
        }
    },
    methods: {
        //老房拆除基装定额
        oldHouseBaseDemolition:function () {
            var installBase = 0;
            var item = this.installBase;
            for(var i = 0; i<item.length;i++){
                installBase += item[i].addTotal;
            }
            return installBase;
        },
        //老房拆除基装综合服务费
        oldHouseBaseServiceDemolition:function () {
            var installBaseServiceCost = 0;
            var item = this.installBaseService;
            for(var i = 0; i<item.length;i++){
                installBaseServiceCost += item[i].addTotal;
            }
            return installBaseServiceCost;
        },
        //老房拆除其他综合服务费
        oldHouseBaseOtherDemolition:function () {
            var installBaseOtherCost = 0;
            var item = this.installBaseChange;
            for(var i = 0; i<item.length;i++){
                installBaseOtherCost += item[i].addTotal;
            }
            return installBaseOtherCost;
        },
        //总金额
        getAll: function () {
            var totalCost = 0;
            totalCost = this.oldHouseBaseDemolition()+this.oldHouseBaseServiceDemolition()+this.oldHouseBaseOtherDemolition();
            return totalCost;
        },

        findDismantle: function () {
            var self = this;
            self.$http.get('/api/dismantleStandBook/findDismantle?orderno=' + this.orderNo).then(function (res) {
                if (this.msg == 4) {
                    if (res.data.code == 1) {
                        self.dismantle = res.data.data;
                        var items = self.dismantle;
                        for (var i = 0; i < items.length; i++) {
                            if (items[i].addItemtype == 5) {
                                this.installBase.push(items[i])
                            } else if (items[i].addItemtype == 6) {
                                this.installBaseService.push(items[i])
                            } else {
                                this.installBaseChange.push(items[i])
                            }
                        }
                    }
                    self.loaded = true;
                }
            })
        }
    },
    watch: {
        msg: function (curVal, oldVal) {
            if (curVal == 4 && !this.loaded) {
                this.findDismantle();
            }
        }

    }
});