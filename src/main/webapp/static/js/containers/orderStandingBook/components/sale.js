var SaleComponent = Vue.extend({
    template: '#saleTmpl',
    data: function () {
        return {
            orderNo: null,
            noNum: 0,
            payDetails: null, //详情
            first: 0,
            medium: 0,
            last: 0,
            sale: ''
        }

    },
    ready: function () {
        this.fetchSale();
    },
    created: function () {
        this.orderNo = RocoUtils.parseQueryString()['orderno'];
    },
    filters: {
        goDate: function (el) {
            if (el == "315504000000" || el == null) {
                return '-';
            } else {
                return moment(el).format('YYYY-MM-DD');
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
        },
        goIsImprestAmount: function (val) {
            switch (val) {
                case 0:
                    return '否';
                    break;
                case 1:
                    return '是';
                    break;
            }
        },
        goIsHaveNoBackTag: function (val) {
            if (val) {
                return '否';
            } else if (val == null || val == undefined || val == '') {
                return '是';
            } else {
                return '否'
            }
        },
        goOrigin: function (val) {
            if (val == 'A01') {
                return '搜索引擎';
            } else if (val == 'A02') {
                return '朋友圈'
            } else if (val == 'A05') {
                return '报价器';
            } else if (val == 'A06') {
                return '极有家'
            } else if (val == 'A07') {
                return '大美装饰管理平台';
            } else if (val == 'A11') {
                return '新媒体1'
            } else if (val == 'A12') {
                return '新媒体2';
            } else if (val == 'B01') {
                return '天猫';
            } else if (val == 'C01') {
                return '广播'
            } else if (val == 'B01') {
                return '天猫';
            } else if (val == 'B03') {
                return '京东'
            } else if (val == 'B04') {
                return '百度糯米'
            } else if (val == 'B05') {
                return '大众点评';
            } else if (val == 'B11') {
                return '电商渠道1'
            } else if (val == 'B12') {
                return '电商渠道2'
            } else if (val == 'C01') {
                return '广播'
            } else if (val == 'C02') {
                return '电视'
            } else if (val == 'C03') {
                return '户外';
            } else if (val == 'C07') {
                return '今日头条'
            } else if (val == 'C08') {
                return '报纸';
            } else if (val == 'D01') {
                return '市场地推';
            } else if (val == 'D05') {
                return '市场电邀'
            } else if (val == 'E01') {
                return '转介绍'
            } else if (val == 'E04') {
                return '小美返单';
            } else if (val == 'F01') {
                return '自然进店';
            } else {
                return '其他';
            }
        }
    },
    methods: {
        fetchSale: function () {
            var self = this;
            self.$http.get('/api/sale/getOrderDetailByOrderNo', {
                params: {
                    orderno: self.orderNo
                }
            }).then(function (res) {
                if (res.data.code == 1 && res.data.data != null) {
                    self.sale = res.data.data;
                    if (res.data.data.isImprestAmount == '1' && res.data.data.haveNoBackTag) {
                        self.sale.bigType = ' A类';
                    } else if (res.data.data.isImprestAmount == '0' || res.data.data.isImprestAmount == '' || res.data.data.isImprestAmount == null) {
                        self.sale.bigType = ' C类';
                    } else if (res.data.data.isImprestAmount == '1' && !res.data.data.haveNoBackTag || res.data.data.haveNoBackTag == undefined || res.data.data.haveNoBackTag == null || res.data.data.haveNoBackTag == '') {
                        self.sale.bigType = ' B类';
                    }
                }
            });
            self.$http.get('/api/standBook/findFinanceDownPayment?orderno=' + this.orderNo).then(function (res) {
                if (res.data.code == 1) {
                    self.payDetails = res.data.data;
                }
            }, function (res) {

            })
        }
    }
});