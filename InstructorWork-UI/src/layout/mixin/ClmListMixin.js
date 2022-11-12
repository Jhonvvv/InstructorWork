import request from '@/utils/request'
export const ClmListMixin = {
  data() {
    return {
      /* 查询条件-请不要在queryParam中声明非字符串值的属性 */
      queryParam: {}
    }
  },
  methods: {
    /**
     * 下载文件 用于excel导出
     * @param url
     * @returns {*}
     */
    downFile(url, parameter) {
      return request({
        url,
        params: parameter,
        method: 'get',
        responseType: 'blob'
      })
    },
    handleExportXls(fileName) {
      if (!fileName || typeof fileName !== 'string') {
        fileName = '导出文件'
      }
      this.downFile(this.url.exportXlsUrl, this.queryParam).then(data => {
        if (!data) {
          this.$message.warning('文件下载失败')
          return
        }
        if (typeof window.navigator.msSaveBlob !== 'undefined') {
          window.navigator.msSaveBlob(new Blob([data], { type: 'application/vnd.ms-excel' }), fileName + '.xls')
        } else {
          const url = window.URL.createObjectURL(new Blob([data], { type: 'application/vnd.ms-excel' }))
          const link = document.createElement('a')
          link.style.display = 'none'
          link.href = url
          link.setAttribute('download', fileName + '.xls')
          document.body.appendChild(link)
          link.click()
          document.body.removeChild(link) // 下载完成移除元素
          window.URL.revokeObjectURL(url) // 释放掉blob对象
        }
      })
    }
  }
}
