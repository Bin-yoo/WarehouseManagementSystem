<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input
          v-model="query.blurry"
          clearable
          size="small"
          placeholder="输入单位编码或名称搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <el-select v-model="query.type"
          placeholder="请选择单位类别"
          class="filter-item"
          style="width: 200px;"
          @change="crud.toQuery"
        >
          <el-option label="供应商" value="1" />
          <el-option label="客户" value="2" />
          <el-option label="供应商、客户" value="3" />
        </el-select>
        <el-input
          v-model="query.contactPerson"
          clearable
          size="small"
          placeholder="输入联系人姓名搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <rrOperation />
      </div>
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="750px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-row>
            <el-col :span="11">
              <el-form-item label="单位编码" prop="cCode">
                <el-input v-model="form.cCode" disabled />
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="单位名称" prop="cName">
                <el-input v-model="form.cName" @input="convertPinyin" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="拼音码" prop="pyCode">
                <el-input v-model="form.pyCode" disabled placeholder="自动生成"/>
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="单位类别" prop="type">
                <el-select v-model="form.type" placeholder="请选择单位类别" style="width: 100%;">
                  <el-option label="供应商" value="1" />
                  <el-option label="客户" value="2" />
                  <el-option label="供应商、客户" value="3" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="所在省份">
                <el-input v-model="form.province" />
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="详细地址">
                <el-input v-model="form.address" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="联系人" prop="contactPerson">
                <el-input v-model="form.contactPerson" />
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="联系电话" prop="contactPhone">
                <el-input v-model="form.contactPhone" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="手机" prop="mobile">
                <el-input v-model="form.mobile" />
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="传真">
                <el-input v-model="form.fax" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="邮编">
                <el-input v-model="form.zip" />
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="邮箱" prop="email">
                <el-input v-model="form.email" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="公司网址">
                <el-input v-model="form.website" />
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="QQ号码">
                <el-input v-model="form.qq" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="银行名称">
                <el-input v-model="form.bank" />
              </el-form-item>
            </el-col>
            <el-col :span="11" :offset="1">
              <el-form-item label="银行卡号">
                <el-input v-model="form.bankAccount" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="23">
              <el-form-item label="备注" prop="remark">
                <el-input type="textarea" row="2" v-model="form.remark"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="cCode" label="单位编码" />
        <el-table-column prop="cName" label="单位名称" />
        <el-table-column prop="pyCode" label="拼音码" />
        <el-table-column prop="type" label="单位类别" />
          <template slot-scope="scope">
            {{ scope.row.type | transferCompanyType()}}
          </template>
        </el-table-column>
        
        <el-table-column prop="province" label="所在省份" width="200">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.province" placement="top-start">
              <div>{{ scope.row.province ? scope.row.province.length > 12 ? scope.row.province.substr(0,12)+'...' : scope.row.province : '' }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="address" label="详细地址" width="200">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.address" placement="top-start">
              <div>{{ scope.row.address ? scope.row.address.length > 12 ? scope.row.address.substr(0,12)+'...' : scope.row.address : '' }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="contactPerson" label="联系人" />
        <el-table-column prop="contactPhone" label="联系电话" width="100" />
        <el-table-column prop="mobile" label="手机" width="100" />
        <el-table-column prop="fax" label="传真" />
        <el-table-column prop="zip" label="邮编" />
        <el-table-column prop="email" label="邮箱" width="150" />
        <el-table-column prop="website" label="公司网址" width="200">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.website" placement="top-start">
              <div>{{ scope.row.website ? scope.row.website.length > 12 ? scope.row.website.substr(0,12)+'...' : scope.row.website : '' }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="qq" label="QQ号码" />
        <el-table-column prop="bank" label="银行名称" />
        <el-table-column prop="bankAccount" label="银行卡号" />
        <el-table-column prop="remark" label="备注" width="200">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.remark" placement="top-start">
              <div>{{ scope.row.remark ? scope.row.remark.length > 12 ? scope.row.remark.substr(0,12)+'...' : scope.row.remark : '' }}</div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column fixed="right" v-if="checkPer(['admin','tbPartnerCompanyInfo:edit','tbPartnerCompanyInfo:del'])" label="操作" width="150px" align="center">
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
import pinyin from 'js-pinyin'

const defaultForm = { id: null, cCode: null, cName: null, pyCode: null, type: '3', province: null, address: null, contactPerson: null, contactPhone: null, mobile: null, fax: null, zip: null, email: null, website: null, qq: null, bank: null, bankAccount: null, remark: null }
export default {
  name: 'TbPartnerCompanyInfo',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '来往单位', url: 'api/tbPartnerCompanyInfo', idField: 'id', sort: 'id,desc', crudMethod: { ...crudTbPartnerCompanyInfo }})
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
          { required: true, message: '单位编码不能为空', trigger: 'blur' }
        ],
        cName: [
          { required: true, message: '单位名称不能为空', trigger: 'blur' }
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
        mobile: [
          { required: true, message: '手机不能为空', trigger: 'blur' },
          { message: '手机号格式不正确', trigger: 'blur', pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/ }
        ],
        email: [
          { message: '邮箱格式不正确', trigger: 'blur', pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/ }
        ],
        remark: [
          { message: '长度不能超过100个字符', trigger: 'blur', max: 100, type: 'string' }
        ]
      }}
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    },
    convertPinyin(value) {
      this.form.pyCode = pinyin.getCamelChars(value)
    },
    // 新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      if (form.id == null) {
        this.getCompanyCode()
      }
    },
    getCompanyCode() {
      crudTbPartnerCompanyInfo.getCompanyCode().then(res => {
        this.form.cCode = res
      })
    }
  },
  filters: {
    transferCompanyType(val) {
      if(val == 1) {
        return '供应商'
      }
      if(val == 2) {
        return '客户'
      }
      if(val == 3) {
        return '供应商、客户'
      }
    }
  }
}
</script>

<style scoped>

</style>
