var vueIndex = null;
var business = "2"; // 出差
var leave = "1"; // 请假
+(function (RocoUtils) {
    $('#leveAndBusinessMenu').addClass('active');
    $('#myLevAffair').addClass('active');
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
                name: '我的申请',
                active: true // 激活面包屑的
            }],
            // 查询表单
            form: {
                keyword: '',	// 请假
                keywordSignature: '',// 费用
                keywordPayment: '',// 报销
                keywordHis: '',	// 历史
                type: '',
                wfType: 'HIS', // 查询历史审批
                status: '',
                signatureStatus: '',
                paymentStatus: ''
            },
            selectedRows: {}, // 选中列
            modalModel: null, // 模式窗体模型
            _$el: null, // 自己的 el $对象
            _$dataTable: null, // datatable $对象
            _$dataTableSignature: null,
            _$dataTablePayment: null,
            _$dataTableCommon: null,
            weixin: false
        },
        created: function () {
        	// debugger;
        	
        },
        ready: function () {
//        	 var itemKey = window.location.pathname;
//        	 var activeTab = sessionStorage.getItem(itemKey);
//        	 if(null == activeTab){
//        		 activeTab = 0;
//        	 }
//        	 var a = $("#tabCard roco-tabs-tab");
//        	 alert(activeTab)
            this.drawTable();
            this.clickEvent({key: 0});
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
            queryCommon: function () {
                this.$dataTableCommon.bootstrapTable('selectPage', 1);
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
            clickEvent: function (val) {// 选项卡单击事件
//            	 var itemKey = window.location.pathname;
//            	 sessionStorage.setItem(itemKey, val.key); 
                if (val.key == 0) {
                    this.form.type = "COMMON"
                    this.dataTableCommon();   // 通用
                } else if (val.key == 1) {
                	this.form.type = "APPROVED"
                    this.drawTableSignature();  // 费用
                } else if (val.key == 2) {
                    this.form.type = "LEAVE"
                    this.drawTable();  		  // 请假
                } else if (val.key == 3) {
                    this.drawTablePayment();  // 报销
                }
            },
            dataTableCommon: function () {
                var self = this;
                self.$dataTableCommon = $('#dataTableCommon', self._$el).bootstrapTable({
                    url: '/api/applyCommon/list',
                    method: 'get',
                    dataType: 'json',
                    cache: false, // 去缓存
                    pagination: true, // 是否分页
                    sidePagination: 'server', // 服务端分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        params.dataType = 'APPROVED'  // 我的申请
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', // 空数据的默认显示字符
                    striped: true, // 斑马线
                    maintainSelected: true, // 维护checkbox选项
                    columns: [{
                        field: 'id',
                        visible: false,
                        title: '申请编号',
                        align: 'center'
                    }, {
                        field: 'serial_number',
                        title: '申请编号',
                        align: 'center'
                    }, {
                        field: 'apply_timehms',
                        title: '提交时间',
                        align: 'center'
                    }, {
                        field: 'title',
                        title: '申请标题',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var id = row.id
                            return '<a href="/admin/commonApply/info?id=' + id + '&t='+new Date().getTime()+'"  >' + value + '</a>';
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
                        field: 'type',
                        title: '申请类型',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'apply_person_name',
                        title: '审批人',
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
                        field: '',
                        title: '操作',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var label = '';
                            if (row.status == 'REFUSE' || row.status == "RESET") {  // 状态是拒绝或者是撤回，都可以编辑
                                label += '<button data-handle="edit" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-primary">编辑</button>&nbsp;&nbsp;&nbsp;&nbsp;';
                            }
                            if (row.status != "REFUSE" && row.status != "RESET" && row.status != "COMPLETE" && row.status != "ADOPT") { // 不是撤回，并且不是已审批,不是已完成
                                label += '<button data-handle="reset" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-primary">撤回</button>&nbsp;&nbsp;&nbsp;&nbsp;';
                            }
                            if (!self.weixin) {  // 微信端不让下载
                                label += '<button data-handle="view-pdf" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-primary">预览</button>&nbsp;&nbsp;&nbsp;&nbsp;';
                                label += '<button data-handle="print-pdf" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">下载</button>&nbsp;&nbsp;&nbsp;&nbsp;';
                            }
                            return label;
                        }
                    }]
                });

                // 提交按钮
                self.$dataTableCommon.on('click', '[data-handle="submit"]', function (e) {
                    var id = $(this).data('id');
                    var type = $(this).data('type');
                    var data = {'id': id};
                    var url;
                    if (type == business) { // 出差
                        url = ctx + '/api/apply/applyBusinessAway/submit';
                    } else if (type == leave) {// 请假
                        url = ctx + '/api/vacations/submit';
                    }
                    self.$http.post(url, data, {emulateJSON: true}).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            self.$toastr.success('提交成功');
                            vueIndex.$dataTable.bootstrapTable('refresh');
                        } else {
                            self.$toastr.error(res.message);
                        }
                    });
                    e.stopPropagation();
                });

                // 编辑
                self.$dataTableCommon.on('click', '[data-handle="edit"]', function (e) {
                    var id = $(this).data('id');
                    window.location.href = '/admin/commonApply/edit?id=' + id;
                });
                // 打印
                self.$dataTableCommon.on('click', '[data-handle="print-pdf"]', function (e) {
                    var id = $(this).data('id');
                    window.location.href = '/api/applyCommon/print/' + id;
                });
                // 打印
                self.$dataTableCommon.on('click', '[data-handle="view-pdf"]', function (e) {
                    var id = $(this).data('id');
                    if (self.weixin) {  // 如果是微信，直接预览，如果是PC就打开一个新页面
                        window.location.href = '/api/applyCommon/view/' + id
                    } else {
                        window.open('/api/applyCommon/view/' + id);
                    }
                });
                // 撤回
                self.$dataTableCommon.on('click', '[data-handle="reset"]', function (e) {
                    var id = $(this).data('id');
                    // //////////////////////////////////////////////
                    swal({
                        title: '撤回操作',
                        text: '确认是否撤回？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.get("/api/applyCommon/reset/" + id, {}).then(function (response) {
                            var res = response.data;
                            if (res.code == '1') {
                                self.$dataTableCommon.bootstrapTable('selectPage', 1);
                            }
                        }).catch(function () {

                        }).finally(function () {
                            swal.close();
                        });
                    });
                });
                // 删除
                self.$dataTableCommon.on('click', '[data-handle="delete"]', function (e) {
                    var id = $(this).data('id');
                    var type = $(this).data('type');
                    if (type == business) { // 出差
                        url = ctx + '/api/apply/applyBusinessAway/delete';
                    } else if (type == leave) {// 请假
                        url = ctx + '/api/vacations/delete';
                    }
                    var data = {
                        id: id
                    }
                    swal({
                        title: '删除',
                        text: '确定删除此条申请记录？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.post(url, data, {emulateJSON: true}).then(function (response) {
                            var res = response.data;
                            if (res.code == '1') {
                                self.$toastr.success('删除成功');
                                vueIndex.$dataTable.bootstrapTable('refresh');
                            } else {
                                self.$toastr.error('删除失败');
                            }
                        }).catch(function () {

                        }).finally(function () {
                            swal.close();
                        });
                    });
                });

                // 详情
                self.$dataTableCommon.on('click', '[data-handle="view"]', function (e) {
                    var id = $(this).data('id');
                    var type = $(this).data('type');
                    window.location.href = "/api/applyCommon/edit?id=" + id;
                    e.stopPropagation();
                });

            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
                    url: '/api/vacationBusiness',
                    method: 'get',
                    dataType: 'json',
                    cache: false, // 去缓存
                    pagination: true, // 是否分页
                    sidePagination: 'server', // 服务端分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        params.type = "LEAVE"
                        params.wfType = "HIS"
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', // 空数据的默认显示字符
                    striped: true, // 斑马线
                    maintainSelected: true, // 维护checkbox选项
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
                        }, {
                            field: 'applyTitle',
                            title: '申请标题',
                            align: 'center',
                            formatter: function (value, row, index) {
                                return '<a data-handle="view" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                            }
                        }, {
                            field: 'reason',
                            title: '申请事由',
                            align: 'center',
                            formatter: function (val, row) {
                                var lable = "";
                                if (val.length > 5) {
                                    lable = val.substring(0, 5) + "...";
                                } else {
                                    lable = val;
                                }
                                return lable;
                            }
                        }, {
                            field: 'type',
                            title: '申请类型',
                            align: 'center',
                            visible: false
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
                        }, {
                            field: '', // 将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                            title: "操作",
                            align: 'center',
                            formatter: function (value, row, index) {
                                var btns = '';
                                if (row.status == 'DRAFT') {
                                    if (RocoUtils.hasPermission('leave:submit'))
                                        btns += '<button data-handle="submit" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">提交</button>&nbsp;';
                                    if (RocoUtils.hasPermission('leave:edit'))
                                        btns += '<button data-handle="edit" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">编辑</button>&nbsp;';
                                    if (RocoUtils.hasPermission('leave:delete'))
                                        btns += '<button data-handle="delete" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-danger">删除</button>&nbsp;';
                                }
                                if (row.status == 'REFUSE') {
                                    if (RocoUtils.hasPermission('leave:edit'))
                                        btns += '<button data-handle="edit" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">编辑</button>&nbsp;';
                                }
                                return btns;
                            }
                        }]
                });

                // 提交按钮
                self.$dataTable.on('click', '[data-handle="submit"]', function (e) {
                    var id = $(this).data('id');
                    var type = $(this).data('type');
                    var data = {'id': id};
                    var url;
                    if (type == business) { // 出差
                        url = ctx + '/api/apply/applyBusinessAway/submit';
                    } else if (type == leave) {// 请假
                        url = ctx + '/api/vacations/submit';
                    }
                    self.$http.post(url, data, {emulateJSON: true}).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            self.$toastr.success('提交成功');
                            vueIndex.$dataTable.bootstrapTable('refresh');
                        } else {
                            self.$toastr.error(res.message);
                        }
                    });
                    e.stopPropagation();
                });

                // 编辑
                self.$dataTable.on('click', '[data-handle="edit"]', function (e) {
                    var id = $(this).data('id');
                    var type = $(this).data('type');
                    if (type == business) { // 出差
                        url = ctx + '/admin/businessAdd?id=' + id;
                    } else if (type == leave) {// 请假
                        url = ctx + '/admin/vacationAdd?id=' + id;
                    }
                    window.location.href = url;
                });

                // 删除
                self.$dataTable.on('click', '[data-handle="delete"]', function (e) {
                    var id = $(this).data('id');
                    var type = $(this).data('type');
                    if (type == business) { // 出差
                        url = ctx + '/api/apply/applyBusinessAway/delete';
                    } else if (type == leave) {// 请假
                        url = ctx + '/api/vacations/delete';
                    }
                    var data = {
                        id: id
                    }
                    swal({
                        title: '删除',
                        text: '确定删除此条申请记录？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.post(url, data, {emulateJSON: true}).then(function (response) {
                            var res = response.data;
                            if (res.code == '1') {
                                self.$toastr.success('删除成功');
                                vueIndex.$dataTable.bootstrapTable('refresh');
                            } else {
                                self.$toastr.error('删除失败');
                            }
                        }).catch(function () {

                        }).finally(function () {
                            swal.close();
                        });
                    });
                });

                // 详情
                self.$dataTable.on('click', '[data-handle="view"]', function (e) {
                    var id = $(this).data('id');
                    var type = $(this).data('type');
                    var url;
                    if (type == business) { // 出差
                        url = '/api/apply/applyBusinessAway/' + id;
                        type = "BUSINESS";
                    } else if (type == leave) {// 请假
                        url = '/api/vacation/info?id=' + id;
                        type = "LEAVE";
                    }
                    // window.location.href =
					// "/admin/businessAway/detailContainer?id=" + id + "&type="
					// + type + "&applyUrl=" + url;
                    window.location.href = url
                    e.stopPropagation();
                });
            },
            drawTableSignature: function () {
                var self = this;
                self.$dataTableSignature = $('#dataTableSignature', self._$el).bootstrapTable({
                    url: '/api/signatures',
                    method: 'get',
                    dataType: 'json',
                    cache: false, // 去缓存
                    pagination: true, // 是否分页
                    sidePagination: 'server', // 服务端分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        self.form.keyword = self.form.keywordSignature;
                        self.form.status = self.form.signatureStatus;
                        self.form.type = 'APPROVED';
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', // 空数据的默认显示字符
                    striped: true, // 斑马线
                    maintainSelected: true, // 维护checkbox选项
                    sortName: 'id', // 默认排序列名
                    sortOrder: 'desc', // 默认排序方式
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
                        align: 'center',
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
                    }, {
                        field: '', // 将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            if (row.status == 'REFUSE') {
                                fragment += ('<button data-handle="operate-edit" data-id="'
                                + row.id + '" data-type="' + row.type + '" type="button" class="m-r-xs btn btn-xs btn-default">编辑</button>');
                            }
                            // fragment += ('<button
                            // data-handle="operate-multiDetail" data-id="' +
                            // row.id + '" type="button" class="m-r-xs btn
                            // btn-xs btn-default">审批详情</button>');

                            if (row.status == 'DRAFT') {
                                if (RocoUtils.hasPermission('signature:submit'))
                                    fragment += ('<button data-handle="operate-commit" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-default">提交</button>');
                                if (RocoUtils.hasPermission('signature:edit'))
                                    fragment += ('<button data-handle="operate-edit" data-id="' + row.id + '" data-type="' + row.type + '" class="m-r-xs btn btn-xs btn-default">编辑</button>');
                                if (RocoUtils.hasPermission('signature:delete'))
                                    fragment += ('<button data-handle="operate-delete" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-danger">删除</button>');
                            }
                            if (!self.weixin) {  // 微信端不让下载
                                fragment += ('<button data-handle="view-pdf" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-primary">预览</button>&nbsp;');
                                fragment += ('<button data-handle="print-pdf" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-primary">下载</button>&nbsp;');
                            }
                            return fragment;
                        }
                    }]
                });

                // 打印
                self.$dataTableSignature.on('click', '[data-handle="print-pdf"]', function (e) {
                    var id = $(this).data('id');
                    window.location.href = '/api/signatures/print/' + id;
                });
                // 预览
                self.$dataTableSignature.on('click', '[data-handle="view-pdf"]', function (e) {
                    var id = $(this).data('id');
                    if (self.weixin) {  // 如果是微信，直接预览，如果是PC就打开一个新页面
                        window.location.href = '/api/signatures/viewSignature/' + id
                    } else {
                        window.open('/api/signatures/viewSignature/' + id);
                    }
                });
                // 编辑费用
                self.$dataTableSignature.on('click', '[data-handle="operate-edit"]', function (e) {
                    var id = $(this).data('id');
                    var type = $(this).data('type');
                    window.location.href = '/admin/applySignature?type=' + type + '&id=' + id;
                    e.stopPropagation();
                });

                // 费用详情
                self.$dataTableSignature.on('click', '[data-handle="operate-detail"]', function (e) {
                    var id = $(this).data('id');
                    // self.$http.get('/api/signatures/' + id +
                    // '/get').then(function (res) {
                    // if (res.data.code == 1) {
                    // var signature = res.data.data;
                    // this.showSignatureDetail(signature);
                    // }
                    // });
                    var url = '/api/signature/info?id=' + id;
                    window.location.href = url;
                    // var url = '/api/signatures/' + id + '/get';
                    // window.location.href =
					// "/admin/businessAway/detailContainer?id=" + id +
					// "&type=SIGNATURE&applyUrl=" + url;
                    e.stopPropagation();
                });

                // 签报审核时候的费用详情
                self.$dataTableSignature.on('click', '[data-handle="operate-multiDetail"]', function (e) {
                    var id = $(this).data('id');
                    self.$http.get('/api/signatures/multiple/' + id).then(function (res) {
                        if (res.data.code == 1) {
                            var signature = res.data.data;
                            this.showMultDetail(signature);
                        }
                    });
                    e.stopPropagation();
                });

                // 提交草稿
                self.$dataTableSignature.on('click', '[data-handle="operate-commit"]', function (e) {
                    var id = $(this).data('id');
                    swal({
                        title: '提交费用',
                        text: '确定提交该费用么？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.get('/api/signatures/' + id + '/commit').then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                self.$dataTableSignature.bootstrapTable('refresh');
                            } else {
                                self.$toastr.error(res.data.message);
                            }
                        }).catch(function () {

                        }).finally(function () {
                            swal.close();
                        });
                    });
                    e.stopPropagation();
                });

                self.$dataTableSignature.on('click', '[data-handle="operate-delete"]', function (e) {
                    var id = $(this).data('id');
                    swal({
                        title: '删除费用',
                        text: '确定删除该费用么？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.delete('/api/signatures/' + id + '/del').then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                self.$dataTableSignature.bootstrapTable('refresh');
                            }
                        }).catch(function () {

                        }).finally(function () {
                            swal.close();
                        });
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
                    cache: false, // 去缓存
                    pagination: true, // 是否分页
                    sidePagination: 'server', // 服务端分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        self.form.keyword = self.form.keywordPayment;
                        self.form.status = self.form.paymentStatus;
                        params.dataType = 'APPROVED'  // 我的申请
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', // 空数据的默认显示字符
                    striped: true, // 斑马线
                    maintainSelected: true, // 维护checkbox选项
                    sortName: 'id', // 默认排序列名
                    sortOrder: 'desc', // 默认排序方式
                    columns: [{
                        field: 'applyCode',
                        title: '申请编码',
                        align: 'center'
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
                            return '<a data-handle="operate-paymentDetail" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                        }
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
                            } else if (value == 'REIMBURSED') {
                                label = '已报销';
                            } else if (value == 'REFUSE') {
                                label = '拒绝';
                            } else {
                                label = '待【' + row.currentApproverName + '】审批';
                            }
                            return label;
                        }
                    }, {
                        field: '', // 将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            if (row.status == 'REFUSE') {
                                fragment += ('<button data-handle="operate-edit" data-id="' + row.id + '" data-type="' + row.type + '" type="button" class="m-r-xs btn btn-xs btn-default">编辑</button>');
                            }
                            // fragment += ('<button
                            // data-handle="operate-multiDetail" data-id="' +
                            // row.id + '" type="button" class="m-r-xs btn
                            // btn-xs btn-default">审批详情</button>');


                            if (row.status == 'DRAFT') {
                                if (RocoUtils.hasPermission('payment:submit'))
                                    fragment += ('<button data-handle="operate-commit" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-default">提交</button>');
                                if (RocoUtils.hasPermission('payment:edit'))
                                    fragment += ('<button data-handle="operate-edit" data-id="' + row.id + '" data-type="' + row.type + '" class="m-r-xs btn btn-xs btn-default">编辑</button>');
                                if (RocoUtils.hasPermission('payment:delete'))
                                    fragment += ('<button data-handle="operate-delete" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-danger">删除</button>');
                            }
                            if (!self.weixin) {  // 微信端不让下载
                                fragment += ('<button data-handle="view-pdf" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-primary">预览</button>&nbsp;');
                                fragment += ('<button data-handle="print-pdf" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-primary">下载</button>&nbsp;');
                            }
                            return fragment;
                        }
                    }]
                });

                // 打印
                self.$dataTablePayment.on('click', '[data-handle="print-pdf"]', function (e) {
                    var id = $(this).data('id');
                    window.location.href = '/api/payments/print/' + id;
                });
                // 预览
                self.$dataTablePayment.on('click', '[data-handle="view-pdf"]', function (e) {
                    var id = $(this).data('id');
                    if (self.weixin) {  // 如果是微信，直接预览，如果是PC就打开一个新页面
                        window.location.href = '/api/payments/viewPayment/' + id
                    } else {
                        window.open('/api/payments/viewPayment/' + id);
                    }
                });
                // 编辑报销
                self.$dataTablePayment.on('click', '[data-handle="operate-edit"]', function (e) {
                    var id = $(this).data('id');
                    var type = $(this).data('type');
                    window.location.href = '/admin/applyPayment?type=' + type + '&id=' + id;
                    e.stopPropagation();
                });

                // 报销详情
                self.$dataTablePayment.on('click', '[data-handle="operate-paymentDetail"]', function (e) {
                    var id = $(this).data('id');
                    var url = '/api/payments/info?id=' + id;
                    window.location.href = url;
                    e.stopPropagation();
                });

                // 预算审核时候的报销详情
                self.$dataTablePayment.on('click', '[data-handle="operate-multiDetail"]', function (e) {
                    var id = $(this).data('id');
                    self.$http.get('/api/payments/multiple/' + id).then(function (res) {
                        if (res.data.code == 1) {
                            var payment = res.data.data;
                            this.showMultDetail(payment);
                        }
                    });
                    e.stopPropagation();
                });

                // 提交草稿
                self.$dataTablePayment.on('click', '[data-handle="operate-commit"]', function (e) {
                    var id = $(this).data('id');
                    swal({
                        title: '提交报销',
                        text: '确定提交该报销么？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.get('/api/payments/' + id + '/commit').then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                self.$dataTablePayment.bootstrapTable('refresh');
                            } else {
                                self.$toastr.error(res.data.message);
                            }
                        }).catch(function () {

                        }).finally(function () {
                            swal.close();
                        });
                    });
                    e.stopPropagation();
                });

                self.$dataTablePayment.on('click', '[data-handle="operate-delete"]', function (e) {
                    var id = $(this).data('id');
                    swal({
                        title: '删除报销',
                        text: '确定删除该报销么？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.delete('/api/payments/' + id + '/del').then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                self.$dataTablePayment.bootstrapTable('refresh');
                            }
                        }).catch(function () {

                        }).finally(function () {
                            swal.close();
                        });
                    });
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
            showPaymentDetail: function (payment) {
                var _$modal = $('#paymentDetail').clone();
                var modal = _$modal.modal({
                    height: 680,
                    maxHeight: 680,
                    backdrop: 'static',
                    keyboard: false
                });
                paymentDetail(modal, payment);
            },
            showMultDetail: function (payment) {
                var _$modal = $('#multDetail').clone();
                var modal = _$modal.modal({
                    height: 680,
                    maxHeight: 680,
                    backdrop: 'static',
                    keyboard: false
                });
                multDetail(modal, payment);
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
        var vueSignatureDetail = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, // 模式窗体 jQuery 对象
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
        return vueSignatureDetail;
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
        var vuePaymentDetail = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, // 模式窗体 jQuery 对象
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
                },
                showSignatureDetail: function (e) {
                    var self = this;
                    var signatureId = self.payment.signatureId;
                    self.$http.get('/api/signatures/' + signatureId + '/get').then(function (res) {
                        if (res.data.code == 1) {
                            var signature = res.data.data;
                            var _$modal = $('#signatureDetail').clone();
                            var modal = _$modal.modal({
                                height: 580,
                                maxHeight: 580,
                                backdrop: 'static',
                                keyboard: false
                            });
                            signatureDetail(modal, signature);
                        }
                    });
                    e.stopPropagation();
                }
            }
        });
        // 创建的Vue对象应该被返回
        return vuePaymentDetail;
    }

    // 审批人查看报销详情弹窗
    function multDetail($el, payment) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        var vueMultDetail = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, // 模式窗体 jQuery 对象
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
                            'type': 'BUDGET',
                            'showType': 'personal'
                        }
                    }).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            self.approveList = res.data;
                        }
                    });
                },
                showSignatureDetail: function (e) {
                    var self = this;
                    var signatureId = self.payment.signatureId;
                    self.$http.get('/api/signatures/' + signatureId + '/get').then(function (res) {
                        if (res.data.code == 1) {
                            var signature = res.data.data;
                            var _$modal = $('#signatureDetail').clone();
                            var modal = _$modal.modal({
                                height: 580,
                                maxHeight: 580,
                                backdrop: 'static',
                                keyboard: false
                            });
                            signatureDetail(modal, signature);
                        }
                    });
                    e.stopPropagation();
                }
            }
        });
        // 创建的Vue对象应该被返回
        return vueMultDetail;
    }

})(this.RocoUtils);

