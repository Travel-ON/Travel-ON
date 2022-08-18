<template>
  <v-container v-if="publisher" id="main-container" class="container" style="max-width: 1200px">
    <v-row id="join">
      <v-col id="join-dialog" class="jumbotron vertical-center">
        <h1 style="border-bottom: 4px solid #adadad">방 매칭하기</h1>
        <v-row style="margin: 20px 0; background-color: #d1e6fb; border-radius: 8px">
          <v-col style="background-color: skyblue; border-radius: 8px 0 0 8px; border-right: 4px solid #fff" cols="4">
            <div>
              <div
                style="
                  background-color: #3e4e6c;
                  color: white;
                  padding: 12px 8px;
                  border-radius: 8px;
                  margin-bottom: 12px;
                "
              >
                {{ `현위치: ` }}
                <span style="color: #f4a84d">{{ `${sido} ` }} </span>
                <span style="color: #03dac5">{{ `${gugun} ` }} </span>
                <span style="color: #b1e3ff">{{ `${dong} ` }} </span>
                <v-icon x-small>mdi-map-marker-radius</v-icon>
              </div>
              <div class="setting" style="background-color: white; border-radius: 8px">
                <v-container fluid>
                  <div style="margin-bottom: 20px; font-size: 24px; border-bottom: 2px solid #adadad">
                    지역범위 설정
                  </div>
                  <v-radio-group v-model="areaScope" mandatory hide-details style="margin-bottom: 32px">
                    <v-radio :label="`시: ${sido}`" value="sido"></v-radio>
                    <v-radio :label="`구: ${gugun}`" value="gugun"></v-radio>
                    <v-radio :label="`동: ${dong}`" value="dong"></v-radio>
                  </v-radio-group>
                  <div style="margin-bottom: 20px; font-size: 24px; border-bottom: 2px solid #adadad">기타 설정</div>
                  <div v-if="resident">
                    <v-container class="px-0" fluid style="padding: 0">
                      <v-checkbox
                        v-model="residentMark"
                        label="현지인 마크 표시하기"
                        density="compact"
                        hide-details
                        style="text-align: left"
                      ></v-checkbox>
                    </v-container>
                  </div>
                </v-container>
              </div>
            </div>
          </v-col>
          <v-col cols="8" style="display: flex; justify-content: center; align-items: center">
            <div class="form-group">
              <div style="font-size: 24px; color: #1b2d47">[미리보기 화면]</div>
              <div class="text-center" style="width: 100%">
                <v-container class="px-0" fluid>
                  <v-row style="position: relative" justify="center">
                    <user-video :stream-manager="publisher" />
                    <v-col
                      v-if="resident && residentMark"
                      style="
                        position: absolute;
                        width: auto;
                        top: 20px;
                        right: 50%;
                        transform: translate(+50%, 0);
                        background-color: #6499ff;
                        border-radius: 8px;
                        border: 2px solid #fff;
                        color: white;
                        padding: 1px 10px;
                      "
                    >
                      <v-icon>mdi-clover</v-icon> 현지인 <v-icon>mdi-clover</v-icon>
                    </v-col>
                  </v-row>
                </v-container>
              </div>
              <div style="display: flex; justify-content: center; margin-top: 12px">
                <div v-if="publisher.stream.videoActive">
                  <v-btn
                    id="btn_video"
                    class="btn mr-2"
                    style="background-color: #6499ff; color: #fff; padding: 4px 12px; border: 2px solid #fff"
                    @click="toggleVideo"
                  >
                    <v-icon color="white">mdi-video-outline</v-icon>&nbsp;비디오 중지</v-btn
                  >
                </div>
                <div v-else>
                  <v-btn
                    id="btn_video"
                    class="btn mr-2"
                    style="background-color: #979797; padding: 4px 12px; border: 2px solid #fff"
                    @click="toggleVideo"
                  >
                    <v-icon color="white">mdi-video-outline</v-icon>&nbsp;비디오 시작</v-btn
                  >
                </div>

                <div v-if="publisher.stream.audioActive">
                  <v-btn
                    id="btn_audio"
                    class="btn mr-2"
                    style="background-color: #6499ff; color: #fff; padding: 4px 12px; border: 2px solid #fff"
                    @click="toggleAudio"
                  >
                    <v-icon color="white">mdi-microphone-outline</v-icon>&nbsp;음소거 설정</v-btn
                  >
                </div>
                <div v-else>
                  <v-btn
                    id="btn_audio"
                    class="btn mr-2"
                    style="background-color: #979797; padding: 4px 12px; border: 2px solid #fff"
                    @click="toggleAudio"
                  >
                    <v-icon color="white">mdi-microphone-off</v-icon>&nbsp;음소거 해제</v-btn
                  >
                </div>
              </div>
            </div>
          </v-col>
        </v-row>
        <v-btn class="btn mr-2" @click="clickHome" style="margin-top: 24px; border: 2px solid #adadad" size="x-large"
          ><v-icon>mdi-home </v-icon>&nbsp;홈으로</v-btn
        >
        <v-btn
          class="btn mr-2"
          style="margin-top: 24px; border: 2px solid #adadad"
          @click="clickMatchingRoom"
          size="x-large"
          ><v-icon>mdi-check-outline </v-icon>&nbsp;방 매칭하기</v-btn
        >
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";
import spring from "@/api/spring_boot";
import axios from "axios";
import Swal from "sweetalert2";
import UserVideo from "./UserVideo.vue";

axios.defaults.headers.post["Content-Type"] = "application/json";

export default {
  name: "VideochatMatching",

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
    ...mapActions("MeetingStore", ["joinSession", "leaveSession", "toggleVideo", "toggleVideo"]),
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
    clickMatchingRoom() {
      axios({
        url: spring.videochat.match(),
        method: "post",
        headers: { Authorization: `Bearer ${this.token}` },
        data: {
          dongCode: this.dongCode,
          areaScope: this.areaScope,
        },
      })
        .then((res) => {
          this.leaveSession();

          if (res.data.playGame === "true") {
            Swal.fire({
              icon: "warning",
              title: "현재 이 방은 게임중입니다!",
              showConfirmButton: false,
              timer: 1000,
            });
            axios({
              url: spring.videochat.leave(res.data.roomCode),
              method: "get",
              headers: { Authorization: `Bearer ${this.token}` },
            })
              .then((response) => {
                console.log(response);
                this.$router.push({
                  name: "home",
                });
              })
              .catch((err) => {
                console.log(err);
              });
          }
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
            title: "방 매칭 실패!",
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
