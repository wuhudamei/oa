var tt = null;
+(function (window, RocoUtils) {
    $('#chairmanMailbox').addClass('active');
    //$('#addMail').addClass('active');
    tt = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            },{
                path: '/',
                name: '总经理信箱'
            }, {
                path: '/',
                name: '信件列表',
                active: true // 激活面包屑的
            }],
            $dataTable: null,
            form: {
                title: '',
                readStatus: false,
                importantDegree: ''
            },
        },
        methods: {
            query: function () {
                this.$dataTable.bootstrapTable('refresh', {pageNumber: 1});
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.datatable).bootstrapTable({
                    url: '/api/chairmanMaibox/list',
                    method: 'get',
                    dataType: 'json',
                    cache: false, // 去缓存
                    pagination: true, // 是否分页
                    sidePagination: 'server', // 服务端分页
                    content: '',
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({readStatus:self.form.readStatus}, params, self.form);
                    },
                    createUserName: '',
                    mobileResponsive: true,
                    undefinedText: '-', // 空数据的默认显示字符
                    striped: true, // 斑马线
                    sortName: 'id', // 默认排序列名
                    sortOrder: 'desc', // 默认排序方式
                    columns: [
                        {
                            field: 'title',
                            title: '标题',
                            width: '15%',
                            align: 'center'/*,
                            formatter: function (value, row, index) {
                                return '<a href="javascript:void(0)" data-handle="view" data-id="' + row.id + '">' + value + '</a>'
                            }*/
                        },
                        {
                            field: 'content',
                            title: '内容',
                            width: '30%',
                            align: 'center',
                            visible: false,
                            formatter: function (value, row, index) {
                                var str = value.substring(0, 10);
                                if (str.indexOf("<") != -1 || str.indexOf("/") != -1) {
                                    str = "(内容包含图片或网页格式，请点击详情查看）";
                                } else {
                                    str = value.substring(0, 15) + "...";
                                }
                                return '<a href="javascript:void(0)" data-handle="view" data-id="' + row.id + '">' + str + '</a>'
                            }
                        },
                        {
                            field: 'importantDegree',
                            title: '重要程度',
                            width: '10%',
                            align: 'center',
                            visible: self.form.readStatus,// 当已审阅列表时显示
                            formatter: function (value, row, index) {
                                var label = '';
                                switch (value) {
                                    case 1:
                                        return '普通';
                                        break;
                                    case 2:
                                        label = '<span style="color:blue;">重要</span>';
                                        break;
                                    case 3:
                                        label = '<span style="color:red;">紧急</span>';
                                        break;
                                    default:
                                        return ;
                                        break;
                                }
                                return label;
                            }
                        },
                        {
                            field: 'companyName',
                            title: '发件人公司',
                            width: '10%',
                            align: 'center'
                        },
                        {
                            field: 'departmentName',
                            title: '发件人部门',
                            width: '10%',
                            align: 'center'
                        },
                        {
                            field: 'createUserName',
                            title: '发件人',
                            width: '10%',
                            align: 'center',
                            formatter: function (value,row) {
                                if (!value || row.anonymous) {
                                    //匿名时,不显示发件人
                                    return "***";
                                }else{
                                    return value;
                                }
                            }
                        },
                        {
                            field: 'createTime',
                            title: '发件时间',
                            width: '10%',
                            align: 'center'
                        },
                        {
                            field: 'id',
                            title: "操作",
                            width: '10%',
                            align: 'center',
                            //visible:!(navigator.userAgent.toLowerCase().match(/MicroMessenger/i) == 'micromessenger'),
                            formatter: function (value, row, index) {
                                // 根据当前登录用户的id显示操作
                                var fragment = '';
                                if(row.readStatus){
                                    //未阅读
                                    fragment = '<button   data-handle="view" data-id="' + row.id + '" data-readstatus="' + row.readStatus + '" type="button" class="btn btn-xs btn-danger">审阅</button>&nbsp;';
                                }else{
                                    fragment = '<button   data-handle="view" data-id="' + row.id + '" data-readstatus="' + row.readStatus + '" type="button" class="btn btn-xs btn-primary">审阅</button>&nbsp;';
                                }
                                return fragment;
                            }
                        }

                    ]
                });

                // 查看事件
                self.$dataTable.on('click', '[data-handle="view"]', function (e) {
                    var id = $(this).data("id");
                    var readStatus = $(this).data("readstatus");
                    if(!readStatus){
                        var data = {
                            id: id,
                            readStatus: true
                        }
                        //变为已阅读
                        self.$http.post('/api/chairmanMaibox/update',data,{emulateJSON: true}).then(function (res) {});
                    }
                    //跳转详情页
                    window.location.href = ctx + '/admin/chairmanMailbox/detail?id=' + id;

                    // 查询公告详情并以弹窗显示
                    // self.$http.get('/noticeboard/findNoticeById/' +
					// id).then(function (response) {
                    // var res = response.data;
                    // if (res.code == '1') {
                    // var _$modal = $('#modal').clone();
                    // var $modal = _$modal.modal({
                    // height: 600,
                    // maxHeight: 630,
                    // s // backdrop: 'static',
                    // // keyboard: false
                    // });
                    // modal($modal, res.data);
                    // } else {
                    // self.$toastr.error(res.message);
                    // }
                    // });
                    e.stopPropagation();
                });

                /*// 修改事件
                self.$dataTable.on('click', '[data-handle="edit"]', function (e) {
                    var id = $(this).data('id');
                    window.location.href = ctx + '/admin/noticeboardedit?id=' + id;
                    e.stopPropagation();
                });


                // 删除事件
                self.$dataTable.on('click', '[data-handle="delete"]', function (e) {
                    var id = $(this).data('id');
                    swal({
                        title: '删除公告',
                        text: '确定删除该公告么？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.delete('/noticeboard/delete/' + id).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                self.$dataTable.bootstrapTable('refresh');
                            }
                        }).catch(function () {
                            self.$toastr.error('系统异常');
                        }).finally(function () {
                            swal.close();
                        });
                    });
                    e.stopPropagation();
                });*/
            },
            //加载 状态
            loadParams : function(){
                var self = this;
                var readStatus = RocoUtils.parseQueryString()["readStatus"];
                if(readStatus == 'N'){
                    self.form.readStatus = false;
                    $('#unReadList').addClass('active');
                    self.breadcrumbs[2].name = "未审阅信件列表"
                }else if(readStatus == 'Y'){
                    self.form.readStatus = true;
                    $('#readList').addClass('active');
                    self.breadcrumbs[2].name = "已审阅信件列表"
                }
                //画表
                this.drawTable();
            },

},

        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.loadParams();
        }
    });
    // 公告详情的弹窗
    // function modal($el, model) {
    // // 获取 node
    // var el = $el.get(0);
    // // 创建 Vue 对象编译节点
    // var vueModal = new Vue({
    // el: el,
    // // 模式窗体必须引用 ModalMixin
    // mixins: [RocoVueMixins.ModalMixin],
    // $modal: $el, //模式窗体 jQuery 对象
    // data: {
    // //控制 按钮是否可点击
    // disabled: false,
    // //模型复制给对应的key
    // noticeboard: model,
    // submitBtnClick: false,
    // // username : window.RocoUser.name
    // },
    //
    // });
    // // 创建的Vue对象应该被返回
    // return vueModal;
    // }

})(this.window, RocoUtils);