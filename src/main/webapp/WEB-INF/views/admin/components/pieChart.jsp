<%@ page language="java" pageEncoding="UTF-8" %>
  <template id="piechart">
    <div class="panel panel-default">
      <!--<div class="panel-heading">-->
          <slot name="title"></slot>
      <!--</div>-->
      <div class="panel-body">
        <slot name="content"></slot>
        <div class="week-month right pie-top">
          <div v-el:echart :style="'height:'+hei+'px'"></div>
          <slot name="href"></slot>
          
        </div>
      </div>
      <slot name="other"></slot>
    </div>
    
  </template>
  <script src="${ctx}/static/vendor/echarts/echarts.common.min.js"></script>
    <script src="${ctx}/static/js/components/pieChart.js"></script>