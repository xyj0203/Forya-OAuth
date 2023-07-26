import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/login/Login'
import checkScope from '@/views/checkScope/checkScope'
import layout from '@/views/layout'
import scope from '@/views/layout/layoutScope'
import user from '@/views/layout/layoutUser'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: Login
  },
  {
    path: '/login',
    component: Login
  }, {
    path: '/checkScope',
    component: checkScope
  }, {
    path: '/',
    component: layout,
    children: [
      {
        path: 'user',
        component: user
      }, {
        path: 'scope',
        component: scope
      }
    ]
  }]

const router = new VueRouter({
  routes,
  mode: 'history'
})

export default router
