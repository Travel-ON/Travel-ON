// import router from "@/router";
import { createApi } from "@/api";
import router from "@/router";

const api = createApi();

export const Notices = {
  namespaced: true,
  state: { token: localStorage.getItem("token") || "", notices: [], notice: {}, totalPage: "", faq: [] },
  getters: { token: (state) => state.token },
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
    // 쿼리스트링으로 페이지가 안넘어감
    getNotices({ commit }, pageNumber) {
      api({
        url: `/notice/page`,
        method: "GET",
        params: { page: pageNumber },
      })
        .then((res) => {
          commit("GET_NOTICES", res.data.p.content);
          commit("TOTAL_PAGE", res.data.p.totalPages);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    writeNotice({ commit, getters }, newNotice) {
      console.log("오지?");
      api({
        url: `/notice/regist`,
        method: "POST",
        data: newNotice,
        headers: {
          Authorization: `Bearer ${getters.token}`,
        },
      })
        .then(() => {
          commit("WRITE_NOTICE", newNotice);
          router.push("/notice");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    modifyNotice({ commit, getters }, payload) {
      api({
        url: `/notice/modify`,
        method: "PUT",
        data: payload,
        headers: {
          Authorization: `Bearer ${getters.token}`,
        },
      }).then(() => {
        commit("MODIFY_NOTICE", payload);
        if (payload.fixationFlag) {
          router.push("/notice");
        } else {
          router.push({
            name: "NoticeDetail",
            params: {
              noticeId: payload.noticeId,
            },
          });
        }
      });
    },
    deleteNotice({ commit, getters }, payload) {
      // eslint-disable-next-line no-unused-expressions
      commit;
      console.log(payload);
      api({
        url: `/notice/delete/${payload}`,
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${getters.token}`,
        },
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
    getSearchFAQ({ commit }, payload) {
      api({
        url: `/notice/faq/search`,
        method: "POST",
        params: { key: payload },
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
};
