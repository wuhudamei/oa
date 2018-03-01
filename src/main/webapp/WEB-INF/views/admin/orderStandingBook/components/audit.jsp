<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="auditTmpl">
    <div class="panel-group panel-stranding-book" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingMain">
                <span hidden>{{msg}}</span>
                <h4 class="panel-title" align="center">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       aria-expanded="true" aria-controls="auditcall" href="#auditcall">
                    <span class="title">合同审计</span>
                    </a>
                </h4>
            </div>
            <div id="auditcall" class="panel-collapse collapse" role="tabpanel" aria-labelledby="auditcall">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <span style="margin-left: 10px">审计人：{{contractAudit.auditorRealname}}</span>
                    <thead>
                    <tr align="center">
                        <td>发生时间</td>
                        <td>审计事项</td>
                    </tr>
                    </thead>
                    <tbody align="center">
                    <tr>
                        <td>{{contractAudit.submitAuditTime | goDate}}</td>
                        <td>首次提交审计时间</td>
                    </tr>
                    <tr>
                        <td>{{contractAudit.assignAuditTime | goDate}}</td>
                        <td>首次分配审计员时间</td>
                    </tr>
                    <tr>
                        <td>{{contractAudit.auditTime | goDate}}</td>
                        <td>审计通过时间</td>
                    </tr>
                    <tr>
                        <td>{{contractAudit.submitOrderTime | goDate}}</td>
                        <td>转单完成时间</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingEight">
                <h4 class="panel-title" align="center">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       aria-expanded="true" aria-controls="audit" href="#audit">
                    <span class="title">变更审计</span>
                    </a>
                </h4>
            </div>
            <div id="audit" class="panel-collapse collapse" role="tabpanel" aria-labelledby="audit">
                <div style="margin-bottom: 5px;">
                    <template v-for="list in changeAudit">
                        <table width="100%" class="table table-striped table-bordered table-hover">
                            <span style="margin-left: 10px">变更编号：{{list.changeNo}}</span>&nbsp;&nbsp;
                            <span style="margin-left: 10px">审计人：{{list.auditorRealname}}</span>
                            <thead align="center">
                                <tr>
                                    <td>发生时间</td>
                                    <td>审计事项</td>
                                </tr>
                            </thead>
                            <tbody align="center">
                            <tr>
                                <td>{{list.submitMaterialTime | goDate}}</td>
                                <td>提交材料审核</td>
                            </tr>
                            <tr>
                                <td>{{list.changeSubmitAuditTime | goDate}}</td>
                                <td>提交审计</td>
                            </tr>
                            <tr>
                                <td>{{list.changeAuditTime | goDate}}</td>
                                <td>审计时间</td>
                            </tr>
                            </tbody>
                        </table>
                    </template>
                </div>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading" role="tab">
                <h4 class="panel-title" align="center">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       aria-expanded="true" aria-controls="areaLevelInfo" href="#areaLevelInfo">
                        <span class="title">面积找平信息</span>
                    </a>
                </h4>
            </div>
            <div id="areaLevelInfo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="areaLevelInfo">
                <div style="margin-bottom: 5px;">
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
            </div>
        </div>
    </div>
    <!--/.panel-group-->
</template>