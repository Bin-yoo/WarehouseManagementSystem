<template>
  <el-popover v-model="pop" v-permission="permission.del" placement="top" width="180" trigger="manual" @show="onPopoverShow" @hide="onPopoverHide">
    <p>{{ msg }}?</p>
    <div style="text-align: right; margin: 0">
      <el-button size="mini" type="text" @click="pop = false">取消</el-button>
      <el-button :loading="status" type="primary" size="mini" @click="doDelete">确定</el-button>
    </div>
    <el-button slot="reference" type="danger" icon="el-icon-delete" size="mini" @click="pop = true" />
  </el-popover>
</template>
<script>
export default {
  props: {
    id: {
      type: String,
      required: true
    },
    permission: {
      type: Object,
      required: true
    },
    status: {
      type: Boolean,
      default: false
    },
    msg: {
      type: String,
      default: '确定删除本条数据吗？'
    }
  },
  data() {
    return {
      pop: false
    }
  },
  methods: {
    doCancel() {
      this.pop = false
    },
    toDelete() {
      this.pop = true
    },
    doDelete() {
      this.doCancel()
      this.$emit('doDelete',this.id);
    },
    onPopoverShow() {
      setTimeout(() => {
        document.addEventListener('click', this.handleDocumentClick)
      }, 0)
    },
    onPopoverHide() {
      document.removeEventListener('click', this.handleDocumentClick)
    },
    handleDocumentClick(event) {
      this.pop = false
    }
  }
}
</script>
