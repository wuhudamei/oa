<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<hthml>
    <head>
        <title>职场设置</title>
        <script src="http://api.map.baidu.com/api?v=2.0&ak=QIaFKnOrdaK5vnRYlYqwUGiv"></script>
    </head>
    <body>

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
                        <!-- 将剩余栅栏的长度全部给button -->
                        <div class="col-md-12 text-right">
                            <shiro:hasPermission name="admin:signsite:add">
                                <div class="form-group">
                                    <button @click="createBtnClickHandler" id="createBtn" type="button"
                                            class="btn btn-outline btn-primary">新增
                                    </button>
                                </div>
                            </shiro:hasPermission>
                        </div>
                    </form>
                </div>
                <table v-el:data-table id="dataTable" width="100%"
                       class="table table-striped table-bordered table-hover">
                </table>
            </div>
        </div>
        <!-- ibox end -->
    </div>
    <!-- container end-->

    <!-- 新建/编辑的modal-->
    <div id="modal" class="modal fade" tabindex="-1" data-width="860">
        <validator name="validation">
            <form name="createMirror" novalidate class="form-horizontal" signsite="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3>{{title}}</h3>
                </div>
                <div class="modal-body">
                    <div class="form-group" :class="{'has-error':$validation.name.invalid && submitBtnClick}">
                        <label class="col-sm-3 control-label">名称</label>
                        <div class="col-sm-8">
                            <input v-model="signsite.sitename"
                                   v-validate:name="{required:{rule:name,message:'请输入职场名称'}}"
                                   maxlength="20"
                                   data-tabindex="1"
                                   type="text"
                                   class="form-control" placeholder="请输入职场名称">
                            <span v-cloak v-if="$validation.name.invalid && submitBtnClick"
                                  class="help-absolute" style="margin-top:2px;">
              <span v-for="error in $validation.name.errors">
                {{error.message}} {{($index !== ($validation.name.errors.length -1)) ? ',':''}}
              </span>
            </span>
                        </div>
                    </div>
                    <div class="form-group"
                         :class="{'has-error':$validation.longitude.invalid && submitBtnClick}">
                        <label class="col-sm-3 control-label">经度</label>
                        <div class="col-sm-8">
                            <input v-model="signsite.longitude"
                                   maxlength="20"
                                   v-validate:longitude="{required:{rule:longitude,message:'请输入经度'}}"
                                   data-tabindex="1"
                                   id="longitude" name="description" type="number"
                                   class="form-control"
                                   placeholder="请输入经度">
                            <span v-cloak v-if="$validation.longitude.invalid && submitBtnClick"
                                  class="help-absolute" style="margin-top:2px;">
                            <span v-for="error in $validation.longitude.errors">
                              {{error.message}} {{($index !== ($validation.longitude.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                        </div>
                    </div>
                    <div class="form-group"
                         :class="{'has-error':$validation.latitude.invalid && submitBtnClick}">
                        <label class="col-sm-3 control-label">纬度</label>
                        <div class="col-sm-8">
                            <input v-model="signsite.latitude"
                                   maxlength="20"
                                   v-validate:latitude="{required:{rule:name,message:'请输入纬度'}}"
                                   data-tabindex="1"
                                   id="latitude" name="latitude" type="number"
                                   class="form-control"
                                   placeholder="请输入纬度">
                            <span v-cloak v-if="$validation.latitude.invalid && submitBtnClick"
                                  class="help-absolute" style="margin-top:2px;">
                            <span v-for="error in $validation.latitude.errors">
                              {{error.message}} {{($index !== ($validation.latitude.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                        </div>
                    </div>

                    <div class="form-group"
                         :class="{'has-error':$validation.radii.invalid && submitBtnClick}">
                        <label class="col-sm-3 control-label">有效半径</label>
                        <div class="col-sm-8">
                            <input v-model="signsite.radii"
                                   maxlength="20"
                                   v-validate:radii="{required:{rule:radii,message:'请输入有效半径'}}"
                                   data-tabindex="1"
                                   name="description" type="number"
                                   class="form-control"
                                   placeholder="请输入有效半径">
                            <span v-cloak v-if="$validation.radii.invalid && submitBtnClick"
                                  class="help-absolute" style="margin-top:2px;">
                            <span v-for="error in $validation.radii.errors">
                              {{error.message}} {{($index !== ($validation.radii.errors.length -1)) ? ',':''}}
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
    <script src="${ctx}/static/js/containers/sign/signsite.js"></script>
    </body>
</hthml>




