import request from '@/utils/request'
const student_api = '/counselor/student'

export function getStudentByDepartId(pageNo, pageSize, data) {
  return request({
    url: `${student_api}/queryByDepartAll?pageNo=${pageNo}&pageSize=${pageSize}`,
    method: 'post',
    data
  })
}

export function addStudent(data) {
  return request({
    url: `${student_api}/add`,
    method: 'post',
    data
  })
}

export function editStudent(data) {
  return request({
    url: `${student_api}/edit`,
    method: 'post',
    data
  })
}

export function deletetStudent(studentId) {
  return request({
    url: `${student_api}/delete?id=${studentId}`,
    method: 'delete'
  })
}
