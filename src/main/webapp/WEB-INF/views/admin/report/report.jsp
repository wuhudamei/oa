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
  </style>
</head>
<body>
<div class="wrapper wrapper-content" id="container" v-cloak>
  <!--转单状况统计-->
  <div class="panel panel-default">
    <div class="panel-heading big-header">转单状况统计</div>
    <div class="panel-body">
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">本日({{currDay}})</h3>
        </div>
        <div class="panel-body clearFix">
          <dl class="week-month left">
            <dd>新大定数:&nbsp; <span class="num-stl">{{change.dayData.BigCount}}</span></dd>
            <dd>新转设计数:&nbsp; <span class="num-stl">{{change.dayData.ToDesign}}</span></dd>
            <dd>新转单数:&nbsp; <span class="num-stl">{{change.dayData.ToConstruct}}</span></dd>
            <dd>新竣工数:&nbsp; <span class="num-stl">{{change.dayData.ToComplete}}</span></dd>
            <dd>新退单数:&nbsp; <span class="num-stl">{{change.dayData.ToQuit}}</span></dd>
          </dl>
          <div class="week-month right pie-top">
            <div id="firstOne" style="height: 100px;"></div>
          </div>
        </div>
      </div>

      <div class="panel panel-default week-month">
        <div class="panel-heading">
          <h3 class="panel-title">本周({{currWeek}})</h3>
        </div>
        <div class="panel-body">
          <dl class="">
            <dd>新大定数:&nbsp; <span class="num-stl">{{change.weekData.BigCount}}</span></dd>
            <dd>新转设计数:&nbsp; <span class="num-stl">{{change.weekData.ToDesign}}</span></dd>
            <dd>新转单数:&nbsp; <span class="num-stl">{{change.weekData.ToConstruct}}</span></dd>
            <dd>新竣工数:&nbsp; <span class="num-stl">{{change.weekData.ToComplete}}</span></dd>
            <dd>新退单数:&nbsp; <span class="num-stl">{{change.weekData.ToQuit}}</span></dd>
          </dl>
        </div>
      </div>
      <div class="panel panel-default week-month">
        <div class="panel-heading">
          <h3 class="panel-title">本月({{currMonth}})</h3>
        </div>
        <div class="panel-body">
          <dl class="">
            <dd>新大定数:&nbsp; <span class="num-stl">{{change.monthData.BigCount}}</span></dd>
            <dd>新转设计数:&nbsp; <span class="num-stl">{{change.monthData.ToDesign}}</span></dd>
            <dd>新转单数:&nbsp; <span class="num-stl">{{change.monthData.ToConstruct}}</span></dd>
            <dd>新竣工数:&nbsp; <span class="num-stl">{{change.monthData.ToComplete}}</span></dd>
            <dd>新退单数:&nbsp; <span class="num-stl">{{change.monthData.ToQuit}}</span></dd>
          </dl>
        </div>
      </div>

      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">近三十日趋势图({{nearThirty}})</h3>
        </div>
        <div class="panel-body">
          <div id="firstFour" style="height: 250px;"></div>
        </div>
      </div>
    </div>
  </div>
  <!--财务应收状况统计-->
  <div class="panel panel-default">
    <div class="panel-heading big-header">财务应收状况统计</div>
    <div class="panel-body">
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">本日应收({{currDay}})</h3>
        </div>
        <div class="panel-body clearFix">
          <dl class="week-month left">
            <dd>首期款:&nbsp; <span class="num-stl">{{receivable.dayData.InitialCharge}} </span>万元</dd>
            <dd>拆改费:&nbsp; <span class="num-stl">{{receivable.dayData.RemoveCharge}} </span>万元</dd>
            <dd>中期款:&nbsp; <span class="num-stl">{{receivable.dayData.MiddleCharge}} </span>万元</dd>
            <dd>尾款:&nbsp; <span class="num-stl">{{receivable.dayData.LastCharge}} </span>万元</dd>
            <dd class="num-stl">合计:&nbsp; <span class="num-stl">{{receivable.dayData.TotalCharge}} </span>万元</dd>
          </dl>
          <div class="week-month right pie-top">
            <div id="secondOne" style="height: 100px;"></div>
          </div>
        </div>
      </div>
      <div class="panel panel-default week-month">
        <div class="panel-heading">
          <h3 class="panel-title">本周应收({{currWeek}})</h3>
        </div>
        <div class="panel-body">
          <dl class="">
            <dd>首期款:&nbsp; <span class="num-stl">{{receivable.weekData.InitialCharge}} </span>万元</dd>
            <dd>拆改费:&nbsp; <span class="num-stl">{{receivable.weekData.RemoveCharge}} </span>万元</dd>
            <dd>中期款:&nbsp; <span class="num-stl">{{receivable.weekData.MiddleCharge}} </span>万元</dd>
            <dd>尾款:&nbsp; <span class="num-stl">{{receivable.weekData.LastCharge}} </span>万元</dd>
            <dd class="num-stl">合计:&nbsp; <span class="num-stl">{{receivable.weekData.TotalCharge}} </span>万元</dd>
          </dl>
        </div>
      </div>
      <div class="panel panel-default week-month">
        <div class="panel-heading">
          <h3 class="panel-title">本月应收({{currMonth}})</h3>
        </div>
        <div class="panel-body">
          <dl class="">
            <dd>首期款:&nbsp; <span class="num-stl">{{receivable.monthData.InitialCharge}} </span>万元</dd>
            <dd>拆改费:&nbsp; <span class="num-stl">{{receivable.monthData.RemoveCharge}} </span>万元</dd>
            <dd>中期款:&nbsp; <span class="num-stl">{{receivable.monthData.MiddleCharge}} </span>万元</dd>
            <dd>尾款:&nbsp; <span class="num-stl">{{receivable.monthData.LastCharge}} </span>万元</dd>
            <dd class="num-stl">合计:&nbsp; <span class="num-stl">{{receivable.monthData.TotalCharge}} </span>万元</dd>
          </dl>
        </div>
      </div>
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">近三十日趋势图({{nearThirty}})</h3>
        </div>
        <div class="panel-body">
          <div id="secondFour" style="height: 250px;"></div>
        </div>
      </div>
    </div>
  </div>
  <!--财务现金收入流水状况统计-->
  <div class="panel panel-default">
    <div class="panel-heading big-header">财务现金收入流水状况统计</div>
    <div class="panel-body">
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">本日({{currDay}})</h3>
        </div>
        <div class="panel-body clearFix">
          <dl class="week-month left">
            <dd>预付款:&nbsp; <span class="num-stl">{{statement.dayInfo.AdvanceCharge}} </span>万元</dd>
            <dd>首期款:&nbsp; <span class="num-stl">{{statement.dayInfo.InitialCharge}} </span>万元</dd>
            <dd>拆改费:&nbsp; <span class="num-stl">{{statement.dayInfo.RemoveCharge}} </span>万元</dd>
            <dd>中期款:&nbsp; <span class="num-stl">{{statement.dayInfo.MiddleCharge}} </span>万元</dd>
            <dd>尾款:&nbsp; <span class="num-stl">{{statement.dayInfo.LastCharge}} </span>万元</dd>
            <dd>尾款后变更款:&nbsp; <span class="num-stl">{{statement.dayInfo.LastChangeCharge}} </span>万元</dd>
            <dd class="num-stl">总收入:&nbsp; <span class="num-stl">{{statement.dayInfo.TotalCharge}} </span>万元</dd>
          </dl>
          <div class="week-month right pie-top">
            <div id="threeOne" style="height: 130px;"></div>
          </div>

        </div>
      </div>
      <div class="panel panel-default week-month">
        <div class="panel-heading">
          <h3 class="panel-title">本周({{currWeek}})</h3>
        </div>
        <div class="panel-body">
          <dl class="">
            <dd>预付款:&nbsp; <span class="num-stl">{{statement.weekInfo.AdvanceCharge}} </span>万元</dd>
            <dd>首期款:&nbsp; <span class="num-stl">{{statement.weekInfo.InitialCharge}} </span>万元</dd>
            <dd>拆改费:&nbsp; <span class="num-stl">{{statement.weekInfo.RemoveCharge}} </span>万元</dd>
            <dd>中期款:&nbsp; <span class="num-stl">{{statement.weekInfo.MiddleCharge}} </span>万元</dd>
            <dd>尾款:&nbsp; <span class="num-stl">{{statement.weekInfo.LastCharge}} </span>万元</dd>
            <dd>尾款后变更款:&nbsp; <span class="num-stl">{{statement.weekInfo.LastChangeCharge}} </span>万元</dd>
            <dd class="num-stl">总收入:&nbsp; <span class="num-stl">{{statement.weekInfo.TotalCharge}} </span>万元</dd>
          </dl>
        </div>
      </div>
      <div class="panel panel-default week-month">
        <div class="panel-heading">
          <h3 class="panel-title">本月({{currMonth}})</h3>
        </div>
        <div class="panel-body">
          <dl class="">
            <dd>预付款:&nbsp; <span class="num-stl">{{statement.monthInfo.AdvanceCharge}} </span>万元</dd>
            <dd>首期款:&nbsp; <span class="num-stl">{{statement.monthInfo.InitialCharge}} </span>万元</dd>
            <dd>拆改费:&nbsp; <span class="num-stl">{{statement.monthInfo.RemoveCharge}} </span>万元</dd>
            <dd>中期款:&nbsp; <span class="num-stl">{{statement.monthInfo.MiddleCharge}} </span>万元</dd>
            <dd>尾款:&nbsp; <span class="num-stl">{{statement.monthInfo.LastCharge}} </span>万元</dd>
            <dd>尾款后变更款:&nbsp; <span class="num-stl">{{statement.monthInfo.LastChangeCharge}} </span>万元</dd>
            <dd class="num-stl">总收入:&nbsp; <span class="num-stl">{{statement.monthInfo.TotalCharge}} </span>万元</dd>
          </dl>
        </div>
      </div>
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">近三十日趋势图({{nearThirty}})</h3>
        </div>
        <div class="panel-body">
          <div id="threeFour" style="height: 250px;"></div>
        </div>
      </div>
    </div>
  </div>
</div>
<script src="${ctx}/static/vendor/md5/md5.min.js"></script>
<script src="${ctx}/static/js/containers/report/option.js?v=123"></script>
<script src="${ctx}/static/js/containers/report/report.js?v=123"></script>
</body>
</html>