+(function () {
  var detail = new Vue({
    el: '#container',
    http: {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    },
    data: {
    	item: null,
    	submitting: false
    },
    created: function () {
    },
    ready: function () {
    	this.initFunction();
    },
    methods: {
    	//通过任务id查询任务信息
    	initFunction: function(){
    		 var self = this;
             var link = location.href;
             if(link.indexOf("?taskId=") != -1 ){
            	 var taskId = link.split("?taskId=")[1];
            	 var ts = link.split("&ts=")[1];
            	 if(ts == null || ts == undefined){
            		 ts = "";
            	 }
            	 self.$http.get("/api/crm/crmBusiness/getTaskById?taskId="+ taskId + "&ts=" + ts ).then(function (res) { 
            		 if (res.data.code == 1) {
            			 self.item = res.data.data;
            		 } else {
            			 Vue.toastr.error(res.data.msg);
            		 }
            	 }).catch(function () {
            		 
            	 }).finally(function () {
            	 });
             }
    	},
    	 //改变状态
        changeTaskStatusById: function(){
        	var self = this;
        	var taskId = "";
        	var link = location.href;
        	if(link.indexOf("?taskId=") != -1 ){
				taskId = link.split("?taskId=")[1];
        	}
        	//禁用
        	self.submitting = true;
        	$.ajax({
                url:"/api/crm/crmBusiness/updateTaskStatusById",
                data: {
                	"taskId": taskId
                },
                dataType:"json",
                success:function(data){
                	if(data.code == 1){
                		//更新成功
                		Vue.toastr.success("操作成功!");
                		//定时返回列表页面
			    		window.setTimeout(function(){
			    			window.location.href = "/oldCrm/list";
			    		},1500);
                	}else{
                		//更新失败
                		Vue.toastr.error(data.msg);
                		//可用
                		self.submitting = false;
                	}
                }
            }); 
            
        }
    }
  });
  
  //格式化时间
  Vue.filter('time', function (value) {
  	if(value != null && value != undefined && value.trim() != ""){
  		if(value.lastIndexOf(".") != -1){
  			return value.substr(0,value.lastIndexOf("."));
  		}
  		return value;
  	}
  	return "";
  })
})();