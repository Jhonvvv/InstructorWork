import request from '@/utils/request'
const work_api = '/counselor/work'
const dailyWork_api = '/counselor/dailyWork'

export function addWork(data) {
  return request({
    url: `${work_api}/add`,
    method: 'post',
    data
  })
}

export function addDailyWork(data) {
  return request({
    url: `${dailyWork_api}/add`,
    method: 'post',
    data
  })
}

export function editWork(data) {
  return request({
    url: `${work_api}/edit`,
    method: 'post',
    data
  })
}

export function editProvisionalWork(data) {
  return request({
    url: `${dailyWork_api}/edit`,
    method: 'post',
    data
  })
}

export function finishDailyWork(dailyWorkId) {
  return request({
    url: `${dailyWork_api}/finishDailyWork?id=${dailyWorkId}`,
    method: 'post'
  })
}

export function revokeFinishDailyWork(dailyWorkId) {
  return request({
    url: `${dailyWork_api}/revokeFinishDailyWork?id=${dailyWorkId}`,
    method: 'post'
  })
}

export function deleteProvisionalWork(workId) {
  return request({
    url: `${dailyWork_api}/delete?id=${workId}`,
    method: 'delete'
  })
}

export function deleteWork(workId) {
  return request({
    url: `${work_api}/delete?id=${workId}`,
    method: 'delete'
  })
}

export function queryWorkNotFinish(username) {
  return request({
    url: `${work_api}/queryWorkNotFinish?username=${username}`,
    method: 'get'
  })
}

export function queryProvisional(username) {
  return request({
    url: `${dailyWork_api}/queryProvisional?username=${username}`,
    method: 'get'
  })
}

export function queryFinishDailyWork(username) {
  return request({
    url: `${dailyWork_api}/queryFinishDailyWork?username=${username}`,
    method: 'get'
  })
}

export function queryWork(username) {
  return request({
    url: `${work_api}/queryWork?username=${username}`,
    method: 'get'
  })
}

export function queryWorkFlow(username) {
  return request({
    url: `${work_api}/queryWorkFlow?username=${username}`,
    method: 'get'
  })
}
