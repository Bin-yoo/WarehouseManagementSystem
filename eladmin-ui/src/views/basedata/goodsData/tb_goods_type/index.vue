<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div class="crud-opts" style="">
        <span class="crud-opts-left">
          <!--左侧插槽-->
          <slot name="left" />
          <el-button v-if="checkPer(['admin',permission.add])" v-permission="permission.add" class="filter-item" size="mini"
            type="primary" icon="el-icon-plus" @click="toAdd()">
            新增
          </el-button>
          <!-- <el-button v-if="crud.optShow.download" :loading="crud.downloadLoading" :disabled="!crud.data.length"
            class="filter-item" size="mini" type="warning" icon="el-icon-download" @click="crud.doExport">导出</el-button> -->
          <!--右侧-->
          <slot name="right" />
        </span>
        <el-button-group class="crud-opts-right">
          <el-button size="mini" icon="el-icon-refresh" @click="refresh()" />
        </el-button-group>
      </div>
    </div>
    <!--表单组件-->
    <el-dialog :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialogShow"
      :title="title" width="500px">
      <el-form ref="form" :model="defaultForm" :rules="rules" size="small" label-width="80px">
        <el-form-item label="类型名称" prop="gtName">
          <el-input v-model="defaultForm.gtName" style="width: 370px;" />
        </el-form-item>
        <el-form-item label="是否目录" prop="isFolder">
          <el-radio-group v-model="defaultForm.isFolder" style="width: 140px">
            <el-radio :label="1">是</el-radio>
            <el-radio :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item style="margin-bottom: 0;" label="上级类型" prop="parentId">
          <treeselect v-model="defaultForm.parentId" :options="typesSelectTree" style="width: 370px;"
            placeholder="选择上级类型" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="text" @click="cancel">取消</el-button>
        <el-button :loading="status" type="primary" @click="submitForm">确认</el-button>
      </div>
    </el-dialog>
    <!--表格渲染-->
    <el-table ref="table" v-loading="tableLoading" row-key='id' default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}" :data="types" size="small"
      style="width: 100%;">
      <el-table-column prop="gtName" label="货品类型名称">
        <template slot-scope="scope">
          <i class="el-icon-folder" v-if="scope.row.isFolder == 1"></i>
          <i class="el-icon-document" v-else></i>
          {{scope.row.gtName}}
        </template>
      </el-table-column>
      <el-table-column v-if="checkPer(['admin','tbGoodsType:edit','tbGoodsType:del'])" label="操作" width="300px"
        align="center">
        <template slot-scope="scope">
          <el-button v-if="checkPer(['admin',permission.add]) && scope.row.isFolder == 1" v-permission="permission.add" :loading="status" class="filter-item" size="mini"
            type="primary" icon="el-icon-plus" @click="toAdd(scope.row.id)" />
          <el-button style="margin-left: 0px;" v-if="scope.row.id != 1" v-permission="permission.edit" :loading="status" size="mini" type="primary" icon="el-icon-edit" @click="toEdit(scope.row.id)" />
          <myDoperation v-if="scope.row.id != 1 && scope.row.children.length == 0" :permission="permission" :status="status" :id='scope.row.id' @doDelete="doDelete"/>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import crudTbGoodsType from '@/api/tbGoodsType'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import {LOAD_CHILDREN_OPTIONS} from '@riophae/vue-treeselect'
import checkPer from '@/utils/permission' // 权限判断函数
import MyDoperation from '@/components/MyD.operation'

export default {
  name: 'TbGoodsType',
  components: {
    Treeselect,
    MyDoperation
  },
  data() {
    return {
      permission: {
        add: ['admin', 'tbGoodsType:add'],
        edit: ['admin', 'tbGoodsType:edit'],
        del: ['admin', 'tbGoodsType:del']
      },
      types: [],
      typesSelectTree: [],
      dialogShow: false,
      status: false,
      tableLoading: false,
      pop: false,
      title: '',
      CUstatus: 1,
      defaultForm: {
        id: null,
        gtName: null,
        parentId: null,
        isFolder: null
      },
      rules: {
        gtName: [{
          required: true,
          message: '货品类型名称不能为空',
          trigger: 'blur'
        }],
        parentId: [{
          required: true,
          message: '父级分类必须设置',
          trigger: 'blur'
        }],
        isFolder: [{
          required: true,
          message: '是否文件夹不能为空',
          trigger: 'blur'
        }]
      }
    }
  },
  created() {
    this.getTypeDatas()
  },
  checkPer,
  methods: {
    refresh() {
      this.getTypeDatas()
    },
    cancel() {
      this.dialogShow = false
      this.$refs['form'].resetFields()
      this.defaultForm.id = ''
    },
    toAdd(id) {
      this.title = '新增货品类型'
      this.dialogShow = true
      this.typesSelectTree = []
      this.loadTypesSelectTree()
      this.CUstatus = 1
      if (id != null || id != '') {
        setTimeout(() => {
          this.defaultForm.parentId = id
        }, 100)
      }
    },
    toEdit(id) {
      this.title = '修改货品类型'
      this.dialogShow = true
      this.typesSelectTree = []
      this.loadTypesSelectTree()
      this.getTypeDetail(id)
      this.CUstatus = 2
    },
    getTypeDatas() {
      this.tableLoading= true
      setTimeout(() => {
        crudTbGoodsType.getTypeDatas().then(res => {
          this.types = res;
          this.tableLoading= false
          // resolve(res.content)
        })
      }, 100)
    },
    // 获取弹窗内分类数据
    loadTypesSelectTree () {
        crudTbGoodsType.getTypesSelectTree().then(res => {
          this.typesSelectTree = res;
        })
    },
    submitForm() {
      this.status = true
      this.$refs['form'].validate((valid) => {
        if (valid) {
          if (this.CUstatus == 1) {
            this.addType()
          } else if (this.CUstatus == 2) {
            this.editType()
          }
          this.status = false
        } else {
          this.notifyMsg('表单有必填字段未填', 'warning')
          this.status = false
        }
      })
    },
    addType(){
      crudTbGoodsType.add(this.defaultForm).then(res => {
        this.cancel()
        this.notifyMsg('新增成功', 'success')
        this.refresh()
      }).catch(err => {
        this.notifyMsg(err.response.data.message, 'error')
      })
    },
    getTypeDetail(id) {
      crudTbGoodsType.getTypeDetail(id).then(res => {
        this.defaultForm = res
      }).catch(err => {
        this.notifyMsg(err.response.data.message, 'error')
      })
    },
    onPopoverShow() {
      setTimeout(() => {
        document.addEventListener('click', this.handleDocumentClick)
      }, 0)
    },
    onPopoverHide() {
      document.removeEventListener('click', this.handleDocumentClick)
    },
    handleDocumentClick(event) {
      this.pop = false
    },
    editType(){
      crudTbGoodsType.edit(this.defaultForm).then(res => {
        this.cancel()
        this.notifyMsg('修改成功', 'success')
        this.refresh()
      }).catch(err => {
        this.notifyMsg(err.response.data.message, 'error')
      })
    },
    doDelete(id) {
      this.status= true
      crudTbGoodsType.del(id).then(res => {
        this.notifyMsg('删除成功', 'success')
        this.refresh()
        this.status= false
      }).catch(err => {
        this.notifyMsg(err.response.data.message, 'error')
      })
    },
    notifyMsg(title, type) {
      this.$notify({
        title,
        type,
        duration: 2500
      })
    }
  }
}
</script>

<style scoped>
  .crud-opts {
    padding: 4px 0;
    display: -webkit-flex;
    display: flex;
    align-items: center;
  }
  .crud-opts .crud-opts-right {
    margin-left: auto;
  }
  .crud-opts .crud-opts-right span {
    float: left;
  }
</style>
