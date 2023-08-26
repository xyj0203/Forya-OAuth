<template>
<div id="container">
  <div class="son" style="width: 60%; ">
  </div>
  <div class="son" style="width: 40%;">
  </div>
</div>
</template>

<script>
import index from '@/api/user/index'
export default {
  methods: {
    async initChart (domName, title) {
      const myChart = this.$echarts.init(domName)
      const option = {
        title: {
          text: title
        },
        legend: {
          data: ['请求量']
        },
        xAxis: {
          data: []
        },
        yAxis: {},
        series: [
          {
            name: '请求量',
            type: 'bar',
            data: []
          }
        ]
      }
      const { data } = await index.queryCount()
      if (data.code === 10000) {
        const { object } = data
        option.xAxis.data = object.date
        option.series[0].data = object.count
      } else {
        this.$message({
          type: 'warning',
          message: data.message
        })
      }
      option && myChart.setOption(option)
    },
    async initPie (domName, title) {
      const mypie = this.$echarts.init(domName)
      const option = {
        title: {
          text: title,
          subtext: 'Request Method',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: [
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      const { data } = await index.queryMethod()
      if (data.code === 10000) {
        const { object } = data
        option.series[0].data.push({ name: 'GET', value: object.GET })
        option.series[0].data.push({ name: 'POST', value: object.POST })
        option.series[0].data.push({ name: 'PUT', value: object.PUT })
        option.series[0].data.push({ name: 'DELETE', value: object.DELETE })
      } else {
        this.$message({
          type: 'warning',
          message: data.message
        })
      }
      option && mypie.setOption(option)
    }
  },
  mounted: function () {
    this.initChart(document.getElementsByClassName('son')[0], '近7天请求量')
    this.initPie(document.getElementsByClassName('son')[1], '请求方式')
  }
}
</script>

  <style lang="less">
  body, html, #app {
    margin: 0;
    padding: 0;
    height: 100%;
  }
  #container {
    height: 650px;
  }
  .son {
    height: 100%;
    display: inline-block;
  }
  .circle {
    height: 50%;
    width: 100%;
  }
  </style>
