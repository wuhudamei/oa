<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <title>数据字典</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
</head>
<body id="app" class="fixed-sidebar full-height-layout gray-bg">
<div id="wrapper">
    <!--右侧部分开始-->
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
                    <div class="zTreeDemoBackground">
                        <ul id="treeDemo" class="ztree"></ul>
                    </div>
                    <div class="row">
                        <form id="searchForm" @submit.prevent="query">
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label class="sr-only" for="keyword"></label>
                                    <input
                                            v-model="form.keyword"
                                            id="keyword"
                                            name="keyword"
                                            type="text"
                                            placeholder="请输入名称" class="form-control"/>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <select v-model="form.searchType"
                                            id="searchParentId"
                                            name="searchParentId"
                                            placeholder="选择父级代码"
                                            class="form-control">
                                        <option value="">全部</option>
                                        <option value="1">科目大类</option>
                                        <option value="2">费用小类</option>
                                        <option value="3">费用明细</option>
                                        <option value="4">规章制度</option>
                                        <option value="5">请假类型</option>
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
                            <!-- 将剩余栅栏的长度全部给button -->
                            <div class="col-md-7 text-right">
                                <shiro:hasPermission name="dictionary:add">
                                    <div class="form-group">
                                        <button @click="createBtnClickHandler" id="createBtn" type="button"
                                                class="btn btn-outline btn-primary">新增
                                        </button>
                                    </div>
                                </shiro:hasPermission>
                            </div>
                        </form>
                    </div>
                    <!-- <div class="columns columns-right btn-group pull-right"></div> -->
                    <table v-el:data-table id="dataTable" width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
            <!-- ibox end -->
        </div>
        <!-- container end-->

        <div id="modal" class="modal fade" tabindex="-1" data-width="760">
            <validator name="validation">
                <form name="createMirror" novalidate class="form-horizontal" role="form">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3>编辑数据字典</h3>
                    </div>
                    <div class="modal-body">
                        <div class="form-group" :class="{'has-error':$validation.type.invalid && submitBtnClick}">
                            <label for="type" class="col-sm-2 control-label">类别</label>
                            <div class="col-sm-8">
                                <select
                                        v-validate:type="{required:true}"
                                        v-model="dict.type"
                                        id="type"
                                        name="type"
                                        data-tabindex="2"
                                        class="form-control">
                                    <option value="">请选择类别</option>
                                    <%--<option value="1">科目大类</option>--%>
                                    <option value="2">费用小类</option>
                                    <option value="3">费用明细</option>
                                    <option value="4">规章制度</option>
                                    <option value="5">请假类型</option>
                                </select>

                                <div v-if="$validation.type.invalid && $validation.touched"
                                     class="help-absolute">
                                    <span v-if="$validation.type.invalid">请选择类别</span>
                                </div>
                            </div>
                        </div>
                        <div v-if="dict.type==2||dict.type==3" class="form-group" :class="{'has-error':$validation.parentid.invalid && submitBtnClick}">
                            <label for="parentid" class="col-sm-2 control-label">父类别</label>
                            <div class="col-sm-8">
                                <select
                                        v-validate:parentId="{required:true}"
                                        v-model="dict.parentCode"
                                        id="parentid"
                                        name="parentid"
                                        data-tabindex="1"
                                        class="form-control">
                                    <option value="">请选择父类别</option>
                                    <%--v-if="parent.type==selectType"--%>
                                    <option  v-for="parent in parents"
                                            :value="parent.id">
                                        {{parent.name}}
                                    </option>
                                </select>

                                <div v-if="$validation.parentid.invalid && $validation.touched"
                                     class="help-absolute">
                                    <span v-if="$validation.parentid.invalid">请选择父类别</span>
                                </div>
                            </div>
                        </div>

                        <div class="form-group" :class="{'has-error':$validation.name.invalid && submitBtnClick}">
                            <label for="name" class="col-sm-2 control-label">字典名称</label>
                            <div class="col-sm-8">
                                <input v-model="dict.name"
                                       v-validate:name="{
                                    required:{rule:true,message:'请输入字典名称'},
                                    maxlength:{rule:20,message:'字典名称最长不能超过20个字符'}
                                }"
                                       data-tabindex="3"
                                       id="name" name="name" type="text" class="form-control" placeholder="请输入字典名称">
                                <span v-cloak v-if="$validation.name.invalid && submitBtnClick" class="help-absolute">
                            <span v-for="error in $validation.name.errors">
                              {{error.message}} {{($index !== ($validation.name.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                            </div>
                        </div>

                        <div class="form-group" :class="{'has-error':$validation.sort.invalid && submitBtnClick}">
                            <label for="sort" class="col-sm-2 control-label">排序</label>
                            <div class="col-sm-8">
                                <input v-model="dict.sort"
                                       v-validate:sort="{
                                    required:{rule:true,message:'请输入排序值'},
                                    maxlength:{rule:5,message:'排序值最长不能超过5个字符'}
                                }"
                                       type="number"
                                       data-tabindex="4"
                                       id="sort" name="sort" type="text" class="form-control" placeholder="请输入排序值">
                                <span v-cloak v-if="$validation.sort.invalid && submitBtnClick" class="help-absolute">
                            <span v-for="error in $validation.sort.errors">
                              {{error.message}} {{($index !== ($validation.name.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
                        <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
                    </div>
                </form>
            </validator>
        </div>

    <!--右侧部分结束-->
</div>
<!-- 主方法，每页均需引用 -->
<script src="/static/js/containers/dictionary/diclist.js"></script>
</body>
</html>