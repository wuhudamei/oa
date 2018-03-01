<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>标签列表</title>
<link href="${ctx}/static/css/zTreeStyle.css" rel="stylesheet">
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
                placeholder="标签名称" class="form-control"/>
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
          <label for="name" class="col-sm-2 control-label">标签名称</label>
          <div class="col-sm-8">
            <input v-model="tag.oid" type="hidden"/>
            <input v-model="tag.name"
                   v-validate:name="{
                     required:{rule:true,message:'请输入标签名称'}
                   }"
                   maxlength="30"
                   id="name" name="name" type="text" class="form-control" placeholder="请输入标签名称">
            <span v-cloak v-if="$validation.name.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.name.errors">
                {{error.message}} {{($index !== ($validation.name.errors.length -1)) ? ',':''}}
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

<!-- 选择用户打标签页面 -->
<div id="tagModal" class="modal fade" tabindex="-1" data-width="760">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3>选择员工</h3>
  </div>
  <div class="modal-body">
    <div class="row">
      <form>
        <div class="col-md-3">
          <div class="form-group">
            <input
              v-model="form.keyword"
              type="text"
              placeholder="工号/姓名/手机号" class="form-control"/>
          </div>
        </div>
        <div class="col-md-3">
          <div class="form-group" v-clickoutside="clickOut">
            <label class="sr-only"></label>
            <input v-model="form.orgName"
                   @click="showOrgTree=true"
                   readonly
                   type="text"
                   placeholder="选择机构" class="form-control"/>
            <!--:show-tree.sync 双向绑定是否显示树属性 一般开始的时候的 false 如果不需要切换隐藏/显示的时候是true-->
            <!--@on-change 绑定节点有checkbox的时候checkbox变化的事件，传入参数为该节点-->
            <!--@on-click 绑定节点被点击事件，传入参数为该节点-->
            <z-tree v-ref:nodes-select
                    :nodes="orgData"
                    :show-tree.sync="showOrgTree"
                    @on-click="selectOrg"
                    :show-checkbox="false">
            </z-tree>
          </div>
        </div>
        <div class="col-md-2 text-right">
          <div class="form-group">
            <button type="button" class="btn btn-block btn-outline btn-default" alt="搜索" title="搜索" @click="query">
              <i class="fa fa-search"></i>
            </button>
          </div>
        </div>
      </form>
    </div>
    <table id="employeeDataTable" width="100%" class="table table-striped table-bordered table-hover">
    </table>
  </div>
  <div class="modal-footer">
    <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
    <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">保存</button>
  </div>
</div>

<!-- 详情页面 -->
<div id="detailModal" class="modal fade" tabindex="-1" data-width="760">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 v-text="tag.name"></h3>
  </div>
  <div class="modal-body">
    <div class="row">
      <form>
        <div class="col-md-3">
          <div class="form-group">
            <input
              v-model="form.keyword"
              type="text"
              placeholder="工号/姓名/手机号" class="form-control"/>
          </div>
        </div>
        <div class="col-md-3">
          <div class="form-group" v-clickoutside="clickOut">
            <label class="sr-only"></label>
            <input v-model="form.orgName"
                   @click="showOrgTree=true"
                   readonly
                   type="text"
                   placeholder="选择机构" class="form-control"/>
            <!--:show-tree.sync 双向绑定是否显示树属性 一般开始的时候的 false 如果不需要切换隐藏/显示的时候是true-->
            <!--@on-change 绑定节点有checkbox的时候checkbox变化的事件，传入参数为该节点-->
            <!--@on-click 绑定节点被点击事件，传入参数为该节点-->
            <z-tree v-ref:nodes-select
                    :nodes="orgData"
                    :show-tree.sync="showOrgTree"
                    @on-click="selectOrg"
                    :show-checkbox="false">
            </z-tree>
          </div>
        </div>
        <div class="col-md-2 text-right">
          <div class="form-group">
            <button type="button" class="btn btn-block btn-outline btn-default" alt="搜索" title="搜索" @click="query">
              <i class="fa fa-search"></i>
            </button>
          </div>
        </div>
      </form>
    </div>
    <table id="detailDataTable" width="100%" class="table table-striped table-bordered table-hover">
    </table>
  </div>
  <div class="modal-footer">
    <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
    <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">保存</button>
  </div>
</div>

<%@include file="/WEB-INF/views/admin/components/ztree.jsp" %>
<script src="${ctx}/static/js/components/ztree.js"></script>
<script src="${ctx}/static/js/jquery.ztree.core.js"></script>
<script src="${ctx}/static/js/jquery.ztree.excheck.js"></script>
<script src="${ctx}/static/js/wx/tag/list.js"></script>