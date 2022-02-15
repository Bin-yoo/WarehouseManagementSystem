import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/sellOrders',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/sellOrders/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/sellOrders',
    method: 'put',
    data
  })
}

export function getCustomerSelect() {
  return request({
    url: 'api/sellOrders/getCustomerSelect',
    method: 'get'
  })
}

export function getManagerSelect() {
  return request({
    url: 'api/sellOrders/getManagerSelect',
    method: 'get'
  })
}

export function getOrderNo() {
  return request({
    url: 'api/sellOrders/getOrderNo',
    method: 'get'
  })
}

export function getGoodChooseList(params) {
  return request({
    url: 'api/sellOrders/getGoodChooseList',
    method: 'get',
    params
  })
}

export function getOrderGoodList(params) {
  return request({
    url: 'api/sellOrders/getOrderGoodList',
    method: 'get',
    params
  })
}

export function approveOrders(data) {
  return request({
    url: 'api/sellOrders/approveOrders',
    method: 'put',
    data
  })
}

export function reApproveOrders(data) {
  return request({
    url: 'api/sellOrders/reApproveOrders',
    method: 'put',
    data
  })
}

export function printOrderReport(id) {
  return request({
    url: 'api/sellOrders/printOrderReport?id=' + id,
    method: 'get'
  })
}

export function getOrderPrintingInfo(id) {
  return request({
    url: 'api/sellOrders/getOrderPrintingInfo?id=' + id,
    method: 'get'
  })
}

export default {
  add,
  edit,
  del,
  getCustomerSelect,
  getManagerSelect,
  getOrderNo,
  getGoodChooseList,
  getOrderGoodList,
  approveOrders,
  reApproveOrders,
  printOrderReport,
  getOrderPrintingInfo
}
