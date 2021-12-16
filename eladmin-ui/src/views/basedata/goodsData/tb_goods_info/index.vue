<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="800px">
        <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="货品编码" prop="gCode">
            <el-input v-model="form.gCode" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="货品名称" prop="gName">
            <el-input v-model="form.gName" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="规格">
            <el-input v-model="form.specification" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="拼音码">
            <el-input v-model="form.pyCode" disabled placeholder="自动生成" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="单位" prop="gUnit">
            <el-input v-model="form.gUnit" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="货品类别" prop="gType">
            <el-input v-model="form.gType" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="进价">
            <el-input v-model="form.pPrice" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="售价">
            <el-input v-model="form.sPrice" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="库存上限">
            <el-input v-model="form.uLimit" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="库存下限">
            <el-input v-model="form.lLimit" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="货位">
            <el-input v-model="form.gAllocation" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="生产厂家">
            <el-input v-model="form.manufacturer" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="备注">
            <el-input v-model="form.remark" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="批次">
            <el-input v-model="form.batch" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="生产日期">
            <el-input v-model="form.productDate" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="有效日期">
            <el-input v-model="form.validDate" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="物资来源">
            <el-input v-model="form.source" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="条形码">
            <el-input v-model="form.barCode" style="width: 280px;" />
          </el-form-item>
          <el-form-item label="二维码">
            <el-input v-model="form.qrCode" style="width: 280px;" />
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
        <el-table-column prop="gCode" label="货品编码" />
        <el-table-column prop="gName" label="货品名称" />
        <el-table-column prop="specification" label="规格" />
        <el-table-column prop="pyCode" label="拼音码" />
        <el-table-column prop="gUnit" label="单位" />
        <el-table-column prop="gType" label="货品类别" />
        <el-table-column prop="pPrice" label="进价" />
        <el-table-column prop="sPrice" label="售价" />
        <el-table-column prop="uLimit" label="库存上限" />
        <el-table-column prop="lLimit" label="库存下限" />
        <el-table-column prop="gAllocation" label="货位" />
        <el-table-column prop="manufacturer" label="生产厂家" />
        <el-table-column prop="remark" label="备注" />
        <el-table-column prop="batch" label="批次" />
        <el-table-column prop="productDate" label="生产日期" />
        <el-table-column prop="validDate" label="有效日期" />
        <el-table-column prop="source" label="物资来源" />
        <el-table-column prop="barCode" label="条形码" />
        <el-table-column prop="qrCode" label="二维码" />
        <el-table-column v-if="checkPer(['admin','tbGoodsInfo:edit','tbGoodsInfo:del'])" label="操作" width="150px" align="center">
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
import crudTbGoodsInfo from '@/api/tbGoodsInfo'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, gCode: null, gName: null, specification: null, pyCode: null, gUnit: null, gType: null, pPrice: null, sPrice: null, uLimit: null, lLimit: null, gAllocation: null, manufacturer: null, remark: null, batch: null, productDate: null, validDate: null, source: null, barCode: null, qrCode: null }
export default {
  name: 'TbGoodsInfo',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '货品', url: 'api/tbGoodsInfo', idField: 'id', sort: 'id,desc', crudMethod: { ...crudTbGoodsInfo }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'tbGoodsInfo:add'],
        edit: ['admin', 'tbGoodsInfo:edit'],
        del: ['admin', 'tbGoodsInfo:del']
      },
      rules: {
        id: [
          { required: true, message: '货品id不能为空', trigger: 'blur' }
        ],
        gCode: [
          { required: true, message: '货品编码不能为空', trigger: 'blur' }
        ],
        gName: [
          { required: true, message: '货品名称不能为空', trigger: 'blur' }
        ],
        gUnit: [
          { required: true, message: '单位不能为空', trigger: 'blur' }
        ],
        gType: [
          { required: true, message: '货品类别不能为空', trigger: 'blur' }
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
