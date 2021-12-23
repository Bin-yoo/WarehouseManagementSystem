import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/tbGoodsType',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/tbGoodsType/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/tbGoodsType',
    method: 'put',
    data
  })
}

export function getTypeDatas() {
  return request({
    url: 'api/tbGoodsType',
    method: 'get'
  })
}

export function getTypesSelectTree() {
  return request({
    url: 'api/tbGoodsType/getTypesSelectTree',
    method: 'get'
  })
}

export function getTypesFolderSelectTree() {
  return request({
    url: 'api/tbGoodsType/getTypesFolderSelectTree',
    method: 'get'
  })
}

export function getTypeDetail(id) {
  return request({
    url: 'api/tbGoodsType/getGoodsTypeById?id=' + id,
    method: 'get'
  })
}

export default { add, edit, del, getTypeDatas, getTypesSelectTree, getTypesFolderSelectTree, getTypeDetail }
