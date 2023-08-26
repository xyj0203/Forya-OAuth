import axios from '@/utils/request'

const client = {
  // 1.获取所有信息
  findAll: (pageNow, pageNumber) => axios({
    url: `/sysClient/queryAll?clientName=&id=&pageNow=${pageNow}&pageNumber=${pageNumber}&sort=`,
    method: 'get'
  }),
  deleteById: (id) => axios({
    url: `/sysClient/deleteById/${id}`,
    method: 'delete'
  }),
  changeEnable: (id, enable) => axios({
    url: `/sysClient/changeEnable?id=${id}&enable=${enable}`,
    method: 'put'
  }),
  deleteByIds: (arr) => axios({
    url: '/sysClient/deleteByIds',
    method: 'delete',
    data: arr
  }),
  insertClient: (obj) => axios({
    url: '/sysClient/insertClient',
    method: 'post',
    data: obj
  }),
  updateClient: (obj) => axios({
    url: '/sysClient/updateClient',
    method: 'put',
    data: obj
  }),
  queryScopeAll: () => axios({
    url: '/sysClient/queryScopeAll',
    method: 'get'
  }),
  search: (pageNow, pageNumber, keywords) => axios({
    url: `/sysClient/queryClientByName?clientName=${keywords}&id=&pageNow=${pageNow}&pageNumber=${pageNumber}&sort=`,
    method: 'get'
  })
}

export default client
