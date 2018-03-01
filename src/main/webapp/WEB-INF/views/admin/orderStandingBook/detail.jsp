<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>详情</title>
    <link rel="stylesheet" href="/static/css/tab.css">
    <style>
        .panel-stranding-book .panel-heading {
            background: #f5f7f9;
            padding: 5px 15px 7px 15px;
        }

        .panel-stranding-book .panel-title a {
            display: inline-block;
            width: 100%;
            color: #657180;
            font-weight: normal;
        }

        .panel-stranding-book .panel-title .title {
            margin-right: 40px;
        }

        .detail-stranding-book {
            margin-bottom: 20px;
        }

        [v-cloak] {
            display: none;
        }

        .border-bottom-style {
            border-bottom: 1px solid #ddd !important
        }

        .table-last-color-light tbody tr:last-child {
            color: red
        }

        .order-content .title-state {
            display: none
        }

        .order-content .panel-default {
            border: none
        }

        @media screen and (max-width: 500px) {
            .order-content .title-state {
                display: block;
                border-bottom: none !important;
                margin-bottom: 15px;
            }

            .order-content .detail-state {
                margin: 0 0;
                border-radius: 0 0 5px 5px;
                max-height: 0px;
                min-height: 0px;
                overflow: hidden;
                -webkit-transition: max-height .4s;
                transition: max-height .4s;
            }

            .order-content .hasHeight {
                max-height: 1000px;
                transition: max-height ease-in .4s;
            }
        }
    </style>
</head>


<body id="app" class="fixed-sidebar full-height-layout gray-bg">
<div id="container" class="wrapper wrapper-content">
    <!-- 面包屑 -->
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>


    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content order-content">
            <div class="panel-group">
                <div class="panel panel-default">
                    <div class="panel-heading border-bottom-style title-state">
                        <h4 @click="orderToggle" class="panel-title text-center">订单详情</h4>
                    </div>
                    <div :class="{hasHeight:isShow }" class="row detail-stranding-book detail-state" v-cloak>

                        <div class="col-sm-4 col-xs-12">
                            <label for="">项目编号：</label>
                            {{details.orderNo}}
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">客户姓名：</label>
                            {{details.customerName}}
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">所选套餐：</label>
                            {{details.selectedPackage}}
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">建筑面积：</label>
                            {{details.constructionArea}}
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">联系方式：</label>
                            {{details.contact}}
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">房屋地址：</label>
                            {{details.houseAddress}}
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">房屋户型：</label>
                            {{details.houseDoorModel}}
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">有无电梯：</label>
                            {{elevator}}
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">房屋状况：</label>
                            {{houseConditions}}
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">计价面积：</label>
                            {{details.valuationArea}}
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">第二联系人：</label>
                            {{secondContact}}
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">第二联系人电话：</label>
                            {{secondContactTel}}
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">计划开工时间：</label>
                            {{planStartTime }}
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">计划完成时间：</label>
                            {{planFinishTime }}
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">订单状态：</label>
                            <span style="color: red;">{{orderStatus}}</span>
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">施工合同总金额：</label>
                            {{details.orderAmount}}
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">套餐金额：</label>
                            {{selectedPackageAmount}}
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">变更金额：</label>
                            {{details.changeMoney}}
                        </div>
                        <div class="col-sm-4 col-xs-12">
                            <label for="">现合同金额：</label>
                            {{nowOrderAmount}}
                        </div>
                    </div>
                </div>
            </div>
            <div>

                <!--/.row-->
                <Tabs v-ref:tabs type="card" @on-click="clickEvent">
                    <Tab-pane v-for="($index,item) in tabList" :label="item.label">
                        <sale-component v-if="$index == 0" :msg="index"></sale-component>
                        <selection-list-component v-if="$index == 1" :msg="index"></selection-list-component>
                        <change-component v-if="$index == 2" :msg="index"></change-component>
                        <select-material-component v-if="$index == 3" :msg="index"></select-material-component>
                        <dismantle-component v-if="$index == 4" :msg="index"></dismantle-component>
                        <audit-component v-if="$index == 5" :msg="index"></audit-component>
                        <project-component v-if="$index == 6" :msg="index"></project-component>
                        <quality-testing-component v-if="$index == 7" :msg="index"></quality-testing-component>
                        <customer-management-component v-if="$index == 8" :msg="index"></customer-management-component>
                        <pay-component v-if="$index == 9" :msg="index"></pay-component>
                        <installed-base-component v-if="$index == 10" :msg="index"></installed-base-component>
                        <material-installation-component v-if="$index == 11" :msg="index"></material-installation-component>
                        <supply-component v-if="$index == 12" :msg="index"></supply-component>
                        <material-cost-component  v-if="$index == 13" :msg="index"></material-cost-component>
                        <%--<construction-cost-component v-if="$index == 14" :msg="index"></construction-cost-component>--%>
                        <gross-profit-component v-if="$index == 14" :msg="index"></gross-profit-component>
                        <%--<staff-cost-component v-if="$index == 14"></staff-cost-component>--%>
                        <%--<construction-plan-component v-if="$index == 14" :msg="index"></construction-plan-component>--%>
                    </Tab-pane>
                </Tabs>
                <div>
                </div>
            </div>
        </div>
        <!-- ibox end -->


    </div>
    <!-- container end-->

    <%@include file="components/selectMaterial.jsp" %>
    <%@include file="components/pay.jsp" %>
    <%@include file="components/materialCost.jsp" %>
    <%@include file="components/staffCost.jsp" %>
    <%@include file="components/dismantle.jsp" %>
    <%--<%@include file="components/constructionPlan.jsp" %>--%>
    <%@include file="components/installedBase.jsp" %>
    <%@include file="components/audit.jsp" %>
    <%@include file="components/change.jsp" %>
    <%@include file="components/project.jsp" %>
    <%@include file="components/sale.jsp" %>
    <%@include file="components/selectList.jsp" %>
    <%@include file="components/qualityTesting.jsp" %>
    <%@include file="components/customerManagement.jsp" %>
    <%@include file="components/materialInstallation.jsp" %>
    <%@include file="components/supply.jsp" %>
    <%--<%@include file="components/constructionCost.jsp" %>--%>
    <%@include file="components/grossProfit.jsp" %>
    <script src="${ctx}/static/js/components/tab.js?v=1.0"></script>
    <!--<script src="${ctx}/static/js/containers/yearbudget/budgets.js?v=1.1"></script>-->
    <script src="${ctx}/static/js/containers/orderStandingBook/components/grossProfit.js"></script>
    <script src="${ctx}/static/js/containers/orderStandingBook/components/selectMaterial.js"></script>
    <script src="${ctx}/static/js/containers/orderStandingBook/components/materialCost.js"></script>
    <script src="${ctx}/static/js/containers/orderStandingBook/components/pay.js"></script>
    <script src="${ctx}/static/js/containers/orderStandingBook/components/dismantle.js"></script>
    <%--<script src="${ctx}/static/js/containers/orderStandingBook/components/constructionPlan.js"></script>--%>
    <script src="${ctx}/static/js/containers/orderStandingBook/components/staffCost.js"></script>
    <script src="${ctx}/static/js/containers/orderStandingBook/components/installedBase.js"></script>
    <script src="${ctx}/static/js/containers/orderStandingBook/components/sale.js"></script>
    <script src="${ctx}/static/js/containers/orderStandingBook/components/change.js"></script>
    <script src="${ctx}/static/js/containers/orderStandingBook/components/audit.js"></script>
    <script src="${ctx}/static/js/containers/orderStandingBook/components/project.js"></script>
    <script src="${ctx}/static/js/containers/orderStandingBook/components/selectList.js"></script>
    <script src="${ctx}/static/js/containers/orderStandingBook/components/qualityTesting.js"></script>
    <script src="${ctx}/static/js/containers/orderStandingBook/components/customerManagement.js"></script>
    <script src="${ctx}/static/js/containers/orderStandingBook/components/materialInstallation.js"></script>
    <script src="${ctx}/static/js/containers/orderStandingBook/components/supply.js"></script>
    <%--<script src="${ctx}/static/js/containers/orderStandingBook/components/constructionCost.js"></script>--%>
    <script src="${ctx}/static/js/containers/orderStandingBook/detail.js"></script>
</body>

