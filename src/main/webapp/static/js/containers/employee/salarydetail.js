+(function (RocoUtils) {
    $('#empManagerMenu').addClass('active');
    $('#employeeManage').addClass('active');
    var vueIndex = new Vue({
        el: '#container',
        mixins: [RocoVueMixins.DataTableMixin],
        data: {
            // 查询表单
            form: {
                empId: ''
            },
            empId: '',
            empName: '',
            selectedRows: {}, //选中列
            modalModel: null, //模式窗体模型
            _$el: null, //自己的 el $对象
            _$dataTable: null //datatable $对象
        },
        created: function () {
            var self = this;
            var orgCode= this.$parseQueryString()['orgCode'];
            var name = this.$parseQueryString()['name'];
            var empId = this.$parseQueryString()['id'];
            self.empId = empId;
            self.orgCode = orgCode;
            self.empName = name;
        },
        ready: function () {
            this.drawTable();
        },
        methods: {
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                //查询
                self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
                    url: '/api/rest/salarybasicdata/findAllSalaryBasicData?empId='+self.empId,
                    method: 'get',
                    dataType: 'json',
                    cache: false, //去缓存
                    pagination: true, //是否分页
                    //sidePagination: 'server', //服务端分页
                    sidePagination: 'client', //服务端分页
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
                            field: 'idNum',
                            title: '身份证号',
                            align: 'center'
                        }, {
                            field: 'bank',
                            title: '银行',
                            align: 'center'
                        },{
                            field: 'bankOfDeposit',
                            title: '开户行',
                            align: 'center'
                        }, {
                            field: 'creditCardNumbers',
                            title: '银行卡号',
                            align: 'center'
                        }, {
                            field: 'orgCompanyName',
                            title: '公司',
                            align: 'center'
                        }, {
                            field: 'orgDepartmentName',
                            title: '部门',
                            align: 'center'
                        }, {
                            field: 'salaryBasicDn',
                            title: '基本工资',
                            align: 'center'
                        }, {
                            field: 'housingFundDn',
                            title: '公积金',
                            align: 'center'
                        }, {
                            field: 'socialSecurityPersonalDn',
                            title: '社保',
                            align: 'center'
                        }, {
                            field: 'mealSubsidyDn',
                            title: '餐补',
                            align: 'center'
                        }, {
                            field: 'effectiveDate',
                            title: '工资生效日期',
                            align: 'center',
                            formatter: function (value, row, index) {
                                return moment(value).format('YYYY-MM');
                            }
                        }, {
                            field: 'expiryDate',
                            title: '工资终止日期',
                            align: 'center',
                            formatter: function (value, row, index) {
                                return moment(value).format('YYYY-MM');
                            }
                        }, {
                            field: '',
                            title: "操作",
                            align: 'center',
                            formatter: function (value, row, index) {
                                var btns = '';
                                btns += '<button data-handle="edit" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">编辑</button>&nbsp;';
                                btns += '<button data-handle="del" data-index="' + index + '" data-id="' + row.id + '" type="button" class="btn btn-xs btn-danger">删除</button>&nbsp;';
                                return btns;
                            }
                        }]
                });

                // 编辑按钮
                self.$dataTable.on('click', '[data-handle="edit"]', function (e) {
                    var id = $(this).data('id');
                    self.$http.get('/api/rest/salarybasicdata/getById?id=' + id).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            var _$modal = $('#modalEdit').clone();
                            var $modal = _$modal.modal({
                                height: 250,
                                maxHeight: 450,
                                backdrop: 'static',
                                keyboard: false
                            });
                            $modal.on('shown.bs.modal', function () {
                                modalEdit($modal, response.data.data, true);
                            });
                        }
                    });
                    e.stopPropagation();
                });

                // 删除按钮
                self.$dataTable.on('click', '[data-handle="del"]', function (e) {
                    var id = $(this).data('id');
                    swal({
                        title: '基本工资信息删除',
                        text: '确定要删除该信息？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.delete('/api/rest/salarybasicdata/deleteById?id=' + id).then(function (response) {
                            var res = response.data;
                            if (res.code == '1') {
                                self.$toastr.success('删除成功！');
                                self.$dataTable.bootstrapTable('refresh');
                            }
                        }).catch(function () {
                            self.$toastr.error('系统异常');
                        }).finally(function () {
                            swal.close();
                        });
                    });
                    e.stopPropagation();
                });

                self.checkEventHandle('ordNo');
            },

            createBtnClickHandler: function (e) {
                var self = this;
                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    height: 450,
                    maxHeight: 450,
                    backdrop: 'static',
                    keyboard: false
                });
                $modal.on('shown.bs.modal', function () {
                    modal($modal, self.empId, false);
                });
            },

            cancel : function() {
                window.history.go(-1);
            }

        }
    });
    // 实现弹窗方法--添加
    function modal($el, empId, isEdit) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        vueContract = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                salarydetail:{
                    id: '',
                    bank:'',
                    bankOfDeposit:'',
                    creditCardNumbers:'',
                    effectiveDate:'',
                    expiryDate:'',
                    shouldWorkDays:'',
                    salaryBasic:'',
                    mealSubsidy:'',
                    socialSecurityPersonal:'',
                    housingFund:'',
                    empId:empId
                },
                //控制 按钮是否可点击
                disabled: false,
                // 进项列表
                submitBtnClick: false
            },
            validators: {
                num: {
                    message: '请输入正确的金额格式，例（10或10.00）',
                    check: function (val) {
                        // return /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/.test(val);
                        // return /^0|[0-9]+(.[0-9]{1,2})?$/.test(val);
                        return /^([1-9]\d{0,9}|0)([.]?|(\.\d{1,2})?)$/.test(val);
                    }
                },
                days: {
                    message: '请输入正确的天数，例（1-31）',
                    check: function (val) {
                        return (/^([1-9]|[12]\d|3[01])$/i).test(val);
                    }
                },
                card: {
                    message: '请输入正确的银行卡号，银行卡号长度为16位或19位',
                    check: function (val) {
                        return (/^([1-9]{1})(\d{15}|\d{18})$/).test(val);
                    }
                }
            },
            created: function () {
                var self = this;
                var name = this.$parseQueryString()['name'];
                self.empName = name;
            },
            ready: function () {
                this.activeDatepicker();
            },
            methods: {
                activeDatepicker: function () {
                    var self = this;
                    $(self.$els.effectiveDate).datetimepicker({
                        clearBtn:true,
                        startView: 3,//启始视图显示年视图
                        minView: 3, //选择日期后，不会再跳转去选择时分秒
                        maxView: 3, //选择日期后，不会再跳转去选择时分秒
                        format: 'yyyy-mm',
                        todayBtn: true
                    })
                    $(self.$els.expiryDate).datetimepicker({
                        clearBtn:true,
                        startView: 3,//启始视图显示年视图
                        minView: 3, //选择日期后，不会再跳转去选择时分秒
                        maxView: 3, //选择日期后，不会再跳转去选择时分秒
                        format: 'yyyy-mm',
                        todayBtn: true
                    })

                },

                submit: function () {
                    var self = this;
                    self.submitBtnClick = true;
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            var data = self.filterNull(self.salarydetail);
                            self.disabled = true;
                            self.$http.post(ctx + '/api/rest/salarybasicdata/insertOrUpdate', data).then(function (res) {
                                console.log(res.data);
                                if (res.data.code == 1) {
                                    $el.on('hidden.bs.modal', function () {
                                        vueIndex.$dataTable.bootstrapTable('refresh');
                                        self.$toastr.success('创建成功');
                                    });
                                    $el.modal('hide');
                                }else {
                                    self.$toastr.error('日期不允许重复添加');
                                }

                            }).finally(function () {
                                self.disabled = false;
                            });
                        }
                    });
                },
                filterNull: function (data) {
                    var t = {};
                    if (data) {
                        for (var k in data) {
                            var v = data[k];
                            if (v) {
                                t[k] = v;
                            }
                        }
                    }
                    return t;
                },
            }
        });
        // 创建的Vue对象应该被返回
        return vueContract;
    }

    // 实现弹窗方法--编辑
    function modalEdit($el, model, isEdit) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        var vueContract = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                salarydetail:{
                    id: model.id,
                    effectiveDate:model.effectiveDate,
                    expiryDate:model.expiryDate,
                    shouldWorkDays:model.shouldWorkDays
                },
                name:null,
                //控制 按钮是否可点击
                disabled: false,
                // 进项列表
                submitBtnClick: false
            },
            validators: {
                days: {
                    message: '请输入正确的天数，例（1-31）',
                    check: function (val) {
                        return (/^([1-9]|[12]\d|3[01])$/i).test(val);
                    }
                }
            },
            created: function () {
                var self = this;
                self.empName = this.$parseQueryString()['name'];
            },
            ready: function () {
                var self = this;
                self.activeDatepicker();
            },
            methods: {
                activeDatepicker: function () {
                    var self = this;
                    $(self.$els.effectiveDate).datetimepicker({
                        clearBtn:true,
                        minView: 3, //日期时间选择器所能够提供的最精确的时间选择视图
                        startView: 3,//日期时间选择器打开之后首先显示的视图
                        maxView: 3, //最高能展示的选择范围视图
                        format: 'yyyy-mm',
                        todayBtn: true
                    })
                    $(self.$els.expiryDate).datetimepicker({
                        clearBtn:true,
                        minView: 3, //选择日期后，不会再跳转去选择时分秒
                        startView: 3,//启始视图显示年视图
                        maxView: 3, //选择日期后，不会再跳转去选择时分秒
                        format: 'yyyy-mm',
                        todayBtn: true
                    })
                },

                submit: function () {
                    var self = this;
                    self.submitBtnClick = true;
                    if(self.salarydetail.effectiveDate > self.salarydetail.expiryDate){
                        self.$toastr.error('生效日期不能大于终止日期');
                        return;
                    }else{
                        self.$validate(true, function () {
                            if (self.$validation.valid) {
                                self.disabled = true;
                                var data = self.salarydetail;
                                if (isEdit) {
                                    self.$http.put(ctx + '/api/rest/salarybasicdata/insertOrUpdate', self.salarydetail).then(function (res) {
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
                                }
                            }
                        });
                    }

                },
            }

        });
        // 创建的Vue对象应该被返回
        return vueContract;
    }

})(this.RocoUtils);

