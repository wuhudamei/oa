+(function () {
    $('#orderStandingBook').addClass('active');
    $('#orderStandingBookList').addClass('active');
    new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '台帐列表',
                active: true //激活面包屑的
            }],
            el: '#container',
            mixins: [RocoVueMixins.DataTableMixin],
            $dataTable: null,
            form: {
                keyword: '',
                status: '',
                endDate: '',
                startDate: ''
            },
            _$el: null,
            _$dataTable: null
        },
        methods: {
            activeDatepiker: function () {
                var self = this;
                $(self.$els.endDate).datetimepicker('setStartDate', '');
                $(self.$els.startDate).datetimepicker('setStartDate', '');
            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
                    url: '/api/standBook/getOrderByKeywordAndStatus',
                    method: 'get',
                    dataType: 'json',
                    cache: false, //去缓存
                    sidePagination: 'server', //服务端分页
                    pagination: true, //是否分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', //空数据的默认显示字符
                    striped: true, //斑马线
                    maintainSelected: true, //维护checkbox选项
                    columns: [
                        {
                            field: 'OrderNo',
                            title: '项目编号',
                            align: 'center',
                            formatter: function (value, row, index) {
                                return '<a href="detail?orderno=' + value + '" data-handle="view" data-orderno="' + value + '" >' + value + '</a>';
                            }
                        }, {
                            field: 'CustomerName',
                            title: '客户姓名',
                            align: 'center'
                        }, {
                            field: 'Mobile',
                            title: '联系方式',
                            align: 'center',
                            formatter: function (val) {
                                if (val != null) {
                                    return val.substr(0, 11);
                                }
                            }
                        }, {
                            field: 'SignFinishTime',
                            title: '签约时间',
                            align: 'center',
                            formatter: function (val) {
                                if (val == "315504000000" || val == null) {
                                    return '-';
                                } else {
                                    return moment(val).format('YYYY-MM-DD');
                                }
                            }
                        }, {
                            field: 'AllotState',
                            title: '订单状态',
                            align: 'center',
                            formatter: function (value, row, index) {
                                switch (value) {
                                    case 5:
                                        return '<span style="color:red;">待转单</span>';
                                    case 7:
                                        return '<span style="color:red;">待施工</span>';
                                    case 8:
                                        return '<span style="color:red;">施工中</span>';
                                    case 9:
                                        return '<span style="color:red;">竣工</span>';
                                    default:
                                        return '';
                                }
                            }
                        }]
                });
            }
        },
        created: function () {
        },
        ready: function () {
            this.drawTable();
            this.activeDatepiker();
        }
    });
})();