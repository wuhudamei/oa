/**
 * Created by aaron on 2017/3/9.
 */
+(function() {
	$('#wechatMenu').addClass('active');
	$('#wxMessage').addClass('active');
	var vueIndex = new Vue({
		el : '#container',
		mixins : [ RocoVueMixins.DataTableMixin ],
		data : {
			// 面包屑
			breadcrumbs : [ {
				path : '/',
				name : '主页'
			}, {
				path : '/',
				name : '微信消息',
				active : true
			// 激活面包屑的
			} ],
			// 查询表单
			form : {
				keyword : ''
			},
			selectedRows : {}, // 选中列
			modalModel : null, // 模式窗体模型
			_$el : null, // 自己的 el $对象
			_$dataTable : null
		// datatable $对象
		},
		created : function() {
		},
		ready : function() {
			this.drawTable();
		},
		methods : {
			query : function() {
				this.$dataTable.bootstrapTable('refresh', {
					pageNumber : 1
				});
			},
			drawTable : function() {
				var self = this;
				self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
					url : '/wx/message/list',
					method : 'get',
					dataType : 'json',
					cache : false, // 去缓存
					pagination : true, // 是否分页
					sidePagination : 'server', // 服务端分页
					queryParams : function(params) {
						// 将table 参数与搜索表单参数合并
						return _.extend({}, params, self.form);
					},
					mobileResponsive : true,
					undefinedText : '-', // 空数据的默认显示字符
					striped : true, // 斑马线
					maintainSelected : true, // 维护checkbox选项
					columns : [ {
						field : 'serial_number',
						title : '申请编号',
						align : 'center'
					}, {
						field : 'title',
						title : '标题',
						align : 'center'
					}, {
						field : 'fromUser',
						title : '发消息人',
						align : 'center'
					}, {
						field : 'toUsers',
						title : '收消息人',
						align : 'center'
					}, {
						field : 'createTimeHMS',
						title : '发送时间',
						align : 'center'
					}, {
						field : 'cc_person_name',
						title : '抄送人',
						align : 'center',
						formatter : function(value, row) {
							var label = "";
							if (value != "[]") {
								label = value;
							}
							return label;
						}
					} ]
				});

				// 打标签
				self.$dataTable.on('click', '[data-handle="view"]',
						function(e) {
						});

				// 编辑按钮事件
				self.$dataTable.on('click', '[data-handle="edit"]',
						function(e) {
						});

				// 删除按钮事件
				self.$dataTable.on('click', '[data-handle="delete"]', function(
						e) {
				});

				// 选择用户打标签
				self.$dataTable.on('click', '[data-handle="tag"]', function(e) {
				});
			},
			create : function() {
			}
		}
	});

	function createModal($el, model, isEdit) {
	}

	// 选择用户打标签modal
	function tagModal($el, tagId) {
	}

	// 查看详情modal
	function detailModal($el, tag) {
	}
})();
