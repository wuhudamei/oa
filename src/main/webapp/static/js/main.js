(function (Vue, $) {
  // 主功能类
  function Main(container) {
    // dom容器
    this.container = container;
    // jquery 容器
    this.$container = $(container);
    // metisMenu对象
    this.$metisMenu = null;
    // 左侧整个侧边栏
    this.$sidebar = null;
    this.$nav = null;
    // 主业务容器
    this.$pagerWrapper = null;
    // resize 定时器
    this.resizeTimer = null;
    // nav 的 transition timer
    this.transitionTimer = null;
    this.init();
    this.bindEvent();
  }

  // 初始化找到对应 dom
  Main.prototype.init = function () {
    this.$metisMenu = $('#sideMenu', this.$container).metisMenu();
    this.$sidebar = $('.sidebar-collapse', this.$container);
    this.$nav = $('#nav', this.$container);
    this.$pagerWrapper = $('#page-wrapper', this.$container);
    this.$toggleMenuMD = $('#toggleMenuMD', this.$container);
    this.$toggleMenuXS = $('#toggleMenuXS', this.$container);
  };

  // 事件绑定
  Main.prototype.bindEvent = function () {
    var self = this;

    // load 和 resize 处理
    $(window).on('load resize', function (e) {

      if (e.type === 'resize' && e.isTrigger == 3) {
        return false;
      }
      clearTimeout(self.resizeTimer);
      self.resizeTimer = setTimeout(function () {
        if ($(window).width() < 500) {
          self.$container.removeClass('mini-navbar');
          self.$container.addClass('no-navbar');
          self.$nav.css('height', 0);
        } else if ($(window).width() < 769) {
          self.$container.addClass('mini-navbar');
          self.$container.removeClass('no-navbar');
          self.$nav.css('height', '100%');
          self.$nav.fadeIn();
        } else {
          self.$container.removeClass('mini-navbar');
          self.$container.removeClass('no-navbar');
          self.$nav.css('height', '100%');
          self.$nav.fadeIn();
        }
        self.$sidebar.perfectScrollbar('update');
      }, 300);
    });

    // 监控 metismenu 展开隐藏
    self.$metisMenu.on('hidden.metisMenu', function (e) {
      self.$sidebar.perfectScrollbar('update');
    });
    self.$metisMenu.on('shown.metisMenu', function (e) {
      self.$sidebar.perfectScrollbar('update');
    });

    self.$toggleMenuMD.on('click', function (e) {
      self.$container.toggleClass('mini-navbar');
    });

    self.$toggleMenuXS.on('click', function (e) {
      var $btn = $(this);
      if (!$btn.clicked) {
        self.$nav.css('height', 0);
      }
      setTimeout(function () {
        if (self.$nav.height() === 0) {
          self.$nav.css({
            'display': 'block',
            'height': 0
          });
          setTimeout(function () {
            self.$nav.animate({
              'height': 400
            }, 300, function () {
              self.$sidebar.perfectScrollbar('update');
            });
          }, 1);
        } else {
          self.$nav.animate({
            'height': 0
          }, 300);
        }
      }, 1);
    });

    // 为侧边栏添加滚动条
    self.$sidebar.perfectScrollbar();
    // 1秒后激活主业务区域内滚动条
    if (Vue.util.isAndroid || Vue.util.isIos || !/windows|win32|win64/.test(navigator.userAgent.toLowerCase())) {
      // 移动端 mac不需要滚动条
      self.$pagerWrapper.css('overflow-y', 'scroll');
    } else {
      setTimeout(function () {
        self.$pagerWrapper.perfectScrollbar({
          suppressScrollX: true
        });
      }, 1000);

    }

  }

  $(function () {
    new Main(document.getElementById('app'));
  });

  $(function () {
    // 用户角标
    var navUser = new Vue({
      el: '#navUser',
      data: {
        user: null
      },
      created: function () {
        this.getUser();
      },
      ready: function () {
      },
      methods: {
        getUser: function () {
          var self = this;
          self.$http.get('/api/login/user').then(function (response) {
            var res = response.data;
            if (res.code == '1') {
              self.user = res.data;
              self.user.headImg = self.user.headImg || '/static/img/default_head.jpg';
            }
          });
          // delete window.RocoUser;
          $('#userScript').remove();
        }
      }
    });
  });

})(Vue, jQuery);


var isWeChat = false;
if(navigator.userAgent.indexOf("MicroMessenger") >= 0) {
	isWeChat = true;
}