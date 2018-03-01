<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<title>规则设置</title>
<!-- 面包屑 -->
<div id="container" class="wrapper wrapper-content">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">

            <validator name="validation">
                <%--<h2 class="text-center">{{title}}</h2>--%>
                <hr/>
                <form name="setUpRules" novalidate class="form-horizontal" style="width: 80%" role="form">

                    <h2 class="text-center">设计师等级及提成设置</h2>
                    <div class="form-group" style="text-align:-webkit-center">
                        <table class="table table-bordered table-hover table-striped" style="width:95%;">
                            <thead>
                            <tr>
                                <th>等级分类</th>
                                <th>排名比例%</th>
                                <th>直接费用提成比例%</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="level in levels">
                                <td>{{level.name}}</td>
                                <td><input type="number" class="form-control" style="border-style:none"
                                           v-model="level.ratio1"/></td>
                                <td><input type="number" class="form-control" style="border-style:none"
                                           v-model="level.ratio2"/></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <hr/>

                    <h2 class="text-center">间接费用提成比例设置</h2>
                    <div class="form-group" style="text-align:-webkit-center">
                        <table class="table table-bordered table-hover table-striped" style="width:95%;">
                            <thead>
                            <tr>
                                <th>间接费用类目</th>
                                <th>提成比例%</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="indirection in indirections">
                                <td>{{indirection.name}}</td>
                                <td><input type="number" class="form-control" style="border-style:none"
                                           v-model="indirection.ratio1"/></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <hr/>

                    <h2 class="text-center">发放比例设置</h2>
                    <div class="form-group" style="text-align:-webkit-center">
                        <table class="table table-bordered table-hover table-striped" style="width:95%;">
                            <thead>
                            <tr>
                                <th>名称</th>
                                <th>发放比例%</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="grant in grants">
                                <td>{{grant.name}}</td>
                                <td><input type="number" class="form-control" style="border-style:none"
                                           v-model="grant.ratio1"/></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <hr/>

                    <div class="text-center">
                        <button @click="submit" :disabled="disabled" type="button"
                                class="btn btn-info">保存
                        </button>
                    </div>
                </form>
            </validator>

        </div>
    </div>
    <!-- ibox end -->
</div>
<!-- container end-->

<script src="${ctx}/static/js/containers/stylist/rules.js?v=1.0"></script>