<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="单位id" prop="id">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="货品编码" prop="cCode">
            <el-input v-model="form.cCode" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="货品名称" prop="cName">
            <el-input v-model="form.cName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="拼音码" prop="pyCode">
            <el-input v-model="form.pyCode" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="单位类别" prop="type">
            <el-input v-model="form.type" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="所在省份">
            <el-input v-model="form.province" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="详细地址">
            <el-input v-model="form.address" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="联系人" prop="contactPerson">
            <el-input v-model="form.contactPerson" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="联系电话" prop="contactPhone">
            <el-input v-model="form.contactPhone" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="手机" prop="mobile">
            <el-input v-model="form.mobile" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="传真">
            <el-input v-model="form.fax" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="邮编">
            <el-input v-model="form.zip" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="form.email" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="公司网址">
            <el-input v-model="form.website" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="QQ号码">
            <el-input v-model="form.qq" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="银行名称">
            <el-input v-model="form.bank" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="银行卡号">
            <el-input v-model="form.bankAccount" style="width: 370px;" />
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
        <el-table-column prop="id" label="单位id" />
        <el-table-column prop="cCode" label="货品编码" />
        <el-table-column prop="cName" label="货品名称" />
        <el-table-column prop="pyCode" label="拼音码" />
        <el-table-column prop="type" label="单位类别" />
        <el-table-column prop="province" label="所在省份" />
        <el-table-column prop="address" label="详细地址" />
        <el-table-column prop="contactPerson" label="联系人" />
        <el-table-column prop="contactPhone" label="联系电话" />
        <el-table-column prop="mobile" label="手机" />
        <el-table-column prop="fax" label="传真" />
        <el-table-column prop="zip" label="邮编" />
        <el-table-column prop="email" label="邮箱" />
        <el-table-column prop="website" label="公司网址" />
        <el-table-column prop="qq" label="QQ号码" />
        <el-table-column prop="bank" label="银行名称" />
        <el-table-column prop="bankAccount" label="银行卡号" />
        <el-table-column prop="remark" label="备注" />
        <el-table-column v-if="checkPer(['admin','tbPartnerCompanyInfo:edit','tbPartnerCompanyInfo:del'])" label="操作" width="150px" align="center">
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
import crudTbPartnerCompanyInfo from '@/api/tbPartnerCompanyInfo'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, cCode: null, cName: null, pyCode: null, type: null, province: null, address: null, contactPerson: null, contactPhone: null, mobile: null, fax: null, zip: null, email: null, website: null, qq: null, bank: null, bankAccount: null, remark: null }
export default {
  name: 'TbPartnerCompanyInfo',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: 'tb_partner_company_info', url: 'api/tbPartnerCompanyInfo', idField: 'id', sort: 'id,desc', crudMethod: { ...crudTbPartnerCompanyInfo }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'tbPartnerCompanyInfo:add'],
        edit: ['admin', 'tbPartnerCompanyInfo:edit'],
        del: ['admin', 'tbPartnerCompanyInfo:del']
      },
      rules: {
        id: [
          { required: true, message: '单位id不能为空', trigger: 'blur' }
        ],
        cCode: [
          { required: true, message: '货品编码不能为空', trigger: 'blur' }
        ],
        cName: [
          { required: true, message: '货品名称不能为空', trigger: 'blur' }
        ],
        pyCode: [
          { required: true, message: '拼音码不能为空', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '单位类别不能为空', trigger: 'blur' }
        ],
        contactPerson: [
          { required: true, message: '联系人不能为空', trigger: 'blur' }
        ],
        contactPhone: [
          { required: true, message: '联系电话不能为空', trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: '手机不能为空', trigger: 'blur' }
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
