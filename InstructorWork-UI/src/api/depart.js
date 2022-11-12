import request from '@/utils/request'
const depart_api = '/system/depart'

export function addDepartTree(data) {
  return request({
    url: `${depart_api}/addDepartTree`,
    method: 'post',
    data
  })
}

export function editDepartTree(data) {
  return request({
    url: `${depart_api}/editDepartTree`,
    method: 'post',
    data
  })
}

export function deleteDepartTree(departId) {
  return request({
    url: `${depart_api}/delete?id=${departId}`,
    method: 'delete'
  })
}

export function queryTreeList() {
  return request({
    url: `${depart_api}/queryTreeList`,
    method: 'get'
  })
}

export function getDepartByDepartIds() {
  return request({
    url: `${depart_api}/getDepartByDepartIds`,
    method: 'get'
  })
}

export function getAllDepart() {
  return request({
    url: `${depart_api}/getAllDepart`,
    method: 'get'
  })
}

export function getDepartByUsername(username) {
  return request({
    url: `${depart_api}/getDepartByUsername?username=${username}`,
    method: 'get'
  })
}
