<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<title>未对账</title>
<meta name="keywords" content="">
<meta name="description" content="">
<style>
</style>
</head>
<body id="app" class="fixed-sidebar full-height-layout gray-bg">
<div id="container" class="wrapper wrapper-content" v-cloak>
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div>
                <div class="row">
                    <form id="searchForm" @submit.prevent="query">


                        <div class="col-sm-5">
                            <div class="form-group" style="line-height: 34px"
                                 :class="{'has-error':($validation.starttime.invalid && $validation.touched)}">
                                <label for="startTime" class="col-sm-3 control-label">验收时间</label>
                                <div class="col-sm-4 no-padding">
                                    <input
                                            v-model="form.startTime"
                                            v-el:start-time
                                            id="startTime"
                                            name="startTime"
                                            type="text"
                                            class="form-control datepicker"
                                            placeholder="请选择开始时间">
                                </div>
                                <label for="endTime" class="control-label col-sm-1">~</label>
                                <div class="col-sm-4 no-padding">
                                    <input
                                            v-model="form.endTime"
                                            v-el:end-time
                                            id="endTime"
                                            name="endTime"
                                            type="text"
                                            class="form-control datepicker"
                                            placeholder="请选择结束时间">
                                </div>
                            </div>
                        </div>

                        <div class="col-md-1" >
                            <div class="form-group" >
                                <button id="searchBtn" @click="fetchRecord" type="button" class="btn btn-block btn-outline btn-default"
                                        alt="搜索"
                                        title="搜索">
                                    <i class="fa fa-search"></i>
                                </button>
                            </div>
                        </div>
                         <div class="col-md-5" >
                            <button @click="export" id="createBtn" type="button" class="btn btn-outline btn-primary">导出</button>
                            <button @click="exportAndMark" id="createBtn2" type="button" class="btn btn-outline btn-primary">导出并标记对账</button>
                            <button @click="mark(false)" id="createBtn3" type="button" class="btn btn-outline btn-primary">标记对账</button>
                         </div>

                        <div class="col-md-1" style="float: right;padding-bottom:10px;" >
                            <button @click="goBack" id="goBack" type="button" class="btn btn-outline btn-primary">返回</button>
                        </div>
                    </form>
                </div >
                <div style="color: red;float: right;padding-bottom:10px;">当前选择商品个数：{{pdcount}}（个） &nbsp;&nbsp;   当前选择商品总额：{{totalAmount}}（元）</div>
                <%--<div style="color: red"></div>--%>

                <div>
                    <validator name="valid">
                        <table id="dataTable2" class="table table-bordered">
                            <tr id="titileTr" >
                                <td style="text-align: center;font-weight:bold"><input @click="checkAll"  v-model="checkAllValue" class="checkAll" type="checkbox"/></td>
                                <td style="text-align: center;font-weight:bold">供应商名称</td>
                                <td style="text-align: center;font-weight:bold">品牌</td>
                                <td style="text-align: center;font-weight:bold">商品名称</td>
                                <td style="text-align: center;font-weight:bold">规格</td>
                                <td style="text-align: center;font-weight:bold">型号</td>
                                <td style="text-align: center;font-weight:bold">待对账数量</td>
                                <td style="text-align: center;font-weight:bold">本次对账数量</td>
                                <td style="text-align: center;font-weight:bold">单价</td>
                                <td style="text-align: center;font-weight:bold">总价</td>
                                <td style="text-align: center;font-weight:bold">状态</td>
                                <td style="text-align: center;font-weight:bold">是否调整</td>
                                <td style="text-align: center;font-weight:bold">操作</td>
                            </tr>
                            <tr v-for="stRdBilltem in stRdBilltems" >
                                <td style="text-align: center;font-weight:bold"><input type="checkbox" class="checkItem"  v-model="checkArr" :value="stRdBilltem.billItemId"/></td>
                                <td style="text-align: center">{{stRdBilltem.pdSupplier}}</td>
                                <td style="text-align: center">{{stRdBilltem.pdBrandname}}</td>
                                <td style="text-align: center">{{stRdBilltem.pdSkuname}}</td>
                                <td style="text-align: center">{{stRdBilltem.pdModel}}</td>
                                <td style="text-align: center">{{stRdBilltem.pdSkuSalesAttribute}}</td>

                                <td style="text-align: center" >{{stRdBilltem.pdCurrentCount}}</td>
                                <td style="text-align: center":class="{'has-error':$valid['count' + $index].invalid&&$valid.touched}" >
                                    <input 
                                    class="form-control"
                                    min="0"
                                    :max="stRdBilltem.pdCurrentCount"
                                    :field="'count'+ $index"
                                    v-validate="{min:0,numeric:true,overed:{rule:stRdBilltem.pdCurrentCount}}"
                                    v-model="stRdBilltem.cuCount"
                                    @change="countChange(stRdBilltem)"
                                    type="number" style="text-align: center" value="{{stRdBilltem.pdCurrentCount}}"/>
                                    <span style="position: initial;" v-show="($valid['count' + $index].numeric || $valid['count' + $index].min)&&$valid.touched" class="help-absolute">请输入正整数</span>
                                    <span class="help-absolute" style="position: initial;" v-show="$valid['count' + $index].overed&&$valid.touched">结算数量需小于待结算数量</span>
                                    </td>
                                <td style="text-align: center">{{stRdBilltem.pdPrice}}</td>
                                <td style="text-align: center">{{(stRdBilltem.pdPrice*stRdBilltem.cuCount).toFixed(2)}}</td>
                                <td style="text-align: center">{{stRdBilltem.reconciliationStatus}}</td>
                                <td style="text-align: center">{{stRdBilltem.change === true ? '已调整' : '未调整'}}</td>
                                <td style="text-align: center"><a
                                        href="javascript:void(0)" @click="showModel(stRdBilltem) ">调整</a>
                                    <a v-if="stRdBilltem.change" href="javascript:void(0)" @click="showModel2(stRdBilltem)">调整记录</a></td>
                            </tr>
                            <tr v-if="stRdBilltems.length === 0">
                                <td class="text-center" colspan="13">没有找到匹配的记录</td>
                            </tr>
                        </table>
                    </validator>
                </div>
            </div>
        </div>
    </div>
    <!-- ibox end -->
</div>

<div id="modal" class="modal fade" tabindex="-1" data-width="760">
    <div class="wrapper wrapper-content">
        <div class="ibox-content">
            <form name="createMirror" novalidate class="form-horizontal" role="form" style="margin-right:10px">
                <div class="text-center">
                    <h3>调整待结算数量</h3>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-sm-12" style="font-weight:bold;" v-model="bill.pdCurrentCount">
                        原待对账数量：{{noticeboard.pdCurrentCount}}
                    </div>
                    <div class="col-sm-12" style="font-weight:bold;  margin: 15px 0">
                        现待对账数量：<input
                                            type="text"
                                            style="width: 200px;"
                                            v-model="bill.pdChangeCountEnd" />


                    </div>
                    <div class="col-sm-12" style="font-weight:bold; padding-left: 31px">
                        调 整 说 明：<textarea style=" vertical-align: middle; width: 200px;" v-model="bill.changeAdjust"></textarea>
                    </div>

                </div>
            </form>

            <!-- ibox end -->
        </div>
        <div class="modal-footer">
            <button @click="cancel" type="button" data-dismiss="modal" class="btn">关闭</button>
            <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
        </div>
    </div>
</div>

<div id="modal2" class="modal fade" tabindex="-1" data-width="760">
    <div class="wrapper wrapper-content">
        <div class="ibox-content">
            <form name="createMirror" novalidate class="form-horizontal" role="form" style="margin-right:10px">
                <div class="text-center">
                    <h3>调整记录</h3>
                </div>
                <hr/>
                <table v-el:dataTable id="dataTable" width="100%"
                       class="table table-striped table-bordered table-hover text-center">
                       <thead>
                            <tr>
                                <th class="text-center">类型</th>
                                <th class="text-center">调整前数量</th>
                                <th class="text-center">调整后数量</th>
                                <th class="text-center">调整说明</th>
                                <th class="text-center">操作人</th>
                                <th class="text-center">操作时间</th>
                            </tr>
                       </thead>
                       <tbody>
                           <tr v-for="item in tableData">
                                <td>{{item.operateType}}</td>
                                <td>{{item.pdChangeCountBefore}}</td>
                                <td>{{item.pdChangeCountEnd}}</td>
                                <td>{{item.changeAdjust}}</td>
                                <td>{{item.operator}}</td>
                                <td>{{item.changeTime | moment-date}}</td>
                           </tr>
                       </tbody>
                </table>
            </form>

            <!-- ibox end -->
        </div>
        <div class="modal-footer">
            <button @click="cancel" type="button" data-dismiss="modal" class="btn">关闭</button>
        </div>
    </div>
</div>

<div id="modal3" class="modal fade" tabindex="-1" data-width="760">
    <div class="wrapper wrapper-content">
        <div class="ibox-content">
            <form name="createMirror" novalidate class="form-horizontal" role="form" style="margin-right:10px">
                <div class="text-center">
                    <h3>标记对账</h3>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-sm-12" style="font-weight:bold; padding-left: 31px">
                        对 账 说 明：<textarea style=" vertical-align: middle; width: 200px;" v-model="batchExplain"></textarea>
                    </div>

                </div>
            </form>

            <!-- ibox end -->
        </div>
        <div class="modal-footer">
            <button @click="cancel" type="button" data-dismiss="modal" class="btn">关闭</button>
            <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
        </div>
    </div>
</div>

</div>
<script src="/static/vendor/decimal/decimal.min.js"></script>
<script src="/static/js/containers/materialSupplierAccountChecking/materialSupplierAccountChecking.js"></script>
</body>
</html>