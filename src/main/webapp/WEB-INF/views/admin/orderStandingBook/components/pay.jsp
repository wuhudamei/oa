<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="payTmpl">
    <style>
        .table-ded {
            display: none;
        }

        .td-light tbody tr:last-child {
            color: red
        }

        @media screen and (max-width: 500px) {
            .table-de {
                display: none;
            }

            .table-ded {
                display: block
            }
        }

    </style>
    <div>
        <div class="panel-group">
            <div class="panel panel-default">
                <div class="panel-heading border-bottom-style">
                    <h4 class="panel-title">交费详情</h4>
                </div>
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr align="center">
                        <td>暂收款</td>
                        <td>预收款</td>
                        <td>营业收入</td>
                        <td>应收款</td>
                    </tr>
                    </thead>
                    <tbody align="center">
                    <tr>
                        <td>{{noMortgage.toFixed(2)}}</td>
                        <td>{{prePay().toFixed(2)}}</td>
                        <td>{{nowOrderAmount.toFixed(2)}}</td>
                        <td>{{receivable().toFixed(2)}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="panel-group">
            <div class="panel panel-default">
                <div class="panel-heading border-bottom-style">
                    <h4 class="panel-title">定金信息</h4>
                </div>
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr align="center">
                        <td>已交</td>
                        <td>未抵</td>
                        <td>已抵</td>
                    </tr>
                    </thead>
                    <tbody align="center">
                    <tr>
                        <td>{{payList.imprestAmount.toFixed(2)}}</td>
                        <td>{{mortgage().toFixed(2)}}</td>
                        <td>{{payList.totalImprestAmountDeductible.toFixed(2)}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="panel-group">
            <div class="panel panel-default">
                <div class="panel-heading border-bottom-style">
                    <h4 class="panel-title">缴费台帐</h4>
                </div>
                <span hidden>{{msg}}</span>
                <table width="100%" class="table table-striped table-bordered table-hover td-light">
                    <thead>
                    <tr align="center">
                        <td>期数</td>
                        <td>已缴</td>
                        <td>未缴</td>
                    </tr>
                    </thead>
                    <tbody align="center">
                    <tr v-show="isShow(payList.modifyCost)">
                        <td>拆改费</td>
                        <td>{{payList.modifyCost.toFixed(2)}}</td>
                        <td>{{noNum.toFixed(2)}}</td>
                    </tr>

                    <tr v-show="isShow(payList.firstAmount)">
                        <td>首期</td>
                        <td>{{payList.firstAmount.toFixed(2)}}</td>
                        <td>{{unpaid().toFixed(2)}}</td>
                    </tr>
                    <tr v-show="isShow(payList.mediumAmount)">
                        <td>中期</td>
                        <td>{{payList.mediumAmount.toFixed(2)}}</td>
                        <td>{{model().toFixed(2)}}</td>
                    </tr>
                    <tr v-show="isShow(payList.finalAmount)">
                        <td>尾款</td>
                        <td>{{payList.finalAmount}}</td>
                        <td>{{lastPay().toFixed(2)}}</td>
                    </tr>
                    <tr v-show="isShow(payList.finishChangeAmount)">
                        <td>尾款后变更款</td>
                        <td>{{payList.finishChangeAmount.toFixed(2)}}</td>
                        <td>{{noNum.toFixed(2)}}</td>
                    </tr>

                    <tr>
                        <td>总额</td>
                        <td>{{getAll().toFixed(2)}}</td>
                        <td>{{al().toFixed(2)}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="panel-group table-responsive">
            <div class="panel panel-default">
                <div class="panel-heading border-bottom-style">
                    <h4 class="panel-title">详情</h4>
                </div>
                <table width="100%" class="table table-striped table-bordered table-hover table-de">
                    <thead>
                    <tr align="center">
                        <td>期数</td>
                        <td>收据号</td>
                        <td>缴款时间</td>
                        <td>缴款方式</td>
                        <td>缴款金额</td>
                    </tr>
                    </thead>
                    <tbody>
                    <tr align="center" v-for="item in payDetails">
                        <td>{{item.financeType | goType}}</td>
                        <td>{{item.receiptNumber}}</td>
                        <td>{{item.financeTime | goDate}}</td>
                        <td>{{item.payment}}</td>
                        <td>{{item.received}}</td>
                    </tr>
                    </tbody>
                </table>

                <div class="bootstrap-table table-ded" style=" padding:10px">
                    <div class="fixed-table-container">
                        <div class="fixed-table-body">
                            <table width="100%" class="table table-striped table-bordered table-hover"
                                   style="margin-top: 0px;">
                                <tbody>
                                <tr v-for="item in payDetails">
                                    <td colspan="7">
                                        <div class="card-views">
                                            <div class="card-view">
                                                <span class="title" style="text-align: center; ">期数</span>
                                                <span class="value">{{item.financeType}}</span>
                                            </div>
                                        </div>
                                        <div class="card-views">
                                            <div class="card-view">
                                                <span class="title" style="text-align: center; ">收据号</span>
                                                <span class="value">{{item.receiptNumber}}</span>
                                            </div>
                                        </div>
                                        <div class="card-views">
                                            <div class="card-view">
                                                <span class="title" style="text-align: center; ">缴款人姓名</span>
                                                <span class="value">{{item.payName}}</span>
                                            </div>
                                        </div>
                                        <div class="card-views">
                                            <div class="card-view">
                                                <span class="title" style="text-align: center; ">缴款人电话</span>
                                                <span class="value">{{item.payMobile}}</span>
                                            </div>
                                        </div>
                                        <div class="card-views">
                                            <div class="card-view">
                                                <span class="title" style="text-align: center; ">缴款时间</span>
                                                <span class="value">{{item.financeTime | goDate}}</span>
                                            </div>
                                        </div>
                                        <div class="card-views">
                                            <div class="card-view">
                                                <span class="title" style="text-align: center; ">缴款方式</span>
                                                <span class="value">{{item.payment}}</span>
                                            </div>
                                        </div>
                                        <div class="card-views">
                                            <div class="card-view">
                                                <span class="title" style="text-align: center; ">缴款金额</span>
                                                <span class="value">{{item.received}}</span>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>


            </div>
        </div>

    </div>
</template>