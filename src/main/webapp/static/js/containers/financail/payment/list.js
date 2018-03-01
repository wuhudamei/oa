+(function () {
  var vueIndex = new Vue({
    el: '#container',
    mixins: [RocoVueMixins.DataTableMixin],
    data: {
      breadcrumbs: [{
        path: '/',
        name: '主页'
      },
      {
        path: '/',
        name: '财务报销管理'
      }],
      form: {
        keyword: '',
        status: '',
      },
      noteFlag:'',
      $dataTable: null,
      modalModel: null,
      _$el: null,
      _$dataTable: null,
    },
    created: function () {
    	var self = this;
        self.params = RocoUtils.parseQueryString();
        self.initParams();
    },
    ready: function () {
      var self = this;
      this.drawTable();
    },
    methods: {
    	// 根据地址栏参数，初始化数据,激活菜单样式
        initParams: function () {
        	$('#financailPayment').addClass('active');
            var self = this;
            var params = RocoUtils.parseQueryString(window.location.search.substr(1));
            var type = params['type'];
            self.form.status = type;
            if(type == 'GRANT'){
            	$('#chairmanGrant').addClass('active');
            	self.noteFlag = false;
            }else{
            	$('#toReimbursed').addClass('active');
            	self.noteFlag = true;
            }
        },
      query: function () {
        this.$dataTable.bootstrapTable('selectPage', 1);
      },
      drawTable: function () {
        var self = this;
        self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
          url: '/api/financail/payment/list',
          method: 'get',
          dataType: 'json',
          cache: false,
          pagination: true,
          sidePagination: 'server',
          queryParams: function (params) {
            return _.extend({},
              params, self.form);
          },
          mobileResponsive: true,
          undefinedText: '-',
          striped: true,
          maintainSelected: true,
          sortOrder: 'desc',
          columns: [
            {
              field: "id",
              visible: false
            },
            {
                field: 'wfProcessTittle',
                title: '工作流标题',
                width: '20%',
                orderable: true,
                align: 'center',
                formatter: function (value, row, index) {
                    return '<a data-handle="view"  data-type="' + row.applyType + '" data-id="' + row.formId + '">' + value + '</a>';
                }
            },{
                field: 'budgetMonth',
                title: '预算月份',
                width: '10%',
                orderable: true,
                align: 'center'
            },{
                field: 'applyDate',
                title: '申请时间',
                width: '20%',
                orderable: true,
                align: 'center',
                formatter: function (value, row) {
                	function add0(m){return m<10?'0'+m:m }
                	var time = new Date(value);
                	var y = time.getFullYear();
                	var m = time.getMonth()+1;
                	var d = time.getDate();
                	var h = time.getHours();
                	var mm = time.getMinutes();
                	var s = time.getSeconds();
                	return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
                }
            },{
                field: 'company',
                title: '申请公司',
                width: '15%',
                orderable: true,
                align: 'center',
                formatter: function (value, row) {
                	return value.orgName;
                }
            },
            {
              field: 'applyUser',
              title: '申请人',
              width: '10%',
              orderable: true,
              align: 'center',
              formatter: function (value, row) {
              	return value.name;
              }
            },{
                field: 'invoiceTotal',
                title: '发票张数',
                width: '10%',
                orderable: true,
                align: 'center'
            },{
                field: 'totalPrice',
                title: '报销总金额',
                width: '10%',
                orderable: true,
                align: 'center'
            },{
                field: 'note',
                title: '备注说明',
                width: '30%',
                orderable: true,
                align: 'center',
                visible: self.noteFlag
            },
            {
              field: 'id',
              title: '操作',
              width: '15%',
              orderable: false,
              align: 'center',
              formatter: function (value, row) {
                var html = ''
                html += '<button style="margin-left:10px;"';
                html += 'data-handle="data-edit"';
                html += 'data-id="' + value + '"';
                html += 'data-status="' + self.form.status + '"';
                if(self.form.status == 'GRANT'){
                	html += 'data-note=""';
                    html += 'class="m-r-xs btn btn-xs btn-primary" type="button">授权</button>'	;
                }else{
                	html += 'data-note="' + row.note + '"';
                	html += 'data-form-id="' + row.formId + '"';
                	html += 'class="m-r-xs btn btn-xs btn-primary" type="button">报销</button>'	;
                }
                return html ;
              }
            }]
        });
        // 授权/报销
        self.$dataTable.on('click', '[data-handle="data-edit"]',
          function (e) {
            var model = $(this).data();
            noteModal(model);
            e.stopPropagation();
          }
        );
        // 工作流详情
        self.$dataTable.on('click', '[data-handle="view"]',
          function (e) {
            // var model = $(this).data();
            // window.location.href = "/admin/approval/detail?id=" + model.id + "&type=" + model.type;
              var id = $(this).data('id');
              var url = '/api/payments/info?id=' + id;
              window.location.href = url;
            e.stopPropagation();
          }
        );
      }
    }
  });

  // 授权/报销
  function noteModal(model) {
    var _modal = $('#noteModal').clone();
    var $el = _modal.modal({
      height: 180,
      maxHeight: 400
    });
    $el.on('shown.bs.modal',
      function () {
        var el = $el.get(0);
        var vueModal = new Vue({
          el: el,
          http: {
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
            }
          },
          mixins: [RocoVueMixins.ModalMixin],
          components: {
            'web-uploader': RocoVueComponents.WebUploaderComponent
          },
          $modal: $el,
          created: function () {
          },
          data: model,
          methods: {
            save: function () {
            	var self = this;
	            self.submitting = true;
	            self.$http.post('/api/financail/payment/change', $.param(self._data)).then(function (res) {
	              if(res.data.code === '1'){
	                Vue.toastr.success(res.data.message);
	                $el.modal('hide');
	                vueIndex.$dataTable.bootstrapTable('selectPage', 1);
	                self.$destroy();
	              }
	              },
	            function (error) {
	              Vue.toastr.error(error.responseText);
	            }).catch(function () {
	            }).finally(function () {
	              self.submitting = false;
	            });
            }
          },
        });
        // 创建的Vue对象应该被返回
        return vueModal;
      });
  }
})();