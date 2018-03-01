var vueIndex = null;
+(function (RocoUtils) {
    $('#designerMenu').addClass('active');
    $('#contractManager').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '合同管理',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            //合同状态枚举
            statuses: [{name: 'FIRST_STAGE', label: '一期'},
                {name: 'SECOND_STAGE', label: '二期'},
                {name: 'THREE_STAGE', label: '三期'},
                {name: 'FOURTH_STAGE', label: '四期'}],
            form: {
                keyword: '',
                searchMonth: ''
            },
            //同步月份
            synMonth: '',
            fUser: ''
        },
        methods: {
            /**
             * 获取上月日期
             * @returns
             */
            getLastMonth: function () {
                return moment().add('month', -1).format('YYYY-MM');
            },
            activeDatepicker: function () {
                var self = this;
                $(self.$els.searchMonth).datetimepicker({
                    startView: 3,//启始视图显示年视图
                    minView: "year", //选择日期后，不会再跳转去选择时分秒
                    format: 'yyyy-mm',
                    todayBtn: true
                });
                $(self.$els.synMonth).datetimepicker({
                    startView: 3,//启始视图显示年视图
                    minView: "year", //选择日期后，不会再跳转去选择时分秒
                    format: 'yyyy-mm',
                    todayBtn: true
                });
            },
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/stylist/contracts',
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
                        field: 'customerName',
                        title: '客户姓名',
                        align: 'center'
                    }, {
                        field: 'contractNo',
                        title: '合同编号',
                        align: 'center'
                    }, {
                        field: 'money',
                        title: '合同金额',
                        align: 'center'
                    }, {
                        field: 'status',
                        title: '合同状态',
                        align: 'center',
                        formatter: function (val, row) {
                            for (var i = 0; i < vueIndex.statuses.length; i++) {
                                if (vueIndex.statuses[i].name == val.status) {
                                    return vueIndex.statuses[i].label;
                                }
                            }
                        }
                    }, {
                        field: 'taxesMoney',
                        title: '税金',
                        align: 'center'
                    }, {
                        field: 'managerMoney',
                        title: '管理费',
                        align: 'center'
                    }, {
                        field: 'designMoney',
                        title: '设计费',
                        align: 'center'
                    }, {
                        field: 'remoteMoney',
                        title: '远程费',
                        align: 'center'
                    }, {
                        field: 'othersMoney',
                        title: '其他费用',
                        align: 'center'
                    }, {
                        field: 'privilegeMoney',
                        title: '优惠费',
                        align: 'center'
                    }, {
                        field: 'modifyMoney',
                        title: '变更后费用',
                        align: 'center'
                    }, {
                        field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            if (RocoUtils.hasPermission('stylist:contract-edit')) {
                                fragment += ('<button data-handle="operate-edit" data-id="' + row.id + '" data-status="' + row.status.status + '" type="button" class="m-r-xs btn btn-xs btn-info">编辑</button>');
                            }
                            return fragment;
                        }
                    }]
                });

                self.$dataTable.on('click', '[data-handle="operate-delete"]', function (e) {
                    var id = $(this).data('id');
                    swal({
                        title: '删除设计师',
                        text: '确定删除该设计师么？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.get('/api/stylists/' + id + '/del').then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('删除成功');
                                self.$dataTable.bootstrapTable('refresh');
                            }
                        }).catch(function () {

                        }).finally(function () {
                            swal.close();
                        });
                    });
                    e.stopPropagation();
                });

                self.$dataTable.on('click', '[data-handle="operate-edit"]', function (e) {
                    var id = $(this).data('id');
                    var hasThreeStage = 'THREE_STAGE' == $(this).data('status');
                    self.$http.get('/api/stylist/contracts/' + id + '/get').then(function (response) {
                        var result = response.data;
                        if (result.code == 1) {
                            return result.data;
                        }
                    }).then(function (contract) {
                        this.showModel(contract, hasThreeStage);
                    }).catch(function () {

                    }).finally(function () {
                        swal.close();
                    });
                    e.stopPropagation();
                });
            },
            query: function () {
                this.$dataTable.bootstrapTable('refresh', {pageNumber: 1});
            },
            showModel: function (contract, hasThreeStage) {
                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    width: 680,
                    maxWidth: 680,
                    height: 480,
                    maxHeight: 480,
                    backdrop: 'static',
                    keyboard: false
                });
                modal($modal, contract, hasThreeStage);
            },
            synContractHandler: function () {
                var self = this;
                if (RocoUtils.isEmpty(self.synMonth)) {
                    self.$toastr.warning("请选择同步月份！");
                }
                self.disabled = true;
                self.$http.get('/api/stylist/contracts/synContract?synMonth=' + self.synMonth).then(function (res) {
                    if (res.data.code == 1) {
                        vueIndex.$dataTable.bootstrapTable('refresh');
                        self.$toastr.success('操作成功');
                    }else{
                        self.$toastr.warning(res.data.message);
                    }
                }).finally(function () {
                    self.disabled = false;
                });
            }
        },
        created: function () {

            var self = this;
            this.fUser = window.RocoUser;
            self.form.searchMonth = this.getLastMonth();
            self.synMonth = this.getLastMonth();
        },
        ready: function () {
            var self = this;
            // self.form.searchMonth = self.getLastMonth();
            // self.synMonth = self.getLastMonth();
            this.activeDatepicker();
            this.drawTable();
        }
    })
    ;
    // 编辑设计师合同弹窗
    function modal($el, contract, hasThreeStage) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        var changeVue = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                //控制 按钮是否可点击
                disabled: false,
                hasThreeStage: hasThreeStage,
                contract: contract,
                submitBtnClick: false
            },
            created: function () {

            },
            ready: function () {
            },
            methods: {
                submit: function () {
                    var self = this;
                    self.submitBtnClick = true;
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.disabled = true;
                            self.$http.post('/api/stylist/contracts', contract).then(function (res) {
                                if (res.data.code == 1) {
                                    $el.on('hidden.bs.modal', function () {
                                        vueIndex.$dataTable.bootstrapTable('refresh');
                                        self.$toastr.success('操作成功');
                                    });
                                    $el.modal('hide');
                                }
                            }).finally(function () {
                                self.disabled = false;
                            });
                        }
                    });
                }
            }
        });
        // 创建的Vue对象应该被返回
        return changeVue;
    }
})
(this.RocoUtils);