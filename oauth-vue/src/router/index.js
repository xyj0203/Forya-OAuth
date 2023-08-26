import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '@/views/login/Login'
import checkScope from '@/views/checkScope/checkScope'
import layout from '@/views/layout'
import client from '@/views/layout/layoutClient'
import user from '@/views/layout/layoutUser'
import index from '@/views/layout/layoutIndex'
import store from '@/store'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: login
  },
  {
    path: '/login',
    component: login
  }, {
    path: '/checkScope',
    component: checkScope
  }, {
    path: '/',
    component: layout,
    children: [
      {
        path: '/index',
        component: index
      },
      {
        path: 'user',
        component: user
      }, {
        path: 'client',
        component: client
      }
    ]
  }]

const router = new VueRouter({
  routes,
  mode: 'history'
})

router.beforeEach(async function (to, from, next) {
  if (to.path === '/login') {
    next()
  }
  if (to.path === '/') {
    const role = store.getters['user/getRole']
    if (role === 'null') {
      next('/login')
    } else if (role === 'USER') {
      next('/checkScope')
    } else {
      next('/index')
    }
  } else {
    next()
  }
})

export default router
