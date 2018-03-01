<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>修改密码</title>
<div id="container" class="wrapper wrapper-content">
  <div id="breadcrumb">
    <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
  </div>
  <!-- ibox start -->
  <div class="ibox-content">
    <validator name="validation">
      <form name="createMirror" novalidate class="form-horizontal" role="form">
        <div class="text-center">
          <h3>修改密码</h3>
        </div>
        <div>
          <div class="form-group"
               :class="{'has-error':$validation.oldpassword.invalid && submitBtnClick}">
            <label for="plainPwd" class="col-sm-2 control-label">原密码</label>
            <div class="col-sm-8">
              <input v-model="user.plainPwd"
                     v-validate:oldpassword="{
                                    required:{rule:true,message:'请输入原密码'}
                                }"
                     maxlength="20"
                     data-tabindex="1"
                     id="plainPwd" name="plainPwd" type="password" class="form-control"
                     placeholder="原密码">
              <span v-cloak v-if="$validation.oldpassword.invalid && submitBtnClick"
                    class="help-absolute">
                            <span v-for="error in $validation.oldpassword.errors">
                              {{error.message}} {{($index !== ($validation.oldpassword.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
            </div>
          </div>

          <div class="form-group"
               :class="{'has-error':$validation.newpassword.invalid && submitBtnClick}">
            <label for="loginPwd" class="col-sm-2 control-label">新密码</label>
            <div class="col-sm-8">
              <input v-model="user.loginPwd"
                     v-validate:newpassword="{
                                    required:{rule:true,message:'请输入描述'},
                                    required:{rule:password,message:'请输入正确格式的密码'}
                                }"
                     maxlength="20"
                     data-tabindex="1"
                     id="loginPwd" name="loginPwd" type="password" class="form-control"
                     placeholder="新密码">
              <span v-cloak v-if="$validation.newpassword.invalid && submitBtnClick"
                    class="help-absolute">
                            <span v-for="error in $validation.newpassword.errors">
                              {{error.message}} {{($index !== ($validation.newpassword.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
            </div>
          </div>

          <div class="form-group"
               :class="{'has-error':$validation.confirmpwd.invalid && submitBtnClick}">
            <label for="confirmPwd" class="col-sm-2 control-label">确认新密码</label>
            <div class="col-sm-8">
              <input v-model="user.confirmPwd"
                     v-validate:confirmpwd="{
                                    required:{rule:true,message:'请输入确认密码'},
                                    required:{rule:password,message:'请输入正确格式的密码'}
                                }"
                     maxlength="20"
                     data-tabindex="1"
                     id="confirmPwd" name="confirmPwd" type="password" class="form-control"
                     placeholder="输入确认密码">
              <span v-cloak v-if="$validation.confirmpwd.invalid && submitBtnClick"
                    class="help-absolute">
                            <span v-for="error in $validation.confirmpwd.errors">
                              {{error.message}} {{($index !== ($validation.confirmpwd.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
            </div>
          </div>
        </div>
        <div class="text-center">
          <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">修改密码
          </button>
        </div>
      </form>
    </validator>
    <!-- ibox end -->
  </div>
</div>
<!-- container end-->
<script src="${ctx}/static/js/containers/password/modify.js"></script>