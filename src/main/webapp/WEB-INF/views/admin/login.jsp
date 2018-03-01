<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="renderer" content="webkit">

  <title>大美装饰管理平台登录</title>
  <meta name="keywords" content="大美装饰管理平台">
  <meta name="description" content="大美装饰管理平台">

  <%--<link rel="shortcut icon" href="/static/css/img/favicon.ico">--%>
  <link rel="stylesheet" href="${ctx}/static/css/lib.css">
  <link rel="stylesheet" href="${ctx}/static/css/style.css">
  <script src="${ctx}/static/js/lib.js"></script>
  <!-- 页面公用 -->
  <script>
    var ctx = '${ctx}';
  </script>
</head>

<body class="gray-bg">
<div id=loginCont class="middle-box text-center loginscreen  animated fadeInDown">
	    <div style="margin: 20px 0px">
	      <img src="/static/img/logo.png">
	    </div>
		<h3>欢迎使用 大美装饰管理平台</h3>
		
		<div class="login-form" style="width:99%;height:18px;left:0;top:18px;border-right:1px solid #f4f4f4;text-decoration:none">
			<ul class="nav nav-tabs" role="tablist">
			    <li role="presentation" class="active" style="width: 50%;"><a @click="a('qrcode')" aria-controls="home" role="tab" data-toggle="tab"> 扫码登录</a></li>
			    <li role="presentation" style="width: 50%;"><a @click="a('login')" aria-controls="profile" role="tab" data-toggle="tab">账户登录</a></li>
			</ul>
          <div class="qrcode-login" v-show="nameLogin==0">
	        <div class="form-group">
			    <div style="margin-top: 1px">
			      <img width="267px" height="267px" :src="qrcode"/>
			    </div>
			</div>
			<div>
				<font size="4.5px" color="red">打开手机微信 &nbsp;&nbsp;扫描二维码</font>
			</div>
          </div>			
          <div class="login-box" v-if="nameLogin==1" style="padding: 20px 0;">
			  <form v-on:submit.prevent="submit" role="form">
			    <div class="form-group">
			      <input
			        v-model="form.username"
			        id="username" name="username" type="text" class="form-control" placeholder="请输入员工工号" required="">
			    </div>
			    <div class="form-group">
			      <input
			        v-model="form.password"
			        type="password" class="form-control" placeholder="请输入密码" required="">
			    </div>
			    <button :disabled="submitting" type="submit" class="btn btn-block btn-primary">登 录</button>
			  </form>    
          </div>
		</div>
</div>

<script src="${ctx}/static/js/utils.js"></script>
<script src="${ctx}/static/js/socket.io.js"></script>
<script src="${ctx}/static/js/containers/login/login.js"></script>
</body>
</html>
