<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="materialCostTmpl">
    <div class="panel-group panel-stranding-book" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingMain">
                <span hidden>{{msg}}</span>
                <h4 class="panel-title">
                    <a class="text-center" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseMain" aria-expanded="true" aria-controls="collapseMain">
                        <span class="title">主材</span> <br/> 总计：{{total1}}（项） 总金额：{{mainMaterialTotalAmount}}
                    </a>
                </h4>
            </div>
            <div id="collapseMain" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingMain">
                <div class="panel-body">
                    <table v-el:data-table-main-material width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
        </div>
        <%--暂时注销不要删除--%>
        <%--<div class="panel panel-default">--%>
        <%--<div class="panel-heading" role="tab" id="headingEight">--%>
        <%--<h4 class="panel-title">--%>
        <%--<a class="collapsed text-center" role="button" data-toggle="collapse" data-parent="#accordion" href="#auxiliaryMaterial" aria-expanded="false" aria-controls="collapseEight">--%>
        <%--<span class="title">辅材</span> <br/> 总计：{{total2}}（项）  总金额：{{toMaterialDataRows}}--%>
        <%--</a>--%>
        <%--</h4>--%>
        <%--</div>--%>
        <%--<div id="auxiliaryMaterial" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingEight">--%>
        <%--<div class="panel-body">--%>
        <%--<table v-el:data-table-material width="100%"--%>
        <%--class="table table-striped table-bordered table-hover">--%>
        <%--</table>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
    </div>
    <!--/.panel-group-->
</template>