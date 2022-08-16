import { createApi } from "@/api";
import router from "@/router";

const api = createApi();

export const Members = {
  namespaced: true,

  state: { titles: [], setTitle: "" },
  getters: {},

  mutations: {
    GET_TITLES(state, payload) {
      state.titles = payload;
    },

    MODIFY_TITLE(state, payload) {
      state.setTitle = payload;
    },
  },
  actions: {
    getTitles({ commit, rootGetters }, payload) {
      const value = { sidoName: payload };

      api({
        url: `/user/title`,
        method: "post",
        data: value,
        headers: {
          Authorization: `Bearer ${rootGetters.token}`,
        },
      })
        .then((res) => {
          commit("GET_TITLES", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    modifyTitle({ commit, rootGetters }, payload) {
      const value = { title: payload };
      api({
        url: `/user/title`,
        method: "PUT",
        data: value,
        headers: {
          Authorization: `Bearer ${rootGetters.token}`,
        },
      }).then(() => {
        commit("SET_TITLE", payload, { root: true });

        router.push("/member/setTitle");
      });
    },
  },
};
