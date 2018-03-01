<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>年度预算</title>
<!-- 面包屑 -->
<link rel="stylesheet" href="/static/css/tab.css">
<div id="container" class="wrapper wrapper-content">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div>
                <Tabs type="card">
                    <Tab-pane label="我的申请">
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
                                <div class="col-md-2">
                                    <div class="form-group">
                                        <select v-model="form.status"
                                                id="status"
                                                name="status"
                                                placeholder="选择状态"
                                                class="form-control">
                                            <option value="">全部状态</option>
                                            <option value="APPROVALING">审核中</option>
                                            <option value="ADOPT">审核通过</option>
                                            <option value="REFUSE">拒绝</option>
                                            <option value="DRAFT">草稿</option>
                                        </select>
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
                        <!-- <div class="columns columns-right btn-group pull-right"></div> -->
                        <table v-el:data-table id="dataTable" width="100%"
                               class="table table-striped table-bordered table-hover">
                        </table>
                    </Tab-pane>
                    <Tab-pane label="审批历史">
                        <div class="row">
                            <form id="searchForm2" @submit.prevent="query">
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label class="sr-only" for="keyword"></label>
                                        <input
                                                v-model="form.keywordHis"
                                                id="keywordHis"
                                                name="keywordHis"
                                                type="text"
                                                placeholder="申请标题/申请编号" class="form-control"/>
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <div class="form-group">
                                        <button id="searchBtn2" type="submit" class="btn btn-block btn-outline btn-default" alt="搜索"
                                                title="搜索">
                                            <i class="fa fa-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <table id="dataTableHis" width="100%" class="table table-striped table-bordered table-hover">
                        </table>
                    </Tab-pane>
                </Tabs>
            </div>
        </div>
    </div>
    <!-- ibox end -->
</div>
<!-- container end-->

<!-- 预算详情-->
<div id="detail" class="modal fade" tabindex="0" data-width="900">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>年度预算申请详情</h3>
    </div>
    <div class="modal-body">
        <jsp:include page="detail.jsp"></jsp:include>
    </div>
<!--     <div class="modal-footer"> -->
<!--         <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">关闭</button> -->
<!--     </div> -->
</div>

<!-- 历史审批详情 -->
<div id="approveHisModel" class="modal fade" tabindex="0" data-width="900">
    <validator name="validation">
        <form name="createMirror" novalidate class="form-horizontal" role="form">
            <jsp:include page="../approval/approvalDetail.jsp"></jsp:include>
        </form>
    </validator>
</div>

<script src="${ctx}/static/js/components/tab.js?v=1.0"></script>
<script src="${ctx}/static/js/containers/yearbudget/budgets.js?v=1.1"></script>
