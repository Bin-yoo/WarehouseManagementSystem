<template>
  <el-dialog
    width="1000px"
    title="查看打印单据"
    :visible.sync="visible"
    :close-on-click-modal="false"
    append-to-body
    @open="printOrderReport()"
    @close="cancel()"
  >
    <div style="padding: 0 30px; color: #000000; width: 100%">
      <el-button type="primary" icon="el-icon-printer" v-print="'#printMe'">打印单据</el-button>
    </div>
    <div id="printMe" v-html="templateData" v-loading="loading">
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="cancel">取消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import ProductOrders from '@/api/productOrders.js'

export default {
  name: 'ProductOrderPrinter',
  props: {
    innerVisible: {
      type: Boolean,
      default: false
    },
    printOrderId: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      orderId: null,
      visible: false,
      templateData: null,
      loading: false
    }
  },
  methods: {
    cancel() {
      this.$emit('cancel')
      this.orderId = null
      this.templateData = null
    },
    printOrderReport() {
      this.loading = true
      ProductOrders.printOrderReport(this.orderId).then(res => {
        this.templateData = res
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    }
  },
  watch: {
    innerVisible: function(val) {
      this.visible = val
    },
    printOrderId: function(val) {
      this.orderId = val
    }
  }
}

</script>

<style>

</style>
