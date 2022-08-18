<template>
  <v-container class="game-panel" style="height: 100%">
    <div style="display: flex; flex-direction: column; height: 100%">
      <div style="padding: 8px; color: white; background-color: #50a0f0; border-radius: 8px; margin-bottom: 4px">
        게임 진행
      </div>
      <!-- 채팅 내역 -->
      <div
        id="game-area"
        :style="`
          height: 100%;
          border-radius: 8px;
          border: 2px solid #50a0f0;
          padding: 12px;
          background-color: #d1e6fb;
          flex: 1;
          max-height: ${isChatPanel ? 'calc(50vh - 196px)' : 'calc(100vh - 186px)'};
        `"
      >
        <div
          class="mt-2 text-left message"
          v-for="(gameCommentary, i) of gameCommentarys"
          :key="i"
          style="margin-bottom: 8px"
        >
          <div>
            {{ gameCommentary.comment }}
          </div>
        </div>
      </div>
      <div class="footer d-flex mt-auto"></div>
    </div>
  </v-container>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";

export default {
  name: "GamePanel",
  data() {
    return {
      gameCommentary: "",
      gameHeight: "30vh",
    };
  },
  watch: {
    gameCommentarys(val, oldVal) {
      console.log("do stuff", val, oldVal);
      setTimeout(() => {
        const gameDiv = document.getElementById("game-area");
        gameDiv.scrollTo({
          top: gameDiv.scrollHeight - gameDiv.clientHeight,
          behavior: "smooth",
        });
      }, 50);
    },
  },
  computed: {
    ...mapState("MeetingStore", ["gameCommentarys"]),
    ...mapGetters("MeetingStore", ["isChatPanel", "isGamePanel"]),
  },
  methods: {
    ...mapActions("MeetingStore", ["toggleGamePanel"]),
  },
};
</script>

<style scoped>
.game-box {
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
#game-area {
  overflow-y: auto;
}
#game-area::-webkit-scrollbar {
  width: 12px;
  height: 12px;
}
#game-area::-webkit-scrollbar-track {
  background: #d1e6fb;
}
#game-area::-webkit-scrollbar-corner {
  background: #50a0f0;
}
#game-area::-webkit-scrollbar-thumb {
  background: #50a0f0;
  border-radius: 4px;
}
#game-area::-webkit-scrollbar-button {
  background-color: #50a0f0;
  border-radius: 4px;
  height: 12px;
}
</style>
