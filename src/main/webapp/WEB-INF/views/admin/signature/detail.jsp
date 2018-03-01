<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<form name="createForm" novalidate class="form-horizontal" role="form">
    <div class="form-group">
        <div class="row">
            <label class="col-sm-2 control-label">申请标题</label>
            <div class="col-sm-4">
                <div style="margin-top: 5px;">{{signature.applyTitle}}</div>
            </div>

            <label class="col-sm-2 control-label">申请编码</label>
            <div class="col-sm-4">
                <div style="margin-top: 5px;">{{signature.applyCode}}</div>
            </div>
        </div>
    </div>


    <div class="form-group">
        <div class="row">
            <label class="col-md-2 control-label text-left">费用承担公司:</label>
            <div class="col-sm-4">
                <div style="margin-top: 5px;">{{signature.company.orgName}}</div>
            </div>

            <div>
                <label class="col-sm-2 control-label">费用申请月份:</label>
                <div class="col-sm-4">
                    <div style="margin-top: 5px;">{{signature.signatureDate}}</div>
                </div>
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="row">
            <label class="col-md-2 control-label">提交人:</label>
            <div class="col-sm-4">
                <div style="margin-top: 5px;">{{signature.createUser.name}}</div>
            </div>

            <div>
                <label class="col-sm-2 control-label">申请时间:</label>
                <div class="col-sm-4">
                    <div style="margin-top: 5px;">{{signature.createDate}}</div>
                </div>
            </div>
        </div>
    </div>

    <hr/>

    <div class="form-group">
        <div class="row">
            <label class="col-md-2 control-label">费用总额:</label>
            <div class="col-sm-4">
                <div style="margin-top: 5px;">{{signature.totalMoney}}</div>
            </div>
            <div class="col-sm-1"></div>
            <div class="col-sm-4" v-if="signature.attachment != ''">
                <a class="btn btn-primary" href="{{signature.attachment}}">下载附件</a>
            </div>
        </div>
    </div>

    <div class="form-group">
        <table class="table table-bordered table-hover table-striped">
            <thead>
            <tr>
                <th>费用科目</th>
                <th>科目明细</th>
                <th>费用说明</th>
                <th>费用金额</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="signatureItem in signature.signatureDetails">
                <td>{{signatureItem.costItemName}}</td>
                <td>{{signatureItem.costDetailName}}</td>
                <td>{{signatureItem.remark}}</td>
                <td>{{signatureItem.money}}</td>
            </tr>
            </tbody>
        </table>
    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label">费用说明:</label>
        <div class="col-sm-10">
            <div style="margin-top: 5px;">{{signature.reason}}</div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/admin/approval/detail.jsp"></jsp:include>
</form>