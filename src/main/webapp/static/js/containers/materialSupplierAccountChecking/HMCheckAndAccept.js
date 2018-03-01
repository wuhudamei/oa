var tt = null;
var checkAllFlag = false;
+(function (window, RocoUtils) {
    $('#materialBill').addClass('active');
    $('#hmCheckAndAccept').addClass('active');
    tt = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '手动验收',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            form: {
                pdSupplierId: '',
                pdSupplier:'',
                pdSkuname: '',
                startTime: '',
                endTime: '',
                keyword:''
            },
            items: [],
            level1:[],
            skunameArr:[],
            stRdBilltems: null,
            selecrs:{
                pd_supplier:'',
                skuname:''
            },
            pd_supplierArr:[],
            checkArr: []     //选中的数组
        },
        methods: {
            findSuppAndProName:function () {
                var self = this
                self.$http.get('/api/stRdBillItem/findSupplyAndGood?status=0&accep=0').then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        res.data.forEach(function(value,index){
                            value.id = index
                            var arr = value.skuname.split(',')
                            // 二级
                            arr.forEach(function(val){
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
                this.$dataTable.bootstrapTable('refresh');
                //恢复全选按钮初始
                $("#checkAllCheckBox").attr('checked',false);
                checkAllFlag = false;
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.datatable).bootstrapTable({
                    url: '/api/stRdBillItem/findItemByAcceptance',
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
                            field:'billItemId',
                            title:"<input id='checkAllCheckBox' data-handle='checkAll' type='checkbox' />",
                            align:'center',
                            valign:'middle',
                            width: '10%',
                            formatter:function (value) {
                                return '<input name="billItemId" class="checkItem" value="'+value+'" type="checkbox" />';
                            }
                        },
                        {
                            field: 'orderNo',
                            title: '订单编号',
                            width: '30%',
                            align: 'center',
                        },
                        {
                            field: 'customerName',
                            title: '客户姓名',
                            width: '20%',
                            align: 'center',
                        },
                        {
                            field: 'pdSkuname',
                            title: '商品名称',
                            width: '30%',
                            align: 'center',
                        },
                        {
                            field: 'pdModel',
                            title: '规格',
                            width: '20%',
                            align: 'center',
                        }]
                });

                //全选
                self.$dataTable.on('click', '[data-handle="checkAll"]', function (e) {
                    if(!checkAllFlag){
                        $(".checkItem").each(function(){
                            $(this).context.checked = true;
                        });
                        checkAllFlag = true;
                    }else{
                        $(".checkItem").each(function(){
                            $(this).context.checked = false;
                        });
                        checkAllFlag = false;
                    }

                    e.stopPropagation();
                });

            },
            mark:function(){ //手动验收
                var self = this;
                $(".checkItem").each(function(){
                    if($(this).context.checked) {
                        self.checkArr.push($(this).context.defaultValue);
                    }
                });

                if(self.checkArr.length === 0){
                    self.$toastr.error('请选择数据');
                    return
                }
                var data = {
                    'billItemIds':this.checkArr
                }
                self.$http.post('/api/stRdBillItem/checkAndAccept', data,{emulateJSON:true}).then(function(res){
                    if(res.data.code === '1'){
                        self.$toastr.success('操作成功');
                        this.query();
                    }
                })
            }
        },
        created: function () {
            this.findSuppAndProName();
        },
        ready: function () {
            this.activeDate();
            this.drawTable();
        },
        watch:{
            // 动态获取供应商名称
            'form.pdSupplierId':function(val){
                if(val !== ''){
                    var pdSupplier = document.querySelector('#pdSupplierId')
                    // var pdSupplierName =
                    this.form.pdSupplier = pdSupplier.options[pdSupplier.selectedIndex].text
                }else{
                    this.form.pdSupplier = ''
                }
            }
        }
    });
})(this.window, RocoUtils);