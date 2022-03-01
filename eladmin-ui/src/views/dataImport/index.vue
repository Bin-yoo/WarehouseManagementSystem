<template>
  <div class="app-container">
    <el-tabs type="border-card">
      <el-tab-pane label="货品资料">
        <div>
          <div style="fontSize: 18px; fontWeight: bold;">导入货品资料</div>
          <br>
          <div>1.导入时请确保数据没有特殊字符与换行...</div>
          <div>2.导入时日期和数量价格都不能为空...</div>
          <div>3.导入请按模版导入...</div>
          <div>4.模版中红色列为必填项...</div>
          <div>5.文件后缀必须为xls或者xlsx</div>
          <div>6.导入数据上限为200条，若超过请分批导入!</div>
          <div>
            <span>7.点击下载</span>
            <el-link type="primary" :underline="false" @click="getGoodsImportTemplate">货品资料导入excel模版</el-link>
          </div>
          <div style="width: 250px;">
            <el-upload
              action="#"
              class="upload-demo"
              ref="goodsImportUpload"
              :file-list="fileList"
              :auto-upload="false"
              :http-request="goodsImport"
              :limit="1"
              :on-exceed="handleExceed"
            >
              <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
              <el-button style="margin-left: 10px;" size="small" type="success" @click="goodsSubmitUpload">提交</el-button>
            </el-upload>
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="往来单位资料 ">
        <div>
          <div style="fontSize: 18px; fontWeight: bold;">导入往来单位资料</div>
          <br>
          <div>1.导入时请确保数据没有特殊字符与换行...</div>
          <div>2.导入时日期和数量价格都不能为空...</div>
          <div>3.导入请按模版导入...</div>
          <div>4.模版中红色列为必填项...</div>
          <div>5.文件后缀必须为xls或者xlsx</div>
          <div>6.导入数据上限为200条，若超过请分批导入!</div>
          <div>
            <span>7.点击下载</span>
            <el-link type="primary" :underline="false" @click="getCompanyImportTemplate">往来单位资料导入excel模版</el-link>
          </div>
          <el-upload
            action="#"
            class="upload-demo"
            ref="companiesImportUpload"
            :file-list="fileList"
            :auto-upload="false"
            :http-request="companiesImport"
            :limit="1"
            :on-exceed="handleExceed"
          >
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button style="margin-left: 10px;" size="small" type="success" @click="companiesSubmitUpload">提交</el-button>
          </el-upload>
        </div>
      </el-tab-pane>
      <el-tab-pane label="员工资料">
        <div>
          <div style="fontSize: 18px; fontWeight: bold;">导入员工资料</div>
          <br>
          <div>1.导入时请确保数据没有特殊字符与换行...</div>
          <div>2.导入时日期和数量价格都不能为空...</div>
          <div>3.导入请按模版导入...</div>
          <div>4.模版中红色列为必填项...</div>
          <div>5.文件后缀必须为xls或者xlsx</div>
          <div>6.导入数据上限为200条，若超过请分批导入!</div>
          <div>
            <span>7.点击下载</span>
            <el-link type="primary" :underline="false" @click="getEmployeeImportTemplate">员工资料导入excel模版</el-link>
          </div>
          <el-upload
            action="#"
            class="upload-demo"
            ref="employeeImportUpload"
            :file-list="fileList"
            :auto-upload="false"
            :http-request="employeeImport"
            :limit="1"
            :on-exceed="handleExceed"
          >
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button style="margin-left: 10px;" size="small" type="success" @click="employeeSubmitUpload">提交</el-button>
          </el-upload>
        </div>
      </el-tab-pane>
      <!-- <el-tab-pane label="货品库初">
        <div>
          <div style="fontSize: 18px; fontWeight: bold;">导入货品库初</div>
          <br>
          <div>1.导入时请确保数据没有特殊字符与换行...</div>
          <div>2.导入时日期和数量价格都不能为空...</div>
          <div>3.导入请按模版导入...</div>
          <div>4.模版中红色列为必填项...</div>
          <div>5.文件后缀必须为xls或者xlsx</div>
          <div>6.导入数据上限为200条，若超过请分批导入!</div>
          <div>
            <span>7.点击下载</span>
            <el-link type="primary" :underline="false" @click="getInitialGoodsImportTemplate">货品库初导入excel模版</el-link>
          </div>
          <el-upload
            action="#"
            class="upload-demo"
            ref="initialGoodImportUpload"
            :file-list="fileList"
            :auto-upload="false"
            :http-request="initialGoodImport"
            :limit="1"
            :on-exceed="handleExceed"
          >
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button style="margin-left: 10px;" size="small" type="success" @click="initialGoodSubmitUpload">提交</el-button>
          </el-upload>
        </div>
      </el-tab-pane> -->
    </el-tabs>
  </div>
</template>

<script>
import DataImport from '@/api/dataImport'

export default {
  name: 'TbEmployee',
  data() {
    return {
      permission: {
        add: ['admin', 'dataImport:add'],
        edit: ['admin', 'dataImport:edit'],
        del: ['admin', 'dataImport:del']
      },
      fileList: []
    }
  },
  methods: {
    goodsImport(fileObj) {
      const file = fileObj.file
      const formData = new FormData()
      formData.append('file', file)
      DataImport.goodsImport(formData).then(res => {
        this.$notify({
          title: '导入成功!',
          type: 'success',
          duration: 2500
        })
      }).catch(error => {
        this.$notify({
          title: '导入失败!',
          message: error,
          type: 'error',
          duration: 2500
        })
      })
    },
    goodsSubmitUpload() {
      this.$refs.goodsImportUpload.submit()
    },
    companiesImport(fileObj) {
      const file = fileObj.file
      const formData = new FormData()
      formData.append('file', file)
      DataImport.companiesImport(formData).then(res => {
        this.$notify({
          title: '导入成功!',
          type: 'success',
          duration: 2500
        })
      }).catch(error => {
        this.$notify({
          title: '导入失败!',
          message: error,
          type: 'error',
          duration: 2500
        })
      })
    },
    companiesSubmitUpload() {
      this.$refs.companiesImportUpload.submit()
    },
    employeeImport(fileObj) {
      const file = fileObj.file
      const formData = new FormData()
      formData.append('file', file)
      DataImport.employeeImport(formData).then(res => {
        this.$notify({
          title: '导入成功!',
          type: 'success',
          duration: 2500
        })
      }).catch(error => {
        this.$notify({
          title: '导入失败!',
          message: error,
          type: 'error',
          duration: 2500
        })
      })
    },
    employeeSubmitUpload() {
      this.$refs.employeeImportUpload.submit()
    },
    initialGoodImport() {
      DataImport.initialGoodImport().then(res => {
        this.jobs = res
      })
    },
    initialGoodSubmitUpload() {
      this.$refs.initialGoodImportUpload.submit()
    },
    getGoodsImportTemplate() {
      DataImport.getGoodsImportTemplate().then(res => {
        this.downloadTemplate(res, '货品资料导入模板.xls')
      })
    },
    getCompanyImportTemplate() {
      DataImport.getCompanyImportTemplate().then(res => {
        this.downloadTemplate(res, '往来单位资料导入模板.xls')
      })
    },
    getEmployeeImportTemplate() {
      DataImport.getEmployeeImportTemplate().then(res => {
        this.downloadTemplate(res, '员工资料导入模板.xls')
      })
    },
    getInitialGoodsImportTemplate() {
      DataImport.getInitialGoodsImportTemplate().then(res => {
        this.downloadTemplate(res, '库初信息导入模板.xls')
      })
    },
    downloadTemplate(data, fileName) {
      const link = document.createElement('a')
      // type就是blob的type,是MIME类型的，可以自己查看MIME类型都有哪些
      const blogw = new Blob([data], { type: 'application/msexcel; charset=utf-8' })
      // 创建一个新的url对象
      const objectUrl = window.URL.createObjectURL(blogw)
      link.href = objectUrl
      // let file_name = res.headers['content-disposition'].split(';')[1].split('=')[1]; //拆解获取文件名，
      // 下载的时候自定义的文件名
      link.download = fileName
      link.click()
      // 为了更好地性能和内存使用状况，应该在适当的时候释放url.
      window.URL.revokeObjectURL(objectUrl)
    },
    handleExceed(files, fileList) {
      this.$message.warning('最多只能上传一个文件')
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
