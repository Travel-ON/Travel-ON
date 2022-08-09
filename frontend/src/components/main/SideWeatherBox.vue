<template>
  <div style="height: 300px; width: 242px; border-radius: 8px; margin-top: 32px">
    <div
      style="
        height: 50px;
        background-color: #2f2f2f;
        color: #fff;
        border-radius: 8px 8px 0 0;
        display: flex;
        font-size: 16px;
      "
    >
      <div style="margin: auto">오늘의 {{ sido !== "" ? sido : "서울" }} 날씨는</div>
    </div>
    <div style="height: 250px; background-color: #acf; color: #fff; border-radius: 0 0 8px 8px">
      <div style="height: 175px">
        <v-img :src="'http://openweathermap.org/img/wn/' + icon + '@2x.png'"></v-img>
      </div>
      <div style="height: 75px; font-size: 48px; font-weight: bold">{{ temp + "ºC" }}</div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  computed: {
    ...mapGetters(["sido", "latitude", "longitude", "temp", "icon"]),
  },
  methods: {
    ...mapActions(["getWeather"]),
  },
  mounted() {
    this.getWeather();
  },
};
</script>

<style></style>
