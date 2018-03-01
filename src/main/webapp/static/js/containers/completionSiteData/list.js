var vueIndex = null;
+(function (RocoUtils) {
    $('#completionSiteData').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '竣工工地数据',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            form: {
                keyword: ''
            },
            completionSiteDataList: null,
            webUploaderSub: {
                type: 'sub',
                accept: {
                    title: '文件',
                    extensions: 'xls,xlsx'
                },
                server: ctx + '/api/completionSiteData/importFile?',
                //上传路径
                fileNumLimit: 1,
                fileSizeLimit: 50000 * 1024,
                fileSingleSizeLimit: 5000 * 1024
            }
        },
        events: {
            'webupload-upload-success-sub': function (file, res) {
                if (res.code == '1') {
                    this.$toastr.success(res.message);
                    this.fetchData();
                } else {
                    this.$toastr.error(res.message);
                }
            }
        },
        methods: {
            fetchData: function () {
                var self = this;
                this.$http.get('/api/completionSiteData/list?keyword='+self.form.keyword).then(function (res) {
                    if (res.data.code == 1) {
                        self.completionSiteDataList = res.data.data;
                    }
                })
            },
            viewDetails: function (storeName){
                if(storeName != "全国"){
                    window.location.href = ctx + '/admin/completionSiteData/listDetails?storeName=' + storeName;
                }
            },
            downLoadExcel: function (e) {
                window.location.href = "/static/template/completionSiteData.xls";
            },
            activeDatepicker: function () {
                var self = this;
                $(self.$els.completionYear).datetimepicker({
                    startView: 4,//启始视图显示年视图
                    format: 'yyyy',
                    todayBtn: true,
                    minView: 4, //选择日期后，不会再跳转去选择时分秒
                    clearBtn:true
                })
            },
            orderInsert: function (e) {
                var self = this;
                swal({
                        title: '同步全国竣工工地数据',
                        text: '确定同步数据吗？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    },
                    function () {
                        self.$http.get('/api/completionSiteData/orderInsert').then(function (res) {
                            if (res.data.code == 1) {
                                Vue.toastr.success(res.data.message);
                                this.fetchData();
                            } else {
                                Vue.toastr.error(res.data.message);
                            }

                        }).catch(function () {

                        }).finally(function () {
                            swal.close();
                        });
                    });

                e.stopPropagation();
            }
        },
        components: {
            'web-uploader': RocoVueComponents.WebUploaderComponent
        },
        created: function () {

        },
        ready: function () {
            this.fetchData();
            this.activeDatepicker();
        }
    });
})
(this.RocoUtils);