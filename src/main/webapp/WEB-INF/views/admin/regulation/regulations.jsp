<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>规章制度</title>
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
                                        placeholder="规章制度标题" class="form-control"/>
                            </div>
                        </div>
                        <div class="col-md-2">
                            <div class="form-group">
                                <select v-model="form.type"
                                        id="type"
                                        name="type"
                                        placeholder="规章制度分类"
                                        class="form-control">
                                    <option value="">全部状态</option>
                                    <option v-for="type of types" value="{{type.id}}">{{type.name}}</option>
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
                        <%--<div class="form-group">--%>
                            <%--<button @click="insertSign" id="createBtnq" type="button"--%>
                                    <%--class="btn btn-outline btn-primary">生成数据--%>
                            <%--</button>--%>
                        <%--</div>--%>
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
<!-- container end-->

<script src="${ctx}/static/js/containers/regulation/regulations.js?v=1.0"></script>
