<template>
  <div style="display: flex; justify-content: space-between">
    <div class="left-space"></div>
    <div class="main-space">
      <div style="position: absolute; right: -20px; top: 40px">
        <v-img
          src="https://user-images.githubusercontent.com/97648026/183853221-9475cb02-66dc-4b69-abef-dc280908b172.png"
          height="30"
          width="50"
        ></v-img>
      </div>
      <h1>This is main space</h1>
    </div>
    <div class="right-space"></div>
  </div>
</template>

<script>
export default {
  name: "PlannerView",
};
</script>

<style>
.main-space {
  width: 1128px;
  height: 966px;
  padding: 30px;
  background-color: #fff4e4;
  position: relative;
}
</style>
