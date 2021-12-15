import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/tbWarehouse',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/tbWarehouse/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/tbWarehouse',
    method: 'put',
    data
  })
}

export function getQueryParamAdviceList(name) {
  return request({
    url: 'api/tbWarehouse/getQueryParamAdviceList?name=' + name,
    method: 'get'
  })
}

export function getEmpSelect() {
  return request({
    url: 'api/tbWarehouse/getEmpSelect',
    method: 'get'
  })
}

export default { add, edit, del, getQueryParamAdviceList, getEmpSelect }
