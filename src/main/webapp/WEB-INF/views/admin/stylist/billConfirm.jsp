<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>合同管理</title>
<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div class="row">
                <form id="searchForm" @submit.prevent="query">
                    <div class="col-md-2">
                        <div class="form-group">
                            <label class="sr-only" for="keyword"></label>
                            <input
                                    v-model="keyword"
                                    id="keyword"
                                    name="keyword"
                                    type="text"
                                    placeholder="姓名/工号/手机号" class="form-control"/>
                        </div>
                    </div>
                    <div class="col-md-1">
                        <div class="form-group">
                            <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default"
                                    alt="搜索"
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

            <div class="text-center">
                <button @click="rollback" :disabled="disabled" type="button"
                        class="btn btn-danger">取消
                </button>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <button @click="submit" :disabled="disabled" type="button"
                        class="btn btn-info">保存
                </button>
            </div>
        </div>
    </div>
    <!-- ibox end -->
</div>
<!-- container end-->

<div id="modal" class="modal fade" tabindex="-1" data-width="760">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>设计师账单详情</h3>
    </div>
    <div class="modal-body">
        <!-- <div class="columns columns-right btn-group pull-right"></div> -->
        <table v-el:bill-details-table id="billDetailsTable" width="100%"
               class="table table-striped table-bordered table-hover">
        </table>
    </div>

    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn">关闭</button>
    </div>

</div>

<script src="${ctx}/static/js/containers/stylist/billConfirm.js?v=1.0"></script>