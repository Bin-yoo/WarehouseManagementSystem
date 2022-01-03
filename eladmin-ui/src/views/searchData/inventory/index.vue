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
          placeholder="货品分类"
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
      <el-dialog :close-on-click-modal="false" :before-close="cancel" :visible.sync="dialogShow" :title="title" width="500px">
        <el-table :data="detailData" size="small" style="width: 100%;">
          <el-table-column prop="gCode" label="货品编码" />
          <el-table-column prop="gName" label="货品名称" />
          <el-table-column prop="pyCode" label="拼音码" />
          <el-table-column prop="typeName" label="货品类别" />
          <el-table-column prop="specification" label="规格" />
          <el-table-column prop="model" label="型号" />
          <el-table-column prop="weight" label="重量" />
          <el-table-column prop="color" label="颜色" />
          <el-table-column prop="unitName" label="计量单位" />
          <el-table-column prop="purchasePrice" label="进价" />
          <el-table-column prop="sellPrice" label="售价" />
          <el-table-column prop="allocation" label="货位(m²)" />
          <el-table-column prop="manufacturer" label="生产厂家" />
          <el-table-column prop="barCode" label="条形码" />
          <el-table-column prop="qrCode" label="二维码" />
          <el-table-column prop="remark" label="备注" width="200">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.remark" placement="top-start">
              <div>{{ scope.row.remark ? scope.row.remark.length > 12 ? scope.row.remark.substr(0,12)+'...' : scope.row.remark : '' }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        </el-table>
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
        <el-table-column prop="typeName" label="货品类别" />
        <el-table-column prop="specification" label="规格" />
        <el-table-column prop="model" label="型号" />
        <el-table-column prop="weight" label="重量" />
        <el-table-column prop="color" label="颜色" />
        <el-table-column prop="unitName" label="计量单位" />
        <el-table-column prop="purchasePrice" label="进价" />
        <el-table-column prop="sellPrice" label="售价" />
        <el-table-column prop="allocation" label="货位(m²)" />
        <el-table-column prop="manufacturer" label="生产厂家" />
        <el-table-column prop="barCode" label="条形码" />
        <el-table-column prop="qrCode" label="二维码" />
        <el-table-column prop="count" label="库存数量" />
        <el-table-column prop="lower_limit" label="库存下限" />
        <el-table-column prop="upper_limit" label="库存上限" />
        <el-table-column v-if="checkPer(['admin','tbWhInventory:edit','tbWhInventory:del'])" label="操作" width="150px" align="center">
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
  data() {
    return {
      permission: {
        add: ['admin', 'tbWhInventory:add'],
        edit: ['admin', 'tbWhInventory:edit'],
        del: ['admin', 'tbWhInventory:del']
      },
      rules: {
        goodId: [
          { required: true, message: '货品id不能为空', trigger: 'blur' }
        ],
        whId: [
          { required: true, message: '仓库id不能为空', trigger: 'blur' }
        ],
        count: [
          { required: true, message: '货品数量不能为空', trigger: 'blur' }
        ]
      },
      whSelect: [],
      types: [],
      unitSelect: [],
      dialogShow: false,
      detailData: []
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
      crudTbWhInventory.getWhInOutDetail(goodId).then(res => {
        this.detailData = res
      })
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
