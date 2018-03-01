<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <link rel="stylesheet" href="/static/vendor/webuploader/webuploader.css">
    <style>
        .form-control {
            padding: 6px 0px 6px 0px;
        }
    </style>
</head>
<title>年度预算</title>
<div id="container" class="wrapper wrapper-content animated fadeInRight" v-if="!isWeChat">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content" v-if="isWeChat">
            <h2 class="text-center">此数据格式只适用于pc端填写</h2>
        </div>
        <div class="ibox-content" v-if="!isWeChat">
            <h2 class="text-center">{{title}}</h2>
            <hr/>
            <!--                 <div class="row" style="margin-bottom: 10px;"> -->
            <!--                     <div class="col-md-12 text-center" style="font-size: 30px;"> -->
            <!--                         {{title}} -->
            <!--                     </div> -->
            <!--                 </div> -->
            <form novalidate class="form-horizontal">
                <div class="row" v-if="params.id">
                    <div class="col-md-2 text-right">
                        <label>申请编码：</label>
                    </div>
                    <div class="col-md-4">
                        <label>{{info.applyCode}}</label>
                    </div>
                    <div class="col-md-2 text-right">
                        <label>申请标题：</label>
                    </div>
                    <div class="col-md-4">
                        <label>{{info.applyTitle}}</label>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-2 text-right">
                        <label>公司：</label>
                    </div>
                    <div class="col-md-4">
                        <label>{{info.applyCompany.orgName}}</label>
                    </div>
                    <div class="col-md-2 text-right">
                        <label>预算月份：</label>
                    </div>
                    <div class="col-md-2" style="margin-top:-6px">
                        <input type="text" class="form-control" readonly style="border" id="yearBudgetId"
                               v-model="info.budgetYear">
                    </div>
                </div>
                </br>
                <div class="row">
                    <div class="col-md-2 text-right">
                        <label>提交人：</label>
                    </div>
                    <div class="col-md-4">
                        <label>{{info.applyUser.name}}</label>
                    </div>
                    <div class="col-md-2 text-right">
                        <label>申请时间：</label>
                    </div>
                    <div class="col-md-4">
                        <label>{{info.applyTime}}</label>
                    </div>
                </div>
            </form>
            <hr>
            <div class="row">
                <div class="col-md-6">
                    <label>预算总额：{{budgetTotal}}</label>
                </div>
                <div class="col-md-2 text-right">
                    <label class="">
                        <web-uploader
                                :type="webUploaderSub.type" :w-server="webUploaderSub.server"
                                :w-accept="webUploaderSub.accept"
                                :w-file-size-limit="webUploaderSub.fileSizeLimit"
                                :w-file-single-size-limit="webUploaderSub.fileSingleSizeLimit"
                                :w-form-data="{category:'APPLY_ATTACHMENT'}">
                            <button type="button" class="btn btn-primary">上传附件</button>
                        </web-uploader>
                    </label>
                </div>
                <div class="col-sm-1 text-right">
                    <a v-if="info.attachment != '' && info.attachment != null"
                       :href="info.attachment"
                       class="btn btn-sm btn-primary">
                        下载附件
                    </a>
                </div>
                <div class="col-sm-1 text-right">
                    <button @click="deleteAttachment()"
                            v-if="info.attachment != '' && info.attachment != null" type="button"
                            class="btn btn-sm btn-danger">
                        删除附件
                    </button>
                </div>
            </div>
            <table width="100%" class="table table-striped table-bordered table-hover" style="margin-top: 0px;">
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
                <tr v-for="item in info.yearBudgetDetailList">
                    <td>{{item.subjectCode | show-name}}</td>
                    <td><input number v-model="item.january" class="form-control"
                               :style="{'borderColor': typeof item.january === 'string' ? 'red' : ''}"/></td>
                    <td><input number v-model="item.february" class="form-control"
                               :style="{'borderColor': typeof item.february === 'string' ? 'red' : ''}"/></td>
                    <td><input number v-model="item.march" class="form-control"
                               :style="{'borderColor': typeof item.march === 'string' ? 'red' : ''}"/></td>
                    <td><input number v-model="item.april" class="form-control"
                               :style="{'borderColor': typeof item.april === 'string' ? 'red' : ''}"/></td>
                    <td><input number v-model="item.may" class="form-control"
                               :style="{'borderColor': typeof item.may === 'string' ? 'red' : ''}"/></td>
                    <td><input number v-model="item.june" class="form-control"
                               :style="{'borderColor': typeof item.june === 'string' ? 'red' : ''}"/></td>
                    <td><input number v-model="item.july" class="form-control"
                               :style="{'borderColor': typeof item.july === 'string' ? 'red' : ''}"/></td>
                    <td><input number v-model="item.august" class="form-control"
                               :style="{'borderColor': typeof item.august === 'string' ? 'red' : ''}"/></td>
                    <td><input number v-model="item.september" class="form-control"
                               :style="{'borderColor': typeof item.september === 'string' ? 'red' : ''}"/></td>
                    <td><input number v-model="item.october" class="form-control"
                               :style="{'borderColor': typeof item.october === 'string' ? 'red' : ''}"/></td>
                    <td><input number v-model="item.november" class="form-control"
                               :style="{'borderColor': typeof item.november === 'string' ? 'red' : ''}"/></td>
                    <td><input number v-model="item.december" class="form-control"
                               :style="{'borderColor': typeof item.december === 'string' ? 'red' : ''}"/></td>
                </tr>
                </tbody>
            </table>

            <div class="row">
                <div class="col-md-1">
                    预算说明：
                </div>
                <div class="col-md-11">
                    <textarea name="" id="" cols="100" rows="5" v-model="info.reason"></textarea>
                </div>
            </div>
            <div class="row" style="margin-top: 35px;">
                <div class="col-md-12 text-center">
                    <input type="button" class="btn btn-primary" value="提交" @click="commit"/>
                    <input type="button" class="btn btn-info" value="保存" @click="save"/>
                    <input type="button" class="btn btn-default" value="取消" @click="cancel"/>
                </div>
            </div>
        </div>
    </div>
    <!-- ibox end -->
</div>

<div class="modal fade" tabindex="-1" role="dialog" id="askDialog" v-if="!isWeChat">
    <div class="modal-content">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                    aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">{{title}}</h4>
        </div>
        <div class="modal-body">
            <p>{{content}}</p>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-default" @click="cancel">取消</button>
            <button type="button" class="btn btn-primary" @click="ok">确定</button>
        </div>
    </div><!-- /.modal-content -->
</div>
<!-- /.modal -->

<script src="${ctx}/static/vendor/webuploader/webuploader.js"></script>
<script src="${ctx}/static/js/components/webuploader.js"></script>
<script src="${ctx}/static/js/containers/yearbudget/decimal.min.js"></script>
<script src="${ctx}/static/js/containers/yearbudget/apply.js"></script>
    
