import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/purchaseRefundOrders',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/purchaseRefundOrders/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/purchaseRefundOrders',
    method: 'put',
    data
  })
}

export function getSupplierSelect() {
  return request({
    url: 'api/purchaseRefundOrders/getSupplierSelect',
    method: 'get'
  })
}

export function getManagerSelect() {
  return request({
    url: 'api/purchaseRefundOrders/getManagerSelect',
    method: 'get'
  })
}

export function getOrderNo() {
  return request({
    url: 'api/purchaseRefundOrders/getOrderNo',
    method: 'get'
  })
}

export function getGoodChooseList(params) {
  return request({
    url: 'api/purchaseRefundOrders/getGoodChooseList',
    method: 'get',
    params
  })
}

export function getOrderGoodList(id) {
  return request({
    url: 'api/purchaseRefundOrders/getOrderGoodList?id=' + id,
    method: 'get'
  })
}

export function approveOrders(data) {
  return request({
    url: 'api/purchaseRefundOrders/approveOrders',
    method: 'put',
    data
  })
}

export function reApproveOrders(data) {
  return request({
    url: 'api/purchaseRefundOrders/reApproveOrders',
    method: 'put',
    data
  })
}

export function printOrderReport(id) {
  return request({
    url: 'api/purchaseRefundOrders/printOrderReport?id=' + id,
    method: 'get'
  })
}

export function getOrderPrintingInfo(id) {
  return request({
    url: 'api/purchaseRefundOrders/getOrderPrintingInfo?id=' + id,
    method: 'get'
  })
}

export function getOrderChooseList(params) {
  return request({
    url: 'api/purchaseRefundOrders/getOrderChooseList',
    method: 'get',
    params
  })
}

export default {
  add,
  edit,
  del,
  getSupplierSelect,
  getManagerSelect,
  getOrderNo,
  getGoodChooseList,
  getOrderGoodList,
  approveOrders,
  reApproveOrders,
  printOrderReport,
  getOrderPrintingInfo,
  getOrderChooseList
}
