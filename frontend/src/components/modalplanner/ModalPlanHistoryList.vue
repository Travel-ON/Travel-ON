<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<template>
  <div id="plan-history-list">
    <div id="plan-list">
      <div id="plans">
        <div class="plan" v-for="(plan, index) in convertedHistoryListModal()" :key="index">
          <div v-if="typeof plan === 'string'" style="background-color: #d1e6fb; font-size: 18px; height: 27px">
            <span>{{
              `${new Date(plan).getFullYear()}년 ${new Date(plan).getMonth() + 1}월 ${new Date(plan).getDate()}일`
            }}</span>
          </div>
          <!-- 각 요소 클릭시, 우측 컴포넌트 전환: 열람 뷰 -->
          <div
            v-else
            :style="planStyle"
            @click="switchDetail(plan)"
            :mouseover="changebgcolor"
            :mouseout="originalcolor"
          >
            <div class="plan-address">
              <div>{{ `${plan.sidoName} ${plan.gugunName}` }}</div>
            </div>
            <div class="plan-name">
              <div>{{ plan.visitedPlace }}</div>
            </div>
            <div v-if="plan.ratePoint" class="plan-rating">
              <div style="margin-right: 5px; color: #f5c343; font-size: 16px">{{ plan.ratePoint.toFixed(1) }}</div>
              <div>
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
            <div v-else class="plan-rating">
              <div style="margin-right: 5px; color: #f5c343; font-size: 16px">?.?</div>
              <div>
                <v-rating
                  value="0"
                  active-color="#f5c343"
                  color="#979797"
                  half-increments
                  disabled
                  readonly
                  size="x-small"
                ></v-rating>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  data: () => ({
    planStyle: {
      display: "flex",
      borderBottom: "1px solid #ddd",
      cursor: "pointer",
      backgroundColor: "#efefef",
      height: "27px",
    },
  }),
  methods: {
    ...mapGetters(["token", "planHistoryList", "convertedHistoryListModal"]),
    ...mapActions(["getPlanListModal"]),
    switchDetail(plan) {
      // 상위에 switchDetail 이벤트 전달
      this.$emit("switchDetail", plan);
    },
    changebgcolor() {
      this.planStyle.backgroundColor = "#aaa";
    },
    originalcolor() {
      this.planStyle.backgroundColor = "#efefef";
    },
  },
  mounted() {
    this.getPlanListModal();
  },
};
</script>
<style></style>
