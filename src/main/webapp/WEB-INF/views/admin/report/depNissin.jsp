<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>分店日清报表</title>
    <%@include file="/WEB-INF/views/admin/shims/config.jsp" %>
</head>
<body id="app" class="fixed-sidebar full-height-layout gray-bg">
<!-- container -->
<!-- 内容部分 start-->
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
                    <div class="col-md-3">
                        <div class="form-group">
                            <input v-model="form.date"
                                   id="date" name="date"
                                   type="text" class="datepicker form-control"
                                   placeholder="时间" readonly="readonly"/>
                        </div>
                    </div>
                    <div class="col-md-2">
                            <select v-model="form.companyId" class="form-control"
                                   type="text"
                                   >
                                <option value="">请选择分店</option>
                                <option v-for="company in companys" :value="company.id">{{company.orgName}}</option>
                            </select>
                    </div>

                    <div class="col-md-2">
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
            <table  v-el:data-table width="100%" id="dataTable" class="table table-striped table-bordered table-hover">
            </table>
        </div>
    </div>
    <!-- ibox end -->
</div>
<!-- container end-->

<script src="${ctx}/static/vendor/md5/md5.min.js"></script>
<script src="${ctx}/static/js/containers/report/depNissin.js"></script>
</body>
</html>