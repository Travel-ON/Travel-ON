/* eslint-disable no-return-assign */
import spring from "@/api/spring_boot";
import { OpenVidu } from "openvidu-browser";
import axios from "axios";
import Swal from "sweetalert2";
import moment from "moment";
import router from "@/router";
import kakao from "@/api/kakao_api";

const OPENVIDU_SERVER_URL = `https://${window.location.hostname}:4443`;
// const OPENVIDU_SERVER_URL = `https://${window.location.hostname}:8443`;

const OPENVIDU_SERVER_SECRET = "MY_SECRET";
// const OPENVIDU_SERVER_SECRET = "ssafy";
axios.defaults.headers.post["Content-Type"] = "application/json";

export const MeetingStore = {
  namespaced: true,
  state: {
    OV: undefined,
    session: undefined,
    publisher: undefined,
    subscribers: [],
    mainStreamManager: undefined,

    mySessionId: "impermanent_session",

    hostName: "",
    residentMark: false,
    videoFlag: "true",
    audioFlag: "true",
    // chatting
    isChatPanel: false,
    messages: [],

    // 입장할때 이름이 전부떠서 체크해주기위한 변수
    isNewbie: true,

    // game
    playGame: false,
    isGamePanel: false,
    gameName: "",
    gameCommentarys: [],
    participants: [],

    // liar
    topic: "",
    keyword: "",
    liar: "",
    startLiarTalkFlag: false,
    stopLiarTalkFlag: false,
    votes: [],
    voteCount: 0,

    // roulette
    rouletteTargetName: "",
    roulettePointer: "",
  },
  getters: {
    chatItems(state) {
      const items = state.subscribers.map(function (val) {
        return JSON.parse(val.stream.connection.data).clientName;
      });
      return ["모두", ...items];
    },
    subscribers: (state) => state.subscribers,
    subscribersCount: (state) => state.subscribers.length,
    isChatPanel: (state) => state.isChatPanel,
    isGamePanel: (state) => state.isGamePanel,
    hostFlex(state) {
      let result;
      if (state.subscribers.length <= 2) {
        result = 60;
      } else {
        result = Math.floor(100 / Math.floor((state.subscribers.length + 1) / 2));
      }
      return result;
    },
    subFlex(state) {
      let result;
      if (state.subscribers.length < 2) {
        result = 40;
      } else {
        result = Math.floor(100 / (Math.floor(state.subscribers.length / 2) + 1));
      }
      return result;
    },
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
    // game
    SET_PLAY_GAME(state, value) {
      state.playGame = value;
    },
    SET_IS_GAMEPANEL(state, value) {
      state.isGamePanel = value;
    },
    SET_GAME_COMMANTARY(state, commentary) {
      state.gameCommentarys = commentary;
    },
    SET_GAME_PARTICIPANTS(state, participants) {
      state.participants = participants;
    },
    SET_GAME_NAME(state, value) {
      state.gameName = value;
    },

    // liar
    SET_LIAR_TOPIC(state, topic) {
      state.topic = topic;
    },
    SET_LIAR_KEYWORD(state, keyword) {
      state.keyword = keyword;
    },
    SET_LIAR(state, liar) {
      state.liar = liar;
    },
    SET_VOTES(state, votes) {
      state.votes = votes;
    },
    SET_VOTE_COUNT(state, voteCount) {
      state.voteCount = voteCount;
    },

    // roulette
    SET_ROULETTE_TARGET_NAME(state, rouletteTargetName) {
      state.rouletteTargetName = rouletteTargetName;
    },
    SET_ROULETTE_POINTER(state, roulettePointer) {
      state.roulettePointer = roulettePointer;
    },
  },
  actions: {
    setGameName({ commit }, gameName) {
      commit("SET_GAME_NAME", gameName);
    },
    setPlayGame({ commit }, status) {
      commit("SET_PLAY_GAME", status);
    },
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
      commit("SET_OV", new OpenVidu());
      // --- Init a session ---
      // --- Specify the actions when events take place in the session ---
      commit("SET_SESSION", state.OV.initSession());
      // On every new Stream received...
      state.session.on("streamCreated", ({ stream }) => {
        const subscriber = state.session.subscribe(stream);
        state.subscribers.push(subscriber);
        if (!state.isNewbie) {
          const data = {
            sender: "SYSTEM",
            receiver: "모두",
            time: moment(new Date()).format("HH:mm"),
            message: `🎉${JSON.parse(stream.connection.data).clientName}님이 입장하였습니다🎉`,
          };
          state.messages.push(data);
        }
      });

      // On every Stream destroyed...
      state.session.on("streamDestroyed", ({ stream }) => {
        const index = state.subscribers.indexOf(stream.streamManager, 0);

        let check = false;
        if (
          state.sessionId !== "impermanent_session" &&
          state.hostName === JSON.parse(stream.connection.data).clientName
        ) {
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
            sender: "SYSTEM",
            time: moment(new Date()).format("HH:mm"),
            receiver: "모두",
            message: `✋${JSON.parse(stream.connection.data).clientName}님이 퇴장하였습니다✋`,
          };
          state.messages.push(data);
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
            commit("SET_MAINSTREAMMANAGER", publisher);
            commit("SET_PUBLISHER", publisher);

            state.session.publish(state.publisher);
            state.session.on("signal:chat", (event) => {
              const eventData = JSON.parse(event.data);
              const data = {};
              const time = new Date();
              data.message = eventData.message;
              data.sender = eventData.from;
              // 강퇴
              if (eventData.type === "kickout") {
                if (eventData.to === rootGetters.currentUser) {
                  dispatch("leaveSession");
                  Swal.fire("화상채팅방 강퇴", "호스트에 의해 화상채팅방에서 강퇴되었습니다.", "warning");
                  router.push({
                    name: "home",
                  });
                }
                data.sender = "SYSTEM";
                data.message = `✋${eventData.to}님을 강퇴하였습니다✋`;
              }
              if (eventData.to[0] === undefined) data.receiver = "모두";
              // eslint-disable-next-line prefer-destructuring
              else data.receiver = eventData.to[0];
              data.time = moment(time).format("HH:mm");
              if (eventData.isHashTag) {
                axios({
                  url: kakao.region.imageSearch(),
                  headers: { Authorization: "KakaoAK a7cedeb35de4c99731ff3ee0bc0ade21" },
                  method: "GET",
                  params: {
                    query: eventData.message,
                    sort: "accuracy",
                    size: 4,
                  },
                })
                  .then((res) => {
                    data.url = res.data.documents[0].image_url;
                    data.doc_url = `https://search.naver.com/search.naver?where=image&query=${data.message}`;
                    if (
                      data.sender === rootGetters.currentUser ||
                      data.receiver === rootGetters.currentUser ||
                      data.receiver === "모두"
                    ) {
                      state.messages.push(data);
                    }
                  })
                  .catch((err) => {
                    console.log(err);
                  });
              } else if (
                data.sender === rootGetters.currentUser ||
                data.receiver === rootGetters.currentUser ||
                data.receiver === "모두"
              ) {
                state.messages.push(data);
              }
            });

            state.session.on("signal:game", (event) => {
              const eventData = JSON.parse(event.data);
              const Toast = Swal.mixin({
                toast: true,
                position: "top-end",
                showConfirmButton: false,
                timer: 3000,
              });

              if (eventData.gameId === "liar") {
                if (eventData.step === 1) {
                  // 라이어 게임 시작
                  dispatch("startGame");
                  dispatch("setGameName", "라이어");
                  state.gameCommentarys.push({ comment: "📣 라이어게임을 시작합니다!!!" });
                  state.gameCommentarys.push({ comment: "📣 방장이 주제를 선택중입니다.." });
                  if (state.hostName === rootGetters.currentUser) {
                    axios({
                      url: spring.videochat.liarTopic(),
                      method: "get",
                      headers: { Authorization: `Bearer ${rootGetters.token}` },
                    })
                      .then(({ data }) => {
                        dispatch("selectTopic", data);
                      })
                      .catch((err) => {
                        Swal.fire({
                          icon: "error",
                          title: "주제 조회 실패!",
                          showConfirmButton: false,
                          timer: 1000,
                        });
                        console.log(err);
                      });
                  }
                } else if (eventData.step === 2) {
                  commit("SET_GAME_PARTICIPANTS", eventData.content.participants);
                  commit("SET_LIAR_TOPIC", eventData.content.topic);
                  commit("SET_LIAR_KEYWORD", eventData.content.keyword);
                  commit("SET_LIAR", eventData.content.liar);
                  // 방장이 주제 선택 및 세팅
                  // 키워드 및 라이어 알림
                  state.gameCommentarys.push({ comment: `📣 주제는 ${state.topic}입니다.` });
                  if (state.liar === rootGetters.currentUser) {
                    // 라이어 - 라이어 알림
                    Swal.fire({
                      title: "🤫 라이어 🤫",
                      html: `키워드를 아는 것처럼 사람들을 속여보세요!`,
                      icon: "warning",
                    });
                  } else {
                    // 일반 - 키워드 전송
                    Swal.fire({
                      title: `🤭 키워드: ${state.keyword} 🤭`,
                      html: `사람들과 이야기를 나누며 라이어를 찾아보세요!`,
                      icon: "warning",
                    });
                  }
                  state.gameCommentarys.push({
                    comment: `📣 키워드를 확인했다면 방장이 하단 [대화 시작] 버튼을 눌러 대화를 나눠보세요!`,
                  });
                  if (state.hostName === rootGetters.currentUser) {
                    // 이야기 시간 3분 시작 시그널
                    state.participants.forEach(function (participant) {
                      state.votes[participant] = 0;
                    });
                    state.startLiarTalkFlag = true;
                  }
                } else if (eventData.step === 3) {
                  // 이야기 시간 3분 시작 시그널
                  state.gameCommentarys.push({
                    comment: `📣 대화가 시작됐습니다. 돌아가며 키워드에 대해 이야기해보세요! 과연 라이어는 누구일까요?`,
                  });
                  Toast.fire({
                    icon: "success",
                    title: `📣 대화가 시작됐습니다.`,
                  });
                  // 3분 후 또는 이야기종료 버튼으로 투표 시그널 전달
                } else if (eventData.step === 4) {
                  // 3분 후 또는 이야기종료 버튼으로 투표 시그널 전달
                  if (eventData.content.again) {
                    state.gameCommentarys.push({
                      comment: `📣 최다득표자가 2명이상으로 재투표해주세요! `,
                    });
                  } else {
                    state.gameCommentarys.push({
                      comment: `📣 대화가 종료됐습니다. 라이어를 찾았나요?`,
                    });
                  }
                  dispatch("clickVote");
                  // 방장에게 투표전달(전체 시그널로 투표완료자 인원수 보이기)
                } else if (eventData.step === 5) {
                  // 방장에게 투표전달(전체 시그널로 투표완료자 인원수 보이기)
                  if (state.hostName === rootGetters.currentUser) {
                    state.votes[eventData.content.selector] += 1;
                    commit("SET_VOTE_COUNT", state.voteCount + 1);

                    if (state.voteCount === state.participants.length) {
                      // 투표 종료
                      const sortable = [];
                      // eslint-disable-next-line
                      for (var name in state.votes) {
                        sortable.push([name, state.votes[name]]);
                      }
                      sortable.sort(function (a, b) {
                        return b[1] - a[1];
                      });

                      if (sortable[0][1] === sortable[1][1]) {
                        commit("SET_VOTE_COUNT", 0);
                        commit("SET_VOTES", []);
                        state.participants.forEach(function (participant) {
                          state.votes[participant] = 0;
                        });
                        const gameData = {
                          gameId: "liar",
                          step: 4,
                          content: { again: true },
                        };
                        state.session.signal({
                          type: "game",
                          data: JSON.stringify(gameData),
                          to: [],
                        });
                      } else {
                        const gameData = {
                          gameId: "liar",
                          step: 6,
                          content: {
                            vote1st: sortable[0],
                            vote2nd: sortable[1],
                            vote3rd: sortable[2],
                            findLiar: state.liar === sortable[0][0],
                          },
                        };
                        state.session.signal({
                          type: "game",
                          data: JSON.stringify(gameData),
                          to: [],
                        });
                      }
                    }
                    // 참여자 전체 투표가 완료되면 투표결과 시그널 전송
                  }
                } else if (eventData.step === 6) {
                  // 참여자 전체 투표가 완료되면 투표결과 시그널 전송
                  let htmlText = `<h3>라이어는 ${state.liar}님</h3>`;
                  // <br>1st 라미 2표<br>2nd 김영희 1표<br>3rd 홍길동 1표
                  htmlText += `<br>1st ${eventData.content.vote1st[0]} ${eventData.content.vote1st[1]}표`;
                  if (eventData.content.vote2nd[1] > 0) {
                    htmlText += `<br>2nd ${eventData.content.vote2nd[0]} ${eventData.content.vote2nd[1]}표`;
                    if (eventData.content.vote3rd[1] > 0) {
                      if (eventData.content.vote2nd[1] === eventData.content.vote3rd[1]) {
                        htmlText += `<br>2nd ${eventData.content.vote3rd[0]} ${eventData.content.vote3rd[1]}표`;
                      } else {
                        htmlText += `<br>3rd ${eventData.content.vote3rd[0]} ${eventData.content.vote3rd[1]}표`;
                      }
                    }
                  }
                  if (eventData.content.findLiar) {
                    Swal.fire({
                      icon: "success",
                      title: "라이어 찾기 성공!!",
                      html: htmlText,
                      allowOutsideClick: false,
                    }).then((result) => {
                      if (result.isConfirmed) {
                        state.gameCommentarys.push({
                          comment: `📣 라이어는 키워드를 입력해주세요!`,
                        });
                        if (state.liar === rootGetters.currentUser) {
                          dispatch("clickKeyword");
                        }
                      }
                    });
                  } else {
                    Swal.fire({
                      icon: "error",
                      title: "라이어 찾기 실패!!",
                      html: htmlText,
                      allowOutsideClick: false,
                    }).then((result) => {
                      if (result.isConfirmed) {
                        if (state.liar === rootGetters.currentUser) {
                          dispatch("clickGameResult", true);
                        } else {
                          dispatch("clickGameResult", false);
                        }
                        dispatch("endLiar");
                      }
                    });
                  }
                } else if (eventData.step === 7) {
                  state.gameCommentarys.push({
                    comment: `📣 라이어가 키워드를 입력했습니다! 과연 키워드를 맞췄을까요? `,
                  });
                  // 라이어를 맞췄을 경우 라이어 키워드 입력 후 방장에게 전송
                  if (eventData.content.answer === state.keyword) {
                    if (state.liar === rootGetters.currentUser) {
                      dispatch("clickGameResult", true);
                    } else {
                      dispatch("clickGameResult", false);
                    }
                  } else if (state.liar === rootGetters.currentUser) {
                    dispatch("clickGameResult", false);
                  } else {
                    dispatch("clickGameResult", true);
                  }
                  dispatch("endLiar");
                }
              } else if (eventData.gameId === "roulette") {
                if (eventData.step === 1) {
                  // 룰렛 게임 시작
                  dispatch("startGame");
                  dispatch("setGameName", "룰렛");
                  state.gameCommentarys.push({ comment: "📣 룰렛게임을 시작합니다!!!" });
                  if (state.hostName === rootGetters.currentUser) {
                    const participants = [];
                    participants.push(state.hostName);
                    state.subscribers.forEach(function (subscriber) {
                      participants.push(JSON.parse(subscriber.stream.connection.data).clientName);
                    });
                    const gameData = {
                      gameId: "roulette",
                      step: 2,
                      content: {
                        participants,
                        targetName: participants[Math.floor(Math.random() * participants.length)],
                      },
                    };
                    state.session.signal({
                      type: "game",
                      data: JSON.stringify(gameData),
                      to: [],
                    });
                  }
                } else if (eventData.step === 2) {
                  // 룰렛 셋팅
                  commit("SET_GAME_PARTICIPANTS", eventData.content.participants);
                  commit("SET_ROULETTE_TARGET_NAME", eventData.content.targetName);

                  dispatch("playRoulette");
                }
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
      commit("SET_IS_GAMEPANEL", false);
      commit("SET_GAME_COMMANTARY", []);

      window.removeEventListener("beforeunload", this.leaveSession);
    },
    toggleVideo({ state }) {
      if (state.publisher.stream.videoActive) {
        state.publisher.publishVideo(false);
      } else {
        state.publisher.publishVideo(true);
      }
    },
    changeIsNewbie({ commit }) {
      commit("SET_IS_NEWBIE", false);
    },
    toggleAudio({ state }) {
      if (state.publisher.stream.audioActive) {
        state.publisher.publishAudio(false);
      } else {
        state.publisher.publishAudio(true);
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
    /* ... 게임중 사람들이 들어오거나 나가는 경우 생각! 라이어 게임은 3인 이상 가능
      -- 방장이 진행자 겸 참여자 (라이어는 모르지만 게임 진행은 함) --
      step 1. [시작누른사람 -> 전체 - 수동] 게임시작하기
      step 2. [방장 -> 전체 - 수동] 방장이 주제 선택 및 세팅 (주제 및 키워드 전달, 참여자 리스트, 라이어 선택) => 키워드 및 라이어 알림
      step 3. [방장 -> 전체 - 수동] 이야기 시간 3분 시작 시그널
      step 4. [방장 -> 전체 - 자동 or 수동] 3분 후 또는 이야기종료 버튼으로 투표 시그널 전달
      step 5. [전체 -> 전체 - 수동] 방장에게 투표전달(전체 시그널로 투표완료자 인원수 보이기)
      step 6. [방장 -> 전체 - 자동] 참여자 전체 투표가 완료되면 투표결과 시그널 전송
      step 7. [라이어 -> 방장 - 수동] 라이어를 맞췄을 경우 라이어 키워드 입력 후 방장에게 전송
      step 8. [방장 -> 전체 - 자동] 최종 결과 시그널로 전송 (라이어와 일반 참여자는 다른 결과를 보여야함)
      */

    startLiar({ state }) {
      const gameData = {
        gameId: "liar",
        step: 1,
        content: {},
      };
      state.session.signal({
        type: "game",
        data: JSON.stringify(gameData),
        to: [],
      });
    },
    toggleGamePanel({ state, commit }) {
      commit("SET_IS_GAMEPANEL", !state.isGamePanel);
      if (state.isGamePanel === true) {
        setTimeout(() => {
          const gameDiv = document.getElementById("game-area");
          gameDiv.scrollTo({
            top: gameDiv.scrollHeight - gameDiv.clientHeight,
            behavior: "smooth",
          });
        }, 50);
      }
    },
    async selectTopic({ rootGetters, state, dispatch }, topics) {
      // eslint-disable-next-line
      const inputOptions = Object.assign({}, topics);
      const { value: topic } = await Swal.fire({
        title: "라이어 게임 주제",
        input: "radio",
        width: 1000,
        allowOutsideClick: false,
        inputOptions,
        inputValidator: (value) => {
          if (!value) {
            return "주제를 선택해주세요!";
          }
          return "";
        },
      });
      if (topic) {
        axios({
          url: spring.videochat.liarKeyword(topics[topic]),
          method: "get",
          headers: { Authorization: `Bearer ${rootGetters.token}` },
        })
          .then(({ data }) => {
            const participants = [];
            participants.push(state.hostName);
            state.subscribers.forEach(function (subscriber) {
              participants.push(JSON.parse(subscriber.stream.connection.data).clientName);
            });
            const gameData = {
              gameId: "liar",
              step: 2,
              content: {
                participants,
                topic: topics[topic],
                keyword: data,
                liar: participants[Math.floor(Math.random() * participants.length)],
              },
            };
            state.session.signal({
              type: "game",
              data: JSON.stringify(gameData),
              to: [],
            });
          })
          .catch((err) => {
            Swal.fire({
              icon: "error",
              title: "주제 조회 실패!",
              showConfirmButton: false,
              timer: 1000,
            });
            dispatch("endLiar");
            console.log(err);
          });
      }
    },
    startLiarTalk({ state }) {
      state.startLiarTalkFlag = false;
      state.stopLiarTalkFlag = true;
      const gameData = {
        gameId: "liar",
        step: 3,
        content: {},
      };
      state.session.signal({
        type: "game",
        data: JSON.stringify(gameData),
        to: [],
      });
    },
    stopLiarTalk({ state }) {
      state.stopLiarTalkFlag = false;
      const gameData = {
        gameId: "liar",
        step: 4,
        content: {},
      };
      state.session.signal({
        type: "game",
        data: JSON.stringify(gameData),
        to: [],
      });
    },
    async clickVote({ state }) {
      const inputOptions = {};
      state.participants.forEach(function (participant) {
        inputOptions[participant] = participant;
      });
      const { value: liar } = await Swal.fire({
        title: "🔍 라이어 찾기 🔍",
        input: "select",
        inputOptions,
        inputPlaceholder: "누가 거짓말을 하고 있나요?",
        allowOutsideClick: false,
        inputValidator: (value) => {
          if (!value) {
            return "라이어를 선택해주세요!";
          }
          return "";
        },
      });

      const gameData = {
        gameId: "liar",
        step: 5,
        content: { selector: liar },
      };
      state.session.signal({
        type: "game",
        data: JSON.stringify(gameData),
        to: [],
      });
      state.gameCommentarys.push({
        comment: `📣 투표를 집계중입니다. 잠시만 기다려주세요!`,
      });
    },
    async clickKeyword({ state }) {
      const { value: keyword } = await Swal.fire({
        icon: "warning",
        title: "🔥 마지막 기회 🔥",
        input: "text",
        inputLabel: "라이어님 키워드를 맞춰주세요!",
        allowOutsideClick: false,
        inputPlaceholder: "키워드 입력",
      });
      const gameData = {
        gameId: "liar",
        step: 7,
        content: { answer: keyword },
      };
      state.session.signal({
        type: "game",
        data: JSON.stringify(gameData),
        to: [],
      });
    },
    clickGameResult({ state }, win) {
      if (win) {
        Swal.fire({
          icon: "success",
          title: "라이어 게임 결과",
          html: `<h2>🥳 승리 🥳</h2>키워드: ${state.keyword}`,
        });
      } else {
        Swal.fire({
          icon: "error",
          title: "라이어 게임 결과",
          html: `<h2>😭 패배 😭</h2>키워드: ${state.keyword}`,
        });
      }
    },
    endLiar({ state, dispatch, commit }) {
      // 초기화
      state.gameCommentarys.push({
        comment: `📣 라이어게임이 종료되었습니다 `,
      });
      commit("SET_LIAR_TOPIC", "");
      commit("SET_LIAR_KEYWORD", "");
      commit("SET_LIAR", "");
      commit("SET_VOTES", []);
      commit("SET_VOTE_COUNT", 0);

      dispatch("endGame");
    },
    startGame({ state, commit, rootGetters }) {
      if (state.hostName === rootGetters.currentUser) {
        axios({
          url: spring.videochat.game(state.mySessionId),
          method: "put",
          headers: { Authorization: `Bearer ${rootGetters.token}` },
        })
          .then((res) => {
            console.log(res);
          })
          .catch((err) => {
            console.log(err);
          });
      }
      commit("SET_PLAY_GAME", true);
      commit("SET_IS_GAMEPANEL", true);
    },
    endGame({ state, commit, rootGetters, dispatch }) {
      dispatch("setGameName", "");
      state.gameCommentarys.push({
        comment: `===========================`,
      });

      commit("SET_PLAY_GAME", false);
      commit("SET_IS_GAMEPANEL", false);
      commit("SET_GAME_PARTICIPANTS", []);

      if (state.hostName === rootGetters.currentUser) {
        axios({
          url: spring.videochat.game(state.mySessionId),
          method: "delete",
          headers: { Authorization: `Bearer ${rootGetters.token}` },
        })
          .then((res) => {
            console.log(res);
          })
          .catch((err) => {
            console.log(err);
          });
      }
    },
    startRoulette({ state }) {
      const gameData = {
        gameId: "roulette",
        step: 1,
        content: {},
      };
      state.session.signal({
        type: "game",
        data: JSON.stringify(gameData),
        to: [],
      });
    },

    playRoulette({ state, commit, dispatch }) {
      let delay = 400;
      const size = state.participants.length;
      for (let i = 0; i < 40; i += 1) {
        delay += i * i;
        // eslint-disable-next-line no-loop-func
        if (i > 30 && state.participants[i % size] === state.rouletteTargetName) {
          i = 1000;
          setTimeout(async () => {
            state.gameCommentarys.push({
              comment: `📣 룰렛게임 결과 ${state.rouletteTargetName}님이 당첨되셨습니다!!! `,
            });
            commit("SET_ROULETTE_POINTER", state.rouletteTargetName);
            setTimeout(async () => {
              commit("SET_ROULETTE_POINTER", "");
              setTimeout(async () => {
                commit("SET_ROULETTE_POINTER", state.rouletteTargetName);
                setTimeout(async () => {
                  commit("SET_ROULETTE_POINTER", "");
                  setTimeout(async () => {
                    commit("SET_ROULETTE_POINTER", state.rouletteTargetName);
                    setTimeout(async () => {
                      commit("SET_ROULETTE_POINTER", "");
                      setTimeout(async () => {
                        commit("SET_ROULETTE_POINTER", state.rouletteTargetName);
                        setTimeout(async () => {
                          commit("SET_ROULETTE_POINTER", "");
                          setTimeout(async () => {
                            commit("SET_ROULETTE_POINTER", state.rouletteTargetName);
                            setTimeout(async () => {
                              Swal.fire({
                                title: "룰렛 게임 결과",
                                html: `<h2>🥳 ${state.rouletteTargetName}님 당첨 🥳</h2>`,
                              });
                              commit("SET_ROULETTE_POINTER", "");
                              state.gameCommentarys.push({
                                comment: `📣 룰렛게임이 종료되었습니다 `,
                              });
                              setTimeout(async () => {
                                commit("SET_ROULETTE_TARGET_NAME", "");
                                commit("SET_ROULETTE_POINTER", "");
                                dispatch("endGame");
                              }, 1000);
                            }, 3000);
                          }, 100);
                        }, 100);
                      }, 100);
                    }, 100);
                  }, 100);
                }, 100);
              }, 100);
            }, 100);
          }, delay);
        } else {
          setTimeout(async () => {
            commit("SET_ROULETTE_POINTER", state.participants[i % size]);
          }, delay);
        }
      }
    },
  },
};
