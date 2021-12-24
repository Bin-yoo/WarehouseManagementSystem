import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/tbGoodsInfo',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/tbGoodsInfo/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/tbGoodsInfo',
    method: 'put',
    data
  })
}

export function getGoodCode(typeId) {
  return request({
    url: 'api/tbGoodsInfo/getGoodCode?typeId=' + typeId,
    method: 'get'
  })
}

export function getTypesSelectTree() {
  return request({
    url: 'api/tbGoodsInfo/getTypesSelectTree',
    method: 'get'
  })
}

export function getGoodUnitsSelect() {
  return request({
    url: 'api/tbGoodsInfo/getGoodUnitsSelect',
    method: 'get'
  })
}

export function getWareHouseGoodsInfo() {
  return request({
    url: 'api/tbGoodsInfo/getWareHouseGoodsInfo',
    method: 'get'
  })
}

export function getWareHouseGoodsInfoByGoodId(id) {
  return request({
    url: 'api/tbGoodsInfo/getWareHouseGoodsInfoByGoodId?goodId=' + id,
    method: 'get'
  })
}

export function uploadGoodPic(data) {
  return request({
      url: '/api/tbGoodsInfo/uploadGoodPic',
      method: 'post',
      data
  });
}

export default { add, edit, del, getGoodCode, getTypesSelectTree, getGoodUnitsSelect, getWareHouseGoodsInfoByGoodId, getWareHouseGoodsInfo, uploadGoodPic }
