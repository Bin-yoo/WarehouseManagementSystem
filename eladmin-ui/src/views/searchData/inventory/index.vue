<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <div>
        <!-- 搜索 -->
        <el-select
          v-model="query.whId"
          placeholder="选择存放仓库"
          style="width: 200px;"
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
          placeholder="输入拼音码搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <treeselect
          v-model="query.typeId"
          :options="types"
          :load-options="loadSreachParamTypesSelectTree"
          class="dept_tree_select"
          placeholder="货品类别"
        />
        <el-select
          v-model="query.unitId"
          placeholder="选择计量单位"
          style="width: 200px;"
          class="filter-item"
          clearable
        >
          <el-option
            v-for="item in unitSelect"
            :key="item.id"
            :label="item.guName"
            :value="item.id"
          />
        </el-select>
        <rrOperation />
      </div>
      <!-- <crudOperation :permission="permission" /> -->
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :visible.sync="dialogShow" title="查看库存明细" width="1200px" @closed="dialogClose">
        <el-row>
          <el-table v-loading="detailDataLoading" :data="detailData" size="small" style="width: 100%;">
            <el-table-column prop="orderNo" label="单号" width="150" />
            <el-table-column prop="orderType" label="单据类型">
              <template slot-scope="scope">
                {{ formatOrderType(scope.row.orderType) }}
              </template>
            </el-table-column>
            <el-table-column prop="gCode" label="货品编码" />
            <el-table-column prop="gName" label="货品名称" />
            <el-table-column prop="pyCode" label="拼音码" />
            <el-table-column prop="specification" label="规格" />
            <el-table-column prop="unitName" label="单位" />
            <el-table-column prop="price" label="单价" />
            <el-table-column prop="count" label="数量">
              <template slot-scope="scope">
                <span v-if="scope.row.inOutType === 2">-</span>{{ scope.row.count }}
              </template>
            </el-table-column>
            <el-table-column prop="totalPrice" label="金额" />
            <el-table-column prop="whName" label="仓库" />
            <el-table-column prop="source" label="来源" />
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
          <el-button type="text" @click="dialogShow = false">关闭</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column prop="whName" label="仓库名称">
          <template slot-scope="scope">
            {{ scope.row.whName ? scope.row.whName : '全部仓库' }}
          </template>
        </el-table-column>
        <el-table-column prop="gCode" label="货品编码" />
        <el-table-column prop="gName" label="货品名称" />
        <el-table-column prop="pyCode" label="拼音码" />
        <el-table-column prop="type" label="货品类别" />
        <el-table-column prop="specification" label="规格" />
        <el-table-column prop="model" label="型号" />
        <el-table-column prop="count" label="库存数量" />
        <el-table-column prop="lowerLimit" label="库存下限" />
        <el-table-column prop="upperLimit" label="库存上限" />
        <el-table-column prop="weight" label="重量" />
        <el-table-column prop="color" label="颜色" />
        <el-table-column prop="unit" label="计量单位" />
        <el-table-column prop="purchasePrice" label="进价" />
        <el-table-column prop="sellPrice" label="售价" />
        <el-table-column prop="allocation" label="货位" />
        <el-table-column prop="manufacturer" label="生产厂家" />
        <!-- <el-table-column prop="barCode" label="条形码" /> -->
        <!-- <el-table-column prop="qrCode" label="二维码" /> -->
        <el-table-column fixed="right" label="操作" width="80px" align="center">
          <template slot-scope="scope">
            <!-- <udOperation
              :data="scope.row"
              :permission="permission"
            /> -->
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
import crudTbWhInventory from '@/api/tbWhInventory'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import crudTbGoodsInfo from '@/api/tbGoodsInfo'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

const defaultForm = { goodId: null, whId: null, count: null }
export default {
  name: 'TbWhInventory',
  components: { pagination, crudOperation, rrOperation, udOperation, Treeselect },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '查看库存详情', url: 'api/tbWhInventory', idField: 'whId', sort: 'whId,desc', crudMethod: { ...crudTbWhInventory }})
  },
  dicts: ['order_type'],
  data() {
    return {
      permission: {
        add: ['admin', 'tbWhInventory:add'],
        edit: ['admin', 'tbWhInventory:edit'],
        del: ['admin', 'tbWhInventory:del']
      },
      whSelect: [],
      types: [],
      unitSelect: [],
      dialogShow: false,
      detailData: [],
      detailDataLoading: false,
      page: 1,
      size: 10,
      total: 0,
      goodId: null
    }
  },
  created() {
    this.getSearchGoodUnitsSelect()
    this.loadSreachParamTypesSelectTree()
    this.getWareHouseSelect()
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    },
    getSearchGoodUnitsSelect() {
      crudTbGoodsInfo.getGoodUnitsSelect().then(res => {
        this.unitSelect = res
      })
    },
    loadSreachParamTypesSelectTree() {
      crudTbGoodsInfo.getTypesSelectTree().then(res => {
        this.types = res
      })
    },
    getWareHouseSelect() {
      crudTbWhInventory.getWareHouseSelect().then(res => {
        this.whSelect = res
      })
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
      crudTbWhInventory.getWhInOutDetail(params).then(res => {
        this.detailData = res.content
        this.total = res.totalElements
        this.detailDataLoading = false
      })
    },
    dialogClose() {
      this.goodId = null
      this.size = 10
      this.detailData = []
    },
    formatOrderType(val) {
      let label = ''
      for (const [i, v] of this.dict.order_type.entries()) {
        if (v.value == val) {
          label = v.label
          break
        }
      }
      return label
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
  .dept_tree_select {
    width: 200px;
    height: 28.5px;
    line-height: 28.5px;
    font-size: 13px;
    display: inline-block;
    vertical-align: middle;
    margin: 0 3px 12px 0;
    .vue-treeselect__placeholder, .vue-treeselect__single-value, .vue-treeselect__control {
      height: 28.5px !important;
      line-height: 28.5px !important;
    }
  }
</style>
