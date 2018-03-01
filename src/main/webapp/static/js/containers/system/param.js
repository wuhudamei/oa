+(function () {
	$('#systemMenu').addClass('active');
	$('#systemParam').addClass('active');
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
        name: '系统参数设置'
      }],
      form: {
        keyword: ''
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
          url: '/api/system/param/list',
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
                field: 'paramKey',
                title: '系统参数KEY',
                width: '10%',
                orderable: true,
                align: 'center',
                visible: false
            },{
                field: 'paramKeyName',
                title: '参数名称',
                width: '20%',
                orderable: true,
                align: 'center'
            },{
                field: 'paramFlag',
                title: '是否自定义参数',
                width: '10%',
                orderable: true,
                align: 'center',
                visible: false,
                formatter: function (value, row) {
                	if(value){
                		return '是';
                	}else{
                		return '否';
                	}
                }
            },{
                field: 'paramValue',
                title: '参数值',
                width: '50%',
                orderable: true,
                align: 'center', 
                formatter: function (value, row) {
                	if(!row.paramFlag){
                    	if(value == "N"){
                    		return '禁用';
                    	}else{
                    		return '启用';
                    	}
                	}else{
                		return value;
                	}
                }
            },
            {
              field: 'id',
              title: '操作',
              width: '10%',
              orderable: false,
              align: 'center',
              formatter: function (value, row) {
                var html = ''
                	if(!row.paramFlag){
                		if(row.paramValue=='Y'){
                			html += '<button style="margin-left:10px;"';
                            html += 'data-handle="data-change"';
                            html += 'data-id="' + value + '"';
                        	html += 'data-param-value="N"';
                        	html += 'class="m-r-xs btn btn-xs btn-danger" type="button">禁用</button>'	;
                		}else{
                			html += '<button style="margin-left:10px;"';
                            html += 'data-handle="data-change"';
                            html += 'data-id="' + value + '"';
                        	html += 'data-param-value="Y"';
                        	html += 'class="m-r-xs btn btn-xs btn-warning" type="button">启用</button>'	;
                		}
                	}
                html += '<button style="margin-left:10px;"';
                html += 'data-handle="data-edit"';
                html += 'data-id="' + value + '"';
                html += 'data-param-flag="' + row.paramFlag + '"';
            	html += 'data-param-key="' + row.paramKey + '"';
            	html += 'data-param-key-name="' + row.paramKeyName + '"';
            	html += 'data-param-value="' + row.paramValue + '"';
            	html += 'class="m-r-xs btn btn-xs btn-primary" type="button">编辑</button>'	;
                return html ;
              }
            }]
        });
        // 编辑
        self.$dataTable.on('click', '[data-handle="data-edit"]',
          function (e) {
            var model = $(this).data();
            editModal(model);
            e.stopPropagation();
          }
        );
        
        
     // 启用禁用
        self.$dataTable.on('click', '[data-handle="data-change"]',
          function (e) {
            var model = $(this).data();
            if(model.paramValue=='Y'){
            	swal({
                    title: '启用参数',
                    text: '确定启用该参数么？',
                    type: 'info',
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    showCancelButton: true,
                    showConfirmButton: true,
                    showLoaderOnConfirm: true,
                    confirmButtonColor: '#ed5565',
                    closeOnConfirm: false
                }, function () {
                    self.$http.post('/api/system/param/change', model, {emulateJSON: true}).then(function (res) {
                        if (res.data.code == 1) {
                            self.$toastr.success('操作成功');
                            self.$dataTable.bootstrapTable('refresh');
                        }
                    }).catch(function () {

                    }).finally(function () {
                        swal.close();
                    });
                });
            }else{
            	swal({
                    title: '禁用参数',
                    text: '确定禁用该参数么？',
                    type: 'info',
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    showCancelButton: true,
                    showConfirmButton: true,
                    showLoaderOnConfirm: true,
                    confirmButtonColor: '#ed5565',
                    closeOnConfirm: false
                }, function () {
                    self.$http.post('/api/system/param/change', model, {emulateJSON: true}).then(function (res) {
                        if (res.data.code == 1) {
                            self.$toastr.success('操作成功');
                            self.$dataTable.bootstrapTable('refresh');
                        }
                    }).catch(function () {

                    }).finally(function () {
                        swal.close();
                    });
                });
            }
            e.stopPropagation();
          }
        );
        
        
      }
    }
  });

  // 编辑
  function editModal(model) {
    var _modal = $('#editModal').clone();
    var $el = _modal.modal({
      height: 250,
      maxHeight: 600
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
        	  save:function(){
        		  var self = this;
                  self.$validate(true,
                    function () {
                      if (self.$validation.valid) {
                        self.submitting = true;
                        self.$http.post('/api/system/param/edit', $.param(self._data)).then(function (res) {
                            Vue.toastr.success(res.data.message);
                            $el.modal('hide');
                            vueIndex.$dataTable.bootstrapTable('selectPage', 1);
                            self.$destroy();
                          },
                          function (error) {
                            Vue.toastr.error(error.responseText);
                          }).catch(function () {
                        }).finally(function () {
                          self.submitting = false;
                        });
                      }
                    });
        	  }
        	  
          },
        });
        // 创建的Vue对象应该被返回
        return vueModal;
      });
  }
})();