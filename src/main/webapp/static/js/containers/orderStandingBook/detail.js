+(function () {
    $('#orderStandingBook').addClass('active');
    $('#orderStandingBookList').addClass('active');
    var detail = new Vue({
        el: '#container',
        components: {
            'sale-component': SaleComponent,
            'change-component': ChangeComponent,
            'selection-list-component': SelectionListComponent,
            'select-material-component': SelectMaterialComponent,
            'dismantle-component': DismantleComponent,
            'audit-component': AuditComponent,
            'project-component': ProjectComponent,
            'quality-testing-component': QualityTestingComponent,
            'customer-management-component': CustomerManagementComponent,
            'pay-component': PayComponent,
            'installed-base-component': InstalledBaseComponent,
            'material-installation-component': MaterialInstallationComponent,
            'supply-component': SupplyComponent,
            'material-cost-component': MaterialCostComponent,
            //'construction-cost-component': ConstructionCostComponent,
            'gross-profit-component': GrossProfitComponent
            //'construction-plan-component': ConstructionPlanComponent
            // 'staff-cost-component': StaffCostComponent,
        },
        data: {
            index: 0,
            isShow: false,
            orderNo: null,
            contractNo: null,
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/admin/orderStandingBook/list',
                name: '台账列表'
            }, {
                path: '/',
                name: '列表详情',
                active: true //激活面包屑的
            }],
            showCase: 'selectMaterial',
            details: {
                // 项目编号
                orderNo: '',
                // 客户姓名
                customerName: '',
                // 所选套餐
                selectedPackage: '',
                // 建筑面积
                constructionArea: '',
                // 联系方式
                contact: '',
                // 房屋地址
                houseAddress: '',
                // 房屋户型
                houseDoorModel: '',
                // 有无电梯
                elevator: '',
                // 房屋状况
                houseConditions: '',
                // 计价面积
                valuationArea: '',
                // 第二联系人
                secondContact: '',
                // 第二联系人电话
                secondContactTel: '',
                // 房屋类型
                houseType: '',
                // 计划开工时间
                planStartTime: '',
                // 计划完成时间
                planFinishTime: '',
                // 订单状态
                orderStatus: '',
                // 订单金额
                orderAmount: '',
                // 变更金额
                changeMoney: '',
                //状态id
                allotGroupId: '',
                //客服
                serviceName: '',
                //设计师
                stylistName: ''
            },
            selectedPackageAmount: '',
            nowOrderAmount: 0,
            tabList: []
        },
        computed: {
            showPay: function () {
                return this.showCase === 'pay';
            },
            // showConstructionPlan: function () {
            //     return this.showCase === 'constructionPlan';
            // },
            showMaterialCost: function () {
                return this.showCase === 'materialCost';
            },
            showSelectMaterial: function () {
                return this.showCase === 'selectMaterial';
            },
            showStaffCost: function () {
                return this.showCase === 'staffCost';
            },
            showAudit: function () {
                return this.showCase === 'audit';
            },
            showChange: function () {
                return this.showCase === 'change';
            },
            showProject: function () {
                return this.showCase === 'project';
            },
            showSale: function () {
                return this.showCase === 'sale';
            },
            showSelectList: function () {
                return this.showCase === 'selectList';
            },
            showQualityTesting: function () {
                return this.showCase === 'qualityTesting';
            },
            showCustomerManagement: function () {
                return this.showCase === 'customerManagement';
            },
            showMaterialInstallation: function () {
                return this.showCase === 'materialInstallation';
            },
            showSupply: function () {
                return this.showCase === 'supply';
            },
            /*showConstructionCost: function () {
                return this.showCase === 'constructionCost';
            },*/
            showDismantle: function () {
                return this.showCase === 'dismantle';
            },
            showGrossProfit: function () {
                return this.showCase === 'grossProfit';
            },
            totalStandardAmount: function () {
                var sum = 0;
                sum = this.details.valuationArea * this.details.unitprice;
                return sum.toFixed(2);
            }
        },
        created: function () {
            this.orderNo = RocoUtils.parseQueryString()['orderno'];
            this.contractNo = RocoUtils.parseQueryString()['contractNo'];
            this.fetchData();
            this.widths();
            this.tabList.push({label: '销售台账'});
            this.tabList.push({label: '设计方案台账'});
            this.tabList.push({label: '变更台账'});
            this.tabList.push({label: '最终选材台账'});
            this.tabList.push({label: '拆改台账'});
            this.tabList.push({label: '审计台账'});
            this.tabList.push({label: '工程台账'});
            this.tabList.push({label: '质检台账'});
            this.tabList.push({label: '客管台账'});
            this.tabList.push({label: '财务台帐'});
            this.tabList.push({label: '基装材料台帐'});
            this.tabList.push({label: '主材安装台账'});
            this.tabList.push({label: '供应台账'});
            this.tabList.push({label: '材料成本台帐'});
            //this.tabList.push({label: '应付施工费台账'});
            this.tabList.push({label: '毛利台账'});
            //this.tabList.push({label:'施工计划台帐'})

        },
        methods: {
            clickEvent: function (val) {
                this.index = val.key;
            },
            widths: function () {
                var winWidth = window.innerWidth;
                if (winWidth < 500) {
                    this.isShow = false;
                }
                else {
                    this.isShow = true;
                }

                // console.log(this.isShow)
            },
            orderToggle: function () {
                //alert(1)
                this.isShow = !this.isShow;
            },
            fetchData: function () {
                var self = this;
                self.$http.get("/api/standBook/getOrderDetailByOrderNo", {
                    params: {
                        orderno: self.orderNo
                    }
                }).then(function (res) {
                    if (res.data.code == 1) {
                        self.details.orderNo = res.data.data.orderNo;
                        self.details.customerName = res.data.data.customerName;
                        self.details.selectedPackage = res.data.data.comboType;
                        self.details.constructionArea = res.data.data.floorArea;
                        self.details.contact = res.data.data.mobile;
                        self.details.houseAddress = res.data.data.houseAddress;
                        self.details.houseDoorModel = res.data.data.housetype;
                        self.details.elevator = res.data.data.lift;
                        self.details.houseConditions = res.data.data.newHouse;
                        self.details.valuationArea = res.data.data.measurehousearea;
                        self.details.secondContact = res.data.data.secondLinkman;
                        self.details.secondContactTel = res.data.data.secondLinkmanMobile;
                        self.details.planStartTime = res.data.data.contractStartTime;
                        self.details.planFinishTime = res.data.data.contractCompleteTime;
                        self.details.orderStatus = res.data.data.allotState;
                        self.details.orderAmount = res.data.data.totalMoney;
                        self.details.changeMoney = res.data.data.changeMoney;
                        self.details.allotGroupId = res.data.data.allotGroupId;
                        self.details.serviceName = res.data.data.serviceName;
                        self.details.stylistName = res.data.data.stylistName;
                        self.details.unitprice = res.data.data.unitprice;
                        self.selectedPackageAmount = (res.data.data.unitprice * res.data.data.measurehousearea).toFixed(2);
                        self.nowOrderAmount = (res.data.data.totalMoney+res.data.data.changeMoney).toFixed(2);
                    }
                });
            }
        },
        computed: {
            elevator: function () {
                if (this.details.elevator) {
                    return '有';
                } else {
                    return '无';
                }
            },
            houseConditions: function () {
                if (this.details.houseConditions) {
                    return '新房';
                } else {
                    return '旧房';
                }
            },
            orderStatus: function () {
                switch (this.details.orderStatus) {
                    case 0:
                        return '付款中';
                    case 1:
                        return '督导组长待分配';
                    case 2:
                        return '待审核';
                    case 3:
                        return '待设计';
                    case 5:
                        return '待签约';
                    case 6:
                        return '工程待派单';
                    case 7:
                        return '待施工';
                    case 8:
                        return '施工中';
                    case 9:
                        return '竣工';
                    case 11:
                        return '设计待分配';
                    case 20:
                        return '设计变更中';
                    case 21:
                        return '订单退单关闭';
                    default:
                        return '';
                }
            },
            secondContact: function () {
                if (this.details.secondContact != "") {
                    return this.details.secondContact;
                } else {
                    return '无';
                }
            },
            secondContactTel: function () {
                if (this.details.secondContactTel != "") {
                    return this.details.secondContactTel;
                } else {
                    return '无';
                }
            },
            planStartTime: function () {
                if (this.details.planStartTime == "1980-01-01 00:00:00.0") {
                    return '-'
                } else {
                    return moment(this.details.planStartTime).format('YYYY-MM-DD');
                }
            },
            planFinishTime: function () {
                if (this.details.planFinishTime == "1980-01-01 00:00:00.0") {
                    return '-'
                } else {
                    return moment(this.details.planFinishTime).format('YYYY-MM-DD');
                }
            }
        }
    });
})();