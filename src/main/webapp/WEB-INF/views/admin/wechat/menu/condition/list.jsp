<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>个性化菜单管理</title>
<div id="container" class="wrapper wrapper-content animated fadeInRight">
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
              <label class="sr-only" for="keyword">名称</label>
              <input
                v-model="form.keyword"
                id="keyword"
                name="keyword"
                type="text"
                placeholder="名称" class="form-control"/>
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
          <div class="col-md-9 text-right">
            <div class="form-group">
              <button @click="create" type="button"
                      class="btn btn-outline btn-primary">添加
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

<div id="createModal" class="modal fade" tabindex="-1" data-width="760">
  <validator name="validation">
    <form name="createForm" novalidate class="form-horizontal" role="form">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>新建标签</h3>
      </div>
      <div class="modal-body">
        <div class="form-group" :class="{'has-error':$validation.name.invalid && submitBtnClick}">
          <label for="name" class="col-sm-2 control-label">菜单名称</label>
          <div class="col-sm-8">
            <input v-model="menu.name"
                   v-validate:name="{
                     required:{rule:true,message:'请输入个性化菜单名称'}
                   }"
                   maxlength="20"
                   id="name" name="name" type="text" class="form-control" placeholder="请输入个性化菜单名称">
            <span v-cloak v-if="$validation.name.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.name.errors">
                {{error.message}} {{($index !== ($validation.name.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

        <div class="form-group" :class="{'has-error':$validation.description.invalid && submitBtnClick}">
          <label for="description" class="col-sm-2 control-label">菜单描述</label>
          <div class="col-sm-8">
            <textarea v-model="menu.description"
                      v-validate:description="{
                        required:{rule:true,message:'请输入个性化菜单描述'}
                      }"
                      maxlength="255"
                      id="description" name="description"
                      class="form-control" placeholder="请输入个性化菜单描述"></textarea>
            <span v-cloak v-if="$validation.description.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.description.errors">
                {{error.message}} {{($index !== ($validation.description.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

        <div class="form-group" :class="{'has-error':$validation.tag.invalid && submitBtnClick}">
          <label class="col-sm-2 control-label">匹配标签</label>
          <div class="col-sm-8">
            <select v-model="menu.tag.id"
                    v-validate:tag="{
                      required:{rule:true,message:'请输入个性化菜单描述'}
                    }"
                    class="form-control">
              <option value="">请选择匹配标签</option>
              <option v-for="tag in tags" value="{{tag.id}}">{{tag.name}}</option>
            </select>
            <span v-cloak v-if="$validation.tag.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.tag.errors">
                {{error.message}} {{($index !== ($validation.tag.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

      </div>
      <div class="modal-footer">
        <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
        <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">保存</button>
      </div>
    </form>
  </validator>
</div>

<script src="${ctx}/static/js/wx/menu/condition/list.js"></script>