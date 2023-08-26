import axios from '@/utils/request'

const role = {
  queryRole: (id) => axios({
    url: `/user/role/${id}`,
    method: 'get'
  }),
  queryAll: () => axios({
    url: '/sysRole/queryAll',
    method: 'get'
  })
}

export default role
