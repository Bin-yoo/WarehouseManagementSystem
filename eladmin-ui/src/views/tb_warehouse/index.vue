<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="货品id" prop="id">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="仓库名称" prop="whName">
            <el-input v-model="form.whName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="所属部门">
            <el-input v-model="form.deptId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="库管">
            <el-input v-model="form.keeper" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="负责人">
            <el-input v-model="form.director" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input v-model="form.phone" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="地址">
            <el-input v-model="form.address" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="存货分类">
            <el-input v-model="form.goodsType" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="备注">
            <el-input v-model="form.remark" style="width: 370px;" />
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
        <el-table-column prop="id" label="货品id" />
        <el-table-column prop="whName" label="仓库名称" />
        <el-table-column prop="deptId" label="所属部门" />
        <el-table-column prop="keeper" label="库管" />
        <el-table-column prop="director" label="负责人" />
        <el-table-column prop="phone" label="联系电话" />
        <el-table-column prop="address" label="地址" />
        <el-table-column prop="goodsType" label="存货分类" />
        <el-table-column prop="remark" label="备注" />
        <el-table-column v-if="checkPer(['admin','tbWarehouse:edit','tbWarehouse:del'])" label="操作" width="150px" align="center">
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
import crudTbWarehouse from '@/api/tbWarehouse'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, whName: null, deptId: null, keeper: null, director: null, phone: null, address: null, goodsType: null, remark: null }
export default {
  name: 'TbWarehouse',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'tb_warehouse', url: 'api/tbWarehouse', idField: 'id', sort: 'id,desc', crudMethod: { ...crudTbWarehouse }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'tbWarehouse:add'],
        edit: ['admin', 'tbWarehouse:edit'],
        del: ['admin', 'tbWarehouse:del']
      },
      rules: {
        id: [
          { required: true, message: '货品id不能为空', trigger: 'blur' }
        ],
        whName: [
          { required: true, message: '仓库名称不能为空', trigger: 'blur' }
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
