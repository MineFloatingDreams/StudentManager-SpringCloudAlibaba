<template>
  <div class="footer">
    <el-row class="footer_context">
      <el-col :span="4">
        <div class="grid-content">
          <a href=""> 关于我们</a></div>
      </el-col>
      <el-col :span="1">|</el-col>
      <el-col :span="4">
        <div class="grid-content"><a href="">法律声明及隐私权政策</a></div>
      </el-col>
      <el-col :span="1">|</el-col>
      <el-col :span="4">
        <div class="grid-content"><a href="">Cookies政策</a></div>
      </el-col>
      <el-col :span="1">|</el-col>
      <el-col :span="4">
        <div class="grid-content"><a href="">廉正举报</a></div>
      </el-col>
      <el-col :span="1">|</el-col>
      <el-col :span="4">
        <div class="grid-content"><a href="">联系我们</a></div>
      </el-col>
    </el-row>

  </div>

</template>

<script>
export default {
  name: "Footer"
}
</script>

<style scoped>
.footer {
  background-color: #252525;
  color: #FFFFFF;
  height: 100%;
  text-align: center;
  color: #9B9EA0;
}

.footer_context {
  padding-top: 20px;
}

a {
  text-decoration: none;
  color: #9B9EA0;
}
a:hover {
  text-decoration: none;
  color: #409EFF;
}

</style>