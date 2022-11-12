<template>
  <div class="app-container">
    <div class="custom-tree-container">
      <div class="block">
        <div style="margin-bottom: 10px">
          <el-button type="primary" size="mini" round icon="el-icon-plus" @click="addDepart">添加部门</el-button>
        </div>
        <div>
          <el-row :gutter="22">
            <el-col :span="5">部门名称</el-col>
            <el-col :span="6">描述</el-col>
            <el-col :span="5">备注</el-col>
            <el-col :span="4">机构等级</el-col>
            <el-col :span="2">状态</el-col>
            <el-col :span="2">操作</el-col>
          </el-row>
        </div>
        <el-tree :data="departTree" node-key="id" :props="defaultProps" default-expand-all :expand-on-click-node="false">
          <span slot-scope="{ node, data }" class="custom-tree-node">
            <el-col :span="5">{{ data.title }}</el-col>
            <el-col :span="6">{{ data.description }}</el-col>
            <el-col :span="5">{{ data.remake }}</el-col>
            <el-col :span="4"> {{ data.orgType | formatOrgType }}</el-col>
            <el-col :span="2">{{ data.status | formatStatus }}</el-col>
            <span>
              <el-button type="text" size="mini" @click="() => append(data)"> 添加 </el-button>
              <el-button type="text" size="mini" @click="() => handleUpdate(node, data)"> 编辑 </el-button>
              <el-button type="text" size="mini" @click="() => remove(node, data)"> 删除 </el-button>
            </span>
          </span>
        </el-tree>
      </div>
    </div>
    <el-drawer :title="textMap[drawerStatus]" :visible.sync="table" direction="rtl" size="30%">
      <el-form ref="depart" :model="depart" :rules="rules" label-position="center" label-width="85px">
        <el-form-item label="部门名称" prop="title">
          <el-input v-model="depart.title" placeholder="请输入部门名称" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="depart.description" placeholder="请输入部门描述" />
        </el-form-item>
        <el-form-item label="备注" prop="remake">
          <el-input v-model="depart.remake" placeholder="请输入部门备注" />
        </el-form-item>
      </el-form>
      <div class="drawer-footer">
        <el-button @click="table = false"> 取消 </el-button>
        <el-button type="primary" @click="drawerStatus === 'create' ? createData() : drawerStatus === 'update' ? updateData() : createDepart()"> 确认 </el-button>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { queryTreeList, addDepartTree, editDepartTree, deleteDepartTree } from '@/api/depart' // 部门
export default {
  filters: {
    // 状态显示转换
    formatOrgType(status) {
      const orgTypeMap = {
        1: '一级部门',
        2: '子部门'
      }
      return orgTypeMap[status]
    },
    // 状态显示转换
    formatStatus(status) {
      const statusMap = {
        1: '正常',
        2: '冻结'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      // 部门树
      departTree: [],
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      table: false,
      drawerStatus: '',
      textMap: {
        update: '修改',
        create: '添加',
        createDepart: '添加一级部门'
      },
      depart: {},
      currentDepart: {},
      rules: {
        title: [{ required: true, message: '请输入菜单标题', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getDepartTreeList()
  },
  methods: {
    addDepart() {
      this.depart = {}
      this.currentDepart = {}
      this.drawerStatus = 'createDepart'
      this.table = true
    },
    createDepart() {
      addDepartTree(this.depart).then(response => {
        this.depart = response.result
        this.departTree.push(this.depart)
        this.table = false
      })
    },
    async getDepartTreeList() {
      queryTreeList().then(response => {
        this.departTree = response.result
      })
    },
    append(data) {
      this.depart = {}
      this.drawerStatus = 'create'
      this.table = true
      this.currentDepart = data
      if (data.id) {
        this.depart.parentId = data.id
        console.log(this.depart)
      }
      this.$nextTick(() => {
        this.$refs['depart'].clearValidate()
      })
    },
    handleUpdate(data) {
      this.depart = data.data
      this.drawerStatus = 'update'
      this.table = true
      this.$nextTick(() => {
        this.$refs['depart'].clearValidate()
      })
    },
    remove(node, data) {
      this.$confirm('确认删除部门?', '警告', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          deleteDepartTree(data.id).then(response => {
            const parent = node.parent
            const children = parent.data.children || parent.data
            const index = children.findIndex(d => d.id === data.id)
            children.splice(index, 1)
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
    createData() {
      console.log(this.depart)
      addDepartTree(this.depart).then(response => {
        this.depart = response.result
        if (!this.currentDepart.children) {
          this.$set(this.currentDepart, 'children', [])
        }
        this.currentDepart.children.push(this.depart)
        this.table = false
        this.$message({
          message: '添加成功',
          type: 'success'
        })
      })
    },
    updateData() {
      editDepartTree(this.depart).then(response => {
        this.table = false
        this.$message({
          message: '修改成功',
          type: 'success'
        })
      })
    }
  }
}
</script>
<style lang="scss" scoped>
.drawer-footer {
  padding-right: 20px;
  float: right;
}
.el-row {
  margin-top: 20px;
  font-size: 14px;
  height: 40px;
  line-height: 40px;
  background-color: #fafafa;
  &:last-child {
    margin-bottom: 0;
  }
}
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>
