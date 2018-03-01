<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="changeTmpl">
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
    <div class="panel panel-default">
        <div class="panel-heading" role="tab" id="headingMain">
            <span hidden>{{msg}}</span>
            <h4 class="panel-title" align="center">
                <span class="title">变更信息</span>
            </h4>
        </div>
        <div class="panel-body">
            <table width="100%" class="table table-striped table-bordered table-hover">
                <thead>
                <tr align="center">
                    <td>主材变更次数</td>
                    <td>主材变更金额</td>
                    <td>基装变更次数</td>
                    <td>基装变更金额</td>
                    <td>变更总次数</td>
                    <td>变更总金额</td>
                </tr>
                </thead>
                <tbody align="center">
                <tr>
                    <td>{{count}}</td>
                    <td>{{mainMaterialChangeTotalAmount}}</td>
                    <td>{{installBaseCount}}</td>
                    <td>{{mainInstallBaseChangeTotalAmount}}</td>
                    <td>{{totalCount}}</td>
                    <td>{{totalChangeMoney}}</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading border-bottom-style">
            <h4 class="panel-title" align="center">主材变更</h4>
        </div>
        <template v-for="list in changes ">
            <div class="panel-heading" role="tab" id="headingOne" style="margin-top: 5px;">
                <h4 class="panel-title">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       aria-expanded="true" aria-controls="{{$index}}" id="{{$index}}aId" href="#" @click="chooseSpan($index)">
                        <span>变更编号：{{list.changeNo}}</span>
                        <span style="margin-left: 20%;">变更发起人：{{list.createUser}}</span>
                        <span style="margin-left: 20%;">变更时间：{{list.changeDate | goDate}}</span>
                    </a>
                </h4>
            </div>
            <div id="{{$index}}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="{{$index}}">
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr align="center">
                            <td>套餐项</td>
                            <td>增减项</td>
                            <td>商品类目</td>
                            <td>名称</td>
                            <td>品牌</td>
                            <td>型号</td>
                            <td>属性</td>
                            <td>原用量</td>
                            <td>现用量</td>
                            <td>单价</td>
                        </tr>
                        </thead>
                        <tbody align="center" v-for="item in list.changeStandBookList">
                        <tr>
                            <td>{{item.itemType | goItemType}}</td>
                            <td>{{item.incrementItem | incrementItem}}</td>
                            <td>{{item.flagname}}</td>
                            <td>{{item.pdName}}</td>
                            <td>{{item.pdBrandname }}</td>
                            <td>{{item.pdModel }}</td>
                            <td>{{item.pdSkuSalesattribute}}</td>
                            <td>{{item.pdCount}}</td>
                            <td>{{item.nowPdCount}}</td>
                            <td>{{item.pdPrice}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </template>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading border-bottom-style">
            <h4 class="panel-title" align="center">基装变更</h4>
        </div>
        <template v-for="list in installBaseChanges ">
            <div class="panel-heading" role="tab" id="headingTwo" style="margin-top: 5px;">
                <h4 class="panel-title">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       aria-expanded="true" aria-controls="{{$index + 100}}" id="{{$index + 100}}bId" href="#" @click="choose($index + 100)">
                        <span>变更编号：{{list.constructionChangeNo}}</span>
                        <span style="margin-left: 20%;">变更时间：{{list.changeApplyDate | goChangeDate}}</span>
                    </a>
                </h4>
            </div>
            <div id="{{$index + 100}}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="{{$index + 100}}">
                <div class="panel-body">
                    <table width="100%" class="table table-striped table-bordered table-hover">
                        <thead>
                        <tr align="center">
                            <td>类型</td>
                            <td>项目名称</td>
                            <td>单位</td>
                            <td>数量</td>
                            <td>损耗</td>
                            <td>人工费</td>
                            <td>综合单价</td>
                            <td>总价</td>
                            <td>说明</td>
                        </tr>
                        </thead>
                        <tbody align="center" v-for="item in list.installBaseChangeStandBookList">
                        <tr>
                            <td>{{item.changeType | goChangeType}}</td>
                            <td>{{item.changeProjectName}}</td>
                            <td>{{item.unit}}</td>
                            <td>{{item.amount}}</td>
                            <td>{{item.loss }}</td>
                            <td>{{item.laborCosts }}</td>
                            <td>{{item.totalUnitPrice}}</td>
                            <td>{{item.unitProjectTotalPrice}}</td>
                            <td>{{item.explain}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </template>
    </div>
</template>