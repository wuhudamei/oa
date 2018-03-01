var ChangeComponent = Vue.extend({
    template: '#changeTmpl',
    data: function () {
        return {
            orderNo: null,
            //主材变更
            changes: '',
            //基装变更
            installBaseChanges:'',
            //主材变更次数
            count: null,
            //基装变更次数
            installBaseCount:null,
            //变更总次数
            TotalCount:null,

            totals: null,
            loaded: false
        }
    },
    ready: function () {
    },
    created: function () {
        this.orderNo = RocoUtils.parseQueryString()['orderno'];
    },
    filters: {
        goDate: function (el) {
            if (el) {
                return moment(el).format('YYYY-MM-DD HH:mm:ss');
            } else {
                return '-';
            }
        },
        incrementItem:function (val) {
            if(val>0){
                return '增项';
            }else if(val<0){
                return '减项';
            }else {
                return '-';
            }
        },
        goItemType:function (val) {
            if(val == 'item'){
                return '套餐内';
            }else{
                return '套餐外';
            }
        },
        goChangeType:function (val) {
            if(val == 1){
                return '增项';
            }else{
                return '减项';
            }
        },
        goChangeDate: function (el) {
            if (el) {
                return moment(el).format('YYYY-MM-DD');
            } else {
                return '-';
            }
        }
    },
    methods: {
        //面板超链接
        chooseSpan: function (index) {
            $("#" + index + "aId").attr("href", "#" + index);
        },
        choose: function (index) {
            $("#" + index + "bId").attr("href", "#" + index);
        },
        fetchData: function () {
            var self = this;
            this.$http.get('/api/changeStandBook/change?orderno=' + this.orderNo).then(function (res) {
                if(this.msg == 2){
                    if (res.data.code == 1) {
                        self.changes = res.data.data;
                        self.count = res.data.data.length;
                    }
                    self.loaded = true;
                }
            })
        },
        fetchChangeData: function () {
            var self = this;
            this.$http.get('/api/changeStandBook/installBaseChange?orderno=' + this.orderNo).then(function (res) {
                if(this.msg == 2){
                    if (res.data.code == 1) {
                        self.installBaseChanges = res.data.data;
                        self.installBaseCount = res.data.data.length;
                    }
                    self.loaded = true;
                }
            })
        },
        //计算总次数
        getCount:function () {
            var self = this;
          return self.TotalCount = self.count + self.installBaseCount;
        },
        getMultiplication: function (rows, key, key2, key3,key4,key5) {
            if (rows) {
                var sum = 0;
                for (var i = 0, len = rows.length; i < len; i++) {
                    for (var j = 0, p = rows[i].changeStandBookList.length; j < p; j++) {
                        if (rows[i].changeStandBookList[j][key3] !== 'item') {
                            if (!isNaN(rows[i].changeStandBookList[j][key]) && !isNaN(rows[i].changeStandBookList[j][key2])) {
                                if(rows[i].changeStandBookList[j][key3] == 'lessen'){
                                    sum += 0-(rows[i].changeStandBookList[j][key] - rows[i].changeStandBookList[j][key2]);
                                }else if(rows[i].changeStandBookList[j][key3] == 'activity' &&　rows[i].changeStandBookList[j][key5] == 1){
                                    sum += rows[i].changeStandBookList[j][key4];
                                }else if(rows[i].changeStandBookList[j][key3] == 'activity' &&　rows[i].changeStandBookList[j][key5] == 2){
                                    sum -= rows[i].changeStandBookList[j][key4];
                                }else {
                                    sum += (rows[i].changeStandBookList[j][key] - rows[i].changeStandBookList[j][key2]);
                                }
                            }
                        }
                    }
                }
                return sum.toFixed(2);
            } else {
                return 0;
            }
        },
        getInstallBaseMultiplication: function (rows, key, key2) {
            if (rows) {
                var sum = 0;
                for (var i = 0, len = rows.length; i < len; i++) {
                    for (var j = 0, p = rows[i].installBaseChangeStandBookList.length; j < p; j++) {
                        if (rows[i].installBaseChangeStandBookList[j][key2] == 1) {
                            if (!isNaN(rows[i].installBaseChangeStandBookList[j][key])) {
                                sum += (rows[i].installBaseChangeStandBookList[j][key]);
                            }
                        }else {
                            sum -= (rows[i].installBaseChangeStandBookList[j][key]);
                        }
                    }
                }
                return sum.toFixed(2);
            } else {
                return 0;
            }
        }
    },
    computed: {
        //变更合计
        mainMaterialChangeTotalAmount: function () {
            return this.getMultiplication(this.changes, 'nowPdTotal', 'pdTotal', 'itemType','nowPdCount','quotaWay');
        },
        mainInstallBaseChangeTotalAmount: function () {
            return this.getInstallBaseMultiplication(this.installBaseChanges, 'unitProjectTotalPrice', 'changeType');
        },
        //变更总次数
        totalCount:function () {
            return this.getCount();
        },
        //变更总金额
        totalChangeMoney:function () {
            return (parseFloat(this.mainMaterialChangeTotalAmount)+parseFloat(this.mainInstallBaseChangeTotalAmount)).toFixed(2);
        }
    },
    props: ['msg'],
    watch: {
        msg: function (curVal, oldVal) {
            if (curVal == 2 && !this.loaded) {
                this.fetchData();
                this.fetchChangeData();
            }
        }

    }
});