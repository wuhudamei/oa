<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<head>
    <title>竣工工地数据列表详情</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <style>
    </style>
</head>
<body id="app" class="fixed-sidebar full-height-layout gray-bg">
<div id="container" class="wrapper wrapper-content" v-cloak>
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div>
                <div class="row">
                    <form id="searchForm" @submit.prevent="query">
                        <div class="col-md-2">
                            <div class="form-group">
                                <label class="sr-only" for="keyword">项目编号/姓名/手机号</label>
                                <input
                                        v-model="form.keyword"
                                        id="keyword"
                                        name="keyword"
                                        type="text"
                                        placeholder="项目编号/姓名/手机号" class="form-control"/>
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
                        <button onclick="javascript:history.go(-1);" type="button" data-dismiss="modal" class="btn">返回</button>
                    </form>
                </div>
                <!-- <div class="columns columns-right btn-group pull-right"></div> -->
                <table v-el:data-table id="dataTable" width="100%"
                       class="table table-striped table-bordered table-hover">
                </table>
            </div>
        </div>
    </div>
    <!-- ibox end -->
</div>
<script src="/static/js/containers/completionSiteData/listDetails.js"></script>
</body>
</html>