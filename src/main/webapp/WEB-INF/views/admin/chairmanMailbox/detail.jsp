<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>公告详情</title>
<div id="modal">
    <div id="breadcrumb">
        <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
    </div>
    <div class="wrapper wrapper-content">
        <div class="ibox-content">
                <div class="text-center">
                    <h3>{{mailbox.title}}</h3>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="row">
                            <div class="form-group" style="margin-bottom: 45px;">
                                <div class="col-sm-2"></div>

                                <div class="col-sm-10 noticeImg">
                                    {{{mailbox.content}}}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            <!-- ibox end -->
        </div>

        <%--<div class="text-center">
            <hr>
        </div>--%>
    </div>
    <div class="ibox-content" v-cloak>
        <validator name="validation">
            <form name="createMirror" id="createMirror" novalidate class="form-horizontal" role="form">
                <div>

                    <%--有语音消息时 显示--%>
                    <div class="form-group" v-if="mailbox.voiceUrl">
                        <label class="col-sm-2 control-label">语音消息</label>
                        <div class="col-sm-8">
                                <audio src="{{mailbox.voiceUrl}}" controls preload="none"></audio>
                        </div>
                    </div>
                    <%--有图片时 显示--%>
                    <div class="form-group" v-if="pictureUrlArr">
                        <label class="col-sm-2 control-label">图片</label>
                        <div class="col-sm-8">
                            <div v-for="pic in pictureUrlArr">
                                <img src="{{pic}}" alt="" width="100%" height="100%"/>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">重要程度</label>
                        <div class="col-sm-8">
                            <select v-model="mailbox.importantDegree"
                                    id="importantDegree"
                                    name="importantDegree"
                                    class="form-control" >
                                <option value="1">普通</option>
                                <option value="2">重要</option>
                                <option value="3">紧急</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label">批注</label>
                        <div class="col-sm-8">
                            <textarea v-model="mailbox.comment"
                                   id="comment" name="comment" class="form-control"
                                   placeholder="请输入信件批注" rows="3" maxlength="1000">
                            </textarea>
                        </div>
                    </div>

                </div>
                <div class="text-center">
                    <button @click="submit" :disabled="disabled"  type="button" class="btn btn-primary">提交</button>
                    <button @click="goBack" type="button"  class="btn">返回</button>
                </div>
            </form>
        </validator>
        <!-- ibox end -->
    </div>
</div>
<!-- container end-->
<script src="${ctx}/static/js/containers/chairmanMailbox/detail.js"></script>