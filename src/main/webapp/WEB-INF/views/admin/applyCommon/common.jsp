<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<form name="createForm" novalidate class="form-horizontal" role="form">
	<div class="form-group">
		<div class="row">
			<label class="col-sm-2 control-label">申请编号</label>
			<div class="col-sm-4">
				<div style="margin-top: 5px;">{{common.serialNumber}}</div>
			</div>
		</div>
	</div>

	<div class="form-group">
		<div class="row">
			<label class="col-sm-2 control-label">申请时间</label>
			<div class="col-sm-4">
				<div style="margin-top: 5px;">{{common.applyTime}}</div>
			</div>
		</div>
	</div>

	<div class="form-group">
		<div class="row">
			<label class="col-sm-2 control-label">申请标题</label>
			<div class="col-sm-4">
				<div style="margin-top: 5px;">{{common.title}}</div>
			</div>
		</div>
	</div>

	<div class="form-group">
		<div class="row">
			<label class="col-sm-2 control-label">申请内容</label>
			<div class="col-sm-4">
				<div style="margin-top: 5px;">{{common.content}}</div>
			</div>
		</div>
	</div>
	<div class="form-group">
		<div class="row">
			<label class="col-sm-2 control-label">照片</label>
			<div class="col-sm-10">
			<label id="view-images">
				<label v-for="src in photoSrcs">
					<img v-model="form.photoSrcs"   :src="src.path"  alt="" style="width: 105px; margin-left: 60px">
				</label>
			</label>
			</div>
		</div>
	</div>

	<div class="form-group">
		<div class="row">
			<label class="col-sm-2 control-label">附件</label>
			<div class="col-sm-10">
				<label v-for="src in accessories">
					{{src.fileName}}
					<i class="fa fa-fw fa-download" style="height: 15px; width: 55px;" @click="downloadFile(src,$index,'accessories')">
					<font color="blue">下载</font>
					</i>&nbsp;&nbsp;
				</label>
			</div>
		</div>
	</div>
	<div class="form-group">
		<div class="row">
			<label class="col-md-2 control-label">提交人:</label>
			<div class="col-sm-4">
				<div style="margin-top: 5px;">{{payment.submitUserName}}</div>
			</div>
		</div>
	</div>
	<div class="form-group">
		<div class="row">
			<label class="col-md-2 control-label">抄送人:</label>
			<div class="col-sm-4">
				<div style="margin-top: 5px;">{{payment.ccPersonName}}</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/admin/applyCommon/detail.jsp"></jsp:include>
</form>