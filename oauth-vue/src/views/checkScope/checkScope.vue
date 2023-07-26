<template>
<div>
    <el-card class="box-card">
  <div slot="header" class="clearfix">
    <img src="../../assets/logo.png"><span>请勾选您要授予的权限</span>
  </div>
  <div>
    <el-checkbox v-model="personInformation" label="个人信息(允许应用访问你的基本信息)" border></el-checkbox>
  </div>
  <el-divider></el-divider>
  <!-- <div style="margin-top: 20px">
    <el-checkbox v-model="checked1" label="" border></el-checkbox>
  </div> -->
  <el-button type="primary" class="grant" @click="grantPermission">授予权限</el-button>
</el-card>
</div>
</template>

<script>
export default {
  data () {
    return {
      personInformation: true
    }
  }
}
</script>

<style>
.text {
    font-size: 14px;
  }

  .item {
    margin-bottom: 18px;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 480px;
  }

  .clearfix img {
    height: 40px;
  border-radius: 5px;
  margin: 0 auto;
  }
  .clearfix span {
    font-size: 25px;
    font-family: 'Gill Sans', 'Gill Sans MT', Calibri, 'Trebuchet MS', sans-serif;
  }
  .box-card {
    position: absolute;
    margin-left: 35%;
    margin-top: 10%;
  }
</style>
