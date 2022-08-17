<!-- eslint-disable vuejs-accessibility/mouse-events-have-key-events -->
<template>
  <div
    class="ml-2 mr-2"
    v-if="streamManager"
    style="position: relative; max-height: 800px; max-width: 1060px"
    @mouseleave="mouseOn = null"
    @mouseover="mouseOn = true"
  >
    <ov-video :stream-manager="streamManager" />
    <div v-if="isRoom && mouseOn" class="text-center" style="position: absolute; top: 20px; right: 20px">
      <v-menu bottom offset-x>
        <template v-slot:activator="{ props }">
          <v-btn dark icon v-bind="props">
            <v-icon>mdi-dots-vertical</v-icon>
          </v-btn>
        </template>
        <v-list>
          <v-list-item
            v-for="(item, i) in items.filter(
              (c) => (!c.onlyOther || currentUser !== clientName) && (!c.onlyHost || hostName === currentUser),
            )"
            :key="i"
            @click="check(item)"
          >
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </div>

    <div style="position: absolute; bottom: 22px; right: 50%; transform: translate(+50%, 0)">
      <div
        class="label mb-1"
        v-if="clientTitle && clientTitle !== ' ' && clientTitle !== ''"
        style="
          background-color: darkblue;
          text-shadow: -1px 0px white, 0px 1px white, 1px 0px white, 0px -1px white;
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
        v-if="isResident === 'true'"
        class="label"
        style="background-color: #6499ff; color: #fff; border: 2px solid #fff; border-radius: 8px; padding: 1px 10px"
      >
        <v-icon>mdi-clover</v-icon> 현지인 <v-icon>mdi-clover</v-icon>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
import spring from "@/api/spring_boot";
import axios from "axios";
import Swal from "sweetalert2";
import OvVideo from "./OvVideo.vue";

export default {
  name: "UserVideo",
  data: () => ({
    items: [
      { title: "여행 플래너 보기", onlyHost: false, onlyOther: false }, // 플래너 보기
      { title: "신고하기", onlyHost: false, onlyOther: true },
      { title: "강퇴하기", onlyHost: true, onlyOther: true },
    ],
    mouseOn: null, // 마우스 올라갔는지?
  }),
  components: {
    OvVideo,
  },
  props: {
    streamManager: Object,
  },
  computed: {
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
    ...mapActions("MeetingStore", ["sendMessage"]),
    getConnectionData() {
      const { connection } = this.streamManager.stream;
      return JSON.parse(connection.data);
    },
    async check(item) {
      if (item.title === "여행 플래너 보기") {
        alert(this.clientName.concat(" 플래너 보기!"));
      } else if (item.title === "신고하기") {
        alert(this.clientName.concat(" [", this.clientUserId, "] ", this.clientTitle));

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
            .then((res) => {
              console.log(res);
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
      } else if (item.title === "강퇴하기") {
        alert(this.clientName.concat(" 강퇴!"));
        const data = { type: "kickout", from: this.currentUser, to: this.clientName };
        this.sendMessage(data);
      }
    },
  },
};
</script>
<style scoped>
.label {
  padding: 1px 10px;
}
</style>
