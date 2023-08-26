<template>
  <el-container style="height: 100%; border: 1px solid #eee">
  <el-aside width="200px">
    <div class="logo">
      <div><img src="../assets/logo.png"/></div>
      <div>OAuth2.0</div>
    </div>
    <el-menu >
      <router-link to="/index">
        <el-menu-item>
          <i class="el-icon-s-platform"></i>
          <span>首页</span>
        </el-menu-item>
      </router-link>
      <router-link to="/user">
        <el-menu-item>
        <i class="el-icon-user"></i>
        <span>用户管理</span>
      </el-menu-item>
      </router-link>
      <router-link to="/client">
        <el-menu-item>
        <i class="el-icon-s-custom"></i>
        <span>客户端管理</span>
      </el-menu-item>
      </router-link>
    </el-menu>
  </el-aside>

  <el-container>
    <el-header style="text-align: right; font-size: 15px" >
      <span class="header">OAuth2.0后台管理系统</span>
      <span @click="logout" class="logout" ><i class="el-icon-switch-button" ></i>退出登录</span>
    </el-header>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</el-container>
</template>

<script>
import store from '@/store'
export default {
  name: 'layout-component',
  data () {
    const item = {
      date: '2016-05-02',
      name: '王小虎',
      address: '上海市普陀区金沙江路 1518 弄'
    }
    return {
      tableData: Array(20).fill(item)
    }
  },
  methods: {
    logout () {
      // 移除本地的用户信息
      this.$axios.get('/baseUser/userLogout')
        .then(res => {
          const { data } = res
          if (data.code <= 20000) {
            this.$router.push('/login')
            store.commit('user/setOnlineOnlineState', null)
            store.commit('user/setRole', null)
          }
        })
        .catch(err => {
          console.error(err)
        })
    }
  }
}
</script>

<style lang="scss" scoped>
.el-header {
    color: #333;
    line-height: 60px;
    width: 100%;
    position: relative;
  }

  .el-aside {
    color: #333;
    border-right: 1px solid #606266;
  }

  .logo {
    text-align: center;
    img {
      width: 50px;
      height: 50px;
      border-radius: 50%;
    }
  }

  .header {
    margin: 0 auto;
    font-size: 30px;
    position: absolute;
    left: 50%;
    transform: translateX(-50%);
  }
a{
  text-decoration: none;
  color:#606266 ;
}
.logout {
  cursor: pointer;
}

</style>
