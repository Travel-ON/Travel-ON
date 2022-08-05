import { createStore } from "vuex";
// import { createApi } from "@/api";
import { Notices } from "@/store/modules/notices";
import { Accounts } from "./modules/accounts";
// const api = createApi();

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
