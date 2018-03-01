var tt = null;

+(function (window,RocoUtils) {
    $('#chairmanMailbox').addClass('active');
    //$('#contentMenu').addClass('active');
    tt = new Vue({
        el: '#modal',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            },{
                path: '/',
                name: '审阅',
                active: true //激活面包屑的
            }],
            mailbox: null,
            disabled: false,
            readStatus: false,
            //图片数组
            pictureUrlArr: null
        },
        methods: {
            goBack:function () {
                //window.location.href= ctx + "/admin/chairmanMailbox/list";
                window.history.go(-1);
            },
            submit: function () {
                var self = this;
                self.disabled = true;
                self.$http.post('/api/chairmanMaibox/update',self.mailbox,{emulateJSON: true}).then(function (res) {
                    if(res.data.code == 1){
                        self.$toastr.success("操作成功!");
                        setTimeout(function () {
                            window.history.go(-1);
                        },1500)
                    }else{
                        self.$toastr.error(res.message);
                        self.disabled = false;
                    }
                });
            },
            loadFunction: function(){
                var self = this;
                var id = this.$parseQueryString()['id'];
                self.$http.get('/api/chairmanMaibox/findMailById/' + id).then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.mailbox = res.data;
                        //将图片pictureUrls 变成图片数组
                        if(self.mailbox.pictureUrls){
                            self.pictureUrlArr = self.mailbox.pictureUrls.split("&");
                        }

                    } else {
                        self.$toastr.error(res.message);
                    }
                });
            }
        },
        created: function () {
        },
        ready: function () {
           this.loadFunction();
        }
    });

})(this.window,RocoUtils);