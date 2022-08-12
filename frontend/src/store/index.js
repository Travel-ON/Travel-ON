import { createStore } from "vuex";
import { Notices } from "@/store/modules/notices";
// import { createApi } from "@/api";
import { MeetingStore } from "@/store/modules/meetingStore";
import { Accounts } from "./modules/accounts";
import { QnAs } from "./modules/qnas";
import { Locations } from "./modules/locations";
import { Weather } from "./modules/weather";
import { Plans } from "./modules/plans";

// const api = createApi();

export default createStore({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    Accounts,
    QnAs,
    Notices,
    Locations,
    Weather,
    MeetingStore,
    Plans,
  },
});
