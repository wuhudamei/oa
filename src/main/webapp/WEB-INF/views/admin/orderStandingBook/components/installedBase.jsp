<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="installedBaseTmpl">
    <style>
        .table-ded {
            display: none;
        }

        @media screen and (max-width: 500px) {
            .table-de {
                display: none;
            }

            .table-ded {
                display: block
            }
        }

        .fixed_headers thead tr {
            display: block;
            position: relative;
            overflow-y: scroll;
            display: -webkit-box;
            display: -webkit-flex;
            display: -ms-flexbox;
            display: flex;
        }
        .fixed_headers thead tr th {
            -webkit-box-flex: 1;
            -webkit-flex: 1;
            -ms-flex: 1;
            flex: 1;
            text-align: center;
            border-left: 0!important;
            border-bottom: 0!important;
        }
        .fixed_headers tbody {
            display: block;
            overflow-y: scroll;
            max-height: 300px;
        }

        .fixed_headers tbody tr {
            display: -webkit-box;
            display: -webkit-flex;
            display: -ms-flexbox;
            display: flex;
        }
        .fixed_headers tbody tr td {
            -webkit-box-flex: 1;
            -webkit-flex: 1;
            -ms-flex: 1;
            flex: 1;
            border-left: 0!important;
            border-right: 0 !important;
            border-bottom: 0!important;
        }

    </style>
    <div>
        <div class="panel-group">
            <div class="panel-heading border-bottom-style">
                <h4 class="panel-title">基装成本</h4>
            </div>
            <div class="panel panel-default">
                <table width="100%" class="table table-striped table-bordered  table-hover">
                    <thead>
                    <tr align="center">
                        <td>门店</td>
                        <td>任务包名称</td>
                        <td>任务包结算方式</td>
                        <td>工人组长名称</td>
                        <td>人工费</td>
                        <td>材料费</td>
                        <td>任务包造价</td>
                        <td>领用材料费</td>
                        <td>扣押质保</td>
                        <td>奖罚</td>
                        <td>结算金额</td>
                        <%--<td>结算金额(最终工人结算)</td>--%>
                        <%--<td>材料费用(网真)</td>--%>
                    </tr>
                    </thead>
                    <tbody align="center">
                    <tr align="center" v-for="item in packageInfo">
                        <td>{{item.storeId}}</td>
                        <td>{{item.packageName}}</td>
                        <td>{{item.settleStyle | goType}}</td>
                        <td>{{item.workerGroupName}}</td>
                        <td>{{item.laborSettleAmount}}</td>
                        <td>{{item.auxiliaryMaterialsSettleAmount}}</td>
                        <td>{{item.laborAuxiliaryMaterialsSettleAmount}}</td>
                        <td>{{item.auxiliaryAmountworker}}</td>
                        <td>{{item.guaranteeMoneyAmount}}</td>
                        <td>{{item.rewardPunishAmount}}</td>
                        <td>{{item.workerGroupSettleAmount}}</td>
                        <%--<td>{{item.settlementAmount}}</td>--%>
                        <%--<td>{{item.auxiliaryAmountWangzhen}}</td>--%>
                    </tr>
                    <tr v-if="packageInfo.length === 0" align="center">
                        <td colspan="7">
                            没有找到匹配的记录
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="panel-group">
            <div class="panel-heading border-bottom-style">
                <h4 class="panel-title">提成</h4>
            </div>
            <div class="panel panel-default">
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
        <div class="panel-group">
            <div class="panel-heading border-bottom-style">
                <h4 class="panel-title">基装主材</h4>
            </div>

            <div>
                <span hidden>{{msg}}</span>
                <div class="panel panel-default">
                    <table class="table table-striped table-bordered table-hover fixed_headers" >
                        <thead >
                        <tr align="center">
                            <th>供应商名称</th>
                            <th>商品类目</th>
                            <th>商品名称</th>
                            <th>用量</th>
                            <th>单价</th>
                            <th>计量单位</th>
                            <th>验收时间</th>
                        </tr>
                        </thead>
                        <tbody style="width:100%">
                        <tr align="center" v-for="item in installBasePrincipal">
                            <td>{{item.pdSupplier}}</td>
                            <td>{{item.auxiliaryMaterialflagName}}</td>
                            <td>{{item.pdSkuname}}</td>
                            <td>{{item.count}}</td>
                            <td>{{item.workerPrice}}</td>
                            <td>{{item.pdMeasureunit}}</td>
                            <td>{{item.acceptanceTime}}</td>
                        </tr>
                        <tr v-if="installBasePrincipal.length ===0" align="center">
                            <td colspan="7">
                                没有找到匹配的记录
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="panel-group">
            <div class="panel-heading border-bottom-style">
                <h4 class="panel-title">基装辅材</h4>
            </div>

            <div>
                <span hidden>{{msg}}</span>
                <div class="panel panel-default">
                    <table class="table table-striped table-bordered table-hover fixed_headers" >
                        <thead >
                        <tr align="center">
                            <th>供应商名称</th>
                            <th>商品类目</th>
                            <th>商品名称</th>
                            <th>用量</th>
                            <th>单价</th>
                            <th>计量单位</th>
                            <th>验收时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr align="center" v-for="item in installBaseAuxiliary">
                            <td>{{item.pdSupplier}}</td>
                            <td>{{item.auxiliaryMaterialflagName}}</td>
                            <td>{{item.pdSkuname}}</td>
                            <td>{{item.count}}</td>
                            <td>{{item.workerPrice}}</td>
                            <td>{{item.pdMeasureunit}}</td>
                            <td>{{item.acceptanceTime}}</td>
                        </tr>
                        <tr v-if="installBaseAuxiliary.length ===0" align="center">
                            <td colspan="7">
                                没有找到匹配的记录
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

</template>

