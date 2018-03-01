var AuditComponent = Vue.extend({
    template: '#auditTmpl',
    data: function () {
        return {
            orderNo: null,
            // 合同
            contractAudit: '',
            //变更
            changeAudit: '',
            areaLevelInfo: {
                floorTileBudgetArea:'',
                floorTileSettleArea:'',
                dosage:'',
                floorSettleArea:'',
                measurehousearea:'',
                areaStatus:''
            },
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
                return moment(el).format('YYYY-MM-DD HH:mm:ss');
            }
            else {
                return '-';
            }
        },
        doChange: function (val) {
            if (val) {
                return val;
            } else {
                return 0;
            }
        }
    },
    methods: {
        // 获取数据, interfaceName接口名字，colums定义表数据的数组, key接收数据的对象
        fetchContractAudit: function () {
            var self = this;
            this.$http.get('/api/auditStandBook/contractAudit?orderno=' + this.orderNo).then(function (res) {
                if (this.msg == 5) {
                    if (res.data.code == 1) {
                        self.contractAudit = res.data.data;
                    }
                }
                self.loaded = true;
            })
        },
        fetchChangeContractAudit: function () {
            var self = this;
            this.$http.get('/api/auditStandBook/changeAudit?orderno=' + this.orderNo).then(function (res) {
                if (this.msg == 5) {
                    if (res.data.code == 1) {
                        self.changeAudit = res.data.data;
                    }
                }
                self.loaded = true;
            })
        },
        fetchAreaLevelInfo: function () {
            var self = this;
            this.$http.get('/api/qualityCheckStandBook/findAreaLevelInfo?orderNumber=' + this.orderNo).then(function (res) {
                if (this.msg == 5) {
                    self.areaLevelInfo = res.data[0];
                    if(self.areaLevelInfo != null){
                        if(self.areaLevelInfo.floorTileSettleArea + self.areaLevelInfo.floorSettleArea > self.areaLevelInfo.measurehousearea){
                            self.areaLevelInfo.areaStatus = "有异常";
                        }else if(self.areaLevelInfo.floorTileSettleArea + self.areaLevelInfo.floorSettleArea <= self.areaLevelInfo.measurehousearea){
                            self.areaLevelInfo.areaStatus = "正常";
                        }
                    }
                    self.loaded = true;
                }
            })
        }
    },
    watch: {
        msg: function (curVal, oldVal) {
            if (curVal == 5 && !this.loaded) {
                this.fetchContractAudit();
                this.fetchChangeContractAudit();
                this.fetchAreaLevelInfo();
            }
        }

    }
});