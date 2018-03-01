<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>二维码列表</title>
<div id="container" class="wrapper wrapper-content animated fadeInRight">
  <div id="breadcrumb">
    <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
  </div>
  <!-- ibox start -->
  <div class="ibox">
    <div class="ibox-content">
      <div class="row">
        <form id="searchForm" @submit.prevent="query">
          <div class="col-md-2">
            <div class="form-group">
              <label class="sr-only" for="keyword">名称</label>
              <input
                v-model="form.keyword"
                id="keyword"
                name="keyword"
                type="text"
                placeholder="二维码名称" class="form-control"/>
            </div>
          </div>
          <div class="col-md-1">
            <div class="form-group">
              <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default" alt="搜索"
                      title="搜索">
                <i class="fa fa-search"></i>
              </button>
            </div>
          </div>
          <div class="col-md-9 text-right">
            <div class="form-group">
              <button @click="createModel" type="button"
                      class="btn btn-outline btn-primary">生成二维码
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

<div id="createModal" class="modal fade" tabindex="-1" data-width="760">
  <validator name="validation">
    <form name="createForm" novalidate class="form-horizontal" role="form">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>生成二维码</h3>
      </div>
      <div class="modal-body">
        <div class="form-group" :class="{'has-error':$validation.sceneid.invalid && submitBtnClick}">
          <label for="name" class="col-sm-2 control-label">标签</label>
          <div class="col-sm-8">
            <select v-model="qrCode.sceneId"
            		v-validate:sceneid="{required:true}"
                    id="sceneId"
                    name="sceneId"
                    class="form-control">
                <option v-for="tmpTag in tags" :value="tmpTag.oid">{{tmpTag.name}}</option>
            </select>
            <span v-cloak v-if="$validation.sceneid.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.oid.errors">
                {{error.message}} {{($index !== ($validation.sceneid.errors.length -1)) ? ',':''}}
              </span>
            </span>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
        <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">确定</button>
      </div>
    </form>
  </validator>
</div>

<script src="${ctx}/static/js/wx/qrcode/list.js"></script>