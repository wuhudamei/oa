<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>待审批</title>
<link rel="stylesheet" href="/static/css/tab.css">

<div id="container" class="wrapper wrapper-content animated fadeInRight">
  <div id="breadcrumb">
    <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
  </div>
  <!-- ibox start -->
  <div class="ibox">
    <div class="ibox-content">
      <div>
        <Tabs type="card"  @on-click="clickEvent">
         <Tab-pane label="通用申请">
            <div class="row">
              <form id="searchForm" @submit.prevent="queryCommon">
                <div class="col-md-4">
                  <div class="form-group">
                    <label class="sr-only" for="keyword"></label>
                    <input
                            v-model="form.keywordSignature"
                            id="keywordSignature"
                            name="keywordSignature"
                            type="text"
                            placeholder="申请标题/申请编号/申请人" class="form-control"/>
                  </div>
                </div>
                <div class="col-md-1">
                  <div class="form-group">
                    <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default" alt="搜索"
                            title="搜索">
                      <i class="fa fa-search"></i>
                    </button>
                  </div>
                </div>
              </form>
            </div>
            <table id="dataTableCommon" width="100%" class="table table-striped table-bordered table-hover">
            </table>
          </Tab-pane>
          <Tab-pane label="费用" >
            <div class="row">
              <form id="searchForm" @submit.prevent="querySignature">
                <div class="col-md-4">
                  <div class="form-group">
                    <label class="sr-only" for="keyword"></label>
                    <input
                            v-model="form.keywordSignature"
                            id="keywordSignature"
                            name="keywordSignature"
                            type="text"
                            placeholder="申请标题/申请编号" class="form-control"/>
                  </div>
                </div>
                <div class="col-md-1">
                  <div class="form-group">
                    <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default" alt="搜索"
                            title="搜索">
                      <i class="fa fa-search"></i>
                    </button>
                  </div>
                </div>
              </form>
            </div>
            <table id="dataTableSignature" width="100%" class="table table-striped table-bordered table-hover">
            </table>
          </Tab-pane>
          <Tab-pane label="请假" >
            <div class="row">
              <form id="searchForm" @submit.prevent="query">
                <div class="col-md-4">
                  <div class="form-group">
                    <label class="sr-only" for="keyword"></label>
                    <input
                            v-model="form.keyword"
                            id="keyword"
                            name="keyword"
                            type="text"
                            placeholder="申请标题/申请编号" class="form-control"/>
                  </div>
                </div>
                <div class="col-md-1">
                  <div class="form-group">
                    <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default" alt="搜索"
                            title="搜索">
                      <i class="fa fa-search"></i>
                    </button>
                  </div>
                </div>
              </form>
            </div>
            <table id="dataTable" width="100%" class="table table-striped table-bordered table-hover">
            </table>
          </Tab-pane>

          <Tab-pane label="报销" >
            <div class="row">
              <form id="searchForm" @submit.prevent="queryPayment">
                <div class="col-md-4">
                  <div class="form-group">
                    <label class="sr-only" for="keyword"></label>
                    <input
                            v-model="form.keywordPayment"
                            id="keywordPayment"
                            name="keywordPayment"
                            type="text"
                            placeholder="申请标题/申请编号" class="form-control"/>
                  </div>
                </div>
                <div class="col-md-1">
                  <div class="form-group">
                    <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default" alt="搜索"
                            title="搜索">
                      <i class="fa fa-search"></i>
                    </button>
                  </div>
                </div>
              </form>
            </div>
            <table id="dataTablePayment" width="100%" class="table table-striped table-bordered table-hover">
            </table>
          </Tab-pane>
         
        </Tabs>
      </div>
    </div>
  </div>
  <!-- ibox end -->
</div>
<div class="modal fade" id="turnListModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					审批转派
				</h4>
			</div>
			<div class="modal-body">
				<form id="stylistSearchForm" @submit.prevent="query">
				<div class="row">
						<div class="col-md-4">
							<div class="form-group">
								<input
										v-model="form.keyword"
										type="text"
										placeholder="工号/姓名/手机号" class="form-control"/>
							</div>
						</div>
						<div class="col-md-3 text-right">
							<div class="form-group">
								<button id="stylistSearchBtn"  class="btn btn-block btn-outline btn-default"
										alt="搜索"
										title="搜索">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
				</div>
				<div class="row">
					<div class="col-md-10">
						<div class="row">
						 　　<font color="red">注：双击行，添加审批人</font>
							<div class="form-group">
								<table id="turnListDataTable" width="100%" class="table table-striped table-bordered table-hover">
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-10">
						<div class="form-group">
					    	<label for="name">转派描述</label>
					    	<textarea class="form-control"  rows="3" v-model="form.remark"></textarea>
					  	</div>
					</div>
				</div>
				<div class="row">
					<div class="btn-group" v-for="btn in turnEmployees">
						<span v-if="$index > 0">►</span>
						<span>
							<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">{{btn.name}} 
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu" style="min-width:100%;" role="menu">
								<li><a href="#" @click="deleteItem(btn)">删除</a></li>
							</ul>
						</span>
					</div>
					<br/>
					<br/>
					<br/>
				</div>
			</div>
			</form>
			<div class="modal-footer">
				<button type="button"  @click="turnCommitStylist" class="btn btn-primary">
					确定
				</button>
			</div>
		</div><!-- /.modal-content -->
</div><!-- /.modal -->
<div class="modal fade" id="ccPersonStylistModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					添加抄送人
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<form id="stylistSearchForm" @submit.prevent="query">
						<div class="col-md-4">
							<div class="form-group">
								<input
										v-model="form.keyword"
										type="text"
										placeholder="工号/姓名/手机号" class="form-control"/>
							</div>
						</div>
						<div class="col-md-3 text-right">
							<div class="form-group">
								<button id="stylistSearchBtn"  class="btn btn-block btn-outline btn-default"
										alt="搜索"
										title="搜索">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
					</form>
				</div>
				<div class="row">
					<div class="col-md-10">
						<div class="row">
						 　　<font color="red">注：双击行，添加抄送人</font>
							<div class="form-group">
								<table id="ccPersonDataTable" width="100%" class="table table-striped table-bordered table-hover">
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<span>
						<button type="button" class="btn btn-primary " v-for="btn in sourceCCPersonInfo">{{btn.name}} 
						</button>
						<div class="btn-group" v-for="btn in newCCPersonInfo">
							<button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown" >{{btn.name}} 
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu" style="min-width:100%;" role="menu">
								<li><a href="#" @click="deleteItem(btn)">移除</a></li>
							</ul>
						</div>
					</span>
					<br/>
					<br/>
					<br/>
				</span>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button"  @click="commitCCUser" class="btn btn-primary">
					确定
				</button>
			</div>
		</div><!-- /.modal-content -->
</div><!-- /.modal -->
<div class="modal fade" id="addApproverModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					{{modelTitle}}
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<form id="stylistSearchForm" @submit.prevent="query">
						<div class="col-md-4">
							<div class="form-group">
								<input
										v-model="form.keyword"
										type="text"
										placeholder="工号/姓名/手机号" class="form-control"/>
							</div>
						</div>
						<div class="col-md-3 text-right">
							<div class="form-group">
								<button id="stylistSearchBtn"  class="btn btn-block btn-outline btn-default"
										alt="搜索"
										title="搜索">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
					</form>
				</div>
				<div class="row">
					<div class="col-md-10">
						<div class="row">
						 　　<font color="red">注：双击行，添加审批人</font>
							<div class="form-group">
								<table id="addApproverDataTable" width="100%" class="table table-striped table-bordered table-hover">
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-10">
						<div class="form-group">
					    	<label for="name">添加描述</label>
					    	<textarea class="form-control"  rows="3" v-model="form.remark"></textarea>
					  	</div>
					</div>
				</div>
				<div class="row">
					<span>
						<div class="btn-group" v-for="btn in sourceApproveListAdd" >
							<button type="button" v-if="$index == addIndex && resultAdd " class="btn btn-danger dropdown-toggle"  data-toggle="dropdown">{{btn.name}}
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu" style="min-width:100%;" role="menu" v-if="$index == addIndex && resultAdd ">
								<li><a href="#" @click="deleteItem(btn)">移除</a></li>
							</ul>
							
							<button type="button" class="btn btn-primary "  v-else v-if="currentUser.userId == btn.id" >
									 {{btn.name}} 
							</button>
						</div>
					</span>
					<br/>
					<br/>
					<br/>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button"  @click="commitaddApprover" class="btn btn-primary">
					确定
				</button>
			</div>
		</div><!-- /.modal-content -->
</div><!-- /.modal -->
<div class="modal fade" id="moreModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					更多操作
				</h4>
			</div>
			<div class="modal-body">
				<div class="row" align="center">
						<button type="button" @click="turnAdd" class="btn btn-primary" style="width: 110px; margin-top: 5px;">转派</button><br/>
						<button type="button" @click="addApproverBefore" class="btn btn-primary" style="width: 110px; margin-top: 5px;">前添加审批人</button><br/>
						<button type="button" @click="addApproverAfter" class="btn btn-primary"  style="width: 110px; margin-top: 5px;">后添加审批人</button><br/>
						<button type="button" @click="addCCuser" class="btn btn-primary"  style="width: 110px; margin-top: 5px;">添加抄送人</button>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button"  @click="closeCommitaddApprover" class="btn btn-primary">
					关闭
				</button>
			</div>
		</div><!-- /.modal-content -->
</div><!-- /.modal -->
<!-- 流程表单详情 -->
<div id="approveHisModel" class="modal fade" tabindex="0" data-width="900">
  <validator name="validation">
    <form name="createMirror" novalidate class="form-horizontal" role="form">
      	<jsp:include page="../approval/approvalDetail.jsp"></jsp:include>
        <jsp:include page="../approval/approvalButton.jsp"></jsp:include>
    </form>
  </validator>
</div>
<script src="${ctx}/static/js/components/tab.js"></script>
<script src="${ctx}/static/js/containers/approval/list.js"></script>