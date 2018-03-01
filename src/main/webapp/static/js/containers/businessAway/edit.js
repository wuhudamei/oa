/**
 * Created by andy on 2017/4/10.
 */
var vueIndex;
+(function () {
    $('#leveAndBusinessMenu').addClass('active');
    $('#myBusinessApply').addClass('active');
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
                name: '出差申请',
                active: true //激活面包屑的
            }],
            _$el: null, //自己的 el $对象
            submitBtnClick: false,
            disabled: false,
            businessAway: {
                id: '',
                userId: '',
                applyTitle: '',
                applyCode: '',
                setOutAddress: '',
                address: '',
                beginTime: '',
                endTime: '',
                daysNum: '',
                reason: '',
                estimatedCost: ''
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
            this.activeDatepicker();
            this.editBusiness();
        },
        methods: {
            cancel: function () {
                window.location.href = '/admin/businessAway/leaveAndBusiness';
            },
            activeDatepicker: function () {
                var self = this;
                $(this.$els.beginTime).datetimepicker({
                    minView: 2,
                    format: 'yyyy-mm-dd',
                    // endDate: self.businessAway.endTime,
                    todayBtn: true
                }).on('changeDate', function (ev) {
                    self.calculateDaysNum();
                    $(self.$els.endTime).datetimepicker('setStartDate', ev.date);
                });
                $(this.$els.endTime).datetimepicker({
                    minView: 2,
                    format: 'yyyy-mm-dd',
                    // startDate: self.businessAway.beginTime,
                    todayBtn: true
                }).on('changeDate', function (ev) {
                    $(self.$els.beginTime).datetimepicker('setEndDate', ev.date);
                    self.calculateDaysNum();
                });
            },
            calculateDaysNum: function () {
                var self = this;
                if (self.businessAway.beginTime != '' && self.businessAway.beginTime != undefined && self.businessAway.endTime != '' && self.businessAway.endTime != undefined) {
                    self.businessAway.daysNum = moment(self.businessAway.endTime).diff(moment(self.businessAway.beginTime), 'days') + 1;
                }
            },
            submit: function () {
                var self = this;
                self.submitBtnClick = true;
                self.$validate(true, function () {
                    if (self.$validation.valid) {
                        self.disabled = true;
                        self.$http.post(ctx + '/api/apply/applyBusinessAway/saveSubmit', self.businessAway, {emulateJSON: true}).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('提交成功');
                                setTimeout(function () {
                                    window.location.href = '/admin/businessAway/leaveAndBusiness';
                                }, 1500);
                            } else {
                            	self.$toastr.error(res.data.message);
                                self.disabled = false;
                            }
                        }).finally(function () {

                        });
                    }
                });
            },
            saveDraft: function () {
                var self = this;
                self.submitBtnClick = true;
                self.$validate(true, function () {
                    if (self.$validation.valid) {
                        self.disabled = true;
                        self.$http.post(ctx + '/api/apply/applyBusinessAway/saveDraft', self.businessAway, {emulateJSON: true}).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('保存成功');
                                setTimeout(function () {
                                    window.location.href = '/admin/businessAway/leaveAndBusiness';
                                }, 1500);
                            } else {
                                self.disabled = false;
                            }
                        }).finally(function () {

                        });
                    }
                });
            },
            editBusiness: function () {
                var self = this;
                var id = this.$parseQueryString()['id'];
                if (id != null && id != undefined) {
                    self.$http.get(ctx + "/api/apply/applyBusinessAway/" + id).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            self.businessAway = res.data;
                        }
                    });
                }
            }
        }
    });
})();