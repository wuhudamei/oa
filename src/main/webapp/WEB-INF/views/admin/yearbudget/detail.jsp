<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style type="text/css">
	  body {
		font-family: arial;
	  }
	
	  .table-loop {
		border: 1px solid #ccc;
		width: 100%;
		margin:0;
		padding:0;
		border-collapse: collapse;
		border-spacing: 0;
		margin: 0 auto;
	  }
	
	  .table-loop tr {
		border: 1px solid #ddd;
		padding: 5px;
	  }
	
	  .table-loop th, .table-loop td {
		padding: 10px;
		text-align: center;
	  }
	
	  .table-loop th {
		text-transform: uppercase;
		font-size: 14px;
		letter-spacing: 1px;
	  }

		
	
	  @media only screen and (max-width: 800px)  {
	
		.table-loop {
		  border: 0;
		}
	
		.table-loop thead {
		  display: none;
		}
	
		.table-loop tr {
		  margin-bottom: 10px;
		  display: block;
		  /* border-bottom: 2px solid #ddd; */
		}
	
		.table-loop td {
		  display: block;
		  text-align: right;
		  font-size: 13px;
		  /* border-bottom: 1px dotted #ccc; */
		}
	
		.table-loop td:last-child {
		  border-bottom: 0;
		}
	
		.table-loop td:before {
		  content: attr(data-label);
		  float: left;
		  text-transform: uppercase;
		  font-weight: bold;
		}
	  }
	
	</style>
<div class="ibox">
    <div class="ibox-content">
        <div class="row" style="margin-bottom: 10px;">
            <div class="col-md-12 text-center" style="font-size: 30px;">
                {{budget.applyTitle}}
            </div>
        </div>
        <form novalidate class="form-horizontal">
            <div class="row">
                <div class="col-md-2 text-right">
                        <label>申请编号：</label>
                </div>
                <div class="col-md-4">
                        <label>{{budget.applyCode}}</label>
                </div>
                <div class="col-md-2 text-right">
                        <label>申请标题：</label>
                </div>
                <div class="col-md-4">
                        <label>{{budget.applyTitle}}</label>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2 text-right">
                        <label>公司：</label>
                </div>
                <div class="col-md-4">
                        <label>{{budget.applyCompany.orgName}}</label>
                </div>
                <div class="col-md-2 text-right">
                        <label>预算年份：</label>
                </div>
                <div class="col-md-4">
                        <label>{{budget.budgetYear}}</label>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2 text-right">
                        <label>提交人：</label>
                </div>
                <div class="col-md-4">
                        <label>{{budget.applyUser.name}}</label>
                </div>
                <div class="col-md-2 text-right">
                        <label>申请时间：</label>
                </div>
                <div class="col-md-4">
                        <label>{{budget.applyTime}}</label>
                </div>
            </div>
        </form>

        <hr>
        <div class="row">
            <div class="col-md-6">
                <label>预算总额：{{budget.totalMoney}}</label>
            </div>
            <div class="col-sm-1"></div>
            <div class="col-sm-4" v-if="budget.attachment != ''">
                <a class="btn btn-primary" href="{{budget.attachment}}">下载附件</a>
            </div>
        </div>
        <table width="100%" class="table table-striped  table-hover table-loop" style="margin-top: 0px;">
            <thead>
            <tr>
                <th style="text-align: center; " width="10%">
                    <div class="th-inner ">费用科目</div>
                    <div class="fht-cell"></div>
                </th>
                <th style="text-align: center; ">
                    <div class="th-inner ">1月</div>
                    <div class="fht-cell"></div>
                </th>
                <th style="text-align: center; ">
                    <div class="th-inner ">2月</div>
                    <div class="fht-cell"></div>
                </th>
                <th style="text-align: center; ">
                    <div class="th-inner ">3月</div>
                    <div class="fht-cell"></div>
                </th>
                <th style="text-align: center; ">
                    <div class="th-inner ">4月</div>
                    <div class="fht-cell"></div>
                </th>
                <th style="text-align: center; ">
                    <div class="th-inner ">5月</div>
                    <div class="fht-cell"></div>
                </th>
                <th style="text-align: center; ">
                    <div class="th-inner ">6月</div>
                    <div class="fht-cell"></div>
                </th>
                <th style="text-align: center; ">
                    <div class="th-inner ">7月</div>
                    <div class="fht-cell"></div>
                </th>
                <th style="text-align: center; ">
                    <div class="th-inner ">8月</div>
                    <div class="fht-cell"></div>
                </th>
                <th style="text-align: center; ">
                    <div class="th-inner ">9月</div>
                    <div class="fht-cell"></div>
                </th>
                <th style="text-align: center; ">
                    <div class="th-inner ">10月</div>
                    <div class="fht-cell"></div>
                </th>
                <th style="text-align: center; ">
                    <div class="th-inner ">11月</div>
                    <div class="fht-cell"></div>
                </th>
                <th style="text-align: center; ">
                    <div class="th-inner ">12月</div>
                    <div class="fht-cell"></div>
                </th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="item in budget.yearBudgetDetailList">
                <td data-label="费用科目">{{item.subjectName | detail-show-name}}</td>
                <td data-label="1月">{{item.january}}</td>
                <td data-label="2月">{{item.february}}</td>
                <td data-label="3月">{{item.march}}</td>
                <td data-label="4月">{{item.april}}</td>
                <td data-label="5月">{{item.may}}</td>
                <td data-label="6月">{{item.june}}</td>
                <td data-label="7月">{{item.july}}</td>
                <td data-label="8月">{{item.august}}</td>
                <td data-label="9月">{{item.september}}</td>
                <td data-label="10月">{{item.october}}</td>
                <td data-label="11月">{{item.november}}</td>
                <td data-label="12月">{{item.december}}</td>
            </tr>
            </tbody>
        </table>
		  <div class="form-group">
		    <label class="col-sm-2 control-label">预算说明:</label>
		    <div class="col-sm-10">
		      <div style="margin-top: 5px;">{{budget.reason}}</div>
		    </div>
		  </div>
    </div>
<jsp:include page="/WEB-INF/views/admin/approval/detail.jsp"></jsp:include>
</div>
