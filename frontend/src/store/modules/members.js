import { createApi } from "@/api";
import router from "@/router";

const api = createApi();

export const Members = {
  namespaced: true,

  state: { token: localStorage.getItem("token") || "", titles: [], setTitle: "" },
  getters: { token: (state) => state.token },

  mutations: {
    GET_TITLES(state, payload) {
      state.titles = payload;
    },

    MODIFY_TITLE(state, payload) {
      state.setTitle = payload;
    },
  },
  actions: {
    getTitles({ commit, getters }, payload) {
      const value = { sidoName: payload };

      api({
        url: `/user/title`,
        method: "post",
        data: value,
        headers: {
          Authorization: `Bearer ${getters.token}`,
        },
      })
        .then((res) => {
          console.log(res);
          commit("GET_TITLES", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    modifyTitle({ commit, getters }, payload) {
      const value = { title: payload };
      api({
        url: `/user/title`,
        method: "PUT",
        data: value,
        headers: {
          Authorization: `Bearer ${getters.token}`,
        },
      }).then((res) => {
        console.log(res);
        commit("SET_TITLE", payload);

        router.push("/member/setTitle");
      });
    },
  },
};
