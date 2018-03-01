<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<title>项目毛利率预测/项目收付款预测</title>
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

                        <div class="col-sm-6">
                            <div class="form-group" style="line-height: 34px"
                                 :class="{'has-error':($validation.starttime.invalid && $validation.touched)}">
                                <label for="startDate" class="col-sm-3 control-label">关键字</label>
                                <div class="col-sm-9 no-padding">
                                    <input
                                            v-model="form.keyword"
                                            id="keyword"
                                            name="keyword"
                                            type="text"
                                            placeholder="订单编号/客户姓名/客户手机号" class="form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group" style="line-height: 34px"
                                 :class="{'has-error':($validation.starttime.invalid && $validation.touched)}">
                                <label for="startDate" class="col-sm-3 control-label">预计开工时间</label>
                                <div class="col-sm-4 no-padding">
                                    <input
                                            v-model="form.startDate"
                                            v-el:start-date
                                            id="startDate"
                                            name="startDate"
                                            type="text"
                                            class="form-control datepicker"
                                            placeholder="请选择时间">
                                </div>
                                <label for="endDate" class="control-label col-sm-1">~</label>
                                <div class="col-sm-4 no-padding">
                                    <input
                                            v-model="form.endDate"
                                            v-el:end-date
                                            id="endDate"
                                            name="endDate"
                                            type="text"
                                            class="form-control datepicker"
                                            placeholder="请选择时间">
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-6">
                            <div class="form-group" style="line-height: 34px"
                                 :class="{'has-error':($validation.starttime.invalid && $validation.touched)}">
                                <label for="startTime" class="col-sm-3 control-label">预计完工时间</label>
                                <div class="col-sm-4 no-padding">
                                    <input
                                            v-model="form.startTime"
                                            v-el:start-time
                                            id="startTime"
                                            name="startTime"
                                            type="text"
                                            class="form-control datepicker"
                                            placeholder="请选择时间">
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
                                            placeholder="请选择时间">
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
                <div style=" width: 100%; overflow-x: scroll;">
                    <table id="dataTable" class="table-bordered" style=" width: 2000px;">
                        <tr id="titileTr">
                            <td style="text-align: center;font-weight:bold;">项目编号</td>
                            <td style="text-align: center;font-weight:bold;">客户姓名</td>
                            <td style="text-align: center;font-weight:bold;">面积</td>
                            <td style="text-align: center;font-weight:bold;">装修地址</td>
                            <td style="text-align: center;font-weight:bold;">预计开工日期</td>
                            <td style="text-align: center;font-weight:bold;">预计完工日期</td>
                            <td style="text-align: center;font-weight:bold">合同总金额</td>
                            <td style="text-align: center;font-weight:bold">拆改（金额）</td>
                            <td style="text-align: center;font-weight:bold">首期（金额）</td>
                            <td style="text-align: center;font-weight:bold">二期（金额）</td>
                            <td style="text-align: center;font-weight:bold">三期（金额）</td>
                            <td style="text-align: center;font-weight:bold">尾款（金额）</td>
                            <td style="text-align: center;font-weight:bold">预计收款合计</td>
                            <td style="text-align: center;font-weight:bold">预计最高成本</td>
                            <td style="text-align: center;font-weight:bold">拆改（成本）</td>
                            <td style="text-align: center;font-weight:bold">材料费</td>
                            <td style="text-align: center;font-weight:bold">施工费</td>
                            <td style="text-align: center;font-weight:bold">间接费</td>
                            <td style="text-align: center;font-weight:bold">预计最低毛利率</td>
                        </tr>
                       <tr v-for="budget in budgets">
                            <td style="text-align: center; width: 100px;">{{budget.orderNo}}</td>
                            <td style="text-align: center">{{budget.customerName}}</td>
                            <td style="text-align: center">{{budget.houseSpace}}</td>
                            <td style="text-align: center">{{budget.houseAddress}}</td>
                            <td style="text-align: center">{{budget.predictStartTime | goDate}}</td>
                            <td style="text-align: center">{{budget.predictEndTime | goDate}}</td>
                            <td style="text-align: center">{{budget.contractTotalMoney}}</td>
                            <td style="text-align: center">{{budget.dismantleAlterMoney}}</td>
                            <td style="text-align: center">{{budget.downPaymentMoney}}</td>
                            <td style="text-align: center">{{budget.theSecondPhaseMoney}}</td>
                            <td style="text-align: center">{{budget.threeStagesMoney}}</td>
                            <td style="text-align: center">{{budget.finalPaymentMoney}}</td>
                            <td style="text-align: center">{{budget.predictReceiptMoney}}</td>
                            <td style="text-align: center">{{budget.predictTheHighestCost}}</td>
                            <td style="text-align: center">{{budget.dismantleAlterCost}}</td>
                            <td style="text-align: center">{{budget.materialsExpenses}}</td>
                            <td style="text-align: center">{{budget.constructionFee}}</td>
                            <td style="text-align: center">{{budget.indirectCharges}}</td>
                            <td style="text-align: center">{{budget.predictMinGrossProfitRate}}</td>
                        </tr>
                        <tr v-if="stRdBilltems.length === 0">
                            <td class="text-center" colspan="11">没有找到匹配的记录</td>
                        </tr>
                    </table>
                </div>
    </div>
    <!-- ibox end -->
</div>


</div>
<script src="/static/js/containers/materialSupplierAccountChecking/budget.js"></script>
</body>
</html>