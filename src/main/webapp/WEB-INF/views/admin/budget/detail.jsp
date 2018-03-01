<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="ibox">
    <div class="ibox-content">
        <form name="createForm" novalidate class="form-horizontal" role="form">
            <div class="form-group">
                <div class="row">
                    <label class="col-sm-2 control-label">申请标题</label>
                    <div class="col-sm-4">
                        <div style="margin-top: 5px;">{{budget.applyTitle}}</div>
                    </div>

                    <label class="col-sm-2 control-label">申请编码</label>
                    <div class="col-sm-4">
                        <div style="margin-top: 5px;">{{budget.applyCode}}</div>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="row">
                    <label class="col-md-2 control-label text-left">预算公司:</label>
                    <div class="col-sm-4">
                        <div style="margin-top: 5px;">{{budget.company.orgName}}</div>
                    </div>

                    <div>
                        <label class="col-sm-2 control-label">预算月份:</label>
                        <div class="col-sm-4">
                            <div style="margin-top: 5px;">{{budget.budgetDate}}</div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="form-group">
                <div class="row">
                    <label class="col-md-2 control-label">提交人:</label>
                    <div class="col-sm-4">
                        <div style="margin-top: 5px;">{{budget.createUser.name}}</div>
                    </div>

                    <div>
                        <label class="col-sm-2 control-label">申请时间:</label>
                        <div class="col-sm-4">
                            <div style="margin-top: 5px;">{{budget.createDate}}</div>
                        </div>
                    </div>
                </div>
            </div>

            <hr/>

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
            <div class="form-group">
                <table class="table table-bordered table-hover table-striped">
                    <thead>
                    <tr>
                        <th>费用科目</th>
                        <th>科目说明</th>
                        <th>预算金额</th>
                        <th>预算说明</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="budgetItem in budget.budgetDetails">
                        <td>{{budgetItem.costItemName}}</td>
                        <td>包含：{{budgetItem.costDetailNames}}</td>
                        <td>{{budgetItem.money}}</td>
                        <td>{{budgetItem.remark}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">预算说明:</label>
                <div class="col-sm-10">
                    <div style="margin-top: 5px;">{{budget.remark}}</div>
                </div>
            </div>
        </form>
    </div>
    <jsp:include page="/WEB-INF/views/admin/approval/detail.jsp"></jsp:include>
</div>