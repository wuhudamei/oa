<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>查看员工</title>
<div id="container" class="wrapper wrapper-content">
  <div id="breadcrumb">
    <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
  </div>
  <!-- ibox start -->
  <div class="ibox-content">
    <validator name="validation">
      <form name="createMirror" novalidate class="form-horizontal" role="form">

        <div class="text-center">
          <h3>{{regulation.title}}</h3>
        </div>
        <hr/>
        <div class="row">
         {{{regulation.content}}}

        <br/>
        <br/>
        <div class="text-center">
          <button @click="cancel" type="button" class="btn btn-default">返回
          </button>
        </div>
        </div>
      </form>
    </validator>
    <!-- ibox end -->
  </div>
</div>
<!-- container end-->
<script src="${ctx}/static/js/containers/regulation/detail.js"></script>