var tt = null;
+(function (window, RocoUtils) {
    $('#noticeList').addClass('active');
    $('#contentMenu').addClass('active');
    tt = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '公告通知',
                active: true // 激活面包屑的
            }],
            $dataTable: null,
            form: {
                title: '',
                status: ''
            },
            userid: null,
            items: null,
            isWeixin:null
        },
        methods: {
            query: function () {
                this.$dataTable.bootstrapTable('refresh', {pageNumber: 1});
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.datatable).bootstrapTable({
                    url: '/noticeboard/list',
                    method: 'get',
                    dataType: 'json',
                    cache: false, // 去缓存
                    pagination: true, // 是否分页
                    sidePagination: 'server', // 服务端分页
                    content: '',
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({}, params, self.form);
                    },
                    userid: null,
                    createUserName: '',
                    mobileResponsive: true,
                    undefinedText: '-', // 空数据的默认显示字符
                    striped: true, // 斑马线
                    sortName: 'id', // 默认排序列名
                    sortOrder: 'desc', // 默认排序方式
                    columns: [
                        {
                            field: 'title',
                            title: '公告标题',
                            width: '30%',
                            align: 'center',
                            formatter: function (value, row, index) {
                                return '<a href="javascript:void(0)" data-handle="view" data-id="' + row.id + '">' + value + '</a>'
                            }
                        },
                        {
                            field: 'content',
                            title: '公告内容',
                            width: '30%',
                            align: 'center',
                            visible: false,// 不显示该列
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
                            field: 'noticeStatus',
                            title: '状态',
                            width: '10%',
                            align: 'center',
                            formatter: function (value, row, index) {
                                var label = '';
                                switch (value) {
                                    case '1':
                                        return '普通';
                                        break;
                                    case '2':
                                        label = '<span style="color:blue;">重要</span>';
                                        break;
                                    case '3':
                                        label = '<span style="color:red;">紧急</span>';
                                        break;
                                    default:
                                        return '';
                                        break;
                                }
                                return label;
                            }
                        },
                        {
                            field: 'createTime',
                            title: '发布时间',
                            width: '10%',
                            align: 'center',
                            formatter: function (data) {
                                if (data != null) {
                                    return moment(data).format('YYYY-MM-DD HH:mm:ss');
                                }
                            }
                        },
                        {
                            field: 'createName',
                            title: '发布人',
                            width: '10%',
                            align: 'center',
                            visible:!(navigator.userAgent.toLowerCase().match(/MicroMessenger/i) == 'micromessenger')
                        },
                        {
                            field: 'id',
                            title: "操作",
                            width: '10%',
                            align: 'center',
                            visible:!(navigator.userAgent.toLowerCase().match(/MicroMessenger/i) == 'micromessenger'),
                            formatter: function (value, row, index) {
                                // 根据当前登录用户的id显示操作
                                if (row.createNameId == window.RocoUser.userId) {
                                    var fragment = '';
                                    if (RocoUtils.hasPermission('content:notice:edit'))
                                        fragment += '<button   data-handle="edit" data-id="' + row.id + '" type="button" class="btn btn-xs btn-default">编辑</button>&nbsp;';
                                    if (RocoUtils.hasPermission('content:notice:delete'))
                                        fragment += '<button   data-handle="delete" data-id="' + row.id + '" type="button" class="btn btn-xs btn-danger">删除</button>&nbsp;';
                                    return fragment;
                                }
                            }
                        }

                    ]
                });

                // 查看事件
                self.$dataTable.on('click', '[data-handle="view"]', function (e) {
                    var id = $(this).data("id");
                    window.location.href = ctx + '/admin/noticedetils?id=' + id;
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

                // 修改事件
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
                });
            },
        },

        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.drawTable();
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