/**
 * Created by andy on 2017-3-26 10:08:45
 */
var vueIndex;
+(function() {
    $('#myNeedDo').addClass('active');
    $('#waitApproval').addClass('active');
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
                name: '待审批',
                active: true//激活面包屑的
            }],
            // 查询表单
            form: {
                keyword: '',
                keywordSignature: '',
                keywordPayment: '',
                type: 'LEAVE',
                isFromHomePage: false
            },
            selectedRows: {},
            //选中列
            modalModel: null,
            //模式窗体模型
            _$el: null,
            //自己的 el $对象
            _$dataTable: null,
            //datatable $对象
            _$dataTableSignature: null,
            _$dataTablePayment: null,
            _$dataTableCommon: null,
            status: null,
            turnEmployees : [], //转派人员
            newCCPersonInfo : [] //新添加抄送人
        },
        created: function() {
            this.status = this.$parseQueryString()['type'];
        },
        ready: function() {
            this.drawTable();
            this.clickEvent({
                key: 0
            });
        },
        methods: {
        	closeCommitaddApprover: function(){
        		alert('close closeCommitaddApprover');
        	},
            query: function() {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            queryCommon: function() {
            	this.$dataTableCommon.bootstrapTable('selectPage', 1);
            },
            querySignature: function() {
                this.$dataTableSignature.bootstrapTable('selectPage', 1);
            },
            queryPayment: function() {
                this.$dataTablePayment.bootstrapTable('selectPage', 1);
            },
            clickEvent: function(val) {
                var self = this;
                if (val.key == 0) {
                    self.form.type == "COMMON"
                    this.dataTableCommon();
                    //通用审批
                } else if (val.key == 1) {
                    self.form.type == "SIGNATURE"
                    this.drawTableSignature();
                    //费用审批
                } else if (val.key == 2) {
                    self.form.type == "LEAVE"
                    this.drawTable();
                    //请假
                } else if (val.key == 3) {
                    self.form.type == "EXPENSE"
                    this.drawTablePayment();
                    //报销
                }
            },
            drawTable: function() {
                var self = this;
                self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
                    url: '/api/wfmanager/list',
                    method: 'get',
                    dataType: 'json',
                    cache: false,
                    //去缓存
                    pagination: true,
                    //是否分页
                    sidePagination: 'server',
                    //服务端分页
                    queryParams: function(params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-',
                    //空数据的默认显示字符
                    striped: true,
                    //斑马线
                    maintainSelected: true,
                    //维护checkbox选项
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
                        field: 'applyTitle',
                        title: '申请标题',
                        align: 'center',
                        formatter: function(value, row, index) {
                            return '<a data-handle="wf-approve" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                        }
                    }, {
                        field: 'applyUserName',
                        title: '申请人',
                        align: 'center'
                    }, {
                        field: 'applyCode',
                        title: '申请编码',
                        align: 'center'
                    }, {
                        field: 'createTime',
                        title: '提交时间',
                        align: 'center'
                    }, {
                        field: 'applyUserId',
                        title: '申请人ID',
                        align: 'center',
                        visible: false
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
                    }, {
                        field: '',
                        //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function(value, row, index) {
                            var button = "";
                            button += '<button data-handle="agree" data-order="' + row + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-primary">通过</button>&nbsp;&nbsp;';
                            button += '<button data-handle="refuse" data-order="' + row + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-danger">拒绝</button>';
                            return button;
                        }
                    }]
                });

                /**
         * 通过
         */
                self.$dataTable.on('click', '[data-handle="agree"]', function(e) {
                    var id = $(this).data('id');
                    var index = $(this).data('index');
                    var rowData = self.$dataTable.bootstrapTable('getData');
                    var curData = rowData[index];
                    quickApproveEx(self, curData, "AGREE", self.$dataTable);
                    e.stopPropagation();
                });

                /**
         * 拒绝
         */
                self.$dataTable.on('click', '[data-handle="refuse"]', function(e) {
                    var id = $(this).data('id');
                    var index = $(this).data('index');
                    var rowData = self.$dataTable.bootstrapTable('getData');
                    var curData = rowData[index];
                    quickApproveEx(self, curData, "REFUSE",self.$dataTable);
                    e.stopPropagation();
                });

                /**
         * 审批详情
         */
                self.$dataTable.on('click', '[data-handle="wf-approve"]', function(e) {
                    var id = $(this).data('id');
                    var index = $(this).data('index');
                    var rowData = self.$dataTable.bootstrapTable('getData');
                    var curData = rowData[index];
                    window.location.href = "/admin/approval/detail?id=" + id + "&type=" + curData.type + "&isApprove=true";
                    //          approvalDetail(self,curData.type,curData);
                    e.stopPropagation();
                });
            },
            drawTableSignature: function() {
                var self = this;
                self.$dataTableSignature = $('#dataTableSignature', self._$el).bootstrapTable({
                    url: '/api/signatures',
                    method: 'get',
                    dataType: 'json',
                    cache: false,
                    //去缓存
                    pagination: true,
                    //是否分页
                    sidePagination: 'server',
                    //服务端分页
                    queryParams: function(params) {
                        // 将table 参数与搜索表单参数合并
                        self.form.keyword = self.form.keywordSignature;
                        self.form.type = 'PENDING';
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-',
                    //空数据的默认显示字符
                    striped: true,
                    //斑马线
                    maintainSelected: true,
                    //维护checkbox选项
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
                        field: 'applyCode',
                        title: '申请编码',
                        align: 'center'
                    }, {
                        field: 'createDate',
                        title: '提交时间',
                        align: 'center'
                    }, {
                        field: 'applyUserId',
                        title: '申请人ID',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'createUser',
                        title: '申请人',
                        align: 'center',
                        formatter: function(value, row, index) {
                            return value['name']
                        }
                    }, {
                        field: 'costItemName',
                        title: '费用科目',
                        align: 'center'
                    }, {
                        field: 'applyTitle',
                        title: '申请标题',
                        align: 'center',
                        formatter: function(value, row, index) {
                            return '<a data-handle="wf-signatureApprove" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                        }
                    }, {
                        field: 'nodeType',
                        title: '节点类型',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'totalMoney',
                        title: '费用金额',
                        align: 'center'
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
                    }, {
                        field: '',
                        //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function(value, row, index) {
                            var button = "";
                            button += '<button data-handle="agree" data-order="' + row + '" data-index="' + index + '" data-id="' + row.id + '" data-wfid="' + row.wfId + '" type="button" class="btn btn-xs btn-primary">通过</button>&nbsp;&nbsp;';
                            button += '<button data-handle="refuse" data-order="' + row + '" data-index="' + index + '" data-id="' + row.id + '"  data-wfid="' + row.wfId + '" type="button" class="btn btn-xs btn-danger">拒绝</button>';
                            return button;
                        }
                    }]
                });

                /**
           * 通过
           */
                self.$dataTableSignature.on('click', '[data-handle="agree"]', function(e) {
                    var id = $(this).data('id');
                    var wfid = $(this).data('wfid');
                    var index = $(this).data('index');
                    var rowData = self.$dataTableSignature.bootstrapTable('getData');
                    var curData = rowData[index];
                    curData.id = wfid;
                    quickApproveEx(self, curData, "AGREE", self.$dataTableSignature);
                    e.stopPropagation();
                });

                /**
           * 拒绝
           */
                self.$dataTableSignature.on('click', '[data-handle="refuse"]', function(e) {
                    var id = $(this).data('id');
                    var wfid = $(this).data('wfid');
                    var index = $(this).data('index');
                    var rowData = self.$dataTableSignature.bootstrapTable('getData');
                    var curData = rowData[index];
                    curData.id = wfid;
                    quickApproveEx(self, curData, "REFUSE",self.$dataTableSignature);
                    e.stopPropagation();
                });

                /**
           * 审批详情
           */
                self.$dataTableSignature.on('click', '[data-handle="wf-signatureApprove"]', function(e) {
                    var id = $(this).data('id');
                    var url = '/api/signature/info?id=' + id;
                    window.location.href = url;
                    // var index = $(this).data('index');
                    // var rowData = self.$dataTableSignature.bootstrapTable('getData');
                    // var curData = rowData[index];
                    // window.location.href = "/admin/approval/detail?id=" + id + "&type=" + curData.type + "&isApprove=true";
                    //            approvalDetail(self,curData.type,curData);
                    e.stopPropagation();
                });
            },
            drawTablePayment: function() {
                var self = this;
                self.$dataTablePayment = $('#dataTablePayment', self._$el).bootstrapTable({
                    url: '/api/payments',
                    method: 'get',
                    dataType: 'json',
                    cache: false,
                    //去缓存
                    pagination: true,
                    //是否分页
                    sidePagination: 'server',
                    //服务端分页
                    queryParams: function(params) {
                        // 将table 参数与搜索表单参数合并
                        self.form.keyword = self.form.keywordPayment;
                        self.form.type = 'EXPENSE';
                        params.dataType = 'PENDING'  // 我的申请
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-',
                    //空数据的默认显示字符
                    striped: true,
                    //斑马线
                    maintainSelected: true,
                    //维护checkbox选项
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
                        field: 'applyCode',
                        title: '申请编码',
                        align: 'center' 
                    }, {
                        field: 'paymentDate',
                        title: '提交时间',
                        align: 'center'
                    }, {
                        field: 'applyUserId',
                        title: '申请人ID',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'createUser',
                        title: '申请人',
                        align: 'center',
                        formatter: function(value, row, index) {
                            return value.name;
                        }
                    }, {
                        field: 'applyTitle',
                        title: '申请标题',
                        align: 'center',
                        formatter: function(value, row, index) {
                            return '<a data-handle="wf-apymentApprove" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                        }
                    }, {
                        field: 'nodeType',
                        title: '节点类型',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'costItemName',
                        title: '费用科目',
                        align: 'center'
                    }, {
                        field: 'totalMoney',
                        title: '报销金额',
                        align: 'center'
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
                    }, {
                        field: '',
                        //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function(value, row, index) {
                            var button = "";
                            button += '<button data-handle="agree" data-order="' + row + '" data-index="' + index + '" data-wfid="' + row.wfId + '" type="button" class="btn btn-xs btn-primary">通过</button>&nbsp;&nbsp;';
                            button += '<button data-handle="refuse" data-order="' + row + '" data-index="' + index + '" data-wfid="' + row.wfId + '" type="button" class="btn btn-xs btn-danger">拒绝</button>';
                            return button;
                        }
                    }]
                });

                /**
             * 通过
             */
                self.$dataTablePayment.on('click', '[data-handle="agree"]', function(e) {
                    var id = $(this).data('id');
                    var wfid = $(this).data('wfid');
                    var index = $(this).data('index');
                    var rowData = self.$dataTablePayment.bootstrapTable('getData');
                    var curData = rowData[index];
                    curData.id = wfid;
                    quickApproveEx(self, curData, "AGREE", self.$dataTablePayment);
                    e.stopPropagation();
                });

                /**
             * 拒绝
             */
                self.$dataTablePayment.on('click', '[data-handle="refuse"]', function(e) {
                    var id = $(this).data('id');
                    var wfid = $(this).data('wfid');
                    var index = $(this).data('index');
                    var rowData = self.$dataTablePayment.bootstrapTable('getData');
                    var curData = rowData[index];
                    curData.id = wfid;
                    quickApproveEx(self, curData, "REFUSE",self.$dataTablePayment);
                    e.stopPropagation();
                });

                /**
             * 审批详情
             */
                self.$dataTablePayment.on('click', '[data-handle="wf-apymentApprove"]', function(e) {
                    var id = $(this).data('id');
                    var url = '/api/payments/info?id=' + id;
                    window.location.href = url;
                    //              var index = $(this).data('index');
                    //              var rowData = self.$dataTablePayment.bootstrapTable('getData');
                    //              var curData = rowData[index];
                    //              window.location.href = "/admin/approval/detail?id=" + id + "&type=" + curData.type + "&isApprove=true";
                    //              approvalDetail(self,curData.type,curData);
                    e.stopPropagation();
                });
            },
            dataTableCommon: function() {
                var self = this;
                self.$dataTableCommon = $('#dataTableCommon', self._$el).bootstrapTable({
                    url: '/api/applyCommon/list',
                    method: 'get',
                    dataType: 'json',
                    cache: false,
                    //去缓存
                    pagination: true,
                    //是否分页
                    sidePagination: 'server',
                    //服务端分页
                    queryParams: function(params) {
                        // 将table 参数与搜索表单参数合并
                        self.form.keyword = self.form.keywordSignature;
                        self.form.type = 'COMMON'
                        self.form.dataType = 'PENDING'  //待审批
                        //待审批
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-',
                    //空数据的默认显示字符
                    striped: true,
                    //斑马线
                    maintainSelected: true,
                    //维护checkbox选项
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
                            return '<a data-handle="wf-signatureApprove" data-type="COMMON" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
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
                    }, {
                        field: '',
                        //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        visible: false,
                        //该操作已不显示
                        formatter: function(value, row, index) {
                            var button = "";
                            button += "<div class=\"btn-group btn-group-xs\">\n";
                            button += '<button data-handle="turn-send" data-order="' + row + '" data-index="' + index + '" data-id="' + row.id + '" data-wfid="' + row.wfid + '" type="button" class="btn btn-xs btn-primary">转派</button>&nbsp;&nbsp;';
                            button += '<button data-handle="before-add" data-order="' + row + '" data-index="' + index + '" data-id="' + row.id + '" data-wfid="' + row.wfid + '" type="button" class="btn btn-xs btn-primary">前添加审批人</button>&nbsp;&nbsp;';
                            button += '<button data-handle="after-add" data-order="' + row + '" data-index="' + index + '" data-id="' + row.id + '" data-wfid="' + row.wfid + '" type="button" class="btn btn-xs btn-primary">后添加审批人</button>&nbsp;&nbsp;';
                            button += '<button data-handle="add-ccuser" data-order="' + row + '" data-index="' + index + '" data-id="' + row.id + '" data-wfid="' + row.wfid + '" type="button" class="btn btn-xs btn-primary">添加抄送人</button>&nbsp;&nbsp;';
                            button += '<button data-handle="agree" data-order="' + row + '" data-index="' + index + '" data-id="' + row.id + '" data-wfid="' + row.wfid + '" type="button" class="btn btn-xs btn-primary">通过</button>&nbsp;&nbsp;';
                            button += '<button data-handle="refuse" data-order="' + row + '" data-index="' + index + '" data-id="' + row.id + '" data-wfid="' + row.wfid + '"  type="button" class="btn btn-xs btn-danger">拒绝</button>';
                            button += '</div>';
                            return button;
                        }
                    }, {
                        field: '',
                        //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function(value, row, index) {
                            var button = "";
                            button += "\n";
                            button += '&nbsp;&nbsp;&nbsp;&nbsp;<button data-handle="agree" data-order="' + row + '" data-index="' + index + '" data-id="' + row.id + '" data-wfid="' + row.wfid + '" type="button" class="btn btn-xs btn-primary">通过</button>';
                            button += '&nbsp;&nbsp;&nbsp;&nbsp;<button data-handle="refuse" data-order="' + row + '" data-index="' + index + '" data-id="' + row.id + '" data-wfid="' + row.wfid + '"  type="button" class="btn btn-xs btn-danger">拒绝</button>';
                            button += '&nbsp;&nbsp;&nbsp;&nbsp;<button data-handle="more" data-order="' + row + '" data-index="' + index + '" data-id="' + row.id + '" data-wfid="' + row.wfid + '" data-apply_person="' + row.apply_person + '"  type="button" class="btn btn-xs ">更多</button>';
                            button += '';
                            return button;
                        }
                    }]
                });
                //更多
                self.$dataTableCommon.on('click', '[data-handle="more"]', function(e) {
                	var wfid = $(this).data('wfid');
                	var id = $(this).data('id');
                	var applyPerson = $(this).data('apply_person') + ""; 
                	//转字符串 number没能indexOf方法
                	var applyPersonArrays= [];
                	if(applyPerson.indexOf(',') > -1){
                		applyPersonArrays = applyPerson.split(",");// 
                	}else{
                		applyPersonArrays.push(applyPerson);
                	}
                	moreExec(id,wfid,applyPersonArrays);
                });
                /**
               * 通过
               */
                self.$dataTableCommon.on('click', '[data-handle="agree"]', function(e) {
                    var id = $(this).data('id');
                    var wfid = $(this).data('wfid');
                    var index = $(this).data('index');
                    var rowData = self.$dataTableCommon.bootstrapTable('getData');
                    var curData = rowData[index];
                    curData.id = wfid
                    quickApproveEx(self, curData, "AGREE", self.$dataTableCommon);
                    e.stopPropagation();
                });

                /**
               * 拒绝
               */
                self.$dataTableCommon.on('click', '[data-handle="refuse"]', function(e) {
                    var id = $(this).data('id');
                    var wfid = $(this).data('wfid');
                    var index = $(this).data('index');
                    var rowData = self.$dataTableCommon.bootstrapTable('getData');
                    var curData = rowData[index];
                    curData.id = wfid
                    quickApproveEx(self, curData, "REFUSE",self.$dataTableCommon);
                    e.stopPropagation();
                });

                /**
               * 审批详情
               */
                self.$dataTableCommon.on('click', '[data-handle="wf-signatureApprove"]', function(e) {
                    var id = $(this).data('id');
                    var index = $(this).data('index');
                    var rowData = self.$dataTableCommon.bootstrapTable('getData');
                    var curData = rowData[index];
                    var url = "/admin/commonApply/info?id=" + id+"&t=" + new Date().getTime();
                    window.location.href = url;
                    //window.location.href = "/admin/approval/detail?id=" + id + "&type=COMMON&isApprove=true";
                    //                approvalDetail(self,curData.type,curData);
                    e.stopPropagation();
                });
            }

        }
    });

}
)(RocoUser);
