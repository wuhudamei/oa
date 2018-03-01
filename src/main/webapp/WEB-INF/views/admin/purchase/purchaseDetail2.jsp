<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form name="createForm" novalidate class="form-horizontal" role="form">
  <div class="form-group">
    <label class="col-sm-2 control-label">申请标题</label>
    <div class="col-sm-8"><div style="margin-top: 5px;">{{purchase.applyTitle}}</div></div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">申请编码</label>
    <div class="col-sm-8"><div style="margin-top: 5px;">{{purchase.applyCode}}</div></div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">一级科目</label>
    <div class="col-sm-8"><div style="margin-top: 5px;">{{purchase.firstName}}</div></div>
  </div>

  <div class="form-group">
    <label class="col-sm-2 control-label">二级科目</label>
    <div class="col-sm-8"><div style="margin-top: 5px;">{{purchase.secondName}}</div></div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">采购月份</label>
    <div class="col-sm-8"><div style="margin-top: 5px;">{{purchase.purchaseMonth}}</div></div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">年度总预算</label>
    <div class="col-sm-8"><div style="margin-top: 5px;">{{purchase.totalBudget}}</div></div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">剩余预算</label>
    <div class="col-sm-8"><div style="margin-top: 5px;">{{purchase.leftBudget}}</div></div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">物品名称</label>
    <div class="col-sm-8"><div style="margin-top: 5px;">{{purchase.goodName}}</div></div>
  </div>

  <div class="form-group">
    <label class="col-sm-2 control-label">数量</label>
    <div class="col-sm-8"><div style="margin-top: 5px;">{{purchase.goodNum}}</div></div>
  </div>

  <div class="form-group">
    <label class="col-sm-2 control-label">单价</label>
    <div class="col-sm-8"><div style="margin-top: 5px;">{{purchase.goodPrice}}</div></div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">总价</label>
    <div class="col-sm-8"><div style="margin-top: 5px;">{{purchase.totalPrice}}</div></div>
  </div>
  <div class="form-group">
    <label class="col-sm-2 control-label">物品用途说明</label>
    <div class="col-sm-8"><div style="margin-top: 5px;">{{purchase.description}}</div></div>
  </div>

  <jsp:include page="/WEB-INF/views/admin/approval/detail.jsp"></jsp:include>
</form>
