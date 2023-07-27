import router from '@/router'
import store from '@/store'
import axios from 'axios'
import { Message } from 'element-ui'

const request = axios.create({
  baseURL: '/api',
  timeout: 5000
})

request.interceptors.request.use(config => {
// Do something before request is sent
  if (!store.state.user === undefined) {
    config.headers.Authorization = store.state.user.token
  }
  return config
}, error => {
// Do something with request error
  return Promise.reject(error)
})

request.interceptors.response.use(response => {
// Do something before response is sent
  return response
}, error => {
// Do something with response error
  console.log(error)
  if (error.response.data.code === 401) {
    // token过期
    store.commit('user/del')
    router.push('/login')
  } else {
    Message({
      showClose: true,
      message: error.response.data.message,
      type: 'error'
    })
  }
  return Promise.reject(error)
})

export default request