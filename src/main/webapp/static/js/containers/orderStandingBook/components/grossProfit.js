var GrossProfitComponent = Vue.extend({
    template: '#grossProfitTmpl',
    data: function () {
        return {
            orderNo: null,
            loaded: false,
            //毛利数据
            grossProfit: null,
            //缴费
            payList: null,
            //基装变更合计
            totalChangeMoney: null,
            //基装变更
            installBaseChanges: null,
            //拆改费
            modifyCost: 0,
            //基装成本
            mealCost: 0,
            //任务包详情
            packageInfo: null,
            //计价面积
            valuationArea: 0,
            //套餐价格
            unitprice: 0,
            //套餐报价
            selectedPackageAmount: 0,
            //原始合同金额
            orderAmount: 0,
            //最终合同金额
            payedAll: 0,
            //实缴金额
            amountPaid: 0,
            total: 0,
            materialRows: null,
            //项目经理提成
            pmSettleInfo: null,
            //材料成本
            selectMaterialStandBookDetails: null,
            //基装主材
            installBasePrincipal: []
        }
    },
    props: ['msg'],
    ready: function () {

    },
    created: function () {
        this.orderNo = RocoUtils.parseQueryString()['orderno'];
    },
    filters: {
        goDate: function (el) {
            if (el) {
                return moment(el).format('YYYY-MM-DD HH:mm:ss');
            }
            else {
                return '-';
            }
        }
    },
    methods: {
        //交费总金额
        getPayedAll: function () {
            this.payedAll = this.orderAmount + this.changeMoney;
            return this.payedAll;
        },
        //实缴金额
        getAmountPaid: function () {
            var item = this.payList;
            this.amountPaid = item.firstAmount + item.mediumAmount + item.finalAmount + item.finishChangeAmount + item.modifyCost;
            return this.amountPaid;
        },
        //税金
        getTax: function () {
            var item = this.grossProfit;
            var tax = 0;
            for (var i = 0; i < item.length; i++) {
                if (item[i].pdName == '税金' || item[i].pdName == '新房税金' || item[i].pdName == '优惠税金') {
                    tax += item[i].pdTotal;
                }
            }
            return tax;
        },
        //管理费
        getManagerCost: function () {
            var item = this.grossProfit;
            var managerCost = 0;
            for (var i = 0; i < item.length; i++) {
                if (item[i].pdName == '管理费' || item[i].pdName == '管理费（新房）' || item[i].pdName == '优惠管理费') {
                    managerCost += item[i].pdTotal;
                }
            }
            return managerCost;
        },
        //远程费
        getRemoteCost: function () {
            var item = this.grossProfit;
            var remoteCost = 0;
            for (var i = 0; i < item.length; i++) {
                if (item[i].pdName == '远程费用' || item[i].pdName == '远程费用' || item[i].pdName == '远程费用（10公里内）' || item[i].pdName == '远程费用（25公里以内）' || item[i].pdName == '远程费用（40公里以内）') {
                    remoteCost += item[i].pdTotal;
                }
            }
            return remoteCost;
        },
        //其他费用
        getOtherCost: function () {
            var item = this.grossProfit;
            var otherCost = 0;
            for (var i = 0; i < item.length; i++) {
                if (item[i].itemType == 'addotherquota') {
                    if (item[i].pdName != '税金' && item[i].pdName != '新房税金' && item[i].pdName != '优惠税金') {
                        if (item[i].pdName != '远程费用' && item[i].pdName != '远程费用' && item[i].pdName != '远程费用（10公里内）' && item[i].pdName != '远程费用（25公里以内）' && item[i].pdName != '远程费用（40公里以内）') {
                            if (item[i].pdName != '管理费' && item[i].pdName != '管理费（新房）' && item[i].pdName != '优惠管理费') {
                                otherCost += item[i].pdTotal;
                            }
                        }
                    }
                }
            }
            return otherCost;
        },
        //其他优惠
        getOtherDiscountCost: function () {
            var item = this.grossProfit;
            var otherDiscountCost = 0;
            for (var i = 0; i < item.length; i++) {
                if (item[i].itemType == 'activity') {
                    if (item[i].pdName != '税金' && item[i].pdName != '新房税金' && item[i].pdName != '优惠税金') {
                        if (item[i].pdName != '管理费' && item[i].pdName != '管理费（新房）' && item[i].pdName != '优惠管理费') {
                            otherDiscountCost += item[i].pdTotal;
                        }
                    }
                }
            }
            return otherDiscountCost;
        },
        //间接费用
        getIndirectCost: function () {
            var indirectCost = 0;
            indirectCost = this.getTax() + this.getManagerCost() + this.getRemoteCost() + this.getOtherCost() + this.getOtherDiscountCost();
            return indirectCost;
        },
        //基装增项报价
        getIncrease: function () {
            var item = this.grossProfit;
            var increase = 0;
            for (var i = 0; i < item.length; i++) {
                if (item[i].itemType == 'addquota' || item[i].itemType == 'addquotaservice') {
                    increase += item[i].pdTotal;
                }
            }
            return increase;
        },
        //基装减项
        getLessenquota: function () {
            var item = this.grossProfit;
            var lessenquota = 0;
            for (var i = 0; i < item.length; i++) {
                if (item[i].itemType == 'lessenquota') {
                    lessenquota += item[i].pdTotal;
                }
            }
            if (lessenquota == 0) {
                return 0.00;
            } else {
                return -lessenquota.toFixed(2);
            }
        },
        //主材增项、升级项报价
        getAddUpgrade: function () {
            var item = this.grossProfit;
            var addUpgrade = 0;
            for (var i = 0; i < item.length; i++) {
                if (item[i].itemType == 'add' || item[i].itemType == 'upgrade') {
                    addUpgrade += item[i].pdTotal;
                }
            }
            return addUpgrade;
        },
        //主材减项报价
        getLessen: function () {
            var item = this.grossProfit;
            var lessen = 0;
            for (var i = 0; i < item.length; i++) {
                if (item[i].itemType == 'lessen') {
                    lessen += item[i].pdTotal;
                }
            }
            if (lessen == 0) {
                return 0.00;
            } else {
                return -lessen.toFixed(2);
            }
        },
        //直接费用
        getDirectCost: function () {
            var direct = 0;
            direct = this.modifyCost + this.selectedPackageAmount + this.getIncrease() + this.getLessenquota() + this.getAddUpgrade() + this.getLessen() + this.getInstallChange();
            return direct;
        },
        //基装成本
        getBaseCost: function () {
            return this.mealCost + this.getInstallBaseCost();
        },
        //主材成本
        getMaterialCost: function () {
            var item = this.grossProfit;
            var materialCost = 0;
            for (var i = 0; i < item.length; i++) {
                if (item[i].itemType == 'item' || item[i].itemType == 'add' || item[i].itemType == 'upgrade') {
                    materialCost += item[i].dosageArea * item[i].storePrice;
                }
            }
            return materialCost;
        },
        //基装主材成本
        getInstallBaseCost: function () {
            var sum = 0;
            if (this.installBasePrincipal == null || this.installBasePrincipal == undefined) {
                return sum;
            } else {
                for (var i = 0, len = this.installBasePrincipal.length; i < len; i++) {
                    if (!isNaN(this.installBasePrincipal[i].workerPrice) && !isNaN(this.installBasePrincipal[i].count)) {
                        sum += this.installBasePrincipal[i].workerPrice * this.installBasePrincipal[i].count;
                    }
                }
                return sum;
            }
        },
        //项目经理提成
        getSums: function () {
            var sum = 0;
            if (this.pmSettleInfo == null || this.pmSettleInfo == undefined) {
                return sum;
            } else {
                for (var i = 0, len = this.pmSettleInfo.length; i < len; i++) {
                    if (!isNaN(this.pmSettleInfo[i].settleAmount)) {
                        sum += this.pmSettleInfo[i].settleAmount;
                    }
                }
                return sum;
            }
        },
        //直接成本
        getDirect: function () {
            var cost = 0;
            cost = this.getMaterialCost() + this.mealCost;
            return cost;
        },
        //税金提成
        getTaxDeduction: function () {
            var taxDeduction = 0;
            taxDeduction = this.getTax() * (0.1465);
            return taxDeduction;
        },
        //管理费提成
        getManagerCostDeduction: function () {
            var managerCostDeduction = 0;
            managerCostDeduction = this.getManagerCost() * (0.4);
            return managerCostDeduction;
        },
        //远程费提成
        getRemoteCostDeduction: function () {
            var remoteCostDeduction = 0;
            remoteCostDeduction = this.getRemoteCost() * (0.1);
            return remoteCostDeduction;
        },
        //拆改费提成
        getModifyCostDeduction: function () {
            var modifyCostDeduction = 0;
            modifyCostDeduction = this.modifyCost * (0.0427);
            return modifyCostDeduction;
        },
        //设计师提成
        getDesignerDeduction: function () {
            var designerDeduction = 0;
            designerDeduction = this.getDirectCost() * (0.0424) + this.getTaxDeduction()
                + this.getManagerCostDeduction() + this.getRemoteCostDeduction() + this.getModifyCostDeduction();
            return designerDeduction;
        },
        //设计部长提成
        getDesignCommission: function () {
            var designCommission = 0;
            designCommission = this.getDesignerDeduction() * (0.1);
            return designCommission;
        },
        //客服部提成
        getCustomerServiceCommission: function () {
            var customerServiceCommission = 0;
            customerServiceCommission = this.orderAmount * (0.006);
            return customerServiceCommission;
        },
        //工程部大区提成
        getProjectCommission: function () {
            var projectCommission = 0;
            projectCommission = this.payedAll * (0.0015);
            return projectCommission;
        },
        //审计部提成
        getAuditCommission: function () {
            var auditCommission = 0;
            auditCommission = this.orderAmount * (0.0005);
            return auditCommission;
        },
        //总监及总经理提成
        getGeneralManagerCommission: function () {
            var generalManagerCommission = 0;
            generalManagerCommission = this.payedAll * (0.004);
            return generalManagerCommission;
        },
        //间接成本
        getIndirect: function () {
            var indirect = 0;
            indirect = this.getDesignCommission() + this.getDesignerDeduction() + this.getCustomerServiceCommission()
                + this.getProjectCommission() + this.getAuditCommission() + this.getGeneralManagerCommission() + this.getSums() + 627 + 55 + 265;
            return indirect;
        },
        //总成本
        getTotalCost: function () {
            var total = 0;
            total = this.getDirect() + this.getIndirect();
            return total;
        },
        //毛利
        getGross: function () {
            var gross = 0;
            if (this.payedAll != 0) {
                gross = ( ( this.payedAll - this.getTotalCost() ) / this.payedAll ) * 100;
            }
            return gross;
        },
        //计算基装变更金额
        getInstallBaseMultiplication: function (rows, key, key2) {
            if (rows) {
                var sum = 0;
                for (var i = 0, len = rows.length; i < len; i++) {
                    for (var j = 0, p = rows[i].installBaseChangeStandBookList.length; j < p; j++) {
                        if (rows[i].installBaseChangeStandBookList[j][key2] == 1) {
                            if (!isNaN(rows[i].installBaseChangeStandBookList[j][key])) {
                                sum += (rows[i].installBaseChangeStandBookList[j][key]);
                            }
                        } else {
                            sum -= (rows[i].installBaseChangeStandBookList[j][key]);
                        }
                    }
                }
                return sum;
            } else {
                return 0;
            }
        },
        getInstallChange:function () {
            return this.getInstallBaseMultiplication(this.installBaseChanges, 'unitProjectTotalPrice', 'changeType');
        },
        getMultiplication: function (rows, key, key2) {
            if (rows) {
                var sum = 0;
                for (var i = 0, len = rows.length; i < len; i++) {
                    if (!isNaN(rows[i][key]) && !isNaN(rows[i][key2])) {
                        sum += rows[i][key] * rows[i][key2];
                    }
                }
                return sum.toFixed(2);
            } else {
                return '';
            }
        },
        // 获取数据, interfaceName接口名字，colums定义表数据的数组, key接收数据的对象
        fetchGrossProfit: function () {
            var self = this;
            self.$http.get('/api/grossProfit/findGrossProfit?orderno=' + this.orderNo).then(function (res) {
                if (this.msg == 14) {
                    if (res.data.code == 1) {
                        this.grossProfit = res.data.data;
                    }
                    self.loaded = true;
                }
            }, function (res) {
            });
            self.$http.get('/api/standBook/getPayment?orderno=' + this.orderNo).then(function (res) {
                if (this.msg == 14) {
                    if (res.data.code == 1) {
                        self.payList = res.data.data;
                        if (self.payList != null) {
                            self.modifyCost = self.payList.modifyCost;
                        } else {
                            self.modifyCost = 0;
                        }
                    }
                    self.loaded = true;
                }
            }, function (res) {
            });
            this.$http.get('/api/changeStandBook/installBaseChange?orderno=' + this.orderNo).then(function (res) {
                if (this.msg == 14) {
                    if (res.data.code == 1) {
                        self.installBaseChanges = res.data.data;
                    }
                    self.loaded = true;
                }
            });
            self.$http.post("/api/standBook/installedBase?orderId=" + this.orderNo).then(function (res) {
                if (this.msg == 14) {
                    this.packageInfo = res.data.packageInfo;
                    //套餐报价
                    var items = this.packageInfo;
                    if (items != undefined || items != null) {
                        for (var i = 0; i < items.length; i++) {
                            this.mealCost += items[i].laborAuxiliaryMaterialsSettleAmount;
                        }
                    }
                    if (this.mealCost == 0) {
                        this.mealCost = self.valuationArea * 252;
                    }
                    return this.mealCost;
                    self.loaded = true;
                }
            }, function (res) {
            });
            self.$http.get("/api/standBook/getOrderDetailByOrderNo", {
                params: {
                    orderno: self.orderNo
                }
            }).then(function (res) {
                if (res.data.code == 1) {
                    self.valuationArea = res.data.data.measurehousearea;
                    self.unitprice = res.data.data.unitprice;
                    self.orderAmount = res.data.data.totalMoney;
                    self.changeMoney = res.data.data.changeMoney;
                    self.selectedPackageAmount = self.unitprice * self.valuationArea;
                }
            });
            this.$http.get('/api/materialCostStandBook/principalMaterial', {
                params: {
                    orderno: self.orderNo
                }
            }).then(function (res) {
                if (this.msg == 14) {
                    if (res.data.code == 1) {
                        self.materialRows = res.data.data.rows;
                    }
                    self.loaded = true;
                }
            });
            //提成
            self.$http.post("/api/standBook/installedBasePm?orderId=" + this.orderNo).then(function (res) {
                if (this.msg == 14) {
                    this.pmSettleInfo = res.data.pmSettleInfo;
                    self.loaded = true;
                }
            }, function (res) {
            });
            //基装主材
            self.$http.get("/api/materialCostStandBook/auxiliaryMaterial?orderno=" + this.orderNo).then(function (res) {
                if (this.msg == 14) {
                    if (res.data.code == 1) {
                        this.selectMaterialStandBookDetails = res.data.data.rows;
                        for (var i = 1; i < this.selectMaterialStandBookDetails.length; i++) {
                            if (this.selectMaterialStandBookDetails[i].auxiliaryMaterialflagName == '开关面板' || this.selectMaterialStandBookDetails[i].auxiliaryMaterialflagName == '电源' || this.selectMaterialStandBookDetails[i].auxiliaryMaterialflagName == '灯带' || this.selectMaterialStandBookDetails[i].auxiliaryMaterialflagName == '筒灯') {
                                this.installBasePrincipal.push(this.selectMaterialStandBookDetails[i]);
                            }
                        }
                    }
                    self.loaded = true;
                }
            })
        }
    },
    computed: {
        // mainInstallBaseChangeTotalAmount: function () {
        //     return this.getInstallBaseMultiplication(this.installBaseChanges, 'unitProjectTotalPrice', 'changeType');
        // },
        //主材合计
        materialTotalAmount: function () {
            return this.getMultiplication(this.materialRows, 'smStoreprice', 'consumptiondosage');
        }
    },
    watch: {
        msg: function (curVal, oldVal) {
            if (curVal == 14 && !this.loaded) {
                this.fetchGrossProfit();
            }
        }

    }
});
