<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript">
  var hasEditStorePrivelege = false;
  <shiro:hasPermission name="store:edit">
	hasEditStorePrivelege = true;
  </shiro:hasPermission>
</script> 
<head>
  <style>
    .progress {
      height: 0px;
      transition: all .6s ease;
    }

    .progress-uploading {
      margin-top: 2px;
      height: 20px;
    }
  </style>
</head>
<div id="container" class="wrapper wrapper-content  animated fadeInRight">
  <div id="breadcrumb">
    <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
  </div>
  <!-- ibox start -->
  <div class="ibox">
    <div class="ibox-content">
      <div class="row">
        <form id="searchForm" @submit.prevent="query">

          <div class="col-md-3">
            <div class="form-group">
              <input
                  v-model="form.keyword"
                  type="text"
                  placeholder="名称" class="form-control"/>
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
        </form>
      </div>
      <!-- <div class="columns columns-right btn-group pull-right"></div> -->
      <table id="dataTable" width="100%" class="table table-striped table-bordered table-hover">
      </table>
    </div>
  </div>
  <!-- ibox end -->
</div>

<script src="${ctx}/static/vendor/webuploader/webuploader.js"></script>
<script src="${ctx}/static/js/components/webuploader.js"></script>
<script src="${ctx}/static/js/containers/financail/payment/reimbursed.js"></script>
