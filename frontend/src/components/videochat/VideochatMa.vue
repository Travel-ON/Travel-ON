<template>
  <v-container v-if="publisher" id="main-container" class="container">
    <v-row id="join">
      <v-col id="join-dialog" class="jumbotron vertical-center">
        <h1>방 매칭 화면</h1>
        <v-row style="margin-top: 20px">
          <v-col style="background-color: skyblue">
            <div>현위치: {{ sido }} {{ gugun }} {{ dong }} <v-icon x-small>mdi-map-marker-radius</v-icon></div>
            <h3 class="mt-3">설정</h3>
            <v-row class="setting" style="background-color: white; margin: 5px">
              <v-container fluid>
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
            <!-- </div> -->
          </v-col>
          <v-col>
            <div class="form-group">
              <!-- <v-text-field label="Participant" v-model="myUserName" class="form-control" type="text" required /> -->
              <!-- <v-text-field label="session" v-model="mySessionId" class="form-control" type="text" required /> -->
              <p>[미리보기 화면]</p>
              <p class="text-center">
                <!-- <v-btn class="btn btn-lg btn-success" @click="joinSession()">Join!</v-btn> -->

                <user-video :stream-manager="publisher" @click="$emit(updateMainVideoStreamManager(publisher))" />
                <v-container class="px-0" fluid>
                  <div v-if="title && title != ''">{{ title }}</div>
                  <div v-if="resident && residentMark">현지인</div>
                </v-container>
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
                <v-btn class="btn mr-2" @click="clickMatchingRoom">방 매칭하기</v-btn>
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

const OPENVIDU_SERVER_URL = `https://${window.location.hostname}:8443`;
const OPENVIDU_SERVER_SECRET = "ssafy";
// const OPENVIDU_SERVER_URL = `https://${window.location.hostname}:4443`;
// const OPENVIDU_SERVER_SECRET = "MY_SECRET";

export default {
  name: "VideochatMa",

  components: {
    UserVideo,
  },
  computed: {
    ...mapGetters({
      sido: "sido",
      gugun: "gugun",
      dong: "dong",
      dongCode: "dongCode",
      resident: "resident",
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
      areaScope: null,
      residentMark: true,
      video: true,
      audio: true,

      mySessionId: "impermanent_session",
      // myUserName: `닉네임들어갈곳`,
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
      this.getToken(this.mySessionId).then((token) => {
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
    // 지역 범위
    // 비디오 중지
    // 음소거 설정
    clickMatchingRoom() {
      axios({
        // url: "http://localhost:3000/api/videochat/match",
        url: "http://i7b301.p.ssafy.io:3000/api/videochat/match",
        method: "post",
        headers: { Authorization: `Bearer ${this.token}` },
        data: {
          dongCode: this.dongCode,
          areaScope: this.areaScope,
        },
      })
        .then((res) => {
          // console.log(res);
          // console.log("res.data:  ", res.data);
          // this.idChecked = id;
          alert("방 매칭 완료!");
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
          // this.joinSession();
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

    getToken(mySessionId) {
      return this.createSession(mySessionId).then((sessionId) => this.createToken(sessionId));
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
