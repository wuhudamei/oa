var header;
(function () {
    $(function () {
        header = new Vue({
            el: '#container',
            data: {
                //模型复制给对应的key
                businessAway: null,
                vacation: null,
                approveList: null,
                showType: null,
                signature:null,
                payment:null,
                titleName: null,
                //通用申请开始
                photoSrcs : [],  //图片集合
    			accessories : [],  //附件集合
    			applyPerson : [],  //审批人
        	    ccPersons : [],  //抄送人
    			approveList : [],   //表格数据 
    			//通用申请结束
                form: {
                    id: null,
                    type: null,
                    applyUrl: null,
                    //通用申请开始
    				applyTime : '', 
    				serialNumber : '',  //编号
    				photoNums : 0,
    				title : "",
    				content : "",
    				ctime : "",
    				ccPersonName : ""
    				//通用申请结束
                }
            },
            methods: {
            	downloadFile: function (src,index,type) {
            		window.location.href = "/api/applyCommon/download?file=" + src['path']+"&fileName=" + src['fileName']
            	},
            	
            	//预览图片
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
                getApproveList: function () {
                    var self = this;
                    self.$http.get("/api/wfmanager/wfApproveDetail/", {
                        params: {
                            'formId': this.form.id,
                            'type': this.form.type,
                            'showType': 'personal'
                        }
                    }).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            self.approveList = res.data;
                        }
                    });
                },
                getApprovalDetailData: function () {
                    var self = this;
                    self.$http.get(this.form.applyUrl).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            if (this.form.type == "BUSINESS") { //出差
                                self.showType = "1";
                                self.titleName = "出差";
                                self.businessAway = res.data;
                            } else if (this.form.type == "LEAVE") {//请假
                                self.showType = "2";
                                self.titleName = "请假";
                                self.vacation = res.data;
                            } else if (this.form.type == "SIGNATURE") {//签报
                                self.showType = "6";
                                self.titleName = "签报";
                                self.signature = res.data;
                            } else if (this.form.type == "EXPENSE") {//报销
                                self.showType = "7";
                                self.titleName = "报销";
                                self.payment = res.data;
                            }else if (this.form.type == "COMMON") {//报销
                                self.showType = "8";
                                self.titleName = "通用申请";
                                var rdata = res.data;
                                self.form.serialNumber = rdata.serialNumber;
                        		self.form.applyTime = rdata.applyTime;
                        		self.form.title = rdata.title;
                        		self.form.content = rdata.content;
                        		self.form.ccPersonName = rdata.ccPersonName;
                        		self.photoSrcs = $.parseJSON(rdata.photos); 
                        		self.accessories = $.parseJSON(rdata.accessories); 
                        		self.applyPerson = $.parseJSON(rdata.applyPersonInfo); 
                        		self.ccPersons = $.parseJSON(rdata.ccPersonInfo); 
                            }
                        }
                    });
                }
            },
            created: function () {
                this.form.id = this.$parseQueryString()['id'];
                this.form.type = this.$parseQueryString()['type'];
                this.form.applyUrl = this.$parseQueryString()['applyUrl'];
            },
            ready: function () {
                this.getApprovalDetailData();
                this.getApproveList();
            }
        });
        //视听图片元素变化
        header.$watch('photoSrcs',function(){
            $('#view-images').viewer();
        });
    });
})();
