import { createStore } from "vuex";
import { Notices } from "@/store/modules/notices";
import { Accounts } from "./modules/accounts";

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
