<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>上班下班</title>
    <script src="http://api.map.baidu.com/api?v=2.0&ak=bEuehUvcPai3D7lVqTbCTE3rmE9N86Gp"></script>
    <%--引入微信js文件--%>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <style>
        .row_margin_top_15 {
            margin-top: 15px;
        }

        [v-cloak] {
            display: none;
        }
    </style>
</head>
<div id="container" class="wrapper wrapper-content" v-cloak>
    <div v-show="noshow" style="width:100%;height:150px;border:#ccc solid 1px;" id="dituContent"></div>
    <%--<div v-show="weixin && noshow" style="width:100%;height:150px;border:#ccc solid 1px;" id="dituContent"></div>
    <div style="font-weight: 900">
        {{usersss}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{usercom}}
    </div>--%>

    <!-- 新版界面调整 begin -->
    <div class="ibox">
        <div class="ibox-title">
            {{usersss}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{usercom}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;当前位置：{{signType}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </div>
        <div class="ibox-content">
            <!-- 内勤打卡begin -->
            <div id="inSideDiv" v-show="!outSide">
                <validator name="validation">
                    <form v-cloak name="signInOut" novalidate class="form-horizontal">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-4">
                                    上班（上班时间&nbsp;&nbsp;{{depSignTime}}） &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;打卡时间：{{signtime}}
                                </div>
                            </div>
                            <div v-show="oldTypeShow" style="margin-top: 10px">上次打卡类型：{{oldType == 2 ? '外勤' : '内勤'}}
                            </div>
                            <div class="row row_margin_top_15">
                                <div class="col-sm-4" style="text-align: center">
                                    <button type="button" class="btn btn-primary" @click="submitClickHandler(true)"
                                            v-show="signtime != '' && loadingFlag">更新上班打卡信息
                                    </button>
                                </div>
                            </div>
                            <div class="row row_margin_top_15">
                                <div class="col-sm-4">
                                    下班（下班时间&nbsp;&nbsp;{{depSignOutTime}}） &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;打卡时间：{{signouttime}}
                                </div>
                            </div>
                            <div v-show="oldOutTypeShow" style="margin-top: 10px">上次打卡类型：{{oldOutType == 2 ? '外勤' :
                                '内勤'}}
                            </div>
                            <div class="row row_margin_top_15">
                                <div class="col-sm-4" style="text-align: center">
                                    <button type="button" class="btn btn-primary" @click="submitClickHandler(false)"
                                            v-show="signouttime != '' && loadingFlag">更新下班打卡信息
                                    </button>
                                </div>
                            </div>
                            <div class="row row_margin_top_15">
                                <div class="col-sm-4" style="text-align:center;">
                                    <span v-show="signtime == '' && signouttime == '' && loadingFlag"><img
                                            src="/static/img/sign_in.png" width="150" height="150"
                                            @click="submitClickHandler(true)"></span>
                                    <span v-show="signtime != '' && signouttime == ''  && loadingFlag"><img
                                            src="/static/img/sign_out.png" width="150" height="150"
                                            @click="submitClickHandler(false)"></span>
                                </div>
                            </div>
                            <div class="row row_margin_top_15">
                                <div class="col-sm-4" style="text-align: center;">
                                    <label style="text-align: center">当前时间：{{currentData}}</label><br>
                                    <label v-if="weixin" style="text-align: center">当前位置：{{currentaddress}}</label><br>
                                    <%--<span v-if="loadingFlag && weixin">正在获取地理位置，请稍等...</span>--%>
                                    <span v-if="signScope && weixin" style="color: red">&nbsp;&nbsp;&nbsp;&nbsp;您不在签到范围内！</span>
                                    <span v-if="!weixin && !hasSign " style="text-align: center; color: red"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(提示:请使用微信公众号签到!)</span>
                                </div>
                            </div>
                        </div>
                    </form>
                </validator>
            </div>
            <!-- 内勤打卡end -->

            <!-- 外勤打卡begin -->
            <div id="outSideDiv" v-show="outSide">
                <validator name="validation">
                    <form v-cloak name="signInOut" novalidate class="form-horizontal">
                        <div class="form-group">
                            <div class="row">
                                <div class="col-sm-4">
                                    上班（上班时间&nbsp;&nbsp;{{depSignTime}}） &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;打卡时间：{{signtime}}
                                </div>
                            </div>
                            <div v-show="oldTypeShow" style="margin-top: 10px">上次打卡类型：{{oldType == 2 ? '外勤' : '内勤'}}
                            </div>
                            <div class="row row_margin_top_15">
                                <div class="col-sm-4" style="text-align: center">
                                    <button type="button" class="btn btn-primary"
                                            @click="outSideSubmitClickHandler(true)" v-show="signtime != '' && loadingFlag">更新上班打卡信息
                                    </button>
                                </div>
                            </div>
                            <div class="row row_margin_top_15">
                                <div class="col-sm-4">
                                    下班（下班时间&nbsp;&nbsp;{{depSignOutTime}}） &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;打卡时间：{{signouttime}}
                                </div>
                            </div>
                            <div v-show="oldOutTypeShow" style="margin-top: 10px">上次打卡类型：{{oldOutType == 2 ? '外勤' :
                                '内勤'}}
                            </div>
                            <div class="row row_margin_top_15">
                                <div class="col-sm-4" style="text-align: center">
                                    <button type="button" class="btn btn-primary"
                                            @click="outSideSubmitClickHandler(false)" v-show="signouttime != '' && loadingFlag">更新下班打卡信息
                                    </button>
                                </div>
                            </div>
                            <div class="row row_margin_top_15">
                                <div class="col-sm-4" style="text-align:center;">
                                    <span v-show="signtime == '' && signouttime == '' && loadingFlag"><img
                                            src="/static/img/sign_in.png" width="150" height="150"
                                            @click="outSideSubmitClickHandler(true)"></span>
                                    <span v-show="signtime != '' && signouttime == ''  && loadingFlag"><img
                                            src="/static/img/sign_out.png" width="150" height="150"
                                            @click="outSideSubmitClickHandler(false)"></span>
                                </div>
                            </div>
                            <div class="row row_margin_top_15">
                                <div class="col-sm-4">
                                    <label>当前时间：{{currentData}}</label><br>
                                    <label v-if="weixin">当前位置：{{currentaddress}}</label><br>
                                    <%--<span v-if="loadingFlag && weixin">正在获取地理位置，请稍等...</span>--%>
                                    <span v-if="signScope && weixin" style="color: red">&nbsp;&nbsp;&nbsp;&nbsp;您不在签到范围内！</span>
                                    <span v-if="!weixin && !hasSign " style="text-align: center; color: red"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(提示:请使用微信公众号签到!)</span>
                                </div>
                            </div>
                        </div>
                    </form>
                </validator>
            </div>
            <!-- 外勤打卡end -->

        </div>
    </div>
    <!-- 新版界面调整 end -->

    <%--<table v-el:dataTable id="dataTable" width="100%"
           class="table table-striped table-bordered table-hover">
    </table>--%>
    <!-- ibox end -->
</div>
<script src="/static/js/containers/sign/sign.js?v=new Date()"></script>
</body>
</html>