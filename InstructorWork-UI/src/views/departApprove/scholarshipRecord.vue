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
    <el-table v-loading="listLoading" :data="scholarshipList" border fit highlight-current-row style="width: 100%; margin-top: 20px">
      <el-table-column label="辅导员" prop="counselorName" width="110px" align="center" />
      <el-table-column label="学生学号" sortable prop="username" width="110px" align="center" />
      <el-table-column label="学生姓名" prop="realName" width="120px" align="center" />
      <el-table-column label="班级" align="center" class-name="status-col" width="180px">
        <template slot-scope="{ row }">
          {{ row.departId | formatDepart(that) }}
        </template>
      </el-table-column>
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
      <el-table-column label="审核前状态" align="center" class-name="status-col" width="180px">
        <template slot-scope="{ row }">
          <el-tag :type="row.beforeStatus | beforeStatusFilter">
            {{ row.beforeStatus | formatBeforeStatus }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" class-name="status-col" width="180px">
        <template slot-scope="{ row }">
          <el-tag :type="row.currentStatus | currentStatusFilter">
            {{ row.currentStatus | formatCurrentStatus }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="审核后状态" align="center" class-name="status-col" width="180px">
        <template slot-scope="{ row }">
          <el-tag :type="row.afterStatus | statusFilter">
            {{ row.afterStatus | formatStatus }}
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
          <el-form ref="scholarship" :model="scholarship" :rules="rules" label-width="120px">
            <el-form-item label="审核意见" prop="auditOpinion">
              <el-input v-model="scholarship.auditOpinion" type="textarea" autosize />
            </el-form-item>
          </el-form>
        </template>
        <template slot="extra">
          <el-button type="success" size="small" @click="pass">通过</el-button>
          <el-button type="danger" size="small" @click="reject">拒绝</el-button>
        </template>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user" />
            学生姓名
          </template>
          {{ scholarship.realName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-star-off" />
            学生学号
          </template>
          {{ scholarship.username }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-office-building" />
            部门
          </template>
          {{ scholarship.departId | formatDepart(that) }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-medal" />
            奖学金等级
          </template>
          <el-tag :type="scholarship.level | levelFilter">
            {{ scholarship.level | formatLevel }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-trophy" />
            综测排名
          </template>
          {{ scholarship.ranking }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-time" />
            申请时间
          </template>
          {{ scholarship.createTime }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user" />
            辅导员
          </template>
          {{ scholarship.counselorName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-chat-dot-round" />
            辅导员意见
          </template>
          {{ scholarship.counselorOpinion }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-check" />
            审核前状态
          </template>
          <el-tag :type="scholarship.beforeStatus | beforeStatusFilter">
            {{ scholarship.beforeStatus | formatBeforeStatus }}
          </el-tag>
        </el-descriptions-item>
      </el-descriptions>
      <el-descriptions class="margin-top" direction="vertical" :column="2" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-check" />
            审核状态
          </template>
          <el-tag :type="scholarship.currentStatus | currentStatusFilter">
            {{ scholarship.currentStatus | formatCurrentStatus }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-s-check" />
            审核后状态
          </template>
          <el-tag :type="scholarship.afterStatus | statusFilter">
            {{ scholarship.afterStatus | formatStatus }}
          </el-tag>
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-chat-dot-round" />
            审核意见
          </template>
          {{ scholarship.auditOpinion }}
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
import { getScholarshipApproveByCounselor, passScholarship, rejectScholarship } from '@/api/scholarshipApprove' // 奖学金记录请求
import { getDepartByUsername } from '@/api/depart' // 部门
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'ScholarshipApprove',
  directives: { waves },
  components: { Pagination },
  // TODO 可通过字典查询
  filters: {
    // 审核前状态类型
    beforeStatusFilter(status) {
      const statusMap = {
        0: 'success',
        1: 'danger'
      }
      return statusMap[status]
    },

    // 当前审核状态类型
    currentStatusFilter(status) {
      const statusMap = {
        0: '',
        1: 'info'
      }
      return statusMap[status]
    },

    // 审核状态类型
    statusFilter(status) {
      const statusMap = {
        null: '',
        0: 'success',
        1: 'danger'
      }
      return statusMap[status]
    },

    // 审核前状态显示转换
    formatBeforeStatus(status) {
      const statusMap = {
        0: '辅导员通过',
        1: '辅导员拒绝'
      }
      return statusMap[status]
    },

    // 当前审核状态显示转换
    formatCurrentStatus(status) {
      const statusMap = {
        0: '待审核',
        1: '已审核'
      }
      return statusMap[status]
    },

    // 状态显示转换
    formatStatus(status) {
      const statusMap = {
        null: '待审核',
        0: '通过',
        1: '拒绝'
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
      scholarshipList: [],
      scholarship: {},
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
      rules: {
        auditOpinion: [{ required: true, message: '请输入审核意见', trigger: 'blur' }]
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
      this.$refs.scholarship.validate(valid => {
        if (valid) {
          passScholarship(this.scholarship).then(response => {
            this.scholarship = response.result
            const index = this.scholarshipList.findIndex(v => v.id === this.scholarship.id)
            this.scholarshipList.splice(index, 1, this.scholarship)
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
      this.$refs.scholarship.validate(valid => {
        if (valid) {
          rejectScholarship(this.scholarship).then(response => {
            this.scholarship = response.result
            const index = this.scholarshipList.findIndex(v => v.id === this.scholarship.id)
            this.scholarshipList.splice(index, 1, this.scholarship)
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
      this.scholarshipList.forEach(item => {
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
      getScholarshipApproveByCounselor(this.page, this.limit, this.listQuery).then(response => {
        this.scholarshipList = response.result.records
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
      this.scholarship = {}
      // copy obj
      this.scholarship = row
      this.dialogStatus = 'approve'
      this.dialogFormVisible = true
      if (this.scholarship.prove !== null) {
        this.fileList = this.scholarship.prove.split(',')
      }
      this.$nextTick(() => {
        this.$refs['scholarship'].clearValidate()
      })
    },

    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['辅导员', '学生学号', '学生姓名', '奖学金等级', '综测排名', '证明材料', '辅导员意见', '审核意见', '审核前状态', '审核状态', '审核后状态', '申请时间']
        const filterVal = ['counselorName', 'username', 'realName', 'level', 'ranking', 'prove', 'counselorOpinion', 'auditOpinion', 'beforeStatus', 'currentStatus', 'afterStatus', 'createTime']
        const list = this.scholarshipList
        const data = this.formatJson(filterVal, list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: '奖学金申请记录信息'
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
          } else if (j === 'beforeStatus') {
            return this.$options.filters.formatBeforeStatus(v[j])
          } else if (j === 'currentStatus') {
            return this.$options.filters.formatCurrentStatus(v[j])
          } else if (j === 'afterStatus') {
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
