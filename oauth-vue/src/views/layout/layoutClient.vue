<template>
<div>
  <div class="search">
    <el-input v-model="keyword" placeholder="Search" style="width: 300px;"></el-input>
    <el-button type="primary" icon="el-icon-search">搜索</el-button>
    <el-button type="primary" icon="el-icon-circle-plus-outline" @click="showAddDialog">添加</el-button>
</div>

    <el-table
    :data="tableData"
    style="width: 100%"
    border="true"
    :default-sort = "{prop: 'date', order: 'descending'}"
    >
    <el-table-column
      prop="date"
      label="序号"
      width="50"
      type="selection"
      align="center">
    </el-table-column>
    <el-table-column
      prop="date"
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
              @change="changeSwitch(scope.row)"/>
      </template>
    </el-table-column>
    <el-table-column label="操作"
    align="center">
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="handleEdit(scope.$index, scope.row)">查看</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">删除</el-button>
      </template>
    </el-table-column>
  </el-table>

  <!--表单是否可见-->
  <el-dialog title="详情" :visible.sync="formAdd">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
  <el-form-item label="客户端名称" prop="name">
    <el-input v-model="ruleForm.name"></el-input>
  </el-form-item>
  <el-form-item label="活动区域" prop="region">
    <el-select v-model="ruleForm.region" placeholder="请选择活动区域">
      <el-option label="区域一" value="shanghai"></el-option>
      <el-option label="区域二" value="beijing"></el-option>
    </el-select>
  </el-form-item>
  <el-form-item label="活动时间" required>
    <el-col :span="11">
      <el-form-item prop="date1">
        <el-date-picker type="date" placeholder="选择日期" v-model="ruleForm.date1" style="width: 100%;"></el-date-picker>
      </el-form-item>
    </el-col>
    <el-col class="line" :span="2">-</el-col>
    <el-col :span="11">
      <el-form-item prop="date2">
        <el-time-picker placeholder="选择时间" v-model="ruleForm.date2" style="width: 100%;"></el-time-picker>
      </el-form-item>
    </el-col>
  </el-form-item>
  <el-form-item label="即时配送" prop="delivery" required>
    <el-switch v-model="ruleForm.delivery"></el-switch>
  </el-form-item>
  <el-form-item label="Scope" prop="type">
    <el-checkbox-group v-model="ruleForm.type">
      <el-checkbox label="美食/餐厅线上活动" name="type"></el-checkbox>
      <el-checkbox label="地推活动" name="type"></el-checkbox>
      <el-checkbox label="线下主题活动" name="type"></el-checkbox>
      <el-checkbox label="单纯品牌曝光" name="type"></el-checkbox>
    </el-checkbox-group>
  </el-form-item>
  <el-form-item label="特殊资源" prop="resource">
    <el-radio-group v-model="ruleForm.resource">
      <el-radio label="线上品牌商赞助"></el-radio>
      <el-radio label="线下场地免费"></el-radio>
    </el-radio-group>
  </el-form-item>
  <el-form-item label="活动形式" prop="desc">
    <el-input type="textarea" v-model="ruleForm.desc"></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="submitForm('ruleForm')">确定</el-button>
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
      hide-on-single-page="true"
      background="true"
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
      currentPage: 1,
      pageNumber: 10,
      total: 0,
      formAdd: false,
      tableData: [],
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
      rules: {
        name: [
          { required: true, message: '请输入活动名称', trigger: 'blur' },
          { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
        ],
        region: [
          { required: true, message: '请选择活动区域', trigger: 'change' }
        ],
        date1: [
          { type: 'date', required: true, message: '请选择日期', trigger: 'change' }
        ],
        date2: [
          { type: 'date', required: true, message: '请选择时间', trigger: 'change' }
        ],
        type: [
          { type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change' }
        ],
        resource: [
          { required: true, message: '请选择活动资源', trigger: 'change' }
        ],
        desc: [
          { required: true, message: '请填写活动形式', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    handleEdit (index, row) {
      this.formAdd = true
      console.log(index, row)
    },
    handleDelete (index, row) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    cancle () {
      this.formAdd = false
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
      console.log(data)
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
    }
  },
  mounted: function () {
    this.init()
  }
}
</script>

<style>
.search {
  margin-bottom: 10px;
}
</style>
