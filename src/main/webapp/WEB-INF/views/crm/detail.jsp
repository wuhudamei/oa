<%--
 	根据任务ID获取单条派发的任务.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<title>查看任务详情</title>
<meta name="viewport"
	content="user-scalable=no, width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="${ctx}/static/css/lib.css">
<style>
#header {
	position: absolute;
	z-index: 2;
	top: 0;
	left: 0;
	width: 100%;
	height: 45px;
	line-height: 45px;
	/* background: #f60; */
	background: #1ab394;
	padding: 0;
	color: #eee;
	font-size: 20px;
	text-align: center;
	font-weight: bold;
}

.xs-container {
	padding: 15px;
}

.panel-default {
	border-radius: 10px;
}
</style>
<script src="${ctx}/static/js/lib.js"></script>
<div id="container">
	<div class="xs-container">
		<div id="header">任务详情</div>
		<hr />
		<br />
		<!-- <div class="panel panel-default">
				<div class="panel-body">
					<div class="row">
						<div class="col-sm-6 col-xs-12">
							<p>客户名称:{{item.KEH}}</p>
						</div>
						<div class="col-sm-6 col-xs-12">
							<p>
								客户电话: <a :href="'tel:' + item.KEHDH">{{item.KEHDH}}</a>
							</p>
						</div>
					</div>
	
					<div class="row">
						<div class="col-sm-6 col-xs-12">
							<p>操作时间: {{item.LASTUPDATETIME}}</p>
						</div>
						<div class="col-sm-6 col-xs-12">
							<p>客户评价: {{item.SHIY}}</p>
						</div>
					</div>
	
				</div>
			</div> -->
			
		<ul class="list-group">
			<li class="list-group-item">客户名称: {{item.KEH}}</li>
			<li class="list-group-item">联系方式: <a :href="'tel:' + item.KEHDH">{{item.KEHDH}}</a></li>
			<li class="list-group-item">来电时间: 
					{{item.LAIDSJ | time}}
			</li>
			<li class="list-group-item">房产证面积: {{item.FANGCZMJ}}
				<span v-if="item.FANGCZMJ != null && item.FANGCZMJ != undefined && item.FANGCZMJ.trim() != ''" > 
					&nbsp;m²
				</span>
			</li>
			<li class="list-group-item">
				是否交房: <span v-if="item.SHIFJF == 0">未交房</span>
						<span v-if="item.SHIFJF == 1">已交房</span>
			</li>
			<li class="list-group-item">户 型: {{item.FANGX}}</li>
			<li class="list-group-item">详细地址: {{item.XIANGXDZ}}</li>
			<li class="list-group-item">新旧房: {{item.XINFJF}}</li>
			<li class="list-group-item">
				任务状态: <span v-if="item.RENWZT == 0" style="color: red"> 待邀约</span>
						<span v-if="item.RENWZT == 1" style="color: blue">已邀约</span>
			</li>
			<li class="list-group-item">操作时间: 
					{{item.LASTUPDATETIME | time}}
			</li>
			<li class="list-group-item">事 由: {{item.SHIY}}</li>
		</ul>
		
		
		<div class="text-center" v-if="item.RENWZT == 0">
			<a class="btn btn-default" href="javascript:;" :disabled="submitting"
				@click="changeTaskStatusById()">回访成功</a>
		</div>

		<br /> <br />
	</div>
</div>
<!-- container end-->
<script src="${ctx}/static/crm/js/detail.js"></script>