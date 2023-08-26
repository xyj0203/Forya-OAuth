import axios from '@/utils/request'

const oauth = {
  // 获取客户端想要获取的所有权限
  getClientScope: () => axios({
    url: '/user/oauth/getClientScope',
    method: 'get'
  }),
  login: (username, password) => axios({
    url: '/baseUser/login',
    method: 'post',
    data: {
      username,
      password
    },
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
    }
  }),
  hasLogin: () => axios({
    url: '/baseUser/loginMessage',
    method: 'get'
  }),
  submit: (list) => axios({
    url: '/user/oauth/submit',
    method: 'post',
    data: list
  })
}

export default oauth
