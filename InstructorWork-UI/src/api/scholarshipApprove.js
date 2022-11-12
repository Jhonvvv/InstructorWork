import request from '@/utils/request'
const scholarshipApprove_api = '/counselor/scholarshipApprove'

export function getScholarshipApproveByCounselor(pageNo, pageSize, data) {
  return request({
    url: `${scholarshipApprove_api}/queryScholarshipApproveByCounselor?pageNo=${pageNo}&pageSize=${pageSize}`,
    method: 'post',
    data
  })
}

export function passScholarship(data) {
  return request({
    url: `${scholarshipApprove_api}/passScholarship`,
    method: 'post',
    data
  })
}

export function rejectScholarship(data) {
  return request({
    url: `${scholarshipApprove_api}/rejectScholarship`,
    method: 'post',
    data
  })
}
