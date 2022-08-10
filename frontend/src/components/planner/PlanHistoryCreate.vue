<template>
  <div style="flex: 6">
    <div style="height: 90px; display: flex; align-items: center">
      <div style="display: flex; align-items: center; margin-left: 20px">
        <div style="margin-right: 10px"><v-icon>mdi-plus-circle-outline</v-icon></div>
        <div style="font-weight: bold; font-size: 28px">방문장소 추가</div>
      </div>
    </div>
    <div style="display: flex; justify-content: center; margin: 0 50px">
      <v-form>
        <div
          style="
            width: 560px;
            padding: 20px 50px 0px;
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
                v-model="date"
                style="border-bottom: 2px solid #adadad; margin-bottom: 30px; margin-right: auto"
              />
            </label>
          </div>
          <div style="margin: 0 30px; display: flex">
            <v-select label="시(도)" style="margin-right: 30px" bg-color="#efefef" density="comfortable"></v-select>
            <v-select label="구(군)" bg-color="#efefef" density="comfortable"></v-select>
          </div>
          <v-text-field
            label="장소를 입력해주세요"
            prepend-inner-icon="mdi-map-marker"
            bg-color="#efefef"
            style="margin: 0 30px"
            density="comfortable"
          ></v-text-field>
        </div>
        <div>
          <div
            style="
              width: 560px;
              padding: 20px 50px;
              background-color: #c5dcff;
              border-radius: 12px;
              margin-bottom: 30px;
            "
          >
            <div style="display: flex; align-items: center; margin-bottom: 30px">
              <div><v-icon>mdi-pen</v-icon></div>
              <div style="font-size: 16px; margin-left: 10px; font-weight: bold">리뷰 작성 (선택)</div>
              <div style="display: flex">
                <div></div>
                <div></div>
              </div>
            </div>
          </div>
        </div>
      </v-form>
    </div>
  </div>
</template>

<script>
export default {
  data: () => ({
    date: new Date().toISOString().substr(0, 10),
    dateMenu: false,
  }),
};
</script>

<style></style>
