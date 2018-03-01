<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${ctx}/static/css/zTreeStyle.css" rel="stylesheet">
<title>请假申请</title>
<div id="container" class="wrapper wrapper-content">
  <div id="breadcrumb">
    <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
  </div>
  <!-- ibox start -->
  <div class="ibox-content">
	  <validator name="validation">
	    <form name="createMirror" novalidate class="form-horizontal" role="form">
	      <div class="modal-body">
	    	<input type="hidden" v-model="vacation.id">
	        <div class="form-group" v-if="vacation.applyCode">
	          <label class="col-sm-2 control-label">申请编号</label>
	          <div class="col-sm-8" style="margin-top: 6px;">
                  <span >{{vacation.applyCode}}</span>
              </div>
	        </div>
	       <div class="form-group" v-if="vacation.applyTitle">
	          <label class="col-sm-2 control-label">申请标题</label>
	          <div class="col-sm-8" style="margin-top: 6px;">
                  <span >{{vacation.applyTitle}}</span>
              </div>
	       </div>	      
	        <div class="form-group" :class="{'has-error':$validation.applytype.invalid && submitBtnClick}">
	          <label class="col-sm-2 control-label">请假类型</label>
	          <div class="col-sm-8">
	            <select
	                    v-validate:applytype="{required:true}"
	                    v-model="vacation.applyType"
	                    id="applyType"
	                    name="applyType"
	                    data-tabindex="1"
	                    class="form-control">
	              <option value="">请选择请假类型</option>
	              <option  v-for="child in applyType"
	                       :value="child.id">
	                {{child.name}}
	              </option>
	
	            </select>
	            <span v-cloak v-if="$validation.applytype.invalid && submitBtnClick" class="help-absolute">
	              <span v-for="error in $validation.applytype.errors">
	                {{error.message}} {{($index !== ($validation.applytype.errors.length -1)) ? ',':''}}
	              </span>
	            </span>
	          </div>
	        </div>
	
	        <div class="form-group" :class="{'has-error':$validation.starttime.invalid && submitBtnClick}">
	          <label class="col-sm-2 control-label">开始时间</label>
	          <div class="col-sm-8">
	            <input v-model="vacation.startTime"
	                   v-validate:starttime="{required:{rule:true,message:'请选择开始时间'}}"
	                   readonly
	                   v-el:start-time
	                   data-tabindex="1"
	                   name="startDate" type="text" class="form-control" placeholder="请选择开始日期">
	            <span v-cloak v-if="$validation.starttime.invalid && submitBtnClick" class="help-absolute">
	              <span v-for="error in $validation.starttime.errors">
	                {{error.message}} {{($index !== ($validation.starttime.errors.length -1)) ? ',':''}}
	              </span>
	            </span>
	          </div>
	        </div>
	
	        <div class="form-group" :class="{'has-error':$validation.endtime.invalid && submitBtnClick}">
	          <label class="col-sm-2 control-label">结束时间</label>
	          <div class="col-sm-8">
	            <input v-model="vacation.endTime"
	                   v-validate:endtime="{required:{rule:true,message:'请选择结束时间'}}"
	                   readonly
	                   v-el:end-time
	                   data-tabindex="1"
	                   name="endTime" type="text" class="form-control" placeholder="请选择结束时间">
	            <span v-cloak v-if="$validation.endtime.invalid && submitBtnClick" class="help-absolute">
	              <span v-for="error in $validation.endtime.errors">
	                {{error.message}} {{($index !== ($validation.endtime.errors.length -1)) ? ',':''}}
	              </span>
	            </span>
	          </div>
	        </div>
	
	        <div class="form-group" :class="{'has-error':$validation.days.invalid && submitBtnClick}">
	          <label class="col-sm-2 control-label">请假天数</label>
	          <div class="col-sm-8">
	            <input v-model="vacation.days"
	                   v-validate:days="{
	                     required:{rule:true,message:'请输入请假天数'},
	                     validDays:{rule:true,message:'请假天数为整数或小数'}
	                   }"
	                   data-tabindex="1"
	                   readonly
	                   name="companyName" type="text" class="form-control" placeholder="请输入请假天数">
	            <span v-cloak v-if="$validation.days.invalid && submitBtnClick" class="help-absolute">
	              <span v-for="error in $validation.days.errors">
	                {{error.message}} {{($index !== ($validation.days.errors.length -1)) ? ',':''}}
	              </span>
	            </span>
	          </div>
	        </div>
	
	        <div class="form-group" :class="{'has-error':$validation.reason.invalid && submitBtnClick}">
	          <label class="col-sm-2 control-label">请假事由</label>
	          <div class="col-sm-8">
	            <textarea v-model="vacation.reason"
	                      v-validate:reason="{
	                        required:{rule:true,message:'请输入请假事由'},
	                        maxlength:{rule:255,message:'请假事由最长不能超过255个字符'}
	                      }"
	                      class="form-control"
	                      placeholder="请输入请假事由">
	            </textarea>
	            <span v-cloak v-if="$validation.reason.invalid && submitBtnClick" class="help-absolute">
	              <span v-for="error in $validation.reason.errors">
	                {{error.message}} {{($index !== ($validation.reason.errors.length -1)) ? ',':''}}
	              </span>
	            </span>
	          </div>
	        </div>
	      </div>
	      <div class="text-center" >
	        <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;
	        <button @click="save" :disabled="disabled" type="button" class="btn btn-info">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
	        <button @click="cancel" type="button" data-dismiss="modal" class="btn">返回</button>
	      </div>
	    </form>
	  </validator>
</div>
</div>
<script src="${ctx}/static/js/containers/vacation/edit.js"></script>