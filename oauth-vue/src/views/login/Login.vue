<template>
    <div class="login-container">
       <div class="login login-left">

       </div>
       <div class="login">
          <div class="login-card">
            <div class="card-title">
              <div class="card-head">
                <img src="../../assets/logo.png">Login
              </div>
              <div>
                Oauth2.0 For Login
              </div>
              <el-form autocomplete="off" :model="form" :rules="rules" ref="box">

              <el-form-item label="用户名" prop="username">
                <el-input placeholder="输入用户名" v-model="form.username"></el-input>
              </el-form-item>

              <el-form-item label="密码" prop="password">
            <el-input type="password" placeholder="输入用户密码" v-model="form.password"></el-input>
            </el-form-item>

            <el-form class="tc">
          <el-button type="primary" @click="submit">登 录</el-button>
      </el-form>
      </el-form>
            </div>
          </div>
      </div>
    </div>
</template>
<script>
export default {
  name: 'login-component',
  data () {
    return {
      form: {
        username: '',
        password: ''
      },
      rules: {
        username: [
          { required: true, message: '请输入用户名', trigger: ['blur', 'change'] },
          { min: 5, max: 11, message: '长度在 5 到 11 个字符', trigger: ['blur', 'change'] }
        ],
        // 密码
        password: [
          { required: true, message: '请输入密码', trigger: ['blur', 'change'] }, // 必填
          // 5-11位 \w字母数字下划线    发现文档上下没有查询内容！分散在文章某个a标签！
          { pattern: /^\w{5,11}$/, message: '长度在5到11个 字母数字下划线', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  methods: {
    async submit () {
      const { data } = await this.$axios.post('/userLogin',
        {
          username: this.form.username,
          password: this.form.password
        }
      )
      console.log(data)
      if (data.code === 10000) {
        if (data.object.roleName === 'ADMIN') {
          this.$router.push('/user')
        } else {
          this.$router.push('/checkScope')
        }
      } else {
        this.$message({
          message: data.message,
          type: 'warning'
        })
      }
    }
  }
}
</script>

<style scoped>
.login-container {
  height: 100vh;
  display: flex;
}
 .login {
    width: 50%;
    float: left;
 }
.login-left {
  background-image: url('../../assets/loginBackground.jpg');
  background-repeat: no-repeat;
  background-size: cover;
  height: 100%;
}
.login-card {
  width: 400px;
  height: 400px;
  margin-left: 130px;
  margin-top: 100px;
  border-radius: 15px;
}
.card-title img {
  height: 40px;
  border-radius: 5px;
  margin: 0 auto;
}
.card-head {
  font-family: 'Courier New', Courier, monospace;
  font-size: 40px;
}
.card-title div{
  text-align: center;
}
.card-head {
  margin-top: 20px;
}

.login-form {
  margin-top: 1.2rem;
}
.login-card button {
  margin-top: 1rem;
  width: 100%;
}
</style>
