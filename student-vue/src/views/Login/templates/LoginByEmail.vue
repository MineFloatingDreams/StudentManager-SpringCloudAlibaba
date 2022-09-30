<template>
  <div class="form">
    <el-form :model="login" status-icon :rules="rules" ref="login" label-width="80px" class="demo-ruleForm">
      <el-form-item label="邮箱号" prop="email">
        <el-input type="text" v-model="login.email" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="验证码" prop="code">
        <el-col :span="12">
          <el-input type="text" v-model="login.code" autocomplete="off"></el-input>
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
      <div>
        <el-button class="form-btn" type="primary" :loading="updating" plain @click="loginByEmail()">登录</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import "@/styles/login.css"
import "@/api/code"
import {getForCode} from "@/api/code";
import {loginByEmailRequest} from "@/api/login";

export default {
  name: "LoginByPassword",
  data() {
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
        email: [
          {validator: validateEmail, trigger: 'blur'}
        ],
        code: [
          {validator: validateCode, trigger: 'blur'}
        ]
      },
      login: {
        email: '',
        code: '',
      },
      updating: false
    }
  },
  methods: {
    verification() {
      this.$refs.login.validateField('email', errorMsg => {
            if (!errorMsg) {
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
            }
          }
      )
    },
    getVerification() {
      getForCode(this.login.email).then(response => {
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
    loginByEmail() {
      this.updating = !this.updating;
      this.$refs['login'].validate((valid) => {
        if (valid) {
          loginByEmailRequest(this.login.email, this.login.code).then(res => {
            if (res.code === 200) {
              this.$message({
                message: res.message,
                type: 'success'
              });
              sessionStorage.setItem("user", JSON.stringify(res.data.user))
              localStorage.setItem("token", res.data.token)
              this.$router.push({
                path: "/",
              })
            } else {
              this.$message({
                message: res.code + ":" + res.message,
                type: 'warning'
              });
            }
            this.updating = !this.updating;
          }).catch(res => {
            this.$message({
              message: res,
              type: 'warning'
            });
          })
        }
      })
    },
    keyDown(e) {
      // 回车则执行登录方法 enter键的ASCII是13
      if (e.keyCode === 13 || e.keyCode === 100) {
        this.loginByEmail(); // 定义的登录方法
      }
    },
  },
  mounted() {
    // 绑定监听事件
    window.addEventListener("keydown", this.keyDown);
  },
  destroyed() {
    // 销毁事件
    window.removeEventListener("keydown", this.keyDown, false);
  },
}
</script>

<style scoped>

.code-button {
  float: right;
  margin-right: 5px;
  margin-top: 5px
}

.form-btn {
  width: 320px;
  height: 50px;
}

.form {
  padding-right: 30px;
}
</style>