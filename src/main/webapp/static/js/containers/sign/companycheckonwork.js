+(function (Vue, moment) {
    $('#workMenu').addClass('active');
    $('#comcheckonwork').addClass('active');
    new Vue({
        el: '#container',
        validators: {
            laterThanStart: function (val, startDate) {
                try {
                    var end = moment(val);
                    return end.isBefore(startDate) ? false : true;
                } catch (e) {
                    return false;
                }
            }
        },
        data: {
            allCount: '',
            numberOfPeopleSupposedToCome: '',
            attendance: '',
            beLate: 0,
            leaveEarly: 0,
            absenteeism: 0,
            punchCardType: 0,
            belateandleaveearly: 0,
            $dataTable: null,
            form: {
                empName: '',
                companyName: '',
                startDate: '',
                endDate: '',
                signType: '',
                punchCardType: '',
            },
            weixin: false
        },
        methods: {
            findCom:function () {
              var self = this;
                self.form.companyName = RocoUser.company;
            },
            findAllcount:function () {
              var self = this;
              self.$http.get('/api/checkonwork/findAllcount?empName=' + self.form.empName + '&companyName=' + RocoUser.company +
                '&startDate=' + self.form.startDate + '&endDate=' + self.form.endDate + '&signType=' + self.form.signType +
                '&punchCardType=' + self.form.punchCardType).then(function (res) {
                    if(res.data.code == 1 ){
                        if(res.data.data.ABSENTEEISMNUM != null && res.data.data.ABSENTEEISMNUM != '' && res.data.data.ABSENTEEISMNUM != undefined){
                            self.absenteeism = res.data.data.ABSENTEEISMNUM;
                        }else{
                            self.absenteeism = 0;
                        }
                        if(res.data.data.BELATEANDLEAVEEARLYNUM != null && res.data.data.BELATEANDLEAVEEARLYNUM != '' && res.data.data.BELATEANDLEAVEEARLYNUM != undefined){
                            self.belateandleaveearly = res.data.data.BELATEANDLEAVEEARLYNUM;
                        }else{
                            self.belateandleaveearly = 0;
                        }
                        if(res.data.data.BELATENUM != null && res.data.data.BELATENUM != '' && res.data.data.BELATENUM != undefined){
                            self.beLate = res.data.data.BELATENUM;
                        }else {
                            self.beLate = 0;
                        }
                        if(res.data.data.LEAVEEARLYNUM != null && res.data.data.LEAVEEARLYNUM != '' && res.data.data.LEAVEEARLYNUM != undefined){
                            self.leaveEarly = res.data.data.LEAVEEARLYNUM;
                        }else{
                            self.leaveEarly = 0;
                        }
                        if(res.data.data.PunchCardType != null && res.data.data.PunchCardType != '' && res.data.data.PunchCardType != undefined){
                            self.punchCardType = res.data.data.PunchCardType;
                        }else{
                            self.punchCardType = 0;
                        }
                    }
              })
            },
            activeDate: function () {
                $(this.$els.startTime).datetimepicker({
                    minView: 2,
                    format: 'yyyy-mm-dd',
                    weekStart: 1,
                    todayBtn: true
                });
                $(this.$els.endTime).datetimepicker({
                    minView: 2,
                    format: 'yyyy-mm-dd',
                    weekStart: 1,
                    todayBtn: true,
                });
            },
            query: function () {
                var self = this;
                this.$dataTable.bootstrapTable('refresh', {pageNumber: 1});
                this.findAllcount();

            },

            setType:function () {
                self.leaveEarly = 0;
                self.belateandleaveearly = 0;
                self.bsenteeism = 0;
                self.punchCardType = 0;
                self.beLate = 0;
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.datatable).bootstrapTable({
                    url: '/api/checkonwork/checkonwork',
                    method: 'get',
                    dataType: 'json',
                    cache: false, //去缓存
                    pagination: true, //是否分页
                    sidePagination: 'server', //服务端分页
                    content: '',
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({}, params, self.form);
                    },
                    userid: null,
                    createUserName: '',
                    mobileResponsive: true,
                    undefinedText: '-', //空数据的默认显示字符
                    striped: true, //斑马线
                    sortName: 'id', //默认排序列名
                    sortOrder: 'desc', //默认排序方式
                    columns: [
                        {
                            field: 'empName',
                            title: '员工姓名',
                            align: 'center',
                        },
                        {
                            field: 'companyName',
                            title: '公司名称',
                            align: 'center',
                        },
                        {
                            field: 'depName',
                            title: '所在部门',
                            align: 'center',
                        },
                        {
                            field: 'signTime',
                            title: '签到时间',
                            align: 'center',
                        },
                        {
                            field: 'signOutTime',
                            title: '签退时间',
                            align: 'center',
                        },
                        {
                            field: 'address',
                            title: '签到地点',
                            align: 'center',
                        },
                        {
                            field: 'outAddress',
                            title: '签退地点',
                            align: 'center',
                        },
                        {
                            field: 'signType',
                            title: '考勤类型',
                            align: 'center',
                            formatter: function (data) {
                                switch (data) {
                                    case 'NORMAL':
                                        return '正常'
                                        break;
                                    case 'BELATE' :
                                        return '迟到'
                                        break;
                                    case 'LEAVEEARLY' :
                                        return '早退'
                                        break;
                                    case 'ABSENTEEISM' :
                                        return '旷工'
                                        break;
                                    case 'BELATEANDLEAVEEARLY' :
                                        return '迟到并早退'
                                        break;
                                }
                            }
                        },
                        {
                            field: 'punchCardType',
                            title: '签到打卡类型',
                            align: 'center',
                            formatter: function (data) {
                                switch (data) {
                                    case 1:
                                        return '内勤';
                                        break;
                                    case 2 :
                                        return '外勤';
                                        break;
                                }
                            }
                        }
                        ,
                        {
                            field: 'outPunchCardType',
                            title: '签退打卡类型',
                            align: 'center',
                            formatter: function (data) {
                                switch (data) {
                                    case 1:
                                        return '内勤';
                                        break;
                                    case 2 :
                                        return '外勤';
                                        break;
                                }
                            }
                        }
                    ]
                });
            },
            isWeixin: function () {
                var self = this;
                var ua = navigator.userAgent.toLowerCase();
                if (ua.match(/MicroMessenger/i) == "micromessenger") {
                    self.weixin = true;
                } else {
                    self.weixin = false;
                }
            },
            activeDatePicker: function () {
                $(this.$els.signTime).datetimepicker({
                    format: 'yyyy-mm-dd hh:ii:00',
                    minView: 'hour'
                });
            },
            exportBill: function (startDate,endDate) { //导出报表
                var self = this;
                if (startDate && endDate) {
                    window.location.href = '/api/checkonwork/export?empName=' + self.form.empName + '&companyName=' +
                        self.form.companyName + '&startDate=' + self.form.startDate + '&endDate=' + self.form.endDate +
                        '&signType=' + self.form.signType + '&punchCardType=' + self.form.punchCardType;
                }else{
                    self.$toastr.error("请选择日期!");
                }
            },

        },
        watch: {
            'form.startDate':function (val,vala) {
                if (val) {
                    var end=moment(val).endOf('month').format("YYYY-MM-DD");
                    $('#endTime').datetimepicker('setEndDate', end);
                    $('#endTime').datetimepicker('setStartDate',val);
                }
            },
            'form.endDate':function (val,vala) {
                if (val) {
                    var start=moment(val).startOf('month').format("YYYY-MM-DD");
                    $('#startTime').datetimepicker('setEndDate', val);
                    $('#startTime').datetimepicker('setStartDate',start);
                }
            }
        },
        created: function () {
            this.isWeixin();
            this.findAllcount();
            this.findCom();
        },
        ready: function () {
            this.activeDate();
            this.drawTable();

        }
    });
})(Vue, moment);