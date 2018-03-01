<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<title>手动验收</title>
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
                        <div class="col-sm-3">
                            <div class="form-group" style="line-height: 34px"
                                 :class="{'has-error':($validation.starttime.invalid && $validation.touched)}">
                                <label  class="col-sm-5 control-label">材料供应商</label>
                                <div class="col-sm-5 no-padding">
                                    <select v-model="form.pdSupplierId"
                                            id="pdSupplierId"
                                            name="pdSupplierId"
                                            class="form-control"
                                            @change="form.pdSkuname=''">
                                            <option value="">请选择</option>
                                        <option :value="item.id"
                                                v-for="item of level1"
                                                placeholder="请选择材料供应商" v-text="item.pd_supplier">
                                        </option>
                                    </select>
                                    <input type="hidden" v-model="form.pdSupplier">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="form-group" style="line-height: 34px"
                                 :class="{'has-error':($validation.starttime.invalid && $validation.touched)}">
                                <label class="col-sm-4 control-label">商品名称</label>
                                <div class="col-sm-5 no-padding">
                                    <select v-model="form.pdSkuname"
                                            id="pdSkuname"
                                            name="pdSkuname"
                                            class="form-control">
                                        <option value="">请选择</option>
                                        <option :value="item.text"
                                                v-for="item in skunameArr"
                                                v-if="item.proid === form.pdSupplierId"
                                                placeholder="请选择商品名称">{{item.text}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-3">
                            <div class="form-group">
                                <label class="sr-only" for="keyword"></label>
                                <input
                                        v-model="form.keyword"
                                        id="keyword"
                                        name="keyword"
                                        type="text"
                                        placeholder="请输入订单编号/客户姓名" class="form-control"/>
                            </div>
                        </div>

                        <div class="col-md-1" >
                            <div class="form-group" >
                                <button id="searchBtn" @click="query" type="button" class="btn btn-block btn-outline btn-default"
                                        alt="搜索"
                                        title="搜索">
                                    <i class="fa fa-search"></i>
                                </button>
                            </div>
                        </div>
                         <div class="col-md-2" >
                            <button @click="mark" id="createBtn" type="button" class="btn btn-outline btn-primary">验收</button>

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
    <!-- ibox end -->
</div>



</div>
<script src="/static/vendor/decimal/decimal.min.js"></script>
<script src="/static/js/containers/materialSupplierAccountChecking/HMCheckAndAccept.js"></script>
</body>
</html>