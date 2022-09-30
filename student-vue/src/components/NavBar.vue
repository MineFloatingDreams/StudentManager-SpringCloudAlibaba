<template>
  <el-menu
      class="el-menu-demo"
      mode="horizontal"
      @select="handleSelect"
      background-color="#324057"
      text-color="#fff"
      active-text-color="#409EFF">
    <el-menu-item>
      <div class="logo">
        学生管理系统
      </div>
    </el-menu-item>
    <el-menu-item index="1">首页</el-menu-item>
    <el-menu-item v-if="username===''" style="float: right" index="login">请登录</el-menu-item>
    <el-submenu v-else style="float: right" index="5">
      <template slot="title">欢迎您，{{ username }}</template>
      <el-menu-item index="5-1">退出登录</el-menu-item>
    </el-submenu>
  </el-menu>
</template>

<script>
export default {
  inject: ['reload'], // 注入依赖
  name: "NavBar",
  props: ['username'],
  data() {
    return {};
  }
  ,
  methods: {
    handleSelect(key, keyPath) {
      console.log(key)
      if (key === "login") {
        this.$router.push("/" + keyPath)
      }
      if (key === "5-1") {
        this.logout()
      }
      if (key === "1") {
        this.$router.push("/")
      }
    },
    logout() {
      sessionStorage.clear()
      localStorage.clear()
      this.$router.go(0)
    }
  }
}
</script>

<style scoped>
.logo {
  font-size: 20px;
}

</style>