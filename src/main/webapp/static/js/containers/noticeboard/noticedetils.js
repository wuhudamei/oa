var tt = null;

+(function (window,RocoUtils) {
    $('#noticeList').addClass('active');
    $('#contentMenu').addClass('active');
    tt = new Vue({
        el: '#modal',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            },{
                path: '/',
                name: '公告通知',
                active: true //激活面包屑的
            }],
            notice:null
        },
        methods: {
            goBack:function () {
                window.location.href= ctx + "/admin/noticeboard/noticeboard";
            }
        },
        created: function () {
        },
        ready: function () {
            var self = this;
            var id = this.$parseQueryString()['id'];
            self.$http.get('/noticeboard/findNoticeById/' + id).then(function (response) {
                var res = response.data;
                if (res.code == '1') {
                    self.notice = res.data;
                } else {
                    self.$toastr.error(res.message);
                }
            });
        }
    });

})(this.window,RocoUtils);