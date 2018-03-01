<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>合同管理</title>
<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div class="row">
                <form id="searchForm" @submit.prevent="query">
                    <div class="col-md-2">
                        <div class="form-group">
                            <label class="sr-only" for="keyword"></label>
                            <input
                                    v-model="form.keyword"
                                    id="keyword"
                                    name="keyword"
                                    type="text"
                                    placeholder="客户姓名/合同编号" class="form-control"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="sr-only" for="searchMonth">查询月份：</label>
                        <div class="col-md-2">
                            <input
                                    v-model="form.searchMonth"
                                    readonly
                                    id="searchMonth"
                                    v-el:search-month
                                    name="searchMonth"
                                    type="text"
                                    placeholder="查询月份" class="form-control"/>
                        </div>
                    </div>
                    <div class="col-md-1">
                        <div class="form-group">
                            <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default"
                                    alt="搜索"
                                    title="搜索">
                                <i class="fa fa-search"></i>
                            </button>
                        </div>
                    </div>

                    <div class="col-md-4"></div>
                    <shiro:hasPermission name="stylist:contract-sync">
                        <div class="col-md-2">
                            <div class="form-group">
                                <label class="sr-only" for="synMonth">同步月份：</label>
                                <input
                                        v-model="synMonth"
                                        readonly
                                        id="synMonth"
                                        v-el:syn-month
                                        name="synMonth"
                                        type="text"
                                        placeholder="同步月份" class="form-control"/>
                            </div>
                        </div>
                        <!-- 将剩余栅栏的长度全部给button -->
                        <div class="col-md-1">

                            <div class="form-group">

                                <button @click="synContractHandler" id="synContractBtn" :disabled="disabled"
                                        type="button"
                                        class="btn btn-outline btn-primary">同步
                                </button>

                            </div>

                        </div>
                    </shiro:hasPermission>
                </form>
            </div>
            <!-- <div class="columns columns-right btn-group pull-right"></div> -->
            <table v-el:data-table id="dataTable" width="100%"
                   class="table table-striped table-bordered table-hover">
            </table>
        </div>
    </div>
    <!-- ibox end -->
</div>
<!-- container end-->

<div id="modal" class="modal fade" tabindex="-1" data-width="760">
    <validator name="validation">
        <form name="createMirror" novalidate class="form-horizontal" role="form">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3>编辑合同金额</h3>
            </div>
            <div class="modal-body">

                <div class="form-group">
                    <label for="name" class="col-sm-2 control-label">客户姓名:</label>
                    <div class="col-sm-4">
                        <input
                                readonly
                                id="name"
                                v-model="contract.customerName"
                                class="form-control"
                                type="text">
                    </div>

                    <label for="contractNo" class="col-sm-2 control-label">合同编号:</label>
                    <div class="col-sm-4">
                        <input
                                readonly
                                id="contractNo"
                                v-model="contract.contractNo"
                                class="form-control"
                                type="text">
                    </div>
                </div>

                <div class="form-group">
                    <label for="money" class="col-sm-2 control-label">合同总额:</label>
                    <div class="col-sm-4">
                        <input
                                readonly
                                id="money"
                                v-model="contract.money"
                                class="form-control"
                                type="text">
                    </div>

                    <label for="taxesMoney" class="col-sm-2 control-label">税金:</label>
                    <div class="col-sm-4">
                        <input
                                readonly
                                id="taxesMoney"
                                v-model="contract.taxesMoney"
                                class="form-control"
                                type="text">
                    </div>
                </div>

                <div class="form-group">
                    <label for="managerMoney" class="col-sm-2 control-label">管理费:</label>
                    <div class="col-sm-4">
                        <input
                                readonly
                                id="managerMoney"
                                v-model="contract.managerMoney"
                                class="form-control"
                                type="text">
                    </div>
                </div>

                <div class="form-group" :class="{'has-error':$validation.designMoney.invalid && submitBtnClick}">
                    <label for="designMoney" class="col-sm-2 control-label">设计费:</label>
                    <div class="col-sm-4">
                        <input
                                id="designMoney"
                                v-model="contract.designMoney"
                                class="form-control"
                                v-validate:design-money="{required:{rule:true,message:'请输入设计费'}}"
                                type="number">
                        <span v-cloak v-if="$validation.designMoney.invalid && submitBtnClick"
                              class="help-absolute">
              <span v-for="error in $validation.designMoney.errors">
                {{error.message}} {{($index !== ($validation.designMoney.errors.length -1)) ? ',':''}}
              </span>
            </span>
                    </div>
                </div>

                <div class="form-group" :class="{'has-error':$validation.remoteMoney.invalid && submitBtnClick}">
                    <label for="remoteMoney" class="col-sm-2 control-label">远程费:</label>
                    <div class="col-sm-4">
                        <input
                                id="remoteMoney"
                                v-model="contract.remoteMoney"
                                class="form-control"
                                v-validate:remote-money="{required:{rule:true,message:'请输入远程费'}}"
                                type="number">
                        <span v-cloak v-if="$validation.remoteMoney.invalid && submitBtnClick"
                              class="help-absolute">
              <span v-for="error in $validation.remoteMoney.errors">
                {{error.message}} {{($index !== ($validation.remoteMoney.errors.length -1)) ? ',':''}}
              </span>
            </span>
                    </div>
                </div>

                <div class="form-group" :class="{'has-error':$validation.othersMoney.invalid && submitBtnClick}">
                    <label for="othersMoney" class="col-sm-2 control-label">其他费用:</label>
                    <div class="col-sm-4">
                        <input
                                id="othersMoney"
                                v-model="contract.othersMoney"
                                class="form-control"
                                v-validate:others-money="{required:{rule:true,message:'请输入其他费用'}}"
                                type="number">
                        <span v-cloak v-if="$validation.othersMoney.invalid && submitBtnClick"
                              class="help-absolute">
              <span v-for="error in $validation.othersMoney.errors">
                {{error.message}} {{($index !== ($validation.othersMoney.errors.length -1)) ? ',':''}}
              </span>
            </span>
                    </div>
                </div>

                <div class="form-group" :class="{'has-error':$validation.privilegeMoney.invalid && submitBtnClick}">
                    <label for="privilegeMoney" class="col-sm-2 control-label">优惠费:</label>
                    <div class="col-sm-4">
                        <input
                                id="privilegeMoney"
                                v-model="contract.privilegeMoney"
                                class="form-control"
                                v-validate:privilege-money="{required:{rule:true,message:'请输入优惠费'}}"
                                type="number">
                        <span v-cloak v-if="$validation.privilegeMoney.invalid && submitBtnClick"
                              class="help-absolute">
              <span v-for="error in $validation.privilegeMoney.errors">
                {{error.message}} {{($index !== ($validation.privilegeMoney.errors.length -1)) ? ',':''}}
              </span>
            </span>
                    </div>
                </div>

                <div class="form-group" :class="{'has-error':$validation.modifyMoney.invalid && submitBtnClick}"
                     v-if="hasThreeStage">
                    <label for="modifyMoney" class="col-sm-2 control-label">更后费用:</label>
                    <div class="col-sm-4">
                        <input
                                id="modifyMoney"
                                v-model="contract.modifyMoney"
                                class="form-control"
                                v-validate:modify-money="{required:{rule:true,message:'请输入变更后费用'}}"
                                type="number">
                        <span v-cloak v-if="$validation.modifyMoney.invalid && submitBtnClick"
                              class="help-absolute">
              <span v-for="error in $validation.modifyMoney.errors">
                {{error.message}} {{($index !== ($validation.modifyMoney.errors.length -1)) ? ',':''}}
              </span>
            </span>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
                <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
            </div>
        </form>
    </validator>
</div>

<script src="${ctx}/static/js/containers/stylist/contracts.js?v=1.0"></script>