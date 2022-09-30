<template>
  <div>
    <el-form :model="form" :rules="rules" ref="form" label-width="100px" class="demo-dynamic">
      <el-form-item label="班级名称" prop="clazzName">
        <el-input v-model="form.clazzName"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
        <el-button @click="closeMyDialog()">取 消</el-button>
        <el-button type="primary" @click="submitForm()">确 定</el-button>
    </span>
  </div>
</template>

<script>
import {addClassRequest, updateClassRequest} from "@/api/class";

export default {
  name: "ClassForm",
  props: ['update'],
  data() {
    return {
      form: {
        clazzName: ''
      },
      rules: {
        clazzName: [
          {required: true, message: '请输入班级名称', trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    console.log(this.update)
    if (this.update.id !== '') {
      this.form.clazzName = this.update.name
    }
  },
  methods: {
    closeMyDialog() {
      this.$emit("closeMyDialog");
    },
    submitForm() {
      if (this.update.id === '') {
        this.addClazz();
      } else {
        this.updateClazz();
      }
    },
    addClazz() {
      // console.log(this.form);
      addClassRequest(localStorage.getItem("token"), this.form.clazzName).then(response => {
        if (response.code === 200) {
          this.$message({
            message: '添加成功',
            type: 'success'
          });
          this.finish();
        } else {
          this.$message({
            message: '添加失败，错误信息：' + response.code + response.message,
            type: 'error'
          });
        }
      }).catch(error => {
        this.$message({
          message: '添加失败，错误信息：' + error,
          type: 'error'
        });
      })
    },
    updateClazz() {
      updateClassRequest(localStorage.getItem("token"), this.form.clazzName, this.update.id).then(response => {
        if (response.code === 200) {
          this.$message({
            message: '更新成功',
            type: 'success'
          });
          this.finish();
        } else {
          this.$message({
            message: '更新失败，错误信息：' + response.code + response.message,
            type: 'error'
          });
        }
      }).catch(error => {
        this.$message({
          message: '更新失败，错误信息：' + error,
          type: 'error'
        });
      })
    },
    finish() {
      this.$emit("initTable");
      this.closeMyDialog()
    }
  }
}
</script>

<style scoped>

</style>