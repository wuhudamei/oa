<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!-- 审批按钮 -->
<div class="form-group">
    <input type="hidden" v-model="form.id">
    <input type="hidden" v-model="form.formId">
    <input type="hidden" v-model="form.approverId">
    <input type="hidden" v-model="form.nodeId">
    <input type="hidden" v-model="form.isSign">
    <input type="hidden" v-model="form.type">
    <input type="hidden" v-model="form.applyPerson">
    <label class="col-sm-2 control-label">审批意见:</label>
    <div class="col-sm-12">
      <textarea v-model="form.remark"
                id="remark"
                name="remark"
                class="form-control"
                placeholder="请输入审批意见">  
      </textarea>
    </div>
</div>
<div class="text-center">
<%--     <shiro:hasPermission name="approve:access"> --%>
        <button @click="submit('AGREE')" :disabled="disabled" type="button" class="btn btn-primary">通过</button>
<%--     </shiro:hasPermission> --%>
<%--     <shiro:hasPermission name="approve:reject"> --%>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button @click="submit('REFUSE')" :disabled="disabled" type="button" class="btn btn-danger">拒绝</button>
<%--     </shiro:hasPermission> --%>
        &nbsp;&nbsp;&nbsp;&nbsp;
        <button @click="moreExecApprovalDetail(form.formId,form.id,form.applyPerson)"  type="button" class="btn btn-default">更多</button>
</div>
