var vueIndex = null;
+(function (RocoUtils) {


   vueIndex = new Vue({
        el: '#container',
        data: {
            items:[{one:'线索报表',two:'进店报表'},{one:'线索报表',two:'进店报表'},{one:'线索报表',two:'进店报表'}]
        },
        methods:{
        },
        ready: function() {
      
        }
    })
})(this.RocoUtils)