(function (window) {
    var RocoUtils = window.RocoUtils = {};

    //获取地址栏指定参数值
    RocoUtils.getQueryString = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null)return unescape(r[2]);
        return null;
    };

    //把querystring 转换为对象
    RocoUtils.parseQueryString = function (queryString) {
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


    /*
     * 设置nav导航条高亮
     * param section string 一级菜单
     * param subSection string 二级菜单
     */
    RocoUtils.setSection = function (section, subSection) {
        var $item = $('#side-menu');
        $item_li = $item.find('[data-section="' + section + '"]');
        $item.find('li:only-child').removeClass('active');
        $item_li.addClass('active');
        if (subSection) {
            $item_li.find('ul').addClass('in');
            $item_li.find('ul->li').removeClass('active');
            $item_li.find('[data-subsection="' + subSection + '"]').addClass('active');
        }
    };

    /*
     * formatNumber(data,type)
     * 功能：金额按千位逗号分割
     * 参数：data，需要格式化的金额或积分.
     * 参数：type,判断格式化后的金额是否需要小数位(如果为true,末尾带两位小数).
     * 返回：返回格式化后的数值字符串.
     */
    RocoUtils.formatNumber = function (data, type) {
        if (/[^0-9\.]/.test(data))
            return "0";
        if (data == null || data == "")
            return "0";
        data = data.toString().replace(/^(\d*)$/, "$1.");
        data = (data + "00").replace(/(\d*\.\d\d)\d*/, "$1");
        data = data.replace(".", ",");
        var re = /(\d)(\d{3},)/;
        while (re.test(data))
            data = data.replace(re, "$1,$2");
        data = data.replace(/,(\d\d)$/, ".$1");
        if (type == 0) {// 不带小数位(默认是有小数位)
            var a = data.split(".");
            if (a[1] == "00") {
                data = a[0];
            }
        }
        return data;
    };

    /*
     * 格式化时间
     * now:事件对象
     * fmt:时间格式 yyyy-MM-dd HH:mm:ss
     */
    RocoUtils.formatDate = function (now, fmt) {
        // $.formatDate(new Date(),'yyyy-MM-dd hh:mm:ss');
        var o = {
            "M+": now.getMonth() + 1, //月份
            "d+": now.getDate(), //日
            "h+": now.getHours(), //小时
            "m+": now.getMinutes(), //分
            "s+": now.getSeconds(), //秒
            "q+": Math.floor((now.getMonth() + 3) / 3), //季度
            "S": now.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (now.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt))
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };


    /**
     * 判断val是否再数组中,不再返回-1,存在则返回其下标
     * @param val
     * @returns {number}
     */
    Array.prototype.indexOf = function (val) {
        for (var i = 0; i < this.length; i++) {
            if (this[i] == val) return i;
        }
        return -1;
    };

    /**
     * 从数组中移除val元素
     * @param val
     */
    Array.prototype.remove = function (val) {
        var index = this.indexOf(val);
        if (index > -1) {
            this.splice(index, 1);
        }
    };


    /**
     * 判断obj是否是数字类型
     * @param obj
     * @returns {boolean}
     */
    RocoUtils.isNumber = function (obj) {
        return typeof obj === 'number' && !isNaN(obj)
    }

    /**
     * 把obj 转为百分数，如果 传flag：true则带百分号,否则不带
     */
    RocoUtils.decToper = function (obj, flag) {
        flag = flag || false;
        if (typeof obj === 'number') {
            return new Decimal(obj).times(100).toNumber() + (flag ? '%' : 0);
        }
        else if (typeof obj === 'string') {
            try {
                var _n = math.eval(obj);
                return new Decimal(_n).times(100).toNumber() + (flag ? '%' : 0);
            }
            catch (e) {
                throw new Error(obj + "不是数字类型的");
            }
        }
    }

    // 根据是否带着百分号进行判断是否需要 乘以 0.01；如果是数字类型，f 参数为true则乘以0.01,否则直接返回
    RocoUtils.perToDec = function (obj, f) {
        if (typeof obj === 'number') {
            return f ? new Decimal(obj).times(0.01).toNumber() : obj;
        }
        else if (typeof obj === 'string') {
            try {
                if (obj.indexOf("%") > 0) {
                    var _n = math.eval(obj.replace("%", ""));
                    return new Decimal(_n).times(0.01).toNumber();
                }
                return math.eval(obj);

            }
            catch (e) {
                throw new Error(obj + "不是数字类型的");
            }
        }
    }
    // 加法
    RocoUtils.accAdd = function (arg1, arg2) {
        var r1, r2, m, c;
        try {
            r1 = arg1.toString().split(".")[1].length;
        }
        catch (e) {
            r1 = 0;
        }
        try {
            r2 = arg2.toString().split(".")[1].length;
        }
        catch (e) {
            r2 = 0;
        }
        c = Math.abs(r1 - r2);
        m = Math.pow(10, Math.max(r1, r2));
        if (c > 0) {
            var cm = Math.pow(10, c);
            if (r1 > r2) {
                arg1 = Number(arg1.toString().replace(".", ""));
                arg2 = Number(arg2.toString().replace(".", "")) * cm;
            } else {
                arg1 = Number(arg1.toString().replace(".", "")) * cm;
                arg2 = Number(arg2.toString().replace(".", ""));
            }
        } else {
            arg1 = Number(arg1.toString().replace(".", ""));
            arg2 = Number(arg2.toString().replace(".", ""));
        }
        return (arg1 + arg2) / m;
    };

    //将数字转化为大写金额,flag为true,不带圆角分，false带圆角分
    RocoUtils.moneyToUpper = function (money, flag) {
        var cnNums = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"); //汉字的数字
        var cnIntRadice = new Array("", "拾", "佰", "仟"); //基本单位
        var cnIntUnits = new Array("", "万", "亿", "兆"); //对应整数部分扩展单位
        var cnDecUnits = new Array("角", "分", "毫", "厘"); //对应小数部分单位
        var cnInteger = "整"; //整数金额时后面跟的字符
        var cnIntLast = "元"; //整型完以后的单位
        var cnPoint = "点";
        var maxNum = 999999999999999.9999; //最大处理的数字

        var IntegerNum; //金额整数部分
        var DecimalNum; //金额小数部分
        var ChineseStr = ""; //输出的中文金额字符串
        var parts; //分离金额后用的数组，预定义

        if (money == "") {
            return "";
        }

        money = parseFloat(money);
        //alert(money);
        if (money >= maxNum) {
            $.alert('超出最大处理数字');
            return "";
        }
        if (money == 0) {
            flag ? ChineseStr = (cnNums[0] + cnInteger) : ChineseStr = cnNums[0] + cnIntLast + cnInteger;
            // ChineseStr = cnNums[0]+cnIntLast+cnInteger;
            return ChineseStr;
        }
        money = money.toString(); //转换为字符串
        if (money.indexOf(".") == -1) {
            IntegerNum = money;
            DecimalNum = '';
        } else {
            parts = money.split(".");
            IntegerNum = parts[0];
            DecimalNum = parts[1].substr(0, 4);
        }
        if (parseInt(IntegerNum, 10) > 0) {//获取整型部分转换
            zeroCount = 0;
            IntLen = IntegerNum.length;
            for (i = 0; i < IntLen; i++) {
                n = IntegerNum.substr(i, 1);
                p = IntLen - i - 1;
                q = p / 4;
                m = p % 4;
                if (n == "0") {
                    zeroCount++;
                } else {
                    if (zeroCount > 0) {
                        ChineseStr += cnNums[0];
                    }
                    zeroCount = 0; //归零
                    ChineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
                }
                if (m == 0 && zeroCount < 4) {
                    ChineseStr += cnIntUnits[q];
                }
            }
            flag ? ChineseStr : ChineseStr += cnIntLast;
            // ChineseStr += cnIntLast;
            //整型部分处理完毕
        }
        if (DecimalNum != '') {//小数部分
            decLen = DecimalNum.length;
            flag ? ChineseStr += cnPoint : ChineseStr;
            for (i = 0; i < decLen; i++) {
                n = DecimalNum.substr(i, 1);
                if (n != '0') {
                    flag ? ChineseStr += cnNums[Number(n)] : ChineseStr += cnNums[Number(n)] + cnDecUnits[i];
                    // ChineseStr += cnNums[Number(n)]+cnDecUnits[i];
                }
            }
        }
        if (ChineseStr == '') {
            flag ? ChineseStr += cnNums[0] : ChineseStr += cnNums[0] + cnIntLast + cnInteger;
            // ChineseStr += cnNums[0]+cnIntLast+cnInteger;
        }
        else if (DecimalNum == '') {
            ChineseStr += cnInteger;
        }
        return ChineseStr;
    };


    /**
     * 加载搜索时间控件,只输入一个参数表示加载输入参数的时间控件,
     * 输入两个参数表示加载开始日期和结束日期的时间空间
     * 参数为JQuery选择器的值 如 '#startDate'
     * @param start 开始时间
     * @param end 结束时间
     * @param pattern 时间格式 默认为 YYYY-MM-DD
     */
    RocoUtils.initDateControl = function (start, end, format) {
        if (!format)
            format = 'yyyy-mm-dd';
        start.datetimepicker({format: format});
        end.datetimepicker({format: format});
        start.on('dp.change', function (e) {
            end.data("DateTimePicker").minDate(e.date);
            end.data("DateTimePicker").startDate(e.date);
        });
        end.on('dp.change', function (e) {
            start.data("DateTimePicker").maxDate(e.data);
        });
    };

    /**
     * 判断传入的值为空 undefined 或 null 或 ''
     * @param val
     */
    RocoUtils.isEmpty = function (val) {

        return (val == null || typeof(val) == 'undefined' || String(val) == '');
    };

    /**
     * 指定时间后跳转到指定地址
     * @param url 跳转地址
     * @param time 延时时间,不指定直接跳转
     */
    RocoUtils.redirect2Url = function (url, time) {
        if (RocoUtils.isEmpty(time)) {
            window.location.href = url;
        } else {
            setTimeout(function () {
                window.location.href = url
            }, time);
        }
    };

    /**
     * 判断传入的值不为空  不是 undefined 或 null 或 ''
     * @param val
     */
    RocoUtils.isNotEmpty = function (val) {
        return !RocoUtils.isEmpty(val);
    };


    /**
     * 权限判断类
     */
    var WildcardPermission = (function () {

        // WildcardPermission类，来自 org.apache.shiro.authz.permission.WildcardPermission
        var WILDCARD_TOKEN = "*";
        var PART_DIVIDER_TOKEN = ":";
        var SUBPART_DIVIDER_TOKEN = ",";
        var DEFAULT_CASE_SENSITIVE = false;

        function WildcardPermission(wildcardString, caseSensitive) {

            if (typeof(caseSensitive) == 'undefined') {
                caseSensitive = DEFAULT_CASE_SENSITIVE;
            }

            this.setParts(wildcardString, caseSensitive);
        }

        WildcardPermission.prototype = {

            setParts: function (wildcardString, caseSensitive) {
                if (!wildcardString || !wildcardString.length) {
                    throw new Error("Wildcard string cannot be null or empty. Make sure permission strings are properly formatted.");
                }

                wildcardString = $.trim(wildcardString);

                var parts = wildcardString.split(PART_DIVIDER_TOKEN);

                this.parts = [];

                for (var i = 0; i < parts.length; i++) {
                    var part = parts[i];
                    var subparts = part.split(SUBPART_DIVIDER_TOKEN);
                    if (!caseSensitive) {
                        subparts = this.lowercase(subparts);
                    }
                    if (subparts.length == 0) {
                        throw new Error("Wildcard string cannot contain parts with only dividers. Make sure permission strings are properly formatted.");
                    }
                    this.parts.push(subparts);
                }

                if (this.parts.length == 0) {
                    throw new Error("Wildcard string cannot contain only dividers. Make sure permission strings are properly formatted.");
                }
            },

            lowercase: function (subparts) {
                var lowerCasedSubparts = [];
                for (var i = 0; i < subparts.length; i++) {
                    lowerCasedSubparts.push(subparts[i].toLowerCase());
                }
                return lowerCasedSubparts;
            },

            getParts: function () {
                return this.parts;
            },

            implies: function (wp) {

                if (typeof(wp) == 'string') {
                    wp = new WildcardPermission(wp);
                }

                var otherParts = wp.getParts();

                var i = 0;
                for (var index = 0; index < otherParts.length; index++) {
                    var otherPart = otherParts[index];
                    // If this permission has less parts than the other permission, everything after the number of parts contained
                    // in this permission is automatically implied, so return true
                    if (this.getParts().length - 1 < i) {
                        return true;
                    } else {
                        var part = this.getParts()[i];
                        if (part.indexOf(WILDCARD_TOKEN) == -1 && !this.containsAll(part, otherPart)) {
                            return false;
                        }
                        i++;
                    }
                }

                // If this permission has more parts than the other parts, only imply it if all of the other parts are wildcards
                for (; i < this.getParts().length; i++) {
                    var part = this.getParts()[i];
                    if (part.indexOf(WILDCARD_TOKEN) == -1) {
                        return false;
                    }
                }

                return true;
            },

            //判断a是否包含b中所有项
            containsAll: function (a, b) {
                for (var i = 0; i < b.length; i++) {
                    if (a.indexOf(b[i]) == -1) {
                        return false;
                    }
                }
                return true;
            },

            toString: function () {
                var buffer = [];
                for (var i = 0; i < this.parts.length; i++) {
                    var part = this.parts[i];
                    if (buffer.length > 0) {
                        buffer.push(":");
                    }
                    buffer.push(part);
                }
                return buffer.join('');
            }
        };

        return WildcardPermission;
    })();

    /**
     * 将形如 [admin:users:add,admin:user:edit,admin:role:del]的权限字符串转化为
     * 权限对象WildcardPermission数组[obj1,obj2,obj3,obj4]
     * @param permissionsStr 权限字符串
     */
    RocoUtils.permissionsFormat = function (permissionsStr) {
        var wildcardPermissions = [];
        if (RocoUtils.isNotEmpty(permissionsStr) && permissionsStr != '[]' && typeof(permissionsStr ) == 'string') {
            var permissions = permissionsStr.substring(1, permissionsStr.length - 1).split(",");
            if (permissions.length > 0) {
                for (var i = 0; i < permissions.length; i++) {
                    wildcardPermissions.push(new WildcardPermission(permissions[i]));
                }
            }
        }

        return wildcardPermissions;
    };
    /**
     * 判断用户是否具有指定权限
     * @param otherPermission 权限字符串
     * @returns {boolean} 是否需要转化为小写
     */
    RocoUtils.hasPermission = function (otherPermission) {
        if(RocoUtils.isEmpty(otherPermission)){
            return true;
        }
        var permissions = window.RocoUser.permissions;
        if (RocoUtils.isNotEmpty(permissions) && permissions.length > 0) {
            for (var i = 0; i < permissions.length; i++) {
                if (permissions[i].implies(otherPermission)) {
                    return true;
                }
            }
        }
        return false;
    };

    /**
     * 判断用户是否具有指定任意一个权限
     * @param permissions 权限数组
     * @returns {boolean} 是否小写
     */
    RocoUtils.hasAnyPermission = function (permissions) {
        for (var i = 0; i < permissions.length; i++) {
            if (hasPermission(permissions[i])) {
                return true;
            }
        }
        return false;
    };

    // RocoUtils.hasPermission = function (permission) {
    //     var permissions = window.RocoUser.permissions;
    //     return (permissions.indexOf('*') > -1 || permissions.indexOf(permission) > -1) ? true : false;
    // };

    /**
     * 判断登录用户是否具有指定角色
     * @param role 指定角色名
     * @returns {boolean}
     */
    RocoUtils.hasRole = function (role) {
        var roles = window.RocoUser.roles;
        return roles.indexOf(role) > -1 ? true : false;
    };

    /**
     * 判断传入的用户id是否是当前登录用户
     * @param id
     * @returns {boolean}
     */
    RocoUtils.isLoginUser = function (id) {
        var loginId = window.RocoUser.userId;
        return id == loginId ? true : false;
    };

})(window);