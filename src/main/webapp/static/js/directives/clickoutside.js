// 处理点击实现select 等其它html原生弹出效果的指令
Vue.directive('clickoutside', {
  bind: function () {
    var self = this;
    self.documentHandler = function(e) {
      if (self.el.contains(e.target)) {
        return false
      }
      if (self.expression) {
        self.vm[self.expression]()
      }
    }
    document.addEventListener('click', self.documentHandler)
  },
  update: function (newValue, oldValue) {
    // 值更新时的工作
    // 也会以初始值为参数调用一次
  },
  unbind: function () {
    var self = this;
    document.removeEventListener('click', self.documentHandler)
  }
})