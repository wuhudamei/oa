var vueIndex = null;
+(function() {
	// 获取url中的参数
	$('#leveAndBusinessMenu').addClass('active');
	$('#leveAndBusinessMenu ul').addClass('in');
	$('#myLevAffair').addClass('active');
	vueIndex = new Vue({
		el : '#container',
		data : {
			// 面包屑
			breadcrumbs : [ {
				path : '/',
				name : '主页'
			}, {
				path : '/',
				name :'请假申请详情',
				active : true
				// 激活面包屑的
			} , {
				path : '/',
				name : '详情',
				active : true
				// 激活面包屑的
			} ], 
			isStatus :  false,
			approveList : [],   //表格数据
            vacation : null,
			isStatus : false,  //当前登陆人能不能审批
			wfInfoId : null    //能审批的人审批的结点id
		},
		validators : {},
		created : function() {
			var self = this;
		},
		ready : function() {
			var id = this.$parseQueryString()['id']
			this.getApproveList(id);
			this.getApprovalDetailData(id);
		},
		methods : {
			//在前台构造对象，实际上把ID和审批内容传到到后台就好了，
			//原com.rocoinfo.rest.oa.WfManagerController.wfApprove(WfProcess, HttpServletRequest)
			//新com.rocoinfo.rest.oa.WfManagerController.wfApproveExec(Long, String, String, HttpServletRequest)
			//方法接收对象
		    getApproveList: function (id) {
                var self = this;
    	    	Vue.http.get("/api/wfmanager/wfApproveDetail", {
                    params: {
                        'formId': id,
                        'type': 'LEAVE',
                        'showType': 'personal'
                    }
                }).then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.approveList = res.data;
                        self.approveList.forEach(function( val, index ) {
							if(val['status'] == 'PENDING'){
								if(val.approverEmployee['id'] == RocoUser.userId ){
									self.isStatus = true;  // 当前登陆用户和待审批用户一致,表示可以审批
									self.wfInfoId  = val['id'];  // 得到流程驱动ID  /
								}
							}
						})
                    }
                });
            },
            getApprovalDetailData: function (id) {
                var self = this;
                var url = "/api/vacations/" + id;
                Vue.http.get(url).then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                    	self.vacation = res.data;
                    }
                });
            },
            submit: function (resultStatus) {
            	var self = this;
            	if(null != self.wfInfoId){
            		
            	}
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
                    	"remark" : formData.remark,
                    	"approveResult" : resultStatus,
                    	"id" : self.wfInfoId
                    };
                    //重构原来的审批方法，只传审批的结果和待审批的流程ID,不在页面构造WFPROCESS对象
                    self.$http.post('/api/wfmanager/execWfApprove', data, {emulateJSON: true}).then(function (res) {
                        if (res.data.code == 1) {
                            self.$toastr.success('提交成功');
                            setTimeout(function () {
                            	// 审批后跳转到待指批列表
                                window.location.href = "/admin/approval";
                            }, 500);
                        }
                    }).catch(function () {

                    }).finally(function () {
                        swal.close();
                    });
                });
            },
			// 如果传空串，spring反序列化json串时，遇到枚举会报错，所以只传有值得属性
			cancel : function() {
				window.history.go(-1);
			},
		}
	});
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