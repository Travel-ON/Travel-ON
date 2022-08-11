// import router from "@/router";
import { createApi } from "@/api";

import router from "@/router";

const api = createApi();

export const QnAs = {
  namespaced: true,
  state: { token: localStorage.getItem("token") || "", qnas: [], qna: {} },
  getters: {
    getQna: (state) => state.qna,
    token: (state) => state.token,
  },
  mutations: {
    MODIFY_QNA_ANSWER(state, payload) {
      state.qna.qnaid = payload.qnaId;
      state.qna.answer = payload.answer;
    },
    GET_QNAS(state, payload) {
      payload.sort((a, b) => {
        return a.answerFlag > b.answerFlag ? 1 : -1;
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
    getQnas({ commit, getters }, payload) {
      let keyword;
      if (typeof payload === "string") {
        keyword = payload;
      }

      api({
        url: `/qna/`,
        method: "GET",
        params: { keyword },
        headers: {
          Authorization: `Bearer ${getters.token}`,
        },
      })
        .then((res) => {
          commit("GET_QNAS", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getAdminQnas({ commit, getters }, payload) {
      let keyword;
      if (typeof payload === "string") {
        keyword = payload;
      }

      api({
        url: `qna/admin`,
        method: "GET",
        params: { keyword },
        headers: {
          Authorization: `Bearer ${getters.token}`,
        },
      })
        .then((res) => {
          commit("GET_QNAS", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getQna({ commit, getters }, qnaid) {
      api({
        url: `/qna/detail/${qnaid}`,
        method: "GET",
        headers: {
          Authorization: `Bearer ${getters.token}`,
        },
      }).then((res) => {
        commit("GET_QNA", res.data);
      });
    },
    writeQna({ commit, getters }, newQna) {
      api({
        url: `/qna/regist`,
        method: "POST",
        headers: {
          Authorization: `Bearer ${getters.token}`,
        },
        data: newQna,
      })
        .then(() => {
          commit("WRITE_QNA", newQna);
          router.push("/qna");
        })
        .catch((err) => {
          console.log(err);
        });
    },
    modifyQnas({ commit, getters }, payload) {
      api({
        url: `/qna/modify`,
        method: "PUT",
        headers: {
          Authorization: `Bearer ${getters.token}`,
        },
        data: {
          qnaId: payload.qnaid,
          title: payload.title,
          content: payload.content,
        },
      }).then(() => {
        commit("MODIFY_QNA", payload);
        router.push({
          name: "QnaDetail",
          params: {
            qnaid: payload.qnaid,
          },
        });
      });
    },
    deleteQna({ commit, getters }, payload) {
      // eslint-disable-next-line no-unused-expressions
      commit;
      console.log(payload);
      api({
        url: `/qna/delete/${payload}`,
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${getters.token}`,
        },
      }).then(() => {
        router.push({ name: "QnaList" });
      });
    },
    registQnaAnswer({ commit, getters }, data) {
      api({
        url: `/qna/admin/regist`,
        method: "PUT",
        headers: {
          Authorization: `Bearer ${getters.token}`,
        },
        data,
      }).then(() => {
        commit("MODIFY_QNA_ANSWER", data);
        router.push({ name: "QnaList" });
      });
    },
    modifyQnaAnswer({ commit, getters }, data) {
      api({
        url: `/qna/admin/modify`,
        method: "PUT",
        headers: {
          Authorization: `Bearer ${getters.token}`,
        },
        data,
      }).then(() => {
        commit("MODIFY_QNA_ANSWER", data);
        router.push({ name: "QnaList" });
      });
    },
    deleteQnaAnswer({ commit, getters }, payload) {
      api({
        url: `/qna/admin/delete/${payload}`,
        method: "PUT",
        headers: {
          Authorization: `Bearer ${getters.token}`,
        },
      }).then((res) => {
        commit("MODIFY_QNA_ANSWER", res);
        router.push({ name: "QnaList" });
      });
    },
    getNoAnswer({ commit, getters }) {
      api({
        url: `/qna/admin/answer`,
        method: "GET",
        headers: {
          Authorization: `Bearer ${getters.token}`,
        },
      }).then((res) => {
        console.log(res);
        commit("MODIFY_QNA_ANSWER", res);
      });
    },
  },
};
