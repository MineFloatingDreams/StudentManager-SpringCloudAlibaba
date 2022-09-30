<template>
  <div class="student_form">
    <el-form :model="form" :rules="rules" ref="form" label-width="100px">
      <el-form-item label="学生学号" prop="id">
        <el-input v-model="form.id" placeholder="请输入学生学号" :disabled="studentIdInput"></el-input>
      </el-form-item>
      <el-form-item label="学生姓名" prop="name">
        <el-input v-model="form.name" placeholder="请输入学生姓名"></el-input>
      </el-form-item>
      <el-form-item label="学生年龄" prop="age">
        <el-input v-model.number="form.age" placeholder="请输入学生年龄"></el-input>
      </el-form-item>
      <el-form-item label="学生性别" prop="sex">
        <el-radio-group v-model="form.sex">
          <el-radio label="m">男</el-radio>
          <el-radio label="f">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="所在班级" prop="clazzId">
        <el-select v-model="form.classId" placeholder="请选择班级">
          <el-option
              v-for="item in clazzList"
              :key="item.id"
              :label="item.clazzName"
              :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
        <el-button @click="closeMyDialog()">取 消</el-button>
        <el-button type="primary" @click="submitForm()">确 定</el-button>
    </span>
  </div>

</template>

<script>
import {getClazzRequest} from "@/api/class";
import {addStudentRequest, updateStudentRequest} from "@/api/student";

export default {
  name: "StudentForm",
  props: ['update'],
  data() {
    return {
      form: {
        id: '',
        name: '',
        age: null,
        sex: 'm',
        className: '',
        classId: ''
      },
      studentIdInput: false,
      token: '',
      clazzList: [],
      rules: {
        id: {required: true, message: '请输入学号', trigger: 'blur'},
        name: {required: true, message: '请输入学生姓名', trigger: 'blur'},
        age: [
          {required: true, message: '请输入学生年龄', trigger: 'blur'},
          {type: 'number', message: '年龄必须为数字值'}
        ],
        classId: {required: true, message: '请选择班级', trigger: 'blur'}
      }
    }
  },
  created() {
    this.token = localStorage.getItem("token");
    if (this.update.id !== '') {
      this.studentIdInput = true;
      this.form = this.update;
    }
    getClazzRequest(this.token)
        .then(res => {
          this.clazzList = []
          this.clazzList = JSON.parse(res.data);
        })
        .catch(err => {
          console.log(err)
          this.$message.error('发生错误，错误信息：' + err)
        })
  },
  methods: {
    closeMyDialog() {
      this.update.removeOnDestroy = true;
    },
    submitForm() {
      if (this.update.id === '') {
        this.addStudent();
      } else {
        this.updateStudent();
      }
    },
    addStudent() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          addStudentRequest(this.token, this.form.id, this.form.name,
              this.form.age, this.form.sex, this.form.classId).then(response => {
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
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    updateStudent() {
      console.log(this.form)
      this.$refs['form'].validate((valid) => {
        if (valid) {
          updateStudentRequest(this.token, this.form.id, this.form.name,
              this.form.age, this.form.sex, this.form.classId).then(response => {
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
        }
      });
    },
    finish() {
      this.$emit("initTable");
      this.closeMyDialog()
      this.$emit("closeMyDialog");
    }
  }
}
</script>

<style scoped>

</style>