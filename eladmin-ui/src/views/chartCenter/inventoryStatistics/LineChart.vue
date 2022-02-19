<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from '@/views/dashboard/mixins/resize'
import InventoryStatistics from '@/api/inventoryStatistics.js'

export default {
  mixins: [resize],
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
      default: '350px'
    },
    autoResize: {
      type: Boolean,
      default: true
    },
    chartData: {
      type: Object,
      // required: true
    }
  },
  data() {
    return {
      chart: null
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        this.setOptions(val)
      }
    }
  },
  created() {
    this.queryLineBarData()
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart()
    })
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    queryLineBarData() {
      InventoryStatistics.queryLineBarData().then(res => {
        this.setOptions(res)
      })
    },
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      // this.setOptions(this.chart)
    },
    setOptions({ days, inLineBarData, outLineBarData } = {}) {
      this.chart.setOption({
        xAxis: {
          data: days,
          boundaryGap: false,
          axisTick: {
            show: false
          }
        },
        grid: {
          left: 10,
          right: 10,
          bottom: 20,
          top: 30,
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          padding: [5, 10]
        },
        yAxis: {
          axisTick: {
            show: false
          }
        },
        legend: {
          data: ['入库数量', '出库数量']
        },
        series: [
          {
            name: '入库数量',
            itemStyle: {
              normal: {
                color: '#FF005A',
                lineStyle: {
                  color: '#FF005A',
                  width: 2
                },
                areaStyle: {
                  color: '#FF005A'
                }
              }
            },
            type: 'line',
            smooth: false,
            data: inLineBarData,
            animationDuration: 2800,
            animationEasing: 'cubicInOut'
          },
          {
            name: '出库数量',
            type: 'line',
            itemStyle: {
              normal: {
                color: '#3888fa',
                lineStyle: {
                  color: '#3888fa',
                  width: 2
                },
                areaStyle: {
                  color: '#3888fa'
                }
              }
            },
            smooth: false,
            data: outLineBarData,
            animationDuration: 2800,
            animationEasing: 'quadraticOut'
          }
        ]
      })
    }
  }
}
</script>
