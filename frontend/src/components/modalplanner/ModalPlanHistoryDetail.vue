<!-- eslint-disable vue/no-mutating-props -->
<template>
  <div style="flex: 1">
    <div class="modal-history-detail-body">
      <div class="modal-history-detail-body-shell">
        <div style="text-align: right; font-size: 12px; margin-bottom: 24px">
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
        <div class="modal-plan-visit-place">
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
            border-radius: 4px;
            padding: 20px;
            text-align: start;
            margin: 12px 20px 20px;
            line-height: 150%;
            word-break: normal;
            background-image: linear-gradient(to right, white 10px, transparent 10px),
              linear-gradient(to left, white 10px, transparent 10px),
              repeating-linear-gradient(white, white 30px, #ccc 30px, #ccc 31px, white 31px);
          "
        >
          <div>{{ plan.review !== "" && plan.review ? plan.review : "작성된 리뷰가 없습니다." }}</div>
        </div>
      </div>
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
          .then((res) => {
            alert("플랜 삭제 성공하였습니다.");
            console.log(res);
            this.getPlanList();
            this.$emit("deleted");
          })
          .catch((err) => {
            alert("플랜 삭제 실패하였습니다.");
            console.log(err);
          });
      }
    },
  },
};
</script>

<style>
.modal-history-detail-body {
  width: 100%;
  display: flex;
  justify-content: center;
}
.modal-history-detail-body-shell {
  width: 360px;
  padding: 20px 20px 0px;
  margin-bottom: 30px;
}
.modal-plan-visit-place {
  margin: 0 20px;
  text-align: left;
  font-size: 32px;
  font-weight: bold;
  border-bottom: 2px solid #efefef;
  margin-bottom: 12px;
}
</style>
