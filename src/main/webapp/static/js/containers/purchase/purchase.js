var vueIndex = null;
var vueModel = null;
+(function (RocoUtils) {
    $('#PurchaseMenu').addClass('active');
    $('#purchaseAffair').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        mixins: [RocoVueMixins.DataTableMixin],
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/admin/apply/purchaseApply',
                name: '我的申请-采购',
                active: true // 激活面包屑的
            }],
            // 查询表单
            form: {
                keyword: '',
                status: '',
                keywordHis: '',
                type: 'PURCHASE',
                wfType: 'HIS', // 查询历史审批
            },
            params: {
                firstTypeId: '',
                type: ''
            },
            selectedRows: {}, // 选中列
            modalModel: null, // 模式窗体模型
            _$el: null, // 自己的 el $对象
            _$dataTable: null, // datatable $对象
            _$dataTableHis: null
        },
        created: function () {
        },
        ready: function () {
            // this.activeDatepicker();
            this.drawTable();
            // this.drawTableHis();
        },
        methods: {
            clickEvent: function (val) {// 选项卡单击事件
                if (val = 1) {
                    this.drawTableHis();
                }
            },
            drawTableHis: function () {
                var self = this;
                if (self.type) {
                    self.form.type = self.type;
                }
                self.$dataTableHis = $('#dataTableHis', self._$el).bootstrapTable({
                    url: '/api/wfmanager/list',
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
                    columns: [
                        {
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
                            formatter: function (value, row, index) {
                                return '<a data-handle="hisView" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                            }
                        }, {
                            field: 'applyCode',
                            title: '申请编码',
                            align: 'center',
                            formatter: function (value, row, index) {
                                return '<a data-handle="hisView" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                            }
                        }, {
                            field: 'applyUserId',
                            title: '申请人ID',
                            align: 'center',
                            visible: false
                        }, {
                            field: 'applyUserName',
                            title: '申请人',
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
                            formatter: function (value, row, index) {
                                return formApproveStatus(value);
                            }
                        }, {
                            field: 'isSign',
                            title: '是否会签',
                            align: 'center',
                            visible: false,
                            formatter: function (value, row, index) {
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
                        }, {
                            field: 'approveResult',
                            title: '审批结果',
                            align: 'center',
                            formatter: function (value, row, index) {
                                return value == "AGREE" ? "<font color='green'>同意</font>" : "<font color='red'>拒绝</font>";
                            }
                        }, {
                            field: 'formId',
                            title: '表单ID',
                            align: 'center',
                            visible: false
                        }]
                });

                /**
                 * 审批历史详情
                 */
                self.$dataTableHis.on('click', '[data-handle="hisView"]', function (e) {
                    var id = $(this).data('id');
                    var index = $(this).data('index');
                    var rowData = self.$dataTableHis.bootstrapTable('getData');
                    var curData = rowData[index];
                    approvalDetail(self, curData.type, curData);
                    e.stopPropagation();
                });

            },
            fetchParent: function () {
                var self = this;
                this.$http.get('/api/dict/dic/getNode/2').then(function (res) {
                    if (res.data.code == 1) {
                        self.parents = res.data.data;

                    }
                }).catch(function () {

                }).finally(function () {

                });
            },
            fetchChildren: function (val) {
                var self = this;

                this.$http.post('/api/dict/dic/getByType/', self.params, {emulateJSON: true}).then(function (res) {
                    if (res.data.code == 1) {
                        self.children = res.data.data;
                    }
                }).catch(function () {

                }).finally(function () {

                });
            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
                    url: '/api/apply/applyPurchase/list',
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
                    columns: [
                        {
                            field: 'firstTypeId',
                            title: '申请标题',
                            align: 'center',
                            visible: false
                        },
                        {
                            field: 'applyTitle',
                            title: '申请标题',
                            align: 'center',
                            formatter: function (value, row, index) {
                                return '<a data-handle="view" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                            }
                        }, {
                            field: 'applyCode',
                            title: '申请编号',
                            align: 'center',
                            formatter: function (value, row, index) {
                                return '<a data-handle="view" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                            }
                        }, {
                            field: 'goodName',
                            title: '物品名称',
                            align: 'center'
                        }, {
                            field: 'goodNum',
                            title: '数量',
                            align: 'center'
                        }, {
                            field: 'goodPrice',
                            title: '单价',
                            align: 'center'
                        }, {
                            field: 'totalPrice',
                            title: '总价',
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
                                    label = '待【' + row.currentApproverName + '】审批';
                                }
                                return label;
                            }
                        }, {
                            field: 'createTime',
                            title: '提交日期',
                            align: 'center'
                        },
                        {
                            field: '', // 将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                            title: "操作",
                            align: 'center',
                            formatter: function (value, row, index) {
                                var btns = '';
                                if (row.status == 'DRAFT') {
                                    if (RocoUtils.hasPermission('purchase:submit'))
                                        btns += '<button data-handle="submit" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">提交</button>&nbsp;';
                                }
                                if (row.status == 'DRAFT' || row.status == 'REFUSE') {
                                    if (RocoUtils.hasPermission('purchase:edit'))
                                        btns += '<button data-handle="edit" data-type="' + row.type + '" data-index="' + index + '" data-firsttypeid="' + row.firstTypeId + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">编辑</button>&nbsp;';
                                }
                                if (row.status == 'DRAFT') {
                                    if (RocoUtils.hasPermission('purchase:delete'))
                                        btns += '<button data-handle="delete" data-applycode="' + row.applyCode + '" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-danger">删除</button>&nbsp;';
                                }
                                return btns;
                            }
                        }
                    ]
                })
                ;
                // 编辑
                self.$dataTable.on('click', '[data-handle="edit"]', function (e) {
                    var id = $(this).data('id');
                    var firstTypeId = $(this).data('firsttypeid');
                    window.location.href = '/admin/purchase/add?id=' + id + '&firstTypeId=' + firstTypeId;
                });
                // 编辑按钮
                self.$dataTable.on('click', '[data-handle="delete"]', function (e) {
                    var id = $(this).data('id');
                    var applyCode = $(this).data('applycode');
                    if (confirm('是否确定删除申请编号【' + applyCode + '】的请假信息?')) {
                        // var firData = $(this).data('first');
                        // var secData = $(this).data('second');
                        // self.params.firstTypeId = $(this).data('first');

                        self.$http.get('/api/apply/applyPurchase/delete/' + id).then(function (response) {
                            var res = response.data;
                            if (res.code == '1') {
                                self.$toastr.success('删除成功');

                                vueIndex.$dataTable.bootstrapTable('refresh');

                            }
                        });
                    }
                    // console.log($(this).data('first'));
                    e.stopPropagation();
                });
                // 提交按钮
                self.$dataTable.on('click', '[data-handle="submit"]', function (e) {
                    var id = $(this).data('id');
                    var data = {'id': id};

                    self.$http.post('/api/apply/applyPurchase/submit', data, {emulateJSON: true}).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            vueIndex.$dataTable.bootstrapTable('refresh');
                            self.$toastr.success('提交成功');
                        }else{
                        	self.$toastr.error(res.message);
                        }
                    });
                    e.stopPropagation();
                });
                // 查看按钮
                self.$dataTable.on('click', '[data-handle="view"]', function (e) {
                    var id = $(this).data('id');
                    self.$http.get('/api/apply/applyPurchase/' + id).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            var _$modal = $('#viewModal').clone();
                            var $modal = _$modal.modal({
                                height: 450,
                                maxHeight: 450,
                                backdrop: 'static',
                                keyboard: false
                            });

                            viewModal($modal, response.data.data, id);

                        }
                    });
                    e.stopPropagation();
                });

            },
            createBtnClickHandler: function (e) {
                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    height: 530,
                    maxHeight: 450,
                    backdrop: 'static',
                    keyboard: false
                });
                $modal.on('shown.bs.modal', function () {
                    modal($modal, {
                        id: '',
                        firstTypeId: '',
                        secondTypeId: '',
                        goodName: '',
                        purchaseMonth: '',
                        goodNum: '',
                        goodPrice: '',
                        description: '',
                        totalPrice: ''
                    }, false);
                });
            },
            drawTableHis: function () {
                var self = this;
                if (self.type) {
                    self.form.type = self.type;
                }
                self.$dataTableHis = $('#dataTableHis', self._$el).bootstrapTable({
                    url: '/api/wfmanager/list',
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
                    columns: [
                        {
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
                            formatter: function (value, row, index) {
                                return '<a data-handle="hisView" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                            }
                        }, {
                            field: 'applyCode',
                            title: '申请编码',
                            align: 'center',
                            formatter: function (value, row, index) {
                                return '<a data-handle="hisView" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                            }
                        }, {
                            field: 'applyUserId',
                            title: '申请人ID',
                            align: 'center',
                            visible: false
                        }, {
                            field: 'applyUserName',
                            title: '申请人',
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
                            formatter: function (value, row, index) {
                                return formApproveStatus(value);
                            }
                        }, {
                            field: 'isSign',
                            title: '是否会签',
                            align: 'center',
                            visible: false,
                            formatter: function (value, row, index) {
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
                        }, {
                            field: 'approveResult',
                            title: '审批结果',
                            align: 'center',
                            formatter: function (value, row, index) {
                                return value == "AGREE" ? "<font color='green'>同意</font>" : "<font color='red'>拒绝</font>";
                            }
                        }, {
                            field: 'formId',
                            title: '表单ID',
                            align: 'center',
                            visible: false
                        }]
                });

                /**
                 * 审批历史详情
                 */
                self.$dataTableHis.on('click', '[data-handle="hisView"]', function (e) {
                    var id = $(this).data('id');
                    var index = $(this).data('index');
                    var rowData = self.$dataTableHis.bootstrapTable('getData');
                    var curData = rowData[index];
//                    approvalDetail(self, curData.type, curData);
                    window.location.href = "/admin/approval/detail?id=" + id + "&type=" + curData.type + "&isApprove=false";
                    e.stopPropagation();
                });

            }
        }
    });


    // 实现弹窗方法
    function modal($el, model, isEdit) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        vueModel = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, // 模式窗体 jQuery 对象
            data: {
                // 控制 按钮是否可点击
                disabled: false,
                // 模型复制给对应的key
                purchase: model,
                // 进项列表
                submitBtnClick: false,
                parents: null,
                children: null,
                params: {
                    firstTypeId: '',
                    secondTypeId: ''
                }
            },
            created: function () {
            },
            ready: function () {
                this.fetchParent();
                this.activeDatepicker();
                this.drawTableHis();
            },
            watch: {
                'purchase.firstTypeId': function () {
                    var self = this;
                    self.params.parentType = self.purchase.firstTypeId;
                    self.purchase.secondTypeId = '';
                    self.fetchChildren();
                    deep:true;
                },
                'purchase.goodPrice': function (val) {
                    var self = this;
                    console.log(val);
                    self.purchase.totalPrice = (val * self.purchase.goodNum).toFixed(2);
                    deep:true;
                },
                'purchase.goodNum': function (val) {
                    var self = this;
                    console.log(val);
                    self.purchase.totalPrice = (val * self.purchase.goodPrice).toFixed(2);
                    deep:true;
                }
            },
            methods: {
                fetchParent: function () {
                    var self = this;
                    this.$http.get('/api/dict/dic/getNode/2').then(function (res) {
                        if (res.data.code == 1) {
                            self.parents = res.data.data;
                        }
                    }).catch(function () {

                    }).finally(function () {

                    });
                },
                fetchChildren: function () {
                    var self = this;

                    this.$http.post('/api/dict/dic/getByType/', self.params, {emulateJSON: true}).then(function (res) {
                        if (res.data.code == 1) {
                            self.children = res.data.data;
                        }
                    }).catch(function () {

                    }).finally(function () {

                    });
                },
                calculateDaysNum: function () {
                    var self = this;
                    if (self.businessAway.beginTime != '' && self.businessAway.endTime != '') {
                        self.businessAway.daysNum = moment(self.businessAway.endTime).diff(moment(self.businessAway.beginTime), 'days') + 1;
                    }
                },
                activeDatepicker: function () {
                    $(this.$els.purchaseMonth).datetimepicker({
                        minView: 3,
                        startView: 3,
                        format: 'yyyy-mm',
                        todayBtn: true
                    });
                },
                submit: function () {
                    var self = this;
                    self.submitBtnClick = true;
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.disabled = true;
                            if (isEdit) {
                                self.$http.post(ctx + '/api/apply/applyPurchase/update', self.purchase, {emulateJSON: true}).then(function (res) {
                                    if (res.data.code == 1) {
                                        $el.on('hidden.bs.modal', function () {
                                            vueIndex.$dataTable.bootstrapTable('refresh');
                                            self.$toastr.success('更新成功');
                                        });
                                        $el.modal('hide');
                                    }
                                }).finally(function () {
                                    self.disabled = false;
                                });
                            } else {
                                self.$http.post(ctx + '/api/apply/applyPurchase', self.purchase).then(function (res) {
                                    if (res.data.code == 1) {
                                        $el.on('hidden.bs.modal', function () {
                                            vueIndex.$dataTable.bootstrapTable('refresh');
                                            self.$toastr.success('创建成功');
                                        });
                                        $el.modal('hide');
                                    }
                                }).finally(function () {
                                    self.disabled = false;
                                });
                            }
                        }
                    });
                }
            }
        });

        // 创建的Vue对象应该被返回
        return vueModel;
    }

    // 详情查看弹窗
    function viewModal($el, model, id) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        var vueModel = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, // 模式窗体 jQuery 对象
            data: {
                // 模型复制给对应的key
                purchase: model,
                approveList: null
            },
            created: function () {
            },
            ready: function () {
                this.getApproveList();
            },
            methods: {
                getApproveList: function () {
                    var self = this;
                    self.$http.get("/api/wfmanager/wfApproveDetail/", {
                        params: {
                            'formId': id,
                            'type': 'PURCHASE',
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
        return vueModel;
    }
})(this.RocoUtils);

