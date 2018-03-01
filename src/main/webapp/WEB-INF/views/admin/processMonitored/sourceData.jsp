<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<link href="${ctx}/static/vendor/viewer/viewer.css" rel="stylesheet">
<title>通用申请</title>
<style>
.fixed-table-pagination div.pagination, .fixed-table-pagination .pagination-detail{
    display: none;
}
</style>
<div id="container" class="wrapper wrapper-content">
	<div id="breadcrumb">
		<bread-crumb :crumbs="breadcrumbs"></bread-crumb>
	</div>
	<!-- ibox end -->
       <div class="ibox">
       <div class="ibox-content">
			<h2 class="text-center">通用申请</h2>
			<form novalidate class="form-horizontal" role="form">
				<div class="form-group"><div class="col-md-2 text-left"><label>	ID：</label></div><div class="col-md-10"><label>{{applyCommon.id}}</label></div></div>
				<div class="form-group"><div class="col-md-2 text-left"><label>	编号：</label></div><div class="col-md-10"><label>{{applyCommon.serial_number }}</label></div></div>
				<div class="form-group"><div class="col-md-2 text-left"><label>	标题：</label></div><div class="col-md-10"><label>{{applyCommon.title }}</label></div></div>
				<div class="form-group"><div class="col-md-2 text-left"><label>	内容：</label></div><div class="col-md-10"><label>{{applyCommon.content }}</label></div></div>
				<div class="form-group"><div class="col-md-2 text-left"><label>	图片：</label></div><div class="col-md-10"><label>{{applyCommon.photos}}</label></div></div>
				<div class="form-group"><div class="col-md-2 text-left"><label>	附件：</label></div><div class="col-md-10"><label>{{applyCommon.accessories }}</label></div></div>
				<div class="form-group"><div class="col-md-2 text-left"><label>	审批人：</label></div><div class="col-md-10"><label>{{applyCommon.apply_person}}</label></div></div>
				<div class="form-group"><div class="col-md-2 text-left"><label>	抄送人：</label></div><div class="col-md-10"><label>{{applyCommon.cc_person }}</label></div></div>
				<div class="form-group"><div class="col-md-2 text-left"><label>	提交时间：</label></div><div class="col-md-10"><label>{{applyCommon.apply_time}}</label></div></div>
				<div class="form-group"><div class="col-md-2 text-left"><label>	提交人：</label></div><div class="col-md-10"><label>{{applyCommon.submit_user_name }}</label></div></div>
				<div class="form-group"><div class="col-md-2 text-left"><label>	创建时间：</label></div><div class="col-md-10"><label>{{applyCommon.create_time }}</label></div></div>
				<div class="form-group"><div class="col-md-2 text-left"><label>	状态：</label></div><div class="col-md-10"><label>{{applyCommon.status}}</label></div></div>
				<div class="form-group"><div class="col-md-2 text-left"><label>	审批人详情：</label></div><div class="col-md-10"><label>{{applyCommon.apply_person_info }}</label></div></div>
				<div class="form-group"><div class="col-md-2 text-left"><label>	抄送人详情：</label></div><div class="col-md-10"><label>{{applyCommon.cc_person_info}}</label></div></div>
				<div class="form-group"><div class="col-md-2 text-left"><label>	审批人名称：</label></div><div class="col-md-10"><label>{{applyCommon.apply_person_name }}</label></div></div>
				<div class="form-group"><div class="col-md-2 text-left"><label>	抄送人名称：</label></div><div class="col-md-10"><label>{{applyCommon.cc_person_name}}</label></div></div>
				<div class="form-group"><div class="col-md-2 text-left"><label>	是否可见：</label></div><div class="col-md-10"><label>{{applyCommon.cc_user_status}}</label></div></div>
			</form>
		</div>
        <div class="ibox-content">
            <div class="zTreeDemoBackground">
                <ul id="treeDemo" class="ztree"></ul>
            </div>
            <div class="row">
            </div>
            <!-- <div class="columns columns-right btn-group pull-right"></div> -->
            <table v-el:data-table id="dataTable" width="100%"
                   class="table table-striped table-bordered table-hover">
            </table>
        </div>
    </div>
</div>
<script src="${ctx}/static/js/containers/processMonitored/sourceData.js"></script>
