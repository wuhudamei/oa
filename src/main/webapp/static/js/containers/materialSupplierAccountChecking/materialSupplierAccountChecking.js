var tt = null;
var idArr = [], pdArr = [], cuArr = [], priceArr = [], supplierArr = [], billBatch = null, queryCount = 0;
    + (function (window, RocoUtils) {
        $('#materialBill').addClass('active');
        $('#noAccountChecking').addClass('active');
        tt = new Vue({
            el: '#container',
            validators: {
                laterThanStart: function (val, startDate) {
                    try {
                        var end = moment(val);
                        return end.isBefore(startDate) ? false : true;
                    } catch (e) {
                        return false;
                    }
                }
            },
            data: {
                supplierFlag: {},
                // 面包屑
                breadcrumbs: [{
                    path: '/',
                    name: '主页'
                }, {
                    path: '/',
                    name: '未对账',
                    active: true //激活面包屑的
                }],

                $dataTable: null,
                form: {
                    pdSupplierId: '',
                    pdSupplier: '',
                    pdSkuname: '',
                    brandname: '',
                    orderNo: '',
                    startTime: '',
                    endTime: ''
                },
                stRdBilltems: null,
                checkAllValue: false,   //全选按钮
                checkArr: [],     //选中的数组
                totalAmount: 0,
                pdcount: 0
            },
            // 验证器
            validators: {
                numeric: function (val/*,rule*/) {
                    // return  /^[-+]?[0-9]+$/.test(val) || (val === '')   //这个验证是只可以输入正整数
                    return /^\d+(\.\d+)?$/;
                },
                overed: function (val, rule) {
                    return val <= rule
                }
            },
            computed: {
                selection: function () {
                    for (var i = 0; i < this.YX.length; i++) {
                        if (this.YX[i].text === this.A) {
                            return this.YX[i].ZY;
                        }
                    }
                }
            },
            methods: {
                activeDate: function () {
                    $(this.$els.startTime).datetimepicker({
                        minView: 2,
                        format: 'yyyy-mm-dd',
                        weekStart: 1,
                        todayBtn: true
                    });
                    $(this.$els.endTime).datetimepicker({
                        minView: 2,
                        format: 'yyyy-mm-dd',
                        weekStart: 1,
                        todayBtn: true
                    });
                },
                fetchRecord: function () {
                    var self = this;
                    var data = _.clone(self.form);
                    if (queryCount == 0) {//如果是第一次加载，则使用页面传递的参数
                        var id = this.$parseQueryString()['id'];//不是查询条件，是根据id查询订单信息
                        // var orderNo = this.$parseQueryString()['orderNo'];//是查询条件
                        var pdSupplier = this.$parseQueryString()['pdSupplier'];
                        var brandname = this.$parseQueryString()['brandname'];
                        var startTime = this.$parseQueryString()['startTime'];
                        var endTime = this.$parseQueryString()['endTime'];
                        // if(id != null || id !=  ''){
                        //     data.orderNo = id;//不是查询条件
                        // }else{
                        //     data.orderNo = orderNo;//是查询条件
                        // }
                        data.orderNo = id;
                        data.pdSupplier = pdSupplier;
                        data.brandname = brandname;
                        data.startTime = startTime;
                        data.endTime = endTime;
                    }else{
                        data.startTime = self.form.startTime;
                        data.endTime = self.form.endTime;
                    }
                    self.$http.post('/api/stRdBillItem/findItemBySupplyAndSkuName', data, {emulateJSON: true}).then(function (res) {
                        if (res.data.code == 1) {
                            self.stRdBilltems = res.data.data;
                        }
                        self.submitting = false;
                    }).catch(function () {

                    }).finally(function () {
                        self.submitting = false;
                    });
                    queryCount++;
                },
                showModel: function (stRdBilltem) {
                    var self = this;
                    var _$modal = $('#modal').clone();
                    var $modal = _$modal.modal({
                        height: 600,
                        maxHeight: 630,
                    });
                    modal($modal, stRdBilltem);
                },

                showModel2: function (stRdBilltem) {
                    var self = this;
                    var _$modal = $('#modal2').clone();
                    var $modal = _$modal.modal({
                        height: 600,
                        maxHeight: 630,
                    });
                    modal2($modal, stRdBilltem);
                },

                comm: function () {
                    idArr = [];
                    pdArr = [];
                    cuArr = [];
                    priceArr = [];
                    supplierArr = []
                    if (this.checkAllValue) {
                        this.stRdBilltems.forEach(function (val) {
                            idArr.push(val.billItemId)
                            pdArr.push(val.pdCurrentCount)
                            cuArr.push(val.cuCount || 0)
                            priceArr.push(val.pdPrice)
                            supplierArr.push(val.pdSupplier)
                        })
                    } else {
                        for (var i = 0, iLength = this.checkArr.length; i < iLength; i++) {
                            for (var j = 0, jLength = this.stRdBilltems.length; j < jLength; j++) {
                                if (this.checkArr[i] === this.stRdBilltems[j].billItemId) {
                                    idArr.push(this.stRdBilltems[j].billItemId);
                                    pdArr.push(this.stRdBilltems[j].pdCurrentCount);
                                    cuArr.push(this.stRdBilltems[j].cuCount || 0);
                                    priceArr.push(this.stRdBilltems[j].pdPrice);
                                    supplierArr.push(this.stRdBilltems[j].pdSupplier)
                                    break;
                                }
                            }
                        }
                    }
                },

                goBack: function () { //返回
                    var backOrderNo = this.$parseQueryString()['orderNo'];
                    var backpdSupplier = this.$parseQueryString()['pdSupplier'];
                    var backbrandname = this.$parseQueryString()['brandname'];
                    var backstartTime = this.$parseQueryString()['startTime'];
                    var backendTime = this.$parseQueryString()['endTime'];
                    window.location.href = '/admin/noAccountCheckingOrder?orderNo='+backOrderNo+'&pdSupplier='
                        +encodeURIComponent(backpdSupplier)+'&brandname='+encodeURIComponent(backbrandname)+'&startTime='+backstartTime+'&endTime='+backendTime;
                },
                export: function () { //导出
                    this.comm();
                    window.location.href = '/api/stRdBillItem/export?billItemId=' + this.checkArr + '&cuArr=' + cuArr + '&billBatch=' + '';
                },
                exportAndMark: function () { //导出并标记对账
                    if (this.$valid.invalid) {
                        this.$validate(true)
                        return
                    }
                    var self = this;
                    var flag = false
                    for (var i = 0, iLength = this.stRdBilltems.length; i < iLength; i++) {
                        for (var j = 0, jLength = self.checkArr.length; j < jLength; j++) {
                            if (self.checkArr[j] === self.stRdBilltems[i].billItemId) {
                                //    判断本次对账
                                if (typeof(self.stRdBilltems[i].cuCount) === "undefined" || self.stRdBilltems[i].cuCount === '' || self.stRdBilltems[i].cuCount === '0') {
                                    self.$toastr.error('本次对账数量要为正整数');
                                    flag = true
                                    break
                                }
                                //    判断同一供应商
                                self.supplierFlag[self.stRdBilltems[i].pdSupplier] = self.stRdBilltems[i].pdSupplier
                                if (Object.keys(self.supplierFlag).length > 1) {
                                    self.$toastr.error('请选择同一供应商数据');
                                    flag = true
                                    break
                                }
                            }
                        }
                    }
                    if (flag) return
                    self.comm();
                    this.mark(true);
                    self.fetchRecord();
                },
                mark: function (fla) { //标记对账
                    if (this.checkArr.length === 0) {
                        this.$toastr.error('请选择数据');
                        return
                    }
                    if (this.$valid.invalid) {
                        this.$validate(true)
                        return
                    }
                    var self = this;
                    var flag = false
                    for (var i = 0, iLength = this.stRdBilltems.length; i < iLength; i++) {
                        for (var j = 0, jLength = self.checkArr.length; j < jLength; j++) {
                            if (self.checkArr[j] === self.stRdBilltems[i].billItemId) {
                                //    判断本次对账
                                if (typeof(self.stRdBilltems[i].cuCount) === "undefined" || self.stRdBilltems[i].cuCount === '' || self.stRdBilltems[i].cuCount === '0') {
                                    self.$toastr.error('本次对账数量要为正整数');
                                    flag = true
                                    break
                                }
                                //    判断同一供应商
                                self.supplierFlag[self.stRdBilltems[i].pdSupplier] = self.stRdBilltems[i].pdSupplier
                                if (Object.keys(self.supplierFlag).length > 1) {
                                    self.$toastr.error('请选择同一供应商数据');
                                    flag = true
                                    break
                                }
                            }
                        }
                    }
                    if (flag) return

                    var _$modal = $('#modal3').clone();
                    var $modal = _$modal.modal({
                        height: 600,
                        maxHeight: 630,
                    });
                    modal3($modal, '', fla);


                },
                // 全选事件
                checkAll: function () {
                    if (!this.checkAllValue) {
                        this.checkArr = []
                        var self = this
                        $('.checkItem').each(function (value) {
                            self.checkArr.push(this.value)
                        })
                    } else {
                        this.checkArr = []
                    }
                },
                // 本次计算数量Change事件
                countChange: function (item) {
                    if (this.checkArr.indexOf(item.billItemId) >= 0) {
                        this.getTotal()
                    }
                },
                // 计算商品总额
                getTotal: function () {
                    var total = 0;
                    var total2 = 0;
                    for (var i = 0, iLength = this.checkArr.length; i < iLength; i++) {
                        for (var j = 0, jLength = this.stRdBilltems.length; j < jLength; j++) {
                            if (this.checkArr[i] === this.stRdBilltems[j].billItemId) {
                                var sum = 0;
                                var sum2 = 0;
                                if (this.stRdBilltems[j].cuCount) {
                                    sum = new Decimal(this.stRdBilltems[j].pdPrice).times(this.stRdBilltems[j].cuCount)
                                    sum2 = new Decimal(this.stRdBilltems[j].cuCount);
                                    total = new Decimal(total).plus(sum);
                                    total2 = new Decimal(total2).plus(sum2);
                                }
                                break;
                            }
                        }
                    }
                    this.totalAmount = total;
                    this.pdcount = total2;
                },
                showValue: function (event) {
                    var value = evt.target.value;
                    var showBox = document.getElementById("showValue");
                    showBox.value = value;
                }
            },

            created: function () {
                this.fUser = window.RocoUser;
            },
            ready: function () {
                this.fetchRecord();
                this.activeDate();
            },
            computed: {
                checkAllValue: function () {
                    // 当选中的数量等于列表的行数且列表有数据时全选选中
                    return (this.checkArr.length === this.stRdBilltems.length) && this.stRdBilltems.length !== 0
                }
            },
            watch: {
                //监控选中数组
                checkArr: function (val) {
                    if (val.length === 0) {
                        this.totalAmount = 0
                        this.pdcount = 0
                        this.supplierFlag = {}
                    } else {
                        var self = this;
                        self.supplierFlag = {}
                        for (var i = 0, iLength = this.stRdBilltems.length; i < iLength; i++) {
                            for (var j = 0, jLength = self.checkArr.length; j < jLength; j++) {
                                if (self.checkArr[j] === self.stRdBilltems[i].billItemId) {
                                    //    判断同一供应商
                                    self.supplierFlag[self.stRdBilltems[i].pdSupplier] = self.stRdBilltems[i].pdSupplier
                                    break;
                                }
                            }
                        }
                        this.getTotal()
                    }
                },
            }
        });

        function modal($el, model) {
            // 获取 node
            var el = $el.get(0);
            // 创建 Vue 对象编译节点
            var vueModal = new Vue({
                el: el,
                // 模式窗体必须引用 ModalMixin
                mixins: [RocoVueMixins.ModalMixin],
                $modal: $el, //模式窗体 jQuery 对象
                data: {

                    bill: {
                        billItemId: model.billItemId,
                        pdCurrentCount: model.pdCurrentCount,
                        pdChangeCountEnd: '',
                        changeAdjust: ''
                    },
                    //控制 按钮是否可点击
                    disabled: false,
                    //模型复制给对应的key
                    noticeboard: model,
                    submitBtnClick: false,
                    // username : window.RocoUser.name
                },
                // 验证器
                validators: {
                    numeric: function (val/*,rule*/) {
                        // return  /^[-+]?[0-9]+$/.test(val) || (val === '')   //这个验证是只可以输入正整数
                        return /^\d+(\.\d+)?$/;
                    }

                },

                methods: {
                    submit: function () {
                        var self = this;
                        self.submitBtnClick = true;
                        self.disabled = true;
                        self.$http.post('/api/stRdBillItem/updateCount', self.bill, {emulateJSON: true}).then(function (res) {
                            if (res.data.code == 1) {

                                $el.on('hidden.bs.modal', function () {
                                    self.$toastr.success('操作成功');
                                    tt.fetchRecord();
                                });
                                $el.modal('hide');
                            }
                        }).finally(function () {
                            self.disabled = false;
                        });
                    }
                }

            });
            // 创建的Vue对象应该被返回
            return vueModal;
        }

        function modal2($el, model) {
            // 获取 node
            var el = $el.get(0);
            // 创建 Vue 对象编译节点
            var vueModal = new Vue({
                el: el,
                // 模式窗体必须引用 ModalMixin
                mixins: [RocoVueMixins.ModalMixin],
                $modal: $el, //模式窗体 jQuery 对象
                data: {
                    //模型复制给对应的key
                    noticeboard: model,
                    submitBtnClick: false,
                    tableData: []
                    // username : window.RocoUser.name
                },

                methods: {
                    fetchData: function () {
                        var self = this
                        this.$http.get('/api/stRdBillAdjustRecord/findAll?billItemId=' + model.billItemId).then(function (res) {
                            if (res.data.code === '1') {
                                self.tableData = res.data.data
                            }
                        })
                    },
                    drawTable: function () {
                        var self = this;
                        self.$dataTable = $(this.$els.datatable).bootstrapTable({
                            url: '/api/stRdBillAdjustRecord/findAll?billItemId=' + model.billItemId,
                            method: 'get',
                            dataType: 'json',
                            cache: false, // 去缓存
                            pagination: false, // 是否分页
                            sidePagination: 'server', // 服务端分页
                            content: '',
                            queryParams: function (params) {
                                // 将table 参数与搜索表单参数合并
                                return _.extend({}, params, self.form);
                            },
                            createUserName: '',
                            mobileResponsive: true,
                            undefinedText: '-', // 空数据的默认显示字符
                            striped: true, // 斑马线
                            sortName: 'id', // 默认排序列名
                            sortOrder: 'desc', // 默认排序方式
                            columns: [
                                {
                                    field: 'operateType',
                                    title: '类型',
                                    width: '10%',
                                    align: 'center',

                                },
                                {
                                    field: 'pdChangeCountBefore',
                                    title: '调整前数量',
                                    width: '10%',
                                    align: 'center',

                                },
                                {
                                    field: 'pdChangeCountEnd',
                                    title: '调整后数量',
                                    width: '10%',
                                    align: 'center',

                                },
                                {
                                    field: 'changeAdjust',
                                    title: '调整说明',
                                    width: '40%',
                                    align: 'center',
                                },
                                {
                                    field: 'operator',
                                    title: '操作人',
                                    width: '10%',
                                    align: 'center',
                                },
                                {
                                    field: 'changeTime',
                                    title: '操作时间',
                                    width: '20%',
                                    align: 'center',
                                }

                            ]
                        });
                    },
                    submit: function () {
                        var self = this;
                        self.submitBtnClick = true;
                        self.$validate(true, function () {
                            if (self.$validation.valid) {

                                self.$http.get('/api/stRdBillAdjustRecord/findAll?' + model.billItemId).then(function (res) {
                                    if (res.data.code == 1) {
                                        $el.on('hidden.bs.modal', function () {
                                            tt.$dataTable.bootstrapTable('refresh');
                                            self.$toastr.success('操作成功');
                                        });
                                        $el.modal('hide');
                                    }
                                }).finally(function () {
                                });
                            }
                        });
                    },

                },
                created: function () {
                    this.fUser = window.RocoUser;
                    this.fetchData()
                },
                ready: function () {
                    // this.drawTable();
                }

            });
            // 创建的Vue对象应该被返回
            return vueModal;
        }

        function modal3($el, model, flag) {
            // 获取 node
            var el = $el.get(0);
            // 创建 Vue 对象编译节点
            var vueModal = new Vue({
                el: el,
                // 模式窗体必须引用 ModalMixin
                mixins: [RocoVueMixins.ModalMixin],
                $modal: $el, //模式窗体 jQuery 对象
                data: {
                    //控制 按钮是否可点击
                    disabled: false,
                    //模型复制给对应的key
                    noticeboard: model,
                    submitBtnClick: false,
                    batchExplain: '',
                    billBatch: ''
                },

                methods: {
                    submit: function () {
                        var self = this;
                        if (!flag) {
                            tt.comm();
                        }
                        var param = {
                            'billItemIds': idArr,
                            'pdCounts': pdArr,
                            'prices': priceArr,
                            'cuArr': cuArr,
                            'batchExplain': self.batchExplain,
                        }
                        self.$http.post('/api/stRdBillItem/updateBatch', param, {emulateJSON: true}).then(function (res) {
                            if (res.data.code === '1') {
                                billBatch = res.data.data;
                                this.disabled = true;
                                $el.on('hidden.bs.modal', function () {
                                    self.$toastr.success('操作成功');

                                    tt.checkArr = [];
                                    tt.fetchRecord();
                                });
                                $el.modal('hide');
                                if (flag) { //根据状态判断，如果是true则导出
                                    window.location.href = '/api/stRdBillItem/export?billItemId=' + tt.checkArr + '&cuArr=' + cuArr + '&billBatch=' + billBatch;
                                }
                            }
                        })
                    },
                }

            });
            // 创建的Vue对象应该被返回
            return vueModal;
        }

    })(this.window, RocoUtils);