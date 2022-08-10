import { createStore } from "vuex";
import router from "@/router";
import { createApi } from "@/api";
import { Accounts } from "./modules/accounts";
import { Locations } from "./modules/locations";
import { Weather } from "./modules/weather";

const api = createApi();

export default createStore({
  state: { notices: [], notice: {}, totalPage: "", faq: [] },
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
    TOTAL_PAGE(state, payload) {
      state.totalPage = payload;
    },
    GET_FAQ(state, payload) {
      state.faq = payload;
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
    getNotices({ commit }, pageNumber) {
      let params = 0;
      params = pageNumber;
      api({
        url: `/notice/page`,
        method: "GET",
        params: { page: params },
      })
        .then((res) => {
          commit("GET_NOTICES", res.data.p.content);
          commit("TOTAL_PAGE", res.data.p.totalPages);
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
    getFAQ({ commit }, faqPageNumber) {
      let params = 0;
      params = faqPageNumber;
      api({
        url: `/notice/faq`,
        method: "GET",
        params: { page: params },
      })
        .then((res) => {
          console.log(res.data.pf.content);
          commit("GET_FAQ", res.data.pf.content);
          commit("TOTAL_PAGE", res.data.pf.totalPages);
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
  modules: {
    Accounts,
    Locations,
    Weather,
  },
});
