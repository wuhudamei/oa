<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${ctx}/static/css/zTreeStyle.css" rel="stylesheet">
<title>出差申请</title>
<div id="container" class="wrapper wrapper-content">
  <div id="breadcrumb">
    <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
  </div>
  <!-- ibox start -->
  <div class="ibox-content">
  <validator name="validation">
    <form name="createForm" novalidate class="form-horizontal" role="form">
      <div class="modal-body">
          <input type="hidden" id="id" name="id" v-model="businessAway.id">
          <input type="hidden" id="userId" name="userId" v-model="businessAway.userId">
          <div class="form-group" v-if="businessAway.applyCode">
              <label class="col-sm-2 control-label">申请编号</label>
              <div class="col-sm-8" style="margin-top: 6px;">
                  <span >{{businessAway.applyCode}}</span>
              </div>
          </div>
          <div class="form-group" v-if="businessAway.applyTitle">
              <label class="col-sm-2 control-label">申请标题</label>
              <div class="col-sm-8" style="margin-top: 6px;">
                  <span >{{businessAway.applyTitle}}</span>
              </div>
          </div>
        <div class="form-group" :class="{'has-error':$validation.setoutaddress.invalid && submitBtnClick}">
          <label for="address" class="col-sm-2 control-label">出发地</label>
          <div class="col-sm-8">
            <input v-model="businessAway.setOutAddress"
                   v-validate:setoutaddress="{
                                    required:{rule:true,message:'请输入出发地'}
                                }"
                   data-tabindex="1"
                   id="setOutAddress" name="setOutAddress" type="text" class="form-control" placeholder="请输入出发地">
            <span v-cloak v-if="$validation.setoutaddress.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.setoutaddress.errors">
                {{error.message}} {{($index !== ($validation.setoutaddress.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>          
        <div class="form-group" :class="{'has-error':$validation.address.invalid && submitBtnClick}">
          <label for="address" class="col-sm-2 control-label">目的地</label>
          <div class="col-sm-8">
            <input v-model="businessAway.address"
                   v-validate:address="{
                                    required:{rule:true,message:'请输入出差地址'}
                                }"
                   data-tabindex="1"
                   id="address" name="address" type="text" class="form-control" placeholder="请输入出差地址">
            <span v-cloak v-if="$validation.address.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.address.errors">
                {{error.message}} {{($index !== ($validation.address.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

        <div class="form-group" :class="{'has-error':$validation.beginTime.invalid && submitBtnClick}">
          <label for="beginTime" class="col-sm-2 control-label">开始日期</label>
          <div class="col-sm-8">
            <input v-model="businessAway.beginTime"
                   v-validate:begin-time="{
                                    required:{rule:true,message:'请输入开始日期'}
                                }"
                   v-el:begin-time
                   data-tabindex="2"
                   readonly
                   id="beginTime" name="beginTime" type="text" class="form-control datepicker" placeholder="请输入开始日期">
            <span v-cloak v-if="$validation.beginTime.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.beginTime.errors">
                {{error.message}} {{($index !== ($validation.beginTime.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

        <div class="form-group" :class="{'has-error':$validation.endTime.invalid && submitBtnClick}">
          <label for="endTime" class="col-sm-2 control-label">结束日期</label>
          <div class="col-sm-8">
            <input v-model="businessAway.endTime"
                   v-validate:end-time="{
                                    required:{rule:true,message:'请输入结束日期'}
                                }"
                   v-el:end-time
                   data-tabindex="3"
                   readonly
                   id="endTime" name="endTime" type="text" class="form-control datepicker" placeholder="请输入结束日期">
            <span v-cloak v-if="$validation.endTime.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.endTime.errors">
                {{error.message}} {{($index !== ($validation.endTime.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

        <div class="form-group">
          <label for="daysNum" class="col-sm-2 control-label">出差天数</label>
          <div class="col-sm-8">
            <input v-model="businessAway.daysNum"
                   data-tabindex="4"
                   readonly
                   id="daysNum" name="daysNum" type="text" class="form-control" placeholder="请输入出差天数">
          </div>
        </div>
        
        <div class="form-group" :class="{'has-error':$validation.estimatedcost.invalid && submitBtnClick}">
          <label for="estimatedCost" class="col-sm-2 control-label">预估费用</label>
          <div class="col-sm-8">
            <input v-model="businessAway.estimatedCost"
                   v-validate:estimatedcost="{
                                    required:{rule:true,message:'请输入预估费用'},
                                    min:0,
                                    max:99999999
                                }"
                   data-tabindex="4"
                   id="estimatedCost" name="estimatedCost" type="text" class="form-control" placeholder="请输入预估费用">
            <span v-cloak v-if="$validation.estimatedcost.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.estimatedcost.errors">
                {{error.message}} {{($index !== ($validation.estimatedcost.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>

        <div class="form-group" :class="{'has-error':$validation.reason.invalid && submitBtnClick}">
          <label for="reason" class="col-sm-2 control-label">出差理由</label>
          <div class="col-sm-8">
            <textarea
              v-model="businessAway.reason"
              v-validate:reason="{
                                    required:{rule:true,message:'请输入出差理由'}
                                }"
              maxlength="50"
              id="reason"
              name="reason"
              class="form-control"
              rows="3"
              placeholder="请输入出差理由">
            </textarea>
            <span v-cloak v-if="$validation.reason.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.reason.errors">
                {{error.message}} {{($index !== ($validation.reason.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>
      <div class="text-center">
        <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;
        <button @click="saveDraft" :disabled="disabled" type="button" class="btn btn-info">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
        <button @click="cancel" type="button" data-dismiss="modal" class="btn">返回</button>
      </div>
    </form>
  </validator>	  
</div>
</div>
<script src="${ctx}/static/js/containers/businessAway/edit.js"></script>