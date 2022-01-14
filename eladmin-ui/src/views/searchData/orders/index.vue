<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input v-model="query.orderType" clearable placeholder="单据类型" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-input v-model="query.orderNo" clearable placeholder="单号" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-input v-model="query.orderPerson" clearable placeholder="制单人名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-input v-model="query.manager" clearable placeholder="经办人名称" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-input v-model="query.whId" clearable placeholder="存入仓库id" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-input v-model="query.originOrderNo" clearable placeholder="原始单号" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <date-range-picker
          v-model="query.orderDate"
          start-placeholder="制单日期开始时间"
          end-placeholder="制单日期结束时间"
          class="filter-item"
        />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
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
          <el-form-item label="经办人id" prop="managerId">
            <el-input v-model="form.managerId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="经办人名称">
            <el-input v-model="form.manager" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="入库日期" prop="date">
            <el-input v-model="form.date" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="存入仓库名称">
            <el-input v-model="form.whName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="货品来源(来往单位、部门或仓库名称)">
            <el-input v-model="form.sourceName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="原始单号" prop="originOrderNo">
            <el-input v-model="form.originOrderNo" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="采购单状态 1 未审批 2 已通过 3 反审核">
            <el-input v-model="form.status" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="审核日期">
            <el-input v-model="form.verifyDate" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="审核人名称">
            <el-input v-model="form.verifyPerson" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="最后修改日期">
            <el-input v-model="form.updateTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="最后修改人">
            <el-input v-model="form.updateBy" style="width: 370px;" />
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
        <el-table-column prop="orderType" label="单据类型" />
        <el-table-column prop="orderNo" label="单号" />
        <el-table-column prop="orderDate" label="制单日期" />
        <el-table-column prop="orderPerson" label="制单人名称" />
        <el-table-column prop="manager" label="经办人名称" />
        <el-table-column prop="date" label="入库日期" />
        <el-table-column prop="whName" label="存入仓库名称" />
        <el-table-column prop="sourceName" label="货品来源(来往单位、部门或仓库名称)" />
        <el-table-column prop="originOrderNo" label="原始单号" />
        <el-table-column prop="status" label="采购单状态 1 未审批 2 已通过 3 反审核" />
        <el-table-column prop="verifyDate" label="审核日期" />
        <el-table-column prop="verifyPerson" label="审核人名称" />
        <el-table-column prop="delFlag" label="删除标识" />
        <el-table-column prop="updateTime" label="最后修改日期" />
        <el-table-column prop="updateBy" label="最后修改人" />
        <el-table-column prop="remark" label="备注" />
        <el-table-column v-if="checkPer(['admin','purchaseOrders:edit','purchaseOrders:del'])" label="操作" width="150px" align="center">
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
import PurchaseOrders from '@/api/purchaseOrders.js'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker/index'

const defaultForm = { id: null, orderType: null, orderNo: null, orderDate: null, orderPersonId: null, orderPerson: null, managerId: null, manager: null, date: null, whId: null, whName: null, sourceId: null, sourceName: null, originOrderNo: null, status: null, verifyDate: null, verifyPersonId: null, verifyPerson: null, delFlag: null, updateTime: null, updateBy: null, remark: null }
export default {
  name: 'PurchaseOrders',
  components: { pagination, crudOperation, rrOperation, udOperation, DateRangePicker },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '采购入库单', url: 'api/purchaseOrders', idField: 'id', sort: 'id,desc', crudMethod: { ...PurchaseOrders }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'purchaseOrders:add'],
        edit: ['admin', 'purchaseOrders:edit'],
        del: ['admin', 'purchaseOrders:del']
      },
      rules: {
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
        managerId: [
          { required: true, message: '必须选择经办人', trigger: 'blur' }
        ],
        date: [
          { required: true, message: '入库日期不能为空', trigger: 'blur' }
        ],
        whId: [
          { required: true, message: '必须选择存入仓库', trigger: 'blur' }
        ],
        originOrderNo: [
          { required: true, message: '原始单号不能为空', trigger: 'blur' }
        ]
      }
    }
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
