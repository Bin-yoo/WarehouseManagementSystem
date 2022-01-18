import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/purchaseOrders',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/purchaseOrders/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/purchaseOrders',
    method: 'put',
    data
  })
}

export function getSupplierSelect() {
  return request({
    url: 'api/purchaseOrders/getSupplierSelect',
    method: 'get'
  })
}

export function getManagerSelect() {
  return request({
    url: 'api/purchaseOrders/getManagerSelect',
    method: 'get'
  })
}

export function getOrderNo() {
  return request({
    url: 'api/purchaseOrders/getOrderNo',
    method: 'get'
  })
}

export function getGoodChooseList(params) {
  return request({
    url: 'api/purchaseOrders/getGoodChooseList',
    method: 'get',
    params
  })
}

export function getOrderGoodList(id) {
  return request({
    url: 'api/purchaseOrders/getOrderGoodList?id=' + id,
    method: 'get'
  })
}

export function approveOrders(data) {
  return request({
    url: 'api/purchaseOrders/approveOrders',
    method: 'put',
    data
  })
}

export function reApproveOrders(data) {
  return request({
    url: 'api/purchaseOrders/reApproveOrders',
    method: 'put',
    data
  })
}

export function printOrderReport(id) {
  return request({
    url: 'api/purchaseOrders/printOrderReport?id=' + id,
    method: 'get'
  })
}

export function getOrderPrintingInfo(id) {
  return request({
    url: 'api/purchaseOrders/getOrderPrintingInfo?id=' + id,
    method: 'get'
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
  getOrderPrintingInfo
}
