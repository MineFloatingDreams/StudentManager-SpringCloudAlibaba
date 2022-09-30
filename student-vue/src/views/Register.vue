<template>
  <div class="bg-wrap">
    <div class="main-cont-wrap login-model">
      <div class="form-title">
        <span>用户注册</span>
        <div class="fr links">
          <el-link type="primary" @click="toIndex()"><i class="el-icon-house"></i> 前往首页</el-link>
          <el-link type="primary" style="margin-left: 20px" @click="toLogin()"><i class="el-icon-user"></i> 前往登录
          </el-link>
        </div>
      </div>
      <div class="form">
        <el-form :model="register" status-icon :rules="rules" ref="register" label-width="80px" class="demo-ruleForm">
          <el-form-item label="用户名" prop="username">
            <el-input type="text" v-model="register.username" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="密码" prop="pass">
            <el-input type="password" v-model="register.pass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPass">
            <el-input type="password" v-model="register.checkPass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="邮箱号" prop="email">
            <el-input type="text" v-model="register.email" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="验证码" prop="code">
            <el-col :span="12">
              <el-input type="text" v-model="register.code" autocomplete="off"></el-input>
            </el-col>
            <el-col :span="12">
              <el-button v-if="disabled" type="primary" size="small" class="code-button"
                         @click="verification">
                获取验证码
              </el-button>
              <el-button v-if="!disabled" type="info" disabled size="small" class="code-button">
                {{ timer }}秒后重试
              </el-button>
            </el-col>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm()">提交</el-button>
            <el-button @click="resetForm()">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import "@/styles/login.css"
import {registerRequest} from "@/api/register";
import {getForCode} from "@/api/code";

export default {
  name: "Register",
  data() {
    const validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        if (this.register.checkPass !== '') {
          this.$refs.register.validateField('checkPass');
        }
        callback();
      }
    };
    const validatePass2 = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'));
      } else if (value !== this.register.pass) {
        callback(new Error('两次输入密码不一致!'));
      } else {
        callback();
      }
    };
    const validateUsername = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入用户名'));
      } else if (value === "admin" || value === "root") {
        callback(new Error('用户名不可以为admin或root'));
      } else if (value.length < 5) {
        callback(new Error('用户名长度需要大于5'));
      } else {
        callback();
      }
    };
    const validateEmail = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请正确填写邮箱'));
      } else {
        if (value !== '') {
          const reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
          if (!reg.test(value)) {
            callback(new Error('请输入有效的邮箱'));
          }
        }
        callback();
      }
    };
    const validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请正确填写验证码'));
      } else if (value.length !== 6) {
        callback(new Error('验证码长度为6'));
      } else {
        callback();
      }
    }

    return {
      disabled: true,
      timer: 60,
      rules: {
        pass: [
          {validator: validatePass, trigger: 'blur'}
        ],
        checkPass: [
          {validator: validatePass2, trigger: 'blur'}
        ],
        username: [
          {validator: validateUsername, trigger: 'blur'}
        ],
        email: [
          {validator: validateEmail, trigger: 'blur'}
        ],
        code: [
          {validator: validateCode, trigger: 'blur'}
        ]
      },
      register: {
        pass: '',
        checkPass: '',
        username: '',
        email: '',
        code: ''
      }
    };

  },
  methods: {
    toLogin() {
      this.$router.push("/Login")
    },
    toIndex() {
      this.$router.push("/")
    },
    verification() {
      this.disabled = false
      this.getVerification()// 调获取验证码接口
      const authTimer = setInterval(() => {
        this.timer--
        if (this.timer <= 0) {
          this.disabled = true
          this.timer = 60
          clearInterval(authTimer)
        }
      }, 1000)
    },
    getVerification() {
      getForCode(this.register.email).then(response => {
        if (response.code === 200) {
          this.$message({
            message: "验证码发送成功，注意查收",
            type: 'success'
          });
        } else {
          this.$message({
            message: response.code + ":" + response.message,
            type: 'warning'
          });
        }
      })
    },
    submitForm() {
      this.$refs['register'].validate((valid) => {
        if (valid) {
          const register = this.register
          registerRequest(register.username, register.pass, register.email, register.code).then(res => {
            if (res.code === 200) {
              this.$message({
                message: '注册成功',
                type: 'success'
              });
            } else {
              this.$message.error('错误信息:' + res.code + ":" + res.message);
            }
          }).catch(res => {
            this.$message.error('错误信息:' + res);
          })
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm() {
      this.$refs['register'].resetFields();
    }
  }
}
</script>

<style scoped>
.form-title {
  margin-bottom: 40px;
}

.links {
  margin-top: 5px;
}


.form {
  padding-right: 30px;
}

.code-button {
  float: right;
  margin-right: 5px;
  margin-top: 5px
}
</style>