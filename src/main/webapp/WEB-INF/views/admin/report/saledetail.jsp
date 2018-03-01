<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="keywords" content="">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
  <title>报表系统</title>
  <!-- style -->
  <link rel="shortcut icon" href="/static/img/favicon.ico">
  <link rel="stylesheet" href="${ctx}/static/css/lib.css">
  <!-- <link rel="stylesheet" href="${ctx}/static/css/style.css"> -->

  <script src="${ctx}/static/js/lib.js"></script>
  <script src="${ctx}/static/vendor/echarts/echarts.common.min.js"></script>

  <%@include file="/WEB-INF/views/admin/shims/config.jsp" %>
  <script src="${ctx}/static/js/utils.js"></script>
  <script>
    var ctx = '${ctx}';
  </script>
  <style>
    .week-month {
      display: inline-block;
      width: 49%;
    }
    .panel-body {
      padding: 10px;
    }

    .left {
      float: left;
    }
    .right {
      float: right;
    }
    .clearFix {
      overflow: hidden;
    }
    dl {
      margin-top: 10px;
      margin-bottom: 10px;
    }
    .pie-top {
      margin-top: 10px;
    }

    .big-header {
      background-color:#1ab394 !important;
    }

    .num-stl {
      font-weight: bold;
    }

    [v-cloak] {
      display: none;
    }
    .panel-heading {
      padding: 10px;
     }
  </style>
</head>
<body>
<div class="wrapper wrapper-content" id="container" v-cloak>
  <!--转单状况统计-->
  <div class="panel panel-default">
    <div class="panel-heading big-header">{{date}} <button class="pull-right" type="" @click="backList">主页</button><button class="pull-right" type="" @click="goBack">返回</button></div>
    <div class="panel-body" v-for="item of pieData">
      <pie-chart :series="item.childPie" name="">
        <div class="panel-heading" slot="title">
          <h3 class="panel-title" >{{item.bigSourceName}}-{{item.totalAmount}}</h3>
        </div>
        
        <dl class="week-month left" slot="content">
          <dd v-for="ite of item.childSource">{{ite.sourceName}}:&nbsp; <span class="num-stl">{{ite.amount}}</span></dd>
        </dl>
      </pie-chart>
    </div>
  </div>

  <!--<div class="panel panel-default">
    <div class="panel-heading big-header">数量统计 <button class="pull-right" type="" @click="backList">主页</button><button class="pull-right" type="">返回</button></div>
    <div class="panel-body">
      <pie-chart>
        <div class="reportBox" slot="title">
          <div class="">前一天</div>
          <div class="">20170511</div>
          <div class="">后一天</div>
        </div>
        <dl class="week-month left" slot="content">
          <dd>新大定数:&nbsp; <span class="num-stl">{{change.dayData.BigCount}}</span></dd>
          <dd>新转设计数:&nbsp; <span class="num-stl">{{change.dayData.ToDesign}}</span></dd>
          <dd>新转单数:&nbsp; <span class="num-stl">{{change.dayData.ToConstruct}}</span></dd>
          <dd>新竣工数:&nbsp; <span class="num-stl">{{change.dayData.ToComplete}}</span></dd>
          <dd>新退单数:&nbsp; <span class="num-stl">{{change.dayData.ToQuit}}</span></dd>
        </dl>
      </pie-chart>
    </div>
  </div>-->
  
   <%@include file="/WEB-INF/views/admin/components/pieChart.jsp" %>
</div>
<script src="${ctx}/static/vendor/md5/md5.min.js"></script>
<script src="${ctx}/static/js/containers/report/option.js?v=123"></script>
<!--<script src="${ctx}/static/js/containers/report/report.js"></script>-->
<script src="${ctx}/static/js/containers/report/saledetail.js"></script>
</body>
</html>