import { createStore } from "vuex";
import router from "@/router";
import { createApi } from "@/api";
import { Accounts } from "./modules/accounts";

const api = createApi();

export default createStore({
  state: { notices: [], notice: {} },
  getters: {},
  mutations: {
    GET_NOTICE(state, payload) {
      state.notice = payload;
    },
    GET_NOTICES(state, payload) {
      // notice 정렬
      payload.sort((a, b) => {
        return a.fixationFlag > b.fixationFlag ? -1 : 1;
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
    // 쿼리스트링으로 페이지가 안넘어감
    getNotices({ commit }, pageNumber) {
      let params = 0;
      console.log("여기랑");
      console.log(pageNumber);
      console.log("여기사이");
      params = pageNumber;
      api({
        url: `/notice/page`,
        method: "GET",
        params: { page: params },
      })
        .then((res) => {
          console.log(res.data.p.content);
          commit("GET_NOTICES", res.data.p.content);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    writeNotice({ commit }, newNotice) {
      api({
        url: `/notice/regist`,
        method: "POST",
        data: newNotice,
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
        data: payload,
      }).then(() => {
        commit("MODIFY_NOTICE", payload);
        router.push({
          name: "NoticeDetail",
          params: {
            noticeId: payload.noticeId,
          },
        });
      });
    },
    deleteNotice({ commit }, payload) {
      // eslint-disable-next-line no-unused-expressions
      commit;
      console.log(payload);
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
