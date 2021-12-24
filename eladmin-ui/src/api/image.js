import request from '@/utils/request'

export function loadImg(id) {
  return request({
      url: 'api/pic?id='+id,
      method: 'get'
  });
}

export function loadBase64Img(id) {
  return request({
      url: 'api/pic/base64?id='+id,
      method: 'get'
  });
}

export default { loadImg, loadBase64Img }
