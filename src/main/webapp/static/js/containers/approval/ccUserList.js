var vueIndex = null;
+(function (RocoUtils) {
    $('#myNeedDo').addClass('active');
    $('#ccUser').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        mixins: [RocoVueMixins.DataTableMixin],
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '抄送',
                active: true //激活面包屑的
            }],
            // 查询表单
            form: {
                keyword: '',	//请假
                keywordSignature: '',//费用
                keywordPayment: '',//报销
                type: 'LEAVE',
                ccFlag:"1"
            },
            selectedRows: {}, //选中列
            modalModel: null, //模式窗体模型
            _$el: null, //自己的 el $对象
            _$dataTable: null, //datatable $对象
            _$dataTableSignature:null,
            _$dataTablePayment:null,
            weixin : false 
        },
        created: function () {
        },
        ready: function () {
            this.drawTable();
            this.clickEvent({key:0});
            this.isWeixin();
        },
        methods: {
      	  isWeixin: function () {
                var self = this;
                var ua = navigator.userAgent.toLowerCase();
                if (ua.match(/MicroMessenger/i) == "micromessenger") {
                    self.weixin = true;
                } else {
                    self.weixin = false;
                }
            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            querySignature: function () {
                this.$dataTableSignature.bootstrapTable('selectPage', 1);
            },
            queryPayment: function () {
                this.$dataTablePayment.bootstrapTable('selectPage', 1);
            },
            clickEvent: function (val) {//选项卡单击事件
            	if (val.key == 0) {
                    this.dataTableCommon();   //通用
                }else if (val.key == 1) {
                	this.drawTableSignature();  //费用
                }else if (val.key == 2) {
                	this.drawTable();  //请假
                }else if (val.key == 3) {
                    this.drawTablePayment();  //报销
                }
            },
            dataTableCommon: function () {
            	var self = this;
            	self.$dataTable = $('#dataTableCommon', self._$el).bootstrapTable({
            		url: '/api/applyCommon/list',
            		method: 'get',
            		dataType: 'json',
            		cache: false, //去缓存
            		pagination: true, //是否分页
            		sidePagination: 'server', //服务端分页
            		queryParams: function (params) {
            			// 将table 参数与搜索表单参数合并
            			params.status = 'ADOPT';//通过审批
            			params.dataType = 'CCPERSON'
            			return _.extend({}, params, self.form);
            		},
            		mobileResponsive: true,
            		undefinedText: '-', //空数据的默认显示字符
            		striped: true, //斑马线
            		maintainSelected: true, //维护checkbox选项
            		columns: [{
                        field: 'id',
                        title: 'ID',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'nodeId',
                        title: '当前节点',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'serial_number',
                        title: '申请编码',
                        align: 'center'
                    }, {
                        field: 'title',
                        title: '申请标题',
                        align: 'center',
                        formatter: function(value, row, index) {
                        	var url = '/admin/commonApply/info?id='+row.id
                        	return "<a href='"+url+"'>"+value+"</a>";
                        }
                    }, {
                        field: 'content',
                        title: '申请事由',
                        align: 'center',
                        formatter: function (value, row, index) {
                        	if(value.length > 10){
                        		var newStr = value.substr(0,10);
                        		return "<span data-toggle='tooltip' data-placement='top' title='"+value+"'>"+newStr+"&nbsp;&nbsp;•••</span>";
                        	}else{
                        		return value;
                        	}
                        }
                    }, {
                        field: 'apply_timehms',
                        title: '提交时间',
                        align: 'center'
                    }, {
                        field: 'applyUserId',
                        title: '申请人ID',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'submitUserName',
                        title: '申请人',
                        align: 'center'
                    }, {
                        field: 'subOrgName',
                        title: '申请人部门',
                        align: 'center'
                    }, {
                        field: 'orgName',
                        title: '申请人机构',
                        align: 'center'
                    }, {
                        field: 'nodeType',
                        title: '节点类型',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'type',
                        title: '流程类型',
                        align: 'center',
                        visible: false,
                        formatter: function(value, row, index) {
                            return formApproveStatus(value);
                        }
                    }, {
                        field: 'isSign',
                        title: '是否会签',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'status',
                        title: '当前状态',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'approverEmployee',
                        title: '审批人',
                        align: 'center',
                        visible: false,
                        formatter: function(value, row, index) {
                            return value.id;
                        }
                    }, {
                        field: 'formId',
                        title: '表单ID',
                        align: 'center',
                        visible: false
                    },
					{
						field : 'status',
						title : '流程状态',
						align : 'center',
						formatter : function(
								value, row,
								index) {
							var label = '';
							if (value == 'DRAFT') {
								label = '草稿';
							} else if (value == 'ADOPT') {
								label = '审核通过';
							} else if (value == 'REFUSE') {
								label = '拒绝';
							} else if (value == 'RESET') {
								label = '已撤回';
							} else if (value == 'APPROVALING') {
								label = '审批中';
							}
							return label;
						}
					},
					{
						field : 'status',
						title : '当前进度',
						align : 'center',
						formatter : function(
								value, row,
								index) {
							var label = '';
							if (value == 'DRAFT') {
								label = '草稿';
							} else if (value == 'ADOPT') {
								label = '审核通过';
							} else if (value == 'REFUSE') {
								label = '拒绝';
							} else if (value == 'RESET') {
								label = '已撤回';
							} else {
								label = '待【'
										+ row.name
										+ '】审批';
							}
							return label;
						}
					}, {
                        field: '',
                        title: '操作',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var label = '';
                            if (!self.weixin) {  // 微信端不让下载
                                label += '<button data-handle="view-pdf" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-primary">预览</button>&nbsp;&nbsp;&nbsp;&nbsp;';
                                label += '<button data-handle="print-pdf" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">下载</button>&nbsp;&nbsp;&nbsp;&nbsp;';
                            }
                            return label;
                        }
                    } ]
            	});
            	 // 打印
            	self.$dataTable.on('click', '[data-handle="print-pdf"]', function (e) {
                	var id = $(this).data('id');
                	window.location.href = '/api/applyCommon/print/' + id;
                });
                // 打印
            	self.$dataTable.on('click', '[data-handle="view-pdf"]', function (e) {
                	var id = $(this).data('id');
                	if(self.weixin){  //如果是微信，直接预览，如果是PC就打开一个新页面
                		window.location.href ='/api/applyCommon/view/' + id
                	}else{
                		window.open('/api/applyCommon/view/' + id);
                	}
                });
            },
            
            drawTable: function () {
                var self = this;
                self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
                    url: '/api/vacationBusiness',
                    method: 'get',
                    dataType: 'json',
                    cache: false, //去缓存
                    pagination: true, //是否分页
                    sidePagination: 'server', //服务端分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', //空数据的默认显示字符
                    striped: true, //斑马线
                    maintainSelected: true, //维护checkbox选项
                    columns: [
                        {
                            field: 'applyCode',
                            title: '申请编号',
                            align: 'center',
                            formatter: function (value, row, index) {
                                return '<a data-handle="view" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                            }
                        }, {
                            field: 'createTime',
                            title: '提交时间',
                            align: 'center'
                        },{
                            field: 'applyTitle',
                            title: '申请标题',
                            align: 'center',
                            formatter: function (value, row, index) {
                                return '<a data-handle="view" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                            }
                        }, {
                            field: 'type',
                            title: '申请类型',
                            align: 'center',
                            visible: false
                        }, {
                            field: 'reason',
                            title: '申请事由',
                            align: 'center'
                        }, {
                            field: 'days',
                            title: '申请天数',
                            align: 'center'
                        }, {
                            field: 'status',
                            title: '当前进度',
                            align: 'center',
                            formatter: function (value, row, index) {
                                var label = '';
                                if (value == 'DRAFT') {
                                    label = '草稿';
                                } else if (value == 'ADOPT') {
                                    label = '审核通过';
                                } else if (value == 'REFUSE') {
                                    label = '拒绝';
                                } else {
                                    label = '待【' + row.approver + '】审批';
                                }
                                return label;
                            }
                        }]
                });

                //详情
                self.$dataTable.on('click', '[data-handle="view"]', function (e) {
                    var id = $(this).data('id');
                    var type = $(this).data('type');
                    var url = '/api/vacations/' + id;
                    window.location.href="/admin/businessAway/detailContainer?id=" + id + "&type=LEAVE&applyUrl=" + url;
                    e.stopPropagation();
                });
            },
            drawTableSignature: function () {
                var self = this;
                self.$dataTableSignature = $('#dataTableSignature', self._$el).bootstrapTable({
                    url: '/api/signatures',
                    method: 'get',
                    dataType: 'json',
                    cache: false, //去缓存
                    pagination: true, //是否分页
                    sidePagination: 'server', //服务端分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                    	self.form.keyword = self.form.keywordSignature;
                    	self.form.type = "CCUSER";
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', //空数据的默认显示字符
                    striped: true, //斑马线
                    maintainSelected: true, //维护checkbox选项
                    sortName: 'id', //默认排序列名
                    sortOrder: 'desc', //默认排序方式
                    columns: [{
                        field: 'applyCode',
                        title: '申请编码',
                        align: 'center',
                        formatter: function (value, row, index) {
                            return '<a data-handle="operate-detail" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                        }
                    }, {
                        field: 'createDate',
                        title: '提交时间',
                        align: 'center'
                    }, {
                        field: 'costItem',
                        title: '费用科目',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'costItemName',
                        title: '费用科目',
                        align: 'center'
                    }, {
                        field: 'applyTitle',
                        title: '申请标题',
                        align: 'center',
                        formatter: function (value, row, index) {
                            return '<a data-handle="operate-detail" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                        }
                    }, {
                        field: 'reason',
                        title: '申请事由',
                        align: 'center'
                    }, {
                        field: 'totalMoney',
                        title: '费用金额',
                        align: 'center',
                        formatter: function (val, row) {
                            return val + '元';
                        }
                    }, {
                        field: 'status',
                        title: '当前进度',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var label = '';
                            if (value == 'DRAFT') {
                                label = '草稿';
                            } else if (value == 'ADOPT') {
                                label = '审核通过';
                            } else if (value == 'REFUSE') {
                                label = '拒绝';
                            } else {
                                label = '待【' + row.currentApproverName + '】审批';
                            }
                            return label;
                        }
                    }]
                });

                //费用详情
                self.$dataTableSignature.on('click', '[data-handle="operate-detail"]', function (e) {
                    var id = $(this).data('id');
                    self.$http.get('/api/signatures/' + id + '/get').then(function (res) {
                        if (res.data.code == 1) {
                            var signature = res.data.data;
                            this.showSignatureDetail(signature);
                        }
                    });
                    e.stopPropagation();
                });
            },
            drawTablePayment: function () {
                var self = this;
                self.$dataTablePayment = $('#dataTablePayment', self._$el).bootstrapTable({
                    url: '/api/payments',
                    method: 'get',
                    dataType: 'json',
                    cache: false, //去缓存
                    pagination: true, //是否分页
                    sidePagination: 'server', //服务端分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                    	self.form.keyword = self.form.keywordPayment;
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', //空数据的默认显示字符
                    striped: true, //斑马线
                    maintainSelected: true, //维护checkbox选项
                    sortName: 'id', //默认排序列名
                    sortOrder: 'desc', //默认排序方式
                    columns: [{
                        field: 'applyCode',
                        title: '申请编码',
                        align: 'center',
                        formatter: function (value, row, index) {
                            return '<a data-handle="operate-paymentDetail" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.formId + '">' + value + '</a>';
                        }
                    }, {
                        field: 'createDate',
                        title: '提交时间',
                        align: 'center'
                    }, {
                        field: 'costItem',
                        title: '费用科目',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'costItemName',
                        title: '费用科目',
                        align: 'center'
                    },{
                        field: 'applyTitle',
                        title: '申请标题',
                        align: 'center',
                        formatter: function (value, row, index) {
                            return '<a data-handle="operate-paymentDetail" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                        }
                    }, {
                        field: 'reason',
                        title: '申请事由',
                        align: 'center'
                    }, {
                        field: 'totalMoney',
                        title: '报销金额',
                        align: 'center',
                        formatter: function (val, row) {
                            return val + '元';
                        }
                    }, {
                        field: 'status',
                        title: '当前进度',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var label = '';
                            if (value == 'DRAFT') {
                                label = '草稿';
                            } else if (value == 'ADOPT') {
                                label = '审核通过';
                            } else if (value == 'REIMBURSED'){
                            	label = '已报销'; 
                            } else if (value == 'REFUSE') {
                                label = '拒绝';
                            } else {
                                label = '待【' + row.currentApproverName + '】审批';
                            }
                            return label;
                        }
                    }]
                });

                //报销详情
                self.$dataTablePayment.on('click', '[data-handle="operate-paymentDetail"]', function (e) {
                    var id = $(this).data('id');
                    var url = '/api/payments/info?id='+id;
	                window.location.href = url;
//                    self.$http.get('/api/payments/' + id + '/get').then(function (res) {
//                        if (res.data.code == 1) {
//                            var payment = res.data.data;
//                            this.showPaymentDetail(payment);
//                        }
//                    });
                    e.stopPropagation();
                });
            },            
            showSignatureDetail: function (signature) {
                var _$modal = $('#signatureDetail').clone();
                var modal = _$modal.modal({
                    height: 580,
                    maxHeight: 580,
                    backdrop: 'static',
                    keyboard: false
                });
                signatureDetail(modal, signature);
            },
            showPaymentDetail:function (payment){
                var _$modal = $('#paymentDetail').clone();
                var modal = _$modal.modal({
                    height: 580,
                    maxHeight: 580,
                    backdrop: 'static',
                    keyboard: false
                });
                paymentDetail(modal, payment);
            }
        }
    });
    
    /**
     * 费用单审批详情弹窗
     * @param $el 窗口对象
     * @param signature 费用单对象
     */
    function signatureDetail($el, signature) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        var vueDetail = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                signature: null,
                approveList: null
            },
            created: function () {

            },
            ready: function () {
                this.signature = signature;
                this.getApproveList();
            },
            watch: {},
            computed: {},
            methods: {
                getApproveList: function () {
                    var self = this;
                    self.$http.get("/api/wfmanager/wfApproveDetail/", {
                        params: {
                            'formId': this.signature.id,
                            'type': 'SIGNATURE',
                            'showType': 'personal'
                        }
                    }).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            self.approveList = res.data;
                        }
                    });
                }
            }
        });
        // 创建的Vue对象应该被返回
        return vueDetail;
    }
    
    /**
     * 报销详情弹窗
     * @param $el 窗口对象
     * @param payment 报销单对象
     */
    function paymentDetail($el, payment) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        var vueDetail = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                payment: null,
                approveList: null
            },
            created: function () {

            },
            ready: function () {
                this.payment = payment;
                this.getApproveList();
            },
            watch: {},
            computed: {},
            methods: {
                getApproveList: function () {
                    var self = this;
                    self.$http.get("/api/wfmanager/wfApproveDetail/", {
                        params: {
                            'formId': this.payment.id,
                            'type': 'EXPENSE',
                            'showType': 'personal'
                        }
                    }).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            self.approveList = res.data;
                        }
                    });
                }
            }
        });
        // 创建的Vue对象应该被返回
        return vueDetail;
    }

})(this.RocoUtils);
