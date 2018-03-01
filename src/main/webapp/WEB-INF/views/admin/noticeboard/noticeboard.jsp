<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<title>公告通知</title>
<meta name="keywords" content="">
<meta name="description" content="">
<style>
</style>
</head>
<body id="app" class="fixed-sidebar full-height-layout gray-bg">
<div id="container" class="wrapper wrapper-content">
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
                                <label class="sr-only" for="title"></label>
                                <input
                                        v-model="form.title"
                                        id="title"
                                        name="title"
                                        type="text"
                                        placeholder="公告标题" class="form-control"/>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="form-group">
                                <select v-model="form.status"
                                        id="status"
                                        name="status"
                                        class="form-control">
                                    <option value="" selected>--公告状态--</option>
                                    <option value="1">普通</option>
                                    <option value="2">重要</option>
                                    <option value="3">紧急</option>
                                </select>
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
                <table v-el:dataTable id="dataTable" width="100%"
                       class="table table-striped table-bordered table-hover">
                </table>
            </div>
        </div>
    </div>
    <!-- ibox end -->
</div>


<!-- container end-->
<%-- 公告详情的弹窗 --%>
<%--<div id="modal" class="modal fade" tabindex="-1" data-width="760">--%>
<%--<div class="wrapper wrapper-content">--%>
<%--<div class="ibox-content">--%>
<%--<form name="createMirror" novalidate class="form-horizontal" role="form" style="margin-right:10px">--%>
<%--<div class="text-center">--%>
<%--<h3>{{noticeboard.title}}</h3>--%>
<%--</div>--%>
<%--<hr/>--%>
<%--<div class="row">--%>
<%--<div class="col-sm-12">--%>
<%--<div class="row">--%>
<%--<div class="form-group" style="margin-bottom: 45px;">--%>
<%--<div class="col-sm-12 noticeImg">--%>
<%--{{{noticeboard.content}}}--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="row">--%>
<%--<div class="form-group">--%>
<%--<div class="col-md-12" >--%>
<%--<span  style="display:inline-block;border: 0;">&nbsp;&nbsp;&nbsp;&nbsp;发布人：{{noticeboard.createName}}</span>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--<div class="row">--%>
<%--<div class="form-group">--%>
<%--<div class="col-md-12 ">--%>
<%--<span  style="display:inline-block;border: 0;">&nbsp;&nbsp;&nbsp;&nbsp;发布时间：{{noticeboard.createTime}}</span>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>

<%--</div>--%>
<%--</form>--%>

<%--<!-- ibox end -->--%>
<%--</div>--%>
<%--<div class="modal-footer">--%>
<%--<button @click="cancel" type="button" data-dismiss="modal" class="btn">关闭</button>--%>
<%--</div>--%>
<%--</div>--%>
<%--</div>--%>

</div>
<script src="/static/js/containers/noticeboard/noticeboard.js"></script>
</body>
</html>