<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<template>
  <div class="expect-history-list">
    <div class="expect-list">
      <div class="expects">
        <div class="expect" v-for="(expect, index) in expectHistoryListModal()" :key="index">
          <div :style="expectStyle">
            <div class="expect-address">
              <div>{{ `${expect.sidoName} ${expect.gugunName}` }}</div>
            </div>
            <div class="expect-name">
              <div>{{ expect.expectedPlace }}</div>
            </div>
            <div class="expect-date">
              <div>
                {{
                  `${new Date(expect.expectedDate).getFullYear()}년 ${
                    new Date(expect.expectedDate).getMonth() + 1
                  }월 ${new Date(expect.expectedDate).getDate()}일`
                }}
              </div>
            </div>
            <div class="expect-d-day">
              <div
                :style="`color: ${
                  convertDday(expect.expectedDate) > 0
                    ? '#7a64ff'
                    : convertDday(expect.expectedDate) < 0
                    ? '#000'
                    : '#ff5151'
                }`"
              >
                {{
                  convertDday(expect.expectedDate) > 0
                    ? `D-${convertDday(expect.expectedDate)}`
                    : convertDday(expect.expectedDate) < 0
                    ? `D+${convertDday(expect.expectedDate) * -1}`
                    : "D-DAY"
                }}
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
    page: 1, // 페이지네이션 변수
    expectStyle: {
      display: "flex",
      borderBottom: "1px solid #ddd",
      height: "40px",
    },
  }),
  methods: {
    convertDday(day) {
      return parseInt(Math.ceil((new Date(day).getTime() - new Date()) / (1000 * 60 * 60 * 24)), 10);
    },
    ...mapGetters(["token", "expectHistoryListModal"]),
    ...mapActions(["getExpectListModal"]),
    changebgcolor() {
      this.expectStyle.backgroundColor = "#aaa";
    },
    originalcolor() {
      this.expectStyle.backgroundColor = "#efefef";
    },
  },
  mounted() {
    this.getExpectListModal(this.username);
  },
};
</script>

<style>
.expect-history-list {
  flex: 5;
  margin-top: 20px;
  padding: 0 20px 0 0;
}
.expect-list {
  width: 100%;
  border-bottom: 2px solid #d1e6fb;
}
.expects {
  height: 550px;
}
.expect {
  width: 100%;
  font-size: 12px;
}
.expect-address {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: start;
  margin-left: 12px;
  font-size: 14px;
}
.expect-name {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: start;
  font-weight: bold;
  font-size: 24px;
}
.expect-date {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: start;
  font-weight: bold;
  font-size: 16px;
}
.expect-d-day {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  font-weight: bold;
}
</style>
