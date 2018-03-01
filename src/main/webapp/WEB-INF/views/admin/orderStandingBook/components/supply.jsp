<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="supplyTmpl">
    <div v-if="supply.length ===0" style="margin-left: 40%;color: red;">
        没有找到匹配的记录
    </div>
    <div class="panel panel-default">
        <template v-for="list in supply ">
            <div class="panel-heading" role="tab" id="headingOne" style="margin-top: 5px;">
                <h4 class="panel-title">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       aria-expanded="true" aria-controls="{{$index}}" id="{{$index}}aId" href="#" @click="chooseSpan($index)">
                        <span>批次编号：{{list.branchNo}}</span>
                        <span style="margin-left: 10%;">下单时间：{{list.creatTime | goDate}}</span>
                        <span style="margin-left: 15%;">制单类型：{{list.placeStatus | placeStatus}}</span>
                        <span style="margin-left: 20%;">下单人：{{list.createUser}}</span>
                    </a>
                </h4>
            </div>
            <div id="{{$index}}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="{{$index}}">
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr align="center">
                            <td>商品明细</td>
                            <td>型号</td>
                            <td>规格</td>
                            <td>属性</td>
                            <td>订货数量</td>
                            <td>安装位置</td>
                            <td>通知安装时间</td>
                            <td>安装时间</td>
                        </tr>
                        </thead>
                        <tbody align="center"  v-for="items in list.supplyList">
                        <tr>
                            <td>{{items.skuName}}</td>
                            <td>{{items.model}}</td>
                            <td>{{items.spec}}</td>
                            <td>{{items.attribute1}}{{items.attribute2}}{{items.attribute3}}</td>
                            <td>{{items.quantity}}</td>
                            <td>{{items.installationLocation}}</td>
                            <td>{{items.noticeInstallDate}}</td>
                            <td>{{items.actualInstallDate}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </template>
    </div>
</template>