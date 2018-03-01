var vueIndex = null;
$('#leveAndBusinessMenu').addClass('active');
$('#paymentApply').addClass('active');
+(function (RocoUtils, moment) {
    var vueIndex = new Vue({
        el: '#container',
        // 模式窗体必须引用 ModalMixin
        mixins: [RocoVueMixins.ModalMixin],
        data: {
            //报销科目大类
            types: [{'id': 1, 'name': "人事类", 'type': 'MATTERS'},
                {'id': 2, 'name': "营销类", 'type': 'MARKETING'},
                {'id': 3, 'name': "行政类", 'type': 'ADMINISTRATOR'},
                {'id': 4, 'name': "财务类", 'type': 'FINANCE'},
                {'id': 5, 'name': "客管类", 'type': 'CUSTOMER'},
                {'id': 6, 'name': "其他类", 'type': 'OTHERS'}],
            //APPROVALING("审核中"), ADOPT("通过"), REFUSE("拒绝"), DRAFT("草稿");
            //控制 按钮是否可点击
            subjects:[],
            attachments : [],//附件列表
            disabled: false,
            submitBtnClick: false,
            user: '',
            title: '',
            //用来记录watch次数的变量，第一次watch不清楚费用科目和科目明细
            watchNumber: 0,

            isEdit: '',
            payment: null,
            applyDate: '',
            //费用单相关变量
            signatures: [],
            budgetRemains: {},
            surpassBudget: false,
            surpassMessage: '',

            //用户新增子项
            $costItemModal: '',
            isWeChat: isWeChat,
            costItems: [],
            costDetails: [],
            newPaymentItem: {},
            currentCostDetail: '',
            addBtnClick: false,
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
        validators: {
            num: {
                message: '请输入正确的金额，例（10或10.00）',
                check: function (val) {
                	return /^([1-9]\d{0,9}|0)([.]?|(\.\d{1,2})?)$/.test(val);
                }
            },
            ticket: {
                message: '请输入正确的票据张数',
                check: function (val) {
                    return  /^[1-9]\d*$/.test(val);
                }
            }
        },
        components: {
            'web-uploader': RocoVueComponents.WebUploaderComponent
        },
        events: {
            'webupload-upload-success-sub': function (file, res) {
                if (res.code == '1') {
                    this.$toastr.success('上传成功');
                    this.attachments.push(res.data);// 附件
                    this.payment.attachment = res.data.path;
                } else {
                    this.$toastr.error(res.message);
                }
            }
        },
        created: function () {
        	this.initDictType();
        },
        ready: function () {
            //初始化类型
            this.initParams();
            this.user = window.RocoUser;
            this.activeDatepicker();
        },
        watch: {
            'payment.paymentDate': function (val, old) {
                if (this.watchNumber != 0) {
                    this.payment.signatureId = '';
                }
                this.getSignatures();
                this.getRemains();
            },
            'payment.type': function (val, oldVal) {
                this.costItems = [];
                if (val) {
                    this.getCostItems(val);
                }
                console.log("costItem:" + oldVal +" , " + val);
                //清空费用小项和费用明细列表及费用对象中的费用小项及明细列表
                if (this.watchNumber != 0) {
                    this.payment.costItem = '';
                }
                //获取费用单列表
                this.getSignatures();
                //构建申请标题
                this.buildApplyTitle();
                //获取预算剩余
                this.getRemains();
            },
            'payment.costItem': function (val, oldVal) {
                if (val && val != '') {
                    this.getCostDetails(val);
                }
                if (this.watchNumber != 0) {
                    //清空科目明细及选择的科目明细
                    this.costDetails = [];
                    this.payment.paymentDetails = [];
                }
                this.watchNumber++;

            },
            'payment.signatureId': function (val, old) {
                this.getRemains();
            }
        },
        computed: {},
        methods: {
            //删除
            removeImg : function(src,index){
  				var self = this;
  				self.attachments.splice(index, 1)
  			},
        	initDictType : function(){
           	 var self = this;
           	 //费用科目分类
           	 self.$http.get('/api/dict/list?searchType=1').then(function (res) {
           		var data = res.data.data.rows;
           		$.each(data, function(index,val) {
           			var obj = {};
           			obj.id = val['id'];
           			obj.name = val['name'];
           			self.subjects.push(obj);
           		});
               });
           },
            //根据地址栏参数，初始化数据
            initParams: function () {
                var self = this;
                var params = RocoUtils.parseQueryString(window.location.search.substr(1));
                var id = params['id'];
                if (id) {
                    self.isEdit = true;
                } else {
                    self.isEdit = false;
                }
                self.buildPayment(id);
                self.applyDate = self.getCurrentTime();//当前时间
            },
            /**
             * 构建申请标题
             */
            buildApplyTitle: function () {
                var self = this;
                var typeName = "";
                self.types.forEach(function (type) {
                    if (self.payment.type == type.id) {
                        typeName = type.name;
                    }
                });
                if (self.isEdit) {
                    self.title = "编辑" + typeName + "报销申请";
                } else {
                    self.title = "新增" + typeName + "报销申请";
                }
            },
            //构建报销数据
            buildPayment: function (id) {
                var self = this;
                if (id) {
                    self.$http.get('/api/payments/' + id + '/get').then(function (res) {
                        if (res.data.code == 1) {
                            self.payment = res.data.data;
                            self.attachments = $.parseJSON(res.data.data.attachment);
                            //报销单主体上没有科目信息，从明细表取第一个就行，因为都在一个科目上
                            if(null != self.payment.paymentDetails && self.payment.paymentDetails.length > 0){
                            	self.payment.costItem = self.payment.paymentDetails[0].costItemId
                            }
                            self.getRemains();
                        } else {
                            //提示用户
                        }
                    });
                } else {
                    self.payment = {
                        type: '',
                        costItem: '',
                        totalMoney: 0,
                        invoiceNum: 0,
                        signatureId: "",
                        surpassBudget: 0,
                        applyCode: '',
                        attachment: '',
                        paymentDate: self.getLastMonth(),
                        paymentDetails: []
                    };
                    self.getApplyCode()
                }
            },
            getLastMonth: function () {
                return moment().add('month', -1).format('YYYY-MM');
            },
            getCurrentTime: function () {
                return moment().format('YYYY年MM月DD日 HH:mm:ss');
            },
            activeDatepicker: function () {
                var self = this;
                $(self.$els.paymentDate).datetimepicker({
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
             * 上传附件
             */
            deleteAttachment: function () {
                var self = this;
                self.$http.delete('/api/upload', {
                    params: {
                        path: self.payment.attachment
                    }
                }).then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.payment.attachment = '';
                    } else {
                        self.$toastr.error("删除失败");
                    }
                });
            },
            getApplyCode: function () {
                var self = this;
                self.$http.get('/api/payments/getApplyCode').then(function (response) {
                    var result = response.data;
                    if (result.code == '1') {
                        self.payment.applyCode = result.data;
                    }
                });
            },
            getSignatures: function () {
                var self = this;
                var id = (self.isEdit) ? self.payment.id : '';
                self.$http.get('/api/signatures/getSignaturesByMonth?month=' + self.payment.paymentDate + '&paymentId=' + id).then(function (response) {
                    var result = response.data;
                    if (result.code == '1') {
                        self.signatures = result.data;
                    }
                });
            },
            //查询预算剩余
            getRemains: function () {
                var self = this;
                if (self.payment.type) {
                    var signatureId = "";
                    if (RocoUtils.isNotEmpty(self.payment.signatureId)) {
                        signatureId = self.payment.signatureId;
                    }
                    self.$http.get('/api/payments/getRemain?signatureId=' + signatureId + '&type=' + self.payment.type + '&month=' + self.payment.paymentDate).then(function (response) {
                        var result = response.data;
                        if (result.code == '1') {
                            self.budgetRemains = result.data;
                            self.calculation();
                        }
                    });
                } else {
                    self.budgetRemains = {};
                }
            },
            /**
             * 获取费用小类
             */
            getCostItems: function (parentId) {
                var self = this;
                self.$http.get('/api/dict/getByTypeFilter?type=2&parentType=' + parentId).then(function (res) {
                    if (res.data.code == 1) {
                        self.costItems = res.data.data;
                    }
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
                });
            },
            //显示添加费用子项弹窗
            showAddItemModal: function () {
                var _$modal = $('#modal');
                var $costItemModal = _$modal.modal({
                    height: 450,
                    maxHeight: 450,
                    backdrop: 'static',
                    keyboard: false
                });
                this.$costItemModal = $costItemModal;
            },
            //删除子项
            deleteItem: function (index) {
                var self = this;
                self.payment.paymentDetails.splice(index, 1);
                self.calculation();
            },
            // 添加
            addNew: function () {
                var self = this;
                self.addBtnClick = true;
                self.$validate(true, function () {

                    if (self.$additemvalidation.valid) {
                        //check当前费用小类和费用明细是否已经存在在报销列表中
                        var flag = false
                        self.payment.paymentDetails.forEach(function (costItem) {
                            if (costItem.costDetailId == self.currentCostDetail.id) {
                                Vue.toastr.warning('已经添加了该费用小类');
                                flag = true
                            }
                        });
                        if (flag) return false;
                        //构建报销子项
                        var paymentItem = {
                            costDate: self.newPaymentItem.costDate,
                            invoiceNum: self.newPaymentItem.invoiceNum,
                            money: self.newPaymentItem.money,
                            remark: self.newPaymentItem.remark,
                            costItemId: self.payment.costItem,
                            costDetailId: self.currentCostDetail.id,
                            costDetailName: self.currentCostDetail.name
                        };
                        self.payment.paymentDetails.push(paymentItem);
                        self.calculation();
                        self.newPaymentItem = {};

                        self.$costItemModal.modal('hide');
                        self.addBtnClick = false;
                    }
                });


            },
            //计算报销总额及票据总数
            calculation: function () {
                var self = this;
                var totalMoney = 0;
                var totalInvoiceNum = 0;
                _.forEach(self.payment.paymentDetails, function (item) {
                    totalMoney += (typeof item.money == 'string' ? parseFloat(item.money) : item.money);
                    totalInvoiceNum += (typeof item.invoiceNum == 'string' ? parseInt(item.invoiceNum) : item.invoiceNum);
                });
                self.payment.totalMoney = totalMoney.toFixed(2);
                self.payment.invoiceNum = totalInvoiceNum;

                self.checkHasSurpass();
            },
            checkHasSurpass: function () {
                var self = this;
                self.surpassBudget = false;
                self.surpassMessage = null;
                var currentMoneys = {};
                _.forEach(self.payment.paymentDetails, function (item) {
                    var costItemMoney = currentMoneys[item.costItemId];
                    if (RocoUtils.isEmpty(costItemMoney)) {
                        costItemMoney = 0;
                    }
                    costItemMoney = parseFloat(item.money) + parseFloat(costItemMoney);
                    if (costItemMoney > parseFloat(self.budgetRemains[item.costItemId])) {
                        self.surpassBudget = true;
                        self.surpassMessage = '超出预算';
                    }
                });
            },

            //提交报销
            submitOrSave2Draft: function (status) {
                var self = this;
                self.submitBtnClick = true;

                self.$validate(true, function () {
                    console.log(1)
                    if (self.$validation.valid) {
                        self.disabled = true;
                        if (self.payment.paymentDetails && self.payment.paymentDetails.length == 0) {
                            Vue.toastr.warning('至少填写一项费用小项');
                            self.disabled = false;
                            return false;
                        }
                        //添加状态
                        self.payment.status = status;
                        //是否超预算
                        if (self.surpassBudget) {
                            self.payment.surpassBudget = 1;
                        } else {
                            self.payment.surpassBudget = 0;
                        }
            			 var jsonAtt = JSON.stringify(self.attachments);
            			 self.payment.attachment = jsonAtt;
                        self.$http.post('/api/payments',
                            self.payment
                        ).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                setTimeout(window.location.href = '/admin/businessAway/leaveAndBusiness', 2000);
                            } else {
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
                window.location.href = '/admin/businessAway/leaveAndBusiness';
            }
        }
    });
})
(this.RocoUtils, moment);