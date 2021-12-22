<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input
          v-model="query.whName"
          clearable
          size="small"
          placeholder="输入仓库名搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <treeselect
          v-model="query.deptId"
          :options="depts"
          :load-options="loadDepts"
          class="dept_tree_select"
          placeholder="所属部门"
        />
        <el-autocomplete
          v-model="query.keeper"
          :fetch-suggestions="QuerySearchAsync"
          :trigger-on-focus="false"
          placeholder="输入库管名称搜索"
          clearable
          class="filter-item"
          @clear="crud.toQuery()"
        />
        <el-autocomplete
          v-model="query.director"
          :fetch-suggestions="QuerySearchAsync"
          :trigger-on-focus="false"
          placeholder="输入负责人名称搜索"
          clearable
          class="filter-item"
          @clear="crud.toQuery()"
        />
        <rrOperation />
      </div>
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="仓库名称" prop="whName">
            <el-input v-model="form.whName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="所属部门">
            <treeselect
              v-model="form.deptId"
              :options="depts"
              :load-options="loadDepts"
              style="width: 370px;"
              class="dept_tree_select_dialog"
              placeholder="所属部门"
            />
          </el-form-item>
          <el-form-item label="库管">
            <el-select v-model="form.keeperId" placeholder="请选择库管" style="width: 370px;">
              <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="负责人">
            <el-select v-model="form.directorId" placeholder="请选择负责人" style="width: 370px;">
              <el-option
                v-for="item in options"
                :key="item.id"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="联系电话" prop="phone">
            <el-input v-model="form.phone" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="地址">
            <el-input v-model="form.address" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="存货分类">
            <el-input v-model="form.goodsType" style="width: 370px;" />
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
        <el-table-column prop="whName" label="仓库名称" />
        <el-table-column prop="deptName" label="所属部门" />
        <el-table-column prop="keeper" label="库管" />
        <el-table-column prop="director" label="负责人" />
        <el-table-column prop="phone" label="联系电话" />
        <el-table-column prop="address" label="地址" />
        <el-table-column prop="goodsType" label="存货分类" />
        <el-table-column prop="remark" label="备注" />
        <el-table-column v-if="checkPer(['admin','tbWarehouse:edit','tbWarehouse:del'])" label="操作" width="150px" align="center">
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
import crudTbWarehouse from '@/api/tbWarehouse'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { LOAD_CHILDREN_OPTIONS } from '@riophae/vue-treeselect'
import { getDepts, getDeptSuperior } from '@/api/system/dept'

const defaultForm = { whId: null, whName: null, deptId: null, deptName: null, keeper: null, director: null, keeperId: null, directorId: null, phone: null, address: null, goodsType: null, remark: null }
export default {
  name: 'TbWarehouse',
  components: { pagination, crudOperation, rrOperation, udOperation, Treeselect },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '仓库', url: 'api/tbWarehouse', idField: 'whId', sort: 'whId,desc', crudMethod: { ...crudTbWarehouse }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'tbWarehouse:add'],
        edit: ['admin', 'tbWarehouse:edit'],
        del: ['admin', 'tbWarehouse:del']
      },
      rules: {
        whName: [
          { required: true, message: '仓库名称不能为空', trigger: 'blur' }
        ],
        phone: [
          { message: '手机号格式不正确', trigger: 'blur', pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/ }
        ]
      },
      depts: [],
      options: []
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      this.getDepts()
      return true
    },
    getDepts() {
      getDepts({ enabled: true }).then(res => {
        this.depts = res.content.map(function(obj) {
          if (obj.hasChildren) {
            obj.children = null
          }
          return obj
        })
      })
    },
    getSupDepts(deptId) {
      getDeptSuperior(deptId).then(res => {
        const date = res.content
        this.buildDepts(date)
        this.depts = date
      })
    },
    buildDepts(depts) {
      depts.forEach(data => {
        if (data.children) {
          this.buildDepts(data.children)
        }
        if (data.hasChildren && !data.children) {
          data.children = null
        }
      })
    },
    // 获取弹窗内部门数据
    loadDepts({ action, parentNode, callback }) {
      if (action === LOAD_CHILDREN_OPTIONS) {
        getDepts({ enabled: true, pid: parentNode.id }).then(res => {
          parentNode.children = res.content.map(function(obj) {
            if (obj.hasChildren) {
              obj.children = null
            }
            return obj
          })
          setTimeout(() => {
            callback()
          }, 200)
        })
      }
    },
    QuerySearchAsync(queryString, cb) {
      crudTbWarehouse.getQueryParamAdviceList(queryString).then(res => {
        var result = []
        result = res.map((terminal) => {
          return {
            value: terminal.name,
            name: terminal.id
          }
        })
        cb(result)
      })
    },
    // 新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      this.getEmpSelect()
      if (form.whId == null) {
        this.getDepts()
      } else {
        this.getSupDepts(form.deptId)
      }
    },
    getEmpSelect() {
      crudTbWarehouse.getEmpSelect().then(res => {
        this.options = res
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

  .dept_tree_select_dialog {
    height: 30px;
    line-height: 30px;
    font-size: 13px;
    .vue-treeselect__placeholder, .vue-treeselect__single-value, .vue-treeselect__control {
      height: 30px !important;
      line-height: 30px !important;
    }
  }

</style>
