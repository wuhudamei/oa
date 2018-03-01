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
                <div style="margin-top: 5px;">{{signature.company}}</div>
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
                <div style="margin-top: 5px;">{{signature.applyName}}</div>
            </div>

            <div>
                <label class="col-sm-2 control-label">申请时间:</label>
                <div class="col-sm-4">
                    <div style="margin-top: 5px;">{{signature.applyDate}}</div>
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
    <div class="row ">
        <div class="col-sm-12">
            <table class="table table-striped table-bordered table-hover text-center">
                <thead>
                <tr class="text-center">
                    <td>费用科目</td>
                    <td>月度可用余额</td>
                    <td>年度预算剩余</td>
                    <td>科目明细</td>
                    <td>费用说明</td>
                    <td>费用金额</td>
                </tr>
                </thead>
                <tbody v-for="item in signature.costItems">
                <tr v-for="ite in item.costDetails">
                    <td v-if="$index===0"
                        :rowspan="item.costDetails.length">{{item.costItemName}}
                    </td>
                    <td v-if="$index===0"
                        :rowspan="item.costDetails.length">{{item.costRemain}}
                    </td>
                    <td v-if="$index===0"
                        :rowspan="item.costDetails.length">{{item.yearRemain}}
                    </td>
                    <td>{{ite.detailName}}</td>
                    <td>{{ite.remark}}</td>
                    <td><span
                            v-if="item.itemMoney>item.costRemain" style="color: red">{{ite.money}}</span>
                        <span v-if="item.itemMoney<item.costRemain" style="color: blue">{{ite.money}}</span></td>
                </tr>
                </tbody>
            </table>
        </div>

    </div>

    <div class="form-group">
        <label class="col-sm-2 control-label">费用说明:</label>
        <div class="col-sm-10">
            <div style="margin-top: 5px;">{{signature.remark}}</div>
        </div>
    </div>

    <jsp:include page="/WEB-INF/views/admin/approval/detail.jsp"></jsp:include>
</form>