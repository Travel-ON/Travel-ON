<template>
  <v-container class="chat-panel" style="height: 100%">
    <div style="display: flex; flex-direction: column; height: 100%">
      <div style="padding: 8px; color: white; background-color: #50a0f0; border-radius: 8px; margin-bottom: 4px">
        채팅
      </div>
      <!-- 채팅 내역 -->
      <div
        ref="chatArea"
        id="chat-area"
        :style="`
          height: 100%;
          border-radius: 8px;
          border: 2px solid #50a0f0;
          padding: 12px;
          background-color: #d1e6fb;
          flex: 1;
          max-height: ${isGamePanel ? '262.66px' : '731.22px'};
        `"
      >
        <div class="mt-2 text-left message" v-for="(message, i) of messages" :key="i">
          <div class="message-title">
            보낸사람:
            <span class="mr-2 message-header">{{ message.sender }} </span>
            <span class="mr-2 message-header" v-if="message.sender === currentUser">(나)</span>
            <span class="mr-2 message-header">님이 {{ message.receiver }}에게: </span>
            <br />
            시간: <span class="message-header">{{ message.time }}</span>
          </div>
          <div>
            <div v-if="message.url">
              <v-card class="mx-auto" max-width="200px" height="260px">
                <v-card-title> #{{ message.message }} </v-card-title>
                <v-btn :href="messages[i].doc_url" target="_blank">
                  <v-img :src="messages[i].url" alt="사진" width="200px" />
                </v-btn>
              </v-card>
            </div>

            <div v-else>
              {{ message.message }}
            </div>
          </div>
        </div>
      </div>
      <div style="margin-top: 4px; background-color: #d1e6fb; border-radius: 8px; padding: 12px">
        <!--  귓속말 기능    -->
        <div style="margin-top: 8px">
          <v-select
            :items="chatItems"
            v-model="select"
            label="보낼 곳"
            bg-color="#d1e6fb"
            style="border-radius: 8px; margin-bottom: 8px"
            hide-details
            color="#50a0f0"
            density="compact"
            elevation="0"
          ></v-select>
        </div>
        <!-- 채팅 입력 -->
        <form>
          <div style="display: flex">
            <div style="margin-right: 12px">
              <v-btn
                @click="SearchTypeToggle"
                icon="mdi-pound"
                size="small"
                color="#50a0f0"
                bg-color=""
                style="color: #fff"
              ></v-btn>
            </div>
            <div style="flex: 1">
              <input
                style="width: 100%; height: 40px"
                class="text-box"
                :class="{ inputColor: isHashTag }"
                v-model="message"
                @keydown.enter.prevent="clickSendMessage"
              />
            </div>
            <div>
              <v-btn
                @click="clickSendMessage"
                variant="text"
                color="#50a0f0"
                bg-color="#fff"
                style="background-color: #fff"
                height="40"
              >
                <v-icon>mdi-arrow-left-bottom</v-icon>
              </v-btn>
            </div>
          </div>
        </form>
      </div>
    </div>
  </v-container>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";
// import { onMounted } from "vue";

export default {
  name: "ChatPanel",
  data() {
    return {
      message: "",
      select: "모두",
      isHashTag: false,
    };
  },
  // 스크롤 자동 내리기 왜안댐?
  // setup() {
  //   console.log("1차로 들어오니?");
  //   onMounted(() => {
  //     console.log("2차로 들어오니?");
  //     const { chatArea } = this.$refs;
  //     chatArea.scrollTop = chatArea.scrollHeight;
  //   });
  // },
  // watch: {
  //   messages() {
  //     this.$nextTick(() => {
  //       const chatDiv = this.$refs.chatArea;
  //       chatDiv.scrollTo({
  //         top: chatDiv.scrollHeight - chatDiv.clientHeight,
  //         behavior: "smooth",
  //       });
  //     });
  //   },
  // },
  computed: {
    ...mapState("MeetingStore", ["messages"]),
    ...mapGetters(["currentUser"]),
    ...mapGetters("MeetingStore", ["subscribers", "chatItems", "isGamePanel"]),
  },
  methods: {
    ...mapActions("MeetingStore", ["toggleChatPanel", "sendMessage"]),
    SearchTypeToggle() {
      this.isHashTag = !this.isHashTag;
    },
    clickSendMessage() {
      const data = { to: [this.select], from: this.currentUser, message: this.message, isHashTag: this.isHashTag };
      if (data.to[0] === "모두") data.to = [];
      if (this.message.trim()) {
        this.sendMessage(data);
        this.message = "";
        this.isHashTag = false;
      }
    },
  },
};
</script>

<style scoped>
.chat-box {
  height: 100%;
}
.header {
  position: relative;
}
.text-box {
  background-color: #fff;
  width: 100%;
  border-radius: 4px 0 0 4px;
  color: #020715;
  padding-left: 10px;
}
.text-box:focus {
  outline: none;
}
.title {
  padding-left: 5%;
  font-size: 1rem !important;
  color: #ff0000;
}
.header {
  width: 100%;
  border-radius: 20px;
  box-shadow: 3px 3px 3px rgb(0, 0, 0, 0.3);
  height: 4vh;
}
.message-title {
  font-size: 0.8rem;
}
.message-header {
  font-family: "Nanum Gothic", sans-serif;
}
.message {
  color: black;
}
.send-btn {
  color: white;
}
.inputColor {
  background-color: #50a0f0;
}
#chat-area {
  overflow-y: auto;
}
#chat-area::-webkit-scrollbar {
  width: 12px;
  height: 12px;
}
/* 스크롤 바닥색*/
#chat-area::-webkit-scrollbar-track {
  background: #d1e6fb;
}
#chat-area::-webkit-scrollbar-corner {
  background: #50a0f0;
}
/* 스크롤바 색*/
#chat-area::-webkit-scrollbar-thumb {
  background: #50a0f0;
  border-radius: 4px;
}
#chat-area::-webkit-scrollbar-button {
  background-color: #50a0f0;
  border-radius: 4px;
  height: 12px;
}
</style>
