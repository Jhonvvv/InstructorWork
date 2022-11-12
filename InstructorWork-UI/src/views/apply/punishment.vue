<template>
  <div class="app-container">
    <div class="app-container">
      <div class="filter-container">
        <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload"> 导出 </el-button>
      </div>
      <!-- 表格 -->
      <el-table v-loading="listLoading" :data="punishmentList" border fit highlight-current-row height="500" style="width: 100%; margin-top: 20px">
        <el-table-column label="处罚类型" sortable prop="type" width="200px" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="row.type | punishmentFilter">
              {{ row.type | formatPunishment }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="处罚原因" prop="reason" min-width="350px" align="center" />
        <el-table-column label="是否撤销" prop="isRevoke" width="200px" align="center">
          <template slot-scope="{ row }">
            <el-tag :type="row.isRevoke | isRevokeFilter">
              {{ row.isRevoke | formatIsRevoke }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="处罚时间" prop="createTime" sortable width="150px" align="center" />
        <el-table-column label="操作" align="center" fixed="right" width="220px" class-name="small-padding fixed-width">
          <template slot-scope="{ row }">
            <el-button type="primary" size="mini" @click="handleUpdate(row)"> 撤销处罚申请 </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 处罚信息和申请信息 -->
      <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="55%">
        <el-descriptions class="margin-top" :column="2" border title="处罚信息">
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-star-on" />
              处罚等级
            </template>
            <el-tag :type="punishment.type | punishmentFilter">
              {{ punishment.type | formatPunishment }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-info" />
              处罚原因
            </template>
            {{ punishment.reason }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-collection" />
              是否撤销
            </template>
            <el-tag :type="punishment.isRevoke | isRevokeFilter">
              {{ punishment.isRevoke | formatIsRevoke }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-time" />
              处罚时间
            </template>
            {{ punishment.createTime }}
          </el-descriptions-item>
        </el-descriptions>
        <el-descriptions class="margin-top" style="margin-top: 20px" :column="2" border title="申请撤销处罚信息">
          <template slot="extra">
            <el-button size="small" :disabled="formRequired" type="primary" @click="handleDelete()">删除</el-button>
          </template>

          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-time" />
              申请时间
            </template>
            {{ revokePunishment.createTime }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-s-check" />
              申请状态
            </template>
            <el-tag :type="revokePunishment.approveStatus | statusFilter">
              {{ revokePunishment.approveStatus | formatStatus }}
            </el-tag>
          </el-descriptions-item>
        </el-descriptions>
        <el-descriptions class="margin-top" direction="vertical" :column="2" border>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-chat-dot-round" />
              辅导员意见
            </template>
            {{ revokePunishment.counselorOpinion }}
          </el-descriptions-item>
          <el-descriptions-item>
            <template slot="label">
              <i class="el-icon-chat-dot-round" />
              审核意见
            </template>
            {{ revokePunishment.auditOpinion }}
          </el-descriptions-item>
        </el-descriptions>
        <el-form ref="punishment" style="margin-top: 20px" :disabled="formRequired" :model="punishment" :rules="rules" label-position="left" label-width="150px">
          <el-row :gutter="10">
            <el-col :span="24">
              <el-form-item label="申请撤销处罚原因" prop="objection">
                <el-input v-model="punishment.objection" type="textarea" :rows="2" placeholder="请输入内容" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="24">
              <el-form-item label="证明材料" prop="prove">
                <el-upload class="upload-demo" action="http://localhost:8081/system/common/upload" :multiple="false" :limit="2" :on-preview="handlePreview" :on-remove="handleRemove" :on-success="upload" :file-list="fileList" list-type="picture">
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
  </div>
</template>

<script>
import { queryPunishmentHisByUsername, queryRevokePunishment, applyRevoke, deletePunishment } from '@/api/rewardAndPunishment' // 处罚请求
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'

export default {
  name: 'Punishment',
  directives: { waves },
  // TODO 可通过字典查询
  filters: {
    // 处罚类型
    punishmentFilter(punishment) {
      const punishmentMap = {
        0: '',
        1: '',
        2: 'info',
        3: 'warning',
        4: 'danger',
        5: 'danger'
      }
      return punishmentMap[punishment]
    },
    // 处罚显示转换
    formatPunishment(punishment) {
      const punishmentMap = {
        0: '警告',
        1: '严重警告',
        2: '记过',
        3: '留校查看',
        4: '劝退',
        5: '开除学籍'
      }
      return punishmentMap[punishment]
    },
    // 是否撤销状态类型
    isRevokeFilter(isRevoke) {
      const isRevokeMap = {
        0: 'danger',
        1: 'success'
      }
      return isRevokeMap[isRevoke]
    },
    //  是否撤销状态显示
    formatIsRevoke(isRevoke) {
      const isRevokeMap = {
        0: '处罚中',
        1: '已撤销'
      }
      return isRevokeMap[isRevoke]
    },
    // 审核状态类型
    statusFilter(status) {
      const statusMap = {
        undefined: '',
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
        undefined: '未申请',
        0: '待审核',
        1: '辅导员通过',
        2: '通过审核',
        3: '拒绝审核'
      }
      return statusMap[status]
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
      punishmentList: [],
      punishment: {},
      revokePunishment: {},
      listLoading: true,
      username: this.$store.getters.name,
      punishmentType: [
        { value: 0, label: '警告' },
        { value: 1, label: '严重警告' },
        { value: 2, label: '记过' },
        { value: 3, label: '留校查看' },
        { value: 4, label: '劝退' },
        { value: 5, label: '开除学籍' }
      ],
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改',
        create: '添加'
      },
      formRequired: true,
      rules: {
        objection: [{ required: true, message: '请输入撤销处罚原因', trigger: 'blur' }]
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
      this.punishmentList.forEach(item => {
        if (item.prove !== null) this.fileShowList.push(item.prove.split(',').shift())
        else this.fileShowList.push('')
      })
    },
    upload(response, file, fileList) {
      if (fileList.length > 1) {
        this.punishment.prove += ',' + response.result.path
      } else if (fileList.length <= 1) {
        this.punishment.prove = response.result.path
      }
    },
    handleRemove(file, fileList) {
      const list = []
      fileList.forEach(item => {
        list.push(item.url)
      })
      this.punishment.prove = list.join(',')
    },
    handlePreview(file) {
      console.log(file)
    },
    getList() {
      this.listLoading = true
      queryPunishmentHisByUsername(this.username).then(response => {
        this.punishmentList = response.result
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
    handleUpdate(row) {
      this.fileList = []
      this.punishment = {}
      this.revokePunishment = {}
      // copy obj
      this.punishment = row
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['punishment'].clearValidate()
      })
      queryRevokePunishment(this.punishment.id).then(response => {
        if (response.result !== null) {
          this.revokePunishment = response.result
          this.punishment.objection = this.revokePunishment.objection
          this.punishment.prove = this.revokePunishment.prove
          if (this.revokePunishment.approveStatus === 0) this.formRequired = false
          else if (this.revokePunishment.approveStatus === 2) this.formRequired = true
          if (this.revokePunishment.prove !== null) {
            const list = this.revokePunishment.prove.split(',')
            list.forEach(item => {
              const name = item.substring(item.lastIndexOf('/') + 1, item.lastIndexOf('.'))
              this.proveList.push({ name: name, url: item })
            })
          }
        }
      })
      if (this.revokePunishment.approveStatus === undefined) this.formRequired = false
    },
    updateData() {
      this.$refs['punishment'].validate(valid => {
        if (valid) {
          applyRevoke(this.punishment).then(response => {
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '操作成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handleDelete() {
      this.$confirm('确认删除该申请?', '警告', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          if (this.revokePunishment.approveStatus === 0) {
            deletePunishment(this.revokePunishment.id).then(response => {
              this.revokePunishment = {}
              this.punishment.prove = ''
              this.punishment.objection = ''
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
        const tHeader = ['处罚类型', '处罚原因', '申请时间']
        const filterVal = ['type', 'reason', 'createTime']
        const list = this.punishmentList
        const data = this.formatJson(filterVal, list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: '处罚信息'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v =>
        filterVal.map(j => {
          if (j === 'type') {
            return this.$options.filters.formatPunishment(v[j])
          } else if (j === 'createTime') {
            return parseTime(v[j])
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
