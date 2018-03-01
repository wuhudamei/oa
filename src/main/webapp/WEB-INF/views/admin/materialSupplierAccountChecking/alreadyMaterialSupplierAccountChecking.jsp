<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<title>已对账</title>
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
                                <label for="startTime" class="col-sm-4 control-label">商品品牌</label>
                                <div class="col-sm-5 no-padding">
                                    <select v-model="form.brandname"
                                            id="brandname"
                                            name="brandname"
                                            class="form-control">
                                        <option value="">请选择</option>
                                        <option :value="item.text"
                                                v-for="item in skunameArr"
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
                                <label for="startTime" class="col-sm-3 control-label">对账时间</label>
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
                                <button id="searchBtn" @click="query" type="button" class="btn btn-block btn-outline btn-default"
                                        alt="搜索"
                                        title="搜索">
                                    <i class="fa fa-search"></i>
                                </button>
                            </div>
                        </div>

                    </form>
                </div>
                <div>
                    <div style="color: red;float: right;padding-bottom:10px;">对账总金额：{{totalPrice.toFixed(2)}}（元）</div>
                    <table v-el:dataTable id="dataTable" width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
                <!-- <div class="columns columns-right btn-group pull-right"></div> -->
                <%--<div>--%>
                    <%--<validator name="valid">--%>
                        <%--<table id="dataTable" class="table table-bordered">--%>
                            <%--<tr id="titileTr">--%>
                                <%--<td style="text-align: center;font-weight:bold">供应商</td>--%>
                                <%--<td style="text-align: center;font-weight:bold">批次</td>--%>
                                <%--<td style="text-align: center;font-weight:bold">批次说明</td>--%>
                                <%--<td style="text-align: center;font-weight:bold">对账时间</td>--%>
                                <%--<td style="text-align: center;font-weight:bold">对账金额</td>--%>
                            <%--</tr>--%>
                            <%--<tr v-for="stRdBillBatch in stRdBillBatchs">--%>
                                <%--<td  style="text-align: center">{{stRdBillBatch.supplier}}</td>--%>
                                <%--<td  style="text-align: center"><a href="javascript:void(0)" @click="showModel(stRdBillBatch) ">--%>
                                    <%--{{stRdBillBatch.billBatch}}</a>--%>
                                <%--</td>--%>
                                <%--<td style="text-align: center">{{stRdBillBatch.batchExplain}}</td>--%>
                                <%--<td style="text-align: center">{{stRdBillBatch.operateTime | moment-date}}</td>--%>
                                <%--<td style="text-align: center">{{stRdBillBatch.billAmountMoney}}</td>--%>
                            <%--</tr>--%>
                            <%--<tr v-if="stRdBilltems.length === 0">--%>
                                <%--<td class="text-center" colspan="11">没有找到匹配的记录</td>--%>
                            <%--</tr>--%>
                        <%--</table>--%>
                    <%--</validator>--%>
                <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
            <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
        <%--</div>--%>
    </div>
    <!-- ibox end -->
</div>

<div id="modal" class="modal fade" tabindex="-1" data-width="760">
    <div class="wrapper wrapper-content">
        <div class="ibox-content">
            <form name="createMirror" novalidate class="form-horizontal" role="form" style="margin-right:10px">
                <div class="text-center">
                    <h3>批次详情</h3>
                </div>
                <hr/>
                <div>
                    <validator name="valid">
                        <table id="dataTable3" class="table table-bordered">
                            <tr id="titileTr2">
                                <td style="text-align: center;font-weight:bold">客户姓名</td>
                                <td style="text-align: center;font-weight:bold">订单编号</td>
                                <td style="text-align: center;font-weight:bold">商品名称</td>
                                <td style="text-align: center;font-weight:bold">单价</td>
                                <td style="text-align: center;font-weight:bold">规格</td>
                                <td style="text-align: center;font-weight:bold">对账数量</td>
                                <td style="text-align: center;font-weight:bold">对账金额</td>
                            </tr>
                            <tr v-for="noticeboard in noticeboards">
                                <td  style="text-align: center">{{noticeboard.customerName}}</td>
                                <td  style="text-align: center">{{noticeboard.orderNo}}</td>
                                <td style="text-align: center">{{noticeboard.pdSkuname}}</td>
                                <td style="text-align: center">{{noticeboard.pdPrice}}</td>
                                <td style="text-align: center">{{noticeboard.pdModel}}</td>
                                <td style="text-align: center">{{noticeboard.billNumber}}</td>
                                <td style="text-align: center">{{noticeboard.billAmountMoney}}</td>
                            </tr>
                            <tr v-if="stRdBilltems.length === 0">
                                <td class="text-center" colspan="11">没有找到匹配的记录</td>
                            </tr>
                        </table>
                    </validator>
                </div>
            </form>

            <!-- ibox end -->
        </div>
        <div class="modal-footer">
            <button @click="cancel" type="button" data-dismiss="modal" class="btn">关闭</button>
        </div>
    </div>
</div>

</div>
<script src="/static/js/containers/materialSupplierAccountChecking/alreadyMaterialSupplierAccountChecking.js"></script>
</body>
</html>