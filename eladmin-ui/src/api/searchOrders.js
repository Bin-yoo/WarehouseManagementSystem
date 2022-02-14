import request from '@/utils/request'

export function getOrderGoodList(id) {
  return request({
    url: 'api/searchOrders/getOrderGoodList?id=' + id,
    method: 'get'
  })
}

export function printOrderReport(id) {
  return request({
    url: 'api/searchOrders/printOrderReport?id=' + id,
    method: 'get'
  })
}

export function getOrderPrintingInfo(id) {
  return request({
    url: 'api/searchOrders/getOrderPrintingInfo?id=' + id,
    method: 'get'
  })
}

export default {
  getOrderGoodList,
  printOrderReport,
  getOrderPrintingInfo
}
