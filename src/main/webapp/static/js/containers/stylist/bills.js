+(function (RocoUtils) {
    $('#designerMenu').addClass('active');
    $('#stylistBillManager').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '提成账单管理',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            keyword:'',
            bills:[],
            generateMonth: '',
            fUser: ''
        },
        methods: {
            initTableData: function () {
                var self = this;
                var params = RocoUtils.parseQueryString(window.location.search.substr(1));
                var id = params['id'];
                if (RocoUtils.isEmpty(id)) {
                    self.$toastr.error('生成月份不正确！');
                }
                self.$http.get('/api/stylist/bills/'+id+'/getByMonthBillId').then(function (response) {
                    var result = response.data;
                    if (result.code == 1) {
                        return result.data;
                    } else {
                        self.$toastr.error(result.message);
                    }
                }).then(function (data) {
                    self.bills = data;
                    self.drawTable(data);
                });
            },
            drawTable: function (bills) {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    data: bills,
                    pagination: true, //是否分页
                    mobileResponsive: true,
                    undefinedText: '-', //空数据的默认显示字符
                    striped: true, //斑马线
                    maintainSelected: true, //维护checkbox选项
                    columns: [{
                        field: 'name',
                        title: '姓名',
                        align: 'center'
                    }, {
                        field: 'jobNo',
                        title: '工号',
                        align: 'center'
                    }, {
                        field: 'mobile',
                        title: '手机号',
                        align: 'center'
                    }, {
                        field: 'billMonth',
                        title: '账单月份',
                        align: 'center'
                    }, {
                        field: 'totalMoney',
                        title: '账单总额',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            fragment += ('<a data-handle="operate-details" data-id="' + row.id + '">' + value + '</a>');
                            return fragment;
                        }
                    }
                        // , {
                        //     field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        //     title: "操作",
                        //     align: 'center',
                        //     formatter: function (value, row, index) {
                        //         var fragment = '';
                        //         fragment += ('<button data-handle="operate-edit" data-id="' + row.id + '" data-status="' + row.status.status + '" type="button" class="m-r-xs btn btn-xs btn-info">编辑</button>');
                        //         return fragment;
                        //     }
                        // }
                    ]
                });

                self.$dataTable.on('click', '[data-handle="operate-details"]', function (e) {
                    var id = $(this).data('id');
                    self.$http.get('/api/stylist/bills/' + id + '/findDetails').then(function (response) {
                        var result = response.data;
                        if (result.code == 1) {
                            return result.data;
                        }
                    }).then(function (details) {
                        this.showModel(details);
                    }).catch(function () {

                    }).finally(function () {
                        swal.close();
                    });
                    e.stopPropagation();
                });
            },
            query: function () {
                var self = this;
                var bills = self.bills;
                var keyword = self.keyword;
                if (RocoUtils.isEmpty(keyword)) {
                    this.$dataTable.bootstrapTable('load', bills);
                } else {
                    var queryBills = [];
                    bills.forEach(function (bill) {
                        if ((RocoUtils.isNotEmpty(bill.name) && bill.name.indexOf(keyword) > -1)
                            || (RocoUtils.isNotEmpty(bill.jobNo) && bill.jobNo.indexOf(keyword) > -1)
                            || (RocoUtils.isNotEmpty(bill.mobile) && bill.mobile.indexOf(keyword) > -1)
                        ) {
                            queryBills.push(bill);
                        }
                    });
                    this.$dataTable.bootstrapTable('load', queryBills);
                }
            },
            showModel: function (details) {
                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    width: 680,
                    maxWidth: 680,
                    height: 480,
                    maxHeight: 480,
                    backdrop: 'static',
                    keyboard: false
                });
                modal($modal, details);
            }
        },
        created: function () {
            this.fUser = window.RocoUser;

        },
        ready: function () {
            this.initTableData();
        }
    });
    // 编辑设计师合同弹窗
    function modal($el, details) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        var detailsVue = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                //控制 按钮是否可点击
                disabled: false,
                statuses: [{name: 'FIRST_STAGE', label: '一期'},
                    {name: 'SECOND_STAGE', label: '二期'},
                    {name: 'THREE_STAGE', label: '三期'},
                    {name: 'FOURTH_STAGE', label: '四期'}],
                _$billDetailsTable: null,
                details: details,
                submitBtnClick: false
            },
            created: function () {

            },
            ready: function () {
                this.drawBillDetailsTable();
            },
            methods: {
                drawBillDetailsTable: function () {
                    var self = this;
                    self._$billDetailsTable = $(self.$els.billDetailsTable).bootstrapTable({
                        data: self.details,
                        columns: [{
                            field: 'customerName',
                            title: '客户姓名',
                            align: 'center'
                        }, {
                            field: 'projectMoney',
                            title: '工程总价',
                            align: 'center'
                        }, {
                            field: 'contractStatus',
                            title: '合同状态',
                            align: 'center',
                            formatter: function (val, row) {
                                for (var i = 0; i < self.statuses.length; i++) {
                                    if (self.statuses[i].name == val) {
                                        return self.statuses[i].label;
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
                            field: 'billMoney',
                            title: '提成总额',
                            align: 'center'
                        }]
                    });
                }
            }
        });
        // 创建的Vue对象应该被返回
        return detailsVue;
    }
})
(this.RocoUtils);