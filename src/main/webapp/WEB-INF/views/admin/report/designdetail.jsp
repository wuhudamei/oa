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
  <title>设计报表</title>
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
     .text-red {
        color: #D53A35;
      }
  </style>
</head>
<body>
<div class="wrapper wrapper-content" id="container" v-cloak>
  <!--转单状况统计-->
  <div class="panel panel-default">
    <div class="panel-heading big-header">{{date}} <button class="pull-right" type="" @click="backList">主页</button><button class="pull-right" type="" @click="goBack">返回</button></div>
    <div class="panel-body" v-for="item of pieData">
      <pie-chart :series="item.chat.list" :name="date" :unit="false" :hei="150">
        <div class="panel-heading" slot="title">
          <h3 class="panel-title" >{{item.channel}}</h3>
        </div>
        
        <dl class="week-month left" slot="content">
          <dd :class="[ ite.status === 7 ? 'num-stl text-red' : '']" v-for="ite of item.items">{{ite.name}}:&nbsp; <span class="num-stl">{{ite.status === 7 ? ite.money + '万元' : ite.count}}</span></dd>
        </dl>
      </pie-chart>
    </div>
  </div>
  
   <%@include file="/WEB-INF/views/admin/components/pieChart.jsp" %>
</div>
<script src="${ctx}/static/vendor/md5/md5.min.js"></script>
<script src="${ctx}/static/js/containers/report/option.js?v=123"></script>
<!--<script src="${ctx}/static/js/containers/report/report.js"></script>-->
<script src="${ctx}/static/js/containers/report/designdetail.js"></script>
</body>
</html>