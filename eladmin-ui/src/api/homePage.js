import request from '@/utils/request'

export function queryData() {
  return request({
    url: 'api/homePage/queryData',
    method: 'get'
  })
}

export function queryLineBarData() {
  return request({
    url: 'api/homePage/queryLineBarData',
    method: 'get'
  })
}

export default {
  queryData,
  queryLineBarData
}
