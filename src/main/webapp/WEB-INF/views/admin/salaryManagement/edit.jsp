<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<link href="${ctx}/static/css/zTreeStyle.css" rel="stylesheet">
<link rel="stylesheet" href="/static/vendor/webuploader/webuploader.css">

<title>编辑工资</title>
<div id="container" class="wrapper wrapper-content">
	<div id="breadcrumb">
		<bread-crumb :crumbs="breadcrumbs"></bread-crumb>
	</div>
	<!-- ibox start -->
	<div class="ibox-content">
		<validator name="validation">
		<form name="createMirror" novalidate class="form-horizontal"
			role="form">
			<div class="text-center">
				<h3>基本信息</h3>
			</div>
			<hr/>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">姓名</label>
							<div class="col-md-9">
								<input v-model="salaryDetail.empName"
									maxlength="20" name="name" type="text" class="form-control" disabled="disabled"/>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">员工编号</label>
							<div class="col-md-9">
								<input v-model="salaryDetail.orgCode"
									maxlength="20" name="orgCode" type="text" class="form-control" disabled="disabled"/>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">身份证号</label>
							<div class="col-md-9">
								<input v-model="salaryDetail.idNum" maxlength="20" name="idNum" type="text" class="form-control"  disabled="disabled"/>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">公司</label>
							<div class="col-md-9">
								<input v-model="salaryDetail.orgCompanyName"
									   data-tabindex="1" name="companyName" type="text"
									   class="form-control" disabled="disabled"/>
							</div>
						</div>
					</div>
				</div>
			</div>


			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">部门</label>
							<div class="col-md-9">
								<input v-model="salaryDetail.orgDepartmentName"
									   maxlength="20" name="orgName" type="text" class="form-control" disabled="disabled"/>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">岗位</label>
							<div class="col-md-9">
								<input v-model="salaryDetail.position"
										   maxlength="50" name="position" type="text" class="form-control" disabled="disabled"/>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">开户行</label>
							<div class="col-md-9"
								 :class="{'has-error':$validation.bankOfDeposit.invalid && submitBtnClick}">
								<input v-model="salaryDetail.bankOfDeposit"
									   v-validate:bank-of-deposit="{
                      required:{rule:true,message:'请输入开户行'},
                      maxlength:{rule:50,message:'邮箱最长不能超过50个字符'}
                     }"
									   maxlength="50" name="bankOfDeposit" type="text" class="form-control" />
								<span v-cloak v-if="$validation.bankOfDeposit.invalid && submitBtnClick"
									  class="help-absolute"> <span
										v-for="error in $validation.email.errors">
										{{error.message}} {{($index !==
										($validation.email.errors.length -1)) ? ',':''}} </span>
								</span>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">银行卡号</label>

							<div class="col-md-9"
								 :class="{'has-error':$validation.creditCardNumbers.invalid && submitBtnClick}">
								<input v-model="salaryDetail.creditCardNumbers"
									   v-validate:credit-card-numbers="['card']"
									   maxlength="20" name="creditCardNumbers" type="text" class="form-control" onkeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))"/>
								<span v-cloak v-if="$validation.creditCardNumbers.invalid && submitBtnClick"
									  class="help-absolute"> <span
										v-for="error in $validation.creditCardNumbers.errors">
										{{error.message}} {{($index !==($validation.creditCardNumbers.errors.length -1)) ? ',':''}} </span>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">应出勤天数</label>
							<div class="col-sm-9" :class="{'has-error':$validation.shouldWorkDays.invalid && submitBtnClick}">
								<input v-model="salaryDetail.shouldWorkDays"
									   v-validate:should-work-days="['days']"
									   name="shouldWorkDays" type="text" class="form-control" onkeypress="return (/[\d{2}.]/.test(String.fromCharCode(event.keyCode)))">
								<span v-cloak v-if="$validation.shouldWorkDays.invalid && submitBtnClick" class="help-absolute"/>
								<span v-for="error in $validation.shouldWorkDays.errors">
									{{error.message}} {{($index !== ($validation.shouldWorkDays.errors.length -1)) ? ',':''}}
								  </span>
								</span>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">实际出勤天数</label>
							<div class="col-md-9" :class="{'has-error':$validation.practicalWorkDays.invalid && submitBtnClick}">
								<input v-model="salaryDetail.practicalWorkDays"
									   v-validate:practical-work-days="['days2']"
									   name="practicalWorkDays" type="text" class="form-control" onkeypress="return (/[\d{2}.]/.test(String.fromCharCode(event.keyCode)))">
								<span v-cloak v-if="$validation.practicalWorkDays.invalid && submitBtnClick" class="help-absolute"/>
								<span v-for="error in $validation.practicalWorkDays.errors">
									{{error.message}} {{($index !== ($validation.practicalWorkDays.errors.length -1)) ? ',':''}}
								  </span>
								</span>

							</div>
						</div>
					</div>
				</div>
			</div>


			<div class="text-center">
				<h3>薪资信息</h3>
			</div>
			<hr />

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">基本工资</label>
							<div class="col-sm-9" :class="{'has-error':$validation.salaryBasicDn.invalid && submitBtnClick}">
								<input v-model="salaryDetail.salaryBasicDn"
									   v-validate:salary-basic-dn="['num']"
									   id="salaryBasic" name="salaryBasic" type="number" class="form-control" onkeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))">
								<span v-cloak v-if="$validation.salaryBasicDn.invalid && submitBtnClick" class="help-absolute" />
								  <span v-for="error in $validation.salaryBasicDn.errors">
									{{error.message}} {{($index !== ($validation.salaryBasicDn.errors.length -1)) ? ',':''}}
								  </span>
                        		</span>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">餐补</label>
							<div class="col-md-9"
									 :class="{'has-error':$validation.mealSubsidyDn.invalid && submitBtnClick}">
								<input v-model="salaryDetail.mealSubsidyDn"
									   v-validate:meal-subsidy-dn="['num']"
									   maxlength="50"
									   data-tabindex="11"
									   id="mealSubsidy" name="mealSubsidyDn" type="number" class="form-control" onkeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))"/>
								<span v-cloak v-if="$validation.mealSubsidyDn.invalid && submitBtnClick" class="help-absolute">
								  <span v-for="error in $validation.mealSubsidyDn.errors">
									{{error.message}} {{($index !== ($validation.mealSubsidyDn.errors.length -1)) ? ',':''}}
								  </span>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">其他补助</label>
							<div class="col-md-9"
								 :class="{'has-error':$validation.otherSubsidyDn.invalid && submitBtnClick}">
								<input v-model="salaryDetail.otherSubsidyDn"
									   v-validate:other-subsidy-dn="['num']"
									   maxlength="50"
									   data-tabindex="11"
									   name="otherSalary" type="number" class="form-control" onkeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))"/>
								<span v-cloak v-if="$validation.otherSubsidyDn.invalid && submitBtnClick" class="help-absolute">
								  <span v-for="error in $validation.otherSubsidyDn.errors">
									{{error.message}} {{($index !== ($validation.otherSubsidyDn.errors.length -1)) ? ',':''}}
								  </span>
								</span>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">补偿金</label>
							<div class="col-md-9"
								 :class="{'has-error':$validation.compensationDn.invalid && submitBtnClick}">
								<input v-model="salaryDetail.compensationDn"
									   v-validate:compensation-dn="['num']"
									   maxlength="50"
									   data-tabindex="11"
									   name="otherSalary" type="number" class="form-control" onkeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))"/>
								<span v-cloak v-if="$validation.compensationDn.invalid && submitBtnClick" class="help-absolute" >
								  <span v-for="error in $validation.compensationDn.errors">
									{{error.message}} {{($index !== ($validation.compensationDn.errors.length -1)) ? ',':''}}
								  </span>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>


			<div class="text-center" style="margin-top: 20px">
				<h3>社保,公积金以及其他扣款项目</h3>
			</div>
			<hr />

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">社保扣款</label>
							<div class="col-md-9"
								 :class="{'has-error':$validation.socialSecurityPersonalDn.invalid && submitBtnClick}">
								<input v-model="salaryDetail.socialSecurityPersonalDn"
									   v-validate:social-security-personal-dn="['num']"
									   maxlength="50"
									   data-tabindex="11"
									   name="otherSalary" type="number" class="form-control" onkeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))"/>
								<span v-cloak v-if="$validation.socialSecurityPersonalDn.invalid && submitBtnClick" class="help-absolute">
								  <span v-for="error in $validation.socialSecurityPersonalDn.errors">
									{{error.message}} {{($index !== ($validation.socialSecurityPersonalDn.errors.length -1)) ? ',':''}}
								  </span>
								</span>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">公积金</label>
							<div class="col-md-9"
								 :class="{'has-error':$validation.housingFundDn.invalid && submitBtnClick}">
								<input v-model="salaryDetail.housingFundDn"
									   v-validate:housing-fund-dn="['num']"
									   maxlength="50"
									   data-tabindex="11"
									   name="otherSalary" type="number" class="form-control" onkeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))"/>
								<span v-cloak v-if="$validation.housingFundDn.invalid && submitBtnClick" class="help-absolute">
								  <span v-for="error in $validation.housingFundDn.errors">
									{{error.message}} {{($index !== ($validation.housingFundDn.errors.length -1)) ? ',':''}}
								  </span>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">其他扣款</label>
							<div class="col-md-9"
								 :class="{'has-error':$validation.deductMoneyDn.invalid && submitBtnClick}">
								<input v-model="salaryDetail.deductMoneyDn"
									   v-validate:deduct-money-dn="['num']"
									   maxlength="50"
									   data-tabindex="11"
									   name="otherSalary" type="number" class="form-control" onkeypress="return (/[\d.]/.test(String.fromCharCode(event.keyCode)))"/>
								<span v-cloak v-if="$validation.deductMoneyDn.invalid && submitBtnClick" class="help-absolute">
								  <span v-for="error in $validation.deductMoneyDn.errors">
									{{error.message}} {{($index !== ($validation.deductMoneyDn.errors.length -1)) ? ',':''}}
								  </span>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="text-center" style="margin-top: 20px">
				<h3>统计</h3>
			</div>
			<hr />
			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">应发基本工资</label>
							<div class="col-md-9">
								<input v-model="salaryDetail.shouldBasicSalaryDn" maxlength="20"
									   name="shouldBasicSalary" type="text" class="form-control" disabled="disabled"/>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">应税工资</label>
							<div class="col-md-9">
								<input v-model="salaryDetail.salaryTaxableDn" maxlength="20"
									   name="salaryTaxable" type="text" class="form-control" disabled="disabled"/>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">个人所得税</label>
							<div class="col-md-9">
								<input v-model="salaryDetail.individualIncomeTaxDn" maxlength="20"
									   name="individualIncomeTax" type="text" class="form-control" disabled="disabled"/>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">实发工资</label>
							<div class="col-md-9">
								<input v-model="salaryDetail.practicalSalaryTotalDn" maxlength="20"
									   name="practicalSalaryTotal" type="text" class="form-control" disabled="disabled"/>
							</div>
						</div>
					</div>
				</div>

			</div>
			<br /> <br />
			<div class="text-center">
				<button @click="submit" :disabled="disabled" type="button"
						class="btn btn-primary">提交</button>
				<button @click="cancel" type="button" class="btn btn-default">取消
				</button>
			</div>
		</form>
		</validator>
		<!-- ibox end -->
	</div>
</div>


<%@include file="/WEB-INF/views/admin/components/ztree.jsp"%>
<script src="${ctx}/static/js/components/ztree.js"></script>
<script src="${ctx}/static/js/jquery.ztree.core.js"></script>
<script src="${ctx}/static/js/jquery.ztree.excheck.js"></script>
<script src="${ctx}/static/vendor/webuploader/webuploader.js"></script>
<script src="${ctx}/static/js/components/webuploader.js"></script>
<script src="${ctx}/static/js/containers/salaryManagement/edit.js"></script>