<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>台帐列表</title>
</head>
<body id="app" class="fixed-sidebar full-height-layout gray-bg">
<div id="container" class="wrapper wrapper-content">
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div>
                <div class="row">
                    <form id="searchForm" @submit.prevent="query">
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="sr-only" for="keyword"></label> <input
                                    v-model="form.keyword" id="keyword" name="keyword" type="text"
                                    placeholder="项目编号/客户姓名/手机号" class="form-control"/>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <label class="sr-only" for="keyword"></label>
                                <select v-model="form.status" name="type" id="type" class="form-control">
                                    <option value="">请选择状态</option>
                                    <option value="5">待转单</option>
                                    <option value="7">待施工</option>
                                    <option value="8">施工中</option>
                                    <option value="9">竣工</option>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <input v-model="form.startDate" v-el:start-date id="startDate"
                                       name="startDate" type="text" readonly
                                       class="form-control datepicker" placeholder="请选择签约开始时间">
                            </div>
                        </div>
                        <div class="col-md-4">
                            <div class="form-group">
                                <input v-model="form.endDate" v-el:end-date id="endDate"
                                       name="endDate" type="text" readonly
                                       class="form-control datepicker" placeholder="请选择签约结束时间">
                            </div>
                        </div>

                        <div class="col-md-2">
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
                <table v-el:data-table id="dataTable" width="100%"
                       class="table table-striped table-bordered table-hover">
                </table>
            </div>
        </div>
    </div>
    <!-- ibox end -->
</div>
<script src="${ctx}/static/js/containers/orderStandingBook/list.js"></script>
</body>
</html>