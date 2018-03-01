<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<link href="${ctx}/static/css/zTreeStyle.css" rel="stylesheet">
<title>签报单展示</title>
<!-- 内容部分 start-->
<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content">
	<div id="breadcrumb">
		<bread-crumb :crumbs="breadcrumbs"></bread-crumb>
	</div>
	<!-- ibox start -->
	<div class="ibox">
		<div class="ibox-content">
			<form id="searchForm" @submit.prevent="query">
				<div class="row">
					<label class="col-md-1 line-height34">执行状态:</label>
					<div class="col-md-2">
						<select class="form-control" v-model="form.statue" id="state"
							name="state" class="form-control">
							<option value="">请选择</option>
							<option value="1">执行</option>
							<option value="0" selected = "selected">未执行</option>
						</select>
					</div>

					<div class="">
						<label class="col-md-1 line-height34">起止日期:</label>
						<div class="col-md-2">
							<input v-model="form.beginDate" placeholder="请选择开始时间"
								v-el:entry-date name="entryDate" type="text"
								class="form-control datepicker">
						</div>

						<div class="col-md-2">
							<input v-model="form.endDate" placeholder="请选择结束时间" v-el:end-date
								name="endDate" type="text" class="form-control datepicker">
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-6">
						<input v-model="form.search" placeholder="姓名，电话" type="text"
							id="search" name="search" class="form-control datepicker">
					</div>

					<div class="col-md-2">
						<button id="searchBtn" type="submit"
							class="btn btn-block btn-outline btn-default" alt="搜索"
							style="float: right" title="搜索">
							<i class="fa fa-search"></i>
						</button>
					</div>
					<div class="col-md-4 text-right">
						<button @click="exportBill" id="createBtn" type="button"
							class="btn btn-outline btn-primary">导出
						</button>
						
						<!-- <button @click="createWorkOrder"  type="button"
							class="btn btn-outline btn-primary">新建
						</button> -->

					</div>
					<!-- <div class="col-md-1" style="padding: 2px">
							<button @click="createWorkOrder" id="createBtn" type="button"
                                               class="btn btn-outline btn-primary">&nbsp;&nbsp;&nbsp;&nbsp;新建&nbsp;&nbsp;&nbsp;&nbsp;
                               </button>
						</div> -->
				</div>


			</form>

			<br />
			<!-- <div class="columns columns-right btn-group pull-right"></div> -->
			<table v-el:data-table id="dataTable" width="100%"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
	<!-- ibox end -->
</div>
<!-- container end-->
<!-- 编辑工单-->
<div id="workorder" class="modal fade" tabindex="-1" data-width="800">
	<validator name="validation">
	<form name="createMirror" novalidate class="form-horizontal"
		role="form">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3>新建签报单</h3>
		</div>
		<div class="row" style="margin: 10px 0; text-align: center">
			<input type="hidden" id="orderNum" v-model="form.orderNo">
			<div class="col-sm-4">
				<label>订单号：{{form.orderNo}}</label>
			</div>
			<div class="col-sm-4">
				<label>客户姓名：{{form.customerName}}</label>
			</div>
<!-- 			<input type="hidden" name="" value="{{form.signCode}}" /> -->
			<!-- <h3> &nbsp;&nbsp;&nbsp;订单号：{{form.orderNum}}	&nbsp;&nbsp;&nbsp;	客户姓名：{{form.customerName}} &nbsp;&nbsp;&nbsp; 客户电话：{{form.customerPhone}}</h3> -->
			<!-- <br/> -->
			<!-- <h3> &nbsp;&nbsp;&nbsp;订单地址：{{form.orderAddress}}   &nbsp;&nbsp;&nbsp;   订单状态：施工中 </h3> -->
		</div>
		<div class="row" style="margin: 10px 0; text-align: center">
			<div class="col-sm-4">
				<label>客户电话：{{form.mobile}}</label>
			</div>
			<div class="col-sm-4">
				<label>订单状态：施工中</label>
			</div>			
<!-- 			<input type="hidden" name="" value="{{form.signCode}}" /> -->
		</div>		
		<div class="row" style="margin: 10px 0; text-align: center">
			<div class="col-sm-4">
				<label>订单地址：</label>
			</div>
			<div class="col-sm-4">
				{{form.address}}
			</div>
		</div>
		<hr />
		<div class="form-group">
			<label for="problem" class="col-sm-2 control-label"><a @click="showContant">查询合同+</a></label>
		</div>		
		<div class="form-group"
			:class="{'has-error':$validation.proceduredescribe.invalid && $validation.touched}">
			<label for="procedureDescribe" class="col-sm-2 control-label">过程描述</label>
			<div class="col-sm-8">
				<textarea v-validate:proceduredescribe="{required:true}"
					v-model="form.procedureDescribe" maxlength="3000"
					id="procedureDescribe" name="procedureDescribe"
					class="form-control" placeholder=""></textarea>
				<div v-if="$validation.proceduredescribe.invalid && $validation.touched"
					class="help-absolute">
					<span v-if="$validation.proceduredescribe.invalid">请输过程描述</span>
				</div>
			</div>
		</div>

		<div class="form-group"
			:class="{'has-error':$validation.managerview.invalid && $validation.touched}">
			<label for="managerView" class="col-sm-2 control-label">总经理意见</label>
			<div class="col-sm-8">
				<textarea v-validate:managerview="{required:true}"
					v-model="form.managerView" maxlength="3000" id="managerView"
					name="managerView" class="form-control" placeholder=""></textarea>
				<div v-if="$validation.managerview.invalid && $validation.touched"
					class="help-absolute">
					<span v-if="$validation.managerview.invalid">请输入总经理意见</span>
				</div>
			</div>
		</div>

		<div class="form-group">
			<div class="form-group" style="margin: 20px 30px">
				<div class="pull-right">
					<button class="btn btn-primary" type="button" @click="add">添加处罚</button>
				</div>
			</div>
			<hr />
			<div v-for="item of groups" track-by="$index">
				<div class="form-group" style="margin: 20px">
					<label class="col-sm-2 control-label">责任部门</label>
					<div class="col-md-2">
						<div class="form-group" v-clickoutside="clickOut">
							<label class="sr-only"></label> <input type="hidden" id="orgCode"
								v-model="form.orgCode"> <input
								v-model="item.punishDepartName" @click="showTree(item)" readonly
								type="text" placeholder="选择机构" class="form-control" />
							<!--:show-tree.sync 双向绑定是否显示树属性 一般开始的时候的 false 如果不需要切换隐藏/显示的时候是true-->
							<!--@on-change 绑定节点有checkbox的时候checkbox变化的事件，传入参数为该节点-->
							<!--@on-click 绑定节点被点击事件，传入参数为该节点-->
							<z-tree v-ref:nodes-select :querys="item" :nodes="orgData"
								:show-tree.sync="item.showOrgTree" @on-click="selectOrg"
								:show-checkbox="false"> </z-tree>
						</div>
					</div>

					<label class="col-sm-2 control-label">责任人</label>
					<div class="col-sm-2" :class="{'has-error':$validation['punishNam'+$index].invalid && $validation.touched}">
						<input type="hidden" id="parts" v-model="parts"> <input
							type="text" id="people" @click="showPerson(item)"
							:field="'punishNam'+$index"
							v-validate="{required:true}"
							v-model="item.punishManName" readonly class="form-control">
					</div>

					<label class="col-sm-2 control-label">处罚金额</label>
					<div class="col-sm-2" :class="{'has-error':$validation['money'+$index].invalid && $validation.touched}">
						<input class="form-control" type="number"
						:field="'money'+$index"
						v-validate="{required:true}"
							v-model="item.punishMoney">
					</div>

				</div>
				<div class="form-group">
					<div class="col-sm-10 col-sm-offset-1" :class="{'has-error':$validation['reason'+$index].invalid && $validation.touched}">
						<textarea class="form-control" v-model="item.punishReason" rows=""
						:field="'reason'+$index"
						v-validate="{required:true}"
							cols="" placeholder="请输入处罚原因"></textarea>
					</div>

				</div>
			</div>
		</div>
		<input v-model="form.liableDepartment" id="liableDepartment"
			name="liableDepartment" type="hidden" class="form-control" />
		<div class="modal-footer">
			<button :disabled="disabled" type="button" data-dismiss="modal"
				class="btn">取消</button>
			<button @click="submitClickHandler" :disabled="submitting" type="button"
				class="btn btn-primary">提交</button>
		</div>
	</form>
	</validator>
</div>

<!-- 合同列表 -->
<div id="mdnOrder" class="modal fade" tabindex="-1" data-width="1000">
	<!-- ibox start -->
	<div class="ibox">
		<div class="ibox-content">
			<div class="row">
				<form id="searchForm" action='#'>
					<div class="col-md-4">
						<div class="form-group">
							<input v-model="form.keyword" value="" type="text" placeholder="电话号"
								class="form-control" />
						</div>
					</div>
					<div class="col-md-3 text-right">
						<div class="form-group">
							<button id="searchBtn" type="button" @click.prevent.stop="query"
								class="btn btn-block btn-outline btn-default" alt="搜索"
								title="搜索">
								<i class="fa fa-search"></i>
							</button>
						</div>
					</div>
				</form>
			</div>
			<!-- <div class="columns columns-right btn-group pull-right"></div> -->
			<table id="dataTable" width="100%"
				class="table table-striped table-bordered table-hover">
			</table>

			<div class="modal-footer">
				<button type="button" data-dismiss="modal" class="btn">关闭</button>
				<button type="button" @click="commitUsers" class="btn btn-primary">确定</button>
			</div>
		</div>
	</div>
</div>

<!-- 签报单明细 -->
<div id="billModal" class="modal fade" tabindex="-1" data-width="760">
	<div class="wrapper wrapper-content">
		<div class="ibox-content">
			<form name="createMirror" novalidate class="form-horizontal"
				role="form" style="margin-right: 10px">
				<div class="text-center">
					<h3>签报单明细</h3>
				</div>
				<hr />
				<div class="row">
					<div class="col-md-6">
						<div class="row">
							<div class="form-group">
								<label class="col-md-4 control-label">签报编码:</label>
								<div class="col-md-4 control-label" style="text-align:left">
									{{signbill.signCode}}
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="row">
							<div class="form-group">
								<label class="col-md-4 control-label">处罚编码:</label>
								<div class="col-md-4 control-label" style="text-align:left">
									{{signbill.punishCode}}
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-6">
						<div class="row">
							<div class="form-group">
								<label class="col-md-4 control-label">客户姓名:</label>
								<div class="col-md-4 control-label" style="text-align:left">
									{{signbill.customerName}}
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="row">
							<div class="form-group">
								<label class="col-md-4 control-label">客户电话:</label>
								<div class="col-md-4 control-label" style="text-align:left">
									{{signbill.customerPhone}}
								</div>
							</div>
						</div>
					</div>					
				</div>

				<div class="row">
					<div class="col-md-12">
						<div class="row">
							<div class="form-group">
								<label class="col-md-2 control-label">订单地址:</label>
								<div class="col-md-10 control-label" style="text-align:left">
									{{signbill.orderAddress}}
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-6">
						<div class="row">
							<div class="form-group">
								<label class="col-md-4 control-label">责任部门:</label>
								<div class="col-md-4 control-label" style="text-align:left">
									{{signbill.punishDepartment}}
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<div class="row">
							<div class="form-group">
								<label class="col-md-4 control-label">责任人:</label>
								<div class="col-md-4 control-label" style="text-align:left">
									{{signbill.punishMan}}
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="row">
					<div class="col-md-6">
						<div class="row">
							<div class="form-group">
								<label class="col-md-4 control-label">处罚金额:</label>
								<div class="col-md-4 control-label" style="text-align:left">
									{{signbill.punishMoney}}
								</div>
							</div>
						</div>
					</div>				
					<div class="col-md-6">
						<div class="row">
							<div class="form-group">
								<label class="col-md-4 control-label">执行状态:</label>
								<div class="col-md-4 control-label" style="text-align:left">
									{{signbill.punishCode==0 ? '执行':'未执行'}}
								</div>
							</div>
						</div>
					</div>				
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="row">
							<div class="form-group">
								<label class="col-md-2 control-label">处罚原因:</label>
								<div class="col-md-10 control-label" style="text-align:left">
									{{signbill.punishReason}}
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-6">
						<div class="row">
							<div class="form-group">
								<label class="col-md-4 control-label">签报时间:</label>
								<div class="col-md-6 control-label" style="text-align:left">
									{{signbill.time}}
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="modal-footer">
					<button @click="cancel" type="button" data-dismiss="modal"
						class="btn">关闭</button>
				</div>
			</form>
			<!-- ibox end -->
		</div>
	</div>
</div>

<!-- 员工列表 -->
<div id="employeeListModel" class="modal fade" tabindex="-1"
	data-width="800" style="top: 30%">
	<!-- ibox start -->
	<div class="ibox-content">
		<div class="row">
			<form id="searchForm" action='#'>
				<div class="col-md-4">
					<div class="form-group">
						<input v-model="form.keyword" type="text" placeholder="姓名/手机号"
							class="form-control" />
					</div>
				</div>
				<div class="col-md-3 text-right">
					<div class="form-group">
						<button id="searchBtn" type="button" @click.prevent.stop="query"
							class="btn btn-block btn-outline btn-default" alt="搜索" title="搜索">
							<i class="fa fa-search"></i>
						</button>
					</div>
				</div>
			</form>
		</div>
		<table id="dataTable" width="100%"
			class="table table-striped table-bordered table-hover">
		</table>
		<div class="modal-footer">
			<button type="button" data-dismiss="modal" class="btn">关闭</button>
			<button type="button" @click="commitUsers" class="btn btn-primary">确定</button>
		</div>
	</div>
</div>
<%@include file="/WEB-INF/views/admin/components/ztree.jsp"%>
<script src="${ctx}/static/js/components/ztree.js"></script>
<script src="${ctx}/static/js/jquery.ztree.core.js"></script>
<script src="${ctx}/static/js/jquery.ztree.excheck.js"></script>
<script src="${ctx}/static/js/containers/singleSign/add.js"></script>