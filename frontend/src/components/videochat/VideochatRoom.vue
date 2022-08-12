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
                      <v-btn id="btn_video" class="btn mr-2" style="background-color: #6499ff" @click="clickMuteVideo">
                        <v-icon color="white">mdi-video-outline</v-icon> 비디오 중지</v-btn
                      >
                    </div>
                    <div v-else>
                      <v-btn id="btn_video" class="btn mr-2" style="background-color: #979797" @click="clickMuteVideo">
                        <v-icon color="white">mdi-video-outline</v-icon> 비디오 시작</v-btn
                      >
                    </div>

                    <div v-if="publisher.stream.audioActive">
                      <v-btn id="btn_audio" class="btn mr-2" style="background-color: #6499ff" @click="clickMuteAudio">
                        <v-icon color="white">mdi-microphone-outline</v-icon> 음소거 설정</v-btn
                      >
                    </div>
                    <div v-else>
                      <v-btn id="btn_audio" class="btn mr-2" style="background-color: #979797" @click="clickMuteAudio">
                        <v-icon color="white">mdi-microphone-off</v-icon> 음소거 해제</v-btn
                      >
                    </div>

                    <v-btn class="btn mr-2" style="background-color: darkblue; color: white" @click="clickSharecode">
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
import { mapGetters } from "vuex";
import axios from "axios";
import { OpenVidu } from "openvidu-browser";
import Swal from "sweetalert2";
import UserVideo from "./UserVideo.vue";

axios.defaults.headers.post["Content-Type"] = "application/json";

// const OPENVIDU_SERVER_URL = `https://${window.location.hostname}:8443`;
// const OPENVIDU_SERVER_SECRET = "ssafy";
const OPENVIDU_SERVER_URL = `https://${window.location.hostname}:4443`;
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: "VideochatRoom",

  components: {
    UserVideo,
  },
  computed: {
    ...mapGetters({
      currentUser: "currentUser",
      currentUserId: "currentUserId",
      token: "token",
      title: "title",
      isLoggedIn: "isLoggedIn",
    }),
  },

  data() {
    return {
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],

      residentMark: this.$route.params.residentMark,
      video: this.$route.params.video,
      audio: this.$route.params.audio,
      roomCode: this.$route.params.roomCode,
      hostName: this.$route.params.hostName,
    };
  },
  created() {
    if (this.isLoggedIn) {
      this.joinSession();
    }
  },
  methods: {
    joinSession() {
      // 오픈비두 세션 초기화
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();
      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        let check = false;
        if (this.hostName === JSON.parse(stream.connection.data).clientName) {
          check = true;
        }
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
        if (check) {
          this.leaveSession();
          Swal.fire("화상채팅방 종료", "호스트에 의해 화상채팅방이 종료되었습니다.", "warning");
          this.$router.push({
            name: "home",
          });
        }
      });

      // On every asynchronous exception...
      this.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });

      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      this.getToken(this.roomCode).then((token) => {
        this.session
          .connect(token, {
            clientName: this.currentUser,
            clientTitle: this.title,
            isResident: this.residentMark,
            hostName: this.hostName,
            isRoom: true,
            clientUserId: this.currentUserId,
          })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            const publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: this.audio === "true", // Whether you want to start publishing with your audio unmuted or not
              publishVideo: this.video === "true", // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- Publish your stream ---

            this.session.publish(this.publisher);
          })
          .catch((error) => {
            console.log("There was an error connecting to the session:", error.code, error.message);
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },

    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      window.removeEventListener("beforeunload", this.leaveSession);
    },

    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },

    // yuna start
    clickMuteVideo() {
      if (this.publisher.stream.videoActive) {
        this.publisher.publishVideo(false);
      } else {
        this.publisher.publishVideo(true);
      }
    },
    clickMuteAudio() {
      if (this.publisher.stream.audioActive) {
        this.publisher.publishAudio(false);
      } else {
        this.publisher.publishAudio(true);
      }
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
            // url: `http://i7b301.p.ssafy.io:3000/api/videochat/${this.roomCode}`,
            url: `http://localhost:3000/api/videochat/${this.roomCode}`,
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
            // url: `http://i7b301.p.ssafy.io:3000/api/videochat/leave/${this.roomCode}`,
            url: `http://localhost:3000/api/videochat/leave/${this.roomCode}`,
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
    clickSharecode() {
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
    // yuna end

    /**
     * --------------------------
     * SERVER-SIDE RESPONSIBILITY
     * --------------------------
     * These methods retrieve the mandatory user token from OpenVidu Server.
     * This behavior MUST BE IN YOUR SERVER-SIDE IN PRODUCTION (by using
     * the API REST, openvidu-java-client or openvidu-node-client):
     *   1) Initialize a Session in OpenVidu Server	(POST /openvidu/api/sessions)
     *   2) Create a Connection in OpenVidu Server (POST /openvidu/api/sessions/<SESSION_ID>/connection)
     *   3) The Connection.token must be consumed in Session.connect() method
     */

    getToken(roomCode) {
      return this.createSession(roomCode).then((sessionId) => this.createToken(sessionId));
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            },
          )
          .then((response) => response.data)
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`,
              );
              if (
                // eslint-disable-next-line no-alert
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`,
                )
              ) {
                Location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },

    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-connection
    createToken(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            {},
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            },
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => reject(error.response));
      });
    },
  },
};
</script>
