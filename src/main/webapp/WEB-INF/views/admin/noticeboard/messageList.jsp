<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<title>我的消息列表</title>
</head>
<body id="app" class="fixed-sidebar full-height-layout gray-bg">
	<div id="container" class="wrapper wrapper-content">
		<!-- ibox start -->
		<div class="ibox">
			<div class="ibox-content">
				<div>
					<div class="row">
						<form id="searchForm" @submit.prevent="query">
							<div class="col-md-2">
								<div class="form-group">
									<label class="sr-only" for="keyword"></label> <input
										v-model="form.keyword" id="keyword" name="keyword" type="text"
										placeholder="消息标题" class="form-control" />
								</div>
							</div>
							<div class="col-md-2">
								<div class="form-group">
									<label class="sr-only"></label> <select v-model="form.status"
										id="status" name="status" placeholder="选择消息状态"
										class="form-control">
										<option value="">全部</option>
										<option value="0">未读</option>
										<option value="1">已读</option>
									</select>
								</div>
							</div>
							<div class="col-md-2">
								<div class="form-group">
									<label class="sr-only"></label> <select
										v-model="form.messageLevel" id="messageLevel"
										name="messageLevel" placeholder="选择消息级别" class="form-control">
										<option value="">全部</option>
										<option value="1">一般</option>
										<option value="2">重要</option>
										<option value="3">紧急</option>
										<option value="4">特急</option>
									</select>
								</div>
							</div>
							<div class="col-md-2">
								<div class="form-group">
									<label class="sr-only" for="beginTime">过期时间区间</label> <input
										v-model="form.beginTime" v-el:begin-time id="beginTime"
										type="text" readonly class="form-control datepicker"
										placeholder="请选择时间">
								</div>
							</div>
							<div class="col-md-2">
								<div class="form-group">
									<label class="sr-only" for="endTime"></label> <input
										v-model="form.endTime" v-el:end-time id="endTime"
										name="endTime" type="text" readonly
										class="form-control datepicker" placeholder="请选择结束时间">
								</div>
							</div>

							<div class="col-md-2">
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
					<table v-el:data-table id="dataTable" width="100%"
						class="table table-striped table-bordered table-hover">
					</table>
				</div>
			</div>
		</div>
		<!-- ibox end -->
	</div>
	</div>
	<div id="messageModal" class="modal fade" tabindex="-1"
		data-width="450">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3>消息详情</h3>
		</div>
		<div class="modal-body">
			<div class="row">
				<div class="form-group">
					<label class="col-sm-3 control-label">消息标题</label>
					<div class="col-sm-8">{{msg.messageTitle}}</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group">
					<label class="col-sm-3 control-label">消息内容</label>
					<div class="col-sm-8">{{msg.messageContent}}</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group">
					<label class="col-sm-3 control-label">重要级别</label>
					<div class="col-sm-8">{{msg.messageLevel | converLevel}}</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group">
					<label class="col-sm-3 control-label">创建时间</label>
					<div class="col-sm-8">{{msg.createTime}}</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group">
					<label class="col-sm-3 control-label">过期时间</label>
					<div class="col-sm-8">{{msg.expirationTime}}</div>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button :disabled="disabled" type="button" data-dismiss="modal"
				class="btn">关闭</button>
		</div>
	</div>
	<script src="${ctx}/static/js/containers/noticeboard/messageList.js"></script>
</body>
</html>