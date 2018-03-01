<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="materialInstallationTmpl">
    <div class="panel-group panel-stranding-book" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingMain">
                <span hidden>{{msg}}</span>
                <h4 class="panel-title" align="center">
                    <span class="title">安装信息</span>
                </h4>
            </div>
            <div class="panel-body">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr align="center">
                        <td>安装项名称</td>
                        <td>安装项状态</td>
                        <td>项目经理申请时间</td>
                        <td>期望进场日期</td>
                        <td>实际进场日期</td>
                        <td>实际完成日期</td>
                        <td>实际验收日期</td>
                        <td>是否延期</td>
                        <td>延期天数</td>
                    </tr>
                    </thead>
                    <tbody align="center" v-for="item in principalInstall">
                    <tr>
                        <td>{{item.installItemName}}</td>
                        <td>{{item.label}}</td>
                        <td>{{item.applyIntoCreateDatetime | goDate}}</td>
                        <td>{{item.applyIntoDate | goDate}}</td>
                        <td>{{item.realIntoDate | goDate}}</td>
                        <td>{{item.realCompleteDate | goDate}}</td>
                        <td>{{item.realAcceptDate | goDate}}</td>
                        <td>{{item.isCompleteDelay}}</td>
                        <td>{{item.completeDelayDays}}</td>
                    </tr>
                    <tr v-if="principalInstall.length ===0" align="center">
                        <td colspan="9">
                            没有找到匹配的记录
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingEight">
                <h4 class="panel-title" align="center">
                    <span class="title">复尺信息</span>
                </h4>
            </div>
            <div class="panel-body">
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr align="center">
                        <td>申请复尺时间</td>
                        <td>复尺内容</td>
                        <td>供应商复尺日期</td>
                        <td>复尺状态</td>
                    </tr>
                    </thead>
                    <tbody align="center" v-for="item in review">
                    <tr>
                        <td>{{item.createTime | goDate}}</td>
                        <td>{{item.name}}</td>
                        <td>{{item.noticeTime | goDate}}</td>
                        <td>{{item.reviewStatus | reviewStatus}}</td>
                    </tr>
                    <tr v-if="review.length ===0" align="center">
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