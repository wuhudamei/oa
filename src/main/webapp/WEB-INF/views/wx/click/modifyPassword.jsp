<%--
  这个页面是扫码登录绑定账号时绑定用，和点击菜单绑定账号逻辑不同，所以才会两个绑定页面
--%>
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

  <title>大美装饰管理平台账号绑定</title>
  <meta name="keywords" content="大美装饰管理平台系统,登录">
  <meta name="description" content="大美装饰管理平台系统登录">

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
  <div>
    <h3>请修改你的OA密码</h3>

    <form v-on:submit.prevent="submit" role="form">
      <div class="form-group">
        <input
          v-model="form.newPassword"
          id="newPassword" name="newPassword" type="text" class="form-control" placeholder="请输入新密码" required="">
      </div>
      <div class="form-group">
        <input
          v-model="form.confirmPassword" name="confirmPassword" type="text" class="form-control" placeholder="请再次输入新密码" required="">
      </div>
      <button :disabled="submitting" type="submit" class="btn btn-block btn-primary">保存</button>
      </p>
    </form>
  </div>
</div>
<script src="${ctx}/static/js/wx/click/modifyPassword.js?v=8"></script>
</body>
</html>
