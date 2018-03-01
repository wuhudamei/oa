var vueIndex,uploader = null;
+(function () {
    $('#chairmanMailbox').addClass('active');
    $('#addMail').addClass('active');
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function (action) {
        if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
            return '/api/upload?category=CHAIRMAN_MAIBOX';
        } else if (action == 'uploadvideo') {
            return '';
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    };
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '写信',
                active: true //激活面包屑的
            }],
            form: {
                title: '',
                content: '',
                anonymous: false,
                //用于微信数据提交,存本地语音id,点击提交后,是微信端服务器上的serverId
                //微信端图片url集合
                pictureUrls: '',
                //微信服务器端语音id
                voiceUrl: '',
            },
            ueditor: null,
            disabled: false,
            submitBtnClick: false,
            isPc: true,
            voiceStatus: '开始录音',
            voiceClass: 'btn btn-primary',
            //录音切换
            toggle: 1,
            //显示删除录音按钮
            showDelVoice: false,
            //播放
            playVoice: "播放录音",
            playClass: 'btn btn-primary',
            showStop: false,
            //微信端本地图片集合
            localImageArr: null,
            //微信端本地语音id
            localVoice: ''

        },
        methods: {
            //富文本编辑
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
                        'charts' // 图表
                    ]],
                    height: 465,
                    autoHeightEnabled: false
                });
                self.ueditor.on('ready',
                    function () {
                        if (self.form.content) {
                            self.ueditor.setContent(self.form.content);
                        }
                    });
            },

            //提交
            submit: function () {
                var self = this;
                if(self.checkFromWxOrPc()){
                    //PC端
                    self.form.content = self.ueditor.getContent();
                }
                console.log(self.form.content);
                if(!self.form.content){
                    self.$toastr.error("信件内容不能为空!");
                    return ;
                }

                //去掉最后一个&
                if(self.form.pictureUrls && self.form.pictureUrls.endsWith("&")){
                    self.form.pictureUrls = self.form.pictureUrls.substring(0, (self.form.pictureUrls.length - 1) );
                }

                //成功后 继续提交
                self.$validate(true, function () {
                    if (self.$validation.valid) {
                        self.disabled = true;
                        self.$http.post('/api/chairmanMaibox/add', self.form, {emulateJSON: true}).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                //返回首页
                                setTimeout(function () {
                                    window.location.href = "/";
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
                window.history.go(-1);
            },
            checkFromWxOrPc: function () {
                var self = this;
                var ua = window.navigator.userAgent.toLowerCase();
                if(ua.match(/MicroMessenger/i) == 'micromessenger'){
                    self.isPc = false;
                    return false;
                }else{
                    return true;

                }
            },
            loadJsApiConfig: function(){
                var self = this;
                //1.获取js-config中必要数据
                self.$http.get('/wx/jssdk/config?url='+ window.location.href).then(function (res) {
                    if (res.data.code == 1) {
                        console.log(res.data.data);
                        //调用js-sdk
                        wx.config({
                            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                            appId: res.data.data.appId, // 必填，公众号的唯一标识
                            timestamp: res.data.data.timestamp, // 必填，生成签名的时间戳
                            nonceStr: res.data.data.nonceStr, // 必填，生成签名的随机串
                            signature: res.data.data.signature,// 必填，签名，见附录1
                            jsApiList: ['checkJsApi','chooseImage','previewImage','uploadImage',
                                'downloadImage','startRecord','stopRecord','onVoiceRecordEnd',
                                'playVoice','pauseVoice','stopVoice','onVoicePlayEnd',
                                'uploadVoice','downloadVoice'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                        });

                        //成功后
                        wx.ready(function(){
                            // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，
                            // config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，
                            // 则须把相关接口放在ready函数中调用来确保正确执行。
                            // 对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。

                        });
                    }
                });

            },
            //选择图片
            chooseImage: function(){
                var self = this;
                wx.chooseImage({
                    count: 9, // 默认9
                    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
                    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
                    success: function (res) {
                        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                        //保存到本地图片集合
                        self.localImageArr = localIds;
                        var tempArr = localIds.slice(0);

                        //上传到微信服务器
                        self.uploadImage(tempArr);
                    }
                });
            },
            uploadImage: function (tempImageArr) {
                var self = this;
                var localId = tempImageArr.shift();
                wx.uploadImage({
                    localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
                    isShowProgressTips: 1, // 默认为1，显示进度提示
                    success: function (res) {
                        var serverId = res.serverId; // 返回图片的服务器端ID
                        self.form.pictureUrls += serverId + '&';
                        //递归调用 依次上传文件
                        if(tempImageArr.length > 0){
                            self.uploadImage(tempImageArr);
                        }
                    }
                });
            },
            //删除图片,将本地 imageServerArr 清空
            delImage: function () {
                var self = this;
                if(window.confirm("确认删除?")){
                    self.localImageArr = null;
                    self.form.pictureUrls = '';
                }
            },
            //开始录音接口
            startRecord: function () {
                var self = this;
                self.toggle += 2;
                console.log(self.toggle);
                if(self.toggle == 3){
                    self.voiceStatus = "录音中...";
                    self.voiceClass = "btn btn-warning";
                    wx.startRecord();
                }else if(self.toggle == 5){
                    self.stopRecord();
                }else if(self.toggle == 7){
                    //提示 是否重新录音
                    if( window.confirm("是否重新录音?")){
                        //清空voice
                        console.log("kaishi 重新");
                        self.delRecord(false);
                        self.startRecord();
                    }else{
                        //如果是7 减2 防止下次加2 超过7
                        if(self.toggle == 7){
                            self.toggle -= 2;
                        }
                    }
                }
            },
            //停止录音接口
            stopRecord: function () {
                var self = this;
                self.voiceStatus = "录音结束";
                self.voiceClass = "btn btn-success";
                //显示播放按钮
                self.showDelVoice = true;
                wx.stopRecord({
                    success: function (res) {
                        self.localVoice = res.localId;
                        //上传录音到微信服务器
                        self.uploadVoice();
                    }
                });
            },

            //删除录音
            delRecord: function (flag) {
                var self = this;
                var result = false;
                if(flag){
                    if(window.confirm("是否删除录音?")){
                        result = true;
                    }
                }else{
                    self.toggle = 1;
                    result = true;
                }

                if(result){
                    self.form.voiceUrl = '';
                    self.voiceStatus = '开始录音';
                    self.voiceClass = 'btn btn-primary';
                    //录音切换
                    self.toggle = 1;
                    //显示播放按钮
                    self.showDelVoice = false;
                }

            },

            //监听录音自动停止接口
            listenRecord: function () {
                wx.onVoiceRecordEnd({
                    // 录音时间超过一分钟没有停止的时候会执行 complete 回调
                    complete: function (res) {
                        var localId = res.localId;
                        self.localVoice = localId;
                    }
                });
            },
            //播放语音接口
            play: function () {
                var self = this;
                self.playVoice = "播放中...";
                self.playClass = "btn btn-warning";
                self.showStop = true;

                wx.playVoice({
                    localId: self.localVoice // 需要播放的音频的本地ID，由stopRecord接口获得
                });
            },
            //停止播放接口
            stopVoice: function () {
                var self = this;
                self.playVoice = "播放录音";
                self.playClass = "btn btn-primary";
                self.showStop = false;

                wx.stopVoice({
                    localId: self.localVoice // 需要停止的音频的本地ID，由stopRecord接口获得
                });
            },
            //上传语音
            uploadVoice: function () {
                var self = this;
                wx.uploadVoice({
                    localId: self.localVoice, // 需要上传的音频的本地ID，由stopRecord接口获得
                    isShowProgressTips: 1, // 默认为1，显示进度提示
                    success: function (res) {
                        var serverId = res.serverId; // 返回音频的服务器端ID
                        self.form.voiceUrl = serverId;
                    }
                });
            }

        },
        created: function () {
            var self = this;
        },
        ready: function () {
            $(this.$els.time).datetimepicker({
                minView: 2,
                todayBtn: true
            });

            if(this.checkFromWxOrPc()) {
                //UE.getEditor('content');
                //PC端
               this.buildUeditor();

            }else{
                //微信端-- 去加载js配置
                this.isPc = false;
                this.loadJsApiConfig();
            }
        }

    });
})
();