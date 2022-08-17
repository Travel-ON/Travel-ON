<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<template>
  <div style="display: flex; justify-content: center; align-items: center; width: 75%">
    <div>
      <div style="font-weight: bold; font-size: 40px; margin: 12px 0; width: 1080px; color: #50a0f0">칭호설정</div>
      <div style="width: 1080px; border-top: 3px solid #020715; background-color: #aaccff; border-radius: 0 0 8px 8px">
        <v-card-text>
          <div style="display: flex; justify-content: center">
            <div style="margin-right: 48px">
              <div>
                <AchievementsMap />
              </div>
            </div>
            <div>
              <div style="height: 550px; width: 100%; background-color: #6499ff; border-radius: 18px">
                <div
                  style="
                    height: 60px;
                    width: 100%;
                    font-size: 24px;
                    color: white;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                  "
                >
                  <div>획득한 칭호 리스트</div>
                </div>
                <div
                  style="
                    height: 490px;
                    width: 100%;
                    border: 4px solid #6499ff;
                    border-radius: 0 0 18px 18px;
                    padding: 12px 36px;
                    background-color: #fff;
                  "
                >
                  <!-- 리스트 구역 -->
                  <div style="width: 260px">
                    <v-select
                      label="시(도)"
                      :items="sidoList"
                      item-title="name"
                      item-value="codeSet"
                      v-model="selectedSido"
                      bg-color="#bfd9ff"
                      density="comfortable"
                      required
                    ></v-select>
                  </div>
                  <div
                    v-for="temptitle in titles"
                    :key="temptitle.userAchievementId"
                    :style="`
                      height: 42px;
                      margin-bottom: 8px;
                      width: 260px;
                      border: 3px solid #bfd9ff;
                      display: flex;
                      justify-content: center;
                      align-items: center;
                      background-color: ${
                        (combiTitle ? combiTitle : title) === `${temptitle.sidoName} ${temptitle.title}`
                          ? '#bfd9ff'
                          : '#fff'
                      };
                      color: ${
                        (combiTitle ? combiTitle : title) === `${temptitle.sidoName} ${temptitle.title}`
                          ? '#020715'
                          : '#000'
                      };
                      cursor: pointer;
                    `"
                    @click="setTitle(temptitle.sidoName, temptitle.title)"
                  >
                    {{ temptitle.sidoName }} {{ temptitle.title }}
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div
            style="
              margin-top: 36px;
              display: flex;
              justify-content: center;
              align-items: center;
              font-size: 36px;
              color: #285078;
            "
          >
            <!-- <div v-if="title == null && changeTitle == null">나의 칭호 ▶ 칭호를 선택해주세요</div>
            <div v-else-if="title != null && changeTitle == null">나의 칭호 > {{ title }}</div>
            <div v-else-if="changeTitle != null">나의 칭호 > {{ sido }} {{ changeTitle }}</div> -->
            <div style="display: flex; justify-content: center; align-items: center">
              <div style="margin-right: 20px; font-weight: bold">나의 칭호 ▶</div>
              <div
                style="
                  height: 64px;
                  width: 360px;
                  font-size: 24px;
                  background-color: #fff;
                  border: 2px dashed #020715;
                  border-radius: 4px;
                  display: flex;
                  justify-content: center;
                  align-items: center;
                  position: relative;
                "
              >
                <div>
                  {{
                    changeTitle !== null
                      ? changeTitle !== ""
                        ? `${sido} ${changeTitle}`
                        : "칭호를 선택해주세요"
                      : title !== " "
                      ? `${title}`
                      : "칭호를 선택해주세요"
                  }}
                </div>
                <div style="position: absolute; top: 0; right: 0">
                  <v-btn
                    color="transparent"
                    icon="mdi-close"
                    size="x-large"
                    style="font-size: 32px"
                    @click="
                      changeTitle = '';
                      sido = '';
                    "
                  ></v-btn>
                </div>
              </div>
            </div>
          </div>
        </v-card-text>
      </div>
      <div style="margin-top: 36px">
        <v-btn @click="modifyTitle()" color="#50a0f0" size="x-large" width="180" height="64">
          <span style="color: #fff; font-size: 32px">설정</span>
        </v-btn>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapGetters } from "vuex";
import AchievementsMap from "../main/AchievementsMap.vue";

export default {
  computed: {
    ...mapState("Members", ["titles"]),
    ...mapGetters(["trophy", "trophyList"]),
    ...mapGetters(["title"]),
  },
  data() {
    return {
      nowTitle: null,
      sido: null, // 변경 값
      changeTitle: null, // 변경 값
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
      this.combiTitle = `${sidoName} ${setTitle}`;
    },
    modifyTitle() {
      this.combiTitle = `${this.sido} ${this.changeTitle}`;
      this.$store.dispatch("Members/modifyTitle", this.combiTitle);
    },
  },
  components: { AchievementsMap },
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
