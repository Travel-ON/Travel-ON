import { createStore } from "vuex";
import router from "@/router";
import { createApi } from "../api";
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
      state.notices.push(payload);
    },
    MODIFY_NOTICE(state, payload) {
      state.notice = payload;
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
          commit("GET_NOTICES", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    writeNotice({ commit }, newNotice) {
      api({
        url: `/notice/regist`,
        method: "POST",
        params: newNotice,
      })
        .then(() => {
          commit("WRITE_NOTICE", newNotice);
          router.push("/notice");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    modifyNotice({ commit }, payload) {
      api({
        url: `/notice/modify`,
        method: "PUT",
        params: payload,
      }).then(() => {
        commit("MODIFY_NOTICE", payload);
        router.push({
          name: "NoticeDetail",
          params: {
            id: payload.no,
          },
        });
      });
    },
    deleteNotice({ commit }, payload) {
      // eslint-disable-next-line no-unused-expressions
      commit;
      api({
        url: `/notice/delete/${payload}`,
        method: "DELETE",
      }).then(() => {
        router.push({ name: "NoticeList" });
      });
    },
  },
  modules: {
    Accounts,
  },
});
