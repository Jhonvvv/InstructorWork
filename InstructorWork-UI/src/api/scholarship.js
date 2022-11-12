import request from '@/utils/request'
const scholarship_api = '/counselor/scholarship'

export function getScholarshipByUsername(username) {
  return request({
    url: `${scholarship_api}/queryScholarshipByUsername?username=${username}`,
    method: 'get'
  })
}

export function getScholarshipByCounselor(pageNo, pageSize, data) {
  return request({
    url: `${scholarship_api}/queryScholarshipByCounselor?pageNo=${pageNo}&pageSize=${pageSize}`,
    method: 'post',
    data
  })
}

export function addScholarship(data) {
  return request({
    url: `${scholarship_api}/add`,
    method: 'post',
    data
  })
}

export function editScholarship(data) {
  return request({
    url: `${scholarship_api}/edit`,
    method: 'post',
    data
  })
}

export function deletetScholarship(scholarship) {
  return request({
    url: `${scholarship_api}/delete?id=${scholarship}`,
    method: 'delete'
  })
}

export function passScholarship(data) {
  return request({
    url: `${scholarship_api}/passScholarship`,
    method: 'post',
    data
  })
}

export function rejectScholarship(data) {
  return request({
    url: `${scholarship_api}/rejectScholarship`,
    method: 'post',
    data
  })
}
