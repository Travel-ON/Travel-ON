<template>
  <div class="main">
    <div style="flex: 1" />
    <div style="display: flex; flex-direction: column; flex: 5">
      <div class="comment">
        <span class="comment">업적을 달성하고 <br />나의 칭호를 자랑해봐요!</span>
      </div>
    </div>
    <!--    <div style="flex: 1" />-->
    <div class="image">
      <v-img class="achievementsMap" src="@/assets/achievementsMap.png" alt="업적지도" />
      <v-img class="titleList" src="@/assets/setChingHo.png" alt="업적지도" />
    </div>
    <div style="flex: 1" />
  </div>
</template>

<script>
export default {
  name: "MainScroll4",
};
</script>

<style scoped>
.main {
  background-color: #8b97ff;
  display: flex;
  width: 100vw;
  height: 100vh;
}
div.comment {
  flex: 7;
}
span.comment {
  position: absolute;
  top: 16rem;
  color: white;
  font-weight: 600;
  font-size: 4rem;
}
div.image {
  flex: 4;
}
.achievementsMap {
  top: 8rem;
  right: 8rem;
  position: absolute;
  width: 45rem;
}
.titleList {
  top: 34rem;
  left: 16rem;
  position: absolute;
  width: 20rem;
}
</style>
