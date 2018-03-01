var vueIndex = null;
+(function() {
	$('#leveAndBusinessMenu').addClass('active');
	$('#leveAndBusinessMenu ul').addClass('in');
	$('#myLevAffair').addClass('active');
	// 获取url中的参数
	vueIndex = new Vue({
		el : '#container',
		data : {
			// 面包屑
			breadcrumbs : [ {
				path : '/',
				name : '主页'
			}, {
				path : '/',
				name : '通用申请',
				active : true
				// 激活面包屑的
			} , {
				path : '/',
				name : '详情',
				active : true
				// 激活面包屑的
			} ], 
			form : {
				applyTime : '', 
				serialNumber : '',  // 编号
				photoNums : 0,
				submitUserName : "",
				title : "",
				content : "",
				ctime : "",
				ccPersonName : "",
				subOrgName:"",// 提交人部门
				orgName:"",// 提交人所属机构
				// 以下是流程驱动信息
				id : null,
				formId : null,
				nodeId : null,
				isSign : null,
				type : null,
				applyUserId : null,
				applyCode : null,
				applyTitle : null,
				approverId : null,
				applyPerson : null
			},
			photoSrcs : [],  // 图片集合
			accessories : [],  // 附件集合
			applyPerson : [],  // 审批人
    	    ccPersons : [],  // 抄送人
			approveList : [],   // 表格数据
			isStatus :  false
		},
		validators : {},
		created : function() {
			var self = this;
			// 处理URL的ID参数
			var id = this.$parseQueryString()['id'];
			 
        	Vue.http.post('/api/applyCommon/info', {id:id, t: new Date().getTime()},{headers: {},emulateJSON: true}).then(function(response) {
        		var responseText = response.data;
        		var rdata = responseText.data;
        		self.form.serialNumber = rdata.serialNumber;
        		self.form.applyTime = rdata.applyTime;
        		self.form.submitUserName = rdata.submitUserName;
        		self.form.title = rdata.title;
        		self.form.content = rdata.content;
        		self.form.ccPersonName = rdata.ccPersonName;
        		self.form.subOrgName = rdata.subOrgName;
        		self.form.orgName = rdata.orgName;
        		self.form.applyPerson = rdata.applyPerson;
        		self.photoSrcs = $.parseJSON(rdata.photos); 
        		self.accessories = $.parseJSON(rdata.accessories); 
        		self.applyPerson = $.parseJSON(rdata.applyPersonInfo); 
        		self.ccPersons = $.parseJSON(rdata.ccPersonInfo); 
        		$.each(self.applyPerson, function(i,val){      
	       			var id = val['id']
	       		  });
        	    }, function(response) {
        	    });
        	// 审批详情
        	
			self.getApproveList(id);
			// 初始化事件
		},
		ready : function() {
			
// self.imageMaxView();
			
		},
		methods : {
			imageMaxView : function(){
				$('#view-images').viewer({toolbar:false});
			},
            submit: function (resultStatus) {
                var self = this;
                swal({
                    title: '审批操作',
                    text: '确定审批此流程？',
                    type: 'info',
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    showCancelButton: true,
                    showConfirmButton: true,
                    showLoaderOnConfirm: true,
                    confirmButtonColor: '#ed5565',
                    closeOnConfirm: false
                }, function () {
                    var formData = _.cloneDeep(self.form);
                    var data = {
                        id: formData.id,
                        nodeId: formData.nodeId,
                        isSign: formData.isSign,
                        type: formData.type,
                        formId: formData.formId,
                        approveResult: resultStatus,
                        remark: formData.remark,
                        applyUserId: formData.applyUserId,
                        applyCode: formData.applyCode,
                        applyTitle: formData.applyTitle,
                        approverEmployee: {
                            id: formData.approverId
                        }
                    };
                    self.$http.post('/api/wfmanager/wfApprove', data).then(function (res) {
                        if (res.data.code == 1) {
                            self.$toastr.success('提交成功');
                            setTimeout(function () {
                            	// 审批后跳转到待指批列表
                                window.location.href = "/admin/approval";
                            }, 500);
                        }else{
                        	self.$toastr.error(res.data.message);
                        }
                    }).catch(function () {

                    }).finally(function () {
                        swal.close();
                    });
                });
            },
            moreExecApprovalDetail: function(id, wfId,applyPerson){
            	//转字符串 number没能indexOf方法
            	var applyPersonArrays= [];
            	var applyPersonTmp = applyPerson + "";
            	if(applyPersonTmp.indexOf(',') > -1){
            		applyPersonArrays = applyPersonTmp.split(",");// 
            	}else{
            		applyPersonArrays.push(applyPersonTmp);
            	}
            	moreExec(id, wfId, applyPersonArrays);
            },
            getApprovalData: function (id) {
                var self = this;
                self.$http.get('/api/wfmanager/getWfApprove/' + id).then(function (res) {
                    if (res.data.code == 1) {
                        var curData = res.data.data;
                        self.form.id = curData.id;
                        self.form.formId = curData.formId;
                        self.form.nodeId = curData.nodeId;
                        self.form.isSign = curData.isSign;
                        self.form.type = curData.type;
                        self.form.applyUserId = curData.applyUserId;
                        self.form.applyCode = curData.applyCode;
                        self.form.applyTitle = curData.applyTitle;
                        self.form.approverId = curData.approverEmployee.id;
                       
                    }
                }).catch(function () {

                }).finally(function () {
                });
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
                        res.data.forEach(function( val, index ) {
							if(val['status'] == 'PENDING'){
								if(val.approverEmployee['id'] == RocoUser.userId ){
									self.isStatus = true;  // 当前登陆用户和待审批用户一致,表示可以审批
									var wfInfoId  = val['id'];  // 得到流程驱动ID
									self.getApprovalData(wfInfoId);  // 给要提交对象赋流程驱动信息
								}
							}
						})
                    }
                });
            },
            // 实始化
            initValues : function(id){},
			downloadFile: function (src,index,type) {
				 window.location.href = "/api/applyCommon/download?file=" + src['path']+"&fileName=" + src['fileName']
			},
			load : function(){
				
			},
			// 如果传空串，spring反序列化json串时，遇到枚举会报错，所以只传有值得属性
			cancel : function() {
				window.history.go(-1);
			},
			imageView : function(img){
         	    var _$modal = $('#imageViewMax').clone();
	            var $modal = _$modal.modal({
	            	width: document.body.clientWidth,
                    height: document.body.clientHeight,
	                backdrop: 'static',
	                keyboard: false
	            });
	            var editView = new Vue({
	          		  el: $modal[0],
	          		  data: {
	          			imgPath : ""
	          		  },
	              ready: function () {
	            	  var self = this;
	            	  self.imgPath = img.path
	              },
	              methods: {}
          		}) 
			
			}
		}
	});
	vueIndex.$watch('photoSrcs',function(){
        $('#view-images').viewer();
    });

	// 添加教育经历
	// 添加工作经历
	function workModal($el, model, isEdit, parent) {
	}
})();
Vue.filter('formatData', function(value,type) {
	  var lable = "";
	  if(type == "status"){
	      switch (value) {
	      case 'PENDING':
	    	lable="待审批";
	        break;
	      case 'INIT':
	    	lable="未开始";
	        break;
	      case 'COMPLETE':
	      	lable="已审批";    	  
	        break;
	      case 'RESET':
	    	  lable="已撤回";    	  
	    	  break;
	      case 'TURN':
	    	  lable="转派";    	  
	    	  break;
	      case 'ADD':
	    	  lable="添加";    	  
	    	  break;
	      case 'INVALIDATE':
	    	  lable="失效";    	  
	    	  break;
	      default:
	        break;
  	}
	  }else if(type == "result"){
		  if(value != null && value!=""){
		      if(value =="AGREE"){
		    	  lable = "同意";
		      }else if( value =="REFUSE" ){
				  lable = "拒绝";
			  }else if( value =="RESET" ){
				  lable = "已撤回";
			  }else if( value =="ADDBEFORE" ){
				  lable = "之前添加";
			  }else if( value =="ADDAFTER" ){
				  lable = "之后添加";
			  }
		  }else{
			  lable = "-"
		  }
	  }else if(type == "time"){
		  if(value != null && value!=""){
		      lable = value;
		  }else{
			  lable = "-"
		  }
	  }else if(type == "css"){
	      if(value =="AGREE"){
	    	  lable = "#00DD00";
	      }else if( value =="REFUSE" ){
			  lable = "#FF0000";
		  }
	  }
  return lable;
});