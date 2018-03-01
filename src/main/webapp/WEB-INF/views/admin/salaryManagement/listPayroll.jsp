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
              <label class="sr-only" for="keyword">工号/姓名</label>
              <input
                v-model="form.keyword"
                id="keyword"
                name="keyword"
                type="text"
                placeholder="工号/姓名" class="form-control"/>
            </div>
          </div>
          <div class="col-md-2">
            <div class="form-group" v-clickoutside="clickOut">
              <label class="sr-only"></label>
              <input v-model="form.orgName"
                     @click="showOrgTree=true"
                     readonly
                     type="text"
                     placeholder="安徽智装研究院" class="form-control"/>
              <!--:show-tree.sync 双向绑定是否显示树属性 一般开始的时候的 false 如果不需要切换隐藏/显示的时候是true-->
              <!--@on-change 绑定节点有checkbox的时候checkbox变化的事件，传入参数为该节点-->
              <!--@on-click 绑定节点被点击事件，传入参数为该节点-->
              <%--<z-tree v-ref:nodes-select--%>
                      <%--:nodes="orgData"--%>
                      <%--:show-tree.sync="showOrgTree"--%>
                      <%--@on-click="selectOrg"--%>
                      <%--:show-checkbox="false">--%>
              <%--</z-tree>--%>
            </div>
          </div>
          <div class="col-md-2">
            <input v-model="form.salaMonth" v-el:sala-month
                   id="salaMonth" maxlength="20" data-tabindex="1" readonly
                   name="salaMonth" type="text"
                   class="form-control datetime input-sm" placeholder="月份选择">
          </div>

          <div class="col-md-1">
            <div class="form-group">
              <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default" alt="搜索"
                      title="搜索">
                <i class="fa fa-search"></i>
              </button>
            </div>
          </div>

          <div class="col-md-1">
            <div class="form-group">
              <button @click="exportBill" id="createBtn" type="button"
                      class="btn btn-outline btn-primary">导出
              </button>
            </div>
          </div>
          <div class="col-md-1">
            <div class="form-group">
              <button @click="salary" id="createBtnq" type="button"
                      class="btn btn-outline btn-primary">生成工资单
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


<script type="text/javascript">
  // 等前登录员工的机构信息
  /*var orgName = '<shiro:principal property="orgName"/>';
  var orgFamilyCode = '<shiro:principal property="orgFamilyCode"/>';*/
  var orgName = '安徽智装研究院';
  var orgFamilyCode = '1-194';
</script>
<%@include file="/WEB-INF/views/admin/components/ztree.jsp" %>
<script src="${ctx}/static/js/components/ztree.js"></script>
<script src="${ctx}/static/js/jquery.ztree.core.js"></script>
<script src="${ctx}/static/js/jquery.ztree.excheck.js"></script>
<script src="${ctx}/static/js/containers/salaryManagement/listPayroll.js"></script>