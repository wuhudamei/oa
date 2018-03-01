<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<title>信息补全</title>
<meta name="keywords" content="">
<meta name="description" content="">
<style>
</style>
</head>
<body id="app" class="fixed-sidebar full-height-layout gray-bg">
<div id="container" class="wrapper wrapper-content" v-cloak>
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div>
                <div class="row">
                    <form id="searchForm" @submit.prevent="query">
                        <div class="col-md-2" >
                            <button @click="mark" id="createBtn" type="button" class="btn btn-outline btn-primary">提交</button>
                        </div>
                    </form>
                </div>
                <div>
                    <table v-el:dataTable id="dataTable" width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>



</div>
<script src="/static/vendor/decimal/decimal.min.js"></script>
<script src="/static/js/containers/materialSupplierAccountChecking/materialSupplierAccountSupplement.js"></script>
</body>
</html>