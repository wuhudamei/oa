<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<span hidden>{{msg}}</span>
<template id="grossProfitTmpl">
    <div class="panel-group panel-stranding-book" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingMain">
                <table width="100%">
                    <tr>
                        <td>原始合同金额:&nbsp;{{orderAmount}}</td>
                        <td>现合同金额:&nbsp;{{getPayedAll().toFixed(2)}}</td>
                        <td>总成本:&nbsp;{{getTotalCost().toFixed(2)}}</td>
                        <td>毛利:&nbsp;{{getGross().toFixed(2)}}%</td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingEight">
                <span class="title">间接费用:&nbsp;{{getIndirectCost().toFixed(2)}}</span>
            </div>
            <div class="panel-body">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead align="center">
                    <td>税金</td>
                    <td>管理费</td>
                    <td>远程费</td>
                    <td>其他综合费</td>
                    <td>优惠其他费用</td>
                    </tr>
                    </thead>
                    <tbody align="center">
                    <tr>
                        <td>{{getTax().toFixed(2)}}</td>
                        <td>{{getManagerCost().toFixed(2)}}</td>
                        <td>{{getRemoteCost().toFixed(2)}}</td>
                        <td>{{getOtherCost().toFixed(2)}}</td>
                        <td>{{getOtherDiscountCost().toFixed(2)}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingNine">
                <span class="title">直接费用:&nbsp;{{getDirectCost().toFixed(2)}}</span>
            </div>
            <div class="panel-body">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead align="center">
                    <tr>
                        <td>拆改费</td>
                        <td>套餐报价</td>
                        <td>基装增项报价</td>
                        <td>基装减项报价</td>
                        <td>基装变更合计</td>
                        <td>主材增项、升级项报价</td>
                        <td>主材减项报价</td>
                    </tr>
                    </thead>
                    <tbody align="center">
                    <tr>
                        <td>{{modifyCost.toFixed(2)}}</td>
                        <td>{{selectedPackageAmount.toFixed(2)}}</td>
                        <td>{{getIncrease().toFixed(2)}}</td>
                        <td>{{getLessenquota().toFixed(2)}}</td>
                        <td>{{getInstallChange().toFixed(2)}}</td>
                        <td>{{getAddUpgrade().toFixed(2)}}</td>
                        <td>{{getLessen().toFixed(2)}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingTen">
                <span class="title">直接成本:&nbsp;{{getDirect().toFixed(2)}}</span>
            </div>
            <div class="panel-body">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead align="center">
                    <tr>
                        <td>基装成本</td>
                        <td>主材成本(标配+升级项+增项+变更)</td>
                    </tr>
                    </thead>
                    <tbody align="center">
                    <tr>
                        <td>{{getBaseCost().toFixed(2)}}</td>
                        <td>{{materialTotalAmount}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingEleven">
                <span class="title">间接成本:&nbsp;{{getIndirect().toFixed(2)}}</span>
            </div>
            <div class="panel-body">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr>
                        <td>客服部提成</td>
                        <td>设计师提成</td>
                        <td>设计部长提成</td>
                        <td>审计部提成</td>
                        <td>工程部大区文员提成</td>
                        <td>项目经理提成</td>
                        <td>工程部大区提成</td>
                        <td>材料部提成</td>
                        <td>质检部提成</td>
                        <td>总监及总经理提成</td>
                    </tr>
                    </thead>
                    <tbody align="center">
                    <tr>
                        <td>{{getCustomerServiceCommission().toFixed(2)}}</td>
                        <td>{{getDesignerDeduction().toFixed(2)}}</td>
                        <td>{{getDesignCommission().toFixed(2)}}</td>
                        <td>{{getAuditCommission().toFixed(2)}}</td>
                        <td>55.00</td>
                        <td>{{getSums().toFixed(2)}}</td>
                        <td>{{getProjectCommission().toFixed(2)}}</td>
                        <td>265.00</td>
                        <td>627.00</td>
                        <td>{{getGeneralManagerCommission().toFixed(2)}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>