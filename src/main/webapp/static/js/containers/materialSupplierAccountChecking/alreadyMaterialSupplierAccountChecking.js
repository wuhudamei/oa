var tt = null;
var queryCount = 0;
+(function (window, RocoUtils) {
    $('#materialBill').addClass('active');
    $('#isAccountChecking').addClass('active');
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
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '已对账',
                active: true //激活面包屑的
            }],
            totalPrice:0.00,
            $dataTable: null,
            form: {
                pdSupplierId: '',
                pdSupplier: '',
                supplier: '',
                orderNo: '',
                brandname: '',
                startTime: '',
                endTime: ''
            },
            level1: [],
            skunameArr: [],
            checkAllValue: false,   //全选按钮
            checkArr: [],     //选中的数组
            totalAmount: 0
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


            findSuppAndProName: function () {
                var self = this
                self.$http.get('/api/stRdBillItem/findSupplyAndGood?status=1&accep=1').then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        res.data.forEach(function (value, index) {
                            value.id = index
                            var arr = value.brandname.split(',')
                            // 二级
                            arr.forEach(function (val) {
                                var obj = {}
                                obj.text = val
                                obj.proid = index
                                self.skunameArr.push(obj)
                            })
                        })
                        self.level1 = res.data;
                    } else {
                        self.$toastr.error(res.message);
                    }
                });
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.datatable).bootstrapTable({
                    url: '/api/stRdBillBatchDetail/detail',
                    method: 'get',
                    dataType: 'json',
                    cache: false, // 去缓存
                    pagination: false, // 是否分页
                    sidePagination: 'client', // 客户端分页
                    content: '',
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', // 空数据的默认显示字符
                    striped: true, // 斑马线
                    sortName: 'id', // 默认排序列名
                    sortOrder: 'desc', // 默认排序方式
                    columns: [
                        {
                            field: 'orderNo',
                            title: '订单编号',
                            width: '10%',
                            align: 'center',
                            sortable: true
                        },
                        {
                            field: 'customerName',
                            title: '客户姓名',
                            width: '10%',
                            align: 'center',
                        },
                        {
                            field: 'pdSupplier',
                            title: '供应商',
                            width: '10%',
                            align: 'center',
                        },
                        {
                            field: 'pdBrandname',
                            title: '商品品牌',
                            width: '10%',
                            align: 'center',

                        },
                        {
                            field: 'pdSkuname',
                            title: '商品名称',
                            width: '10%',
                            align: 'center',
                        },
                        {
                            field: 'pdModel',
                            title: '商品规格',
                            width: '10%',
                            align: 'center',
                        },
                        {
                            field: 'pdPrice',
                            title: '单价',
                            width: '10%',
                            align: 'center',
                        },
                        {
                            field: 'billNumber',
                            title: '对账数量',
                            width: '10%',
                            align: 'center',
                        },
                        {
                            field: 'operateTime',
                            title: '对账时间',
                            width: '15%',
                            align: 'center',
                            sortable: true,
                            formatter: function (data) {
                                if (data != null) {
                                    return moment(data).format('YYYY-MM-DD HH:mm:ss');
                                }
                            }
                        },
                        {
                            field: 'billAmountMoney',
                            title: "对账金额",
                            align: 'center',
                            valign: 'middle',
                            width: '15%',
                            formatter:function (value) {
                                self.totalPrice += value;
                                return value;
                            }
                        }]

                });



            },
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
            //点击搜索功能
            query: function () {
                if (queryCount > 0) {
                    this.$dataTable.bootstrapTable('refresh', {pageNumber: 1});
                    this.totalPrice = 0.00;
                } else {
                    this.drawTable();
                    queryCount++;
                }
            },



        },

        created: function () {
            this.fUser = window.RocoUser;
            this.findSuppAndProName();
        },
        ready: function () {
            this.activeDate();
            // this.drawTable();
        },
        watch: {
            // 动态获取供应商名称
            'form.pdSupplierId': function (val) {
                if (val !== '') {
                    var pdSupplier = document.querySelector('#pdSupplierId')
                    // var pdSupplierName =
                    this.form.pdSupplier = pdSupplier.options[pdSupplier.selectedIndex].text
                } else {
                    this.form.pdSupplier = ''
                }
            },

        }
    });



})(this.window, RocoUtils);