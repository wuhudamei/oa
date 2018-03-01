<%@ page language="java" pageEncoding="UTF-8" %>
<template id="breadcrumbTmpl">
  <ol class="breadcrumb">
    <li v-for="crumb in crumbs">
      <a v-if="!crumb.active" :href="crumb.path">{{crumb.name}}</a>
      <strong v-else>{{crumb.name}}</strong>
    </li>
  </ol>
</template>