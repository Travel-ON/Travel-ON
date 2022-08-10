<template>
  <div>
    <select v-model="selected">
      <option disabled value="">전지역</option>
      <option>강원</option>
      <option>경기</option>
      <option>경남</option>
      <option>경북</option>
      <option>광주</option>
      <option>대구</option>
      <option>대전</option>
      <option>부산</option>
      <option>서울</option>
      <option>세종특별자치시</option>
      <option>울산</option>
      <option>인청</option>
      <option>전남</option>
      <option>전북</option>
      <option>제주특별자치도</option>
      <option>충남</option>
      <option>충북</option>
    </select>
    <span>선택함: {{ selected }}</span>
  </div>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      credentials: {
        id: "",
        password: "",
        passwordConfirm: "",
        email: "",
        nickname: "",
        sidoCode: "",
      },
      sido: [
        { code: "4200000000", name: "강원" },
        { code: "4100000000", name: "경기" },
        { code: "4800000000", name: "경남" },
        { code: "4700000000", name: "경북" },
        { code: "2900000000", name: "광주" },
        { code: "2700000000", name: "대구" },
        { code: "3000000000", name: "대전" },
        { code: "2600000000", name: "부산" },
        { code: "1100000000", name: "서울" },
        { code: "3611000000", name: "세종특별자치시" },
        { code: "3100000000", name: "울산" },
        { code: "2800000000", name: "인천" },
        { code: "4600000000", name: "전남" },
        { code: "4500000000", name: "전북" },
        { code: "5000000000", name: "제주특별자치도" },
        { code: "4400000000", name: "충남" },
        { code: "4300000000", name: "충북" },
      ],
    };
  },
};

