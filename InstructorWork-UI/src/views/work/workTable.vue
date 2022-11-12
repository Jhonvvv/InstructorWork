<template>
  <div class="app-container">
    <div class="filter-container">
      <el-select v-model="provisional" placeholder="工作性质" class="filter-item" style="width: 220px; padding-right: 10px">
        <el-option v-for="item in provisionalOptions" :key="item.value" :label="item.label" :value="item.value" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter"> 搜索 </el-button>
      <el-button class="filter-item" style="margin-left: 10px" type="primary" icon="el-icon-edit" @click="handleCreate"> 添加 </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload"> 导出 </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-document" @click="handleExportXls('工作信息模板')"> 模板 </el-button>
      <div style="display: inline-block; position: relative">
        <el-upload style="position: absolute; top: -25px; left: 10px" class="upload-demo" :action="url.importExcelUrl" multiple :limit="1" :show-file-list="false">
          <el-button class="filter-item" type="primary" icon="el-icon-upload2">导入</el-button>
        </el-upload>
      </div>
    </div>
    <el-table v-loading="listLoading" :data="workList" border fit highlight-current-row style="width: 100%; margin-top: 20px" height="500" :default-sort="{ prop: 'startTime', order: 'ascending' }">
      <el-table-column label="工作名称" prop="workName" width="110px" align="center" />
      <el-table-column label="工作类别" align="center" class-name="status-col" width="100px">
        <template slot-scope="{ row }">
          <el-tag :type="row.type | typeFilter">
            {{ row.type | formatType }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" sortable prop="startTime" width="110px" align="center" />
      <el-table-column label="工作描述" prop="remake" min-width="180px" align="center" />
      <el-table-column label="操作" align="center" fixed="right" width="230px" class-name="small-padding fixed-width">
        <template slot-scope="{ row, $index }">
          <el-button type="primary" size="mini" @click="handleUpdate(row)"> 修改 </el-button>
          <el-button v-if="row.status != 'deleted'" size="mini" type="danger" @click="handleDelete(row, $index)"> 删除 </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="55%">
      <el-form ref="work" :model="work" :rules="rules" label-position="left" label-width="85px">
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="工作名称" prop="workName">
              <el-input v-model="work.workName" placeholder="请输入工作名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工作类型" prop="type">
              <el-select v-model="work.type" clearable placeholder="请选择工作类型">
                <el-option v-for="item in typeOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="9">
            <el-form-item label="间隔(分钟)">
              <el-input-number v-model="interval" style="width: 100%" :min="15" :max="600" label="描述文字" :step="15" controls-position="right" @change="handleInterval" />
            </el-form-item>
          </el-col>
          <el-col :span="15">
            <el-form-item label-width="100px" label="工作时间范围">
              <el-time-picker v-model="dateRangeValue" style="width: 90%" value-format="HH:mm" is-range range-separator="至" start-placeholder="开始时间" end-placeholder="结束时间" placeholder="选择时间范围" @blur="handDateRange" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="开始时间" prop="startTime">
              <el-time-select v-model="work.startTime" placeholder="起始时间" :picker-options="dayTime" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工作性质" prop="addProvisional">
              <el-select v-model="addProvisional" placeholder="请选择工作性质" clearable class="filter-item" :disabled="typeDisabled">
                <el-option v-for="item in provisionalOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="工作描述" prop="remake">
              <el-input v-model="work.remake" type="textarea" placeholder="请输入工作描述" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false"> 取消 </el-button>
        <el-button type="primary" @click="dialogStatus === 'create' ? createData() : updateData()"> 确认 </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { queryWork, addWork, editWork, deleteWork, queryProvisional, addDailyWork, editProvisionalWork, deleteProvisionalWork } from '@/api/work'
import waves from '@/directive/waves' // waves directive
import { ClmListMixin } from '@/layout/mixin/ClmListMixin'
export default {
  name: 'WorkTable',
  directives: { waves },
  filters: {
    // 工作类型
    typeFilter(type) {
      const typeMap = {
        0: 'success',
        1: 'danger'
      }
      return typeMap[type]
    },
    // 工作显示转换
    formatType(type) {
      const typeMap = {
        0: '工作',
        1: '会议'
      }
      return typeMap[type]
    }
  },
  mixins: [ClmListMixin],
  data() {
    return {
      listLoading: false,
      workList: [],
      work: {},
      queryParam: {
        userId: this.$store.getters.name
      },
      username: this.$store.getters.name,
      downloadLoading: false,
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改',
        create: '添加'
      },
      rules: {
        workName: [{ required: true, message: '请工作名称', trigger: 'blur' }],
        // addProvisional: [{ required: true, message: '请选择工作类别', trigger: 'change' }],
        startTime: [{ required: true, message: '请选择工作时间', trigger: 'change' }]
      },
      typeOptions: [
        {
          label: '工作',
          value: 0
        },
        {
          label: '会议',
          value: 1
        }
      ],
      dayTime: {
        start: '07:00',
        step: '00:15',
        end: '18:30'
      },
      interval: '',
      // dateRangeValue: [new Date(2021, 12, 10, 7, 0), new Date(2021, 12, 10, 19, 0)]
      dateRangeValue: ['7:00:00', '19:00:00'],
      provisional: 0,
      addProvisional: 0,
      typeDisabled: false,
      provisionalOptions: [
        {
          label: '日常',
          value: 0
        },
        {
          label: '临时',
          value: 1
        }
      ],
      url: {
        exportXlsUrl: 'http://localhost:8081/counselor/work/exportXls',
        importExcelUrl: 'http://localhost:8081/counselor/work/importExcel'
      }
    }
  },
  created() {
    this.getWorkList()
  },
  methods: {
    handleFilter() {
      if (this.provisional === 0) {
        this.getWorkList()
      } else {
        this.getProvisionalWork()
      }
    },
    getProvisionalWork() {
      this.listLoading = true
      queryProvisional(this.username).then(response => {
        this.workList = response.result
        this.listLoading = false
      })
    },
    getWorkList() {
      this.listLoading = true
      queryWork(this.username).then(response => {
        this.workList = response.result
        this.listLoading = false
      })
    },
    handleCreate() {
      this.typeDisabled = false
      this.interval = '15'
      this.rangeValue = {}
      this.dialogFormVisible = true
      this.dialogStatus = 'create'
      this.work = {}
      this.$nextTick(() => {
        this.$refs['work'].clearValidate()
      })
    },
    handleUpdate(row) {
      this.work = {}
      this.typeDisabled = true
      this.dialogFormVisible = true
      this.dialogStatus = 'update'
      this.work = row
      console.log(this.work)
      if (row.isProvisional !== undefined || row.isProvisional === 0) {
        this.addProvisional = 1
      } else {
        this.addProvisional = 0
      }
      this.$nextTick(() => {
        this.$refs['work'].clearValidate()
      })
    },
    handDateRange() {
      this.dayTime.start = this.dateRangeValue[0]
      this.dayTime.end = this.dateRangeValue[1]
    },
    handleInterval() {
      this.dayTime.step = '00:' + this.interval
      console.log(this.dayTime)
    },
    handleDelete(row, index) {
      this.$confirm('确认删除工作?', '警告', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          console.log(row)
          if (this.provisional === 0) {
            deleteWork(row.id).then(response => {
              this.workList.splice(index, 1)
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
            })
          } else {
            deleteProvisionalWork(row.id).then(response => {
              this.workList.splice(index, 1)
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
            })
          }
        })
        .catch(err => {
          console.error(err)
        })
    },
    createData() {
      this.$refs.work.validate(valid => {
        if (valid) {
          this.work.userId = this.username
          if (this.addProvisional === 0) {
            addWork(this.work).then(response => {
              this.workList.push(response.result)
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '添加成功',
                type: 'success',
                duration: 2000
              })
            })
          } else {
            addDailyWork(this.work).then(response => {
              this.dialogFormVisible = false
              if (this.provisional === 1) {
                this.workList.push(response.result)
              }
              this.$notify({
                title: '成功',
                message: '添加成功',
                type: 'success',
                duration: 2000
              })
            })
          }
        }
      })
    },
    updateData() {
      this.$refs.work.validate(valid => {
        if (valid) {
          if (this.addProvisional === 0) {
            editWork(this.work).then(response => {
              const index = this.workList.findIndex(v => v.id === this.work.id)
              this.workList.splice(index, 1, response.result)
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '修改成功',
                type: 'success',
                duration: 2000
              })
            })
          } else {
            editProvisionalWork(this.work).then(response => {
              const index = this.workList.findIndex(v => v.id === this.work.id)
              this.workList.splice(index, 1, response.result)
              this.dialogFormVisible = false
              this.$notify({
                title: '成功',
                message: '修改成功',
                type: 'success',
                duration: 2000
              })
            })
          }
        }
      })
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['工作名称', '工作类别', '开始时间', '工作描述']
        const filterVal = ['workName', 'type', 'startTime', 'remake']
        const list = this.workList
        const data = this.formatJson(filterVal, list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: '工作信息'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v =>
        filterVal.map(j => {
          if (j === 'type') {
            return this.$options.filters.formatType(v[j])
          } else {
            return v[j]
          }
        })
      )
    }
  }
}
</script>

<style></style>
