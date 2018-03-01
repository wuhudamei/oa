+(function() {
  Vue.filter('user-type-filter', function(value) {
    switch (value) {
      case 0:
        return '超级管理员';
      default:
        return '';
    }
  });

  Vue.filter('user-img-filter', function(value) {
    return value || '/static/img/defImg.jpg';
  });

  Vue.filter('capacity-filter', function(value) {
    try {
      if(value === ''){
        return '';
      }else{
        return (value / 1024 / 1024).toFixed(2) + 'MB';
      }
    } catch (e) {
      return value;
    }
  });

  Vue.filter('zipname-filter', function(value) {
    try {
      return value.substring(value.lastIndexOf('/') + 1);
    } catch (e) {
      return value;
    }
  });

  Vue.filter('ms-to-s-filter',function(value){
    try{
      return (value / 1000).toFixed(2);
    }catch(e){
      return value;
    }
  });

   Vue.filter('moment-date', function(value) {
    try {
      return moment(value).format('YYYY-MM-DD HH:mm:ss')
    } catch (e) {
      return value;
    }
  });

    Vue.filter('moment-hh', function(value) {
        try {
            return moment(value).format('HH:mm')
        } catch (e) {
            return value;
        }
    });
})(Vue);