<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="货品id" prop="id">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="姓名" prop="name">
            <el-input v-model="form.name" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="性别">
            <el-input v-model="form.gender" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="拼音码">
            <el-input v-model="form.pyCode" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="所属部门" prop="deptId">
            <el-input v-model="form.deptId" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="籍贯">
            <el-input v-model="form.jg" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="联系电话">
            <el-input v-model="form.phone" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="手机号码">
            <el-input v-model="form.mobile" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="家庭住址">
            <el-input v-model="form.address" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="职位">
            <el-input v-model="form.position" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="职称">
            <el-input v-model="form.title" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="生日">
            <el-input v-model="form.birthday" style="width: 370px;" />
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
        <el-table-column prop="id" label="货品id" />
        <el-table-column prop="name" label="姓名" />
        <el-table-column prop="gender" label="性别" />
        <el-table-column prop="pyCode" label="拼音码" />
        <el-table-column prop="deptId" label="所属部门" />
        <el-table-column prop="jg" label="籍贯" />
        <el-table-column prop="phone" label="联系电话" />
        <el-table-column prop="mobile" label="手机号码" />
        <el-table-column prop="address" label="家庭住址" />
        <el-table-column prop="position" label="职位" />
        <el-table-column prop="title" label="职称" />
        <el-table-column prop="birthday" label="生日" />
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

const defaultForm = { id: null, name: null, gender: null, pyCode: null, deptId: null, jg: null, phone: null, mobile: null, address: null, position: null, title: null, birthday: null }
export default {
  name: 'TbEmployee',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'tb_employee', url: 'api/tbEmployee', idField: 'id', sort: 'id,desc', crudMethod: { ...crudTbEmployee }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'tbEmployee:add'],
        edit: ['admin', 'tbEmployee:edit'],
        del: ['admin', 'tbEmployee:del']
      },
      rules: {
        id: [
          { required: true, message: '货品id不能为空', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '姓名不能为空', trigger: 'blur' }
        ],
        deptId: [
          { required: true, message: '所属部门不能为空', trigger: 'blur' }
        ]
      }}
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
