import router from '@/router'
import axios from 'axios'
import { Message } from 'element-ui'

const request = axios.create({
  baseURL: process.env.NODE_ENV === 'production' ? process.env.VUE_APP_PROD_BASE_URL : process.env.VUE_APP_DEV_BASE_URL,
  timeout: 5000
})

request.interceptors.request.use(config => {
// Do something before request is sent
  return config
}, error => {
// Do something with request error
  return Promise.reject(error)
})
request.interceptors.response.use(response => {
// Do something before response is sent
  const { data } = response
  if (data.code >= 20000) {
    Message({
      message: data.message,
      type: 'warning'
    })
  }
  return response
}, error => {
// Do something with response error
  if (error.response.data.code === 401) {
    Message({
      message: '无访问权限',
      type: 'warning'
    })
    router.push('/login')
  } else {
    if (error.response.status === 500) {
      Message({
        message: '服务器繁忙',
        type: 'warning'
      })
    } else {
      Message({
        message: '服务器有误,请联系管理员',
        type: 'warning'
      })
    }
  }
  return Promise.reject(error)
})

export default request
