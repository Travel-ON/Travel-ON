<template>
  <div style="flex: 6">
    <div style="height: 90px; display: flex; align-items: center">
      <div style="display: flex; align-items: center; margin-left: 20px">
        <div style="margin-right: 10px"><v-icon>mdi-plus-circle-outline</v-icon></div>
        <div style="font-weight: bold; font-size: 28px">방문장소 추가</div>
      </div>
    </div>
    <div style="display: flex; justify-content: center; margin: 0 50px">
      <v-form v-model="valid" ref="form">
        <div
          style="
            width: 560px;
            padding: 20px 20px 0px;
            background-color: #c5dcff;
            border-radius: 12px;
            margin-bottom: 30px;
          "
        >
          <div style="display: flex; align-items: center; margin-bottom: 10px">
            <div><v-icon>mdi-pen</v-icon></div>
            <div style="font-size: 16px; margin-left: 10px; font-weight: bold">장소 작성 (필수)</div>
          </div>
          <div style="text-align: left; margin-left: 30px">
            <label for="planDate">
              <input
                type="date"
                id="planDate"
                v-model="planData.date"
                style="border-bottom: 2px solid #adadad; margin-bottom: 30px; margin-right: auto"
                required
              />
            </label>
          </div>
          <div style="margin: 0 30px; display: flex">
            <v-select
              label="시(도)"
              :items="sido"
              item-title="name"
              item-value="code"
              v-model="planData.sidoCode"
              style="margin-right: 30px"
              bg-color="#efefef"
              density="comfortable"
              required
            ></v-select>
            <v-select label="구(군)" bg-color="#efefef" density="comfortable" required></v-select>
          </div>
          <v-text-field
            label="장소를 입력해주세요"
            prepend-inner-icon="mdi-map-marker"
            bg-color="#efefef"
            style="margin: 0 30px"
            density="comfortable"
            required
          ></v-text-field>
        </div>
        <div>
          <div
            style="
              width: 560px;
              padding: 20px 20px;
              background-color: #c5dcff;
              border-radius: 12px;
              margin-bottom: 30px;
            "
          >
            <div>
              <div style="display: flex; align-items: center">
                <div><v-icon>mdi-pen</v-icon></div>
                <div style="font-size: 16px; margin-left: 10px; font-weight: bold">리뷰 작성 (선택)</div>
              </div>
              <div style="display: flex; align-items: center; margin-left: 30px">
                <div style="margin-bottom: 4px">
                  <span style="font-size: 16px; font-weight: bold">만족도는 어땠나요? </span>
                  <span style="font-size: 16px">({{ planData.rating.toFixed(1) }}) </span>
                </div>
                <div>
                  <v-rating
                    v-model="planData.rating"
                    hover
                    active-color="#ffec65"
                    color="#979797"
                    half-increments
                  ></v-rating>
                </div>
              </div>
              <v-textarea
                bg-color="#efefef"
                label="소감은 어떠셨나요?"
                style="margin: 0 30px"
                v-model="planData.review"
              ></v-textarea>
            </div>
          </div>
        </div>
        <div style="display: flex; justify-content: center">
          <v-btn
            color="#50a0f0"
            size="x-large"
            style="font-weight: bold; color: #efefef; margin-right: 30px"
            @click="validate"
            width="180px"
            >저장</v-btn
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
  </div>
</template>

<script>
export default {
  data: () => ({
    valid: true,
    planData: {
      date: new Date().toISOString().substr(0, 10),
      rating: 3,
      sidoCode: null,
      gugun: null,
      locate: "",
      review: "",
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
      { code: "3611000000", name: "세종" },
      { code: "3100000000", name: "울산" },
      { code: "2800000000", name: "인천" },
      { code: "4600000000", name: "전남" },
      { code: "4500000000", name: "전북" },
      { code: "5000000000", name: "제주" },
      { code: "4400000000", name: "충남" },
      { code: "4300000000", name: "충북" },
    ],
    gugun: [],
  }),
  methods: {
    validate() {
      this.$refs.form.validate();
    },
    reset() {
      this.$refs.form.reset();
    },
  },
  watch: {
    // "planData.sido": function(val) {
    //   axios({
    //     url:
    //   })
    // },
  },
};
</script>

<style></style>
