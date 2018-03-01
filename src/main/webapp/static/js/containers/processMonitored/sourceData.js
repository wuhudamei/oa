var mainView = null;
+(function (RocoUtils) {
	$('#monitored').addClass('active');
	mainView = new Vue({
        el: '#container',
        data: {
        	applyCommon : null
        },
        methods: {
            drawTable: function (id) {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/wfmanager/sourcedata?id=' + id,
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
                    pageSize: 1000,
                    sortName: 'sort', //默认排序列名
                    sortOrder: 'desc', //默认排序方式
                    columns: [{field:'id',title:'id',align:'center'},
                    	{field:'node_id',title:'当前节点ID',align:'center'},
                    	{field:'super_node_id',title:'父节点',align:'center'},
                    	{field:'node_type',title:'当前节点类型',align:'center',
                            formatter: function(value, row, index) {
                                return "通用审批";
                            }
                    	},
                    	{field:'wf_nature',title:'流程性质',align:'center',
                            formatter: function(value, row, index) {
                                return "审批";
                            }
                    	},
                    	{field:'is_sign',title:'是否需要会签',align:'center',
                            formatter: function(value, row, index) {
                            	var val = value;
                            	if(value == "0"){
                            		val = "会签";
                            	}else if(value == "1"){
                            		val = "非会签";
                            	}
                                return val;
                            }
                    	},
                    	{field:'status',title:'节点状态',align:'center',
                            formatter: function(value, row, index) {
                            	var val = value;
                            	if(value == "RESET"){
                            		val = "<font color='#FF0000'>撤回</font>";
                            	}else if(value == "INVALIDATE"){
                            		val = "<font color='#FF0000'>无效</font>";
                            	}else if(value == "INIT"){
                            		val = "未开始";
                            	}else if(value == "PENDING"){
                            		val = "待审批";
                            	}else if(value == "ADD"){
                            		val = "添加";
                            	}else if(value == "COMPLETE"){
                            		val = "已审批";
                            	}
                                return val;
                            }
                    	},
                    	{field:'type',title:'申请单类型',align:'center',
                            formatter: function(value, row, index) {
                                return "通用审批";
                            }
                    	},
                    	{field:'approver_name',title:'审批人',align:'center'},
                    	{field:'create_timehms',title:'创建时间',align:'center'},
                    	{field:'read_timehms',title:'阅读时间',align:'center'},
                    	{field:'approve_timehms',title:'审批时间',align:'center'},
                    	{field:'approve_result',title:'审批结果',align:'center',
                            formatter: function(value, row, index) {
                            	var val = value;
                            	if(value == "ADDAFTER"){
                            		val = "之后添加";
                            	}else if(value == "ADDBEFORE"){
                            		val = "之前添加";
                            	}
                                return val;
                            }
                    	},
                    	{field:'remark',title:'备注',align:'center'},
                    	{field:'form_id',title:'表单ID',align:'center'},
                    	{field:'apply_code',title:'流水号',align:'center'},
                    	{field:'apply_title',title:'申请标题',align:'center'},
                    	{field:'apply_user_name',title:'申请人',align:'center'}]
                });
            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            sourceData: function (id) {
                var self = this;
    	    	Vue.http.get("/api/applyCommon/sourcedata?id={id}", {
                    params: {id: id}
                }).then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.applyCommon = res.data;
                    }
                });
            }
        },
        created: function () {
        	
        },
        ready: function () {
        	var id = this.$parseQueryString()['id']
            this.drawTable(id);
        	this.sourceData(id);
        }
    });
    // 实现弹窗方法
})(RocoUtils);