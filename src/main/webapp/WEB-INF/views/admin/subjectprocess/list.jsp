<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<link rel="stylesheet"
      href="/static/vendor/jstree/themes/default/style.css"/>
<title>科目流程管理</title>
<div id="container" class="wrapper wrapper-content animated fadeInRight">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div class="row">
                <form id="searchForm" @submit.prevent="query">
                    <div class="col-md-4">
                        <div class="form-group">
                            <label class="sr-only" for="keyword">流程名称</label>
                            <input
                                    v-model="form.keyword"
                                    id="keyword"
                                    name="keyword"
                                    type="text"
                                    placeholder="流程名称" class="form-control"/>
                        </div>
                    </div>

                    <div class="col-md-1">
                        <div class="form-group">
                            <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default" alt="搜索"
                                    title="搜索">
                                <i class="fa fa-search"></i>
                            </button>
                        </div>
                    </div>
                    <div class="col-md-2" v-if="hasPermissionAdd">
                        <div class="form-group">
                            <button class="btn btn-block btn-outline btn-default" type="button" @click="add"
                                    title="新建">
                                新建
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <table id="dataTable" width="100%" class="table table-striped table-bordered table-hover">
            </table>
        </div>
    </div>
    <!-- ibox end -->
</div>

<div id="treemodal" class="modal fade" tabindex="-1" data-width="500">
    <form name="createMirror" novalidate class="form-horizontal" role="form">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3>选择机构</h3>
        </div>
        <div class="modal-body">
            <div class="form-group">
                <div class="col-sm-1"></div>
                <div class="col-sm-11">
                    <div id="tree"></div>
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <button @click="cancel" type="button" data-dismiss="modal" class="btn">取消</button>
            <button @click="ok" type="button" class="btn btn-primary">确定</button>
        </div>
    </form>
</div>

<div id="addprocess" class="modal fade" tabindex="-1" data-width="760">
    <form name="createMirror" novalidate class="form-horizontal" role="form">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3>流程</h3>
        </div>
        <validator name="validation">
        <div class="modal-body">
            <div class="form-group" :class="{'has-error':$validation.wfid.invalid && submitBtnClick}">
                <label for="nodeTitle" class="col-sm-2 control-label">流程名称</label>
                <div class="col-sm-8">
                    <input  type="text" v-validate:wfid="{
                                    required:{rule:true,message:'请选择流程'}
                               }" class="form-control" placeholder="请选择流程" @click="changeWf" readonly v-model="wfName">
                    <span v-cloak v-if="$validation.wfid.invalid && submitBtnClick"
                          class="help-absolute">
                        <span v-for="error in $validation.wfid.errors">
							{{error.message}} {{($index !==
							($validation.wfid.errors.length -1)) ? ',':''}}
                        </span>
					</span>
                </div>
            </div>
            <div class="form-group"
                 :class="{'has-error':$validation.processtypeid.invalid && submitBtnClick}">
                <label class="col-sm-2 control-label">流程类型</label>
                <div class="col-sm-8">
                    <select class="form-control" v-validate:processtypeid="{
                                    required:{rule:true,message:'请选择流程类型'}
                               }" v-model="obj.processTypeId" @change="proTypeChange">
                        <option value="">请选择</option>
                        <option value="3">行政类</option>
                        <option value="2">营销类</option>
                        <option value="4">财务类</option>
                        <option value="1">人事类</option>
                        <option value="5">客管类</option>
                        <option value="6">其他类</option>
                    </select>

                    <span v-cloak v-if="$validation.processtypeid.invalid && submitBtnClick"
                        class="help-absolute">
                        <span v-for="error in $validation.processtypeid.errors">
							{{error.message}} {{($index !==
							($validation.processtypeid.errors.length -1)) ? ',':''}}
                        </span>
					</span>
                </div>
            </div>
            <div class="form-group"
                 :class="{'has-error':$validation.subjectid.invalid && submitBtnClick}">
                <label for="nodeTitle" class="col-sm-2 control-label">费用科目</label>
                <div class="col-sm-8">
                    <select class="form-control" v-validate:subjectid="{
                                    required:{rule:true,message:'请选择费用科目'}
                               }" v-model="obj.subjectId">
                        <option value="">请选择</option>
                        <option :value="item.id" v-for="item in subList">{{item.name}}</option>
                    </select>

                    <span v-cloak v-if="$validation.subjectid.invalid && submitBtnClick"
                        class="help-absolute">
                        <span v-for="error in $validation.subjectid.errors">
							{{error.message}} {{($index !==
							($validation.subjectid.errors.length -1)) ? ',':''}}
                        </span>
					</span>
                </div>
            </div>
        </div>
        </validator>
        <div class="modal-footer">
            <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
            <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
        </div>
    </form>
</div>

<script type="text/javascript">
    // 等前登录员工的机构信息
    var orgName = '<shiro:principal property="orgName"/>';
    var orgFamilyCode = '<shiro:principal property="orgFamilyCode"/>';
</script>
<script src="/static/vendor/jstree/jstree.js"></script>
<script src="${ctx}/static/js/containers/subjectprocess/list.js"></script>