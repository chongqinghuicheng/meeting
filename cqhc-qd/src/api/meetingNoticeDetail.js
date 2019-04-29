import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/meetingNoticeDetail',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/meetingNoticeDetail/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/meetingNoticeDetail',
    method: 'put',
    data
  })
}
