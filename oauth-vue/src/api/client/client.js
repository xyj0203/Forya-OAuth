import axios from '@/utils/request'

const client = {
  // 1.获取所有信息
  findAll: (pageNow, pageNumber) => axios({
    url: `/client/queryAll?clientName=&id=&pageNow=${pageNow}&pageNumber=${pageNumber}&sort=`,
    method: 'get'
  }),
  deleteById: (id) => axios({
    url: `/client/deleteById/${id}`,
    method: 'delete'
  }),
  changeEnable: (id, enable) => axios({
    url: `/client/changeEnable?id=${id}&enable=${enable}`,
    method: 'put'
  }),
  deleteByIds: (arr) => axios({
    url: '/client/deleteByIds',
    method: 'delete',
    data: arr
  }),
  insertClient: (obj) => axios({
    url: '/client/insertClient',
    method: 'post',
    data: obj
  }),
  updateClient: (obj) => axios({
    url: '/client/updateClient',
    method: 'put',
    data: obj
  })
}

export default client
