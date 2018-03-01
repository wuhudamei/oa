<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="keywords" content="">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
  <title>列表</title>
  <!-- style -->
  <link rel="stylesheet" href="${ctx}/static/crm/bootstrap/css/bootstrap.min.css">
  <link rel="stylesheet" href="${ctx}/static/vendor/toastr/toastr.min.css">

  <style>
  body{
    height:100%;
    width:100%;
    position:absolute;
  }
  #header {
      position: absolute;
      z-index: 2;
      top: 0;
      left: 0;
      width: 100%;
      height: 45px;
      line-height: 45px;
      /* background: #f60; */
      background: #1ab394;
      padding: 0;
      color: #eee;
      font-size: 20px;
      text-align: center;
      font-weight: bold;
  }

  #footer {
      position: absolute;
      z-index: 2;
      bottom: 0;
      left: 0;
      width: 100%;
      height: 48px;
      background: #444;
      padding: 0;
      border-top: 1px solid #444;
      line-height: 48px;
      color: #fff;
      text-align: center;
  }

  #J_Scroll {
      position: absolute;
      z-index: 1;
      top: 45px;
      bottom: 48px;
      left: 0;
      width: 100%;
      overflow: hidden;
  }

  #J_Scroll li {
      width: 100%;
      padding: 0 10px;
      height: 40px;
      line-height: 40px;
      border-bottom: 1px solid #ccc;
      background-color: #fafafa;
  }

  .xs-plugin-pulldown-container {
      text-align: center;
      width: 100%;
      line-height: 50px;
  }

  .xs-plugin-pulldown-up .up {
      display: inline;
  }

  .xs-plugin-pulldown-up .down {
      display: none;
  }

  .xs-plugin-pulldown-down .up {
      display: none;
  }

  .xs-plugin-pulldown-down .down {
      display: inline;
  }

  .xs-plugin-pullup-container {
      line-height: 40px;
      text-align: center;
  }

  .lbl-ctn {
      width: 100%;
      position: absolute;
      -webkit-transform: translateX(0) translateZ(0);
      background: #fff;
      z-index: 1;
  }

  .lbl {
      width: 100%;
      display: inline-block;
      vertical-align: top;
      text-align: center;
      position: absolute;
      background: #fff;
      z-index: 1;
      left: 0;
  }

  .control {
      position: absolute;
      white-space: nowrap;
      width: 40%;
      z-index: 0;
      right: 0;
  }

  .btn {
      display: inline-block;
      color: #fff;
      width: 50%;
      text-align: center;
  }

  .btn-delete {
      background: red;
  }

  .btn-mark {
      background: #ccc;
  }

  .btn-marked {
      background: green;
  }
  .xs-container {
	padding: 15px;
  }

  .panel-default {
	border-radius: 10px;
  }
  </style>
  <script src="${ctx}/static/crm/vue/vue.min.js"></script>
  <script src="${ctx}/static/crm/jquery/jquery-2.2.4.min.js"></script>
  <script src="${ctx}/static/crm/xscroll/xscroll.min.js"></script>
  <script src="${ctx}/static/crm/xscroll/plugins/pulldown.min.js"></script>
  <script src="${ctx}/static/vendor/toastr/toastr.min.js"></script>
  <script>
  toastr.options = {
		    closeButton: false, //关闭按钮去掉
		    debug: false, //debug模式
		    newestOnTop: true, //最新的在上面
		    progressBar: false,
		    preventDuplicates:true,
		    positionClass: "toast-top-center"
		  };
  </script>
</head>
<body>
  <div id="app" style="height:100%;width:100%;position:absolute;">
  <div id="header">任务列表</div>
  <div ref="jScroll" id="J_Scroll">
      <div class="xs-container xs-content" >
			<div class="panel panel-default"  v-for="item in items">
				
				<div class="panel-body" @click="toDetail(item.taskId)">
					<div class="row">
						<div class="col-sm-6 col-xs-12">
							<p >
								 客户名称：{{item.customerName}}
							</p>
						</div>
						<div class="col-sm-6 col-xs-12">
							<p>
								客户电话：{{item.customerMobile}}
							</p>
						</div>
					</div>

					<div class="row">
						<div class="col-sm-6 col-xs-12">
							<p>操作时间：{{item.optTime | time}}</p>
						</div>
						<div class="col-sm-6 col-xs-12">
							<p>客户评价：{{item.customerEvaluation}}</p>
						</div>
					</div>
					
				</div>
			</div>
		</div>
  </div>
  </div>
  <script>
    var v = new Vue({
      el:'#app',
      data:{
        items:[],
        pulldown: null,
        xScroll: null,
        submitting: false
      },
      created:function(){
      },
      mounted: function(){
        this.$nextTick(function(){
          this.activeXscroll();
        })
      },
      methods:{
    	loadData: function(){
    		var self = this;
    		//请求数据
    		$.ajax({
                url:"/api/crm/crmBusiness/getAllTask",
                dataType:"json",
                success:function(data){
					self.items = data.data;
					
					self.$nextTick(function(){
            self.xscroll.render();
					  //scrollback to the top
					  self.pulldown && self.pulldown.reset(function(){
					      //repaint
					      self.xscroll && self.xscroll.render();
					  });
					});

					//没有获取到数据
					if(data.code == 0){
						toastr.error(data.message);
					}
                }
            });
    	},
        activeXscroll:function(){
          var self = this;
          self.xscroll = new XScroll({
            renderTo:self.$refs.jScroll,
            scrollbarX:false,
            lockX:true,
            lockY:false
        	});

	         self.pulldown = new XScroll.Plugins.PullDown({
	            autoRefresh:false,
	            content:'下拉刷新',
	            downContent: '下拉刷新',
	            upContent:'释放刷新',
	            loadingContent:'加载中...'
	        });

        	self.xscroll.plug(self.pulldown);

	        self.pulldown.on("loading",function(e){
	          self.loadData();
	        })
          self.loadData();
        },
       	
        //去详情页面
        toDetail: function(taskId){
        	window.location.href = "/oldCrm/detail?taskId=" + taskId;
        }
        
      }
    });
    //格式化时间
    //格式化时间
	Vue.filter('time', function (value) {
		if(value != null && value != undefined && value.trim() != ""){
			if(value.lastIndexOf(".") != -1){
				return value.substr(0,value.lastIndexOf("."));
			}
			return value;
		}
		return "";
	})
  </script>
</body>
</html>