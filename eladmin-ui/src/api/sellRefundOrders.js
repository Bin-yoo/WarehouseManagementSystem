import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/sellRefundOrders',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/sellRefundOrders/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/sellRefundOrders',
    method: 'put',
    data
  })
}

export function getCustomerSelect() {
  return request({
    url: 'api/sellRefundOrders/getCustomerSelect',
    method: 'get'
  })
}

export function getManagerSelect() {
  return request({
    url: 'api/sellRefundOrders/getManagerSelect',
    method: 'get'
  })
}

export function getOrderNo() {
  return request({
    url: 'api/sellRefundOrders/getOrderNo',
    method: 'get'
  })
}

export function getGoodChooseList(params) {
  return request({
    url: 'api/sellRefundOrders/getGoodChooseList',
    method: 'get',
    params
  })
}

export function getOrderGoodList(id) {
  return request({
    url: 'api/sellRefundOrders/getOrderGoodList?id=' + id,
    method: 'get'
  })
}

export function approveOrders(data) {
  return request({
    url: 'api/sellRefundOrders/approveOrders',
    method: 'put',
    data
  })
}

export function reApproveOrders(data) {
  return request({
    url: 'api/sellRefundOrders/reApproveOrders',
    method: 'put',
    data
  })
}

export function printOrderReport(id) {
  return request({
    url: 'api/sellRefundOrders/printOrderReport?id=' + id,
    method: 'get'
  })
}

export function getOrderPrintingInfo(id) {
  return request({
    url: 'api/sellRefundOrders/getOrderPrintingInfo?id=' + id,
    method: 'get'
  })
}

export function getOrderChooseList(params) {
  return request({
    url: 'api/sellRefundOrders/getOrderChooseList',
    method: 'get',
    params
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
  getOrderPrintingInfo,
  getOrderChooseList
}
