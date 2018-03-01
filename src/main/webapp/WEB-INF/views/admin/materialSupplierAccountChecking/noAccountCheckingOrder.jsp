<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<title>未对账订单</title>
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
                                <label for="startTime" class="col-sm-5 control-label">材料供应商</label>
                                <div class="col-sm-5 no-padding">
                                    <select v-model="form.pdSupplierId"
                                            id="pdSupplierId"
                                            name="pdSupplierId"
                                            class="form-control"
                                            @change="form.brandname=''">
                                        <option value="">请选择</option>
                                        <option
                                                v-for="item of level1"
                                                :value="item.id"
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
                                <label for="startTime" class="col-sm-4 control-label">商品品牌</label>
                                <div class="col-sm-5 no-padding">
                                    <select v-model="form.brandname"
                                            id="brandname"
                                            name="brandname"
                                            class="form-control">
                                        <option value="">请选择</option>
                                        <option :value="item.text"
                                                v-for="item in brandnameArr"
                                                v-if="item.proid === form.pdSupplierId"
                                                placeholder="请选择品牌名称">{{item.text}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <div class="form-group" style="line-height: 34px"
                                 :class="{'has-error':($validation.starttime.invalid && $validation.touched)}">
                                <label for="startTime" class="col-sm-4 control-label">订单编号</label>
                                <div class="col-sm-5 no-padding">
                                    <input
                                            v-model="form.orderNo"
                                            id="orderNo"
                                            name="orderNo"
                                            type="text"
                                            placeholder="请输入订单编号" class="form-control"/>
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-5">
                            <div class="form-group" style="line-height: 34px"
                                 :class="{'has-error':($validation.starttime.invalid && $validation.touched)}">
                                <label for="startTime" class="col-sm-3 control-label">验收时间</label>
                                <div class="col-sm-4 no-padding">
                                    <input
                                            v-model="form.startTime"
                                            v-el:start-time
                                            id="startTime"
                                            name="startTime"
                                            type="text"
                                            class="form-control datepicker"
                                            placeholder="请选择开始时间">
                                </div>
                                <label for="endTime" class="control-label col-sm-1">~</label>
                                <div class="col-sm-4 no-padding">
                                    <input
                                            v-model="form.endTime"
                                            v-el:end-time
                                            id="endTime"
                                            name="endTime"
                                            type="text"
                                            class="form-control datepicker"
                                            placeholder="请选择结束时间">
                                </div>
                            </div>
                        </div>

                        <div class="col-md-1" >
                            <div class="form-group" >
                                <button id="searchBtn"  type="submit" class="btn btn-block btn-outline btn-default"
                                        alt="搜索"
                                        title="搜索">
                                    <i class="fa fa-search"></i>
                                </button>
                            </div>
                        </div>

                    </form>
                </div >
                <table v-el:dataTable id="dataTable" width="100%"
                       class="table table-striped table-bordered table-hover">
                </table>
                <%--<div>--%>
                    <%--<validator name="valid">--%>
                        <%--<table id="dataTable2" class="table table-bordered">--%>
                            <%--<tr id="titileTr">--%>
                                <%--<td style="text-align: center;font-weight:bold">订单编号</td>--%>
                                <%--<td style="text-align: center;font-weight:bold">客户姓名</td>--%>
                                <%--<td style="text-align: center;font-weight:bold">客户手机号码</td>--%>
                                <%--<td style="text-align: center;font-weight:bold">未对账商品个数</td>--%>
                            <%--</tr>--%>
                            <%--<tr v-for="stRdBilltem in stRdBilltems">--%>
                                <%--<td   style="text-align: center">{{stRdBilltem.orderNo}}</td>--%>
                                <%--<td  style="text-align: center">{{stRdBilltem.customerName}}</td>--%>
                                <%--<td  style="text-align: center">{{stRdBilltem.customerName}}</td>--%>
                                <%--<td  style="text-align: center">{{stRdBilltem.customerName}}</td>--%>
                            <%--</tr>--%>
                            <%--<tr v-if="stRdBilltems.length === 0">--%>
                                <%--<td class="text-center" colspan="11">没有找到匹配的记录</td>--%>
                            <%--</tr>--%>
                        <%--</table>--%>
                    <%--</validator>--%>
                <%--</div>--%>
            </div>
        </div>
    </div>
    <!-- ibox end -->
</div>




</div>
<script src="/static/vendor/decimal/decimal.min.js"></script>
<script src="/static/js/containers/materialSupplierAccountChecking/noAccountCheckingOrder.js"></script>
</body>
</html>