import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/inventoryOrders',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/inventoryOrders/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/inventoryOrders',
    method: 'put',
    data
  })
}

export function getSupplierSelect() {
  return request({
    url: 'api/inventoryOrders/getSupplierSelect',
    method: 'get'
  })
}

export function getManagerSelect() {
  return request({
    url: 'api/inventoryOrders/getManagerSelect',
    method: 'get'
  })
}

export function getOrderNo() {
  return request({
    url: 'api/inventoryOrders/getOrderNo',
    method: 'get'
  })
}

export function getGoodChooseList(params) {
  return request({
    url: 'api/inventoryOrders/getGoodChooseList',
    method: 'get',
    params
  })
}

export function getOrderGoodList(id) {
  return request({
    url: 'api/inventoryOrders/getOrderGoodList?id=' + id,
    method: 'get'
  })
}

export function approveOrders(data) {
  return request({
    url: 'api/inventoryOrders/approveOrders',
    method: 'put',
    data
  })
}

export function reApproveOrders(data) {
  return request({
    url: 'api/inventoryOrders/reApproveOrders',
    method: 'put',
    data
  })
}

export function printOrderReport(id) {
  return request({
    url: 'api/inventoryOrders/printOrderReport?id=' + id,
    method: 'get'
  })
}

export function getOrderPrintingInfo(id) {
  return request({
    url: 'api/inventoryOrders/getOrderPrintingInfo?id=' + id,
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
