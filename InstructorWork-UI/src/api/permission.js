import request from '@/utils/request'
const permission_api = '/system/permission'

export function addPermission(data) {
  return request({
    url: `${permission_api}/add`,
    method: 'post',
    data
  })
}
export function editPermission(data) {
  return request({
    url: `${permission_api}/edit`,
    method: 'post',
    data
  })
}

export function deletePermission(permissionId) {
  return request({
    url: `${permission_api}/delete?id=${permissionId}`,
    method: 'delete'
  })
}
