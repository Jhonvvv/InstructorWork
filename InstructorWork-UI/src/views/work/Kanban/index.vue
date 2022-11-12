<template>
  <div class="board-column">
    <div class="board-column-header">
      {{ headerText }}
    </div>
    <draggable :list="list" v-bind="$attrs" :disabled="disabled" class="board-column-content" :set-data="setData" @add="addList" @end="endDrag">
      <div v-for="item in list" :key="item.id" class="board-item">
        <el-tag :type="item.type | typeFilter">
          {{ item.type | formatType }}
        </el-tag>
        {{ item.startTime }} {{ item.workName }}
        <el-link v-if="item.endTime !== ''" type="success" :underline="false" style="float: right">{{ item.endTime }}</el-link>
      </div>
    </draggable>
  </div>
</template>

<script>
import draggable from 'vuedraggable'
import { finishDailyWork, revokeFinishDailyWork } from '@/api/work'

export default {
  name: 'WorkKanBan',
  components: {
    draggable
  },
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
  props: {
    headerText: {
      type: String,
      default: 'Header'
    },
    options: {
      type: Object,
      default() {
        return {}
      }
    },
    list: {
      type: Array,
      default() {
        return []
      }
    }
  },
  data() {
    return {
      work: {},
      disabled: false
    }
  },
  methods: {
    setData(dataTransfer) {
      // to avoid Firefox bug
      // Detail see : https://github.com/RubaXa/Sortable/issues/1012
      dataTransfer.setData('Text', '')
    },
    async addList(e) {
      this.work = e.item['_underlying_vm_']
      this.disabled = true
      if (this.work.isFinish === 1) {
        await revokeFinishDailyWork(this.work.id).then(response => {
          this.work.endTime = ''
          this.work = response.result
        })
      } else {
        await finishDailyWork(this.work.id).then(response => {
          this.work = response.result
        })
      }
      this.disabled = false
    },
    endDrag() {}
  }
}
</script>
<style lang="scss" scoped>
.board-column {
  min-width: 300px;
  min-height: 200px;
  height: auto;
  overflow: hidden;
  background: #f0f0f0;
  border-radius: 3px;

  .board-column-header {
    height: 80px;
    line-height: 80px;
    overflow: hidden;
    padding: 0 20px;
    text-align: center;
    background: #333;
    color: #fff;
    border-radius: 3px 3px 0 0;
  }

  .board-column-content {
    height: auto;
    overflow: hidden;
    border: 10px solid transparent;
    min-height: 60px;
    display: flex;
    justify-content: flex-start;
    flex-direction: column;
    align-items: center;

    .board-item {
      cursor: pointer;
      width: 100%;
      height: 64px;
      margin: 5px 0;
      background-color: #fff;
      text-align: left;
      line-height: 54px;
      padding: 5px 10px;
      box-sizing: border-box;
      box-shadow: 0px 1px 3px 0 rgba(0, 0, 0, 0.2);
    }
  }
}
</style>
