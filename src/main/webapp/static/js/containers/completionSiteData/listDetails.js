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
                name: '竣工工地数据列表详情',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            form: {
                keyword: ''
            },
            storeName: ''
        },
        methods: {
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/completionSiteData/listDetails?storeName=' + self.storeName,
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
                        title: '序号',//标题  可不加  
                        formatter: function (value, row, index) {
                            return index+1;
                        }
                    }, {
                        field: 'projectCode',
                        title: '项目编号',
                        align: 'center',
                    }, {
                        field: 'customerName',
                        title: '客户姓名',
                        align: 'center',
                    }, {
                        field: 'customerPhone',
                        title: '客户电话',
                        align: 'center'
                    }, {
                        field: 'projectAddress',
                        title: "工程地址",
                        align: 'center',
                    },{
                        field: 'managerName',
                        title: '项目经理姓名',
                        align: 'center',
                    }, {
                        field: 'managerPhone',
                        title: '项目经理电话',
                        align: 'center'
                    },{
                        field: 'designerName',
                        title: '设计师',
                        align: 'center',
                    }, {
                        field: 'designerPhone',
                        title: '设计师电话',
                        align: 'center'
                    },{
                        field: 'inspectorName',
                        title: '质检员',
                        align: 'center',
                    }, {
                        field: 'inspectorPhone',
                        title: '质检员电话',
                        align: 'center'
                    },{
                        field: 'contractStartDate',
                        title: '合同签订开工日期',
                        align: 'center',
                    }, {
                        field: 'contractFinishDate',
                        title: '合同签订竣工日期',
                        align: 'center'
                    },{
                        field: 'actualStartDate',
                        title: '实际开工日期',
                        align: 'center',
                    }, {
                        field: 'actualFinishDate',
                        title: '实际竣工日期',
                        align: 'center'
                    },{
                        field: 'contractTotalAmount',
                        title: '合同总金额(元)',
                        align: 'center',
                    }, {
                        field: 'alterationTotalAmount',
                        title: '拆改总金额(元)',
                        align: 'center'
                    },{
                        field: 'changeTotalAmount',
                        title: '变更总金额(元)',
                        align: 'center',
                    }, {
                        field: 'paidTotalAmount',
                        title: '实收总金额(元)',
                        align: 'center'
                    }]
                });
            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            loadParms: function () {
                var self = this;
                self.storeName = RocoUtils.parseQueryString()['storeName'];
            }
        },
        created: function () {
            this.loadParms();
        },
        ready: function () {
            this.drawTable();
        }
    });
})
(this.RocoUtils);