var vueIndex = null;
var vueModal = null;// 流程新增窗口
+(function (RocoUtils) {
    $('#systemMenu').addClass('active');
    $('#processManager').addClass('active');


    var TYPE = {
        2: [{
            value: 'YEARBUDGET_ADMINISTRATOR',
            name: '年度-行政类'
        }, {
            value: 'YEARBUDGET_MATTERS',
            name: '年度-人事类'
        }, {
            value: 'YEARBUDGET_FINANCE',
            name: '年度-财务类'
        }, {
            value: 'YEARBUDGET_CUSTOMER',
            name: '年度-客管类'
        }, {
            value: 'YEARBUDGET_MARKETING',
            name: '年度-营销类'
        }],
        3: [{
            value: 'BUDGET_ADMINISTRATOR',
            name: '预算-行政类'
        }, {
            value: 'BUDGET_MATTERS',
            name: '预算-人事类'
        }, {
            value: 'BUDGET_FINANCE',
            name: '预算-财务类'
        }, {
            value: 'BUDGET_CUSTOMER',
            name: '预算-客管类'
        }, {
            value: 'BUDGET_MARKETING',
            name: '预算-营销类'
        }],
        5: [{
            value: 'EXPENSE_ADMINISTRATOR',
            name: '报销-行政类'
        }, {
            value: 'EXPENSE_MATTERS',
            name: '报销-人事类'
        }, {
            value: 'EXPENSE_FINANCE',
            name: '报销-财务类'
        }, {
            value: 'EXPENSE_CUSTOMER',
            name: '报销-客管类'
        }, {
            value: 'EXPENSE_MARKETING',
            name: '报销-营销类'
        }],
        4: [{
            value: 'PURCHASE_ADMINISTRATOR',
            name: '采购-行政类'
        }, {
            value: 'PURCHASE_MATTERS',
            name: '采购-人事类'
        }, {
            value: 'PURCHASE_FINANCE',
            name: '采购-财务类'
        }, {
            value: 'PURCHASE_CUSTOMER',
            name: '采购-客管类'
        }, {
            value: 'PURCHASE_MARKETING',
            name: '采购-营销类'
        }],
        6: [{
            value: 'SIGNATURE_ADMINISTRATOR',
            name: '签报-行政类'
        }, {
            value: 'SIGNATURE_MATTERS',
            name: '签报-人事类'
        }, {
            value: 'SIGNATURE_FINANCE',
            name: '签报-财务类'
        }, {
            value: 'SIGNATURE_CUSTOMER',
            name: '签报-客管类'
        }, {
            value: 'SIGNATURE_MARKETING',
            name: '签报-营销类'
        }],
        8: [
            {
                value: 'BUSINESS',
                name: '出差'
            }
        ],
        7: [
            {
                value: 'LEAVE',
                name: '请假'
            }
        ]
    }

    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '流程管理',
                active: true // 激活面包屑的
            }],
            $dataTable: null,
            typeList: [], // 流程节点
            wfType:null,	// 上级流程类型
            wfNature:null,	// 上级流程性质(审批,执行)
            wfTypeDisabled:false, // 类型是否不可选
            wfNatureDisabled:false, // 性质是否不可选
            add:false,
            addDisabled: false,
            edit:false,
            deleted:false,
            isShow:true
        },
        methods: {
            initParams: function () {
                var self = this;
                self.drawPermissionTree();
            },
            drawPermissionTree: function () {
                var self = this;
                var _$processTree = $('#jstree');
                self.$http.get('/api/process/findAll').then(function (response) {
                    var result = response.data;

                    result.data.state.disabled = true;

                    if (result.code == 1) {
                        $.jstree.defaults.sort = function (obj, deep) {
                            return 1;
                        }
                        _$processTree.jstree({
                            core: {
                                multiple: false,
                                // 不加此项无法动态删除节点
                                check_callback: true,
                                data: result.data
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
                        
                      // 单击事件
                        _$processTree.bind('click.jstree', function(event) { 
	                           var ref = _$processTree.jstree(true);
	                           var sel = ref.get_selected(true);

	                           if(!sel.length){
	                        	  return ; 
	                           }
                               var pid = sel[0].original.pid;
                               var tmpwfType = sel[0].original.parentWfType;
                               var tmpwfNature = sel[0].original.parentWfNature;

                               vueIndex.wfType = tmpwfType;
                               vueIndex.wfNature = tmpwfNature;
                               vueIndex.wfTypeDisabled = tmpwfType != null ? true : false;
                               vueIndex.wfNatureDisabled = tmpwfNature !=null ? true : false;

                               if(pid == '0'){
                            	  vueIndex.edit = true;
                            	  vueIndex.deleted = true;
                            	  vueIndex.isShow = false;

                            	  self.addDisabled = true;
                               }
                               else if (pid == '1') {
                                   vueIndex.edit = true;
                                   vueIndex.deleted = true;
                                   vueIndex.isShow = false;

                                   self.addDisabled = false;
                               }
                               else if(pid == '2' || pid == '3' || pid == '4'){
                            	  vueIndex.edit = false;
                            	  vueIndex.deleted = false;
                            	  vueIndex.isShow = false;

                            	  // 如果有两个节点则不能添加了
                                    if (sel[0].children.length == 2) {
                                        self.addDisabled = true;
                                    }
                                    else {
                                        self.addDisabled = false;
                                    }
                               }else{
                            	   vueIndex.edit = false;
                            	   vueIndex.deleted = false;
                            	   vueIndex.isShow = true;

                                   self.addDisabled = false;
                               }
                       });

                    } else if (result.code == '0') {
                        self.$toastr.error(result.message);
                    }
                }).catch(function () {
                }).finally(function () {
                });
            },
            // 返回流程管理树选择的节点
            getManageTreeSelectedNode: function () {
                return this.getManageTree().get_selected(true);
            },
            getManageTree: function () {
                return $('#jstree').jstree(true);
            },
            createBtnClickHandler: function (e) {
                var self = this;
                var _$processTree = $('#jstree');
                var ref = _$processTree.jstree(true),
                    sel = ref.get_selected(true);

                var _selNode = sel[0];

                var pid = _selNode.original.pid;

                // 未选择分类创建一级分类
                if (!sel.length) {
                    toastr.warning("请选择节点");
                    return;
                }
                // else if (pid == 1 && _selNode.children.length == 5) {
                //     toastr.warning("请选择其它节点");
                //     return;
                // }
                else if ((pid == '2' || pid == '3' || pid == '4') && _selNode.children.length == 2) {
                    toastr.warning("请选择其它节点");
                    return;
                }
                else {

                    // 新建的时候，如果现在的选择节点是二级节点，则可以看到全数据
                    if (pid == '2' || pid == '3' || pid == '4') {
                        vueIndex.isShow = true;
                    }

                    // self.typeList = TYPE[_selNode.parents.length < 3 ? _selNode.id : _selNode.parents[_selNode.parents.length - 3]]

                    var process = {
                        id: '',
                        nodeTitle: '',
                        wfType: vueIndex.wfType,
                        wfNature: vueIndex.wfNature,
                        approvalType:null,
                        approver:null,
                        approvalAmount:0,
                        approvalDayNum:0,
                        applyOrg:null,
                        seq: '',
                        pid: sel[0].id,
                        applyOrgName: '',
                        approverName: '',
                        ccUser: null,
                        ccUserName: ''
                    };
                    self.showModel(process, false);
                }
            },
            editBtn: function (e) {
                var self = this;
                var _$processTree = $('#jstree');
                var ref = _$processTree.jstree(true),
                    sel = ref.get_selected(true);

                // 未选择分类创建一级分类
                if (!sel.length) {
                    self.$toastr.warning("请选择节点");
                    return;
                } else {
                    var _sel = sel[0];

                    // 编辑的时候，如果现在的选择节点是二级节点，则不可以看到全数据
                    if (_sel.id == '2' || _sel.id == '3' || _sel.id == '4') {
                        vueIndex.isShow = false;
                    }

                    // 是否需要选择上级 如果父节点超过3个则可以选择
                    var chooseP = _sel.parents.length >= 4 ? true : false;

                    var id = _sel.id;
                    self.$http.get('/api/process/' + id + '/get').then(function (response) {
                        var result = response.data;
                        if (result.code == '1') {
                            if (chooseP) {
                                result.data.chooseP = chooseP;
                                result.data.parentFId = _sel.parents[_sel.parents.length - 4];

                                var pNode = ref.get_node(_sel.parent);

                                result.data.nextPName = pNode.text;

                            }

                            // self.typeList = TYPE[_sel.parents.length < 3 ? _sel.id : _sel.parents[_sel.parents.length - 3]]

                            self.showModel(result.data, true);
                        } else {
                            self.$toastr.warning(result.message);
                            return;
                        }
                    });
                }
            },
            showModel: function (process, isEdit) {
                var self = this;
                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    height: 400,
                    maxHeight: 450,
                    backdrop: 'static',
                    keyboard: false
                });
                modal($modal, process, isEdit, function (data) {
                    var _$jstree = $('#jstree');

                    var ref = _$jstree.jstree(true),
                        sel = ref.get_selected(true);
                    if (process.id != '') {
                        ref.rename_node(sel, data.text);
                        // 可能会移动节点
                        ref.move_node(sel, data.pid);
                    } else {
                        ref.create_node(process.pid, data);
                    }
                });
            },
            deleteBtn: function (e) {
                var self = this;
                var _$processTree = $('#jstree');
                var ref = _$processTree.jstree(true),
                    sel = ref.get_selected(true);
                if (!sel.length) {
                    toastr.warning("请选择节点");
                    return;
                } else if(sel[0].parents.length == 1){
                    toastr.warning("根节点不能删除");
                    return;
                }
                else if (sel[0].children.length > 0) {
                    toastr.warning("先请删除叶子节点");
                    return;
                }
                else {
                    swal({
                        title: "你确定删除该权限吗?",
                        text: "警告:删除后不可恢复！",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        closeOnConfirm: false
                    }, function (isConfirm) {
                        if (isConfirm) {
                            self.$http.delete('/api/process/' + sel[0].id + '/del').then(function (response) {
                                var result = response.data;
                                if (result.code == 1) {
                                    swal("操作成功!", "", "success");
                                    ref.delete_node(sel);
                                } else {
                                    self.$toastr.error(result.message);
                                }
                            }).catch(function () {
                                swal("操作失败！", "", "error");
                            }).finally(function () {
                                swal.close();
                            });
                        }
                    });
                }
            }
        },
        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.initParams();
        }
    });

    // 新建编辑弹窗
    function modal($el, process, isEdit, callback) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        vueModal = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, // 模式窗体 jQuery 对象
            data: {

                typeList: [], // 类型列表

                // 控制 按钮是否可点击
                disabled: false,
                // 模型复制给对应的key
                process: process,
                isEdit: isEdit,
                submitBtnClick: false,
                isShow:vueIndex.isShow,
                wfTypeDisabled:vueIndex.wfTypeDisabled,
                wfNatureDisabled:vueIndex.wfNatureDisabled,
            },
            created: function () {
                this.typeList = vueIndex.typeList
            },
            ready: function () {

            },
            methods: {
                changeP: function () {
                    var self = this;

                    // 如果已经是流程性质的第一个节点了，则不能选择了
                    var manageTreee = vueIndex.getManageTree();
                    var pNode = manageTreee.get_node(self.process.pid)
                    if (pNode.parent == 1 || pNode.parent == 2 || pNode.parent == 3 || pNode.parent == 4 || pNode.parent == 5 || pNode.parent == 6 || pNode.parent == 7 || pNode.parent == 8) {
                        return;
                    }

                    chooseP(self.process, function (node) {
                        self.process.nextPName = node.text;
                        self.process.pid = node.id;
                    });
                },
                // 提交
                submit: function () {
                    var self = this;
                    self.submitBtnClick = true;
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.disabled = true;

                            // 判断流程流程性质是否已存在
                            var selectedNode = vueIndex.getManageTreeSelectedNode()[0];
                            // 如果是类型添加
                            if (!self.process.id && (selectedNode.parent == 2 || selectedNode.parent == 3 || selectedNode.parent == 4)
                                && selectedNode.children && selectedNode.children.length > 0) {
                                // 如果已有两个节点则不提交了
                                if (selectedNode.children.length >= 2) {
                                    self.$toastr.error('已有两个节点，不能继续添加了');
                                    self.disabled = false;
                                    return;
                                }

                                var manageTree = vueIndex.getManageTree();
                                // 看看另外一个节点是什么性质的，如果是相同性质的则不让提交了
                                var childNode = manageTree.get_node(selectedNode.children[0]);
                                if (childNode && childNode.original.parentWfNature == self.process.wfNature) {
                                    self.$toastr.error('该流程性质已存在，不能选择');
                                    self.disabled = false;
                                    return;
                                }
                            }
                            // 当前选择的是根节点,判断该类型是否已添加过
                            // else if (selectedNode.parent == '1' && selectedNode.children && selectedNode.children.length > 0) {
                            //     //
                            //     var manageTree = vueIndex.getManageTree();
                            //     for (var i = 0; i < selectedNode.children.length; i++) {
                            //         var childNode = manageTree.get_node(selectedNode.children[i]);
                            //         if (childNode.original.parentWfType == self.process.wfType) {
                            //             self.$toastr.error('该流程类型已存在，不能选择');
                            //             self.disabled = false;
                            //             return;
                            //         }
                            //     }
                            // }



                            var _nextPName = self.process.nextPName, _parentFId = self.process.parentFId, _chooseP = self.process.chooseP;

                            delete self.process.nextPName
                            delete self.process.parentFId
                            delete self.process.chooseP

                            self.$http.post('/api/process/edit', self.process).then(function (response) {
                                var result = response.data;
                                if (result.code == 1) {
                                    $el.on('hidden.bs.modal', function () {
                                        self.$toastr.success('操作成功');
                                        callback(result.data);
                                    });
                                    $el.modal('hide');
                                } else {
                                    self.$toastr.error(response.data.message);

                                    self.process.nextPName = _nextPName;
                                    self.process.parentFId = _parentFId;
                                    self.process.chooseP = _chooseP;
                                }
                            }).finally(function () {
                                self.disabled = false;
                            });
                        }
                    });
                },
                showApprover:function(type){ // 选择审批人
                	showApprover(type);
                },
                showApplyOrg:function(){// 选择申请部门

                	chooseOrg({
                	    list: this.process.applyOrg ? this.process.applyOrg.substring(1, this.process.applyOrg.length - 1).split(',') : [],
                	    checkbox: true,
                        cascade: false,
                        callback: function (data) {
                            var tmpOrgId = ",";
                            var tmpOrgName = "";

                            data.forEach(function (val) {
                                tmpOrgId += val.id + ",";
                                tmpOrgName += val.text + ",";
                            });
                            vueModal.process.applyOrg = !data.length ? '' : tmpOrgId;
                            vueModal.process.applyOrgName = !data.length ? '' : tmpOrgName.substring(0,tmpOrgName.length-1);
                    }})
                }
            }
        });
        // 创建的Vue对象应该被返回
        return vueModal;
    }

    function chooseP(process, callback) {
        var _$modal = $('#parentModalChoose').clone();
        var $modal = _$modal.modal({
            height: 400,
            maxHeight: 450,
            backdrop: 'static',
            keyboard: false
        });
        showChoosePModal();

        function showChoosePModal() {
            var choosePM = new Vue({
                el: $modal.get(0),
                // 模式窗体必须引用 ModalMixin
                mixins: [RocoVueMixins.ModalMixin],
                $modal: $modal, // 模式窗体 jQuery 对象
                data: {

                },
                created: function () {
                },
                ready: function () {
                    this.initPTree()
                },
                methods: {
                    ok: function () {
                        var self = this;
                        var chooseTree = $modal.find('#chooseParent').jstree(true);
                        var selectedNode = chooseTree.get_selected(true);

                        if (selectedNode && selectedNode.length > 0) {
                            callback && callback(selectedNode[0]);
                            $modal.modal('hide');
                            this.$destroy();
                        }
                        else {
                            self.$toastr.error('未选择任何节点');
                            return;
                        }
                    },
                    handleTreeNode: function (nodes) {

                        for (var i = nodes.length - 1; i >= 0; i--) {
                            if (nodes[i].id == process.pid) {
                                nodes[i].state.disabled = true;
                            }
                            if (nodes[i].id == process.id) {
                                // nodes[i] = undefined
                                nodes.splice(i, 1);
                            }

                            if (nodes[i] && nodes[i].children && nodes[i].children.length > 0) {
                                this.handleTreeNode(nodes[i].children)
                            }
                        }
                    },
                    // 初始化该节点树
                    initPTree: function () {
                        var self = this;
                        self.$http.delete('/api/process/findByPid?pid=' + process.parentFId + '&wfNature=' + process.wfNature).then(function (response) {
                            var result = response.data;
                            if (result.code == 1) {
                                var _node = [result.data];
                                self.handleTreeNode(_node);
                                var chooseParent = $modal.find('#chooseParent');
                                chooseParent.jstree({
                                    core: {
                                        multiple: false,
                                        // 不加此项无法动态删除节点
                                        check_callback: true,
                                        data: result.data
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
                            } else {
                                self.$toastr.error(result.message);
                            }
                        }).catch(function () {
                            swal("操作失败！", "", "error");
                        }).finally(function () {

                        });
                    }
                }
            });
        }
    }

    /**
	 * 选择审批人  userType 为 1 审批人  为 0 抄送人
	 */
    function showApprover(userType){
        var _modal = $('#approverListModel').clone();
        var $el = _modal.modal({
            height: 200,
            maxHeight: 200,
            backdrop: 'static',
        });
        $el.on('shown.bs.modal',
            function () {
                var el = $el.get(0);
                var approverModal = new Vue({
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
                            keyword: ''
                            // limit: 5
                        },
                        selectApprover: [], // 添加选中的审批人
                        tmpSelectApproverList:[],

                        modalModel: null, // 模式窗体模型
                        _$el: null, // 自己的 el $对象
                        _$dataTable: null // datatable $对象
                    },
                    methods: {
                        query: function () {
                            this.$dataTable.bootstrapTable('refresh');
                        },
                        initList: function () {
                            var self = this;
                            var _tmpUserr = userType == 1 ? vueModal.process.approver : vueModal.process.ccUser;
                            
                            if (_tmpUserr && _tmpUserr != null && _tmpUserr !== '') {
                                _tmpUserr.split(',').forEach(function (item) {
                                    self.tmpSelectApproverList.push(item);
                                })
                            }
                        },
                        // type: true 添加 type: false 删除
                        handleApprover: function (obj, type) {
                            var self = this;
                            // 添加
                            if (type) {
                                for (var i = 0; i < self.selectApprover.length; i++) {
                                    if (self.selectApprover[i].id == obj.id) return;
                                }

                                self.selectApprover.push({
                                    id: obj.id,
                                    name:obj.name
                                })
                            }
                            // 删除
                            else {
                                for (var i = 0; i < self.selectApprover.length; i++) {
                                    if (self.selectApprover[i].id == obj.id) {
                                        self.selectApprover.splice(i, 1);
                                        return;
                                    }
                                }
                            }
                        },
                        // type: true 添加 type: false 删除
                        handleTmpSelected: function (id, type) {
                            var self = this;
                            // 添加
                            if (type) {
                                for (var i = 0; i < self.tmpSelectApproverList.length; i++) {
                                    if (self.tmpSelectApproverList[i] == id) return;
                                }

                                self.tmpSelectApproverList.push(id)
                            }
                            // 删除
                            else {
                                for (var i = 0; i < self.tmpSelectApproverList.length; i++) {
                                    if (self.tmpSelectApproverList[i] == id) {
                                        self.tmpSelectApproverList.splice(i, 1);
                                        return;
                                    }
                                }
                            }
                        },
                        drawTable: function () {
                            var self = this;
                            self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
                                url: '/api/employees?employeeStatus=ON_JOB',
                                method: 'get',
                                dataType: 'json',
                                cache: false,
                                pagination: true,// 不分页
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
                                    checked: false,
                                    formatter: function (value, row, index) {
                                        for (var i = 0; i < self.tmpSelectApproverList.length; i++) {
                                            if (self.tmpSelectApproverList[i] == row.id) {
                                                self.handleApprover(row, true);
                                                return true;
                                            }
                                        }

                                        return false;
                                    }
                                }, {
                                    field: 'id',
                                    title: 'id',
                                    width: '6%',
                                    visible: false
                                }, {
                                    field: 'orgCode',
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
                            //选一条
                            self.$dataTable.on('check.bs.table', function (row, data) {
                                self.handleTmpSelected(data.id, true);
                                self.handleApprover(data, true);
                            });
                            //取消选一条
                            self.$dataTable.on('uncheck.bs.table', function (row, data) {
                                self.handleTmpSelected(data.id, false);
                                self.handleApprover(data, false);
                            });
                            //全选
                            self.$dataTable.on('check-all.bs.table', function (row, data) {
                            	data.forEach(function (val, index) {
                                    self.handleTmpSelected(val.id, true);
                                    self.handleApprover(val, true);
                                });
                            });
                            //取消全选
                            self.$dataTable.on('uncheck-all.bs.table', function (row, data) {
                                data.forEach(function (val, index) {
                                    self.handleTmpSelected(val.id, false);
                                    self.handleApprover(val, false);
                                });
                            });                            
                        },
                        commitUsers: function () {
                            var self = this;
                            if (self.selectApprover != null && self.selectApprover != undefined && self.selectApprover.length > 0) {
                            	var tmpId = "";
                            	var tmpName = "";
                            	self.selectApprover.forEach(function (val) {
                                		tmpId += val.id + ",";
                                		tmpName += val.name + ",";
                            	});

                            	if (userType == 1) {
                                    vueModal.process.approver = tmpId.substring(0, tmpId.length - 1);
                                    vueModal.process.approverName = tmpName.substring(0, tmpName.length - 1);
                                }
                                else if (userType == 0) {
                                    vueModal.process.ccUser = tmpId.substring(0, tmpId.length - 1);
                                    vueModal.process.ccUserName = tmpName.substring(0, tmpName.length - 1);
                                }

                            }
                            else {
                                if (userType == 1) {
                                    vueModal.process.approver = '';
                                    vueModal.process.approverName = '';
                                }
                                else if (userType == 0) {
                                    vueModal.process.ccUser = '';
                                    vueModal.process.ccUserName = '';
                                }

                            }
                            $el.modal('hide');
                            this.$destroy();
                        }
                    },
                    ready: function () {
                        this.initList();
                        this.drawTable();
                    }
                });

                // 创建的Vue对象应该被返回
                return approverModal;
            });
    }
})(RocoUtils);