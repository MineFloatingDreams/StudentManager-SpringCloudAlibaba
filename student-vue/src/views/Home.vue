<template>
  <div class="animate">
    <kinesis-container easing="cubic-bezier(0.23, 1, 0.32, 1)" class="head">

      <h1>
        <kinesis-element type="rotate" :originX="50" :strength="20">欢</kinesis-element>
        <kinesis-element style="margin-top: -10px" :originY="20">迎</kinesis-element>
        <kinesis-element type="translate" :originY="50" :strength="20">使</kinesis-element>
        <kinesis-element type="rotate" :strength="-20" style="margin-top: 10px" :originX="100">用</kinesis-element>
      </h1>
      <h1>
        <kinesis-element type="rotate" :originX="50" :strength="-20">学</kinesis-element>
        <kinesis-element :originX="80" :strength="-10" style="margin-top: -10px">生</kinesis-element>
        <kinesis-element :strength="30">管</kinesis-element>
        <kinesis-element :originY="40" style="margin-top: 10px">理</kinesis-element>
        <kinesis-element type="rotate" :originX="50" :strength="20">系</kinesis-element>
        <kinesis-element style="margin-top: -10px">统</kinesis-element>
      </h1>
      <kinesis-element class="circle circle-yellow" :strength="-30">
      </kinesis-element>
      <kinesis-element class="circle circle-purple" :strength="-50">
      </kinesis-element>
      <kinesis-element class="circle circle-green" :strength="40">
      </kinesis-element>
      <kinesis-element class="circle circle-orange" :strength="20">
      </kinesis-element>
    </kinesis-container>
  </div>

</template>

<script>
export default {
  name: "Home"
}
</script>

<style scoped>


.head {
  font-size: 50px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: relative;
  height: 100%;
  width: 100%;
}

.circle-yellow {
  background-color: #faf9ea;
  width: 8vw;
  height: 8vw;
  left: 30%;
  top: 15%;
}

.circle-purple {
  border: 25px solid #f2eafa;
  width: 16vw;
  height: 16vw;
  left: 70%;
}

.circle-orange {
  border: 25px solid #faf3ea;
  width: 8vw;
  height: 8vw;
  left: 20%;
  top: 60%;
}

.circle-green {
  background-color: #eafaf2;
  width: 10vw;
  height: 10vw;
  right: 20%;
  top: 10%;
}

.circle {
  border-radius: 100%;
  position: absolute;
  z-index: 0;
}

h1 {
  z-index: 1;
  position: relative;
  display: flex;

}
h1>div{
  margin-left: 10px;
}
</style>