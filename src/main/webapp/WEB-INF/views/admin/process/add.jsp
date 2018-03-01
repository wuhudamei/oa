<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>权限管理</title>
<link rel="stylesheet"
      href="/static/vendor/jstree/themes/default/style.css"/>
<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div class="ibox-content">
                <div class="row">
                    <form id="searchForm">
                        <div class="col-md-12 text-left">
                            <div class="form-group" id="buttons">
                                <button id="createBtn" type="button" :disabled="addDisabled"
                                        @click="createBtnClickHandler"
                                        class="btn btn-outline btn-primary">新增流程节点
                                </button>
                                <button id="editBtn" type="button" :disabled="edit"
                                        @click="editBtn" class="btn btn-outline btn-primary">编辑
                                </button>
                                <button id="deleteBtn" type="button" :disabled="deleted"
                                        @click="deleteBtn" class="btn btn-outline btn-danger">删除
                                </button>
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
    </div>
    <!-- ibox end -->
</div>
<!-- container end-->

<!-- 新建/编辑的modal-->
<div id="modal" class="modal fade" tabindex="-1" data-width="760">
    <validator name="validation">
        <form name="createMirror" novalidate class="form-horizontal"
              role="form">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h3>流程节点管理</h3>
            </div>
            <div class="modal-body">
                <div class="form-group" v-if="process.chooseP">
                    <label for="nodeTitle" class="col-sm-2 control-label">上级</label>
                    <div class="col-sm-8">
                        <input  type="text" class="form-control" placeholder="请选择上级节点" @click="changeP" readonly v-model="process.nextPName">
                    </div>
                </div>
                <div class="form-group"
                     :class="{'has-error':$validation.nodetitle.invalid && submitBtnClick}">
                    <label for="nodeTitle" class="col-sm-2 control-label">节点标题</label>
                    <div class="col-sm-8">
                        <input v-model="process.nodeTitle"
                               v-validate:nodetitle="{
                                    required:{rule:true,message:'请输入节点标题'}
                               }"
                               maxlength="20" data-tabindex="1" id="nodeTitle" name="nodeTitle"
                               type="text" class="form-control" placeholder="请输入节点标题"> <span
                            v-cloak v-if="$validation.nodetitle.invalid && submitBtnClick"
                            class="help-absolute"> <span
                            v-for="error in $validation.nodetitle.errors">
							{{error.message}} {{($index !==
							($validation.nodetitle.errors.length -1)) ? ',':''}} </span>
					</span>
                    </div>
                </div>

                <%--<div class="form-group"--%>
                     <%--:class="{'has-error':($validation.wftype.invalid && $validation.touched)}">--%>
                    <%--<label for="wfType" class="col-sm-2 control-label">流程类型</label>--%>
                    <%--<div class="col-sm-8">--%>
                        <%--<select v-validate:wftype="{required:true}" :disabled="wfTypeDisabled" v-model="process.wfType" id="wfType" name="wfType" class="form-control">--%>
                            <%--<option v-for="item in typeList" :value="item.value">{{item.name}}</option>--%>
                        <%--</select>--%>
                        <%--<div v-if="$validation.wftype.invalid && $validation.touched"--%>
                             <%--class="help-absolute">--%>
                            <%--<span v-if="$validation.wftype.invalid">请选择流程类型</span>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>

                <div v-if="isShow">
                    <div class="form-group"
                         :class="{'has-error':($validation.wfnature.invalid && $validation.touched)}">
                        <label for="wfNature" class="col-sm-2 control-label">流程性质</label>
                        <div class="col-sm-8">
                            <select v-validate:wfnature="{required:true}" :disabled="wfNatureDisabled"
                                    v-model="process.wfNature" id="wfNature" name="wfNature"
                                    class="form-control">
                                <option value="APPROVAL">审批</option>
                                <option value="EXECUTE">执行</option>
                            </select>
                            <div v-if="$validation.wfnature.invalid && $validation.touched"
                                 class="help-absolute">
                                <span v-if="$validation.wfnature.invalid">请选择流程性质</span>
                            </div>
                        </div>
                    </div>

                    <div class="form-group"
                         :class="{'has-error':($validation.approvaltype.invalid && $validation.touched)}">
                        <label for="approvalType" class="col-sm-2 control-label">审批类型</label>
                        <div class="col-sm-8">
                            <select v-validate:approvaltype="{required:true}"
                                    v-model="process.approvalType" id="approvalType"
                                    name="approvalType" class="form-control">
                                <option value="JOINTLY_SIGN">会签</option>
                                <option value="ORDINARY">一般</option>
                            </select>
                            <div v-if="$validation.approvaltype.invalid && $validation.touched"
                                 class="help-absolute">
                                <span v-if="$validation.approvaltype.invalid">请选择审批类型</span>
                            </div>
                        </div>
                    </div>

                    <div class="form-group"
                         :class="{'has-error':($validation.approver.invalid && $validation.touched)}">
                        <label for="approver" class="col-sm-2 control-label">审批人</label>
                        <div class="col-sm-8">
                            <input type="hidden" v-model="process.approver"/>
                            <input v-model="process.approverName"
                                   v-validate:approver="{
                                    required:{rule:true,message:'请选择审批人'}
                                  }"
                                   id="approver" type="text" placeholder="点击选择审批人" readonly=true
                                   @click="showApprover(1)" class="form-control"> </input>
                            <span v-cloak
                              v-if="$validation.approver.invalid && submitBtnClick"
                              class="help-absolute">
                                <span v-for="error in $validation.approver.errors">
                                    {{error.message}} {{($index !==
                                    ($validation.approver.errors.length -1)) ? ',':''}}
                                </span>
                            </span>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="applyOrg" class="col-sm-2 control-label">抄送人</label>
                        <div class="col-sm-8">
                            <input type="hidden" v-model="process.ccUser"/>
                            <input v-model="process.ccUserName"
                                   type="text" placeholder="点击选择抄送人" readonly=true
                                   @click="showApprover(0)" class="form-control"> </input>
                        </div>
                    </div>

                    <div class="form-group"
                         :class="{'has-error':($validation.approvalamount.invalid && $validation.touched)}">
                        <label for="approvalAmount" class="col-sm-2 control-label">审批金额</label>
                        <div class="col-sm-8">
                            <input v-model="process.approvalAmount"
                                   v-validate:approvalamount="{ required:true,min:0 }"
                                   id="approvalAmount" type="number" step="1" placeholder="请输入审批金额"
                                   class="form-control"> </input> <span v-cloak
                                                                        v-if="$validation.approvalamount.invalid && submitBtnClick"
                                                                        class="help-absolute"><span>审批金额必须大于等于0</span> </span>
                        </div>
                    </div>

                    <div class="form-group"
                         :class="{'has-error':($validation.approvaldaynum.invalid && $validation.touched)}">
                        <label for="approvalDayNum" class="col-sm-2 control-label">审批天数</label>
                        <div class="col-sm-8">
                            <input v-model="process.approvalDayNum"
                                   v-validate:approvaldaynum="{ required:true,min:0}"
                                   id="approvalDayNum" type="number" placeholder="请输入审批天数"
                                   class="form-control"> </input> <span v-cloak
                                                                        v-if="$validation.approvaldaynum.invalid && submitBtnClick"
                                                                        class="help-absolute"> <span>审批天数必须大于等于0</span>
					</span>
                        </div>
                    </div>

                    <%--<div class="form-group"--%>
                         <%--:class="{'has-error':($validation.applyorg.invalid && $validation.touched)}">--%>
                    <div class="form-group">
                        <label for="applyOrg" class="col-sm-2 control-label">申请部门</label>
                        <div class="col-sm-8">
                            <input type="hidden" v-model="process.applyOrg"/>

                            <input v-model="process.applyOrgName"
                                   id="applyOrg" type="text" placeholder="点击选择部门" readonly=true
                                   @click="showApplyOrg" class="form-control"> </input>
                            <%--<input v-model="process.applyOrgName"--%>
                                   <%--v-validate:applyorg="{--%>
                                    <%--required:{rule:true,message:'请选择申请部门'}--%>
                                  <%--}"--%>
                                   <%--id="applyOrg" type="text" placeholder="点击选择部门" readonly=true--%>
                                   <%--@click="showApplyOrg" class="form-control"> </input> --%>
                            <%--<span v-cloak--%>
                                                                                              <%--v-if="$validation.applyorg.invalid && submitBtnClick"--%>
                                                                                              <%--class="help-absolute"> <span--%>
                                <%--v-for="error in $validation.applyorg.errors">--%>
							<%--{{error.message}} {{($index !==--%>
							<%--($validation.applyorg.errors.length -1)) ? ',':''}} </span>--%>
					<%--</span>--%>
                        </div>
                    </div>

                </div>
                <div class="form-group"
                     :class="{'has-error':$validation.seq.invalid && submitBtnClick}">
                    <label for="seq" class="col-sm-2 control-label">排序值</label>
                    <div class="col-sm-8">
                        <input v-model="process.seq"
                               v-validate:seq="{
                                    required:{rule:true,message:'请输入排序值'}
                                  }"
                               id="seq" type="number" maxlength="255" placeholder="请输入排序值"
                               class="form-control"> </input> <span v-cloak
                                                                    v-if="$validation.seq.invalid && submitBtnClick"
                                                                    class="help-absolute"> <span
                            v-for="error in $validation.seq.errors"> {{error.message}}
							{{($index !== ($validation.seq.errors.length -1)) ? ',':''}} </span>
					</span>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button :disabled="disabled" type="button" data-dismiss="modal"
                        class="btn">取消
                </button>
                <button @click="submit" :disabled="disabled" type="button"
                        class="btn btn-primary">提交
                </button>
            </div>
        </form>
    </validator>
</div>

<!-- 审批人列表窗口 -->
<div id="approverListModel" class="modal fade" tabindex="-1"
     data-width="800" style="top: 30%">
    <!-- ibox start -->
    <div class="ibox-content">
        <div class="row">
            <form id="searchForm" action='#'>
                <div class="col-md-4">
                    <div class="form-group">
                        <input v-model="form.keyword" type="text" placeholder="工号/姓名"
                               class="form-control"/>
                    </div>
                </div>
                <div class="col-md-3 text-right">
                    <div class="form-group">
                        <button id="searchBtn" type="button" @click.prevent.stop="query"
                                class="btn btn-block btn-outline btn-default" alt="搜索" title="搜索">
                            <i class="fa fa-search"></i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
        <!-- <div class="columns columns-right btn-group pull-right"></div> -->
        <table id="dataTable" width="100%"
               class="table table-striped table-bordered table-hover">
        </table>
        <div class="modal-footer">
            <button type="button" data-dismiss="modal" class="btn">关闭</button>
            <button type="button" @click="commitUsers" class="btn btn-primary">确定</button>
        </div>
    </div>
</div>

<!--选择上级-->
<div id="parentModalChoose" class="modal fade" tabindex="-1" data-width="760">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>选择上级机构</h3>
    </div>
    <div class="modal-body">
        <div id="chooseParent"></div>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn">取消</button>
        <button type="button" class="btn btn-primary" @click="ok">确定</button>
    </div>
</div>

<!-- 申请机构 -->
<jsp:include page="/WEB-INF/views/admin/organization/chooseOrg.jsp"></jsp:include>

<script src="/static/vendor/iCheck/icheck.min.js"></script>
<script src="/static/vendor/sweetalert/sweetalert.min.js"></script>
<script src="/static/vendor/jstree/jstree.js"></script>
<script src="${ctx}/static/js/containers/process/add.js?v=<%=Math.random()%>"></script>
