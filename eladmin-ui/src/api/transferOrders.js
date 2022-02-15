import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/transferOrders',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/transferOrders/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/transferOrders',
    method: 'put',
    data
  })
}

export function getSupplierSelect() {
  return request({
    url: 'api/transferOrders/getSupplierSelect',
    method: 'get'
  })
}

export function getManagerSelect() {
  return request({
    url: 'api/transferOrders/getManagerSelect',
    method: 'get'
  })
}

export function getOrderNo() {
  return request({
    url: 'api/transferOrders/getOrderNo',
    method: 'get'
  })
}

export function getGoodChooseList(params) {
  return request({
    url: 'api/transferOrders/getGoodChooseList',
    method: 'get',
    params
  })
}

export function getOrderGoodList(params) {
  return request({
    url: 'api/transferOrders/getOrderGoodList',
    method: 'get',
    params
  })
}

export function approveOrders(data) {
  return request({
    url: 'api/transferOrders/approveOrders',
    method: 'put',
    data
  })
}

export function reApproveOrders(data) {
  return request({
    url: 'api/transferOrders/reApproveOrders',
    method: 'put',
    data
  })
}

export function printOrderReport(id) {
  return request({
    url: 'api/transferOrders/printOrderReport?id=' + id,
    method: 'get'
  })
}

export function getOrderPrintingInfo(id) {
  return request({
    url: 'api/transferOrders/getOrderPrintingInfo?id=' + id,
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
