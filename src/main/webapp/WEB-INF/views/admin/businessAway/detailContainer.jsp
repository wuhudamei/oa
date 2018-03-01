<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${ctx}/static/vendor/viewer/viewer.css" rel="stylesheet">
<div id="container" class="wrapper wrapper-content" v-cloak>
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div class="modal-header">
                <h3>{{titleName}}申请详情</h3>
            </div>
            <div v-if="showType=='1'" class="modal-body">
                <jsp:include page="detail.jsp"></jsp:include>
            </div>
            <div v-if="showType=='2'" class="modal-body">
                <jsp:include page="../vacation/detail.jsp"></jsp:include>
            </div>
            <div v-if="showType=='6'" class="modal-body">
                <jsp:include page="../signature/detail.jsp"></jsp:include>
            </div>
            <div v-if="showType=='7'" class="modal-body">
                <jsp:include page="../payment/detail.jsp"></jsp:include>
            </div>
            <div v-if="showType=='8'" class="modal-body">
                <jsp:include page="../applyCommon/wxinfo.jsp"></jsp:include>
            </div>
        </div>
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
<script src="${ctx}/static/js/containers/businessAway/detailContainer.js"></script>
