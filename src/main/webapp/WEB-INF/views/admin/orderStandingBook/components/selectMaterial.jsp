<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="selectMaterialTmpl">
    <div class="panel-group panel-stranding-book" id="accordion" role="tablist" aria-multiselectable="true">
        <span hidden>{{msg}}</span>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingOne">
                <h4 class="panel-title">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                        <span class="title">套餐标配</span> <br/> 总计：{{total1}}（项） 总金额：{{totalStandardAmount | toDub }}
                    </a>
                </h4>
            </div>
            <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                <div class="panel-body">
                    <table v-el:data-table-standard width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingTwo">
                <h4 class="panel-title">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                        <span class="title">升级项</span> <br> 总计：{{total2}}（项） 总金额：{{totalUpdateAmount}}
                    </a>
                </h4>
            </div>
            <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                <div class="panel-body">
                    <table v-el:data-table-update width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingThree">
                <h4 class="panel-title">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                        <span class="title">增项</span> <br/> 总计：{{total3}}（项） 总金额：{{totalAddAmount}}
                    </a>
                </h4>
            </div>
            <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                <div class="panel-body">
                    <table v-el:data-table-add width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingFour">
                <h4 class="panel-title">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                        <span class="title">减项</span> <br/> 总计：{{total4}}（项） 总金额：{{totalSubAmount}}
                    </a>
                </h4>
            </div>
            <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
                <div class="panel-body">
                    <table v-el:data-table-sub width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingFive">
                <h4 class="panel-title">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseFive" aria-expanded="false" aria-controls="collapseFive">
                        <span class="title">基装定额（增项）</span> <br/>总计：{{total5}}（项） 总金额：{{totalBaseNormAmount}}
                    </a>
                </h4>
            </div>
            <div id="collapseFive" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFive">
                <div class="panel-body">
                    <table v-el:data-table-base-norm width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingTen">
                <h4 class="panel-title">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseTen" aria-expanded="false" aria-controls="collapseTen">
                        <span class="title">基装变更</span> <br/>总计：{{installBaseCount}}（项） 总金额：{{mainInstallBaseChangeTotalAmount}}
                    </a>
                </h4>
            </div>
            <div id="collapseTen" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTen">
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
                        <tbody align="center" v-for="item in installBaseChanges">
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
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingNine">
                <h4 class="panel-title">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseNine" aria-expanded="false" aria-controls="collapseNine">
                        <span class="title">基装定额（减项）</span> <br/>总计：{{total6}}（项） 总金额：{{totalreduceItemDataAmount}}
                    </a>
                </h4>
            </div>
            <div id="collapseNine" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingNine">
                <div class="panel-body">
                    <table v-el:data-table-reduce-item width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingSix">
                <h4 class="panel-title">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseSix" aria-expanded="false" aria-controls="collapseSix">
                        <span class="title">活动、优惠及其它金额增减</span> <br/> 总计：{{total7}}（项） 总金额：{{preferentialAmount}}
                    </a>
                </h4>
            </div>
            <div id="collapseSix" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingSix">
                <div class="panel-body">
                    <table v-el:data-table-preferential-amount width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingSeven">
                <h4 class="panel-title">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseSeven" aria-expanded="false" aria-controls="collapseSeven">
                        <span class="title">其他综合费</span> <br/> 总计：{{total8}}（项） 总金额：{{toOtherDataRows}}
                    </a>
                </h4>
            </div>
            <div id="collapseSeven" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingSeven">
                <div class="panel-body">
                    <table v-el:data-table-other width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
        </div>
        <%--<div class="panel panel-default">--%>
        <%--<div class="panel-heading" role="tab" id="headingEight">--%>
        <%--<h4 class="panel-title">--%>
        <%--<a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"--%>
        <%--href="#collapseEight" aria-expanded="false" aria-controls="collapseEight">--%>
        <%--<span class="title">辅材</span> <br/> 总计：{{total9}}（项） 总金额：{{toMaterialDataRows}}--%>
        <%--</a>--%>
        <%--</h4>--%>
        <%--</div>--%>
        <%--<div id="collapseEight" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingEight">--%>
        <%--<div class="panel-body">--%>
        <%--<table v-el:data-table-material width="100%"--%>
        <%--class="table table-striped table-bordered table-hover">--%>
        <%--</table>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
    </div>
    <!--/.panel-group-->
</template>