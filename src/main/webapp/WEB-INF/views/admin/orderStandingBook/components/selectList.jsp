<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="selectListTmpl">
    <span hidden>{{msg}}</span>
    <table width="100%" class="table table-striped table-bordered table-hover">
        <thead>
        <tr align="center">
            <td>发生时间</td>
            <td>设计事项</td>
        </tr>
        </thead>
        <tbody align="center">
        <tr>
            <td>{{occurrenceTime.stylistAllotTime | goDate}}</td>
            <td>分配设计师 : {{occurrenceTime.stylistName}} {{occurrenceTime.stylistMobile}}</td>
        </tr>
        <tr>
            <td>{{occurrenceTime.measureFinishTime | goDate}}</td>
            <td>量房完成</td>
        </tr>
        <tr>
            <td>{{occurrenceTime.blueprintFinishTime | goDate}}</td>
            <td>出图完成</td>
        </tr>
        <tr>
            <td>{{occurrenceTime.createTime | goDate}}</td>
            <td>选材完成</td>
        </tr>
        <tr>
            <td>{{occurrenceTime.firstAmountTime | goDate}}</td>
            <td>首期款缴纳完成时间</td>
        </tr>
        <tr>
            <td>{{occurrenceTime.signFinishTime | goDate}}</td>
            <td>签订施工合同</td>
        </tr>
        </tbody>
    </table>
    <div class="panel-group panel-stranding-book" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingOneList">
                <h4 class="panel-title">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseOneList" aria-expanded="true" aria-controls="collapseOneList">
                        <span class="title">套餐标配</span> <br/> 总计：{{total1}}（项） 总金额：{{totalStandardAmount | toDub }}
                    </a>
                </h4>
            </div>
            <div id="collapseOneList" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOneList">
                <div class="panel-body">
                    <table v-el:data-table-standard width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingTwoList">
                <h4 class="panel-title">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseTwoList" aria-expanded="false" aria-controls="collapseTwoList">
                        <span class="title">升级项</span> <br> 总计：{{total2}}（项） 总金额：{{totalUpdateAmount}}
                    </a>
                </h4>
            </div>
            <div id="collapseTwoList" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
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
                       href="#collapseThreeList" aria-expanded="false" aria-controls="collapseThreeList">
                        <span class="title">增项</span> <br/> 总计：{{total3}}（项） 总金额：{{totalAddAmount}}
                    </a>
                </h4>
            </div>
            <div id="collapseThreeList" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThreeList">
                <div class="panel-body">
                    <table v-el:data-table-add width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingFourList">
                <h4 class="panel-title">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseFourList" aria-expanded="false" aria-controls="collapseFourList">
                        <span class="title">减项</span> <br/> 总计：{{total4}}（项） 总金额：{{totalSubAmount}}
                    </a>
                </h4>
            </div>
            <div id="collapseFourList" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
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
                       href="#collapseFiveList" aria-expanded="false" aria-controls="collapseFiveList">
                        <span class="title">基装定额（增项）</span> <br/>总计：{{total5}}（项） 总金额：{{totalBaseNormAmount}}
                    </a>
                </h4>
            </div>
            <div id="collapseFiveList" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFiveList">
                <div class="panel-body">
                    <table v-el:data-table-base-norm width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingNine">
                <h4 class="panel-title">
                    <a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseNineList" aria-expanded="false" aria-controls="collapseNineList">
                        <span class="title">基装定额（减项）</span> <br/>总计：{{total6}}（项） 总金额：{{totalreduceItemDataAmount}}
                    </a>
                </h4>
            </div>
            <div id="collapseNineList" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingNineList">
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
                       href="#collapseSixList" aria-expanded="false" aria-controls="collapseSixList">
                        <span class="title">活动、优惠及其它金额增减</span> <br/> 总计：{{total7}}（项） 总金额：{{preferentialAmount}}
                    </a>
                </h4>
            </div>
            <div id="collapseSixList" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingSixList">
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
                       href="#collapseSevenList" aria-expanded="false" aria-controls="collapseSevenList">
                        <span class="title">其他综合费</span> <br/> 总计：{{total8}}（项） 总金额：{{toOtherDataRows}}
                    </a>
                </h4>
            </div>
            <div id="collapseSevenList" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingSevenList">
                <div class="panel-body">
                    <table v-el:data-table-other width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!--/.panel-group-->
</template>