<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<link href="${ctx}/static/css/zTreeStyle.css" rel="stylesheet">
<title>员工管理</title>
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
              <label class="sr-only" for="keyword">工号/姓名/手机号</label>
              <input
                v-model="form.keyword"
                id="keyword"
                name="keyword"
                type="text"
                placeholder="工号/姓名/手机号" class="form-control"/>
            </div>
          </div>
          <div class="col-md-2">
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
          <div class="col-md-2">
            <div class="form-group">
              <select v-model="form.employeeStatus"
                      placeholder="员工状态"
                      class="form-control">
                <option value="">员工状态</option>
                <option value="ON_JOB">在职</option>
                <option value="DIMISSION">离职</option>
              </select>
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
        </form>
      </div>
      <table id="dataTable" width="100%" class="table table-striped table-bordered table-hover">
      </table>
    </div>
  </div>
  <!-- ibox end -->
</div>

<div id="modal" class="modal fade" tabindex="-1" data-width="500">
  <form name="createMirror" novalidate class="form-horizontal" role="form">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
      <h3>选择机构</h3>
    </div>
    <div class="modal-body">
      <div class="form-group">
        <div class="col-sm-1"></div>
        <div class="col-sm-11">
          <z-tree v-ref:nodes-checkbox
                  :nodes="orgData"
                  :show-tree.sync="showOrgTree"
                  @on-change="changeOrg"
                  :show-checkbox="true">
          </z-tree>
        </div>
      </div>
    </div>
    <div class="modal-footer">
      <button @click="cancel" type="button" data-dismiss="modal" class="btn">取消</button>
      <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">保存</button>
    </div>
  </form>
</div>

<div id="rolesModal" class="modal fade" tabindex="-1" data-width="760">
  <form name="createMirror" novalidate class="form-horizontal" role="form">
    <div class="modal-header">
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
      <h3>设置用户科目角色</h3>
    </div>
    <div class="modal-body">
      <div class="form-group">
        <div class="row" style="margin-left: 40px">
          <div v-for="role in roles">
            <div class="col-md-4 col-sm-4 col-xs-6  form-group role-item ellips">
              <label :for="role.id" +$index title="{{role.description}}">
                <input type="checkbox" :checked="role.checked"
                       id=role"+$index
                       @click="checkSub(role,$event)"
                       data-checkbox="sub"
                       data-rolename="role.name" data-rolevalue="role.id">
                {{role.name}}</label>
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
</div>

<script type="text/javascript">
  // 等前登录员工的机构信息
  var orgName = '<shiro:principal property="orgName"/>';
  var orgFamilyCode = '<shiro:principal property="orgFamilyCode"/>';
</script>
<%@include file="/WEB-INF/views/admin/components/ztree.jsp" %>
<script src="${ctx}/static/js/components/ztree.js"></script>
<script src="${ctx}/static/js/jquery.ztree.core.js"></script>
<script src="${ctx}/static/js/jquery.ztree.excheck.js"></script>
<script src="${ctx}/static/js/containers/employee/management.js"></script>