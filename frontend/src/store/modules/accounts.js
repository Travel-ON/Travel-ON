import router from "@/router";
// import axios from "axios";
// import spring from "@/api/spring_boot";

/* eslint-disable no-return-assign */
export const Accounts = {
  /* eslint-disable */
  // namespaced: true,
  // state는 직접 접근하지 않는다.
  state: () => ({
    token: localStorage.getItem("token") || "", // 토큰
    currentUser: "", // 현재 유저 닉네임
    admin: false, // 관리자 여부
  }),
  getters: {
    isLoggedIn: (state) => !!state.currentUser, // 로그인 여부
    isAdmin: (state) => state.admin, // 관리자 여부
    currentUser: (state) => state.currentUser, // 현재 로그인한 사용자명
    admin: (state) => state.admin,
  },
  mutations: {
    SET_CURRENT_USER: (state, user) => (state.currentUser = user),
    SET_TOKEN: (state, token) => (state.token = token),
    SET_ADMIN: (state, admin) => (state.admin = admin),
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
    login({ commit }, credentials) {
      /*
      POST: 사용자 입력정보를 login URL로 보내기
        성공하면
          응답 토큰 저장
          현재 사용자 정보 받기
          메인 페이지로 이동
        실패하면
          에러 메시지 표시
      */
      // axios({
      //   url: spring.accounts.login(),
      //   method: "post",
      //   data: credentials,
      // })
      //   .then((res) => {
      //     // const token = res.data.key;
      //     // dispatch("saveToken", token);
      //     // dispatch("fetchCurrentUser");
      //     router.push({ name: "home" });
      //     commit("SET_CURRENT_USER", res.nickname);
      //     commit("SET_ADMIN", res.adminFlag);
      //     console.log(res);
      //   })
      //   .catch((err) => {
      //     console.error(err.response.data);
      //   });
      commit("SET_CURRENT_USER", "김태훈");
      commit("SET_ADMIN", false);
      alert("로그인 완료!");
      router.push({ name: "home" });
    },
    logout({ commit }) {
      commit("SET_CURRENT_USER", "");
      commit("SET_ADMIN", false);
      alert("성공적으로 로그아웃 했습니다!");
      router.push({ name: "home" });
    },
  },
};
