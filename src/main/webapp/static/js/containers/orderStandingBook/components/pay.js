var PayComponent = Vue.extend({
    template: '#payTmpl',
    data: function () {
        return {
            orderNo: null,
            noNum: 0,
            payList: null, //缴费台帐
            payDetails: null, //详情
            first: 0,
            medium: 0,
            last: 0,
            loaded: false,
            //已交总金额
            payedAll: 0,

            //未抵
            noMortgage: 0,
            //预收款
            prePayment: 0,
            //营业收入
            businessIncome: 0,
            //应收款
            receivables: 0,
            //变更款
            changeMoney: 0,
            //合同金额
            orderAmount: 0,
            //现合同金额
            nowOrderAmount: 0
        }
    },
    props: ['msg'],
    created: function () {
        this.orderNo = RocoUtils.parseQueryString()['orderno'];
    },
    ready: function () {
    },
    filters: {
        goDate: function (el) {
            if (el == "315504000000") {
                return '-';
            } else {
                return moment(el).format('YYYY-MM-DD');
            }
        },
        temporary: function (val) {
            if (val != null || val != 0) {
                return val;
            } else {
                return 0.0;
            }
        },
        goType: function (val) {
            switch (val) {
                case 1 :
                    return '定金';
                    break;
                case 2 :
                    return '首期';
                    break;
                case 3 :
                    return '中期';
                    break;
                case 4 :
                    return '尾款';
                    break;
                case 5 :
                    return '设计费从退款扣除';
                    break;
                case 6 :
                    return '其他费用从退款扣除';
                    break;
                case 7 :
                    return '拆改费';
                    break;
                case 8 :
                    return '退款';
                    break;
                case 9 :
                    return '尾款后变更和赔款';
                    break
            }
        }
    },
    methods: {
        //总金额
        getAll: function () {
            var item = this.payList;
            this.payedAll = item.firstAmount + item.mediumAmount + item.finalAmount + item.finishChangeAmount + item.modifyCost;
            return this.payedAll;
        },
        //未缴
        imprest: function () {
            var item = this.payList;
            return item.imprest ? 0 : 10000 - item.imprestAmount;
        },
        //首期未缴费金额
        unpaid: function () {
            var items = this.payList;
            this.first = this.nowOrderAmount - items.firstAmount - ( (this.orderAmount - items.modifyCost) * 0.35 );
            if (items.imfirst == true && items.mediumAmount != 0) {
                return 0;
            } else if (this.first >= 0.01 || this.first <= 1) {
                return 0;
            } else {
                return this.first;
            }
        },
        //中期未缴费金额
        model: function () {
            var items = this.payList;
            this.medium = (this.nowOrderAmount - items.firstAmount - items.mediumAmount) - items.endPayment - ( (this.orderAmount - items.modifyCost) * 0.65 );
            if (items.immedium == true && items.finalAmount != 0) {
                return 0;
            } else if (this.medium >= 0.00 || this.medium <= 1) {
                return 0;
            } else {
                return this.medium;
            }
        },
        //尾期未缴费金额
        lastPay: function () {
            var items = this.payList;
            this.last = this.nowOrderAmount - (items.mediumAmount + items.firstAmount + items.finalAmount + items.modifyCost);
            return this.last;
        },
        //未缴费总金额
        al: function () {
            var items = this.payList;
            var total;
            total = (this.nowOrderAmount - (items.mediumAmount + items.firstAmount + items.finalAmount + items.modifyCost));
            return total;
        },
        //是否显示
        isShow: function (ev) {
            return ev == null || ev == '' ? false : true;
        },
        //未抵
        mortgage: function () {
            var item = this.payList;
            return this.noMortgage = item.imprestAmount - item.totalImprestAmountDeductible;
        },
        //预收款
        prePay: function () {
            var item = this.payList;
            if (item.status == "9") {
                return this.prePayment = 0;
            } else {
                this.prePayment = this.payedAll;
                return this.prePayment;
            }
        },
        //营业收入
        income: function () {
            var item = this.payList;
            if (item.status == "9") {
                return this.businessIncome = this.getAll() + this.al();
            } else {
                return this.businessIncome = 0;
            }
        },
        //应收款
        receivable: function () {
            var item = this.payList;
            if (item.status == "9") {
                return this.receivables = this.al();
            } else {
                return this.receivables = 0;
            }
        },
        fetchData: function () {
            var self = this;
            self.$http.get('/api/standBook/getPayment?orderno=' + this.orderNo).then(function (res) {
                if (this.msg == 9) {
                    if (res.data.code == 1) {
                        self.payList = res.data.data;
                    }
                    self.loaded = true;
                }
            }, function (res) {
            });
            self.$http.get('/api/standBook/getFinance?orderno=' + this.orderNo).then(function (res) {
                if (this.msg == 9) {
                    if (res.data.code == 1) {
                        self.payDetails = res.data.data;
                    }
                    self.loaded = true;
                }
            }, function (res) {
            });
            self.$http.get("/api/standBook/getOrderDetailByOrderNo", {
                params: {
                    orderno: self.orderNo
                }
            }).then(function (res) {
                if (res.data.code == 1) {
                    self.changeMoney = res.data.data.changeMoney;
                    self.orderAmount = res.data.data.totalMoney;
                    self.nowOrderAmount = res.data.data.totalMoney + res.data.data.changeMoney;
                }
            });
        }
    },
    watch: {
        msg: function (curVal, oldVal) {
            if (curVal == 9 && !this.loaded) {
                this.fetchData();
            }
        }

    }
});