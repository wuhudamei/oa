<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="qualityTestingTmpl">
    <div class="panel-group panel-stranding-book" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingMain">
                <span hidden>{{msg}}</span>
                <table width="100%">
                    <tr>
                        <td>工程模式:{{check.label}}</td>
                        <td>工程区域:{{check.name}}</td>
                        <td>实际开工日期:{{check.actualStartDate | actualStartDate}}</td>
                    </tr>
                </table>
            </div>
            <div class="panel-body">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr align="center">
                        <td>质检报告编号</td>
                        <td>约检节点</td>
                        <td>项目经理</td>
                        <td>约检时间</td>
                        <td>质检员</td>
                        <td>验收通过时间</td>
                    </tr>
                    </thead>
                    <tbody align="center" v-for="item in qualityCheck">
                    <tr>
                        <td>{{item.qcBillCode}}</td>
                        <td>{{item.qcCheckNodeName}}</td>
                        <td>{{item.itemManager}}</td>
                        <td>{{item.createDate | goDate}}</td>
                        <td>{{item.realName}}</td>
                        <td>{{item.acceptCheckDatetime | goDate}}</td>
                    </tr>
                    <tr v-if="qualityCheck.length ===0" align="center">
                        <td colspan="6">
                            没有找到匹配的记录
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingEight">
                <span class="title">复检</span>
            </div>
            <div class="panel-body">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr align="center">
                        <td>质检报告编号</td>
                        <td>约检节点</td>
                        <td>项目经理</td>
                        <td>约检时间</td>
                        <td>质检员</td>
                        <td>验收通过时间</td>
                    </tr>
                    </thead>
                    <tbody align="center" v-for="item in repeatQualityCheck">
                    <tr>
                        <td>{{item.qcBillCode}}</td>
                        <td>{{item.qcCheckNodeName}}</td>
                        <td>{{item.itemManager}}</td>
                        <td>{{item.createDate | goDate}}</td>
                        <td>{{item.realName}}</td>
                        <td>{{item.acceptCheckDatetime | goDate}}</td>
                    </tr>
                    <tr v-if="repeatQualityCheck.length == 0" align="center">
                        <td colspan="6">
                            没有找到匹配的记录
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <%--<div class="panel panel-default">
            <div class="panel-heading" role="tab" id="heading">
                <span class="title">面积找平信息</span>
            </div>
            <div class="panel-body">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr align="center">
                        <td>任务包地砖铺贴预算面积</td>
                        <td>任务包地砖铺贴结算面积</td>
                        <td>选材地板面积(不含损耗面积)</td>
                        <td>木地板(地面找平施工)结算面积</td>
                        <td>计价面积</td>
                        <td>是否异常</td>
                    </tr>
                    </thead>
                    <tbody align="center">
                    <tr>
                        <td>{{areaLevelInfo.floorTileBudgetArea | doChange}}</td>
                        <td>{{areaLevelInfo.floorTileSettleArea | doChange}}</td>
                        <td>{{areaLevelInfo.dosage | doChange}}</td>
                        <td>{{areaLevelInfo.floorSettleArea | doChange}}</td>
                        <td>{{areaLevelInfo.measurehousearea | doChange}}</td>
                        <td>{{areaLevelInfo.areaStatus}}</td>
                    </tr>
                    <tr v-if="areaLevelInfo.length == 0" align="center">
                        <td colspan="6">
                            没有找到匹配的记录
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>--%>
    </div>
    <!--/.panel-group-->
</template>