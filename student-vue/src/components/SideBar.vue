<template>
  <div class="home">
    <el-row>
      <el-col>
        <el-menu background-color="#FAFAFA" default-active="1" @select="handleSelect">
          <el-menu-item index="1">
            <i class="el-icon-s-home"></i>
            <span slot="title">首页</span>
          </el-menu-item>
          <el-menu-item index="2">
            <i class="el-icon-s-management"></i>
            <span slot="title">班级管理系统</span>
          </el-menu-item>
          <el-menu-item index="3">
            <i class="el-icon-s-custom"></i>
            <span slot="title">学生管理系统</span>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "SideBar",
  methods: {
    handleSelect(key, keyPath) {
      this.$emit("changePath", key);
    },
  }
}
</script>

<style scoped>
.home {
  width: 100%;
}

</style>
