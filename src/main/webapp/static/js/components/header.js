/**
 * 2017-5-17 Andy 修改
 * @description 
 * 新增 weixin:false参数
 * 新增 isWeiXin 方法,判断是否微信.
 */
(function() {
	$(function() {
		var header = new Vue({
			el : '#header',
			data : {
//				isShow : false
			},
			methods : {
				logout : function() {
					var self = this;
					self.$http.get('/api/logout').then(
							function(response) {
//								window.location.href = ctx + "/login";
								window.location.href = ctx + "/logout";
							});
				}
//				isWeixin : function() {
//					var self = this;
//					var ua = navigator.userAgent.toLowerCase();
//					if (ua.match(/MicroMessenger/i) == "micromessenger") { //微信
//						self.isShow = false; //微信不显示菜单
//					} else {
//						self.isShow = true;  //PC显示菜单
//					}
//				}
			},
			created : function() {
//				this.isWeixin();
			}
		});
	});
})();

/**
 * 根据传入的流程类型返回中文
 * @param value 类型
 */
function formApproveStatus(value){
	  var label = "";
    switch (value) {
	      case 'BUSINESS':
	    	  label = '出差';
	    	  break;
	      case 'LEAVE':
	    	  label = '请假';
	    	  break;
	      case 'BUDGET':
	    	  label = '预算';
	    	  break;
	      case 'YEARBUDGET':
	    	  label = '年预算';
	    	  break;	    	  
	      case 'EXPENSE':
	    	  label = '报销';
	    	  break;
	      case 'PURCHASE':
	    	  label = '采购';
	    	  break;
	      case 'SIGNATURE':
	    	  label = '费用';
	    	  break;
	     case 'COMMON':
		    label = '通用';
		     break;	    	  
	      default:
	    	  label = value;
	      	  break;
    }
    return label;
}