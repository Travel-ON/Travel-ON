import { createApi } from "@/api";

const api = createApi();

export const QnAs = {
  namespaced: true,
  state: { qnas: [], qna: {} },
  getters: {},
  mutations: {
    MODIFY_QNA_ANSWER(state, payload) {
      state.qna.qnaid = payload.qnaId;
      state.qna.answer = payload.answer;
    },
    GET_QNAS(state, payload) {
      payload.sort((a, b) => {
        return a.qnaDate < b.qnaDate ? 1 : -1;
      });
      state.qnas = payload;
    },
    GET_QNA(state, payload) {
      state.qna = payload;
    },
    WRITE_QNA(state, payload) {
      state.qnas.push(payload);
    },
    MODIFY_QNA(state, payload) {
      state.qna = payload;
    },
  },
  actions: {
    getQnas({ commit, rootGetters }, payload) {
      let keyword;
      if (typeof payload === "string") {
        keyword = payload;
      }

      api({
        url: `/qna/`,
        method: "GET",
        params: { keyword },
        headers: {
          Authorization: `Bearer ${rootGetters.token}`,
        },
      })
        .then((res) => {
          commit("GET_QNAS", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getAdminQnas({ commit, rootGetters }, payload) {
      let keyword;
      if (typeof payload === "string") {
        keyword = payload;
      }

      api({
        url: `qna/admin`,
        method: "GET",
        params: { keyword },
        headers: {
          Authorization: `Bearer ${rootGetters.token}`,
        },
      })
        .then((res) => {
          commit("GET_QNAS", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getQna({ commit, rootGetters }, qnaid) {
      api({
        url: `/qna/detail/${qnaid}`,
        method: "GET",
        headers: {
          Authorization: `Bearer ${rootGetters.token}`,
        },
      }).then((res) => {
        commit("GET_QNA", res.data);
      });
    },
    writeQna({ commit, rootGetters }, newQna) {
      api({
        url: `/qna/regist`,
        method: "POST",
        headers: {
          Authorization: `Bearer ${rootGetters.token}`,
        },
        data: newQna,
      })
        .then(() => {
          commit("WRITE_QNA", newQna);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    modifyQnas({ commit, rootGetters }, payload) {
      api({
        url: `/qna/modify`,
        method: "PUT",
        headers: {
          Authorization: `Bearer ${rootGetters.token}`,
        },
        data: {
          qnaId: payload.qnaid,
          title: payload.title,
          content: payload.content,
        },
      }).then(() => {
        commit("MODIFY_QNA", payload);
      });
    },
    deleteQna({ rootGetters }, payload) {
      // eslint-disable-next-line no-unused-expressions
      api({
        url: `/qna/delete/${payload}`,
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${rootGetters.token}`,
        },
      }).then(() => {});
    },
    registQnaAnswer({ commit, rootGetters }, data) {
      api({
        url: `/qna/admin/regist`,
        method: "PUT",
        headers: {
          Authorization: `Bearer ${rootGetters.token}`,
        },
        data,
      }).then(() => {
        commit("MODIFY_QNA_ANSWER", data);
      });
    },
    modifyQnaAnswer({ commit, rootGetters }, data) {
      api({
        url: `/qna/admin/modify`,
        method: "PUT",
        headers: {
          Authorization: `Bearer ${rootGetters.token}`,
        },
        data,
      }).then(() => {
        commit("MODIFY_QNA_ANSWER", data);
      });
    },
    deleteQnaAnswer({ commit, rootGetters }, payload) {
      api({
        url: `/qna/admin/delete/${payload}`,
        method: "PUT",
        headers: {
          Authorization: `Bearer ${rootGetters.token}`,
        },
      }).then((res) => {
        commit("MODIFY_QNA_ANSWER", res);
      });
    },
    getNoAnswer({ commit, rootGetters }) {
      api({
        url: `/qna/admin/answer`,
        method: "GET",
        headers: {
          Authorization: `Bearer ${rootGetters.token}`,
        },
      }).then((res) => {
        commit("GET_QNAS", res.data);
      });
    },
    getCompletAnswer({ commit, rootGetters }) {
      api({
        url: `/qna/answer/complete`,
        method: "GET",
        headers: {
          Authorization: `Bearer ${rootGetters.token}`,
        },
      }).then((res) => {
        commit("GET_QNAS", res.data);
      });
    },
  },
};
