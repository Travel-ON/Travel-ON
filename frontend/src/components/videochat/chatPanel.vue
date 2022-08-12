<template>
  <v-container class="chat-panel">
    <v-row class="chat-box p-2 d-flex flex-column h-100">
      <v-col class="header text-left">
        <span class="title"> 채팅 </span>
      </v-col>
      <!-- 채팅 내역 -->
      <v-col id="chat-area" :height="chatHeight">
        <v-row class="mt-2 text-left message" v-for="(message, i) of messages" :key="i">
          <div class="message-title">
            <span class="mr-2 message-header">{{ message.sender }}</span>
            <span class="message-header">{{ message.time }}</span>
          </div>
          <div>
            {{ message.message }}
          </div>
        </v-row>
      </v-col>
      <div class="footer d-flex mt-auto">
        <div class="col-10 px-1 py-0">
          <input class="text-box" v-model="message" @keyup.enter="clickSendMessage" />
        </div>
        <div class="col-2 p-0">
          <v-btn class="send-btn" @click="clickSendMessage">
            <i class="fas fa-paper-plane"></i>
          </v-btn>
        </div>
      </div>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions, mapState } from "vuex";

export default {
  name: "ChatPanel",
  data() {
    return {
      message: "",
      chatHeight: "33vh",
    };
  },
  watch: {
    messages(val, oldVal) {
      console.log("do stuff", val, oldVal);
      setTimeout(() => {
        const chatDiv = document.getElementById("chat-area");
        chatDiv.scrollTo({
          top: chatDiv.scrollHeight - chatDiv.clientHeight,
          behavior: "smooth",
        });
      }, 50);
    },
  },
  computed: {
    ...mapState("MeetingStore", ["messages"]),
  },
  methods: {
    ...mapActions("MeetingStore", ["toggleChatPanel", "sendMessage"]),

    clickSendMessage() {
      if (this.message.trim()) {
        this.sendMessage(this.message);
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
.close-btn {
  position: absolute;
  color: white;
  top: 3px;
  right: 10px;
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
  width: 15px;
  height: 15px;
}
#chat-area::-webkit-scrollbar-track {
  background: #37474f;
}
#chat-area::-webkit-scrollbar-corner {
  background: #37474f;
}
#chat-area::-webkit-scrollbar-thumb {
  background: #b0a2c8;
}
#chat-area::-webkit-scrollbar-button {
  background-color: #37474f;
  height: 10px;
}
</style>
