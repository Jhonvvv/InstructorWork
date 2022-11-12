<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.realName" placeholder="学生姓名" style="width: 200px" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.departId" placeholder="班级" clearable class="filter-item" style="width: 220px; padding-right: 10px">
        <el-option v-for="item in departList" :key="item.id" :label="item.departName" :value="item.id" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter"> 搜索 </el-button>
      <el-button class="filter-item" style="margin-left: 10px" type="primary" icon="el-icon-edit" @click="handleCreate"> 添加 </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload"> 导出 </el-button>
    </div>
    <!-- 表格 -->
    <el-table v-loading="listLoading" :data="studentList" border fit highlight-current-row style="width: 100%; margin-top: 20px">
      <el-table-column label="学号" sortable prop="username" width="110px" align="center" />
      <el-table-column label="头像" width="110px" align="center">
        <template slot-scope="{ row }">
          <el-avatar shape="square" :src="row.avatar" />
        </template>
      </el-table-column>
      <el-table-column label="性别" width="70px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.sex | formatSex }}</span>
        </template>
      </el-table-column>
      <el-table-column v-for="(item, index) in defColumns" :key="index" :label="item.title" :prop="item.prop" :sortable="item.sortable" :align="item.align" :width="item.width" />
      <el-table-column label="班级" align="center" class-name="status-col" width="180px">
        <template slot-scope="{ row }">
          {{ row.departId | formatDepart(that) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{ row, $index }">
          <el-button type="primary" size="mini" @click="handleUpdate(row)"> 修改 </el-button>
          <el-button v-if="row.status != 'deleted'" size="mini" type="danger" @click="handleDelete(row, $index)"> 删除 </el-button>
          <el-button type="success" size="mini" @click="handleReword(row)"> 奖惩 </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="page" :limit.sync="limit" @pagination="getList" />

    <!-- 学生信息 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="55%">
      <el-form ref="user" :model="user" :rules="rules" label-position="left" label-width="85px">
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="学号" prop="username">
              <el-input v-model="user.username" placeholder="请输入学号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="学生姓名" prop="realName">
              <el-input v-model="user.realName" placeholder="请输入学生姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="身份证号" prop="idCard">
              <el-input v-model="user.idCard" placeholder="请输入身份证号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model.number="user.phone" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="电子邮箱" prop="email">
              <el-input v-model.number="user.email" placeholder="请输入电子邮箱" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-select v-model="user.sex" clearable placeholder="请选择性别">
                <el-option v-for="item in sexOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="出生日期" prop="birthday">
              <el-date-picker v-model="user.birthday" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所属部门" prop="departId">
              <el-select ref="departSelect" v-model="user.departId" clearable>
                <el-option v-for="item in departList" :key="item.id" :label="item.departName" :value="item.id" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="监护人姓名" prop="guardian">
              <el-input v-model="user.guardian" placeholder="请输入监护人姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="监护人电话" prop="guardianTel">
              <el-input v-model.number="user.guardianTel" placeholder="请输入监护人电话" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="家庭地址" prop="status">
              <el-input v-model="user.address" placeholder="请输入家庭地址" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="头像" prop="avatar">
              <el-upload class="upload-demo" action="http://localhost:8081/system/common/upload" :multiple="false" :limit="1" :on-preview="handlePreview" :on-remove="handleRemove" :on-success="upload" :file-list="fileList" list-type="picture">
                <el-button size="small" type="primary">点击上传</el-button>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false"> 取消 </el-button>
        <el-button type="primary" @click="dialogStatus === 'create' ? createData() : updateData()"> 确认 </el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel" />
        <el-table-column prop="pv" label="Pv" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">确认</el-button>
      </span>
    </el-dialog>
    <!-- 奖罚 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogTableVisible" width="55%">
      <el-tabs v-model="activeName" @tab-click="handleTab">
        <el-tag>{{ currentStudent.realName }}</el-tag>
        <el-button class="filter-item" style="float: right" type="primary" size="small" icon="el-icon-edit-outline" @click="dialogStatus === 'reward' ? handleStudentReward() : handleStudentPunishment()"> 申请 </el-button>
        <!-- 奖励 -->
        <el-tab-pane label="奖励管理" name="reward">
          <el-table :data="rewardList" border fit height="350" highlight-current-row style="width: 100%; margin-top: 20px">
            <el-table-column label="奖励类型" prop="type" width="110px" align="center">
              <template slot-scope="{ row }">
                <el-tag :type="row.type | rewardFilter">
                  {{ row.type | formatReward }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="奖励原因" prop="reason" max-width="320px" align="center" />
            <el-table-column label="奖励时间" prop="createTime" sortable width="110px" align="center" />
          </el-table>
        </el-tab-pane>
        <!-- 处罚 -->
        <el-tab-pane label="惩罚管理" name="punishment">
          <el-table :data="punishmentList" border fit height="350px" highlight-current-row style="width: 100%; margin-top: 20px">
            <el-table-column label="学号" width="110px" align="center">
              <template>
                {{ currentStudent.username }}
              </template>
            </el-table-column>
            <el-table-column label="班级" align="center" class-name="status-col" width="180px">
              <template>
                {{ currentStudent.departId | formatDepart(that) }}
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
            <el-table-column label="证明材料" width="140px" align="center">
              <template slot-scope="{ $index }">
                <el-avatar shape="square" :src="fileShowList[$index]" />
              </template>
            </el-table-column>
            <el-table-column v-for="(item, index) in PunishmentDefColumns" :key="index" :label="item.title" :prop="item.prop" :sortable="item.sortable" :align="item.align" :min-width="item.width" />
            <el-table-column label="审核状态" align="center" class-name="status-col" width="180px">
              <template slot-scope="{ row }">
                <el-tag :type="row.approveStatus | approveStatusFilter">
                  {{ row.approveStatus | formatApproveStatus }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="申请时间" sortable prop="createTime" width="180px" align="center" />
            <el-table-column label="操作" align="center" fixed="right" width="200px" class-name="small-padding fixed-width">
              <template slot-scope="{ row }">
                <el-button type="primary" size="mini" @click="handleUpdatePunishment(row)"> 修改 </el-button>
                <!-- <el-button type="danger" size="mini" @click="handleDeletePunishment(row, $index)"> 删除 </el-button> -->
              </template>
            </el-table-column>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
    <!-- 奖励新增 -->
    <el-dialog title="新增奖励" :visible.sync="dialogRewardFormVisible" width="55%">
      <el-form ref="reward" :model="reward" :rules="rewardRules" label-position="left" label-width="85px">
        <el-form-item label="奖励类型" prop="type">
          <el-select ref="rewardType" v-model="reward.type" clearable>
            <el-option v-for="item in rewardType" :key="item.key" :label="item.value" :value="item.key" />
          </el-select>
        </el-form-item>
        <el-form-item label="奖励原因" prop="reason">
          <el-input v-model="reward.reason" type="textarea" placeholder="请输入奖励原因" maxlength="200" show-word-limit />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogRewardFormVisible = false"> 取消 </el-button>
        <el-button type="primary" @click="addReward()"> 确认 </el-button>
      </div>
    </el-dialog>
    <!-- 处罚新增 -->
    <el-dialog :title="textMap[dialogPunishmentStatus]" :visible.sync="dialogPunishmentFormVisible" width="55%">
      <el-descriptions class="margin-top" :column="3" border>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-user" />
            学生姓名
          </template>
          {{ currentStudent.realName }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-star-off" />
            学号
          </template>
          {{ currentStudent.username }}
        </el-descriptions-item>
        <el-descriptions-item>
          <template slot="label">
            <i class="el-icon-office-building" />
            班级
          </template>
          {{ currentStudent.departId | formatDepart(that) }}
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
          <el-tag :type="punishment.approveStatus | approveStatusFilter">
            {{ punishment.approveStatus | formatApproveStatus }}
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
            审核意见
          </template>
          {{ punishment.auditOpinion }}
        </el-descriptions-item>
      </el-descriptions>
      <el-form ref="punishment" :model="punishment" :disabled="formRequired" :rules="PunishmentRules" label-position="left" label-width="85px">
        <el-form-item label="处罚类型" prop="type">
          <el-select ref="punishmentType" v-model="punishment.type" clearable>
            <el-option v-for="item in punishmentType" :key="item.key" :label="item.value" :value="item.key" />
          </el-select>
        </el-form-item>
        <el-form-item label="处罚原因" prop="reason">
          <el-input v-model="punishment.reason" type="textarea" placeholder="请输入处罚原因" maxlength="200" show-word-limit />
        </el-form-item>
        <el-form-item label="证明材料" prop="prove">
          <el-upload class="upload-demo" action="http://localhost:8081/system/common/upload" :multiple="false" :limit="2" :on-preview="handlePreviewProve" :on-remove="handleRemoveProve" :on-success="uploadProve" :file-list="proveList" list-type="picture">
            <el-button size="small" type="primary" :disabled="formRequired">点击上传</el-button>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogPunishmentFormVisible = false"> 取消 </el-button>
        <el-button type="primary" :disabled="formRequired" @click="dialogPunishmentStatus === 'addPunishment' ? addPunishment() : updatePunishment()"> 确认 </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { checkusername } from '@/api/user' // 用户请求
import { getStudentByDepartId, editStudent, addStudent, deletetStudent } from '@/api/student' // 学生请求
import { queryRewardByStudentId, rewardAdd, punishmentAdd, queryPunishmentByStudentId, deletePunishment, updatePunishment } from '@/api/rewardAndPunishment' // 奖惩请求
import { getDepartByUsername } from '@/api/depart' // 部门
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

const defaultUser = {
  username: '',
  password: '',
  departId: '',
  departIds: '',
  sex: 1
}

var usernameId = ''
export default {
  name: 'Student',
  components: { Pagination },
  directives: { waves },
  // TODO 可通过字典查询
  filters: {
    // 审核状态类型
    approveStatusFilter(status) {
      const statusMap = {
        undefined: '',
        0: '',
        1: 'warning',
        2: 'success',
        3: 'danger'
      }
      return statusMap[status]
    },
    // 审核状态转换
    formatApproveStatus(status) {
      const statusMap = {
        0: '待审核',
        1: '辅导员通过',
        2: '通过审核',
        3: '拒绝审核'
      }
      return statusMap[status]
    },
    // 奖励类型
    rewardFilter(reward) {
      const rewardMap = {
        0: 'success',
        1: 'info',
        2: 'danger',
        3: 'danger'
      }
      return rewardMap[reward]
    },
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
    // 奖励显示转换
    formatReward(reward) {
      const rewardMap = {
        0: '三好学生',
        1: '优秀学生干部',
        2: '优秀党员',
        3: '优秀团员'
      }
      return rewardMap[reward]
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
    // 性别显示转换
    formatSex(sex) {
      const sexMap = {
        1: '男',
        2: '女'
      }
      return sexMap[sex]
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
    const usernameRule = (rule, value, callback) => {
      checkusername(usernameId, value).then(response => {
        if (response.result) {
          return callback(new Error('用户账号不能重复'))
        }
        callback()
      })
    }
    return {
      that: this,
      departList: [],
      // 表头
      defColumns: [
        {
          title: '用户姓名',
          align: 'center',
          width: '110px',
          prop: 'realName',
          sortable: false
        },
        {
          title: '身份证号',
          align: 'center',
          width: '180px',
          prop: 'idCard',
          sortable: false
        },
        {
          title: '出生日期',
          align: 'center',
          width: '130px',
          prop: 'birthday',
          sortable: true
        },
        {
          title: '手机号码',
          align: 'center',
          width: '110px',
          prop: 'phone',
          sortable: false
        },
        // {
        //   title: '班级',
        //   align: 'center',
        //   width: '180px',
        //   prop: 'departId',
        //   sortable: false
        // },
        {
          title: '监护人',
          align: 'center',
          width: '110px',
          prop: 'guardian',
          sortable: false
        },
        {
          title: '监护人手机号码',
          align: 'center',
          width: '150px',
          prop: 'guardianTel',
          sortable: false
        },
        {
          title: '家庭地址',
          align: 'center',
          width: '200px',
          prop: 'address',
          sortable: false
        }
      ],
      // 处罚表头
      PunishmentDefColumns: [
        {
          title: '审核意见',
          align: 'center',
          width: '250px',
          prop: 'auditOpinion',
          sortable: false
        }
      ],
      // 用户数组
      studentList: [],
      user: Object.assign({}, defaultUser),
      // 当前学生
      currentStudent: {},
      total: 0,
      listLoading: true,
      page: 1,
      limit: 10,
      listQuery: {
        realName: null,
        username: this.$store.getters.name,
        departId: null
      },
      // 奖励
      rewardList: [],
      reward: {},
      // 处罚
      punishmentList: [],
      punishment: {},
      // 性别显示
      sexOptions: [
        { value: 1, label: '男' },
        { value: 2, label: '女' }
      ],
      dialogFormVisible: false,
      dialogStatus: '',
      dialogPunishmentStatus: '',
      // 奖惩模态框
      dialogTableVisible: false,
      dialogRewardFormVisible: false,
      dialogPunishmentFormVisible: false,
      textMap: {
        update: '修改',
        create: '添加',
        reward: '奖励',
        punishment: '惩罚',
        addPunishment: '新增惩罚',
        updatePunishment: '修改惩罚'
      },
      activeName: 'reward',
      // 奖励类型
      rewardType: [
        { key: 0, value: '三好学生' },
        { key: 1, value: '优秀学生干部' },
        { key: 2, value: '优秀党员' },
        { key: 3, value: '优秀团员' }
      ],
      punishmentType: [
        { key: 0, value: '警告' },
        { key: 1, value: '严重警告' },
        { key: 2, value: '记过' },
        { key: 3, value: '留校查看' },
        { key: 4, value: '劝退' },
        { key: 5, value: '开除学籍' }
      ],
      dialogPvVisible: false,
      pvData: [],
      rules: {
        username: [
          { required: true, message: '请输入学号', trigger: 'blur' },
          {
            validator: usernameRule,
            trigger: 'blur'
          }
        ],
        realName: [{ required: true, message: '输入学生姓名', trigger: 'blur' }],
        departId: [{ required: true, message: '请选择用户所属部门', trigger: 'change' }]
      },
      rewardRules: {
        type: [{ required: true, message: '请选择奖励类型', trigger: 'change' }],
        reason: [{ required: true, message: '请输入奖励原因', trigger: 'blur' }]
      },
      PunishmentRules: {
        type: [{ required: true, message: '请选择处罚类型', trigger: 'change' }],
        reason: [{ required: true, message: '请输入处罚原因', trigger: 'blur' }]
      },
      downloadLoading: false,
      // 文件
      fileList: [],
      proveList: [],
      fileShowList: [],
      formRequired: false
    }
  },
  created() {
    this.getDeparts()
    this.getList()
  },
  methods: {
    handelImage() {
      this.punishmentList.forEach(item => {
        if (item.prove !== null) this.fileShowList.push(item.prove.split(',').shift())
        else this.fileShowList.push('')
      })
    },
    uploadProve(response, file, fileList) {
      if (fileList.length > 1) {
        this.punishment.prove += ',' + response.result.path
      } else if (fileList.length <= 1) {
        this.punishment.prove = response.result.path
      }
    },
    handleRemoveProve(file, fileList) {
      const list = []
      fileList.forEach(item => {
        list.push(item.url)
      })
      this.punishment.prove = list.join(',')
    },
    handlePreviewProve(file) {
      console.log(file)
    },
    handleUpdatePunishment(row) {
      this.punishment = row
      this.dialogPunishmentStatus = 'updatePunishment'
      this.proveList = []
      this.dialogPunishmentFormVisible = true
      if (this.punishment.approveStatus === 1) this.formRequired = false
      else this.formRequired = true
      if (this.punishment.prove !== null) {
        const list = this.punishment.prove.split(',')
        list.forEach(item => {
          const name = item.substring(item.lastIndexOf('/') + 1, item.lastIndexOf('.'))
          this.proveList.push({ name: name, url: item })
        })
      }
      this.$nextTick(() => {
        this.$refs['punishment'].clearValidate()
      })
    },
    handleDeletePunishment(row, index) {
      this.$confirm('确认删除处罚申请?', '警告', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          if (row.approveStatus === 1) {
            deletePunishment(this.punishment.id).then(response => {
              this.punishmentList.splice(index, 1)
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
    updatePunishment() {
      this.$refs['punishment'].validate(valid => {
        if (valid) {
          updatePunishment(this.punishment).then(response => {
            const index = this.punishmentList.findIndex(v => v.id === this.punishment.id)
            this.punishmentList.splice(index, 1, this.punishment)
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
    addPunishment() {
      this.$refs.punishment.validate(valid => {
        if (valid) {
          this.punishment.userId = this.currentStudent.userId
          this.punishment.studentId = this.currentStudent.studentId
          this.punishment.departId = this.currentStudent.departId
          this.punishment.counselorOpinion = this.listQuery.username
          punishmentAdd(this.punishment).then(response => {
            this.punishmentList.push(response.result)
            this.dialogPunishmentFormVisible = false
          })
        }
      })
    },
    addReward() {
      this.$refs.reward.validate(valid => {
        if (valid) {
          this.reward.studentId = this.currentStudent.studentId
          rewardAdd(this.reward).then(response => {
            this.rewardList.push(response.result)
            this.dialogRewardFormVisible = false
          })
        }
      })
    },
    handleStudentReward() {
      this.reward = {}
      this.dialogRewardFormVisible = true
      this.$nextTick(() => {
        this.$refs['reward'].clearValidate()
      })
    },
    handleStudentPunishment() {
      this.punishment = {}
      this.proveList = []
      this.dialogPunishmentStatus = 'addPunishment'
      this.dialogPunishmentFormVisible = true
      this.$nextTick(() => {
        this.$refs['punishment'].clearValidate()
      })
    },
    async getDeparts() {
      await getDepartByUsername(this.$store.getters.name).then(response => {
        this.departList = response.result
      })
    },
    upload(response, file, fileList) {
      this.user.avatar = response.result.path
    },
    handleRemove(file, fileList) {
      this.user.avatar = ''
      console.log(file, fileList)
    },
    handlePreview(file) {
      console.log(file)
    },
    getList() {
      this.listLoading = true
      getStudentByDepartId(this.page, this.limit, this.listQuery).then(response => {
        this.studentList = response.result.records
        this.total = response.result.total
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
    handleTab(tab, event) {
      if (tab.name === 'reward') {
        this.dialogStatus = 'reward'
        queryRewardByStudentId(this.currentStudent.studentId).then(response => {
          this.rewardList = response.result
        })
      } else {
        queryPunishmentByStudentId(this.currentStudent.studentId).then(response => {
          this.punishmentList = response.result
          this.fileShowList = []
          this.handelImage()
        })
        this.dialogStatus = 'punishment'
      }
    },
    handleReword(row) {
      this.dialogTableVisible = true
      this.currentStudent = row
      this.rewardList = []
      this.punishmentList = []
      if (this.dialogStatus === 'reward' || this.dialogStatus !== 'punishment') {
        this.dialogStatus = 'reward'
        queryRewardByStudentId(row.studentId).then(response => {
          this.rewardList = response.result
        })
      } else if (this.dialogStatus === 'punishment') {
        this.dialogStatus = 'punishment'
        queryPunishmentByStudentId(row.studentId).then(response => {
          this.punishmentList = response.result
          this.fileShowList = []
          this.handelImage()
        })
      }
    },
    handleCreate() {
      this.fileList = []
      this.user = Object.assign({}, defaultUser)
      this.singleDepartLabel = ''
      this.multipleDepartLabel = []
      this.multipleDepartids = []
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['user'].clearValidate()
      })
    },
    createData() {
      this.$refs.user.validate(valid => {
        if (valid) {
          console.log(this.user)
          addStudent(this.user).then(response => {
            this.studentList.push(response.result)
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
      usernameId = ''
      this.user = Object.assign({}, defaultUser)
      this.singleDepartLabel = ''
      this.multipleDepartLabel = []
      this.multipleDepartids = []
      // copy obj
      this.user = Object.assign({}, row)
      usernameId = this.user.userId
      if (this.user.departIds) {
        this.multipleDepartids = this.user.departIds.split(',')
      }
      if (this.user.avatar) {
        const avatar = this.user.avatar
        const avatarName = avatar.substring(avatar.lastIndexOf('/') + 1, avatar.lastIndexOf('.'))
        this.fileList.push({ name: avatarName, url: avatar })
      }

      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['user'].clearValidate()
      })
    },
    updateData() {
      this.$refs['user'].validate(valid => {
        if (valid) {
          editStudent(this.user).then(response => {
            const index = this.studentList.findIndex(v => v.userId === this.user.userId)
            this.studentList.splice(index, 1, Object.assign({}, this.user))
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
      this.$confirm('确认删除用户?', '警告', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          deletetStudent(row.studentId).then(response => {
            this.studentList.splice(index, 1)
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
          })
        })
        .catch(err => {
          console.error(err)
        })
    },

    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['学号', '头像', '性别', '用户姓名', '身份证号', '出生日期', '手机号码', '电子邮件', '监护人姓名', '监护人电话', '部门']
        const filterVal = ['username', 'avatar', 'sex', 'realName', 'idCard', 'birthday', 'phone', 'email', 'guardian', 'guardianTel', 'departId']
        const list = this.studentList
        const data = this.formatJson(filterVal, list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: '学生信息'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v =>
        filterVal.map(j => {
          if (j === 'sex') {
            return this.$options.filters.formatSex(v[j])
          } else if (j === 'birthday') {
            return parseTime(v[j])
          } else if (j === 'departId') {
            return this.$options.filters.formatDepart(v[j], this)
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
