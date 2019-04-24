import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/parameterConfig',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/parameterConfig/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/parameterConfig',
    method: 'put',
    data
  })
}
