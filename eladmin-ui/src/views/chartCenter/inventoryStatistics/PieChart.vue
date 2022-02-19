<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import { debounce } from '@/utils'
import InventoryStatistics from '@/api/inventoryStatistics.js'

export default {
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '300px'
    }
  },
  data() {
    return {
      chart: null,
      chartData: []
    }
  },
  created() {
    this.queryPieChartData()
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
      this.__resizeHandler = debounce(() => {
        if (this.chart) {
          this.chart.resize()
        }
      }, 100)
      window.addEventListener('resize', this.__resizeHandler)
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    window.removeEventListener('resize', this.__resizeHandler)
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    queryPieChartData() {
      InventoryStatistics.queryPieChartData().then(res => {
        this.chartData = res
        this.setOptions(res)
      })
    },
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
    },
    setOptions({ data, pieData } = {}) {
      this.chart.setOption({
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          left: 'center',
          bottom: '10',
          data: data
        },
        calculable: true,
        series: [
          {
            name: '单据占比',
            type: 'pie',
            radius: [15, 95],
            center: ['50%', '38%'],
            data: pieData,
            animationEasing: 'cubicInOut',
            animationDuration: 2600
          }
        ]
      })
    }
  }
}
</script>
