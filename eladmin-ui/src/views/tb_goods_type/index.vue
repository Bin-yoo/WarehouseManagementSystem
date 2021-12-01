<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="货品类型id" prop="id">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="货品类型名称" prop="gtName">
            <el-input v-model="form.gtName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="父级id" prop="parentId">
            <el-input v-model="form.parentId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="是否文件夹" prop="isFolder">
            <el-input v-model="form.isFolder" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="货品类型id" />
        <el-table-column prop="gtName" label="货品类型名称" />
        <el-table-column prop="parentId" label="父级id" />
        <el-table-column prop="isFolder" label="是否文件夹" />
        <el-table-column v-if="checkPer(['admin','tbGoodsType:edit','tbGoodsType:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudTbGoodsType from '@/api/tbGoodsType'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, gtName: null, parentId: null, isFolder: null }
export default {
  name: 'TbGoodsType',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'tb_goods_type', url: 'api/tbGoodsType', idField: 'id', sort: 'id,desc', crudMethod: { ...crudTbGoodsType }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'tbGoodsType:add'],
        edit: ['admin', 'tbGoodsType:edit'],
        del: ['admin', 'tbGoodsType:del']
      },
      rules: {
        id: [
          { required: true, message: '货品类型id不能为空', trigger: 'blur' }
        ],
        gtName: [
          { required: true, message: '货品类型名称不能为空', trigger: 'blur' }
        ],
        parentId: [
          { required: true, message: '父级id不能为空', trigger: 'blur' }
        ],
        isFolder: [
          { required: true, message: '是否文件夹不能为空', trigger: 'blur' }
        ]
      }}
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
