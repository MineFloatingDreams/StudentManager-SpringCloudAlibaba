<template>
  <div class="form">
    <el-form :model="login" status-icon :rules="rules" ref="login" label-width="80px" class="demo-ruleForm">
      <el-form-item label="用户名" prop="username">
        <el-input :autofocus="true" type="text" v-model="login.username" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input type="password" v-model="login.password" autocomplete="off"></el-input>
      </el-form-item>
      <div>
        <el-button class="form-btn" type="primary" :loading="updating" plain @click="loginByPassword()">登录</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import "@/styles/login.css"
import "@/api/login"
import {loginByPasswordRequest} from "@/api/login";

export default {
  name: "LoginByPassword",
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'));
      } else {
        callback();
      }
    };
    const validateUsername = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入用户名'));
      } else {
        callback();
      }
    };
    return {
      rules: {
        password: [
          {validator: validatePassword, trigger: 'blur'}
        ],
        username: [
          {validator: validateUsername, trigger: 'blur'}
        ],
      },
      login: {
        password: '',
        username: '',
      },
      updating: false
    }
  },
  methods: {
    loginByPassword() {
      this.updating = !this.updating;
      this.$refs['login'].validate((valid) => {
        if (valid) {
          loginByPasswordRequest(this.login.username, this.login.password).then(res => {
            console.log(res)
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
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    keyDown(e) {
      // 回车则执行登录方法 enter键的ASCII是13
      if (e.keyCode === 13 || e.keyCode === 100) {
        this.loginByPassword(); // 定义的登录方法
      }
    }
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
.form {
  padding-right: 30px;
}

.form-btn {
  width: 320px;
  height: 50px;
}
</style>