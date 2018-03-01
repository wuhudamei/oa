var vueIndex = null;
$('#leveAndBusinessMenu').addClass('active');
$('#signatureApply').addClass('active');
+(function (RocoUtils, moment) {
    var vueIndex = new Vue({
            el: '#container',
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            data: {
                //报销科目大类　,改为从后台获取
                types: [{'id': 1, 'name': "人事类", 'type': 'MATTERS'},
                    {'id': 2, 'name': "营销类", 'type': 'MARKETING'},
                    {'id': 3, 'name': "行政类", 'type': 'ADMINISTRATOR'},
                    {'id': 4, 'name': "财务类", 'type': 'FINANCE'},
                    {'id': 5, 'name': "客管类", 'type': 'CUSTOMER'},
                    {'id': 6, 'name': "其他类", 'type': 'OTHERS'}],
                subjects:[],
                attachments : [],
                //APPROVALING("审核中"), ADOPT("通过"), REFUSE("拒绝"), DRAFT("草稿");
                //控制 按钮是否可点击
                disabled: false,
                submitBtnClick: false,
                $dataTable: null,
                user: '',
                //watch执行的次数，用于第一次不执行清空费用科目和科目明细列表逻辑
                watchNumber: 0,

                currentType: null,
                title: '',
                isEdit: false,
                signature: null,
                applyDate: '',
                //计算是否超预算的相关变量
                budgetRemains: null,
                surpassBudget: false,
                surpassMessage: null,

                //用户新增子项
                $costItemModal: '',
                costItems: [],
                costDetails: [],
                newSignatureItem: {},
                currentCostDetail: '',
                newMoney: '',
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
                        this.signature.attachment = res.data.path;
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
                this.initDictType();
                this.user = window.RocoUser;
                this.activeDatepicker();
            },
            watch: {
//            	'subjects': function (val, oldVal) {
//            		alert('subjects');
//            	},
                'signature.type': function (val, oldVal) {
                    this.costItems = [];
                    if (val) {
                        this.getCostItems(val);
                    }
                    //清空费用小项和费用明细列表及费用对象中的费用小项及明细列表
                    if (this.watchNumber != 0) {
                        this.signature.costItem = '';
                    }
                    //构建申请标题
                    this.buildApplyTitle();
                    //获取当前选中的type
                    this.getCurrentType();
                    //获取预算剩余
                    this.getRemains();
                },
                'signature.costItem': function (val, oldVal) {
                    if (val) {
                        this.getCostDetails(val);
                    }
                    //清空费用明细列表，及当前选中的费用列表
                    this.costDetails = [];
                    if (this.watchNumber != 0) {
                        this.signature.signatureDetails = [];
                    }
                    //增加watch次数，以后都执行清空费用科目、费用明细操作
                    this.watchNumber++;
                },
                'signature.signatureDate': function (val, old) {
                    this.getRemains();
                }
            },
            computed: {
            	
            },
            methods: {
    			removeImg : function(src,index){
    				var self = this;
    				self.attachments.splice(index, 1);
    			},
                /**
                 * 获取申请编号
                 */
                initApplyCode: function () {
                    var self = this;
                    self.$http.get('/api/signatures/getApplyCode').then(function (response) {
                        var result = response.data;
                        if (result.code == 1) {
                            self.signature.applyCode = result.data;
                        }
                    });
                }
                ,
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
                    self.buildSignature(id);
                    self.applyDate = self.getCurrentTime();//当前时间
                } ,
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
                /**
                 * 构建申请标题
                 */
                buildApplyTitle: function () {
                    var self = this;
                    var typeName = "";
                    self.types.forEach(function (type) {
                        if (self.signature.type == type.id) {
                            typeName = type.name;
                        }
                    });
                    if (self.isEdit) {
                        self.title = "编辑" + typeName + "费用申请单";
                    } else {
                        self.title = "新增" + typeName + "费用申请单";
                    }
                }
                ,
                /**
                 * 根据typeId获取当前选中的type
                 * @param typeId
                 */
                getCurrentType: function () {
                    var self = this;
                    if (self.signature.type) {
                        self.types.forEach(function (type) {
                            if (self.signature.type == type.id) {
                                self.currentType = type;
                            }
                        });
                    } else {
                        self.currentType = null;
                    }
                }
                ,
                //构建费用数据
                buildSignature: function (id) {
                    var self = this;
                    if (id) {
                        self.$http.get('/api/signatures/' + id + '/get').then(function (res) {
                            if (res.data.code == 1) {
                            	var attachment = res.data.data.attachment;
                            	//修改理处理附件
                            	self.attachments = $.parseJSON(res.data.data.attachment);
                                self.signature = res.data.data;
                                self.getRemains();
                            } else {
                                //提示用户
                            }
                        });
                    } else {
                        self.signature = {
                            type: '',
                            costItem: '',
                            applyCode: '',
                            totalMoney: 0,
                            surpassBudget: 0,
                            attachment: '',
                            signatureDate: self.getLastMonth(),
                            signatureDetails: []
                        };
                        self.initApplyCode();
                    }
                }
                ,
                getLastMonth: function () {
                    return moment().add('month', -1).format('YYYY-MM');
                }
                ,
                getCurrentTime: function () {
                    return moment().format('YYYY年MM月DD日 HH:mm:ss');
                }
                ,
                activeDatepicker: function () {
                    var self = this;
                    $(self.$els.signatureDate).datetimepicker({
                        startView: 3,//启始视图显示年视图
                        minView: "year", //选择日期后，不会再跳转去选择时分秒
                        format: 'yyyy-mm',
                        todayBtn: true
                    });
                }
                ,
                /**
                 * 上传附件
                 */
                deleteAttachment: function () {
                    var self = this;
                    self.$http.delete('/api/upload', {
                        params: {
                            path: self.signature.attachment
                        }
                    }).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            self.signature.attachment = '';
                        } else {
                            self.$toastr.error("删除失败");
                        }
                    });
                }
                ,
                getRemains: function () {
                    var self = this;
                    if (self.currentType) {
                        self.$http.get('/api/signatures/getBudgetRemain?type=' + self.currentType.id + '&month=' + self.signature.signatureDate).then(function (response) {
                            var result = response.data;
                            if (result.code == '1') {
                                self.budgetRemains = result.data;
                                self.calculation();
                            }
                        });
                    } else {
                        self.budgetRemains = {};
                    }
                }
                ,
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
                }
                ,
                /**
                 * 根据费用小类获取费用详情列表
                 * @param parentId 费用小类id
                 */
                getCostDetails: function (parentId) {
                    var self = this;
                    self.$http.get('/api/dict/getByTypeFilter?type=3&parentType=' + parentId).then(function (res) {
                        self.costDetails = res.data.data;
                    });
                }
                ,
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
                }
                ,
                //删除子项
                deleteItem: function (index) {
                    var self = this;
                    self.signature.signatureDetails.splice(index, 1);
                    self.calculation();
                }
                ,
                // 添加
                addNew: function () {
                    var self = this;
                    self.addBtnClick = true;
                    self.$validate(true, function () {
                        if (self.$additemvalidation.valid) {
                            //check当前费用小类和费用明细是否已经存在在报销列表中
                            var flag = false;
                            self.signature.signatureDetails.forEach(function (costItem) {
                                if (costItem.costDetailId == self.currentCostDetail.id) {
                                    Vue.toastr.warning('已经添加了该费用小类');
                                    flag = true
                                }
                            });
                            if (flag) return false;
                            //构建报销子项
                            var signatureItem = {
                                costItemId: self.signature.costItem,
                                money: self.newSignatureItem.money,
                                remark: self.newSignatureItem.remark,
                                costDetailId: self.currentCostDetail.id,
                                costDetailName: self.currentCostDetail.name
                            };
                            self.signature.signatureDetails.push(signatureItem);
                            self.calculation();
                            self.newSignatureItem = {};
                            self.$costItemModal.modal('hide');
                            self.addBtnClick = false;
                        }
                    });
                }
                ,
                //计算报销总额及票据总数
                calculation: function () {
                    var self = this;
                    var totalMoney = 0;
                    _.forEach(self.signature.signatureDetails, function (item) {
                        totalMoney += (typeof item.money == 'string' ? parseFloat(item.money) : item.money);
                    });
                    self.signature.totalMoney = totalMoney.toFixed(2);

                    //判断是否超出预算
                    self.checkHasSurpass();
                }
                ,
                checkHasSurpass: function () {
                    var self = this;
                    self.surpassBudget = false;
                    self.surpassMessage = null;
                    var currentMoneys = {};
                    _.forEach(self.signature.signatureDetails, function (item) {
                        var costItemMoney = currentMoneys[item.costItemId];
                        if (RocoUtils.isEmpty(costItemMoney)) {
                            costItemMoney = 0;
                        }
                        costItemMoney = parseFloat(item.money) + parseFloat(costItemMoney);
                        if (costItemMoney > parseFloat(self.budgetRemains[self.signature.costItem])) {
                            self.surpassBudget = true;
                            self.surpassMessage = '超出预算';
                        }
                    });
                }
                ,
                //提交报销
                submitOrSave2Draft: function (status) {
                    var self = this;
                    self.submitBtnClick = true;
                    
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.disabled = true;
                            if (self.signature.signatureDetails && self.signature.signatureDetails.length == 0) {
                                Vue.toastr.warning('至少填写一项费用小项');
                                self.disabled = false;
                                return false;
                            }
                            //添加状态
                            self.signature.status = status;
                            if (self.surpassBudget) {
                                self.signature.surpassBudget = 1;
                            }
                            //处理附件
                            var jsonAtt = JSON.stringify(self.attachments);
                            self.signature.attachment = jsonAtt;
                            self.$http.post('/api/signatures',self.signature).then(function (res) {
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
                }
                ,
                cancel: function () {
                    window.location.href = '/admin/businessAway/leaveAndBusiness';
                }
            }
        })
    ;
})
(this.RocoUtils, moment);