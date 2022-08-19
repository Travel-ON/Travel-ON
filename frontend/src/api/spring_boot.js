const HOST = "https://i7b301.p.ssafy.io:3000/";

const USER = "api/user/";
const PLAN = "api/plan/";
const ALARM = "api/alarm/";
const VIDEOCHAT = "api/videochat/";

/* eslint-disable */
export default {
  accounts: {
    login: () => HOST + USER + "login", // 로그인
    logout: () => HOST + USER + "logout", // 로그아웃
    regist: () => HOST + USER + "regist", // 회원가입
    idCheck: () => HOST + USER + "idcheck", // id 중복검사
    nickCheck: () => HOST + USER + "nickcheck", // 닉네임 중복검사
    pwdCheck: () => HOST + USER + "check", // 비번 중복검사
    detail: () => HOST + USER + "detail", // 회원정보 조회
    userModify: () => HOST + USER + "modify", // 회원정보 수정
    userDelete: (id) => HOST + USER + "delete", // 회원정보 삭제
    userPassInit: () => HOST + USER + "email", // 비밀번호 찾기(초기화)
    userTitle: () => HOST + USER + "title", // 칭호 변경, 불러오기
    userTrophy: () => HOST + USER + "trophy/" + String(id) + "/", // 업적지도용 여행횟수 가져오기
    userTrophyUpdate: () => HOST + USER + "trophy", // 여행횟수 업데이트
  },
  location: {
    trophy: () => HOST + USER + "trophy", // 업적 지도용 카운트수
  },
  plan: {
    gugun: (sido) => HOST + PLAN + "load/" + sido,
    regist: () => HOST + PLAN + "regist",
    getHistory: () => HOST + PLAN + "page",
    filter: () => HOST + PLAN + "page/filter",
    modify: () => HOST + PLAN + "modify",
    delete: (visitPlaceId) => HOST + PLAN + "delete/" + String(visitPlaceId),
    getExpectHistory: () => HOST + PLAN + "expect/page",
    registExpect: () => HOST + PLAN + "expect/regist",
    deleteExpect: (visitExpectedId) => HOST + PLAN + "expect/delete/" + String(visitExpectedId),
    modifyExpect: (visitExpectedId) => HOST + PLAN + "expect/modify/" + String(visitExpectedId),
    filterExpect: () => HOST + PLAN + "expect/page/filter",
    getUserHistory: () => HOST + PLAN + "page/chat",
    getUserExpectHistory: () => HOST + PLAN + "expect/page/chat",
  },
  alarm: {
    alarmList: () => HOST + ALARM,
  },
  videochat: {
    room: (roomCode) => HOST + VIDEOCHAT + roomCode,
    creat: () => HOST + VIDEOCHAT,
    match: () => HOST + VIDEOCHAT + "match",
    leave: (roomCode) => HOST + VIDEOCHAT + "leave/" + roomCode,
    report: () => HOST + VIDEOCHAT + "report",
    game: (roomCode) => HOST + VIDEOCHAT + "game/" + roomCode,
    liarTopic: () => HOST + VIDEOCHAT + "liargame",
    liarKeyword: (topic) => HOST + VIDEOCHAT + "liargame/" + topic,
  },
};
