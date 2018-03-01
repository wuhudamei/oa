<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<link rel="stylesheet" href="/static/vendor/webuploader/webuploader.css">

<title>考核管理</title>
<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content">
	<div id="breadcrumb">
		<bread-crumb :crumbs="breadcrumbs"></bread-crumb>
	</div>
	<!-- ibox start -->
	<div class="ibox">
		<div class="ibox-content">
			<div class="row">
				<form id="searchForm" @submit.prevent="query">
					<div class="col-md-2">
						<div class="form-group">
							<label class="sr-only" for="keyword"></label> <input
								v-model="form.keyword" id="keyword" name="keyword" type="text"
								placeholder="姓名/手机号" class="form-control" />
						</div>
					</div>
					<div class="col-md-2">
						<input v-model="form.evaluateMonth" v-el:evaluate-month
							id="evaluateMonth" maxlength="20" data-tabindex="1" readonly
							name="evaluateMonth" type="text"
							class="form-control datetime input-sm" placeholder="考核月份">
					</div>
					<div class="col-md-1">
						<div class="form-group">
							<button id="searchBtn" type="submit"
								class="btn btn-block btn-outline btn-default" alt="搜索"
								title="搜索">
								<i class="fa fa-search"></i>
							</button>
						</div>
					</div>
					<!-- 将剩余栅栏的长度全部给button -->
					<div class="col-md-7 text-right">
						<div class="form-group">
							<shiro:hasPermission name="stylist:assessment-new">
								<button @click="createBtnClickHandler" id="createBtn"
									type="button" class="btn btn-outline btn-primary">添加</button>
							</shiro:hasPermission>
							<shiro:hasPermission name="stylist:assessment-template">
								<button @click="downLoadExcel" id="downLoadExcel" type="button"
									class="btn btn-outline btn-primary">模板下载</button>
							</shiro:hasPermission>
							<shiro:hasPermission name="stylist:assessment-batchAdd">
								<label class="control-label btn btn-primary"> <web-uploader
										:type="webUploaderSub.type" :w-server="webUploaderSub.server"
										:w-accept="webUploaderSub.accept"
										:w-file-size-limit="webUploaderSub.fileSizeLimit"
										:w-file-single-size-limit="webUploaderSub.fileSingleSizeLimit"
										:w-form-data="webUploaderSub.formData"> 批量导入 </web-uploader>
								</label>
							</shiro:hasPermission>
						</div>
					</div>
				</form>
			</div>
			<!-- <div class="columns columns-right btn-group pull-right"></div> -->
			<table v-el:data-table id="dataTable" width="100%"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
	<!-- ibox end -->
</div>
<!-- container end-->

<!-- 添加考核管理弹窗-->
<div id="modal" class="modal fade" tabindex="-1" data-width="760">
	<validator name="validation">
	<form name="createMirror" novalidate class="form-horizontal"
		role="form">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3>添加考核信息</h3>
		</div>
		<div class="modal-body">
			<div class="form-group"
				:class="{'has-error':$validation.name.invalid && submitBtnClick}">
				<label for="minister" class="col-sm-2 control-label">设计师:</label>
				<div class="col-sm-8">
					<input type="hidden" v-model="evaluate.jobNo" id="stylistId">
					<input id="name" v-model="evaluate.name" class="form-control"
						readonly
						@click="stylist"
						v-validate:stylistname="{required:{rule:true,message:'点击请选择设计师'}}"
						type="text" placeholder="请选择设计师"> <span v-cloak
						v-if="$validation.name.invalid && submitBtnClick"
						class="help-absolute"> <span
						v-for="error in $validation.name.errors"> {{error.message}}
							{{($index !== ($validation.name.errors.length -1)) ? ',':''}} </span>
					</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">月份:</label>
				<div class="col-sm-8">
					<input id="evaluateMonth" v-model="evaluate.evaluateMonth"
						class="form-control" readonly type="text">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">手机号:</label>
				<div class="col-sm-8">
					<input id="mobile" v-model="evaluate.mobile" class="form-control"
						readonly readonly type="text">
				</div>
			</div>
			<div class="form-group"
				:class="{'has-error':$validation.score.invalid && submitBtnClick}">
				<label class="col-sm-2 control-label">得分:</label>
				<div class="col-sm-8">
					<input id="score" v-model="evaluate.score" class="form-control"
						v-validate:score="{
	                                    required:{rule:true,message:'请输入得分'},
	                                    min:1,
	                                    max:100
										}"
						type="text" placeholder="请输入得分"> <span v-cloak
						v-if="$validation.score.invalid && submitBtnClick"
						class="help-absolute"> <span
						v-for="error in $validation.score.errors">
							{{error.message}} {{($index !== ($validation.score.errors.length
							-1)) ? ',':''}} </span>
					</span>
				</div>
			</div>

		</div>
		<div class="modal-footer">
			<button :disabled="disabled" type="button" data-dismiss="modal"
				class="btn">取消</button>
			<button @click="submit" :disabled="disabled" type="button"
				class="btn btn-primary">提交</button>
		</div>
	</form>
	</validator>
</div>

<!-- 设计师列表 -->
<div id="stylstListModel" class="modal fade" tabindex="-1"
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
<script src="${ctx}/static/vendor/webuploader/webuploader.js"></script>
<script src="${ctx}/static/js/components/webuploader.js"></script>
<script src="${ctx}/static/js/containers/stylist/evaluate.js?v=1.0"></script>