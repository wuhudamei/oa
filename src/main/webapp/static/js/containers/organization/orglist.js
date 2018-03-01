var tt = null;

+(function () {
    $('#systemMenu').addClass('active');

    $('#organizationMenu').addClass('active');

    Vue.filter('org-property', function (val) {
        switch (val){
            case 'BASE':
                return '集团'
                break;
            case 'COMPANY' :
                return '公司'
                break;
            case 'DEPARTMENT' :
                return '部门'
            case 'GROUP' :
                return '组'
                break;
        }
    })

    tt = new Vue({
        el: '#container',
        data: {
            $dataTable: null,
            form: {
                keyword: '',
                status: '',
                parents: '',
                selectedItem: ''
            },

            organizations: ''
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            fetchJsTree: function () {
                var self = this;
                var _$jstree = $('#jstree');
                this.$http.get('/api/org/tree').then(function (res) {
                    if (res.data.code == 1) {

                        $.jstree.defaults.sort = function(obj,deep){
                            return 1;
                        }
                        _$jstree.jstree({
                            core: {
                                multiple: false,
                                // 不加此项无法动态删除节点
                                check_callback: true,
                                data: res.data.data
                            },
                            types: {
                                default: {
                                    icon: 'glyphicon glyphicon-stop'
                                }
                            },
                            sort:function(){
                                var aaa = this.get_node(arguments[0]);
                                var bbb = this.get_node(arguments[1]);
                                return aaa.original.sort > bbb.original.sort ? 1 : -1;

                            },
                            plugins: ['sort','types', 'wholerow', 'changed']
                        });

                    }
                }).catch(function () {

                }).finally(function () {

                });
            },
            createBtnClickHandler: function (e) {
                var self = this;
                var _$jstree = $('#jstree');
                var org = {
                    id: '',
                    orgCode: '',
                    orgName: '',
                    parentId: '',
                    tmpId: '',
                    familyCode: '',
                    parentType:'',
                    type:''
                };
                var ref = _$jstree.jstree(true),
                    sel = ref.get_selected(true);
                // 未选择分类创建一级分类
                if (!sel.length) {
                    toastr.error("请选择节点");
                    return;
                } else {

                    org.parentId = sel[0].id;
                    org.tmpId = sel[0].id;
                    org.parentType = sel[0].original.type;
                    // if(org.parentType=='GROUP'){
                    //     toastr.error("组下不允许创建架构");
                    //     return;
                    // }
                    switch (org.parentType){
                        case 'BASE':
                            org.type='COMPANY';
                            break;
                        case 'COMPANY' :
                            org.type='DEPARTMENT';
                            break;
                        case 'DEPARTMENT' :
                            org.type='GROUP';
                        case 'GROUP' :
                            org.type='GROUP';
                            break;
                    }

                    this.showModel(org, true);
                }


            },
            editBtn: function () {
                var self = this;
                var _$jstree = $('#jstree');
                var ref = _$jstree.jstree(true),
                    sel = ref.get_selected(true);
                if (!sel.length) {
                    toastr.error("请选择节点");
                    return;
                } else {
                    self.$http.get('/api/org/' + sel[0].id).then(function (res) {
                        if (res.data.code == 1) {
                            var org = res.data.data;
                            this.showModel(org, false);
                            // ref.rename_node(sel,data.text);
                        }
                    });
                }
            },
            deleteBtn: function () {
                var self = this;
                var _$jstree = $('#jstree');
                var ref = _$jstree.jstree(true),
                    sel = ref.get_selected(true);
                if (!sel.length) {
                    toastr.error("请选择节点");
                    return;
                } else {
                    swal({
                        title: "你确定删除该分类吗?",
                        text: "警告:删除后不可恢复！",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        closeOnConfirm: false
                    }, function (isConfirm) {
                        if (isConfirm) {
                            self.$http.get('/api/org/delete/' + sel[0].id).then(function (res) {
                                if (res.data.code == 1) {
                                    swal("操作成功!", "", "success");
                                    ref.delete_node(sel);
                                }else {
                                    self.$toastr.error(res.data.message);
                                }
                            }).catch(function () {
                                swal("操作失败！", "", "error");
                            }).finally(function () {
                                swal.close();
                            });
                        }
                    });
                }
            },
            showModel: function (org, isEdit) {
                var self = this;

                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    height: 300,
                    maxHeight: 450,
                    backdrop: 'static',
                    keyboard: false
                });
                modal($modal, org, isEdit, function (data, parentId, tmpId) {
                    var _$jstree = $('#jstree');

                    var ref = _$jstree.jstree(true),
                        sel = ref.get_selected(true);
                    if (org.id != '') {
                        ref.rename_node(sel, data.text);

                        // 移动节点
                        if (parentId != tmpId) {
                            ref.move_node(sel, tmpId);
                        }
                    } else {
                        ref.create_node(org.parentId, data);
                    }
                });
            }
        },
        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.fetchJsTree();
        }
    });
    // 实现弹窗方法-- 新增或编辑
    function modal($el, model, isEdit, callback) {
        console.log(model);
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
                //模型复制给对应的key
                parenId: '',
                parents: '',
                org: model,
                isEdit:isEdit,
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

                            // 不需要这个属性，只是展示的时候使用了
                            delete self.org.parent

                            self.$http.post('/api/org', self.org, {emulateJSON: true}).then(function (res) {
                            	if (res.data.code == 1) {
                                    $el.on('hidden.bs.modal', function () {
                                        self.$toastr.success('添加成功');
                                        var result = res.data.data;

                                        callback(result, self.org.parentId, self.org.tmpId);
                                    });
                                    $el.modal('hide');
                                }else{
                                	self.$toastr.error(res.data.message);
                                }
                            }).catch(function () {
                                swal(res.data.message, "", "error");
                            }).finally(function () {
                                self.disabled = false;
                            });
                        }
                    });
                },
                // choose
                chooseParent: function () {
                    var self = this;

                    if (self.org.type === 'COMPANY' || self.org.type === 'BASE') return;

                    var _$modal = $('#modaltree').clone();
                    var $modal = _$modal.modal({
                        height: 300,
                        maxHeight: 450,
                        backdrop: 'static',
                        keyboard: false
                    });
                    treeModal($modal, self.org, function (data) {
                        self.org.tmpId = data.id
                        self.org.parent.id = data.id
                        self.org.parent.orgName  = data.text
                    });
                }
            }
        });

        // 创建的Vue对象应该被返回
        return vueModal;
    }

    function treeModal($el, model, callback) {
        var el = $el.get(0);
        var modalTreeVue = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                org: model
            },
            ready: function () {
                this.showModalTree()
            },
            methods: {
                handleNode: function (nodes) {
                    var self = this;
                    var type = self.org.type;

                    for (var i = 0; i < nodes.length; i++) {
                        var node = nodes[i]
                        if (node.children && node.children.length > 0) {
                            self.handleNode(nodes[i].children);
                        }
                        var _type = node.type;

                        if ((type === 'GROUP' && (_type === 'COMPANY' || _type === 'BASE'))
                            || (type === 'DEPARTMENT' && _type === 'GROUP')
                            || (type === 'COMPANY' || type === 'BASE')) {
                            nodes[i].state.disabled = true;
                        }
                    }
                },
                showModalTree: function () {
                    var modalTree = $el.find('#treeModal');

                    this.$http.get('/api/org/tree')
                        .then(function (res) {
                        if (res.data.code == 1) {
                            var _node = res.data.data;
                            this.handleNode(_node);
                            var treeOrg = modalTree.jstree({
                                core: {
                                    multiple: false,
                                    // 不加此项无法动态删除节点
                                    check_callback: true,
                                    data: _node
                                },
                                types: {
                                    default: {
                                        icon: 'glyphicon glyphicon-stop'
                                    }
                                },
                                sort:function(){
                                    var aaa = this.get_node(arguments[0]);
                                    var bbb = this.get_node(arguments[1]);
                                    return aaa.original.sort > bbb.original.sort ? 1 : -1;

                                },
                                plugins: ['sort','types', 'wholerow', 'changed']
                            });

                            treeOrg.bind('select_node.jstree', function (e, data) {
                                callback && callback(data.node)
                                $el.modal('hide');
                            })
                        }
                    })
                }
            }
        })
    }
})();