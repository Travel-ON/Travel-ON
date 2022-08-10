import router from "@/router";
import axios from "axios";
import spring from "@/api/spring_boot";

/* eslint-disable no-return-assign */
export const Accounts = {
  /* eslint-disable */
  // namespaced: true,
  // state는 직접 접근하지 않는다.
  state: () => ({
    token: localStorage.getItem("token") || "", // 토큰
    currentUser: "", // 현재 유저 닉네임
    admin: false, // 관리자 여부
    title: "", // 유저 타이틀
    trophy: [], // 여행 횟수 리스트
    resident: false, // 현지인 여부
  }),
  getters: {
    isLoggedIn: (state) => !!state.token, // 로그인 여부
    token: (state) => state.token,
    currentUser: (state) => state.currentUser,
    admin: (state) => state.currentUser,
    title: (state) => state.title,
    trophy: (state) => state.trophy,
    resident: (state) => state.resident,
  },
  mutations: {
    SET_CURRENT_USER: (state, user) => (state.currentUser = user),
    SET_TOKEN: (state, token) => (state.token = token),
    SET_ADMIN: (state, admin) => (state.admin = admin),
    SET_TITLE: (state, title) => (state.title = title),
    SET_TROPHY: (state, trophy) => (state.trophy = trophy),
    SET_RESIDENT: (state, resident) => (state.resident = resident),
  },
  actions: {
    saveToken({ commit }, token) {
      /*
      state.token 추가
      localStorage에 token 추가
      */
      commit("SET_TOKEN", token);
      localStorage.setItem("token", token, Date.now() + 1);
    },
    removeToken({ commit }) {
      /*
      state.token 삭제
      localStorage에 token 추가
      */
      commit("SET_TOKEN", "");
      localStorage.setItem("token", "");
    },
    login({ commit, dispatch }, credentials) {
      /*
      POST: 사용자 입력정보를 login URL로 보내기
        성공하면
          응답 토큰 저장
          현재 사용자 정보 갱신
          메인 페이지로 이동
        실패하면
          에러 메시지 표시
      */
      console.log("login 메서드 실행");
      console.log(spring.accounts.login());
      console.log(credentials);
      axios({
        url: spring.accounts.login(),
        method: "post",
        data: credentials, // credentials.username, cresentials.password
      })
        .then(({ data }) => {
          console.log(data)
          const token = data.accessToken;
          const nickName = data.nickname;
          const userTitle = data.userTitle;
          const adminFlag = data.adminFlag;

          dispatch("saveToken", token);
          commit("SET_CURRENT_USER", nickName);
          commit("SET_ADMIN", adminFlag);
          commit("SET_TITLE", userTitle);
          dispatch("getLocation", true);
          dispatch("getTrophy");
          console.log(data);
          alert("로그인 완료!");
          router.push({ name: "home" });
        })
        .catch((err) => {
          console.error(err);
          alert("아이디가 없거나 비밀번호가 일치하지 않습니다!")
          router.push({ name: "MemberLogin" })
        });
    },
    logout({ commit, dispatch }) {
      dispatch("removeToken")
      commit("SET_CURRENT_USER", "");
      commit("SET_ADMIN", false);
      commit("SET_TITLE", "");
      commit("SET_RESIDENT", false);
      commit("SET_TROPHY", []);
      dispatch("removeLocation");
      alert("성공적으로 로그아웃 했습니다!");
      router.push({ name: "home" });
    },
    regist({ commit, dispatch }, formData) {
        /* 
        POST: 사용자 입력정보를 signup URL로 보내기
          성공하면
            응답 토큰 저장
            현재 사용자 정보 받기
            메인 페이지(ArticleListView)로 이동
          실패하면
            에러 메시지 표시
        */
      console.log("regist 메서드 실행")
      console.log(formData)
      console.log(spring.accounts.regist())
      axios({
        url: spring.accounts.regist(),
        method: "post",
        data: formData,
      })
        .then(res => {
          const token = res.accessToken;
          const nickName = res.nickname;
          const userTitle = res.userTitle;
          const adminFlag = res.adminFlag;
          dispatch("saveToken", token);
          commit("SET_CURRENT_USER", nickName);
          commit("SET_ADMIN", adminFlag);
          commit("SET_TITLE", userTitle);
          alert("회원가입 완료!")
          router.push({ name: "home" });
        })
        .catch(err => {
          console.error(err)
          alert("회원가입 실패")
        });
    },
    detail( { getters } ) {
      const token = getters.token;
      console.log(token);
      axios({
        url: spring.accounts.detail(),
        method: "get",
        headers: {
          Authorization: `Bearer ${ token }`,
        },
      })
        .then((res) => {
          console.log(res);
        })
        .catch((err) => {
          console.log(err);
        })
    },
    fetchCurrentUser({ commit, getters, dispatch }) {
      if (getters.isLoggedIn) {
        axios({
          url: spring.accounts.detail(),
          method: "get",
          headers: {
            Authorization: `Bearer ${ getters.token }`,
          },
        })
          .then((res) => {
            const nickName = res.data.nickname;
            const userTitle = res.data.userTitle;
            const adminFlag = res.data.adminFlag;
            commit("SET_CURRENT_USER", nickName);
            commit("SET_ADMIN", adminFlag);
            commit("SET_TITLE", userTitle);
            dispatch("getTrophy");
          })
          .catch((err) => {
            console.log(err);
            dispatch("removeToken");
          })
      }
    },
    getTrophy({ commit, getters }) {
      axios({
        url: spring.location.trophy(),
        method: "get",
        headers: {Authorization: `Bearer ${ getters.token }`},
      })
        .then((res) => {
          const trophyList = res.data;
          const trophyEngToKor = { // 지역명 한글 -> 영문 변환
            서울: "seoul",
            부산: "busan",
            대구: "daegu",
            인천: "incheon",
            광주: "gwangju",
            대전: "daejeon",
            울산: "ulsan",
            세종특별자치시: "sejong",
            경기: "gyeonggi",
            강원: "gangwon",
            충북: "chungcheongbuk",
            충남: "chungcheongnam",
            전북: "jeollabuk",
            전남: "jeollanam",
            경북: "gyeongsangbuk",
            경남: "gyeongsangnam",
            제주특별자치도: "jeju",
          }
          trophyList.forEach((element, index) => {
            trophyList[index].sidoName = trophyEngToKor[element.sidoName];
          });
          commit("SET_TROPHY", trophyList);
        })
        .catch((err) => {
          console.log(err);
        })
    },
    fetchTrophy({ commit, getters }) {
      axios({ // 업적 업데이트
        url: spring.location.trophy(),
        method: "post",
        headers: {Authorization: `Bearer ${getters.token}`},
        data: {dongCode: getters.dongCode}
      })
        .then((res)=> {
          console.log("지역 카운트값 증가!")
          if(res.response.status === 200) {
            commit("SET_RESIDENT", true);
          }
        })
        .catch((err)=> {
          console.log(err);
        })
    }
  }
}
