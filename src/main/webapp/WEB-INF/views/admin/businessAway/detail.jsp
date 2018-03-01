<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<form name="createForm" novalidate class="form-horizontal" role="form">
	<div class="form-group">
		<label class="col-sm-2 control-label">申请标题:</label>
		<div class="col-sm-8">
			<div style="margin-top: 5px;">{{businessAway.applyTitle}}</div>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">申请编码:</label>
		<div class="col-sm-8">
			<div style="margin-top: 5px;">{{businessAway.applyCode}}</div>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">出发地:</label>
		<div class="col-sm-8">
			<div style="margin-top: 5px;">{{businessAway.setOutAddress}}</div>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">目的地:</label>
		<div class="col-sm-8">
			<div style="margin-top: 5px;">{{businessAway.address}}</div>
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-2 control-label">开始日期:</label>
		<div class="col-sm-8">
			<div style="margin-top: 5px;">{{businessAway.beginTime}}</div>
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-2 control-label">结束日期:</label>
		<div class="col-sm-8">
			<div style="margin-top: 5px;">{{businessAway.endTime}}</div>
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-2 control-label">出差天数:</label>
		<div class="col-sm-8">
			<div style="margin-top: 5px;">{{businessAway.daysNum}}</div>
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-2 control-label">预估费用:</label>
		<div class="col-sm-8">
			<div style="margin-top: 5px;">{{businessAway.estimatedCost}}</div>
		</div>
	</div>

	<div class="form-group">
		<label class="col-sm-2 control-label">出差理由:</label>
		<div class="col-sm-8">
			<div style="margin-top: 5px;">{{businessAway.reason}}</div>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">申请人:</label>
		<div class="col-sm-8">
			<div style="margin-top: 5px;">{{businessAway.applyUserName}}</div>
		</div>
	</div>
	<div class="form-group">
		<label class="col-sm-2 control-label">申请时间:</label>
		<div class="col-sm-8">
			<div style="margin-top: 5px;">{{businessAway.createTime}}</div>
		</div>
	</div>

	<jsp:include page="/WEB-INF/views/admin/approval/detail.jsp"></jsp:include>
</form>
