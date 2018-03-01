+(function() {
  var Breadcrumb = Vue.extend({
    template:'#breadcrumbTmpl',
    props:{
      crumbs:{
        type:Array,
        default:[]
      }
    },
    data: function(){
      return {};
    }
  });

  Vue.component('bread-crumb', Breadcrumb);
})();