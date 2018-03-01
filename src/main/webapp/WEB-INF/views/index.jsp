<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>系统首页</title>
<head>
  <style>
    .iboxHeight {
      min-height: 165px;
      max-height: 165px;
    }
  </style>
</head>

<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content">
  <div id="breadcrumb">
  	<a href=""  title="匿名信"><img width="30px" height="30px" src="../../static/img/edit.png"></a>
  </div>
  <!-- ibox start -->

  <div class="row">
        <div class="col-sm-6">
          <div class="ibox" style="margin-bottom:10px;">
            <div class="ibox-title">
              <h5>通知公告</h5>
            </div>
            <div class="ibox-content  iboxHeight" >
              <div class="row">
                <ul>
                  <li style="padding-bottom: 5px;" v-for="item in noticeMessage" >
                    <a @click="noticeViewMsgDetial( $index )" href="javascript:void(0)">【{{item.noticeStatus | NoticeConverLevel}}】{{item.title}}</a>
                  </li>
                </ul>
                <a  href="/admin/noticeboard/noticeboard" v-if="noticeMessage.length > 0 " style="margin-left: 20px;">查看更多</a>
                <span v-if="noticeMessage.length == 0 " style="margin-left: 10px;">暂无通知</span>
              </div>
            </div>
          </div>
        </div>

        <div class="col-sm-6">
          <div class="ibox" style="margin-bottom:10px;">
            <div class="ibox-title">
              <h5>消息提醒</h5>
            </div>
            <div class="ibox-content  iboxHeight" >
              <div class="row">
                <ul>
                  <li style="padding-bottom: 5px;" v-for="item in messages" >
                    <a @click="viewMsgDetial( $index )" href="javascript:void(0)">【{{item.messageLevel | converLevel}}】{{item.messageTitle}}</a>
                  </li>
                </ul>
                <a @click="showMoreMsg" href="javascript:void(0)" v-if="messages.length > 0 " style="margin-left: 20px;">查看更多</a>
                <span v-if="messages.length == 0 " style="margin-left: 10px;">暂无未读消息</span>
              </div>
            </div>
          </div>
        </div>
      <div class="ibox-content">
         <div class="ibox-title">
            <h5>我的待审批</h5>
            <div class="text-right"><a @click="showMoreApprove">更多...</a></div>
         </div>
	  	 <table id="dataTable" width="100%" class="table table-striped table-bordered table-hover">
	     </table>
	  </div>
	  
      </div>

    <!--   <div class="row"> -->
    <!--     <div class="col-sm-12"> -->
    <!--       <div class="ibox"> -->
    <!--         <div class="ibox-title"> -->
    <!--           <h5>工作日历</h5> -->
    <!--           <div class="text-right"> -->
    <!--             <button @click="createBtnClickHandler" id="createBtn" type="button" -->
    <!--                     class="btn btn-outline btn-primary">新增 -->
    <!--             </button> -->
    <!--           </div> -->
    <!--         </div> -->
    <!--         <div class="ibox-content"> -->
    <!--           <div v-el:calendar id="calendar"></div> -->
    <!--         </div> -->
    <!--       </div> -->
    <!--     </div> -->
    <!--   </div> -->
    <!--   </div> -->
    <!-- </div> -->

    <!-- ibox end -->

    <!-- 编辑工作计划的弹窗 -->
    <!-- 编辑/编辑的modal-->
    <div id="modal" class="modal fade" tabindex="-1" data-width="760">
      <validator name="validation">
        <form name="createMirror" novalidate class="form-horizontal" role="form">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h3>编辑工作计划</h3>
          </div>
          <div class="modal-body">
            <div class="form-group" :class="{'has-error':$validation.content.invalid && submitBtnClick}">
              <label for="content" class="col-sm-2 control-label">内容</label>
              <div class="col-sm-8">
                                <textarea v-model="plan.content"
                                          v-validate:content="{
                                               required:{rule:true,message:'请输入计划内容'}
                                          }"
                                          data-tabindex="1"
                                          id="content" name="content" class="form-control" placeholder="计划内容">

                                </textarea>
                <span v-cloak v-if="$validation.content.invalid && submitBtnClick"
                      class="help-absolute">
                            <span v-for="error in $validation.content.errors">
                              {{error.message}} {{($index !== ($validation.content.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
              </div>
            </div>

            <div class="form-group"
                 :class="{'has-error':$validation.starttime.invalid && submitBtnClick}">
              <label for="starttime" class="col-sm-2 control-label">开始时间</label>
              <div class="col-sm-8">
                <input v-model="plan.startTime"
                       v-validate:starttime="{
                                    required:{rule:true,message:'请选择开始日期'}
                                }"
                       v-el:start-date
                       readonly
                       maxlength="20"
                       data-tabindex="1"
                       id="starttime" name="starttime" type="text" class="form-control"
                       placeholder="开始日期">
                <span v-cloak v-if="$validation.starttime.invalid && submitBtnClick"
                      class="help-absolute">
                            <span v-for="error in $validation.starttime.errors">
                              {{error.message}} {{($index !== ($validation.starttime.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
              </div>
            </div>

            <div class="form-group"
                 :class="{'has-error':$validation.endtime.invalid && submitBtnClick}">
              <label for="endTime" class="col-sm-2 control-label">结束日期</label>
              <div class="col-sm-8">
                <input v-model="plan.endTime"
                       v-validate:endtime="{
                                    required:{rule:true,message:'请选择开始日期'}
                                }"
                       v-el:end-date
                       readonly
                       maxlength="20"
                       data-tabindex="1"
                       id="endTime" name="endTime" type="text" class="form-control"
                       placeholder="结束日期">
                <span v-cloak v-if="$validation.endtime.invalid && submitBtnClick"
                      class="help-absolute">
                            <span v-for="error in $validation.endtime.errors">
                              {{error.message}} {{($index !== ($validation.endtime.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
            <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
          </div>
        </form>
      </validator>

    </div>


  </div>
  
  <%--  首页消息详情model  begin--%>
  <div id="messageModal" class="modal fade" tabindex="-1" data-width="450">
    <div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
      <h3>消息详情</h3>
    </div>
    <div class="modal-body">
      <div class="row">
        <div class="form-group">
          <label class="col-sm-3 control-label">消息标题</label>
          <div class="col-sm-8">{{msg.messageTitle}}</div>
        </div>
      </div>
      <div class="row">
        <div class="form-group">
          <label class="col-sm-3 control-label">消息内容</label>
          <div class="col-sm-8">{{msg.messageContent}}</div>
        </div>
      </div>
      <div class="row">
        <div class="form-group">
          <label class="col-sm-3 control-label">重要级别</label>
          <div class="col-sm-8">{{msg.messageLevel | converLevel}}</div>
        </div>
      </div>
      <div class="row">
        <div class="form-group">
          <label class="col-sm-3 control-label">创建时间</label>
          <div class="col-sm-8">{{msg.createTime}}</div>
        </div>
      </div>
      <div class="row">
        <div class="form-group">
          <label class="col-sm-3 control-label">过期时间</label>
          <div class="col-sm-8">{{msg.expirationTime}}</div>
        </div>
      </div>
    </div>
    <div class="modal-footer">
      <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">关闭</button>
    </div>
  </div>
  <%--  首页消息详情model  end--%>

 <%--  首页消息列表model  begin--%>
 <div id="messageListModal" class="modal fade" tabindex="-1" data-width="760">
   <div class="modal-header">
     <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
     <h3>我的消息列表</h3>
   </div>
   <div class="modal-body">
     <div class="ibox">
       <div class="ibox-content">
         <div class="row">
           <form id="searchForm" @submit.prevent="query">
             <div class="col-md-2">
               <div class="form-group">
                 <label class="sr-only" for="keyword"></label>
                 <input
                   v-model="form.keyword"
                   id="keyword"
                   name="keyword"
                   type="text"
                   placeholder="消息标题" class="form-control"/>
               </div>
             </div>
             <div class="col-md-2">
               <div class="form-group">
                 <label class="sr-only"></label>
                 <select v-model="form.status"
                         id="status"
                         name="status"
                         placeholder="选择消息状态"
                         class="form-control">
                   <option value="">全部</option>
                   <option value="0">未读</option>
                   <option value="1">已读</option>
                 </select>
               </div>
             </div>
             <div class="col-md-2">
               <div class="form-group">
                 <label class="sr-only"></label>
                 <select v-model="form.messageLevel"
                         id="messageLevel"
                         name="messageLevel"
                         placeholder="选择消息级别"
                         class="form-control">
                   <option value="">全部</option>
                   <option value="1">一般</option>
                   <option value="2">重要</option>
                   <option value="3">紧急</option>
                   <option value="4">特急</option>
                 </select>
               </div>
             </div>
             <div class="col-md-2">
               <div class="form-group">
                 <label class="sr-only" for="beginTime">过期时间区间</label>
                 <input
                   v-model="form.beginTime"
                   v-el:begin-time
                   id="beginTime"
                   type="text"
                   readonly
                   class="form-control datepicker"
                   placeholder="请选择时间">
               </div>
             </div>
             <div class="col-md-2">
               <div class="form-group">
                 <label class="sr-only" for="endTime"></label>
                 <input
                   v-model="form.endTime"
                   v-el:end-time
                   id="endTime"
                   name="endTime"
                   type="text"
                   readonly
                   class="form-control datepicker"
                   placeholder="请选择结束时间">
               </div>
             </div>

             <div class="col-md-2">
               <div class="form-group">
                 <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default"
                         alt="搜索"
                         title="搜索">
                   <i class="fa fa-search"></i>
                 </button>
               </div>
             </div>

           </form>
         </div>
         <table id="dataTableMsg" width="100%" class="table table-striped table-bordered table-hover">
         </table>
       </div>
     </div>
   </div>
   <div class="modal-footer">
     <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">关闭</button>
   </div>
 </div>
 <%--  首页消息列表model  end--%>
  
<!-- container end-->
<!-- 流程表单详情 -->
<div id="approveHisModel" class="modal fade" tabindex="0" data-width="900">
  <validator name="validation">
    <form name="createMirror" novalidate class="form-horizontal" role="form">
      	<jsp:include page="admin/approval/approvalDetail.jsp"></jsp:include>
        <jsp:include page="admin/approval/approvalButton.jsp"></jsp:include>
    </form>
  </validator>
</div>


<%--公告详情开始,弹窗显示--%>
<%--<div id="noticeModal" class="modal fade" tabindex="-1" data-width="760">--%>
  <%--<div class="wrapper wrapper-content">--%>
    <%--<div class="ibox-content">--%>
      <%--<form name="createMirror" novalidate class="form-horizontal" role="form" style="margin-right:10px">--%>
        <%--<div class="text-center">--%>
          <%--<h3>{{noticeboard.title}}</h3>--%>
        <%--</div>--%>
        <%--<hr/>--%>
        <%--<div class="row">--%>
          <%--<div class="col-sm-12">--%>
            <%--<div class="row">--%>
              <%--<div class="form-group" style="margin-bottom: 45px;">--%>
                <%--<div class="col-sm-12 noticeImg">--%>
                  <%--{{{noticeboard.content}}}--%>
                <%--</div>--%>
              <%--</div>--%>
            <%--</div>--%>
            <%--<div class="row">--%>
              <%--<div class="form-group">--%>
                <%--<div class="col-md-12" >--%>
                  <%--<span  style="display:inline-block;border: 0;">&nbsp;&nbsp;&nbsp;&nbsp;发布人：{{noticeboard.createName}}</span>--%>
                <%--</div>--%>
              <%--</div>--%>
            <%--</div>--%>
            <%--<div class="row">--%>
              <%--<div class="form-group">--%>
                <%--<div class="col-md-12 ">--%>
                  <%--<span  style="display:inline-block;border: 0;">&nbsp;&nbsp;&nbsp;&nbsp;发布时间：{{noticeboard.createTime}}</span>--%>
                <%--</div>--%>
              <%--</div>--%>
            <%--</div>--%>
          <%--</div>--%>

        <%--</div>--%>
      <%--</form>--%>

      <%--<!-- ibox end -->--%>
    <%--</div>--%>
    <%--<div class="modal-footer">--%>
      <%--<button @click="cancel" type="button" data-dismiss="modal" class="btn">关闭</button>--%>
    <%--</div>--%>
  <%--</div>--%>
<%--</div>--%>
<%--公告详情结束--%>
<script src="${ctx}/static/js/containers/index.js"></script>