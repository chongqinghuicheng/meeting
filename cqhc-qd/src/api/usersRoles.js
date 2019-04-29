import request from '@/utils/request'

export function add(data) {
  return request({
    url: 'api/usersRoles',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: 'api/usersRoles/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: 'api/usersRoles',
    method: 'put',
    data
  })
}
