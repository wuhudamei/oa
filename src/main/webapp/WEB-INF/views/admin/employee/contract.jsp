<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>员工合同管理</title>
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
          <div class="col-md-10 text-right">
            <div class="form-group">
              <button @click="createBtnClickHandler" type="button"
                      class="btn btn-outline btn-primary">添加
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

<div id="modal" class="modal fade" tabindex="-1" data-width="760">
  <validator name="validation">
    <form name="createForm" novalidate class="form-horizontal" role="form">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>创建合同</h3>
      </div>
      <div class="modal-body">

        <div class="form-group" :class="{'has-error':$validation.contractno.invalid && submitBtnClick}">
          <label for="contractNumber" class="col-sm-2 control-label">合同编号</label>
          <div class="col-sm-8">
            <input v-model="contract.contractNo"
                   v-validate:contractno="{
                     required:{rule:true,message:'请输入合同编号'}
                   }"
                   maxlength="50"
                   data-tabindex="2"
                   id="contractNumber" name="contractno" type="text" class="form-control" placeholder="请输入合同编号">
            <span v-cloak v-if="$validation.contractno.invalid && submitBtnClick" class="help-absolute">
                          <span v-for="error in $validation.contractno.errors">
                            {{error.message}} {{($index !== ($validation.contractno.errors.length -1)) ? ',':''}}
                          </span>
                        </span>
          </div>
        </div>

        <div class="form-group" :class="{'has-error':$validation.firstParty.invalid && submitBtnClick}">
          <label for="secondParty" class="col-sm-2 control-label">甲方名称</label>
          <div class="col-sm-8">
            <input v-model="contract.firstParty"
                   v-validate:first-party="{
                     required:{rule:true,message:'请选择甲方名称'}
                   }"
                   maxlength="50"
                   data-tabindex="4"
                   id="firstParty" name="firstParty" type="text" class="form-control" placeholder="请选择甲方名称">
            <span v-cloak v-if="$validation.firstParty.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.firstParty.errors">
                {{error.message}} {{($index !== ($validation.firstParty.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

        <div class="form-group" :class="{'has-error':$validation.signDate.invalid && submitBtnClick}">
          <label for="signDate" class="col-sm-2 control-label">签订日期</label>
          <div class="col-sm-8">
            <input v-model="contract.signDate"
                   v-validate:sign-date="{
                     required:{rule:true,message:'请输入签订日期'}
                   }"
                   v-el:sign-date
                   maxlength="50"
                   data-tabindex="5"
                   readonly
                   id="signDate" name="signDate" type="text" class="form-control datepicker" placeholder="请输入签订日期">
            <span v-cloak v-if="$validation.signDate.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.signDate.errors">
                {{error.message}} {{($index !== ($validation.signDate.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

        <div class="form-group" :class="{'has-error':$validation.effectiveDate.invalid && submitBtnClick}">
          <label for="effectiveDate" class="col-sm-2 control-label">生效日期</label>
          <div class="col-sm-8">
            <input v-model="contract.effectiveDate"
                   v-validate:effective-date="{
                     required:{rule:true,message:'请输入生效日期'}
                   }"
                   v-el:effective-date
                   maxlength="50"
                   data-tabindex="6"
                   id="effectiveDate" name="effectiveDate" type="text" class="form-control datepicker" placeholder="请输入生效日期">
            <span v-cloak v-if="$validation.effectiveDate.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.effectiveDate.errors">
                {{error.message}} {{($index !== ($validation.effectiveDate.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

        <div class="form-group" :class="{'has-error':$validation.tryDate.invalid && submitBtnClick}">
          <label for="tryDate" class="col-sm-2 control-label">转正日期</label>
          <div class="col-sm-8">
            <input v-model="contract.tryDate"
                   v-validate:try-date="{
                                    required:{rule:true,message:'请输入转正日期'}
                                }"
                   v-el:try-date
                   maxlength="50"
                   data-tabindex="7"
                   id="tryDate" name="tryDate" type="text" class="form-control datepicker" placeholder="请输入转正日期">
            <span v-cloak v-if="$validation.tryDate.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.tryDate.errors">
                {{error.message}} {{($index !== ($validation.tryDate.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

        <div class="form-group" :class="{'has-error':$validation.endDate.invalid && submitBtnClick}">
          <label for="endDate" class="col-sm-2 control-label">终止日期</label>
          <div class="col-sm-8">
            <input v-model="contract.endDate"
                   v-validate:end-date="{
                     required:{rule:true,message:'请输入终止日期'}
                   }"
                   v-el:end-date
                   maxlength="50"
                   data-tabindex="8"
                   id="endDate" name="endDate" type="text" class="form-control datepicker" placeholder="请输入终止日期">
            <span v-cloak v-if="$validation.endDate.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.endDate.errors">
                {{error.message}} {{($index !== ($validation.endDate.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

        <div class="form-group" :class="{'has-error':$validation.baseSalary.invalid && submitBtnClick}">
          <label for="baseSalary" class="col-sm-2 control-label">基本薪资</label>
          <div class="col-sm-8">
            <input v-model="contract.baseSalary"
                   v-validate:base-salary="{
                      required:{rule:true,message:'请输入基本薪资'},
                      min:0,
                      max:99999999.99,
                      unitTwodecimal:{rule:true,message:'请输入正确的数字'}
                   }"
                   data-tabindex="9"
                   id="baseSalary" name="baseSalary" type="text" class="form-control" placeholder="请输入基本薪资">
            <span v-cloak v-if="$validation.baseSalary.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.baseSalary.errors">
                {{error.message}} {{($index !== ($validation.baseSalary.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

        <div class="form-group" :class="{'has-error':$validation.meritPay.invalid && submitBtnClick}">
          <label for="meritPay" class="col-sm-2 control-label">绩效工资</label>
          <div class="col-sm-8">
            <input v-model="contract.meritPay"
                   v-validate:merit-pay="{
                     required:{rule:true,message:'请输入绩效工资'},
                     min:0,
                     max:99999999.99,
                     unitTwodecimal:{rule:true,message:'请输入正确的数字'}
                   }"
                   data-tabindex="10"
                   id="meritPay" name="meritPay" type="text" class="form-control" placeholder="请输入绩效工资">
            <span v-cloak v-if="$validation.meritPay.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.meritPay.errors">
                {{error.message}} {{($index !== ($validation.meritPay.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

        <div class="form-group" :class="{'has-error':$validation.otherSalary.invalid && submitBtnClick}">
          <label for="otherSalary" class="col-sm-2 control-label">其他薪资</label>
          <div class="col-sm-8">
            <input v-model="contract.otherSalary"
                   v-validate:other-palary="{
                     required:{rule:true,message:'请输入其他薪资'},
                     min:0,
                     max:99999999.99,
                     unitTwodecimal:{rule:true,message:'请输入正确的数字'}
                   }"
                   maxlength="50"
                   data-tabindex="11"
                   id="otherSalary" name="otherSalary" type="text" class="form-control" placeholder="请输入其他薪资">
            <span v-cloak v-if="$validation.otherSalary.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.otherSalary.errors">
                {{error.message}} {{($index !== ($validation.otherSalary.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

        <div class="form-group" :class="{'has-error':$validation.fileName.invalid && submitBtnClick}">
          <label for="fileName" class="col-sm-2 control-label">合同附件</label>
          <div class="col-sm-8">
            <input type="hidden" v-model="contract.fileUrl">
            <input v-model="contract.fileName"
                   v-validate:file-name="{
                     maxlength:{rule:100,message:'附件名称最长不能超过100个字符'}
                   }"
                   readonly
                   maxlength="100"
                   data-tabindex="12"
                   id="fileName" name="fileName" type="text" class="form-control" placeholder="附件名称">
            <span v-cloak v-if="$validation.fileName.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.fileName.errors">
                {{error.message}} {{($index !== ($validation.fileName.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>
        <div class="form-group">
          <label for="" class="col-sm-2 control-label"></label>
          <div class="col-sm-8">
            <div class="col-sm-4" style="position:relative;">
              <web-uploader
                :type="webUploaderSub.type"
                :w-server="webUploaderSub.server"
                :w-accept="webUploaderSub.accept"
                :w-file-size-limit="webUploaderSub.fileSizeLimit"
                :w-file-single-size-limit="webUploaderSub.fileSingleSizeLimit"
                :w-form-data="{category:'CONTRACT'}">
                <button type="button" class="btn btn-sm btn-primary">上传附件</button>
              </web-uploader>
            </div>
            <div class="col-sm-4">
              <button @click="deleteFlie()" v-if="contract.fileName != '' && contract.fileName != null" type="button" class="btn btn-sm btn-danger">
                删除附件
              </button>
            </div>
          </div>
        </div>

        <div class="form-group">
          <label class="col-sm-2 control-label">备注</label>
          <div class="col-sm-8">
            <textarea
              v-model="contract.remarks"
              id="remarks"
              name="remarks"
              class="form-control"
              rows="3"
              placeholder="请输入备注信息">
            </textarea>
          </div>
        </div>

      </div>
      <div class="modal-footer">
        <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
        <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">创建</button>
      </div>
    </form>
  </validator>
</div>

<script src="${ctx}/static/vendor/webuploader/webuploader.js"></script>
<script src="${ctx}/static/js/components/webuploader.js"></script>
<script src="${ctx}/static/js/containers/employee/contract.js"></script>
