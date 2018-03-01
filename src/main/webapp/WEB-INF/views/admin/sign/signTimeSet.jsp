<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>组织架构管理</title>
<link rel="stylesheet" href="/static/vendor/jstree/themes/default/style.css"/>
<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div class="row">
                <form id="searchForm">
                    <div class="col-md-12 text-left">
                        <div class="form-group" id="buttons">
                            <a id="createBtn" @click="createBtnClickHandler"
                               class="btn btn-outline btn-primary">设置上下班时间</a>
                        </div>
                    </div>
                </form>
            </div>
            <div class="ibox-content">
                <div class="row">
                    <div class="col-md-5">
                        <div id="jstree"></div>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <!-- ibox end -->
</div>
<!-- container end-->
<div id="modal" class="modal fade" tabindex="-1" data-width="760">

    <div class="modal-header" style="text-align:center">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>设置上下班时间</h3>
    </div>
    <validator name="validation">
        <form name="createMirror" novalidate class="form-horizontal" role="form">
            <div class="modal-body">
                <div>
                    <div class="col-sm-5">
                        <div class="form-group" style="line-height: 34px"
                             :class="{'has-error':$validation.signtime.invalid && $validation.touched}">
                            <label for="startTime" class="col-sm-4 control-label">上班时间</label>
                            <div class="col-sm-7 no-padding">
                                <input
                                        v-model="startTime"
                                        v-validate:signtime="{required:true}"
                                        id="startTime"
                                        name="startTime"
                                        type="text"
                                        class="form-control datepicker"
                                        placeholder="如:10:00(24小时制)">
                                </select> <span v-cloak
                                                v-if="$validation.signtime.invalid && $validation.touched"
                                                class="help-absolute"> 请填写上班时间 </span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-5">
                    <div class="form-group" style="line-height: 34px"
                         :class="{'has-error':$validation.signouttime.invalid && $validation.touched}">
                        <label for="endTime" class="col-sm-4 control-label">下班时间</label>
                        <div class="col-sm-7 no-padding">
                            <input
                                    v-model="endTime"
                                    v-validate:signouttime="{required:true}"
                                    id="endTime"
                                    name="endTime"
                                    type="text"
                                    class="form-control datepicker"
                                    placeholder="如:19:00(24小时制)">
                            <span v-cloak
                                  v-if="$validation.signouttime.invalid && $validation.touched"
                                  class="help-absolute"> 请填写下班时间 </span>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </validator>
    <div class="modal-footer" style="text-align: center">
        <button @click="submit" type="button" class="btn btn-primary">提交</button>
        <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
    </div>

</div>


<script src="/static/vendor/iCheck/icheck.min.js"></script>

<script src="/static/vendor/sweetalert/sweetalert.min.js"></script>
<script src="/static/vendor/jstree/jstree.js"></script>
<script src="${ctx}/static/js/containers/sign/signTimeSet.js"></script>
