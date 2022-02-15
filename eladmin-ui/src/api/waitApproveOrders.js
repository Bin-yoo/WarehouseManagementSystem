import request from '@/utils/request'

export function getOrderGoodList(id) {
  return request({
    url: 'api/waitApproveOrders/getOrderGoodList?id=' + id,
    method: 'get'
  })
}

export function approveOrders(data) {
  return request({
    url: 'api/waitApproveOrders/approveOrders',
    method: 'put',
    data
  })
}

export function reApproveOrders(data) {
  return request({
    url: 'api/waitApproveOrders/reApproveOrders',
    method: 'put',
    data
  })
}

export function printOrderReport(id) {
  return request({
    url: 'api/waitApproveOrders/printOrderReport?id=' + id,
    method: 'get'
  })
}

export function getOrderPrintingInfo(id) {
  return request({
    url: 'api/waitApproveOrders/getOrderPrintingInfo?id=' + id,
    method: 'get'
  })
}

export default {
  getOrderGoodList,
  approveOrders,
  reApproveOrders,
  printOrderReport,
  getOrderPrintingInfo
}
