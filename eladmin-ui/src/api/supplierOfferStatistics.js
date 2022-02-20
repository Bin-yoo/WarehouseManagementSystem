import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/supplierOfferStatistics',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/supplierOfferStatistics/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/supplierOfferStatistics',
    method: 'put',
    data
  })
}

export function getDetail(params) {
  return request({
    url: 'api/supplierOfferStatistics/getDetail',
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
