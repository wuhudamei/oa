var detailDialog = null;
(function() {
    Vue.filter('detail-show-name', function(id) {
        var name = ''
        _.forEach(detailDialog.costItems, function(item) {
            if (id == item.id) name = item.name
        })
        return name
    })
    // 问询对话框
    detailDialog = new Vue({
        el: '#detailDialog',
        data: {
            budget: null, // 详情数据
            costItems: [], // 科目列表
            approveList:[]
        },
        ready: function(){
            this.getApproveList();
        },
        computed: {
            // 计算预算总额
            budgetTotal: function() {
                var self = this
                var total = 0
                _.forEach(self.budget.yearBudgetDetailList, function(o, ind) {
                    total += Decimal(_.isString(o.january) ? 0 : o.january)
                    .plus(Decimal(_.isString(o.february) ? 0 : o.february))
                    .plus(Decimal(_.isString(o.march) ? 0 : o.march))
                    .plus(Decimal(_.isString(o.april) ? 0 : o.april))
                    .plus(Decimal(_.isString(o.may) ? 0 : o.may))
                    .plus(Decimal(_.isString(o.june) ? 0 : o.june))
                    .plus(Decimal(_.isString(o.july) ? 0 : o.july))
                    .plus(Decimal(_.isString(o.august) ? 0 : o.august))
                    .plus(Decimal(_.isString(o.september) ? 0 : o.september))
                    .plus(Decimal(_.isString(o.october) ? 0 : o.october))
                    .plus(Decimal(_.isString(o.november) ? 0 : o.november))
                    .plus(Decimal(_.isString(o.december) ? 0 : o.december))
                    .toNumber()
                })

                return total;
            }
        },
        methods: {
            handle: function (id) {
                var self = this;
                self.$http.get(ctx + '/api/yearBudget/' + id)
                .then(function(res) {
                    if(res.data.code == "1") {
                        self.budget = res.data.data;
                        console.log(self.budget);
                        $('#detailDialog').modal({
                            width: 1000
                        })
                    }
                })
            },
            show: function (id, parentType) {
                var self = this;
                self.$http.get('/api/dict/getByType?type=2&parentType=' + parentType)
                .then(function (res) {
                    if (res.data.code == 1) {
                        //返回的明细对象
                        self.costItems = res.data.data;
                    self.$nextTick(function() {
                            self.handle(id)
                        })
                    }
                });
            },
            getApproveList: function () {
                var self = this;
                self.$http.get("/api/wfmanager/wfApproveDetail/", {
                    params: {
                        'formId': detailDialog.budget.id,
                        'type': 'YEARBUDGET'+detailDialog.budget.type,
                        'showType': 'personal'
                    }
                }).then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.approveList = res.data;
                    }
                });
            }
        }
    })
})()
