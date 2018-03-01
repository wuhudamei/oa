var QualityTestingComponent = Vue.extend({
    template: '#qualityTestingTmpl',
    data: function () {
        return {
            orderNo: null,
            qualityCheck: '',
            check: '',
            repeatQualityCheck: '',
            /*areaLevelInfo: {
                floorTileBudgetArea:'',
                floorTileSettleArea:'',
                dosage:'',
                floorSettleArea:'',
                measurehousearea:'',
                areaStatus:''
            },*/
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
        actualStartDate: function (val) {
            if (val) {
                return moment(val).format('YYYY-MM-DD');
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
        fetchQualityCheck: function () {
            var self = this;
            this.$http.get('/api/qualityCheckStandBook/findQualityCheckByOrderNo?orderno=' + this.orderNo).then(function (res) {
                if (this.msg == 7) {
                    self.qualityCheck = res.data;
                    self.check = res.data[0];
                    self.loaded = true;
                }
            })
        },
        fetchRepeatQualityCheck: function () {
            var self = this;
            this.$http.get('/api/qualityCheckStandBook/findRepeatQualityCheck?orderno=' + this.orderNo).then(function (res) {
                if (this.msg == 7) {
                    self.repeatQualityCheck = res.data;
                    self.loaded = true;
                }
            })
        }/*,
        fetchAreaLevelInfo: function () {
            var self = this;
            this.$http.get('/api/qualityCheckStandBook/findAreaLevelInfo?orderNumber=' + this.orderNo).then(function (res) {
                if (this.msg == 7) {
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
        }*/
    },
    watch: {
        msg: function (curVal, oldVal) {
            if (curVal == 7 && !this.loaded) {
                this.fetchQualityCheck();
                this.fetchRepeatQualityCheck();
                //this.fetchAreaLevelInfo();
            }
        }

    }
});