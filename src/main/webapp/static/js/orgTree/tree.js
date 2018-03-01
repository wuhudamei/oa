var tt = null;
+(function (RocoUtils) {
    // 引入文件:
    // /static/css/wx/zTreeStyle.css
    // /static/admin/js/jquery.ztree.core.js
    // /static/admin/js/jquery.ztree.excheck.js

    // <%@include file="/WEB-INF/views/admin/component/ztree.jsp" %>
    // <script src="/static/admin/js/components/ztree.js"></script>

    $('#dictionaryMenu').addClass('active');

    tt = new Vue({
        el: '#container',
        data: {
            nodesCheck: null,
            nodesSelect: null,
            showTreeCheck: false,
            showTreeSelect: false
        },
        methods: {


            createBtnClickHandler: function () {
                var self = this;
                var org = {
                    id: '',
                    orgCode: '',
                    orgName: '',
                    parentId: '',
                    familyCode: '',
                    type:'DEPARTMENT'
                };
                // 获取选择的checkbox节点

                this.showModel(org, false);
            },
            showModel: function (org) {
                var self = this;

                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    height: 300,
                    maxHeight: 450,
                    backdrop: 'static',
                    keyboard: false
                });

                modal($modal, org, function (data) {

                });
            },

        },
        created: function () {
        },
        ready: function () {

        }
    });
// 实现弹窗方法
    function modal($el, model, callback) {
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
                nodesCheck: null,
                nodesSelect: null,
                showTreeCheck: false,
                showTreeSelect: false

            },
            created: function () {
                var self=this;
                self.fetchJsTree();
            },
            ready: function () {
            },
            methods: {
                callbackTree:function () {
                    var self=this;
                    self.showTreeCheck=false;
                },
                // 该方法获取checkbox选择状态变化的节点数据
                treeCheckboxChange: function (node) {
                    console.log(node);
                },
                // 点击节点的时候返回该节点的数据
                treeClick: function (node) {
                    console.log(node);
                },
                fetchJsTree: function () {
                    var self = this;
                    this.$http.get('/api/org/fetchTree').then(function (res) {
                        if (res.data.code == 1) {
                            self.nodesCheck = res.data.data;
                            self.nodesSelect=res.data.data;
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

                            self.$http.post('/api/org', self.org).then(function (res) {
                                if (res.data.code == 1) {
                                    $el.on('hidden.bs.modal', function () {
                                        self.$toastr.success('添加成功');
                                        callback(res.data.data);
                                    });
                                    $el.modal('hide');
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
})(RocoUtils);