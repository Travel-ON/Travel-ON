<template>
  <div style="display: flex; justify-content: space-between">
    <div class="left-space"></div>
    <div class="plan-main-space">
      <div
        :style="`position: absolute; right: -20px; top: ${40 + 50 * item}px`"
        v-for="item in [0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17]"
        :key="item"
      >
        <v-img
          src="https://user-images.githubusercontent.com/97648026/183853221-9475cb02-66dc-4b69-abef-dc280908b172.png"
          height="30"
          width="50"
        ></v-img>
      </div>
      <div v-if="selectedPage === 0">
        <div
          style="
            height: 80px;
            width: 100%;
            border-bottom: 2px solid #adadad;
            display: flex;
            justify-content: center;
            align-items: center;
          "
        >
          <div><h1>방문한 장소</h1></div>
        </div>
        <div class="plan-content">
          <PlanHistoryList v-if="selectedPage === 0" @switch-create="switchCreate()" @switch-detail="switchDetail" />
          <PlanHistoryCreate v-if="selectedView === 0" />
          <PlanHistoryDetail v-else-if="selectedView === 1" :plan="planDetail" />
        </div>
      </div>
    </div>
    <div class="right-space"></div>
  </div>
</template>

<script>
import PlanHistoryCreate from "@/components/planner/PlanHistoryCreate.vue";
import PlanHistoryList from "@/components/planner/PlanHistoryList.vue";
import PlanHistoryDetail from "@/components/planner/PlanHistoryDetail.vue";

export default {
  name: "PlannerView",
  data: () => ({
    selectedPage: 0, // 0: 방문 장소, 1: 방문 예정 장소
    selectedView: 0, // 0: 작성뷰, 1: 열람뷰, 2: 수정뷰
    planDetail: {},
  }),
  components: { PlanHistoryCreate, PlanHistoryList, PlanHistoryDetail },
  methods: {
    switchCreate() {
      this.selectedView = 0;
    },
    switchDetail(plan) {
      this.planDetail = plan;
      this.selectedView = 1;
    },
    switchUpdate() {
      this.selectedView = 2;
    },
  },
};
</script>

<style>
.plan-main-space {
  width: 1128px;
  height: 966px;
  padding: 30px 50px 30px 30px;
  background-color: #fff4e4;
  position: relative;
  border: 2px solid #adadad;
}
.plan-content {
  display: flex;
  height: 100%;
}
</style>
