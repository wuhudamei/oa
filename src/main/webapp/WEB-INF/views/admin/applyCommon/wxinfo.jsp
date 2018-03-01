<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<title>通用申请</title>
<div id="container" class="wrapper wrapper-content">
	<div id="breadcrumb">
		<bread-crumb :crumbs="breadcrumbs"></bread-crumb>
	</div>
	<!-- ibox start -->
	<div class="ibox" v-cloak>
		<div class="ibox-content">
			<h2 class="text-center">通用申请</h2>
			<form  novalidate class="form-horizontal" role="form">
				<div class="form-group">
					<div class="col-md-2 text-left">
						<label>申请编码：</label>
					</div>
					<div class="col-md-4 text-left">
						<label >{{form.serialNumber}}</label>
					</div>
					
				</div>
				<div class="form-group">
					<div class="col-md-2 text-left">
						<label>申请标题：</label>
					</div>
					<div class="col-md-4">
						<label>{{form.title}}</label>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-2 text-left">
						<label>申请时间：</label>
					</div>
					<div class="col-md-4">
						<label>{{form.applyTime}}</label>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-2 text-left">
						<label>申请内容：</label>
					</div>
					<div class="col-md-4">
						<label>{{form.content}}</label>
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-md-2 text-left">
						<label>照片：</label>
					</div>
					<div class="col-md-8">
						<label id="view-images">
							<label v-for="src in photoSrcs"> <img
							v-model="form.photoSrcs"  :src="src.path" alt=""
							style="width: 105px; margin-left: 60px">
							</label>
						</label>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-2 text-left">
						<label>附件：</label>
					</div>
					<div class="col-md-10">
						<label v-for="src in accessories"> 
						{{src.fileName}}
							<i class="fa fa-fw fa-download" style="height: 15px; width: 55px;" @click="downloadFile(src,$index,'accessories')">
							<font color="blue">下载</font>
							</i>&nbsp;&nbsp;
						</label>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-2 text-left">
						<label>审批信息</label>
					</div>
				</div>
				<div class="form-group">
					<table class="table table-striped table-bordered table-hover">
						<tr>
							<td align="center" width="20%">审批人</td>
							<td align="center" width="20%">状态</td>
							<td align="center" width="15%">审批结果</td>
							<td align="center" width="20%">审批时间</td>
							<td align="center" width="30%">备注</td>
						</tr>
						<tr v-for="approve in approveList">
							<td align="center">
								{{approve.approverEmployee.name}}
							</td>
							<td align="center" style="color: blue">
								{{approve.status | formatData 'status'}}
							</td>
							<td align="center" style="color: {{approve.approveResult | formatData 'css'">
								{{approve.approveResult | formatData 'result'}}</td>
							<td align="center">
								{{approve.approveTime | formatData 'time'}}</td>
							<td align="center">{{approve.remark}}</td>
						</tr>
					</table>
				</div>
				<div class="form-group">
					<div class="col-md-2 text-left">
						<label>抄送信息：</label>
					</div>
					<div class="col-md-4">
						<label>{{form.ccPersonName}}</label>
					</div>
				</div>
				<div class="text-center">
				</div>
			</form>
		</div>
	</div>
	</validator>
	<!-- ibox end -->
</div>
</div>
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