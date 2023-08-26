import axios from '@/utils/request'

const index = {
  queryCount: () => axios({
    url: '/sysIndex/queryCount',
    method: 'get'
  }),
  queryMethod: () => axios({
    url: '/sysIndex/queryMethod',
    method: 'get'
  })
}

export default index
