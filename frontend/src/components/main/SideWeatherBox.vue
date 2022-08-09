<template>
  <div style="height: 300px; width: 242px; border-radius: 8px; margin-top: 32px">
    <div style="height: 50px; background-color: #2f2f2f; color: #fff; border-radius: 8px 8px 0 0; display: flex">
      <div style="margin: auto">오늘의 {{ sido !== "" ? sido : "서울" }} 날씨는</div>
    </div>
    <div style="height: 250px; background-color: #acf; color: #fff; border-radius: 0 0 8px 8px">
      <div style="height: 100px"></div>
      <div style="height: 50px"></div>
      <div style="height: 100px"></div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  computed: {
    ...mapGetters(["sido", "latitude", "longitude"]),
  },
};
</script>

<style></style>
