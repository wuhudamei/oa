var tt = null;
var queryCount = 0;
var backorderNo = RocoUtils.parseQueryString().orderNo;
var backpdSupplier = decodeURIComponent(RocoUtils.parseQueryString().pdSupplier);
var backbrandname = decodeURIComponent(RocoUtils.parseQueryString().brandname);
var backstartTime = RocoUtils.parseQueryString().startTime;
var backendTime = RocoUtils.parseQueryString().endTime;
    + (function (window, RocoUtils) {
        $('#materialBill').addClass('active');
        $('#noAccountCheckingOrder').addClass('active');
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
                    name: '未对账订单',
                    active: true //激活面包屑的
                }],
                $dataTable: null,
                form: {
                    pdSupplierId: '',
                    pdSupplier: '',
                    brandname: '',
                    orderNo:'',
                    startTime: '',
                    endTime: ''
                },
                selects: {},
                userid: null,
                items: [],
                level1: [],
                stRdBilltems: null,
                selecrs: {
                    pd_supplier: '',
                    skuname: ''
                },
                pd_supplierArr: [],
                suppliers: '',
                brandnameArr: [],
                checkArr: [],     //选中的数组
            },
            methods: {
                findSuppAndProName: function () {
                    var self = this
                    self.$http.get('/api/stRdBillItem/findSupplyAndGood?status=0&accep=1').then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            res.data.forEach(function (value, index) {
                                value.id = index.toString()
                                var arr = value.brandname.split(',');
                                // 二级
                                arr.forEach(function (val) {
                                    var obj = {}
                                    obj.text = val
                                    obj.proid = index.toString()
                                    self.brandnameArr.push(obj)
                                })
                            })
                            self.level1 = res.data;
                            self.initParam();
                        } else {
                            self.$toastr.error(res.message);
                        }
                    });

                },
                query: function () {
                    if(queryCount > 0){//如果不是第一次访问则刷新页面
                         this.$dataTable.bootstrapTable('refresh', {pageNumber: 1});
                    }else{
                        this.drawTable();
                        queryCount++;
                    }
                },
                drawTable: function () {
                    var self = this;
                    self.$dataTable = $(this.$els.datatable).bootstrapTable({
                        url: '/api/stRdBillItem/noAccountCheckingOrder',
                        method: 'get',
                        dataType: 'json',
                        cache: false, // 去缓存
                        pagination: true, // 是否分页
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
                                width: '20%',
                                align: 'center',
                                formatter: function (value, row, index) {
                                    return '<a href="javascript:void(0)" data-handle="view" data-id="' + value + '">' + value + '</a>'
                                }
                            },
                            {
                                field: 'customerName',
                                title: '客户姓名',
                                width: '20%',
                                align: 'center',
                            },
                            {
                                field: 'customerMobile',
                                title: '客户手机号码',
                                width: '20%',
                                align: 'center',
                            },
                            {
                                field: 'houseAddress',
                                title: '房屋地址',
                                width: '20%',
                                align: 'center',
                            }]
                    });
                    // 查看事件
                    self.$dataTable.on('click', '[data-handle="view"]', function (e) {
                        var id = $(this).data("id");//不是查询条件
                        if(tt.form.orderNo != null || tt.form.orderNo != ''){
                            var orderNo = tt.form.orderNo;//是查询条件
                        }
                        var pdSupplier = tt.form.pdSupplier;
                        var brandname = tt.form.brandname;
                        var startTime = tt.form.startTime;
                        var endTime = tt.form.endTime;
                        window.location.href = ctx + '/admin/materialSupplierAccountChecking?id=' + id +'&orderNo='+orderNo+'&pdSupplier='
                            + pdSupplier +'&brandname='+brandname+'&startTime='+startTime+'&endTime='+endTime;
                        e.stopPropagation();
                    });
                },

                initParam:function () {//返回的查询数据，需要默认选中
                    var self = this;
                    if(backorderNo == null || backorderNo == ''){ //返回的订单编号
                        $("#orderNo").val('');
                    }else{
                        $("#orderNo").val(backorderNo);
                    }
                    if(backpdSupplier == null || backpdSupplier == ''){ //返回的供应商名称

                    }else {
                        var arr = self.level1;
                        for(var i=0;i<arr.length;i++){
                            if(arr[i].pd_supplier == backpdSupplier){
                                self.form.pdSupplierId = i + '';
                                break;
                            }
                        }
                    }
                    if(backbrandname == null || backbrandname == ''){//返回的商品品牌

                    }else {
                        var arr = self.brandnameArr;
                        for(var i=0;i<arr.length;i++){
                            if(arr[i].text == backbrandname){
                                self.form.brandname = backbrandname;
                                break;
                            }
                        }
                    }
                    if(backstartTime == null || backstartTime == ''){//返回的验收开始时间
                        $("#startTime").val('');
                    }else {
                        $("#startTime").val(backstartTime);
                    }

                    if(backendTime == null || backendTime == ''){//返回的验收结束时间
                        $("#endTime").val('');
                    }else {
                        $("#endTime").val(backendTime);
                    }
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
            },

            created: function () {
                this.findSuppAndProName();
            },
            ready: function () {
                this.activeDate();
            },
            watch:{
                // 动态获取供应商名称
                'form.pdSupplierId':function(val){
                    if(val !== ''){
                        var pdSupplier = document.querySelector('#pdSupplierId')
                        this.form.pdSupplier = pdSupplier.options[pdSupplier.selectedIndex].text
                    }else{
                        this.form.pdSupplier = ''
                    }
                }
            }
        });

    })(this.window, RocoUtils);