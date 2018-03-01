<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${ctx}/static/vendor/viewer/viewer.css" rel="stylesheet">
<!-- 流程表单详情 -->
<div id="container" class="wrapper wrapper-content animated fadeInRight">
    <validator name="validation">
        <form name="createMirror" novalidate class="form-horizontal" role="form">
            <div class="modal-header">
                <h3>流程审批 - {{form.type | tielFilter}}</h3>
            </div>
            <div class="modal-body">
                <div v-if="formType == 'BUSINESS'">
                    <jsp:include page="/WEB-INF/views/admin/businessAway/detail.jsp"></jsp:include>
                </div>
                <div v-if="formType == 'LEAVE'">
                    <jsp:include page="/WEB-INF/views/admin/vacation/detail.jsp"></jsp:include>
                </div>
                <div v-if="formType == 'BUDGET'">
                    <jsp:include page="/WEB-INF/views/admin/budget/multDetail.jsp"></jsp:include>
                </div>
                <div v-if="formType == 'YEARBUDGET'">
                    <jsp:include page="/WEB-INF/views/admin/yearbudget/detail.jsp"></jsp:include>
                </div>
                <div v-if="formType == 'EXPENSE'">
                    <jsp:include page="/WEB-INF/views/admin/payment/multDetail.jsp"></jsp:include>
                </div>
                <div v-if="formType == 'PURCHASE'">
                    <jsp:include page="/WEB-INF/views/admin/purchase/purchaseDetail.jsp"></jsp:include>
                </div>
                <div v-if="formType == 'SIGNATURE'">
                    <jsp:include page="/WEB-INF/views/admin/signature/multDetail.jsp"></jsp:include>
                </div>
                <div v-if="formType == 'COMMON'">
                    <jsp:include page="/WEB-INF/views/admin/applyCommon/common.jsp"></jsp:include>
                </div>
            </div>
 			<div v-if="isStatus == true">  <!-- 微信端 -->
                <jsp:include page="approvalButton.jsp"></jsp:include>
            </div>
        </form>
    </validator>
</div>

<!-- 费用单详情-->
<div id="signatureDetail" class="modal fade" tabindex="-1" data-width="760">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>费用申请详情</h3>
    </div>
    <div class="modal-body">
        <jsp:include page="../signature/detail.jsp"></jsp:include>
    </div>
    <div class="modal-footer">
        <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">关闭</button>
    </div>
</div>
<div class="modal fade" id="imageView" tabindex="-1" role="dialog"
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
<script src="${ctx}/static/vendor/viewer/viewer.js"></script>
<script src="${ctx}/static/js/containers/approval/approvalDetail.js"></script>