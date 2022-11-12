import { login, logout, getPermissionList, getInfo, getUserPermissionList } from '@/api/user'
import { getToken, setToken, removeToken, getUsername, setUsername, removeUsername, getAvatar, setAvatar, removeAvatar } from '@/utils/auth'
import { resetRouter } from '@/router'

const getDefaultState = () => {
  return {
    token: getToken(),
    name: getUsername(),
    avatar: getAvatar(),
    menus: [],
    userMenus: []
  }
}

const state = getDefaultState()

const mutations = {
  RESET_STATE: state => {
    Object.assign(state, getDefaultState())
  },
  SET_TOKEN: (state, token) => {
    state.token = token
  },
  SET_NAME: (state, name) => {
    state.name = name
  },
  SET_AVATAR: (state, avatar) => {
    state.avatar = avatar
  },
  SET_MENUS: (state, menus) => {
    state.menus = menus
  },
  SET_USERMENUS: (state, userMenus) => {
    state.userMenus = userMenus
  }
}

const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password: password })
        .then(response => {
          const data = response.result
          // 设置token
          commit('SET_TOKEN', data.token)
          setToken(data.token)
          // 设置个人信息
          const { username, avatar } = data.userInfo
          commit('SET_NAME', username)
          setUsername(username)
          commit('SET_AVATAR', avatar)
          setAvatar(avatar)

          resolve()
        })
        .catch(error => {
          reject(error)
        })
    })
  },

  getPermissionList({ commit, state }) {
    return new Promise((resolve, reject) => {
      getPermissionList()
        .then(response => {
          const data = response.result

          if (!data) {
            return reject('验证失败，请重新登录')
          }
          const menus = data
          commit('SET_MENUS', menus)
          resolve(data)
        })
        .catch(error => {
          reject(error)
        })
    })
  },

  getUserPermissionList({ commit, state }) {
    return new Promise((resolve, reject) => {
      getUserPermissionList(state.name)
        .then(response => {
          const data = response.result
          if (!data) {
            return reject('验证失败，请重新登录')
          }
          const menus = data
          commit('SET_USERMENUS', menus)
          resolve(data)
        })
        .catch(error => {
          reject(error)
        })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token)
        .then(response => {
          const { data } = response.result.userInfo

          if (!data) {
            return reject('验证失败，请重新登录')
          }

          const { username, avatar } = data

          commit('SET_NAME', username)
          commit('SET_AVATAR', avatar)
          resolve(data)
        })
        .catch(error => {
          reject(error)
        })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      const user = { token: state.token, userInfo: {}}
      user.userInfo.username = state.name
      logout(user)
        .then(() => {
          removeToken() // must remove  token  first
          removeUsername()
          removeAvatar()
          resetRouter()
          commit('RESET_STATE')
          resolve()
        })
        .catch(error => {
          reject(error)
        })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      removeToken() // must remove  token  first
      commit('RESET_STATE')
      resolve()
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
