<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>写信</title>
<link rel="stylesheet" href="/static/css/tab.css">
<div id="container" class="wrapper wrapper-content" v-cloak>
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <!-- ibox start -->
    <div class="ibox-content">
        <validator name="validation">
            <form name="createMirror" id="createMirror" novalidate class="form-horizontal" role="form">
                <div>
                    <div class="form-group"
                         :class="{'has-error':$validation.title.invalid  && $validation.touched}">
                        <label class="col-sm-2 control-label"><font color="red">* </font>信件标题</label>
                        <div class="col-sm-8">
                            <input v-model="form.title"
                                   v-validate:title="{
                                            required:{rule:title,message:'请输入信件标题'}
                                        }"
                                   data-tabindex="1"
                                   id="title" name="title" class="form-control"
                                   placeholder="请输入信件标题" maxlength="500">
                            <div v-if="$validation.title.invalid && $validation.touched"
                                 class="help-absolute" style="margin-top: 0px">
                                <span v-if="$validation.title.invalid">请输入信件标题</span>
                            </div>
                        </div>
                    </div>

                    <div class="form-group" :class="{'has-error':$validation.content.invalid && $validation.touched}">
                        <label class="col-sm-2 control-label"><font color="red">* </font>信件内容</label>
                        <div class="col-sm-8">
                            <div v-el:ueditor v-if="isPc"></div>
                            <%--加载微信端--%>
                            <div v-if="!isPc">
                                <textarea v-model="form.content"
                                       v-validate:content="{
                                            required:{rule:title,message:'请输入信件内容'}
                                        }"
                                       data-tabindex="1"
                                       id="content" name="content" class="form-control"
                                          placeholder="请输入信件内容" maxlength="10000">
                                </textarea>
                                <div v-if="$validation.content.invalid && $validation.touched"
                                     class="help-absolute" style="margin-top: 0px">
                                    <span v-if="$validation.content.invalid">请输入信件内容</span>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div id="uploader" class="wu-example">
                        <!--用来存放文件信息-->
                        <div class="btns">
                            <%--微信端 选择图片--%>
                            <div class="row form-group" v-if="!isPc">
                                <label class="col-sm-2 col-xs-12 control-label">选择图片</label>
                                <div class="col-sm-8 col-xs-6 " style="text-align: center">
                                    <button id="imageUpload" type="button" class="btn btn-sm btn-primary" @click="chooseImage">上传图片</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row form-group" v-show="!isPc && localImageArr.length > 0">
                        <label class="col-sm-2 control-label">图片预览</label>
                        <div class="col-sm-8">
                            <div class="col-sm-2">
                                <img v-for="img in localImageArr" :src="img" width="100%" height="100%">
                                <button id="delImage" type="button" class="btn btn-sm btn-primary" @click="delImage">删除</button>
                            </div>
                        </div>
                    </div>

                    <%--微信端 开始录音--%>
                    <div class="row form-group" v-if="!isPc">
                        <label class="col-sm-2 col-xs-12 control-label">语音消息</label>
                        <div class="col-sm-3 col-xs-6" style="text-align: center">
                            <button id="voiceUrl" @click="startRecord" type="button" class="btn-sm "
                                    :class="voiceClass">{{voiceStatus}}</button>
                        </div>
                        <div class="col-sm-3 col-xs-6" v-show="showDelVoice">
                            <button id="delVoice" @click="delRecord(true)" type="button"
                                    class="btn btn-sm btn-danger" >删除录音</button>
                        </div>
                    </div>
                    <div class="form-group" v-if="!isPc && form.voiceUrl">
                        <div class="col-sm-3 col-xs-6" style="text-align: center">
                            <button id="playVoice" @click="play" type="button" class="btn-sm "
                                    :class="playClass">{{playVoice}}</button>
                        </div>
                        <div class="col-sm-3 col-xs-6">
                            <button id="stopVoice" @click="stopVoice()" type="button" v-show="showStop"
                                    class="btn btn-sm btn-danger" >停止播放</button>
                        </div>
                    </div>

                    <div class="form-group" >
                        <label class="col-sm-2 control-label"></label>
                        <div class="col-sm-4 control-label" style="text-align: left">
                            <input v-model="form.anonymous"
                                   data-tabindex="1"
                                   id="anonymous" name="anonymous" type="checkbox" >
                            <span class="help-absolute" style="margin-top: 0px">&nbsp匿名</span>
                        </div>
                    </div>


                </div>
                <div class="text-center">
                    <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
                    <button @click="cancel" type="button" data-dismiss="modal" class="btn">返回</button>
                </div>
            </form>
        </validator>
        <!-- ibox end -->
    </div>
</div>

<!-- container end-->
<%@include file="/WEB-INF/views/admin/components/ztree.jsp" %>
<script src="${ctx}/static/js/components/ztree.js"></script>
<script src="${ctx}/static/js/jquery.ztree.core.js"></script>
<script src="${ctx}/static/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" charset="utf-8" src="/static/vendor/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="/static/vendor/ueditor/ueditor.all.js"></script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=bEuehUvcPai3D7lVqTbCTE3rmE9N86Gp"></script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="/static/vendor/ueditor/lang/zh-cn/zh-cn.js"></script>
<script src="${ctx}/static/js/containers/chairmanMailbox/add.js"></script>
<link href="${ctx}/static/css/zTreeStyle.css" rel="stylesheet">
<%--引入微信js文件--%>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>