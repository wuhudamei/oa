var vueIndex = null;
+(function (RocoUtils) {
    $('#PurchaseMenu').addClass('active');
    $('#purchaseApply').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        // 模式窗体必须引用 ModalMixin
        mixins: [RocoVueMixins.DataTableMixin],
        data: {
            types: {
                'MATTERS': {'id': 1, 'name': "人事类"},
                'MARKETING': {'id': 2, 'name': "营销类"},
                'ADMINISTRATOR': {'id': 3, 'name': "行政类"},
                'FINANCE': {'id': 4, 'name': "财务类"},
                'CUSTOMER': {'id': 5, 'name': "客管类"},
                'OTHERS': {'id': 6, 'name': "其他类"}
            },
            typeName: {
                '1': {'id': "MATTERS", 'name': "人事类"},
                '2': {'id': "MARKETING", 'name': "营销类"},
                '3': {'id': "ADMINISTRATOR", 'name': "行政类"},
                '4': {'id': "FINANCE", 'name': "财务类"},
                '5': {'id': "CUSTOMER", 'name': "客管类"},
                '6': {'id': "OTHERS", 'name': "其他类"}
            },            
            // 控制 按钮是否可点击
            disabled: false,
            // 模型复制给对应的key
            purchase: {
                id: '',
                firstTypeId: '',
                secondTypeId: '',
                goodName: '',
                purchaseMonth: '',
                goodNum: '',
                goodPrice: '',
                description: '',
                totalPrice: '',
                type:''
            },
            parentType:'',
            // 进项列表
            submitBtnClick: false,
            isWeChat:isWeChat,
            parents: null,
            children: null,
            params: {
                parentType: ''
            }
        },
        created: function () {
        },
        ready: function () {
            this.initParams();
            // this.fetchChildren();
            this.activeDatepicker();
            this.editPurchase();
        },
        watch: {
            // 'purchase.firstTypeId': function () {
            // var self = this;
            // self.params.parentType = self.purchase.firstTypeId;
            // // self.purchase.secondTypeId = '';
            // self.fetchChildren();
            // deep:true;
            // },
            'purchase.goodPrice': function (val) {
                var self = this;
                self.purchase.totalPrice = (val * self.purchase.goodNum).toFixed(2);
                deep:true;
            },
            'purchase.goodNum': function (val) {
                var self = this;
                self.purchase.totalPrice = (val * self.purchase.goodPrice).toFixed(2);
                deep:true;
            }
        },
        methods: {
	  	    cancel:function(){
	 	    	window.location.href = '/admin/purchase/list';
	 	    },
            initParams: function () {
                var self = this;
                var params = RocoUtils.parseQueryString(window.location.search.substr(1));
                var type = params['type'];
                var id = params['id'];
                var firstTypeId = params['firstTypeId'];
                if (type) {
                    self.addActiveClass(type);
                    self.purchase.firstTypeId = self.types[type].id;
                    self.purchase.type = type;
                    self.params.parentType = self.types[type].id;
                } else if(firstTypeId) {
                    this.params.parentType=firstTypeId;
                    self.purchase.type = self.typeName[firstTypeId].id;
                }else {
                    // 错误提示
                }
                self.fetchChildren();
                console.log(self.purchase.type);
            },
            addActiveClass: function (type) {
                $('#purchaseMenu').addClass('active');
                switch (type) {
                    case 'MATTERS':
                        $('#mattersPurchaseApply').addClass('active');
                        break;
                    case 'MARKETING':
                        $('#marketingPurchaseApply').addClass('active');
                        break;
                    case 'ADMINISTRATOR':
                        $('#adminPurchaseApply').addClass('active');
                        break;
                    case 'FINANCE':
                        $('#financePurchaseApply').addClass('active');
                        break;
                    case 'CUSTOMER':
                        $('#customerPurchaseApply').addClass('active');
                        break;
                    case 'OTHERS':
                        $('#othersPurchaseApply').addClass('active');
                        break;
                    default:
                        $('#purchaseAffair').addClass('active');
                        break;

                }
            },
            fetchParent: function () {
                var self = this;
                this.$http.get('/api/dict/dic/getNode/2').then(function (res) {
                    if (res.data.code == 1) {
                        self.parents = res.data.data;
                    }
                }).catch(function () {

                }).finally(function () {

                });
            },
            fetchChildren: function () {
                var self = this;

                this.$http.post('/api/dict/dic/getByType/', self.params, {emulateJSON: true}).then(function (res) {
                    if (res.data.code == 1) {
                        self.children = res.data.data;
                    }
                }).catch(function () {

                }).finally(function () {

                });
            },
            activeDatepicker: function () {
                $(this.$els.purchaseMonth).datetimepicker({
                    minView: 3,
                    startView: 3,
                    format: 'yyyy-mm',
                    todayBtn: true
                });
            },
            submit: function () {
                var self = this;
                self.submitBtnClick = true;
                self.$validate(true, function () {
                    if (self.$validation.valid) {
                        self.disabled = true;
                        self.$http.post(ctx + '/api/apply/applyPurchase', self.purchase,{emulateJSON: true}).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('提交成功');
                                setTimeout(function () {
                                    window.location.href = '/admin/purchase/list';
                                }, 1500);
                            }else{
                            	self.$toastr.error(res.data.message);
                            	self.disabled = false;
                            }
                        }).finally(function () {
                        });
                    }
                });
            },
            save: function () {
                var self = this;
                self.submitBtnClick = true;
                self.$validate(true, function () {
                    if (self.$validation.valid) {
                        self.disabled = true;

                        self.$http.post(ctx + '/api/apply/applyPurchase/saveDraft', self.purchase,{emulateJSON: true}).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('保存成功');
                                setTimeout(function () {
                                    window.location.href = '/admin/purchase/list';
                                }, 1500);
                            }else{
                            	 self.disabled = false;
                            }
                        }).finally(function () {
                        });
                    }
                });
            },
            editPurchase:function () {
                var self=this;
                var id = this.$parseQueryString()['id'];
                if(id != null && id != undefined){
                        self.$http.get('/api/apply/applyPurchase/' + id).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            self.purchase = res.data;
                            self.purchase.type = self.typeName[res.data.firstTypeId].id;
                        }
                    });
                }
            }
        }
    });

})(this.RocoUtils);

