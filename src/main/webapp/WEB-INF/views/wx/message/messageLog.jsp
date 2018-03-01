<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<title>标签列表</title>
<link href="${ctx}/static/css/zTreeStyle.css" rel="stylesheet">
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
							<label class="sr-only" for="keyword">名称</label> 
							<input style="width: 176px;"    
								v-model="form.keyword" id="keyword" name="keyword" type="text"
								placeholder="员工编号/姓名/申请编号" class="form-control" />
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
			<table id="dataTable" width="100%"
				class="table table-striped table-bordered table-hover">
			</table>
		</div>
	</div>
	<!-- ibox end -->
</div>
<script src="${ctx}/static/js/wx/message/messageLog.js"></script>