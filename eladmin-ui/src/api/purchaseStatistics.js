import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/purchaseStatistics',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/purchaseStatistics/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/purchaseStatistics',
    method: 'put',
    data
  })
}

export function getDetail(params) {
  return request({
    url: 'api/purchaseStatistics/getDetail',
    method: 'get',
    params
  })
}

export default {
  add,
  edit,
  del,
  getDetail
}
