import request from '@/utils/request'
const reward_api = '/counselor/reward'
const punishment_api = '/counselor/punishment'

export function queryRewardByStudentId(studentId) {
  return request({
    url: `${reward_api}/getRewardByStudentId?id=${studentId}`,
    method: 'get'
  })
}

export function rewardAdd(data) {
  return request({
    url: `${reward_api}/add`,
    method: 'post',
    data
  })
}

export function punishmentAdd(data) {
  return request({
    url: `${punishment_api}/add`,
    method: 'post',
    data
  })
}

export function queryPunishmentByStudentId(studentId) {
  return request({
    url: `${punishment_api}/getPunishmentByStudentId?id=${studentId}`,
    method: 'get'
  })
}

export function queryPunishmentHisByStudentId(studentId) {
  return request({
    url: `${punishment_api}/getPunishmentHisByStudentId?id=${studentId}`,
    method: 'get'
  })
}

export function queryPunishmentHisByUsername(username) {
  return request({
    url: `${punishment_api}/getPunishmentByUsername?username=${username}`,
    method: 'get'
  })
}

export function queryRevokePunishment(id) {
  return request({
    url: `${punishment_api}/getRevokePunishment?id=${id}`,
    method: 'get'
  })
}

export function revokePunishment(id) {
  return request({
    url: `${punishment_api}/revokePunishment?id=${id}`,
    method: 'get'
  })
}

export function applyRevoke(data) {
  return request({
    url: `${punishment_api}/applyRevoke`,
    method: 'post',
    data
  })
}

export function deletePunishment(punishmentId) {
  return request({
    url: `${punishment_api}/delete?id=${punishmentId}`,
    method: 'delete'
  })
}

export function updatePunishment(data) {
  return request({
    url: `${punishment_api}/update`,
    method: 'post',
    data
  })
}

export function getRevokePunishmentByCounselor(pageNo, pageSize, data) {
  return request({
    url: `${punishment_api}/getRevokePunishmentByCounselor?pageNo=${pageNo}&pageSize=${pageSize}`,
    method: 'post',
    data
  })
}

export function passRevokePunishment(data) {
  return request({
    url: `${punishment_api}/passRevokePunishment`,
    method: 'post',
    data
  })
}

export function rejectRevokePunishment(data) {
  return request({
    url: `${punishment_api}/rejectRevokePunishment`,
    method: 'post',
    data
  })
}
