<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>我的申请</title>
<link rel="stylesheet" href="/static/css/tab.css">
<div id="container" class="wrapper wrapper-content animated fadeInRight">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div>
                <Tabs id="tabCard" type="card" @on-click="clickEvent">
                    <Tab-pane label="通用申请">
                        <div class="row">
                            <form id="searchForm" @submit.prevent="queryCommon">
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
                                        <button id="searchBtn" type="submit"
                                                class="btn btn-block btn-outline btn-default" alt="搜索"
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
                       <Tab-pane label="费用">
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
                                <div class="col-md-2">
                                    <div class="form-group">
                                        <select v-model="form.signatureStatus"
                                                id="signatureStatus"
                                                name="signatureStatus"
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
                                        <button id="searchBtn" type="submit"
                                                class="btn btn-block btn-outline btn-default" alt="搜索"
                                                title="搜索">
                                            <i class="fa fa-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <table id="dataTableSignature" width="100%"
                               class="table table-striped table-bordered table-hover">
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
                                        <button id="searchBtn" type="submit"
                                                class="btn btn-block btn-outline btn-default" alt="搜索"
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
                                <div class="col-md-2">
                                    <div class="form-group">
                                        <select v-model="form.paymentStatus"
                                                id="paymentStatus"
                                                name="paymentStatus"
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
                                        <button id="searchBtn" type="submit"
                                                class="btn btn-block btn-outline btn-default" alt="搜索"
                                                title="搜索">
                                            <i class="fa fa-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <table id="dataTablePayment" width="100%"
                               class="table table-striped table-bordered table-hover">
                        </table>
                    </Tab-pane>
                </Tabs>
            </div>
        </div>
    </div>
    <!-- ibox end -->
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

<!-- 报销单详情-->
<div id="paymentDetail" class="modal fade" tabindex="-1" data-width="760">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>报销申请详情</h3>
    </div>
    <div class="modal-body">
        <jsp:include page="../payment/detail.jsp"></jsp:include>
    </div>
    <div class="modal-footer">
        <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">关闭</button>
    </div>
</div>

<!-- 报销审批详情 Test-->
<div id="multDetail" class="modal fade" tabindex="-1" data-width="760">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>报销申请详情</h3>
    </div>
    <div class="modal-body">
        <jsp:include page="../payment/multDetail.jsp"></jsp:include>
    </div>
    <div class="modal-footer">
        <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">关闭</button>
    </div>
</div>
<script src="${ctx}/static/js/components/tab.js"></script>
<script src="${ctx}/static/js/containers/businessAway/leaveAndBusiness.js"></script>