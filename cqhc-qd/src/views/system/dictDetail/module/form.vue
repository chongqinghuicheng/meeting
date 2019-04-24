<template>
  <el-dialog :append-to-body="true" :visible.sync="dialog" :title="isAdd ? '新增' : '编辑'" width="500px">
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
      <el-form-item label="areaCode">
        <el-input v-model="form.areaCode" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="name">
        <el-input v-model="form.name" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="address">
        <el-input v-model="form.address" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="workTel">
        <el-input v-model="form.workTel" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="principal">
        <el-input v-model="form.principal" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="principalPosition">
        <el-input v-model="form.principalPosition" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="principalPhone">
        <el-input v-model="form.principalPhone" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="contact">
        <el-input v-model="form.contact" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="contactPosition">
        <el-input v-model="form.contactPosition" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="contactPhone">
        <el-input v-model="form.contactPhone" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="数据字典version_number
            0-基础版
            1-标准版
            2-党委版
            3-政府版
            4-人大版
            5-政协版
            6-部门版
            7-企业版
            9-其他
            ">
        <el-input v-model="form.version" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="terminalNum">
        <el-input v-model="form.terminalNum" style="width: 370px;"/>
      </el-form-item>
      <el-form-item label="数据字典dept_status
            true正常
            false停用">
        <el-input v-model="form.enabled" style="width: 370px;"/>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="text" @click="cancel">取消</el-button>
      <el-button :loading="loading" type="primary" @click="doSubmit">确认</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { add, edit } from '@/api/unit'
export default {
  props: {
    isAdd: {
      type: Boolean,
      required: true
    },
    sup_this: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      loading: false, dialog: false,
      form: {
        id: '',
        areaCode: '',
        name: '',
        address: '',
        workTel: '',
        principal: '',
        principalPosition: '',
        principalPhone: '',
        contact: '',
        contactPosition: '',
        contactPhone: '',
        version: '',
        terminalNum: '',
        enabled: ''
      }
    }
  },
  methods: {
    cancel() {
      this.resetForm()
    },
    doSubmit() {
      this.loading = true
      if (this.isAdd) {
        this.doAdd()
      } else this.doEdit()
    },
    doAdd() {
      add(this.form).then(res => {
        this.resetForm()
        this.$notify({
          title: '添加成功',
          type: 'success',
          duration: 2500
        })
        this.loading = false
        this.$parent.$parent.init()
      }).catch(err => {
        this.loading = false
        console.log(err.response.data.message)
      })
    },
    doEdit() {
      edit(this.form).then(res => {
        this.resetForm()
        this.$notify({
          title: '修改成功',
          type: 'success',
          duration: 2500
        })
        this.loading = false
        this.sup_this.init()
      }).catch(err => {
        this.loading = false
        console.log(err.response.data.message)
      })
    },
    resetForm() {
      this.dialog = false
      this.$refs['form'].resetFields()
      this.form = {
        id: '',
        areaCode: '',
        name: '',
        address: '',
        workTel: '',
        principal: '',
        principalPosition: '',
        principalPhone: '',
        contact: '',
        contactPosition: '',
        contactPhone: '',
        version: '',
        terminalNum: '',
        enabled: ''
      }
    }
  }
}
</script>

<style scoped>

</style>
