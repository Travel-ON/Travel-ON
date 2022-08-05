import { createStore } from "vuex";
import { createApi } from "@/api";
import router from "@/router";
import { Accounts } from "./modules/accounts";

const api = createApi();

export default createStore({
  state: {
    notices: [],
    notice: {},
  },
  getters: {},
  mutations: {
    GET_NOTICE(state, payload) {
      state.notice = payload;
    },
    GET_NOTICES(state, payload) {
      // notice 정렬
      payload.sort((a, b) => {
        return a.fixation_flag > b.fixation_flag ? -1 : 1;
      });
      state.notices = payload;
    },
    WRITE_NOTICE(state, payload) {
      console.log(payload);
      state.notices.push(payload);
      console.log(state.notices);
    },
  },
  actions: {
    getNotice({ commit }, noticeId) {
      api({
        url: `/notice/detail/${noticeId}`,
        method: "GET",
      })
        .then((res) => {
          commit("GET_NOTICE", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getNotices({ commit }, payload) {
      let params = null;
      if (payload) {
        params = payload;
      }
      api({
        url: `/notice/page`,
        moethod: "GET",
        params,
      })
        .then((res) => {
          console.log(res);
          commit("GET_NOTICES", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    writeNotice({ commit }, newBoard) {
      api({
        url: `/notice/regist`,
        method: "POST",
        params: newBoard,
      })
        .then(() => {
          commit("WRITE_NOTICE", newBoard);
          router.push("/notice");
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  modules: {
    Accounts,
  },
});
