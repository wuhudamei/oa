<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>规章制度</title>
<head>
    <link rel="stylesheet" href="/static/vendor/webuploader/webuploader.css">
</head>

<style type="text/css">
    div {
        width: 100%;
    }
</style>
<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox" v-cloak style="padding-right: 60px;">
        <div class="ibox-content">
            <validator name="validation">
                <h2 class="text-center">{{title}}</h2>
                <hr/>
                <form name="createRegulation" novalidate class="form-horizontal" role="form">

                    <div class="form-group" :class="{'has-error':$validation.type.invalid && submitBtnClick}">
                        <label for="type" class="col-sm-2 control-label">规章制度类型:</label>
                        <div class="col-sm-4">
                            <select v-model="regulation.type"
                                    id="type"
                                    v-validate:type="{required:{rule:true,message:'请选择规章制度类型'}}"
                                    name="budgetDescribe" class="form-control"
                                    placeholder="规章制度类型">
                                <option value="">请选择规章制度类型</option>
                                <option v-for="type of types" :value="type.id">{{type.name}}</option>
                            </select>
                            <span v-cloak v-if="$validation.type.invalid && submitBtnClick"
                                  class="help-absolute">
                            <span v-for="error in $validation.type.errors">
                                {{error.message}} {{($index !== ($validation.type.errors.length -1)) ? ',':''}}
                            </span>
                            </span>
                        </div>
                    </div>

                    <div class="form-group" :class="{'has-error':$validation.title.invalid && submitBtnClick}">
                        <label for="title" class="col-sm-2 control-label">标题:</label>
                        <div class="col-sm-8">
                            <input v-model="regulation.title"
                                   id="title"
                                   v-validate:title="{required:{rule:true,message:'请输入标题'}}"
                                   maxlength="200"
                                   data-tabindex="1"
                                   name="title" class="form-control"
                                   placeholder="标题"/>
                            <span v-cloak v-if="$validation.title.invalid && submitBtnClick"
                                  class="help-absolute">
                            <span v-for="error in $validation.title.errors">
                                {{error.message}} {{($index !== ($validation.title.errors.length -1)) ? ',':''}}
                            </span>
                            </span>
                        </div>
                    </div>


                    <div class="form-group" :class="{'has-error':$validation.sort.invalid && submitBtnClick}">
                        <label for="sort" class="col-sm-2 control-label">排序字段:</label>
                        <div class="col-sm-8">
                            <input v-model="regulation.sort"
                                   id="sort"
                                   v-validate:sort="{required:{rule:true,message:'请输入排序值'}}"
                                   maxlength="11"
                                   type="number"
                                   data-tabindex="1"
                                   name="sort" class="form-control datetime input-sm"
                                   placeholder="排序字段"/>
                            <span v-cloak v-if="$validation.sort.invalid && submitBtnClick"
                                  class="help-absolute">
                            <span v-for="error in $validation.sort.errors">
                                {{error.message}} {{($index !== ($validation.sort.errors.length -1)) ? ',':''}}
                            </span>
                            </span>
                        </div>
                    </div>

                    <div class="form-group"
                         :class="{'has-error':$validation.orgfamilycode.invalid && submitBtnClick}">

                        <label class="col-sm-2 control-label">组织架构</label>
                        <div class="col-sm-7"
                             :class="{'has-error':$validation.orgfamilycode.invalid && submitBtnClick}">
                            <input v-model="regulation.orgFamilyCode"
                                   readonly
                                   id="orgFamilyCode"
                                   name="orgFamilyCode"
                                   type="text"
                                   v-validate:orgfamilycode="{
                                      required:{rule:orgfamilycode,message:'请选择所属机构'}
                                   }"
                                   class="form-control"/>
                            <input v-model="regulation.orgId"
                                   name="orgId"
                                   type="hidden"/>
                            <span v-cloak v-if="$validation.orgfamilycode.invalid && submitBtnClick"
                                  class="help-absolute">
                                <span v-for="error in $validation.orgfamilycode.errors">
                                    {{error.message}} {{($index !== ($validation.orgfamilycode.errors.length -1)) ? ',':''}}
                                </span>
                            </span>
                        </div>

                        <label class="col-md-1 control-label" style="text-align: left;padding:0px 10px"
                               v-clickoutside="clickOut">
                            <button type="button" class="btn btn-primary btn-outline" @click="showOrgTree=true">选择机构
                            </button>
                            <z-tree v-ref:nodes-checkbox
                                    :nodes="orgData"
                                    :show-tree.sync="showOrgTree"
                                    @on-change="selectOrg"
                                    :show-checkbox="true">
                            </z-tree>
                        </label>
                    </div>



                    <div class="form-group" :class="{'has-error':$validation.fileName.invalid && submitBtnClick}">
                        <label for="fileName" class="col-sm-2 control-label">附件</label>
                        <div class="col-sm-8">
                            <input type="hidden" v-model="regulation.fileUrl">
                            <input v-model="regulation.fileName"
                                   v-validate:file-name="{
                                     maxlength:{rule:100,message:'附件名称最长不能超过100个字符'}
                                   }"
                                   readonly
                                   maxlength="100"
                                   data-tabindex="12"
                                   id="fileName" name="fileName" type="text" class="form-control" placeholder="附件名称">
                            <span v-cloak v-if="$validation.fileName.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.fileName.errors">
                {{error.message}} {{($index !== ($validation.fileName.errors.length -1)) ? ',':''}}
              </span>
            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="" class="col-sm-2 control-label"></label>
                        <div class="col-sm-8">
                            <div v-if="!is_weixin" class="col-sm-4" style="position:relative;">
                                <web-uploader
                                        :type="webUploaderSub.type"
                                        :w-server="webUploaderSub.server"
                                        :w-accept="webUploaderSub.accept"
                                        :w-file-size-limit="webUploaderSub.fileSizeLimit"
                                        :w-file-single-size-limit="webUploaderSub.fileSingleSizeLimit"
                                        :w-form-data="{category:'CONTRACT'}">
                                    <button type="button" class="btn btn-sm btn-primary">上传附件</button>
                                </web-uploader>
                            </div>
                            <div class="col-sm-4">
                                <button @click="deleteFlie()"
                                        v-if="regulation.fileName != '' && regulation.fileName != null" type="button"
                                        class="btn btn-sm btn-danger">
                                    删除附件
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="form-group" :class="{'has-error':$validation.content.invalid && submitBtnClick}">
                        <label for="content" class="col-sm-2 control-label">内容:</label>
                        <div class="col-sm-8">
                            <div v-el:ueditor></div>
                            <%--<textarea v-el:ueditor id="content" type="text/plain" style="width:750px;height:500px;" class="col-sm-8"></textarea>--%>
                            <%--<textarea v-el:ueditor--%>
                            <%--id="content"--%>
                            <%--v-validate:content="{required:{rule:true,message:'请输入内容'}}"--%>
                            <%--maxlength="2000"--%>
                            <%--data-tabindex="1"--%>
                            <%--name="content" class="form-control datetime input-sm"--%>
                            <%--placeholder="内容"></textarea>--%>
                            <%--<span v-cloak v-if="$validation.content.invalid && submitBtnClick"--%>
                            <%--class="help-absolute">--%>
                            <%--<span v-for="error in $validation.content.errors">--%>
                            <%--{{error.message}} {{($index !== ($validation.content.errors.length -1)) ? ',':''}}--%>
                            <%--</span>--%>
                            <%--</span>--%>
                        </div>
                    </div>

                    <div class="text-center">
                        <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <button @click="cancel" type="button" data-dismiss="modal" class="btn">返回</button>
                    </div>
                </form>
            </validator>

        </div>
    </div>
    <!-- ibox end -->
</div>
<script src="${ctx}/static/vendor/webuploader/webuploader.js"></script>
<script src="${ctx}/static/js/components/webuploader.js"></script>

<%@include file="/WEB-INF/views/admin/components/ztree.jsp" %>
<script src="${ctx}/static/js/components/ztree.js"></script>
<script src="${ctx}/static/js/jquery.ztree.core.js"></script>
<script src="${ctx}/static/js/jquery.ztree.excheck.js"></script>
<!-- container end-->
<script type="text/javascript" charset="utf-8" src="/static/vendor/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/static/vendor/ueditor/ueditor.all.js"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="/static/vendor/ueditor/lang/zh-cn/zh-cn.js"></script>

<script src="${ctx}/static/js/containers/regulation/create.js?v=1.0"></script>
<link href="${ctx}/static/css/zTreeStyle.css" rel="stylesheet">