var vm;
+(function (Vue, moment) {
    $('#workMenu').addClass('active');
    $('#sign').addClass('active');
    var fixedLongitude = 116.40387397;
    var fixedLatitude = 39.91488908;
    var map;
    vm = new Vue({
        el: '#container',
        data: {
            noshow: false,
            $dataTable: null,
            sign: {
                punchCardType: '',
                longitude: '',
                latitude: '',
                signTime: '',
                address: '',
                signSiteId: '',
                sitename: '',
                workType:''
            },
            outSign: {
                outPunchCardType: '',
                outAddress: '',
                outLatitude: '',
                outLongitude: '',
                outWorkType:''
            },
            street: '',//街道
            district: '',//区
            province: '',//省
            city: '',//市
            currentaddress: '',
            longitude: '',
            weixin: false,
            signScopeOrLngAndLat: '',
            locationFlag: '',
            loadingFlag: false,
            signScope: false,
            hasSign: false,
            outSide: true,//外勤
            currentData: moment().format('YYYY-MM-DD HH:mm:ss'),
            usersss: RocoUser.name,
            usercom: RocoUser.company,
            depSignTime: '',
            depSignOutTime: '',
            signtime: '',
            signouttime: '',
            signType: '',
            oldType: '',
            oldOutType: '',
            oldTypeShow: false,
            oldOutTypeShow: false,
            address: ''
        },
        methods: {


            //查询上班时间
            findSigntime: function () {
                var self = this;
                self.$http.get('/api/sign/findTime').then(function (res) {
                    if (res.data.code == 1) {
                        if (res.data.data.depSignTime != null && res.data.data.depSignTime != '' && res.data.data.depSignTime != undefined) {
                            self.depSignTime = res.data.data.depSignTime;
                        } else {
                            self.depSignTime = '';
                        }
                        if (res.data.data.depSignOutTime != null && res.data.data.depSignOutTime != '' && res.data.data.depSignOutTime != undefined) {
                            self.depSignOutTime = res.data.data.depSignOutTime;
                        } else {
                            self.depSignOutTime = '';
                        }
                        if (res.data.data.signTime != null && res.data.data.signTime != '' && res.data.data.signTime != undefined) {
                            self.signtime = moment(res.data.data.signTime).format('HH:mm');
                        } else {
                            self.signtime = '';
                        }
                        if (res.data.data.signOutTime != null && res.data.data.signOutTime != '' && res.data.data.signOutTime != undefined) {
                            self.signouttime = moment(res.data.data.signOutTime).format('HH:mm');
                        } else {
                            self.signouttime = '';
                        }
                    }

                })
            },

            /** 去掉查询列表（2017-09-11 新需求要求）
             drawTable: function () {

                var self = this;
                self.$dataTable = $(this.$els.datatable).bootstrapTable({
                    url: '/api/sign/list',
                    method: 'get',
                    dataType: 'json',
                    cache: false, //去缓存
                    pagination: false, //是否分页
                    sidePagination: 'server', //服务端分页
                    content: '',
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({}, params, self.sign);
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
                            field: 'signTime',
                            title: '签到时间',
                            width: '50%',
                            align: 'center',
                        },
                        {
                            field: 'signOutTime',
                            title: '签退时间',
                            width: '50%',
                            align: 'center',
                        }
                    ]
                });
            },
             **/
            isWeixin: function () {
                var self = this;
                var ua = navigator.userAgent.toLowerCase();
                if (ua.match(/MicroMessenger/i) == "micromessenger") {
                    self.weixin = true;
                    return true;
                } else {
                    self.weixin = false;
                    return false;
                }
            },
            findOldType: function () {
                var self = this;
                self.$http.get('/api/sign/oldType').then(function (res) {
                    if (res.data.code == 1) {
                        if (res.data.data) {
                            if (res.data.data.punchCardType != null) {
                                self.oldTypeShow = true;
                                self.oldType = res.data.data.punchCardType;
                            }
                            if (res.data.data.outPunchCardType != null) {
                                self.oldOutTypeShow = true;
                                self.oldOutType = res.data.data.outPunchCardType;
                            }
                        }
                    }
                })
            },
            activeDatePicker: function () {
                $(this.$els.signTime).datetimepicker({
                    format: 'yyyy-mm-dd hh:ii:00',
                    minView: 'hour'
                });
            },
            // 内勤签到/签退
            submitClickHandler: function (flag) {
                var self = this;
                self.submit(flag);
            },
            // 外勤签到/签退
            outSideSubmitClickHandler: function (flag) {
                var self = this;
                self.outSideSubmit(flag);
            },
            goBack: function () {
                window.history.back(-1);
            },

            submit: function (flag) {
                var self = this;
                swal({
                    title: "是否确定打卡?",
                    type: "warning",
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    showCancelButton: true,
                    showConfirmButton: true,
                    showLoaderOnConfirm: true,
                    confirmButtonColor: '#ed5565',
                    closeOnConfirm: false
                }, function () {
                    self.sign.punchCardType = 1;
                    self.sign.workType = 1;//上班打卡
                    self.outSign.outPunchCardType = 1;
                    self.outSign.outWorkType = 2;//下班打卡
                    self.submitting = true;
                    var data = self.filterNull(self.sign);
                    var outData = self.filterNull(self.outSign);
                    if (flag) {
                        self.$http.post(ctx + '/api/sign/saveSign', data).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('提交成功');
                                setTimeout(function () {
                                    if (!self.weixin) { //PC
                                        window.location.reload();
                                    } else { //微信
                                        window.location.href = '/admin/sign';// + new Date();//这么写是为了解决安卓微信浏览器不刷新的问题
                                    }
                                }, 1000);
                            } else {
                                self.$toastr.error(res.data.message);
                                setTimeout(function () {
                                    if (!self.weixin) { //PC
                                        window.location.reload();
                                    } else { //微信
                                        window.location.href = '/admin/sign';// + new Date();//这么写是为了解决安卓微信浏览器不刷新的问题
                                    }
                                }, 1000);
                                swal.close();
                            }
                        }).catch(function () {

                        }).finally(function () {
                            self.submitting = false;
                        });
                    } else {

                        self.$http.post(ctx + '/api/sign/signOut', outData).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('提交成功');
                                setTimeout(function () {
                                    if (!self.weixin) { //PC
                                        window.location.reload();
                                    } else { //微信
                                        window.location.href = '/admin/sign';// + new Date();//这么写是为了解决安卓微信浏览器不刷新的问题
                                    }
                                }, 1000);
                            } else {
                                self.$toastr.error(res.data.message);
                                setTimeout(function () {
                                    if (!self.weixin) { //PC
                                        window.location.reload();
                                    } else { //微信
                                        window.location.href = '/admin/sign';// + new Date();//这么写是为了解决安卓微信浏览器不刷新的问题
                                    }
                                }, 1000);
                                swal.close();
                            }
                        }).catch(function () {

                        }).finally(function () {
                            self.submitting = false;
                        })
                    }
                });
            },

            outSideSubmit: function (flag) {
                var self = this;
                swal({
                    title: "是否确定【" + self.signType + "】打卡?",
                    type: "warning",
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    showCancelButton: true,
                    showConfirmButton: true,
                    showLoaderOnConfirm: true,
                    confirmButtonColor: '#ed5565',
                    closeOnConfirm: false
                }, function () {
                    self.sign.punchCardType = 2;
                    self.sign.workType = 1;//上班打卡
                    self.outSign.outPunchCardType = 2;
                    self.outSign.outWorkType = 2;//下班打卡
                    self.submitting = true;
                    var data = self.filterNull(self.sign);
                    var outData = self.filterNull(self.outSign);
                    if (flag) {
                        self.$http.post(ctx + '/api/sign/saveSign', data).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('提交成功');
                                setTimeout(function () {
                                    if (!self.weixin) { //PC
                                        window.location.reload();
                                    } else { //微信
                                        window.location.href = '/admin/sign';// + new Date();//这么写是为了解决安卓微信浏览器不刷新的问题
                                    }
                                }, 1000);
                            } else {
                                self.$toastr.error(res.data.message);
                                setTimeout(function () {
                                    if (!self.weixin) { //PC
                                        window.location.reload();
                                    } else { //微信
                                        window.location.href = '/admin/sign';// + new Date();//这么写是为了解决安卓微信浏览器不刷新的问题
                                    }
                                }, 1000);
                                swal.close();
                            }
                        }).catch(function () {

                        }).finally(function () {
                            self.submitting = false;
                        });
                    } else {
                        self.$http.post(ctx + '/api/sign/signOut', outData).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('提交成功');
                                setTimeout(function () {
                                    if (!self.weixin) { //PC
                                        window.location.reload();
                                    } else { //微信
                                        window.location.href = '/admin/sign';// + new Date();//这么写是为了解决安卓微信浏览器不刷新的问题
                                    }
                                }, 1000);
                            } else {
                                self.$toastr.error(res.data.message);
                                setTimeout(function () {
                                    if (!self.weixin) { //PC
                                        window.location.reload();
                                    } else { //微信
                                        window.location.href = '/admin/sign'; //+ new Date();//这么写是为了解决安卓微信浏览器不刷新的问题
                                    }
                                }, 1000);
                                swal.close();
                            }
                        }).catch(function () {

                        }).finally(function () {
                            self.submitting = false;
                        })
                    }
                });
            },

            filterNull: function (data) {
                var t = {};
                if (data) {
                    for (var k in data) {
                        var v = data[k];
                        if (v) {
                            t[k] = v;
                        }
                    }
                }
                return t;
            },

            //加载js-config
            loadJsApiConfig: function () {
                var self = this;
                //1.获取js-config中必要数据
                self.$http.get('/wx/jssdk/config?url=' + window.location.href).then(function (res) {
                    if (res.data.code == 1) {
                        console.log(res.data.data);
                        //调用js-sdk
                        wx.config({
                            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                            appId: res.data.data.appId, // 必填，公众号的唯一标识
                            timestamp: res.data.data.timestamp, // 必填，生成签名的时间戳
                            nonceStr: res.data.data.nonceStr, // 必填，生成签名的随机串
                            signature: res.data.data.signature,// 必填，签名，见附录1
                            jsApiList: ['getLocation', 'openLocation'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
                        });

                        //成功后
                        wx.ready(function () {
                            // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，
                            // config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，
                            // 则须把相关接口放在ready函数中调用来确保正确执行。
                            // 对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
                            self.getLocation();
                        });
                    }
                });

            },

            getLocation: function () {
                var self = this;
                wx.getLocation({
                    type: 'gcj02', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
                    success: function (res) {
                        // self.loadingFlag = res.latitude === '' ? true : false
                        self.sign.latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                        self.sign.longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                        self.outSign.outLatitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
                        self.outSign.outLongitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
                        var speed = res.speed; // 速度，以米/每秒计
                        var accuracy = res.accuracy; // 位置精度
                        var point = new BMap.Point(self.sign.longitude, self.sign.latitude);
                        var gc = new BMap.Geocoder();
                        var mapAddress = '';
                        gc.getLocation(point, function (rs) {
                            self.address = rs.addressComponents;
                            self.currentaddress = self.address.province + self.address.city + self.address.district
                                + self.address.street + self.address.streetNumber;
                            self.signscope();
                        });
                    }

                });
            },

            //获取签到范围
            signscope: function () {
                var self = this;
                // var circle = new BMap.Geolocation();
                // circle.getCurrentPosition(function (position) {
                // if (position) {
                // self.loadingFlag = position.latitude === '' ? true : false
                // self.sign.longitude = position.longitude;//经度
                // self.sign.latitude = position.latitude;//纬度
                // self.outSign.outLatitude = position.latitude;
                // self.outSign.outLongitude = position.longitude;
                // self.province = position.address.province;//省份
                // self.city = position.address.city;//城市
                // self.district = position.address.district;//区
                // self.street = position.address.street;//街道
                // //获取当前位置信息
                // self.currentaddress = position.address.province + position.address.city + position.address.district + position.address.street;
                //
                // var point = new BMap.Point(self.sign.longitude, self.sign.latitude);
                // //使用当前经纬度创建地图
                // map.centerAndZoom(point, 11);
                // //创建标注
                // var marker = new BMap.Marker(point);
                // //在地图上添加标注
                // map.addOverlay(marker);
                // //把当前的位置定位成地图的中心
                // map.panTo(point);
                // //调用点击标注显示信息的方法
                // self.addClickHandler(self.currentaddress, marker);

                //如果手机没有开启定位或者拒绝获取当前位置时返回首页(手机没有开启定位或者拒绝获取当前位置时默认的经纬度是天安门的经纬度)
                if (self.weixin == true && self.sign.longitude == fixedLongitude && self.sign.latitude == fixedLatitude) {
                    alert('请授权开启定位服务或授权访问当前位置')
                    window.location.href = ctx + '/index';
                }

                //如果当前位置信息为空，则把获取到的经纬度转换为具体地址
                if (self.currentaddress == null || self.currentaddress == '' || self.currentaddress == undefined) {

                    var point = new BMap.Point(self.sign.longitude, self.sign.latitude);
                    //通过获取到的经纬度转换为具体地址
                    var gc = new BMap.Geocoder();
                    gc.getLocation(point, function (rs) {
                        var addComp = rs.addressComponents;
                        self.currentaddress = addComp.province + addComp.city + addComp.district + addComp.street + addComp.streetNumber;
                    });

                }

                self.$http.get('/api/signsite/signscope').then(function (response) {//查询职场(签到范围)
                    var res = response.data;
                    if (res.code == 1) {//如果有职场
                        self.signScopeOrLngAndLat = res.data;
                        //获取当前位置的经纬度
                        var pointB = new BMap.Point(parseFloat(self.sign.longitude), parseFloat(self.sign.latitude));
                        for (var i = 0, length = self.signScopeOrLngAndLat.length; i < length; i++) {
                            //获取职场的经纬度
                            var pointA = new BMap.Point(parseFloat(self.signScopeOrLngAndLat[i].longitude), parseFloat(self.signScopeOrLngAndLat[i].latitude));
                            //计算两点之间的距离
                            var dis = map.getDistance(pointA, pointB).toFixed(2);
                            //两点之间的距离小于于有效半径
                            if (dis < parseInt(self.signScopeOrLngAndLat[i].radii)) {//在签到范围内,是内勤
                                self.signType = '内勤';
                                self.outSide = false;
                                self.currentaddress = self.signScopeOrLngAndLat[i].sitename;
                                self.sign.address = self.signScopeOrLngAndLat[i].sitename;
                                self.outSign.outAddress = self.signScopeOrLngAndLat[i].sitename;
                                self.sign.signSiteId = self.signScopeOrLngAndLat[i].id;
                                // self.signScope = false;
                                break;
                            }
                        }
                        if (self.sign.address == '') {//不在公司签到范围内则是外勤(外勤要存实际地址)，否则是内勤
                            // self.signScope = true;
                            self.signType = '外勤';
                            // self.sign.signSiteId = self.signScopeOrLngAndLat[i].id;
                            self.sign.address = self.address.province + self.address.city + self.address.district + self.address.street;
                            self.outSign.outAddress = self.address.province + self.address.city + self.address.district + self.address.street;
                        }
                    }
                    // })
                    // }


                })
            },

            //点击标注显示信息的方法
            addClickHandler: function (content, marker) {
                var self = this;
                marker.addEventListener("click", function (e) {
                    self.openInfo(content, e)
                })
            },

            //初始化信息提示框
            openInfo: function (content, e) {
                var opts = {
                    width: 50,     // 信息窗口宽度
                    height: 30,     // 信息窗口高度
                    enableMessage: true//设置允许信息窗发送短息
                };
                var p = e.target;
                var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
                var infoWindow = new BMap.InfoWindow(content, opts);  // 创建信息窗口对象
                map.openInfoWindow(infoWindow, point); //开启信息窗口
            },
            //在页面中初始化一个地图
            initMap: function () {
                this.setMapEvent();//设置地图事件
                this.addMapControl();//向地图添加控件
            },

            createMap: function () {
                var self = this;
                var point = new BMap.Point(self.sign.longitude, self.sign.latitude);//定义一个中心点坐标
                window.map = map;//将map变量存储在全局
            },

            //设置地图事件
            setMapEvent: function () {
                //启用地图拖拽事件
                map.enableDragging();
                //启用地图滚轮放大缩小
                map.enableScrollWheelZoom();
                //启用鼠标双击放大，默认启用(可不写)
                map.enableDoubleClickZoom();
            },

            //添加地图控件
            addMapControl: function () {
                //向地图中添加缩放控件
                var ctrl_nav = new BMap.NavigationControl({
                    anchor: BMAP_ANCHOR_TOP_LEFT,
                    type: BMAP_NAVIGATION_CONTROL_LARGE
                });
                map.addControl(ctrl_nav);
            },


            //
        },

        created: function () {
            //微信端加载
            this.loadJsApiConfig();
            this.isWeixin();
            map = new BMap.Map("dituContent");
            this.sign.signTime = moment().format('YYYY-MM-DD HH:mm:ss');
            this.initMap();
            this.findOldType();

        },
        watch: {

            'currentaddress': function (val) {
                var self = this;
                self.loadingFlag = val === '' ? false : true
            },

            'sign.latitude': function () {
                this.createMap();//创建地图
            }

        },
        ready: function () {
            this.activeDatePicker();
            //this.drawTable();
            this.findSigntime();
        }
    });
})(Vue, moment);