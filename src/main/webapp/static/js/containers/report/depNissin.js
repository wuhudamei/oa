var vueIndex = null;
+(function (RocoUtils) {
    $('#reportManager').addClass('active');
    $('#depNissin').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '分店日清报表',
                active: true //激活面包屑的
            }],
            form: {
                date: '', //日期
                companyId:'',//查询的公司
                isDep:true,
            },
            dataTable: null,
            companys:null
        },
        ready: function() {
            this.activeDatePicker();
            this.getCompany();
            this.drawTable()
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/report/nissin/group',
                    method: 'get',
                    dataType: 'json',
                    //showFooter:true,
                    cache: false, //去缓存
                    pagination: false, //是否分页
                    sidePagination: 'server', //服务端分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '/', //空数据的默认显示字符
                    striped: true, //斑马线
                    sortName: 'id', //默认排序列名
                    sortOrder: 'desc', //默认排序方式
                    columns: [
                        [
                            {
                                "title": "客户管理部全国日清报表",
                                "halign":"center",
                                "align":"center",
                                "colspan": 29
                            }
                        ],
                        [
                            {
                                title: "序号",
                                align:"center",
                                valign:"middle",
                                field:'',
                                formatter: function (value, row, index) {
                                    return index + 1;
                                },
                                colspan: 1,
                                rowspan: 3
                            },
                            {
                                title: "分公司",
                                align:"center",
                                field:'orgName',
                                valign:"middle",
                                colspan: 1,
                                rowspan: 3
                            },
                            {
                                title: "综合排名",
                                align:"center",
                                valign:"middle",
                                field:'sort',
                                colspan: 1,
                                rowspan: 3
                            },
                            {
                                title: "当天产生的工单数量",
                                align:"center",
                                field:'daySize',
                                valign:"middle",
                                colspan: 1,
                                rowspan: 3
                            },
                            {
                                title: "当天系统工单状态",
                                align:"center",
                                colspan: 5,
                                rowspan: 1
                            },
                            {
                                title: "当月客诉处理结果汇总",
                                align:"center",
                                colspan: 9,
                                rowspan: 1
                            },
                            {
                                title: "集团客管部回访结果",
                                align:"center",
                                colspan: 7,
                                rowspan: 1
                            },
                            {
                                title: "回访未执行",
                                align:"center",
                                colspan: 3,
                                rowspan: 1
                            },
                            {
                                title: "综合",
                                align:"center",
                                colspan: 1,
                                rowspan: 3,
                                field:'score'
                            }
                        ],
                        [
                            {
                                title: "客管部",
                                align:"center",
                                colspan: 1,
                                rowspan: 1
                            },
                            {
                                title: "责任部门",
                                align:"center",
                                colspan: 4,
                                rowspan: 1
                            },
                            {
                                title: "已到期未完成",
                                align:"center",
                                colspan: 4,
                                rowspan: 1
                            },
                            {
                                title: "当月总解决率",
                                align:"center",
                                colspan: 5,
                                rowspan: 1,
                            },
                            {
                                title: "成功回访满意率",
                                align:"center",
                                colspan: 4,
                                rowspan: 1
                            },
                            {
                                title: "回访失败",
                                align:"center",
                                colspan: 2,
                                rowspan: 1,
                            },
                            {
                                title: "未回访",
                                align:"center",
                                colspan: 1,
                                rowspan: 2,
                                field:'visitCompleted'
                            },
                            {
                                title: "数量",
                                align:"center",
                                colspan: 1,
                                rowspan: 2,
                                field:'visitUnsatisfiedComplain'
                            },
                            {
                                title: "未执行率",
                                align:"center",
                                colspan: 1,
                                rowspan: 2,
                                field:'monthUnsatisfiedComplainRate',
                                formatter: function (value, row, index) {
                                    return value+'%';
                                },
                            },
                            {
                                title: "得分5",
                                align:"center",
                                colspan: 1,
                                rowspan: 2,
                                field:'score5'
                            }
                        ],
                        [
                            {
                                title: "待分配",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'dayAssign'
                            },
                            {
                                title: "申诉无效",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'dayRefusedAgain'
                            },
                            {
                                title: "未派单",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'dayCreate'
                            },
                            {
                                title: "已接收",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'dayReceive'
                            },
                            {
                                title: "得分1",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'score1'
                            },
                            {
                                title: "已到期",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'monthMaturity'
                            },
                            {
                                title: "未解决",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'monthNoProcessing'
                            },
                            {
                                title: "解决率",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'monthProcessingRate',
                                formatter: function (value, row, index) {
                                    return value+'%';
                                },
                            },
                            {
                                title: "得分2",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'score2'
                            },
                            {
                                title: "当月量",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'monthSize'
                            },
                            {
                                title: "解决量",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'monthSolve'
                            },
                            {
                                title: "未解决",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'monthNoSolve'
                            },
                            {
                                title: "总解决率",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'monthSolveRate',
                                formatter: function (value, row, index) {
                                    return value+'%';
                                }
                            },
                            {
                                title: "得分3",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'score3'
                            },
                            {
                                title: "满意",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'visitSatisfied'
                            },
                            {
                                title: "一般满意",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'visitCommonly'
                            },
                            {
                                title: "不满意",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'visitUnsatisfied'
                            },
                            {
                                title: "得分4",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'score4'
                            },
                            {
                                title: "暂无评价",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'visitInvalid'
                            },
                            {
                                title: "拒绝回访",
                                align:"center",
                                colspan: 1,
                                rowspan: 1,
                                field:'visitFail'
                            },
                        ]
                        ]
                });
            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
                this.$dataTable.bootstrapTable('refresh');
            }, 
            // 日历初始化
            activeDatePicker: function () {
                var self = this;
                $('#date', self._$el).datetimepicker({});
                var date=new Date();
                $('#date').datetimepicker( 'setDate', date );
                self.form.date=$('#date').val();
            },
            //获取下拉框
            getCompany:function () {
                var  self=this;
                self.$http.get('/api/report/nissin/depList').then(function (res) {
                    if (res.data.code == 1) {
                        self.companys = res.data.data;
                    }
                });

            }

        }
    })
})(this.RocoUtils)