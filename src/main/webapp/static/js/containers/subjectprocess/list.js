/**
 * Created by aaron on 2017/3/9.
 */
+(function () {
    $('#systemMenu').addClass('active');
    $('#subProcessManager').addClass('active');

    // 流程树
    var treeNode = null;

    var vueIndex = new Vue({
        el: '#container',
        mixins: [RocoVueMixins.DataTableMixin],
        data: {
            hasPermissionAdd: RocoUtils.hasPermission('subjectpro:add'), // 新建 权限
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '科目流程管理',
                active: true //激活面包屑的
            }],
            // 查询表单
            form: {
                keyword: ''
            },
            modalModel: null, //模式窗体模型
            _$el: null, //自己的 el $对象
            _$dataTable: null, //datatable $对象
        },
        created: function () {
            this.fetchOrgTree();
        },
        ready: function () {
            var self = this;
            self.drawTable();
        },
        methods: {
            // 流程树
            fetchOrgTree: function () {
                var self = this;

                self.$http.get('/api/process/findAll').then(function (response) {
                    var result = response.data;
                    result.data.state.disabled = true;
                    treeNode = result.data;
                });
            },
            // 新建
            add: function () {
                var subPro = {
                    wfType: '',
                    wfId: '',
                    processTypeId: '',
                    subjectId: ''
                }
                this.showModal(subPro);
            },
            showModal: function (obj) {
                var self = this;
                var _$modal = $('#addprocess').clone();
                var $modal = _$modal.modal({
                    height: 400,
                    maxHeight: 450,
                    backdrop: 'static',
                    keyboard: false
                });

                processModal($modal, obj, function () {
                    self.query();
                });
            },
            query: function () {
                this.$dataTable.bootstrapTable('refresh', {pageNumber: 1});
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
                    url: '/api/subpro',
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
                    uniqueId: 'id',
                    maintainSelected: true, //维护checkbox选项
                    columns: [
                        {
                            field: 'subjectName',
                            title: '费用科目',
                            align: 'center'
                        }, {
                            field: 'processTypeName',
                            title: '类型',
                            align: 'center'
                        }, {
                            field: 'wfName',
                            title: '流程名称',
                            align: 'center'
                        }, {
                            field: 'id', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                            title: "操作",
                            align: 'center',
                            formatter: function (value, row, index) {
                                var fragment = '';
                                if (RocoUtils.hasPermission('subjectpro:edit'))
                                    fragment += '<button data-handle="edit" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">编辑</button>&nbsp;';
                                if (RocoUtils.hasPermission('subjectpro:delete'))
                                    fragment += '<button data-handle="delete" data-id="' + row.id + '" type="button" class="btn btn-xs btn-danger">删除</button>&nbsp;';
                                return fragment;
                            }
                        }]
                });

                // 编辑事件
                self.$dataTable.on('click', '[data-handle="edit"]', function (e) {
                    var id = $(this).data('id');
                    var rowData = self.$dataTable.bootstrapTable('getRowByUniqueId', id);
                    self.showModal(rowData);
                    e.stopPropagation();
                });

                // 删除事件
                self.$dataTable.on('click', '[data-handle="delete"]', function (e) {
                    var id = $(this).data('id');
                    swal({
                        title: '删除流程',
                        text: '确定删除该流程么？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.delete('/api/subpro/' + id).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
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
            }
        }
    });

    // 选择流程
    function treeModal($el, id, callback) {
        // 获取 node
        var el = $el.get(0);

        // 创建 Vue 对象编译节点
        var vueModal = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, //模式窗体 jQuery 对象
            data: {

            },
            created: function () {

            },
            ready: function () {
                this.initTree()
            },
            methods: {
                initState: function (nodes) {
                    for (var i = 0; i < nodes.length; i++) {
                        // 初始化选择状态
                        if (id == nodes[i].id) {
                            nodes[i].state.selected = true
                        }

                        // 默认打开可选择的节点
                        if (nodes[i].id == 2 || nodes[i].id == 3 || nodes[i].id == 4 || nodes[i].id == 5) {
                            nodes[i].state.opened = true
                        }

                        // 只有流程名称的节点可选，其它的都不可选择
                        if (nodes[i].pid == 2 || nodes[i].pid == 3 || nodes[i].pid == 4 || nodes[i].pid == 5) {
                            nodes[i].state.disabled = false
                        }
                        else {
                            nodes[i].state.disabled = true
                        }

                        if (nodes[i].children && nodes[i].children.length > 0) {
                            this.initState(nodes[i].children)
                        }
                    }
                },
                // 初始化流程树
                initTree: function () {
                    var self = this;
                    var tree = $el.find('#tree');

                    var nodes = $.extend(true, {}, treeNode);

                    self.initState([nodes]);

                    $.jstree.defaults.sort = function (obj, deep) {
                        return 1;
                    }
                    tree.jstree({
                        core: {
                            multiple: false,
                            // 不加此项无法动态删除节点
                            check_callback: true,
                            data: nodes
                        },
                        types: {
                            default: {
                                icon: 'glyphicon glyphicon-stop'
                            }
                        },
                        sort: function () {
                            var one = this.get_node(arguments[0]);
                            var two = this.get_node(arguments[1]);
                            return one.original.sort > two.original.sort ? 1 : -1;

                        },
                        plugins: ['sort', 'types', 'wholerow', 'changed']
                    });
                },
                // 选择回调事件
                ok: function () {
                    var tree = $el.find('#tree').jstree(true);
                    var nodes = tree.get_selected(true);
                    if (nodes && nodes.length == 0) return;
                    callback && callback(nodes[0])
                    $el.modal('hide');
                }
            }
        });

        // 创建的Vue对象应该被返回
        return vueModal;
    }

    // 新建/编辑
    function processModal($el, obj, callback) {
        //获取node
        var el = $el.get(0);

        //创建Vue对象编译节点
        var vueModal = new Vue({
            el: el,
            minxins: [RocoVueMixins.ModalMixin],
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                obj: obj,
                wfName: '',
                submitBtnClick: false,
                subList: []
            },
            created: function () {
                if (this.obj && this.obj.processTypeId) {
                    this.fetchSub(this.obj.processTypeId);
                    this.wfName = this.obj.wfName;
                }
            },
            ready: function () {

            },
            methods: {
                // 选择流程
                changeWf: function () {
                    var self = this;
                    var _$modal = $('#treemodal').clone();
                    var $modal = _$modal.modal({
                        height: 400,
                        maxHeight: 450,
                        backdrop: 'static',
                        keyboard: false
                    });

                    treeModal($modal, this.obj.wfId, function (node) {
                        self.obj.wfType = node.original.parentWfType;
                        self.obj.wfId = node.id;
                        self.wfName = node.text;
                    })
                },
                // 流程类型改变，重新请求对应的费用科目
                proTypeChange: function () {
                    var self = this;
                    self.$nextTick(function () {
                        self.fetchSub(self.obj.processTypeId)
                        self.obj.subjectId = '';
                    })
                },
                fetchSub: function (id) {
                    var self = this;
                    this.$http.get('/api/dict/getByType?parentType=' + id + '&type=2').then(function (res) {
                        if (res.data.code == 1) {
                            self.subList = res.data.data
                        }
                    }).finally(function () {
                    });
                },
                // 提交
                submit: function () {
                    var self = this;
                    self.submitBtnClick = true;
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.disabled = true;

                            self.$http.post('/api/subpro/saveorudpate', self.obj).then(function (res) {
                                if (res.data.code == 1) {
                                    self.$toastr.success('操作成功');
                                    callback && callback();
                                    $el.modal('hide');
                                    self.$destroy();
                                }
                                else {
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
        //创建的vue对象应该被返回
        return vueModal;
    }
})();

