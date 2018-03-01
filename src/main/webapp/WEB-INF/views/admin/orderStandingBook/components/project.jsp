<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="projectTmpl">
  <div>
    <style>
      .table-ded{display: none;}
      @media screen and (max-width: 500px) {
        .table-de{ display: none;}
        .table-ded{ display: block}
      }

    </style>
    <div class="panel-group">
      <div class="panel panel-default">
        <div class="panel-heading border-bottom-style">
            <h4 align="center"><span>工程进度</span></h4>
            <span>现场交底：{{person.disclosure | goDate }}&nbsp;&nbsp;</span>
            <span>确认开工：{{person.confirmation | goDate }}</span>
        </div>
        <span hidden>{{msg}}</span>
        <table class="table table-striped table-bordered table-hover table-de">
          <thead>
            <tr align="center">
              <td>计划节点名称</td>
              <td>排期时间</td>
              <td>计划完成时间</td>
              <td>实际完成时间</td>
              <td>延期天数</td>
            </tr>
          </thead>
          <tbody>
            <tr align="center" v-for="list in tabPlanList">
              <td>{{list.nodeName}}</td>
              <td>{{list.planCheckTime | goDate }}</td>
              <td>{{list.planDoneDate | goDate }}</td>
              <td>{{list.realDoneDate | goDate }}</td>
              <td>{{list.realDoneDate | isNo list.planDoneDate }}</td>
            </tr>
            <tr v-if="tabPlanList.length ===0" align="center">
              <td colspan="4">
                    没有找到匹配的记录
              </td>
            </tr>
          </tbody>
        </table>
        <div class="panel-heading border-bottom-style">
          <h4 class="panel-title" align="center">
            <span>延期单</span>
          </h4>
        </div>
        <table class="table table-striped table-bordered table-hover table-de">
          <thead>
          <tr align="center">
            <td>延期阶段</td>
            <td>延期类别</td>
            <td>延期原因</td>
            <td>延期开始</td>
            <td>延期结束</td>
            <td>延期天数</td>
            <td>延期状态</td>
          </tr>
          </thead>
          <tbody>
          <tr align="center" v-for="list in delaySheet">
            <td>{{list.label}}</td>
            <td>{{list.delayTypeName }}</td>
            <td>{{list.deferredInstruction }}</td>
            <td>{{list.delayBeginDatetime | goDate }}</td>
            <td>{{list.delayEndDatetime | goDate }}</td>
            <td>{{list.delayDays}}</td>
            <td>{{list.status | goType}}</td>
          </tr>
          <tr v-if="delaySheet.length ===0" align="center">
            <td colspan="7">
              没有找到匹配的记录
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>


  </div>

</template>