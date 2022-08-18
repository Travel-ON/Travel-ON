<!-- eslint-disable vue/no-mutating-props -->
<template>
  <div style="flex: 6">
    <div class="history-detail-title">
      <div style="display: flex; align-items: center; margin-left: 20px">
        <div style="margin-right: 10px"><v-icon>mdi-check</v-icon></div>
        <div style="font-weight: bold; font-size: 28px">방문장소 확인</div>
      </div>
    </div>
    <div class="history-detail-body">
      <div class="history-detail-body-shell">
        <div style="text-align: right; font-size: 12px">
          {{
            `${new Date(plan.visitDate).getFullYear()}년 ${new Date(plan.visitDate).getMonth() + 1}월 ${new Date(
              plan.visitDate,
            ).getDate()}일 방문`
          }}
        </div>
        <div style="display: flex; align-items: center; margin-bottom: 10px">
          <div><v-icon>mdi-map-marker</v-icon></div>
          <div style="font-size: 20px; margin-left: 10px; font-weight: bold">
            {{ `${plan.sidoName} ${plan.gugunName}` }}
          </div>
        </div>
        <div class="plan-visit-place">
          {{ plan.visitedPlace }}
        </div>
        <div style="display: flex; margin: 0 20px; align-items: center">
          <div style="margin-right: 5px; color: #f5c343; font-size: 16px">
            {{ plan.ratePoint ? plan.ratePoint.toFixed(1) : "?.?" }}
          </div>
          <div v-if="plan.ratePoint">
            <v-rating
              v-model="plan.ratePoint"
              active-color="#f5c343"
              color="#979797"
              half-increments
              disabled
              readonly
              size="x-small"
            ></v-rating>
          </div>
        </div>
        <div
          style="
            border: 2px solid #efefef;
            background-color: #efefef;
            border-radius: 8px;
            padding: 20px;
            text-align: start;
            margin: 12px 20px 20px;
            line-height: 150%;
            word-break: normal;
          "
        >
          <div>{{ plan.review !== "" && plan.review ? plan.review : "작성된 리뷰가 없습니다." }}</div>
        </div>
      </div>
    </div>
    <div style="display: flex; justify-content: center">
      <v-btn
        color="#50a0f0"
        size="x-large"
        style="font-weight: bold; color: #efefef; margin-right: 30px"
        @click="switchUpdate(plan)"
        width="180px"
        >기록수정</v-btn
      >
      <v-btn
        color="#efefef"
        size="x-large"
        style="font-weight: bold; color: #50a0f0"
        @click="deletePlan()"
        width="180px"
        >기록삭제</v-btn
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
    plan: Object,
  },
  methods: {
    ...mapActions(["getPlanList"]),
    ...mapGetters(["token"]),
    switchUpdate(plan) {
      // 상위에 switchUpdate 이벤트 전달
      this.$emit("switchUpdate", plan);
    },
    deletePlan() {
      if (window.confirm("정말 이 게시글을 삭제하시겠습니까?")) {
        axios({
          url: spring.plan.delete(this.plan.visitPlaceId),
          method: "delete",
          headers: {
            Authorization: `Bearer ${this.token()}`,
          },
        })
          .then(() => {
            Toast.fire({
              icon: "success",
              title: "플랜 삭제에 성공하였습니다.",
            });
            this.getPlanList();
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
.history-detail-title {
  height: 90px;
  display: flex;
  align-items: center;
}
.history-detail-body {
  display: flex;
  justify-content: center;
  margin: 0 50px;
}
.history-detail-body-shell {
  width: 560px;
  padding: 20px 20px 0px;
  background-color: #c5dcff;
  border-radius: 12px;
  margin-bottom: 30px;
}
.plan-visit-place {
  margin: 0 20px;
  text-align: left;
  font-size: 32px;
  font-weight: bold;
  border-bottom: 2px solid #efefef;
  margin-bottom: 12px;
}
</style>
