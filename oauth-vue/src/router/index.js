import Vue from 'vue'
import VueRouter from 'vue-router'
import login from '@/views/login/login'
import checkScope from '@/views/checkScope/checkScope'
import layout from '@/views/layout'
import client from '@/views/layout/layoutClient'
import user from '@/views/layout/layoutUser'
import index from '@/views/layout/layoutIndex'

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

export default router
