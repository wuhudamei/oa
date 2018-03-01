<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<link href="${ctx}/static/css/zTreeStyle.css" rel="stylesheet">
<link rel="stylesheet" href="/static/vendor/webuploader/webuploader.css">

<title>编辑员工</title>
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
			<hr />

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">姓名</label>
							<div class="col-md-9"
								:class="{'has-error':$validation.name.invalid && submitBtnClick}">
								<input v-model="employee.name"
									v-validate:name="{
                      required:{rule:true,message:'请输入员工姓名'},
                      maxlength:{rule:20,message:'员工姓名最长不能超过20个字符'}
                     }"
									maxlength="20" name="name" type="text" class="form-control" />
								<span v-cloak v-if="$validation.name.invalid && submitBtnClick"
									class="help-absolute"> <span
									v-for="error in $validation.name.errors">
										{{error.message}} {{($index !==
										($validation.name.errors.length -1)) ? ',':''}} </span>
								</span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">员工编号</label>
							<div class="col-md-9"
								:class="{'has-error':$validation.orgcode.invalid && submitBtnClick}">
								<input v-model="employee.orgCode" readonly="readonly"
									v-validate:orgcode="{
                      required:{rule:true,message:'请输入员工编号'},
                      maxlength:{rule:20,message:'员工编号最长不能超过20个字符'}
                     }"
									maxlength="20" name="orgCode" type="text" class="form-control" />
								<span v-cloak
									v-if="$validation.orgcode.invalid && submitBtnClick"
									class="help-absolute"> <span
									v-for="error in $validation.orgcode.errors">
										{{error.message}} {{($index !==
										($validation.orgcode.errors.length -1)) ? ',':''}} </span>
								</span>
							</div>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">职务</label>
							<div class="col-md-9"
								:class="{'has-error':$validation.position.invalid && submitBtnClick}">
								<input v-model="employee.position"
									v-validate:position="{
                      required:{rule:true,message:'请输入员工职务'},
                      maxlength:{rule:20,message:'员工职务最长不能超过20个字符'}
                     }"
									maxlength="20" name="position" type="text" class="form-control" />
								<span v-cloak
									v-if="$validation.position.invalid && submitBtnClick"
									class="help-absolute"> <span
									v-for="error in $validation.position.errors">
										{{error.message}} {{($index !==
										($validation.position.errors.length -1)) ? ',':''}} </span>
								</span>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-2">
					<img :src="employee.photo" alt=""
						style="width: 105px; margin-left: 60px">
				</div>

				<div class="col-md-1">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label"> &nbsp; </label>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label"> &nbsp; </label>
						</div>
					</div>
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label"> <web-uploader
									:type="webUploaderSub.type" :w-server="webUploaderSub.server"
									:w-accept="webUploaderSub.accept"
									:w-file-size-limit="webUploaderSub.fileSizeLimit"
									:w-file-single-size-limit="webUploaderSub.fileSingleSizeLimit"
									:w-form-data="{category:'EMPLOYEE_PHOTO'}">
								<button type="button" class="btn btn-primary">上传证件照</button>
								</web-uploader>
							</label>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">手机号</label>
							<div class="col-md-9"
								:class="{'has-error':$validation.mobile.invalid && submitBtnClick}">
								<input v-model="employee.mobile"
									v-validate:mobile="{
                      required:{rule:true,message:'请输入员工手机号'},
                      maxlength:{rule:20,message:'手机号最长不能超过20个字符'},
                      mobile:{rule:true,message:'请输入正确的手机号'}
                     }"
									maxlength="20" name="mobile" type="text" class="form-control" />
								<span v-cloak
									v-if="$validation.mobile.invalid && submitBtnClick"
									class="help-absolute"> <span
									v-for="error in $validation.mobile.errors">
										{{error.message}} {{($index !==
										($validation.mobile.errors.length -1)) ? ',':''}} </span>
								</span>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">电子邮箱</label>
							<div class="col-md-9"
								:class="{'has-error':$validation.email.invalid && submitBtnClick}">
								<input v-model="employee.email"
									v-validate:email="{
                      required:{rule:true,message:'请输入员工邮箱'},
                      maxlength:{rule:50,message:'邮箱最长不能超过50个字符'},
                      email:{rule:true,message:'邮箱格式错误'}
                     }"
									maxlength="50" name="email" type="text" class="form-control" />
								<span v-cloak v-if="$validation.email.invalid && submitBtnClick"
									class="help-absolute"> <span
									v-for="error in $validation.email.errors">
										{{error.message}} {{($index !==
										($validation.email.errors.length -1)) ? ',':''}} </span>
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
							<label class="col-md-3 control-label">性别</label>
							<div class="col-md-9">
								<label class="radio-inline"> <input
									v-model="employee.gender" name="gender" type="radio"
									value="MALE" />男
								</label> <label class="radio-inline"> <input
									v-model="employee.gender" name="gender" type="radio"
									value="FEMALE" />女
								</label>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">入职日期</label>
							<div class="col-md-9"
								:class="{'has-error':$validation.entrydate.invalid && submitBtnClick}">
								<input v-model="employee.entryDate" readonly
									v-validate:entrydate="{
                      required:{rule:true,message:'请选择员工的入职日期'},
                     }"
									v-el:entry-date name="entryDate" type="text"
									class="form-control datepicker"> <span v-cloak
									v-if="$validation.entrydate.invalid && submitBtnClick"
									class="help-absolute"> <span
									v-for="error in $validation.entrydate.errors">
										{{error.message}} {{($index !==
										($validation.entrydate.errors.length -1)) ? ',':''}} </span>
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
							<label class="col-md-3 control-label">账号类型</label>
							<div class="col-md-9">
								<select v-model="employee.accountType" id="accountType"
									name="accountType" class="form-control">
									<option value="EMPLOYEE">内部账户</option>
									<option value="OUTSUPPLIER">外部账户</option>
								</select>
							</div>
						</div>
					</div>
				</div>

				<!--           <div class="col-md-6"> -->
				<!--             <div class="row"> -->
				<!--               <div class="form-group"> -->
				<!--                 <label class="col-md-3 control-label">入职日期</label> -->
				<!--                 <div class="col-md-9" :class="{'has-error':$validation.entrydate.invalid && submitBtnClick}"> -->
				<!--                   <input v-model="employee.entryDate" -->
				<!--                          readonly -->
				<!--                          v-validate:entrydate="{ -->
				<!--                       required:{rule:true,message:'请选择员工的入职日期'}, -->
				<!--                      }" -->
				<!--                          v-el:entry-date -->
				<!--                          name="entryDate" -->
				<!--                          type="text" -->
				<!--                          class="form-control datepicker"> -->
				<!--                   <span v-cloak v-if="$validation.entrydate.invalid && submitBtnClick" class="help-absolute"> -->
				<!--                     <span v-for="error in $validation.entrydate.errors"> -->
				<!--                       {{error.message}} {{($index !== ($validation.entrydate.errors.length -1)) ? ',':''}} -->
				<!--                     </span> -->
				<!--                   </span> -->
				<!--                 </div> -->
				<!--               </div> -->
				<!--             </div> -->
				<!--           </div> -->
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">机构</label>
							<div class="col-md-7"
								:class="{'has-error':$validation.org.invalid && submitBtnClick}">
								<input v-model="orgName" readonly name="orgName" type="text"
									class="form-control" /> <input v-model="employee.orgId"
									v-validate:org="{
                      required:{rule:true,message:'请选择员工所属机构'}
                     }"
									name="departmentId" type="hidden" /> <span v-cloak
									v-if="$validation.org.invalid && submitBtnClick"
									class="help-absolute"> <span
									v-for="error in $validation.org.errors">
										{{error.message}} {{($index !== ($validation.org.errors.length
										-1)) ? ',':''}} </span>
								</span>
							</div>

							<label class="col-md-2 control-label"
								style="text-align: left; padding: 0px 10px"
								v-clickoutside="clickOut">
								<button type="button" class="btn btn-primary btn-outline"
									@click="showOrgTree=true">选择机构</button> <z-tree
									v-ref:nodes-select :nodes="orgData"
									:show-tree.sync="showOrgTree" @on-click="selectOrg"
									:show-checkbox="false"> </z-tree>
							</label>
						</div>
					</div>
				</div>
			</div>

			<div class="text-center">
				<h3>其他信息</h3>
			</div>
			<hr />

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">最高学历</label>
							<div class="col-md-9">
								<input v-model="employee.education" maxlength="20"
									name="education" type="text" class="form-control" />
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">职称</label>
							<div class="col-md-9">
								<input v-model="employee.title" maxlength="50" name="title"
									type="text" class="form-control" />
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">籍贯</label>
							<div class="col-md-9">
								<input v-model="employee.nativePlace" name="nativePlace"
									type="text" class="form-control" />
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">身份证号</label>
							<div class="col-md-9">
								<input v-model="employee.idNum" maxlength="20" name="idNum"
									type="text" class="form-control" />
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">民族</label>
							<div class="col-md-9">
								<input v-model="employee.nation" maxlength="20" name="nation"
									type="text" class="form-control" />
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">政治面貌</label>
							<div class="col-md-9">
								<input v-model="employee.politicsStatus" maxlength="20"
									name="politicsStatus" type="text" class="form-control" />
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">户口所在地</label>
							<div class="col-md-9">
								<input v-model="employee.censusAddress" name="censusAddress"
									type="text" class="form-control" />
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">户口性质</label>
							<div class="col-md-9">
								<label class="radio-inline"> <input
									v-model="employee.censusNature" name="censusNature"
									type="radio" value="BJ_CITY" />北京城镇
								</label> <label class="radio-inline"> <input
									v-model="employee.censusNature" name="censusNature"
									type="radio" value="BJ_COUNTRY" />北京农村
								</label> <label class="radio-inline"> <input
									v-model="employee.censusNature" name="censusNature"
									type="radio" value="OTHER_CITY" />外地城镇
								</label> <label class="radio-inline"> <input
									v-model="employee.censusNature" name="censusNature"
									type="radio" value="OTHER_COUNTRY" />外地农村
								</label>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">现住址</label>
							<div class="col-md-9">
								<input v-model="employee.presentAddress" maxlength="100"
									name="presentAddress" type="text" class="form-control" />
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">婚姻状态</label>
							<div class="col-md-9">
								<label class="radio-inline"> <input
									v-model="employee.maritalStatus" name="maritalStatus"
									type="radio" value="MARRIED" />已婚
								</label> <label class="radio-inline"> <input
									v-model="employee.maritalStatus" name="maritalStatus"
									type="radio" value="UNMARRIED" />未婚
								</label>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">出生日期</label>
							<div class="col-md-9">
								<input v-model="employee.birthday" readonly v-el:birthday
									name="birthday" type="text" class="form-control datepicker">
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">英语水平</label>
							<div class="col-md-9">
								<label class="radio-inline"> <input
									v-model="employee.englishLevel" name="englishLevel"
									type="radio" value="MASTER" />精通
								</label> <label class="radio-inline"> <input
									v-model="employee.englishLevel" name="englishLevel"
									type="radio" value="PROFICIENCY" />熟练
								</label> <label class="radio-inline"> <input
									v-model="employee.englishLevel" name="englishLevel"
									type="radio" value="GENERAL" />一般
								</label> <label class="radio-inline"> <input
									v-model="employee.englishLevel" name="englishLevel"
									type="radio" value="BASE" />基本
								</label>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">用工类型</label>
							<div class="col-md-9">
								<label class="checkbox-inline"> <input
									v-model="employee.types" name="types" type="checkbox"
									value="PRACTICE" />实习学生
								</label> <label class="checkbox-inline"> <input
									v-model="employee.types" name="types" type="checkbox"
									value="SOLDIER" />在役军人
								</label> <label class="checkbox-inline"> <input
									v-model="employee.types" name="types" type="checkbox"
									value="DISABLED" />残疾人
								</label> <label class="checkbox-inline"> <input
									v-model="employee.types" name="types" type="checkbox"
									value="RETIRE" />退休
								</label> <label class="checkbox-inline"> <input
									v-model="employee.types" name="types" type="checkbox"
									value="FOREIGN" />外籍及港澳台人员
								</label> <label class="checkbox-inline"> <input
									v-model="employee.types" name="types" type="checkbox"
									value="OVERAGE" />超龄人员
								</label> <label class="checkbox-inline"> <input
									v-model="employee.types" name="types" type="checkbox"
									value="INSURANCE_IN_ORIGIN" />保险关系在原单位
								</label> <label class="checkbox-inline"> <input
									v-model="employee.types" name="types" type="checkbox"
									value="INSURANCE_IN_TALENT" />保险公司在人才或职介
								</label> <label class="checkbox-inline"> <input
									v-model="employee.types" name="types" type="checkbox"
									value="OTHER" />其他
								</label>
							</div>
						</div>
					</div>
				</div>
			</div>


			<div class="text-center" style="margin-top: 20px">
				<h3>紧急联系人信息</h3>
			</div>
			<hr />

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">姓名</label>
							<div class="col-md-9">
								<input v-model="employee.linkman1" maxlength="20"
									name="linkman1" type="text" class="form-control" />
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">电话</label>
							<div class="col-md-9">
								<input v-model="employee.linkphone1" maxlength="20"
									name="linkphone1" type="text" class="form-control" />
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">姓名</label>
							<div class="col-md-9">
								<input v-model="employee.linkman2" maxlength="20"
									name="linkman2" type="text" class="form-control" />
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">电话</label>
							<div class="col-md-9">
								<input v-model="employee.linkphone2" maxlength="20"
									name="linkphone2" type="text" class="form-control" />
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="text-center">
				<h3>
					教育经历
					<div class="text-right">
						<button @click="addEdu" type="button"
							class="btn btn-outline btn-success">添加</button>
					</div>
				</h3>
			</div>
			<hr />
			<table id="eduDataTable" width="100%"
				class="table table-striped table-bordered table-hover">
			</table>

			<div class="text-center" style="margin-top: 20px">
				<h3>
					工作经历
					<div class="text-right">
						<button @click="addWork" type="button"
							class="btn btn-outline btn-success">添加</button>
					</div>
				</h3>
			</div>
			<hr />
			<table id="workDataTable" width="100%"
				class="table table-striped table-bordered table-hover">
			</table>

			<div class="text-center" style="margin-top: 20px">
				<h3>与原单位解除劳动关系证明</h3>
			</div>
			<hr />

			<div class="col-md-12">
				<div class="form-group">
					<label class="col-md-1 control-label"> <input
						type="checkbox" v-model="employee.origProve" name="origProve">
					</label> <label class="text-left col-md-11" style="padding-top: 7px;">已提供同原单位解除劳动关系的证明文件;</label>
				</div>
			</div>

			<div class="col-md-12">
				<div class="form-group">
					<label class="col-md-1 control-label"> <input
						type="checkbox" v-model="employee.retireProve" name="retireProve">
					</label> <label class="text-left col-md-11" style="padding-top: 7px;">在原单位已退休，已提供退休证明文件;</label>
				</div>
			</div>

			<div class="col-md-12">
				<div class="form-group">
					<label class="col-md-1 control-label"> <input
						type="checkbox" v-model="employee.noProve" name="noProve">
					</label> <label class="text-left col-md-11" style="padding-top: 7px;">
						无法提供解除劳动关系的证明文件，已声明：若因与原单位未解除劳动关系而产生的法律纠纷和经济补偿一概由本人承担，与本公司无关。 </label>
				</div>
			</div>

			<br /> <br />
			<div class="text-center">
				<button @click="submit" :disabled="disabled" type="button"
					class="btn btn-primary">保存</button>
				<button @click="cancel" type="button" class="btn btn-default">取消
				</button>
			</div>
		</form>
		</validator>
		<!-- ibox end -->
	</div>
</div>

<div id="edu" class="modal fade" tabindex="-1" data-width="760">
	<validator name="validation">
	<form name="createMirror" novalidate class="form-horizontal"
		role="form">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3>教育经历</h3>
		</div>
		<div class="modal-body">
			<div class="form-group"
				:class="{'has-error':$validation.startdate.invalid && submitBtnClick}">
				<label class="col-sm-2 control-label">开始日期</label>
				<div class="col-sm-8">
					<input v-model="edu.startDate"
						v-validate:startdate="{required:{rule:true,message:'请选择开始日期'}}"
						readonly v-el:start-date data-tabindex="1" name="startDate"
						type="text" class="form-control" placeholder="请选择开始日期"> <span
						v-cloak v-if="$validation.startdate.invalid && submitBtnClick"
						class="help-absolute"> <span
						v-for="error in $validation.startdate.errors">
							{{error.message}} {{($index !==
							($validation.startdate.errors.length -1)) ? ',':''}} </span>
					</span>
				</div>
			</div>

			<div class="form-group"
				:class="{'has-error':$validation.enddate.invalid && submitBtnClick}">
				<label class="col-sm-2 control-label">结束日期</label>
				<div class="col-sm-8">
					<input v-model="edu.endDate"
						v-validate:enddate="{required:{rule:true,message:'请选择结束日期'}}"
						readonly v-el:end-date data-tabindex="1" name="endDate"
						type="text" class="form-control" placeholder="请选择结束日期"> <span
						v-cloak v-if="$validation.enddate.invalid && submitBtnClick"
						class="help-absolute"> <span
						v-for="error in $validation.enddate.errors">
							{{error.message}} {{($index !==
							($validation.enddate.errors.length -1)) ? ',':''}} </span>
					</span>
				</div>
			</div>


			<div class="form-group"
				:class="{'has-error':$validation.schoolname.invalid && submitBtnClick}">
				<label class="col-sm-2 control-label">学校名称</label>
				<div class="col-sm-8">
					<input v-model="edu.schoolName"
						v-validate:schoolname="{required:{rule:true,message:'请输入学校名称'},
                                    maxlength:{rule:20,message:'学校名称最长不能超过50个字符'}
                                }"
						data-tabindex="1" name="schoolName" type="text"
						class="form-control" placeholder="请输入学校名称"> <span v-cloak
						v-if="$validation.schoolname.invalid && submitBtnClick"
						class="help-absolute"> <span
						v-for="error in $validation.schoolname.errors">
							{{error.message}} {{($index !==
							($validation.schoolname.errors.length -1)) ? ',':''}} </span>
					</span>
				</div>
			</div>

			<div class="form-group"
				:class="{'has-error':$validation.subject.invalid && submitBtnClick}">
				<label class="col-sm-2 control-label">专业</label>
				<div class="col-sm-8">
					<input v-model="edu.subject"
						v-validate:subject="{
                      required:{rule:true,message:'请输入专业名称'},
                      maxlength:{rule:20,message:'专业名称最长不能超过50个字符'}
                   }"
						data-tabindex="1" name="subject" type="text" class="form-control"
						placeholder="请输入专业名称"> <span v-cloak
						v-if="$validation.subject.invalid && submitBtnClick"
						class="help-absolute"> <span
						v-for="error in $validation.subject.errors">
							{{error.message}} {{($index !==
							($validation.subject.errors.length -1)) ? ',':''}} </span>
					</span>
				</div>
			</div>

			<div class="form-group"
				:class="{'has-error':$validation.degree.invalid && submitBtnClick}">
				<label class="col-sm-2 control-label">学历</label>
				<div class="col-sm-8">
					<input v-model="edu.degree"
						v-validate:degree="{
                      required:{rule:true,message:'请输入学历'},
                      maxlength:{rule:20,message:'学历最长不能超过50个字符'}
                   }"
						data-tabindex="1" name="degree" type="text" class="form-control"
						placeholder="请输入学历"> <span v-cloak
						v-if="$validation.degree.invalid && submitBtnClick"
						class="help-absolute"> <span
						v-for="error in $validation.degree.errors">
							{{error.message}} {{($index !== ($validation.degree.errors.length
							-1)) ? ',':''}} </span>
					</span>
				</div>
			</div>
		</div>
		<div class="modal-footer">
			<button :disabled="disabled" type="button" data-dismiss="modal"
				class="btn">取消</button>
			<button @click="submit" :disabled="disabled" type="button"
				class="btn btn-primary">添加</button>
		</div>
	</form>
	</validator>
</div>

<div id="work" class="modal fade" tabindex="-1" data-width="760">
	<validator name="validation">
	<form name="createMirror" novalidate class="form-horizontal"
		role="form">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3>工作经历</h3>
		</div>
		<div class="modal-body">
			<div class="form-group"
				:class="{'has-error':$validation.startdate.invalid && submitBtnClick}">
				<label class="col-sm-2 control-label">开始日期</label>
				<div class="col-sm-8">
					<input v-model="work.startDate"
						v-validate:startdate="{required:{rule:true,message:'请选择开始日期'}}"
						readonly v-el:start-date data-tabindex="1" name="startDate"
						type="text" class="form-control" placeholder="请选择开始日期"> <span
						v-cloak v-if="$validation.startdate.invalid && submitBtnClick"
						class="help-absolute"> <span
						v-for="error in $validation.startdate.errors">
							{{error.message}} {{($index !==
							($validation.startdate.errors.length -1)) ? ',':''}} </span>
					</span>
				</div>
			</div>

			<div class="form-group"
				:class="{'has-error':$validation.enddate.invalid && submitBtnClick}">
				<label class="col-sm-2 control-label">结束日期</label>
				<div class="col-sm-8">
					<input v-model="work.endDate"
						v-validate:enddate="{required:{rule:true,message:'请选择结束日期'}}"
						readonly v-el:end-date data-tabindex="1" name="endDate"
						type="text" class="form-control" placeholder="请选择结束日期"> <span
						v-cloak v-if="$validation.enddate.invalid && submitBtnClick"
						class="help-absolute"> <span
						v-for="error in $validation.enddate.errors">
							{{error.message}} {{($index !==
							($validation.enddate.errors.length -1)) ? ',':''}} </span>
					</span>
				</div>
			</div>


			<div class="form-group"
				:class="{'has-error':$validation.companyname.invalid && submitBtnClick}">
				<label class="col-sm-2 control-label">公司名称</label>
				<div class="col-sm-8">
					<input v-model="work.companyName"
						v-validate:companyname="{required:{rule:true,message:'请输入公司名称'},
                                    maxlength:{rule:20,message:'公司名称最长不能超过50个字符'}
                                }"
						data-tabindex="1" name="companyName" type="text"
						class="form-control" placeholder="请输入公司名称"> <span v-cloak
						v-if="$validation.companyname.invalid && submitBtnClick"
						class="help-absolute"> <span
						v-for="error in $validation.companyname.errors">
							{{error.message}} {{($index !==
							($validation.companyname.errors.length -1)) ? ',':''}} </span>
					</span>
				</div>
			</div>

			<div class="form-group"
				:class="{'has-error':$validation.position.invalid && submitBtnClick}">
				<label class="col-sm-2 control-label">职位</label>
				<div class="col-sm-8">
					<input v-model="work.position"
						v-validate:position="{
                                    required:{rule:true,message:'请输入职位名称'},
                                    maxlength:{rule:20,message:'职位名称最长不能超过50个字符'}
                                }"
						data-tabindex="1" id="position" name="position" type="text"
						class="form-control" placeholder="请输入职位名称"> <span v-cloak
						v-if="$validation.position.invalid && submitBtnClick"
						class="help-absolute"> <span
						v-for="error in $validation.position.errors">
							{{error.message}} {{($index !==
							($validation.position.errors.length -1)) ? ',':''}} </span>
					</span>
				</div>
			</div>

			<div class="form-group"
				:class="{'has-error':$validation.duty.invalid && submitBtnClick}">
				<label class="col-sm-2 control-label">主要工作</label>
				<div class="col-sm-8">
					<input v-model="work.duty"
						v-validate:duty="{maxlength:{rule:20,message:'主要工作最长不能超过255个字符'}}"
						data-tabindex="1" name="duty" type="text" class="form-control"
						placeholder="请输入主要工作"> <span v-cloak
						v-if="$validation.duty.invalid && submitBtnClick"
						class="help-absolute"> <span
						v-for="error in $validation.duty.errors"> {{error.message}}
							{{($index !== ($validation.duty.errors.length -1)) ? ',':''}} </span>
					</span>
				</div>
			</div>

			<div class="form-group"
				:class="{'has-error':$validation.certifiername.invalid && submitBtnClick}">
				<label class="col-sm-2 control-label">证明人</label>
				<div class="col-sm-8">
					<input v-model="work.certifierName"
						v-validate:certifiername="{maxlength:{rule:20,message:'证明人姓名最长不能超过255个字符'}}"
						maxlength="20" data-tabindex="1" name="duty" type="text"
						class="form-control" placeholder="请输入证明人姓名"> <span v-cloak
						v-if="$validation.certifiername.invalid && submitBtnClick"
						class="help-absolute"> <span
						v-for="error in $validation.certifiername.errors">
							{{error.message}} {{($index !==
							($validation.certifiername.errors.length -1)) ? ',':''}} </span>
					</span>
				</div>
			</div>

			<div class="form-group"
				:class="{'has-error':$validation.certifierphone.invalid && submitBtnClick}">
				<label class="col-sm-2 control-label">联系方式</label>
				<div class="col-sm-8">
					<input v-model="work.certifierPhone"
						v-validate:certifierphone="{maxlength:{rule:20,message:'证明人联系方式最长不能超过20个字符'}}"
						data-tabindex="1" name="certifierPhone" type="text"
						class="form-control" placeholder="请输入证明人联系方式"> <span
						v-cloak
						v-if="$validation.certifierphone.invalid && submitBtnClick"
						class="help-absolute"> <span
						v-for="error in $validation.certifierphone.errors">
							{{error.message}} {{($index !==
							($validation.certifierphone.errors.length -1)) ? ',':''}} </span>
					</span>
				</div>
			</div>

		</div>
		<div class="modal-footer">
			<button @click="cancel" type="button" data-dismiss="modal"
				class="btn">取消</button>
			<button @click="submit" :disabled="disabled" type="button"
				class="btn btn-primary">添加</button>
		</div>
	</form>
	</validator>
</div>

<%@include file="/WEB-INF/views/admin/components/ztree.jsp"%>
<script src="${ctx}/static/js/components/ztree.js"></script>
<script src="${ctx}/static/js/jquery.ztree.core.js"></script>
<script src="${ctx}/static/js/jquery.ztree.excheck.js"></script>
<script src="${ctx}/static/vendor/webuploader/webuploader.js"></script>
<script src="${ctx}/static/js/components/webuploader.js"></script>
<script src="${ctx}/static/js/containers/employee/edit.js?v=new Date()"></script>