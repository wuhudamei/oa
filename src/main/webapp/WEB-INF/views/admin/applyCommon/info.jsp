<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<link href="${ctx}/static/vendor/viewer/viewer.css" rel="stylesheet">
<title>通用申请</title>
<div id="container" class="wrapper wrapper-content">
	<div id="breadcrumb">
		<bread-crumb :crumbs="breadcrumbs"></bread-crumb>
	</div>
	<!-- ibox start -->

	<div class="ibox" v-cloak>
		<div class="ibox-content">
			<h2 class="text-center">通用申请</h2>
			<form novalidate class="form-horizontal" role="form">
				<div class="form-group">
					<div class="col-md-2 text-left">
						<label>申请编码：</label>
					</div>
					<div class="col-md-4 text-left">
						<label>{{form.serialNumber}}</label>
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
						<label>申请人：</label>
					</div>
					<div class="col-md-4">
						<label>{{form.submitUserName}}</label>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-2 text-left">
						<label>申请公司：</label>
					</div>
					<div class="col-md-4">
						<label>{{form.orgName}}</label>
					</div>
				</div>
				<div class="form-group">
					<div class="col-md-2 text-left">
						<label>申请部门：</label>
					</div>
					<div class="col-md-4">
						<label>{{form.subOrgName}}</label>
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
					<div class="col-md-8" >
						<label id="view-images">
							<label id="imageView" v-for="src in photoSrcs"> 
							<img
								:data-original="src.path" v-model="form.photoSrcs"
								:src="src.path" 
								alt="图片"
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
						<label v-for="src in accessories"> {{src.fileName}} <i
							class="fa fa-fw fa-download" style="height: 15px; width: 55px;"
							@click="downloadFile(src,$index,'accessories')"> <font
								color="blue">下载</font>
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
							<td align="center" width="35%">备注</td>
						</tr>
						<tr v-for="approve in approveList">
							<td align="center">{{approve.approverEmployee.name}}</td>
							<td align="center" style="color: blue">{{approve.status |
								formatData 'status'}}</td>
							<td align="center"
								style="color: {{approve.approveResult| formatData 'css'">
								{{approve.approveResult | formatData 'result'}}
							<td align="center">{{approve.approveTime | formatData
								'time'}}</td>
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
				<div v-if="isStatus == true">
					<jsp:include
						page="/WEB-INF/views/admin/approval/approvalButton.jsp"></jsp:include>
				</div>
				<div v-else class="text-center">
					<button @click="cancel()" type="button" class="btn btn-default">返回
					</button>
				</div>
			</form>
		</div>
	</div>
	</validator>
	<!-- ibox end -->
</div>
<div class="modal fade" id="imageViewMax" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" >
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h4 class="modal-title" id="myModalLabel">图片预览</h4>
		</div>
		<div class="modal-body">
			<div style="margin: 0 auto; text-align:center" >
				<img  alt="" :src="imgPath" >
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">关闭
			</button>
		</div>
	</div>
	<!-- /.modal-content -->
</div>
<!-- /.modal -->
<div class="modal fade" id="turnListModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					审批转派
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<form id="stylistSearchForm" @submit.prevent="query">
						<div class="col-md-4">
							<div class="form-group">
								<input
										v-model="form.keyword"
										type="text"
										placeholder="工号/姓名/手机号" class="form-control"/>
							</div>
						</div>
						<div class="col-md-3 text-right">
							<div class="form-group">
								<button id="stylistSearchBtn"  class="btn btn-block btn-outline btn-default"
										alt="搜索"
										title="搜索">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
					</form>
				</div>
				<div class="row">
					<div class="col-md-10">
						<div class="row">
						 　　<font color="red">注：双击行，添加审批人</font>
							<div class="form-group">
								<table id="turnListDataTable" width="100%" class="table table-striped table-bordered table-hover">
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-10">
						<div class="form-group">
					    	<label for="name">转派描述</label>
					    	<textarea class="form-control"  rows="3" v-model="form.remark"></textarea>
					  	</div>
					</div>
				</div>
				<div class="row">
					<div class="btn-group" v-for="btn in turnEmployees">
						<span v-if="$index > 0">►</span>
						<span>
							<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">{{btn.name}} 
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu" style="min-width:100%;" role="menu">
								<li><a href="#" @click="deleteItem(btn)">删除</a></li>
							</ul>
						</span>
					</div>
					<br/>
					<br/>
					<br/>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button"  @click="turnCommitStylist" class="btn btn-primary">
					确定
				</button>
			</div>
		</div><!-- /.modal-content -->
</div><!-- /.modal -->
<div class="modal fade" id="ccPersonStylistModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					添加抄送人
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<form id="stylistSearchForm" @submit.prevent="query">
						<div class="col-md-4">
							<div class="form-group">
								<input
										v-model="form.keyword"
										type="text"
										placeholder="工号/姓名/手机号" class="form-control"/>
							</div>
						</div>
						<div class="col-md-3 text-right">
							<div class="form-group">
								<button id="stylistSearchBtn"  class="btn btn-block btn-outline btn-default"
										alt="搜索"
										title="搜索">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
					</form>
				</div>
				<div class="row">
					<div class="col-md-10">
						<div class="row">
						 　　<font color="red">注：双击行，添加抄送人</font>
							<div class="form-group">
								<table id="ccPersonDataTable" width="100%" class="table table-striped table-bordered table-hover">
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<span>
						<button type="button" class="btn btn-primary " v-for="btn in sourceCCPersonInfo">{{btn.name}} 
							
						</button>
						<div class="btn-group" v-for="btn in newCCPersonInfo">
							<button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown" >{{btn.name}} 
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu"  role="menu">
								<li><a href="#" @click="deleteItem(btn)">移除</a></li>
							</ul>
						</div>
					</span>
				</span>
				<br/>
				<br/>
				<br/>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button"  @click="commitCCUser" class="btn btn-primary">
					确定
				</button>
			</div>
		</div><!-- /.modal-content -->
</div><!-- /.modal -->
<div class="modal fade" id="addApproverModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					{{modelTitle}}
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<form id="stylistSearchForm" @submit.prevent="query">
						<div class="col-md-4">
							<div class="form-group">
								<input
										v-model="form.keyword"
										type="text"
										placeholder="工号/姓名/手机号" class="form-control"/>
							</div>
						</div>
						<div class="col-md-3 text-right">
							<div class="form-group">
								<button id="stylistSearchBtn"  class="btn btn-block btn-outline btn-default"
										alt="搜索"
										title="搜索">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
					</form>
				</div>
				<div class="row">
					<div class="col-md-10">
						<div class="row">
						 　　<font color="red">注：双击行，添加审批人</font>
							<div class="form-group">
								<table id="addApproverDataTable" width="100%" class="table table-striped table-bordered table-hover">
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-10">
						<div class="form-group">
					    	<label for="name">转派描述</label>
					    	<textarea class="form-control"  rows="3" v-model="form.remark"></textarea>
					  	</div>
					</div>
				</div>
				<div class="row">
					<span>
						<div class="btn-group" v-for="btn in sourceApproveListAdd" >
							<button type="button" v-if="$index == addIndex && resultAdd " class="btn btn-danger dropdown-toggle"  data-toggle="dropdown">{{btn.name}}
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu" style="min-width:100%;" role="menu" v-if="$index == addIndex && resultAdd ">
								<li><a href="#" @click="deleteItem(btn)">移除</a></li>
							</ul>
							
							<button type="button" v-else v-if="currentUser.userId == btn.id" class="btn btn-primary " >{{btn.name}}
							</button>
						</div>
					</span>
				</span>
				<br/>
				<br/>
				<br/>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button"  @click="commitaddApprover" class="btn btn-primary">
					确定
				</button>
			</div>
		</div><!-- /.modal-content -->
</div><!-- /.modal -->
<div class="modal fade" id="moreModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					更多操作
				</h4>
			</div>
			<div class="modal-body">
				<div class="row" align="center">
						<button type="button" @click="turnAdd" class="btn btn-primary" style="width: 110px; margin-top: 5px;">转派</button><br/>
						<button type="button" @click="addApproverBefore" class="btn btn-primary" style="width: 110px; margin-top: 5px;">前添加审批人</button><br/>
						<button type="button" @click="addApproverAfter" class="btn btn-primary" style="width: 110px; margin-top: 5px;">后添加审批人</button><br/>
						<button type="button" @click="addCCuser" class="btn btn-primary" style="width: 110px; margin-top: 5px;">添加抄送人</button>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button"  @click="closeCommitaddApprover" class="btn btn-primary">
					关闭
				</button>
			</div>
		</div><!-- /.modal-content -->
</div><!-- /.modal -->
<script src="${ctx}/static/vendor/viewer/viewer.js"></script>
<script src="${ctx}/static/vendor/webuploader/webuploader.js"></script>
<script src="${ctx}/static/js/components/webuploader.js"></script>
<script src="${ctx}/static/js/containers/approval/approvalDetail.js"></script>
<script src="${ctx}/static/js/containers/commonApply/commonapplyInfo.js"></script>
