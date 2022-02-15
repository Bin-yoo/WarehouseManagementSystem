<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div>
        <!-- 搜索 -->
        <el-input v-model="query.orderNo" clearable placeholder="单号" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-select
          v-model="query.orderType"
          placeholder="单据类型"
          style="width: 185px;"
          class="filter-item"
          clearable
        >
          <el-option
            v-for="item in dict.order_type_for_searching"
            :key="item.id"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-input v-model="query.originOrderNo" clearable placeholder="原始单号" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-select
          v-model="query.whId"
          placeholder="仓库"
          style="width: 185px;"
          class="filter-item"
          clearable
        >
          <el-option
            v-for="item in whSelect"
            :key="item.whId"
            :label="item.whName"
            :value="item.whId"
          />
        </el-select>
        <el-input v-model="query.sourceName" clearable placeholder="来源(来往单位/部门/仓库)" style="width: 200px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <date-range-picker
          v-model="query.orderDate"
          start-placeholder="制单日期开始时间"
          end-placeholder="制单日期结束时间"
          class="filter-item"
        />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <!-- <crudOperation :permission="permission" /> -->
      <el-button
        slot="right"
        v-permission="['admin', permission.approve]"
        class="filter-item"
        size="mini"
        type="success"
        icon="el-icon-check"
        :disabled="crud.selections.length === 0"
        :loading="approveAllLoading"
        @click="toApprove(crud.selections)"
      >
        审批
      </el-button>
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="showDisable ? '查看单据' : crud.status.title" width="1000px" @closed="closeDialog">
        <el-form ref="form" :model="form" size="small" label-width="80px">
          <div class="order_info">
            <span class="order_info_title">单据信息</span>
            <el-row>
              <el-col :span="8">
                <el-form-item prop="orderNo">
                  <span slot="label">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号:</span>
                  <span>{{ form.orderNo }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="制单日期:" prop="orderDate">
                  <span>{{ form.orderDate | formatDate("") }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item prop="orderPerson">
                  <span slot="label">制&nbsp;&nbsp;单&nbsp;&nbsp;人:</span>
                  <span>{{ form.orderPerson }}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item prop="date">
                  <span slot="label" v-if="form.inOutType === 1">入库日期:</span>
                  <span slot="label" v-else-if="form.inOutType === 2">出库日期:</span>
                  <span slot="label" v-else>盘点日期:</span>
                  <span>{{ form.date | formatDate("") }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item>
                  <span slot="label" v-if="form.inOutType === 1">入库仓库:</span>
                  <span slot="label" v-else-if="form.inOutType === 2 && form.orderType !== 6">出库仓库:</span>
                  <span slot="label" v-else-if="form.orderType === 6">调入仓库:</span>
                  <span slot="label" v-else>盘点仓库:</span>
                  <span>{{ form.whName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item>
                  <span slot="label" v-if="form.orderType === 1 || form.orderType === 4">供&nbsp;&nbsp;应&nbsp;&nbsp;商:</span>
                  <span slot="label" v-else-if="form.orderType === 2">生产部门:</span>
                  <span slot="label" v-else-if="form.orderType === 3 || form.orderType === 5">客&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户:</span>
                  <span slot="label" v-else-if="form.orderType === 6">调出仓库:</span>
                  <span>{{ form.sourceName }}</span>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item>
                  <span slot="label">经&nbsp;&nbsp;办&nbsp;&nbsp;人:</span>
                  <span>{{ form.manager }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="原始单号:" prop="originOrderNo">
                  <span>{{ form.originOrderNo }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item prop="remark:">
                  <span slot="label">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</span>
                  <span>{{ form.remark }}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
          <div class="order_info" style="margin-top: 12px;">
            <span class="order_info_title">货品明细</span>
            <el-table ref="goodsTable" v-loading="goodListLoading" :data="form.goodList" size="small" style="width: 100%; margin-bottom: 10px;" max-height="400">
              <el-table-column type="index" label="#" width="40px" />
              <el-table-column prop="gCode" label="货品编码" />
              <el-table-column prop="gName" label="货品名称" />
              <el-table-column prop="unitName" label="单位" width="70px" />
              <el-table-column prop="specification" label="规格" width="70px" />
              <el-table-column prop="price" label="单价" />
              <template v-if="form.orderType === 7">
                <el-table-column prop="paperGoodNum" label="账面数量" />
                <el-table-column prop="inventoryGoodNum" label="实盘数量" />
                <el-table-column prop="plGoodNum" label="盈亏数量" />
                <el-table-column prop="plPrice" label="盈亏金额" />
              </template>
              <template v-else>
                <el-table-column prop="goodNum" label="数量" />
                <el-table-column prop="totalPrice" label="金额" />
              </template>
            </el-table>
            <div style="margin: 10px 0;">
              <el-row v-if="form.orderType === 7">
                <el-col :span="14">
                  <span style="color: #000;">大写金额: {{ form.upperCasePrice }}</span>
                </el-col>
                <el-col :span="5">
                  <span style="color: #000;">合计盈亏数量: {{ form.amountCount }}</span>
                </el-col>
                <el-col :span="5">
                  <span style="color: #000;">合计盈亏金额: {{ form.amountPrice }}元</span>
                </el-col>
              </el-row>
              <el-row v-else>
                <el-col :span="14">
                  <span style="color: #000;">大写金额: {{ form.upperCasePrice }}</span>
                </el-col>
                <el-col :span="5">
                  <span style="color: #000;">合计数量: {{ form.amountCount }}</span>
                </el-col>
                <el-col :span="5">
                  <span style="color: #000;">合计金额: {{ form.amountPrice }}元</span>
                </el-col>
              </el-row>
            </div>
          </div>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button v-if="!showDisable" :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="orderType" label="单据类型">
              <template slot-scope="scope">
                {{ formatOrderType(scope.row.orderType) }}
              </template>
            </el-table-column>
        <el-table-column prop="orderNo" label="单号" width="150">
          <template slot-scope="scope">
            <span style="color: #0000FF; cursor: pointer;" @click="crud.toEdit(scope.row),showOrder()">{{ scope.row.orderNo }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="orderDate" label="制单日期" width="90px">
          <template slot-scope="scope">
            {{ scope.row.orderDate | formatDate("") }}
          </template>
        </el-table-column>
        <el-table-column prop="orderPerson" label="制单人" />
        <el-table-column prop="manager" label="经办人" />
        <el-table-column prop="date" label="出库日期" width="90px">
          <template slot-scope="scope">
            {{ scope.row.date | formatDate("") }}
          </template>
        </el-table-column>
        <el-table-column prop="whName" label="仓库" />
        <el-table-column prop="sourceName" label="来往单位/部门/仓库" />
        <el-table-column prop="originOrderNo" label="原始单号" />
        <el-table-column prop="status" label="单据状态">
          <template slot-scope="scope">
            {{ scope.row.status | convertStatus("") }}
          </template>
        </el-table-column>
        <el-table-column prop="verifyPerson" label="审核人" />
        <el-table-column prop="verifyDate" label="审核日期" width="90px" />
        <el-table-column prop="updateBy" label="修改人" />
        <el-table-column prop="updateTime" label="修改日期" width="90px" />
        <el-table-column prop="remark" label="备注" width="200">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.remark" placement="top-start">
              <div>{{ scope.row.remark ? scope.row.remark.length > 12 ? scope.row.remark.substr(0,12)+'...' : scope.row.remark : '' }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column v-if="checkPer(['admin','waitApproveOrders:print'])" fixed="right" label="操作" align="center" width="200px">
          <template slot-scope="scope">
            <el-button type="success" :loading="crud.dataStatus[crud.getDataId(scope.row)].approve === 2" size="mini" icon="el-icon-check" @click="toApprove(scope.row)" />
            <el-button size="mini" icon="el-icon-printer" @click="toPrint(scope.row)" />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
      <OrderPrinting :print-order-id="printOrderId" :inner-visible="printVisible" @cancel="cancelprinting" />
    </div>
  </div>
</template>

<script>
import WaitApproveOrders from '@/api/waitApproveOrders.js'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker/index'
import crudTbWhInventory from '@/api/tbWhInventory'
import { getFormatDate } from '@/utils/common.js'
import OrderPrinting from '@/components/OrderPrintingTemplate/SearchOrderTemplate'

const defaultForm = { id: null, inOutType: null, orderType: null, orderNo: null, orderDate: null, orderPersonId: null, orderPerson: null, managerId: null, manager: null, date: null, whId: null, whName: null, sourceId: null, sourceName: null, originOrderNo: null, upperCasePrice: '零元整', amountCount: 0, amountPrice: 0, status: null, verifyDate: null, verifyPersonId: null, verifyPerson: null, delFlag: null, updateTime: null, updateBy: null, remark: null, goodList: [] }
export default {
  name: 'WaitApproveOrders',
  components: { pagination, crudOperation, rrOperation, DateRangePicker, OrderPrinting },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '采购退货单', url: 'api/waitApproveOrders', idField: 'id', sort: 'id,desc', crudMethod: { ...WaitApproveOrders }})
  },
  dicts: ['order_type_for_searching'],
  data() {
    return {
      permission: {
        detail: ['admin', 'waitApproveOrders:detail'],
        approve: ['admin', 'waitApproveOrders:approve'],
        print: ['admin', 'waitApproveOrders:print']
      },
      whSelect: [],
      innerVisible: false,
      showDisable: false,
      goodListLoading: false,
      printVisible: false,
      printOrderId: null,
      chooseOrderVisible: false,
      approveAllLoading: false
    }
  },
  created() {
    this.getWareHouseSelect()
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    },
    // 新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      if (form.id !== null) {
        this.getOrderGoodList(form.id)
      }
    },
    closeDialog() {
      this.showDisable = false
    },
    getWareHouseSelect() {
      crudTbWhInventory.getWareHouseSelect().then(res => {
        this.whSelect = res
      })
    },
    getOrderGoodList(id) {
      this.goodListLoading = true
      WaitApproveOrders.getOrderGoodList(id).then(res => {
        this.form.goodList = res
        this.goodListLoading = false
      }).catch(() => {
        this.goodListLoading = false
      })
    },
    showOrder() {
      this.showDisable = true
    },
    toPrint(data) {
      this.printOrderId = data.id
      this.printVisible = true
    },
    cancelprinting() {
      this.printVisible = false
      this.printOrderId = null
    },
    deleteAllPicked() {
      this.form.goodList = []
      this.$nextTick(() => {
        this.calculate()
      })
    },
    chooseOrder() {
      if (this.form.goodList.length == 0 || this.form.goodList === []) {
        this.chooseOrderVisible = true
      } else {
        this.$notify({
          title: '选择单据前,需清空货品明细!',
          type: 'warning',
          duration: 2500
        })
      }
    },
    cancelChoose() {
      this.chooseOrderVisible = false
    },
    selectGood(val) {
      this.form.goodList = val.selectGoodList
      this.form.whId = val.whId
      this.form.whName = val.whName
      this.form.sourceId = val.sourceId
      this.form.sourceName = val.sourceName
      this.$nextTick(() => {
        this.calculate()
      })
    },
    formatOrderType(val) {
      let label = ''
      for (const [i, v] of this.dict.order_type_for_searching.entries()) {
        if (v.value == val) {
          label = v.label
          break
        }
      }
      return label
    },
    toApprove(datas) {
      let confirm = ''
      if (datas instanceof Array) {
        confirm = `确认审批通过选中的${datas.length}条数据?`
      } else {
        confirm = `确认审批通过该条数据?`
      }
      this.$confirm(confirm, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        let processAll = false
        let dataStatus
        const ids = []
        if (datas instanceof Array) {
          this.approveAllLoading = true
          processAll = true
          datas.forEach(val => {
            ids.push(this.crud.getDataId(val))
          })
        } else {
          ids.push(this.crud.getDataId(datas))
          dataStatus = this.crud.getDataStatus(this.crud.getDataId(datas))
        }
        if (!processAll) {
          dataStatus.approve = 2
        }
        return WaitApproveOrders.approveOrders(ids).then(() => {
          if (processAll) {
            this.approveAllLoading = false
          } else dataStatus.approve = 0
          this.successNotify('审批')
          this.crud.refresh()
        }).catch(() => {
          if (processAll) {
            this.approveAllLoading = false
          } else dataStatus.approve = 0
        })
      }).catch(() => {
        this.approveAllLoading = false
      })
    },
    successNotify(title) {
      this.$notify({
        title: '批量' + title + '成功!',
        type: 'warning',
        duration: 2500
      })
    }
  },
  filters: {
    convertStatus(val) {
      if (val === 1) {
        return '未审批'
      }
      if (val === 2) {
        return '已审批'
      }
      if (val === 3) {
        return '反审中'
      }
    },
    formatDate(val) {
      return getFormatDate(val)
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
.order_info{
  position: relative;
  border: 1px solid #1E9FFF;
  border-radius: 5px;
  padding: 15px 5px 0px 15px;
  .order_info_title{
    position: absolute;
    top: -12px;
    left: 12px;
    padding: 5px;
    font-size: 12px;
    background-color: #fff;
    color: red;
  }
}
.el-dialog__body {
  padding: 30px 10px !important;
}
</style>
