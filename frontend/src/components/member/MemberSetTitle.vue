<template>
  <div>
    <div><strong>칭호설정</strong></div>
    <v-card class="mx-auto rounded-xl" elevation="5" max-width="1300" tile>
      <v-card-text style="display: table">
        <div style="display: table-row">
          <div style="display: table-cell">
            업적지도 구역
            <div>
              <KoreaMap />
            </div>
          </div>
          <div style="display: table-cell">
            칭호리스트 구역
            <table v-for="title in titles" :key="title.userAchievmentId">
              <v-row @click="setTitle(title.sidoName, title.title)">{{ title.sidoName }} {{ title.title }}</v-row>
              <v-row>--------------------------------------</v-row>
              <v-row>.</v-row>
              <v-row>.</v-row>
              <v-row>.</v-row>
            </table>
          </div>
          <div>
            <v-select
              label="시(도)"
              :items="sidoList"
              item-title="name"
              item-value="codeSet"
              v-model="selectedSido"
              style="margin-right: 30px"
              bg-color="#efefef"
              density="comfortable"
              required
            ></v-select>
          </div>
        </div>

        <div>
          <div v-if="title == null && changeTitle == null">나의 칭호 > 칭호를 선택해주세요</div>
          <div v-else-if="title != null && changeTitle == null">나의 칭호 > {{ title }}</div>
          <div v-else-if="changeTitle != null">나의 칭호 > {{ sido }} {{ changeTitle }}</div>
        </div>
        <div><v-btn @click="modifyTitle()">설정</v-btn></div>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>
import { mapState, mapGetters } from "vuex";
import KoreaMap from "../main/KoreaMap.vue";

export default {
  computed: {
    ...mapState("Members", ["titles"]),
    ...mapGetters(["trophy", "trophyList"]),
    ...mapGetters(["title"]),
  },
  data() {
    return {
      nowTitle: null,
      sido: null,
      changeTitle: null,
      combiTitle: null,
      selectedSido: null,
      sidoName: null,
      sidoList: [
        { codeSet: { code: "4200000000", name: "강원" }, name: "강원" },
        { codeSet: { code: "4100000000", name: "경기" }, name: "경기" },
        { codeSet: { code: "4800000000", name: "경남" }, name: "경남" },
        { codeSet: { code: "4700000000", name: "경북" }, name: "경북" },
        { codeSet: { code: "2900000000", name: "광주" }, name: "광주" },
        { codeSet: { code: "2700000000", name: "대구" }, name: "대구" },
        { codeSet: { code: "3000000000", name: "대전" }, name: "대전" },
        { codeSet: { code: "2600000000", name: "부산" }, name: "부산" },
        { codeSet: { code: "1100000000", name: "서울" }, name: "서울" },
        { codeSet: { code: "3611000000", name: "세종" }, name: "세종" },
        { codeSet: { code: "3100000000", name: "울산" }, name: "울산" },
        { codeSet: { code: "2800000000", name: "인천" }, name: "인천" },
        { codeSet: { code: "4600000000", name: "전남" }, name: "전남" },
        { codeSet: { code: "4500000000", name: "전북" }, name: "전북" },
        { codeSet: { code: "5000000000", name: "제주" }, name: "제주" },
        { codeSet: { code: "4400000000", name: "충남" }, name: "충남" },
        { codeSet: { code: "4300000000", name: "충북" }, name: "충북" },
      ],
    };
  },
  mounted() {
    this.$store.dispatch("Members/getTitles", null);
  },
  watch: {
    selectedSido(val) {
      this.$store.dispatch("Members/getTitles", val.name);
    },
  },
  methods: {
    setTitle(sidoName, setTitle) {
      this.sido = sidoName;
      this.changeTitle = setTitle;
    },
    modifyTitle() {
      this.combiTitle = `${this.sido} ${this.changeTitle}`;
      this.$store.dispatch("Members/modifyTitle", this.combiTitle);
    },
  },
  components: { KoreaMap },
};
</script>

<style>
.level0 {
  fill: #ccc;
  stroke-linejoin: round;
  stroke: #ffffff;
  stroke-width: 1;
}

.level1 {
  fill: #ffee57;
  stroke-linejoin: round;
  stroke: #ffffff;
  stroke-width: 1;
}

.level2 {
  fill: #d0ff6d;
  stroke-linejoin: round;
  stroke: #ffffff;
  stroke-width: 1;
}

.level3 {
  fill: #52cbff;
  stroke-linejoin: round;
  stroke: #ffffff;
  stroke-width: 1;
}

.level4 {
  fill: #8263ff;
  stroke-linejoin: round;
  stroke: #ffffff;
  stroke-width: 1;
}

.level5 {
  fill: #ff6262;
  stroke-linejoin: round;
  stroke: #ffffff;
  stroke-width: 1;
}

.TEXT {
  fill: #ffffff;
  font-size: 16;
  font-weight: normal;
  text-anchor: middle;
  alignment-baseline: middle;
}
</style>
