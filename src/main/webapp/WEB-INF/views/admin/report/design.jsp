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
      <meta http-equiv="Cache-Control" content="no-siteapp" />
      <title>设计报表</title>
      <!-- style -->
      <link rel="shortcut icon" href="/static/img/favicon.ico">
      <link rel="stylesheet" href="${ctx}/static/css/lib.css">
      <!--<link rel="stylesheet" href="${ctx}/static/css/style.css">-->
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
            background-color: #1ab394 !important;
          }

          .num-stl {
            font-weight: bold;
          }

          [v-cloak] {
            display: none;
          }

          .reportBox {
            display: -webkit-box; 
            display: -moz-box;
            display: -ms-flexbox; 
            display: -webkit-flex; 
            display: flex;
            justify-content: space-between;
            text-align: center;
            padding: 10px 15px;
            border-bottom: 1px solid #eee;
            background-color: #f5f5f5;
            font-weight: 500;
            border-bottom: 1px solid #ddd;
            border-top-left-radius: 3px;
            border-top-right-radius: 3px;
          }

          .text-red {
            color: #D53A35;
          }
          .gray-back{
            background-color: #f5f5f5;
            border-top: 1px solid #ddd;
            border-bottom: 1px solid #ddd;
          }
          .btn-primary {
            background-color: #1ab394;
            border-color: #1ab394;
            color: #FFFFFF;
          }
          .btn-text {
            line-height: 1.42857143;
            padding: 6px 0;
            flex:1;
            -webkit-box-flex:1;
            -moz-box-flex:1;
            -webkit-flex:1;
            -ms-flex:1
          }

          .btn-primary.disabled:hover,
          .btn-primary[disabled]:hover,
          fieldset[disabled] .btn-primary:hover,
          .btn-primary.disabled:focus,
          .btn-primary[disabled]:focus,
          fieldset[disabled] .btn-primary:focus,
          .btn-primary.disabled.focus,
          .btn-primary[disabled].focus,
          fieldset[disabled] .btn-primary.focus,.btn-primary:hover,.btn-primary:active:focus,.btn-primary:active:hover {
             background-color: #1ab394;
            border-color: #1ab394;
            color: #fff;
          }
        </style>
    </head>

    <body>
      <div class="wrapper wrapper-content" id="container" v-cloak>
        <!--转单状况统计-->
        <div class="panel">
          <div class="panel-heading big-header">设计报表 <button class="pull-right" type="" @click="backList">主页</button><button class="pull-right" type="" @click="goBack">返回</button></div>
        </div>
        <div class="panel-body">
          <pie-chart :series="dayData" :hei="150" :name="dateTime" :unit="false">
            <div class="reportBox" slot="title">
              <button type="button" class="btn btn-primary"  @click="lastClick(false)">前一天</button>
              <span class="btn-text">{{dateTime}}</span>
              <button type="button" class="btn btn-primary" :disabled="dayDisabled" @click="nextClick(false)">后一天</button>
            </div>
            <dl class="week-month left" slot="content">
              <dd v-for="ite of dayData">{{ite.name}}:&nbsp; <span class="num-stl">{{ite.value}}</span></dd>
              <dd class="num-stl text-red" v-if="transferMoneyData || transferMoneyData === 0">转单产值:&nbsp; <span class="num-stl">{{transferMoneyData}}万元</span></dd>
            </dl>
            <a slot="href" style="margin-top:8px" class="pull-right" href="javascript:void(0)" @click="handleHref(false)">查看详情</a>
          </pie-chart>
        </div>


        <div class="panel-body">
          <pie-chart :show="true" :series="monthData" :name="dataMonth+'月'" :unit="false" :hei="150">
            <div class="reportBox" slot="title">
              <button type="button" class="btn btn-primary" @click="lastClick(true)">前一月</button>
              <span class="btn-text">{{dataMonth}}月</span>
              <button type="button" class="btn btn-primary" :disabled="monthDisabled" @click="nextClick(true)">后一月</button>
            </div>
            <dl class="week-month left" slot="content">
              <dd v-for="ite of monthData">{{ite.name}}:&nbsp; <span class="num-stl">{{ite.value}}</span></dd>
              <dd class="num-stl text-red" v-if="monthTransferMoneyData || monthTransferMoneyData === 0">转单产值:&nbsp; <span class="num-stl">{{monthTransferMoneyData}}万元</span></dd>
            </dl>
            <a slot="href" class="pull-right" href="javascript:void(0)" @click="handleHref('month')">查看详情</a>
            <div class="" slot="other">
              <div class="panel-heading gray-back text-center">
                <h3 class="panel-title">趋势图</h3>
              </div>
              <div class="panel-body">
                <div id="secondFour" style="height: 250px;"></div>
              </div>
            
              </div>
          </pie-chart>
          
        </div>


        <%@include file="/WEB-INF/views/admin/components/pieChart.jsp" %>
      </div>
      <script src="${ctx}/static/vendor/moment/moment.min.js"></script>
      <script src="${ctx}/static/js/containers/report/option.js?v=123"></script>
      <!--<script src="${ctx}/static/js/containers/report/report.js"></script>-->
      <script src="${ctx}/static/js/containers/report/design.js"></script>
    </body>

    </html>