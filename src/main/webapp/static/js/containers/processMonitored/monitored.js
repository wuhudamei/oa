var vueIndex = null;
+(function(RocoUtils) {
	$('#monitored').addClass('active');
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
						name : '流程监控',
						active : true
					// 激活面包屑的
					} ],
					// 查询表单
					form : {
						keyword : '', // 请假
						keywordSignature : '',// 费用
						keywordPayment : '',// 报销
						type : '',
						status : "",
						ccFlag : "2" // 监控
					},
					selectedRows : {}, // 选中列
					modalModel : null, // 模式窗体模型
					_$el : null, // 自己的 el $对象
					_$dataTable : null, // datatable $对象
					_$dataTableSignature : null,
					_$dataTablePayment : null,
					_$drawTableCommon : null,
				},
				created : function() {
				},
				ready : function() {
					this.drawTableCommon();
				},
				methods : {
					queryCommon : function() {
						this.$drawTableCommon.bootstrapTable('selectPage', 1);
					},
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
						if (val.key == 0) {
							this.drawTableCommon();
						} else if (val.key == 1) {
							this.drawTable();
						} else if (val.key == 2) {
							this.drawTableSignature();
						} else if (val.key == 3) {
							this.drawTablePayment();
						}
					},
					drawTableCommon : function() {
						var self = this;
						self.$drawTableCommon = $('#dataTableCmmon', self._$el)
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
										params.dataType='MONIT'  //监控查所有
										return _.extend({}, params,
												self.form);
									},
									mobileResponsive : true,
									undefinedText : '-', // 空数据的默认显示字符
									striped : true, // 斑马线
									maintainSelected : true, // 维护checkbox选项
									columns : [{
			                            field: 'id',
			                            visible: false,
			                            title: '申请编号',
			                            align: 'center'
			                        },{
			                            field: 'serial_number',
			                            title: '申请编号',
			                            align: 'center'
			                        }, {
			                            field: 'title',
			                            title: '申请标题',
			                            align: 'center',
			            				formatter: function (value, row, index) {
				        					var id = row.id
				        					return '<a href="/admin/commonApply/info?id=' + id + '"  >' + value + '</a>';
			            				}
			                        }, {
			                            field: 'content',
			                            title: '申请事由',
			                            align: 'center'
			                        }, {
			                            field: 'type',
			                            title: '申请类型',
			                            align: 'center',
			                            visible: false
			                        }, {
			    						field : 'apply_person_name',
			    						title : '审批人',
			    						align : 'center'
			    					}, {
			                            field: 'apply_timehms',
			                            title: '提交时间',
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
			                                } else if (value == 'RESET') {
			                                    label = '已撤回';
			                                } else {
			                                    label = '待【' + row.name + '】审批';
			                                }
			                                return label;
			                            }
			                        }, {
			                            field: 'status',
			                            title: '源数据',
			                            align: 'center',
			                            formatter: function (value, row, index) {
			                                var label = '';
			                                label += '<button data-handle="source-data" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">源数据</button>&nbsp;&nbsp;&nbsp;&nbsp;';
			                                if(RocoUtils.hasPermission("monitored:del")){
			                                	label += '<button data-handle="del-data" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-danger">删除</button>&nbsp;&nbsp;&nbsp;&nbsp;';
			                                }
			                                return label;
			                            }
			                        }]
									
								});
		                self.$drawTableCommon.on('click', '[data-handle="source-data"]', function (e) {
		                    var id = $(this).data('id');
		                   window.location.href = '/admin/sourceData?id=' + id;
		                });
		                self.$drawTableCommon.on('click', '[data-handle="del-data"]', function (e) {
		                	var id = $(this).data('id');
		                	self.$http.get('/api/applyCommon/del/'+id).then(
								function(res) {
									if (res.data.code == 1) {
										self.$toastr.success(res.data['message']);
										self.$drawTableCommon.bootstrapTable('selectPage', 1);
									}
							});
		                });
					},
					drawTable : function() {
						var self = this;
						self.$dataTable = $('#dataTable', self._$el)
								.bootstrapTable(
										{
											url : '/api/vacationBusiness',
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
														field : 'applyCode',
														title : '申请编号',
														align : 'center',
														formatter : function(
																value, row,
																index) {
															return '<a data-handle="view" data-type="'
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
														field : 'createTime',
														title : '提交时间',
														align : 'center'
													},
													{
														field : 'applyTitle',
														title : '申请标题',
														align : 'center',
														formatter : function(
																value, row,
																index) {
															return '<a data-handle="view" data-type="'
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
														field : 'type',
														title : '申请类型',
														align : 'center',
														visible : false
													},
													{
														field : 'reason',
														title : '申请事由',
														align : 'center'
													},
													{
														field : 'days',
														title : '申请天数',
														align : 'center'
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
															} else {
																label = '待【'
																		+ row.approver
																		+ '】审批';
															}
															return label;
														}
													} ]
										});

						// 详情
						self.$dataTable
								.on(
										'click',
										'[data-handle="view"]',
										function(e) {
											var id = $(this).data('id');
											var type = $(this).data('type');
											var url = '/api/vacations/' + id;
											window.location.href = "/admin/businessAway/detailContainer?id="
													+ id
													+ "&type=LEAVE&applyUrl="
													+ url;
											e.stopPropagation();
										});
					},
					drawTableSignature : function() {
						var self = this;
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
												return _.extend({}, params,
														self.form);
											},
											mobileResponsive : true,
											undefinedText : '-', // 空数据的默认显示字符
											striped : true, // 斑马线
											maintainSelected : true, // 维护checkbox选项
											sortName : 'id', // 默认排序列名
											sortOrder : 'desc', // 默认排序方式
											columns : [
													{
														field : 'applyCode',
														title : '申请编码',
														align : 'center',
														formatter : function(
																value, row,
																index) {
															return '<a data-handle="operate-detail" data-type="'
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
														field : 'createDate',
														title : '提交时间',
														align : 'center'
													},
													{
														field : 'costItem',
														title : '费用科目Id',
														align : 'center',
														visible : false
													},
													{
														field : 'costItemName',
														title : '费用科目',
														align : 'center'
													},
													{
														field : 'applyTitle',
														title : '申请标题',
														align : 'center',
														formatter : function(
																value, row,
																index) {
															return '<a data-handle="operate-detail" data-type="'
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
														field : 'reason',
														title : '申请事由',
														align : 'center'
													},
													{
														field : 'totalMoney',
														title : '费用金额',
														align : 'center',
														formatter : function(
																val, row) {
															return val + '元';
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
															} else {
																label = '待【'
																		+ row.currentApproverName
																		+ '】审批';
															}
															return label;
														}
													} ]
										});

						// 费用详情
						self.$dataTableSignature
								.on(
										'click',
										'[data-handle="operate-detail"]',
										function(e) {
											var id = $(this).data('id');
											self.$http
													.get(
															'/api/signatures/'
																	+ id
																	+ '/get')
													.then(
															function(res) {
																if (res.data.code == 1) {
																	var signature = res.data.data;
																	this
																			.showSignatureDetail(signature);
																}
															});
											e.stopPropagation();
										});
					},
					drawTablePayment : function() {
						var self = this;
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
												return _.extend({}, params,
														self.form);
											},
											mobileResponsive : true,
											undefinedText : '-', // 空数据的默认显示字符
											striped : true, // 斑马线
											maintainSelected : true, // 维护checkbox选项
											sortName : 'id', // 默认排序列名
											sortOrder : 'desc', // 默认排序方式
											columns : [
													{
														field : 'applyCode',
														title : '申请编码',
														align : 'center',
														formatter : function(
																value, row,
																index) {
															return '<a data-handle="operate-paymentDetail" data-type="'
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
														field : 'createDate',
														title : '提交时间',
														align : 'center'
													},
													{
														field : 'costItem',
														title : '费用科目Id',
														align : 'center',
														visible : false
													},
													{
														field : 'costItemName',
														title : '费用科目',
														align : 'center'
													},
													{
														field : 'applyTitle',
														title : '申请标题',
														align : 'center',
														formatter : function(
																value, row,
																index) {
															return '<a data-handle="operate-paymentDetail" data-type="'
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
														field : 'reason',
														title : '申请事由',
														align : 'center'
													},
													{
														field : 'totalMoney',
														title : '报销金额',
														align : 'center',
														formatter : function(
																val, row) {
															return val + '元';
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
															} else if (value == 'REIMBURSED') {
																label = '已报销';
															} else if (value == 'REFUSE') {
																label = '拒绝';
															} else {
																label = '待【'
																		+ row.currentApproverName
																		+ '】审批';
															}
															return label;
														}
													} ]
										});

						// 报销详情
						self.$dataTablePayment
								.on(
										'click',
										'[data-handle="operate-paymentDetail"]',
										function(e) {
											var id = $(this).data('id');
											self.$http
													.get(
															'/api/payments/'
																	+ id
																	+ '/get')
													.then(
															function(res) {
																if (res.data.code == 1) {
																	var payment = res.data.data;
																	this
																			.showPaymentDetail(payment);
																}
															});
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
