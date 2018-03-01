var vueIndex = null;
var showBill,addBill;
+(function (RocoUtils) {
    $('#singleSign').addClass('active');
    $('#companySign').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            $dataTable: null,
            form: {
                company: '',
                beginDate: '',
                endDate:'',
                statue:'',
                department:'',
                search:'',
                orgCode: '',
                orgName: ''
            },
            orgData: null, // 机构树数据
            showOrgTree: false // 是否显示机构树
        },
        methods: {
        	fetchOrgTree: function () {
                var self = this;
                this.$http.get('/api/org/fetchTree').then(function (response) {
                  var res = response.data;
                  if (res.code == 1) {
                    self.orgData = res.data;
                  }
                })
              },
              // 勾选机构数外部时，隐藏窗口
              clickOut: function () {
                this.showOrgTree = false
              },
              // 选择机构时回调事件
              selectOrg: function (node) {
                var self = this;
                self.form.orgName = node.name;
                self.form.orgCode = node.id;
              },
// addProject: function () {
// window.open('/admin/singleSign/add');
// },
        	// 初始化时间选择器
            initDatePicker: function () {
            	var self = this;
              $(this.$els.entryDate).datetimepicker({
                minView: 2,
                format: 'yyyy-mm-dd',
                todayBtn: true
              });
              $(this.$els.endDate).datetimepicker({
                minView: 2,
                format: 'yyyy-mm-dd',
                todayBtn: true
              });
            },
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/singleSign',
                    method: 'get',
                    dataType: 'json',
                    cache: false, // 去缓存
                    pagination: true, // 是否分页
                    sidePagination: 'server', // 服务端分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', // 空数据的默认显示字符
                    striped: true, // 斑马线
                    maintainSelected: true, // 维护checkbox选项
                    sortName: 'id', // 默认排序列名
                    sortOrder: 'desc', // 默认排序方式
                    columns: [{
                        field: 'pid',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'signCode',
                        title: '签报编码',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'punishCode',
                        title: '处罚编码',
                        align: 'center',
                        visible: false
                    },{
                        field: 'customerName',
                        title: '客户姓名',
                        align: 'center'
                    },{
                        field: 'customerPhone',
                        title: '客户电话',
                        align: 'center'
                    },{
                        field: 'orderAddress',
                        title: '订单地址',
                        align: 'center'
                    },{
                        field: 'punishDepartment',
                        title: '责任部门',
                        align: 'center'
                       
                    },{
                        field: 'punishMan',
                        title: '责任人',
                        align: 'center',
                        formatter: function (value, row,index) {
                            /*
							 * var department = self.getDepartmentName(row.org);
							 * var company = self.getCompanyName(row.org);
							 */
                             return '<a href="javascript:void(0)" data-handle="view" data-id="' + row.signCode + '"  data-index="' + index + '">' + value + '</a>';
                         }
                    },{
                        field: 'punishMoney',
                        title: '处罚金额',
                        align: 'center'
                    },{
                        field: 'punishReason',
                        title: '处罚原因',
                        align: 'center',
                        visible: false
                    },
                    {
                        field: 'createTime',
                        title: '签报时间',
                        align: 'center',
                        formatter: function (time) {
                            if (time != null) {
                                return moment(time).format('YYYY-MM-DD');
                            }
                        }
                    },{
                        field: 'status',
                        title: '执行状态',
                        align: 'center',
                        sortable: true,
                        formatter: function(value, row, index) {
                            if(value == 0){
                            	return '未执行'
                            }else{
                            	return '执行'
                            }
                         }
                    },{
                        field: '', // 将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            if(row.status == 0){
                            	fragment += ('<button data-handle="operate-edit" data-id="' + row.id +'" type="button" class="m-r-xs btn btn-xs btn-info">执行</button>');
                            }
                            if(row.status == 0){
                            if (RocoUtils.hasPermission(''))
                                fragment += ('<button data-handle="operate-delete" data-id="' + row.id + '" data-code="' + row.signCode + '" type="button" class="m-r-xs btn btn-xs btn-danger">删除</button>');
                            return fragment;
                           }
                        }
                    }]
                });
                self.$dataTable.on('click', '[data-handle="view"]', function (e) {
                    var index = $(this).data('index');
                    var rowData = self.$dataTable.bootstrapTable('getData');
                    var curData = rowData[index];
                    showBillModel(curData);
                });
                
                
                self.$dataTable.on('click', '[data-handle="operate-edit"]', function (e) {
                    var id = $(this).data('id');
                    self.$http.get('/api/singleSign/updateStatus/' + id).then(function (res) {
                        if (res.data.code == 1) {
                        	vueIndex.$dataTable.bootstrapTable('refresh');
                        }
                    });
                });


                self.$dataTable.on('click', '[data-handle="operate-delete"]', function (e) {
                    var id = $(this).data('id');
                    var signCode = $(this).data('code');
                    swal({
                        title: '删除记录',
                        text: '确定删除该记录吗？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.get('/api/singleSign/delete/' + id+'?signCode='+signCode).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                self.$dataTable.bootstrapTable('refresh');
                            }
                        }).catch(function () {

                        }).finally(function () {
                            swal.close();
                        });
                    });
                    e.stopPropagation();
                });
            },
            createWorkOrder: function (e) { // 创建工单
                modal(this.form.company);//弹出新建签报单页面
        		showMdniOrder();//弹出查询合同页面
            },
            exportBill:function(){ // 导出报表
            	var self = this;
            	var beginDate = self.form.beginDate;
            	var endDate = self.form.endDate;
            	var statue = self.form.statue;
            	var companyId = self.form.company;
            	var search = self.form.search;
            	window.open('/api/singleSign/export?beginDate='+beginDate+'&search='+search+'&endDate='+endDate+'&statue='+statue+'&companyId=' + companyId);
            },
            showModel: function () {
                
            },
            query: function () {
            	this.$dataTable.bootstrapTable('refresh');
            },
            createBtnClickHandler: function (e) {
                var user = {
                    name: '',
                    company: '',
                    phone:'',
                    customerid:''
                };
                this.showModel(user, false);
            }
        },
        created: function () {
        	
            this.fUser = window.RocoUser;
            this.fetchOrgTree();
        },
        ready: function () {
        	var self = this;
        	self.drawTable();
            /*
			 * if (orgName && orgFamilyCode) { self.form.orgName = orgName;
			 * self.form.orgCode = orgFamilyCode; } if (!self.form.orgName ||
			 * self.form.orgName == 'null') { self.form.orgName = ''; }
			 */
        	this.initDatePicker();
        }
    });
    
    /**
     * 新建签报单
     * @param orgId 组织机编号
     */
    function modal(orgId) {
        var _$modal = $('#workorder').clone();
        var $modal = _$modal.modal({
            height: 450,
            maxHeight: 450,
            backdrop: 'static',
            keyboard: false
        });
        // 获取 node
        var el = $modal.get(0);
        // 创建 Vue 对象编译节点
        addBill = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $modal, // 模式窗体 jQuery 对象
            data: {
            	groups:[{
            		punishMoney:0,
            		punishReason:'',
            		punishDepartment:'',
            		punishMan:'',
            		punishManName:'',
            		punishDepartName:'',
            		showOrgTree:false
            	}],
            	form: {
            		orderNo: '',
            		customerName: '',
            		mobile: '',
            		address: '',
                    procedureDescribe:'',
                    managerView:''
                },
                // 控制 按钮是否可点击
                disabled: false,
                // 模型复制给对应的key
                orgData: null, // 机构树数据
                showOrgTree: false // 是否显示机构树
            },
            methods: {
            	fetchOrgTree1:function () {
                    var self = this;
                    this.$http.get('/api/org/fetchTree').then(function (response) {
                      var res = response.data;
                      if (res.code == 1) {
                        self.orgData = res.data;
                      }
                    })
                  },
                  // 勾选机构数外部时，隐藏窗口
                  clickOut: function () {
                  },
                  showTree:function(item){
                	  item.showOrgTree=true  
                  },
                  showContant:function(){
                	showMdniOrder();  
                  },
                  // 选择机构时回调事件
                  selectOrg: function (node,item) {
                    var self = this;
                    self.form.orgName = node.name;
                    self.form.orgCode = node.familyCode;
                    item.punishDepartName = node.name;
                    item.punishDepartment = node.id;
                    item.showOrgTree = false
                  },
            	add:function(e){
            		var group;
            		if(this.groups.length != 0){
            			group = this.groups[this.groups.length-1];
            			if(group.selectedPart == '' || group.selectedPerson == '' || group.textAreas){
                			return false;
                		}
            			this.groups.push({
            				punishMoney:0,
                    		punishReason:'',
                    		punishDepartment:'',
                    		punishMan:'',
                    		punishManName:'',
                    		punishDepartName:'',
                    		showOrgTree:false
                    	});
            		}else{
            			this.groups.push({
                    		punishMoney:0,
                    		punishReason:'',
                    		punishDepartment:'',
                    		punishMan:''
                    	});
            		};
            	},
                submitClickHandler: function () {
                    var self = this;
                    if(self.form.orderNo == null || self.form.orderNo==''){
                    	self.$toastr.error('请选择合同信息！');
                    	return false;
                    }
                    
                    if (self.submitting) {
                        return false;
                    }
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.submit();
                        }
                    });
                },
                submit: function () {
                    var self = this;
                    self.submitting = true;
                    var formData = _.cloneDeep(self.form);
                    var data = {
                    	orderNum:formData.orderNo,
                    	customerName:formData.customerName,
                    	customerPhone:formData.mobile,
                    	orderAddress:formData.address,
                    	subCompany:orgId,
                    	procedureDescribe:formData.procedureDescribe,
                    	managerView:formData.managerView,
                    	publishBillList:this.groups
                    };
                    
                   self.$http.post("/api/singleSign/insert", JSON.stringify(data)).then(function (res) {
                       if (res.data.code == 1) {
                            self.$toastr.success('提交成功');
                            $modal.modal('hide');
                            vueIndex.$dataTable.bootstrapTable('refresh');
                        }
                    }).catch(function () {

                    }).finally(function () {
                        // 请求返回后dom还没有销毁，任然可以重复提交，所以提交成功也不可再次提交
                        self.submitting = false;
                    });
                },
                showPerson:function(item){
                	showEmploneeByOrgId(item);
                }
            },
		// watch: {
		// "groups":function(){
		// Vue.set(this.groups,this.groups)
		// }
		// },
            created: function () {
            	 this.fetchOrgTree1();
            },
            ready: function () {
            }
        });
        // 创建的Vue对象应该被返回
        return addBill;
    }

    
    function showMdniOrder() {
        var _modal = $('#mdnOrder').clone();
        var $el = _modal.modal({
            height: 800,
            backdrop: 'static',
        });
        $el.on('shown.bs.modal',
            function () {
                var el = $el.get(0);
                var vueModal = new Vue({
                    el: el,
                    http: {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    },
                    mixins: [RocoVueMixins.ModalMixin],
                    $modal: $el,
                    created: function () {
                    },
                    data: {
                        selectOrder: [],
                        form:{
                        	keyword:''
                        },
                    },
                    methods: {
                        query: function () {
// this.$dataTable.bootstrapTable('selectPage', 1);
                            this.$dataTable.bootstrapTable('refresh');
                        },
                        drawTable: function () {
                            var self = this;
                            self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
                                url: '/api/singleSign/mdnOrderList',
                                method: 'get',
                                dataType: 'json',
                                cache: false,
                                pagination: false,// 不分页
                                sidePagination: 'server',
                                queryParams: function (params) {
                                    return _.extend({}, params, self.form);
                                },
                                mobileResponsive: true,
                                undefinedText: '-',
                                striped: true,
                                maintainSelected: true,
                                sortOrder: 'desc',
                                columns: [{
                                    checkbox: true,
                                    align: 'center',
                                    width: '5%',
                                    radio: true
                                }, {
                                    field: 'orderNo',
                                    title: '订单号',
                                    width: '6%',
                                    orderable: false
                                }, {
                                    field: 'customerName',
                                    title: '客户姓名',
                                    width: '6%',
                                    orderable: false
                                }, {
                                    field: 'mobile',
                                    title: '客户电话',
                                    width: '6%',
                                    orderable: false
                                }, {
                                    field: 'address',
                                    title: '订单地址',
                                    width: '10%',
                                    orderable: false
                                }]
                            });
                            
                            self.$dataTable.on('check.bs.table', function (row, data) {
                                self.selectOrder = [];
                                var order = {
                            		orderNo: data.orderNo,
                            		customerName: data.customerName,
                            		mobile: data.mobile,
                            		address: data.address,
                                };
                                self.selectOrder.push(order);
                            });
                        },
                        commitUsers: function () {
                            var self = this;
                            if (self.selectOrder != null && self.selectOrder != undefined && self.selectOrder.length > 0) {
                            	addBill.form.orderNo = self.selectOrder[0].orderNo;
                            	addBill.form.customerName = self.selectOrder[0].customerName;
                            	addBill.form.mobile = self.selectOrder[0].mobile;
                            	addBill.form.address = self.selectOrder[0].address;
                            }
                            $el.modal('hide');
                            this.$destroy();
                        }
                    },
                    ready: function () {
                        this.drawTable();
                    }
                });

                // 创建的Vue对象应该被返回
                return vueModal;
            });

    }
    
    /**
	 * 签报单明细详情
	 * 
	 * @param rowData
	 * @returns
	 */
    function showBillModel(rowData) {
        var _$modal = $('#billModal').clone();
        var $modal = _$modal.modal({
            height: 450,
            maxHeight: 500,
            backdrop: 'static',
            keyboard: false
        });
        // 获取 node
        var el = $modal.get(0);
        // 创建 Vue 对象编译节点
        showBill = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $modal, // 模式窗体 jQuery 对象
            data: {
                // 控制 按钮是否可点击
                disabled: false,
                signbill: {
        			signCode:rowData.signCode,
        			punishCode:rowData.punishCode,
        			status:rowData.status,
        			customerName:rowData.customerName,
        			customerPhone:rowData.customerPhone,
        			orderAddress:rowData.orderAddress,
        			punishDepartment:rowData.punishDepartment,
        			punishMan:rowData.punishMan,
        			punishMoney:rowData.punishMoney,
        			punishReason:rowData.punishReason,
        			time:moment(rowData.createTime).format('YYYY-MM-DD HH:mm:ss')
                }
            },
            methods: {},
            watch: {},
            created: function () {},
            ready: function () {}
        });
        // 创建的Vue对象应该被返回
        return showBill;
    }

    /**
	 * 根据部门ID查询员工
	 * 
	 * @returns
	 */
    function showEmploneeByOrgId(item) {
    	if(addBill.form.orgCode != null && addBill.form.orgCode != undefined && addBill.form.orgCode != ""){
            var _modal = $('#employeeListModel').clone();
            var $el = _modal.modal({
                height: 200,
                maxHeight: 200,
                backdrop: 'static',
            });
            $el.on('shown.bs.modal',
                function () {
                    var el = $el.get(0);
                    var vueModal = new Vue({
                        el: el,
                        http: {
                            headers: {
                                'Content-Type': 'application/x-www-form-urlencoded'
                            }
                        },
                        mixins: [RocoVueMixins.ModalMixin],
                        $modal: $el,
                        data: {
                            form: {
                                keyword: '',
                                orgCode:addBill.form.orgCode,
                                limit: 5
                            },
                            employeeId:null,
                            employeeName:null,
                            modalModel: null, // 模式窗体模型
                            _$el: null, // 自己的 el $对象
                            _$dataTable: null // datatable $对象
                        },
                        methods: {
                            query: function () {
                            	this.$dataTable.bootstrapTable('refresh');
                            },
                            drawTable: function () {
                                var self = this;
                                self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
                                    url: '/api/employees',
                                    method: 'get',
                                    dataType: 'json',
                                    cache: false,
                                    pagination: true,
                                    sidePagination: 'server',
                                    queryParams: function (params) {
                                        return _.extend({}, params, self.form);
                                    },
                                    mobileResponsive: true,
                                    undefinedText: '-',
                                    striped: true,
                                    maintainSelected: true,
                                    pageSize: 5,
                                    sortOrder: 'desc',
                                    columns: [ {
                                        checkbox: true,
                                        align: 'center',
                                        width: '5%',
                                        radio: true
                                    }, {
                                        field: 'jobNum',
                                        title: '员工号',
                                        width: '6%'
                                    }, {
                                        field: 'name',
                                        title: '姓名',
                                        width: '10%'
                                    }, {
                                        field: 'mobile',
                                        title: '手机号',
                                        width: '6%'
                                    }]
                                });
                                self.$dataTable.on('check.bs.table', function (row, data) {
                                    self.employeeId = data.id;
                                    self.employeeName = data.name;
                                });
                            },
                            commitUsers: function () {
                                var self = this;
 // order.parts = self.employeeId;
 // order.people = self.employeeName;
                                item.punishMan = self.employeeId
                                item.punishManName = self.employeeName
                                $el.modal('hide');
                                this.$destroy();
                            }
                        },
                        created: function () {
                        },
                        ready: function () {
                            this.drawTable();
                        }
                    });

                    // 创建的Vue对象应该被返回
                    return vueModal;
                });
    	}else{
    		addBill.$toastr.error('请先选择责任部门！');
    	}
    }
})
(this.RocoUtils);