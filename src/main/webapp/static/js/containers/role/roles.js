var vueIndex = null;
+(function (RocoUtils) {
	$('#systemMenu').addClass('active');
    $('#role').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '科目角色管理',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            form: {
                keyword: '',
                status: ''
            },
            organizations: ''
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/roles',
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
                        title: '名称',
                        align: 'center'
                    }, {
                        field: 'description',
                        title: '描述',
                        align: 'center'
                    }, {
                        field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            if (RocoUtils.hasPermission('role:edit'))
                                fragment += ('<button data-handle="operate-edit" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-default">编辑</button>');
                            if (RocoUtils.hasPermission('role:auth'))
                                fragment += ('<button data-handle="operate-setPermission" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-default">设置科目权限</button>');
                            if (RocoUtils.hasPermission('role:delete'))
                                fragment += ('<button data-handle="operate-delete" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-danger">删除</button>');
                            return fragment;
                        }
                    }]
                });

                self.$dataTable.on('click', '[data-handle="operate-edit"]', function (e) {
                    var id = $(this).data('id');
                    self.$http.get('/api/roles/' + id).then(function (res) {
                        if (res.data.code == 1) {
                            var role = res.data.data;
                            var model = {
                                id: role.id,
                                name: role.name,
                                description: role.description
                            };
                            this.showModel(model, true);
                        }
                    });
                });

                self.$dataTable.on('click', '[data-handle="operate-setPermission"]', function (e) {
                    var id = $(this).data('id');
                    var _$modal = $('#permissionModal').clone();
                    var $modal = _$modal.modal({
                        height: 450,
                        maxHeight: 450,
                        backdrop: 'static',
                        keyboard: false
                    });
                    permissionModal($modal, id);
                });

                self.$dataTable.on('click', '[data-handle="operate-delete"]', function (e) {
                    var id = $(this).data('id');
                    swal({
                        title: '删除角色',
                        text: '确定删除该角色么？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.get('/api/roles/' + id + '/del').then(function (res) {
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
                var user = {
                    name: '',
                    description: ''
                };
                this.showModel(user, false);
            },
            showModel: function (role, isEdit) {
                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    width: 480,
                    maxWidth: 480,
                    height: 280,
                    maxHeight: 280,
                    backdrop: 'static',
                    keyboard: false
                });
                modal($modal, role, isEdit);
            }
        },
        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.drawTable();
        }
    });
    // 新建编辑弹窗
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
                //控制 按钮是否可点击
                disabled: false,
                //模型复制给对应的key
                role: model,
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
                            self.$http.post('/api/roles', self.role, {emulateJSON: true}).then(function (res) {
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
        return vueModal;
    }

    //设置权限的弹窗
    function permissionModal($el, id) {
        //获取node
        var el = $el.get(0);

        //创建Vue对象编译节点
        var vueModal = new Vue({
                el: el,
                minxins: [RocoVueMixins.ModalMixin],
                $modal: $el, //模式窗体 jQuery 对象
                data: {
                    permissions: {},
                    selectedPermissions: {},
                    nodesCheck: null,
                    chkboxType: { "Y": "ps", "N": "ps" }
                },
                created: function () {
                    // this.getRoles(id);
                    this.initTree();
                },
                ready: function () {
                },
                methods: {
                    initTree: function () {
                        var self = this;
                        self.$http.get('/api/roles/subjectTree?roleId=' + id).then(function (res) {
                            if (res.data.code == 1) {
                               self.nodesCheck = res.data.data;
                            }
                        }).catch(function () {

                        }).finally(function () {

                        });
                    },
                    //查询用户角色信息
                    getRoles: function (id) {
                        var self = this;
                        self.$http.get('/api/roles/findRolePermission/' + id).then(function (res) {
                            if (res.data.code == 1) {
                                self.permissions = res.data.data;
                                self.setCheckedPermissions();//将该角色已有的权限添加到已选列表中
                            }
                        }).catch(function () {

                        }).finally(function () {

                        });
                    },
                    setCheckedPermissions: function () {
                        var self = this;
                        if (self.permissions) {
                            for (var module in self.permissions) {
                                if (self.permissions[module]) {
                                    var permissions = self.permissions[module];
                                    $(permissions).each(function (index, _item) {
                                        if (_item.checked == true) {
                                            self.selectedPermissions[_item.id] = _item.id;
                                        }
                                    });
                                }
                            }
                        }
                    },
                    //所有权限选择
                    selAllCb: function (permissions, e) {
                        var self = this;
                        _.each(permissions,
                            function (permission, index, array) {
                                self.checkAll(permission, e);
                            });
                    },
                    checkAll: function (permission, e) {
                        var self = this;
                        var checked = e.target.checked;
                        _.each(permission,
                            function (content, index, array) {
                                content.checked = checked;
                                self.checkSub(content, e);
                            });
                    },
                    // 单个权限选择
                    checkSub: function (content, e) {
                        var self = this;
                        var checked = e.target.checked;
                        if (checked) {
                            self.selectedPermissions[content.id] = content.id;
                        } else {
                            self.selectedPermissions[content.id] = null;
                        }
                    },
                    // submit: function () {
                    //     var self = this;
                    //     var permissions = [];
                    //     for (var key in self.selectedPermissions) {
                    //         if (self.selectedPermissions[key]) {
                    //             permissions.push(self.selectedPermissions[key]);
                    //         }
                    //     }
                    //     if (permissions.length == 0) {
                    //         Vue.toastr.warning('请至少选择一个权限');
                    //         return false;
                    //     }
                    //     var data = {
                    //         roleId: id,
                    //         permissions: permissions
                    //     };
                    //     self.$http.post('/api/roles/rolepermission', data, {emulateJSON: true}).then(function (res) {
                    //         if (res.data.code == 1) {
                    //             self.$toastr.success('操作成功');
                    //             $el.modal('hide');
                    //             self.$destroy();
                    //         }
                    //     }).finally(function () {
                    //         self.disabled = false;
                    //     });
                    // }
                    submit: function () {
                        var self = this;
                        // 获取选择的checkbox节点
                        var nodesCheckbox =  self.$refs.nodesCheckbox.getCheckedNodes();
                        // console.log(nodesCheckbox);
                        // console.log(id)
                        var list = [];
                        for (var i = 0; i < nodesCheckbox.length; i++) {
                            list.push(
                                nodesCheckbox[i].id
                            )
                        }

                        self.$http.post('/api/roles/setSubject', {roleId: id, list: list}, {emulateJSON: true})
                            .then(function (res) {
                                if (res.data.code == 1) {
                                    self.$toastr.success('操作成功');
                                    $el.modal('hide');
                                    self.$destroy();
                                }
                                else {
                                    self.$toastr.error(res.data.message);
                                }
                            })
                    }
                }
            }
        );
        //创建的vue对象应该被返回
        return vueModal;
    }
})
(this.RocoUtils);