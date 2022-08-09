<template>
  <div style="display: flex; justify-content: space-around">
    <div class="left-space"></div>
    <div class="main-space">
      <HomeMainButtons />
      <MainServices />
    </div>
    <div class="right-space">
      <SideLocationBox />
    </div>
  </div>
</template>

<script>
import HomeMainButtons from "./HomeMainButtons.vue";
import MainServices from "./MainServices.vue";
import SideLocationBox from "./SideLocationBox.vue";

export default {
  name: "MainSpacing",
  components: { HomeMainButtons, MainServices, SideLocationBox },
};
</script>

<style>
.left-space {
  width: 242px;
}
.main-space {
  width: 1128px;
  padding: 30px, 20px;
}
.right-space {
  margin-top: 30px;
  padding: 30px, 20px;
  width: auto;
}
</style>
