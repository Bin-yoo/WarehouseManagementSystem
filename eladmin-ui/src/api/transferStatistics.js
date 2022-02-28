import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/transferStatistics',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/transferStatistics/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/transferStatistics',
    method: 'put',
    data
  })
}

export function getDetail(params) {
  return request({
    url: 'api/transferStatistics/getDetail',
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
