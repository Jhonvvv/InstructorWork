<template>
  <div class="app-container">
    <div v-if="workTimeLine.length > 0" class="block">
      <el-timeline>
        <el-timeline-item v-for="item in workTimeLine" :key="item.id" :timestamp="item.startTime" :color="item.isProvisional === 1 ? '#F56C6C' : '#67C23A'" placement="top">
          <el-card>
            <h4>{{ item.workName }}</h4>
            <p>
              {{ item.remake }}
              <span v-if="item.endTime != null ? true : false">完成于 {{ item.endTime }} </span>
            </p>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>
    <el-empty v-else description="暂无数据" />
  </div>
</template>

<script>
import { queryWorkFlow } from '@/api/work'
import waves from '@/directive/waves' // waves directive
import moment from 'moment'
export default {
  name: 'WorkTimeLine',
  directives: { waves },
  data() {
    return {
      username: this.$store.getters.name,
      workList: [],
      workTimeLine: []
    }
  },
  created() {
    this.getWorkFlow()
  },
  methods: {
    getWorkFlow() {
      queryWorkFlow(this.username).then(response => {
        const work = response.result
        this.workList.push(...work.workNorFinish)
        this.workList.push(...work.workFinish)
        this.workList.push(...work.workProvisional)
        this.workList.sort(this.compare('startTime'))
        this.handlingTime()
      })
    },
    compare(prop) {
      return (a, b) => {
        const date1 = new Date()
        const date2 = new Date()
        const value1 = a[prop].split(':')
        const value2 = b[prop].split(':')
        date1.setHours(value1[0])
        date1.setMinutes(value1[1])
        date2.setHours(value2[0])
        date2.setMinutes(value2[1])
        return date1.getTime() - date2.getTime()
      }
    },
    handlingTime() {
      this.workList.forEach(element => {
        const item = element
        let itemTime = element.startTime
        itemTime = itemTime.split(':')
        const itemDate = new Date()
        itemDate.setHours(itemTime[0])
        itemDate.setMinutes(itemTime[1])
        item.startTime = moment(itemDate).format('YYYY-MM-DD HH:mm')
        if (element.endTime !== null && element.endTime !== undefined) {
          itemTime = element.endTime.split(':')
          itemDate.setHours(itemTime[0])
          itemDate.setMinutes(itemTime[1])
          item.endTime = moment(itemDate).format('YYYY-MM-DD HH:mm')
        }

        this.workTimeLine.push(item)
      })
    }
  }
}
</script>

<style></style>
