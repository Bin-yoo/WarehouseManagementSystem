<template>
  <el-dialog
    width="1000px"
    title="选择订单"
    :visible.sync="visible"
    :close-on-click-modal="false"
    @open="toQuery()"
    @close="cancel()"
    append-to-body>
    <el-row>
      <el-alert title="点击表格行数据,进而查看挑选单据货品明细。" type="success" show-icon />
    </el-row>
    <el-row style="margin-top: 8px;">
      <el-col :span="8">
        <date-range-picker
          v-model="orderDate"
          start-placeholder="制单日期开始时间"
          end-placeholder="制单日期结束时间"
          class="filter-item"
          style="width: 100%"
        />
      </el-col>
      <el-col :span="5" style="margin-left: 5px;">
        <el-input v-model="orderNo" clearable placeholder="请输入单号" class="filter-item"></el-input>
      </el-col>
      <el-col :span="5" style="margin-left: 5px;">
        <el-input v-model="originOrderNo" clearable placeholder="请输入原始单号" class="filter-item"></el-input>
      </el-col>
      <el-col :span="5" style="margin-left: 15px;">
        <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
        <el-button class="filter-item" size="mini" type="warning" icon="el-icon-refresh-left" @click="resetQuery">重置</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-table v-loading="tableLoading" :data="orderData" size="small" border style="width: 100%; margin-top: 8px;" @row-click="rowClick">
        <el-table-column prop="orderType" label="单据类型" width="85px">
          <span>采购入库单</span>
        </el-table-column>
        <el-table-column prop="orderNo" label="单号" width="150" />
        <el-table-column prop="orderDate" label="制单日期" width="90px">
          <template slot-scope="scope">
            {{ scope.row.orderDate | formatDate("") }}
          </template>
        </el-table-column>
        <el-table-column prop="orderPerson" label="制单人" />
        <el-table-column prop="manager" label="经办人" />
        <el-table-column prop="date" label="出入库日期" width="90px">
          <template slot-scope="scope">
            {{ scope.row.date | formatDate("") }}
          </template>
        </el-table-column>
        <el-table-column prop="whName" label="仓库" />
        <el-table-column prop="sourceName" label="供应商" width="120px" />
        <el-table-column prop="originOrderNo" label="原始单号" width="120px" />
        <el-table-column prop="remark" label="备注" width="200">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.remark" placement="top-start">
              <div>{{ scope.row.remark ? scope.row.remark.length > 12 ? scope.row.remark.substr(0,12)+'...' : scope.row.remark : '' }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <!-- <el-table-column label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <el-button type="button" @click="rowClick(scope.row)">选择</el-button>
          </template>
        </el-table-column> -->
      </el-table>
    </el-row>
    <el-row style="border: 1px solid #dfe6ec;">
      <el-pagination
        :page-size.sync="size"
        :total="total"
        :current-page.sync="page"
        style="margin-top: 8px;"
        layout="total, prev, pager, next, sizes"
        @size-change="toQuery()"
        @current-change="toQuery()"
      />
    </el-row>
    <el-row>
      <el-table v-loading="goodListLoading" :data="goodList" size="small" border style="width: 100%; margin-top: 8px;" max-height="400" @selection-change="selectionLineChangeHandle">
        <el-table-column type="selection" width="55" />
        <el-table-column type="index" label="#" width="40px" />
        <el-table-column prop="gCode" label="货品编码" />
        <el-table-column prop="gName" label="货品名称" />
        <el-table-column prop="unitName" label="单位" width="70px" />
        <el-table-column prop="specification" label="规格" width="70px" />
        <el-table-column prop="purchasePrice" label="单价" />
        <el-table-column prop="goodNum" label="数量" />
        <el-table-column prop="totalPrice" label="金额" />
      </el-table>
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button type="primary" @click="commitGoodList">确定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import PurchaseRefundOrders from '@/api/purchaseRefundOrders.js'
import DateRangePicker from '@/components/DateRangePicker/index'
import { getFormatDate } from '@/utils/common.js'

export default {
  name: 'PurchaseOrderSelectBoard',
  components: {
    DateRangePicker
  },
  props: {
    innerVisible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      visible: false,
      loading: false,
      tableLoading: false,
      orderDate: null,
      orderNo: null,
      originOrderNo: null,
      orderData: [],
      page: 1,
      size: 10,
      total: 0,
      goodListLoading: false,
      goodList: [],
      orderInfo:{
        whId: null,
        whName: null,
        sourceId: null,
        sourceName: null,
        selectGoodList: []
      }
    }
  },
  methods: {
    cancel() {
      this.$emit('cancel')
      this.orderData = []
      this.goodList = []
      this.orderInfo = {
        whId: null,
        whName: null,
        sourceId: null,
        sourceName: null,
        selectGoodList: []
      }
      this.resetQuery()
    },
    pick(val, index) {
      this.$emit('pickup', val)
      this.orderData.splice(index, 1)
    },
    toQuery() {
      this.tableLoading = true
      const params = {
          orderDate: this.orderDate,
          orderNo: this.orderNo,
          originOrderNo: this.originOrderNo,
          page: this.page - 1,
          size: this.size
      }
      PurchaseRefundOrders.getOrderChooseList(params).then(res => {
        this.orderData = res.content
        this.total = res.totalElements
        this.tableLoading = false
      })
    },
    resetQuery() {
      this.orderDate = null,
      this.orderNo = null,
      this.originOrderNo = null,
      this.page = 1
      this.size = 10
    },
    rowClick(row, column, event) {
      this.getOrderGoodList(row.id)
      this.orderInfo.whId = row.whId
      this.orderInfo.sourceId = row.sourceId
      this.orderInfo.whName = row.whName
      this.orderInfo.sourceName = row.sourceName
      this.$message('您选中了单号为' + row.orderNo + '的单据');
    },
    selectionLineChangeHandle(val) {
      this.orderInfo.selectGoodList = val
    },
    getOrderGoodList(id) {
      this.goodListLoading = true
      const params = { id: id }
      PurchaseRefundOrders.getOrderGoodList(params).then(res => {
        this.goodList = res
        this.goodListLoading = false
      }).catch(() => {
        this.goodListLoading = false
      })
    },
    commitGoodList() {
      if (this.orderInfo.selectGoodList.length == 0 || this.orderInfo.selectGoodList == []) {
        this.$notify({
          title: '必须选择一条单据明细货品!',
          type: 'warning',
          duration: 2500
        })
      } else {
        this.$emit('selectGood', this.orderInfo)
        this.cancel()
      }
    }
  },
  watch: {
    innerVisible: function(val) {
      this.visible = val
    }
  },
  filters: {
    formatDate(val) {
      return getFormatDate(val)
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  
</style>
