var CustomerManagementComponent = Vue.extend({
    template: '#customerManagementTmpl',
    data: function () {
        return {
            orderNo: null,
            completCount: null,
            workCount: null,
            noCompletCount: null,
            // 客管表数据
            customerManagement: '',
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
        goType: function (val) {
            if (val == 'CREATE') {
                return '未派单';
            } else if (val == 'RECEIVED') {
                return '已接收'
            } else if (val == 'PROCESSING') {
                return '处理中';
            } else if (val == 'REFUSED') {
                return '驳回'
            } else if (val == 'REFUSEDAGAIN') {
                return '被驳回';
            } else if (val == 'PENDING') {
                return '待处理'
            } else if (val == 'SATISFIED') {
                return '回访满意';
            } else if (val == 'COMMONLY') {
                return '一般满意';
            } else if (val == 'UNSATISFIED') {
                return '回访不满意'
            } else if (val == 'INVALIDVISIT') {
                return '无效回访';
            } else if (val == 'FAILUREVISIT') {
                return '回访不成功'
            } else if (val == 'ASSIGN') {
                return '待分配'
            } else if (val == 'URGE') {
                return '催单';
            } else if (val == 'NREPLY') {
                return '待回复'
            } else if (val == 'NVISIT') {
                return '待回访'
            } else if (val == 'CONSULTOVER') {
                return '咨询完毕'
            } else if (val == 'UNEXECUTED') {
                return '回访未执行'
            } else if (val == 'COMPLETED') {
                return '已完成';
            }
        }
    },
    methods: {
        // 获取数据, interfaceName接口名字，colums定义表数据的数组, key接收数据的对象
        fetchData: function () {
            var self = this;
            this.$http.get('/api/customerManagement/getCustomerManagement?orderno=' + this.orderNo).then(function (res) {
                if(this.msg==8){
                    self.customerManagement = res.data;
                    if (res.data.length > 0) {
                        self.completCount = res.data[0].completCount;
                        self.workCount = res.data[0].workCount;
                        self.noCompletCount = self.workCount - self.completCount;
                    }
                    self.loaded = true;
                }
            })
        }
    },
    watch: {
        msg: function (curVal, oldVal) {
            if (curVal == 8 && !this.loaded) {
                this.fetchData();
            }
        }

    }
});