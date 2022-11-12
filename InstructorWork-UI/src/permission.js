import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

import Layout from '@/layout'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  const hasToken = getToken()

  if (hasToken) {
    if (to.path === '/login') {
      // if is logged in, redirect to the home page
      next({ path: '/' })
      NProgress.done()
    } else {
      // const hasGetUserInfo = store.getters.name
      if (store.getters.userMenus && store.getters.userMenus.length > 0) {
        next()
      } else {
        try {
          // get user info
          // await store.dispatch('user/getInfo')
          await store.dispatch('user/getUserPermissionList')
          if (store.getters.userMenus.length < 1) {
            global.antRouter = []
            next()
          }
          const menus = filterAsyncRouter(store.getters.userMenus) // 1.过滤路由
          router.addRoutes(menus) // 2.动态添加路由
          global.antRouter = menus // 3.将路由数据传递给全局变量，做侧边栏菜单渲染工作
          next({
            ...to,
            replace: true
          }) // hack方法 确保addRoutes已完成 ,set the replace
        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch('user/resetToken')
          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/

    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
// 过滤
function filterAsyncRouter(asyncRouterMap) {
  const routes = []
  for (const route of asyncRouterMap) {
    if (route.menuType === 0 && route.isLeaf === 1) {
      const obj = {
        path: route.path,
        name: null,
        meta: {},
        component: Layout
      }
      // 如果存在redirect这个参数 就赋值 否则什么也不干
      route.redirect ? (obj.redirect = route.redirect) : null
      obj.children = [
        {
          path: route.path,
          name: route.name,
          meta: { title: route.title, icon: route.icon },
          component: resolve => require([`@/views${route.path}`], resolve),
          redirect: null
        }
      ]
      obj.children['children'] = null
      routes.push(obj)
    } else {
      const obj = {
        path: route.path,
        name: route.name,
        meta: { title: route.title, icon: route.icon },
        component: route.component === 'Layout' ? Layout : resolve => require([`@/views${route.component}`], resolve)
      }
      // 如果存在redirect这个参数 就赋值 否则什么也不干
      route.redirect ? (obj.redirect = route.redirect) : null
      route.children ? (obj['children'] = filterAsyncRouter(route.children)) : null
      routes.push(obj)
    }
  }
  return routes
}
