<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="采购单id" prop="id">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="单号" prop="orderNo">
            <el-input v-model="form.orderNo" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="制单日期" prop="orderDate">
            <el-input v-model="form.orderDate" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="制单人id" prop="orderPersonId">
            <el-input v-model="form.orderPersonId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="制单人名称" prop="orderPerson">
            <el-input v-model="form.orderPerson" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="入库日期" prop="inDate">
            <el-input v-model="form.inDate" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="存入仓库id" prop="inWhId">
            <el-input v-model="form.inWhId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="存入仓库名称">
            <el-input v-model="form.inWhName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="生产部门id" prop="deptId">
            <el-input v-model="form.deptId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="生产部门名称">
            <el-input v-model="form.deptName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="原始单号">
            <el-input v-model="form.originOrderNo" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="备注">
            <el-input v-model="form.remark" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="采购单状态 1 未审批 2 已通过 3 未通过">
            <el-input v-model="form.status" style="width: 370px;" />
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
        <el-table-column prop="id" label="采购单id" />
        <el-table-column prop="orderNo" label="单号" />
        <el-table-column prop="orderDate" label="制单日期" />
        <el-table-column prop="orderPersonId" label="制单人id" />
        <el-table-column prop="orderPerson" label="制单人名称" />
        <el-table-column prop="inDate" label="入库日期" />
        <el-table-column prop="inWhId" label="存入仓库id" />
        <el-table-column prop="inWhName" label="存入仓库名称" />
        <el-table-column prop="deptId" label="生产部门id" />
        <el-table-column prop="deptName" label="生产部门名称" />
        <el-table-column prop="originOrderNo" label="原始单号" />
        <el-table-column prop="remark" label="备注" />
        <el-table-column prop="status" label="采购单状态 1 未审批 2 已通过 3 未通过" />
        <el-table-column v-if="checkPer(['admin','tbProductOrder:edit','tbProductOrder:del'])" label="操作" width="150px" align="center">
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
import crudTbProductOrder from '@/api/tbProductOrder'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, orderNo: null, orderDate: null, orderPersonId: null, orderPerson: null, inDate: null, inWhId: null, inWhName: null, deptId: null, deptName: null, originOrderNo: null, remark: null, status: null }
export default {
  name: 'TbProductOrder',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'tb_product_order', url: 'api/tbProductOrder', idField: 'id', sort: 'id,desc', crudMethod: { ...crudTbProductOrder }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'tbProductOrder:add'],
        edit: ['admin', 'tbProductOrder:edit'],
        del: ['admin', 'tbProductOrder:del']
      },
      rules: {
        id: [
          { required: true, message: '采购单id不能为空', trigger: 'blur' }
        ],
        orderNo: [
          { required: true, message: '单号不能为空', trigger: 'blur' }
        ],
        orderDate: [
          { required: true, message: '制单日期不能为空', trigger: 'blur' }
        ],
        orderPersonId: [
          { required: true, message: '制单人id不能为空', trigger: 'blur' }
        ],
        orderPerson: [
          { required: true, message: '制单人名称不能为空', trigger: 'blur' }
        ],
        inDate: [
          { required: true, message: '入库日期不能为空', trigger: 'blur' }
        ],
        inWhId: [
          { required: true, message: '存入仓库id不能为空', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '生产部门id不能为空', trigger: 'blur' }
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
