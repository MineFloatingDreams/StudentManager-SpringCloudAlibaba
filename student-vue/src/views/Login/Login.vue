<template>
  <div class="bg-wrap">
    <div class="main-cont-wrap login-model">
      <form id="form">
        <div class="form-title">
          <span>用户登录</span>
          <div class="fr links">
            <el-link type="primary" @click="toIndex()"><i class="el-icon-house"></i> 前往首页</el-link>
            <el-link type="primary" style="margin-left: 20px" @click="toRegister()"><i class="el-icon-user"></i> 前往注册
            </el-link>
          </div>
        </div>
        <el-tabs activeName="first"  @tab-click="handleClick" :stretch="TabStatus">
          <el-tab-pane label="密码登录" name="first"></el-tab-pane>
          <el-tab-pane label="邮箱登录" name="second"></el-tab-pane>
        </el-tabs>
        <router-view></router-view>

      </form>
    </div>
  </div>
</template>

<script>
import "@/styles/login.css"

export default {
  name: "Login",
  data() {
    return {
      radio: '密码登录',
      TabStatus:true
    }
  },
  created() {
    // this.$router.push("/Login/LoginByPassword")
  },
  methods: {
    toRegister() {
      this.$router.push("/Register")
    },
    toPassword() {
      this.$router.push("/Login/LoginByPassword")
    },
    toEmail() {
      this.$router.push("/Login/LoginByEmail")
    },
    toIndex() {
      this.$router.push("/")
    },
    handleClick(tab, event) {
      // console.log(tab, event);
      // console.log(tab.paneName)
      const name = tab.paneName;
      if (name === "first") {
        this.toPassword();
      } else {
        this.toEmail();
      }
    }
  }
}
</script>

<style scoped>
.links {
  margin-top: 5px;
  margin-right: 30px;
}

.form-method {
  margin-bottom: 20px;
}
</style>