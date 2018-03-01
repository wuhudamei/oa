var vueIndex = null;
+(function () {
    $('#noticeList').addClass('active');
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function (action) {
        if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
            return '/api/upload?category=NOTICEBOARD';
        } else if (action == 'uploadvideo') {
            return '';
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    };
    vueIndex = new Vue({
        // container
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '公告修改',
                active: true //激活面包屑的
            }],

            notice: {
                content: '',
                title: '',
                noticeStatus: '',
                orgFamilyCode: '',
                orgId: '',
            },

            orgData: {},
            disabled: false,
            showOrgTree: false, // 是否显示机构树
            submitBtnClick: false,
            params: null,
            ueditor: null,
            orgName: ''
        },
        methods: {
            //富文本编辑器
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
                        if (self.notice.content) {
                            self.ueditor.setContent(self.notice.content);
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
                            if ((',' + self.notice.orgFamilyCode + ',').indexOf(',' + item.familyCode + ',') >= 0) {
                                item.checked = true;
                            }else if (self.notice.orgFamilyCode == '1') {
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

                //去计算 当前节点node,其兄弟节点如果有一个没有选中,就将其父节点不选中
                if (node) {
                    var parentNode = node.getParentNode();
                    if (parentNode) {
                        var childLen = childLen = parentNode.children.length;
                        var checkCount = 0;
                        parentNode.children.forEach(function (node) {
                            if (node.checked) {
                                checkCount++;
                            }
                        })
                        if (checkCount < childLen) {
                            parentNode.checked = false;
                        }
                    }
                }

                //选择机构时显示的名称
                var _names = '';
                //选择机构时显示的id
                var _ids = '';

                var len = nodesCheckbox.length - 1;
                for (var i = 0; i < nodesCheckbox.length; i++) {
                    if (nodesCheckbox[i].checked) {
                        _names += nodesCheckbox[i].name + (i < len ? ',' : '');
                        _ids += nodesCheckbox[i].id + (i < len ? ',' : '')
                    }
                }
                if (len < count) {
                    _names = _names.replace("大美装饰管理平台,", "");
                    _ids = _ids.replace("1,", "");
                } else if (len == count) {
                    _names = "大美装饰管理平台";
                    _ids = '1';
                }
                self.orgName = _names;
                self.notice.orgId = _ids;
                if (_names === '' && window.RocoUser.orgId === 9999) {
                    self.orgName = '大美装饰管理平台';
                    self.notice.orgId = '1';

                } else {
                    self.orgName = _names;
                    self.notice.orgId = _ids;
                }
            },
            submit: function () {
                var self = this;
                self.notice.content = self.ueditor.getContent();
                self.submitBtnClick = true;
                var id = this.$parseQueryString()['id'];
                self.$validate(true, function () {
                    if (self.$validation.valid) {
                        self.disabled = true;
                        self.$http.post('/noticeboard/editNotice/' + id, self.notice, {emulateJSON: true}).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                setTimeout(function () {
                                    window.location.href = "/admin/noticeboard/noticeboard";
                                }, 1500);
                            } else {
                                self.disabled = false;
                            }
                        }).finally(function () {
                        });
                    }
                });
            },
            cancel: function () {
                window.location.href = "/admin/noticeboard/noticeboard";
            },
            fetchDetail: function () {
                // UE.getEditor('content');
                var self = this
                self.$http.get('/noticeboard/findNoticeById/' + self.params.id).then(function (response) {
                    var res = response.data;

                    if (res.code == '1') {
                        self.notice = res.data;
                        self.fetchOrgTree();
                    } else {
                        self.$toastr.error(res.message);
                    }
                });
            }
        },
        created: function () {
            var self = this;
            self.params = self.$parseQueryString()
            self.fetchDetail()
        },
        ready: function () {
            this.buildUeditor();
        }
    });
})
();