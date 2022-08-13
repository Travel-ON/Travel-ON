<template>
  <div class="ml-2 mr-2" v-if="streamManager" style="position: relative">
    <ov-video :stream-manager="streamManager" />
    <div v-if="isRoom" class="text-center" style="position: absolute; top: 10px; right: 10px">
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

    <div style="position: absolute; bottom: 10px; right: 50%">
      <div
        class="label mb-1"
        v-if="clientTitle"
        style="background-color: darkblue; color: whitesmoke; border-radius: 5px"
      >
        {{ clientTitle }}
      </div>
      <div class="label" style="background-color: paleturquoise; border-radius: 5px">
        {{ clientName }}
      </div>
    </div>
    <div style="position: absolute; top: 10px; right: 50%; border-radius: 15px">
      <div v-if="isResident === 'true'" class="label" style="background-color: #6499ff; color: whitesmoke">
        <v-icon>mdi-clover</v-icon> 현지인 <v-icon>mdi-clover</v-icon>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
import axios from "axios";
import Swal from "sweetalert2";
import OvVideo from "./OvVideo.vue";

export default {
  name: "UserVideo",
  data: () => ({
    items: [
      { title: "여행 플래너 보기", onlyHost: false, onlyOther: false },
      { title: "신고하기", onlyHost: false, onlyOther: true },
      { title: "강퇴하기", onlyHost: true, onlyOther: true },
    ],
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
            url: `http://i7b301.p.ssafy.io:3000/api/videochat/report`,
            // url: `http://localhost:3000/api/videochat/report`,
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
      }
    },
  },
};
</script>
<style scoped>
.label {
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 1px;
  padding-bottom: 1px;
}
</style>
