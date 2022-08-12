<template>
  <div class="container">
    <v-btn height="120px" z-index="0" @click="hostBtn">
      <div>
        <div style="font-weight: bold; font-size: large">방만들기</div>
        <div>직접 방을 생성해</div>
        <div>사람들을 만나보세요!</div>
      </div>
    </v-btn>
    <v-btn height="120px" z-index="0" @click="guestBtn">
      <div>
        <div style="font-weight: bold; font-size: large">방매칭하기</div>
        <div>이미 만들어진 방에 들어가</div>
        <div>사람들을 만나보세요!</div>
      </div>
    </v-btn>
    <v-btn height="120px" z-index="0">
      <div style="line-height: 150%">
        <div style="font-weight: bold; font-size: large">여행플래너 작성하기</div>
        <div>여행을 하며 방문한 장소와</div>
        <div>가보고 싶은 장소를 기록해보세요!</div>
      </div>
    </v-btn>
  </div>
</template>

<script>
import { mapActions } from "vuex";

export default {
  name: "HomeMainButtons",
  data() {
    return {};
  },
  methods: {
    ...mapActions("MeetingStore", ["createSessionId"]),
    // eslint-disable-next-line consistent-return
    hostBtn() {
      this.$router.push({ name: "CreateMeeting" });
    },
    // 방참여(EnterMeeting) 가는 버튼 보류
    guestBtn() {},
  },
};
</script>

<style>
.container {
  display: flex;
  margin-bottom: 30px;
  margin-top: 30px;
}
.container > button {
  line-height: 150%;
  text-align: left;
  flex: 1;
  margin: 0 10px;
  padding: 20px;
  height: 120px;
  border: 3px solid #c9deff;
  border-radius: 18px;
}
</style>
