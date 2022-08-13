<template>
  <v-container>
    <div>매칭 화면</div>
    <div>
      <!--        <v-row class="row no-gutters theme-background" style="height: 91vh">-->

      <!--          <v-col id="capture" :class="{ 'col-8': isChatPanel, 'col-12': !isChatPanel }">-->
      <!--            <LeftPanel class="h-100"></LeftPanel>-->
      <!--          </v-col>-->

      <!--          <v-col class="right-panel" :class="{ 'col-4': isChatPanel, 'col-0': !isChatPanel }" v-if="isChatPanel">-->
      <!--            <ChatPanel class="chat-panel" height="100px" v-if="isChatPanel"> </ChatPanel>-->
      <!--          </v-col>-->
      <!--        </v-row>-->
      <!--      </div>-->
    </div>
    <router-view></router-view>
    <!--    <v-footer dark padless>-->
    <!--      <v-card class="flex-grow-1" tile>-->
    <!--        <v-card-title class="teal">-->
    <!--          <v-btn v-for="icon in icons" :key="icon" class="mx-4" dark>-->
    <!--            <v-icon size="24px">-->
    <!--              {{ icon }}-->
    <!--            </v-icon>-->
    <!--          </v-btn>-->
    <!--        </v-card-title>-->
    <!--        <v-row>-->
    <!--          <v-col>-->
    <!--            <v-card-text class="py-2 white&#45;&#45;text text-center">-->
    <!--              {{ new Date().getFullYear() }} — <strong>Vuetify</strong>-->
    <!--            </v-card-text>-->
    <!--          </v-col>-->
    <!--          <v-col>-->
    <!--            <v-card-text>-->

    <!--              <v-btn class="btn mr-2" @click="toggleChatPanel()"> 채팅온오프 </v-btn>-->
    <!--            </v-card-text>-->
    <!--          </v-col>-->
    <!--        </v-row>-->
    <!--      </v-card>-->
    <!--    </v-footer>-->
  </v-container>
</template>

<script>
import { mapGetters, mapActions, mapState } from "vuex";
import Swal from "sweetalert2";
// import LeftPanel from "@/components/meetingpage/LeftPanel.vue";
// import ChatPanel from "@/components/meetingpage/chatPanel.vue";
// import VideochatCreate from "@/components/videochat/VideochatCreate.vue";

export default {
  data() {
    return {
      icons: ["mdi-facebook", "mdi-twitter", "mdi-linkedin", "mdi-instagram"],
      chatPanel: false,
    };
  },
  name: "VideochatView",
  components: {
    // VideochatCreate,
    // LeftPanel,
    // ChatPanel,
  },
  methods: {
    ...mapActions("MeetingStore", ["toggleChatPanel"]),
  },
  computed: {
    ...mapGetters({ isLoggedIn: "isLoggedIn" }),
    ...mapState("MeetingStore", ["isChatPanel"]),
  },
  created() {
    if (!this.isLoggedIn) {
      Swal.fire({
        title: "로그인이 필요한 서비스입니다.",
        text: "로그인 화면으로 이동할까요?",
        icon: "warning",
        showCancelButton: true,
        buttons: true,
        dangerMode: true,
      }).then((result) => {
        if (result.isConfirmed) {
          this.$router.push({
            name: "MemberLogin",
          });
        } else {
          this.$router.push({
            name: "home",
          });
        }
      });
    }
  },
};
</script>

<style></style>
