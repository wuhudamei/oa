var vueIndex = null;
+(function (RocoUtils) {
    $('#designerMenu').addClass('active');
    $('#designerManager').addClass('active');
    var newStylistModal = null;
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '设计师管理',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            form: {
                keyword: ''
            },
            fUser: ''
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/stylists',
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
                        field: 'ministerName',
                        title: '部长',
                        align: 'center'
                    }, {
                        field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            if (RocoUtils.hasPermission('stylist:manager-del')) {
                                fragment += ('<button data-handle="operate-delete" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-danger">删除</button>');
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
                        self.$http.delete('/api/stylists/' + id + '/del').then(function (res) {
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
            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            createBtnClickHandler: function (e) {
                this.showModel();
            },
            showModel: function () {
                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    width: 680,
                    maxWidth: 680,
                    height: 480,
                    maxHeight: 480,
                    backdrop: 'static',
                    keyboard: false
                });
                modal($modal);
            }
        },
        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.drawTable();
        }
    });
    // 新增设计师及部长弹窗
    function modal($el) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        newStylistModal = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                //控制 按钮是否可点击
                disabled: false,
                //设计师列表
                stylists: [],
                //部长
                minister: {name: '', jobNo: '', mobile: ''},
                submitBtnClick: false
            },
            created: function () {

            },
            ready: function () {
            },
            methods: {
                addStylist: function (type) {
                    addStylistModal(type);
                },
                removeStylist: function (index) {
                    this.stylists.splice(index, 1);
                },
                buildStylists: function () {
                    var self = this;
                    var stylists = [];
                    var minister = self.minister;
                    self.stylists.forEach(function (stylist) {
                        var newStylist = {
                            userId: stylist.userId,
                            name: stylist.name,
                            jobNo: stylist.jobNo,
                            mobile: stylist.mobile,
                            minister: minister.jobNo,
                            ministerName: minister.name,
                            ministerMobile: minister.mobile
                        };
                        stylists.push(newStylist);
                    });
                    return stylists;
                },
                submit: function () {
                    var self = this;
                    self.submitBtnClick = true;
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.disabled = true;
                            var stylists = self.buildStylists();
                            self.$http.post('/api/stylists/batchInsert', stylists).then(function (res) {
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
        return newStylistModal;
    }

    // 新增或修改窗口
    function addStylistModal(type) {
        var _modal = $('#addStylistModal').clone();
        var $el = _modal.modal({
            // height: 480,
            // maxHeight: 500,
            // backdrop: 'static',
            // keyboard: false
            width: 680,
            maxWidth: 680,
            height: 480,
            maxHeight: 480,
            backdrop: 'static',
            keyboard: false
        });
        $el.on('shown.bs.modal',
            function () {
                var el = $el.get(0);
                var vueModal = new Vue({
                    el: el,
                    http: {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    },
                    mixins: [RocoVueMixins.ModalMixin],
                    $modal: $el,
                    created: function () {
                    },
                    data: {
                        selectStylists: []
                    },
                    methods: {
                        query: function () {
                            this.$dataTable.bootstrapTable('selectPage', 1);
                        },
                        drawTable: function () {
                            var self = this;
                            self.$dataTable = $('#stylistDataTable', self._$el).bootstrapTable({
                                url: '/api/employees',
                                method: 'get',
                                dataType: 'json',
                                cache: false,
                                pagination: true,
                                sidePagination: 'server',
                                queryParams: function (params) {
                                    return _.extend({},
                                        params, self.form);
                                },
                                mobileResponsive: true,
                                undefinedText: '-',
                                striped: true,
                                maintainSelected: true,
                                sortOrder: 'desc',
                                columns: [{
                                    checkbox: true,
                                    align: 'center',
                                    width: '5%',
                                    checked: true,
                                    formatter: function (value, row) {
                                        var select = false;
                                        self.selectStylists.forEach(function (val) {
                                            if (row.jobNum == val.jobNo) {
                                                select = true;
                                            }
                                        });
                                        return select;
                                    }
                                }, {
                                    field: 'jobNum',
                                    title: '工号',
                                    align: 'center'
                                }, {
                                    field: 'name',
                                    title: '姓名',
                                    align: 'center'
                                }, {
                                    field: 'mobile',
                                    title: '手机号',
                                    align: 'center'
                                }]
                            });
                            self.$dataTable.on('check.bs.table', function (row, data) {
                                var stylist = {
                                    userId: data.id,
                                    jobNo: data.jobNum,
                                    name: data.name,
                                    mobile: data.mobile
                                };
                                self.selectStylists.push(stylist);
                            });
                            self.$dataTable.on('uncheck.bs.table', function (row, data) {
                                self.selectStylists.forEach(function (val, index) {
                                    if (data.jobNum == val.jobNo) {
                                        self.selectStylists.splice(index, 1);
                                    }
                                });
                            });
                            //全选功能  TODO
                            self.$dataTable.on('check-all.bs.table', function (row, data) {
                                console.log(data);
                            });
                            //取消全选  TODO
                            self.$dataTable.on('uncheck-all.bs.table', function (row, data) {
                                console.log(data);
                            });
                        },
                        commitStylist: function () {
                            var self = this;
                            if (type == 'STYLIST') {
                                //已经选择的人员
                                var alreadyStylist = newStylistModal.stylists;
                                self.selectStylists.forEach(function (selectStylist) {

                                    //判断是否已经选择
                                    var contain = false;
                                    alreadyStylist.forEach(function (alreadyUser) {
                                        if (selectStylist.jobNo == alreadyUser.jobNo) {
                                            contain = true;
                                        }
                                    });
                                    //如果没有包含所选人员，则添加人员
                                    if (!contain) {
                                        newStylistModal.stylists.push(selectStylist);
                                    }
                                });
                            } else if (type == 'MINISTER') {
                                if (self.selectStylists.length != 1) {
                                    Vue.toastr.warning('请选择一个部长');
                                    return false;
                                }
                                var minister = {
                                    userId: self.selectStylists[0].userId,
                                    jobNo: self.selectStylists[0].jobNo,
                                    name: self.selectStylists[0].name,
                                    mobile: self.selectStylists[0].mobile
                                };
                                newStylistModal.minister = minister;
                            }
                            $el.modal('hide');
                            this.$destroy();
                        }
                    },
                    ready: function () {
                        this.drawTable();
                    }
                });

                // 创建的Vue对象应该被返回
                return vueModal;
            });

    }
})
(this.RocoUtils);