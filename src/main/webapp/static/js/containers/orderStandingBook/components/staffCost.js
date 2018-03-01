var StaffCostComponent = Vue.extend({
    template: '#staffCostTmpl',
    data: function () {
        return {
            userInfoData: [],
            tableList: [],
            loaded:false
        }
    },
    created: function () {
        this.fetchData()
    },
    props: ['msg'],
    watch: {
        msg: function (curVal, oldVal) {
            if (curVal == 14 && !this.loaded) {
                this.fetchData();
            }
        }

    },
    methods: {
        fetchData: function () {
            var self = this;
            self.$http.get("/static/js/containers/orderStandingBook/components/le_json/cost.json").then(function (res) {
                if(this.msg == 14){
                    if (res.data.code === 1) {
                        self.userInfoData = res.data.data.userInfo;
                        self.tableList = res.data.data.tableList;
                    }
                    self.loaded = true;
                }
            }, function (res) {
            })
        }
    }
});