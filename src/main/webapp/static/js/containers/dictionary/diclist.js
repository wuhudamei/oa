var tt = null;
+(function (RocoUtils) {
    $('#systemMenu').addClass('active');
    $('#dictionaryMenu').addClass('active');
    tt = new Vue({
        el: '#container',
        data: {
            $dataTable: null,
            form: {
                keyword: '',
                searchType: ''
            },
            parents: '',
            organizations: '',
            dict: {},
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            fetchParent: function () {
                var self = this;
                this.$http.get('/api/dict/getNode').then(function (res) {
                    if (res.data.code == 1) {
                        self.parents = res.data.data;
                    }
                }).catch(function () {

                }).finally(function () {

                });
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/dict/list',
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
                    sortName: 'sort', //默认排序列名
                    sortOrder: 'desc', //默认排序方式
                    columns: [{
                        field: 'name',
                        title: '名称',
                        align: 'center'
                    }, {
                        field: 'parentName',
                        title: '父级名称',
                        align: 'center'
                    }, {
                        field: 'type',
                        title: '类型',
                        align: 'center',
                        formatter: function (value, row, index) {
                            switch (value) {
                                case 1:
                                    return '科目大类';
                                    break;
                                case 2:
                                    return '费用小类';
                                    break;
                                case 3:
                                    return '费用明细';
                                    break;
                                case 4:
                                    return '规章制度';
                                    break;
                                case 5:
                                    return '请假类型';
                                    break;
                                default:
                                    return '';
                                    break;
                            }
                        }
                    }, {
                        field: 'sort',
                        title: '排序',
                        align: 'center'
                    }, {
                        field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            if(row.type !=1){
                                if (RocoUtils.hasPermission("dictionary:edit"))
                                    fragment += ('<button data-handle="operate-edit" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-default">编辑</button>');
                                if (RocoUtils.hasPermission("dictionary:delete"))
                                    fragment += ('<button data-handle="operate-delete" data-index="' + index + '" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-danger">删除</button>');
                        	}
                            return fragment;
                        }
                    }]
                });
                self.$dataTable.on('click', '[data-handle="operate-edit"]', function (e) {
                    var id = $(this).data('id');
                    self.$http.get('/api/dict/' + id).then(function (res) {
                        if (res.data.code == 1) {
                            var org = res.data.data;

                            self.showModel(org, true);

                        }
                    });
                });
                self.$dataTable.on('click', '[data-handle="operate-delete"]', function (e) {
                    var id = $(this).data('id');
                    swal({
                        title: '删除字典',
                        text: '确定删除这个字典吗？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.get('/api/dict/delete/' + id).then(function (res) {
                            if (res.data.code == 1) {
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
                dict = {
                    name: '',
                    parentCode: '',
                    type: '',
                    sort: ''
                };
                this.showModel(dict, false);
            },
            showModel: function (dict, isEdit) {
                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    height: 230,
                    maxHeight: 250,
                    backdrop: 'static',
                    keyboard: false
                });
                modal($modal, dict, isEdit);
            }
        },
        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.drawTable();
            // this.fetchParent();
        }
    });
    // 实现弹窗方法
    function modal($el, model, isEdit) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        var vueModal = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            validators: {
                validAppName: function (val) {
                    if (_.trim(val) === '') {
                        return true;
                    }
                    return /^[A-Za-z0-9_-]+$/.test(val);
                }
            },
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                //控制 按钮是否可点击
                disabled: false,
                parentCode: '',
                selectType: '',
                parents: null,
                //模型复制给对应的key
                dict: model,
                // 进项列表
                submitBtnClick: false
            },
            watch: {
                'dict.type': function () {
                    var self = this;

                    self.selectType = self.dict.type;
                    self.dict.parentCode = '';
                    self.fetchParent(self.selectType);

                    deep:true;
                }
            },
            created: function () {

            },
            ready: function () {
                var self = this;
                if (self.dict.id != null) {
                    this.fetchParent(self.dict.type);
                }
            },
            methods: {
                fetchParent: function (val) {
                    console.log(val);
                    var self = this;
                    this.$http.get('/api/dict/getNode/' + val).then(function (res) {
                        if (res.data.code == 1) {
                            self.parents = res.data.data;
                        }
                    }).catch(function () {

                    }).finally(function () {

                    });
                },
                submit: function () {
                    var self = this;
                    self.submitBtnClick = true;
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.disabled = true;

                            self.$http.post('/api/dict', self.dict).then(function (res) {
                                if (res.data.code == 1) {
                                    $el.on('hidden.bs.modal', function () {
                                        tt.$dataTable.bootstrapTable('refresh');
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
        return vueModal;
    }
})(RocoUtils);