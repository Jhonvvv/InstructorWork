<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.realName" placeholder="用户姓名" style="width: 200px" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.roleId" placeholder="角色" clearable class="filter-item" style="width: 130px; padding-right: 10px">
        <el-option v-for="item in roleList" :key="item.id" :label="item.roleName" :value="item.id" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter"> 搜索 </el-button>
      <el-button class="filter-item" style="margin-left: 10px" type="primary" icon="el-icon-edit" @click="handleCreate"> 添加 </el-button>
      <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-download" @click="handleDownload"> 导出 </el-button>
      <!-- <div style="display:inline-block;position: relative">
        <el-upload style="position: absolute;top:-25px;left:10px" class="upload-demo" :action="url.importExcelUrl" multiple :limit="1" :show-file-list="false">
          <el-button class="filter-item" type="primary" icon="el-icon-upload2">导入</el-button>
        </el-upload>
      </div> -->

      <!-- <el-button v-waves :loading="downloadLoading" class="filter-item" type="primary" icon="el-icon-upload2"> 导入 </el-button> -->
    </div>
    <!-- 表格 -->
    <el-table v-loading="listLoading" :data="list" border fit highlight-current-row style="width: 100%; margin-top: 20px">
      <el-table-column label="用户账号" prop="username" sortable width="110px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.username }}</span>
        </template>
      </el-table-column>
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
      <el-table-column label="部门" align="center" class-name="status-col" width="180px">
        <template slot-scope="{ row }">
          {{ row.departId | formatDepart(that) }}
        </template>
      </el-table-column>
      <el-table-column label="负责部门" min-width="250px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.departIds | formatDeparts(that) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="身份" align="center" class-name="status-col" width="100">
        <template slot-scope="{ row }">
          <el-tag :type="row.userIdentity | userIdentityFilter">
            {{ row.userIdentity | formatUserIdentity }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" class-name="status-col" width="100">
        <template slot-scope="{ row }">
          <el-tag :type="row.status | statusFilter">
            {{ row.status | formatStatus }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" fixed="right" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{ row, $index }">
          <el-button type="primary" size="mini" @click="handleUpdate(row)"> 修改 </el-button>
          <el-button v-if="row.status != 'deleted'" size="mini" type="danger" @click="handleDelete(row, $index)"> 删除 </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="page" :limit.sync="limit" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible" width="55%">
      <el-form ref="user" :model="user" :rules="rules" label-position="left" label-width="85px">
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="用户账号" prop="username">
              <el-input v-model="user.username" placeholder="请输入用户账号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="用户姓名" prop="realName">
              <el-input v-model="user.realName" placeholder="请输入用户姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="登录密码" prop="password">
              <el-input v-model="user.password" placeholder="请输入登录密码" />
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
            <el-form-item label="角色分配" prop="role">
              <el-select v-model="user.roleId" clearable placeholder="请选择">
                <el-option v-for="item in roleList" :key="item.id" :label="item.roleName" :value="item.id" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="12">
            <el-form-item label="性别" prop="sex">
              <el-select v-model="user.sex" clearable placeholder="请选择性别">
                <el-option v-for="item in sexOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出生日期" prop="birthday">
              <el-date-picker v-model="user.birthday" type="date" value-format="yyyy-MM-dd" placeholder="选择日期" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="18">
            <el-form-item label="所属部门" prop="departId">
              <el-select ref="singleTree" v-model="singleDepartLabel" style="width: 80%" clearable popper-class="select-tree-popper" value-key="id" @clear="singleDepartClear" @visible-change="singleDepartVisible">
                <el-option :value="user.departId">
                  <el-tree ref="singleSelectTree" :accordion="true" :check-strictly="checkStrictly" :default-checked-keys="[user.departid]" show-checkbox :data="departTree" :highlight-current="true" :props="defaultProps" node-key="id" @check="singleDepartChange">
                    <template #defalut="{ node }">
                      <span>{{ node.label }}</span>
                    </template>
                  </el-tree>
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="状态" label-width="50px" prop="status">
              <el-switch v-model="user.status" :active-value="1" :inactive-value="2" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="负责部门" prop="departIds">
              <el-select ref="multipleTree" v-model="multipleDepartLabel" style="width: 100%" multiple clearable popper-class="select-tree-popper" value-key="id" @remove-tag="removeSelectTreeTag">
                <el-option :value="user.departIds">
                  <el-tree ref="multipleSelectTree" :accordion="true" :check-strictly="checkStrictly" :default-checked-keys="multipleDepartids" show-checkbox :data="departTree" :highlight-current="true" :props="defaultProps" node-key="id" @check="multipleDepartChange">
                    <template #defalut="{ node }">
                      <span>{{ node.label }}</span>
                    </template>
                  </el-tree>
                </el-option>
              </el-select>
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
  </div>
</template>

<script>
import { getAllUser, addUser, checkusername, editUser, deleteUser } from '@/api/user' // 用户请求
import { queryTreeList, getAllDepart } from '@/api/depart' // 部门
import { queryRoleAll } from '@/api/role'
import waves from '@/directive/waves' // waves directive
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import { ClmListMixin } from '@/layout/mixin/ClmListMixin'

const defaultUser = {
  username: '',
  password: '',
  departId: '',
  departIds: '',
  sex: 0,
  status: 1
}

var usernameId = ''
export default {
  name: 'User',
  components: { Pagination },
  directives: { waves },
  // TODO 可通过字典查询
  filters: {
    // 状态类型
    statusFilter(status) {
      const statusMap = {
        1: 'success',
        2: 'info'
      }
      return statusMap[status]
    },
    // 状态显示转换
    formatStatus(status) {
      const statusMap = {
        1: '正常',
        2: '冻结'
      }
      return statusMap[status]
    },
    // 身份类型
    userIdentityFilter(userIdentity) {
      const userIdentityMap = {
        1: 'primary',
        2: 'warning',
        3: 'danger',
        4: 'info'
      }
      return userIdentityMap[userIdentity]
    },
    // 身份显示转换
    formatUserIdentity(userIdentity) {
      const userIdentityMap = {
        1: '学生',
        2: '辅导员',
        3: '管理员',
        4: '其他'
      }
      return userIdentityMap[userIdentity]
    },
    // 性别显示转换
    formatSex(sex) {
      const sexMap = {
        0: '未知',
        1: '男',
        2: '女'
      }
      return sexMap[sex]
    },
    // 角色过滤
    formatRole(role, that) {
      var roleName = that.roleList.filter(item => {
        if (role === item.id) return item
      })
      return roleName[0].roleName
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
    },
    // 负责部门过滤
    formatDeparts(departs, that) {
      if (departs !== '') {
        var departList = departs.split(',')
        var departListName = []
        departList.forEach(element => {
          var departName = ''
          departName = that.departList.filter(item => {
            if (element === item.id) return item
          })
          departListName.push(departName[0].departName)
        })
        return departListName.join(',')
      }
    }
  },
  mixins: [ClmListMixin],
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
        }
        // {
        //   title: '部门',
        //   align: 'center',
        //   width: '180px',
        //   prop: 'departId',
        //   sortable: false
        // }
      ],
      // 用户数组
      list: [],
      user: Object.assign({}, defaultUser),
      total: 0,
      listLoading: true,
      page: 1,
      limit: 10,
      listQuery: {
        realName: '',
        roleId: ''
      },
      // 状态显示
      statusOptions: ['正常', '冻结'],
      // 性别显示
      sexOptions: [
        { value: 0, label: '未知' },
        { value: 1, label: '男' },
        { value: 2, label: '女' }
      ],
      // 性别显示
      roleOptions: [
        { value: 1, label: '学生' },
        { value: 2, label: '辅导员' },
        { value: 3, label: '管理员' }
      ],
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '修改',
        create: '添加'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        username: [
          { required: true, message: '请输入用户账号', trigger: 'blur' },
          {
            validator: usernameRule,
            trigger: 'blur'
          }
        ],
        password: [{ required: true, message: '请输入用户密码', trigger: 'blur' }],
        roleId: [{ required: true, message: '请选择用户角色', trigger: 'change' }],
        singleDepartLabel: [{ required: true, message: '请选择用户所属部门', trigger: 'change' }]
      },
      downloadLoading: false,
      // 角色
      roleList: [],
      // 部门树
      departTree: [],
      // 部门数组
      departList: [],
      // 单选树
      singleDepartLabel: '',
      // 多选树
      multipleDepartLabel: [],
      multipleDepartids: [],
      // 权限子树
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      // 父子是否关联
      checkStrictly: true,
      // 文件
      fileList: [],
      url: {
        exportXlsUrl: 'http://localhost:8081/system/user/exportXls',
        importExcelUrl: 'http://localhost:8081/system/user/importExcel'
      }
    }
  },
  created() {
    this.getRoles()
    this.getDeparts()
    this.getList()
  },
  methods: {
    async getRoles() {
      await queryRoleAll().then(response => {
        this.roleList = response.result
      })
    },
    async getDeparts() {
      await getAllDepart().then(response => {
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
    removeSelectTreeTag(val) {
      const stack = JSON.parse(JSON.stringify(this.departTree))
      while (stack.length) {
        const curr = stack.shift()
        if (curr.title === val) {
          return this.$refs.multipleSelectTree.setChecked(curr.id, false)
        }
        if (curr.children && curr.children.length) {
          stack.unshift(...curr.children)
        }
      }
    },
    multipleDepartChange(data, node, el) {
      this.multipleDepartLabel.push(data.title)
      if (this.$refs.multipleSelectTree) {
        this.multipleDepartids = this.$refs.multipleSelectTree.getCheckedKeys()
        this.user.departIds = this.$refs.multipleSelectTree.getCheckedKeys().join(',')
      }
    },
    multipleDepartClear(val) {
      if (this.$refs.multipleSelectTree) {
        this.$refs.multipleSelectTree.setCheckedKeys([])
      }
      this.user.departIds = ''
    },
    singleDepartVisible() {
      if (this.$refs.singleSelectTree) {
        this.$refs.singleSelectTree.setCheckedKeys([])
      }
      this.$refs.singleSelectTree.setCheckedKeys([this.user.departId])
    },
    singleDepartChange(data, node, el) {
      this.singleDepartLabel = data.title
      this.user.departId = data.id
      this.$refs.singleTree.blur()
    },
    singleDepartClear() {
      if (this.$refs.singleSelectTree) {
        this.$refs.singleSelectTree.setCheckedKeys([])
      }
      this.user.departId = ''
    },
    getList() {
      this.listLoading = true
      getAllUser(this.page, this.limit, this.listQuery).then(response => {
        this.list = response.result.records
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
    handleCreate() {
      this.fileList = []
      this.user = Object.assign({}, defaultUser)
      this.singleDepartLabel = ''
      this.multipleDepartLabel = []
      this.multipleDepartids = []
      queryTreeList().then(response => {
        this.departTree = response.result
      })
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
          addUser(this.user).then(response => {
            this.list.push(response.result)
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
      usernameId = this.user.id
      queryTreeList().then(response => {
        this.departTree = response.result
        this.singleDepartLabel = this.handleDepartLabel(this.user.departId)
        if (this.user.departIds) {
          this.multipleDepartids = this.user.departIds.split(',')
          this.multipleDepartids.forEach(item => {
            this.multipleDepartLabel.push(this.handleDepartLabel(item))
          })
        }
      })
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
    handleDepartLabel(id) {
      const stack = JSON.parse(JSON.stringify(this.departTree))
      let departLabel = ''
      while (stack.length) {
        const curr = stack.shift()
        if (curr.id === id) {
          departLabel = curr.title
        }
        if (curr.children && curr.children.length) {
          stack.unshift(...curr.children)
        }
      }
      return departLabel
    },
    updateData() {
      this.$refs['user'].validate(valid => {
        if (valid) {
          console.log(this.user.getUserIdentity)
          editUser(this.user).then(response => {
            const index = this.list.findIndex(v => v.id === this.user.id)
            this.list.splice(index, 1, response.result)
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
          console.log(this.$store.getters.name)
          if (this.$store.getters.name === row.username) {
            this.$message({
              type: 'warning',
              message: '不能删除当前登录的用户!'
            })
          } else {
            deleteUser(row.id).then(response => {
              this.list.splice(index, 1)
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
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['用户账号', '头像', '性别', '用户姓名', '出生日期', '手机号码', '电子邮件', '部门', '负责部门', '身份', '角色', '状态']
        const filterVal = ['username', 'avatar', 'sex', 'realName', 'birthday', 'phone', 'email', 'departId', 'departIds', 'userIdentity', 'roleId', 'status']
        const list = this.list
        const data = this.formatJson(filterVal, list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: '用户信息'
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
          } else if (j === 'departIds') {
            return this.$options.filters.formatDeparts(v[j], this)
          } else if (j === 'roleId') {
            return this.$options.filters.formatRole(v[j], this)
          } else if (j === 'userIdentity') {
            return this.$options.filters.formatUserIdentity(v[j])
          } else if (j === 'status') {
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
/* 下拉树样式-----------开始 */
.select-tree-popper {
  .el-scrollbar {
    .el-scrollbar__view {
      .el-select-dropdown__item {
        height: auto;
        max-height: 274px;
        padding: 0;
        overflow-y: auto;
        line-height: 26px;
      }
    }
  }
}

/* 下拉树样式-----------结束 */
</style>
