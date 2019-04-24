<template>
  <div>
    <el-button size="mini" type="success" @click="to">编辑</el-button>
    <eForm ref="form" :sup_this="sup_this" :is-add="false"/>
  </div>
</template>
<script>
import eForm from './form'
export default {
  components: { eForm },
  props: {
    data: {
      type: Object,
      required: true
    },
    sup_this: {
      type: Object,
      required: true
    }
  },
  methods: {
    to() {
      const _this = this.$refs.form
      _this.form = {
        id: this.data.id,
        areaCode: this.data.areaCode,
        name: this.data.name,
        address: this.data.address,
        workTel: this.data.workTel,
        principal: this.data.principal,
        principalPosition: this.data.principalPosition,
        principalPhone: this.data.principalPhone,
        contact: this.data.contact,
        contactPosition: this.data.contactPosition,
        contactPhone: this.data.contactPhone,
        version: this.data.version,
        terminalNum: this.data.terminalNum,
        enabled: this.data.enabled
      }
      _this.dialog = true
    }
  }
}
</script>

<style scoped>
  div{
    display: inline-block;
    margin-right: 3px;
  }
</style>
