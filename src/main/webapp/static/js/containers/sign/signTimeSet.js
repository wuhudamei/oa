var tt = null;
+(function () {
    $('#workMenu').addClass('active');
    $('#signtimeset').addClass('active');
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
            startTime: '',
            endTime: '',
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
                        $.jstree.defaults.sort = function (obj, deep) {
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
                            sort: function () {
                                var aaa = this.get_node(arguments[0]);
                                var bbb = this.get_node(arguments[1]);
                                return aaa.original.sort > bbb.original.sort ? 1 : -1;

                            },
                            plugins: ['sort', 'types', 'wholerow', 'changed']
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
                    orgCode: '',
                    orgName: '',
                    id: '',
                    familyCode: '',
                    parentType: '',
                    type: '',
                    signTime: '',
                    signOutTime: ''
                };
                var ref = _$jstree.jstree(true),
                    sel = ref.get_selected(true);
                // 未选择分类创建一级分类
                if (!sel.length) {
                    toastr.error("请选择节点");
                    return;
                } else {

                    org.id = sel[0].id;
                    org.parentType = sel[0].original.type;
                    // if(org.parentType=='GROUP'){
                    //     toastr.error("组下不允许创建架构");
                    //     return;
                    // }
                    switch (org.parentType) {
                        case 'BASE':
                            org.type = 'COMPANY';
                            break;
                        case 'COMPANY' :
                            org.type = 'DEPARTMENT';
                            break;
                        case 'DEPARTMENT' :
                            org.type = 'GROUP';
                        case 'GROUP' :
                            org.type = 'GROUP';
                            break;
                    }
                    this.showModel(org, true);

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
                modal($modal, org);
            }
        },
        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.fetchJsTree();
        }
    });
    // 实现弹窗方法
    function modal($el, model) {
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
                submitBtnClick: false,
                startTime: '',
                endTime: ''
            },
            created: function () {

            },
            ready: function () {
                this.findTime();
            },
            methods: {
                findTime: function () {
                    var self = this;
                    self.$http.get('/api/org/findTime?id=' + model.id).then(function (res) {
                        if (res.data.code == 1) {
                            res.data.data.forEach(function (item) {
                                if (item != null) {
                                    self.startTime = item.signTime.split(" ")[1];
                                    self.endTime = item.signOutTime.split(" ")[1];
                                }
                            })
                        }
                    })
                },

                submit: function () {
                    var self = this;
                    self.submitBtnClick = true;
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.disabled = true;
                            self.org.signTime = self.startTime;
                            self.org.signOutTime = self.endTime;
                            self.$http.post('/api/org/signTimeSet', self.org, {emulateJSON: true}).then(function (res) {
                                if (res.data.code == 1) {
                                    $el.on('hidden.bs.modal', function () {
                                        self.$toastr.success('设置成功');
                                    });
                                    $el.modal('hide');
                                } else {
                                    self.$toastr.error(res.data.message);
                                }
                            }).catch(function () {
                                swal(res.data.message, "", "error");
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
})();