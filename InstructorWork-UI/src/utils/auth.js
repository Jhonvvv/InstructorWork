import Cookies from 'js-cookie'

const TokenKey = 'clm_token'
const username = 'clm_name'
const userAvatar = 'clm_avatar'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}

export function setUsername(name) {
  return Cookies.set(username, name)
}
export function getUsername() {
  return Cookies.get(username)
}
export function removeUsername() {
  return Cookies.remove(username)
}

export function setAvatar(avatar) {
  return Cookies.set(userAvatar, avatar)
}
export function getAvatar() {
  return Cookies.get(userAvatar)
}
export function removeAvatar() {
  return Cookies.remove(userAvatar)
}
