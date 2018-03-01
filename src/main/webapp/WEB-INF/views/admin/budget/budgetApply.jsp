<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>我的预算事务</title>
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
                <form name="createBudget" novalidate class="form-horizontal" role="form">
                    <!-- 编辑模式显示 -->
                    <div class="form-group" v-if="isEdit">
                        <div class="col-md-2 text-right">
                            <label>申请编码：</label>
                        </div>
                        <div class="col-md-4">
                            <label>{{budget.applyCode}}</label>
                        </div>
                        <div class="col-md-2 text-right">
                            <label>申请标题：</label>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label>{{budget.applyTitle}}</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-2 text-right">
                            <label>预算公司：</label>
                        </div>
                        <div class="col-md-4">
                            <label>{{user.company}}</label>
                        </div>
                        <div class="col-md-2 text-right">
                            <label>预算月份：</label>
                        </div>
                        <div class="col-md-2" style="margin-top:-6px">
                            <input v-model="budget.budgetDate"
                                   v-el:budget-date
                                   id="budgetDate"
                                   v-validate:budgetdate="{required:{rule:true,message:'请选择预算月份'}}"
                                   maxlength="20"
                                   data-tabindex="1"
                                   readonly
                                   name="budgetDate" type="text" class="form-control datetime input-sm"
                                   placeholder="预算月份">
                            <span v-cloak v-if="$validation.budgetdate.invalid && submitBtnClick"
                                  class="help-absolute">
	                            <span v-for="error in $validation.budgetdate.errors">
	                                {{error.message}} {{($index !== ($validation.budgetdate.errors.length -1)) ? ',':''}}
	                            </span>
                            </span>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-md-2 text-right">
                            <label>提交人：</label>
                        </div>
                        <div class="col-md-4">
                            <label>{{user.name}}</label>
                        </div>
                        <div class="col-md-2 text-right">
                            <label>申请时间：</label>
                        </div>
                        <div class="col-md-2">
                            <label>{{applyDate}}</label>
                        </div>
                    </div>

                    <hr/>

                    <div class="form-group">
                        <div class="col-md-2 text-right">
                            <label>预算总额：</label>
                        </div>
                        <div class="col-md-4">
                            <label>{{totalMoney}}</label>
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
                        <div class="col-sm-1 text-right">
                            <a v-if="budget.attachment != '' && budget.attachment != null"
                               :href="budget.attachment"
                               class="btn btn-sm btn-primary">
                                下载附件
                            </a>
                        </div>
                        <div class="col-sm-1 text-right">
                            <button @click="deleteAttachment()"
                                    v-if="budget.attachment != '' && budget.attachment != null" type="button"
                                    class="btn btn-sm btn-danger">
                                删除附件
                            </button>
                        </div>
                    </div>
                    <div class="form-group" style="text-align:-webkit-center">
                        <table class="table table-bordered table-hover table-striped" style="width:95%;">
                            <thead>
                            <tr>
                                <th>费用科目</th>
                                <th>科目说明</th>
                                <th>预算金额</th>
                                <th>预算说明</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="budgetItem in budget.budgetDetails">
                                <td>{{budgetItem.costItemName}}</td>
                                <td>包含：{{budgetItem.costDetailNames}}</td>
                                <td><input type="number" class="form-control" style="border-style:none"
                                           v-model="budgetItem.money"/></td>
                                <td><input type="text" class="form-control" style="border-style:none"
                                           v-model="budgetItem.remark"/></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="form-group" :class="{'has-error':$validation.remark.invalid && submitBtnClick}">
                        <label for="budgetDescribe" class="col-sm-2 control-label">预算说明:</label>
                        <div class="col-sm-8">
                            <textarea v-model="budget.remark"
                                      id="budgetDescribe"
                                      v-validate:remark="{required:{rule:true,message:'请输入预算说明'}}"
                                      maxlength="200"
                                      data-tabindex="1"
                                      name="budgetDescribe" class="form-control datetime input-sm"
                                      placeholder="预算说明"></textarea>
                            <span v-cloak v-if="$validation.remark.invalid && submitBtnClick"
                                  class="help-absolute">
                            <span v-for="error in $validation.remark.errors">
                                {{error.message}} {{($index !== ($validation.remark.errors.length -1)) ? ',':''}}
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
                        <button @click="submitOrSave2Draft('DRAFT')" :disabled="disabled" type="button"
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
</div>
<!-- container end-->
<script src="${ctx}/static/vendor/webuploader/webuploader.js"></script>
<script src="${ctx}/static/js/components/webuploader.js"></script>
<script src="${ctx}/static/js/containers/budget/apply.js?v=1.0"></script>
