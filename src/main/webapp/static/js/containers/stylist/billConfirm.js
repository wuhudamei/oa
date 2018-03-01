var vueIndex = null;
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
                name: '账单确认页',
                active: true //激活面包屑的
            }],
            fUser: '',
            redirectUrl: '/admin/stylist/monthBills',
            $dataTable: null,
            keyword: '',
            monthBill: null,
            bills: [],
            generateMonth: null,
            disabled: false
        },
        methods: {
            generateBills: function () {
                var self = this;
                var params = RocoUtils.parseQueryString(window.location.search.substr(1));
                var generateMonth = params['generateMonth'];
                if (RocoUtils.isEmpty(generateMonth)) {
                    self.$toastr.error('生成月份不正确！');
                }
                self.generateMonth = generateMonth;
                self.$http.get('/api/stylist/bills/generateBill?generateMonth=' + self.generateMonth).then(function (response) {
                    var result = response.data;
                    if (result.code == 1) {
                        return result.data;
                    } else if (result.code == 2) {
                        self.$toastr.warning(result.message);
                    } else {
                        self.$toastr.error(result.message);
                    }
                }).then(function (data) {
                    self.bills = data.bills;
                    self.monthBill = data.monthBill;
                    self.drawTable(self.bills);
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
                if (typeof(keyword) == 'undefined' || keyword == '') {
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
            },
            submit: function () {
                var self = this;
                self.disabled = true;

                self.$http.get('/api/stylist/bills/' + self.monthBill.id + '/submit').then(function (response) {
                    var result = response.data;
                    if (result.code == 1) {
                        //跳转到账单列表页面
                        self.$toastr.success('提交成功！');
                        RocoUtils.redirect2Url(self.redirectUrl, 3000);
                    }
                });

            },
            jump2Url: function (url) {
                window.location.href = url;
            },
            rollback: function () {
                var self = this;
                self.disabled = true;
                var billIds = this.getBillIds();
                if (billIds.length == 0) {
                    self.$toastr.success('取消成功！');
                    RocoUtils.redirect2Url(self.redirectUrl, 3000);
                    return false;
                }
                self.$http.get('/api/stylist/bills/' + self.monthBill.id + '/rollback').then(function (response) {
                    var result = response.data;
                    if (result.code == 1) {
                        //跳转到账单列表页面
                        self.$toastr.success('取消成功！');
                        RocoUtils.redirect2Url(self.redirectUrl, 3000);
                    }
                });
            },
            getBillIds: function () {
                var self = this;
                var billIds = [];
                self.bills.forEach(function (bill) {
                    if (bill && bill.id) {
                        billIds.push(bill.id);
                    }
                });
                return billIds;
            }
        },
        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.generateBills();
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