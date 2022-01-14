<template>
  <el-dialog
    width="1000px"
    title="选择货品"
    :visible.sync="visible"
    :close-on-click-modal="false"
    @open="toQuery()"
    @close="cancel()"
    append-to-body>
    <el-row>
      <el-col :span="5">
        <el-input v-model="goodCode" clearable placeholder="请输入货品编号"></el-input>
      </el-col>
      <el-col :span="5">
        <el-input v-model="goodName" clearable placeholder="请输入货品名称" style="margin-left: 10px;"></el-input>
      </el-col>
      <el-col :span="5" :offset="1">
        <el-button class="filter-item" size="mini" type="success" icon="el-icon-search" @click="toQuery">搜索</el-button>
        <el-button class="filter-item" size="mini" type="warning" icon="el-icon-refresh-left" @click="resetQuery">重置</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-table v-loading="tableLoading" :data="tableData" size="small" style="width: 100%;">
        <el-table-column prop="gCode" label="货品编码" width="180px" />
        <el-table-column prop="gName" label="货品名称" />
        <el-table-column prop="unitName" label="计量单位" />
        <el-table-column prop="count" label="库存数量" />
        <el-table-column prop="specification" label="规格" />
        <el-table-column prop="purchasePrice" label="进价" />
        <el-table-column prop="sellPrice" label="售价" />
        <el-table-column prop="manufacturer" label="生产厂家" />
        <el-table-column label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <el-button type="button" @click="pick(scope.row, scope.$index)">选择</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-row>
    <el-row>
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
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="cancel">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import PurchaseOrders from '@/api/purchaseOrders.js'

export default {
  name: 'GoodChooseBoard',
  props: {
    innerVisible: {
      type: Boolean,
      default: false
    },
    whId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      visible: false,
      loading: false,
      tableLoading: false,
      goodName: null,
      goodCode: null,
      tableData: [],
      page: 0,
      size: 10,
      total: 0
    }
  },
  methods: {
    cancel() {
      this.$emit('cancel')
      this.resetQuery()
    },
    pick(val, index) {
      this.$emit('pickup', val)
      this.tableData.splice(index, 1)
    },
    toQuery() {
      this.tableLoading = true
      const params = {
          gName: this.goodName,
          gCode: this.goodCode,
          whId: this.whId,
          page: this.page,
          size: this.size
      }
      PurchaseOrders.getGoodChooseList(params).then(res => {
        this.tableData = res.content
        this.total = res.totalElements
        this.tableLoading = false
      })
    },
    resetQuery() {
      this.goodName = null
      this.goodCode = null
      this.page = 0
      this.size = 10
    }
  },
  watch: {
    innerVisible: function (val) {
      this.visible = val
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  
</style>
