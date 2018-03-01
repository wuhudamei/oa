<%@ page language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<!--[if lt IE 9]>
<meta http-equiv="refresh" content="0;ie.html" />
<![endif]-->
<!-- polyfill start-->
<!-- console,Function.bind -->
<!--[if lte IE 9]>
<script type="text/javascript">
(function(e){e||(e=window.console={log:function(e,t,n,r,i){},info:function(e,t,n,r,i){},warn:function(e,t,n,r,i){},error:function(e,t,n,r,i){}});if(!Function.prototype.bind){Function.prototype.bind=function(e){var t=this,n=Array.prototype.slice.call(arguments,1);return function(){return t.apply(e,Array.prototype.concat.apply(n,arguments))}}}if(typeof e.log==="object"){e.log=Function.prototype.call.bind(e.log,e);e.info=Function.prototype.call.bind(e.info,e);e.warn=Function.prototype.call.bind(e.warn,e);e.error=Function.prototype.call.bind(e.error,e)}"groupCollapsed"in e||(e.groupCollapsed=function(t){e.info("\n------------\n"+t+"\n------------")});"group"in e||(e.group=function(t){e.info("\n------------\n"+t+"\n------------")});"groupEnd"in e||(e.groupEnd=function(){});"time"in e||function(){var t={};e.time=function(e){t[e]=(new Date).getTime()};e.timeEnd=function(n){var r=(new Date).getTime(),i=n in t?r-t[n]:0;e.info(n+": "+i+"ms")}}()})(window.console)
</script>
<![endif]-->
<!-- ie10 以下没有 location origin -->
<script type="text/javascript">
    window.location.origin || (window.location.origin = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ":" + window.location.port : ""));
</script>

<!-- polyfill end-->
<script id="userScript">
    function null2Empty(val){
        if(val == 'null' || val == null)
            return '';
        return val;
    }
    window.RocoUser = {
        userId: null2Empty('<shiro:principal property="id"/>'),
        account: null2Empty('<shiro:principal property="username"/>'),
        name: null2Empty('<shiro:principal property="name" />'),
        company:null2Empty('<shiro:principal property="companyName" />'),
        roles: null2Empty('<shiro:principal property="roles" />'),
        permissions: RocoUtils.permissionsFormat(null2Empty('<shiro:principal property="permissions" />'))
    }
</script>