<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>设计师管理</title>
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
                                    placeholder="姓名/手机号" class="form-control"/>
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
                        <shiro:hasPermission name="stylist:manager-new">
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

<!-- 添加设计师弹窗-->
<div id="modal" class="modal fade" tabindex="-1" data-width="760">
    <validator name="validation">
        <form name="createMirror" novalidate class="form-horizontal" role="form">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3>添加设计师</h3>
            </div>
            <div class="modal-body">
                <div class="form-group" :class="{'has-error':$validation.minister.invalid && submitBtnClick}">
                    <label for="minister" class="col-sm-2 control-label">部长:</label>
                    <div class="col-sm-3">
                        <input
                                id="minister"
                                v-model="minister.name"
                                class="form-control"
                                disabled
                                v-validate:minister="{required:{rule:true,message:'请选择部长'}}"
                                type="text">
                        <span v-cloak v-if="$validation.minister.invalid && submitBtnClick"
                              class="help-absolute">
              <span v-for="error in $validation.minister.errors">
                {{error.message}} {{($index !== ($validation.minister.errors.length -1)) ? ',':''}}
              </span>
            </span>
                    </div>
                    <div class="col-sm-2">
                        <input type="button" @click="addStylist('MINISTER')"
                               class="btn-sm-2 btn-info" value="选择">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">设计师:</label>
                    <div class="col-sm-8">
                        <div style="margin-right:10px;margin-top:8px;">
                            <a @click="addStylist('STYLIST')" href="javascript:;">添加+</a>
                        </div>
                        <div class="form-group" v-for="stylist in stylists">
                            <label class="col-sm-2 control-label">{{stylist.name}}</label>

                            <div class="col-sm-5">
                                <input type="button" @click="removeStylist($index)"
                                       class="btn-sm-2 btn-danger" value="删除">
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
    </validator>
</div>

<!--选择设计师的弹窗 -->
<div id="addStylistModal" class="modal fade" tabindex="-1" data-width="600" style="top: 20%;">
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div class="row">
                <form id="stylistSearchForm" @submit.prevent="query">
                    <div class="col-md-4">
                        <div class="form-group">
                            <input
                                    v-model="form.keyword"
                                    type="text"
                                    placeholder="工号/姓名/手机号" class="form-control"/>
                        </div>
                    </div>
                    <div class="col-md-3 text-right">
                        <div class="form-group">
                            <button id="stylistSearchBtn" type="submit" class="btn btn-block btn-outline btn-default"
                                    alt="搜索"
                                    title="搜索">
                                <i class="fa fa-search"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <!-- <div class="columns columns-right btn-group pull-right"></div> -->
            <table id="stylistDataTable" width="100%" class="table table-striped table-bordered table-hover">
            </table>

            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn">关闭</button>
                <button type="button" @click="commitStylist" class="btn btn-primary">保存</button>
            </div>
        </div>
    </div>
</div>

<script src="${ctx}/static/js/containers/stylist/stylists.js?v=1.0"></script>