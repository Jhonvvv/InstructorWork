<template>
  <div class="app-container">
    <el-button type="primary" @click="handleAddRole">新增角色</el-button>

    <el-table :rules="rules" :data="rolesList" style="width: 100%; margin-top: 30px" border>
      <el-table-column align="center" label="角色键" width="160">
        <template slot-scope="scope">
          {{ scope.row.roleCode }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="角色名" width="180">
        <template slot-scope="scope">
          {{ scope.row.roleName }}
        </template>
      </el-table-column>
      <el-table-column align="header-center" min-width="200" label="描述">
        <template slot-scope="scope">
          {{ scope.row.description }}
        </template>
      </el-table-column>
      <el-table-column min-width="180" align="center" label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope)">编辑权限</el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <pagination v-show="total > 0" :total="total" :page-size="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :visible.sync="dialogVisible" :title="dialogType === 'edit' ? '修改角色' : '新增角色'">
      <el-form ref="role" :model="role" :rules="rules" label-width="80px" label-position="left">
        <el-form-item label="角色键" prop="roleCode">
          <el-input v-model="role.roleCode" placeholder="角色键" />
        </el-form-item>
        <el-form-item label="角色名">
          <el-input v-model="role.roleName" placeholder="角色名" />
        </el-form-item>
        <el-form-item label="角色描述">
          <el-input v-model="role.description" :autosize="{ minRows: 2, maxRows: 4 }" type="textarea" placeholder="角色描述" />
        </el-form-item>
        <el-form-item v-show="dialogType === 'edit' ? true : false" label="菜单">
          <el-tree ref="tree" style="margin-top: 8px" :check-strictly="checkStrictly" :data="routerPremission" :props="defaultProps" :default-checked-keys="routerChecked" show-checkbox node-key="id" class="permission-tree" />
        </el-form-item>
      </el-form>
      <div style="text-align: right">
        <el-button type="danger" @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="confirmRole">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { deepClone } from '@/utils'
import { getRoles, addRole, deleteRole, updateRole, getRolePermission, checkRoleCode, saveRolePermission, getUserRole } from '@/api/role'
import Pagination from '@/components/Pagination'

const defaultRole = {
  roleName: '',
  roleCode: '',
  description: ''
}
var roleCodeId = ''
export default {
  components: { Pagination },

  data() {
    const codeRule = (rule, value, callback) => {
      checkRoleCode(roleCodeId, value).then(response => {
        if (response.result) {
          return callback(new Error('角色编码不能重复'))
        }
        callback()
      })
    }
    return {
      role: Object.assign({}, defaultRole),
      // 角色数组
      rolesList: [],
      dialogVisible: false,
      dialogType: 'new',
      // 父子是否关联
      checkStrictly: false,
      // 分页
      total: 0,
      listQuery: {
        page: 1,
        limit: 10
      },
      // 校验规则
      rules: {
        roleCode: [
          { required: true, message: '请输入角色键', trigger: 'blur' },
          {
            validator: codeRule,
            trigger: 'blur'
          }
        ]
      },
      // 权限树
      routerPremission: [],
      routerChecked: [],
      // 权限子树
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      username: this.$store.getters.name,
      userRole: {}
    }
  },

  created() {
    this.getRoles()
    this.routesData()
    this.getUserRole()
  },
  methods: {
    getUserRole() {
      getUserRole(this.username).then(response => {
        this.userRole = response.result
      })
    },
    // 获取菜单（权限）
    async routesData() {
      if (this.$store.getters.menus.length === 0) {
        await this.$store.dispatch('user/getPermissionList')
      }
      this.routerPremission = this.$store.getters.menus
      // console.log(this.routerPremission)
    },
    getList() {
      this.listLoading = true
      this.getRoles()
    },
    async getRoles() {
      const res = await getRoles(this.listQuery.page, this.listQuery.limit)
      this.rolesList = res.result.records
      this.total = res.result.total
    },
    handleAddRole() {
      this.role = Object.assign({}, defaultRole)
      roleCodeId = ''
      if (this.$refs.tree) {
        this.$refs.tree.setCheckedKeys([])
      }
      this.resetForm('role')
      this.dialogType = 'new'
      this.dialogVisible = true
    },
    handleEdit(scope) {
      this.dialogType = 'edit'
      this.dialogVisible = true
      this.checkStrictly = true
      if (this.$refs.tree) {
        this.$refs.tree.setCheckedKeys([])
      }
      this.role = deepClone(scope.row)
      roleCodeId = this.role.id
      this.resetForm('role')
      getRolePermission(this.role.id).then(response => {
        this.routerChecked = response.result
        this.$refs.tree.setCheckedKeys(this.routerChecked)
        this.checkStrictly = false
      })
    },
    // 清空校验
    resetForm(formName) {
      if (this.$refs[formName] !== undefined) {
        this.$refs[formName].resetFields()
      }
    },
    handleDelete({ $index, row }) {
      this.$confirm('确认删除角色?', '警告', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          if (this.userRole.id !== row.id) {
            await deleteRole(row.id)
            this.rolesList.splice($index, 1)
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
          } else {
            this.$message({
              type: 'warning',
              message: '不能删除当前登录的角色!'
            })
          }
        })
        .catch(err => {
          console.error(err)
        })
    },
    // 确认
    async confirmRole() {
      const that = this
      this.$refs.role.validate(valid => {
        if (valid) {
          const isEdit = that.dialogType === 'edit'
          if (isEdit) {
            updateRole(that.role).then(response => {
              for (let index = 0; index < this.rolesList.length; index++) {
                if (this.rolesList[index].id === this.role.id) {
                  this.rolesList.splice(index, 1, Object.assign({}, this.role))
                  break
                }
              }
            })
            const rolePermission = {
              roleId: '',
              permissionIds: ''
            }
            that.routerChecked = that.$refs.tree.getCheckedKeys()
            rolePermission.roleId = that.role.id
            rolePermission.permissionIds = that.routerChecked.join(',')
            console.log(rolePermission)
            saveRolePermission(rolePermission)
          } else {
            addRole(that.role).then(response => {
              this.rolesList.push(response.result)
            })
          }

          const { description, roleName, roleCode } = that.role
          that.dialogVisible = false
          that.$notify({
            title: 'Success',
            dangerouslyUseHTMLString: true,
            message: `
            <div角色键: ${roleCode}</div>
            <div>角色名: ${roleName}</div>
            <div>描述: ${description}</div>
          `,
            type: 'success'
          })
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.app-container {
  .roles-table {
    margin-top: 30px;
  }
  .permission-tree {
    margin-bottom: 30px;
  }
}
</style>
