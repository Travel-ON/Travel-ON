<template>
  <div style="display: flex; justify-content: space-between">
    <div style="flex: 3; height: 100px; margin-right: 30px">
      <TourCarousel />
      <StayCarousel />
    </div>
    <div style="flex: 2; height: 100px">
      <AchievementsMap />
    </div>
  </div>
</template>

<script>
import TourCarousel from "./TourCarousel.vue";
import StayCarousel from "./StayCarousel.vue";
import AchievementsMap from "./AchievementsMap.vue";

export default {
  name: "MainServices",
  data() {
    return {};
  },
  components: { TourCarousel, StayCarousel, AchievementsMap },
};
</script>

<style>
.v-window {
  border-radius: 16px;
  border: 5px solid #6499ff;
}
</style>
