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
      prop="id"
      label="序号"
      width="100"
      type="index"
      align="center">
    </el-table-column>
    <el-table-column
      label="客户端名称"
      prop="clientName"
      width="180"
      align="center">
    </el-table-column>
    <el-table-column
      prop="createTime"
      label="添加时间"
      sortable
      width="180"
      align="center">
    </el-table-column>
    <el-table-column
      prop="updateTime"
      label="更新时间"
      sortable
      width="180"
      align="center">
    </el-table-column>
    <el-table-column
      label="是否启用"
      width="130"
      align="center">
      <template slot-scope="scope">
        <el-switch
              v-model="scope.row.enable"
              :active-value="1"
              :inactive-value="0"
              active-color="#02538C"
              inactive-color="#B9B9B9"
              @change="changeSwitch(scope.row.id, scope.row.enable)"/>
      </template>
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
          @click="handleDelete(scope.row.id)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <!--表单是否可见-->
  <el-dialog  title="详情" :visible.sync="formEdit" :before-close="handleClose">
    <el-form :model="client" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
  <el-form-item label="客户端名称" prop="clientName">
    <el-input v-model="client.clientName"></el-input>
  </el-form-item>
  <el-form-item label="重定向链接" prop="redirectUrl">
    <el-input type="textarea" v-model="client.redirectUrl"></el-input>
  </el-form-item>
  <el-form-item label="客户端ID" prop="clientId">
    <el-input :disabled=" true"  v-model="client.clientId"></el-input>
  </el-form-item>
  <el-form-item label="启用" prop="enable" required>
    <el-switch v-model="client.enable"></el-switch>
  </el-form-item>
  <el-form-item label="Scope" prop="scope">
    <el-cascader v-model="client.scope" ref="cascader" :props="props" :options="options"  collapse-tags clearable></el-cascader>
  </el-form-item>
  <el-form-item label="客户端描述" prop="description">
    <el-input type="textarea" v-model="client.description"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="onUpdate">确定</el-button>
    <el-button @click="cancle()">取消</el-button>
  </el-form-item>
</el-form>
  <!--添加表单-->
  </el-dialog>
    <!--表单是否可见-->
    <el-dialog title="详情" :visible.sync="formAdd" :before-close="handleClose">
    <el-form :model="client" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
  <el-form-item label="客户端名称" prop="clientName">
    <el-input v-model="client.clientName"></el-input>
  </el-form-item>
  <el-form-item label="重定向链接" prop="redirectUrl">
    <el-input type="textarea" v-model="client.redirectUrl"></el-input>
  </el-form-item>
  <el-form-item label="客户端ID" prop="clientId">
    <el-input :disabled=" true"  v-model="client.clientId"></el-input>
  </el-form-item>
  <el-form-item label="启用" prop="enable">
    <el-switch v-model="client.enable"></el-switch>
  </el-form-item>
  <el-form-item label="Scope" prop="scope">
    <el-cascader ref="cascader" :props="props" :options="options"  collapse-tags clearable></el-cascader>
  </el-form-item>
  <el-form-item label="客户端描述" prop="description">
    <el-input type="textarea" v-model="client.description"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="onAdd">确定</el-button>
    <el-button @click="cancle()">取消</el-button>
  </el-form-item>
</el-form>
  </el-dialog>
  <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="currentPage"
      :page-sizes="[5,7, 10]"
      :page-size="pageNumber"
      background
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
  </div>

</template>

<script>
import client from '@/api/client/client'
export default {
  data () {
    return {
      options: [
      ],
      props: {
        multiple: true
      },
      currentPage: 1,
      pageNumber: 7,
      total: 0,
      formAdd: false,
      formEdit: false,
      tableData: [],
      client: {
      },
      keyword: '',
      rules: {
        clientName: [
          { required: true, message: '请输入客户端名称', trigger: 'blur' },
          { min: 5, max: 20, message: '长度在 5 到 20 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    async handleCascader () {
      const { data } = await client.queryScopeAll()
      if (data.code === 10000) {
        const { object } = data
        for (const obj of object) {
          const tmp = { value: obj.id, label: obj.scopeDescription }
          // 定义孩子节点
          const children = []
          const read = { value: obj.scopeRead.id, label: obj.scopeRead.scopePermission }
          const write = { value: obj.scopeWrite.id, label: obj.scopeWrite.scopePermission }
          const readChildren = []
          for (const obj1 of obj.scopeRead.scopeList) {
            readChildren.push({ value: obj1.id, label: obj1.description })
          }
          read.children = readChildren
          const writeChildren = []
          for (const obj1 of obj.scopeWrite.scopeList) {
            writeChildren.push({ value: obj1.id, label: obj1.description })
          }
          write.children = writeChildren
          // 压入数组中
          children.push(read)
          children.push(write)
          tmp.children = children
          this.options.push(tmp)
        }
      }
    },

    async onAdd () {
      const obj = Object.assign({}, this.client)
      obj.enable = obj.enable ? 1 : 0
      const arr = this.$refs.cascader.getCheckedNodes(true)
      if (arr.length === 0) {
        this.$message({
          type: 'warning',
          message: '权限不能为空'
        })
        return
      }
      const array = []
      for (const node of arr) {
        array.push(node.value)
      }
      obj.scope = array
      const { data } = await client.insertClient(obj)
      if (data.code === 10000) {
        this.$message({
          type: 'success',
          message: '添加成功'
        })
        this.init()
      } else {
        this.$message({
          type: 'warning',
          message: '服务端有误'
        })
      }
      this.formAdd = false
      this.client = {}
    },
    async onUpdate () {
      const obj = Object.assign({}, this.client)
      obj.enable = obj.enable ? 1 : 0
      const arr = this.$refs.cascader.getCheckedNodes(true)
      if (arr.length === 0) {
        this.$message({
          type: 'warning',
          message: '权限不能为空'
        })
        return
      }
      const array = []
      for (const node of arr) {
        array.push(node.value)
      }
      obj.scope = array
      const { data } = await client.updateClient(obj)
      if (data.code === 10000) {
        this.$message({
          type: 'success',
          message: '更新成功'
        })
        this.init()
      } else {
        this.$message({
          type: 'warning',
          message: '服务端有误'
        })
      }
      this.formEdit = false
      this.client = {}
    },
    handleEdit (client) {
      this.formEdit = true
      this.client = Object.assign({}, client)
      this.client.enable = this.client.enable === 1
    },
    async handleBatchDelte () {
      const selections = this.$refs.multipleTable.selection
      const arr = selections.map(function (item, index, seli) {
        return item.id
      })
      if (arr.length === 0) {
        this.$message({
          type: 'warning',
          message: '未选中数据'
        })
        return
      }
      const { data } = await client.deleteByIds(arr)
      if (data.code === 10000) {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
      } else {
        this.$message({
          type: 'success',
          message: '删除失败'
        })
      }
      this.init()
    },
    changeSwitch (id, enable) {
      client.changeEnable(id, enable)
    },
    handleDelete (index) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteById(index)
        this.init()
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    cancle () {
      this.formAdd = false
      this.formEdit = false
      this.client = {}
    },
    reset () {
      this.keyword = ''
      this.init()
    },
    getAll () {
      return client.findAll(this.currentPage, this.pageNumber)
    },
    handleSizeChange (val) {
      this.pageNumber = val
      this.init()
    },
    handleCurrentChange (val) {
      this.currentPage = val
      this.init()
    },
    async init () {
      const { data } = await this.getAll()
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
    async deleteById (id) {
      const { data } = await client.deleteById(id)
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
    handleClose (done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
          this.client = {}
        })
        .catch(_ => { })
    },
    async search () {
      if (this.keyword.length === 0) {
        this.$message({
          type: 'warning',
          message: '名称不能为空'
        })
        return
      }
      const { data } = await client.search(0, this.pageNumber, this.keyword)
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
    }
  },
  mounted: function () {
    this.init()
    this.handleCascader()
  }
}
</script>

<style>
.search {
  margin-bottom: 10px;
}
</style>
