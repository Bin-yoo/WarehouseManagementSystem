<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme
import resize from './mixins/resize'
import HomePage from '@/api/homePage.js'

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
      HomePage.queryLineBarData().then(res => {
        this.setOptions(res)
      })
    },
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      // this.setOptions(this.chart)
    },
    setOptions({ days, purchaseLineBarData, produceLineBarData, sellRefundLineBarData, purchaseRefundLineBarData, sellLineBarData } = {}) {
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
          data: ['采购入库', '生产入库', '销售退货', '采购退货', '销售提货']
        },
        series: [
          {
            name: '采购入库',
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
            smooth: false,
            type: 'line',
            data: purchaseLineBarData,
            animationDuration: 2800,
            animationEasing: 'cubicInOut'
          },
          {
            name: '生产入库',
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
            data: produceLineBarData,
            animationDuration: 2800,
            animationEasing: 'quadraticOut'
          },
            {
            name: '销售退货', itemStyle: {
              normal: {
                color: '#409EFF',
                lineStyle: {
                  color: '#409EFF',
                  width: 2
                },
                areaStyle: {
                  color: '#409EFF'
                }
              }
            },
            smooth: false,
            type: 'line',
            data: sellRefundLineBarData,
            animationDuration: 2800,
            animationEasing: 'cubicInOut'
          },
          {
            name: '采购退货',
            type: 'line',
            itemStyle: {
              normal: {
                color: '#67C23A',
                lineStyle: {
                  color: '#67C23A',
                  width: 2
                },
                areaStyle: {
                  color: '#67C23A'
                }
              }
            },
            smooth: false,
            data: purchaseRefundLineBarData,
            animationDuration: 2800,
            animationEasing: 'quadraticOut'
          },
          {
            name: '销售提货',
            type: 'line',
            itemStyle: {
              normal: {
                color: '#DCDFE6',
                lineStyle: {
                  color: '#DCDFE6',
                  width: 2
                },
                areaStyle: {
                  color: '#DCDFE6'
                }
              }
            },
            smooth: false,
            data: sellLineBarData,
            animationDuration: 2800,
            animationEasing: 'quadraticOut'
          }
        ]
      })
    }
  }
}
</script>
