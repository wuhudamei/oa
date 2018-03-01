var vueIndex = null;
var vueMsgList = null;
var vueContract = null;
+(function (Vue, $, _, moment, RocoUtils) {
    $('#dashboardMenu').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        mixins: [RocoVueMixins.DataTableMixin],
        data: {
            messages:[],
            noticeMessage:[],// 公告
            _$dataTable: null, // datatable $对象
            form:{
            	limit:5
            }
        },
        methods: {
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
              },
            drawTable: function () {
                var self = this;
                if (self.type) {
                  self.form.type = self.type;
                }
                self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
                  url: '/api/wfmanager/list',
                  method: 'get',
                  dataType: 'json',
                  cache: false, // 去缓存
                  pagination: false, // 是否分页
                  sidePagination: 'server', // 服务端分页
                  queryParams: function (params) {
                    // 将table 参数与搜索表单参数合并
                    return _.extend({}, params, self.form);
                  },
                  mobileResponsive: true,
                  undefinedText: '-', // 空数据的默认显示字符
                  striped: true, // 斑马线
                  maintainSelected: true, // 维护checkbox选项
                  columns: [
                      {
        			   field: 'id',
        			   title: 'ID',
        			   align: 'center',
        			   visible: false
        			  },{
                       field: 'nodeId',
                       title: '当前节点',
                       align: 'center',
                       visible: false
                     } ,{
                       field: 'applyCode',
                       title: '申请编码',
                       align: 'center'
                     } ,{
        	           field: 'applyTitle',
        	           title: '申请标题',
        	           align: 'center',
                       formatter:function(value,row,index){
                         	return '<a data-handle="wf-approve" data-type="'+ row.type + '" data-index="' + index + '" data-id="' + row.formId + '">' + value + '</a>';
                      }
        	         },{
        	           field: 'applyUserId',
        	           title: '申请人ID',
        	           align: 'center',
        	           visible: false
                     },{
        	           field: 'applyUserName',
        	           title: '申请人',
        	           align: 'center'
        	         },{
        	           field: 'nodeType',
        	           title: '节点类型',
        	           align: 'center',
        	           visible: false
        		     },{
                      field: 'type',
                      title: '流程类型',
                      align: 'center',
                      formatter: function (value, row, index) {
                        return formApproveStatus(value);
                      }
        	          }, {
                       field: 'isSign',
                       title: '是否会签',
                       align: 'center',
                       visible: false
                    }, {
                      field: 'status',
                      title: '当前状态',
                      align: 'center',
                      visible: false
                    }, {
                      field: 'approverEmployee',
                      title: '审批人',
                      align: 'center',
                      visible: false,
                      formatter: function (value, row, index) {
                          return value.id;
                      }
                    }, {
                      field: 'createTime',
                      title: '提交时间',
                      align: 'center'
                    }, {
                      field: 'formId',
                      title: '表单ID',
                      align: 'center',
                      visible: false
                    }, {
                      field: '', // 将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                      title: "操作",
                      align: 'center',
                      formatter: function (value, row, index) {
                    	  var button = "";
                    	  button += '<button data-handle="agree" data-order="' + row + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-primary">通过</button>&nbsp;&nbsp;';
                    	  button += '<button data-handle="refuse" data-order="' + row + '" data-index="' + index + '" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-danger">拒绝</button>';
                          return button;
                      }
                    }]
                });
                
                /**
				 * 通过
				 */
                self.$dataTable.on('click', '[data-handle="agree"]', function (e) {
                  var id = $(this).data('id');
                  var index = $(this).data('index');
                  var rowData = self.$dataTable.bootstrapTable('getData');
                  var curData = rowData[index];
                  quickApproveEx(self,curData,"AGREE",self.$dataTable);
                  e.stopPropagation();
                });
                
                /**
				 * 拒绝
				 */
                self.$dataTable.on('click', '[data-handle="refuse"]', function (e) {
                  var id = $(this).data('id');
                  var index = $(this).data('index');
                  var rowData = self.$dataTable.bootstrapTable('getData');
                  var curData = rowData[index];
                  quickApproveEx(self,curData,"REFUSE",self.$dataTable);
                  e.stopPropagation();
                });
                
                
                /**
				 * 审批历史详情
				 */
                self.$dataTable.on('click', '[data-handle="wf-approve"]', function (e) {
                  var id = $(this).data('id');
                  var index = $(this).data('index');
                  var rowData = self.$dataTable.bootstrapTable('getData');
                  var curData = rowData[index];
                  var type = curData.type;
                  var formId = curData.formId;
                  var url = "";
                  if("SIGNATURE" == type){  // 签报
                	  url = "/api/signature/info?id=" + formId;
                  }else if("EXPENSE" == type){  //报销
                	  url = "/api/payments/info?id=" + formId;
                  }else  if ("COMMON" == type) { // 通用
                	  url = "/admin/commonApply/info?id=" + formId;
          		  }
                  if(url != ""){
                	  window.location.href = url;
                  }
//                  approvalDetail(self,curData.type,curData);
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
            getNoticeMessages:function(){// 公告开始
                var self = this;
                self.$http.get('/noticeboard/allList', null, {emulateJSON: true}).then(function (res) {
                    if (res.data.code == 1) {

                        self.noticeMessage = res.data.data.slice(0,3);
                    }
                }).finally(function () {

                });
            },
            viewMsgDetial:function(index){
                var self = this;
                var obj = self.messages[index];
                // 1、更新消息为已读
                self.$http.put('/api/index/changeMsgStatus', {"messageId":obj.id}, {emulateJSON: true}).then(function (res) {
                    if (res.data.code == 1) {
                    	vueIndex.getMessages(); // 更新成功后重新获取首页消息条数
                    }
                }).finally(function () {

                });
                // 2、弹框展示消息详情
                var _$modal = $('#messageModal').clone();
                var $modal = _$modal.modal({
                    height: 300,
                    maxHeight: 300,
                    keyboard: false
                });

                showMsgModal($modal,obj);
            },
            noticeViewMsgDetial:function(index){// 查看更多公告
                var self = this;
                var obj = self.noticeMessage[index];
                window.location.href = ctx + '/admin/noticedetils?id='+obj.id;
            },
            showMoreMsg:function () { // 查看更多消息
                var self = this;
            	window.location.href = ctx + '/admin/message/list';
				// var self = this;
				// var _$modal = $('#messageListModal').clone();
				// var $modal = _$modal.modal({
				// height: 450,
				// maxHeight: 500,
				// keyboard: false,
				// backdrop: 'static'
				// });
				// showMsgListModal($modal);
            },
            showMoreApprove:function(){
            	window.location.href = '/admin/approval';
            }
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
            },
            NoticeConverLevel:function (value) {
                var label = '';
                switch (value) {
                    case '1':
                        label = '普通';
                        break;
                    case '2':
                        label = '重要';
                        break;
                    case '3':
                        label = '紧急';
                        break;
                    default:
                        break;
                }
                return label;
            }
        },
        created: function () {
        },
        ready: function () {
            this.getMessages();
            this.drawTable();
            this.getNoticeMessages()
        }
    });

    /**
	 * 显示消息提醒窗口详情
	 * 
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
            $modal: $el, // 模式窗体 jQuery 对象
            data: {
                // 模型复制给对应的key
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
    
    /**
	 * 显示更多消息列表
	 * 
	 * @param $el
	 * @returns
	 */
    function showMsgListModal($el){
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        vueMsgList = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, // 模式窗体 jQuery 对象
            data: {
                // 查询表单
                form: {
                    keyword: '',
                    status: '',
                    messageLevel: '',
                    beginTime: '',
                    endTime: '',
                },
                selectedRows: {}, // 选中列
                modalModel: null, // 模式窗体模型
                _$el: null, // 自己的 el $对象
                _$dataTable: null // datatable $对象
            },
            created: function () {

            },
            ready: function () {
                this.activeDatepicker();
                this.drawTable();
            },
            methods: {
                query: function () {
                    this.$dataTable.bootstrapTable('selectPage', 1);
                },
                drawTable: function () {
                    var self = this;
                    self.$dataTable = $('#dataTableMsg', self._$el).bootstrapTable({
                        url: '/api/index/messageList',
                        method: 'get',
                        dataType: 'json',
                        cache: false, // 去缓存
                        pagination: true, // 是否分页
                        sidePagination: 'server', // 服务端分页
                        queryParams: function (params) {
                            // 将table 参数与搜索表单参数合并
                            return _.extend({}, params, self.form);
                        },
                        mobileResponsive: true,
                        undefinedText: '-', // 空数据的默认显示字符
                        striped: true, // 斑马线
                        maintainSelected: true, // 维护checkbox选项
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
                                vueIndex.getMessages(); // 弹出列表点击查看后刷新首页消息记录
                                self.$dataTable.bootstrapTable('refresh');// 刷新列表
                                showMsgModal($modal,response.data.data);
                            }
                        });
                        e.stopPropagation();
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

        });

        // 创建的Vue对象应该被返回
        return vueMsgList;
    }
    // 新建编辑弹窗
    function modal($el, model, isEdit) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        var vueModal = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, // 模式窗体 jQuery 对象
            data: {
                // 控制 按钮是否可点击
                disabled: false,
                // 模型复制给对应的key
                plan: model,
                submitBtnClick: false
            },
            created: function () {

            },
            ready: function () {
                this.activeDatepiker();
            },
            methods: {
                activeDatepiker: function () {
                    RocoUtils.initDateControl($(this.$els.startDate), $(this.$els.endDate));
                },
                submit: function () {
                    var self = this;
                    self.submitBtnClick = true;
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.disabled = true;
                            self.$http.post('/api/plans', self.plan, {emulateJSON: true}).then(function (res) {
                                if (res.data.code == 1) {
                                    $el.on('hidden.bs.modal', function () {
                                        vueIndex.destroyFullCalendar();
                                        vueIndex.activeFullCalendar();
                                        self.$toastr.success('操作成功');
                                    });
                                    self.$destroy();
                                    $el.modal('hide');
                                }
                            }).finally(function () {
                                self.disabled = false;
                            });
                        }
                    });
                }
            }
        });
        // 创建的Vue对象应该被返回
        return vueModal;
    }
    function noticeModal($el, model) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        var vueModal = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, // 模式窗体 jQuery 对象
            data: {
                // 控制 按钮是否可点击
                disabled: false,
                // 模型复制给对应的key
                noticeboard: model,
                submitBtnClick: false,
                // username : window.RocoUser.name
            },

        });
        // 创建的Vue对象应该被返回
        return vueModal;
    }
})(Vue, jQuery, _, moment, RocoUtils);

