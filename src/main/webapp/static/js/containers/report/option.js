var pieOption = {
    title: {
        text: '',
        subtext: '',
        x: 'center'
    },
    tooltip: {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c}"
    },
    legend: {
        show: false,
        top: 'middle',
        orient: 'vertical',
        left: 'left',
        data: []
    },
    series: [{
        name: '本日',
        type: 'pie',
        radius: '80%',
        center: ['50%', '50%'],
        label: {
            normal: {
                show: false
            }
        },
        labelLine: {
            normal: {
                show: false
            }
        },
        data: [
        // {
        //     value: 335,
        //     name: '应收合计'
        // }, {
        //     value: 310,
        //     name: '邮件营销'
        // }, {
        //     value: 234,
        //     name: '联盟广告'
        // }, {
        //     value: 135,
        //     name: '视频广告'
        // }, {
        //     value: 1548,
        //     name: '搜索引擎'
        // }
        ],
        itemStyle: {
            emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
        }
    }]
};

var lineOption = {
    title: {
        text: ''
    },
    tooltip: {
        show: true,
        formatter: '{b} <br />{a0}: {c0}<br />{a1}: {c1}<br />{a2}: {c2}<br />{a3}: {c3}<br />{a4}: {c4}',
        trigger: 'axis'
    },
    legend: {
        data:[]
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    toolbox: {
        show: false
    },
    xAxis: {
        show: false,
        type: 'category',
        boundaryGap: false,
        data: []
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            name:'邮件营销',
            type:'line',
            data:[120, 132, 101, 134, 90, 230, 210]
        },
        {
            name:'联盟广告',
            type:'line',
            data:[220, 182, 191, 234, 290, 330, 310]
        },
        {
            name:'视频广告',
            type:'line',
            data:[150, 232, 201, 154, 190, 330, 410]
        },
        {
            name:'直接访问',
            type:'line',
            data:[320, 332, 301, 334, 390, 330, 320]
        },
        {
            name:'搜索引擎',
            type:'line',
            data:[820, 932, 901, 934, 1290, 1330, 1320]
        }
    ]
};