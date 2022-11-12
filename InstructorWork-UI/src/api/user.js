import request from '@/utils/request'
const login_api = '/system'
const user_api = '/system/user'
const permission_api = '/system/permission'

export function login(data) {
  return request({
    url: `${login_api}/login`,
    method: 'post',
    data
  })
}

export function checkusername(userId, username) {
  return request({
    url: `${user_api}/checkUsername?userId=${userId}&username=${username}`,
    method: 'get'
  })
}

export function addUser(data) {
  return request({
    url: `${user_api}/addUserByRole`,
    method: 'post',
    data
  })
}

export function editUser(data) {
  return request({
    url: `${user_api}/edit`,
    method: 'post',
    data
  })
}
export function deleteUser(userId) {
  return request({
    url: `${user_api}/delete?id=${userId}`,
    method: 'delete'
  })
}
export function getAllUser(pageNo, pageSize, searchObj) {
  return request({
    url: `${user_api}/queryAll?pageNo=${pageNo}&pageSize=${pageSize}`,
    method: 'post',
    data: searchObj
  })
}

export function getPermissionList() {
  return request({
    url: `${permission_api}/list`,
    method: 'get'
  })
}

export function getUserPermissionList(username) {
  return request({
    url: `${permission_api}/queryUserRoleList?username=${username}`,
    method: 'get'
  })
}

export function getInfo(token) {
  return request({
    url: '/vue-admin-template/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout(data) {
  return request({
    url: `${login_api}/logout`,
    method: 'post',
    data
  })
}
