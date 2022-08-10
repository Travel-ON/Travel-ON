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
                    <!--자기 얼굴-->
                    <user-video :stream-manager="publisher" @click="$emit(updateMainVideoStreamManager(publisher))" />
                    <!-- 너매 얼굴 -->
                    <user-video
                      v-for="sub in subscribers"
                      :key="sub.stream.connection.connectionId"
                      :stream-manager="sub"
                      @click="$emit(updateMainVideoStreamManager(sub))"
                    />
                  </v-row>
                  <v-row class="mt-8">
                    <v-btn id="btn_video" class="btn mr-2" style="background-color: #6499ff" @click="clickMuteVideo">
                      <div v-if="publisher.stream.videoActive">
                        <v-icon color="white">mdi-video-outline</v-icon> 비디오 중지
                      </div>
                      <div v-else><v-icon color="white">mdi-video-off-outline</v-icon> 비디오 시작</div>
                    </v-btn>

                    <v-btn id="btn_audio" class="btn mr-2" style="background-color: #6499ff" @click="clickMuteAudio"
                      ><div v-if="publisher.stream.audioActive">
                        <v-icon color="white">mdi-microphone-outline</v-icon> 음소거 설정
                      </div>
                      <div v-else><v-icon color="white">mdi-microphone-off</v-icon> 음소거 해제</div></v-btn
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
import UserVideo from "./UserVideo.vue";

axios.defaults.headers.post["Content-Type"] = "application/json";

const OPENVIDU_SERVER_URL = `https://${window.location.hostname}:4443`;
const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: "VideochatRoom",

  components: {
    UserVideo,
  },
  computed: {
    ...mapGetters({
      // resident: "resident",
      currentUser: "currentUser",
      token: "token",
      title: "title",
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
    this.joinSession();
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
        // console.log()
        if (this.hostName === stream.connection.connectionId) {
          console.log("방 종료됨!!!!!");
        }
        if (index >= 0) {
          this.subscribers.splice(index, 1);
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
          .connect(token, { clientData: this.currentUser })
          .then(() => {
            // --- Get your own camera stream with the desired properties ---

            const publisher = this.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });

            this.mainStreamManager = publisher;
            this.publisher = publisher;

            // --- Publish your stream ---

            console.log("hostName");
            console.log(this.hostName);

            console.log("currentUser");
            console.log(this.currentUser);

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
      const bodyTag = document.getElementById("btn_video");
      if (this.publisher.stream.videoActive) {
        this.publisher.publishVideo(false);
        bodyTag.style.backgroundColor = "#979797";
        this.video = false;
      } else {
        this.publisher.publishVideo(true);
        bodyTag.style.backgroundColor = "#6499FF";
        this.video = true;
      }
    },
    clickMuteAudio() {
      const bodyTag = document.getElementById("btn_audio");
      if (this.publisher.stream.audioActive) {
        this.publisher.publishAudio(false);
        bodyTag.style.backgroundColor = "#979797";
        this.audio = false;
      } else {
        this.publisher.publishAudio(true);
        bodyTag.style.backgroundColor = "#6499FF";
        this.audio = true;
      }
    },
    clickCloseRoom() {
      axios({
        url: `http://localhost:3000/api/videochat/${this.roomCode}`,
        method: "delete",
        headers: { Authorization: `Bearer ${this.token}` },
      })
        .then((res) => {
          console.log(res);
          alert("방 종료!");
          this.leaveSession();
        })
        .catch((err) => {
          // alert("이미 있는 아이디 입니다!");
          console.log(err);
        });
    },
    clickLeaveRoom() {
      axios({
        url: `http://localhost:3000/api/videochat/leave/${this.roomCode}`,
        method: "get",
        headers: { Authorization: `Bearer ${this.token}` },
      })
        .then((res) => {
          console.log(res);
          alert("방 나가기!");
          this.leaveSession();
        })
        .catch((err) => {
          // alert("이미 있는 아이디 입니다!");
          console.log(err);
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
