import { createStore } from "vuex";
import { createApi } from "@/api";
import router from "@/router";
import { Accounts } from "./modules/accounts";
import { Notices } from "@/store/modules/notices";
const api = createApi();

export default createStore({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    Accounts,
    Notices,
  },
});
