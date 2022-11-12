import request from '@/utils/request'
const punishmentApprove_api = '/counselor/punishmentApprove'

export function getPunishmentByCounselor(pageNo, pageSize, data) {
  return request({
    url: `${punishmentApprove_api}/queryPunishmentByCounselor?pageNo=${pageNo}&pageSize=${pageSize}`,
    method: 'post',
    data
  })
}

export function passPunishment(data) {
  return request({
    url: `${punishmentApprove_api}/passPunishment`,
    method: 'post',
    data
  })
}

export function rejectPunishment(data) {
  return request({
    url: `${punishmentApprove_api}/rejectPunishment`,
    method: 'post',
    data
  })
}
