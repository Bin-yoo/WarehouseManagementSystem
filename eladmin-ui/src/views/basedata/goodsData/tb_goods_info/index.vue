<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <div v-if="crud.props.searchToggle">
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
          placeholder="输入拼音码搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <treeselect
          v-model="query.type"
          :options="types"
          :load-options="loadTypesSelectTree"
          class="dept_tree_select"
          placeholder="货品分类"
        />
        <!-- <el-input
          v-model="query.specification"
          clearable
          size="small"
          placeholder="输入规格搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <el-input
          v-model="query.model"
          clearable
          size="small"
          placeholder="输入型号搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <el-input
          v-model="query.weight"
          clearable
          size="small"
          placeholder="输入重量搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <el-input
          v-model="query.color"
          clearable
          size="small"
          placeholder="输入颜色搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        /> -->
        <el-select
          v-model="query.unit"
          placeholder="选择计量单位"
          style="width: 200px;"
          class="filter-item"
          clearable
        >
          <el-option
            v-for="item in search_units_select"
            :key="item.id"
            :label="item.guName"
            :value="item.id"
          />
        </el-select>
        <!-- <el-input
          v-model="query.purchasePrice"
          clearable
          size="small"
          placeholder="输入进价搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <el-input
          v-model="query.sellPrice"
          clearable
          size="small"
          placeholder="输入售价搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        />
        <el-input
          v-model="query.manufacturer"
          clearable
          size="small"
          placeholder="输入生产厂家搜索"
          style="width: 200px;"
          class="filter-item"
          @keyup.enter.native="crud.toQuery"
        /> -->
        <rrOperation />
      </div>
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" @closed="dialogClose" width="700px">
        <div class="el-dialog-div">
          <el-tabs type="border-card" v-model="activeName">
            <el-tab-pane label="基本信息" name="base_info">
              <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
                <el-row>
                  <el-col :span="11">
                    <el-form-item label="货品编码" prop="gCode">
                      <el-input v-model="form.gCode" disabled placeholder="选择货品类别后自动生成" />
                    </el-form-item>
                  </el-col>
                  <el-col :span="11" :offset="1">
                    <el-form-item label="货品名称" prop="gName">
                      <el-input v-model="form.gName" @input="convertPinyin" />
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="23">
                    <el-form-item label="货品类别" prop="type">
                      <treeselect
                        v-model="form.type"
                        :options="typesSelectTree"
                        placeholder="选择货品类别"
                        class="dept_tree_select_dialog"
                        @select="selectType"
                        @input="deselectType"
                      />
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="11">
                    <el-form-item label="拼音码">
                      <el-input v-model="form.pyCode" disabled placeholder="自动生成" />
                    </el-form-item>
                  </el-col>
                  <el-col :span="11" :offset="1">
                    <el-form-item label="规格">
                      <el-input v-model="form.specification" />
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="11">
                    <el-form-item label="型号">
                      <el-input v-model="form.model" />
                    </el-form-item>
                  </el-col>
                  <el-col :span="11" :offset="1">
                    <el-form-item label="重量">
                      <el-input v-model="form.weight" />
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="11">
                    <el-form-item label="颜色">
                      <el-input v-model="form.color" />
                    </el-form-item>
                  </el-col>
                  <el-col :span="11" :offset="1">
                    <el-form-item label="计量单位" prop="unit">
                      <el-select v-model="form.unit" placeholder="请选择货品单位" style="width: 100%">
                        <el-option
                          v-for="item in units"
                          :key="item.id"
                          :label="item.guName"
                          :value="item.id"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="11">
                    <el-form-item label="进价">
                      <el-input-number v-model="form.purchasePrice" :min="0" controls-position="right" :precision="2" style="width: 100%" />
                    </el-form-item>
                  </el-col>
                  <el-col :span="11" :offset="1">
                    <el-form-item label="售价">
                      <el-input-number v-model="form.sellPrice" :min="0" controls-position="right" :precision="2" style="width: 100%" />
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="11">
                    <el-form-item label="货位(m²)">
                      <el-input-number v-model="form.allocation" :min="0" :max="999" controls-position="right" style="width: 100%" />
                    </el-form-item>
                  </el-col>
                  <el-col :span="11" :offset="1">
                    <el-form-item label="生产厂家">
                      <el-input v-model="form.manufacturer" />
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="23">
                    <el-form-item label="备注" prop="remark">
                      <el-input v-model="form.remark" type="textarea" row="2" />
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="23">
                    <el-form-item label="二维码">
                      <el-input v-model="form.qrCode" />
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="23">
                    <el-form-item label="货品图片">
                      <el-upload
                        :class="{uploadBtn:uploadBtn,disUpload:disUpload}"
                        action="#"
                        list-type="picture-card"
                        ref="uploadImg"
                        :file-list="imgList"
                        :http-request="upload"
                        :on-remove="handleRemove"
                        :on-change="handleChange"
                        :on-preview="handlePictureCardPreview"
                        :limit="1"
                      >
                        <i slot="default" class="el-icon-plus" />
                      </el-upload>
                      <el-dialog :visible.sync="dialogVisible" append-to-body width="600px">
                        <img width="100%" :src="dialogImageUrl" alt="">
                      </el-dialog>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
            </el-tab-pane>
            <el-tab-pane label="库存信息" name="inventory_info">
              <el-table :data="form.whGoodsList" border size="small" style="width: 100%;" max-height="600">
                <el-table-column type="index" label="#" />
                <el-table-column prop="whName" label="仓库名称" />
                <el-table-column label="库初数量">
                  <template slot-scope="scope">
                    <el-input-number v-model="scope.row.initialCount" :min="0" controls-position="right" style="width: 100%" />
                  </template>
                </el-table-column>
                <el-table-column label="库存下限">
                  <template slot-scope="scope">
                    <el-input-number v-model="scope.row.lowerLimit" :min="0" controls-position="right" style="width: 100%" />
                  </template>
                </el-table-column>
                <el-table-column label="库存上限">
                  <template slot-scope="scope">
                    <el-input-number v-model="scope.row.upperLimit" :min="0" controls-position="right" style="width: 100%" />
                  </template>
                </el-table-column>
              </el-table>
            </el-tab-pane>
          </el-tabs>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" fixed="left" />
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
        <!-- <el-table-column prop="pic" label="货品图片" /> -->
        <el-table-column v-if="checkPer(['admin','tbGoodsInfo:edit','tbGoodsInfo:del'])" fixed="right" label="操作" width="150px" align="center">
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
import crudTbGoodsInfo from '@/api/tbGoodsInfo'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'
import pinyin from 'js-pinyin'
import image from '@/api/image'

const defaultForm = { id: null, gCode: null, gName: null, specification: null, pyCode: null, manufacturer: null, remark: null, barCode: null, qrCode: null, type: null, model: null, weight: null, color: null, unit: null, purchasePrice: null, sellPrice: null, allocation: null, pic: null, whGoodsList: null }
export default {
  name: 'TbGoodsInfo',
  components: { pagination, crudOperation, rrOperation, udOperation, Treeselect },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '货品', url: 'api/tbGoodsInfo', idField: 'id', sort: 'id,desc', crudMethod: { ...crudTbGoodsInfo }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'tbGoodsInfo:add'],
        edit: ['admin', 'tbGoodsInfo:edit'],
        del: ['admin', 'tbGoodsInfo:del']
      },
      rules: {
        id: [
          { required: true, message: '货品id不能为空', trigger: 'blur' }
        ],
        gCode: [
          { required: true, message: '货品编码不能为空', trigger: 'blur' }
        ],
        gName: [
          { required: true, message: '货品名称不能为空', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '货品类别不能为空', trigger: 'blur' }
        ],
        unit: [
          { required: true, message: '计量单位不能为空', trigger: 'blur' }
        ],
        remark: [
          { message: '长度不能超过100个字符', trigger: 'blur', max: 100, type: 'string' }
        ]
      },
      activeName: 'base_info',
      typesSelectTree: [],
      types: [],
      search_units_select: [],
      units: [],
      imgList: [],
      dialogVisible: false,
      disabled: false,
      uploadBtn: true,
      disUpload: false,
      dialogImageUrl: ''
    }
  },
  created() {
    this.loadSreachParamTypesSelectTree()
    this.getSearchGoodUnitsSelect()
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    },
    // 新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      form.whGoodsList = []
      if (form.id != null) {
        this.getWareHouseGoodsInfoByGoodId(form.id)
      } else {
        this.getWareHouseGoodsInfo()
      }
      this.loadTypesSelectTree()
      this.getGoodUnitsSelect()
      if (form.pic != null) {
        this.loadGoodPic(form.pic)
      }
    },
    dialogClose() {
      this.activeName = 'base_info'
      this.$refs.uploadImg.clearFiles()
      this.disUpload = false
      this.imgList = []
    },
    getSearchGoodUnitsSelect() {
      crudTbGoodsInfo.getGoodUnitsSelect().then(res => {
        this.search_units_select = res
      })
    },
    loadSreachParamTypesSelectTree() {
      crudTbGoodsInfo.getTypesSelectTree().then(res => {
        this.types = res
      })
    },
    // 获取弹窗内分类数据
    loadTypesSelectTree() {
      crudTbGoodsInfo.getTypesSelectTree().then(res => {
        this.typesSelectTree = res
      })
    },
    selectType(node, instanceId) {
      this.getGoodCode(node.id)
    },
    deselectType(value, instanceId) {
      if (value == null) {
        this.form.gCode = null
      }
    },
    getGoodCode(typeId) {
      crudTbGoodsInfo.getGoodCode(typeId).then(res => {
        this.form.gCode = res
      })
    },
    getGoodUnitsSelect() {
      crudTbGoodsInfo.getGoodUnitsSelect().then(res => {
        this.units = res
      })
    },
    getWareHouseGoodsInfoByGoodId(id) {
      crudTbGoodsInfo.getWareHouseGoodsInfoByGoodId(id).then(res => {
        this.form.whGoodsList = res
      })
    },
    getWareHouseGoodsInfo() {
      crudTbGoodsInfo.getWareHouseGoodsInfo().then(res => {
        this.form.whGoodsList = res
      })
    },
    beforeAvatarUpload(file) {
      return new Promise((resolve, reject) => {
        const isRuleImg = file.type === 'image/jpeg' || file.type === 'image/png'
        // const size = file.size / 1024 < 50
        if (!isRuleImg) {
          // ctx.root.$message.error('图片只能是 JPG/PNG 格式!')
          reject()
        }
        // if (!size) {
        //     ctx.root.$message.error(`图片大小不能超过50KB!`)
        //     reject()
        // }
        resolve()
      })
    },
    upload(fileObj) {
      const file = fileObj.file
      this.beforeAvatarUpload(file)
      const formData = new FormData()
      formData.append('file', file)
      crudTbGoodsInfo.uploadGoodPic(formData).then(res => {
        this.form.pic = res
      })
    },
    handleRemove(file, fileList) {
      this.disUpload = fileList.length >= 1
      this.imgList = []
      this.dialogImageUrl = ''
    },
    handleChange(file, fileList) {
      this.disUpload = fileList.length >= 1
      this.dialogImageUrl = file.url
    },
    handlePictureCardPreview(file) {
      // this.form.pic = file.url
      this.dialogVisible = true
    },
    loadGoodPic(id) {
      image.loadBase64Img(id).then(res => {
        const obj = {
          url: 'data:image/png;base64,' + res
        }
        this.imgList.push(obj)
        this.dialogImageUrl = obj.url
        this.disUpload = true
      })
    },
    handleDownload(file) {
      console.log(file)
    },
    convertPinyin(value) {
      this.form.pyCode = pinyin.getCamelChars(value)
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
  .el-dialog-div{
    height: 60vh;
    overflow: auto;
  }
  .el-tabs{
    margin: 0;
  }
  .uploadBtn {
    .el-upload--picture-card{
      width:110px;
      height:110px;
      line-height:110px;
    }
    .el-upload-list{
      .el-upload-list__item{
        width:110px;
        height:110px;
        line-height:110px;
      }
    }
  }
  .disUpload .el-upload--picture-card{
    display: none;   /* 上传按钮隐藏 */
  }
</style>
