import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/tbWhInventory',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/tbWhInventory/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/tbWhInventory',
    method: 'put',
    data
  })
}

export function getWareHouseSelect(id) {
  return request({
    url: 'api/tbWhInventory/getWareHouseSelect',
    method: 'get'
  })
}

export function getWhInOutDetail(params) {
  return request({
    url: 'api/tbWhInventory/getWhInOutDetail',
    method: 'get',
    params
  })
}

export default { add, edit, del, getWareHouseSelect, getWhInOutDetail }
