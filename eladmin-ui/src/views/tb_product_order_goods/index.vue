<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="采购单d" prop="orderId">
            <el-input v-model="form.orderId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="货品id" prop="goodId">
            <el-input v-model="form.goodId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="货品数量" prop="goodNum">
            <el-input v-model="form.goodNum" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="单价" prop="price">
            <el-input v-model="form.price" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="总价金额" prop="totalPrice">
            <el-input v-model="form.totalPrice" style="width: 370px;" />
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
        <el-table-column prop="orderId" label="采购单d" />
        <el-table-column prop="goodId" label="货品id" />
        <el-table-column prop="goodNum" label="货品数量" />
        <el-table-column prop="price" label="单价" />
        <el-table-column prop="totalPrice" label="总价金额" />
        <el-table-column prop="remark" label="备注" />
        <el-table-column v-if="checkPer(['admin','tbProductOrderGoods:edit','tbProductOrderGoods:del'])" label="操作" width="150px" align="center">
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
import crudTbProductOrderGoods from '@/api/tbProductOrderGoods'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { orderId: null, goodId: null, goodNum: null, price: null, totalPrice: null, remark: null }
export default {
  name: 'TbProductOrderGoods',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'tb_product_order_goods', url: 'api/tbProductOrderGoods', idField: 'goodId', sort: 'goodId,desc', crudMethod: { ...crudTbProductOrderGoods }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'tbProductOrderGoods:add'],
        edit: ['admin', 'tbProductOrderGoods:edit'],
        del: ['admin', 'tbProductOrderGoods:del']
      },
      rules: {
        orderId: [
          { required: true, message: '采购单d不能为空', trigger: 'blur' }
        ],
        goodId: [
          { required: true, message: '货品id不能为空', trigger: 'blur' }
        ],
        goodNum: [
          { required: true, message: '货品数量不能为空', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '单价不能为空', trigger: 'blur' }
        ],
        totalPrice: [
          { required: true, message: '总价金额不能为空', trigger: 'blur' }
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
