<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<link href="${ctx}/static/css/zTreeStyle.css" rel="stylesheet">
<link rel="stylesheet" href="/static/vendor/webuploader/webuploader.css">
<link href="${ctx}/static/vendor/viewer/viewer.css" rel="stylesheet">
<title>通用申请</title>
<div id="container" class="wrapper wrapper-content">
	<div id="breadcrumb">
		<bread-crumb :crumbs="breadcrumbs"></bread-crumb>
	</div>
	<!-- ibox start -->
	<form  class="ibox-content">
		<validator name="validation">
		<div class="form-horizontal" >
			<div class="text-center">
				<h3>通用申请</h3>
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">申请编号：</label>
							<div class="col-md-9" style="top: 7px;">
								<label>{{form.serialNumber}}</label>
								<input readonly v-model="form.serialNumber"
									maxlength="20" name="serialNumber" id="serialNumber" 
									type="hidden" class="form-control" />
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">申请时间：</label>
							<div class="col-md-9" style="top: 7px;">
							<label>{{form.applyTime}}</label>
								<input readonly v-model="form.applyTime" maxlength="100"
									name="applyTime" type="hidden" class="form-control" />
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group" :class="{'has-error':$validation.title.invalid}  ">
							<label class="col-md-3 control-label">申请标题：</label>
							<div class="col-md-9">
								<input v-model="form.title" id="title" name="title" maxlength="100"
									type="text" class="form-control" 
                                      v-validate:title="{required:{rule:true,message:'请输入标题'}}"/>
                                  <span style="margin-top: 0px;" v-cloak v-if="$validation.title.invalid " class="help-absolute">
	                                  <span v-for="error in $validation.title.errors">
	                                     {{error.message}}
	                                  </span>
                               	 </span>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
			</div>
			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group" :class="{'has-error':$validation.content.invalid}  ">
							<label class="col-md-3 control-label">申请内容：</label>
							<div class="col-md-9">
								 <textarea v-model="form.content" id="content" name="content" 
								 class="form-control" rows="3" 
                                 v-validate:content="{required:{rule:true,message:'请输入标题'}}"/></textarea>
                                 <span style="margin-top: 0px;" v-cloak v-if="$validation.content.invalid " class="help-absolute">
	                                  <span v-for="error in $validation.content.errors">
	                                     {{error.message}}
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
							<label class="col-md-3 control-label">照片上传：</label>
							<label class="col-md-2 control-label"
								style="text-align: left; padding: 0px 10px"
								v-clickoutside="">
								<web-uploader
									:type="webUploaderImage.type" :w-server="webUploaderImage.server"
									:w-accept="webUploaderImage.accept"
									:w-file-size-limit="webUploaderImage.fileSizeLimit"
									:w-file-single-size-limit="webUploaderImage.fileSingleSizeLimit"
									:w-form-data="{category:'CONTRACT'}">
								<button type="button" class="btn btn-primary btn-outline" >上传图片</button> 
								</web-uploader>
							</label>
						</div>
					</div>
				</div>
			</div>
			<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-10" id="showImages">
				<label id="view-images">
					<label v-for="src in photoSrcs">
						<img v-model="form.photoSrcs"   data-original="{{src.path}}" :src="src.path"  alt="" style="width: 105px; margin-left: 60px">
						<i class="fa fa-fw fa-close"　 @click="removeImg(src,$index,'image')" style="width: 40px;">
							<font color="blue">删除</font>
						</i>
					</label>
				</label>
			</div>
			</div>
			
			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">附件上传：</label>
							<label class="col-md-3 control-label"
								style="text-align: left; padding: 0px 10px" v-clickoutside="">
								<web-uploader
									:type="webUploaderAccessories.type" :w-server="webUploaderAccessories.server"
									:w-accept="webUploaderAccessories.accept"
									:w-file-size-limit="webUploaderAccessories.fileSizeLimit"
									:w-file-single-size-limit="webUploaderAccessories.fileSingleSizeLimit"
									:w-form-data="{category:'CONTRACT'}">
								<button type="button" class="btn btn-primary btn-outline" >上传附件</button>
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
							<label class="col-md-3 control-label"></label>
							<label class="col-md-7 control-label"
								style="text-align: left; padding: 0px 10px" v-clickoutside="">
								<font color="red">苹果手机无法使用附件功能</font>
							</label>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-10">
				<span v-for="src in accessories">
					{{src.fileName}}
					<i class="fa fa-fw fa-close"　 @click="removeImg(src,$index,'accessories')" style="width: 40px;">
						<font color="blue">删除</font>
					</i>
				</span>
			</div>
			</div>
			
			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">审批人配置：</label>
							<label class="col-md-2 control-label"
								style="text-align: left; padding: 0px 10px" v-clickoutside="">
								<button type="button" @click="add()" class="btn btn-primary btn-outline" >编辑</button> 
							</label>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="form-group">
						    <label class="col-md-3 control-label"></label>
							<span v-for="btn in employees">
								<span v-if="$index > 0">►</span>
									<button type="button" class="btn btn-primary btn-outline">{{btn.name}}</button>
							</span>
						</div>
					</div>
				</div>
			</div>

			<div class="row">
				<div class="col-md-6">
					<div class="row">
						<div class="form-group">
							<label class="col-md-3 control-label">抄送人配置：</label>
							<label class="col-md-2 control-label"
								style="text-align: left; padding: 0px 10px" v-clickoutside="">
								<button type="button" class="btn btn-primary btn-outline" @click="addCcPerson()">编辑</button> 
							</label>
						</div>
					</div>
				</div>
			</div>
			
			<div class="row">
				<div class="col-md-12">
					<div class="row">
						<div class="form-group">
						    <label class="col-md-3 control-label"></label>
							<span v-for="btn in ccPersons">
								<button type="button" class="btn btn-primary btn-outline">{{btn.name}}</button>
							</span>
						</div>
					</div>
				</div>
			</div>
			<div class="text-center">
				<button @click="submit()" type="button" class="btn btn-primary">提交</button>
				<button @click="cancel()" type="button" class="btn btn-default">取消	</button>
			</div>
		</form> <!-- from -->
		</validator>
		<!-- ibox end -->
	</div>
</div>
<div class="modal fade" id="addStylistModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					添加审批人
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<form id="stylistSearchForm" @submit.prevent="query">
						<div class="col-md-4">
							<div class="form-group">
								<input
										v-model="form.keyword"
										type="text"
										placeholder="工号/姓名/手机号" class="form-control"/>
							</div>
						</div>
						<div class="col-md-3 text-right">
							<div class="form-group">
								<button id="stylistSearchBtn"  class="btn btn-block btn-outline btn-default"
										alt="搜索"
										title="搜索">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
					</form>
				</div>
				<div class="row">
					<div class="col-md-10">
						<div class="row">
						 　　<font color="red">注：双击行，添加审批人</font>
							<div class="form-group">
								<table id="stylistDataTable" width="100%" class="table table-striped table-bordered table-hover">
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="btn-group" v-for="btn in employees">
						<span v-if="$index > 0">►</span>
						<span>
							<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">{{btn.name}} 
								<span class="caret"></span>
							</button>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#" @click="deleteItem(btn)">删除</a></li>
							</ul>
						</span>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button"  @click="commitStylist" class="btn btn-primary">
					确定
				</button>
			</div>
		</div><!-- /.modal-content -->
</div><!-- /.modal -->
<div class="modal fade" id="ccPersonStylistModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" 
						aria-hidden="true">×
				</button>
				<h4 class="modal-title" id="myModalLabel">
					添加抄送人
				</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<form id="stylistSearchForm" @submit.prevent="query">
						<div class="col-md-4">
							<div class="form-group">
								<input
										v-model="form.keyword"
										type="text"
										placeholder="工号/姓名/手机号" class="form-control"/>
							</div>
						</div>
						<div class="col-md-3 text-right">
							<div class="form-group">
								<button id="stylistSearchBtn"  class="btn btn-block btn-outline btn-default"
										alt="搜索"
										title="搜索">
									<i class="fa fa-search"></i>
								</button>
							</div>
						</div>
					</form>
				</div>
				<div class="row">
					<div class="col-md-10">
						<div class="row">
						 　　<font color="red">注：双击行，添加审批人</font>
							<div class="form-group">
								<table id="ccPersonDataTable" width="100%" class="table table-striped table-bordered table-hover">
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
						<div class="btn-group" v-for="btn in ccPersons">
							<span>
								<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">{{btn.name}} 
									<span class="caret"></span>
								</button>
								<ul class="dropdown-menu" role="menu">
									<li><a href="#" @click="deleteItem(btn)">删除</a></li>
								</ul>
							</span>
						</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button"  @click="commitStylist" class="btn btn-primary">
					确定
				</button>
			</div>
		</div><!-- /.modal-content -->
</div><!-- /.modal -->
<div class="modal fade" id="imageView" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true" >
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h4 class="modal-title" id="myModalLabel">图片预览</h4>
		</div>
		<div class="modal-body">
			<div style="margin: 0 auto; text-align:center" >
				<img  alt="" :src="imgPath" >
			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-default" data-dismiss="modal">关闭
			</button>
		</div>
	</div>
	<!-- /.modal-content -->
</div>
<script src="${ctx}/static/vendor/viewer/viewer.js"></script>
<script src="${ctx}/static/vendor/webuploader/webuploader.js"></script>
<script src="${ctx}/static/js/components/webuploader.js"></script>
<script src="${ctx}/static/js/containers/commonApply/create.js"></script>
