var vueIndex = null;
+(function (RocoUtils, moment) {
    var vueIndex = new Vue({
        el: '#container',
        // 模式窗体必须引用 ModalMixin
        mixins: [RocoVueMixins.ModalMixin],
        data: {
            //报销科目大类
            types: {
                'MATTERS': {'id': 1, 'name': "人事类"},
                'MARKETING': {'id': 2, 'name': "营销类"},
                'ADMINISTRATOR': {'id': 3, 'name': "行政类"},
                'FINANCE': {'id': 4, 'name': "财务类"},
                'CUSTOMER': {'id': 5, 'name': "客管类"},
                'OTHERS': {'id': 6, 'name': "其他类"}
            },
            //APPROVALING("审核中"), ADOPT("通过"), REFUSE("拒绝"), DRAFT("草稿");
            //控制 按钮是否可点击
            disabled: false,
            submitBtnClick: false,
            isWeChat: isWeChat,
            user: '',
            title: '',
            currentType: '',
            isEdit: '',
            budget: null,
            applyDate: '',
            costItems: [],
            costDetails: [],
            addBtnClick: false,
            //预算总额
            totalMoney: 0,

            webUploaderSub: {
                type: 'sub',
                formData: {},
                accept: {
                    title: '文件',
                    extensions: '*'
                },
                server: ctx + '/api/upload',
                //上传路径
                fileNumLimit: 1,
                fileSizeLimit: 50000 * 1024,
                fileSingleSizeLimit: 5000 * 1024
            }
        },
        created: function () {

        },
        ready: function () {
            //初始化类型
            this.initParams();
            this.user = window.RocoUser;
            this.activeDatepicker();
        },
        watch: {},
        components: {
            'web-uploader': RocoVueComponents.WebUploaderComponent
        },
        events: {
            'webupload-upload-success-sub': function (file, res) {
                if (res.code == '1') {
                    this.$toastr.success('上传成功');
                    this.budget.attachment = res.data.path;
                } else {
                    this.$toastr.error(res.message);
                }
            }
        },
        computed: {
            'totalMoney': function () {
                var self = this;
                var num = 0;
                _.forEach(self.budget.budgetDetails, function (item) {
                    num += (isNaN(item.money) || item.money == null || item.money == '') ? 0 : (typeof item.money == 'string' ? parseFloat(item.money) : item.money)
                });
                self.budget.totalMoney = num;
                return num;
            }
        },
        methods: {
            //根据地址栏参数，初始化数据,激活菜单样式
            initParams: function () {
                var self = this;
                var params = RocoUtils.parseQueryString(window.location.search.substr(1));
                var type = params['type'];
                self.currentType = self.types[type];
                var id = params['id'];
                if (type) {
                    self.addActiveClass(type);
                    var typeName = self.currentType.name;
                    if (id) {
                        self.title = '编辑' + typeName + '预算申请';
                        self.isEdit = true;
                    } else {
                        self.isEdit = false;
                        self.title = '新增' + typeName + '预算申请';
                    }
                    self.buildBudget(id, type);
                } else {
                    //错误提示
                }
                self.applyDate = self.getCurrentTime();//当前时间
            },
            addActiveClass: function (type) {
                $('#budgetMenu').addClass('active');
                switch (type) {
                    case 'MATTERS':
                        $('#mattersBudgetApply').addClass('active');
                        break;
                    case 'MARKETING':
                        $('#marketingBudgetApply').addClass('active');
                        break;
                    case 'ADMINISTRATOR':
                        $('#adminBudgetApply').addClass('active');
                        break;
                    case 'FINANCE':
                        $('#financeBudgetApply').addClass('active');
                        break;
                    case 'CUSTOMER':
                        $('#customerBudgetApply').addClass('active');
                        break;
                    case 'OTHERS':
                        $('#othersBudgetApply').addClass('active');
                        break;
                    default:
                        $('#budgetAffair').addClass('active');
                        break;

                }
            },
            //构建
            buildBudget: function (id, type) {
                var self = this;
                if (id) {
                    self.$http.get('/api/budgets/' + id + '/get').then(function (res) {
                        if (res.data.code == 1) {
                            self.budget = res.data.data;
                        } else {
                            //提示用户
                        }
                    });
                } else {
                    self.budget = {
                        type: type,
                        totalMoney: 0,
                        attachment:'',
                        budgetDate: self.getLastMonth(),//上个月时间
                        budgetDetails: []
                    };
                    //构建费用科目
                    this.initBudgetDetails(self.currentType.id);
                }
            },
            initBudgetDetails: function (parentId) {
                var self = this;
                self.$http.get('/api/dict/getByTypeFilter?type=2&parentType=' + parentId).then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                        return res.data;
                    }
                }).then(function (data) {
                    self.$http.get('/api/dict/getDictsByTypeFilter?type=3').then(function (res) {
                        var costDetails = res.data.data;
                        var budgetDetails = [];
                        data.forEach(function (costItem) {
                            var budgetDetail = self.buildBudgetDetail(costItem, costDetails);
                            budgetDetails.push(budgetDetail);
                        });
                        self.budget.budgetDetails = budgetDetails;
                    });
                });
            },
            buildBudgetDetail: function (costItem, costDetails) {
                var costDetailNames = '';
                costDetails.forEach(function (detail) {
                    if (detail.parentCode == costItem.id) {
                        costDetailNames += (detail.name + ',');
                    }
                });
                costDetailNames = costDetailNames.substring(0, costDetailNames.length - 1);
                var budgetDetail = {
                    costItemId: costItem.id,
                    costItemName: costItem.name,
                    costDetailNames: costDetailNames,
                    money: 0.0,
                    remark: ''
                }
                return budgetDetail;
            },
            getLastMonth: function () {
                return moment().add('month', -1).format('YYYY-MM');
            },
            getCurrentTime: function () {
                return moment().format('YYYY年MM月DD日 HH:mm:ss');
            },
            activeDatepicker: function () {
                var self = this;
                $(self.$els.budgetDate).datetimepicker({
                    startView: 3,//启始视图显示年视图
                    minView: "year", //选择日期后，不会再跳转去选择时分秒
                    format: 'yyyy-mm',
                    todayBtn: true
                });
                $(self.$els.costDate).datetimepicker({
                    minView: "month", //选择日期后，不会再跳转去选择时分秒
                    format: 'yyyy-mm-dd',
                    todayBtn: true
                });
            },
            /**
             * 根据费用小类获取费用详情列表
             * @param parentId 费用小类id
             */
            getCostDetails: function (parentId) {
                var self = this;
                self.$http.get('/api/dict/getByTypeFilter?type=3&parentType=' + parentId).then(function (res) {
                    self.costDetails = res.data.data;
                    if (self.costDetails && self.costDetails.length > 0) {
                        self.currentCostDetail = self.costDetails[0];
                    } else {
                        self.currentCostDetail = '';
                    }
                });
            },
            /**
             * 上传附件
             */
            deleteAttachment:function(){
                var self = this;
                self.$http.delete('/api/upload', {
                    params: {
                        path: self.budget.attachment
                    }
                }).then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.budget.attachment = '';
                    } else {
                        self.$toastr.error("删除失败");
                    }
                });
            },
            //计算报销总额及票据总数
            calculation: function () {
                var self = this;
                var totalMoney = 0;
                var totalInvoiceNum = 0;
                _.forEach(self.budget.budgetDetails, function (item) {
                    totalMoney += (typeof item.money == 'string' ? parseFloat(item.money) : item.money);
                    totalInvoiceNum += (typeof item.invoiceNum == 'string' ? parseInt(item.invoiceNum) : item.invoiceNum);
                });
                self.budget.totalMoney = totalMoney;
                self.budget.invoiceNum = totalInvoiceNum;
            },

            //提交报销
            submitOrSave2Draft: function (status) {
                var self = this;
                self.submitBtnClick = true;
                self.$validate(true, function () {
                    if (self.$validation.valid) {
                        self.disabled = true;
                        if (self.budget.budgetDetails && self.budget.budgetDetails.length == 0) {
                            Vue.toastr.warning('至少填写一项费用小项');
                            self.disabled = false;
                            return false;
                        }
                        //添加状态
                        self.budget.status = status;
                        self.$http.post('/api/budgets',
                            self.budget
                        ).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                setTimeout(window.location.href = '/admin/budgets', 2000);
                            }else{
                            	self.$toastr.error(res.data.message);
                            }
                        }).finally(function () {
                            self.disabled = false;
                            self.submitBtnClick = false;
                        });
                    }
                });
            },
            cancel: function () {
                window.location.href = '/admin/budgets';
            }
        }
    });
})
(this.RocoUtils, moment);