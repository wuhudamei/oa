<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<link href="${ctx}/static/css/zTreeStyle.css" rel="stylesheet">
<title>员工列表</title>
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
          <%-- 员工列表页不显示离职员工 --%>
          <%--<div class="col-md-2">--%>
          <%--<div class="form-group">--%>
          <%--<select v-model="form.employeeStatus"--%>
          <%--placeholder="员工状态"--%>
          <%--class="form-control">--%>
          <%--<option value="">员工状态</option>--%>
          <%--<option value="ON_JOB">在职</option>--%>
          <%--<option value="DIMISSION">离职</option>--%>
          <%--</select>--%>
          <%--</div>--%>
          <%--</div>--%>
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

<div id="modal" class="modal fade" tabindex="-1" data-width="760">
  <div class="wrapper wrapper-content">
    <div class="ibox-content">
      <form name="createMirror" novalidate class="form-horizontal" role="form" style="margin-right:10px">
        <div class="text-center">
          <h3>个人名片</h3>
        </div>
        <hr/>
        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">姓名</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.name"></label>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">员工编号</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.orgCode"></label>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">职务</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.position"></label>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <img :src="employee.photo" alt="" style="width: 105px;margin-left: 84px">
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">手机号</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.mobile"></label>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">邮箱</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.email"></label>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">入职日期</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.entryDate"></label>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">性别</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.gender | gender-filter"></label>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="row">
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">部门</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.department"></label>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6">
            <div class="row">
              <div class="form-group">
                <label class="col-md-3 control-label">公司</label>
                <div class="col-md-9">
                  <label class="form-control" v-text="employee.company"></label>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="modal-footer">
          <button @click="cancel" type="button" data-dismiss="modal" class="btn">关闭</button>
        </div>
      </form>
      <!-- ibox end -->
    </div>
  </div>
</div>

<script type="text/javascript">
  // 等前登录员工机构信息
  var orgName = '<shiro:principal property="orgName"/>';
  var orgFamilyCode = '<shiro:principal property="orgFamilyCode"/>';
</script>
<%@include file="/WEB-INF/views/admin/components/ztree.jsp" %>
<script src="${ctx}/static/js/components/ztree.js"></script>
<script src="${ctx}/static/js/jquery.ztree.core.js"></script>
<script src="${ctx}/static/js/jquery.ztree.excheck.js"></script>
<script src="${ctx}/static/js/containers/employee/list.js"></script>