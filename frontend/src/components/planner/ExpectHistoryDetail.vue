<!-- eslint-disable vue/no-mutating-props -->
<template>
  <div style="flex: 6">
    <div class="expect-history-detail-title">
      <div style="display: flex; align-items: center; margin-left: 20px">
        <div style="margin-right: 10px"><v-icon>mdi-plus-circle-outline</v-icon></div>
        <div style="font-weight: bold; font-size: 28px">일정 상세</div>
      </div>
    </div>
    <div class="expect-history-detail-body">
      <img
        src="https://user-images.githubusercontent.com/97648026/184546386-33f45530-dbf1-4188-b738-fee8c55d6eec.png"
        style="width: 560px; height: 480px"
        alt="notePad"
      />
      <div class="expect-history-detail-body-shell">
        <div style="text-align: right; font-size: 24px; font-weight: bold">
          <div :style="`color: ${convertDday(expect.expectedDate) > 0 ? '#7a64ff' : '#ff5151'}; z-index: 2`">
            {{ `D-${convertDday(expect.expectedDate) > 0 ? convertDday(expect.expectedDate) : "DAY"}` }}
          </div>
        </div>
        <div style="display: flex; align-items: center; margin-bottom: 10px">
          <div><v-icon>mdi-calendar-text</v-icon></div>
          <div style="font-size: 20px; margin-left: 10px; font-weight: bold">방문 예정 날짜</div>
        </div>
        <div class="expect-place">
          <div style="margin-left: 24px">
            {{
              `${new Date(expect.expectedDate).getFullYear()}. ${
                new Date(expect.expectedDate).getMonth() + 1
              }. ${new Date(expect.expectedDate).getDate()}`
            }}
          </div>
        </div>
        <div style="display: flex; align-items: center; margin-bottom: 10px">
          <div><v-icon>mdi-store-marker</v-icon></div>
          <div style="font-size: 20px; margin-left: 10px; font-weight: bold">방문 예정 장소</div>
        </div>
        <div class="expect-place">
          <div style="margin-left: 24px">
            <span style="font-size: 20px; font-weight: normal">{{ `${expect.sidoName} ` }}</span>
            <span style="font-size: 20px; font-weight: normal">{{ `${expect.gugunName} ` }}</span>
            <span>{{ `${expect.expectedPlace}` }}</span>
          </div>
        </div>
      </div>
    </div>
    <div style="display: flex; justify-content: center">
      <v-btn
        color="#50a0f0"
        size="x-large"
        style="font-weight: bold; color: #efefef; margin-right: 30px"
        @click="switchUpdate(expect)"
        width="180px"
        >방문확인</v-btn
      >
      <v-btn
        color="#efefef"
        size="x-large"
        style="font-weight: bold; color: #50a0f0"
        @click="deleteExpect()"
        width="180px"
        >일정삭제</v-btn
      >
    </div>
  </div>
</template>

<script>
import axios from "axios";
import spring from "@/api/spring_boot";
import { mapGetters, mapActions } from "vuex";

export default {
  data: () => ({}),
  props: {
    expect: Object,
  },
  methods: {
    ...mapActions(["getExpectList"]),
    ...mapGetters(["token"]),
    convertDday(day) {
      return parseInt(Math.ceil((new Date(day).getTime() - new Date()) / (1000 * 60 * 60 * 24)), 10);
    },
    switchUpdate() {
      if (window.confirm("방문 기록으로 옮기시겠습니까?")) {
        axios({
          url: spring.plan.modifyExpect(this.expect.visitExpectedId),
          method: "put",
          headers: {
            Authorization: `Bearer ${this.token()}`,
          },
        })
          .then((res) => {
            Toast.fire({
              icon: "success",
              title: "방문 기록으로 옮기기 성공하였습니다.",
            });
            console.log(res);
            this.getExpectList();
          })
          .catch((err) => {
            Toast.fire({
              icon: "error",
              title: "방문 기록으로 옮기기 실패하였습니다.",
            });
            console.log(err);
          });
      }
    },
    deleteExpect() {
      if (window.confirm("정말 이 게시글을 삭제하시겠습니까?")) {
        axios({
          url: spring.plan.deleteExpect(this.expect.visitExpectedId),
          method: "delete",
          headers: {
            Authorization: `Bearer ${this.token()}`,
          },
        })
          .then((res) => {
            Toast.fire({
              icon: "success",
              title: "플랜 삭제에 성공하였습니다.",
            });
            console.log(res);
            this.getExpectList();
            this.$emit("deleted");
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
  },
};
</script>

<style>
.expect-history-detail-title {
  height: 90px;
  display: flex;
  align-items: center;
}
.expect-history-detail-body {
  position: relative;
  justify-content: center;
  margin: 0 50px;
}
.expect-history-detail-body-shell {
  position: absolute;
  width: 560px;
  padding: 20px 56px 0;
  border-radius: 12px;
  margin-bottom: 30px;
  top: 8px;
  left: 16px;
}
.expect-place {
  margin: 0 20px;
  text-align: left;
  font-size: 32px;
  font-weight: bold;
  border-bottom: 2px solid #000;
  margin-left: 24px;
  margin-bottom: 24px;
}
</style>
