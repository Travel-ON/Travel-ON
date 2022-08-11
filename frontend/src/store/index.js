import { createStore } from "vuex";
// import router from "@/router";
import { Notices } from "@/store/modules/notices";
import { createApi } from "@/api";
import { Accounts } from "./modules/accounts";
import { QnAs } from "./modules/qnas";
import { Locations } from "./modules/locations";
import { Weather } from "./modules/weather";
import { Plans } from "./modules/plans";

const api = createApi();

export default createStore({
  state: { totalPage: "", faq: [] },
  getters: {},
  mutations: {
    TOTAL_PAGE(state, payload) {
      state.totalPage = payload;
    },
    GET_FAQ(state, payload) {
      state.faq = payload;
    },
  },
  actions: {
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
        url: `/notice/faq`,
        method: "POST",
        params: { page: 0, keyword: payload },
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
    QnAs,
    Notices,
    Locations,
    Weather,
    Plans,
  },
});
