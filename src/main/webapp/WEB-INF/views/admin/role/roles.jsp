<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>角色管理</title>
<link rel="stylesheet" href="${ctx}/static/css/zTreeStyle.css">
<script src="${ctx}/static/js/jquery.ztree.core.js"></script>
<script src="${ctx}/static/js/jquery.ztree.excheck.js"></script>

<%@include file="/WEB-INF/views/admin/components/ztree.jsp" %>

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
                                    v-model="form.keyword"
                                    id="keyword"
                                    name="keyword"
                                    type="text"
                                    placeholder="科目角色名" class="form-control"/>
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
                    <div class="col-md-9 text-right">
                        <shiro:hasPermission name="role:add">
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

<!-- 新建/编辑的modal-->
<div id="modal" class="modal fade" tabindex="-1" data-width="760">
    <validator name="validation">
        <form name="createMirror" novalidate class="form-horizontal" role="form">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3>新建角色</h3>
            </div>
            <div class="modal-body">
                <div class="form-group" :class="{'has-error':$validation.name.invalid && submitBtnClick}">
                    <label for="name" class="col-sm-2 control-label">名称</label>
                    <div class="col-sm-8">
                        <input v-model="role.name"
                               v-validate:name="{required:{rule:true,message:'请输入角色名称'}}"
                               maxlength="20"
                               data-tabindex="1"
                               id="name" name="name" type="text" class="form-control" placeholder="名称">
                        <span v-cloak v-if="$validation.account.invalid && submitBtnClick"
                              class="help-absolute">
              <span v-for="error in $validation.name.errors">
                {{error.message}} {{($index !== ($validation.name.errors.length -1)) ? ',':''}}
              </span>
            </span>
                    </div>
                </div>

                <div class="form-group"
                     :class="{'has-error':$validation.description.invalid && submitBtnClick}">
                    <label for="description" class="col-sm-2 control-label">描述</label>
                    <div class="col-sm-8">
                        <input v-model="role.description"
                               v-validate:description="{
                                    required:{rule:true,message:'请输入描述'}
                                }"
                               maxlength="20"
                               data-tabindex="1"
                               id="description" name="description" type="text" class="form-control"
                               placeholder="描述">
                        <span v-cloak v-if="$validation.description.invalid && submitBtnClick"
                              class="help-absolute">
                            <span v-for="error in $validation.description.errors">
                              {{error.message}} {{($index !== ($validation.description.errors.length -1)) ? ',':''}}
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

<!--
<div id="permissionModal" class="modal fade" tabindex="-1" data-width="760">
    <form name="createMirror" novalidate class="form-inline" role="form">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3>设置角色权限</h3>
        </div>

        <div class="modal-body">
            <div class="form-group">
                <div class="row ">
                    <div class="col-md-3 form-group role-item ellips">
                        <label>
                            <input @click="selAllCb(permissions,$event)" id="selAllCb"
                                   type="checkbox"
                                   data-checkbox="sub">
                            所有权限</label>
                    </div>
                </div>
                <div v-for="permission in permissions">
                    <div class="row ">
                        <div class="col-md-3 col-xs-3 form-group role-item ellips">
                            <label>
                                <input @click="checkAll(permission,$event)" style="margin-left:40px"
                                       type="checkbox"
                                       data-checkbox="select"> {{$key}}</label>
                        </div>
                    </div>

                    <div class="row" style="margin-left:60px">
                        <div v-for="content in permission"
                             class="col-md-4 col-xs-4  form-group role-item ellips">
                            <label>
                                <input @click="checkSub(content,$event)" type="checkbox"
                                       :checked="content.checked"
                                       data-checkbox="sub">{{content.name}}</label>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal-footer">
            <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
            <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
        </div>
    </form>
</div>
-->

<div id="permissionModal" class="modal fade" tabindex="-1" data-width="760">
    <form name="createMirror" novalidate class="form-inline" role="form">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3>设置角色科目权限</h3>
        </div>

        <div class="modal-body">
            <div class="form-group">
                <div class="row ">
                    <div class="col-md-12 form-group role-item ellips">
                        <z-tree v-ref:nodes-checkbox :nodes="nodesCheck" :show-tree.sync="true" :chkbox-type="chkboxType" :abs="false"></z-tree>
                    </div>
                </div>

            </div>
        </div>

        <div class="modal-footer">
            <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
            <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
        </div>
    </form>
</div>

<script src="${ctx}/static/js/components/ztree.js"></script>
<script src="${ctx}/static/js/containers/role/roles.js"></script>
