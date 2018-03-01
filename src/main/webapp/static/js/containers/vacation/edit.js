/**
 * Created by andy on 2017/4/10.
 */
var vueIndex;
+(function () {
  $('#leveAndBusinessMenu').addClass('active');
  $('#myLeveApply').addClass('active');
  vueIndex = new Vue({
    el: '#container',
    mixins: [RocoVueMixins.DataTableMixin],
    data: {
      // 面包屑
      breadcrumbs: [{
        path: '/',
        name: '主页'
      }, {
        path: '/',
        name: '请假申请',
        active: true //激活面包屑的
      }],
      applyType:[],
      _$el: null, //自己的 el $对象
      disabled:false,
      vacation:{
          id:'',
          userId: '',
          applyTitle:'',
          applyCode:'',
          applyType: '',
          startTime: '',
          endTime: '',
          days: '',
          reason:''
      }
    },
    validators: {
        validDays: function (val) {
          return /^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/.test(val);
        }
      },
      created: function () {
      },
      ready: function () {
        this.getApplyType();
        this.initDatePicker();
        this.activeDatepicker();
        this.editVacation();
      },
    methods: {
    	cancel:function(){
    		window.location.href = '/admin/businessAway/leaveAndBusiness';
    	},
        activeDatepicker: function () {
            var self = this;
            $(this.$els.startTime).datetimepicker({
                minView: 2,
                format: 'yyyy-mm-dd',
                todayBtn : true
            }).on('changeDate', function (ev) {
                self.calculateDaysNum();
                $(self.$els.endTime).datetimepicker('setStartDate', ev.date);
            });
            $(this.$els.endTime).datetimepicker({
                minView: 2,
                format: 'yyyy-mm-dd',
                todayBtn : true
            }).on('changeDate', function (ev) {
                $(self.$els.beginTime).datetimepicker('setEndDate', ev.date);
                self.calculateDaysNum();
            });
        },
        calculateDaysNum:function () {
            var self = this; 
            var tmpStartTime = self.vacation.startTime;
            var tmpEndTime = self.vacation.endTime;
            if( tmpStartTime != '' && tmpStartTime != undefined &&  tmpEndTime != '' && tmpEndTime != undefined){
              var tmpHour = moment( tmpEndTime ).diff(moment( tmpStartTime ), 'hour') % 24;
          	  self.vacation.days = moment( tmpEndTime ).diff(moment( tmpStartTime ), 'days')
          	  if(tmpHour > 0){
          		  if(tmpHour >= 8){
          			self.vacation.days += 1;
          		  }else{
          			self.vacation.days += "." + tmpHour;
          		  }
          	  }
            }
        },
    	save :function(){
    		var self = this;
    		self.submitBtnClick = true;
    		self.$validate(true, function () {
                if (self.$validation.valid) {
                	self.disabled = true;
                    var formData = {
                    		id:self.vacation.id,
                        	applyType:self.vacation.applyType,
                        	startTime:self.vacation.startTime,
                        	endTime:self.vacation.endTime,
                        	days:self.vacation.days,
                        	reason:self.vacation.reason
                    }
    	            self.$http.post(ctx + '/api/vacations/saveDraft', formData).then(function (res) {
    	                if (res.data.code == 1) {
    	                    self.$toastr.success('创建成功！');
    	                    setTimeout(function () {
                                window.location.href = '/admin/businessAway/leaveAndBusiness';
                            }, 1500);
    	                }else{
    	                	self.disabled = false;
    	                }
    	              }
    	            ).finally(function () {
    	              
    	            });
                }
    		});
    	},
        getApplyType:function () {
          var self = this;
          self.$http.get(ctx + '/api/dict/getByType',{params:{
        	  'type':5
          }}).then(function (res) {
                if (res.data.code == 1) {
                  self.applyType = res.data.data;
                }
              }
          ).finally(function () {});
        },
        initDatePicker: function () {
          $(this.$els.startTime).datetimepicker({
            minView: 0,
            format: 'yyyy-mm-dd hh:ii',
            todayBtn: true
          });
          $(this.$els.endTime).datetimepicker({
            minView: 0,
            format: 'yyyy-mm-dd hh:ii',
            todayBtn: true
          });
        },
        submit: function () {
          var self = this;
          self.submitBtnClick = true;
          self.$validate(true, function () {
            if (self.$validation.valid) {
              self.disabled = true;
              var formData = {
            		id:self.vacation.id,
            		applyCode:self.vacation.applyCode,
            		applyTitle:self.vacation.applyTitle,
	            	applyType:self.vacation.applyType,
	            	startTime:self.vacation.startTime,
	            	endTime:self.vacation.endTime,
	            	days:self.vacation.days,
	            	reason:self.vacation.reason
              }
	            self.$http.post(ctx + '/api/vacations', formData).then(function (res) {
	                if (res.data.code == 1) {
	                	self.$toastr.success('操作成功！');
  	                    setTimeout(function () {
                            window.location.href = '/admin/businessAway/leaveAndBusiness';
                        }, 1500);
	                }else{
	                	self.$toastr.error(res.data.message);
	                	self.disabled = false;
	                }
	              }
	            ).finally(function () {
	              
	            });
//              } else { // 编辑
//                self.$http.put(ctx + '/api/vacations', formData).then(function (res) {
//                    if (res.data.code == 1) {
//                        self.$toastr.success('编辑成功！');
//                    }
//                  }
//                ).finally(function () {
//                  self.disabled = false;
//                });
//              }
              $el.modal('hide');
            }
          });
        },
        editVacation:function(){
          var self = this;
          var id = this.$parseQueryString()['id'];
          if(id != null && id != undefined){
              self.$http.get( ctx + "/api/vacations/" + id ).then(function (response) {
            	  var res = response.data;
            	  if (res.code == '1') {
            		  self.vacation = res.data;
            	  }
    	      });
          }
        }
      }
  });
})();