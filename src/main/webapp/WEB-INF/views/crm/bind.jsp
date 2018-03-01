<%--
  这个页面是使用手机号和密码进行绑定操作
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">

<title>大美装饰管理平台账号绑定</title>
<meta name="keywords" content="大美装饰管理平台,登录">
<meta name="description" content="大美装饰管理平台登录">

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
	<div id="loginCont" class="middle-box text-center loginscreen  animated fadeInDown">
	  <div>
	    <div>
	      <h1 class="logo-name"><img src="/static/img/logo.png"></h1>
	    </div>
	    <h3>请输入您的手机号/密码进行绑定</h3>
	
	    <form v-on:submit.prevent="submit" role="form">
	      <div class="form-group">
	        <input
	          v-model="form.mobile"
	          id="mobile" name="mobile" type="number" class="form-control"
	          placeholder="请输入手机号" required="" >
	      </div>
	      
	      <div class="form-group">
	        <input
	          v-model="form.password"
	          id="password" name="password" type="text" class="form-control"
	          placeholder="请输入密码" required="">
	      </div>
	      <button :disabled="submitting" type="submit" class="btn btn-block btn-primary">绑定</button>
	      </p>
	    </form>
	  </div>
	</div>
	<script src="${ctx}/static/crm/js/bind.js?v=ss"></script>
</body>
</html>