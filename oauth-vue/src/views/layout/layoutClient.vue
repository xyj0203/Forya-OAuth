<template>
<div>
  <div class="search">
    <el-input v-model="keyword" placeholder="Search" style="width: 300px;"></el-input>
    <el-button type="primary" icon="el-icon-search">搜索</el-button>
    <el-button type="primary" icon="el-icon-circle-plus-outline" @click="formAdd = true">添加</el-button>
    <el-button type="danger" icon="el-icon-delete" @click="handleBatchDelte">批量删除</el-button>
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
      prop="update_time"
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
  <el-dialog title="详情" :visible.sync="formEdit">
    <el-form :model="client" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
  <el-form-item label="客户端名称" prop="clientName">
    <el-input v-model="client.clientName"></el-input>
  </el-form-item>
  <el-form-item label="重定向链接" prop="redirectUrl">
    <el-input type="textarea" v-model="client.description"></el-input>
  </el-form-item>
  <el-form-item label="客户端ID" prop="clientId">
    <el-input :disabled=" true"  v-model="client.clientId"></el-input>
  </el-form-item>
  <el-form-item label="启用" prop="enable" required>
    <el-switch v-model="client.enable"></el-switch>
  </el-form-item>
  <el-form-item label="Scope" prop="type">
    <el-cascader-panel :options="options"></el-cascader-panel>
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
    <el-dialog title="详情" :visible.sync="formAdd">
    <el-form :model="client" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
  <el-form-item label="客户端名称" prop="clientName">
    <el-input v-model="client.clientName"></el-input>
  </el-form-item>
  <el-form-item label="重定向链接" prop="redirectUrl">
    <el-input type="textarea" v-model="client.description"></el-input>
  </el-form-item>
  <el-form-item label="客户端ID" prop="clientId">
    <el-input :disabled=" true"  v-model="client.clientId"></el-input>
  </el-form-item>
  <el-form-item label="启用" prop="enable" required>
    <el-switch v-model="client.enable"></el-switch>
  </el-form-item>
  <el-form-item label="Scope" prop="type">
    <el-cascader-panel :options="options" :props="cascaderProps"></el-cascader-panel>
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
      :page-sizes="[1, 3, 5, 10]"
      :page-size="pageNumber"
      hide-on-single-page
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
    const that = this
    return {
      currentPage: 1,
      pageNumber: 10,
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
      },
      /** 配置级联面板 */
      cascaderProps: {
        multiple: true,
        lazy: true,
        lazyLoad (node, resolve) {
          const { value } = node
          setTimeout(() => {
            const nodes = that.queryScopeProperty(value)
            // 通过调用resolve将子节点数据返回，通知组件数据加载完成
            nodes.then(res => {
              console.log(Array.from(res))
              resolve(Array.from(res))
            })
          }, 1000)
        }
      },
      options: [
      ]
    }
  },
  methods: {
    async queryScopeProperty (id) {
      const { data } = await client.queryScopeProperty(id)
      const nodes = []
      if (data.code === 10000) {
        const { object } = data
        for (const obj of object) {
          nodes.push({
            value: obj.id,
            label: obj.description + (obj.behavior === 0 ? '-读' : '-写'),
            leaf: true
          })
        }
      }
      return nodes
    },
    async queryScope () {
      const { data } = await client.queryScope()
      if (data.code === 10000) {
        const { object } = data
        for (const obj of object) {
          this.options.push({ value: obj.id, label: obj.scopeDescription, leaf: false })
        }
      }
    },
    async onAdd () {
      const obj = Object.assign({}, client)
      this.client = {}
      await client.insertClient(obj)
    },
    async onUpdate () {
      const obj = Object.assign({}, client)
      this.client = {}
      await client.updateClient(obj)
    },
    handleEdit (client) {
      this.formEdit = true
      this.client = Object.assign({}, client)
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
        this.total = object.totalElements
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
    }
  },
  mounted: function () {
    this.init()
    this.queryScope()
  }
}
</script>

<style>
.search {
  margin-bottom: 10px;
}
</style>
