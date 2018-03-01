(function() {
	$(function() {
	    var messageList = new Vue({
	        el: '#container',
	        data: {
	            // 面包屑
	            breadcrumbs: [{
	                path: '/',
	                name: '主页'
	            }, {
	                path: '/',
	                name: '我的消息',
	                active: true //激活面包屑的
	            }],
	            $dataTable: null,
                form: {
                    keyword: '',
                    status: '',
                    messageLevel: '',
                    beginTime: '',
                    endTime: '',
                },
                messages:[]
	        },
	        methods: {
                query: function () {
                    this.$dataTable.bootstrapTable('selectPage', 1);
                },
                drawTable: function () {
                    var self = this;
                    self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
                        url: '/api/index/messageList',
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
                        columns: [
                            {
                                field: 'messageTitle',
                                title: '消息标题',
                                align: 'center',
                                formatter: function (value, row, index) {
                                    return '<a data-handle="view" data-index="' + index + '" data-id="' + row.id + '" >' + value + '</a>';
                                }
                            },{
                                field: 'messageLevel',
                                title: '重要级别',
                                align: 'center',
                                formatter: function (value, row, index) {
                                    var label = '';
                                    switch (value) {
                                        case 1:
                                            label = '一般';
                                            break;
                                        case 2:
                                            label = '重要';
                                            break;
                                        case 3:
                                            label = '紧急';
                                            break;
                                        case 4:
                                            label = '特急';
                                            break;
                                        default:
                                            break;
                                    }
                                    return label;
                                }
                            },{
                                field: 'createTime',
                                title: '创建时间',
                                align: 'center'
                            },{
                                field: 'expirationTime',
                                title: '过期时间',
                                align: 'center'
                            },{
                                field: 'status',
                                title: '状态',
                                align: 'center',
                                formatter: function (value, row, index) {
                                    var label = '';
                                    switch (value) {
                                        case 0:
                                            label = '<span style="color:red;">未读</span>';
                                            break;
                                        case 1:
                                            label = '<span style="color:green;">已读</span>';
                                            break;
                                        default:
                                            break;
                                    }
                                    return label;
                                }
                            }]
                    });

                    // 查看按钮
                    self.$dataTable.on('click', '[data-handle="view"]', function (e) {
                        var id = $(this).data('id');
                        self.$http.get('/api/index/' + id ).then(function (response) {
                            var res = response.data;
                            if (res.code == '1') {
                                var _$modal = $('#messageModal').clone();
                                var $modal = _$modal.modal({
                                    height: 300,
                                    maxHeight: 300,
                                    backdrop: 'static',
                                    keyboard: false
                                });
                                messageList.getMessages(); //弹出列表点击查看后刷新首页消息记录
                                self.$dataTable.bootstrapTable('refresh');//刷新列表
                                showMsgModal($modal,response.data.data);
                            }
                        });
                        e.stopPropagation();
                    });
                },
                getMessages:function(){
                    var self = this;
                    self.$http.get('/api/index/getMineReadingMsg', null, {emulateJSON: true}).then(function (res) {
                        if (res.data.code == 1) {
                            self.messages = res.data.data;
                        }
                    }).finally(function () {

                    });

                },
                activeDatepicker: function () {
                    $(this.$els.beginTime).datetimepicker({
                        minView: 2,
                        format: 'yyyy-mm-dd',
                        todayBtn : true
                    });
                    $(this.$els.endTime).datetimepicker({
                        minView: 2,
                        format: 'yyyy-mm-dd',
                        todayBtn : true
                    });
                }
            },
	        created: function () {
	        },
	        ready: function () {
	        	this.activeDatepicker();
                this.drawTable();
	        }
	    });
	    
	    /**
	     * 显示消息提醒窗口详情
	     * @param $el
	     * @param model
	     * @param needRefresh
	     * @returns
	     */
	    function showMsgModal($el, model){
	        // 获取 node
	        var el = $el.get(0);
	        // 创建 Vue 对象编译节点
	        vueContract = new Vue({
	            el: el,
	            // 模式窗体必须引用 ModalMixin
	            mixins: [RocoVueMixins.ModalMixin],
	            $modal: $el, //模式窗体 jQuery 对象
	            data: {
	                //模型复制给对应的key
	                msg: model
	            },
	            created: function () {

	            },
	            ready: function () {

	            },
	            methods: {
	            },
	            filters: {
	                converLevel: function (value) {
	                    var label = '';
	                    switch (value) {
	                        case 1:
	                            label = '一般';
	                            break;
	                        case 2:
	                            label = '重要';
	                            break;
	                        case 3:
	                            label = '紧急';
	                            break;
	                        case 4:
	                            label = '特急';
	                            break;
	                        default:
	                            break;
	                    }
	                    return label;
	                }
	            }

	        });

	        // 创建的Vue对象应该被返回
	        return vueContract;
	    }
	});
})();