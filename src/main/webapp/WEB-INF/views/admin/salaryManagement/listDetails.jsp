<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<link href="${ctx}/static/css/zTreeStyle.css" rel="stylesheet">
<link rel="stylesheet" href="/static/vendor/webuploader/webuploader.css">

<title>我的工资</title>
<div id="container" class="wrapper wrapper-content">
  <div id="breadcrumb">
    <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
  </div>
  <!-- ibox start -->
  <div class="ibox-content">
        <div class="row">
          <form id="searchForm" @submit.prevent="fetchSalaryDetail">
            <div class="col-md-2">
              <input v-model="form.salaMonth" v-el:sala-month
                     id="salaMonth" maxlength="20" data-tabindex="1" readonly
                     name="salaMonth" type="text"
                     class="form-control datepicker" placeholder="工资月份">
            </div>

            <div class="col-md-2">
              <div class="form-group">
                <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default" alt="搜索"
                        title="搜索">
                  <i class="fa fa-search"></i>
                </button>

              </div>
            </div>
              <button onclick="javascript:history.back(1);" type="button" data-dismiss="modal" class="btn">返回</button>
          </form>
        </div>

        <div class="text-center">
              <p style="text-align: left;font-weight: bold;font-size: 18px;">基本薪资信息</p>
        </div>
        <table  class="table-responsive" style="border: 0px solid #ddd;">
            <tr>
                <td>
                    <p>员工姓名：<span style="font-weight: bold;">{{salaryDetail==null?salaryDetail.empName:salaryDetail.empName}}</span></p>
                </td>
                <td >
                    <p>员工编号：<span style="font-weight: bold;">{{salaryDetail==null?salaryDetail.orgCode:salaryDetail.orgCode}}</span></p>
                </td>
            </tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                <td>
                    <p>基本工资：<span style="font-weight: bold;color: #00a0e9;">{{salaryDetail==null?'0':salaryDetail.salaryBasicDn}}</span></p>
                </td>
                <td >
                    <p>绩效工资：<span style="font-weight: bold;color: #00a0e9;">{{salaryDetail==null?'0':salaryDetail.salaryPerformanceDn}}</span></p>
                </td>
            </tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                <td>
                    <p>应出勤天数：<span style="font-weight: bold;color: #00a0e9;">{{salaryDetail==null?'0':salaryDetail.shouldWorkDays}}</span></p>
                </td>
                <td >
                    <p>实际出勤天数：<span style="font-weight: bold;color: #00a0e9;">{{salaryDetail==null?'0':salaryDetail.practicalWorkDays}}</span></p>
                </td>
            </tr>
            <tr><td>&nbsp;</td></tr>
            <tr>
                <td>
                    <p>考勤系数：</span><span style="font-weight: bold;color: #00a0e9;">{{salaryDetail==null?'0':salaryDetail.workCoefficient}}</span></p>
                </td>
                <td>
                </td>
            </tr>
        </table>
        <hr />

        <div class="text-center">
            <p style="text-align: left;font-weight: bold;font-size: 18px;">补助信息</p>
        </div>
        <table class="table-responsive" style="border: 0px solid #ddd;">
          <tr>
              <td>
                  <p>餐补：<span style="font-weight: bold;color: #00B83F;">{{salaryDetail==null?'0':salaryDetail.mealSubsidyDn}}</span></p>
              </td>
              <td>
                  <p>其他补助：<span style="font-weight: bold;color: #00B83F;">{{salaryDetail==null?'0':salaryDetail.otherSubsidyDn}}</span></p>
              </td>
          </tr>
        </table>
        <hr />

        <div class="text-center">
            <p style="text-align: left;font-weight: bold;font-size: 18px;">扣款项</p>
        </div>
        <table class="table-responsive" style="border: 0px solid #ddd;">
          <tr>
              <td>
                  <p>个人社保扣款：<span style="font-weight: bold;color: red;">{{salaryDetail==null?'0':salaryDetail.socialSecurityPersonalDn}}</span></p>
              </td>
              <td>
                  <p>住房公积金：<span style="font-weight: bold;color: red;">{{salaryDetail==null?'0':salaryDetail.housingFundDn}}</span></p>
              </td>
          </tr>
          <tr><td>&nbsp;</td></tr>
          <tr>
              <td>
                  <p>扣款：<span style="font-weight: bold;color: red;">{{salaryDetail==null?'0':salaryDetail.deductMoneyDn}}</span></p>
              </td>
              <td>
                  <p>个人所得税：<span style="font-weight: bold;color: red;">{{salaryDetail==null?'0':salaryDetail.individualIncomeTaxDn}}</span></p>
              </td>
          </tr>
        </table>

        <hr />
        <div class="text-center">
            <p style="text-align: left;font-weight: bold;font-size: 18px;">补偿</p>
        </div>
        <table class="table-responsive" style="border: 0px solid #ddd;">
          <tr>
              <td>
                  <p>补偿金：<span style="font-weight: bold;color: #00B83F;">{{salaryDetail==null?'0':salaryDetail.compensationDn}}</span></p>
              </td>
              <td>

              </td>
          </tr>
        </table>

        <hr/>
        <table class="table-responsive" style="border: 0px solid #ddd;">
          <tr>
              <td>
                  <p style="font-size: 18px;font-weight: bold;text-align: right">实发工资：{{salaryDetail==null?'0':salaryDetail.practicalSalaryTotalDn}}</p>
              </td>
              <td>

              </td>
          </tr>
        </table>
      </form>
    <!-- ibox end -->
  </div>
</div>

<script src="${ctx}/static/js/containers/salaryManagement/listDetails.js"></script>