<template>
  <div v-if="streamManager" style="position: relative; max-height: 800px; max-width: 1060px">
    <ov-video :stream-manager="streamManager" />
    <div
      v-if="isThisRoom"
      class="text-center"
      style="position: absolute; display: inline-block; top: 20px; right: 20px"
    >
      <v-menu bottom offset-x>
        <template v-slot:activator="{ props }">
          <v-btn dark v-bind="props">
            <v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-dialog v-model="dialog" scrollable class="shadow-none">
            <template v-slot:activator="{ props }">
              <v-list-item v-bind="props">
                <v-list-item-title>여행 플래너 보기</v-list-item-title>
              </v-list-item>
            </template>
            <v-card border="false" color="transparent" style="overflow: hidden; box-shadow: none">
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
                    {{ `📝${this.clientName}님의 여행 스토리` }}
                  </div>
                  <div class="view-select-buttons">
                    <!-- eslint-disable-next-line  -->
                    <div
                      class="select-button"
                      :style="viewState === 0 ? 'background-color: #50a0f0; color: #fff' : ``"
                      @click="viewState = 0"
                    >
                      방문한 장소
                    </div>
                    <!-- eslint-disable-next-line  -->
                    <div
                      class="select-button"
                      :style="viewState === 1 ? 'background-color: #50a0f0; color: #fff' : ``"
                      @click="viewState = 1"
                    >
                      방문 예정 장소
                    </div>
                  </div>
                  <div class="plan-modal-main-body">
                    <v-card-text
                      :style="`padding: 0; ${viewState === 1 ? 'display: flex; justify-content: center' : ''}`"
                    >
                      <div v-show="viewState === 0" style="display: flex">
                        <ModalPlanHistoryList :username="clientName" @switch-detail="switchDetail" />
                        <ModalPlanHistoryDetail v-if="selectedView === 1" :plan="planDetail" />
                        <ModalPlanPleaseSelect v-else />
                      </div>
                      <div v-show="viewState === 1" style="display: flex; width: 70%; justify-content: center">
                        <ModalExpectHistoryList :username="clientName" />
                      </div>
                    </v-card-text>
                  </div>
                </div>
              </v-card-text>
            </v-card>
          </v-dialog>
          <v-list-item v-if="currentUser !== clientName" @click="report()">
            <v-list-item-title>신고하기</v-list-item-title>
          </v-list-item>
          <v-list-item v-if="currentUser !== clientName && hostName === currentUser" @click="kickout()">
            <v-list-item-title>강퇴하기</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </div>
    <div style="position: absolute; bottom: 22px; right: 50%; transform: translate(+50%, 0)">
      <div
        class="label mb-1"
        v-if="clientTitle && clientTitle !== ' ' && clientTitle !== ''"
        style="
          background-color: #285078;
          color: whitesmoke;
          border-radius: 5px;
          padding: 4px 12px;
          border: 2px solid #fff;
        "
      >
        {{ clientTitle }}
      </div>
      <div
        class="label"
        style="
          background-color: #d1e6fb;
          text-shadow: -1px 0px white, 0px 1px white, 1px 0px white, 0px -1px white;
          border-radius: 5px;
          padding: 4px 24px;
          border: 2px solid #fff;
        "
      >
        {{ clientName }}
      </div>
    </div>
    <div style="position: absolute; top: 20px; right: 50%; border-radius: 15px; transform: translate(+50%, 0)">
      <div
        v-if="isResident === true"
        class="label"
        style="background-color: #6499ff; color: #fff; border: 2px solid #fff; border-radius: 8px; padding: 1px 10px"
      >
        <v-icon>mdi-clover</v-icon> 현지인 <v-icon>mdi-clover</v-icon>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";
import spring from "@/api/spring_boot";
import axios from "axios";
import Swal from "sweetalert2";
import ModalPlanHistoryList from "@/components/modalplanner/ModalPlanHistoryList.vue";
import ModalPlanHistoryDetail from "@/components/modalplanner/ModalPlanHistoryDetail.vue";
import ModalPlanPleaseSelect from "@/components/modalplanner/ModalPlanPleaseSelect.vue";
import ModalExpectHistoryList from "@/components/modalplanner/ModalExpectHistoryList.vue";
import OvVideo from "./OvVideo.vue";

export default {
  name: "UserVideo",
  // data: () => ({
  //   // items: [
  //   //   { title: "여행 플래너 보기", onlyHost: false, onlyOther: false }, // 플래너 보기
  //   //   { title: "신고하기", onlyHost: false, onlyOther: true },
  //   //   { title: "강퇴하기", onlyHost: true, onlyOther: true },
  //   // ],

  // }),
  data() {
    return {
      dialogm1: "",
      dialog: false,
      viewState: 0, // 0: 방문한 장소, 1: 방문 예정 장소
      selectedView: 0,
      planDetail: null,
    };
  },
  components: {
    OvVideo,
    ModalPlanHistoryList,
    ModalPlanHistoryDetail,
    ModalPlanPleaseSelect,
    ModalExpectHistoryList,
  },
  props: {
    streamManager: Object,
    isThisRoom: Boolean,
  },
  computed: {
    ...mapState("MeetingStore", ["isChatPanel"]),
    ...mapGetters({
      currentUser: "currentUser",
      currentUserId: "currentUserId",
      token: "token",
    }),
    clientName() {
      const { clientName } = this.getConnectionData();
      return clientName;
    },
    clientUserId() {
      const { clientUserId } = this.getConnectionData();
      return clientUserId;
    },
    clientTitle() {
      const { clientTitle } = this.getConnectionData();
      return clientTitle;
    },
    isResident() {
      const { isResident } = this.getConnectionData();
      return isResident;
    },
    hostName() {
      const { hostName } = this.getConnectionData();
      return hostName;
    },
    isRoom() {
      const { isRoom } = this.getConnectionData();
      return isRoom;
    },
  },

  methods: {
    ...mapActions("MeetingStore", ["sendMessage", "toggleChatPanel"]),
    switchDetail(plan) {
      this.planDetail = plan;
      this.selectedView = 1;
    },
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
    // async check(item) {
    //   if (item.title === "여행 플래너 보기") {
    //     alert(this.clientName.concat(" 플래너 보기!"));
    //   } else if (item.title === "신고하기") {
    //     alert(this.clientName.concat(" [", this.clientUserId, "] ", this.clientTitle));
    //   } else if (item.title === "강퇴하기") {
    //     alert(this.clientName.concat(" 강퇴!"));
    //   }
    // },
    async report() {
      const { value: text } = await Swal.fire({
        input: "textarea",
        title: "신고하기",
        inputLabel: `${this.clientName}님을 신고하실건가요?`,
        inputPlaceholder: "신고 내용을 입력해주세요",
        showCancelButton: true,
      });

      if (text) {
        Swal.fire(text);
        axios({
          url: spring.videochat.report(),
          method: "post",
          headers: { Authorization: `Bearer ${this.token}` },
          data: {
            reportedId: this.clientUserId,
            reportContent: text,
          },
        })
          .then(() => {
            Swal.fire({
              icon: "success",
              title: "신고가 접수되었습니다!",
              showConfirmButton: false,
              timer: 1000,
            });
          })
          .catch((err) => {
            Swal.fire({
              icon: "error",
              title: "잠시후 다시 시도해주세요!",
              showConfirmButton: false,
              timer: 1000,
            });
            console.log(err);
          });
      }
    },
    kickout() {
      const data = { type: "kickout", from: this.currentUser, to: this.clientName };
      this.sendMessage(data);
    },
  },
};
</script>
<style scoped>
.label {
  padding: 1px 10px;
}
.plan-modal-body {
  position: relative;
}
.plan-modal-body-shell {
  position: absolute;
  top: 84px;
  width: 792px;
  height: 780px;
  margin-left: 4px;
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
  justify-content: center;
}
</style>
