<template>
  <div style="height: 550px; width: 100%; background-color: #6499ff; border-radius: 18px">
    <div
      style="
        height: 60px;
        width: 100%;
        font-size: 24px;
        color: white;
        display: flex;
        align-items: center;
        justify-content: center;
      "
    >
      <div>내가 여행가본 지역들은? - 업적</div>
    </div>
    <div style="height: 490px; width: 100%; border: 4px solid #6499ff; border-radius: 18px">
      <KoreaMap />
    </div>
  </div>
</template>

<script>
import KoreaMap from "./KoreaMap.vue";

export default {
  name: "AchievementsMap",
  components: {
    KoreaMap,
  },
};
</script>

<style></style>
