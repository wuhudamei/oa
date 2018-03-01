<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link href="${ctx}/static/css/zTreeStyle.css" rel="stylesheet">
<title>采购申请</title>
<div id="container" class="wrapper wrapper-content">
  <div id="breadcrumb">
    <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
  </div>
  <!-- ibox start -->
  <div class="ibox-content"  v-if="isWeChat"> 
        <h2 class="text-center">此数据格式只适用于pc端填写</h2>
        </div>
  <div class="ibox-content"  v-if="!isWeChat">
	  <validator name="validation">
	    <form name="createMirror" novalidate class="form-horizontal" role="form">
	      <div class="modal-body">
			  <div v-if="purchase.applyTitle!=null" class="form-group" :class="{'has-error':$validation.goodname.invalid && submitBtnClick}">
				  <label class="col-sm-2 control-label">申请标题</label>
				  <div class="col-sm-8" style="margin-top: 8px;">
					  {{purchase.applyTitle}}
				  </div>
			  </div>
			  <div v-if="purchase.applyCode!=null" class="form-group" :class="{'has-error':$validation.goodname.invalid && submitBtnClick}">
				  <label class="col-sm-2 control-label">申请编码</label>
				  <div class="col-sm-8"  style="margin-top: 8px;">
					  {{purchase.applyCode}}
				  </div>
			  </div>
			  <input type="hidden" id="userId" name="userId" v-model="businessAway.userId">
			  <div class="form-group" :class="{'has-error':$validation.childid.invalid && submitBtnClick}">
				  <label for="childid" class="col-sm-2 control-label">科目</label>
				  <div class="col-sm-8">
					  <select
							  v-validate:childid="{required:true}"
							  v-model="purchase.secondTypeId"
							  id="childid"
							  name="childid"
							  data-tabindex="1"
							  class="form-control">
						  <option value="">请选择科目</option>
						  <%--v-if="parent.type==selectType"--%>
						  <option  v-for="child in children"
								   :value="child.id">
							  {{child.name}}
						  </option>
					  </select>

					  <div v-if="$validation.childid.invalid && $validation.touched"
						   class="help-absolute">
						  <span v-if="$validation.childid.invalid">请选择科目</span>
					  </div>
				  </div>
			  </div>


			  <div class="form-group" :class="{'has-error':$validation.goodname.invalid && submitBtnClick}">
				  <label for="goodname" class="col-sm-2 control-label">物品名称</label>
				  <div class="col-sm-8">
					  <input v-model="purchase.goodName"
							 v-validate:goodname="{
                                    required:{rule:true,message:'请输入物品名称'}
                                }"
							 data-tabindex="4"
							 id="goodname" name="goodname" type="text" class="form-control" placeholder="请输入物品名称">
					  <span v-cloak v-if="$validation.goodname.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.goodname.errors">
                {{error.message}} {{($index !== ($validation.goodname.errors.length -1)) ? ',':''}}
              </span>
            </span>
				  </div>
			  </div>
			  <div class="form-group" :class="{'has-error':$validation.purchaseMonth.invalid && submitBtnClick}">
				  <label for="purchaseMonth" class="col-sm-2 control-label">采购月份</label>
				  <div class="col-sm-8">
					  <input v-model="purchase.purchaseMonth"
							 v-validate:purchase-month="{
                                    required:{rule:true,message:'请输入采购月份'}
                                }"
							 v-el:purchase-month
							 data-tabindex="4"
							 readonly
							 id="purchaseMonth" name="purchaseMonth" type="text" class="form-control datepicker" placeholder="请选择月份">
					  <span v-cloak v-if="$validation.purchaseMonth.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.purchaseMonth.errors">
                {{error.message}} {{($index !== ($validation.purchaseMonth.errors.length -1)) ? ',':''}}
              </span>
            </span>
				  </div>
			  </div>
			  <div class="form-group" :class="{'has-error':$validation.goodNum.invalid && submitBtnClick}">
				  <label for="goodNum" class="col-sm-2 control-label">数量</label>
				  <div class="col-sm-8">
					  <input v-model="purchase.goodNum"
							 v-validate:good-num="{
                                    required:{rule:true,message:'请输入数量'},
                                    min:0,
                                    max:99999999.99
                                }"
							 data-tabindex="4"
							 type="number"
							 maxlength="20"
							 id="goodNum" name="goodNum" type="text" class="form-control" placeholder="请输入数量">
					  <span v-cloak v-if="$validation.goodNum.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.goodNum.errors">
                {{error.message}} {{($index !== ($validation.goodNum.errors.length -1)) ? ',':''}}
              </span>
            </span>
				  </div>
			  </div>
			  <div class="form-group" :class="{'has-error':$validation.price.invalid && submitBtnClick}">
				  <label for="price" class="col-sm-2 control-label">单价</label>
				  <div class="col-sm-8">
					  <input v-model="purchase.goodPrice"
							 v-validate:price="{
                                    required:{rule:true,message:'请输入单价'},
                                    min:0,
                                    max:99999999.99
                                }"
							 type="number"
							 maxlength="20"
							 data-tabindex="4"
							 id="price" name="price" type="text" class="form-control" placeholder="请输入单价">
					  <span v-cloak v-if="$validation.price.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.price.errors">
                {{error.message}} {{($index !== ($validation.price.errors.length -1)) ? ',':''}}
              </span>
            </span>
				  </div>
			  </div>

			  <div class="form-group" :class="{'has-error':$validation.totalPrice.invalid && submitBtnClick}">
				  <label for="totalPrice" class="col-sm-2 control-label">总价</label>
				  <div class="col-sm-8">
					  <input v-model="purchase.totalPrice"
							 v-validate:total-price="{
                                    required:{rule:true,message:'请输入总价'},
                                    min:0,
                                    max:99999999.99
                                }"
							 type="number"
							 maxlength="20"
							 data-tabindex="4"
							 id="totalPrice" name="totalPrice" type="text" class="form-control" placeholder="请输入总价">
					  <span v-cloak v-if="$validation.totalPrice.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.totalPrice.errors">
                {{error.message}} {{($index !== ($validation.totalPrice.errors.length -1)) ? ',':''}}
              </span>
            </span>
				  </div>
			  </div>

			  <div class="form-group" :class="{'has-error':$validation.description.invalid && submitBtnClick}">
				  <label for="description" class="col-sm-2 control-label">物品用途说明</label>
				  <div class="col-sm-8">
            <textarea
					v-model="purchase.description"
					v-validate:description="{
                                    required:{rule:true,message:'请输入物品用途说明'}
                                }"
					maxlength="50"
					id="description"
					name="description"
					class="form-control"
					rows="3"
					placeholder="请输入物品用途说明">
            </textarea>
					  <span v-cloak v-if="$validation.description.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.description.errors">
                {{error.message}} {{($index !== ($validation.description.errors.length -1)) ? ',':''}}
              </span>
            </span>
				  </div>
			  </div>
	      </div>
	      <div class="text-center" >
	        <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>&nbsp;&nbsp;&nbsp;&nbsp;
	        <button @click="save" :disabled="disabled" type="button" class="btn">保存</button>&nbsp;&nbsp;&nbsp;&nbsp;
	        <button @click="cancel" type="button" data-dismiss="modal" class="btn">返回</button>
	      </div>
	    </form>
	  </validator>
</div>
</div>
<script src="${ctx}/static/js/containers/purchase/add.js"></script>