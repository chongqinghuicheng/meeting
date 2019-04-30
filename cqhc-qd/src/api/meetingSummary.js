import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/meetingSummary',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/meetingSummary/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/meetingSummary',
    method: 'put',
    data
  })
}
