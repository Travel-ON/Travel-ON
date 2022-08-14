<template>
  <div style="flex: 6">
    <div class="history-detail-title">
      <div style="display: flex; align-items: center; margin-left: 20px">
        <div style="margin-right: 10px"><v-icon>mdi-filter-plus</v-icon></div>
        <div style="font-weight: bold; font-size: 28px">장소 필터 설정</div>
      </div>
    </div>
    <v-form v-model="valid" ref="form">
      <div class="history-detail-body">
        <div class="history-detail-body-shell">
          <div style="display: flex; margin: 30px 30px 0">
            <div style="flex: 2; text-align: center">
              <v-text-field
                type="date"
                v-model="filterData.startDate"
                :disabled="isDisabled"
                bg-color="#fff"
                density="compact"
              ></v-text-field>
            </div>
            <div style="flex: 1; text-align: center; margin-top: 10px">
              <div style="font-size: 24px">~</div>
            </div>
            <div style="flex: 2; text-align: center">
              <v-text-field
                type="date"
                v-model="filterData.endDate"
                :disabled="isDisabled"
                bg-color="#fff"
                density="compact"
              ></v-text-field>
            </div>
          </div>
          <div class="date-toggle">
            <v-btn-toggle v-model="dateState" tile color="blue darken-1" mandatory width="100%">
              <v-btn :value="0"> 1주일 </v-btn>
              <v-btn :value="1"> 1개월 </v-btn>
              <v-btn :value="2"> 3개월 </v-btn>
              <v-btn :value="3"> 6개월 </v-btn>
              <v-btn :value="4"> 임의 </v-btn>
            </v-btn-toggle>
          </div>
          <div class="location-select">
            <v-select
              label="시(도)"
              :items="sido"
              item-title="name"
              item-value="value"
              v-model="filterData.sidoName"
              style="margin-right: 30px"
              bg-color="#efefef"
              density="comfortable"
            ></v-select>
            <v-select
              label="구(군)"
              :items="gugun"
              item-title="gugunName"
              item-value="gugunName"
              v-model="filterData.gugunName"
              bg-color="#efefef"
              density="comfortable"
              :disabled="gugunDisable"
            ></v-select>
          </div>
          <div class="location-place">
            <v-text-field
              prepend-inner-icon="mdi-map-marker"
              label="(선택) 검색할 장소를 입력해주세요"
              :v-model="filterData.visitPlace"
              bg-color="#efefef"
            ></v-text-field>
          </div>
        </div>
      </div>
      <div class="form-btn">
        <v-btn
          color="#50a0f0"
          size="x-large"
          style="font-weight: bold; color: #efefef; margin-right: 30px"
          @click="filterExpect(filterData)"
          width="180px"
          >필터링</v-btn
        >
        <v-btn
          variant="outlined"
          color="#50a0f0"
          bg-color="#efefef"
          size="x-large"
          style="font-weight: bold; background-color: #efefef"
          @click="reset"
          width="180px"
          >초기화</v-btn
        >
      </div>
    </v-form>
  </div>
</template>

<script>
/* eslint-disable no-undef */
import spring from "@/api/spring_boot";
import axios from "axios";
import { mapActions } from "vuex";

const newDate = new Date();

const year = newDate.getFullYear();
const month = newDate.getMonth();
const day = newDate.getDate();

export default {
  data: () => ({
    valid: true,
    filterData: {
      startDate: new Date().toISOString().substr(0, 10),
      endDate: new Date(year, month, day + 7).toISOString().substr(0, 10), // 일주일 전 으로 초기화,
      sidoName: null,
      gugunName: null,
      visitPlace: null,
    },
    dateState: 0, // 0: 1주일, 1: 1개월, 2: 3개월, 3: 6개월, 4: 임의
    isDisabled: true,
    sido: [
      { value: null, name: "(선택 안함)" },
      { value: "경기", name: "경기" },
      { value: "강원", name: "강원" },
      { value: "경남", name: "경남" },
      { value: "경북", name: "경북" },
      { value: "광주", name: "광주" },
      { value: "대구", name: "대구" },
      { value: "대전", name: "대전" },
      { value: "부산", name: "부산" },
      { value: "서울", name: "서울" },
      { value: "세종", name: "세종" },
      { value: "울산", name: "울산" },
      { value: "인천", name: "인천" },
      { value: "전남", name: "전남" },
      { value: "전북", name: "전북" },
      { value: "제주", name: "제주" },
      { value: "충남", name: "충남" },
      { value: "충북", name: "충북" },
    ],
    gugun: [],
    gugunDisable: true,
  }),
  methods: {
    ...mapActions(["filterExpect"]),
    validate() {
      this.$refs.form.validate();
    },
    reset() {
      this.$refs.form.reset();
    },
  },
  watch: {
    dateState(val) {
      if (val === 0) {
        this.filterData.endDate = new Date(year, month, day + 7).toISOString().substr(0, 10);
        this.isDisabled = true;
      } else if (val === 1) {
        this.filterData.endDate = new Date(year, month + 1, day).toISOString().substr(0, 10);
        this.isDisabled = true;
      } else if (val === 2) {
        this.filterData.endDate = new Date(year, month + 3, day).toISOString().substr(0, 10);
        this.isDisabled = true;
      } else if (val === 3) {
        this.filterData.endDate = new Date(year, month + 6, day).toISOString().substr(0, 10);
        this.isDisabled = true;
      } else {
        this.isDisabled = false;
      }
    },
    "filterData.sidoName": function (val) {
      if (val) {
        this.gugunDisable = false;
        this.gugun = [];
        axios({
          url: spring.plan.gugun(val),
          method: "get",
        })
          .then((res) => {
            console.log(val);
            console.log(res);
            this.gugun = res.data;
          })
          .catch((err) => {
            console.error(err);
          });
      } else {
        this.gugunDisable = true;
        this.gugun = [];
      }
      this.filterData.gugunName = null;
    },
  },
};
</script>

<style>
.date-toggle {
  margin-bottom: 28px;
}
.location-select {
  display: flex;
  margin: 0 30px;
}
.location-place {
  display: flex;
  margin: 0 30px;
}
.form-btn {
  display: flex;
  justify-content: center;
}
</style>
