<template>
  <v-container class="chat-panel">
    <v-row class="chat-box p-2 d-flex flex-column h-100">
      <v-col class="header text-left">
        <span class="title"> 채팅 </span>
      </v-col>
      <!-- 채팅 내역 -->
      <div ref="chatArea" id="chat-area" style="height: 500px; border: solid cornflowerblue 1px; max-height: 500px">
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
            {{ message.message }}
          </div>
        </div>
      </div>
      <!--  귓속말 기능    -->
      <div>
        <v-select :items="chatItems" v-model="select" label="보낼 곳"> </v-select>
      </div>
      <div class="footer d-flex mt-auto">
        <div class="col-10 px-1 py-0">
          <input class="text-box" v-model="message" @keyup.enter="clickSendMessage" />
        </div>
        <div class="col-2 p-0">
          <v-btn class="send-btn" @click="clickSendMessage" value="전송버튼">
            <i class="fas fa-paper-plane"></i>
          </v-btn>
        </div>
      </div>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";

export default {
  name: "ChatPanel",
  data() {
    return {
      message: "",
      chatHeight: "30vh",
      select: "모두",
    };
  },
  computed: {
    ...mapState("MeetingStore", ["messages"]),
    ...mapGetters(["currentUser"]),
    ...mapGetters("MeetingStore", ["subscribers", "chatItems"]),
  },
  methods: {
    ...mapActions("MeetingStore", ["toggleChatPanel", "sendMessage"]),

    clickSendMessage() {
      const data = { to: [this.select], from: this.currentUser, message: this.message };
      if (data.to[0] === "모두") data.to = [];
      if (this.message.trim()) {
        this.sendMessage(data);
        this.message = "";
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
  background-color: #d1d1d1;
  width: 100%;
  border-radius: 20px;
  color: black;
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
#chat-area {
  overflow-y: auto;
}
#chat-area::-webkit-scrollbar {
  width: 20px;
  height: 30px;
}
/* 스크롤 바닥색*/
#chat-area::-webkit-scrollbar-track {
  background: #37474f;
}
#chat-area::-webkit-scrollbar-corner {
  background: #37474f;
}
/* 스크롤바 색*/
#chat-area::-webkit-scrollbar-thumb {
  background: #6499ff;
}
#chat-area::-webkit-scrollbar-button {
  background-color: #37474f;
  height: 10px;
}
</style>
