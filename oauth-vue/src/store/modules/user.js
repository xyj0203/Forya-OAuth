import oauth from '@/api/client/oauth'
import role from '@/api/client/role'

const user = {
  namespaced: true,
  state: {
    onlineState: localStorage.getItem('onlineState'),
    role: localStorage.getItem('role')
  },
  mutations: {
    setOnlineOnlineState (state, onlineState) {
      state.onlineState = onlineState
      localStorage.setItem('onlineState', onlineState)
    },
    setRole (state, role) {
      state.role = role
      localStorage.setItem('role', role)
    }
  },
  actions: {
    async fetchOnlineState (context) {
      const { data } = await oauth.hasLogin()
      if (data.code < 20000) {
        console.log(data)
        context.commit('setOnlineOnlineState', data.object)
      }
    },
    async fetchRole (context, roleId) {
      const { data } = await role.queryRole(roleId)
      if (data.code < 20000) {
        context.commit('setRole', data.object.roleName)
      }
    }
  },
  getters: {
    getOnlineOnlineState: state => {
      return state.onlineState
    },
    getRole: state => {
      return state.role
    }
  }
}

export default user
