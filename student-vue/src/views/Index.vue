<template>
  <div class="index">

    <el-container>
      <el-header class="header">
        <nav-bar :username="this.user.username"/>
      </el-header>
      <el-container class="main_container">
        <el-aside width="250px">
          <side-bar @changePath="changePath"/>
        </el-aside>
        <el-container>
          <div class="container">
            <router-view style="width: 90%"></router-view>
          </div>
        </el-container>
      </el-container>
      <el-footer class="footer">
        <Footer/>
      </el-footer>
    </el-container>
  </div>
</template>
<script>
import SideBar from "@/components/SideBar";
import navBar from "@/components/NavBar";
import Footer from "@/components/Footer";

export default {
  name: "Index",
  components: {
    SideBar,
    navBar,
    Footer
  },
  data() {
    return {
      user: {
        username: ""
      }
    }
  },
  created() {
    const user = JSON.parse(sessionStorage.getItem("user"))
    if (user !== null) {
      this.user = user
    }
    console.log(user)
  },
  methods: {
    changePath(path) {
      if (path === "1") {
        this.$router.push({path: "/"})
      } else if (path === "2") {
        this.$router.push({path: "/class"})
      } else if (path === "3") {
        this.$router.push({path: "/student"})
      }
    }
  }
}
</script>

<style scoped>
.index {
  height: calc(100% + 60px);
}

.header,
.footer {
  margin: 0;
  padding: 0;
}

* {
  height: 100%;
}

.main_container {
  height: calc(100% - 60px);
}

.container {
  /*border: 1px solid red;*/
  width: 90%;
  height: 90%;
  min-width: 800px;
  margin: auto auto;
  box-shadow: 0 2px 12px 0 rgb(0 0 0 / 10%);
  background-color: #fff;
}
</style>
