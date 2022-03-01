import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/dataImport',
    method: 'post',
    data
  })
}

export function del(ids) {
  return request({
    url: 'api/dataImport/',
    method: 'delete',
    data: ids
  })
}

export function edit(data) {
  return request({
    url: 'api/dataImport',
    method: 'put',
    data
  })
}

export function getGoodsImportTemplate() {
  return request({
    url: 'api/dataImport/getGoodsImportTemplate',
    method: 'get',
    responseType: 'blob'
  })
}

export function getCompanyImportTemplate() {
  return request({
    url: 'api/dataImport/getCompanyImportTemplate',
    method: 'get',
    responseType: 'blob'
  })
}

export function getEmployeeImportTemplate() {
  return request({
    url: 'api/dataImport/getEmployeeImportTemplate',
    method: 'get',
    responseType: 'blob'
  })
}

export function getInitialGoodsImportTemplate() {
  return request({
    url: 'api/dataImport/getInitialGoodsImportTemplate',
    method: 'get',
    responseType: 'blob'
  })
}

export function goodsImport(data) {
  return request({
      url: '/api/dataImport/goodsImport',
      method: 'post',
      data
  });
}

export function companiesImport(data) {
  return request({
      url: '/api/dataImport/companiesImport',
      method: 'post',
      data
  });
}

export function employeeImport(data) {
  return request({
      url: '/api/dataImport/employeeImport',
      method: 'post',
      data
  });
}

export default {
  add,
  edit,
  del,
  getGoodsImportTemplate,
  getCompanyImportTemplate,
  getEmployeeImportTemplate,
  getInitialGoodsImportTemplate,
  goodsImport,
  companiesImport,
  employeeImport
}
