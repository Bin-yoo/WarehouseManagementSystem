<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="货品id" prop="goodId">
            <el-input v-model="form.goodId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="仓库id" prop="whId">
            <el-input v-model="form.whId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="货品数量" prop="count">
            <el-input v-model="form.count" style="width: 370px;" />
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
        <el-table-column prop="goodId" label="货品id" />
        <el-table-column prop="whId" label="仓库id" />
        <el-table-column prop="count" label="货品数量" />
        <el-table-column v-if="checkPer(['admin','tbWhInventory:edit','tbWhInventory:del'])" label="操作" width="150px" align="center">
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
import crudTbWhInventory from '@/api/tbWhInventory'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { goodId: null, whId: null, count: null }
export default {
  name: 'TbWhInventory',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'tb_wh_inventory', url: 'api/tbWhInventory', idField: 'whId', sort: 'whId,desc', crudMethod: { ...crudTbWhInventory }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'tbWhInventory:add'],
        edit: ['admin', 'tbWhInventory:edit'],
        del: ['admin', 'tbWhInventory:del']
      },
      rules: {
        goodId: [
          { required: true, message: '货品id不能为空', trigger: 'blur' }
        ],
        whId: [
          { required: true, message: '仓库id不能为空', trigger: 'blur' }
        ],
        count: [
          { required: true, message: '货品数量不能为空', trigger: 'blur' }
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
