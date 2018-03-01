var vueIndex = null;
+(function (RocoUtils) {
    $('#workMenu').addClass('active');
    $('#signsite').addClass('active');
    var mapLocal = null;
    vueIndex = new Vue({
        el: '#container',
        data: {
            searchList: '',
            addressEmpty: '',
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '职场设置',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            form: {
                keyword: '',
            },
        },

        methods: {
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/signsite/list',
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
                        field: 'sitename',
                        title: '名称',
                        width: '30%',
                        align: 'center'
                    }, {
                        field: 'longitude',
                        title: '经度',
                        width: '20%',
                        align: 'center'
                    }, {
                        field: 'latitude',
                        title: '纬度',
                        width: '20%',
                        align: 'center'
                    }, {
                        field: 'radii',
                        title: '有效半径',
                        width: '15%',
                        align: 'center',
                    },

                        {
                            field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                            title: "操作",
                            width: '15%',
                            align: 'center',
                            formatter: function (value, row, index) {
                                var fragment = '';
                                if (RocoUtils.hasPermission('signsite:edit')) {
                                    fragment += ('<button data-handle="operate-edit" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-default">编辑</button>');
                                }
                                if (RocoUtils.hasPermission('signsite:delete')) {
                                    fragment += ('<button data-handle="operate-delete" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-danger">删除</button>');
                                }
                                return fragment;
                            }
                        }]
                });


                // 编辑职场
                self.$dataTable.on('click', '[data-handle="operate-edit"]', function (e) {
                    var id = $(this).data('id');
                    var signsite = null;
                    self.$http.get('/api/signsite/getByid/' + id).then(function (res) {
                        if (res.data.code == 1) {
                            signsite = res.data.data;
                            this.showModel(signsite, true);
                        }
                    })
                    e.stopPropagation();
                });
                //删除职场
                self.$dataTable.on('click', '[data-handle="operate-delete"]', function (e) {
                    var id = $(this).data('id');
                    swal({
                        title: '删除职场',
                        text: '确定删除该职场么？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.get('/api/signsite/delete/' + id).then(function (res) {
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
            createBtnClickHandler: function (e) {
                var signsite = {
                    sitename: '',
                    longitude: '',
                    latitude: '',
                    radii: ''
                };
                this.showModel(signsite, false);
            },
            showModel: function (signsite, isEdit) {
                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    width: 760,
                    maxWidth: 770,
                    height: 350,
                    maxHeight: 360,
                    backdrop: 'static',
                    keyboard: false
                });
                modal($modal, signsite, isEdit);
            }
        },

        created: function () {

            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.drawTable();
        }
    });
    // 新增弹窗
    function modal($el, model, isEdit) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        var vueModal = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                currentCity: '',
                searchList: '',
                //控制 按钮是否可点击
                disabled: false,
                //模型复制给对应的key
                signsite: model,
                submitBtnClick: false,
                timer: null,
                title:'',
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
                submit: function () {
                    var self = this;
                    self.submitBtnClick = true;
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.disabled = true;
                            var data = self.filterNull(self.signsite);
                            self.$http.post(ctx + '/api/signsite', data).then(function (res) {
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
                }
            },
            created: function () {
                this.isWeixin();
            },
            ready: function () {
                var self = this;
                if (isEdit == true) {
                    self.title = '编辑职场';
                }else{
                    self.title = '新增职场';
                }
            },

        });
        // 创建的Vue对象应该被返回
        return vueModal;
    }

})
(this.RocoUtils);