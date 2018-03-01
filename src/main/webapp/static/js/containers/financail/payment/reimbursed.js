+(function () {
  $('#financailPayment').addClass('active');
  $('#reimbursed').addClass('active');
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
        status: 'REIMBURSED',
      },
      $dataTable: null,
      modalModel: null,
      _$el: null,
      _$dataTable: null,
    },
    created: function () {
    },
    ready: function () {
      var self = this;
      this.drawTable();
    },
    methods: {
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
                width: '10%',
                orderable: true,
                align: 'center',
                formatter: function (value, row, index) {
                    return '<a data-handle="view"  data-type="' + row.applyType + '" data-id="' + row.wfProcessId + '">' + value + '</a>';
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
                width: '10%',
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
                width: '80%',
                orderable: true,
                align: 'center'
            }]
        });
     // 工作流详情
        self.$dataTable.on('click', '[data-handle="view"]',
          function (e) {
            var model = $(this).data();
            window.location.href = "/admin/approval/detail?id=" + model.id + "&type=" + model.applyType;
            e.stopPropagation();
          }
        );
      }
    }
  });
})();