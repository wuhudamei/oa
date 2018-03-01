<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>请假</title>
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
              <label class="sr-only" for="startTime">开始时间</label>
              <input v-model="form.startTime"
                     readonly
                     id="startTime"
                     v-el:start-time
                     name="startTime"
                     type="text"
                     placeholder="开始日期"
                     class="form-control datepicker">
            </div>
          </div>
          <div class="col-md-2">
            <div class="form-group">
              <label class="sr-only">结束时间</label>
              <input v-model="form.endTime"
                     readonly
                     id="endTime"
                     v-el:end-time
                     name="endDate"
                     type="text"
                     placeholder="结束时间"
                     class="form-control datepicker">
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
          <!-- 将剩余栅栏的长度全部给button -->
          <div class="col-md-7 text-right">
            <div class="form-group">
              <button @click="createBtnClickHandler" type="button"
                      class="btn btn-outline btn-primary">新增
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
<script src="${ctx}/static/js/containers/vacation/vacations.js"></script>