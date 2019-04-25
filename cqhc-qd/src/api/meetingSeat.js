import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/meetingSeat',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/meetingSeat/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/meetingSeat',
    method: 'put',
    data
  })
}
