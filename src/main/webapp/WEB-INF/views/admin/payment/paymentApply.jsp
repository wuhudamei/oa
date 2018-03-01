<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>我的报销事物</title>
<link rel="stylesheet" href="/static/vendor/webuploader/webuploader.css">
<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox" v-cloak>
        <div class="ibox-content" v-if="isWeChat">
            <h2 class="text-center">此数据格式只适用于pc端填写</h2>
        </div>
        <div class="ibox-content" v-if="!isWeChat">
            <validator name="validation">
                <h2 class="text-center">{{title}}</h2>
                <hr/>
                <form name="createPayment" novalidate class="form-horizontal" role="form">

                    <div class="form-group">
                        <div class="col-md-2 text-right">
                            <label>申请编码：</label>
                        </div>
                        <div class="col-md-4">
                            <label>{{payment.applyCode}}</label>
                        </div>
                         <div class="col-md-2 text-right">
                            <label>申请时间：</label>
                        </div>
                        <div class="col-md-4">
                            <label>{{applyDate}}</label>
                        </div>
                    </div>
                    <div class="form-group">
                      <div class="col-md-2 text-right">
                            <label>报销公司：</label>
                        </div>
                        <div class="col-md-4">
                            <label>{{user.company}}</label>
                        </div>
                        <div class="col-md-2 text-right">
                            <label>提交人：</label>
                        </div>
                        <div class="col-md-4">
                            <label>{{user.name}}</label>
                        </div>
					</div>
                     <div class="form-group">
                        <div class="col-md-2 text-right">
                            <label id="paymentDate">报销月份：<span style="color: red">*</span></label>
                        </div>
                        <div class="col-md-4" style="margin-top:-6px">
                            <input v-model="payment.paymentDate"
                                   v-el:payment-date
                                   id="paymentDate"
                                   v-validate:paymentdate="{required:{rule:true,message:'请选择报销月份'}}"
                                   maxlength="20"
                                   data-tabindex="1"
                                   readonly
                                   name="paymentDate" type="text" class="form-control datetime input-sm"
                                   placeholder="报销月份"
                                   style="width: 154px;">
                            <span v-cloak v-if="$validation.paymentdate.invalid && submitBtnClick"
                                  class="help-absolute">
                            <span v-for="error in $validation.paymentdate.errors">
                                {{error.message}} {{($index !== ($validation.paymentdate.errors.length -1)) ? ',':''}}
                            </span>
                            </span>
                        </div>
                         <div :class="{'has-error':$validation.applyTitle.invalid && submitBtnClick}">
                            <label for="applyTitle" class="col-md-2 control-label">申请标题:<span
                                    style="color: red">*</span></label>
                            <div class="col-sm-4">
                                <input v-model="payment.applyTitle"
                                       id="applyTitle"
                                       maxlength="50"
                                       data-tabindex="1"
                                       v-validate:apply-title="{required:{rule:true,message:'请输入申请标题'}}"
                                       name="applyTitle" type="text" class="form-control"
                                       placeholder="申请标题">
                                </input>
                                <span v-cloak v-if="$validation.applyTitle.invalid && submitBtnClick"
                                      class="help-absolute">
                                       <span v-for="error in $validation.applyTitle.errors">
                                        {{error.message}} {{($index !== ($validation.applyTitle.errors.length -1)) ? ',':''}}
                                       </span>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div>
                            <div :class="{'has-error':$validation.applyType.invalid && submitBtnClick}">
                                <label for="applyType" class="col-md-2 control-label">费用类型：<span
                                        style="color: red">*</span></label>
                                <div class="col-sm-2">
                                    <select v-model="payment.type"
                                            id="applyType"
                                            maxlength="50"
                                            data-tabindex="1"
                                            v-validate:apply-type="{required:{rule:true,message:'选择费用类型'}}"
                                            name="applyType" type="text" class="form-control"
                                            placeholder="费用类型">
                                        <option value="">请选择费用类型</option>
                                        <option v-for="type in subjects" :value="type.id">{{type.name}}</option>
                                    </select>
                                    <span v-cloak v-if="$validation.applyType.invalid && submitBtnClick"
                                          class="help-absolute">
                                        <span v-for="error in $validation.applyType.errors">
                                            {{error.message}} {{($index !== ($validation.applyType.errors.length -1)) ? ',':''}}
                                        </span>
                                    </span>
                                </div>
                                <div class="col-sm-2"></div>
                            </div>
                             <div :class="{'has-error':$validation.costItem.invalid && submitBtnClick}">
                                <label for="costItem" class="col-md-2 control-label">费用科目：<span
                                        style="color: red">*</span></label>
                                <div class="col-sm-2">
                                    <select v-model="payment.costItem"
                                            id="costItem"
                                            maxlength="50"
                                            data-tabindex="1"
                                   			 <%--v-validate:cost-item="{required:{rule:true,message:'选择费用科目'}}"--%>
                                            name="costItem" type="text" class="form-control"
                                            placeholder="费用科目">
                                        <option value="">请选择费用科目</option>
                                        <option v-for="costItem in costItems" :value="costItem.id">{{costItem.name}}
                                        </option>
                                    </select>
                                    <span v-cloak v-if="$validation.costItem.invalid && submitBtnClick"
                                          class="help-absolute">
                                        <span v-for="error in $validation.costItem.errors">
                                            {{error.message}} {{($index !== ($validation.costItem.errors.length -1)) ? ',':''}}
                                        </span>
                                    </span>
                                </div>
                            </div>
                        </div>
                        <div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-2 control-label">
                            <label>费用申请单：</label>
                        </div>
                        <div class="col-md-4">
                            <select class="form-control" v-model="payment.paymentSignatureId">
                                <option value=''>请选择费用申请单</option>
                                <option v-for="signature in signatures" :value="signature.id">{{signature.applyTitle}}
                                </option>
                            </select>
                        </div>
                        <div class="col-md-2 text-right">
                            <label class="">
                                <web-uploader
                                        :type="webUploaderSub.type" :w-server="webUploaderSub.server"
                                        :w-accept="webUploaderSub.accept"
                                        :w-file-size-limit="webUploaderSub.fileSizeLimit"
                                        :w-file-single-size-limit="webUploaderSub.fileSingleSizeLimit"
                                        :w-form-data="{category:'APPLY_ATTACHMENT'}">
                                    <button type="button" class="btn btn-primary">上传附件</button>
                                </web-uploader>
                            </label>
                        </div>
                        <div class="col-sm-1 text-right" v-if="1==2">
                            <a v-if="payment.attachment != '' && payment.attachment != null"
                               :href="payment.attachment"
                               class="btn btn-sm btn-primary">
                                	下载附件
                            </a>
                        </div>
                        <div class="col-sm-1 text-right" v-if="1==2">
                            <button @click="deleteAttachment()"
                                    v-if="payment.attachment != '' && payment.attachment != null" type="button"
                                    class="btn btn-sm btn-danger">
                           	     删除附件
                            </button>
                        </div>
                    </div>
 					<hr /> 
                     <div class="form-group">
                    	<div class="col-md-2 text-right">
                            <label>附件列表：</label>
                        </div>
                   		<label v-for="src in attachments"> {{src.fileName}} <i
							class="fa fa-fw fa-close" style="height: 15px; width: 55px;"
							@click="removeImg(src,$index)"> <font
								color="blue">删除</font>
						</i>&nbsp;&nbsp;
						</label>
					</div>
                    <hr />
                    <div class="form-group" style="text-align:-webkit-center">
                        <table class="table table-bordered table-hover table-striped" style="width:85%;">
                            <thead>
                            <tr>
                                <th width="15%">费用日期</th>
                                <th width="15%">科目明细</th>
                                <th width="20%">费用说明</th>
                                <th width="15%">票据张数</th>
                                <th width="15%">报销金额</th>
                                <th width="5%" style="text-align:center">
                                    <button @click="showAddItemModal()" id="addPaymentItem" type="button"
                                            class="btn btn-outline btn-info">增加
                                    </button>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="paymentItem in payment.paymentDetails">
                                <td>{{paymentItem.costDate}}</td>
                                <td>{{paymentItem.costDetailName}}</td>
                                <td>{{paymentItem.remark}}</td>
                                <td>{{paymentItem.invoiceNum}}</td>
                                <td>{{paymentItem.money}}</td>
                                <td style="text-align:center">
                                    <button @click="deleteItem($index)" id="delPaymentItem"
                                            type="button"
                                            class="btn btn-outline btn-primary">删除
                                    </button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
      				<div class="form-group">
                        <div class="col-md-2 text-right">
                            <label>报销总额：</label>
                        </div>
                        <div class="col-md-4">
                            <label>{{payment.totalMoney}} &nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red">{{surpassMessage}}</span></label>
                        </div>
                        <div class="col-md-2 text-right">
                            <label>票据总数：</label>
                        </div>
                        <div class="col-md-4">
                            <label>{{payment.invoiceNum}}</label>
                        </div>
                    </div>
                    <div class="form-group" :class="{'has-error':$validation.paymentreason.invalid && submitBtnClick}">
                        <label for="paymentReason" class="col-sm-2 control-label">报销说明:<span
                                style="color: red">*</span></label>
                        <div class="col-sm-8">
                            <textarea v-model="payment.reason"
                                      id="paymentReason"
                                      v-validate:paymentreason="{required:{rule:true,message:'请输入报销说明'}}"
                                      maxlength="200"
                                      data-tabindex="1"
                                      name="paymentReason" class="form-control datetime input-sm"
                                      placeholder="报销说明"></textarea>
                            <span v-cloak v-if="$validation.paymentreason.invalid && submitBtnClick"
                                  class="help-absolute">
                            <span v-for="error in $validation.paymentreason.errors">
                                {{error.message}} {{($index !== ($validation.paymentreason.errors.length -1)) ? ',':''}}
                            </span>
                            </span>
                        </div>
                    </div>

                    <hr/>
                    <div class="text-center">
                        <button @click="submitOrSave2Draft('APPROVALING')" :disabled="disabled" type="button"
                                class="btn btn-primary">提交
                        </button>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <button style="display:none;" @click="submitOrSave2Draft('DRAFT')" :disabled="disabled" type="button"
                                class="btn btn-info">保存
                        </button>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <button @click="cancel" type="button" data-dismiss="modal" class="btn">返回</button>
                    </div>
                </form>
            </validator>

        </div>
    </div>
    <!-- ibox end -->

    <!--新增报销科目弹窗 -->
    <div id="modal" class="modal fade" tabindex="-1" data-width="800">
        <validator name="additemvalidation">
            <form name="addCostItem" novalidate class="form-horizontal" role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3>新增报销费用明细</h3>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <div :class="{'has-error':$additemvalidation.costdetail.invalid && addBtnClick}">
                            <label for="costDetail" class="col-md-2 control-label">费用明细:</label>
                            <div class="col-sm-4">
                                <select v-model="currentCostDetail"
                                        id="costDetail"
                                        v-validate:costdetail="{required:{rule:true,message:'选择费用明细'}}"
                                        maxlength="50"
                                        data-tabindex="1"
                                        name="costItem" type="text" class="form-control"
                                        placeholder="费用明细">
                                    <option value="">请选择费用明细</option>
                                    <option v-for="costDetail in costDetails" :value="costDetail">
                                        {{costDetail.name}}
                                    </option>
                                </select>
                                <span v-cloak v-if="$additemvalidation.costdetail.invalid && addBtnClick"
                                      class="help-absolute">
                            <span v-for="error in $additemvalidation.costdetail.errors">
                                {{error.message}} {{($index !== ($additemvalidation.costdetail.errors.length -1)) ? ',':''}}
                            </span>
                            </span>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div :class="{'has-error':$additemvalidation.costdate.invalid && addBtnClick}">
                            <label for="costDate" class="col-md-2 control-label">费用日期:</label>
                            <div class="col-sm-4">
                                <input v-model="newPaymentItem.costDate"
                                       v-el:cost-date
                                       id="costDate"
                                       maxlength="20"
                                       readonly
                                       data-tabindex="2"
                                       v-validate:costdate="{required:{rule:true,message:'选择费用日期'}}"
                                       name="costItem" type="text" class="form-control"
                                       placeholder="费用日期">
                                </input>
                                <span v-cloak v-if="$additemvalidation.costitem.invalid && addBtnClick"
                                      class="help-absolute">
                            <span v-for="error in $additemvalidation.costdate.errors">
                                {{error.message}} {{($index !== ($additemvalidation.costdate.errors.length -1)) ? ',':''}}
                            </span>
                            </span>
                            </div>
                        </div>

                    </div>

                    <div class="form-group">
                        <div :class="{'has-error':$additemvalidation.money.invalid && addBtnClick}">
                            <label for="money" class="col-md-2 control-label">报销金额:</label>
                            <div class="col-sm-4">
                                <input v-model="newPaymentItem.money"
                                       id="money"
                                       data-tabindex="3"
                                       v-validate:money="['num']"
                                       name="money" type="number" class="form-control"
                                       placeholder="请输入报销金额">
                                </input>
                                <span v-cloak v-if="$additemvalidation.money.invalid && addBtnClick"
                                      class="help-absolute">
                            <span v-for="error in $additemvalidation.money.errors">
                                {{error.message}} {{($index !== ($additemvalidation.money.errors.length -1)) ? ',':''}}
                            </span>
                            </span>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div :class="{'has-error':$additemvalidation.invoicenum.invalid && addBtnClick}">
                            <label for="itemInvoiceNum" class="col-md-2 control-label">票据张数:</label>
                            <div class="col-sm-4">
                                <input v-model="newPaymentItem.invoiceNum"
                                       id="itemInvoiceNum"
                                       maxlength="20"
                                       min="0"
                                       data-tabindex="4"
                                       v-validate:invoicenum="['ticket']"
                                       name="itemInvoiceNum" type="number" class="form-control"
                                       placeholder="请输入票据张数">
                                </input>
                                <span v-cloak v-if="$additemvalidation.invoicenum.invalid && addBtnClick"
                                      class="help-absolute">
                            <span v-for="error in $additemvalidation.invoicenum.errors">
                                {{error.message}} {{($index !== ($additemvalidation.invoicenum.errors.length -1)) ? ',':''}}
                            </span>
                            </span>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div :class="{'has-error':$additemvalidation.remark.invalid && addBtnClick}">
                            <label for="remark" class="col-sm-2 control-label">费用说明:</label>
                            <div class="col-sm-10">
                            <textarea v-model="newPaymentItem.remark"
                                      id="remark"
                                      v-validate:remark="{required:{rule:true,message:'请输入费用说明'}}"
                                      maxlength="200"
                                      data-tabindex="5"
                                      name="remark" class="form-control"
                                      placeholder="费用说明"></textarea>
                                <span v-cloak v-if="$additemvalidation.remark.invalid && addBtnClick"
                                      class="help-absolute">
                            <span v-for="error in $additemvalidation.remark.errors">
                                {{error.message}} {{($index !== ($additemvalidation.remark.errors.length -1)) ? ',':''}}
                            </span>
                            </span>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
                    <button @click="addNew" :disabled="disabled" type="button" class="btn btn-primary">添加
                    </button>
                </div>
            </form>
        </validator>
    </div>
</div>
<!-- container end-->
<script src="${ctx}/static/vendor/webuploader/webuploader.js"></script>
<script src="${ctx}/static/js/components/webuploader.js"></script>
<script src="${ctx}/static/js/containers/payment/apply.js?v=1.0"></script>
