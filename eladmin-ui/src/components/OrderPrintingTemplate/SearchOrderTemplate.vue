<template>
  <el-dialog
    width="1000px"
    title="查看打印单据"
    :visible.sync="visible"
    :close-on-click-modal="false"
    append-to-body
    @open="getOrderPrintingInfo()"
    @close="cancel()"
  >
    <div style="padding: 0 30px; color: #000000; width: 100%">
      <el-button type="primary" icon="el-icon-printer" v-print="'#printMe'">打印单据</el-button>
    </div>
    <div id="printMe" style="padding: 0 30px; color: #000000; width: 100%" v-loading="loading">
      <el-row>
        <div style="width: 100%; height: 30px; text-indent: 0px; vertical-align: middle; text-align: center; font-size: 18px; line-height: 30px;">
          {{ formatOrderType(templateData.orderType) }}
        </div>
      </el-row>
      <el-row style="margin: 20px 0 0 0; font-size: 12px;">
        <el-col :span="9">
          <span>单&nbsp;&nbsp;&nbsp;&nbsp;号:&nbsp;&nbsp;{{ templateData.orderNo }}</span>
        </el-col>
        <el-col :span="9">
          <span v-if="templateData.inOutType === 1">入库日期:&nbsp;&nbsp;</span>
          <span v-else-if="templateData.inOutType === 2">出库日期:&nbsp;&nbsp;</span>
          <span v-else>盘点日期:&nbsp;&nbsp;</span>
          <span>{{ templateData.date }}</span>
        </el-col>
        <el-col :span="6">
          <span>打印日期:&nbsp;&nbsp;{{ templateData.printDate }}</span>
        </el-col>
      </el-row>
      <el-row style="margin: 20px 0 0 0; font-size: 12px;">
        <el-col :span="9">
          <span v-if="templateData.inOutType === 1">入库仓库&nbsp;&nbsp;:</span>
          <span v-else-if="templateData.inOutType === 2 && templateData.orderType !== 6">出库仓库&nbsp;&nbsp;:</span>
          <span v-else-if="templateData.orderType === 6">调入仓库&nbsp;&nbsp;:</span>
          <span v-else>盘点仓库&nbsp;&nbsp;:</span>
          <span>{{ templateData.whName }}</span>
        </el-col>
        <el-col :span="9">
          <span v-if="templateData.orderType === 1 || templateData.orderType === 4">供&nbsp;&nbsp;应&nbsp;&nbsp;商&nbsp;&nbsp;:</span>
          <span v-else-if="templateData.orderType === 2">生产部门&nbsp;&nbsp;:</span>
          <span v-else-if="templateData.orderType === 3 || templateData.orderType === 5">客&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;:</span>
          <span v-else-if="templateData.orderType === 6">调出仓库&nbsp;&nbsp;:</span>
          <span>{{ templateData.sourceName }}</span>
        </el-col>
        <el-col :span="6">
          <span>制&nbsp;单&nbsp;人:&nbsp;&nbsp;{{ templateData.orderPerson }}</span>
        </el-col>
      </el-row>
      <el-row style="margin: 20px 0 0 0; font-size: 12px;">
        <el-col :span="9">
          <span>经&nbsp;办&nbsp;人:&nbsp;&nbsp;{{ templateData.manager }}</span>
        </el-col>
        <el-col :span="9">
          <span>原始单号:&nbsp;&nbsp;{{ templateData.originOrderNo }}</span>
        </el-col>
        <el-col :span="6">
          <span>备&nbsp;&nbsp;&nbsp;&nbsp;注:&nbsp;&nbsp;{{ templateData.remark }}</span>
        </el-col>
      </el-row>
      <el-row>
        <hr style="margin: 10px 0;" />
      </el-row>
      <el-row>
        <el-table
          :data="templateData.goodList"
          border
          style="width: 100%; color: #000000; border-color: #000000; border-right: 1px solid #000000; border-bottom: 1px solid #000000;"
          :header-cell-class-name="tableHeaderCell"
          :cell-class-name="tableCell"
        >
          <el-table-column type="index" label="#" width="40px" />
          <el-table-column prop="gCode" label="货品编码" />
          <el-table-column prop="gName" label="货品名称" />
          <el-table-column prop="unitName" label="单位" width="70px" />
          <el-table-column prop="specification" label="规格" width="70px" />
          <el-table-column prop="purchasePrice" label="单价" />
          <template v-if="templateData.orderType === 7">
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
      </el-row>
      <el-row>
        <hr style="margin: 10px 0;" />
      </el-row>
      <el-row style="font-size: 12px;">
        <el-col :span="9">
          <span>大写金额:&nbsp;&nbsp;{{ templateData.upperPrice }}</span>
        </el-col>
        <el-col :span="9">
          <span>合计盈亏数量:&nbsp;&nbsp;{{ templateData.amountCount }}</span>
        </el-col>
        <el-col :span="6">
          <span>合计盈亏金额:&nbsp;&nbsp;{{ templateData.amountPrice }}</span>
        </el-col>
      </el-row>
      <el-row style="margin: 20px 0 0 0; font-size: 12px;">
        <el-col :span="6">
          <span>签收人:</span><span>__________________</span>
        </el-col>
        <el-col :span="6">
          <span>签收人:</span><span>__________________</span>
        </el-col>
      </el-row>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="cancel">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import SearchOrders from '@/api/searchOrders.js'

export default {
  name: 'SearchOrderTemplate',
  props: {
    innerVisible: {
      type: Boolean,
      default: false
    },
    printOrderId: {
      type: String,
      default: ''
    }
  },
  dicts: ['order_type_for_searching'],
  data() {
    return {
      orderId: null,
      visible: false,
      templateData: {
        orderNo: '',
        inDate: '',
        printDate: '',
        whName: '',
        supplier: '',
        orderPerson: '',
        manager: '',
        originOrderNo: '',
        remark: '',
        upperPrice: '',
        amountCount: '',
        amountPrice: ''
      },
      loading: false
    }
  },
  methods: {
    cancel() {
      this.$emit('cancel')
      this.orderId = null
      this.templateData = {
        orderNo: '',
        inDate: '',
        printDate: '',
        whName: '',
        supplier: '',
        orderPerson: '',
        manager: '',
        originOrderNo: '',
        remark: '',
        upperPrice: '',
        amountCount: '',
        amountPrice: ''
      }
    },
    getOrderPrintingInfo() {
      this.loading = true
      SearchOrders.getOrderPrintingInfo(this.orderId).then(res => {
        this.templateData = res
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    tableHeaderCell({row,rowIndex}) {
      return 'table_header_cell'
    },
    tableCell({row,rowIndex}) {
      return 'table_cell'
    },
    formatOrderType(val) {
      let label = ''
      for (const [i, v] of this.dict.order_type_for_searching.entries()) {
        if (v.value == val) {
          label = v.label
          break
        }
      }
      return label + '单'
    }
  },
  watch: {
    innerVisible: function(val) {
      this.visible = val
    },
    printOrderId: function(val) {
      this.orderId = val
    }
  }
}

</script>

<style>
.table_header_cell {
  border-color: #000000 !important;
  color: #000000;
}
.table_cell {
  border-color: #000000 !important;
  color: #000000;
}
</style>
