<template>
<div>
    <el-card class="box-card">
  <div slot="header" class="clearfix">
    <img src="../../assets/logo.png"><span>请勾选您要授予的权限</span>
  </div>
  <!-- <div>
    <el-checkbox v-model="personInformation" label="个人信息(允许应用访问你的基本信息)" border></el-checkbox>
  </div> -->
  <div class="option-wrapper">
    <el-tree
  :data="data"
  ref="tree"
  show-checkbox
  node-key="id"
  :default-checked-keys="hasApprove"
  :props="defaultProps">
</el-tree>
  </div>
  <div>
    <el-button type="primary" class="grant" @click="grantPermission">授予权限</el-button>
    <el-button type="warning" class="grant" @click="grantCancel">取消授权</el-button>
  </div>

</el-card>

</div>
</template>

<script>
import oauth from '@/api/client/oauth'

export default {
  data () {
    return {
      personInformation: true,
      activeNames: ['1'],
      hasApprove: [],
      data: [],
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    }
  },
  methods: {
    // 允许授权
    async grantPermission () {
      const arr = this.$refs.tree.getCheckedKeys(true)
      if (arr.length === 0) {
        this.$message({
          message: '未选择任何数据',
          type: 'warning'
        })
        return
      }
      console.log(this.$refs.tree.getCheckedKeys(true))
      const { data } = await oauth.submit(arr)
      if (data.code === 10000) {
        window.location.href = data.object
      } else {
        this.$message({
          message: data.message,
          type: 'warning'
        })
      }
    },
    // 取消授权
    grantCancel () {
      this.$router.push('/login')
    },
    handleChange (val) {
      console.log(val)
    },
    async queryClientScope () {
      const { data } = await oauth.getClientScope()
      const consent = data.object.consent
      for (const obj of consent) {
        // 定义大的分类
        const tmp = { id: obj.id, label: '允许访问您的' + obj.scopeDescription }
        const children = []
        for (const obj1 of obj.scopeList) {
          // 定义子属性
          const property = {}
          const childs = []
          property.label = obj1.propertyName
          if (obj1.readId === undefined) {
            property.id = -obj1.writeId
            childs.push({ id: obj1.writeId, label: '写' })
          }
          if (obj1.writeId === undefined) {
            property.id = -obj1.readId
            childs.push({ id: obj1.readId, label: '读' })
          }
          if (obj1.writeId !== undefined && obj1.readId !== undefined) {
            property.id = -obj1.writeId
            childs.push({ id: obj1.writeId, label: '写' })
            property.id = -obj1.readId
            childs.push({ id: obj1.readId, label: '读' })
          }
          property.children = childs
          children.push(property)
        }
        tmp.children = children
        this.data.push(tmp)
      }
      this.hasApprove = data.object.hasApprove
    }
  },
  mounted: function () {
    this.queryClientScope()
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
  .cancel {
    margin-left: 50px;
  }
</style>
