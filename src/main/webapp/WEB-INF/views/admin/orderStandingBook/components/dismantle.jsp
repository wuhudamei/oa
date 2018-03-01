<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="dismantleTmpl">
    <div>
        <div class="panel-group">
            <div class="panel panel-default">
                <div class="panel-heading border-bottom-style">
                    <h4 align="center"><span>老房拆除工程造价合计：{{getAll().toFixed(2)}}</span></h4>
                </div>
            </div>
        </div>

        <div class="panel-group">
            <div class="panel panel-default">
                <div class="panel-heading border-bottom-style">
                    <h4 align="center"><span>老房拆除基装定额：{{oldHouseBaseDemolition().toFixed(2)}}</span></h4>
                </div>
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr align="center">
                        <td>定额分类</td>
                        <td>定额名称</td>
                        <td>单位</td>
                        <td>数量</td>
                        <td>计价方式</td>
                        <td>单位或占比</td>
                        <td>合价</td>
                        <td>工艺、做法及说明</td>
                    </tr>
                    </thead>
                    <tbody align="center" v-for="item in installBase">
                    <tr>
                        <td>{{item.pdCategoryName}}</td>
                        <td>{{item.pdName}}</td>
                        <td>{{item.pdMeasureunit}}</td>
                        <td>{{item.addCount}}</td>
                        <td>{{item.quotaWay | goType}}</td>
                        <td v-if="item.quotaWay == 3 || item.quotaWay == 4 || item.quotaWay == 6 || item.quotaWay == 7 || item.quotaWay == 8">{{item.quotaScale | quotaScale}}</td>
                        <td v-if="item.quotaWay == 1">{{item.addUnitprice}}</td>
                        <td>{{item.addTotal}}</td>
                        <td>{{item.description}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="panel-group">
            <div class="panel panel-default">
                <div class="panel-heading border-bottom-style">
                    <h4 align="center"><span>老房拆除基装综合服务:{{oldHouseBaseServiceDemolition().toFixed(2)}}</span></h4>
                </div>
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr align="center">
                        <td>定额分类</td>
                        <td>定额名称</td>
                        <td>单位</td>
                        <td>数量</td>
                        <td>计价方式</td>
                        <td>单价或占比</td>
                        <td>合价</td>
                        <td>工艺、做法及说明</td>
                    </tr>
                    </thead>
                    <tbody align="center" v-for="item in installBaseService">
                    <tr>
                        <td>{{item.pdCategoryName}}</td>
                        <td>{{item.pdName}}</td>
                        <td>{{item.pdMeasureunit}}</td>
                        <td>{{item.addCount}}</td>
                        <td>{{item.quotaWay | goType}}</td>
                        <td v-if="item.quotaWay == 3 || item.quotaWay == 4 || item.quotaWay == 6 || item.quotaWay == 7 || item.quotaWay == 8">{{item.quotaScale | quotaScale}}</td>
                        <td v-if="item.quotaWay == 1">{{item.addUnitprice}}</td>
                        <td>{{item.addTotal}}</td>
                        <td>{{item.description}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="panel-group">
            <div class="panel panel-default">
                <div class="panel-heading border-bottom-style">
                    <h4 align="center"><span>老房拆除其他综合服务:{{oldHouseBaseOtherDemolition().toFixed(2)}}</span></h4>
                </div>
                <span hidden>{{msg}}</span>
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr align="center">
                        <td>定额分类</td>
                        <td>定额名称</td>
                        <td>单位</td>
                        <td>数量</td>
                        <td>计价方式</td>
                        <td>单价或占比</td>
                        <td>合价</td>
                        <td>工艺、做法及说明</td>
                    </tr>
                    </thead>
                    <tbody align="center" v-for="item in installBaseChange">
                    <tr>
                        <td>{{item.pdCategoryName}}</td>
                        <td>{{item.pdName}}</td>
                        <td>{{item.pdMeasureunit}}</td>
                        <td>{{item.addCount}}</td>
                        <td>{{item.quotaWay | goType}}</td>
                        <td v-if="item.quotaWay == 3 || item.quotaWay == 4 || item.quotaWay == 6 || item.quotaWay == 7 || item.quotaWay == 8">{{item.quotaScale | quotaScale}}</td>
                        <td v-if="item.quotaWay == 1">{{item.addUnitprice}}</td>
                        <td>{{item.addTotal}}</td>
                        <td>{{item.description}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
</template>