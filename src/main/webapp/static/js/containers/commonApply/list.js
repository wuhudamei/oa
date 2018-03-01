/**
 * Created by aaron on 2017/3/9.
 */
+(function() {
	$('#leveAndBusinessMenu').addClass('active');
	$('#commonApplyList').addClass('active');
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
				name : '通用申请',
				active : true
			// 激活面包屑的
			} ],
			// 查询表单
			form : {
				keyword : '',
				orgCode : '',
				orgName : '',
				employeeStatus : 'ON_JOB'
			},
			selectedRows : {}, // 选中列
			modalModel : null, // 模式窗体模型
			_$el : null, // 自己的 el $对象
			_$dataTable : null, // datatable $对象
			orgData : null, // 机构树数据
			showOrgTree : false
		// 是否显示机构树
		},
		created : function() {

		},
		ready : function() {
			var self = this;
			self.drawTable();
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
					url : '/api/applyCommon/list',
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
						field : 'id',
						title : 'id',
						align : 'center'
					}, {
						field : 'title',
						title : '标题',
						align : 'center',
						formatter: function (value, row) {
						  var id = row.id
		                  return '<a href="/admin/commonApply/info?id=' + id + '"  >' + value + '</a>';
		                }
					}, {
						field : 'content',
						title : '内容',
						align : 'center'
					}, {
						field : 'applyPersonName',
						title : '审批人',
						align : 'center'
					}, {
						field : 'ccPersonName',
						title : '抄送人',
						align : 'center'
					}, {
						field : 'applyTime',
						title : "提交时间",
						align : 'center'
					} ]
				});

				self.$dataTable.on('click', '[data-handle="view"]',
				function(e) {
					var id = $(this).data("id");
					window.location.href = "/oldCrm/list";
				});
			}
		}
	});
})();
