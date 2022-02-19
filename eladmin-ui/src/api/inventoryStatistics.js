import request from '@/utils/request'

export function queryLineBarData() {
  return request({
    url: 'api/inventoryStatistics/queryLineBarData',
    method: 'get'
  })
}

export function queryPieChartData() {
  return request({
    url: 'api/inventoryStatistics/queryPieChartData',
    method: 'get'
  })
}

export default {
  queryLineBarData,
  queryPieChartData
}
