<template>
  <v-container v-if="publisher" id="main-container" class="container">
    <v-row id="join">
      <v-col id="join-dialog" class="jumbotron vertical-center">
        <h1>채팅방화면</h1>
        <v-row style="margin-top: 20px">
          <v-col>
            <div class="form-group">
              <p class="text-center">
                <v-col>
                  <v-row id="video-container">
                    <user-video :stream-manager="publisher" />
                    <user-video
                      v-for="sub in subscribers"
                      :key="sub.stream.connection.connectionId"
                      :stream-manager="sub"
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

                    <v-btn class="btn mr-2" style="background-color: darkblue; color: white" @click="clickShareCode">
                      <v-icon color="white">mdi-share</v-icon> 방코드 확인</v-btn
                    >

                    <v-btn class="btn mr-2" style="background-color: darkblue; color: white" @click="clickPlayGame">
                      <v-icon color="white">mdi-controller</v-icon> 게임하기</v-btn
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
  </v-container>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";
import axios from "axios";
import Swal from "sweetalert2";
import UserVideo from "@/components/openvidu/UserVideo.vue";

axios.defaults.headers.post["Content-Type"] = "application/json";

export default {
  data() {
    return {
      residentMark: this.$route.params.residentMark,
      video: this.$route.params.video,
      audio: this.$route.params.audio,
      roomCode: this.$route.params.roomCode,
      hostName: this.$route.params.hostName,
    };
  },
  name: "LeftPanel",

  components: {
    UserVideo,
  },
  computed: {
    ...mapGetters(["currentUser", "token", "title"]),
    ...mapState("MeetingStore", ["OV", "session", "publisher", "subscribers", "mainStreamManager"]),
  },

  methods: {
    ...mapActions("MeetingStore", ["joinSession", "toggleVideo", "toggleAudio", "leaveSession"]),
    clickShareCode() {
      Swal.fire("방 코드 🔑", `${this.roomCode}`, "info", {
        button: "확인",
      });
    },
    clickPlayGame() {
      Swal.fire({
        title: "게임하고 싶으신가요?",
        text: "사람들에게 동의를 구하고 게임을 시작해보세요!",
        icon: "question",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        confirmButtonText: "게임신청",
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire("게임신청!", "신청기능구현해라~", "success");
        }
      });
    },
    clickCloseRoom() {
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
            // url: `http://localhost:3000/api/videochat/${this.roomCode}`,
            url: `http://i7b301.p.ssafy.io:3000/api/videochat/${this.roomCode}`,
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
    },
    clickLeaveRoom() {
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
            // url: `http://localhost:3000/api/videochat/leave/${this.roomCode}`,
            url: `http://i7b301.p.ssafy.io:3000/api/videochat/leave/${this.roomCode}`,
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
    },
  },
};
</script>
