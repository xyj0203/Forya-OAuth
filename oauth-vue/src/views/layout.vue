<template>
  <el-container style="height: 500px; border: 1px solid #eee">
  <el-aside width="200px">
    <div class="logo">
      <img src="../assets/logo.png"/>
      <span>OAuth2.0</span>
    </div>
    <el-menu >
      <router-link to="/user">
        <el-menu-item>
        <i class="el-icon-user"></i>
        <span>用户管理</span>
      </el-menu-item>
      </router-link>
      <router-link to="/scope">
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
      <span class="logout">
        <i class="el-icon-switch-button" style="margin-right: 15px"></i>
      <span @click="logout" >退出登录</span>
      </span>
    </el-header>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</el-container>
</template>

<script>
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
      this.$axios.get('/userLogout')
        .then(res => {
          const { data } = res
          if (data.code === 10001) {
            this.$router.push('/login')
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
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }

  .el-aside {
    color: #333;
  }

  .logo {
    text-align: center;
    img {
      width: 50px;
      height: 50px;
    }
  }

  .header {
    position: relative;
    right: 400px;
    font-size: 30px;
  }
a{
  text-decoration: none;
  color:#606266 ;
}
.logout {
  cursor: pointer;
}
</style>
