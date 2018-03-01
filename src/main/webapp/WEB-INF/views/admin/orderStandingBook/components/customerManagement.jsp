<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="customerManagementTmpl">
    <div class="panel-group panel-stranding-book" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingMain">
                <span hidden>{{msg}}</span>
                <table width="100%" style="color: red">
                    <tr>
                        <td>工单数量:&nbsp;{{workCount}}</td>
                        <td>已完成:&nbsp;{{completCount}}</td>
                        <td>未完成:&nbsp;{{noCompletCount}}</td>
                    </tr>
                </table>
            </div>
            <div class="panel-body">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr align="center">
                        <td>投诉来源</td>
                        <td>投诉时间</td>
                        <td>责任部门</td>
                        <td>责任部门接收时间</td>
                        <td>责任部门预计完成时间</td>
                        <td>事项分类</td>
                        <td>问题分类</td>
                        <td>工单来源</td>
                        <td>工单状态</td>
                        <td>工单完成时间</td>
                    </tr>
                    </thead>
                    <tbody align="center" v-for="item in customerManagement">
                    <tr>
                        <td>{{item.complaintType.name}}</td>
                        <td>{{item.createDate | goDate}}</td>
                        <td>{{item.liableDepartment.orgName}}</td>
                        <td>{{item.receptionTime | goDate}}</td>
                        <td>{{item.treamentTime | goDate}}</td>
                        <td>{{item.questionType1.name}}</td>
                        <td>{{item.questionType2.name}}</td>
                        <td>{{item.source.name}}</td>
                        <td>{{item.orderStatus | goType}}</td>
                        <td>{{item.operationDate| goDate}}</td>
                    </tr>
                    <tr v-if="customerManagement.length ==0" align="center">
                        <td colspan="9">
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