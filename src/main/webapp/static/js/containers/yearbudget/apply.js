var apply;
(function() {
    Vue.filter('show-name', function(id) {
        var name = ''
        _.forEach(apply.costItems, function(item) {
            if (id == item.id) name = item.name
        })
        return name
    })
    apply = new Vue({
        el: '#container',
        data: {
            //报销科目大类
            types: {
                'MATTERS': {'id': 1, 'name': "人事类"},
                'MARKETING': {'id': 2, 'name': "营销类"},
                'ADMINISTRATOR': {'id': 3, 'name': "行政类"},
                'FINANCE': {'id': 4, 'name': "财务类"},
                'CUSTOMER': {'id': 5, 'name': "客管类"},
                'OTHERS': {'id': 6, 'name': "其他类"}
            },
            disabled: false,
            submitBtnClick: false,
            isWeChat:isWeChat,
            user: '',
            title: '',
            currentType: '',
            typeName:'',
            isEdit: '',
            budget: null,
            applyDate: '',
            costItems: [],
            costDetails: [],
            addBtnClick: false,
            totalMoney:'',
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '年度事务管理',
                active: true //激活面包屑的
            }],
            params: null, // 参数
            info: null,

            webUploaderSub: {
                type: 'sub',
                formData: {},
                accept: {
                    title: '文件',
                    extensions: '*'
                },
                server: ctx + '/api/upload',
                //上传路径
                fileNumLimit: 1,
                fileSizeLimit: 50000 * 1024,
                fileSingleSizeLimit: 5000 * 1024
            }
        },
        components: {
            'web-uploader': RocoVueComponents.WebUploaderComponent
        },
        events: {
            'webupload-upload-success-sub': function (file, res) {
                if (res.code == '1') {
                    this.$toastr.success('上传成功');
                    this.info.attachment = res.data.path;
                } else {
                    this.$toastr.error(res.message);
                }
            }
        },
        created: function() {
            var self = this
            self.params = RocoUtils.parseQueryString()
        },
        ready: function() {
            var self = this;
            self.initParams();
            // 处理月份
            self.handleYearBudget();
        },
        computed: {
            // 计算预算总额
            budgetTotal: function() {
                var self = this
                var total = 0
                _.forEach(self.info.yearBudgetDetailList, function(o, ind) {
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
                self.totalMoney=total;
                return total;
            }
        },
        methods: {
            //根据地址栏参数，初始化数据,激活菜单样式
            initParams: function () {
                var self = this;
                var params = RocoUtils.parseQueryString(window.location.search.substr(1));
                var type = params['type'];
                self.currentType = self.types[type];

                var id = params['id'];
                if (type) {
                    self.addActiveClass(type);
                    var typeName = self.currentType.name;
                    self.typeName=self.currentType.name;
                    if (id) {
                        self.title = '编辑' + typeName + '年度预算申请';
                        self.isEdit = true;
                    } else {
                        self.isEdit = false;
                        self.title = '新增' + typeName + '年度预算申请';
                    }
                    self.initBudgetDetails();
                } else {
                    //错误提示
                }
                self.applyDate = moment().format('YYYY-MM-DD');//当前时间
            },
            addActiveClass: function (type) {
                $('#yearBudgetMenu').addClass('active');
                switch (type) {
                    case 'MATTERS':
                        $('#yearMattersBudgetApply').addClass('active');
                        break;
                    case 'MARKETING':
                        $('#yearMarketingBudgetApply').addClass('active');
                        break;
                    case 'ADMINISTRATOR':
                        $('#yearAdminBudgetApply').addClass('active');
                        break;
                    case 'FINANCE':
                        $('#financeBudgetApply').addClass('active');
                        break;
                    case 'CUSTOMER':
                        $('#yearCustomerBudgetApply').addClass('active');
                        break;
                    case 'OTHERS':
                        $('#yearOthersBudgetApply').addClass('active');
                        break;
                    default:
                        $('#budgetAffair').addClass('active');
                        break;
                }
            },
            /**
             * 上传附件
             */
            deleteAttachment:function(){
                var self = this;
                self.$http.delete('/api/upload', {
                    params: {
                        path: self.info.attachment
                    }
                }).then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.info.attachment = '';
                    } else {
                        self.$toastr.error("删除失败");
                    }
                });
            },
            //获取详情
            fetBudgetInfo: function () {
                var self = this;
                self.$http.get('/api/yearBudget/' + self.params.id).then(function (res) {
                    if (res.data.code == 1) {
                        self.info = res.data.data;
                    } else {
                        askDialog.show('提示', res.data.message)
                    }
                });
            },
            // 添加的时候列表数据初始化
            fetchInitData: function() {
                var self = this;
                var params = RocoUtils.parseQueryString(window.location.search.substr(1));
                var type = params['type'];
                var submitData = {
                    "attachment":'',
                    "applyCode": "",
                    "applyTitle": self.typeName,
                    "applyCompany": {"orgName":RocoUser.company},
                    "applyUser":{"id":RocoUser.userId,"name":RocoUser.name} ,
                    "applyTime": moment().format('YYYY-MM-DD'),
                    "budgetYear": moment().add(1, 'year').format('YYYY'),
                    "reason": "",
                    "totalMoney":self.totalMoney,
                    "type": type,
                    "yearBudgetDetailList": []
                };

                _.forEach(self.costItems, function(item, idx) {
                    submitData.yearBudgetDetailList.push({
                        "yearBudgetId": null,
                        "subjectCode": item.id,
                        "january": 0,
                        "february": 0,
                        "march": 0,
                        "april": 0,
                        "may": 0,
                        "june": 0,
                        "july": 0,
                        "august": 0,
                        "september": 0,
                        "october": 0,
                        "november": 0,
                        "december": 0,
                        "budgetYear": submitData.budgetYear
                    })
                })

                self.info = submitData
            },
            // 费用科目列表
            initBudgetDetails: function () {
                var self = this;
                self.$http.get('/api/dict/getByTypeFilter?type=2&parentType=' + self.currentType.id).then(function (res) {
                    if (res.data.code == 1) {
                        //返回的明细对象
                        self.costItems = res.data.data;

                        // 根据ID判断是添加还是编辑
                        // 编辑
                        if(self.params.id) {
                            self.fetBudgetInfo()
                        }
                        // 添加
                        else {
                            self.$nextTick(function() {
                                self.fetchInitData()
                            })
                        }
                    }
                });
            },
            // 预算月份插件初始化处理
            handleYearBudget: function() {
                $('#yearBudgetId').datetimepicker({
                  startView: 'decade',
                  minView: 'decade',
                  format: 'yyyy',
              })
            },
            // 检查是否符合要求
            check: function() {
                var self = this
                var flag = true
                _.forEach(self.info.yearBudgetDetailList, function(o, ind) {
                    if(
                        _.isString(o.january) ||
                        _.isString(o.february) ||
                        _.isString(o.march) ||
                        _.isString(o.april) ||
                        _.isString(o.may) ||
                        _.isString(o.june)  ||
                        _.isString(o.july)  ||
                        _.isString(o.august) ||
                        _.isString(o.september) ||
                        _.isString(o.october) ||
                        _.isString(o.november) ||
                        _.isString(o.december)
                    ) {
                        flag = false
                    }
                })

                if(flag && !self.info.reason) {
                    return false
                }

                return flag
            },
            // 取消
            cancel: function() {
                var self = this
                askDialog.show('提示', '真的要取消吗？', function() {
                    console.log('点击确定')
                })
            },
            // 保存
            save: function() {
                var self = this
                if(self.check()) {
                    askDialog.show('提示', '确认保存？', function() {
                        self.info.totalMoney=self.totalMoney;
                        self.$http.post(ctx + '/api/yearBudget/add', self.info)//+ (self.params.id ? 'submit' : 'add')
                        .then(function(res) {
                            if(res.data.code == "1") {

                                askDialog.show('提示', '保存成功，即将跳转')
                                setTimeout(function() {
                                    window.location.href = ctx + '/admin/yearbudget/list'
                                }, 1500)
                            }else{
                                self.$toastr.error(res.data.message);
                            }
                        })
                    })
                }
                else {
                    askDialog.show('提示', '数据有错误或预算说明未填写')
                }
            },
            // 提交
            commit: function() {
                var self = this
                if(self.check()) {
                    askDialog.show('提示', '确认保存？', function() {
                        self.info.totalMoney=self.totalMoney;
                        self.$http.post(ctx + '/api/yearBudget/submit', self.info)
                            .then(function(res) {
                                if(res.data.code == "1") {
                                    askDialog.show('提示', '保存成功，即将跳转')
                                    setTimeout(function() {
                                        window.location.href = ctx + '/admin/yearbudget/list'
                                    }, 1500)
                                }else{
                                    self.$toastr.error(res.data.message);
                                }
                            })
                    })
                }
                else {
                    askDialog.show('提示', '数据有错误或预算说明未填写')
                }
            }
        }
    })

    // 问询对话框
    var askDialog = new Vue({
        el: '#askDialog',
        data: {
            title: '',
            content: '',
            cancelFn: null, // 取消回调
            okFn: null, // 确定回调
            dialog: null // 对话框
        },
        ready: function() {
            this.initAskDialog()
        },
        methods: {
            initAskDialog: function() {
                // $('#askDialog').modal()
            },
            show: function(title, content, okFn, cancelFn) {
                this.title = title || '提示'
                this.content = content || '请传入内容'
                this.okFn = okFn || null
                this.cancelFn = cancelFn || null
                $('#askDialog').modal()
            },
            cancel: function() {
                this.cancelFn && this.cancelFn()
                $('#askDialog').modal('hide')
            },
            ok: function() {
                this.okFn && this.okFn()
                $('#askDialog').modal('hide')
            }
        }
    })

})()