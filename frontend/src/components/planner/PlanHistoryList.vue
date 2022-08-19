<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<template>
  <div id="plan-history-list">
    <div id="filter-btn">
      <!-- 클릭시, 우측 컴포넌트 전환: 필터 뷰 -->
      <v-btn append-icon="mdi-filter-outline" style="background-color: #efefef" rounded="pill" @click="switchFilter()"
        >필터</v-btn
      >
    </div>
    <div id="plan-add-btn">
      <!-- 클릭시, 우측 컴포넌트 전환: 작성 뷰 -->
      <v-btn
        prepend-icon="mdi-plus"
        width="100%"
        size="x-large"
        height="64px"
        style="font-size: 20px; font-weight: bold; border: 2px dashed #adadad; background-color: #efefef"
        @click="switchCreate()"
        >방문장소 추가</v-btn
      >
    </div>
    <div id="plan-list">
      <div id="plans">
        <div class="plan" v-for="(plan, index) in convertedHistoryList()[page - 1]" :key="index">
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
              <div style="margin-right: 5px; color: #f5c343; font-size: 16px"><v-icon>mdi-pencil-outline</v-icon></div>
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
      <div id="plan-pagination">
        <v-pagination
          v-model="page"
          :length="convertedHistoryList().length"
          color="#979797"
          active-color="#f5c343"
        ></v-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters, mapActions } from "vuex";

export default {
  data: () => ({
    page: 1, // 페이지네이션 변수
    planStyle: {
      display: "flex",
      borderBottom: "1px solid #ddd",
      cursor: "pointer",
      backgroundColor: "#efefef",
      height: "27px",
    },
  }),
  methods: {
    ...mapGetters(["token", "planHistoryList", "convertedHistoryList"]),
    ...mapActions(["getPlanList"]),
    switchCreate() {
      // 상위에 switchCreate 이벤트 전달
      this.$emit("switchCreate");
    },
    switchDetail(plan) {
      // 상위에 switchDetail 이벤트 전달
      this.$emit("switchDetail", plan);
    },
    switchUpdate() {
      // 상위에 switchUpdate 이벤트 전달
      this.$emit("switchUpdate");
    },
    switchFilter() {
      // 상위에 switchFilter 이벤트 전달
      this.$emit("switchFilter");
    },
    changebgcolor() {
      this.planStyle.backgroundColor = "#aaa";
    },
    originalcolor() {
      this.planStyle.backgroundColor = "#efefef";
    },
  },
  mounted() {
    this.getPlanList();
  },
};
</script>

<style>
#plan-history-list {
  flex: 5;
  border-right: 2px solid #adadad;
  margin-top: 20px;
  padding: 0 20px 0 0;
}
#filter-btn {
  margin-bottom: 12px;
  text-align: right;
}
#plan-add-btn {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 12px;
}
#plan-list {
  width: 100%;
  height: 660px;
  border: 2px dashed #adadad;
  background-color: #efefef;
  border-radius: 8px;
}
#plans {
  height: 550px;
}
#plan-pagination {
  margin-top: 30px;
}
.plan {
  width: 100%;
  font-size: 12px;
}
.plan-address {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
}
.plan-name {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
}
.plan-rating {
  flex: 2;
  display: flex;
  align-items: center;
  justify-content: center;
}
.plan-style {
  height: 27px;
}
</style>
