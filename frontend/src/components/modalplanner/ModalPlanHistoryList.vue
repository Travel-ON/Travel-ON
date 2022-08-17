<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<template>
  <div id="modal-plan-history-list">
    <div id="modal-plan-list">
      <div id="modal-plans">
        <div class="plan" v-for="(plan, index) in convertedHistoryListModal()" :key="index">
          <div
            v-if="typeof plan === 'string'"
            style="
              background-color: #d1e6fb;
              font-size: 18px;
              height: 40px;
              display: flex;
              justify-content: center;
              align-items: center;
            "
          >
            <span>{{
              `${new Date(plan).getFullYear()}년 ${new Date(plan).getMonth() + 1}월 ${new Date(plan).getDate()}일`
            }}</span>
          </div>
          <!-- 각 요소 클릭시, 우측 컴포넌트 전환: 열람 뷰 -->
          <div
            v-else
            style="display: flex; borderbottom: 1px solid #ddd; cursor: pointer; backgroundcolor: #efefef; height: 40px"
            @click="switchDetail(plan)"
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
  props: {
    username: String,
  },
  data: () => ({
    planStyle: {
      display: "flex",
      borderBottom: "1px solid #ddd",
      cursor: "pointer",
      backgroundColor: "#efefef",
      height: "40px",
    },
  }),
  methods: {
    ...mapGetters(["token", "planHistoryListModal", "convertedHistoryListModal"]),
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
    this.getPlanListModal(this.username);
  },
};
</script>
<style>
#modal-plan-history-list {
  flex: 1;
  border-right: 2px solid #adadad;
}
#modal-plan-list {
  width: 100%;
  height: 628px;
  overflow-y: scroll;
  -ms-overflow-style: none;
  max-height: 628px;
  border: 2px dashed #adadad;
  background-color: #efefef;
  border-radius: 0px 0px 0px 32px;
  box-sizing: border-box;
}
#modal-plan-list::-webkit-scrollbar {
  display: none;
}
#modal-plans {
  height: 550px;
}
</style>
