<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<link href="${ctx}/static/css/zTreeStyle.css" rel="stylesheet">
<title>通用申请详情</title>
<div id="container" class="wrapper wrapper-content animated fadeInRight">
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
							<label class="sr-only" for="keyword">名称</label> <input
								v-model="form.keyword" id="keyword" name="keyword" type="text"
								placeholder="工号/姓名/手机号" class="form-control" />
						</div>
					</div>
					<div class="col-md-1">
						<div class="form-group">
							<button id="searchBtn" type="button"
								class="btn btn-block btn-outline btn-default" alt="搜索"
								title="搜索">
								<i class="fa fa-search"></i>
							</button>
						</div>
					</div>
				</form>
			</div>
			<table id="dataTable" width="100%"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
	<!-- ibox end -->
</div>

<script src="${ctx}/static/js/containers/commonApply/list.js"></script>