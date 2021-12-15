<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input
          v-model="query.name"
          clearable
          size="small"
          placeholder="输入姓名搜索"
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
        <rrOperation />
      </div>
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="750px">
        <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="姓名" prop="name">
            <el-input v-model="form.name" style="width: 250px;" @input="convertPinyin" />
          </el-form-item>
          <el-form-item label="拼音码">
            <el-input v-model="form.pyCode" disabled style="width: 250px;" />
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="form.gender" style="width: 250px">
              <el-radio label="男">男</el-radio>
              <el-radio label="女">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="籍贯">
            <el-input v-model="form.jg" style="width: 250px;" />
          </el-form-item>
          <el-form-item label="所属部门" prop="deptId">
            <treeselect
              v-model="form.deptId"
              :options="depts"
              :load-options="loadDepts"
              style="width: 250px;"
              placeholder="选择所属部门"
            />
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input v-model="form.phone" style="width: 250px;" />
          </el-form-item>
          <el-form-item label="手机号码">
            <el-input v-model="form.mobile" style="width: 250px;" />
          </el-form-item>
          <el-form-item label="家庭住址">
            <el-input v-model="form.address" style="width: 250px;" />
          </el-form-item>
          <el-form-item label="岗位">
            <el-select v-model="form.jobId" placeholder="请选择岗位" style="width: 250px;" >
              <el-option
                v-for="item in jobs"
                :key="item.id"
                :label="item.name"
                :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="职称">
            <el-input v-model="form.title" style="width: 250px;" />
          </el-form-item>
          <el-form-item label="生日">
            <el-date-picker
              v-model="form.birthday"
              style="width: 250px;"
              type="date"
              placeholder="选择日期"
            >
            </el-date-picker>
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
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="pyCode" label="拼音码" />
        <el-table-column prop="gender" label="性别" />
        <el-table-column prop="deptName" label="所属部门" />
        <el-table-column prop="jg" label="籍贯" />
        <el-table-column prop="phone" label="联系电话" />
        <el-table-column prop="mobile" label="手机号码" />
        <el-table-column prop="address" label="家庭住址" />
        <el-table-column prop="jobName" label="岗位" />
        <el-table-column prop="title" label="职称" />
        <el-table-column prop="birthday" label="生日">
          <template slot-scope="scope">
            {{ scope.row.birthday | formatDate("") }}
          </template>
        </el-table-column>
        <el-table-column v-if="checkPer(['admin','tbEmployee:edit','tbEmployee:del'])" label="操作" width="150px" align="center">
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
import crudTbEmployee from '@/api/tbEmployee'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import { LOAD_CHILDREN_OPTIONS } from '@riophae/vue-treeselect'
import { getDepts, getDeptSuperior } from '@/api/system/dept'
import pinyin from 'js-pinyin'
import { getFormatDate } from '@/utils/common.js'

const defaultForm = { id: null, name: null, gender: null, pyCode: null, deptId: null, jg: null, phone: null, mobile: null, address: null, jobId: null, title: null, birthday: null }
export default {
  name: 'TbEmployee',
  components: { pagination, crudOperation, rrOperation, udOperation, Treeselect },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '员工', url: 'api/tbEmployee', idField: 'id', sort: 'id,desc', crudMethod: { ...crudTbEmployee }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'tbEmployee:add'],
        edit: ['admin', 'tbEmployee:edit'],
        del: ['admin', 'tbEmployee:del']
      },
      rules: {
        name: [
          { required: true, message: '姓名不能为空', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '所属部门不能为空', trigger: 'blur' }
        ]
      },
      depts: [],
      jobs: []
    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      this.getDepts()
      return true
    },
    // 新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      this.jobs = []
      this.getJobsSelect()
      if (form.id == null) {
        this.getDepts()
      } else {
        this.getSupDepts(form.deptId)
      }
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
    convertPinyin(value) {
      this.form.pyCode = pinyin.getCamelChars(value)
    },
    getJobsSelect() {
      crudTbEmployee.getJobsSelect().then(res => {
        this.jobs = res
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

<style rel="stylesheet/scss" lang="scss" scoped>
  .dept_tree_select {
    width: 200px;
    line-height: 28px;
    font-size: 13px;
    display: inline-block;
    vertical-align: middle;
    margin: 0 3px 10px 0;
    .vue-treeselect__placeholder {
      height: 28px !important;
      line-height: 28px !important;
    }
    .vue-treeselect__control {
      height: 28px !important;
      line-height: 28px !important;
    }
  }
  ::v-deep .el-input-number .el-input__inner {
    text-align: left;
  }
  ::v-deep .vue-treeselect__control,::v-deep .vue-treeselect__placeholder,::v-deep .vue-treeselect__single-value {
    height: 30px;
    line-height: 30px;
  }
</style>
