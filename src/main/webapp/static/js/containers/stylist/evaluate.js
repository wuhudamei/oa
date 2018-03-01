var vueIndex = null;
+(function (RocoUtils) {
    $('#designerMenu').addClass('active');
    $('#assessmentManager').addClass('active');
    var evaluateModal = null;
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '考核管理',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            form: {
                keyword: '',
                evaluateMonth: ''
            },
            webUploaderSub: {
                type: 'sub',
                formData: {month: ''},
                accept: {
                    title: '文件',
                    extensions: 'xls,xlsx'
                },
                server: ctx + '/api/evaluate/importFile?',
                //上传路径
                fileNumLimit: 1,
                fileSizeLimit: 50000 * 1024,
                fileSingleSizeLimit: 5000 * 1024
            }
        },
        watch: {
            'form.evaluateMonth': function (value) {
                this.webUploaderSub.formData.month = value;
            }
        },
        methods: {
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/evaluate',
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
                        field: 'evaluateMonth',
                        title: '月份',
                        align: 'center'
                    }, {
                        field: 'score',
                        title: '得分',
                        align: 'center'
                    }, {
                        field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            if (RocoUtils.hasPermission('stylist:assessment-del')) {
                                fragment += ('<button data-handle="operate-delete" data-id="' + row.id + '" data-index="' + index + '" type="button" class="m-r-xs btn btn-xs btn-danger">删除</button>');
                            }
                            return fragment;
                        }
                    }]
                });

                self.$dataTable.on('click', '[data-handle="operate-delete"]', function (e) {
                    var id = $(this).data('id');
                    var index = $(this).data('index');
                    var rowData = self.$dataTable.bootstrapTable('getData')[index];
                    swal({
                        title: '删除考核信息',
                        text: '是否确定删除【' + rowData.name + ',' + rowData.evaluateMonth + '】的考核信息？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.get('/api/evaluate/' + id + '/del').then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('删除考核信息成功!');
                                self.$dataTable.bootstrapTable('refresh');
                            } else {
                                self.$toastr.error('删除考核信息失败');
                            }
                        }).catch(function () {

                        }).finally(function () {
                            swal.close();
                        });
                    });
                    e.stopPropagation();
                });
            },
            activeDatepicker: function () {
                var self = this;
                $(self.$els.evaluateMonth).datetimepicker({
                    startView: 3,//启始视图显示年视图
                    minView: "year", //选择日期后，不会再跳转去选择时分秒
                    format: 'yyyy-mm',
                    todayBtn: true
                });
                $(self.$els.costDate).datetimepicker({
                    minView: "month", //选择日期后，不会再跳转去选择时分秒
                    format: 'yyyy-mm-dd',
                    todayBtn: true
                });
            },
            createBtnClickHandler: function (e) {
                this.showModel();
            },
            batchImport: function (e) {
                document.getElementById('evaluateFile').click();
            },
            downLoadExcel: function (e) {
                window.location.href = "/static/template/evaluate.xls";
            },
            showModel: function () {
                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    width: 580,
                    maxWidth: 580,
                    height: 260,
                    maxHeight: 260,
                    backdrop: 'static',
                    keyboard: false
                });
                modal($modal);
            }
        },
        components: {
            'web-uploader': RocoVueComponents.WebUploaderComponent
        },
        events: {
            'webupload-upload-success-sub': function (file, res) {
                console.log(res);
                if (res.code == '1') {
                    this.$toastr.success(res.message);
                    vueIndex.$dataTable.bootstrapTable('refresh');
                } else {
                    this.$toastr.error(res.message);
                }
            }
        },
        created: function () {
            this.form.evaluateMonth = getLastMonth();//默认上个月时间
            this.webUploaderSub.formData.month = this.form.evaluateMonth;
        },
        ready: function () {
            this.drawTable();
            this.activeDatepicker();
        }
    });

    /**
     * 获取上月日期
     * @returns
     */
    function getLastMonth() {
        return moment().add('month', -1).format('YYYY-MM');
    }

    /**
     * 新增考核管理
     * @param $el
     * @returns
     */
    function modal($el) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        evaluateModal = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                //控制 按钮是否可点击
                disabled: false,
                submitBtnClick: false,
                evaluate: {
                    jobNo: '',
                    mobile: '',
                    evaluateMonth: '',
                    score: ''
                }
            },
            created: function () {
                this.evaluate.evaluateMonth = getLastMonth();
            },
            ready: function () {
            },
            methods: {
                stylist: function () {
                    showStylist();//查询设计师
                },
                submit: function () {
                    var self = this;
                    self.submitBtnClick = true;
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.disabled = true;
                            self.$http.post('/api/evaluate/insert', self.evaluate).then(function (res) {
                                if (res.data.code == 1) {
                                    $el.on('hidden.bs.modal', function () {
                                        vueIndex.$dataTable.bootstrapTable('refresh');
                                        self.$toastr.success('操作成功');
                                    });
                                    $el.modal('hide');
                                } else {
                                    self.$toastr.error(res.data.message);
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
        return evaluateModal;
    }

    /**
     * 设计师列表
     * @returns
     */
    function showStylist() {
        var _modal = $('#stylstListModel').clone();
        var $el = _modal.modal({
            height: 200,
            maxHeight: 200,
            backdrop: 'static',
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
                        form: {
                            keyword: '',
                            limit: 5
                        },
                        selectStylist: [], //添加选中的设计师信息
                        modalModel: null, //模式窗体模型
                        _$el: null, //自己的 el $对象
                        _$dataTable: null //datatable $对象
                    },
                    methods: {
                        query: function () {
                            this.$dataTable.bootstrapTable('refresh');
                        },
                        drawTable: function () {
                            var self = this;
                            self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
                                url: '/api/stylists',
                                method: 'get',
                                dataType: 'json',
                                cache: false,
                                pagination: true,//不分页
                                sidePagination: 'server',
                                queryParams: function (params) {
                                    return _.extend({}, params, self.form);
                                },
                                mobileResponsive: true,
                                undefinedText: '-',
                                striped: true,
                                maintainSelected: true,
                                pageSize: 5,
                                sortOrder: 'desc',
                                columns: [{
                                    checkbox: true,
                                    align: 'center',
                                    width: '5%',
                                    radio: true
                                }, {
                                    field: 'jobNo',
                                    title: '员工号',
                                    width: '6%'
                                }, {
                                    field: 'name',
                                    title: '姓名',
                                    width: '10%'
                                }, {
                                    field: 'mobile',
                                    title: '手机号',
                                    width: '6%'
                                }]
                            });
                            self.$dataTable.on('check.bs.table', function (row, data) {
                                self.selectStylist = [];
                                var stylist = {
                                    jobNo: data.jobNo,
                                    name: data.name,
                                    mobile: data.mobile
                                };
                                self.selectStylist.push(stylist);
                            });
                        },
                        commitUsers: function () {
                            var self = this;
                            if (self.selectStylist != null && self.selectStylist != undefined && self.selectStylist.length > 0) {
                                evaluateModal.evaluate.jobNo = self.selectStylist[0].jobNo;
                                evaluateModal.evaluate.name = self.selectStylist[0].name;
                                evaluateModal.evaluate.mobile = self.selectStylist[0].mobile;
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