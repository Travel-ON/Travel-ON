import { OpenVidu } from "openvidu-browser";
import axios from "axios";
import Swal from "sweetalert2";
import moment from "moment";
// import { startsWith } from "core-js/core/string";
import router from "@/router";

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
    hostName: "",
    mySessionId: "impermanent_session",
    residentMark: false,
    videoFlag: "true",
    audioFlag: "true",
    // chatting
    isChatPanel: false,
    messages: [],
    // 입장할때 이름이 전부떠서 체크해주기위한 변수
    isNewbie: true,
  },
  getters: {
    chatItems(state) {
      const items = state.subscribers.map(function (val) {
        return JSON.parse(val.stream.connection.data).clientName;
      });
      return ["모두", ...items];
    },
    subscribers: (state) => state.subscribers,
  },
  mutations: {
    // Openvidu
    SET_OV(state, OV) {
      state.OV = OV;
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
    SET_SESSION_ID(state, mySessionId) {
      state.mySessionId = mySessionId;
    },
    SET_HOST_NAME(state, hostName) {
      state.hostName = hostName;
    },
    SET_RESIDENT_MARK(state, residentMark) {
      state.residentMark = residentMark;
    },
    SET_VIDEO_FLAG(state, videoFlag) {
      state.videoFlag = videoFlag;
    },
    SET_AUDIO_FLAG(state, audioFlag) {
      state.audioFlag = audioFlag;
    },
    SET_IS_NEWBIE(state, value) {
      state.isNewbie = value;
    },
    // chatting
    SET_IS_CHATPANEL(state, value) {
      state.isChatPanel = value;
    },
    SET_MESSAGES(state, messages) {
      // state.messages.push(data);
      state.messages = messages;
    },
  },
  actions: {
    setSessionID({ commit }, roomCode) {
      commit("SET_SESSION_ID", roomCode);
    },
    setHostName({ commit }, hostName) {
      commit("SET_HOST_NAME", hostName);
    },
    setResidentMark({ commit }, residentMark) {
      commit("SET_RESIDENT_MARK", residentMark);
    },
    setVideoFlag({ commit }, videoFlag) {
      commit("SET_VIDEO_FLAG", videoFlag);
    },
    setAudioFlag({ commit }, audioFlag) {
      commit("SET_AUDIO_FLAG", audioFlag);
    },
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
    joinSession({ state, commit, dispatch, rootGetters }) {
      // 오픈비두 세션 초기화
      // --- Get an OpenVidu object ---
      // const OV = new OpenVidu();
      commit("SET_OV", new OpenVidu());
      // --- Init a session ---
      // const session = state.OV.initSession();
      // --- Specify the actions when events take place in the session ---
      commit("SET_SESSION", state.OV.initSession());
      // On every new Stream received...
      // const subscribers = [];
      // commit("SET_SUBSCRIBERS", subscribers);
      state.session.on("streamCreated", ({ stream }) => {
        const subscriber = state.session.subscribe(stream);
        state.subscribers.push(subscriber);
        if (!state.isNewbie) {
          const data = {
            from: "SYSTEM",
            to: [],
            message: `🎉${JSON.parse(stream.connection.data).clientName}님이 입장하였습니다🎉`,
          };
          dispatch("sendMessage", data);
        }
      });

      // On every Stream destroyed...
      state.session.on("streamDestroyed", ({ stream }) => {
        const index = state.subscribers.indexOf(stream.streamManager, 0);

        let check = false;
        console.log("누가나갔다!!");
        console.log(state.hostName);
        console.log(stream.connection.data);
        console.log(JSON.parse(stream.connection.data).clientName);
        console.log("====================================");
        if (state.hostName === JSON.parse(stream.connection.data).clientName) {
          check = true;
        }

        if (index >= 0) {
          state.subscribers.splice(index, 1);
        }

        if (check) {
          dispatch("leaveSession");
          Swal.fire("화상채팅방 종료", "호스트에 의해 화상채팅방이 종료되었습니다.", "warning");
          router.push({
            name: "home",
          });
        } else {
          const data = {
            from: "SYSTEM",
            to: [],
            message: `✋${JSON.parse(stream.connection.data).clientName}님이 퇴장하였습니다✋`,
          };
          dispatch("sendMessage", data);
        }
      });

      // On every asynchronous exception...
      state.session.on("exception", ({ exception }) => {
        console.warn(exception);
      });
      // --- Connect to the session with a valid user token ---

      // 'getToken' method is simulating what your server-side should do.
      // 'token' parameter should be retrieved and returned by your own backend
      dispatch("getToken", state.mySessionId).then((token) => {
        state.session
          .connect(token, {
            clientName: rootGetters.currentUser,
            clientTitle: rootGetters.title,
            isResident: state.residentMark,
            hostName: state.hostName,
            isRoom: true,
            clientUserId: rootGetters.currentUserId,
          })
          .then(() => {
            const publisher = state.OV.initPublisher(undefined, {
              audioSource: undefined, // The source of audio. If undefined default microphone
              videoSource: undefined, // The source of video. If undefined default webcam
              publishAudio: state.audioFlag === "true", // Whether you want to start publishing with your audio unmuted or not
              publishVideo: state.videoFlag === "true", // Whether you want to start publishing with your video enabled or not
              resolution: "640x480", // The resolution of your video
              frameRate: 30, // The frame rate of your video
              insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
              mirror: false, // Whether to mirror your local video or not
            });
            // commit("SET_OV", OV);
            commit("SET_MAINSTREAMMANAGER", publisher);
            commit("SET_PUBLISHER", publisher);
            // commit("SET_SESSION", session);
            // commit("SET_SUBSCRIBERS", subscribers);
            // commit("SET_OVTOKEN", token);

            // this.mainStreamManager = publisher;
            // this.publisher = publisher;
            state.session.publish(state.publisher);

            state.session.on("signal:chat", (event) => {
              const eventData = JSON.parse(event.data);
              const data = {};
              const time = new Date();
              data.message = eventData.message;
              data.sender = eventData.from;
              if (eventData.to[0] === undefined) data.receiver = "모두";
              // eslint-disable-next-line prefer-destructuring
              else data.receiver = eventData.to[0];
              // data.sender = JSON.parse(event.from.data).clientName;
              data.time = moment(time).format("HH:mm");
              if (
                data.sender === rootGetters.currentUser ||
                data.receiver === rootGetters.currentUser ||
                data.receiver === "모두"
              ) {
                state.messages.push(data);
              }
            });
          })
          .catch((error) => {
            console.log("There was an error connecting to the session:", error.code, error.message);
          });
      });
      window.addEventListener("beforeunload", this.leaveSession);
    },
    leaveSession({ state, commit }) {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
      if (state.session) state.session.disconnect();

      commit("SET_SESSION", undefined);
      commit("SET_MAINSTREAMMANAGER", undefined);
      commit("SET_PUBLISHER", undefined);
      commit("SET_SUBSCRIBERS", []);
      commit("SET_OV", undefined);
      commit("SET_SESSION_ID", "impermanent_session");
      commit("SET_HOST_NAME", "");
      commit("SET_RESIDENT_MARK", false);
      commit("SET_VIDEO_FLAG", "true");
      commit("SET_AUDIO_FLAG", "true");
      commit("SET_IS_CHATPANEL", false);
      commit("SET_MESSAGES", []);

      window.removeEventListener("beforeunload", this.leaveSession);
    },
    toggleVideo({ state }) {
      if (state.publisher.stream.videoActive) {
        state.publisher.publishVideo(false);
        // this.video = false;
      } else {
        state.publisher.publishVideo(true);
        // this.audio = true;
      }
    },
    changeIsNewbie({ commit }) {
      commit("SET_IS_NEWBIE", false);
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
    sendMessage({ state }, data) {
      state.session.signal({
        type: "chat",
        data: JSON.stringify(data),
      });
    },
  },
};
