+(function () {
    var login = new Vue({
        el: '#loginCont',
        http: {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        },
        data: {
            company:'',
            name:'',
            account:'',
            disable:false
        },
        methods: {

            findUser:function () {
                var self = this;
                self.company = RocoUser.company;
                self.name = RocoUser.name;
                self.account = RocoUser.account;
            },
            removebinding: function () {
                var self = this;
                self.disable = true;
                swal({
                    title: "是否解除绑定?",
                    type: "warning",
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    showCancelButton: true,
                    showConfirmButton: true,
                    showLoaderOnConfirm: true,
                    confirmButtonColor: '#ed5565',
                    closeOnConfirm: false
                }, function () {
                    self.$http.post(ctx + '/wx/removeBinding/removeUser').then(function (response) {
                        var res = response.data;
                        if (res.code == 1) {
                            self.$toastr.success("解绑成功！");
                            swal.close();
                        } else {
                            self.$toastr.error("解绑失败，请联系管理员！");
                        }
                    })
                })
            }
        },
        created: function () {
            this.findUser();
        },
        ready: function () {
        }
    });
})();