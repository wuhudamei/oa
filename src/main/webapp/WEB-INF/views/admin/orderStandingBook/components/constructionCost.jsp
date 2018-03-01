<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="constructionCostTmpl">
    <div class="panel-group panel-stranding-book" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingMain">
                <span hidden>{{msg}}</span>
                <h4 class="panel-title" align="center">
                    <span class="title">项目经理结算</span>
                </h4>
            </div>
            <div class="panel-body">
                <table width="100%" class="table table-striped table-bordered  table-hover">
                    <thead>
                    <tr align="center">
                        <td>项目经理姓名</td>
                        <td>结算周期</td>
                        <td>生成月度结算单时间</td>
                        <td>结算金额</td>
                    </tr>
                    </thead>
                    <tbody align="center">
                    <tr align="center" v-for="item in pmSettleInfo">
                        <td>{{item.itemManager}}</td>
                        <td>{{item.settleBillType | billType}}</td>
                        <td>{{item.createMonthlyDate}}</td>
                        <td>{{item.settleAmount}}</td>
                    </tr>
                    <tr v-if="pmSettleInfo.length ===0" align="center">
                        <td colspan="4">
                            没有找到匹配的记录
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <!--/.panel-group-->
</template>