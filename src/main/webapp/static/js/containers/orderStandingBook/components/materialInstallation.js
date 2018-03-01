var MaterialInstallationComponent = Vue.extend({
    template: '#materialInstallationTmpl',
    data: function () {
        return {
            orderNo: null,
            // 主材安装
            principalInstall: '',
            // 复尺
            review: '',
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
        reviewStatus: function (val) {
            switch (val) {
                case 'NORIVEEWSIZE' :
                    return '未复尺';
                case 'YESRIVEEWSIZE' :
                    return '已复尺';
                case 'REJECT' :
                    return '已驳回';
            }
        }
    },
    methods: {
        // 获取数据, interfaceName接口名字，colums定义表数据的数组, key接收数据的对象
        fetchContractAudit: function () {
            var self = this;
            this.$http.get('/api/principalInstallStandBook/findPrincipalInstall?orderno=' + this.orderNo).then(function (res) {
                if (this.msg == 11) {
                    self.principalInstall = res.data;
                    for (var i = 0; i < self.principalInstall.length; i++) {
                        if (self.principalInstall[i].completeDelayDays != 0) {
                            if (self.principalInstall[i].completeDelayDays != undefined) {
                                if (self.principalInstall[i].completeDelayDays != null) {
                                    self.principalInstall[i].isCompleteDelay = '是';
                                }
                            }
                        } else {
                            self.principalInstall[i].completeDelayDays = 0;
                            self.principalInstall[i].isCompleteDelay = '否';
                        }
                    }
                    self.loaded = true;
                }
            })
        },
        fetchChangeContractAudit: function () {
            var self = this;
            this.$http.get('/api/principalInstallStandBook/findReview?contractNo=' + this.orderNo).then(function (res) {
                if (this.msg == 11) {
                    self.review = res.data;
                    self.loaded = true;
                }
            })
        }
    },
    watch: {
        msg: function (curVal, oldVal) {
            if (curVal == 11 && !this.loaded) {
                this.fetchContractAudit();
                this.fetchChangeContractAudit();
            }
        }

    }
});