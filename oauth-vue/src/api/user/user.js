import axios from '@/utils/request'

const user = {
  // 1.获取所有信息
  findAll: (pageNow, pageNumber, sort) => axios({
    url: `/sysUser/queryAll?username=&id=&pageNow=${pageNow}&pageNumber=${pageNumber}&sort=${sort}`,
    method: 'get'
  }),
  deleteById: (id) => axios({
    url: `/sysUser/deleteById/${id}`,
    method: 'delete'
  }),
  deleteByIds: (arr) => axios({
    url: '/sysUser/deleteByIds',
    method: 'delete',
    data: arr
  }),
  insertUser: (obj) => axios({
    url: '/sysUser/insertUser',
    method: 'post',
    data: obj
  }),
  updateUser: (obj) => axios({
    url: '/sysUser/updateUser',
    method: 'put',
    data: obj
  }),
  search: (pageNow, pageNumber, keywords) => axios({
    url: `/sysUser/queryUserByName?username=${keywords}&id=&pageNow=${pageNow}&pageNumber=${pageNumber}&sort=`,
    method: 'get'
  })
}

export default user
