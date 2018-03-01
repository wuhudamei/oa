

+(function (RocoUtils, io) {
  var login = new Vue({
    el: '#loginCont',
    http: {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    },
    data: {
      form: {
        username: '',
        password: '',
        uuid: ''
      },
      socket: null,
      qrcode: '',
      submitting: false,
      nameLogin:"0" //默认扫码登录
    },
    created: function () {
    },
    ready: function () {
      var self = this;
      // 生成随机的串
      var uuid = self.generateUUID();
      self.form.uuid = uuid;
      // 加载二维码
      self.qrcode = ctx + '/wx/qrcode?uuid=' + uuid;
//       self.socket = io.connect('http://oasocket.test.mdni.net.cn'); //測試
//       self.socket = io.connect('http://socketuat.mdni.net.cn'); //UAT
//       self.socket = io.connect('http://socket.mdni.net.cn'); //生产
      // self.socket = io.connect('http://localhost:14082'); //本地开发
      // 将随机串推到服务端，为了服务端向客户端推送消息时，区分推到哪个客户端
      self.socket.emit('req', {
        'uuid': uuid
      });
      // 监听后端推送的消息
      self.socket.on('req', function (data) {
        if ((typeof data) == "string") {
          res = eval("(" + data + ")");
        }
        if (res.code == 1) {
          window.location.href = res.url;
        } else {
          self.$toastr.error(res.message);
        }
      });
    },
    methods: {
      a:function(type){
    	  var self = this;
    	  if(type == "qrcode"){
    		  self.nameLogin = "0";
    	  }else{
    		  self.nameLogin = "1";
    	  }
      },
      submit: function () {
        var self = this;
        var data = {
          username: self.form.username,
          password: self.form.password,
          uuid: self.form.uuid
        };
        self.submitting = true;
        self.$http.post(ctx + '/api/login', $.param(data)).then(function (res) {
          if (res.data.code == 1) {
            window.location.href = RocoUtils.parseQueryString().successUrl || ctx + '/index';
          } else {
            Vue.toastr.error(res.data.message);
          }
        }).catch(function (e) {
          Vue.toastr.error(e);
        }).finally(function () {
          self.submitting = false;
        });
      },
      generateUUID: function () {
        var s = [];
        var hexDigits = "0123456789abcdef";
        for (var i = 0; i < 36; i++) {
          s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
        }
        s[14] = "4"; // bits 12-15 of the time_hi_and_version field to 0010
        s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1); // bits 6-7 of the clock_seq_hi_and_reserved to 01
        s[8] = s[13] = s[18] = s[23] = "-";

        var uuid = s.join("");
        return uuid.replace(/[-]/g, "");
      }
    }
  });
})(RocoUtils, io);