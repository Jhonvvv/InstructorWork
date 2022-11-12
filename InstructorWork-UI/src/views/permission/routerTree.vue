<template>
  <div class="app-container">
    <div class="custom-tree-container">
      <div class="block">
        <div style="margin-bottom: 10px">
          <el-button type="primary" size="mini" round icon="el-icon-plus" @click="addPermission">添加节点</el-button>
        </div>
        <div>
          <el-row :gutter="22">
            <el-col :span="4">菜单标题</el-col>
            <el-col :span="4">菜单名</el-col>
            <el-col :span="4">菜单图标</el-col>
            <el-col :span="5">组件</el-col>
            <el-col :span="5">路径</el-col>
            <el-col :span="2">操作</el-col>
          </el-row>
        </div>
        <el-tree :data="routerTreeList" node-key="id" :props="defaultProps" default-expand-all :expand-on-click-node="false">
          <span slot-scope="{ node, data }" class="custom-tree-node">
            <el-col :span="4">{{ data.title }}</el-col>
            <el-col :span="4">{{ data.name }}</el-col>
            <el-col :span="4">{{ data.icon }}</el-col>
            <el-col :span="5">{{ data.component }}</el-col>
            <el-col :span="5">{{ data.path }}</el-col>
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
      <el-form ref="routerTree" :model="routerTree" :rules="rules" label-position="center" label-width="85px">
        <el-form-item label="菜单标题" prop="title">
          <el-input v-model="routerTree.title" placeholder="请输入菜单标题" />
        </el-form-item>
        <el-form-item label="菜单名" prop="name">
          <el-input v-model="routerTree.name" placeholder="请输入菜单名" />
        </el-form-item>
        <el-form-item label="菜单图标" prop="icon">
          <el-input v-model="routerTree.icon" placeholder="请输入菜单图标" />
        </el-form-item>
        <el-form-item label="组件" prop="component">
          <el-input v-model="routerTree.component" placeholder="请输入组件" />
        </el-form-item>
        <el-form-item label="路径" prop="path">
          <el-input v-model="routerTree.path" placeholder="请输入路径" />
        </el-form-item>
      </el-form>
      <div class="drawer-footer">
        <el-button @click="table = false"> 取消 </el-button>
        <el-button type="primary" @click="drawerStatus === 'create' ? createData() : drawerStatus === 'update' ? updateData() : createPermission()"> 确认 </el-button>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { addPermission, editPermission, deletePermission } from '@/api/permission' // 路由请求
export default {
  data() {
    return {
      routerTreeList: [],
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      table: false,
      drawerStatus: '',
      textMap: {
        update: '修改',
        create: '添加',
        createPermission: '添加节点'
      },
      routerTree: {},
      currentRouter: {},
      rules: {
        title: [{ required: true, message: '请输入菜单标题', trigger: 'blur' }],
        name: [{ required: true, message: '请输入菜单名', trigger: 'blur' }],
        component: [{ required: true, message: '请输入组件', trigger: 'blur' }],
        path: [{ required: true, message: '请输入路径', trigger: 'blur' }]
      }
    }
  },
  created() {
    this.getRouterList()
  },
  methods: {
    addPermission() {
      this.routerTree = {}
      this.currentRouter = {}
      this.drawerStatus = 'createPermission'
      this.table = true
    },
    createPermission() {
      addPermission(this.routerTree).then(response => {
        this.routerTree = response.result
        this.routerTreeList.push(this.routerTree)
        this.table = false
      })
    },
    async getRouterList() {
      if (this.$store.getters.menus.length === 0) {
        await this.$store.dispatch('user/getPermissionList')
      }
      this.routerTreeList = this.$store.getters.menus
    },
    append(data) {
      this.routerTree = {}
      this.drawerStatus = 'create'
      this.table = true
      this.currentRouter = data
      if (data.id) {
        this.routerTree.parentId = data.id
        console.log(this.routerTree.id)
      }
      this.$nextTick(() => {
        this.$refs['routerTree'].clearValidate()
      })
    },
    handleUpdate(data) {
      this.routerTree = data.data
      this.drawerStatus = 'update'
      this.table = true
      this.$nextTick(() => {
        this.$refs['routerTree'].clearValidate()
      })
    },
    remove(node, data) {
      this.$confirm('确认删除路由?', '警告', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(async() => {
          deletePermission(data.id).then(response => {
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
      addPermission(this.routerTree).then(response => {
        this.routerTree = response.result
        if (!this.currentRouter.children) {
          this.$set(this.currentRouter, 'children', [])
        }
        this.currentRouter.children.push(this.routerTree)
        this.table = false
        this.$message({
          message: '添加成功',
          type: 'success'
        })
      })
    },
    updateData() {
      editPermission(this.routerTree).then(response => {
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
