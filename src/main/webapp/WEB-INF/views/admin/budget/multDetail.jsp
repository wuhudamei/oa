<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="ibox">
    <div class="ibox-content">
        <form name="createForm" novalidate class="form-horizontal" role="form">
            <div class="form-group">
                <div class="row">
                    <label class="col-sm-2 control-label">申请标题</label>
                    <div class="col-sm-4">
                        <div style="margin-top: 5px;">{{budget.title}}</div>
                    </div>

                    <label class="col-sm-2 control-label">申请编码</label>
                    <div class="col-sm-4">
                        <div style="margin-top: 5px;">{{budget.code}}</div>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="row">
                    <label class="col-md-2 control-label text-left">公司:</label>
                    <div class="col-sm-4">
                        <div style="margin-top: 5px;">{{budget.company}}</div>
                    </div>

                    <div>
                        <label class="col-sm-2 control-label">预算月份:</label>
                        <div class="col-sm-4">
                            <div style="margin-top: 5px;">{{budget.yearMonth}}</div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="row">
                    <label class="col-md-2 control-label">提交人:</label>
                    <div class="col-sm-4">
                        <div style="margin-top: 5px;">{{budget.submitter}}</div>
                    </div>

                    <div>
                        <label class="col-sm-2 control-label">申请时间:</label>
                        <div class="col-sm-4">
                            <div style="margin-top: 5px;">{{budget.applyTime}}</div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <label class="col-md-2 control-label">预算总额:</label>
                <div class="col-sm-4">
                    <div style="margin-top: 5px;">{{budget.totalMoney}}</div>
                </div>
                <div class="col-sm-1"></div>
                <div class="col-sm-4" v-if="budget.attachment != ''">
                    <a class="btn btn-primary" href="{{budget.attachment}}">下载附件</a>
                </div>
            </div>
            <hr/>

            <div class="row ">
                <div class="col-sm-12">
                    <table class="table table-striped table-bordered table-hover text-center">
                        <thead>
                        <tr class="text-center">
                            <td>费用科目</td>
                            <td>上月预算</td>
                            <td>上月执行</td>
                            <td>本次预算</td>
                            <td>预算说明</td>
                            <td>本月预算累计</td>
                            <td>全年预算累计</td>
                            <td>全年执行累计</td>
                        </tr>
                        </thead>
                        <tbody>
                        <tr v-for="item in budget.details">
                            <td>{{item.name}}</td>
                            <td>{{item.lastMonthBudget}}</td>
                            <td>{{item.lastMonthExecution}}</td>
                            <td>{{item.thisMonthBudget}}</td>
                            <td>{{item.remark}}</td>
                            <td>{{item.thisMonthBudgetTotal}}</td>
                            <td>{{item.thisYearBudgetTotal}}</td>
                            <td>{{item.thisYearExecution}}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">报销说明:</label>
                <div class="col-sm-10">
                    <div style="margin-top: 5px;">{{budget.remark}}</div>
                </div>
            </div>
        </form>
    </div>
    <jsp:include page="/WEB-INF/views/admin/approval/detail.jsp"></jsp:include>
</div>