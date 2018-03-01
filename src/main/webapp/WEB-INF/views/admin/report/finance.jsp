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
          <div class="panel-heading big-header">{{params.type === "finance" ? '现金收入流水报表' : '应收报表'}} <button class="pull-right" type="" @click="backList">主页</button><button class="pull-right" type="" @click="goBack">返回</button></div>
          <div class="panel-body">
            <div class="panel panel-default">
            <div class="reportBox" slot="title">
              <!--<div class="" @click="lastClick(false)">前一天</div>
              <div class="">{{dateTime}}</div>
              <div class="" @click="nextClick(false)">后一天</div>-->
              <button type="button" class="btn btn-primary"  @click="lastClick(false)">前一天</button>
              <span class="btn-text">{{dateTime}}</span>
              <button type="button" class="btn btn-primary" :disabled="dayDisabled" @click="nextClick(false)">后一天</button>
            </div>
            <div class="panel-body clearFix">
              <div class="">
                <table class="table table-striped table-bordered table-hover" width="100%" v-if="setday">
                  <tbody>
                    <tr v-if="setday.FinanceStatementInfo[0].AdvanceCharge">
                      <td>预付款</td>
                      <td>{{setday.FinanceStatementInfo[0].AdvanceCharge}}万元</td>
                    </tr>
                    <tr>
                      <td>首期款</td>
                      <td>{{setday.FinanceStatementInfo ? setday.FinanceStatementInfo[0].InitialCharge : setday.dayReceivableInfo[0].InitialCharge}}万元</td>
                    </tr>
                    <tr>
                      <td>中期款</td>
                      <td>{{setday.FinanceStatementInfo ? setday.FinanceStatementInfo[0].MiddleCharge : setday.dayReceivableInfo[0].MiddleCharge}}万元</td>
                    </tr>
                    <tr>
                      <td>尾款</td>
                      <td>{{setday.FinanceStatementInfo ? setday.FinanceStatementInfo[0].LastCharge : setday.dayReceivableInfo[0].LastCharge}}万元</td>
                    </tr>
                    <tr>
                      <td>拆改费</td>
                      <td>{{setday.FinanceStatementInfo ? setday.FinanceStatementInfo[0].RemoveCharge : setday.dayReceivableInfo[0].RemoveCharge}}万元</td>
                    </tr>
                    <tr v-if="setday.FinanceStatementInfo[0].LastChangeCharge">
                      <td>尾款后变更</td>
                      <td>{{setday.FinanceStatementInfo[0].LastChangeCharge}}万元</td>
                    </tr>
                    <tr>
                      <td>总收入</td>
                      <td>{{setday.FinanceStatementInfo ? setday.FinanceStatementInfo[0].TotalCharge : setday.dayReceivableInfo[0].TotalCharge}}万元</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
            </div>
          </div>
          <div class="panel-body">
            <div class="panel panel-default">
            <div class="reportBox" slot="title">
              <!--<div class="" @click="lastClick(true)">上一月</div>
              <div class="">{{dateMonth}}月</div>
              <div class="" @click="nextClick(true)">下一月</div>-->
              <button type="button" class="btn btn-primary" @click="lastClick(true)">前一月</button>
              <span class="btn-text">{{dateMonth}}月</span>
              <button type="button" class="btn btn-primary" :disabled="monthDisabled" @click="nextClick(true)">后一月</button>
            </div>
            <div class="panel-body">
              <h3 class="panel-title text-center" style="margin:5px 0">趋势图</h3>
              <div id="secondFour" style="height: 250px;"></div>
              <table class="table table-striped table-bordered table-hover" width="100%" v-if="setmonth">
                  <tbody>
                    <tr v-if="setmonth.FinanceStatementInfo[0].AdvanceCharge">
                      <td>预付款</td>
                      <td>{{setmonth.FinanceStatementInfo[0].AdvanceCharge}}万元</td>
                    </tr>
                   <tr>
                      <td>首期款</td>
                      <td>{{setmonth.FinanceStatementInfo ? setmonth.FinanceStatementInfo[0].InitialCharge : setmonth.MonthReceivableInfo[0].InitialCharge}}万元</td>
                    </tr>
                    <tr>
                      <td>中期款</td>
                      <td>{{setmonth.FinanceStatementInfo ? setmonth.FinanceStatementInfo[0].MiddleCharge : setmonth.MonthReceivableInfo[0].MiddleCharge}}万元</td>
                    </tr>
                    <tr>
                      <td>尾款</td>
                      <td>{{setmonth.FinanceStatementInfo ? setmonth.FinanceStatementInfo[0].LastCharge : setmonth.MonthReceivableInfo[0].LastCharge}}万元</td>
                    </tr>
                    <tr>
                      <td>拆改费</td>
                      <td>{{setmonth.FinanceStatementInfo ? setmonth.FinanceStatementInfo[0].RemoveCharge : setmonth.MonthReceivableInfo[0].RemoveCharge}}万元</td>
                    </tr>
                    <tr v-if="setmonth.FinanceStatementInfo[0].LastChangeCharge">
                      <td>尾款后变更</td>
                      <td>{{setmonth.FinanceStatementInfo[0].LastChangeCharge}}万元</td>
                    </tr>
                    <tr>
                      <td>总收入</td>
                      <td>{{setmonth.FinanceStatementInfo ? setmonth.FinanceStatementInfo[0].TotalCharge : setmonth.MonthReceivableInfo[0].TotalCharge}}万元</td>
                    </tr>
                  </tbody>
                </table>
             </div>
          </div>
          </div>
        </div>

        <%@include file="/WEB-INF/views/admin/components/pieChart.jsp" %>
      </div>
      <script src="${ctx}/static/js/containers/report/option.js?v=123"></script>
      <!--<script src="${ctx}/static/js/containers/report/report.js"></script>-->
      <script src="${ctx}/static/js/containers/report/finance.js"></script>
    </body>

    </html>