<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
  <%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
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
        function null2Empty(val){
            if(val == 'null' || val == null)
                return '';
            return val;
        }
        window.RocoUser = {
            userId: null2Empty('<shiro:principal property="id"/>'),
            account: null2Empty('<shiro:principal property="username"/>'),
            name: null2Empty('<shiro:principal property="name" />'),
            company:null2Empty('<shiro:principal property="companyName" />'),
            roles: null2Empty('<shiro:principal property="roles" />'),
            permissions: null2Empty('<shiro:principal property="permissions" />')
        }
        </script>
        <style>
          [v-cloak] {
            display: none;
          }

          .gray-bg {
            background-color: #f3f3f4
          }

          .white-bg {
            background-color: #ffffff;
          }

          .ibox-title {
            -moz-border-bottom-colors: none;
            -moz-border-left-colors: none;
            -moz-border-right-colors: none;
            -moz-border-top-colors: none;
            background-color: #ffffff;
            border-color: #e7eaec;
            -webkit-border-image: none;
            -o-border-image: none;
            border-image: none;
            border-style: solid solid none;
            border-width: 4px 0px 0;
            color: inherit;
            margin-bottom: 0;
            padding: 14px 15px 7px;
            min-height: 48px;
          }
          .ibox-content {
            background-color: #ffffff;
            color: inherit;
            padding: 15px 20px 20px 20px;
            border-color: #e7eaec;
            -webkit-border-image: none;
            -o-border-image: none;
            border-image: none;
            border-style: solid solid none;
            border-width: 1px 0px;
          }
          .ibox-content>div {
/*             display: flex; */
            margin-bottom: 10px;
          }
          .btn-primary {
            background-color: #1ab394;
            border-color: #1ab394;
            color: #FFFFFF;
/*             flex: 1; */
          }
        </style>
    </head>

    <body class="gray-bg">
      <div class="wrapper wrapper-content" id="container" v-cloak>
        <!--转单状况统计--> 
        <div class="panel panel-default gray-bg" style="margin:10px">
          <!--<div class="panel-body">
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">营销报表</h3>
        </div>
        <div class="panel-body clearFix">
          
        </div>
      </div>
    </div>-->

         <shiro:hasPermission name="report:reportCenter-wholeReport">
              <div class="ibox-title">
                <h3 class="panel-title">整体报表</h3>
              </div>
              <div class="panel-body ibox-content">
                <div class="row">
                  <div class="col-xs-6">
                    <a href="/api/reports" type="" class="btn btn-primary btn-block-query btn-block">实时报表</a>
                  </div>
                  <!--<div class="col-xs-6">
                    <a href="/api/reports" style="margin-right:15px" type="" class="btn btn-primary btn-block-query">实时报表</a>
                  </div>-->
                </div>
              </div>
          </shiro:hasPermission>

          <div class="row" style="margin-top:10px">
            <div class="col-sm-12">
              <div class="ibox-title">
                <h3 class="panel-title">营销报表</h3>
              </div>
              <div class="panel-body ibox-content">
                 <div class="row">
                  <shiro:hasPermission name="report:reportCenter-clueReport">
                   		<div class="col-xs-6">
                  			<a href="/api/reports/salereport?type=clue" style="margin-right:15px" type="" class="btn btn-primary btn-block-query col-xs-12">线索报表</a>
                  		</div>
                  </shiro:hasPermission>
                  <shiro:hasPermission name="report:reportCenter-intoReport">
                  		<div class="col-xs-6">
                  			<a href="/api/reports/salereport?type=shop" type="" class="btn btn-primary btn-block-query col-xs-12">进店报表</a>
                  		</div>
                  </shiro:hasPermission>
                </div>

                <div class="row">
                	<shiro:hasPermission name="report:reportCenter-smallReport">
                		<div class="col-xs-6">
                  			<a href="/api/reports/set?type=small" style="margin-right:15px" type="" class="btn btn-primary btn-block-query col-xs-12">小定报表</a>
                  		</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="report:reportCenter-bigReport">
                    	<div class="col-xs-6">
                  			<a href="/api/reports/set?type=large" type="" class="btn btn-primary btn-block-query col-xs-12">大定报表</a>
                  		</div>
                    </shiro:hasPermission>
                </div>
              </div>
            </div>
          </div>
          <div class="row" style="margin-top:10px">
            <div class="col-sm-12">
                <div class="ibox-title">
                  <h3 class="panel-title">财务报表</h3>
                </div>
                <div class="panel-body ibox-content">
                  <div class="row">
                  	<shiro:hasPermission name="report:reportCenter-cashReport">
                  		<div class="col-xs-7">
                    		<a href="/api/reports/finance?type=finance" style="margin-right:15px" class="btn btn-primary btn-block-query col-xs-12">现金收入流水报表</a>
                    	</div>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="report:reportCenter-receivableReport">
                    	<div class="col-xs-5">
                    		<a href="/api/reports/finance?type=receive" type="" class="btn btn-primary btn-block-query col-xs-12">应收报表</a>
                    	</div>
                    </shiro:hasPermission>
                  </div>
                  
                </div>
              </div>
          </div>

          <shiro:hasPermission name="report:reportCenter-wholeReport">
            <div class="ibox-title">
              <h3 class="panel-title">设计报表</h3>
            </div>
            <div class="panel-body ibox-content">
              <div class="row">
                <div class="col-xs-6">
                  <a href="/api/reports/design" type="" class="btn btn-primary btn-block-query btn-block">设计报表</a>
                </div>
                <!--<div class="col-xs-6">
                              <a href="/api/reports" style="margin-right:15px" type="" class="btn btn-primary btn-block-query">实时报表</a>
                            </div>-->
              </div>
            </div>
          </shiro:hasPermission>
        </div>
      </div>
      <script src="${ctx}/static/vendor/md5/md5.min.js"></script>
      <script src="${ctx}/static/js/containers/report/option.js?v=123"></script>
      <script src="${ctx}/static/js/containers/report/list.js"></script>
    </body>

    </html>