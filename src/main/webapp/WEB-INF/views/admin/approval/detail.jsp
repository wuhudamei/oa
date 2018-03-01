<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div style="border-top: 1px solid #999;margin-bottom: 10px"></div>
<div style="font-size: 16px;font-weight:bold;margin-bottom: 10px;">审批信息</div>
<table class="table table-striped table-bordered table-hover">
	<tr>
		<td align="center" width="15%">审批人</td>
		<td align="center" width="10%">状态</td>
		<td align="center" width="15%">审批结果</td>
		<td align="center" width="20%">审批时间</td>
		<td align="center" width="50%">备注</td>
	</tr>
	<tr v-for="approve in approveList">
		<td align="center">{{approve.approverEmployee.name}}</td>
		<td align="center" style="color:blue">{{approve.status | formatData 'status'}}</td>
		<td align="center" style="color:{{approve.approveResult | formatData 'css'}}">{{approve.approveResult | formatData 'result'}}</td>
		<td align="center">{{approve.approveTime | formatData 'time'}}</td>
		<td align="center">{{approve.remark}}</td>
	</tr>
</table>
<script type="text/javascript">
/**
 * 格式化参数
 * @param value
 * @param type
 */
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
	      default:
	        break;
    	}
	  }else if(type == "result"){
		  if(value != null && value!=""){
		      if(value =="AGREE"){
		    	  lable = "同意";
		      }else if( value =="REFUSE" ){
				  lable = "拒绝";
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
</script>
