+(function (RocoUtils) {
    $('#designerMenu').addClass('active');
    $('#stylistBillManager').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '提成账单管理',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            form: {
                keyword: '',
                month: ''
            },
            generateMonth: '',
            fUser: ''
        },
        methods: {
            /**
             * 获取上月日期
             * @returns
             */
            getLastMonth: function () {
                return moment().add(-1, 'month').format('YYYY-MM');
            },
            activeDatepicker: function () {
                var self = this;
                $(self.$els.month).datetimepicker({
                    startView: 3,//启始视图显示年视图
                    minView: "year", //选择日期后，不会再跳转去选择时分秒
                    format: 'yyyy-mm',
                    todayBtn: true,
                    initialDate: moment().add(-1, 'month').format('YYYY-MM')
                });
                $(self.$els.generateMonth).datetimepicker({
                    startView: 3,//启始视图显示年视图
                    minView: "year", //选择日期后，不会再跳转去选择时分秒
                    format: 'yyyy-mm',
                    todayBtn: true,
                    initialDate: moment().add(-1, 'month').format('YYYY-MM')
                });
            },
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/stylist/monthBills',
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
                    columns: [{
                        field: 'title',
                        title: '名称',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            fragment += ('<a data-handle="operate-details" data-id="' + row.id + '">' + value + '</a>');
                            return fragment;
                        }
                    }, {
                        field: 'month',
                        title: '月份',
                        align: 'center'
                    }, {
                        field: 'totalMoney',
                        title: '总额',
                        align: 'center'
                    }, {
                        field: 'createUserName',
                        title: '生成人',
                        align: 'center'
                    }, {
                        field: 'createTime',
                        title: '生成时间',
                        align: 'center'
                    }
                        // , {
                        //     field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        //     title: "操作",
                        //     align: 'center',
                        //     formatter: function (value, row, index) {
                        //         var fragment = '';
                        //         fragment += ('<button data-handle="operate-edit" data-id="' + row.id + '" data-status="' + row.status.status + '" type="button" class="m-r-xs btn btn-xs btn-info">编辑</button>');
                        //         return fragment;
                        //     }
                        // }
                    ]
                });

                self.$dataTable.on('click', '[data-handle="operate-details"]', function (e) {
                    var id = $(this).data('id');
                    window.location.href = '/admin/stylist/bills?id=' + id;
                    e.stopPropagation();
                });
            },
            query: function () {
                this.$dataTable.bootstrapTable('refresh', {pageNumber: 1});
            },
            generateBillHandler: function () {
                var self = this;
                if (RocoUtils.isEmpty(self.generateMonth)) {
                    self.$toastr.warning('请选择生成月份！');
                    return false;
                }
                window.location.href = '/admin/stylist/billConfirm?generateMonth=' + self.generateMonth;
            }
        },
        created: function () {
            this.fUser = window.RocoUser;

        },
        ready: function () {
            this.form.month = this.getLastMonth();
            this.generateMonth = this.getLastMonth();
            this.activeDatepicker();
            this.drawTable();
        }
    });
})
(this.RocoUtils);