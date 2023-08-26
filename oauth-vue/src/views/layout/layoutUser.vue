<template>
  <div>
    <div class="search">
    <el-input v-model="keyword" placeholder="Search" style="width: 300px;"></el-input>
    <el-button type="primary" icon="el-icon-search" @click="search">搜索</el-button>
    <el-button type="primary" icon="el-icon-circle-plus-outline" @click="formAdd = true">添加</el-button>
    <el-button type="danger" icon="el-icon-delete" @click="handleBatchDelte">批量删除</el-button>
    <el-button type="info" @click="reset">重置</el-button>
  </div>
  <el-table
    ref="multipleTable"
    :data="tableData"
    style="width: 100%"
    border
    :default-sort = "{prop: 'date', order: 'descending'}"
    >
    <el-table-column
      prop="check"
      label="序号"
      width="50"
      type="selection"
      align="center">
    </el-table-column>
    <el-table-column
      prop="userId"
      label="序号"
      width="100"
      type="index"
      align="center">
    </el-table-column>
    <el-table-column
      label="用户名"
      prop="username"
      width="180"
      align="center">
    </el-table-column>
    <el-table-column
      prop="nickName"
      label="昵称"
      width="180"
      align="center">
    </el-table-column>
    <el-table-column
      label="头像"
      align="center"
      width="180">
      <template slot-scope="scope">
        <el-avatar size="small" :src="scope.row.userImage">
        </el-avatar>
      </template>
    </el-table-column>
    <el-table-column
      prop="sex"
      label="性别"
      width="100"
      align="center">
    </el-table-column>
    <el-table-column
      prop="age"
      label="年龄"
      sortable
      width="180"
      align="center">
    </el-table-column>
    <el-table-column
      prop="role.roleDesc"
      label="角色"
      width="180"
      align="center">
    </el-table-column>
    <el-table-column label="操作"
    align="center">
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="handleEdit(scope.row)">查看</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.row.userId)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

 <!--表单是否可见-->
 <el-dialog  title="详情" :visible.sync="formEdit" :before-close="handleClose">
    <el-form :model="user" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
  <el-form-item label="用户名" prop="username">
    <el-input :disabled=" true" v-model="user.username"></el-input>
  </el-form-item>
  <el-form-item label="密码" prop="password">
    <el-input type="password" v-model="user.password"></el-input>
  </el-form-item>
  <el-form-item label="昵称" prop="nickName">
    <el-input type="input" v-model="user.nickName"></el-input>
  </el-form-item>
  <el-form-item label="生日" prop="birthday">
    <el-date-picker
      v-model="user.birthday"
      type="date"
      format="yyyy-MM-dd"
      value-format = "yyyy-MM-dd"
      placeholder="选择日期">
    </el-date-picker>
  </el-form-item>
  <el-form-item label="描述" prop="description" required>
    <el-input type="textarea" v-model="user.description"></el-input>
  </el-form-item>
  <el-form-item label="性别">
    <el-radio v-model="user.sex" label="男">男</el-radio>
    <el-radio v-model="user.sex" label="女">女</el-radio>
  </el-form-item>
  <el-form-item label="角色" prop="role">
    <template>
  <el-select  placeholder="请选择角色" v-model="user.role">
    <el-option
      v-for="role in roles"
      :key="role.roleId"
      :label="role.roleDesc"
      :value="role.roleId">
    </el-option>
  </el-select>
</template>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="onUpdate">确定</el-button>
    <el-button @click="cancle()">取消</el-button>
  </el-form-item>
</el-form>
</el-dialog>

 <!--表单是否可见-->
 <el-dialog  title="详情" :visible.sync="formAdd" :before-close="handleClose">
    <el-form :model="user" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
  <el-form-item label="用户名" prop="username">
    <el-input  v-model="user.username"></el-input>
  </el-form-item>
  <el-form-item label="密码" prop="password">
    <el-input type="password" v-model="user.password"></el-input>
  </el-form-item>
  <el-form-item label="昵称" prop="nickName">
    <el-input type="input" v-model="user.nickName"></el-input>
  </el-form-item>
  <el-form-item label="生日" prop="birthday">
    <el-date-picker
      v-model="user.birthday"
      type="date"
      format="yyyy-MM-dd"
      value-format = "yyyy-MM-dd"
      placeholder="选择日期">
    </el-date-picker>
  </el-form-item>
  <el-form-item label="描述" prop="description" required>
    <el-input type="textarea" v-model="user.description"></el-input>
  </el-form-item>
  <el-form-item label="性别">
    <el-radio v-model="user.sex" label="男">男</el-radio>
    <el-radio v-model="user.sex" label="女">女</el-radio>
  </el-form-item>
  <el-form-item label="角色" prop="role">
    <template>
  <el-select  placeholder="请选择角色" v-model="user.role">
    <el-option
      v-for="role in roles"
      :key="role.roleId"
      :label="role.roleDesc"
      :value="role.roleId">
    </el-option>
  </el-select>
</template>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="onAdd">确定</el-button>
    <el-button @click="cancle()">取消</el-button>
  </el-form-item>
</el-form>
</el-dialog>
<!--分页-->
<el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[5, 7, 10]"
      :page-size="pageNumber"
      background
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      >
    </el-pagination>
  </div>

</template>

<script>
import user from '@/api/user/user'
import role from '@/api/client/role'
export default {
  data () {
    return {
      tableData: [],
      roles: [],
      currentPage: 1,
      pageNumber: 7,
      total: 0,
      formAdd: false,
      formEdit: false,
      isSearch: false,
      ruleForm: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      user: {},
      keyword: '',
      rules: {

      }
    }
  },
  methods: {
    handleCurrentChange (val) {
      this.currentPage = val
      if (this.isSearch) {
        this.search()
      } else {
        this.queryAll()
      }
    },
    // 分页
    handleSizeChange (val) {
      this.pageNumber = val
      if (this.isSearch) {
        this.search()
      } else {
        this.queryAll()
      }
    },
    // 取消操作
    cancle () {
      this.formAdd = false
      this.formEdit = false
      this.user = {}
    },
    // 置对象为空
    handleClose (done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
          this.user = {}
        })
        .catch(_ => { })
    },
    async queryRoles () {
      const { data } = await role.queryAll()
      if (data.code === 10000) {
        const { object } = data
        this.roles = object
      } else {
        this.$message({
          type: 'warning',
          message: data.message
        })
      }
    },
    // 获取表格数据
    async queryAll () {
      const { data } = await user.findAll(this.currentPage, this.pageNumber, '')
      if (data.code === 10000) {
        const { object } = data
        this.total = parseInt(object.totalElements)
        this.tableData = object.content
      } else {
        this.$message({
          type: 'warning',
          message: data.message
        })
      }
    },
    // 搜索
    async search () {
      if (this.keyword.length === 0) {
        this.$message({
          type: 'warning',
          message: '名称不能为空'
        })
        return
      }
      this.isSearch = true
      const { data } = await user.search(this.currentPage, this.pageNumber, this.keyword)
      if (data.code === 400) {
        this.$message({
          type: 'warning',
          message: data.message
        })
      } else {
        const { object } = data
        this.total = parseInt(object.totalElements)
        this.tableData = object.content
      }
    },
    // 重置
    reset () {
      this.keyword = ''
      this.isSearch = false
      this.queryAll()
    },
    // 编辑
    handleEdit (user) {
      this.formEdit = true
      this.user = Object.assign({}, user)
      this.user.role = user.role.roleId
    },
    // 删除
    async deleteById (id) {
      const { data } = await user.deleteById(id)
      if (data.code === 10000) {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
      } else {
        this.$message({
          type: 'warning',
          message: data.message
        })
      }
    },
    // 批量删除
    async handleBatchDelte () {
      const selections = this.$refs.multipleTable.selection
      const arr = selections.map(function (item, index, seli) {
        return item.userId
      })
      if (arr.length === 0) {
        this.$message({
          type: 'warning',
          message: '未选中数据'
        })
        return
      }
      const { data } = await user.deleteByIds(arr)
      if (data.code === 10000) {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
      } else {
        this.$message({
          type: 'error',
          message: '删除失败'
        })
      }
      this.queryAll()
    },
    // 删除
    handleDelete (index) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteById(index)
        this.queryAll()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 添加
    async onAdd () {
      const obj = Object.assign({}, this.user)
      obj.sex = obj.sex === '男' ? 0 : 1
      const { data } = await user.insertUser(obj)
      if (data.code === 10000) {
        this.$message({
          type: 'success',
          message: '添加成功'
        })
        this.formAdd = false
        this.user = {}
        this.queryAll()
      } else {
        this.$message({
          type: 'warning',
          message: data.message
        })
      }
    },
    async onUpdate () {
      const obj = Object.assign({}, this.user)
      obj.sex = obj.sex === '男' ? 0 : 1
      const { data } = await user.updateUser(obj)
      if (data.code === 10000) {
        this.$message({
          type: 'success',
          message: '更新成功'
        })
        this.formEdit = false
        this.user = {}
        this.queryAll()
      } else {
        this.$message({
          type: 'warning',
          message: data.message
        })
      }
    }
  },

  mounted: function () {
    this.queryAll()
    this.queryRoles()
  }
}
</script>

<style>
.search {
  margin-bottom: 10px;
}
</style>
