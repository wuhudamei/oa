<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>公告修改</title>
<script type="text/javascript" charset="utf-8" src="/static/vendor/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/static/vendor/ueditor/ueditor.all.js"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="/static/vendor/ueditor/lang/zh-cn/zh-cn.js"></script>
<link href="${ctx}/static/css/zTreeStyle.css" rel="stylesheet">
<div id="container" class="wrapper wrapper-content">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox-content">
        <validator name="validation">
            <form name="createMirror" id="createMirror" novalidate class="form-horizontal" role="form">
                <div>
                    <div class="form-group"
                         :class="{'has-error':$validation.title.invalid && submitBtnClick}">
                        <label class="col-sm-2 control-label">公告标题</label>
                        <div class="col-sm-8">
                            <input v-model="notice.title"
                                   v-validate:title="{
                                            required:{rule:title,message:'请输入公告标题'}
                                        }"
                                   data-tabindex="1"
                                   id="title" name="title" class="form-control"
                                   placeholder="标题">
                            <span v-cloak v-if="$validation.title.invalid && submitBtnClick"
                                  class="help-absolute">
                            <span v-for="error in $validation.title.errors">
                              {{error.message}} {{($index !== ($validation.title.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                        </div>
                    </div>

                    <div class="form-group"
                         :class="{'has-error':$validation.noticeStatus.invalid && submitBtnClick}">
                        <label class="col-sm-2 control-label">公告状态</label>
                        <div class="col-sm-8">
                            <select v-model="notice.noticeStatus"
                                    id="noticeStatus"
                                    name="noticeStatus"
                                    class="form-control"
                                    v-validate:noticeStatus="{
                                            required:{rule:noticeStatus,message:'请选择公告状态'}
                                        }">
                                <option value="1">普通</option>
                                <option value="2">重要</option>
                                <option value="3">紧急</option>

                            </select>

                            <span v-cloak v-if="$validation.noticeStatus.invalid && submitBtnClick"
                                  class="help-absolute">
                            <span v-for="error in $validation.noticeStatus.errors">
                              {{error.message}} {{($index !== ($validation.noticeStatus.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                        </div>
                    </div>

                    <div class="form-group"
                         :class="{'has-error':$validation.orgfamilycode.invalid && submitBtnClick}">
                        <label class="col-sm-2 control-label">组织架构</label>
                        <div class="col-sm-7"
                             :class="{'has-error':$validation.orgfamilycode.invalid && submitBtnClick}">
                            <input v-model="orgName"
                                   readonly
                                   name="orgFamilyCode"
                                   type="text"
                                   class="form-control"/>
                            <input v-model="notice.orgId"
                                   v-validate:orgfamilycode="{
                                      required:{rule:orgfamilycode,message:'请选择所属机构'}
                                   }"
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

                    <div class="form-group" :class="{'has-error':$validation.content.invalid && submitBtnClick}">
                        <label class="col-sm-2 control-label">公告内容</label>
                        <div class="col-sm-8">
                            <div v-el:ueditor></div>

                        </div>
                    </div>


                </div>
                <div class="text-center">
                    <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">保存</button>
                    <button @click="cancel" type="button" class="btn btn-default">返回</button>
                </div>
            </form>
        </validator>
        <!-- ibox end -->
    </div>
</div>
<!-- container end-->
<%@include file="/WEB-INF/views/admin/components/ztree.jsp" %>
<script src="${ctx}/static/js/components/ztree.js"></script>
<script src="${ctx}/static/js/jquery.ztree.core.js"></script>
<script src="${ctx}/static/js/jquery.ztree.excheck.js"></script>
<script src="${ctx}/static/js/containers/noticeboard/noticeboardedit.js"></script>