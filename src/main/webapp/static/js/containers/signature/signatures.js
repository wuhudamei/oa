var vueIndex = null;
+(function (RocoUtils) {
    $('#signatureMenu').addClass('active');
    $('#signatureAffair').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '我的事务',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            // 查询表单
            form: {
                keyword: '',
                keywordHis: '',
                type: 'SIGNATURE',
                wfType: 'HIS', //查询历史审批
                status: ''
            },
            organizations: ''
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/signatures',
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
                    sortName: 'id', //默认排序列名
                    sortOrder: 'desc', //默认排序方式
                    columns: [{
                        field: 'applyTitle',
                        title: '申请标题',
                        align: 'center',
                        formatter: function (value, row, index) {
                            return '<a data-handle="operate-detail" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                        }
                    }, {
                        field: 'applyCode',
                        title: '申请编码',
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
                        field: 'createDate',
                        title: '提交时间',
                        align: 'center'
                    }, {
                        field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            if (row.status == 'REFUSE') {
                                fragment += ('<button data-handle="operate-edit" data-id="'
                                + row.id + '" data-type="' + row.type + '" type="button" class="m-r-xs btn btn-xs btn-default">编辑</button>');
                            }
                            // fragment += ('<button data-handle="operate-multiDetail" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-default">审批详情</button>');

                            if (row.status == 'DRAFT') {
                                if (RocoUtils.hasPermission('signature:submit'))
                                    fragment += ('<button data-handle="operate-commit" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-default">提交</button>');
                                if (RocoUtils.hasPermission('signature:edit'))
                                    fragment += ('<button data-handle="operate-edit" data-id="' + row.id + '" data-type="' + row.type + '" class="m-r-xs btn btn-xs btn-default">编辑</button>');
                                if (RocoUtils.hasPermission('signature:delete'))
                                    fragment += ('<button data-handle="operate-delete" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-danger">删除</button>');
                            }
                            return fragment;
                        }
                    }]
                });

                //编辑费用
                self.$dataTable.on('click', '[data-handle="operate-edit"]', function (e) {
                    var id = $(this).data('id');
                    var type = $(this).data('type');
                    window.location.href = '/admin/applySignature?id=' + id;
                    e.stopPropagation();
                });

                //费用详情
                self.$dataTable.on('click', '[data-handle="operate-detail"]', function (e) {
                    var id = $(this).data('id');
                    self.$http.get('/api/signatures/' + id + '/get').then(function (res) {
                        if (res.data.code == 1) {
                            var signature = res.data.data;
                            this.showDetail(signature);
                        }
                    });
                    e.stopPropagation();
                });

                //预算审核时候的费用详情
                self.$dataTable.on('click', '[data-handle="operate-multiDetail"]', function (e) {
                    var id = $(this).data('id');
                    self.$http.get('/api/signatures/multiple/' + id).then(function (res) {
                        if (res.data.code == 1) {
                            var signature = res.data.data;
                            this.showMultDetail(signature);
                        }
                    });
                    e.stopPropagation();
                });

                //提交草稿
                self.$dataTable.on('click', '[data-handle="operate-commit"]', function (e) {
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
                                self.$dataTable.bootstrapTable('refresh');
                            }else{
                            	self.$toastr.error(res.data.message);
                            }
                        }).catch(function () {

                        }).finally(function () {
                            swal.close();
                        });
                    });
                    e.stopPropagation();
                });

                self.$dataTable.on('click', '[data-handle="operate-delete"]', function (e) {
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
                                self.$dataTable.bootstrapTable('refresh');
                            }
                        }).catch(function () {

                        }).finally(function () {
                            swal.close();
                        });
                    });
                    e.stopPropagation();
                });
            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            showModel: function (signature, isEdit) {
                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    width: 900,
                    maxWidth: 900,
                    height: 580,
                    maxHeight: 580,
                    backdrop: 'static',
                    keyboard: false
                });
                $modal.on('shown.bs.modal', function () {
                    modal($modal, signature, isEdit);
                });

            },

            showDetail: function (signature) {
                var _$modal = $('#detail').clone();
                var modal = _$modal.modal({
                    height: 580,
                    maxHeight: 580,
                    backdrop: 'static',
                    keyboard: false
                });
                detail(modal, signature);
            },

            showMultDetail: function (signature) {
                var _$modal = $('#multDetail').clone();
                var modal = _$modal.modal({
                    height: 580,
                    maxHeight: 580,
                    backdrop: 'static',
                    keyboard: false
                });
                detail(modal, signature);
            },
            queryHis: function () {
                this.$dataTableHis.bootstrapTable('selectPage', 1);
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

        },
        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.drawTable();
            this.drawTableHis();
        }
    });
    //费用单审批详情弹窗
    function detail($el, signature) {
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

    //审批人查看费用详情弹窗
    function multDetail($el, signature) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        var vueMultDetail = new Vue({
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
        return vueMultDetail;
    }
})
(this.RocoUtils);