/**
 * Created by Andy on 2017/5/17.
 */
+(function () {
  $('#wechatMenu').addClass('active');
  $('#qrcodeMenu').addClass('active');
  var vueIndex = new Vue({
    el: '#container',
    mixins: [RocoVueMixins.DataTableMixin],
    data: {
      // 面包屑
      breadcrumbs: [{
        path: '/',
        name: '主页'
      }, {
        path: '/', 
        name: '二维码列表',
        active: true // 激活面包屑的
      }],
      // 查询表单
      form: {
        keyword: ''
      },
      _$el: null, // 自己的 el $对象
      _$dataTable: null // datatable $对象
    },
    created: function () {
    },
    ready: function () {
      this.drawTable();
    },
    methods: {
      query: function () {
        this.$dataTable.bootstrapTable('refresh', {pageNumber: 1});
      },
      drawTable: function () {
        var self = this;
        self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
          url: '/wx/qrcode/findQrCodeByCondition',
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
              field: 'tagName',
              title: '二维码名称',
              align: 'center'
            },{
              field: 'ticket',
              title: 'ticket',
              align: 'center',
              visible: false// 不显示该列
            }, {
              field: 'id',
              title: "操作",
              align: 'center',
              formatter: function (value, row, index) {
            	  return '<a href="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=' + row.ticket + '" target="_blank" data-id="' + row.id + '" data-name="' + row.name + '">查看二维码</a>';
              }
            }]
        });
      },
      createModel: function () {
        var _$modal = $('#createModal').clone();
        var $modal = _$modal.modal({
          height: 120,
          maxHeight: 450,
          backdrop: 'static',
          keyboard: false
        });
        createModal($modal, false);
      }
    }
  });

  /**
	 * 生成二维码
	 * 
	 * @param $el
	 * @param model
	 * @param isEdit
	 * @returns
	 */
  function createModal($el) {
    // 获取 node
    var el = $el.get(0);
    // 创建 Vue 对象编译节点
    var vueTag = new Vue({
      el: el,
      // 模式窗体必须引用 ModalMixin
      mixins: [RocoVueMixins.ModalMixin],
      $modal: $el, // 模式窗体 jQuery 对象
      data: {
        // 控制 按钮是否可点击
        disabled: false,
        qrCode:{
        	sceneId:null,
        	type:"QR_LIMIT_SCENE"
        },
        // 进项列表
        submitBtnClick: false,
        validate:false,
        tags:null
      },
      ready: function () {
          this.getTagList();
      },
      methods: {
          getTagList: function () {// 获取标签列表
              var self = this;
              self.$http.get('/api/wx/tag').then(function (res) {
                  if (res.data.code == 1) {
                	  self.tags = res.data.data.rows;
                  }
              }).catch(function () {
              }).finally(function () {
              });
            },
        submit: function () {
          var self = this;
       	  self.submitBtnClick = true;
          self.$validate(true, function () {
            if (self.$validation.valid) {
              self.disabled = true;
              console.log(self.qrCode);
                self.$http.post(ctx + '/wx/qrcode', self.qrCode)
                  .then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                      $el.on('hidden.bs.modal', function () {
                        vueIndex.$dataTable.bootstrapTable('refresh');
                        self.$toastr.success('操作成功!');
                      });
                      $el.modal('hide');
                    }else{
                    	self.$toastr.error(res.message);
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
    return vueTag;
  }
})();

