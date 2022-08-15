<!-- eslint-disable vuejs-accessibility/click-events-have-key-events -->
<template>
  <div>
    <v-row justify="center">
      <v-dialog v-model="dialog" scrollable class="shadow-none">
        <template v-slot:activator="{ props }">
          <v-btn color="primary" v-bind="props"> Open Dialog </v-btn>
        </template>
        <!-- <v-card>
          <v-card-title>Select Country</v-card-title>
          <v-divider></v-divider>
          <v-card-text style="height: 300px">중간부분</v-card-text>
          <v-divider></v-divider>
          <v-card-actions>
            <v-btn color="blue-darken-1" text @click="dialog = false"> Close </v-btn>
            <v-btn color="blue-darken-1" text @click="dialog = false"> Save </v-btn>
          </v-card-actions>
        </v-card> -->
        <v-card border="false" color="transparent">
          <v-card-text>
            <div class="plan-modal-body">
              <img
                src="https://user-images.githubusercontent.com/97648026/184590069-0f116524-1fa2-43ac-b6ec-dc5cf47c4a3d.png"
                alt="test"
                width="800"
                height="840"
                rounded="xl"
              />
            </div>
            <div style="position: absolute; top: 56px; right: 56px">
              <v-btn
                color="transparent"
                icon="mdi-close"
                size="x-large"
                style="font-size: 32px"
                @click="dialog = false"
              ></v-btn>
            </div>
            <div class="plan-modal-body-shell">
              <div style="font-size: 36px; margin-left: 36px; font-weight: bold; margin-bottom: 28px">
                {{ `📝${username}님의 여행 스토리` }}
              </div>
              <div class="view-select-buttons">
                <div
                  class="select-button"
                  :style="viewState === 0 ? 'background-color: #50a0f0; color: #fff' : ``"
                  @click="viewState = 0"
                >
                  방문한 장소
                </div>
                <div
                  class="select-button"
                  :style="viewState === 1 ? 'background-color: #50a0f0; color: #fff' : ``"
                  @click="viewState = 1"
                >
                  방문 예정 장소
                </div>
              </div>
              <div class="plan-modal-main-body">
                <div></div>
              </div>
            </div>
          </v-card-text>
        </v-card>
      </v-dialog>
    </v-row>
  </div>
</template>

<script>
export default {
  name: "PlanModalTest",
  data() {
    return {
      dialogm1: "",
      dialog: false,
      username: "박재현",
      viewState: 0,
    };
  },
};
</script>

<style>
.v-card--variant-elevated,
.v-dialog .v-overlay__content > .v-card,
.v-dialog .v-overlay__content > .v-sheet {
  box-shadow: none !important;
}
.plan-modal-body {
  position: relative;
}
.plan-modal-body-shell {
  position: absolute;
  top: 84px;
  width: 794px;
  height: 780px;
  margin-left: 3px;
}
.v-btn--variant-elevated {
  box-shadow: none !important;
}
.view-select-buttons {
  display: flex;
  font-size: 24px;
  font-weight: bold;
}
.select-button {
  width: 240px;
  height: 54px;
  display: flex;
  justify-content: center;
  align-items: center;
  box-sizing: border-box;
  border-radius: 18px 18px 0 0;
  border-top: 3px solid #50a0f0;
  border-left: 3px solid #50a0f0;
  border-right: 3px solid #50a0f0;
  background-color: #fff;
  color: #020715;
  cursor: pointer;
}
.plan-modal-main-body {
  border-top: 3px solid #50a0f0;
  display: flex;
}
</style>
