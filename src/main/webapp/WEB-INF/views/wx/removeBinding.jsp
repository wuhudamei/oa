<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <script>
        var ctx = '${ctx}';
    </script>
</head>

<body class="gray-bg">
<div id=loginCont class="middle-box text-center loginscreen  animated fadeInDown">
    <div>
        <form role="form">
            <div style="text-align: left">
                <div>部门：{{company}}</div>
                <br>
                <div>姓名：{{name}}</div>
                <br>
                <div> 工号：{{account}}</div>
            </div>
        </form>
    </div>
    <button style="margin-top: 20px"
            type="submit"
            class="btn btn-block btn-danger"
            :disabled="disable"
            @click="removebinding">解除绑定</button>
</div>
<script src="${ctx}/static/js/wx/removeBinding.js"></script>
</body>
</html>
