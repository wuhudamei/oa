<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form name="createForm" novalidate class="form-horizontal" role="form">
    <div class="form-group">
        <div class="row">
            <label class="col-sm-2 control-label">申请标题:</label>
            <div class="col-sm-4">
                <div style="margin-top: 5px;">{{payment.applyTitle}}</div>
            </div>

            <label class="col-sm-2 control-label">申请编码:</label>
            <div class="col-sm-4">
                <div style="margin-top: 5px;">{{payment.applyCode}}</div>
            </div>
        </div>
    </div>


    <div class="form-group">
        <div class="row">
            <label class="col-md-2 control-label text-left">报销公司:</label>
            <div class="col-sm-4">
                <div style="margin-top: 5px;">{{payment.company.orgName}}</div>
            </div>

            <div>
                <label class="col-sm-2 control-label">报销月份:</label>
                <div class="col-sm-4">
                    <div style="margin-top: 5px;">{{payment.paymentDate}}</div>
                </div>
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="row">
            <label class="col-md-2 control-label">提交人:</label>
            <div class="col-sm-4">
                <div style="margin-top: 5px;">{{payment.createUser.name}}</div>
            </div>

            <div>
                <label class="col-sm-2 control-label">申请时间:</label>
                <div class="col-sm-4">
                    <div style="margin-top: 5px;">{{payment.createDate}}</div>
                </div>
            </div>
        </div>
    </div>

    <hr/>

    <div class="form-group">
        <div class="row">
            <label class="col-md-2 control-label">报销总额:</label>
            <div class="col-sm-4">
                <div style="margin-top: 5px;">{{payment.totalMoney}}</div>
            </div>

            <div>
                <label class="col-sm-2 control-label">票据总数:</label>
                <div class="col-sm-4">
                    <div style="margin-top: 5px;">{{payment.invoiceNum}}</div>
                </div>
            </div>
        </div>
    </div>

    <div class="form-group" v-if="payment.signatureTitle != '' && payment.signatureTitle != null">
        <div class="row">
            <label class="col-md-2 control-label">费用申请单:</label>
            <div class="col-sm-4">
                <div style="margin-top: 5px;"><a @click="showSignatureDetail">{{payment.signatureTitle}}</a></div>
            </div>
        </div>
    </div>

    <div class="form-group" v-if="payment.attachment != '' && payment.attachment != null">
        <div class="row">
            <div class="col-sm-2 text-right">
                <a class="btn btn-primary" href="{{payment.attachment}}">下载附件</a>
            </div>
        </div>
    </div>

    <div class="form-group">
        <table class="table table-bordered table-hover table-striped">
            <thead>
            <tr>
                <th>费用日期</th>
                <th>费用科目</th>
                <th>科目明细</th>
                <th>费用说明</th>
                <th>票据张数</th>
                <th>报销金额</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="paymentItem in payment.paymentDetails">
                <td>{{paymentItem.costDate}}</td>
                <td>{{paymentItem.costItemName}}</td>
                <td>{{paymentItem.costDetailName}}</td>
                <td>{{paymentItem.remark}}</td>
                <td>{{paymentItem.invoiceNum}}</td>
                <td>{{paymentItem.money}}</td>
            </tr>
            </tbody>
        </table>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label">报销说明:</label>
        <div class="col-sm-10">
            <div style="margin-top: 5px;">{{payment.reason}}</div>
        </div>
    </div>
    <jsp:include page="/WEB-INF/views/admin/approval/detail.jsp"></jsp:include>
</form>