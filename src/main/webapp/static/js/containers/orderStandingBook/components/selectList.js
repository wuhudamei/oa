var SelectionListComponent = Vue.extend({
    template: '#selectListTmpl',
    data: function () {
        return {
            orderNo: null,
            loaded: false,
            occurrenceTime: '',
            total1: 0,
            total2: 0,
            total3: 0,
            total4: 0,
            total5: 0,
            total6: 0,
            total7: 0,
            total8: 0,
            total9: 0,
            // 套餐标配
            standardDataRows: {
                measurehousearea: null,
                unitprice: null,
            },
            // 升级项
            updateDataRows: null,
            // 增项
            addDataRows: null,
            // 减项
            subDataRows: null,
            // 基装定额
            baseNormDataRows: null,
            reduceItemDataRows: null,
            // 活动、优惠及其它金额增减
            preferentialAmountDataRows: null,
            // 其他综合费
            otherDataRows: null,
            // 辅材
            materialDataRows: null,

            //接口名字
            interfaceName: ['comboStandard', 'upgradeItem', 'addItem', 'reduceItem', 'basicInstallPrice', 'reduceItemInstallPrice', 'otherPrice', 'otherSynthesizePrice'],
            // 套餐标配表数据
            standardData: [{
                field: 'flagName',
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
                field: 'doMain',
                title: '位置',
                align: 'center'
            }, {
                field: 'dosage',
                title: '用量',
                align: 'center',
                formatter:function (value, row, index) {
                    if(row.unitconversion != null){
                        return value+"/"+row.squareCount+"㎡";
                    }else {
                        return value
                    }
                }
            }],
            // 升级项表数据
            updateData: [{
                field: 'flagName',
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
                field: 'doMain',
                title: '位置',
                align: 'center'
            }, {
                field: 'dosage',
                title: '用量',
                align: 'center',
                formatter:function (value, row, index) {
                    if(row.unitconversion != null){
                        return value+"/"+row.squareCount+"㎡";
                    }else {
                        return value
                    }
                }

            }, {
                field: 'itemPrice',
                title: '升级差价',
                align: 'center'
            }],
            // 增项表数据
            addData: [{
                field: 'flagName',
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
                field: 'doMain',
                title: '位置',
                align: 'center'
            }, {
                field: 'dosage',
                title: '用量',
                align: 'center',
                formatter:function (value, row, index) {
                    if(row.unitconversion != null){
                        return value+"/"+row.squareCount+"㎡";
                    }else {
                        return value
                    }
                }
            }, {
                field: 'itemPrice',
                title: '增项单价',
                align: 'center'
            }],
            // 减项表数据
            subData: [{
                field: 'flagName',
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
                field: 'doMain',
                title: '位置',
                align: 'center'
            }, {
                field: 'dosage',
                title: '用量',
                align: 'center',
                formatter:function (value, row, index) {
                    if(row.unitconversion != null){
                        return value+"/"+row.squareCount+"㎡";
                    }else {
                        return value
                    }
                }
            }, {
                field: 'itemPrice',
                title: '减项单价',
                align: 'center'
            }],
            // 增项基装定额表数据
            baseNormData: [{
                field: 'pdCategoryName',
                title: '定额分类',
                align: 'center'
            }, {
                field: 'pdName',
                title: '定额名称',
                align: 'center'
            }, {
                field: 'consumptiondosage',
                title: '数量',
                align: 'center'
            }, {
                field: 'pdMeasureunit',
                title: '单位',
                align: 'center'
            }, {
                field: 'itemPrice',
                title: '单价或占比',
                align: 'center'
            }, {
                field: 'itemTotal',
                title: '合价',
                align: 'center'
            }, {
                field: 'remarkContent',
                title: '设计备注',
                align: 'center'
            }],
            // 减项基装定额表数据
            reduceItemData: [{
                field: 'pdCategoryName',
                title: '定额分类',
                align: 'center'
            }, {
                field: 'pdName',
                title: '定额名称',
                align: 'center'
            }, {
                field: 'consumptiondosage',
                title: '数量',
                align: 'center'
            }, {
                field: 'pdMeasureunit',
                title: '单位',
                align: 'center'
            }, {
                field: 'itemPrice',
                title: '单价或占比',
                align: 'center'
            }, {
                field: 'itemTotal',
                title: '合价',
                align: 'center'
            }],
            // 活动、优惠及其它金额增减表数据
            preferentialAmountData: [{
                field: 'name',
                title: '名称',
                align: 'center'
            }, {
                field: 'itemType',
                title: '类型',
                align: 'center',
                formatter: function (value, row, index) {
                    switch (value) {
                        case 1:
                            return '加项';
                            break;
                        case 2:
                            return '减项';
                            break;
                        default:
                            return '';
                            break;
                    }
                }
            }, {
                field: 'tax',
                title: '是否税后减项',
                align: 'center',
                formatter: function (value, row, index) {
                    switch (value) {
                        case 0:
                            return '否';
                            break;
                        case 1:
                            return '是';
                            break;
                        default:
                            return '';
                            break;
                    }
                }
            }, {
                field: 'account',
                title: '额度',
                align: 'center'
            }, {
                field: 'submitUserName',
                title: '批准人',
                align: 'center'
            }, {
                field: 'info',
                title: '介绍',
                align: 'center'
            }],
            // 其他综合费表数据
            otherData: [{
                field: 'pdCategoryName',
                title: '定额分类',
                align: 'center'
            }, {
                field: 'pdName',
                title: '定额名称',
                align: 'center'
            }, {
                field: 'consumptiondosage',
                title: '数量',
                align: 'center'
            }, {
                field: 'pdMeasureunit',
                title: '计价方式',
                align: 'center'
            }, {
                field: 'itemPrice',
                title: '单价或占比',
                align: 'center',
                // formatter: function (value, row, index) {
                //     if (value !== null) {
                //         return value + '%';
                //     } else {
                //         return row.itemPrice;
                //     }
                // }
            }, {
                field: 'itemTotal',
                title: '合价',
                align: 'center'
            }, {
                field: 'remarkContent',
                title: '设计备注',
                align: 'center'
            }]
        }
    },
    ready: function () {
    },
    created: function () {
        this.orderNo = RocoUtils.parseQueryString()['orderno'];
    },
    filters: {
        goDate: function (el) {
            if (el == "315504000000") {
                return '-';
            } else if (el) {
                return moment(el).format('YYYY-MM-DD');
            } else {
                return '-';
            }
        }
    },
    methods: {
        fetchContractAudit: function () {
            var self = this;
            self.$http.get('/api/selectList/occurrenceTime?orderno=' + this.orderNo).then(function (res) {
                if (this.msg == 1) {
                    if (res.data.code == 1) {
                        self.occurrenceTime = res.data.data;
                    }
                    self.loaded = true;
                }
            })
        },
        // 获取数据, interfaceName接口名字，colums定义表数据的数组, key接收数据的对象
        fetchDataMeterail: function (el, interfaceName, columns, key, total) {
            var self = this;
            self.$http.get('/api/selectList/' + interfaceName, {
                params: {
                    orderno: self.orderNo
                }
            }).then(function (res) {
                if (this.msg == 1) {
                    if (res.data.code == 1) {
                        self.$set(key, res.data.data.rows);
                        if (total == 'total1') {
                            self.total1 = res.data.data.total;
                        } else if (total == 'total2') {
                            self.total2 = res.data.data.total;
                        }
                        else if (total == 'total3') {
                            self.total3 = res.data.data.total;
                        }
                        else if (total == 'total4') {
                            self.total4 = res.data.data.total;
                        }
                        else if (total == 'total5') {
                            self.total5 = res.data.data.total;
                        }
                        else if (total == 'total6') {
                            self.total6 = res.data.data.total;
                        }
                        else if (total == 'total7') {
                            self.total7 = res.data.data.total;
                        }
                        else if (total == 'total8') {
                            self.total8 = res.data.data.total;
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
                    sum += rows[i][key]
                }
                return sum.toFixed(2);
            } else {
                return ''
            }
        },

        getMultiplication: function (rows, key, key2) {
            if (rows) {
                var sum = 0;
                for (var i = 0, len = rows.length; i < len; i++) {
                    sum += rows[i][key] * rows[i][key2]
                }
                return sum.toFixed(2);
            } else {
                return ''
            }
        }
    },
    props: ['msg'],
    watch: {
        msg: function (curVal, oldVal) {
            if (curVal == 1 && !this.loaded) {
                this.fetchContractAudit();
                this.fetchDataMeterail(this.$els.dataTableStandard, this.interfaceName[0], this.standardData, 'standardDataRows', 'total1');
                this.fetchDataMeterail(this.$els.dataTableUpdate, this.interfaceName[1], this.updateData, 'updateDataRows', 'total2');
                this.fetchDataMeterail(this.$els.dataTableAdd, this.interfaceName[2], this.addData, 'addDataRows', 'total3');
                this.fetchDataMeterail(this.$els.dataTableSub, this.interfaceName[3], this.subData, 'subDataRows', 'total4');
                this.fetchDataMeterail(this.$els.dataTableBaseNorm, this.interfaceName[4], this.baseNormData, 'baseNormDataRows', 'total5');
                this.fetchDataMeterail(this.$els.dataTableReduceItem, this.interfaceName[5], this.reduceItemData, 'reduceItemDataRows', 'total6');
                this.fetchDataMeterail(this.$els.dataTablePreferentialAmount, this.interfaceName[6], this.preferentialAmountData, 'preferentialAmountDataRows', 'total7');
                this.fetchDataMeterail(this.$els.dataTableOther, this.interfaceName[7], this.otherData, 'otherDataRows', 'total8');
            }
        }
    },
    computed: {
        //其他综合费
        toOtherDataRows: function () {
            return this.getSum(this.otherDataRows, 'itemTotal');
        },
        //辅材合计
        toMaterialDataRows: function () {
            return this.getMultiplication(this.materialDataRows, 'pdPrice', 'count');
        },
        totalStandardAmount: function () {
            if (this.standardDataRows) {
                var rows = this.standardDataRows;
                var sum = 0;
                // 求和：使用面积乘以单价
                sum = rows[0].measurehousearea * rows[0].unitprice;
                return sum.toFixed(2);
            } else {
                return ''
            }
        },
        //升级项
        totalUpdateAmount: function () {
            return this.getMultiplication(this.updateDataRows, 'itemPrice', 'consumptiondosage');
        },
        totalAddAmount: function () {
            return this.getMultiplication(this.addDataRows, 'itemPrice', 'consumptiondosage');
        },
        totalSubAmount: function () {
            var sum = this.getMultiplication(this.subDataRows, 'itemPrice', 'consumptiondosage');
            return sum > 0 ? '-' + sum : sum
        },
        //增项基装定额
        totalBaseNormAmount: function () {
            return this.getSum(this.baseNormDataRows, 'itemTotal');
        },

        //减项基装定额
        totalreduceItemDataAmount: function () {
            return this.getSum(this.reduceItemDataRows, 'itemTotal');
            return sum > 0 ? '-' + sum : sum
        },
        preferentialAmount: function () {
            var sum = this.preferentialAmountDataRows;
            var price = 0;
            for (var i = 0; i < sum.length; i++) {
                if (sum[i].itemType == '2') {
                    sum[i].account = sum[i].account - sum[i].account * 2
                }
                price = price += sum[i].account;
            }
            return price.toFixed(2);
        }
    }
});