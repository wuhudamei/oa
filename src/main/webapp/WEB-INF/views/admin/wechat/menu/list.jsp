<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>自定义菜单管理</title>
<link rel="stylesheet" href="${ctx}/static/vendor/jstree/themes/default/style.css"/>
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
                 class="btn btn-outline btn-primary">创建菜单</a>

              <a id="editBtn" @click="editBtn"
                 class="btn btn-outline btn-primary">编辑</a>

              <a id="deleteBtn" @click="deleteBtn"
                 class="btn btn-outline btn-danger">删除</a>
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
        <h3>新增菜单</h3>
      </div>
      <div class="modal-body">
        <div class="form-group" :class="{'has-error':$validation.name.invalid && submitBtnClick}">
          <label for="name" class="col-sm-2 control-label">菜单名称</label>
          <div class="col-sm-8">
            <input v-model="menu.name"
                   v-validate:name="{
                      required:{rule:true,message:'请输入菜单名称'},
                      maxlength:{rule:20,message:'菜单名称不能超过20个字符'}
                   }"
                   maxlength="20"
                   data-tabindex="1"
                   id="name" name="name" type="text" class="form-control"
                   placeholder="请输入菜单名称">
            <span v-cloak v-if="$validation.name.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.name.errors">
                {{error.message}} {{($index !== ($validation.name.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

        <div class="form-group" :class="{'has-error':$validation.type.invalid && submitBtnClick}">
          <label for="type" class="col-sm-2 control-label">菜单类型</label>
          <div class="col-sm-8">
            <select v-model="menu.type"
                    v-validate:type="{
                      required:{rule:true,message:'请选择菜单类型'}
                    }"
                    id="type" name="type" class="form-control">
              <option value="VIEW">跳转URL</option>
            </select>
            <span v-cloak v-if="$validation.type.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.type.errors">
                {{error.message}} {{($index !== ($validation.type.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

        <div v-if="type.click" class="form-group" :class="{'has-error':$validation.url.invalid && submitBtnClick}">
          <label for="url" class="col-sm-2 control-label">跳转链接</label>
          <div class="col-sm-8">
            <textarea v-model="menu.url"
                      v-validate:url="{
                      required:{rule:true,message:'请输入跳转链接'},
                      maxlength:{rule:500,message:'跳转链接不能超过500个字符'}
                      }"
                      data-tabindex="1"
                      id="url" name="url" type="text" class="form-control"
                      placeholder="请输入跳转链接"></textarea>
            <span v-cloak v-if="$validation.name.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.name.errors">
                {{error.message}} {{($index !== ($validation.name.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

        <div class="form-group" :class="{'has-error':$validation.sort.invalid && submitBtnClick}">
          <label for="sort" class="col-sm-2 control-label">排序值</label>
          <div class="col-sm-8">
            <input v-model="menu.sort"
                   v-validate:sort="{
                      required:{rule:true,message:'请输入排序值'},
                      digit:{rule:true,message:'排序值请输入数字'}
                   }"
                   maxlength="20"
                   data-tabindex="1"
                   id="sort" name="sort" type="text" class="form-control"
                   placeholder="请输入排序值">
            <span v-cloak v-if="$validation.sort.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.sort.errors">
                {{error.message}} {{($index !== ($validation.sort.errors.length -1)) ? ',':''}}
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

<script src="${ctx}/static/vendor/iCheck/icheck.min.js"></script>
<script src="${ctx}/static/vendor/jstree/jstree.js"></script>
<script src="${ctx}/static/js/wx/menu/list.js"></script>
