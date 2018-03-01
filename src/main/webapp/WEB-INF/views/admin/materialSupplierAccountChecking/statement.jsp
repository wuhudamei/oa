<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<title>报表</title>
<meta name="keywords" content="">
<meta name="description" content="">
<style>
</style>
</head>
<body class="fixed-sidebar full-height-layout gray-bg" >
<div id="container" class="wrapper wrapper-content" v-cloak>
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <div id="reportDiv" style="height: 400px; width: 1100px;"></div>
</div>

<script src="/static/vendor/echarts/echarts.min.js"></script>
<script src="/static/js/containers/materialSupplierAccountChecking/statement.js"></script>
</body>
</html>