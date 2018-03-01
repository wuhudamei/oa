var vueIndex = null;
+(function (RocoUtils) {
    $('#designerMenu').addClass('active');
    $('#ruleSetup').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '规则管理',
                active: true //激活面包屑的
            }],
            submitBtnClick: false,
            disabled: false,
            //等级规则
            levels: [],
            //间接费用比例
            indirections: [],
            //状态发放比例
            grants: []
        },
        methods: {
            //初始化规则参数
            initRules: function () {
                var self = this;
                self.$http.get('/api/rules').then(function (response) {
                    var result = response.data;
                    if (result.code == 1) {
                        var data = result.data;
                        self.levels = data.level;
                        self.indirections = data.indirect;
                        self.grants = data.grant;
                    }
                });
            },
            submit: function () {
                var self = this;
                var rules = [];

                var levelTotalRatio = 0.0;
                var levelError = false;
                self.levels.forEach(function (level) {
                    if (level == null || level.ratio1 == '' || level.ratio1 == null || level.ratio2 == '' || level.ratio2 == null) {
                        levelError = true;
                    }
                    rules.push(level);
                    levelTotalRatio += (typeof level.ratio1 == 'string' ? parseFloat(level.ratio1) : level.ratio1);
                });

                if (levelError) {
                    Vue.toastr.warning('排名比例不能为空！');
                    return false;
                }

                if (levelTotalRatio != 100) {
                    Vue.toastr.warning('排名比例总和应为100%');
                    return false;
                }

                var indirectionError = false;
                self.indirections.forEach(function (indirection) {
                    if (indirection == null || indirection.ratio1 == '' || indirection.ratio1 == null) {
                        indirectionError = true;
                    }
                    rules.push(indirection);
                });

                if (indirectionError) {
                    Vue.toastr.warning('间接费用比例不能为空！');
                    return false;
                }


                var grantTotalRatio = 0.0;
                var grantError = false;
                self.grants.forEach(function (grant) {
                    if (grant == null || grant.ratio1 == '' || grant.ratio1 == null) {
                        grantError = true;
                    }
                    rules.push(grant);
                    grantTotalRatio += (typeof grant.ratio1 == 'string' ? parseFloat(grant.ratio1) : grant.ratio1);

                });

                if (grantError) {
                    Vue.toastr.warning('发放比例不能为空！');
                    return false;
                }

                if (grantTotalRatio != 100) {
                    Vue.toastr.warning('发放比例总和应为100%');
                    return false;
                }

                self.submitBtnClick = true;
                // self.$validate(true, function () {
                //     if (self.$validation.valid) {
                self.disabled = true;
                self.$http.post('/api/rules', rules).then(function (res) {
                    if (res.data.code == 1) {
                        self.$toastr.success(res.data.message);
                    }
                }).finally(function () {
                    self.disabled = false;
                });
                //     }
                // });
            }
        },
        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.initRules();
        }
    });
})
(this.RocoUtils);