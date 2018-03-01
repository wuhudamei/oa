<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="staffCostTmpl">
  <div>
    <div class="row detail-stranding-book" v-for="item in userInfoData" >
      <div class="col-sm-4 col-xs-12">
        <label for="">客户姓名：</label>
        {{item.name}}
      </div>
      <div class="col-sm-4 col-xs-12">
        <label for="">所选套餐：</label>
        {{item.Package}}
      </div>
      <div class="col-sm-4 col-xs-12">
        <label for="">建筑面积：</label>
        {{item.bulidArea}}
      </div>
      <div class="col-sm-4 col-xs-12">
        <label for="">联系方式：</label>
        {{item.tel}}
      </div>
      <div class="col-sm-4 col-xs-12">
        <label for="">房租地址：</label>
        {{item.address}}
      </div>
      <div class="col-sm-4 col-xs-12">
        <label for="">房屋户型：</label>
        {{item.houseLayout}}
      </div>
      <div class="col-sm-4 col-xs-12">
        <label for="">有无电梯：</label>
        {{item.withElevator}}
      </div>
      <div class="col-sm-4 col-xs-12">
        <label for="">房屋状况：</label>
        {{item.houseStatus}}
      </div>
      <div class="col-sm-4 col-xs-12">
        <label for="">计价面积：</label>
        {{item.priceArea}}
      </div>
      <div class="col-sm-4 col-xs-12">
        <label for="">第二联系人：</label>
        {{item.secondName}}
      </div>
      <div class="col-sm-4 col-xs-12">
        <label for="">第二联系人电话：</label>
        {{item.secondTel}}
      </div>
      <div class="col-sm-4 col-xs-12">
        <label for="">房屋类型：</label>
        {{item.houseType}}
      </div>
      <div class="col-sm-4 col-xs-12">
        <label for="">计划开工时间：</label>
        {{item.startUpTime}}
      </div>
      <div class="col-sm-4 col-xs-12">
        <label for="">计划完工时间：</label>
        {{item.completionTime}}
      </div>
      <div class="col-sm-4 col-xs-12">
        <label for="">订单状态：</label>
        {{item.orderStatus}}
      </div>
      <div class="col-sm-4 col-xs-12">
        <label for="">订单金额：</label>
        {{item.orderAmount}}
      </div>
    </div>  
  </div>
</template>
