'use strict' + (function(toastr) {
  toastr.options = {
    closeButton: false, //关闭按钮去掉
    debug: false, //debug模式
    newestOnTop: true, //最新的在上面
    progressBar: false,
    preventDuplicates:true,
    positionClass: "toast-top-center"
  };
  Vue.toastr = toastr;
  Vue.prototype.$toastr = toastr;
})(toastr);