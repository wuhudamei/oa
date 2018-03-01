<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<template id="z-tree">
    <div v-show="showTree" :class="{'z-tree': abs}">
        <ul :id="'_' + r" class="ztree"></ul>
    </div>
</template>