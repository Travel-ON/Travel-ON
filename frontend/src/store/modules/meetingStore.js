import { createApi } from "@/api";

import { OpenVidu } from "openvidu-browser";
import axios from "axios";
import Swal from "sweetalert2";
import moment from "moment";
// import router from "@/router";
const api = createApi();
const OPENVIDU_SERVER_URL = `https://${window.location.hostname}:4443`;
const OPENVIDU_SERVER_SECRET = "MY_SECRET";
axios.defaults.headers.post["Content-Type"] = "application/json";

export const MeetingStore = {
  namespaced: true,
  state: {
    OV: undefined,
    session: undefined,
    publisher: undefined,
    subscribers: [],
    mainStreamManager: undefined,
    // chatting
    isChatPanel: false,
    messages: [],
    secretRoom: false,
    mySessionId: "impermanent_session",
  },
  getters: {
    messages: (state) => state.messages,
  },
  mutations: {
    // Openvidu
    SET_OV(state, OV) {
      state.OV = OV;
    },
    SET_OVTOKEN(state, token) {
      state.ovToken = token;
    },
    SET_SESSION(state, session) {
      state.session = session;
    },
    SET_MAINSTREAMMANAGER(state, mainStreamManager) {
      state.mainStreamManager = mainStreamManager;
    },
    SET_PUBLISHER(state, publisher) {
      state.publisher = publisher;
    },
    SET_SUBSCRIBERS(state, subscribers) {
      state.subscribers = subscribers;
    },

    // chatting
    SET_IS_CHATPANEL(state, value) {
      state.isChatPanel = value;
    },
    SET_MESSAGES(state, data) {
      state.messages.push(data);
    },
  },
  actions: {
    getToken({ dispatch }, mySessionId) {
      return dispatch("createSession", mySessionId).then((sessionId) => dispatch("createToken", sessionId));
    },
    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-session
    createSession(context, sessionId) {
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
    createToken(context, sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions/${sessionId}/connection`,
            JSON.stringify({
              session: sessionId,
            }),
            {
              headers: {
                "Content-Type": "application/json",
              },
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            },
          )
          .then((response) => response.data)
          .then((data) => resolve(data.token))
          .catch((error) => {
            Swal.fire({
              title: "오류가 발생했습니다. 입장 정보를 다시 한 번 확인해주세요.",
              icon: "error",
            });
            reject(error.response);
          });
      });
    },
    // pre meeting
    createSessionId({ dispatch }) {
      api({
        url: `/videochat/`,
        method: "POST",
        headers: { Authorization: `Bearer ${this.token}` },
        data: {
          dongCode: this.dongCode,
          areaScope: this.areaScope,
          privateFlag: this.secretRoom,
          count: this.select,
        },
      }).then((res) => {
        // 긁어온거 copy
        dispatch("joinSession", {
          residentMark: this.residentMark,
          video: this.video,
          audio: this.audio,
          code: res.data.roomCode,
          hostName: res.data.hostName,
        });
      });
    },
    enterSession() {},
    joinSession({ state, commit, dispatch, rootGetters }) {
      // 오픈비두 세션 초기화
      // --- Get an OpenVidu object ---
      const OV = new OpenVidu();
      // --- Init a session ---
      const session = OV.initSession();
      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      const subscribers = [];
      session.on("streamCreated", ({ stream }) => {
        const subscriber = session.subscribe(stream);
        subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      session.on("streamDestroyed", ({ stream }) => {
        const index = subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          subscribers.splice(index, 1);
        }
      });

      // On every asynchronous exception...
      session.on("exception", ({ exception }) => {
        console.warn(exception);
      });
      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      dispatch("getToken", state.roomCode).then((token) => {
        session
          .connect(token, { clientName: rootGetters.currentUser, clientTitle: this.title })
          .then(() => {
            const publisher = OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
              publishVideo: true, // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });
            commit("SET_OV", OV);
            commit("SET_MAINSTREAMMANAGER", publisher);
            commit("SET_PUBLISHER", publisher);
            commit("SET_SESSION", session);
            commit("SET_SUBSCRIBERS", subscribers);
            commit("SET_OVTOKEN", token);

            this.mainStreamManager = publisher;
            this.publisher = publisher;
            state.session.publish(state.publisher);

            state.session.on("signal:chat", (event) => {
              const eventData = JSON.parse(event.data);
              const data = {};
              const time = new Date();
              data.message = eventData.content;
              data.sender = event.from.data.slice(15, -2);
              data.time = moment(time).format("HH:mm");
              commit("SET_MESSAGES", data);
            });
          })
          .catch((error) => {
            console.log("There was an error connecting to the session:", error.code, error.message);
          });
      });

      window.addEventListener("beforeunload", this.leaveSession);
    },
    leaveSession() {
    leaveSession({ state, commit }) {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      state.publisher.stream.disposeWebRtcPeer();
      state.publisher.stream.disposeMediaStream();
      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;
      commit("SET_SESSION", undefined);
      commit("SET_MAINSTREAMMANAGER", undefined);
      commit("SET_PUBLISHER", undefined);
      commit("SET_SUBSCRIBERS", []);
      commit("SET_OV", undefined);

      window.removeEventListener("beforeunload", this.leaveSession);
    },
    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },
    toggleVideo({ state }) {
      if (state.publisher.stream.videoActive) {
        state.publisher.publishVideo(false);
        this.video = false;
      } else {
        state.publisher.publishVideo(true);
        this.audio = true;
      }
    },
    toggleAudio() {
      if (this.publisher.stream.audioActive) {
        this.publisher.publishAudio(false);
      } else {
        this.publisher.publishAudio(true);
      }
    },
    toggleChatPanel({ state, commit }) {
      commit("SET_IS_CHATPANEL", !state.isChatPanel);
      if (state.isChatPanel === true) {
        setTimeout(() => {
          const chatDiv = document.getElementById("chat-area");
          chatDiv.scrollTo({
            top: chatDiv.scrollHeight - chatDiv.clientHeight,
            behavior: "smooth",
          });
        }, 50);
      }
    },
    sendMessage({ state }, message) {
      const messageData = {
        content: message,
        secretName: state.secretName,
      };
      console.log(`세션 출력 ${state.session}`);
      console.log(`메세지 출력 ${message}`);
      state.session.signal({
        type: "chat",
        data: JSON.stringify(messageData),
        to: [],
      });
    },
  },
};
