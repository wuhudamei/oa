<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>基本薪资维护</title>
<head>
    <link rel="stylesheet" href="/static/vendor/webuploader/webuploader.css">
</head>
<div id="container" class="wrapper wrapper-content animated fadeInRight">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div class="row">
                <form id="searchForm" @submit.prevent="query">
                    <div class="col-md-2 text-left">
                        <div class="form-group">
                            <label>姓名：<b v-text="empName"></b></label>
                        </div>
                    </div>
                    <div class="col-md-2 text-left">
                        <div class="form-group">
                            <label>工号：<b v-text="orgCode"></b></label>
                        </div>
                    </div>
                    <div class="col-md-2 text-left">
                        <div class="form-group">
                            <button @click="createBtnClickHandler" type="button"
                                    class="btn btn-outline btn-primary">添加
                            </button>
                        </div>
                    </div>
                  <div class="col-md-6 text-left">
                        <div class="form-group">
                            <button @click="cancel" type="button" class="btn btn btn-info">返回
                            </button>
                        </div>
                  </div>

                </form>
            </div>
            <table id="dataTable" width="100%" class="table table-striped table-bordered table-hover">
            </table>
        </div>
    </div>
    <!-- ibox end -->
</div>

<div id="modal" class="modal fade" tabindex="-1" data-width="1000">

    <validator name="validation">
        <form name="createForm" novalidate class="form-horizontal" role="form">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3>编辑工资信息</h3>
            </div>

            <div class="modal-body">
                <div class="text-center">
                    <h2>基本信息</h2>
                </div>
                <b><hr /></b>

                <div class="form-group" :class="{'has-error':$validation.contractno.invalid && submitBtnClick}">
                    <label for="name" class="col-sm-2 control-label">姓名: </label>
                    <div class="col-sm-4" id="name">
                        <b name="name" v-text="empName"></b>
                    </div>
                </div>

                <div class="form-group">
                    <label for="bank" class="col-sm-2 control-label">银行</label>
                    <div class="col-sm-4" :class="{'has-error':$validation.bank.invalid && submitBtnClick}">
                        <input v-model="salarydetail.bank"
                               v-validate:bank="{
                                 required:{rule:true,message:'请输入银行名称'}
                               }"
                               v-el:bank
                               maxlength="50"
                               tabindex="1"
                               id="bank" name="bank" type="text" class="form-control datepicker" placeholder="请输入银行名称">
                        <span v-cloak v-if="$validation.bank.invalid && submitBtnClick" class="help-absolute">
                          <span v-for="error in $validation.bank.errors">
                            {{error.message}} {{($index !== ($validation.bank.errors.length -1)) ? ',':''}}
                          </span>
                        </span>
                    </div>
                    <label for="bankOfDeposit" class="col-sm-2 control-label">开户行</label>
                    <div class="col-sm-4" :class="{'has-error':$validation.bankOfDeposit.invalid && submitBtnClick}">
                        <input v-model="salarydetail.bankOfDeposit"
                                v-validate:bank-of-deposit="{
                                 required:{rule:true,message:'请输入开户行'}
                                 }"
                               v-el:bank-of-deposit
                               maxlength="50"
                               tabindex="2"
                               id="bankOfDeposit" name="bankOfDeposit" type="text" class="form-control datepicker" placeholder="请输入开户行">
                        <span v-cloak v-if="$validation.bankOfDeposit.invalid && submitBtnClick" class="help-absolute">
                          <span v-for="error in $validation.bankOfDeposit.errors">
                            {{error.message}} {{($index !== ($validation.bankOfDeposit.errors.length -1)) ? ',':''}}
                          </span>
                        </span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="creditCardNumbers" class="col-sm-2 control-label">银行卡号</label>
                    <div class="col-sm-6" :class="{'has-error':$validation.creditCardNumbers.invalid && submitBtnClick}">
                        <input v-model="salarydetail.creditCardNumbers"
                               v-validate:credit-card-numbers="['card']"
                               maxlength="50"
                               tabindex="3"
                               id="creditCardNumbers" name="creditCardNumbers" type="number"class="form-control datepicker" onkeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))" placeholder="请输入银行卡号">
                        <span v-cloak v-if="$validation.creditCardNumbers.invalid && submitBtnClick" class="help-absolute">
                          <span v-for="error in $validation.creditCardNumbers.errors">
                            {{error.message}} {{($index !== ($validation.creditCardNumbers.errors.length -1)) ? ',':''}}
                          </span>
                        </span>
                    </div>
                </div>


                    <div class="form-group">
                        <label for="effectiveDates" class="col-sm-2 control-label">工资生效日期</label>
                        <div class="col-sm-4" :class="{'has-error':$validation.effectiveDate.invalid && submitBtnClick}">
                            <input v-model="salarydetail.effectiveDate"
                                   v-validate:effective-date="{
                                    required:{rule:true,message:'请输入工资生效日期'}
                                }"
                                   v-el:effective-date
                                   maxlength="50"
                                   tabindex="4"
                                   id="effectiveDates" name="effectiveDates" type="text" class="form-control datepicker"  placeholder="请输入工资生效日期" readonly>
                            <span v-cloak v-if="$validation.effectiveDate.invalid && submitBtnClick" class="help-absolute">
                          <span v-for="error in $validation.effectiveDate.errors">
                            {{error.message}} {{($index !== ($validation.effectiveDate.errors.length -1)) ? ',':''}}
                          </span>
                        </span>
                        </div>
                        <label for="expiryDates" class="col-sm-2 control-label">工资终止日期</label>
                        <div class="col-sm-4" :class="{'has-error':$validation.expiryDate.invalid && submitBtnClick}">
                            <input v-model="salarydetail.expiryDate"
                                   v-validate:expiry-date="{
                                    required:{rule:true,message:'请输入工资终止日期'}
                                }"
                                   v-el:expiry-date
                                   maxlength="50"
                                   tabindex="5"
                                   id="expiryDates" name="expiryDates" type="text" class="form-control datepicker" placeholder="请输入工资终止日期" readonly>
                            <span v-cloak v-if="$validation.expiryDate.invalid && submitBtnClick" class="help-absolute">
                          <span v-for="error in $validation.expiryDate.errors">
                            {{error.message}} {{($index !== ($validation.expiryDate.errors.length -1)) ? ',':''}}
                          </span>
                        </span>
                        </div>
                    </div>

                <div class="form-group">
                    <label for="shouldWorkDays" class="col-sm-2 control-label">应出勤天数</label>
                    <div class="col-sm-6" :class="{'has-error':$validation.shouldWorkDays.invalid && submitBtnClick}">
                        <input v-model="salarydetail.shouldWorkDays"
                               v-validate:should-work-days="['days']"
                               maxlength="50"
                               tabindex="6"
                               id="shouldWorkDays" name="shouldWorkDays" type="number" class="form-control datepicker" onkeypress="return (/[\d{2}.]/.test(String.fromCharCode(event.keyCode)))" placeholder="请输入应出勤天数">
                        <span v-cloak v-if="$validation.shouldWorkDays.invalid && submitBtnClick" class="help-absolute">
                          <span v-for="error in $validation.shouldWorkDays.errors">
                            {{error.message}} {{($index !== ($validation.shouldWorkDays.errors.length -1)) ? ',':''}}
                          </span>
                        </span>
                    </div>
                </div>

                <div class="text-center">
                    <h2>薪资信息</h2>
                </div>
                <b><hr /></b>

                <div class="form-group">
                    <label for="salaryBasic" class="col-sm-2 control-label">基本工资</label>
                    <div class="col-sm-4"  :class="{'has-error':$validation.salaryBasic.invalid && submitBtnClick}">
                        <input v-model="salarydetail.salaryBasic"
                               v-validate:salary-basic="['num']"
                               maxlength="50"
                               tabindex="7"
                               id="salaryBasic" name="salaryBasic" type="number" class="form-control" onkeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))"  placeholder="请输入基本工资">
                        <span v-cloak v-if="$validation.salaryBasic.invalid && submitBtnClick" class="help-absolute">
                          <span v-for="error in $validation.salaryBasic.errors">
                            {{error.message}} {{($index !== ($validation.salaryBasic.errors.length -1)) ? ',':''}}
                          </span>
                        </span>
                    </div>
                    <label for="mealSubsidy" class="col-sm-2 control-label">餐补</label>
                    <div class="col-sm-4" :class="{'has-error':$validation.mealSubsidy.invalid && submitBtnClick}">
                        <input v-model="salarydetail.mealSubsidy"
                               v-validate:meal-subsidy="['num']"
                               maxlength="50"
                               tabindex="8"
                               id="mealSubsidy" name="otherSalary" type="number" class="form-control" onkeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))" placeholder="请输入餐补">
                        <span v-cloak v-if="$validation.mealSubsidy.invalid && submitBtnClick" class="help-absolute">
                          <span v-for="error in $validation.mealSubsidy.errors">
                            {{error.message}} {{($index !== ($validation.mealSubsidy.errors.length -1)) ? ',':''}}
                          </span>
                        </span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="socialSecurityPersonal" class="col-sm-2 control-label">社保</label>
                    <div class="col-sm-4" :class="{'has-error':$validation.socialSecurityPersonal.invalid && submitBtnClick}">
                        <input v-model="salarydetail.socialSecurityPersonal"
                               v-validate:social-security-personal="['num']"
                               maxlength="50"
                               tabindex="9"
                               id="socialSecurityPersonal" name="socialSecurityPersonal" type="number" class="form-control" onkeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))"  placeholder="请输入社保">
                        <span v-cloak v-if="$validation.socialSecurityPersonal.invalid && submitBtnClick" class="help-absolute">
                          <span v-for="error in $validation.socialSecurityPersonal.errors">
                            {{error.message}} {{($index !== ($validation.socialSecurityPersonal.errors.length -1)) ? ',':''}}
                          </span>
                        </span>
                    </div>
                    <label for="housingFund" class="col-sm-2 control-label">公积金</label>
                    <div class="col-sm-4" :class="{'has-error':$validation.housingFund.invalid && submitBtnClick}">
                        <input v-model="salarydetail.housingFund"
                               v-validate:housing-fund="['num']"
                               maxlength="50"
                               tabindex="10"
                               id="housingFund" name="housingFund" type="number" class="form-control" onkeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))"  placeholder="请输入公积金">
                        <span v-cloak v-if="$validation.housingFund.invalid && submitBtnClick" class="help-absolute">
                          <span v-for="error in $validation.housingFund.errors">
                            {{error.message}} {{($index !== ($validation.housingFund.errors.length -1)) ? ',':''}}
                          </span>
                        </span>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <div class="col-md-6 text-right">
                    <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
                </div>
                <div class="col-md-6 text-left">
                        <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
                </div>
            </div>
        </form>
    </validator>

</div>

<div id="modalEdit" class="modal fade" tabindex="-1" data-width="760">

    <validator name="validation">
        <form name="createForm" novalidate class="form-horizontal" role="form">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3>编辑工资信息</h3>
            </div>

            <div class="modal-body">
                <div class="text-center">
                    <h2>基本信息</h2>
                </div>
                <b><hr /></b>

                <div class="form-group" :class="{'has-error':$validation.contractno.invalid && submitBtnClick}">
                    <label for="name" class="col-sm-2 control-label">姓名: </label>
                    <div class="col-sm-4" >
                        <b v-text="empName"></b>
                    </div>
                    <label for="shouldWorkDays" class="col-sm-2 control-label">应出勤天数: </label>
                    <div class="col-sm-4" :class="{'has-error':$validation.shouldWorkDays.invalid && submitBtnClick}">
                        <input v-model="salarydetail.shouldWorkDays"
                               v-validate:should-work-days="['days']"
                               maxlength="50"
                                type="number" class="form-control datepicker" onkeypress="return (/[\d{2}.]/.test(String.fromCharCode(event.keyCode)))" placeholder="请输入应出勤天数">
                        <span v-cloak v-if="$validation.shouldWorkDays.invalid && submitBtnClick" class="help-absolute">
                          <span v-for="error in $validation.shouldWorkDays.errors">
                            {{error.message}} {{($index !== ($validation.shouldWorkDays.errors.length -1)) ? ',':''}}
                          </span>
                        </span>
                    </div>
                </div>

                <div class="form-group">
                    <label for="effectiveDates" class="col-sm-2 control-label">工资生效日期</label>
                    <div class="col-sm-4" :class="{'has-error':$validation.effectiveDate.invalid && submitBtnClick}">
                        <input v-model="salarydetail.effectiveDate"
                               v-validate:effective-date="{
                                    required:{rule:true,message:'请输入工资生效日期'}
                                }"
                               v-el:effective-date
                               maxlength="50"
                               data-tabindex="1"
                                type="text" class="form-control datepicker"  placeholder="请输入工资生效日期" readonly>
                        <span v-cloak v-if="$validation.effectiveDate.invalid && submitBtnClick" class="help-absolute">
                          <span v-for="error in $validation.effectiveDate.errors">
                            {{error.message}} {{($index !== ($validation.effectiveDate.errors.length -1)) ? ',':''}}
                          </span>
                        </span>
                    </div>
                    <label for="expiryDates" class="col-sm-2 control-label">工资终止日期</label>
                    <div class="col-sm-4" :class="{'has-error':$validation.expiryDate.invalid && submitBtnClick}">
                        <input v-model="salarydetail.expiryDate"
                               v-validate:expiry-date="{
                                    required:{rule:true,message:'请输入工资终止日期'}
                                }"
                               v-el:expiry-date
                               maxlength="50"
                               data-tabindex="2"
                                type="text" class="form-control datepicker" placeholder="请输入工资终止日期" readonly>
                        <span v-cloak v-if="$validation.expiryDate.invalid && submitBtnClick" class="help-absolute">
                          <span v-for="error in $validation.expiryDate.errors">
                            {{error.message}} {{($index !== ($validation.expiryDate.errors.length -1)) ? ',':''}}
                          </span>
                        </span>
                    </div>
                </div>
            </div>

            <div class="modal-footer">
                <div class="col-md-6 text-right">
                    <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
                </div>
                <div class="col-md-6 text-left">
                    <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
                </div>
            </div>
        </form>
    </validator>

</div>

<script src="${ctx}/static/js/containers/employee/salarydetail.js"></script>
