+(function() {
    var zTree = Vue.extend({
        template:'#z-tree',
        props:{
            abs: {
                type: Boolean,
                default: true
            },
            chkboxType: {
                type: Object,
                default: null
            },
            nodes:{
                type:Array,
                default:[]
            },
            showTree: {
                type: Boolean,
                default: false
            },
            showCheckbox: {
                type:Boolean,
                default: true
            },
            querys:{
            	type:Object
            }
        },
        data: function(){
            return {
                r: '',
                setting: {
                    check: {
                        chkboxType:  { "Y" : "ps", "N" : "ps" },
                        enable: true // 是否显示checkbox框
                    },
                    data: {
                        simpleData: {
                            enable: true
                        }
                    },
                    view: {
                        showIcon: false
                    },
                    callback: {
                        onCheck: this.zTreeOnCheck,
                        onClick: this.zTreeOnClick
                    }
                },
                TreeLayer: null,
                tree: null
            };
        },
        created: function() {
            this.r = +new Date();
            this.setting.check.enable = this.showCheckbox;
            if (this.chkboxType) {
                this.setting.check.chkboxType = this.chkboxType;
            }
        },
        ready: function() {
            var self = this;
            this.tree = $.fn.zTree.init($('#_' + self.r), self.setting, self.nodes);
        },
        methods: {
            zTreeOnCheck: function(event, treeId, treeNode) {
                this.$emit('on-change', treeNode)
            },
            zTreeOnClick: function(event, treeId, treeNode) {
            	if(this.querys){
            		this.$emit('on-click', treeNode,this.querys)
            	}else{
            		this.$emit('on-click', treeNode)
            	}
                
            },
            getCheckedNodes: function() {
                return this.tree.getCheckedNodes(true);
            },
            getSelectedNodes: function() {
                return this.tree.getSelectedNodes();
            }
        },
        watch: {
            nodes: function(val) {
                var self = this;
                this.tree = $.fn.zTree.init($('#_' + self.r), self.setting, val);
            }
        }
    });

    Vue.component('z-tree', zTree);
})();