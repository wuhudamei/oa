var vueIndex = null;
+(function(RocoUtils) {
	$('#myNeedDo').addClass('active');
	$('#doApproval').addClass('active');
	vueIndex = new Vue(
			{
				el : '#container',
				mixins : [ RocoVueMixins.DataTableMixin ],
				data : {
					// 面包屑
					breadcrumbs : [ {
						path : '/',
						name : '主页'
					}, {
						path : '/',
						name : '已审批',
						active : true
					// 激活面包屑的
					} ],
					// 查询表单
					form : {
						keyword : '', // 请假
						keywordSignature : '',// 费用
						keywordPayment : '',// 报销
						type : 'LEAVE',
						wfType : 'HIS' // 查询历史审批
					},
					selectedRows : {}, // 选中列
					modalModel : null, // 模式窗体模型
					_$el : null, // 自己的 el $对象
					_$dataTable : null, // datatable $对象
					_$dataTableSignature : null,
					_$dataTablePayment : null
				},
				created : function() {
				},
				ready : function() {
					this.clickEvent({
						key : 0
					});
					this.drawTable();
				},
				methods : {
					query : function() {
						this.$dataTable.bootstrapTable('selectPage', 1);
					},
					querySignature : function() {
						this.$dataTableSignature
								.bootstrapTable('selectPage', 1);
					},
					queryPayment : function() {
						this.$dataTablePayment.bootstrapTable('selectPage', 1);
					},
					clickEvent : function(val) {// 选项卡单击事件
						if (val.key == 0) { // 通用
							this.dataTableCommon();
						} else if (val.key == 1) { // 费用
							this.drawTableSignature();
						} else if (val.key == 2) { // 请假
							this.drawTable();
						} else if (val.key == 3) { // 报销
							this.drawTablePayment();
						}
					},
					drawTable : function() {
						var self = this;
						self.$dataTable = $('#dataTable', self._$el)
								.bootstrapTable(
										{
											url : '/api/wfmanager/list',
											method : 'get',
											dataType : 'json',
											cache : false, // 去缓存
											pagination : true, // 是否分页
											sidePagination : 'server', // 服务端分页
											queryParams : function(params) {
												// 将table 参数与搜索表单参数合并
												return _.extend({}, params,
														self.form);
											},
											mobileResponsive : true,
											undefinedText : '-', // 空数据的默认显示字符
											striped : true, // 斑马线
											maintainSelected : true, // 维护checkbox选项
											columns : [
													{
														field : 'id',
														title : 'ID',
														align : 'center',
														visible : false
													},
													{
														field : 'nodeId',
														title : '当前节点',
														align : 'center',
														visible : false
													},
													{
														field : 'applyCode',
														title : '申请编码',
														align : 'center',
														formatter : function(
																value, row,
																index) {
															return '<a data-handle="hisView" data-type="'
																	+ row.type
																	+ '" data-index="'
																	+ index
																	+ '" data-id="'
																	+ row.id
																	+ '">'
																	+ value
																	+ '</a>';
														}
													},
													{
														field : 'applyTime',
														title : '提交时间',
														align : 'center'
													},
													{
														field : 'applyUserId',
														title : '申请人ID',
														align : 'center',
														visible : false
													},
													{
														field : 'applyUserName',
														title : '申请人',
														align : 'center'
													},
													{
														field : 'applyTitle',
														title : '申请标题',
														align : 'center',
														formatter : function(
																value, row,
																index) {
															return '<a data-handle="hisView" data-type="'
																	+ row.type
																	+ '" data-index="'
																	+ index
																	+ '" data-id="'
																	+ row.id
																	+ '">'
																	+ value
																	+ '</a>';
														}
													},
													{
														field : 'nodeType',
														title : '节点类型',
														align : 'center',
														visible : false
													},
													{
														field : 'type',
														title : '流程类型',
														align : 'center',
														visible : false,
														formatter : function(
																value, row,
																index) {
															return formApproveStatus(value);
														}
													},
													{
														field : 'isSign',
														title : '是否会签',
														align : 'center',
														visible : false,
														formatter : function(
																value, row,
																index) {
															var label = '';
															switch (value) {
															case 0:
																label = '否';
																break;
															case 1:
																label = '是';
																break;
															default:
																break;
															}
															return label;
														}
													},
													{
														field : 'approveResult',
														title : '审批结果',
														align : 'center',
														formatter : function(
																value, row,
																index) {
															return value == "AGREE" ? "<font color='green'>同意</font>"
																	: "<font color='red'>拒绝</font>";
														}
													}, {
														field : 'formId',
														title : '表单ID',
														align : 'center',
														visible : false
													} ]
										});

						/**
						 * 详情
						 */
						self.$dataTable
								.on(
										'click',
										'[data-handle="hisView"]',
										function(e) {
											var id = $(this).data('id');
											var index = $(this).data('index');
											var rowData = self.$dataTable
													.bootstrapTable('getData');
											var curData = rowData[index];
											window.location.href = "/admin/approval/detail?id="
													+ id
													+ "&type="
													+ curData.type
													+ "&isApprove=false";
											e.stopPropagation();
										});
					},
					drawTableSignature : function() {
						var self = this;
						if (self.type) {
							self.form.type = self.type;
						}
						self.$dataTableSignature = $('#dataTableSignature',
								self._$el)
								.bootstrapTable(
										{
											url : '/api/signatures',
											method : 'get',
											dataType : 'json',
											cache : false, // 去缓存
											pagination : true, // 是否分页
											sidePagination : 'server', // 服务端分页
											queryParams : function(params) {
												// 将table 参数与搜索表单参数合并
												self.form.keyword = self.form.keywordSignature;
												self.form.type = "COMPLETE";
												return _.extend({}, params,
														self.form);
											},
											mobileResponsive : true,
											undefinedText : '-', // 空数据的默认显示字符
											striped : true, // 斑马线
											maintainSelected : true, // 维护checkbox选项
											columns : [
													{
														field : 'id',
														title : 'ID',
														align : 'center',
														visible : false
													},
													{
														field : 'nodeId',
														title : '当前节点',
														align : 'center',
														visible : false
													},
													{
														field : 'applyCode',
														title : '申请编码',
														align : 'center'
													},
													{
														field : 'createDate',
														title : '提交时间',
														align : 'center'
													},
													{
														field : 'applyUserId',
														title : '申请人ID',
														align : 'center',
														visible : false
													},
													{
														field : 'createUser',
														title : '申请人',
														align : 'center',
														formatter : function(
																value, row,
																index) {
															return value['name']
														}
													},
													{
														field : 'applyTitle',
														title : '申请标题',
														align : 'center',
														formatter : function(
																value, row,
																index) {
															return '<a data-handle="signatureHisView" data-type="'
																	+ row.type
																	+ '" data-index="'
																	+ index
																	+ '" data-id="'
																	+ row.id
																	+ '">'
																	+ value
																	+ '</a>';
														}
													},
													{
														field : 'nodeType',
														title : '节点类型',
														align : 'center',
														visible : false
													},
													{
														field : 'type',
														title : '流程类型',
														align : 'center',
														visible : false,
														formatter : function(
																value, row,
																index) {
															return formApproveStatus(value);
														}
													},
													{
														field : 'isSign',
														title : '是否会签',
														align : 'center',
														visible : false,
														formatter : function(
																value, row,
																index) {
															var label = '';
															switch (value) {
															case 0:
																label = '否';
																break;
															case 1:
																label = '是';
																break;
															default:
																break;
															}
															return label;
														}
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
																		+ row.currentApproverName
																		+ '】审批';
															}
															return label;
														}
													} ]
										});

						/**
						 * 审批历史详情
						 */
						self.$dataTableSignature.on('click',
								'[data-handle="signatureHisView"]',
								function(e) {
									var id = $(this).data('id');
									var url = '/api/signature/info?id=' + id;
									window.location.href = url;
									// var index = $(this).data('index');
									// var rowData = self.$dataTableSignature
									// .bootstrapTable('getData');
									// var curData = rowData[index];
									// approvalDetail(self,
									// curData.type, curData);
									// window.location.href =
									// "/admin/approval/detail?id="
									// + id
									// + "&type="
									// + curData.type
									// + "&isApprove=false";
									e.stopPropagation();
								});
					},
					drawTablePayment : function() {
						var self = this;
						if (self.type) {
							self.form.type = self.type;
						}
						self.$dataTablePayment = $('#dataTablePayment',
								self._$el)
								.bootstrapTable(
										{
											url : '/api/payments',
											method : 'get',
											dataType : 'json',
											cache : false, // 去缓存
											pagination : true, // 是否分页
											sidePagination : 'server', // 服务端分页
											queryParams : function(params) {
												// 将table 参数与搜索表单参数合并
												self.form.keyword = self.form.keywordPayment;
												self.form.type = "EXPENSE";
												self.form.dataType = "COMPLETE";
												return _.extend({}, params,self.form);
											},
											mobileResponsive : true,
											undefinedText : '-', // 空数据的默认显示字符
											striped : true, // 斑马线
											maintainSelected : true, // 维护checkbox选项
											columns : [
													{
														field : 'id',
														title : 'ID',
														align : 'center',
														visible : false
													},
													{
														field : 'nodeId',
														title : '当前节点',
														align : 'center',
														visible : false
													},
													{
														field : 'applyCode',
														title : '申请编码',
														align : 'center'
													},
													{
														field : 'paymentDate',
														title : '提交时间',
														align : 'center'
													},
													{
														field : 'applyUserId',
														title : '申请人ID',
														align : 'center',
														visible : false
													},
													{
								                        field: 'createUser',
								                        title: '申请人',
								                        align: 'center',
								                        formatter: function(value, row, index) {
								                            return value.name;
								                        }
								                    },
													{
														field : 'applyTitle',
														title : '申请标题',
														align : 'center',
														formatter : function(
																value, row,
																index) {
															return '<a data-handle="paymentHisView" data-type="'
																	+ row.type
																	+ '" data-index="'
																	+ index
																	+ '" data-id="'
																	+ row.id
																	+ '">'
																	+ value
																	+ '</a>';
														}
													},{
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
																		+ row.currentApproverName
																		+ '】审批';
															}
															return label;
														}
													},
													{
														field : 'nodeType',
														title : '节点类型',
														align : 'center',
														visible : false
													},
													{
														field : 'type',
														title : '流程类型',
														align : 'center',
														visible : false,
														formatter : function(
																value, row,
																index) {
															return formApproveStatus(value);
														}
													},
													{
														field : 'isSign',
														title : '是否会签',
														align : 'center',
														visible : false,
														formatter : function(
																value, row,
																index) {
															var label = '';
															switch (value) {
															case 0:
																label = '否';
																break;
															case 1:
																label = '是';
																break;
															default:
																break;
															}
															return label;
														}
													},
													 {
														field : 'formId',
														title : '表单ID',
														align : 'center',
														visible : false
													} ]
										});

						/**
						 * 审批历史详情
						 */
						self.$dataTablePayment.on('click',
								'[data-handle="paymentHisView"]', function(e) {
									var id = $(this).data('id');
									var url = '/api/payments/info?id=' + id;
									window.location.href = url;
									// var index = $(this).data('index');
									// var rowData = self.$dataTablePayment
									// .bootstrapTable('getData');
									// var curData = rowData[index];
									// // approvalDetail(self,
									// // curData.type, curData);
									// window.location.href =
									// "/admin/approval/detail?id="
									// + id
									// + "&type="
									// + curData.type
									// + "&isApprove=false";
									e.stopPropagation();
								});

					},
					dataTableCommon : function() {
						var self = this;
						if (self.type) {
							self.form.type = self.type;
						}
						self.$dataTablePayment = $('#dataTableCommon',
								self._$el)
								.bootstrapTable(
										{
											url : '/api/applyCommon/list',
											method : 'get',
											dataType : 'json',
											cache : false, // 去缓存
											pagination : true, // 是否分页
											sidePagination : 'server', // 服务端分页
											queryParams : function(params) {
												// 将table 参数与搜索表单参数合并
												self.form.keyword = self.form.keywordPayment;
												self.form.type = "COMMON";
												self.form.dataType = "COMPLETE";
												return _.extend({}, params,
														self.form);
											},
											mobileResponsive : true,
											undefinedText : '-', // 空数据的默认显示字符
											striped : true, // 斑马线
											maintainSelected : true, // 维护checkbox选项
											columns : [{
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
											} ]
										});
						/**
						 * 审批历史详情
						 */
						self.$dataTablePayment
								.on(
										'click',
										'[data-handle="paymentHisView"]',
										function(e) {
											var id = $(this).data('id');
											var index = $(this).data('index');
											var rowData = self.$dataTablePayment
													.bootstrapTable('getData');
											var curData = rowData[index];
											// approvalDetail(self,
											// curData.type, curData);
											window.location.href = "/admin/approval/detail?id="
													+ id
													+ "&type="
													+ curData.type
													+ "&isApprove=false";
											e.stopPropagation();
										});

					},
					showSignatureDetail : function(signature) {
						var _$modal = $('#signatureDetail').clone();
						var modal = _$modal.modal({
							height : 580,
							maxHeight : 580,
							backdrop : 'static',
							keyboard : false
						});
						signatureDetail(modal, signature);
					},
					showPaymentDetail : function(payment) {
						var _$modal = $('#paymentDetail').clone();
						var modal = _$modal.modal({
							height : 580,
							maxHeight : 580,
							backdrop : 'static',
							keyboard : false
						});
						paymentDetail(modal, payment);
					}
				}
			});

	/**
	 * 费用单审批详情弹窗
	 * 
	 * @param $el
	 *            窗口对象
	 * @param signature
	 *            费用单对象
	 */
	function signatureDetail($el, signature) {
		// 获取 node
		var el = $el.get(0);
		// 创建 Vue 对象编译节点
		var vueDetail = new Vue({
			el : el,
			// 模式窗体必须引用 ModalMixin
			mixins : [ RocoVueMixins.ModalMixin ],
			$modal : $el, // 模式窗体 jQuery 对象
			data : {
				signature : null,
				approveList : null
			},
			created : function() {

			},
			ready : function() {
				this.signature = signature;
				this.getApproveList();
			},
			watch : {},
			computed : {},
			methods : {
				getApproveList : function() {
					var self = this;
					self.$http.get("/api/wfmanager/wfApproveDetail/", {
						params : {
							'formId' : this.signature.id,
							'type' : 'SIGNATURE',
							'showType' : 'personal'
						}
					}).then(function(response) {
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
	 * 
	 * @param $el
	 *            窗口对象
	 * @param payment
	 *            报销单对象
	 */
	function paymentDetail($el, payment) {
		// 获取 node
		var el = $el.get(0);
		// 创建 Vue 对象编译节点
		var vueDetail = new Vue({
			el : el,
			// 模式窗体必须引用 ModalMixin
			mixins : [ RocoVueMixins.ModalMixin ],
			$modal : $el, // 模式窗体 jQuery 对象
			data : {
				payment : null,
				approveList : null
			},
			created : function() {

			},
			ready : function() {
				this.payment = payment;
				this.getApproveList();
			},
			watch : {},
			computed : {},
			methods : {
				getApproveList : function() {
					var self = this;
					self.$http.get("/api/wfmanager/wfApproveDetail/", {
						params : {
							'formId' : this.payment.id,
							'type' : 'EXPENSE',
							'showType' : 'personal'
						}
					}).then(function(response) {
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
