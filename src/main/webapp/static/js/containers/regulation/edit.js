var vueIndex = null;
+(function (RocoUtils, moment) {
    $('#regulationMenu').addClass('active');
    $('#create').addClass('active');
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function (action) {
        if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
            return '/api/upload?category=UEDITOR';
        } else if (action == 'uploadvideo') {
            return '';
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    };

    var vueIndex = new Vue({
        el: '#container',
        components: {
            'web-uploader': RocoVueComponents.WebUploaderComponent
        },
        // 模式窗体必须引用 ModalMixin
        mixins: [RocoVueMixins.ModalMixin],
        data: {
            webUploaderSub: {
                type: 'sub',
                formData: {},
                is_weixin:false,
                accept: {
                    title: '文件',
                    extensions: 'jpg,png,gif,doc,docx,xls,xlsx,pdf'
                },
                server: ctx + '/api/upload',
                //上传路径
                fileNumLimit: 1,
                fileSizeLimit: 50000 * 1024,
                fileSingleSizeLimit: 5000 * 1024
            },
            //规章制度类型
            types: [],
            //控制 按钮是否可点击
            disabled: false,
            submitBtnClick: false,
            title: '',
            regulation: {
                title: '',
                type: '',
                sort: null,
                fileName: '',
                fileUrl: '',
                content: '',
                orgFamilyCode: null,
                orgName: null,
                orgId: '',
            },
            ueditor: null,
            orgData: null, // 机构树数据
            orgName: '', // 页面回显机构名称
            showOrgTree: false // 是否显示机构树
        },
        created: function () {
            this.isWeixin();
            this.initParams();

        },
        ready: function () {
            // UE.getEditor('content');
            this.getTypes();
            this.fetchOrgTree();
            this.user = window.RocoUser;
            this.buildUeditor();
        },
        watch: {},
        computed: {},
        methods: {
            isWeixin:function(){
                var self = this;
                var ua = navigator.userAgent.toLowerCase();
                if(ua.match(/MicroMessenger/i)=="micromessenger") {
                    self.is_weixin=true;
                } else {
                    self.is_weixin=false;
                }
            },
            getTypes: function () {
                var self = this;
                //查询所有公司制度类型
                self.$http.get('/api/dict/getDictsByType?type=4').then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                        self.types = res.data;
                    }
                });
            },
            //根据地址栏参数，初始化数据
            initParams: function () {
                var self = this;
                var params = RocoUtils.parseQueryString(window.location.search.substr(1));
                var id = params['id'];
                if (id) {
                    self.title = '编辑规章制度';
                } else {
                    self.title = '新增规章制度';
                }
                self.buildRegulation(id);
            },
            //构建
            buildRegulation: function (id) {
                var self = this;
                if (id) {
                    self.$http.get('/api/regulations/' + id + '/get').then(function (res) {
                        if (res.data.code == 1) {
                            self.regulation = res.data.data;
                        } else {
                            //提示用户
                        }
                    });
                } else {
                    // self.regulation = {
                    //     title: '',
                    //     type: '',
                    //     sort: null,
                    //     fileName: '',
                    //     fileUrl: '',
                    //     content: ''
                    // };
                }
            },
            buildUeditor: function () {
                var self = this;
                self.ueditor = UE.getEditor(self.$els.ueditor, {
                    toolbars: [['anchor', //锚点
                        'undo', //撤销
                        'redo', //重做
                        'bold', //加粗
                        'indent', //首行缩进
                        'snapscreen', //截图
                        'italic', //斜体
                        'underline', //下划线
                        'strikethrough', //删除线
                        'subscript', //下标
                        'fontborder', //字符边框
                        'superscript', //上标
                        'formatmatch', //格式刷
                        'source', //源代码
                        'blockquote', //引用
                        'pasteplain', //纯文本粘贴模式
                        'selectall', //全选
                        'print', //打印
                        'preview', //预览
                        'horizontal', //分隔线
                        'removeformat', //清除格式
                        'time', //时间
                        'date', //日期
                        'unlink', //取消链接
                        'insertrow', //前插入行
                        'insertcol', //前插入列
                        'mergeright', //右合并单元格
                        'mergedown', //下合并单元格
                        'deleterow', //删除行
                        'deletecol', //删除列
                        'splittorows', //拆分成行
                        'splittocols', //拆分成列
                        'splittocells', //完全拆分单元格
                        'deletecaption', //删除表格标题
                        'inserttitle', //插入标题
                        'mergecells', //合并多个单元格
                        'deletetable', //删除表格
                        'cleardoc', //清空文档
                        'insertparagraphbeforetable', //"表格前插入行"
                        // 'insertcode', //代码语言
                        'fontfamily', //字体
                        'fontsize', //字号
                        'paragraph', //段落格式
                        'simpleupload', //单图上传
                        'insertimage', //多图上传
                        'edittable', //表格属性
                        'edittd', //单元格属性
                        'link', //超链接
                        'emotion', //表情
                        'spechars', //特殊字符
                        'searchreplace', //查询替换
                        'map', //Baidu地图
                        // 'gmap', //Google地图
                        // 'insertvideo', //视频
                        'help', //帮助
                        'justifyleft', //居左对齐
                        'justifyright', //居右对齐
                        'justifycenter', //居中对齐
                        'justifyjustify', //两端对齐
                        'forecolor', //字体颜色
                        'backcolor', //背景色
                        'insertorderedlist', //有序列表
                        'insertunorderedlist', //无序列表
                        // 'fullscreen', //全屏
                        'directionalityltr', //从左向右输入
                        'directionalityrtl', //从右向左输入
                        'rowspacingtop', //段前距
                        'rowspacingbottom', //段后距
                        'pagebreak', //分页
                        'insertframe', //插入Iframe
                        'imagenone', //默认
                        'imageleft', //左浮动
                        'imageright', //右浮动
                        // 'attachment', //附件
                        'imagecenter', //居中
                        'wordimage', //图片转存
                        'lineheight', //行间距
                        'edittip ', //编辑提示
                        'customstyle', //自定义标题
                        'autotypeset', //自动排版
                        // 'webapp', //百度应用
                        'touppercase', //字母大写
                        'tolowercase', //字母小写
                        'background', //背景
                        'template', //模板
                        // 'scrawl', //涂鸦
                        // 'music', //音乐
                        'inserttable', //插入表格
                        'drafts', // 从草稿箱加载
                        'charts', // 图表
                    ]],
                    height: 465,
                    autoHeightEnabled: false
                });
                self.ueditor.on('ready',
                    function () {
                        if (self.regulation.content) {
                            self.ueditor.setContent(self.regulation.content);
                        }
                    });
            },

            // 获取机构选择树的数据
            fetchOrgTree: function () {
                var self = this;
                this.$http.get('/api/org/fetchTree').then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                        _.forEach(res.data, function (item) {
                            if ((',' + self.regulation.orgFamilyCode + ',').indexOf(',' + item.familyCode + ',') >= 0) {
                                item.checked = true;
                            }
                        })
                        self.orgData = res.data;
                        self.$nextTick(function () {
                            self.selectOrg();
                        })
                    }
                })
            },
            // 勾选机构数外部时，隐藏窗口
            clickOut: function () {
                this.showOrgTree = false
            },
            // 选择机构时回调事件
            selectOrg: function (node) {
                var self = this;
                //总数据
                var count = self.orgData.length - 1;
                var nodesCheckbox = self.$refs.nodesCheckbox.getCheckedNodes();
                if(node){
                //去计算 当前节点node,其兄弟节点如果有一个没有选中,就将其父节点不选中
                var parentNode = node.getParentNode();

                    var childLen = childLen = parentNode.children.length;
                    var checkCount = 0;
                    parentNode.children.forEach(function (node) {
                        if(node.checked){
                            checkCount ++;
                        }
                    })
                    if(checkCount < childLen){
                        parentNode.checked = false;
                    }
                }

                //选择机构时显示的名称
                var _names = '';
                //选择机构时显示的id
                var _ids = '';

                var len = nodesCheckbox.length - 1;
                for(var i = 0; i< nodesCheckbox.length; i++) {
                    if (nodesCheckbox[i].checked) {
                        _names += nodesCheckbox[i].name + (i < len ? ',' : '');
                        _ids += nodesCheckbox[i].id + (i < len ? ',' : '')
                    }
                }
                if(len < count){
                    _names = _names.replace("大美装饰管理平台," , "");
                    _ids = _ids.replace("1,", "");
                }else if(len == count){
                    _names = "大美装饰管理平台";
                    _ids = '1';
                }
                self.regulation.orgFamilyCode = _names;
                self.regulation.orgId = _ids;

            },



            //提交规章制度
            submit: function () {
                var self = this;
                self.regulation.content = self.ueditor.getContent();
                self.submitBtnClick = true;
                self.$validate(true, function () {
                    if (self.$validation.valid) {
                        self.disabled = true;
                        // 如果传空串，spring反序列化json串时，遇到枚举会报错，所以只传有值得属性
                        var data = self.filterNull(self.regulation);
                        self.$http.post('/api/regulations',
                            data
                        ).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                setTimeout(window.location.href = '/admin/regulations', 2000);
                            }
                        }).finally(function () {
                            self.disabled = false;
                            self.submitBtnClick = false;
                        });
                    }
                });
            },
            deleteFlie: function () {
                var self = this;
                self.$http.delete(ctx + '/api/upload', {
                    params: {
                        path: self.regulation.fileUrl
                    }
                }).then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        this.regulation.fileName = '';
                        this.regulation.fileUrl = '';
                    } else {
                        self.$toastr.error("删除失败");
                    }
                });
            },
            cancel: function () {
                window.location.href = '/admin/regulations';
            },
            //如果传空串，spring反序列化json串时，遇到枚举会报错，所以只传有值得属性
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
        }, events: {
            'webupload-upload-success-sub': function (file, res) {

                if (res.code == 1) {
                    this.$toastr.success('上传成功');
                    this.regulation.fileName = res.data.fileName;
                    this.regulation.fileUrl = res.data.path;
                } else {
                    this.$toastr.error(res.message);
                }
            }
        }
    });
})
(this.RocoUtils, moment);