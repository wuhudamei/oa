<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>采购管理</title>
<link rel="stylesheet" href="/static/css/tab.css">
<div id="container" class="wrapper wrapper-content animated fadeInRight">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div>
                <Tabs type="card" @on-click="clickEvent">
                    <Tab-pane label="我的申请">
                        <div class="row">
                            <form id="searchForm" @submit.prevent="query">
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label class="sr-only" for="keyword"></label>
                                        <input
                                                v-model="form.keyword"
                                                id="keyword"
                                                name="keyword"
                                                type="text"
                                                placeholder="物品名称/用途" class="form-control"/>
                                    </div>
                                </div>
                                <div class="col-md-2">
                                    <div class="form-group">
                                        <select v-model="form.status"
                                                id="status"
                                                name="status"
                                                placeholder="选择状态"
                                                class="form-control">
                                            <option value="">全部状态</option>
                                            <option value="APPROVALING">审核中</option>
                                            <option value="ADOPT">通过</option>
                                            <option value="REFUSE">拒绝</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <div class="form-group">
                                        <button id="searchBtn" type="submit"
                                                class="btn btn-block btn-outline btn-default" alt="搜索"
                                                title="搜索">
                                            <i class="fa fa-search"></i>
                                        </button>
                                    </div>
                                </div>

                            </form>
                        </div>
                        <table id="dataTable" width="100%" class="table table-striped table-bordered table-hover">
                        </table>
                    </Tab-pane>
                    <Tab-pane label="审批历史">
                        <div class="row">
                            <form id="searchForm2" @submit.prevent="query">
                                <div class="col-md-4">
                                    <div class="form-group">
                                        <label class="sr-only" for="keyword"></label>
                                        <input
                                                v-model="form.keywordHis"
                                                id="keywordHis"
                                                name="keywordHis"
                                                type="text"
                                                placeholder="申请标题/申请编号" class="form-control"/>
                                    </div>
                                </div>
                                <div class="col-md-1">
                                    <div class="form-group">
                                        <button id="searchBtn2" type="submit" class="btn btn-block btn-outline btn-default" alt="搜索"
                                                title="搜索">
                                            <i class="fa fa-search"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <table id="dataTableHis" width="100%" class="table table-striped table-bordered table-hover">
                        </table>
                    </Tab-pane>
                </Tabs>
            </div>
        </div>
    </div>
    <!-- ibox end -->
</div>

<div id="modal" class="modal fade" tabindex="-1" data-width="760">
    <validator name="validation">
        <form name="createForm" novalidate class="form-horizontal" role="form">
            <div class="modal-header">
                <label class="col-sm-2 control-label">请假类型</label>
            </div>
            <div class="modal-body">
                <input type="hidden" id="userId" name="userId" v-model="businessAway.userId">
                <div class="form-group" :class="{'has-error':$validation.parentid.invalid && submitBtnClick}">
                    <label for="parentid" class="col-sm-2 control-label">一级科目</label>
                    <div class="col-sm-8">
                        <select
                                v-validate:parentId="{required:true}"
                                v-model="purchase.firstTypeId"
                                id="parentid"
                                name="parentid"
                                data-tabindex="1"
                                class="form-control">
                            <option value="">请选择一级科目</option>
                            <%--v-if="parent.type==selectType"--%>
                            <option v-for="parent in parents"
                                    :value="parent.id">
                                {{parent.name}}
                            </option>
                        </select>

                        <div v-if="$validation.parentid.invalid && $validation.touched"
                             class="help-absolute">
                            <span v-if="$validation.parentid.invalid">请选择一级科目</span>
                        </div>
                    </div>
                </div>
                <div class="form-group" :class="{'has-error':$validation.childid.invalid && submitBtnClick}">
                    <label for="childid" class="col-sm-2 control-label">二级科目</label>
                    <div class="col-sm-8">
                        <select
                                v-validate:childid="{required:true}"
                                v-model="purchase.secondTypeId"
                                id="childid"
                                name="childid"
                                data-tabindex="1"
                                class="form-control">
                            <option value="">请选择二级科目</option>
                            <%--v-if="parent.type==selectType"--%>
                            <option v-for="child in children"
                                    :value="child.id">
                                {{child.name}}
                            </option>
                        </select>

                        <div v-if="$validation.childid.invalid && $validation.touched"
                             class="help-absolute">
                            <span v-if="$validation.childid.invalid">请选择二级科目</span>
                        </div>
                    </div>
                </div>


                <div class="form-group" :class="{'has-error':$validation.goodname.invalid && submitBtnClick}">
                    <label for="goodname" class="col-sm-2 control-label">物品名称</label>
                    <div class="col-sm-8">
                        <input v-model="purchase.goodName"
                               v-validate:goodname="{
                                    required:{rule:true,message:'请输入物品名称'}
                                }"
                               data-tabindex="4"
                               id="goodname" name="goodname" type="text" class="form-control" placeholder="请输入物品名称">
                        <span v-cloak v-if="$validation.goodname.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.goodname.errors">
                {{error.message}} {{($index !== ($validation.goodname.errors.length -1)) ? ',':''}}
              </span>
            </span>
                    </div>
                </div>
                <div class="form-group" :class="{'has-error':$validation.purchaseMonth.invalid && submitBtnClick}">
                    <label for="purchaseMonth" class="col-sm-2 control-label">采购月份</label>
                    <div class="col-sm-8">
                        <input v-model="purchase.purchaseMonth"
                               v-validate:purchase-month="{
                                    required:{rule:true,message:'请输入采购月份'}
                                }"
                               v-el:purchase-month
                               data-tabindex="4"
                               id="purchaseMonth" name="purchaseMonth" type="text" class="form-control datepicker"
                               placeholder="请选择月份">
                        <span v-cloak v-if="$validation.purchaseMonth.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.purchaseMonth.errors">
                {{error.message}} {{($index !== ($validation.purchaseMonth.errors.length -1)) ? ',':''}}
              </span>
            </span>
                    </div>
                </div>
                <div class="form-group" :class="{'has-error':$validation.goodNum.invalid && submitBtnClick}">
                    <label for="goodNum" class="col-sm-2 control-label">数量</label>
                    <div class="col-sm-8">
                        <input v-model="purchase.goodNum"
                               v-validate:good-num="{
                                    required:{rule:true,message:'请输入数量'},
                                    min:0,
                                    max:99999999.99
                                }"
                               data-tabindex="4"
                               type="number"
                               maxlength="20"
                               id="goodNum" name="goodNum" type="text" class="form-control" placeholder="请输入数量">
                        <span v-cloak v-if="$validation.goodNum.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.goodNum.errors">
                {{error.message}} {{($index !== ($validation.goodNum.errors.length -1)) ? ',':''}}
              </span>
            </span>
                    </div>
                </div>
                <div class="form-group" :class="{'has-error':$validation.price.invalid && submitBtnClick}">
                    <label for="price" class="col-sm-2 control-label">单价</label>
                    <div class="col-sm-8">
                        <input v-model="purchase.goodPrice"
                               v-validate:price="{
                                    required:{rule:true,message:'请输入单价'},
                                    min:0,
                                    max:99999999.99
                                }"
                               type="number"
                               maxlength="20"
                               data-tabindex="4"
                               id="price" name="price" type="text" class="form-control" placeholder="请输入单价">
                        <span v-cloak v-if="$validation.price.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.price.errors">
                {{error.message}} {{($index !== ($validation.price.errors.length -1)) ? ',':''}}
              </span>
            </span>
                    </div>
                </div>

                <div class="form-group" :class="{'has-error':$validation.totalPrice.invalid && submitBtnClick}">
                    <label for="totalPrice" class="col-sm-2 control-label">总价</label>
                    <div class="col-sm-8">
                        <input v-model="purchase.totalPrice"
                               v-validate:total-price="{
                                    required:{rule:true,message:'请输入总价'},
                                    min:0,
                                    max:99999999.99
                                }"
                               type="number"
                               maxlength="20"
                               data-tabindex="4"
                               id="totalPrice" name="totalPrice" type="text" class="form-control" placeholder="请输入总价">
                        <span v-cloak v-if="$validation.totalPrice.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.totalPrice.errors">
                {{error.message}} {{($index !== ($validation.totalPrice.errors.length -1)) ? ',':''}}
              </span>
            </span>
                    </div>
                </div>

                <div class="form-group" :class="{'has-error':$validation.description.invalid && submitBtnClick}">
                    <label for="description" class="col-sm-2 control-label">物品用途说明</label>
                    <div class="col-sm-8">
            <textarea
                    v-model="purchase.description"
                    v-validate:description="{
                                    required:{rule:true,message:'请输入物品用途说明'}
                                }"
                    maxlength="50"
                    id="description"
                    name="description"
                    class="form-control"
                    rows="3"
                    placeholder="请输入物品用途说明">
            </textarea>
                        <span v-cloak v-if="$validation.description.invalid && submitBtnClick" class="help-absolute">
              <span v-for="error in $validation.description.errors">
                {{error.message}} {{($index !== ($validation.description.errors.length -1)) ? ',':''}}
              </span>
            </span>
                    </div>
                </div>

                <div class="modal-footer">
                    <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
                    <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
                </div>
        </form>
    </validator>
</div>

<div id="viewModal" class="modal fade" tabindex="-1" data-width="760">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>采购申请详情</h3>
    </div>
    <div class="modal-body">
        <jsp:include page="purchaseDetail.jsp"></jsp:include>
    </div>
    <div class="modal-footer">
        <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">关闭</button>
    </div>
</div>

<!-- 流程表单详情 -->
<div id="approveHisModel" class="modal fade" tabindex="0" data-width="760">
  <validator name="validation">
    <form name="createMirror" novalidate class="form-horizontal" role="form">
      	<jsp:include page="../approval/approvalDetail.jsp"></jsp:include>
    </form>
  </validator>
</div>
<script src="${ctx}/static/js/components/tab.js"></script>
<script src="${ctx}/static/js/containers/purchase/purchase.js"></script>