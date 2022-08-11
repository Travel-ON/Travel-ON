<template>
  <div id="plan-history-list">
    <div id="filter-btn">
      <v-btn append-icon="mdi-filter-outline" style="background-color: #efefef">필터</v-btn>
    </div>
    <div id="plan-add-btn">
      <v-btn
        prepend-icon="mdi-plus"
        width="100%"
        size="x-large"
        height="64px"
        style="font-size: 20px; font-weight: bold; border: 2px dashed #adadad; background-color: #efefef"
        >방문장소 추가</v-btn
      >
    </div>
    <div id="plan-list">
      <div id="plans">
        <div class="plan" v-for="(plan, index) in convertedHistoryList[page - 1]" :key="index">
          <div v-if="typeof plan === 'string'" style="background-color: #d1e6fb; font-size: 18px">
            <span>{{
              `${new Date(plan).getFullYear()}년 ${new Date(plan).getMonth() + 1}월 ${new Date(plan).getDate()}일`
            }}</span>
          </div>
          <div v-else style="display: flex">
            <div class="plan-address">
              <span>{{ `${plan.sidoName} ${plan.gugunName}` }}</span>
            </div>
            <div class="plan-name">{{ plan.visitedPlace }}</div>
            <div class="plan-rating">
              <div>{{ plan.ratePoint }}</div>
              <div>
                <v-rating
                  v-model="plan.ratePoint"
                  active-color="#ffec65"
                  color="#979797"
                  half-increments
                  disabled
                  size="x-small"
                ></v-rating>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div id="plan-pagination">
        <v-pagination v-model="page" :length="convertedHistoryList.length"></v-pagination>
      </div>
    </div>
  </div>
</template>

<script>
import spring from "@/api/spring_boot";
import axios from "axios";
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      planHisoryList: [], // 요청 받아오는 리스트
      convertedHistoryList: [], // 출력 전용 리스트
      page: 1, // 페이지네이션 변수
    };
  },
  methods: {
    ...mapGetters(["token"]),
    getList() {
      axios({
        url: spring.plan.getHistory(),
        method: "get",
        headers: {
          Authorization: `Bearer ${this.token()}`,
        },
      })
        .then((res) => {
          console.log("플랜 목록 수집 성공");
          this.planHisoryList = res.data;
        })
        .catch((err) => {
          console.error(err);
        });
    },
  },
  mounted() {
    this.getList();
  },
  watch: {
    planHisoryList(val) {
      let tempDate = null; // 임시 저장 날짜
      let listCount = 0;
      let tempList = []; // 임시 저장 리스트
      val.forEach((element) => {
        if (element.visitDate !== tempDate) {
          // 임시 저장 날짜와 엘리먼트 날짜가 다르면
          tempList.push(element.visitDate); // 날짜 정보 임시 저장
          tempDate = element.visitDate; // 임시 저장 날짜 갱신
          listCount += 1; // 카운트 1 증가
          if (listCount === 15) {
            // 방금으로 인해 카운트가 15됬을 경우,
            listCount = 1; // 카운트 1
            this.convertedHistoryList.push(tempList); // 15짜리 리스트 출력 전용 리스트 push
            tempList = []; // 임시 리스트 초기화
            tempList.push(tempDate); // 임시 리스트에 날짜 정보 임시 저장
          }
        }
        tempList.push(element); // 엘리먼트 임시 저장
        listCount += 1; // 카운트 1 증가
        if (listCount === 15) {
          // 방금으로 인해 카운트가 15됬을 경우,
          this.convertedHistoryList.push(tempList);
          tempList = []; // 임시 리스트 초기화
          listCount = 0; // 카운트값 초기화
          tempDate = null; // 임시 저장 날짜 초기화
        }
      });
      if (tempList !== []) {
        // 반복문 이후 임시 리스트가 남아 있는 경우
        this.convertedHistoryList.push(tempList);
      }
    },
  },
};
</script>

<style>
#plan-history-list {
  flex: 4;
  border-right: 2px solid #adadad;
  margin-top: 20px;
  padding: 0 20px;
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
  font-size: 10px;
}
.plan-address {
  flex: 1;
}
.plan-name {
  flex: 1;
}
.plan-rating {
  flex: 2;
  display: flex;
  align-items: center;
}
</style>
