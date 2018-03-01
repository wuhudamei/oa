<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<title>公告详情</title>
<div id="modal">
    <div class="wrapper wrapper-content">
        <div class="ibox-content">
                <div class="text-center">
                    <h3>{{notice.title}}</h3>
                </div>
                <hr/>
                <div class="row">
                    <div class="col-sm-12">
                        <div class="row">
                            <div class="form-group" style="margin-bottom: 45px;">
                                <div class="col-sm-12 noticeImg">
                                    {{{notice.content}}}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            <!-- ibox end -->
        </div>
        <div class="text-center">
            <button @click="goBack" type="button"  class="btn">返回</button>
        </div>
    </div>
</div>
<!-- container end-->
<script src="${ctx}/static/js/containers/noticeboard/noticedetils.js"></script>