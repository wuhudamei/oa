<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<script type="text/javascript">
	var hasEditStorePrivelege = false;
	<shiro:hasPermission name="store:edit">
	hasEditStorePrivelege = true;
	</shiro:hasPermission>
</script>
<head>
<style>
.progress {
	height: 0px;
	transition: all .6s ease;
}

.progress-uploading {
	margin-top: 2px;
	height: 20px;
}
</style>
</head>
<div id="container"
	class="wrapper wrapper-content  animated fadeInRight">
	<div id="breadcrumb">
		<bread-crumb :crumbs="breadcrumbs"></bread-crumb>
	</div>
	<!-- ibox start -->
	<div class="ibox">
		<div class="ibox-content">
			<div class="row">
				<form id="searchForm" @submit.prevent="query">

					<div class="col-md-3">
						<div class="form-group">
							<input v-model="form.keyword" type="text" placeholder="名称"
								class="form-control" />
						</div>
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
				</form>
			</div>
			<!-- <div class="columns columns-right btn-group pull-right"></div> -->
			<table id="dataTable" width="100%"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
	<!-- ibox end -->
</div>


<!-- 编辑modal -->
<div id="editModal" class="modal fade" tabindex="-1" data-width="600">
	<validator name="validation">
	<form name="banner" novalidate class="form-horizontal" role="form">
		<input type="hidden" v-model="id">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 align="center">编辑</h3>
		</div>

		<div class="modal-body">
			<!-- <div class="form-group"
				:class="{'has-error':$validation.paramKey.invalid && $validation.touched}">
				<label for="paramKey" class="control-label col-sm-3"><span
					style="color: red">*</span>系统参数KEY：</label>
				<div class="col-sm-7">
					<input id="paramKey" v-model="paramKey" readonly="readonly"
						v-validate:param-key="{
                  required:{rule:true,message:'请输入系统参数KEY'}
                }"
						data-tabindex="1" type="text" placeholder="系统参数KEY"
						class="form-control"> <span v-cloak
						v-if="$validation.paramKey.invalid && $validation.touched"
						class="help-absolute"> <span
						v-for="error in $validation.paramKey.errors">
							{{error.message}} {{($index !==
							($validation.paramKey.errors.length -1)) ? ',':''}} </span>
					</span>
				</div>
			</div>
 -->
			<div class="form-group"
				:class="{'has-error':$validation.paramKeyName.invalid && $validation.touched}">
				<label for="paramKeyName" class="control-label col-sm-3"><span
					style="color: red">*</span>参数名称：</label>
				<div class="col-sm-7">
					<input id="paramKeyName" v-model="paramKeyName"
						v-validate:param-key-name="{
                  required:{rule:true,message:'请输入参数名称'}
                }"
						data-tabindex="1" type="text" placeholder="参数名称"
						class="form-control"> <span v-cloak
						v-if="$validation.paramKeyName.invalid && $validation.touched"
						class="help-absolute"> <span
						v-for="error in $validation.paramKeyName.errors">
							{{error.message}} {{($index !==
							($validation.paramKeyName.errors.length -1)) ? ',':''}} </span>
					</span>
				</div>
			</div>

			<!-- <div class="form-group">
				<label for="paramFlag" class="col-sm-3 control-label">是否自定义参数</label>
				<div id="paramFlag" class="col-sm-8">
					<div class="col-sm-3">
						<input type="radio" id="paramFlagOne" :value="true"
							v-model="paramFlag"> <label for="paramFlagOne">是</label>
					</div>
					<div class="col-sm-3">
						<input type="radio" id="paramFlagTwo" :value="false"
							v-model="paramFlag"> <label for="paramFlagTwo">否</label>
					</div>
				</div>
			</div> -->

			<div class="form-group" v-if="!paramFlag"
				:class="{'has-error':($validation.paramValue.invalid && $validation.touched)}">
				<label for="paramValue" class="col-sm-3 control-label">参数值：</label>
				<div class="col-sm-7">
					<select id="paramValue" name="paramValue"
						v-validate:param-value="{required:true}" v-model="paramValue"
						class="form-control">
						<option value="Y">启用</option>
						<option value="N">禁用</option>
					</select>
					<div v-if="$validation.paramValue.invalid && $validation.touched"
						class="help-absolute">
						<span v-if="$validation.paramValue.invalid">请选择参数</span>
					</div>
				</div>
			</div>

			<div class="form-group" v-if="paramFlag"
				:class="{'has-error':$validation.paramValue.invalid && $validation.touched}">
				<label for="paramValue" class="control-label col-sm-3"><span
					style="color: red">*</span>参数值：</label>
				<div class="col-sm-7">
					<input id="paramValue" v-model="paramValue"
						v-validate:param-value="{
                  required:{rule:true,message:'请输入参数值'},
                }"
						data-tabindex="1" type="text" placeholder="参数值"
						class="form-control"> <span v-cloak
						v-if="$validation.paramValue.invalid && $validation.touched"
						class="help-absolute"> <span
						v-for="error in $validation.paramValue.errors">
							{{error.message}} {{($index !==
							($validation.paramValue.errors.length -1)) ? ',':''}} </span>
					</span>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" data-dismiss="modal" class="btn">关闭</button>
			<button type="button" @click="save" class="btn btn-primary">保存</button>
		</div>
</div>
<script src="${ctx}/static/vendor/webuploader/webuploader.js"></script>
<script src="${ctx}/static/js/components/webuploader.js"></script>
<script src="${ctx}/static/js/containers/system/param.js"></script>
