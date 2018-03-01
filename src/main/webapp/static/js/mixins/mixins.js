// 全局 mixin
Vue.mixin({
  ready: function() {
    // 将 $el jquery 化
    this._$el = $(this.$el);
  }
});

// 列表 Mixin
RocoVueMixins.DataTableMixin = {
  created: function() {
    // 会给你创建一个选中对象
    this.selectedRows = {};
  },
  methods: {
    // idKey 必须保证为不重复的唯一 id值
    checkEventHandle: function(key) {
      var self = this;
      if (!key) {
        key = 'id';
      }
      if (self.$dataTable) {
        // 单选选中
        self.$dataTable.on('check.bs.table', function(e, row, $element) {
          var id = row[key];
          self.selectedRows[id] = id;
          self.selectedRows = _.assign({}, self.selectedRows);
        });

        // 单选取消
        self.$dataTable.on('uncheck.bs.table', function(e, row, $element) {
          var id = row[key];
          delete self.selectedRows[id];
          self.selectedRows = _.assign({}, self.selectedRows);
        });

        // 全选选中
        self.$dataTable.on('check-all.bs.table', function(e, rows) {
          _.forEach(rows, function(element, index, array) {
            self.selectedRows[element[key]] = element[key];
          });
          self.selectedRows = _.assign({}, self.selectedRows);
        });

        // 全选取消
        self.$dataTable.on('uncheck-all.bs.table', function(e, rows) {
          _.forEach(rows, function(element, index, array) {
            delete self.selectedRows[element[key]];
          });
          self.selectedRows = _.assign({}, self.selectedRows);
        });
      }
    }
  }
};


// 模式窗体 Mixin
RocoVueMixins.ModalMixin = {
  created: function() {},
  ready: function() {
    var self = this;
    var $modal = self.$options.$modal;
    if ($modal) {
      // 隐藏前触发 Vue 对象的销毁
      $modal.on('hide.bs.modal', function(e) {
        if (e.target == self.$el) {
          self.$destroy();
          console.info('hide.bs.modal from ModalMixin');
        }
      });
      $modal.on('hidden.bs.modal', function(e) {
        if (e.target == self.$el) {
          console.info('hidden.bs.modal from ModalMixin');
        }
      })
    }
  }
};