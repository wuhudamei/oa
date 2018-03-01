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
                            <shiro:hasPermission name="organization:add">
                                <a id="createBtn" @click="createBtnClickHandler"
                                   class="btn btn-outline btn-primary">新增组织</a>
                            </shiro:hasPermission>

                            <shiro:hasPermission name="organization:edit">
                                <a id="editBtn" @click="editBtn"
                                   class="btn btn-outline btn-primary">编辑</a>
                            </shiro:hasPermission>

                            <shiro:hasPermission name="organization:delete">
                                <a id="deleteBtn" @click="deleteBtn"
                                   class="btn btn-outline btn-danger">删除</a>
                            </shiro:hasPermission>

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
    <validator name="validation">
        <form name="createMirror" novalidate class="form-horizontal" role="form">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3>编辑组织机构</h3>
            </div>
            <div class="modal-body">
                <div class="form-group" v-if="!isEdit">
                    <label for="orgName" class="col-sm-2 control-label">组织机构性质</label>
                    <div class="col-sm-8">
                        <input value="{{org.type | org-property}}" type="text" class="form-control" disabled>
                    </div>
                </div>
                <div class="form-group" v-if="!isEdit && org.parentId != 0">
                    <label for="orgName" class="col-sm-2 control-label">上级节点</label>
                    <div class="col-sm-8">
                        <input v-model="org.parent.orgName" type="text" class="form-control" @click="chooseParent" readonly>
                    </div>
                </div>
                <div class="form-group" :class="{'has-error':$validation.name.invalid && submitBtnClick}">
                    <label for="orgName" class="col-sm-2 control-label">组织机构名称</label>
                    <div class="col-sm-8">
                        <input v-model="org.orgName"
                               v-validate:name="{
                                    required:{rule:true,message:'请输入组织机构名称'},
                                    maxlength:{rule:20,message:'组织机构名称最长不能超过20个字符'}
                                }"
                               maxlength="20"
                               data-tabindex="1"
                               id="orgName" name="orgName" type="text" class="form-control"
                               placeholder="组织机构名称">
                        <span v-cloak v-if="$validation.name.invalid && submitBtnClick" class="help-absolute">
                            <span v-for="error in $validation.name.errors">
                              {{error.message}} {{($index !== ($validation.name.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                    </div>
                </div>
                <div class="form-group" :class="{'has-error':$validation.orgcode.invalid && submitBtnClick}">
                    <label for="orgCode" class="col-sm-2 control-label">组织机构代码</label>
                    <div class="col-sm-8">
                        <input v-model="org.orgCode"
                               v-validate:orgcode="{
                                    required:{rule:true,message:'请输入组织机构代码'},
                                    maxlength:{rule:20,message:'组织机构名称最长不能超过20个字符'}
                                }"
                               maxlength="20"
                               data-tabindex="2"
                               onkeyup="value=value.replace(/[^\w\.\/]/ig,'')"
                               id="orgCode" name="orgCode" type="text" class="form-control"
                               placeholder="组织机构代码">
                        <span v-cloak v-if="$validation.orgcode.invalid && submitBtnClick" class="help-absolute">
                            <span v-for="error in $validation.orgcode.errors">
                              {{error.message}} {{($index !== ($validation.orgcode.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                    </div>
                </div>
                <div class="form-group" :class="{'has-error':$validation.sort.invalid && submitBtnClick}">
                    <label for="sort" class="col-sm-2 control-label">排序</label>
                    <div class="col-sm-8">
                        <input v-model="org.sort"
                               v-validate:sort="{
                                    required:{rule:true,message:'请输入数量'},
                                    min:0,
                                    max:99999999.99
                                }"
                               data-tabindex="4"
                               type="number"
                               maxlength="20"
                               id="sort" name="sort" type="text" class="form-control" placeholder="请输入排序值">
                        <span v-cloak v-if="$validation.sort.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.sort.errors">
                {{error.message}} {{($index !== ($validation.sort.errors.length -1)) ? ',':''}}
              </span>
            </span>
                    </div>
                </div>

                <div class="form-group" :class="{'has-error':$validation.type.invalid && submitBtnClick}">
                    <label for="type" class="col-sm-2 control-label">组织机构类型</label>
                    <div class="col-sm-3">
                        <select v-if="isEdit==true"
                        <%--v-validate:type="{required:true}"--%>
                                v-model="org.type"
                                id="type"
                                name="type"
                                data-tabindex="3"
                                class="form-control">
                            <option v-if="org.parentType=='BASE'" value="COMPANY">公司</option>
                            <option v-if="org.parentType=='COMPANY'||org.parentType=='BASE'||org.parentType=='DEPARTMENT'" value="DEPARTMENT">部门</option>
                            <option v-if="org.parentType=='DEPARTMENT'||org.parentType=='GROUP'" value="GROUP">组</option>
                        </select>

                        <%--<input v-if="org.type=='DEPARTMENT' && isEdit==false" value="部门" maxlength="20"--%>
                               <%--data-tabindex="1" type="text" readonly class="form-control">--%>
                        <%--<input v-if="org.type=='GROUP' && isEdit==false" value="组" maxlength="20"--%>
                               <%--data-tabindex="1" type="text" readonly class="form-control">--%>
                        <%--<input v-if="org.type=='COMPANY' && isEdit==false" value="公司" maxlength="20"--%>
                               <%--data-tabindex="1" type="text" readonly class="form-control">--%>
                        <%--<input v-if="org.type=='BASE' && isEdit==false" value="集团" maxlength="20"--%>
                               <%--data-tabindex="1" type="text" readonly class="form-control">--%>
                        <select v-else
                            <%--v-model="org.type"--%>
                            disabled
                            data-tabindex="3"
                            class="form-control">
                            <option v-if="org.type=='DEPARTMENT'" selected>部门</option>
                            <option v-if="org.type=='GROUP'" selected>组</option>
                            <option v-if="org.type=='COMPANY'" selected>公司</option>
                            <option v-if="org.type=='BASE'" selected>集团</option>
                        </select>

                    </div>
                </div>

                <div class="form-group" v-if=" org.type == 'COMPANY'">
                    <label for="storeFlag" class="col-sm-2 control-label">是否为门店</label>
                    <label class="col-sm-3 control-label" style="text-align: left;cursor: pointer;">
                        <input id="storeFlag" type="checkbox" v-model="org.storeFlag"/> &nbsp;&nbsp;是
                    </label>
                </div>

            </div>
            <div class="modal-footer">
                <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
                <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
            </div>
        </form>
    </validator>
</div>

<div id="modaltree" class="modal fade" tabindex="-1" data-width="760">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>选择上级机构</h3>
    </div>
    <div class="modal-body">
        <div id="treeModal"></div>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn">取消</button>
        <%--<button type="button" class="btn btn-primary">确定</button>--%>
    </div>
</div>

<script src="/static/vendor/iCheck/icheck.min.js"></script>

<script src="/static/vendor/sweetalert/sweetalert.min.js"></script>
<script src="/static/vendor/jstree/jstree.js"></script>
<script src="${ctx}/static/js/containers/organization/orglist.js"></script>
