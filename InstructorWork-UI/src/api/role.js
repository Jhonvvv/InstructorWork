import request from '@/utils/request'
const role_api = '/system/role'
const permission_api = '/system/permission'

export function getUserRole(username) {
  return request({
    url: `${role_api}/queryUserRole?username=${username}`,
    method: 'get'
  })
}

export function getRoles(pageNo, pageSize) {
  return request({
    url: `${role_api}/queryAll?pageNo=${pageNo}&pageSize=${pageSize}`,
    method: 'get'
  })
}

export function queryRoleAll() {
  return request({
    url: `${role_api}/queryRoleAll`,
    method: 'get'
  })
}

export function getRolePermission(roleId) {
  return request({
    url: `${permission_api}/queryRolePermission?roleId=${roleId}`,
    method: 'get'
  })
}

export function checkRoleCode(roleId, roleCode) {
  return request({
    url: `${role_api}/checkRoleCode?roleId=${roleId}&roleCode=${roleCode}`,
    method: 'get'
  })
}

export function addRole(data) {
  return request({
    url: `${role_api}/add`,
    method: 'post',
    data
  })
}

export function saveRolePermission(data) {
  return request({
    url: `${role_api}/saveRolePermission`,
    method: 'post',
    data
  })
}

export function updateRole(role) {
  return request({
    url: `${role_api}/edit`,
    method: 'post',
    data: role
  })
}

export function deleteRole(id) {
  return request({
    url: `${role_api}/delete?id=${id}`,
    method: 'delete'
  })
}
