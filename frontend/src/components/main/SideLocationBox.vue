<template>
  <div
    v-if="isLocation"
    style="
      width: 242px;
      height: 120px;
      background-color: #3e4e6c;
      border-radius: 8px;
      position: relative;
      display: flex;
      margin-bottom: 32px;
    "
  >
    <div
      style="position: absolute; width: 32px; height: 32px; border-radius: 16px; margin: 6px; background-color: #2bd36e"
    >
      <v-icon color="#fff">mdi-check</v-icon>
    </div>
    <div style="margin: auto; padding-top: 10px; color: #fff; text-align: left; font-size: 14px">
      <div>현재 접속중인 지역은</div>
      <div>
        <span style="color: #f4a84d">{{ sido + " " }} </span><span style="color: #03dac5">{{ gugun + " " }} </span
        ><span style="color: #b1e3ff">{{ dong }} </span>입니다.
      </div>
    </div>
  </div>
  <div
    v-else
    style="
      width: 242px;
      height: 176px;
      background-color: #3e4e6c;
      border-radius: 8px;
      position: relative;
      display: flex;
    "
  >
    <div
      style="position: absolute; width: 32px; height: 32px; border-radius: 16px; margin: 6px; background-color: #f93c3c"
    >
      <v-icon color="#fff">mdi-close</v-icon>
    </div>
    <div style="margin: auto; color: #fff; text-align: left; font-size: 14px; height: 80px">
      <div>위치 인증이 되지 않았습니다.</div>
      <div>다양한 서비스 제공을 위해</div>
      <div>위치인증 버튼을 눌러주세요.</div>
    </div>
    <div style="position: absolute; bottom: 12px; left: 50%; transform: translate(-50%, 0)">
      <v-btn rounded="pill" bg-color="#fff" @click="getLocation()" :disabled="!isLoggedIn"
        ><span style="font-weight: bold">위치인증하기</span></v-btn
      >
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  data() {},
  computed: {
    ...mapGetters(["isLocation", "dong", "gugun", "sido", "isLoggedIn"]),
  },
  methods: {
    ...mapActions(["getLocation"]),
  },
};
</script>

<style></style>
