<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="saleTmpl">
    <style>
        .table-ded{display: none;}
        .td-light tbody tr:last-child{ color: red}
        @media screen and (max-width: 500px) {
           .table-de{ display: none;}
           .table-ded{ display: block}
        }

    </style>
    <div>
        <span hidden>{{msg}}</span>
        <div class="panel-group">
            <div class="panel panel-default">
                <span hidden>{{msg}}</span>
                <div :class="{hasHeight:isShow }" style="margin-left: 10px" class="row detail-stranding-book detail-state" v-cloak>
                    <div class="col-sm-4 col-xs-12">
                        <label for="">客服姓名：</label>
                        {{sale.serviceName}}({{sale.orgCode}})
                    </div>
                    <div class="col-sm-4 col-xs-12">
                        <label for="">客服手机号：</label>
                        {{sale.serviceMobile}}
                    </div>
                    <div class="col-sm-4 col-xs-12">
                        <label for="">客户来源：</label>
                        {{sale.origin | goOrigin}}
                    </div>
                    <div class="col-sm-4 col-xs-12">
                        <label for="">客户预期装修金额：</label>
                        {{sale.budgetAmount}}
                    </div>
                    <div class="col-sm-4 col-xs-12">
                        <label for="">计划量房时间：</label>
                        {{sale.reMeasureCreateTime | goDate}}
                    </div>
                    <div class="col-sm-4 col-xs-12">
                        <label for="">大定标准：</label>
                        10000
                    </div>
                    <div class="col-sm-4 col-xs-12">
                        <label for="">实收定金金额：</label>
                        {{sale.imprestAmount}}
                    </div>
                    <div class="col-sm-4 col-xs-12">
                        <label for="">是否已签预定合同：</label>
                        {{sale.isImprestAmount | goIsImprestAmount}}
                    </div>
                    <div class="col-sm-4 col-xs-12">
                        <label for="">合同中是否有可退字样：</label>
                        {{sale.haveNoBackTag | goIsHaveNoBackTag}}
                    </div>
                    <div class="col-sm-4 col-xs-12">
                        <label for="">大定类型：</label>
                        {{sale.bigType}}
                    </div>

                </div>

            </div>
        </div>


        <div class="panel-group table-responsive">

                <table width="100%" class="table table-striped table-bordered table-hover table-de panel-heading border-bottom-style">
                    <thead>
                    <tr align="center">
                        <td>交款名称</td>
                        <td>收据号</td>
                        <td>交款时间</td>
                        <td>交款方式</td>
                        <td>交款金额</td>
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

                <%--div class="bootstrap-table table-ded" style=" padding:10px">
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
                </div>--%>


            </div>

    </div>
</template>