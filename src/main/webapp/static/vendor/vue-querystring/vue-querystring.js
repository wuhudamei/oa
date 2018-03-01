'use strict';
(function(Vue) {
  Vue.prototype.$parseQueryString = function(queryString) {
    var params = {};
    var parts = queryString && queryString.split('&') || window.location.search.substr(1).split('\x26');

    for (var i = 0; i < parts.length; i++) {
      var keyValuePair = parts[i].split('=');
      var key = decodeURIComponent(keyValuePair[0]);
      var value = keyValuePair[1] ?
        decodeURIComponent(keyValuePair[1].replace(/\+/g, ' ')) :
        keyValuePair[1];

      switch (typeof(params[key])) {
        case 'undefined':
          params[key] = value;
          break; //first
        case 'array':
          params[key].push(value);
          break; //third or more
        default:
          params[key] = [params[key], value]; // second
      }
    }
    return params;
  };
})(Vue);