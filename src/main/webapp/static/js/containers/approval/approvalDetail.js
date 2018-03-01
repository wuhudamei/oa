/**
 * 审批详情
 * 
 * @param self
 * @param type
 * @param curData
 * @param isApprove
 */
// function approvalDetail(self,type,curData,isApprove){
// if(type == "BUSINESS"){//出差
// getFormData(self,'/api/apply/applyBusinessAway/',curData,isApprove);
// //获取出差表单详情
// } else if(type == "LEAVE"){//请假
// getFormData(self,'/api/vacations/',curData,isApprove); //获取出差表单详情
// } else if(type == "BUDGET"){//预算
// getFormData(self,'/api/budgets/multiple/',curData,isApprove); //获取预算表单详情
// } else if(type == "YEARBUDGET"){//年预算
// getFormData(self,'/api/yearBudget/',curData,isApprove); //获取年预算表单详情
// } else if(type == "EXPENSE"){//报销
// getFormData(self,'/api/payments/multiple/',curData,isApprove); //获取报销表单详情
// } else if(type == "PURCHASE"){//采购
// getFormData(self,'/api/apply/applyPurchase/',curData,isApprove); //获取采购表单详情
// }
// }


/**
 * 费用单审批详情弹窗
 * 
 * @param $el
 *            窗口对象
 * @param signature
 *            费用单对象
 */
function signatureDetail($el, signature) {
    // 获取 node
    var el = $el.get(0);
    // 创建 Vue 对象编译节点
    var vueDetail = new Vue({
        el: el,
        // 模式窗体必须引用 ModalMixin
        mixins: [RocoVueMixins.ModalMixin],
        $modal: $el, // 模式窗体 jQuery 对象
        data: {
            signature: null,
            approveList: null
        },
        created: function () {

        },
        ready: function () {
            this.signature = signature;
            this.getApproveList();
        },
        watch: {},
        computed: {},
        methods: {
            getApproveList: function () {
                var self = this;
                self.$http.get("/api/wfmanager/wfApproveDetail/", {
                    params: {
                        'formId': this.signature.id,
                        'type': 'SIGNATURE',
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
    });
    // 创建的Vue对象应该被返回
    return vueDetail;
}
/**
 * 列表快速审批,通过,拒绝
 * 
 * @param self
 * @param curData
 * @param resultStatus
 * @param isRef
 *            是否刷新列表(true=是,false=否) //暂时不需要此参数
 * @returns
 */
function quickApproveEx(self, curData, resultStatus, tableObj) {
	var msgTitle = "审批操作";
	var msgTxt = "确定审批此流程？";
	if('TURN' == resultStatus){
		msgTitle = "转派操作";
		msgTxt = "转派之后将失去审批的权力，是否确认？\n"
	}else if('ADDBEFORE' == resultStatus){
		msgTitle = "前加审批人";
		msgTxt = "在之前添加审批人,当前审批权限后移,是否确认？"
	}else if('ADDAFTER' == resultStatus){
		msgTitle = "后加审批人";
		msgTxt = "在之后添加审批人,是否确认？"
	}
	swal({
		title: msgTitle,
		text: msgTxt,
		type: 'info',
		confirmButtonText: '确定',
		cancelButtonText: '取消',
		showCancelButton: true,
		showConfirmButton: true,
		showLoaderOnConfirm: true,
		confirmButtonColor: '#ed5565',
		closeOnConfirm: false
	}, function (isOk) {
		if(isOk){
			var formData = _.cloneDeep(self.form);
			var data = {
					id: curData.id,
					nodeId: curData.nodeId,
					isSign: curData.isSign,
					type: curData.type,
					formId: curData.formId,
					approveResult: resultStatus,
					remark: curData.remark,
					applyUserId: curData.applyUserId,
					applyCode: curData.applyCode,
					applyTitle: curData.applyTitle
			};
			if('TURN' == resultStatus){
				data['targetUserId'] = curData['targetUserId'];//已作废,用人对象代替
				data['approverEmployee'] = curData['approverEmployee'];
			}else if('ADDBEFORE' == resultStatus || 'ADDAFTER' == resultStatus){
				data['currentApprove'] = curData['currentApprove'];
				data['targetUserId'] = curData['targetUserId'];//已作废,用人对象代替
			}
			self.$http.post('/api/wfmanager/wfApprove', data).then(function (res) {
				if (res.data.code == 1) {
					self.$toastr.success('操作成功');
					var currentUrl = window.location.href;
					// 说明是info页,处理完以后,直接刷新当前页
					if(currentUrl.indexOf('/info=')){
						window.location.reload();
					}
					tableObj.bootstrapTable('refresh');
				}else{
					self.$toastr.success(res.data.message);
				}
			}).catch(function () {
				
			}).finally(function () {
				swal.close();
			});
		}
	});
}
// 重构为 quickApproveEx方法,用回调刷新对象
// function quickApprove(self, curData, resultStatus) {
// swal({
// title: '审批操作',
// text: '确定审批此流程？',
// type: 'info',
// confirmButtonText: '确定',
// cancelButtonText: '取消',
// showCancelButton: true,
// showConfirmButton: true,
// showLoaderOnConfirm: true,
// confirmButtonColor: '#ed5565',
// closeOnConfirm: false
// }, function () {
// debugger;
// var formData = _.cloneDeep(self.form);
// var data = {
// id: curData.id,
// nodeId: curData.nodeId,
// isSign: curData.isSign,
// type: curData.type,
// formId: curData.formId,
// approveResult: resultStatus,
// remark: curData.remark,
// applyUserId: curData.applyUserId,
// applyCode: curData.applyCode,
// applyTitle: curData.applyTitle
// };
// self.$http.post('/api/wfmanager/wfApprove', data).then(function (res) {
// if (res.data.code == 1) {
// self.$toastr.success('操作成功');
// if(self.form.type == "LEAVE"){ //请假
// self.$dataTable.bootstrapTable('refresh');
// }else if(self.form.type == "SIGNATURE"){ //费用
// self.$dataTableSignature.bootstrapTable('refresh');
// }else if(self.form.type == "EXPENSE"){//报销
// self.$dataTablePayment.bootstrapTable('refresh');
// }else if(self.form.type == "COMMON"){//通用
// self.$dataTableCommon.bootstrapTable('refresh');
// }else{
// self.$dataTable.bootstrapTable('refresh');
// }
// }
// }).catch(function () {
//
// }).finally(function () {
// swal.close();
// });
// });
// }

/**
 * 根据流程类型获取不同的数据
 * 
 * @param self
 *            当前作用域
 * @param curData
 *            当前行数据
 * @param formType
 *            表单类型(请假,出差,预算等)
 * @param isApprove
 *            是否需要审批按钮
 */
// function getFormData(self,url,curData,isApprove){
// self.$http.get(url + curData.formId
// ).then(function (response) {
// var res = response.data;
// if (res.code == '1') {
// approveModel(res.data,curData,isApprove);
// }
// });
// }

/**
 * 审批页面
 * 
 * @param data
 * @param curData
 * @returns
 */
// function approveModel(data,curData,isApprove) {
// var vueModel = new Vue({
// el: '#container',
// mixins: [RocoVueMixins.DataTableMixin],
// data: {
// // 面包屑
// breadcrumbs: [{
// path: '/',
// name: '主页'
// }, {
// path: '/',
// name: '审批详情',
// active: true //激活面包屑的
// }],
// formType:curData.type,
// isApprove:isApprove,
// businessAway: data,//出差
// vacation:data, //请假
// purchase:data, //采购
// budget : data, //预算
// payment:data, //报销
// approveList:null, //审批轨迹
// form:{
// id:curData && curData.id || null,
// formId:curData && curData.formId || null,
// nodeId:curData && curData.nodeId || null,
// isSign:curData.isSign,
// type:curData && curData.type || null,
// resultStatus:null,
// remark: null,
// applyUserId:curData.applyUserId,
// applyCode:curData.applyCode,
// applyTitle:curData.applyTitle,
// approverId:curData.approverEmployee.id,
// }
// },
// methods: {
// getApproveList:function(){
// var self = this;
// self.$http.get("/api/wfmanager/wfApproveDetail/",{
// params:{
// 'formId':curData.formId,
// 'type':curData.type,
// 'showType':'approver'
// }
// }).then(function (response) {
// var res = response.data;
// if (res.code == '1') {
// self.approveList = res.data;
// }
// });
// },
// submit:function(resultStatus){
// var self = this;
// swal({
// title: '审批操作',
// text: '确定审批此流程？',
// type: 'info',
// confirmButtonText: '确定',
// cancelButtonText: '取消',
// showCancelButton: true,
// showConfirmButton: true,
// showLoaderOnConfirm: true,
// confirmButtonColor: '#ed5565',
// closeOnConfirm: false
// }, function () {
// var formData = _.cloneDeep(self.form);
// var data = {
// id:formData.id,
// nodeId:formData.nodeId,
// isSign:formData.isSign,
// type:formData.type,
// formId:formData.formId,
// approveResult:resultStatus,
// remark: formData.remark,
// applyUserId:formData.applyUserId,
// applyCode:formData.applyCode,
// applyTitle:formData.applyTitle,
// approverEmployee:{
// id:formData.approverId
// }
// };
// self.$http.post('/api/wfmanager/wfApprove', data).then(function (res) {
// if (res.data.code == 1) {
// self.$toastr.success('提交成功');
// setTimeout(function () {
// window.location.href="/index";
// }, 1500);
// }
// }).catch(function () {
//
// }).finally(function () {
// swal.close();
// });
// });
// }
// },
// created: function () {
// },
// ready: function () {
// this.getApproveList();
// }
// });
// }
Vue.filter('tielFilter', function (value) {
    return formApproveStatus(value);
});
// 更多操作
function moreExec(id,wfId,applyPerson){
	var _$modal = $('#moreModal').clone();
	var $modal = _$modal.modal({
		width: 680,
		maxWidth: 680,
		height: 190,
		maxHeight: 70,
		backdrop: 'static',
		keyboard: false
	});
	var editView = new Vue({
		el: $modal[0],
		data: {
			form: {
					id: id,
					wfId: wfId,
					applyPerson: applyPerson
				  },
			 weixin:false
		},
		ready: function () {
			this.isWeixin();
		},
		methods: {
			isWeixin: function () {
                var self = this;
                var ua = navigator.userAgent.toLowerCase();
                if (ua.match(/MicroMessenger/i) == "micromessenger") {
                    self.weixin = true;
                } else {
                    self.weixin = false;
                }
            },
			closeCommitaddApprover: function(){
				$modal.modal('hide');
        	},
			turnAdd:function(){
				turnAdd(id, wfId, applyPerson, "TURN");
				$modal.modal('hide');
			},
			addApproverBefore: function(){
				addApprover(id, wfId, applyPerson, "ADD", "BEFORE");
				$modal.modal('hide');
			},
			addApproverAfter: function(){
				addApprover(id, wfId, applyPerson, "ADD", "AFTER");
				$modal.modal('hide');
			},
			addCCuser: function(){
				addCCuser(id);
				$modal.modal('hide');
			}
			
		}
	}) 
}

function containsArr(arr, obj) {  
    var i = arr.length;  
    while (i--) {  
        if (arr[i] == obj) {  
            return true;  
        }  
    }  
    return false;  
}
function turnAdd(id,wfId,applyPerson,approveResult){
	var _$modal = $('#turnListModal').clone();
    var $modal = _$modal.modal({
    	width: 680,
        maxWidth: 680,
        height: 480,
        maxHeight: 480,
        backdrop: 'static',
        keyboard: false
    });
    var editView = new Vue({
  		  el: $modal[0],
  		  data: {
      	      form: {
      	        keyword: '',
      	        orgCode: '',
      	        orgName: '',
      	        employeeStatus: 'ON_JOB',
      	        addTable : "",
      	        formId: id,
      	        wfId: wfId,
      	        approveResult: approveResult,
      	        remark : ""
      	      },
      	      turnEmployees : [], // 转派人
      	      sourceApproveList :[]// 源审批列表
  		  },
      ready: function () {
    	  this.drawTable();
    	  this.initSelect();
    	  this.getApproveList(id);
      },
      methods: {
    	  turnCommitStylist: function(){
    		  var curData = {};
    		  var self = this;
    		  if(self.turnEmployees.length ==0){
    			  self.$toastr.error('转派操作没有添加目标人');
    			  return;
    		  }
    		  curData['id'] = this.form.wfId;
    		  curData['targetUserId'] = self.turnEmployees[0]['id'];
    		  curData['remark'] = self.form.remark;
    		  curData['approverEmployee'] = self.turnEmployees[0];
    		  quickApproveEx(self, curData, "TURN", vueIndex.$dataTableCommon);
    		  $modal.modal('hide');
    	  },
    	  getApproveList: function (id) {
              var self = this;
              self.$http.get("/api/wfmanager/wfApproveDetail/", {
                  params: {
                      'formId': id,
                      'type': 'COMMON',
                      'showType': 'personal' // personal-提交人,approver-审批人
                  }
              }).then(function (response) {
                  var res = response.data;
                  if (res.code == '1') {
                      self.approveList = res.data;
                      var isFilter = false;
                      var approveArray = [];
                     
                      res.data.forEach(function( val, index ) {
                    	  //判断逻辑出现两个以上的人位置判断不了
                    	  var appResultEmployeeId = val['approverEmployee']['id'];
                    	  var isConst = containsArr(applyPerson,appResultEmployeeId);
                    	  var isAdd = containsArr(approveArray,appResultEmployeeId);
                    	  if(isConst && !isAdd){  //原来有的人才添加进来,并且没有添加过
                    		  self.sourceApproveList.push(val.approverEmployee['id']);
                    		  approveArray.push(appResultEmployeeId);
                    	  }
						});
                  }
              });
          },
    	  initSelect : function(){
    		  var self = this;
    	// self.turnEmployees = vueIndex.turnEmployees;
    	  },
    	  drawTable : function () {
    	        var self = this;
    	        self.$dataTable = $('#turnListDataTable', self._$el).bootstrapTable({
    	          url: '/api/employees/applylist',
    	          method: 'get',
    	          dataType: 'json',
    	          pageSize : 5,
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
    	          columns: [{
                      field: 'id',
                      title: 'id',
                      align: 'center',
                      visible: false,
                      order: 'desc'
                  	},{
    	              field: 'orgCode',
    	              title: '工号',
    	              align: 'center'
        	         },{
    	              field: 'jobNum',
    	              visible: false,
    	              title: 'jobNum',
    	              align: 'center'
        	         }, {
    	              field: 'name',
    	              title: '姓名',
    	              align: 'center'
    	            }, {
    	                field: 'position',
    	                title: '职务',
    	                align: 'center'
    	            }, {
    	              field: 'mobile',
    	              title: '手机号',
    	              align: 'center'
    	            },{
                        field: '', // 将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                        	 var html = '<button style="margin-left:10px;" data-handle="select" data-id="' + row.id + '"  data-orgCode="' + row.orgCode + '" data-jobNum="' + row.jobNum + '" data-name="' + row.name + '" data-mobile="' + row.mobile + '" class="m-r-xs btn btn-xs btn-primary" type="button">选择</button>' ;
                             return html;
                        }
                    }]
    	        });
    	        self.$dataTable.on('click', '[data-handle="select"]', function (e) {
    	        	var id = $(this).data('id');  // 取到的是字符串
    	        	var orgCode = $(this).data('orgcode');  // 取到的是字符串
    	        	var jobNum = $(this).data('jobnum');  // 取到的是字符串
    	        	var name = $(this).data('name');  // 取到的是字符串
    	        	var mobile = $(this).data('mobile');  // 取到的是字符串
    	        	var obj = {'id':id,'jobNum':jobNum,'name':name,'mobile':mobile}
    	        	self.addRowItem(id, obj);
    	        });
    	        self.$dataTable.on('check.bs.table', function (e, row, ele,field) {
    	        		var obj = {'id':row.id,'jobNum':row.jobNum,'name':row.name,'mobile':row.mobile}
    	        	});
    	        self.$dataTable.on('uncheck.bs.table', function (e, row, ele,field) {
    	        		self.deleteItem(row);
    	        	});
    	        self.$dataTable.on('dbl-click-row.bs.table', function (e, row, ele,field) {
        	        	var obj = {'id':row.id,'jobNum':row.jobNum,'name':row.name,'mobile':row.mobile}
        	        	var targetId = row.id;
        	        	self.addRowItem(targetId, obj);
	        		});
    	      },
    	      query: function () {
    	          this.$dataTable.bootstrapTable('refresh', {pageNumber: 1});
    	     },
			deleteItem : function(item){
				var self = this;
				var targetId = item.id;
				var delIndex = -1;
       		$.each(self.turnEmployees, function(i,val){      
       			var eachId = val['id'];
       		    if(eachId == targetId)  {
       		    	delIndex = i;
       		    	return false;  
       		    }
       		  });
       		self.turnEmployees.splice(delIndex, 1);
			},
			checkApprove: function(targetId){
				var resultCheck = -1;
				this.sourceApproveList.forEach(function( val, index ) {
					if(val == targetId){
						resultCheck = index;
					}
			})
			return resultCheck;
			},
			addRowItem : function(targetId, obj){
				var self = this;
				var isAdd = -1;
				var resultCheck = self.checkApprove(targetId);
				if(resultCheck > -1){
					self.$toastr.error('新增抄送人['+obj['name']+']已在流程中,添加失败!');
					return;
				}
				if(self.turnEmployees.length > 0){
					self.$toastr.error('转派操作只能添加一个审批人');
					return;
				}
        	$.each(self.turnEmployees, function(i,val){      
       			var eachId = val['id'];
       		    if(eachId == targetId)  {
       		    	isAdd = i;
       		    	return false;  
       		    }
       		  });
        	if(isAdd == -1){
        		self.turnEmployees.push(obj);
        	}else{
        		self.$toastr.error('['+obj.name+']已选择！');
        	}
			}
      }
    }) 
}
function addApprover(id,wfId,applyPerson,approveResult,position){
	var _$modal = $('#addApproverModal').clone();
    var $modal = _$modal.modal({
    	width: 680,
        maxWidth: 680,
        height: 480,
        maxHeight: 480,
        backdrop: 'static',
        keyboard: false
    });
    var editView = new Vue({
  		  el: $modal[0],
  		  data: {
      	      form: {
      	        keyword: '',
      	        orgCode: '',
      	        orgName: '',
      	        employeeStatus: 'ON_JOB',
      	        addTable : "",
      	        formId: id,
      	        wfId: wfId,
      	        approveResult: approveResult,
      	        remark : "",
      	        currentUser : null
      	      },
      	    // turnEmployees: [], //转派人
      	      sourceApproveListAdd: [],// 源审批列表
      	      newApprove: null,
      	     // newApproveListAdd: [],//源审批列表
      	      modelTitle: "",
      	      resultAdd: false,  // 是否已经添 加过
      	      addIndex: -1  // 当前添加方式上(前/后)的目标位置
  		  },
      ready: function () {
    	  this.drawTable();
    	  this.initSelect();
    	  this.getApproveList(id);
    	  this.initCurrentLoginUser(); //实始化当前登陆用户信息
    	  if(position == "BEFORE"){
    		  this.modelTitle = "前添加审批人";
    	  }else{
        	  this.modelTitle = " 后添加审批人";
    	  }
    	  
      },
      methods: {
    	  initCurrentLoginUser: function(){
    		  var self = this;
    		  self.currentUser = RocoUser;
    	  },
    	  // 计算审批人插入的位置,
    	  execAddPosition: function(sourceList){
    		var self = this;
    		var targetIndex = -1;
    		var currentUserId = RocoUser.userId;
			$.each(sourceList, function(i,val){      
       			var eachId = val['id'];
       		    if(eachId == currentUserId)  {
       		    	targetIndex = i;
       		    }
       		  });
			// 前添加,是当前位置+1, 前添加,自己后移一个,
			if(position == 'AFTER'){  
				targetIndex ++;
			}
			self.addIndex = targetIndex;
    	  },
    	  commitaddApprover: function(){
    		  var self = this;
    		  if(self.resultAdd == false && self.newApprove==null){
    			  self.$toastr.error('当前还没有新添加审批人!');
    			  return;
    		  }else{
    			  // self, curData, resultStatus, tableObj
    			  var curData = {};
    			  curData['id'] = wfId;
    			  curData['formId'] = id;
    			  curData['targetUserId'] = self.newApprove['id'];
    			  curData['remark'] = self.form.remark;
    			  curData['currentApprove'] = JSON.stringify(self.sourceApproveListAdd);
    			  // 位置加结果
    			  quickApproveEx(self, curData, approveResult + position, vueIndex.$dataTableCommon);
    		  }
    		  $modal.modal('hide');
    	  },
    	  getApproveList: function (id) {
              var self = this;
              self.$http.get("/api/wfmanager/wfApproveDetail/", {
                  params: {
                      'formId': id,
                      'type': 'COMMON',
                      'showType': 'personal' // personal-提交人,approver-审批人
                  }
              }).then(function (response) {
                  var res = response.data;
                  if (res.code == '1') {
                      self.approveList = res.data;
                      var approveArray = [];
                      var map = {};
                      res.data.forEach(function( val, index ) {
                    	  var appResultEmployee = val['approverEmployee'];
                    	  var appResultEmployeeId = appResultEmployee['id'];
                    	  map[appResultEmployeeId] = appResultEmployee;
                    	  var isConst = containsArr(applyPerson,appResultEmployeeId);
                    	  var isAdd = containsArr(approveArray,appResultEmployeeId);
                    	  //不重复,并且没有添加过
                    	  if(isConst && !isAdd){  //原来有的人才添加进来
                    		  self.sourceApproveListAdd.push(val['approverEmployee']);
                    		  approveArray.push(appResultEmployeeId);
                    	  }
						});
                       var newApproveList = [];
                       applyPerson.forEach(function( val, index ) {
                    	   newApproveList.push(map[val])
                       });
						// 计算目标位置
						self.execAddPosition(self.sourceApproveListAdd);
                  }
              });
          },
    	  initSelect : function(){
    		  var self = this;
    	// self.sourceApproveListAdd = vueIndex.sourceApproveListAdd;
    	  },
    	  drawTable : function () {
    	        var self = this;
    	        self.$dataTable = $('#addApproverDataTable', self._$el).bootstrapTable({
    	          url: '/api/employees/applylist',
    	          method: 'get',
    	          pageSize : 5,
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
    	          columns: [{
                      field: 'id',
                      title: 'id',
                      align: 'center',
                      visible: false,
                      order: 'desc'
                  	},{
    	              field: 'orgCode',
    	              title: '工号',
    	              align: 'center'
        	         },{
    	              field: 'jobNum',
    	              visible: false,
    	              title: 'jobNum',
    	              align: 'center'
        	         }, {
    	              field: 'name',
    	              title: '姓名',
    	              align: 'center'
    	            }, {
    	                field: 'position',
    	                title: '职务',
    	                align: 'center'
    	            }, {
    	              field: 'mobile',
    	              title: '手机号',
    	              align: 'center'
    	            },{
                        field: '', // 将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                        	 var html = '<button style="margin-left:10px;" data-handle="select" data-id="' + row.id + '"  data-orgCode="' + row.orgCode + '" data-jobNum="' + row.jobNum + '" data-name="' + row.name + '" data-mobile="' + row.mobile + '" class="m-r-xs btn btn-xs btn-primary" type="button">选择</button>' ;
                             return html;
                        }
                    }]
    	        });
    	        self.$dataTable.on('click', '[data-handle="select"]', function (e) {
    	        	var id = $(this).data('id');  // 取到的是字符串
    	        	var orgCode = $(this).data('orgcode');  // 取到的是字符串
    	        	var jobNum = $(this).data('jobnum');  // 取到的是字符串
    	        	var name = $(this).data('name');  // 取到的是字符串
    	        	var mobile = $(this).data('mobile');  // 取到的是字符串
    	        	var obj = {'id':id,'jobNum':jobNum,'name':name,'mobile':mobile}
    	        	self.addRowItem(id, obj);
    	        });
    	        self.$dataTable.on('check.bs.table', function (e, row, ele,field) {
    	        		var obj = {'id':row.id,'jobNum':row.jobNum,'name':row.name,'mobile':row.mobile}
    	        	});
    	        self.$dataTable.on('uncheck.bs.table', function (e, row, ele,field) {
    	        		self.deleteItem(row);
    	        	});
    	        self.$dataTable.on('dbl-click-row.bs.table', function (e, row, ele,field) {
        	        	var obj = {'id':row.id,'jobNum':row.jobNum,'name':row.name,'mobile':row.mobile}
        	        	var targetId = row.id;
        	        	self.addRowItem(targetId, obj);
	        		});
    	      },
    	      query: function () {
    	          this.$dataTable.bootstrapTable('refresh', {pageNumber: 1});
    	     },
			deleteItem : function(item){
				var self = this;
				var targetId = item.id;
				var delIndex = -1;
       		$.each(self.sourceApproveListAdd, function(i,val){      
       			var eachId = val['id'];
       		    if(eachId == targetId)  {
       		    	delIndex = i;
       		    	return false;  
       		    }
       		  });
       		self.sourceApproveListAdd.splice(delIndex, 1);
       		self.resultAdd = false;
       		self.newApprove = null;  // 新加的项
			},
			checkAddApprove: function(sourceList,targetId){
				var resultCheck = -1;
				sourceList.forEach(function( val, index ) {
					if(val['id'] == targetId){
						resultCheck = index;
					}
			})
			return resultCheck;
			},
			addRowItem : function(targetId, obj){
				var self = this;
				var isAdd = -1;
				var resultCheck = self.checkAddApprove(self.sourceApproveListAdd,targetId);
				if(resultCheck > -1){
					self.$toastr.error('新添加的审批人['+obj['name']+']已在流程中,添加失败!');
					return;
				}
				if(self.resultAdd){  // 是否已经添加过
					self.$toastr.error('只能添加一个审批人');
					return;
				}
        	console.log('addIndex: ' + self.addIndex);
        	self.sourceApproveListAdd.splice(self.addIndex, 0, obj); 
        	self.newApprove = obj;
        	self.resultAdd = true;
			}
      }
    }) 
}
function addCCuser(id){
	var _$modal = $('#ccPersonStylistModal').clone();
    var $modal = _$modal.modal({
    	width: 680,
        maxWidth: 680,
        height: 480,
        maxHeight: 480,
        backdrop: 'static',
        keyboard: false
    });
    var editView = new Vue({
  		  el: $modal[0],
  		  data: {
      	      form: {
      	        keyword: '',
      	        orgCode: '',
      	        orgName: '',
      	        employeeStatus: 'ON_JOB',
      	        addTable : "",
      	        formId: id,
      	        remark: ""
      	      },
      	      newCCPersonInfo: [],  // 新添加的抄送人
      	      sourceCCPersonInfo: [],// 源抄送列表
      	      sourceCCPersonName: "",
      	      sourceCCPerson:""// 抄送人id
  		  },
      ready: function () {
    	  this.drawTable();
    	  this.initSelect();
    	  this.getApplyCommInfo(id);
      },
      methods: {
    	  getApplyCommInfo: function (id) {
              var self = this;
              self.$http.get("/api/applyCommon/info/{id}", {
                  params: {
                      'id': id // personal-提交人,approver-审批人
                  }
              }).then(function (response) {
                  var res = response.data;
                  if (res.code == '1') {
                  	self.sourceCCPersonInfo = $.parseJSON(res.data.ccPersonInfo);
                  	self.sourceCCPersonName = res.data.ccPersonName;
                  	self.sourceCCPerson = res.data.ccPerson;
                  }
              });
          },
    	  initSelect : function(){
    		  var self = this;
    	  },
    	  drawTable : function () {
    	        var self = this;
    	        self.$dataTable = $('#ccPersonDataTable', self._$el).bootstrapTable({
    	          url: '/api/employees/applylist',
    	          method: 'get',
    	          pageSize : 5,
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
    	          columns: [{
                      field: 'id',
                      title: 'id',
                      align: 'center',
                      visible: false,
                      order: 'desc'
                  	},{
    	              field: 'orgCode',
    	              title: '工号',
    	              align: 'center'
        	         },{
    	              field: 'jobNum',
    	              visible: false,
    	              title: 'jobNum',
    	              align: 'center'
        	         }, {
    	              field: 'name',
    	              title: '姓名',
    	              align: 'center'
    	            }, {
    	                field: 'position',
    	                title: '职务',
    	                align: 'center'
    	            }, {
    	              field: 'mobile',
    	              title: '手机号',
    	              align: 'center'
    	            },{
                        field: '', // 将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                        	 var html = '<button style="margin-left:10px;" data-handle="select" data-id="' + row.id + '"  data-orgCode="' + row.orgCode + '" data-jobNum="' + row.jobNum + '" data-name="' + row.name + '" data-mobile="' + row.mobile + '" class="m-r-xs btn btn-xs btn-primary" type="button">选择</button>' ;
                             return html;
                        }
                    }]
    	        });
    	        self.$dataTable.on('click', '[data-handle="select"]', function (e) {
    	        	var id = $(this).data('id');  // 取到的是字符串
    	        	var orgCode = $(this).data('orgcode');  // 取到的是字符串
    	        	var jobNum = $(this).data('jobnum');  // 取到的是字符串
    	        	var name = $(this).data('name');  // 取到的是字符串
    	        	var mobile = $(this).data('mobile');  // 取到的是字符串
    	        	var obj = {'id':id,'jobNum':jobNum,'name':name,'mobile':mobile}
    	        	self.addRowItem(id, obj);
    	        });
    	        self.$dataTable.on('check.bs.table', function (e, row, ele,field) {
    	        		var obj = {'id':row.id,'jobNum':row.jobNum,'name':row.name,'mobile':row.mobile}
    	        	});
    	        self.$dataTable.on('uncheck.bs.table', function (e, row, ele,field) {
    	        		self.deleteItem(row);
    	        	});
    	        self.$dataTable.on('dbl-click-row.bs.table', function (e, row, ele,field) {
        	        	var obj = {'id':row.id,'jobNum':row.jobNum,'name':row.name,'mobile':row.mobile}
        	        	var targetId = row.id;
        	        	self.addRowItem(targetId, obj);
	        		});
    	      },
    	      query: function () {
    	          this.$dataTable.bootstrapTable('refresh', {pageNumber: 1});
    	     },
			deleteItem : function(item){
				var self = this;
				var targetId = item.id;
				var delIndex = -1;
       		$.each(self.newCCPersonInfo, function(i,val){      
       			var eachId = val['id'];
       		    if(eachId == targetId)  {
       		    	delIndex = i;
       		    	return false;  
       		    }
       		  });
       		self.newCCPersonInfo.splice(delIndex, 1);
			},
			checkCCUser: function(targetId){
				var self = this;
				var resultCheck = -1;
				// 源列表和新列表都做一下判断
				self.sourceCCPersonInfo.forEach(function( val, index ) {
					if(val['id'] == targetId){
						resultCheck = index;
					}
			})
			this.newCCPersonInfo.forEach(function( val, index ) {
				if(val['id'] == targetId){
					resultCheck = index;
				}
			})
			return resultCheck;
			},
			addRowItem : function(targetId, obj){
				var self = this;
				var isAdd = -1;
				console.log('targetId ' + targetId)
				
				var resultCheck = self.checkCCUser(targetId);
				if(resultCheck > -1){
					self.$toastr.error('新增抄送人['+obj['name']+']已在流程中,添加失败!');
					return;
				}
        	$.each(self.newCCPersonInfo, function(i,val){      
       			var eachId = val['id'];
       		    if(eachId == targetId)  {
       		    	isAdd = i;
       		    	return false;  
       		    }
       		  });
        	if(isAdd == -1){
        		self.newCCPersonInfo.push(obj);
        	}else{
        		self.$toastr.error('['+obj.name+']已选择！');
        	}
			},
			commitCCUser : function(){
				var self = this;
				var formData = {};
				var allCCuserInfo = [];
				var allCCuser = [];
				var allCCuserName = [];
				// 合并原来的和新添加的
				$.each(self.newCCPersonInfo, function(i,val){  
					allCCuserInfo.push(val);
					allCCuser.push(val['id']);
					allCCuserName.push(val['name'])
				});
				$.each(self.sourceCCPersonInfo, function(i,val){  
					allCCuserInfo.push(val);
					allCCuser.push(val['id']);
					allCCuserName.push(val['name'])
				});
				formData['id'] = id;
				formData['ccUsers'] = JSON.stringify(allCCuserInfo);
				formData['ccUserIds'] = allCCuser.join(',');
				formData['ccUserNames'] = allCCuserName.join(',');
				
				Vue.http.post('/api/applyCommon/addCCUser', formData,{headers: {},emulateJSON: true}).then(function(response) {
	            		var responseText = response.data;
	            		if(responseText['code'] == '1'){
	            			self.$toastr.success(responseText['message']);
	            			// 刷新列表
	            			var currentUrl = window.location.href;
	    					// 说明是info页,处理完以后,直接刷新当前页
	    					if(currentUrl.indexOf('/info=')){
	    						window.location.reload();
	    					}else{
	    						vueIndex.$dataTableCommon.bootstrapTable('refresh')
	    					}
	            			$modal.modal('hide');
	            		}else{
	            			self.$toastr.error(responseText['message']);
	            		}
	            	    }, function(response) {
	            	        console.log(response)
	            	    });
			}
      }
	}) 
}