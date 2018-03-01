<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>集客报表</title>
    <%@include file="/WEB-INF/views/admin/shims/config.jsp" %>
</head>
<body id="app" class="fixed-sidebar full-height-layout gray-bg">
<!-- container -->
<!-- 内容部分 start-->
<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <form class="form-inline" style="margin-bottom: 20px;">
                <div class="form-group">
                    <label>城市:</label>
                    <select class="form-control" v-model="form.city">
                        <option value="北京" selected>北京</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>日期:</label>
                    <input type="text" class="form-control" v-el:start-date v-model="form.startDate" v-model="form.startDate">
                    ~
                    <input type="text" class="form-control" v-el:end-date v-model="form.endDate" v-model="form.endDate">
                </div>
                <div class="form-group">
                    <label>状态:</label>
                    <select class="form-control" v-model="form.state">
                        <option value="" selected>全部</option>
                        <option value="1">新增</option>
                        <option value="2">退订</option>
                        <option value="3">转单</option>
                        <option value="4">存余</option>
                    </select>
                </div>
                <button class="btn btn-default" @click.prevent="query">查询</button>
                <button class="btn btn-default" @click.prevent="reset">清空</button>
                <!--<button class="btn btn-default" @click="export">导出</button>-->
            </form>
            <table  width="100%" id="table" class="table table-striped table-bordered table-hover">
                <thead>
                    <tr>
                        <th data-field="index">序号</th>
                        <th data-field="sourceBigTypeName">客户来源</th>
                        <th data-field="promotSourceName">来源细分</th>
                        <th data-field="cost">成本投入</th>
                        <th data-field="statuStr">状态</th>
                        <th data-field="candidateCusAmount">线索</th>
                        <th data-field="realCusAmount">进店</th>
                        <th data-field="inRate">邀约率</th>
                        <th data-field="smallSettleOrderAmount">小定数量</th>
                        <th data-field="smallSettleOrderTotalMoney">小定金额</th>
                        <th data-field="bigSettleOrderAmount">大定数量</th>
                        <th data-field="bigSettleOrderTotalMoney">大定金额</th>
                        <th data-field="aTypeBigSettleOrderAmount">A类大定数量</th>
                        <th data-field="aTypeBigSettleOrderTotalMoney">A类大定金额</th>
                        <th data-field="bTypeBigSettleOrderAmount">B类大定数量</th>
                        <th data-field="bTypeBigSettleOrderTotalMoney">B类大定金额</th>
                        <th data-field="cTypeBigSettleOrderAmount">C类大定数量</th>
                        <th data-field="cTypeBigSettleOrderTotalMoney">C类大定金额</th>
                    </tr>
                </thead>
            </table>
        </div>
    </div>
    <!-- ibox end -->
</div>
<!-- container end-->

<script src="${ctx}/static/vendor/md5/md5.min.js"></script>
<script src="${ctx}/static/js/containers/report/grathercustomer.js"></script>
</body>
</html>