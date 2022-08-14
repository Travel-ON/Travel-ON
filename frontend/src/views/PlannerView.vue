<!-- eslint-disable vue/no-parsing-error -->
<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<template>
  <div style="display: flex; justify-content: space-between">
    <div class="plan-left-space"></div>
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
      <div style="position: absolute; display: flex; flex-direction: column; top: 78px; left: -182px">
        <div style="display: flex; margin-bottom: 24px">
          <div style="height: 64px; width: 36px; background-color: #78a0dc; border-radius: 16px 0 0 16px"></div>
          <div
            style="
              height: 64px;
              width: 144px;
              background-color: #efefef;
              display: flex;
              align-items: center;
              justify-content: center;
              border-top: 2px solid #adadad;
              border-bottom: 2px solid #adadad;
            "
          >
            <div style="font-size: 24px">여행플래너</div>
          </div>
        </div>
        <div style="display: flex; margin-bottom: 24px">
          <div
            :style="`
              height: 64px;
              width: 144px;
              background-color: ${selectedPage === 0 ? '#78a0dc' : '#c5dcff'};
              display: flex;
              align-items: center;
              justify-content: center;
              border-radius: 16px 0 0 16px;
              margin-left: 36px;
              font-weight: bold;
              cursor: pointer;
              color: ${selectedPage === 0 ? '#fff' : '#020715'};
            `"
            @click="selectedPage = 0"
          >
            <div style="font-size: 20px">방문한 장소</div>
          </div>
        </div>
        <div style="display: flex; margin-bottom: 24px">
          <div
            :style="`
              height: 64px;
              width: 144px;
              background-color: ${selectedPage === 1 ? '#78a0dc' : '#c5dcff'};
              display: flex;
              align-items: center;
              justify-content: center;
              border-radius: 16px 0 0 16px;
              margin-left: 36px;
              font-weight: bold;
              cursor: pointer;
              color: ${selectedPage === 1 ? '#fff' : '#020715'};
              `"
            @click="selectedPage = 1"
          >
            <div style="font-size: 20px">방문 예정 장소</div>
          </div>
        </div>
      </div>
      <div>
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
          <div>
            <h1 v-if="selectedPage === 0">방문한 장소</h1>
            <h1 v-else-if="selectedPage === 1">방문 예정 장소</h1>
          </div>
        </div>
        <div class="plan-content">
          <!-- 좌측 출력 -->
          <PlanHistoryList
            v-if="selectedPage === 0"
            @switch-create="switchCreate()"
            @switch-detail="switchDetail"
            @switch-filter="switchFilter()"
          />
          <ExpectHistoryList
            v-else-if="selectedPage === 1"
            @switch-create="switchCreate()"
            @switch-detail="switchDetail"
            @switch-filter="switchFilter()"
          />
          <!-- 우측 출력 -->
          <PlanHistoryCreate v-if="selectedPage === 0 && selectedView === 0" />
          <PlanHistoryDetail
            v-else-if="selectedPage === 0 && selectedView === 1"
            :plan="planDetail"
            @switch-update="switchUpdate"
            @deleted="selectedView = 0"
          />
          <PlanHistoryUpdate v-else-if="selectedPage === 0 && selectedView === 2" :plan="planDetail" />
          <PlanHistoryFilter v-else-if="selectedPage === 0 && selectedView === 3" />
          <ExpectHistoryCreate v-else-if="selectedPage === 1 && selectedView === 0" />
          <ExpectHistoryDetail
            v-else-if="selectedPage === 1 && selectedView === 1"
            :expect="planDetail"
            @deleted="selectedView = 0"
          />
          <ExpectHistoryFilter v-else-if="selectedPage === 1 && selectedView === 3" />
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
import PlanHistoryFilter from "@/components/planner/PlanHistoryFilter.vue";
import PlanHistoryUpdate from "@/components/planner/PlanHistoryUpdate.vue";
import ExpectHistoryList from "@/components/planner/ExpectHistoryList.vue";
import ExpectHistoryCreate from "@/components/planner/ExpectHistoryCreate.vue";
import ExpectHistoryDetail from "@/components/planner/ExpectHistoryDetail.vue";
import ExpectHistoryFilter from "@/components/planner/ExpectHistoryFilter.vue";

export default {
  name: "PlannerView",
  data: () => ({
    selectedPage: 0, // 0: 방문 장소, 1: 방문 예정 장소
    selectedView: 0, // 0: 작성뷰, 1: 열람뷰, 2: 수정뷰, 3: 필터뷰
    planDetail: {}, // 우측 뷰에서 참조하는 플랜 저장
  }),
  components: {
    PlanHistoryCreate,
    PlanHistoryList,
    PlanHistoryDetail,
    PlanHistoryFilter,
    PlanHistoryUpdate,
    ExpectHistoryList,
    ExpectHistoryCreate,
    ExpectHistoryDetail,
    ExpectHistoryFilter,
  },
  methods: {
    switchCreate() {
      this.selectedView = 0;
    },
    switchDetail(plan) {
      this.planDetail = plan;
      this.selectedView = 1;
    },
    switchUpdate(plan) {
      this.planDetail = plan;
      this.selectedView = 2;
    },
    switchFilter() {
      this.selectedView = 3;
    },
  },
  watch: {
    selectedPage() {
      this.selectedView = 0;
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
