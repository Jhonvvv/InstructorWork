<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button class="filter-item" style="margin-left: 10px" type="primary" icon="el-icon-edit" @click="handleCreate"> 添加 </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload"> 导出 </el-button>
    </div>
    <!-- 表格 -->
    <el-table v-loading="listLoading" :data="scholarshipList" border fit highlight-current-row height="500" style="width: 100%; margin-top: 20px">
      <el-table-column label="奖学金等级" sortable prop="level" width="180px" align="center">
        <template slot-scope="{ row }">
          <el-tag :type="row.level | levelFilter">
            {{ row.level | formatLevel }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="综测排名" sortable prop="ranking" width="110px" align="center" />
      <el-table-column label="证明材料" width="140px" align="center">
        <template slot-scope="{ $index }">
          <el-avatar shape="square" :src="fileShowList[$index]" />
        </template>
      </el-table-column>
      <el-table-column v-for="(item, index) in defColumns" :key="index" :label="item.title" :prop="item.prop" :sortable="item.sortable" :align="item.align" :min-width="item.width" />
      <el-table-column label="审核状态" align="center" class-name="status-col" width="180px">
        <template slot-scope="{ row }">
          <el-tag :type="row.approveStatus | statusFilter">
            {{ row.approveStatus | formatStatus }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="申请时间" sortable prop="createTime" width="180px" align="center" />
      <el-table-column label="操作" align="center" fixed="right" width="200px" class-name="small-padding fixed-width">
        <template slot-scope="{ row, $index }">
          <el-button type="primary" size="mini" @click="handleUpdate(row)"> 修改 </el-button>
          <el-button v-if="row.status != 'deleted'" size="mini" type="danger" @click="handleDelete(row, $index)"> 删除 </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 奖学金信息 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="55%">
      <el-form ref="scholarship" :disabled="formRequired" :model="scholarship" :rules="rules" label-position="left" label-width="100px">
        <el-row :gutter="10">
          <el-col :span="14">
            <el-form-item label="奖学金等级" prop="level">
              <el-select v-model="scholarship.level" clearable placeholder="请选择奖学金等级">
                <el-option v-for="item in levelOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="综测排名" prop="ranking">
              <el-input-number v-model="scholarship.ranking" style="width: 100%" :min="1" :max="100" :step="1" controls-position="right" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="证明材料" prop="prove">
              <el-upload class="upload-demo" action="http://localhost:8081/system/common/upload" :multiple="false" :limit="2" :on-preview="handlePreview" :on-remove="handleRemove" :on-success="upload" :file-list="proveList" list-type="picture">
                <el-button size="small" type="primary" :disabled="formRequired">点击上传</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false"> 取消 </el-button>
        <el-button type="primary" :disabled="formRequired" @click="dialogStatus === 'create' ? createData() : updateData()"> 确认 </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getScholarshipByUsername, addScholarship, editScholarship, deletetScholarship } from '@/api/scholarship' // 奖学金请求
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'

export default {
  name: 'Scholarship',
  directives: { waves },
  // TODO 可通过字典查询
  filters: {
    // 审核状态类型
    statusFilter(status) {
      const statusMap = {
        0: '',
        1: 'warning',
        2: 'success',
        3: 'danger'
      }
      return statusMap[status]
    },
    // 状态显示转换
    formatStatus(status) {
      const statusMap = {
        0: '待审核',
        1: '辅导员通过',
        2: '通过审核',
        3: '拒绝审核'
      }
      return statusMap[status]
    },
    // 奖学金等级
    levelFilter(level) {
      const levelMap = {
        0: 'danger',
        1: 'success',
        2: ''
      }
      return levelMap[level]
    },
    // 奖学金等级转换
    formatLevel(level) {
      const levelMap = {
        0: '一等奖学金',
        1: '二等奖学金',
        2: '三等奖学金'
      }
      return levelMap[level]
    }
  },

  data() {
    return {
      // 表头
      defColumns: [
        {
          title: '辅导员意见',
          align: 'center',
          width: '250px',
          prop: 'counselorOpinion',
          sortable: false
        },
        {
          title: '审核意见',
          align: 'center',
          width: '250px',
          prop: 'auditOpinion',
          sortable: false
        }
      ],
      scholarshipList: [],
      scholarship: {},
      listLoading: true,
      username: this.$store.getters.name,
      // 奖学金选项
      levelOptions: [
        { value: 0, label: '一等奖学金' },
        { value: 1, label: '二等奖学金' },
        { value: 2, label: '三等奖学金' }
      ],
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改',
        create: '添加'
      },
      formRequired: true,
      rules: {
        level: [{ required: true, message: '请选择奖学金等级', trigger: 'change' }],
        ranking: [{ required: true, message: '请输入综测排名', trigger: 'blur' }]
      },
      downloadLoading: false,
      // 文件
      fileList: [],
      fileShowList: [],
      proveList: []
    }
  },
  created() {
    this.getList()
  },
  methods: {
    handelImage() {
      this.scholarshipList.forEach(item => {
        if (item.prove !== null) this.fileShowList.push(item.prove.split(',').shift())
        else this.fileShowList.push('')
      })
    },
    upload(response, file, fileList) {
      if (fileList.length > 1) {
        this.scholarship.prove += ',' + response.result.path
      } else if (fileList.length <= 1) {
        this.scholarship.prove = response.result.path
      }
    },
    handleRemove(file, fileList) {
      const list = []
      fileList.forEach(item => {
        list.push(item.url)
      })
      this.scholarship.prove = list.join(',')
    },
    handlePreview(file) {
      console.log(file)
    },
    getList() {
      this.listLoading = true
      getScholarshipByUsername(this.username).then(response => {
        this.scholarshipList = response.result
        this.handelImage()
        this.listLoading = false
      })
    },
    handleFilter() {
      this.page = 1
      this.getList()
    },
    handleModifyStatus(row, status) {
      this.$message({
        message: '操作Success',
        type: 'success'
      })
      row.status = status
    },
    handleCreate() {
      this.fileList = []
      this.scholarship = {}
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.formRequired = false
      this.$nextTick(() => {
        this.$refs['scholarship'].clearValidate()
      })
    },
    createData() {
      this.$refs.scholarship.validate(valid => {
        if (valid) {
          this.scholarship.userId = this.username
          console.log(this.scholarship)
          addScholarship(this.scholarship).then(response => {
            this.scholarshipList.push(response.result)
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '添加成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleUpdate(row) {
      this.fileList = []
      this.scholarship = {}
      this.proveList = []
      // copy obj
      this.scholarship = row
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      if (row.approveStatus === 0) this.formRequired = false
      else this.formRequired = true
      if (this.scholarship.prove !== null) {
        const list = this.scholarship.prove.split(',')
        list.forEach(item => {
          const name = item.substring(item.lastIndexOf('/') + 1, item.lastIndexOf('.'))
          this.proveList.push({ name: name, url: item })
        })
      }
      this.$nextTick(() => {
        this.$refs['scholarship'].clearValidate()
      })
    },
    updateData() {
      this.$refs['scholarship'].validate(valid => {
        if (valid) {
          editScholarship(this.scholarship).then(response => {
            const index = this.scholarshipList.findIndex(v => v.id === this.scholarship.id)
            this.scholarshipList.splice(index, 1, this.scholarship)
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '修改成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleDelete(row, index) {
      this.$confirm('确认删除该申请?', '警告', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          if (row.approveStatus === 0) {
            deletetScholarship(row.id).then(response => {
              this.scholarshipList.splice(index, 1)
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
            })
          } else {
            this.$message({
              type: 'warning',
              message: '只能删除待审核的申请!'
            })
          }
        })
        .catch(err => {
          console.error(err)
        })
    },

    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['奖学金等级', '综测排名', '证明材料', '辅导员意见', '审核意见', '审核状态', '申请时间']
        const filterVal = ['level', 'ranking', 'prove', 'counselorOpinion', 'auditOpinion', 'approveStatus', 'createTime']
        const list = this.scholarshipList
        const data = this.formatJson(filterVal, list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: '奖学金申请信息'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v =>
        filterVal.map(j => {
          if (j === 'level') {
            return this.$options.filters.formatLevel(v[j])
          } else if (j === 'createTime') {
            return parseTime(v[j])
          } else if (j === 'approveStatus') {
            return this.$options.filters.formatStatus(v[j])
          } else {
            return v[j]
          }
        })
      )
    }
  }
}
</script>
<style lang="scss" scoped></style>
