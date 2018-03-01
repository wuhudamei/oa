var MaterialCostComponent = Vue.extend({
    template: '#materialCostTmpl',
    data: function () {
        return {
            orderNo: null,
            loaded: false,
            total1: 0,
            total2: 0,
            // 主材
            mainMaterialRows: null,
            //辅材
            materialDataRows: null,
            // 接口名字
            interfaceName: ['principalMaterial', 'auxiliaryMaterial'],
            // 主材表数据
            mainMaterialData: [{
                field: 'principalMaterialflagName',
                title: '商品类目',
                align: 'center'
            }, {
                field: 'pdBrandName',
                title: '品牌',
                align: 'center'
            }, {
                field: 'pdModel',
                title: '型号',
                align: 'center'
            }, {
                field: 'pdSkuSalesattribute',
                title: '属性',
                align: 'center'
            }, {
                field: 'consumptiondosage',
                title: '用量',
                align: 'center'
            }, {
                field: 'smStoreprice',
                title: '门店采购价',
                align: 'center'
            }]
            //辅材  暂时注销（不要删除）
            // materialData: [{
            //     field: 'pdSupplier',
            //     title: '商品供应商',
            //     align: 'center'
            // }, {
            //     field: 'auxiliaryMaterialflagName',
            //     title: '商品类目',
            //     align: 'center'
            // }, {
            //     field: 'pdSkuname',
            //     title: '商品名称',
            //     algin: 'center'
            // }, {
            //     field: 'pdModel',
            //     title: '型号',
            //     align: 'center'
            // }, {
            //     field: 'dosages',
            //     title: '用量',
            //     align: 'center'
            // }, {
            //     field: 'pdPrice',
            //     title: '价格',
            //     align: 'center'
            // }]
        }
    },
    ready: function () {

    },
    created: function () {
        this.orderNo = RocoUtils.parseQueryString()['orderno'];
    },
    methods: {
        // 获取数据, interfaceName接口名字，colums定义表数据的数组, key接收数据的对象
        fetchData: function (el, interfaceName, columns, key, total) {
            var self = this;
            this.$http.get('/api/materialCostStandBook/' + interfaceName, {
                params: {
                    orderno: self.orderNo
                }
            }).then(function (res) {
                if(this.msg == 13){
                    if (res.data.code == 1) {
                        self.$set(key, res.data.data.rows);
                        if (total == 'total1') {
                            self.total1 = res.data.data.total;
                        } else if (total == 'total2') {
                            self.total2 = res.data.data.total;
                        }
                        self.drawTable(el, res.data.data, columns);
                    }
                    self.loaded = true;
                }
            })
        },
        // 画table, colums定义表数据的数组
        drawTable: function (el, data, columns) {
            $(el).bootstrapTable({
                data: data.rows,
                mobileResponsive: true,
                undefinedText: '-', //空数据的默认显示字符
                striped: true, //斑马线
                maintainSelected: false, //维护checkbox选项
                columns: columns
            });
        },
        // 计算总金额，求和，rows是数据对象，key是rows的用来累加的数据
        getSum: function (rows, key) {
            if (rows) {
                var sum = 0;
                for (var i = 0, len = rows.length; i < len; i++) {
                    sum += rows[i][key];
                }
                return sum.toFixed(2);
            } else {
                return '';
            }
        },
        getMultiplication: function (rows, key, key2) {
            if (rows) {
                var sum = 0;
                for (var i = 0, len = rows.length; i < len; i++) {
                    if (!isNaN(rows[i][key]) && !isNaN(rows[i][key2])) {
                        sum += rows[i][key] * rows[i][key2];
                    }
                }
                return sum.toFixed(2);
            } else {
                return '';
            }
        }
    },
    computed: {
        //主材合计
        mainMaterialTotalAmount: function () {
            return this.getMultiplication(this.mainMaterialRows, 'smStoreprice', 'consumptiondosage');
        },
        //辅材合计 暂时注销（不要删除）
        // toMaterialDataRows: function () {
        //     return this.getMultiplication(this.materialDataRows, 'pdPrice', 'count');
        // }
    },
    props: ['msg'],
    watch: {
        msg: function (curVal, oldVal) {
            if (curVal == 13 && !this.loaded) {
                this.fetchData(this.$els.dataTableMainMaterial, this.interfaceName[0], this.mainMaterialData, 'mainMaterialRows', 'total1');
                //this.fetchData(this.$els.dataTableMaterial, this.interfaceName[1], this.materialData, 'materialDataRows', 'total2');
            }
        }

    }
});