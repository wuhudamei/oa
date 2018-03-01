var vueIndex = null;
+(function() {
	$('#leveAndBusinessMenu').addClass('active');
	$('#commonApplyCreate').addClass('active');
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "H+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	}  
	vueIndex = new Vue({
		el : '#container',
		data : {
			// 面包屑
			breadcrumbs : [ {
				path : '/',
				name : '主页'
			}, {
				path : '/',
				name : '通用申请',
				active : true
			// 激活面包屑的
			} , {
				path : '/',
				name : '修改',
				active : true
			// 激活面包屑的
			} ], 
			webUploaderImage : {   // 上传图片
				type : 'image',
				formData : {},
				accept : {
					title : '文件',
					extensions : 'jpg,jpeg,png'
				},
				server : ctx + '/api/applyCommon/upload',
				// 上传路径
				fileNumLimit : 8,
				fileSizeLimit : 50000 * 1024,
				fileSingleSizeLimit : 5000 * 1024
			},
			webUploaderAccessories: {   // 上传附件
				type : 'accessories',
				formData : {},
				accept : {
					title : '文件',
					extensions : 'txt,doc,docx,xls,xlsx,pdf,rar,zip'
				},
				server : ctx + '/api/applyCommon/upload',
				// 上传路径
				fileNumLimit : 8,
				fileSizeLimit : 50000 * 1024,
				fileSingleSizeLimit : 5000 * 1024
			},
			form : {
				applyTime : '', 
				serialNumber : '',  //编号
				photoNums : 0,
				title : "",
				content : "",
				no : "",
				ctime : "",
				id : ""
			},
			photoSrcs : [],  //图片集合
			accessories : [],  //附件集合
    	    employees : [],
    	    ccPersons : [],  //抄送人
    	    approveList : []  //审批人信息
		},
		components : {
			'web-uploader' : RocoVueComponents.WebUploaderComponent
		},
		validators : {},
		events : {
			'webupload-upload-success-image' : function(file, res) {
				if (res.code == '1') {
					var from = this.from;
					this.$toastr.success('上传成功');
					this.photoSrcs.push(res.data);
					//from.photoNums = (from.photoNums * 1) +  1;
					console.log(this.photoSrcs.join(','));
				} else {
					this.$toastr.error(res.message);
				}
			},
			'webupload-upload-success-accessories' : function(file, res) {
				if (res.code == '1') {
					var from = this.from;
					this.$toastr.success('上传成功');
					this.accessories.push(res.data);
					console.log(this.accessories.join(','));
				} else {
					this.$toastr.error(res.message);
				}
			}
		},
		created : function() {
			// 初始化事件
			var self = this;
			self.generSerialNumber();
		},
		ready : function() {
			// 初始化事件
		    var id =  this.$parseQueryString()['id'];
			var self = this;
			var dateF = new Date().Format("yyyy-MM-dd HH:mm:ss");
			var uuid = this.uuid();
			self.form.applyTime = dateF;
			//通用申请详情
			Vue.http.post('/api/applyCommon/info', {id:id},{headers: {},emulateJSON: true}).then(function(response) {
        		var responseText = response.data;
        		var rdata = responseText.data;
        		self.form.serialNumber = rdata.serialNumber;
        		self.form.applyTime = rdata.applyTime;
        		self.form.title = rdata.title;
        		self.form.id = rdata.id;
        		self.form.content = rdata.content;
        		self.form.ccPersonName = rdata.ccPersonName;
        		self.photoSrcs = $.parseJSON(rdata.photos); 
        		self.accessories = $.parseJSON(rdata.accessories); 
        		self.applyPerson = $.parseJSON(rdata.applyPersonInfo); 
        		self.ccPersons = $.parseJSON(rdata.ccPersonInfo); 
        		$.each(self.applyPerson, function(i,val){      
	       			var id = val['id']
	       			
	       		  });
        	    }, function(response) {
        	        console.log(response)
        	    });
        	//审批详情
			self.getApproveList(id);
		},
		methods : {
			imageView : function(img){
         	    var _$modal = $('#imageView').clone();
	            var $modal = _$modal.modal({
	            	width: document.body.clientWidth,
                    height: document.body.clientHeight,
	                backdrop: 'static',
	                keyboard: false
	            });
	            var editView = new Vue({
	          		  el: $modal[0],
	          		  data: {
	          			imgPath : ""
	          		  },
	              ready: function () {
	            	  var self = this;
	            	  self.imgPath = img.path
	              },
	              methods: {}
          		}) 
			},
			submit: function () {
	          var self = this;
	          self.$validate(true, function () {
	            if (self.$validation.valid) {
	            var formData = _.cloneDeep(self.form);
	            formData.photosInfo=JSON.stringify(self.photoSrcs);
	            formData.accessoriesInfo=JSON.stringify(self.accessories);
	            formData.applyPersonInfo=JSON.stringify(self.employees);
	            formData.ccPersonInfo=JSON.stringify(self.ccPersons);
	            formData.ccPersonIds= self.ccPersonToStr();
	            formData.applyPersonIds = self.applyPersonToStr();
	            formData.ccPersonName = self.nameToStr(self.ccPersons,'name');
	            formData.applyPersonName = self.nameToStr(self.employees,'name');
	            Vue.http.post('/api/applyCommon/save', formData,{headers: {},emulateJSON: true}).then(function(response) {
            		var responseText = response.data;
            		if(responseText['code'] == '1'){  //操作成功，刷新列表
            			self.$toastr.success(responseText['message']);
            			window.location.href = '/admin/businessAway/leaveAndBusiness'; //跳转到我的事务列表页
            		}else{
            			self.$toastr.error(responseText['message']);
            		}
            	    }, function(response) {
            	        console.log(response)
            	    });
	            }
	          });
	        },
		 getApproveList: function (id) {
                var self = this;
                self.$http.get("/api/wfmanager/wfApproveDetail/", {
                    params: {
                        'formId': id,
                        'type': 'COMMON',
                        'showType': 'personal' // personal-提交人,approver-审批人
                    }
                }).then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.approveList = res.data;
                    }
                });
            },
			showImage : function(){
				function PreviewImg(imgFile){
				      var image=new Image();
				      image.src=imgFile.value;
				      imgDiv.style.width = image.width;
				      imgDiv.style.height = image.height;
				 }
			},
			generSerialNumber : function(){
				 var self = this;
			     Vue.http.post('/api/applyCommon/generSerialNumber', {},{headers: {},emulateJSON: true}).then(function(response) {
	            		var responseText = response.data;
	            		if(responseText['code'] == '1'){  //操作成功，刷新列表
	            			self.form.serialNumber = responseText['message'];
	            		}
	            	    }, function(response) {
	            	        console.log(response)
	            	    });
			},
			submit: function () {
	          var self = this;
	          self.$validate(true, function () {
	            if (self.$validation.valid) {
	            var formData = _.cloneDeep(self.form);
	            formData.photosInfo=JSON.stringify(self.photoSrcs);
	            formData.accessoriesInfo=JSON.stringify(self.accessories);
	            formData.applyPersonInfo=JSON.stringify(self.employees);
	            formData.ccPersonInfo=JSON.stringify(self.ccPersons);
	            formData.ccPersonIds= self.ccPersonToStr();
	            formData.applyPersonIds = self.applyPersonToStr();
	            formData.ccPersonName = self.nameToStr(self.ccPersons,'name');
	            formData.applyPersonName = self.nameToStr(self.employees,'name');
	            Vue.http.post('/api/applyCommon/update', formData,{headers: {},emulateJSON: true}).then(function(response) {
            		var responseText = response.data;
            		if(responseText['code'] == '1'){  //操作成功，刷新列表
            			self.$toastr.success(responseText['message']);
            			window.location.href = '/admin/businessAway/leaveAndBusiness'; //跳转到我的事务列表页
            		}else{
            			self.$toastr.error(responseText['message']);
            		}
            	    }, function(response) {
            	        console.log(response)
            	    });
	            }
	          });
	        },
	        ccPersonToStr : function(){
	        	var self = this;
	        	var ids = new Array();
	        	$.each(self.ccPersons, function(i,val){      
	       			ids.push(val['id'])
	       		  });
	        	return ids.join(',');
	        },
	        nameToStr : function(arrays,fileName){
	        	var self = this;
	        	var ids = new Array();
	        	$.each(arrays, function(i,val){      
	       			ids.push(val[fileName])
	       		  });
	        	return ids.join(',');
	        },
	        applyPersonToStr : function(){
	        	var self = this;
	        	var ids = new Array();
	        	$.each(self.employees, function(i,val){      
	       			ids.push(val['id'])
	       		  });
	        	return ids.join(',');
	        },
			uuid : function(){  //生成UUID
				 var s = [];  
				    var hexDigits = "0123456789abcdef";  
				    for (var i = 0; i < 36; i++) {  
				        s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);  
				    }  
				    s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010  
				    s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01  
				    s[8] = s[13] = s[18] = s[23] = "-";  
				   
				    var uuid = s.join("");  
				    return uuid.toUpperCase();
			},
			cancel : function() {
				window.history.go(-1);
			},
			removeImg : function(src,index,type){
				var self = this;
				Vue.http.post('/api/applyCommon/delete', 
						{path:src.path},{headers: {},emulateJSON: true})
						.then(function(response) {
							var responseText = response.data;
							if (response.data.code == 1) {
								if(type == 'image'){
									self.photoSrcs.splice(index, 1)
								}else if(type == 'accessories'){
									self.accessories.splice(index, 1)
								}
				              }
	            	    }, function(response) {
	            	        console.log(response)
	            	    });
			},
			add : function(){
         	    var _$modal = $('#addStylistModal').clone();
	            var $modal = _$modal.modal({
	            	width: 680,
                    maxWidth: 680,
                    height: 480,
                    maxHeight: 480,
	                backdrop: 'static',
	                keyboard: false
	            });
	            var editView = new Vue({
	          		  el: $modal[0],
	          		  data: {
		          	      form: {
		          	        keyword: '',
		          	        orgCode: '',
		          	        orgName: '',
		          	        employeeStatus: 'ON_JOB',
		          	        addTable : ""
		          	      },
		          	      employees : []
	          		  },
	              ready: function () {
	            	  this.drawTable();
	            	  this.initSelect();
	              },
	              methods: {
	            	  initSelect : function(){
	            		  var self = this;
	            		  self.employees = vueIndex.employees;
	            	  },
	            	  drawTable : function () {
	            	        var self = this;
	            	        self.$dataTable = $('#stylistDataTable', self._$el).bootstrapTable({
	            	          url: '/api/employees/applylist',
	            	          method: 'get',
	            	          dataType: 'json',
	            	          cache: false, //去缓存
	            	          pagination: true, //是否分页
	            	          sidePagination: 'server', //服务端分页
	            	          queryParams: function (params) {
	            	            // 将table 参数与搜索表单参数合并
	            	            return _.extend({}, params, self.form);
	            	          },
	            	          mobileResponsive: true,
	            	          undefinedText: '-', //空数据的默认显示字符
	            	          striped: true, //斑马线
	            	          maintainSelected: true, //维护checkbox选项
	            	          columns: [{
	                              field: 'id',
	                              title: 'id',
	                              align: 'center',
	                              visible: false,
	                              order: 'desc'
	                          	},{
	            	              field: 'orgCode',
	            	              title: '工号',
	            	              align: 'center'
		            	         },{
	            	              field: 'jobNum',
	            	              visible: false,
	            	              title: 'jobNum',
	            	              align: 'center'
		            	         }, {
	            	              field: 'name',
	            	              title: '姓名',
	            	              align: 'center'
	            	            }, {
            	                field: 'position',
            	                title: '职务',
            	                align: 'center'
	            	            }, {
	            	              field: 'mobile',
	            	              title: '手机号',
	            	              align: 'center'
	            	            },{
	                                field: '', // 将id作为排序时会和将id作为操作field内容冲突，导致无法排序
	                                title: "操作",
	                                align: 'center',
	                                formatter: function (value, row, index) {
	                                	 var html = '<button style="margin-left:10px;" data-handle="select" data-id="' + row.id + '"  data-orgCode="' + row.orgCode + '" data-jobNum="' + row.jobNum + '" data-name="' + row.name + '" data-mobile="' + row.mobile + '" class="m-r-xs btn btn-xs btn-primary" type="button">选择</button>' ;
	                                     return html;
	                                }
	                            }]
	            	        });
	            	        self.$dataTable.on('click', '[data-handle="select"]', function (e) {
	            	        	var id = $(this).data('id');  //取到的是字符串
	            	        	var orgCode = $(this).data('orgcode');  //取到的是字符串
	            	        	var jobNum = $(this).data('jobnum');  //取到的是字符串
	            	        	var name = $(this).data('name');  //取到的是字符串
	            	        	var mobile = $(this).data('mobile');  //取到的是字符串
	            	        	var obj = {'id':id,'jobNum':jobNum,'name':name,'mobile':mobile}
	            	        	self.addRowItem(id, obj);
	            	        });
	            	        self.$dataTable.on('check.bs.table', function (e, row, ele,field) {
	            	        		var obj = {'id':row.id,'jobNum':row.jobNum,'name':row.name,'mobile':row.mobile}
	            	        	});
	            	        self.$dataTable.on('uncheck.bs.table', function (e, row, ele,field) {
	            	        		self.deleteItem(row);
	            	        	});
	            	        self.$dataTable.on('dbl-click-row.bs.table', function (e, row, ele,field) {
		            	        	var obj = {'id':row.id,'jobNum':row.jobNum,'name':row.name,'mobile':row.mobile}
		            	        	var targetId = row.id;
		            	        	addRowItem(targetId, obj);
            	        		});
	            	      },
	            	      
	            	      query: function () {
	            	          this.$dataTable.bootstrapTable('refresh', {pageNumber: 1});
	            	     },
            			addRowItem : function(targetId, obj){
    	       				var self = this;
    	       				var isAdd = -1;
            	        	$.each(self.employees, function(i,val){      
    			       			var eachId = val['id'];
    			       		    if(eachId == targetId)  {
    			       		    	isAdd = i;
    			       		    	return false;  
    			       		    }
    			       		  });
            	        	if(isAdd == -1){
            	        		self.employees.push(obj);
            	        	}else{
            	        		self.$toastr.error('['+row.name+']已选择！');
            	        	}
    	       			},
    	       			deleteItem : function(item){
		       				var self = this;
		       				var targetId = item.id;
		       				var delIndex = -1;
				       		$.each(self.employees, function(i,val){      
				       			var eachId = val['id'];
				       		    if(eachId == targetId)  {
				       		    	delIndex = i;
				       		    	return false;  
				       		    }
				       		  });
				       		console.log("delIndex: " + delIndex);
				       		self.employees.splice(delIndex, 1);
	       			},
	       			commitStylist : function(){
	       				var self = this;
	       				vueIndex.employees = self.employees;
	       				$modal.modal('hide');
	       			}
	              }
          		}) 
			},
			addCcPerson : function(){
         	    var _$modal = $('#ccPersonStylistModal').clone();
	            var $modal = _$modal.modal({
	            	width: 680,
                    maxWidth: 680,
                    height: 480,
                    maxHeight: 480,
	                backdrop: 'static',
	                keyboard: false
	            });
	            var editView = new Vue({
	          		  el: $modal[0],
	          		  data: {
		          	      form: {
		          	        keyword: '',
		          	        orgCode: '',
		          	        orgName: '',
		          	        employeeStatus: 'ON_JOB',
		          	        addTable : ""
		          	      },
		          	    ccPersons : []
	          		  },
	              ready: function () {
	            	  this.drawTable();
	            	  this.initSelect();
	              },
	              methods: {
	            	  initSelect : function(){
	            		  var self = this;
	            		  self.ccPersons = vueIndex.ccPersons;
	            	  },
	            	  drawTable : function () {
	            	        var self = this;
	            	        self.$dataTable = $('#ccPersonDataTable', self._$el).bootstrapTable({
	            	          url: '/api/employees/applylist',
	            	          method: 'get',
	            	          dataType: 'json',
	            	          cache: false, //去缓存
	            	          pagination: true, //是否分页
	            	          sidePagination: 'server', //服务端分页
	            	          queryParams: function (params) {
	            	            // 将table 参数与搜索表单参数合并
	            	            return _.extend({}, params, self.form);
	            	          },
	            	          mobileResponsive: true,
	            	          undefinedText: '-', //空数据的默认显示字符
	            	          striped: true, //斑马线
	            	          maintainSelected: true, //维护checkbox选项
	            	          columns: [{
	                              field: 'id',
	                              title: 'id',
	                              align: 'center',
	                              visible: false,
	                              order: 'desc'
	                          	},{
	            	              field: 'orgCode',
	            	              title: '工号',
	            	              align: 'center'
		            	         },{
	            	              field: 'jobNum',
	            	              visible: false,
	            	              title: 'jobNum',
	            	              align: 'center'
		            	         }, {
	            	              field: 'name',
	            	              title: '姓名',
	            	              align: 'center'
	            	            }, {
	            	                field: 'position',
	            	                title: '职务',
	            	                align: 'center'
	            	            }, {
	            	              field: 'mobile',
	            	              title: '手机号',
	            	              align: 'center'
	            	            },{
	                                field: '', // 将id作为排序时会和将id作为操作field内容冲突，导致无法排序
	                                title: "操作",
	                                align: 'center',
	                                formatter: function (value, row, index) {
	                                	 var html = '<button style="margin-left:10px;" data-handle="select" data-id="' + row.id + '"  data-orgCode="' + row.orgCode + '" data-jobNum="' + row.jobNum + '" data-name="' + row.name + '" data-mobile="' + row.mobile + '" class="m-r-xs btn btn-xs btn-primary" type="button">选择</button>' ;
	                                     return html;
	                                }
	                            }]
	            	        });
	            	        self.$dataTable.on('click', '[data-handle="select"]', function (e) {
	            	        	var id = $(this).data('id');  //取到的是字符串
	            	        	var orgCode = $(this).data('orgcode');  //取到的是字符串
	            	        	var jobNum = $(this).data('jobnum');  //取到的是字符串
	            	        	var name = $(this).data('name');  //取到的是字符串
	            	        	var mobile = $(this).data('mobile');  //取到的是字符串
	            	        	var obj = {'id':id,'jobNum':jobNum,'name':name,'mobile':mobile}
	            	        	self.addRowItem(id, obj);
	            	        });
	            	        self.$dataTable.on('check.bs.table', function (e, row, ele,field) {
	            	        		var obj = {'id':row.id,'jobNum':row.jobNum,'name':row.name,'mobile':row.mobile}
	            	        	});
	            	        self.$dataTable.on('uncheck.bs.table', function (e, row, ele,field) {
	            	        		self.deleteItem(row);
	            	        	});
	            	        self.$dataTable.on('dbl-click-row.bs.table', function (e, row, ele,field) {
		            	        	var obj = {'id':row.id,'jobNum':row.jobNum,'name':row.name,'mobile':row.mobile}
		            	        	var targetId = row.id;
		            	        	self.addRowItem(targetId, obj);
            	        		});
	            	        self.$dataTable.on('click', '[data-handle="select"]', function (e) {
	            	        	var id = $(this).data('id');  //取到的是字符串
	            	        	var orgCode = $(this).data('orgcode');  //取到的是字符串
	            	        	var jobNum = $(this).data('jobnum');  //取到的是字符串
	            	        	var name = $(this).data('name');  //取到的是字符串
	            	        	var mobile = $(this).data('mobile');  //取到的是字符串
	            	        	var obj = {'id':id,'jobNum':jobNum,'name':name,'mobile':mobile}
	            	        	self.addRowItem(id, obj);
	            	        });
	            	      },
	            	      query: function () {
	            	          this.$dataTable.bootstrapTable('refresh', {pageNumber: 1});
	            	     },
	       			deleteItem : function(item){
	       				var self = this;
	       				var targetId = item.id;
	       				var delIndex = -1;
			       		$.each(self.ccPersons, function(i,val){      
			       			var eachId = val['id'];
			       		    if(eachId == targetId)  {
			       		    	delIndex = i;
			       		    	return false;  
			       		    }
			       		  });
			       		self.ccPersons.splice(delIndex, 1);
	       			},
	       			addRowItem : function(targetId,obj){
	       				var self = this;
	       				var isAdd = -1;
        	        	$.each(self.ccPersons, function(i,val){      
			       			var eachId = val['id'];
			       		    if(eachId == targetId)  {
			       		    	isAdd = i;
			       		    	return false;  
			       		    }
			       		  });
        	        	if(isAdd == -1){
        	        		self.ccPersons.push(obj);
        	        	}else{
        	        		self.$toastr.error('['+obj.name+']已选择！');
        	        	}
	       			},
	       			commitStylist : function(){
	       				var self = this;
	       				vueIndex.ccPersons = self.ccPersons;
	       				$modal.modal('hide');
	       			}
	              }
          		}) 
			
			}
		}
	});
	vueIndex.$watch('photoSrcs',function(){
        $('#view-images').viewer();
    });
})();
Vue.filter('formatData', function(value,type) {
	  var lable = "";
	  if(type == "status"){
	      switch (value) {
	      case 'PENDING':
	    	lable="待审批";
	        break;
	      case 'INIT':
	    	lable="未开始";
	        break;
	      case 'COMPLETE':
	      	lable="已审批";    	  
	        break;
        case 'RESET':
	      	lable="已撤回";    	  
	        break;
	      case 'TURN':
	    	  lable="转派";    	  
	    	  break;
	      case 'ADD':
	    	  lable="添加";    	  
	    	  break;
	      case 'INVALIDATE':
	    	  lable="失效";    	  
	    	  break;
	      default:
	        break;
	}
	  }else if(type == "result"){
		  if(value != null && value!=""){
		      if(value =="AGREE"){
		    	  lable = "同意";
		      }else if( value =="REFUSE" ){
				  lable = "拒绝";
			  }else if( value =="RESET" ){
				  lable = "已撤回";
			  }else if( value =="ADDBEFORE" ){
				  lable = "之前添加";
			  }else if( value =="ADDAFTER" ){
				  lable = "之后添加";
			  }
		  }else{
			  lable = "-"
		  }
	  }else if(type == "time"){
		  if(value != null && value!=""){
		      lable = value;
		  }else{
			  lable = "-"
		  }
	  }else if(type == "css"){
	      if(value =="AGREE"){
	    	  lable = "#00DD00";
	      }else if( value =="REFUSE" ){
			  lable = "#FF0000";
		  }
	  }
return lable;
});