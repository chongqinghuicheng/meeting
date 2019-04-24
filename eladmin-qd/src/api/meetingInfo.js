import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/meetingInfo',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/meetingInfo/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/meetingInfo',
    method: 'put',
    data
  })
}
