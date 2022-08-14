<template>
  <v-container v-if="publisher" id="main-container" class="container">
    <v-row id="join">
      <v-col id="join-dialog" class="jumbotron vertical-center">
        <h1>채팅방화면</h1>
        <div>{{ mySessionId }}</div>
        <v-row style="margin-top: 20px">
          <v-col>
            <div class="form-group">
              <p class="text-center">
                <v-col>
                  <v-row id="video-container">
                    <user-video :stream-manager="publisher" @click="$emit(updateMainVideoStreamManager(publisher))" />
                    <user-video
                      v-for="sub in subscribers"
                      :key="sub.stream.connection.connectionId"
                      :stream-manager="sub"
                      @click="$emit(updateMainVideoStreamManager(sub))"
                    />
                  </v-row>
                  <v-row class="mt-8">
                    <div v-if="publisher.stream.videoActive">
                      <v-btn id="btn_video" class="btn mr-2" style="background-color: #6499ff" @click="toggleVideo">
                        <v-icon color="white">mdi-video-outline</v-icon> 비디오 중지</v-btn
                      >
                    </div>
                    <div v-else>
                      <v-btn id="btn_video" class="btn mr-2" style="background-color: #979797" @click="toggleVideo">
                        <v-icon color="white">mdi-video-outline</v-icon> 비디오 시작</v-btn
                      >
                    </div>

                    <div v-if="publisher.stream.audioActive">
                      <v-btn id="btn_audio" class="btn mr-2" style="background-color: #6499ff" @click="toggleAudio">
                        <v-icon color="white">mdi-microphone-outline</v-icon> 음소거 설정</v-btn
                      >
                    </div>
                    <div v-else>
                      <v-btn id="btn_audio" class="btn mr-2" style="background-color: #979797" @click="toggleAudio">
                        <v-icon color="white">mdi-microphone-off</v-icon> 음소거 해제</v-btn
                      >
                    </div>

                    <v-btn class="btn mr-2" style="background-color: darkblue; color: white" @click="clickSharecode">
                      <v-icon color="white">mdi-share</v-icon> 방코드 확인</v-btn
                    >

                    <v-btn
                      class="btn mr-2"
                      v-if="!playGame"
                      style="background-color: darkblue; color: white"
                      @click="clickPlayGame"
                    >
                      <v-icon color="white">mdi-controller</v-icon> 게임하기</v-btn
                    >
                    <v-btn
                      class="btn mr-2"
                      v-if="startLiarTalkFlag"
                      style="background-color: darkblue; color: white"
                      @click="startLiarTalk"
                    >
                      <v-icon color="white">mdi-controller</v-icon> 대화시작</v-btn
                    >
                    <v-btn
                      class="btn mr-2"
                      v-if="stopLiarTalkFlag"
                      style="background-color: darkblue; color: white"
                      @click="stopLiarTalk"
                    >
                      <v-icon color="white">mdi-controller</v-icon> 대화종료</v-btn
                    >
                    <v-btn v-if="hostName === currentUser" class="btn mr-2" @click="clickCloseRoom">종료</v-btn>
                    <v-btn v-else class="btn mr-2" @click="clickLeaveRoom">나가기</v-btn>
                  </v-row>
                </v-col>
              </p>
            </div>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
    <v-row>
      <!--      <v-col id="capture" :class="{ 'col-8': isChatPanel, 'col-12': !isChatPanel }"> </v-col>-->

      <v-col class="right-panel" v-if="isChatPanel">
        <ChatPanel class="chat-panel" height="800px" v-if="isChatPanel"> </ChatPanel>
      </v-col>
      <v-col class="right-panel" v-if="isGamePanel">
        <GamePanel class="game-panel" height="800px" v-if="isGamePanel"> </GamePanel>
      </v-col>
    </v-row>
  </v-container>
  <v-footer dark padless>
    <v-card class="flex-grow-1" tile>
      <v-card-title class="teal">
        <v-btn v-for="icon in icons" :key="icon" class="mx-4" dark>
          <v-icon size="24px">
            {{ icon }}
          </v-icon>
        </v-btn>
      </v-card-title>
      <v-row>
        <v-col>
          <v-card-text class="py-2 white--text text-center">
            {{ new Date().getFullYear() }} — <strong>Vuetify</strong>
          </v-card-text>
        </v-col>
        <v-col>
          <v-card-text>
            <v-btn class="btn mr-2" @click="toggleChatPanel()"> 채팅온오프 </v-btn>
          </v-card-text>
        </v-col>
        <v-col>
          <v-card-text>
            <v-btn class="btn mr-2" @click="toggleGamePanel()"> 게임진행화면 </v-btn>
          </v-card-text>
        </v-col>
      </v-row>
    </v-card>
  </v-footer>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";
import spring from "@/api/spring_boot";
import axios from "axios";
import Swal from "sweetalert2";
import ChatPanel from "@/components/videochat/chatPanel.vue";
import GamePanel from "@/components/videochat/gamePanel.vue";
import UserVideo from "./UserVideo.vue";

axios.defaults.headers.post["Content-Type"] = "application/json";

export default {
  name: "VideochatRoom",

  components: {
    UserVideo,
    ChatPanel,
    GamePanel,
  },
  computed: {
    ...mapState("MeetingStore", [
      "OV",
      "session",
      "mainStreamManager",
      "publisher",
      "subscribers",
      "mySessionId",
      "isChatPanel",
      "isGamePanel",
      "hostName",
      "startLiarTalkFlag",
      "stopLiarTalkFlag",
      "playGame",
    ]),
    ...mapGetters([
      "sido",
      "gugun",
      "dong",
      "dongCode",
      "resident",
      "currentUser",
      "currentUserId",
      "token",
      "title",
      "isLoggedIn",
    ]),
  },

  data() {
    return {
      residentMark: this.$route.params.residentMark,
      video: this.$route.params.video,
      audio: this.$route.params.audio,
      roomCode: this.$route.params.roomCode,
      hostName: this.$route.params.hostName,
    };
  },
  created() {
    if (this.isLoggedIn) {
      this.setSessionID(this.roomCode);
      this.setHostName(this.hostName);
      this.setResidentMark(this.residentMark);
      this.setVideoFlag(this.video);
      this.setAudioFlag(this.audio);
      this.joinSession();
    }
  },
  methods: {
    ...mapActions("MeetingStore", [
      "joinSession",
      "leaveSession",
      "updateMainVideoStreamManager",
      "toggleVideo",
      "toggleAudio",
      "setSessionID",
      "setHostName",
      "setResidentMark",
      "setVideoFlag",
      "setAudioFlag",
      "toggleChatPanel",
      "toggleGamePanel",
      "startLiar",
      "startLiarTalk",
      "stopLiarTalk",
    ]),
    clickCloseRoom() {
      if (this.playGame) {
        Swal.fire({
          title: "게임중에는 종료할 수 없습니다!",
          icon: "error",
        });
      } else {
        Swal.fire({
          title: "화상채팅방을 종료하실건가요?",
          text: "종료하려면 OK를 눌러주세요!",
          icon: "warning",
          showCancelButton: true,
          buttons: true,
          dangerMode: true,
        }).then((result) => {
          if (result.isConfirmed) {
            axios({
              url: spring.videochat.room(this.roomCode),
              method: "delete",
              headers: { Authorization: `Bearer ${this.token}` },
            })
              .then((res) => {
                console.log(res);
                this.leaveSession();

                Swal.fire({
                  icon: "success",
                  title: "화상채팅방이 종료되었습니다!",
                  showConfirmButton: false,
                  timer: 1000,
                });
                this.$router.push({
                  name: "home",
                });
              })
              .catch((err) => {
                console.log(err);
              });
          }
        });
      }
    },
    clickLeaveRoom() {
      if (this.playGame) {
        Swal.fire({
          title: "게임중에는 나갈 수 없습니다!",
          icon: "error",
        });
      } else {
        Swal.fire({
          title: "화상채팅방을 나가실건가요?",
          text: "나가려면 OK를 눌러주세요!",
          icon: "warning",
          showCancelButton: true,
          buttons: true,
          dangerMode: true,
        }).then((result) => {
          if (result.isConfirmed) {
            axios({
              url: spring.videochat.leave(this.roomCode),
              method: "get",
              headers: { Authorization: `Bearer ${this.token}` },
            })
              .then((res) => {
                console.log(res);
                this.leaveSession();
                Swal.fire({
                  icon: "success",
                  title: "화상채팅방이 종료되었습니다!",
                  showConfirmButton: false,
                  timer: 1000,
                });
                this.$router.push({
                  name: "home",
                });
              })
              .catch((err) => {
                // alert("이미 있는 아이디 입니다!");
                console.log(err);
              });
          }
        });
      }
    },
    copyToClipboard() {
      const t = document.createElement("textarea");
      document.body.appendChild(t);
      t.value = `${this.roomCode}`;
      t.select();
      document.execCommand("copy");
      document.body.removeChild(t);
    },
    clickSharecode() {
      Swal.fire("방 코드 🔑", `${this.roomCode}`, "info", {
        button: "확인",
      });
      Swal.fire({
        title: "방 코드 🔑",
        html: `방코드를 공유해 사람들을 초대해보세요!<br> ${this.roomCode}`,
        icon: "info",
        showCancelButton: true,
        confirmButtonText: "코드복사",
        cancelButtonText: "확인",
      }).then((result) => {
        if (result.isConfirmed) {
          this.copyToClipboard();
          Swal.fire({
            icon: "success",
            text: "화상채팅방 코드가 복사되었습니다!",
            showConfirmButton: false,
            timer: 1000,
          });
        }
      });
    },
    clickPlayGame() {
      if (this.subscribers.length < 1) {
        Swal.fire({
          icon: "error",
          title: "게임은 3인 이상만 가능합니다",
          showConfirmButton: false,
          timer: 1000,
        });
      } else {
        Swal.fire({
          title: "게임하고 싶으신가요?",
          text: "사람들에게 동의를 구하고 게임을 시작해보세요!",
          icon: "question",
          showCancelButton: true,
          confirmButtonText: "게임신청",
          cancelButtonText: "취소",
        }).then((result) => {
          if (result.isConfirmed) {
            // 게임 시작하면 게임하기 버튼 비활성화
            this.selectGame();
          }
        });
      }
    },
    async selectGame() {
      const { value: game } = await Swal.fire({
        title: "Select color",
        input: "radio",
        inputOptions: { liar: "라이어게임", roulette: "룰렛게임" },
        showCancelButton: true,
        inputValidator: (value) => {
          if (!value) {
            return "게임을 선택하세요!";
          }
          return "";
        },
      });
      Swal.fire({ html: `You selected: ${game}` });
      if (game === "liar") {
        this.startLiar();
      }
    },
  },
};
</script>
