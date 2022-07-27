<template>
  <div style="display: flex; justify-content: space-between">
    <div class="left-space"></div>
    <div class="main-space">
      <HomeMainButtons />
    </div>
    <div class="right-space"></div>
  </div>
</template>

<script>
import HomeMainButtons from "./HomeMainButtons.vue";

export default {
  name: "MainSpacing",
  components: { HomeMainButtons },
};
</script>

<style>
.main-space {
  width: 1128px;
  padding: 30px, 20px;
  background-color: skyblue;
}
</style>
