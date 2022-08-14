<template>
  <v-container class="game-panel">
    <v-row class="game-box p-2 d-flex flex-column h-100">
      <v-col class="header text-left">
        <span class="title"> 게임 진행 </span>
      </v-col>
      <!-- 채팅 내역 -->
      <v-col id="game-area" :height="gameHeight">
        <v-row class="mt-2 text-left message" v-for="(gameCommentary, i) of gameCommentarys" :key="i">
          <div>
            {{ gameCommentary.comment }}
          </div>
        </v-row>
      </v-col>
      <div class="footer d-flex mt-auto"></div>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions, mapState } from "vuex";

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
#game-area::-webkit-scrollbar {
  width: 15px;
  height: 15px;
}
#game-area::-webkit-scrollbar-track {
  background: #37474f;
}
#game-area::-webkit-scrollbar-corner {
  background: #37474f;
}
#game-area::-webkit-scrollbar-thumb {
  background: #b0a2c8;
}
#game-area::-webkit-scrollbar-button {
  background-color: #37474f;
  height: 10px;
}
</style>
