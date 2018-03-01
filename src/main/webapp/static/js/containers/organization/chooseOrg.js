function chooseOrg(_config) {

    var config = {
        checkbox: false,
        cascade: true,
        height: 300,
        maxHeight: 450,
        backdrop: 'static',
        keyboard: false,
        list: [] // 回显列表
    };

    config = $.extend(config, _config);

    var _$modal = $('#orgModalChoose').clone();
    var $modal = _$modal.modal({
        height: config.height,
        maxHeight: config.maxHeight,
        backdrop: config.backdrop,
        keyboard: config.keyboard
    });
    treeModalShow($modal, config);
}

function treeModalShow($el, config) {
    var el = $el.get(0);
    var modalTreeVue = new Vue({
        el: el,
        // 模式窗体必须引用 ModalMixin
        mixins: [RocoVueMixins.ModalMixin],
        $modal: $el, //模式窗体 jQuery 对象
        ready: function () {
            this.showModalTree()
        },
        methods: {
            ok: function () {
                var tree = $el.find('#chooseTree').jstree(true);
                var nodes = tree.get_selected(true);
                config.callback && config.callback(nodes)
                $el.modal('hide');
            },
            contains: function (id) {
                for (var i = 0; i < config.list.length; i++) {
                    if (config.list[i] == id) {
                        return true
                    }
                }
                return false
            },
            initState: function (nodes) {
                for (var i = 0; i < nodes.length; i++) {
                    if (this.contains(nodes[i].id)) {
                        nodes[i].state.selected = true
                    }
                    if (nodes[i].children && nodes[i].children.length > 0) {
                        this.initState(nodes[i].children)
                    }
                }
            },
            showModalTree: function () {
                var self = this;
                var modalTree = $el.find('#chooseTree');

                this.$http.get('/api/org/tree')
                    .then(function (res) {
                        if (res.data.code == 1) {
                            var _node = res.data.data;

                            if (config.list && config.list.length > 0) {
                                self.initState(_node);
                            }

                            var treeOrg = modalTree.jstree({
                                core: {
                                    multiple: config.checkbox,
                                    // 不加此项无法动态删除节点
                                    check_callback: true,
                                    data: _node
                                },
                                types: {
                                    default: {
                                        icon: 'glyphicon glyphicon-stop'
                                    }
                                },
                                checkbox: {
                                    keep_selected_style: false,
                                    real_checkboxes: true,
                                    three_state: config.cascade  //父子级别级联选择
                                    // tie_selection: false // 是否支持 get_selected 方法
                                },
                                sort:function(){
                                    var aaa = this.get_node(arguments[0]);
                                    var bbb = this.get_node(arguments[1]);
                                    return aaa.original.sort > bbb.original.sort ? 1 : -1;

                                },
                                plugins: config.checkbox ? ['sort','types', 'wholerow', 'changed', 'checkbox'] : ['sort','types', 'wholerow', 'changed']
                            });
                        }
                    })
            }
        }
    })
}