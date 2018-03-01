var vueIndex = null;
var showBill,addBill;
+(function (RocoUtils) {
    $('#singleSign').addClass('active');
    $('#departmentSign').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            $dataTable: null,
            form: {
                beginDate: '',
                endDate:'',
                statue:'',
                search:''
            }
        },
        methods: {
        	// 初始化时间选择器
            initDatePicker: function () {
            	var self = this;
              $(this.$els.entryDate).datetimepicker({
                minView: 2,
                format: 'yyyy-mm-dd',
                todayBtn: true
              });
              $(this.$els.endDate).datetimepicker({
                minView: 2,
                format: 'yyyy-mm-dd',
                todayBtn: true
              });
            },
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/singleSign/departmentList',
                    method: 'get',
                    dataType: 'json',
                    cache: false, // 去缓存
                    pagination: true, // 是否分页
                    sidePagination: 'server', // 服务端分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', // 空数据的默认显示字符
                    striped: true, // 斑马线
                    maintainSelected: true, // 维护checkbox选项
                    sortName: 'id', // 默认排序列名
                    sortOrder: 'desc', // 默认排序方式
                    columns: [{
                        field: 'pid',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'signCode',
                        title: '签报编码',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'punishCode',
                        title: '处罚编码',
                        align: 'center',
                        visible: false
                        
                    },{
                        field: 'customerName',
                        title: '客户姓名',
                        align: 'center'
                    },{
                        field: 'customerPhone',
                        title: '客户电话',
                        align: 'center'
                    },{
                        field: 'orderAddress',
                        title: '订单地址',
                        align: 'center'
                    },{
                        field: 'punishDepartment',
                        title: '责任部门',
                        align: 'center'
                       
                    },{
                        field: 'punishMan',
                        title: '责任人',
                        align: 'center',
                        formatter: function (value, row,index) {
                            /*
 							 * var department = self.getDepartmentName(row.org);
 							 * var company = self.getCompanyName(row.org);
 							 */
                             return '<a href="javascript:void(0)" data-handle="view" data-id="' + row.signCode + '"  data-index="' + index + '">' + value + '</a>';
                         }
                    },{
                        field: 'punishMoney',
                        title: '处罚金额',
                        align: 'center'
                    },{
                        field: 'punishReason',
                        title: '处罚原因',
                        align: 'center'
                    },
                    {
                        field: 'createTime',
                        title: '签报时间',
                        align: 'center',
                        formatter: function (time) {
                            if (time != null) {
                                return moment(time).format('YYYY-MM-DD');
                            }
                        }
                    },{
                        field: 'status',
                        title: '执行状态',
                        align: 'center',
                        sortable: true,
                        formatter: function(value, row, index) {
                            if(value == 0){
                            	return '未执行'
                            }else{
                            	return '执行'
                            }
                         }
                    },
                    {
                        field: '', // 将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            if(row.status == 0){
                            	fragment += ('<button data-handle="operate-edit" data-id="' + row.id +'" type="button" class="m-r-xs btn btn-xs btn-info">执行</button>');
                            }
                            return fragment;
                        }
                    }]
                });
                self.$dataTable.on('click', '[data-handle="view"]', function (e) {
                    var index = $(this).data('index');
                    var rowData = self.$dataTable.bootstrapTable('getData');
                    var curData = rowData[index];
                    showBillModel(curData);
                });
                
                
                self.$dataTable.on('click', '[data-handle="operate-edit"]', function (e) {
                    var id = $(this).data('id');
                    self.$http.get('/api/singleSign/updateStatus/' + id).then(function (res) {
                        if (res.data.code == 1) {
                        	vueIndex.$dataTable.bootstrapTable('refresh');
                        }
                    });
                });
            },
            exportBill:function(){ // 导出报表
            	var self = this;
            	var beginDate = self.form.beginDate;
            	var endDate = self.form.endDate;
            	var statue = self.form.statue;
            	var search = self.form.search;
            	window.open('/api/singleSign/export?beginDate='+beginDate+'&search='+search+'&endDate='+endDate+'&statue='+statue);
            },
            showModel: function () {
            },
            query: function () {
            	this.$dataTable.bootstrapTable('refresh');
            }
        },
        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
        	var self = this;
        	self.drawTable();
        	this.initDatePicker();
        }
    });
   
    /**
	 * 签报单明细详情
	 * 
	 * @param rowData
	 * @returns
	 */
    function showBillModel(rowData) {
        var _$modal = $('#billModal').clone();
        var $modal = _$modal.modal({
            height: 450,
            maxHeight: 500,
            backdrop: 'static',
            keyboard: false
        });
        // 获取 node
        var el = $modal.get(0);
        // 创建 Vue 对象编译节点
        showBill = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $modal, // 模式窗体 jQuery 对象
            data: {
                // 控制 按钮是否可点击
                disabled: false,
                signbill: {
        			signCode:rowData.signCode,
        			punishCode:rowData.punishCode,
        			status:rowData.status,
        			customerName:rowData.customerName,
        			customerPhone:rowData.customerPhone,
        			orderAddress:rowData.orderAddress,
        			punishDepartment:rowData.punishDepartment,
        			punishMan:rowData.punishMan,
        			punishMoney:rowData.punishMoney,
        			punishReason:rowData.punishReason,
        			time:moment(rowData.createTime).format('YYYY-MM-DD HH:mm:ss')
                }
            },
            methods: {},
            watch: {},
            created: function () {},
            ready: function () {}
        });
        // 创建的Vue对象应该被返回
        return showBill;
    }
})
(this.RocoUtils);