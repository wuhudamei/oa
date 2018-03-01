<%--
  Created by IntelliJ IDEA.
  User: liangyt
  Date: 17/7/28
  Time: 下午1:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="orgModalChoose" class="modal fade" tabindex="-1" data-width="760">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3>选择机构</h3>
    </div>
    <div class="modal-body">
        <div id="chooseTree"></div>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn">取消</button>
        <button type="button" class="btn btn-primary" @click="ok">确定</button>
    </div>
</div>

<script src="${ctx}/static/js/containers/organization/chooseOrg.js"></script>
