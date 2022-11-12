<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.realName" placeholder="学生姓名" style="width: 200px" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.departId" placeholder="班级" clearable class="filter-item" style="width: 220px; padding-right: 10px">
        <el-option v-for="item in departList" :key="item.id" :label="item.departName" :value="item.id" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter"> 搜索 </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload"> 导出 </el-button>
    </div>
    <!-- 表格 -->
    <el-table v-loading="listLoading" :data="punishmentList" border fit highlight-current-row style="width: 100%; margin-top: 20px">
      <el-table-column label="学号" sortable prop="username" width="110px" align="center" />
      <el-table-column label="姓名" prop="realName" width="120px" align="center" />
      <el-table-column label="班级" align="center" class-name="status-col" width="180px">
        <template slot-scope="{ row }">
          {{ row.departId | formatDepart(that) }}
        </template>
      </el-table-column>
      <el-table-column label="处罚类型" sortable prop="type" width="180px" align="center">
        <template slot-scope="{ row }">
          <el-tag :type="row.type | punishmentFilter">
            {{ row.type | formatPunishment }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="处罚原因" prop="reason" min-width="200px" align="center" />
      <el-table-column label="申请撤销原因" prop="objection" min-width="200px" align="center" />
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
        <template slot-scope="{ row }">
          <el-button type="primary" size="mini" @click="handleApprove(row)"> 审核 </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="page" :limit.sync="limit" @pagination="getList" />

    <!-- 奖学金信息 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="60%">
      <el-descriptions class="margin-top" :column="3" border>
        <template slot="title">
          <el-form ref="punishment" :disabled="formRequired" :model="punishment" :rules="rules" label-width="120px">
            <el-form-item label="辅导员意见" prop="counselorOpinion">
              <el-input v-model="punishment.counselorOpinion" type="textarea" autosize />
            </el-form-item>
          </el-form>
        </template>
        <template slot="extra">
          <el-button :disabled="formRequired" type="success" size="small" @click="pass">通过</el-button>
          <el-button :disabled="formRequired" type="danger" size="small" @click="reject">拒绝</el-button>
        </template>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user" />
            学生姓名
          </template>
          {{ punishment.realName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-star-off" />
            学号
          </template>
          {{ punishment.username }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-office-building" />
            班级
          </template>
          {{ punishment.departId | formatDepart(that) }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-star-on" />
            处罚类型
          </template>
          <el-tag :type="punishment.type | punishmentFilter">
            {{ punishment.type | formatPunishment }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-time" />
            申请时间
          </template>
          {{ punishment.createTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-check" />
            审核状态
          </template>
          <el-tag :type="punishment.approveStatus | statusFilter">
            {{ punishment.approveStatus | formatStatus }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-chat-dot-round" />
            处罚原因
          </template>
          {{ punishment.reason }}
        </el-descriptions-item>
      </el-descriptions>
      <el-descriptions class="margin-top" direction="vertical" :column="3" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-chat-dot-round" />
            申请撤销原因
          </template>
          {{ punishment.objection }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-chat-dot-round" />
            辅导员意见
          </template>
          {{ punishment.counselorOpinion }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-chat-dot-round" />
            审核意见
          </template>
          {{ punishment.auditOpinion }}
        </el-descriptions-item>
      </el-descriptions>
      <el-descriptions class="margin-top" direction="vertical" :column="1" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-picture-outline" />
            证明材料
          </template>
          <div class="demo-image_lazy">
            <el-image v-for="url in fileList" :key="url" :src="url" :preview-src-list="fileList" lazy />
          </div>
        </el-descriptions-item>
      </el-descriptions>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false"> 取消 </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getRevokePunishmentByCounselor, passRevokePunishment, rejectRevokePunishment } from '@/api/rewardAndPunishment' // 奖惩请求
import { getDepartByUsername } from '@/api/depart' // 部门
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'RevokePunishment',
  directives: { waves },
  components: { Pagination },
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
        0: '待审核',
        1: '辅导员通过',
        2: '通过审核',
        3: '拒绝审核'
      }
      return statusMap[status]
    },
    // 部门过滤
    formatDepart(depart, that) {
      var departName = that.departList.filter(item => {
        if (depart === item.id) return item
      })
      if (departName.length === 0) {
        return ''
      }
      return departName[0].departName
    }
  },

  data() {
    return {
      that: this,
      departList: [],
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
      listLoading: true,
      total: 0,
      page: 1,
      limit: 10,
      listQuery: {
        realName: null,
        username: this.$store.getters.name,
        departId: null
      },
      username: this.$store.getters.name,
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        approve: '审核'
      },
      formRequired: true,
      rules: {
        counselorOpinion: [{ required: true, message: '请输入辅导员意见', trigger: 'blur' }]
      },
      downloadLoading: false,
      // 文件
      fileList: [],
      fileShowList: []
    }
  },
  created() {
    this.getList()
    this.getDeparts()
  },
  methods: {
    pass() {
      this.$refs.punishment.validate(valid => {
        if (valid) {
          this.punishment.counselorName = this.username
          passRevokePunishment(this.punishment).then(response => {
            this.punishment = response.result
            const index = this.punishmentList.findIndex(v => v.id === this.punishment.id)
            this.punishmentList.splice(index, 1, this.punishment)
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '通过申请成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    reject() {
      this.$refs.punishment.validate(valid => {
        if (valid) {
          this.punishment.counselorName = this.username
          rejectRevokePunishment(this.punishment).then(response => {
            this.punishment = response.result
            const index = this.punishmentList.findIndex(v => v.id === this.punishment.id)
            this.punishmentList.splice(index, 1, this.punishment)
            this.dialogFormVisible = false
            this.$notify({
              title: '成功',
              message: '拒绝申请成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    handelImage() {
      this.punishmentList.forEach(item => {
        if (item.prove !== null) this.fileShowList.push(item.prove.split(',').shift())
        else this.fileShowList.push('')
      })
    },
    async getDeparts() {
      await getDepartByUsername(this.$store.getters.name).then(response => {
        this.departList = response.result
      })
    },
    getList() {
      this.listLoading = true
      getRevokePunishmentByCounselor(this.page, this.limit, this.listQuery).then(response => {
        this.punishmentList = response.result.records
        this.total = response.result.total
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
    handleApprove(row) {
      this.fileList = []
      this.punishment = {}
      // copy obj
      this.punishment = row
      this.dialogStatus = 'approve'
      this.dialogFormVisible = true
      if (this.punishment.prove !== null || this.punishment.prove !== undefined) {
        this.fileList = this.punishment.prove.split(',')
      }
      if (this.punishment.approveStatus === 0) this.formRequired = false
      else this.formRequired = true
      this.$nextTick(() => {
        this.$refs['punishment'].clearValidate()
      })
    },

    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['学生学号', '学生姓名', '班级', '处罚类型', '处罚原因', '申请撤销原因', '辅导员意见', '审核意见', '审核状态', '申请时间']
        const filterVal = ['username', 'realName', 'departId', 'type', 'reason', 'objection', 'counselorOpinion', 'auditOpinion', 'approveStatus', 'createTime']
        const list = this.punishmentList
        const data = this.formatJson(filterVal, list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: '撤销处罚申请信息'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v =>
        filterVal.map(j => {
          if (j === 'type') {
            return this.$options.filters.formatPunishment(v[j])
          } else if (j === 'departId') {
            return this.$options.filters.formatDepart(v[j], this)
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
<style lang="scss" scoped>
.demo-image_lazy {
  height: 300px;
  overflow-y: auto;
}
.demo-image_lazy .el-image {
  display: block;
  min-height: 200px;
  margin-bottom: 10px;
}
</style>
