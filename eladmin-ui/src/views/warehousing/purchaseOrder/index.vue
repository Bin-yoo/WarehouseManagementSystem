<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input v-model="query.orderNo" clearable placeholder="单号" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-input v-model="query.orderPerson" clearable placeholder="制单人" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-input v-model="query.manager" clearable placeholder="经办人" style="width: 185px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-select
          v-model="query.whId"
          placeholder="存入仓库"
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
        <el-select
          v-model="query.sourceId"
          placeholder="供应商"
          style="width: 185px;"
          class="filter-item"
          clearable
        >
          <el-option
            v-for="item in supplierSelect"
            :key="item.id"
            :label="item.cName"
            :value="item.id"
          />
        </el-select>
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
      <crudOperation :permission="permission">
        <el-button
          slot="right"
          v-permission="permission.approve"
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
        <el-button
          slot="right"
          v-permission="permission.reApprove"
          class="filter-item"
          size="mini"
          type="warning"
          icon="el-icon-refresh-left"
          style="margin-left: 4px !important;"
          :disabled="crud.selections.length === 0"
          :loading="reApproveAllLoading"
          @click="toReApprove(crud.selections)"
        >
          反审
        </el-button>
      </crudOperation>
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="showDisable ? '查看单据' : crud.status.title" width="1000px" @closed="closeDialog">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <div class="order_info">
            <span class="order_info_title">单据信息</span>
            <el-row>
              <el-col :span="8">
                <el-form-item prop="orderNo">
                  <span slot="label">单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号</span>
                  <el-input v-model="form.orderNo" disabled style="width: 90%" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="制单日期" prop="orderDate">
                  <el-date-picker
                    v-model="form.orderDate"
                    type="date"
                    style="width: 90%"
                    placeholder="制单日期"
                    disabled
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item prop="orderPerson">
                  <span slot="label">制&nbsp;&nbsp;单&nbsp;&nbsp;人</span>
                  <el-input v-model="form.orderPerson" disabled style="width: 90%" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="入库日期" prop="date">
                  <el-date-picker
                    v-model="form.date"
                    type="date"
                    style="width: 90%"
                    placeholder="选择入库日期"
                    :disabled="showDisable"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="存入仓库" prop="whId">
                  <el-select
                    v-model="form.whId"
                    placeholder="请选择存入仓库"
                    style="width: 90%"
                    :disabled="showDisable"
                    clearable
                    @change="changeWarehouse"
                  >
                    <el-option
                      v-for="item in dialogWhSelect"
                      :key="item.whId"
                      :label="item.whName"
                      :value="item.whId"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item prop="sourceId">
                  <span slot="label">供&nbsp;&nbsp;应&nbsp;&nbsp;商</span>
                  <el-select
                    v-model="form.sourceId"
                    placeholder="请选择供应商"
                    style="width: 90%"
                    :disabled="showDisable"
                    clearable
                    @change="changeSupplier"
                  >
                    <el-option
                      v-for="item in dialogSupplierSelect"
                      :key="item.id"
                      :label="item.cName"
                      :value="item.id"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item prop="managerId">
                  <span slot="label">经&nbsp;&nbsp;办&nbsp;&nbsp;人</span>
                  <el-select
                    v-model="form.managerId"
                    placeholder="请选择经办人"
                    style="width: 90%"
                    :disabled="showDisable"
                    clearable
                    @change="changeManager"
                  >
                    <el-option
                      v-for="item in managerSelect"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="原始单号" prop="originOrderNo">
                  <el-input v-model="form.originOrderNo" :disabled="showDisable" style="width: 90%" />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item prop="remark">
                  <span slot="label">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注</span>
                  <el-input v-model="form.remark" :disabled="showDisable" style="width: 90%" />
                </el-form-item>
              </el-col>
            </el-row>
          </div>
          <div class="order_info" style="margin-top: 12px;">
            <span class="order_info_title">货品明细</span>
            <el-button v-if="!showDisable" type="primary" size="mini" icon="el-icon-plus" @click="pickGood">添加货品</el-button>
            <el-button v-if="!showDisable" type="danger" size="mini" icon="el-icon-delete" @click="deleteAllPicked">清空货品</el-button>
            <el-table ref="goodsTable" v-loading="goodListLoading" :data="form.goodList" size="small" style="width: 100%; margin-bottom: 10px;" max-height="400">
              <el-table-column v-if="!showDisable" width="80px">
                <template slot-scope="scope">
                  <el-button type="danger" size="mini" @click="removePick(scope.$index)">移除</el-button>
                </template>
              </el-table-column>
              <el-table-column type="index" label="#" width="40px" />
              <el-table-column prop="gCode" label="货品编码" />
              <el-table-column prop="gName" label="货品名称" />
              <el-table-column prop="unitName" label="单位" width="70px" />
              <el-table-column prop="specification" label="规格" width="70px" />
              <el-table-column label="单价">
                <template slot-scope="scope">
                  <el-input-number v-model="scope.row.purchasePrice" :disabled="showDisable" :min="0" :precision="2" controls-position="right" style="width: 100%" @change="changeNumOrPrice(scope.row,scope.$index)" />
                </template>
              </el-table-column>
              <el-table-column label="数量">
                <template slot-scope="scope">
                  <el-input-number v-model="scope.row.goodNum" :disabled="showDisable" :min="1" controls-position="right" style="width: 100%" @change="changeNumOrPrice(scope.row,scope.$index)" />
                </template>
              </el-table-column>
              <el-table-column label="金额">
                <template slot-scope="scope">
                  <el-input-number v-model="scope.row.totalPrice" :disabled="showDisable" :min="0" :precision="2" controls-position="right" style="width: 100%" @change="calculate()" />
                </template>
              </el-table-column>
            </el-table>
            <div style="margin: 10px 0;">
              <el-row>
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
        <GoodChooseBoard :inner-visible="innerVisible" :wh-id="form.whId" @cancel="cancelPickUp" @pickup="pickup" />
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button v-if="!showDisable" :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="orderType" label="单据类型">
          <span>采购入库单</span>
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
        <el-table-column prop="date" label="入库日期" width="90px">
          <template slot-scope="scope">
            {{ scope.row.date | formatDate("") }}
          </template>
        </el-table-column>
        <el-table-column prop="whName" label="存入仓库" />
        <el-table-column prop="sourceName" label="供应商" />
        <el-table-column prop="originOrderNo" label="原始单号" />
        <el-table-column prop="status" label="采购单状态">
          <template slot-scope="scope">
            <el-tag type="danger" size="mini" effect="dark" v-if="scope.row.status === 1" >未审批</el-tag>
            <el-tag type="info" size="mini" effect="dark" v-if="scope.row.status === 2" >已审批</el-tag>
            <el-tag type="warning" size="mini" effect="dark" v-if="scope.row.status === 3" >反审中</el-tag>
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
        <el-table-column v-if="checkPer(['admin','purchaseOrders:edit','purchaseOrders:del','purchaseOrders:approve','purchaseOrders:reApprove','purchaseOrders:print'])" fixed="right" label="操作" width="295px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            >
              <el-button slot="right" type="success" :loading="crud.dataStatus[crud.getDataId(scope.row)].approve === 2" size="mini" icon="el-icon-check" style="margin-right: 2px;" v-permission="permission.approve" @click="toApprove(scope.row)" />
              <el-button slot="right" type="warning" :loading="crud.dataStatus[crud.getDataId(scope.row)].approve === 2" size="mini" icon="el-icon-refresh-left" style="margin-right: 2px;" v-permission="permission.reApprove" @click="toReApprove(scope.row)" />
              <el-button slot="right" size="mini" icon="el-icon-printer" style="margin-right: 2px;" v-permission="permission.print" @click="toPrint(scope.row)" />
            </udOperation>
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
import PurchaseOrders from '@/api/purchaseOrders.js'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import DateRangePicker from '@/components/DateRangePicker/index'
import crudTbWhInventory from '@/api/tbWhInventory'
import { mapGetters } from 'vuex'
import { getFormatDate, changeMoneyToChinese } from '@/utils/common.js'
import GoodChooseBoard from '@/components/GoodChooseBoard'
import OrderPrinting from '@/components/OrderPrintingTemplate/PurchaseOrderTemplate'

const defaultForm = { id: null, orderType: null, orderNo: null, orderDate: null, orderPersonId: null, orderPerson: null, managerId: null, manager: null, date: null, whId: null, whName: null, sourceId: null, sourceName: null, originOrderNo: null, upperCasePrice: '零元整', amountCount: 0, amountPrice: 0, status: null, verifyDate: null, verifyPersonId: null, verifyPerson: null, delFlag: null, updateTime: null, updateBy: null, remark: null, goodList: [] }
export default {
  name: 'PurchaseOrders',
  components: { pagination, crudOperation, rrOperation, udOperation, DateRangePicker, GoodChooseBoard, OrderPrinting },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '采购入库单', url: 'api/purchaseOrders', idField: 'id', sort: 'id,desc', crudMethod: { ...PurchaseOrders }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'purchaseOrders:add'],
        edit: ['admin', 'purchaseOrders:edit'],
        del: ['admin', 'purchaseOrders:del'],
        approve: ['admin', 'purchaseOrders:approve'],
        reApprove: ['admin', 'purchaseOrders:reApprove'],
        print: ['admin', 'purchaseOrders:print']
      },
      rules: {
        managerId: [
          { required: true, message: '必须选择经办人', trigger: 'blur' }
        ],
        date: [
          { required: true, message: '入库日期不能为空', trigger: 'blur' }
        ],
        whId: [
          { required: true, message: '必须选择存入仓库', trigger: 'blur' }
        ],
        sourceId: [
          { required: true, message: '必须选择供应商', trigger: 'blur' }
        ],
        originOrderNo: [
          { required: true, message: '原始单号不能为空', trigger: 'blur' }
        ],
        remark: [
          { message: '长度不能超过100个字符', trigger: 'blur', max: 100, type: 'string' }
        ]
      },
      whSelect: [],
      supplierSelect: [],
      managerSelect: [],
      dialogWhSelect: [],
      dialogSupplierSelect: [],
      innerVisible: false,
      approveAllLoading: false,
      reApproveAllLoading: false,
      showDisable: false,
      goodListLoading: false,
      printVisible: false,
      printOrderId: null
    }
  },
  computed: {
    ...mapGetters([
      'user'
    ])
  },
  created() {
    this.getWareHouseSelect()
    this.getSupplierSelect()
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    },
    // 新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      this.getManagerSelect()
      this.getDialogWhSelect()
      this.getDialogSupplierSelect()
      if (form.id === null) {
        this.getOrderNo()
        form.orderDate = new Date()
        form.date = new Date()
        this.form.orderPersonId = this.user.id
        this.form.orderPerson = this.user.nickName
      } else {
        this.getOrderGoodList(form.id)
      }
    },
    // 提交前的验证
    [CRUD.HOOK.afterValidateCU]() {
      if (this.form.goodList.length <= 0) {
        this.$message({
          message: '请添加货品!',
          type: 'warning'
        })
        return false
      }
      return true
    },
    closeDialog() {
      this.showDisable = false
    },
    getWareHouseSelect() {
      crudTbWhInventory.getWareHouseSelect().then(res => {
        this.whSelect = res
      })
    },
    getDialogWhSelect() {
      crudTbWhInventory.getWareHouseSelect().then(res => {
        this.dialogWhSelect = res
      })
    },
    getSupplierSelect() {
      PurchaseOrders.getSupplierSelect().then(res => {
        this.supplierSelect = res
      })
    },
    getDialogSupplierSelect() {
      PurchaseOrders.getSupplierSelect().then(res => {
        this.dialogSupplierSelect = res
      })
    },
    getManagerSelect() {
      PurchaseOrders.getManagerSelect().then(res => {
        this.managerSelect = res
      })
    },
    getOrderNo() {
      PurchaseOrders.getOrderNo().then(res => {
        this.form.orderNo = res
      })
    },
    changeSupplier(val) {
      const obj = this.dialogSupplierSelect.find((item) => {
        return item.id === val
      })
      this.form.sourceName = obj.cName
    },
    changeWarehouse(val) {
      const obj = this.dialogWhSelect.find((item) => {
        return item.whId === val
      })
      this.form.whName = obj.whName
    },
    changeManager(val) {
      const obj = this.managerSelect.find((item) => {
        return item.id === val
      })
      this.form.manager = obj.name
    },
    pickGood() {
      if (this.form.whId == null || this.form.whId === '') {
        this.$notify({
          title: '请先选择仓库!',
          type: 'warning',
          duration: 2500
        })
      } else {
        this.innerVisible = true
      }
    },
    cancelPickUp() {
      this.innerVisible = false
    },
    pickup(val) {
      var flag = true
      for (const [i, v] of this.form.goodList.entries()) {
        if (v.gCode === val.gCode) {
          this.form.goodList[i].goodNum += 1
          this.form.goodList[i].totalPrice = this.form.goodList[i].goodNum * this.form.goodList[i].purchasePrice
          flag = false
          break
        }
      }
      if (flag) {
        val.totalPrice = val.goodNum * val.purchasePrice
        this.form.goodList.push(val)
      }
      this.$nextTick(() => {
        this.calculate()
      })
    },
    removePick(index) {
      this.form.goodList.splice(index, 1)
      this.$nextTick(() => {
        this.calculate()
      })
    },
    changeNumOrPrice(val, index) {
      this.form.goodList[index].totalPrice = val.goodNum * val.purchasePrice
      this.$nextTick(() => {
        this.calculate()
      })
    },
    calculate() {
      let count = 0
      let price = 0
      for (const [i, v] of this.form.goodList.entries()) {
        count += v.goodNum
        price += v.totalPrice
      }
      this.form.amountCount = count
      this.form.amountPrice = price
      this.form.upperCasePrice = changeMoneyToChinese(price)
    },
    getOrderGoodList(id) {
      this.goodListLoading = true
      PurchaseOrders.getOrderGoodList(id).then(res => {
        this.form.goodList = res
        this.goodListLoading = false
      }).catch(() => {
        this.goodListLoading = false
      })
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
        return PurchaseOrders.approveOrders(ids).then(() => {
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
    toReApprove(datas) {
      let confirm = ''
      if (datas instanceof Array) {
        confirm = `确认反审选中的${datas.length}条数据?`
      } else {
        confirm = `确认反审该条数据?`
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
          this.reApproveAllLoading = true
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
        return PurchaseOrders.reApproveOrders(ids).then(() => {
          if (processAll) {
            this.reApproveAllLoading = false
          } else dataStatus.approve = 0
          this.successNotify('反审')
          this.crud.refresh()
        }).catch(() => {
          if (processAll) {
            this.reApproveAllLoading = false
          } else dataStatus.approve = 0
        })
      }).catch(() => {
        this.reApproveAllLoading = false
      })
    },
    successNotify(title) {
      this.$notify({
        title: '批量' + title + '成功!',
        type: 'warning',
        duration: 2500
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
    }
  },
  filters: {
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
