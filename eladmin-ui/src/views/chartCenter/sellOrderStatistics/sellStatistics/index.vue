<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div>
        <!-- 搜索 -->
        <el-input
          v-model="query.gName"
          clearable
          size="small"
          placeholder="输入货品名称搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <el-input
          v-model="query.gCode"
          clearable
          size="small"
          placeholder="输入货品编码搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <el-input
          v-model="query.pyCode"
          clearable
          size="small"
          placeholder="输入货品拼音码搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <!-- <crudOperation :permission="permission" /> -->
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :visible.sync="dialogShow" :title="crud.status.title" width="1150px" @closed="dialogClose">
        <el-row>
          <el-table v-loading="detailDataLoading" :data="detailData" size="small" style="width: 100%;">
            <el-table-column prop="orderType" label="单据类型">
              <template slot-scope="scope">
                <span v-if="scope.row.orderType === 3">销售退货单</span>
                <span v-if="scope.row.orderType === 5">销售提货单</span>
              </template>
            </el-table-column>
            <el-table-column prop="orderNo" label="单号" width="150" />
            <el-table-column prop="sourceName" label="客户" />
            <el-table-column prop="whName" label="出入库仓库" />
            <el-table-column prop="date" label="出入库日期" width="85" >
              <template slot-scope="scope">
                {{ scope.row.date | formatDate("") }}
              </template>
            </el-table-column>
            <el-table-column prop="gCode" label="货品编码" />
            <el-table-column prop="gName" label="货品名称" />
            <el-table-column prop="pyCode" label="拼音码" />
            <el-table-column prop="specification" label="规格" />
            <el-table-column prop="unitName" label="单位" />
            <el-table-column prop="price" label="单价">
              <template slot-scope="scope">
                <span>¥&nbsp;{{ scope.row.price }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="count" label="数量" />
            <el-table-column prop="totalPrice" label="金额">
              <template slot-scope="scope">
                <span>¥&nbsp;{{ scope.row.totalPrice }}</span>
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
            @size-change="showDetail()"
            @current-change="showDetail()"
          />
        </el-row>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="dialogShow = false">取消</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="gCode" label="货品编码" />
        <el-table-column prop="gName" label="货品名称" />
        <el-table-column prop="pyCode" label="拼音码" />
        <el-table-column prop="typeName" label="货品类别" />
        <el-table-column prop="specification" label="规格" />
        <el-table-column prop="unitName" label="计量单位" />
        <el-table-column prop="sellCount" label="销售数量" />
        <el-table-column prop="sellAmount" label="销售金额">
          <template slot-scope="scope">
            <span>¥&nbsp;{{ scope.row.sellAmount }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="refundCount" label="退货数量" />
        <el-table-column prop="refundAmount" label="退货金额">
          <template slot-scope="scope">
            <span>¥&nbsp;{{ scope.row.refundAmount }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="totalCount" label="总数量" />
        <el-table-column prop="totalAmount" label="总金额">
          <template slot-scope="scope">
            <span>¥&nbsp;{{ scope.row.totalAmount }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="checkPer(['admin','supplierOfferStatistics:list'])" fixed="right" label="操作" width="295px" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="primary" icon="el-icon-reading" @click="showDetail(scope.row.goodId)" style="margin-left: 0px;" />
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import SellStatistics from '@/api/sellStatistics.js'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import { getFormatDate } from '@/utils/common.js'

const defaultForm = { id: null, orderType: null, orderNo: null, orderDate: null, orderPersonId: null, orderPerson: null, managerId: null, manager: null, date: null, whId: null, whName: null, sourceId: null, sourceName: null, originOrderNo: null, upperCasePrice: '零元整', amountCount: 0, amountPrice: 0, status: null, verifyDate: null, verifyPersonId: null, verifyPerson: null, delFlag: null, updateTime: null, updateBy: null, remark: null, goodList: [] }
export default {
  name: 'SellStatistics',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '查看单据详情', url: 'api/sellStatistics', idField: 'id', sort: 'id,desc', crudMethod: { ...SellStatistics }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'sellStatistics:add'],
        edit: ['admin', 'sellStatistics:edit'],
        del: ['admin', 'sellStatistics:del']
      },
      dialogShow: false,
      detailData: [],
      detailDataLoading: false,
      page: 1,
      size: 10,
      total: 0,
      goodId: null
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    },
    // 新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
    },
    // 提交前的验证
    [CRUD.HOOK.afterValidateCU]() {
      return true
    },
    dialogClose() {
      this.goodId = null
      this.size = 10
      this.total = 0
      this.detailData = []
    },
    showDetail(goodId) {
      this.dialogShow = true
      if (goodId) {
        this.goodId = goodId
      }
      this.detailDataLoading = true
      const params = {
        goodId: this.goodId,
        page: this.page - 1,
        size: this.size
      }
      SellStatistics.getDetail(params).then(res => {
        this.detailData = res.content
        this.total = res.totalElements
        this.detailDataLoading = false
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
