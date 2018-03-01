<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
<title>考勤记录</title>
<meta name="keywords" content="">
<meta name="description" content="">
<style>
</style>
</head>
<body id="app" class="fixed-sidebar full-height-layout gray-bg">
<div id="container" class="wrapper wrapper-content">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <div class="row">
        <form id="searchForm" @submit.prevent="query">
            <validator name="validation">
            <div class="col-md-2">
                <div class="form-group">
                    <label class="sr-only" ></label>
                    <input
                            v-model="form.empName"
                            id="empName"
                            name="empName"
                            type="text"
                            placeholder="姓名" class="form-control"/>
                </div>
            </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <label class="sr-only" ></label>
                        <input
                                v-model="form.companyName"
                                id="companyName"
                                name="companyName"
                                type="text"
                                placeholder="公司名称" class="form-control"/>
                    </div>
                </div>
            <div class="col-sm-3">
                <div class="form-group" style="line-height: 34px">
                    <label for="startTime" class="col-sm-4 control-label">开始时间</label>
                    <div class="col-sm-7 no-padding">
                        <input
                                v-model="form.startDate"
                                v-el:start-time
                                id="startTime"
                                name="startTime"
                                type="text"
                                class="form-control datepicker"
                                placeholder="请选择开始时间">
                    </div>
                </div>
            </div>
            <div class="col-sm-3">
                <div class="form-group" style="line-height: 34px">
                    <label for="endTime" class="col-sm-4 control-label">结束时间</label>
                    <div class="col-sm-7 no-padding">
                        <input
                                v-model="form.endDate"
                                v-el:end-time
                                id="endTime"
                                name="endTime"
                                type="text"
                                class="form-control datepicker"
                                placeholder="请选择结束时间">
                    </div>
                </div>
            </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <select v-model="form.signType"
                                id="searchParentId"
                                name="searchParentId"
                                placeholder="考勤类型"
                                class="form-control">
                            <option value="">全部</option>
                            <option value="NORMAL">正常</option>
                            <option value="BELATE">迟到</option>
                            <option value="LEAVEEARLY">早退</option>
                            <option value="ABSENTEEISM">旷工</option>
                            <option value="BELATEANDLEAVEEARLY">迟到早退</option>
                        </select>
                    </div>
                </div>
                <div class="col-md-2">
                    <div class="form-group">
                        <select v-model="form.punchCardType"
                                name="searchParentId"
                                placeholder="打卡类型"
                                class="form-control">
                            <option value="">全部</option>
                            <option value="1">内勤</option>
                            <option value="2">外勤</option>
                        </select>
                    </div>
                </div>
            <div class="col-md-1">
                <div class="form-group">
                    <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default"
                            alt="搜索"
                            title="搜索">
                        <i class="fa fa-search"></i>
                    </button>
                </div>
            </div>
            <div class="col-md-1">
                <div class="form-group">
                    <button @click="exportBill(form.startDate,form.endDate)" id="createBtn" type="button"
                            class="btn btn-outline btn-primary">导出
                    </button>
                </div>
            </div>
          </validator>
        </form>
    </div>


    <!-- ibox start -->
    <div class="ibox">
        <div style="font-weight: 900;text-align: right" >
            <font color="red">  迟到并早退：{{belateandleaveearly}}人&nbsp;&nbsp;   迟到人数：{{beLate}}人&nbsp;&nbsp;  早退人数：{{leaveEarly}}人&nbsp;&nbsp;   旷工人数： {{absenteeism}}人&nbsp;&nbsp;  外勤人数：{{punchCardType}}人&nbsp;&nbsp;</font>
        </div>
        <table v-el:dataTable id="dataTable" width="100%"
               class="table table-striped table-bordered table-hover">
        </table>
    </div>
    <!-- ibox end -->
</div>

</div>
<script src="/static/js/containers/sign/checkonwork.js?v=new Date()"></script>
</body>
</html>