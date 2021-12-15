import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/tbEmployee',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/tbEmployee/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/tbEmployee',
    method: 'put',
    data
  })
}

export function getJobsSelect() {
  return request({
    url: 'api/tbEmployee/getJobsSelect',
    method: 'get'
  })
}

export default { add, edit, del, getJobsSelect }
