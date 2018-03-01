<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<title>竣工工地数据</title>
<head>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link rel="stylesheet" href="/static/vendor/webuploader/webuploader.css">
</head>
<body id="app" class="fixed-sidebar full-height-layout gray-bg">
<div id="container" class="wrapper wrapper-content" v-cloak>
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div>
                <div class="row">
                    <form id="searchForm" @submit.prevent="fetchData">
                        <div class="col-md-2">
                            <div class="form-group">
                                <input v-model="form.keyword" v-el:completion-year
                                       maxlength="20" data-tabindex="1" readonly
                                       type="text"
                                       class="form-control datetime input-sm" placeholder="年份">
                            </div>
                        </div>
                        <div class="col-md-1">
                            <div class="form-group">
                                <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default"
                                        alt="搜索"
                                        title="搜索">
                                    <i class="fa fa-search"></i>
                                </button>
                            </div>
                        </div>

                        <!-- 导入按钮 -->
                        <div class="col-md-6 text-right">
                            <div class="form-group">
                                <shiro:hasPermission name="stylist:assessment-template">
                                    <button @click="downLoadExcel" id="downLoadExcel" type="button"
                                            class="btn btn-outline btn-primary">模板下载</button>
                                </shiro:hasPermission>
                                <shiro:hasPermission name="stylist:assessment-batchAdd">
                                    <label class="control-label btn btn-primary"> <web-uploader
                                            :type="webUploaderSub.type" :w-server="webUploaderSub.server"
                                            :w-accept="webUploaderSub.accept"
                                            :w-file-size-limit="webUploaderSub.fileSizeLimit"
                                            :w-file-single-size-limit="webUploaderSub.fileSingleSizeLimit"
                                            :w-form-data="webUploaderSub.formData">批量导入</web-uploader>
                                    </label>
                                </shiro:hasPermission>
                                <button @click="orderInsert" id="orderInsertBtn" type="button"
                                        class="btn btn-outline btn-primary">同步
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
                <!-- <div class="columns columns-right btn-group pull-right"></div> -->
                <table width="100%" class="table table-striped table-bordered table-hover">
                    <thead>
                    <tr align="center">
                        <td>城市</td>
                        <td>一月份</td>
                        <td>二月份</td>
                        <td>三月份</td>
                        <td>四月份</td>
                        <td>五月份</td>
                        <td>六月份</td>
                        <td>七月份</td>
                        <td>八月份</td>
                        <td>九月份</td>
                        <td>十月份</td>
                        <td>十一月份</td>
                        <td>十二月份</td>
                        <td>合计</td>
                    </tr>
                    </thead>
                    <tbody align="center" v-for="item in completionSiteDataList">
                    <tr >
                        <td style="vertical-align: middle;"><a class="btn" @click="viewDetails(item.storeName)">{{item.storeName}}</a></td>
                        <td style="vertical-align: middle;">{{item.januaryCount}}</td>
                        <td style="vertical-align: middle;">{{item.februaryCount}}</td>
                        <td style="vertical-align: middle;">{{item.marchCount}}</td>
                        <td style="vertical-align: middle;">{{item.aprilCount}}</td>
                        <td style="vertical-align: middle;">{{item.mayCount}}</td>
                        <td style="vertical-align: middle;">{{item.juneCount}}</td>
                        <td style="vertical-align: middle;">{{item.julyCount}}</td>
                        <td style="vertical-align: middle;">{{item.augustCount}}</td>
                        <td style="vertical-align: middle;">{{item.septemberCount}}</td>
                        <td style="vertical-align: middle;">{{item.octoberCount}}</td>
                        <td style="vertical-align: middle;">{{item.novemberCount}}</td>
                        <td style="vertical-align: middle;">{{item.decemberCount}}</td>
                        <td style="vertical-align: middle;">
                            {{item.decemberCount +item.novemberCount+item.octoberCount+item.septemberCount+item.augustCount+item.julyCount+item.juneCount+item.mayCount+item.aprilCount+item.marchCount+item.februaryCount+item.januaryCount}}
                        </td>
                    </tr>
                    <tr v-if="completionSiteDataList.length == 0" align="center">
                        <td colspan="14">
                            没有找到匹配的记录
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <!-- ibox end -->
</div>
<script src="${ctx}/static/vendor/webuploader/webuploader.js"></script>
<script src="${ctx}/static/js/components/webuploader.js"></script>
<script src="/static/js/containers/completionSiteData/list.js"></script>
</body>
</html>