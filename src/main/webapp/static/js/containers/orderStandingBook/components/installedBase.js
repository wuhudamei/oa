var InstalledBaseComponent = Vue.extend({
    template: '#installedBaseTmpl',
    data: function () {
        return {
            packageInfo: null,
            pmSettleInfo: null,
            selectMaterialStandBookDetails: null,
            //基装主材
            installBasePrincipal:[],
            //基装辅材
            installBaseAuxiliary:[],
            orderNo: null,
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
            if (curVal == 10 && !this.loaded) {
                this.fetchInstallBase();
                this.fetchInstallBasePm();
                this.fetchInstallBaseDetail();
            }
        }

    },
    filters: {
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
        //基装成本
        fetchInstallBase: function () {
            var self = this;
            self.$http.post("/api/standBook/installedBase?orderId=" + this.orderNo).then(function (res) {
                if (this.msg == 10) {
                    this.packageInfo = res.data.packageInfo;
                    self.loaded = true;
                }
            }, function (res) {
            })
        },
        //提成
        fetchInstallBasePm: function () {
            var self = this;
            self.$http.post("/api/standBook/installedBasePm?orderId=" + this.orderNo).then(function (res) {
                if (this.msg == 10) {
                    this.pmSettleInfo = res.data.pmSettleInfo;
                    self.loaded = true;
                }
            }, function (res) {
            })
        },
        //基装材料
        fetchInstallBaseDetail: function () {
            var self = this;
            self.$http.post("/api/materialCostStandBook/auxiliaryMaterial?orderno=" + this.orderNo).then(function (res) {
                if (this.msg == 10) {
                    this.selectMaterialStandBookDetails = res.data.data.rows;
                    for(var i = 1 ; i<this.selectMaterialStandBookDetails.length;i++){
                        if(this.selectMaterialStandBookDetails[i].auxiliaryMaterialflagName == '开关面板' || this.selectMaterialStandBookDetails[i].auxiliaryMaterialflagName == '电源' || this.selectMaterialStandBookDetails[i].auxiliaryMaterialflagName == '灯带' || this.selectMaterialStandBookDetails[i].auxiliaryMaterialflagName == '筒灯'){
                            this.installBasePrincipal.push(this.selectMaterialStandBookDetails[i]);
                        }else {
                            this.installBaseAuxiliary.push(this.selectMaterialStandBookDetails[i]);
                        }
                    }
                    self.loaded = true;
                }
            }, function (res) {
            })
        }
    }
});