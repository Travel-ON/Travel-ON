<template>
  <div style="height: 100vh; width: 100vw; background-color: #50a0f0; padding: 12px 32px 12px 12px; overflow: hidden">
    <div style="height: 100%; width: 100%; background-color: #fff; border-radius: 8px; display: flex">
      <div v-if="publisher" style="flex: 3; padding: 36px 0 36px 36px">
        <div style="border-right: 4px solid #d1e6fb; height: 100%">
          <div style="display: flex; flex-direction: column; height: 100%; justify-content: space-between">
            <!-- 비디오 그룹 시작 -->
            <div
              id="video-container"
              style="
                flex: 1;
                display: flex;
                justify-content: space-around;
                align-items: center;
                flex-direction: column;
                flex-wrap: wrap;
                margin-right: 36px;
              "
            >
              <div>
                <user-video
                  :stream-manager="publisher"
                  :class="{ 'col-12': one, 'col-6': two, 'col-4': three }"
                  @click="$emit(updateMainVideoStreamManager(publisher))"
                  :style="roulettePointer === currentUser ? 'border: 10px solid yellow' : ''"
                />
              </div>
              <div v-for="sub in subscribers" :key="sub.stream.connection.connectionId">
                <user-video
                  :style="
                    roulettePointer === JSON.parse(sub.stream.connection.data).clientName
                      ? 'border: 10px solid yellow'
                      : ''
                  "
                  :stream-manager="sub"
                  :class="{ 'col-12': one, 'col-6': two, 'col-4': three }"
                />
              </div>
            </div>
            <!-- 버튼 그룹 시작 -->
            <div
              style="
                height: 64px;
                display: flex;
                justify-content: space-around;
                align-items: flex-end;
                margin-right: 36px;
              "
            >
              <div v-if="publisher.stream.videoActive">
                <v-btn
                  id="btn_video"
                  class="btn mr-2"
                  style="background-color: #50a0f0; color: #fff; padding: 4px 12px; border: 2px solid #fff"
                  @click="toggleVideo"
                  rounded="pill"
                  size="large"
                >
                  <v-icon color="white">mdi-video-outline</v-icon>&nbsp;비디오 중지</v-btn
                >
              </div>
              <div v-else>
                <v-btn
                  id="btn_video"
                  class="btn mr-2"
                  style="background-color: #adadad; color: #fff; padding: 4px 12px; border: 2px solid #fff"
                  @click="toggleVideo"
                  rounded="pill"
                  size="large"
                >
                  <v-icon color="white">mdi-video-outline</v-icon>&nbsp;비디오 시작</v-btn
                >
              </div>
              <div v-if="publisher.stream.audioActive">
                <v-btn
                  id="btn_audio"
                  class="btn mr-2"
                  style="background-color: #50a0f0; color: #fff; padding: 4px 12px; border: 2px solid #fff"
                  @click="toggleAudio"
                  rounded="pill"
                  size="large"
                >
                  <v-icon color="white">mdi-microphone-outline</v-icon>&nbsp;음소거 설정</v-btn
                >
              </div>
              <div v-else>
                <v-btn
                  id="btn_audio"
                  class="btn mr-2"
                  style="background-color: #adadad; color: #fff; padding: 4px 12px; border: 2px solid #fff"
                  @click="toggleAudio"
                  rounded="pill"
                  size="large"
                >
                  <v-icon color="white">mdi-microphone-off</v-icon>&nbsp;음소거 해제</v-btn
                >
              </div>
              <div>
                <v-btn
                  class="btn mr-2"
                  style="background-color: #50a0f0; color: #fff; padding: 4px 12px; border: 2px solid #fff"
                  @click="clickSharecode"
                  rounded="pill"
                  size="large"
                >
                  <v-icon color="white">mdi-share</v-icon>&nbsp;방코드 확인</v-btn
                >
              </div>
              <div v-if="!playGame">
                <v-btn
                  class="btn mr-2"
                  style="background-color: #285078; color: #fff; padding: 4px 12px; border: 2px solid #fff"
                  @click="clickPlayGame"
                  rounded="pill"
                  size="large"
                >
                  <v-icon color="white">mdi-controller</v-icon>&nbsp;게임하기</v-btn
                >
              </div>
              <div v-if="startLiarTalkFlag">
                <v-btn
                  class="btn mr-2"
                  style="background-color: #285078; color: #fff; padding: 4px 12px; border: 2px solid #fff"
                  @click="startLiarTalk"
                  rounded="pill"
                  size="large"
                >
                  <v-icon color="white">mdi-controller</v-icon>&nbsp;대화시작</v-btn
                >
              </div>
              <div v-if="stopLiarTalkFlag">
                <v-btn
                  class="btn mr-2"
                  style="background-color: #285078; color: #fff; padding: 4px 12px; border: 2px solid #fff"
                  @click="stopLiarTalk"
                  rounded="pill"
                  size="large"
                >
                  <v-icon color="white">mdi-controller</v-icon>&nbsp;대화종료</v-btn
                >
              </div>
              <div v-if="hostName === currentUser">
                <v-btn
                  class="btn mr-2"
                  style="background-color: #f05a50; color: #fff; padding: 4px 12px; border: 2px solid #fff"
                  @click="clickCloseRoom"
                  rounded="pill"
                  size="large"
                  ><v-icon color="white">mdi-close-thick</v-icon>&nbsp;종료</v-btn
                >
              </div>
              <div v-else>
                <v-btn
                  class="btn mr-2"
                  style="background-color: #f05a50; color: #fff; padding: 4px 12px; border: 2px solid #fff"
                  @click="clickLeaveRoom"
                  rounded="pill"
                  size="large"
                  ><v-icon color="white">mdi-close</v-icon>&nbsp;나가기</v-btn
                >
              </div>
            </div>
            <!-- 버튼 그룹 끝 -->
          </div>
        </div>
      </div>
      <div style="flex: 1; padding: 0 0 36px">
        <!-- 우측 그룹 -->
        <div style="display: flex; justify-content: space-between; flex-direction: column-reverse; height: 100%">
          <!-- 사이드 버튼 그룹 -->
          <div style="display: flex; justify-content: space-around">
            <div>
              <div>
                <v-btn
                  class="btn mr-2"
                  style="background-color: #50a0f0; color: #fff; padding: 4px 12px; border: 2px solid #fff"
                  rounded="pill"
                  size="large"
                  @click="toggleChatPanel()"
                >
                  채팅 화면
                </v-btn>
              </div>
            </div>
            <div>
              <div>
                <v-btn
                  class="btn mr-2"
                  style="background-color: #50a0f0; color: #fff; padding: 4px 12px; border: 2px solid #fff"
                  rounded="pill"
                  size="large"
                  @click="toggleGamePanel()"
                >
                  게임 진행 화면
                </v-btn>
              </div>
            </div>
          </div>
          <div style="flex: 1; display: flex; flex-direction: column">
            <div class="right-panel" v-if="isGamePanel" style="flex: 1">
              <GamePanel class="game-panel" v-if="isGamePanel"> </GamePanel>
            </div>
            <div class="right-panel" v-if="isChatPanel" style="flex: 1">
              <ChatPanel class="chat-panel" v-if="isChatPanel"> </ChatPanel>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
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

      // liar
      "startLiarTalkFlag",
      "stopLiarTalkFlag",
      "playGame",
      "roulettePointer",
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
  watch: {
    // subscribers() {
    //   this.addClass();
    // },
  },
  data() {
    return {
      one: true,
      two: false,
      three: false,
      eight: false,
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
  mounted() {
    setTimeout(() => {
      this.changeIsNewbie();
    }, 3000);
    document.getElementById("body").style.overflow = "hidden";
  },
  methods: {
    ...mapActions("MeetingStore", [
      "joinSession",
      "leaveSession",
      "toggleVideo",
      "toggleAudio",
      "setSessionID",
      "setHostName",
      "setResidentMark",
      "setVideoFlag",
      "setAudioFlag",
      "toggleChatPanel",
      "changeIsNewbie",
      "toggleGamePanel",
      "startLiar",
      "startLiarTalk",
      "stopLiarTalk",
      "startRoulette",
    ]),
    // addClass() {
    //   const count = this.subscribers.length + 1;
    //   if (count === 1) {
    //     this.one = true;
    //     this.two = false;
    //     this.three = false;
    //     this.eight = false;
    //     this.$nextTick(() => {
    //       const videos = this.$refs.querySelectorAll("video");
    //       for (let i = 0, len = videos.length; i < len; i += 1) {
    //         videos[i].classList.add("height70");
    //         videos[i].classList.remove("height30");
    //         videos[i].classList.remove("height15");
    //       }
    //     });
    //   } else if (count === 2 || count === 4) {
    //     this.one = false;
    //     this.two = true;
    //     this.three = false;
    //     this.eight = false;
    //     if (count === 2) {
    //       this.$nextTick(() => {
    //         const videos = this.$refs.querySelectorAll("video");
    //         for (let i = 0, len = videos.length; i < len; i += 1) {
    //           videos[i].classList.add("height70");
    //           videos[i].classList.remove("height30");
    //           videos[i].classList.remove("height15");
    //         }
    //       });
    //     } else {
    //       this.$nextTick(() => {
    //         const videos = this.$refs.querySelectorAll("video");
    //         for (let i = 0, len = videos.length; i < len; i += 1) {
    //           videos[i].classList.add("height30");
    //           videos[i].classList.remove("height70");
    //           videos[i].classList.remove("height15");
    //         }
    //       });
    //     }
    //   } else if (count === 3 || count === 5 || count === 6) {
    //     this.one = false;
    //     this.two = false;
    //     this.three = true;
    //     this.eight = false;
    //     if (count === 3) {
    //       this.$nextTick(() => {
    //         const videos = this.$refs.querySelectorAll("video");
    //         for (let i = 0, len = videos.length; i < len; i += 1) {
    //           videos[i].classList.add("height70");
    //           videos[i].classList.remove("height30");
    //           videos[i].classList.remove("height15");
    //         }
    //       });
    //     } else {
    //       this.one = false;
    //       this.two = false;
    //       this.three = false;
    //       this.eight = true;
    //       this.$nextTick(() => {
    //         const videos = this.$refs.querySelectorAll("video");
    //         for (let i = 0, len = videos.length; i < len; i += 1) {
    //           videos[i].classList.add("height30");
    //           videos[i].classList.remove("height70");
    //           videos[i].classList.remove("height15");
    //         }
    //       });
    //     }
    //   } else {
    //     this.$nextTick(() => {
    //       const videos = this.$refs.querySelectorAll("video");
    //       for (let i = 0, len = videos.length; i < len; i += 1) {
    //         videos[i].classList.add("height15");
    //         videos[i].classList.remove("height70");
    //         videos[i].classList.remove("height30");
    //       }
    //     });
    //   }
    // },
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
      if (this.subscribers.length < 2) {
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
        title: "어떤 게임을 하고 싶은가요?",
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
      if (game === "liar") {
        this.startLiar();
      } else {
        this.clickPlayRoulette();
      }
    },
    clickPlayRoulette() {
      this.startRoulette();
    },
  },
};
</script>
<style></style>
