import axios from '@/utils/request'

const client = {
  // 1.获取所有信息
  findAll: (pageNow, pageNumber) => axios({
    url: `/client/queryAll?clientName=&id=&pageNow=${pageNow}&pageNumber=${pageNumber}&sort=`,
    method: 'get'
  })
}

export default client
