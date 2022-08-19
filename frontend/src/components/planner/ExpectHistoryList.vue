<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<template>
  <div id="expect-history-list">
    <div id="filter-btn">
      <!-- 클릭시, 우측 컴포넌트 전환: 필터 뷰 -->
      <v-btn append-icon="mdi-filter-outline" style="background-color: #efefef" rounded="pill" @click="switchFilter()"
        >필터</v-btn
      >
    </div>
    <div id="expect-add-btn">
      <!-- 클릭시, 우측 컴포넌트 전환: 작성 뷰 -->
      <v-btn
        prepend-icon="mdi-plus"
        width="100%"
        size="x-large"
        height="64px"
        style="font-size: 20px; font-weight: bold; border: 2px dashed #adadad; background-color: #efefef"
        @click="switchCreate()"
        >방문예정장소 추가</v-btn
      >
    </div>
    <div id="expect-list">
      <div id="expects">
        <div class="expect" v-for="(expect, index) in convertedExpectHistoryList()[page - 1]" :key="index">
          <div v-if="typeof expect === 'string'" style="background-color: #d1e6fb; font-size: 18px; height: 27px">
            <span>{{
              `${new Date(expect).getFullYear()}년 ${new Date(expect).getMonth() + 1}월 ${new Date(expect).getDate()}일`
            }}</span>
          </div>
          <!-- 각 요소 클릭시, 우측 컴포넌트 전환: 열람 뷰 -->
          <div
            v-else
            :style="expectStyle"
            @click="switchDetail(expect)"
            :mouseover="changebgcolor"
            :mouseout="originalcolor"
          >
            <div class="expect-address">
              <div>{{ `${expect.sidoName} ${expect.gugunName}` }}</div>
            </div>
            <div class="expect-name">
              <div style="font-size: 18px">{{ expect.expectedPlace }}</div>
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
      <div id="expect-pagination">
        <v-pagination
          v-model="page"
          :length="convertedExpectHistoryList().length"
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
    expectStyle: {
      display: "flex",
      borderBottom: "1px solid #ddd",
      cursor: "pointer",
      backgroundColor: "#efefef",
      height: "27px",
    },
  }),
  methods: {
    convertDday(day) {
      return parseInt(Math.ceil((new Date(day).getTime() - new Date()) / (1000 * 60 * 60 * 24)), 10);
    },
    ...mapGetters(["token", "expectHistoryList", "convertedExpectHistoryList"]),
    ...mapActions(["getExpectList"]),
    switchCreate() {
      // 상위에 switchCreate 이벤트 전달
      this.$emit("switchCreate");
    },
    switchDetail(expect) {
      // 상위에 switchDetail 이벤트 전달
      this.$emit("switchDetail", expect);
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
      this.expectStyle.backgroundColor = "#aaa";
    },
    originalcolor() {
      this.expectStyle.backgroundColor = "#efefef";
    },
  },
  mounted() {
    this.getExpectList();
  },
};
</script>

<style>
#expect-history-list {
  flex: 5;
  border-right: 2px solid #adadad;
  margin-top: 20px;
  padding: 0 20px 0 0;
}
#filter-btn {
  margin-bottom: 12px;
  text-align: right;
}
#expect-add-btn {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 12px;
}
#expect-list {
  width: 100%;
  height: 660px;
  border: 2px dashed #adadad;
  background-color: #efefef;
  border-radius: 8px;
}
#expects {
  height: 550px;
}
#expect-pagination {
  margin-top: 30px;
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
}
.expect-name {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: start;
  font-weight: bold;
}
.expect-d-day {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  font-weight: bold;
}
.expect-style {
  height: 27px;
}
</style>
