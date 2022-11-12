<template>
  <div class="app-container">
    <div class="components-container board">
      <Kanban key="todo" :list="workList" :group="daily" class="kanban todo" header-text="待办" />
      <Kanban key="working" :list="finishWork" :group="daily" class="kanban finish" header-text="完成" />
      <div class="board-column kanban provisional">
        <div class="board-column-header">临时</div>
        <Draggable v-bind="$attrs" class="board-column-content">
          <div v-for="item in provisionalWork" :key="item.id" class="board-item">
            <el-tag :type="item.type | typeFilter">
              {{ item.type | formatType }}
            </el-tag>
            {{ item.startTime }} {{ item.workName }}
            <el-link v-if="item.endTime !== ''" type="success" :underline="false" style="float: right">{{ item.endTime }}</el-link>
          </div>
        </Draggable>
      </div>
    </div>
  </div>
</template>
<script>
import Kanban from './Kanban'
import Draggable from 'vuedraggable'
import { queryWorkNotFinish, queryProvisional, queryFinishDailyWork } from '@/api/work'
import waves from '@/directive/waves' // waves directive

export default {
  name: 'WorkBoard',
  components: {
    Kanban,
    Draggable
  },
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
  data() {
    return {
      daily: 'daily',
      provisional: 'provisional',
      username: this.$store.getters.name,
      workList: [],
      provisionalWork: [],
      finishWork: [],
      // 定时器数组
      pageTimer: []
    }
  },
  created() {
    this.getWork()
    this.getProvisionalWork()
    this.getFinishWork()
  },
  methods: {
    getFinishWork() {
      queryFinishDailyWork(this.username).then(response => {
        this.finishWork = response.result
      })
    },
    getProvisionalWork() {
      queryProvisional(this.username).then(response => {
        this.provisionalWork = response.result
        this.provisionalTimer()
      })
    },
    provisionalTimer() {
      this.provisionalWork.forEach(element => {
        const nowDate = new Date()
        let itemTime = element.startTime
        itemTime = itemTime.split(':')
        const itemDate = new Date()
        itemDate.setHours(itemTime[0])
        itemDate.setMinutes(itemTime[1])
        const valueTime = itemDate.getTime() - nowDate.getTime()
        if (valueTime > 0) {
          this.pageTimer.push(
            setTimeout(() => {
              this.$alert(element.remake, element.workName, {
                confirmButtonText: '确定'
              })
            }, valueTime / 1000)
          )
        }
      })
    },
    getWork() {
      queryWorkNotFinish(this.username).then(response => {
        this.workList = response.result
      })
    }
  }
}
</script>
<style lang="scss">
.board {
  width: 1000px;
  margin-left: 20px;
  display: flex;
  justify-content: space-around;
  flex-direction: row;
  align-items: flex-start;
}
.kanban {
  &.todo {
    .board-column-header {
      background: #4a9ff9;
    }
  }
  &.finish {
    .board-column-header {
      background: #2ac06d;
    }
  }
  &.provisional {
    .board-column-header {
      background: #f56c6c;
    }
  }
}
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
