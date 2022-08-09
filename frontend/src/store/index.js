import { createStore } from "vuex";
import { Notices } from "@/store/modules/notices";
import { Accounts } from "./modules/accounts";
// eslint-disable-next-line import/named
import { QnAs } from "./modules/qnas";

export default createStore({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    Accounts,
    QnAs,
    Notices,
  },
});
