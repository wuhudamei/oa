var vueIndex = null;
+(function (RocoUtils) {
    $('#regulationMenu').addClass('active');
    $('#regulations').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            is_weixin:false,
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '规章制度',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            user: '',
            // 查询表单
            form: {
                keyword: '',
                type: ""
            },
            types: []
        },
        methods: {
            insertSign:function(){
                var self = this;
                self.$http.get('/api/rest/salarydetail/insertSign').then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                        this.$dataTable.bootstrapTable('selectPage', 1);
                    }
                });
            },

            isWeixin:function(){
                var self = this;
                var ua = navigator.userAgent.toLowerCase();
                if(ua.match(/MicroMessenger/i)=="micromessenger") {
                    self.is_weixin=true;
                } else {
                    self.is_weixin=false;
                }
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/regulations',
                    method: 'get',
                    dataType: 'json',
                    cache: false, //去缓存
                    pagination: true, //是否分页
                    sidePagination: 'server', //服务端分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', //空数据的默认显示字符
                    striped: true, //斑马线
                    maintainSelected: true, //维护checkbox选项
                    sortName: 'id', //默认排序列名
                    sortOrder: 'desc', //默认排序方式
                    columns: [{
                        field: 'title',
                        title: '规章制度名称',
                        align: 'center',
                        formatter: function (value, row, index) {
                            return '<a data-handle="operate-detail" data-type="' + row.type + '" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                        }
                    }, {
                        field: 'orgName',
                        title: '发布人所在部门',
                        align: 'center',
                    }, {
                        field: 'fileUrl',
                        title: '附件路径',
                        align: 'center',
                        visible: false
                    },
                    {
                        field: 'typeName',
                        title: '规章制度类型',
                        align: 'center'
                    }, {
                        field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        visible:!(navigator.userAgent.toLowerCase().match(/MicroMessenger/i) == 'micromessenger'),
                        formatter: function (value, row, index) {
                            if (row.createUser == window.RocoUser.userId) {
                                var fragment = '';
                                if (RocoUtils.hasPermission('regulation:edit'))
                                    fragment += ('<button data-handle="operate-edit" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-default">编辑</button>');
                                if (row.fileUrl != '' && row.fileUrl != null && row.fileUrl != undefined && !self.is_weixin) {
                                    if (RocoUtils.hasPermission('regulation:att:download'))
                                        fragment += ('<button data-handle="operate-download" data-id="' + row.id + '" data-index="' + index + '" type="button" class="m-r-xs btn btn-xs btn-default">附件下载</button>');
                                }
                                if (RocoUtils.hasPermission('regulation:delete'))
                                    fragment += ('<button data-handle="operate-delete" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-danger">删除</button>');
                                return fragment;
                            }
                        }
                    }]
                });

                //编辑规章制度
                self.$dataTable.on('click', '[data-handle="operate-edit"]', function (e) {
                    var id = $(this).data('id');
                    window.location.href = '/admin/regulation/update?&id=' + id;
                    e.stopPropagation();
                });
                //附件下载
                self.$dataTable.on('click', '[data-handle="operate-download"]', function (e) {
                    var id = $(this).data('id');
                    var index = $(this).data('index');
                    var rowData = self.$dataTable.bootstrapTable('getData');
                    var curData = rowData[index];
                    window.location.href =curData.fileUrl ;
                    e.stopPropagation();
                });
                //规章制度详情
                self.$dataTable.on('click', '[data-handle="operate-detail"]', function (e) {
                    var id = $(this).data('id');
                    window.location.href = '/admin/regulation/detail?id='+id;
                    // self.$http.get('/api/regulations/' + id + '/get').then(function (res) {
                    //     if (res.data.code == 1) {
                    //         var budget = res.data.data;
                    //         this.showDetail(budget);
                    //     }
                    // });
                });
                
                /** 删除规章制度 **/
                self.$dataTable.on('click', '[data-handle="operate-delete"]', function (e) {
                    var id = $(this).data('id');
                    swal({
                        title: '删除规章制度',
                        text: '确定删除该规章制度么？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.get('/api/regulations/' + id + '/del').then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                self.$dataTable.bootstrapTable('refresh');
                            }
                        }).catch(function () {

                        }).finally(function () {
                            swal.close();
                        });
                    });
                    e.stopPropagation();
                });
            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
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
            }
        },
        created: function () {
            this.user = window.RocoUser;
            this.isWeixin();
        },
        ready: function () {
            this.drawTable();
            this.getTypes();
        }
    });
})
(this.RocoUtils);