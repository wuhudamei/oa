<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<link href="${ctx}/static/vendor/viewer/viewer.css" rel="stylesheet">
<div id="container" class="wrapper wrapper-content" v-cloak>
	<div id="breadcrumb">
		<bread-crumb :crumbs="breadcrumbs"></bread-crumb>
	</div>
	<!-- ibox start -->
	<div class="ibox">
		<div class="ibox-content">
			<div class="modal-header">
				<h3>请假申请详情</h3>
			</div>
			<div class="modal-body">
				<form name="createForm" novalidate class="form-horizontal" role="form">
					<div class="form-group">
						<label class="col-sm-2 control-label">申请标题</label>
						<div class="col-sm-8">
							<div style="margin-top: 5px;" v-text="vacation.applyTitle"></div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">申请编码</label>
						<div class="col-sm-8">
							<div style="margin-top: 5px;" v-text="vacation.applyCode"></div>
						</div>
					</div>
					<div class="form-group">
						<label class="col-sm-2 control-label">开始时间</label>
						<div class="col-sm-8">
							<div style="margin-top: 5px;" v-text="vacation.startTime"></div>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">结束时间</label>
						<div class="col-sm-8">
							<div style="margin-top: 5px;" v-text="vacation.endTime"></div>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">天数</label>
						<div class="col-sm-8">
							<div style="margin-top: 5px;" v-text="vacation.days"></div>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">请假事由</label>
						<div class="col-sm-8">
							<div style="margin-top: 5px;" v-text="vacation.reason"></div>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">申请人</label>
						<div class="col-sm-8">
							<div style="margin-top: 5px;" v-text="vacation.employee.name"></div>
						</div>
					</div>

					<div class="form-group">
						<label class="col-sm-2 control-label">申请时间</label>
						<div class="col-sm-8">
							<div style="margin-top: 5px;" v-text="vacation.createTime"></div>
						</div>
					</div>
					<jsp:include page="/WEB-INF/views/admin/approval/detail.jsp"></jsp:include>
				</form>
			</div>

		</div>
	</div>
</div>

<script src="${ctx}/static/js/containers/vacation/info.js"></script>
