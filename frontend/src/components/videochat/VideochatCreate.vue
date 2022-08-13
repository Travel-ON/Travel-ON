<template>
  <v-container v-if="publisher" id="main-container" class="container">
    <v-row id="join">
      <v-col id="join-dialog" class="jumbotron vertical-center">
        <v-btn class="btn mr-2" @click="clickHome">홈으로</v-btn>
        <h1>방 만들기 화면</h1>
        <v-row style="margin-top: 20px">
          <v-col style="background-color: skyblue">
            <div>현위치: {{ sido }} {{ gugun }} {{ dong }} <v-icon x-small>mdi-map-marker-radius</v-icon></div>
            <h3 class="mt-3">설정</h3>
            <v-row class="setting" style="background-color: white; margin: 5px">
              <v-container fluid>
                <v-container class="px-0" fluid>
                  <v-checkbox v-model="secretRoom" label="비공개 설정"></v-checkbox>
                </v-container>
                <v-container class="px-0" fluid>
                  <v-select v-model="select" :items="counts" label="최대 인원"></v-select>
                </v-container>
                <div>지역범위 설정</div>
                <v-radio-group v-model="areaScope" mandatory>
                  <v-radio :label="`시: ${sido}`" value="sido"></v-radio>
                  <v-radio :label="`구: ${gugun}`" value="gugun"></v-radio>
                  <v-radio :label="`동: ${dong}`" value="dong"></v-radio>
                </v-radio-group>
                <div v-if="resident">
                  <v-container class="px-0" fluid>
                    <v-checkbox v-model="residentMark" label="현지인 마크 표시하기"></v-checkbox>
                  </v-container>
                </div>
              </v-container>
            </v-row>
          </v-col>
          <v-col>
            <div class="form-group">
              <p>[미리보기 화면]</p>
              <p class="text-center">
                <v-container class="px-0" fluid>
                  <v-row style="position: relative" justify="center">
                    <user-video :stream-manager="publisher" @click="$emit(updateMainVideoStreamManager(publisher))" />
                    <v-col
                      v-if="resident && residentMark"
                      style="
                        width: 130px;
                        position: absolute;
                        top: 10px;
                        right: 50%;
                        background-color: #6499ff;
                        color: white;
                        padding: 1px 10px;
                      "
                    >
                      <v-icon>mdi-clover</v-icon> 현지인 <v-icon>mdi-clover</v-icon>
                    </v-col>
                  </v-row>
                </v-container>
              </p>
              <div style="display: flex">
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
                <v-btn class="btn mr-2" @click="clickCreateRoom">방 만들기</v-btn>
              </div>
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
import UserVideo from "./UserVideo.vue";

axios.defaults.headers.post["Content-Type"] = "application/json";

export default {
  name: "VideochatCreate",

  components: {
    UserVideo,
  },
  computed: {
    ...mapGetters(["sido", "gugun", "dong", "dongCode", "resident", "currentUser", "token", "title", "isLoggedIn"]),
    ...mapState("MeetingStore", ["OV", "session", "mainStreamManager", "publisher", "subscribers", "mySessionId"]),
  },

  data() {
    return {
      areaScope: "sido",
      residentMark: true,
      select: 4,
      counts: [4, 6, 8],
      secretRoom: false,
      video: true,
      audio: true,
    };
  },
  created() {
    if (this.isLoggedIn) {
      this.joinSession();
    }
  },
  methods: {
    ...mapActions("MeetingStore", [
      "joinSession",
      "leaveSession",
      "updateMainVideoStreamManager",
      "toggleVideo",
      "toggleVideo",
    ]),
    toggleVideo() {
      if (this.publisher.stream.videoActive) {
        this.publisher.publishVideo(false);
        this.video = false;
      } else {
        this.publisher.publishVideo(true);
        this.video = true;
      }
    },
    toggleAudio() {
      if (this.publisher.stream.audioActive) {
        this.publisher.publishAudio(false);
        this.audio = false;
      } else {
        this.publisher.publishAudio(true);
        this.audio = true;
      }
    },
    clickCreateRoom() {
      axios({
        url: "http://localhost:3000/api/videochat/",
        // url: "http://i7b301.p.ssafy.io:3000/api/videochat/",
        method: "post",
        headers: { Authorization: `Bearer ${this.token}` },
        data: {
          dongCode: this.dongCode,
          areaScope: this.areaScope,
          privateFlag: this.secretRoom,
          count: this.select,
        },
      })
        .then((res) => {
          this.leaveSession();
          this.$router.push({
            name: "VideochatRoom",
            params: {
              residentMark: this.residentMark,
              video: this.video,
              audio: this.audio,
              roomCode: res.data.roomCode,
              hostName: res.data.hostName,
            },
          });
        })
        .catch((err) => {
          Swal.fire({
            icon: "error",
            title: "방만들기 실패!",
            showConfirmButton: false,
            timer: 1000,
          });
          console.log(err);
        });
    },
    clickHome() {
      this.leaveSession();
      this.$router.push({
        name: "home",
      });
    },
  },
};
</script>
