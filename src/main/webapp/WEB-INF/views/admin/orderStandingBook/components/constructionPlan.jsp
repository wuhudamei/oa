<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="constructionPlanTmpl">
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
          <h4 class="panel-title" style="color:red">
            <span>客服：{{detail.serviceName | noName }}，</span>
            <span>设计师：{{detail.stylistName | noName }}，</span>
            <span>项目经理：{{person.itemManager | noName }}</span>
          </h4>
        </div>
        <span hidden>{{msg}}</span>
        <table class="table table-striped table-bordered table-hover">
          <thead>
            <tr align="center">
              <td>计划节点名称</td>
              <td>计划完成时间</td>
              <td>实际完成时间</td>
              <td>延期天数</td>
            </tr>
          </thead>
          <tbody>
            <tr align="center" v-for="list in tabPlanList">
              <td>{{list.nodeName}}</td>
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


        <div class="bootstrap-table table-ded" style=" padding:10px">
          <div class="fixed-table-container">
            <div class="fixed-table-body">
              <table width="100%" class="table table-striped table-bordered table-hover"
                     style="margin-top: 0px;">
                <tbody>
                <tr v-for="list in tabPlanList">
                  <td colspan="4">
                    <div class="card-views">
                      <div class="card-view">
                        <span class="title" style="text-align: center; ">计划节点名称</span>
                        <span class="value">{{list.nodeName}}</span>
                      </div>
                    </div>
                    <div class="card-views">
                      <div class="card-view">
                        <span class="title" style="text-align: center; ">计划完成时间</span>
                        <span class="value">{{ list.planDoneDate | goDate }}</span>
                      </div>
                    </div>
                    <div class="card-views">
                      <div class="card-view">
                        <span class="title" style="text-align: center; ">实际完成时间</span>
                        <span class="value">{{ list.realDoneDate | goDate }}</span>
                      </div>
                    </div>
                    <div class="card-views">
                      <div class="card-view">
                        <span class="title" style="text-align: center; ">延期天数</span>
                        <span class="value">{{ list.realDoneDate | isNo list.planDoneDate }}</span>
                      </div>
                    </div>
                  </td>
                </tr>
                <tr v-if="tabPlanList.length ===0" align="center">
                  <td colspan="4">
                    <div class="card-views">
                      <div class="card-view">
                        没有找到匹配的记录
                      </div>
                    </div>
                  </td>
                </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>


      </div>
    </div>


  </div>

</template>